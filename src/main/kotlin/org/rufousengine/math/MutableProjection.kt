package org.rufousengine.math

import kotlin.math.tan

/**
 * A mutable 4x4 projection matrix with symmetric frustum.
 *
 * For a general purpose matrix, see [Matrix4] instead.
 *
 * @property[observer] An observer that gets notified every time this matrix changes. Can be null.
 * @constructor Creates the identity projection matrix.
 */
class MutableProjection : Projection {
    val observer: ((Projection) -> Unit)?

    constructor(observer: ((Projection) -> Unit)? = null) : super() {
        this.observer = observer
    }
    constructor(other: Projection, observer: ((Projection) -> Unit)? = null) : super(other) {
        this.observer = observer
    }

    private var transposeDirty = true
    private val _transpose by lazy { MutableProjection() }
    /** The transpose of this matrix. It creates a new lazy Matrix4 instance. */
    override val transpose : Projection
        get() {
            if(transposeDirty) {
                transpose(_transpose)

                transposeDirty = false
            }

            return _transpose
        }
    private var inverseDirty = true
    private val _inverse by lazy { MutableProjection() }
    /** The inverse of this matrix. It creates a new lazy Matrix4 instance. */
    override val inverse : Projection
        get() {
            if(inverseDirty) {
                inverse(_inverse)

                inverseDirty = false
            }

            return _inverse
        }

    fun set(other: Projection) = set(other.e00, other.e11, other.e22, other.e23, other.e32, other.e33)
    /** Do not set values directly unless you know what you're doing. */
    internal fun set(e00: Float, e11: Float, e22: Float, e23: Float, e32: Float, e33: Float) : MutableProjection {
        if(equals(e00, e11, e22, e23, e32, e33)) {
            return this
        }

        components[0] = e00
        components[5] = e11
        components[10] = e22
        components[11] = e23
        components[14] = e32
        components[15] = e33

        transposeDirty = true
        inverseDirty = true

        observer?.invoke(this)

        return this
    }

    operator fun plusAssign(other: Projection) { add(other) }
    operator fun minusAssign(other: Projection) { subtract(other) }
    operator fun timesAssign(scalar: Float) { scale(scalar) }
    operator fun divAssign(scalar: Float) { scale(1 / scalar) }
    operator fun timesAssign(other: Projection) { multiply(other) }

    /**
     * Sets this matrix to the identity.
     *
     * @return This matrix for chaining.
     */
    fun identity() = set(1f, 1f, 1f, 0f, 0f, 1f)
    /**
     * Sets every component to 0.
     *
     * @return This matrix for chaining.
     */
    fun zero() = set(0f, 0f, 0f, 0f, 0f, 0f)

    /**
     * Transposes this matrix.
     *
     * @return This matrix for chaining.
     */
    fun transpose() = transpose(this)
    /**
     * Inverts this matrix.
     *
     * @return This matrix for chaining.
     */
    fun inverse() = inverse(this)

    /**
     * Scales this matrix (i.e., multiplies each component with [scalar]).
     *
     * @param[scalar] The scalar to multiply the matrix with.
     * @return This matrix for chaining.
     */
    fun scale(scalar: Float) = scale(scalar, this)
    /**
     * Adds [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun add(other: Projection) = add(other, this)
    /**
     * Subtracts [other] from this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun subtract(other: Projection) = subtract(other, this)

    /**
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiply(other: Projection) = multiply(other, this)
    /**
     * Multiplies [other] with this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiplyLeft(other: Projection) = multiplyLeft(other, this)

    /**
     * Sets this matrix as a symmetric orthographic projection.
     *
     * @param[width] The width of the frustum in world units.
     * @param[height] The height of the frustum in world units.
     * @param[near] The distance to the near plane in world units. Must be positive.
     * @param[far] The distance to the far plan in world units. Must be greater than [near].
     * @return This matrix for chaining.
     */
    fun setOrthographic(width: Float, height: Float, near: Float, far: Float) : MutableProjection {
        if(width < 0f || height < 0f || near <= 0f) {
            throw IllegalArgumentException("width, height and near must be positives.")
        }
        if(far <= near) {
            throw IllegalArgumentException("far must be greater than near.")
        }

        val a = 1 / (far - near)
        val r = width * 0.5f
        val t = height * 0.5f

        val e00 = 1 / r
        val e11 = 1 / t
        val e22 = -2 * a
        val e32 = -(far + near) * a

        return set(e00, e11, e22, 0f, e32, 1f)
    }

    /**
     * Sets this matrix as a symmetric perspective projection.
     *
     * @param[fieldOfView] The vertical field of view in degrees.
     * @param[aspectRatio] The aspect ratio.
     * @param[near] The distance to the near plane in world units. Must be positive.
     * @param[far] The distance to the far plan in world units. Must be greater than [near].
     * @return This matrix for chaining.
     */
    fun setPerspective(fieldOfView: Float, aspectRatio: Float, near: Float, far: Float) : MutableProjection {
        if(fieldOfView <= 0f || fieldOfView > 180f) {
            throw IllegalArgumentException("fieldOfView must be in range (0, 180]")
        }
        if(aspectRatio < 0f || near <= 0f) {
            throw IllegalArgumentException("aspectRatio and near must be positives.")
        }
        if(far <= near) {
            throw IllegalArgumentException("far must be greater than near.")
        }

        val a = 1f / tan(fieldOfView.toRadians() * 0.5f)
        val b = 1f / (far - near)
        val e00 = a / aspectRatio
        val e22 = -(far + near) * b
        val e32 = -2f * far * near * b

        return set(e00, a, e22, -1f, e32, 0f)
    }
}