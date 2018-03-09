package org.rufousengine.math

/**
 * A mutable three-dimensional vector.
 *
 * The purpose of the Vector classes is to represent directions. To represent positions, use [Point] instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @property[observer] An observer that gets notified every time this vector changes. Can be null.
 * @constructor Creates a three-dimensional vector pointing towards (x, y, z).
 */
class MutableVector3(x: Float = 0f, y: Float = 0f, z: Float = 0f, observer: ((Vector3) -> Unit)? = null) : Vector3(x, y, z) {
    val observer = observer

    constructor(other: Vector2, observer: ((Vector3) -> Unit)? = null) : this(other.x, other.y, 0f, observer)
    constructor(other: Vector3, observer: ((Vector3) -> Unit)? = null) : this(other.components, observer)
    constructor(other: Vector4, observer: ((Vector3) -> Unit)? = null) : this(other.components, observer)
    constructor(components: FloatArray, observer: ((Vector3) -> Unit)? = null) : this(components[0], components[1], components[2], observer)

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

    fun set(other: Vector2) = set(other.x, other.y, 0f)
    fun set(other: Vector3) = set(other.components)
    fun set(other: Vector4) = set(other.components)
    fun set(components: FloatArray) = set(components[0], components[1], components[2])
    fun set(x: Float = 0f, y: Float = 0f, z: Float = 0f) : MutableVector3 {
        if(equals(x, y, z)) {
            return this
        }

        components[0] = x
        components[1] = y
        components[2] = z

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

    operator fun plusAssign(other: Vector3) { add(other) }
    operator fun minusAssign(other: Vector3) { subtract(other) }
    operator fun timesAssign(scalar: Float) { scale(scalar) }
    operator fun divAssign(scalar: Float) { scale(1 / scalar) }

    /**
     * Adds [other] to this vector.
     *
     * @param[other] The other vector.
     * @return This vector for chaining.
     */
    fun add(other: Vector3) = add(other, this)
    /**
     * Adds ([x], [y], [z]) to this vector.
     *
     * @return This vector for chaining.
     */
    fun add(x: Float, y: Float, z: Float) = add(x, y, z, this)
    /**
     * Subtracts [other] from this vector.
     *
     * @param[other] The other vector.
     * @return This vector for chaining.
     */
    fun subtract(other: Vector3) = subtract(other, this)
    /**
     * Subtracts this vector and ([x], [y], [z]).
     *
     * @return This vector for chaining.
     */
    fun subtract(x: Float, y: Float, z: Float) = subtract(x, y, z, this)

    /**
     * Returns the vector composed of the smallest components between this vector and [other].
     *
     * @param[other] The other vector.
     * @return This vector for chaining.
     */
    fun min(other: Vector3) = min(other, this)
    /**
     * Returns the vector composed of the smallest components between this vector and ([x], [y], [z]).
     *
     * @return This vector for chaining.
     */
    fun min(x: Float, y: Float, z: Float) = min(x, y, z, this)
    /**
     * Returns the vector composed of the largest components between this vector and [other].
     *
     * @param[other] The other vector.
     * @return This vector for chaining.
     */
    fun max(other: Vector3) = max(other, this)
    /**
     * Returns the vector composed of the largest components between this vector and ([x], [y], [z]).
     *
     * @return This vector for chaining.
     */
    fun max(x: Float, y: Float, z: Float) = max(x, y, z, this)

    /**
     * Projects this vector onto [other].
     *
     * @param[other] The other vector.
     * @return This vector for chaining.
     */
    fun projectOnto(other: Vector3) = projectOnto(other, this)
    /**
     * Projects this vector onto ([x], [y], [z]).
     *
     * @return This vector for chaining.
     */
    fun projectOnto(x: Float, y: Float, z: Float) = projectOnto(x, y, z, this)

    /**
     * Rejects this vector from [other].
     *
     * @param[other] The other vector.
     * @return This vector for chaining.
     */
    fun rejectFrom(other: Vector3) = rejectFrom(other, this)
    /**
     * Rejects this vector from ([x], [y], [z]).
     *
     * @return This vector for chaining.
     */
    fun rejectFrom(x: Float, y: Float, z: Float) = rejectFrom(x, y, z, this)

    /**
     * Cross product between this vector and [other].
     *
     * @param[other] The other vector.
     * @return This vector for chaining.
     */
    fun cross(other: Vector3) = cross(other, this)
    /**
     * Cross product between this vector and ([x], [y], [z]).
     *
     * @return This vector for chaining.
     */
    fun cross(x: Float, y: Float, z: Float) = cross(x, y, z, this)

    /**
     * Multiplies [matrix] with this vector.
     *
     * @param[matrix] The matrix.
     * @return This vector for chaining.
     */
    fun multiplyLeft(matrix: Matrix3) = multiplyLeft(matrix, this)
    /**
     * Multiplies [matrix] with this vector.
     *
     * @param[matrix] The matrix.
     * @return This vector for chaining.
     */
    fun multiplyLeft(matrix: Matrix4) = multiplyLeft(matrix, this)

    /**
     * Rotates this vector with [quaternion].
     *
     * If the quaternion is known to be a unit quaternion, [transform] is a cheaper alternative.
     *
     * @param[quaternion] The rotation quaternion.
     * @return This vector for chaining.
     */
    fun transformSafe(quaternion: Quaternion) = transformSafe(quaternion, this)
    /**
     * Rotates this vector with [quaternion].
     *
     * @param[quaternion] The rotation quaternion. Must be unit.
     * @return This vector for chaining.
     */
    fun transform(quaternion: Quaternion) = transform(quaternion, this)
}