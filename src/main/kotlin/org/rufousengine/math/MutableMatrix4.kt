package org.rufousengine.math

import kotlin.math.tan

/**
 * A mutable 4x4 row-major matrix.
 *
 * This is a general matrix representation. To represent transformations or projections, there are more optimized (and limited) alternatives.
 * See [Transformation] and [Projection] instead.
 *
 * @property[observer] An observer that gets notified every time this matrix changes. Can be null.
 * @constructor Creates a 4x4 matrix with the given components.
 */
class MutableMatrix4(e00: Float, e01: Float, e02: Float, e03: Float, e10: Float, e11: Float, e12: Float, e13: Float, e20: Float, e21: Float, e22: Float, e23: Float, e30: Float, e31: Float, e32: Float, e33: Float, observer: ((Matrix4) -> Unit)? = null) : Matrix4(e00, e01, e02, e03, e10, e11, e12, e13, e20, e21, e22, e23, e30, e31, e32, e33) {
    val observer = observer

    constructor(observer: ((Matrix4) -> Unit)? = null) : this(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, observer)
    constructor(other: Projection, observer: ((Matrix4) -> Unit)? = null) : this(other.components, observer)
    constructor(other: Transformation, observer: ((Matrix4) -> Unit)? = null) : this(other.components, observer)
    constructor(other: Matrix4, observer: ((Matrix4) -> Unit)? = null) : this(other.components, observer)
    constructor(components: FloatArray, observer: ((Matrix4) -> Unit)? = null) : this(
            components[0], components[1], components[2], components[3],
            components[4], components[5], components[6], components[7],
            components[8], components[9], components[10], components[11],
            components[12], components[13], components[14], components[15],
            observer
    )
    constructor(a: Vector4, b: Vector4, c: Vector4, d: Vector4, observer: ((Matrix4) -> Unit)? = null) : this(
            a.x, b.x, c.x, d.x,
            a.y, b.y, c.y, d.y,
            a.z, b.z, c.z, d.z,
            a.w, b.w, c.w, d.w,
            observer
    )

