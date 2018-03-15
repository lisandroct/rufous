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

    override var e00: Float
    get() = components[0]
    set(value) {
        if(value isCloseTo components[0]) {
            return
        }

        components[0] = value

        setDirty()
    }
    override var e11: Float
        get() = components[5]
        set(value) {
            if(value isCloseTo components[5]) {
                return
            }

            components[5] = value

            setDirty()
        }
    override var e22: Float
        get() = components[10]
        set(value) {
            if(value isCloseTo components[10]) {
                return
            }

            components[10] = value

            setDirty()
        }
    override var e23: Float
        get() = components[11]
        set(value) {
            if(value isCloseTo components[11]) {
                return
            }

            components[11] = value

            setDirty()
        }
    override var e32: Float
        get() = components[14]
        set(value) {
            if(value isCloseTo components[14]) {
                return
            }

            components[14] = value

            setDirty()
        }
    override var e33: Float
        get() = components[15]
        set(value) {
            if(value isCloseTo components[15]) {
                return
            }

            components[15] = value

            setDirty()
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

        setDirty()

        return this
    }

    private fun setDirty() {
        transposeDirty = true
        inverseDirty = true

        observer?.invoke(this)
    }

    operator fun plusAssign(other: Projection) { add(other) }
    operator fun minusAssign(other: Projection) { subtract(other) }
    operator fun timesAssign(scalar: Float) { scale(scalar) }
    operator fun divAssign(scalar: Float) { scale(1 / scalar) }

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