package org.rufousengine.math

/**
 * A mutable four-dimensional vector.
 *
 * The purpose of the Vector classes is to represent directions. To represent positions, use [Point] instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @property[w] The w component.
 * @property[observer] An observer that gets notified every time this vector changes. Can be null.
 * @constructor Creates a four-dimensional vector pointing towards (x, y, z, w).
 */
class MutableVector4(x: Float = 0f, y: Float = 0f, z: Float = 0f, w: Float = 0f, observer: ((Vector4) -> Unit)? = null) : Vector4(x, y, z, w) {
    val observer = observer

    constructor(other: Vector2, observer: ((Vector4) -> Unit)? = null) : this(other.x, other.y, 0f, 0f, observer)
    constructor(other: Vector3, observer: ((Vector4) -> Unit)? = null) : this(other.x, other.y, other.z, 0f, observer)
    constructor(other: Vector4, observer: ((Vector4) -> Unit)? = null) : this(other.components, observer)
    constructor(components: FloatArray, observer: ((Vector4) -> Unit)? = null) : this(components[0], components[1], components[2], components[3], observer)

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
    override var z: Float
        get() = components[2]
        set(value) {
            if(value isCloseTo components[2]) {
                return
            }

            components[2] = value

            observer?.invoke(this)
        }
    override var w: Float
        get() = components[3]
        set(value) {
            if(value isCloseTo components[3]) {
                return
            }

            components[3] = value

            observer?.invoke(this)
        }

    fun set(other: Vector2) = set(other.x, other.y, 0f, 0f)
    fun set(other: Vector3) = set(other.x, other.y, other.z, 0f)
    fun set(other: Vector4) = set(other.components)
    fun set(components: FloatArray) = set(components[0], components[1], components[2], components[3])
    fun set(x: Float = 0f, y: Float = 0f, z: Float = 0f, w: Float = 0f) : MutableVector4 {
        if(equals(x, y, z, w)) {
            return this
        }

        components[0] = x
        components[1] = y
        components[2] = z
        components[3] = w

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

    operator fun plusAssign(other: Vector4) { add(other) }
    operator fun minusAssign(other: Vector4) { subtract(other) }
    operator fun timesAssign(scalar: Float) { scale(scalar) }
    operator fun divAssign(scalar: Float) { scale(1 / scalar) }

    /**
     * Adds this vector to [other].
     *
     * @param[other] The other vector.
     * @return This vector for chaining.
     */
    fun add(other: Vector4) = add(other, this)
    /**
     * Adds this vector to ([x], [y], [z], [w]).
     *
     * @return This vector for chaining.
     */
    fun add(x: Float, y: Float, z: Float, w: Float) = add(x, y, z, w, this)
    /**
     * Subtracts [other] from this vector.
     *
     * @param[other] The other vector.
     * @return This vector for chaining.
     */
    fun subtract(other: Vector4) = subtract(other, this)
    /**
     * Subtracts ([x], [y], [z], [w]) from this vector.
     *
     * @return This vector for chaining.
     */
    fun subtract(x: Float, y: Float, z: Float, w: Float) = subtract(x, y, z, w, this)

    /**
     * Returns the vector composed of the smallest components between this vector and [other].
     *
     * @param[other] The other vector.
     * @return This vector forchaining.
     */
    fun min(other: Vector4) = min(other, this)
    /**
     * Returns the vector composed of the smallest components between this vector and ([x], [y], [z], [w]).
     *
     * @return This vector for chaining.
     */
    fun min(x: Float, y: Float, z: Float, w: Float) = min(x, y, z, w, this)
    /**
     * Returns the vector composed of the largest components between this vector and [other].
     *
     * @param[other] The other vector.
     * @return This vector for chaining.
     */
    fun max(other: Vector4) = max(other, this)
    /**
     * Returns the vector composed of the largest components between this vector and ([x], [y], [z], [w]).
     *
     * @return This vector forchaining.
     */
    fun max(x: Float, y: Float, z: Float, w: Float) = max(x, y, z, w, this)

    /**
     * Projects this vector onto [other].
     *
     * @param[other] The other vector.
     * @return This vector for chaining.
     */
    fun projectOnto(other: Vector4) = projectOnto(other, this)
    /**
     * Projects this vector onto ([x], [y], [z], [w]).
     *
     * @return This vector forchaining.
     */
    fun projectOnto(x: Float, y: Float, z: Float, w: Float) = projectOnto(x, y, z, w, this)

    /**
     * Rejects this vector from [other].
     *
     * @param[other] The other vector.
     * @return This vector for chaining.
     */
    fun rejectFrom(other: Vector4) = rejectFrom(other, this)
    /**
     * Rejects this vector from ([x], [y], [z], [w]).
     *
     * @return This vector for chaining.
     */
    fun rejectFrom(x: Float, y: Float, z: Float, w: Float) = rejectFrom(x, y, z, w, this)

    /**
     * Multiplies [matrix] with this vector.
     *
     * Wrapper to [Matrix4.multiply].
     *
     * @param[matrix] The matrix.
     * @return This vector for chaining.
     */
    fun multiplyLeft(matrix: Matrix4) = multiplyLeft(matrix, this)
}