package org.rufousengine.math

/**
 * A mutable two-dimensional vector.
 *
 * The purpose of the Vector classes is to represent directions. To represent positions, use [Point] instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[observer] An observer that gets notified every time this vector changes. Can be null.
 * @constructor Creates a two-dimensional vector pointing towards (x, y).
 */
class MutableVector2(x: Float = 0f, y: Float = 0f, observer: ((Vector2) -> Unit)? = null) : Vector2(x, y) {
    val observer = observer

    constructor(other: Vector2, observer: ((Vector2) -> Unit)? = null) : this(other.components, observer)
    constructor(other: Vector3, observer: ((Vector2) -> Unit)? = null) : this(other.components, observer)
    constructor(other: Vector4, observer: ((Vector2) -> Unit)? = null) : this(other.components, observer)
    constructor(components: FloatArray, observer: ((Vector2) -> Unit)? = null) : this(components[0], components[1], observer)

    override var x: Float
        get() = components[0]
        set(value) {
            if(value isCloseTo components[0]) {
                return
            }

            components[0] = value

            observer?.invoke(this)
        }
    override var y: Float
        get() = components[1]
        set(value) {
            if(value isCloseTo components[1]) {
                return
            }

            components[1] = value

            observer?.invoke(this)
        }

    fun set(other: Vector2) = set(other.components)
    fun set(other: Vector3) = set(other.components)
    fun set(other: Vector4) = set(other.components)
    fun set(components: FloatArray) = set(components[0], components[1])
    fun set(x: Float = 0f, y: Float = 0f) : MutableVector2 {
        if(equals(x, y)) {
            return this
        }

        components[0] = x
        components[1] = y

        observer?.invoke(this)

        return this
    }

    operator fun set(index: Int, value: Float) {
        if(value isCloseTo components[index]) {
            return
        }

        components[index] = value

        observer?.invoke(this)
    }

    operator fun plusAssign(other: Vector2) { add(other) }
    operator fun minusAssign(other: Vector2) { subtract(other) }
    operator fun timesAssign(scalar: Float) { scale(scalar) }
    operator fun divAssign(scalar: Float) { scale(1 / scalar) }

    /**
     * Negates each component.
     *
     * @return This vector for chaining.
     */
    fun negate() = negate(this)
    /**
     * Normalizes the vector.
     *
     * @return This vector for chaining.
     */
    fun normalize() = normalize(this)
    /**
     * Applies the absolute value to each component.
     *
     * @return This vector for chaining.
     */
    fun abs() = abs(this)
    /**
     * Scales the vector. Multiplies each component with [scalar].
     *
     * @param[scalar] The scalar to multiply the vector with.
     * @return This vector for chaining.
     */
    fun scale(scalar: Float) = scale(scalar, this)

    /**
     * Adds this vector and [other].
     *
     * @param[other] The other vector.
     * @return This vector for chaining.
     */
    fun add(other: Vector2) = add(other, this)
    /**
     * Adds this vector and ([x], [y]).
     *
     * @return This vector forchaining.
     */
    fun add(x: Float, y: Float) = add(x, y, this)
    /**
     * Subtracts this vector and [other].
     *
     * @param[other] The other vector.
     * @return This vector forchaining.
     */
    fun subtract(other: Vector2) = subtract(other, this)
    /**
     * Subtracts this vector and ([x], [y]).
     *
     * @return This vector forchaining.
     */
    fun subtract(x: Float, y: Float) = subtract(x, y, this)

    /**
     * Returns the vector composed of the smallest components between this vector and [other].
     *
     * @param[other] The other vector.
     * @return This vector forchaining.
     */
    fun min(other: Vector2) = min(other, this)
    /**
     * Returns the vector composed of the smallest components between this vector and ([x], [y]).
     *
     * @return This vector forchaining.
     */
    fun min(x: Float, y: Float) = min(x, y, this)
    /**
     * Returns the vector composed of the largest components between this vector and [other].
     *
     * @param[other] The other vector.
     * @return This vector forchaining.
     */
    fun max(other: Vector2) = max(other, this)
    /**
     * Returns the vector composed of the largest components between this vector and ([x], [y]).
     *
     * @return This vector forchaining.
     */
    fun max(x: Float, y: Float) = max(x, y, this)

    /**
     * Projects this vector onto [other].
     *
     * @param[other] The other vector.
     * @return This vector forchaining.
     */
    fun projectOnto(other: Vector2) = projectOnto(other, this)
    /**
     * Projects this vector onto ([x], [y]).
     *
     * @return This vector forchaining.
     */
    fun projectOnto(x: Float, y: Float) = projectOnto(x, y, this)

    /**
     * Rejects this vector from [other].
     *
     * @param[other] The other vector.
     * @return This vector forchaining.
     */
    fun rejectFrom(other: Vector2) = rejectFrom(other, this)
    /**
     * Rejects this vector from ([x], [y]).
     *
     * @return This vector forchaining.
     */
    fun rejectFrom(x: Float, y: Float) = rejectFrom(x, y, this)

    /**
     * Multiplies [matrix] with this vector.
     *
     * Wrapper to [Matrix2.multiply].
     *
     * @param[matrix] The matrix.
     * @return This vector for chaining.
     */
    fun multiplyLeft(matrix: Matrix2) = multiplyLeft(matrix, this)
}