    override var e00: Float
        get() = components[0]
        set(value) {
            if(value isCloseTo components[0]) {
                return
            }

            components[0] = value

            setDirty()
        }
    override var e01: Float
        get() = components[1]
        set(value) {
            if(value isCloseTo components[1]) {
                return
            }

            components[1] = value

            setDirty()
        }
    override var e02: Float
        get() = components[2]
        set(value) {
            if(value isCloseTo components[2]) {
                return
            }

            components[2] = value

            setDirty()
        }
    override var e03: Float
        get() = components[3]
        set(value) {
            if(value isCloseTo components[3]) {
                return
            }

            components[3] = value

            setDirty()
        }
    override var e10: Float
        get() = components[4]
        set(value) {
            if(value isCloseTo components[4]) {
                return
            }

            components[4] = value

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
    override var e12: Float
        get() = components[6]
        set(value) {
            if(value isCloseTo components[6]) {
                return
            }

            components[6] = value

            setDirty()
        }
    override var e13: Float
        get() = components[7]
        set(value) {
            if(value isCloseTo components[7]) {
                return
            }

            components[7] = value

            setDirty()
        }
    override var e20: Float
        get() = components[8]
        set(value) {
            if(value isCloseTo components[8]) {
                return
            }

            components[8] = value

            setDirty()
        }
    override var e21: Float
        get() = components[9]
        set(value) {
            if(value isCloseTo components[9]) {
                return
            }

            components[9] = value

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
    override var e30: Float
        get() = components[12]
        set(value) {
            if(value isCloseTo components[12]) {
                return
            }

            components[12] = value

            setDirty()
        }
    override var e31: Float
        get() = components[13]
        set(value) {
            if(value isCloseTo components[13]) {
                return
            }

            components[13] = value

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
    private val _transpose by lazy { MutableMatrix4() }
    /** The transpose of this matrix. It creates a new lazy Matrix4 instance. */
    override val transpose : Matrix4
        get() {
            if(transposeDirty) {
                transpose(_transpose)

                transposeDirty = false
            }

            return _transpose
        }
    private var inverseDirty = true
    private val _inverse by lazy { MutableMatrix4() }
    /** The inverse of this matrix. It creates a new lazy Matrix4 instance. */
    override val inverse : Matrix4
        get() {
            if(inverseDirty) {
                inverse(_inverse)

                inverseDirty = false
            }

            return _inverse
        }

    fun set(other: Projection) = set(other.components)
    fun set(other: Transformation) = set(other.components)
    fun set(other: Matrix4) = set(other.components)
    fun set(components: FloatArray) = set(
            components[0], components[1], components[2], components[3],
            components[4], components[5], components[6], components[7],
            components[8], components[9], components[10], components[11],
            components[12], components[13], components[14], components[15]
    )
    fun set(a: Vector4, b: Vector4, c: Vector4, d: Vector4) = set(
            a.x, b.x, c.x, d.x,
            a.y, b.y, c.y, d.y,
            a.z, b.z, c.z, d.z,
            a.w, b.w, c.w, d.w
    )
    fun set(e00: Float, e01: Float, e02: Float, e03: Float, e10: Float, e11: Float, e12: Float, e13: Float, e20: Float, e21: Float, e22: Float, e23: Float, e30: Float, e31: Float, e32: Float, e33: Float) : MutableMatrix4 {
        if(equals(e00, e01, e02, e03, e10, e11, e12, e13, e20, e21, e22, e23, e30, e31, e32, e33)) {
            return this
        }

        components[0] = e00
        components[1] = e01
        components[2] = e02
        components[3] = e03

        components[4] = e10
        components[5] = e11
        components[6] = e12
        components[7] = e13

        components[8] = e20
        components[9] = e21
        components[10] = e22
        components[11] = e23

        components[12] = e30
        components[13] = e31
        components[14] = e32
        components[15] = e33

        setDirty()

        return this
    }

    operator fun set(row: Int, column: Int, value: Float) {
        val index = 4 * row + column

        if(value isCloseTo components[index]) {
            return
        }

        components[index] = value

        setDirty()
    }

    private fun setDirty() {
        transposeDirty = true
        inverseDirty = true

        observer?.invoke(this)
    }

    operator fun plusAssign(other: Projection) { add(other) }
    operator fun plusAssign(other: Transformation) { add(other) }
    operator fun plusAssign(other: Matrix4) { add(other) }
    operator fun minusAssign(other: Projection) { subtract(other) }
    operator fun minusAssign(other: Transformation) { subtract(other) }
    operator fun minusAssign(other: Matrix4) { subtract(other) }
    operator fun timesAssign(scalar: Float) { scale(scalar) }
    operator fun divAssign(scalar: Float) { scale(1 / scalar) }
    operator fun timesAssign(other: Projection) { multiply(other) }
    operator fun timesAssign(other: Transformation) { multiply(other) }
    operator fun timesAssign(other: Matrix4) { multiply(other) }

    /**
     * Sets this matrix to the identity.
     *
     * @return This matrix for chaining.
     */
    fun identity() = set(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
    /**
     * Sets every component to 0.
     *
     * @return This matrix for chaining.
     */
    fun zero() = set(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

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
     * Adds [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun add(other: Transformation) = add(other, this)
    /**
     * Adds [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun add(other: Matrix4) = add(other, this)

    /**
     * Subtracts [other] from this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun subtract(other: Projection) = subtract(other, this)
    /**
     * Subtracts [other] from this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun subtract(other: Transformation) = subtract(other, this)
    /**
     * Subtracts [other] from this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun subtract(other: Matrix4) = subtract(other, this)

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
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiply(other: Transformation) = multiply(other, this)
    /**
     * Multiplies [other] with this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiplyLeft(other: Transformation) = multiplyLeft(other, this)

    /**
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiply(other: Matrix4) = multiply(other, this)
    /**
     * Multiplies [other] with this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiplyLeft(other: Matrix4) = multiplyLeft(other, this)

    /**
     * Sets this matrix as a symmetric orthographic projection.
     *
     * @param[width] The width of the frustum in world units.
     * @param[height] The height of the frustum in world units.
     * @param[near] The distance to the near plane in world units. Must be positive.
     * @param[far] The distance to the far plan in world units. Must be greater than [near].
     * @return This matrix for chaining.
     */
    fun setOrthographic(width: Float, height: Float, near: Float, far: Float) : MutableMatrix4 {
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

        return set(
                e00, 0f, 0f, 0f,
                0f, e11, 0f, 0f,
                0f, 0f, e22, 0f,
                0f, 0f, e32, 1f
        )
    }
    /**
     * Sets this matrix as an orthographic projection.
     *
     * @return This matrix for chaining.
     */
    fun setOrthographic(top: Float, bottom: Float, right: Float, left: Float, near: Float, far: Float) : MutableMatrix4 {
        if(top < 0f || right < 0f || near <= 0f) {
            throw IllegalArgumentException("top, right and near must be positives.")
        }
        if(bottom > 0f || left > 0f) {
            throw IllegalArgumentException("bottom and left must be negatives.")
        }
        if(far <= near) {
            throw IllegalArgumentException("far must be greater than near.")
        }

        val a = 1 / (right - left)
        val b = 1 / (top - bottom)
        val c = 1 / (far - near)

        val e00 = 2 * a
        val e11 = 2 * b
        val e22 = -2f * c
        val e30 = -(right + left) * a
        val e31 = -(top + bottom) * b
        val e32 = -(far + near) * c
        val e33 = 1f

        return set(
                e00, 0f, 0f, 0f,
                0f, e11, 0f, 0f,
                0f, 0f, e22, 0f,
                e30, e31, e32, e33
        )
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
    fun setPerspective(fieldOfView: Float, aspectRatio: Float, near: Float, far: Float) : MutableMatrix4 {
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

        return set(
                e00, 0f, 0f, 0f,
                0f, a, 0f, 0f,
                0f, 0f, e22, -1f,
                0f, 0f, e32, 0f
        )
    }
    /**
     * Sets this matrix as a perspective projection.
     *
     * @return This matrix for chaining.
     */
    fun setPerspective(top: Float, bottom: Float, right: Float, left: Float, near: Float, far: Float) : MutableMatrix4 {
        if(top < 0f || right < 0f || near <= 0f) {
            throw IllegalArgumentException("top, right and near must be positives.")
        }
        if(bottom > 0f || left > 0f) {
            throw IllegalArgumentException("bottom and left must be negatives.")
        }
        if(far <= near) {
            throw IllegalArgumentException("far must be greater than near.")
        }

        val a = 2f * near
        val b = 1 / (right - left)
        val c = 1 / (top - bottom)
        val d = 1 / (far - near)

        val e00 = a * b
        val e11 = a * c
        val e20 = (right + left) * b
        val e21 = (top + bottom) * c
        val e22 = -(far + near) * d
        val e32 = -a * far * d

        return set(
                e00, 0f, 0f, 0f,
                0f, e11, 0f, 0f,
                e20, e21, e22, -1f,
                0f, 0f, e32, 0f
        )
    }
}