package org.rufousengine.math

/**
 * A mutable 2x2 row-major matrix.
 *
 * @property[observer] An observer that gets notified every time this matrix changes. Can be null.
 * @constructor Creates a 2x2 matrix with the given components.
 */
class MutableMatrix2(e00: Float, e01: Float, e10: Float, e11: Float, observer: ((Matrix2) -> Unit)? = null) : Matrix2(e00, e01, e10, e11) {
    val observer = observer

    constructor(observer: ((Matrix2) -> Unit)? = null) : this(1f, 0f, 0f, 1f, observer)
    constructor(other: Matrix2, observer: ((Matrix2) -> Unit)? = null) : this(other.components, observer)
    constructor(components: FloatArray, observer: ((Matrix2) -> Unit)? = null) : this(
            components[0], components[1],
            components[2], components[3],
            observer
    )
    constructor(a: Vector2, b: Vector2, observer: ((Matrix2) -> Unit)? = null) : this(
            a.x, b.x,
            a.y, b.y,
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
    override var e10: Float
        get() = components[2]
        set(value) {
            if(value isCloseTo components[2]) {
                return
            }

            components[2] = value

            setDirty()
        }
    override var e11: Float
        get() = components[3]
        set(value) {
            if(value isCloseTo components[3]) {
                return
            }

            components[3] = value

            setDirty()
        }

    private var transposeDirty = true
    private val _transpose by lazy { MutableMatrix2() }
    /** The transpose of this matrix. It creates a new lazy Matrix4 instance. */
    override val transpose : Matrix2
        get() {
            if(transposeDirty) {
                transpose(_transpose)

                transposeDirty = false
            }

            return _transpose
        }
    private var inverseDirty = true
    private val _inverse by lazy { MutableMatrix2() }
    /** The inverse of this matrix. It creates a new lazy Matrix4 instance. */
    override val inverse : Matrix2
        get() {
            if(inverseDirty) {
                inverse(_inverse)

                inverseDirty = false
            }

            return _inverse
        }

    fun set(other: Matrix2) = set(other.components)
    fun set(components: FloatArray) = set(
            components[0], components[1],
            components[2], components[3]
    )
    fun set(a: Vector2, b: Vector2) = set(
            a.x, b.x,
            a.y, b.y
    )
    fun set(e00: Float, e01: Float, e10: Float, e11: Float) : MutableMatrix2 {
        if(equals(e00, e01, e10, e11)) {
            return this
        }

        components[0] = e00
        components[1] = e01

        components[2] = e10
        components[3] = e11

        setDirty()

        return this
    }

    operator fun set(row: Int, column: Int, value: Float) {
        val index = 2 * row + column

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

    operator fun plusAssign(other: Matrix2) { add(other) }
    operator fun minusAssign(other: Matrix2) { subtract(other) }
    operator fun timesAssign(scalar: Float) { scale(scalar) }
    operator fun divAssign(scalar: Float) { scale(1 / scalar) }
    operator fun timesAssign(other: Matrix2) { multiply(other) }

    /**
     * Sets this matrix to the identity.
     *
     * @return This matrix for chaining.
     */
    fun identity() = set(1f, 0f, 0f, 1f)
    /**
     * Sets every component to 0.
     *
     * @return This matrix for chaining.
     */
    fun zero() = set(0f, 0f, 0f, 0f)

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
    fun add(other: Matrix2) = add(other, this)
    /**
     * Subtracts [other] from this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun subtract(other: Matrix2) = subtract(other, this)

    /**
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiply(other: Matrix2) = multiply(other, this)
    /**
     * Multiplies [other] with this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiplyLeft(other: Matrix2) = multiplyLeft(other, this)
}