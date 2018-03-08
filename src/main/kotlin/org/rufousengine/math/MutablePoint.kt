package org.rufousengine.math

/**
 * An mutable point in space.
 *
 * The purpose of the Point classes is to represent positions. To represent directions, use [Vector2], [Vector3] or [Vector4] instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @property[observer] An observer that gets notified every time this point changes. Can be null.
 * @constructor Creates a point in (x, y, z).
 */
class MutablePoint(x: Float = 0f, y: Float = 0f, z: Float = 0f, observer: ((Point) -> Unit)? = null) : Point(x, y, z) {
    val observer = observer

    constructor(other: Point, observer: ((Point) -> Unit)? = null) : this(other.components, observer)
    constructor(vector: Vector2, observer: ((Point) -> Unit)? = null) : this(vector.x, vector.y, 0f, observer)
    constructor(vector: Vector3, observer: ((Point) -> Unit)? = null) : this(vector.components, observer)
    constructor(vector: Vector4, observer: ((Point) -> Unit)? = null) : this(vector.components, observer)
    constructor(components: FloatArray, observer: ((Point) -> Unit)? = null) : this(components[0], components[1], components[2], observer)

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

    open fun set(other: Point) = set(other.components)
    open fun set(vector: Vector2) = set(vector.x, vector.y, 0f)
    open fun set(vector: Vector3) = set(vector.components)
    open fun set(vector: Vector4) = set(vector.components)
    open fun set(components: FloatArray) = set(components[0], components[1], components[2])
    open fun set(x: Float = 0f, y: Float = 0f, z: Float = 0f) : MutablePoint {
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

    operator fun plusAssign(other: Point) { add(other) }
    operator fun plusAssign(vector: Vector3) { add(vector) }
    operator fun minusAssign(other: Point) { subtract(other) }
    operator fun minusAssign(vector: Vector3) { subtract(vector) }
    operator fun timesAssign(scalar: Float) { scale(scalar) }
    operator fun divAssign(scalar: Float) { scale(1 / scalar) }

    /**
     * Rounds each component to the largest Float value that is smaller than the given value and is a mathematical integer.
     *
     * @return This point for chaining.
     */
    fun floor() = floor(this)
    /**
     * Rounds each component to the smallest Float value that is larger than the given value and is a mathematical integer.
     *
     * @return This point for chaining.
     */
    fun ceil() = ceil(this)
    /**
     * Applies the absolute value to each component.
     *
     * @return This point for chaining.
     */
    fun abs() = abs(this)
    /**
     * Scales this point (i.e., multiplies each component with [scalar]).
     *
     * @param[scalar] The scalar to multiply the point with.
     * @return This point for chaining.
     */
    fun scale(scalar: Float) = scale(scalar, this)

    /**
     * Adds [other] to this point.
     *
     * @param[other] The other point.
     * @return This point for chaining.
     */
    fun add(other: Point) = add(other, this)
    /**
     * Moves this point towards the direction of [vector].
     *
     * @param[vector] The direction vector.
     * @return This point for chaining.
     */
    fun add(vector: Vector3) = add(vector, this)
    /**
     * Adds ([x], [y], [z]) to this point.
     *
     * @return This point for chaining.
     */
    fun add(x: Float, y: Float, z: Float) = add(x, y, z, this)

    /**
     * Subtracts [other] from this point.
     *
     * @param[other] The other point.
     * @return This point for chaining.
     */
    fun subtract(other: Point) = subtract(other, this)
    /**
     * Moves this point towards the direction of -[vector].
     *
     * @param[vector] The direction vector.
     * @return This point for chaining.
     */
    fun subtract(vector: Vector3) = subtract(vector, this)
    /**
     * Subtracts ([x], [y], [z]) from this point.
     *
     * @return This point for chaining.
     */
    fun subtract(x: Float, y: Float, z: Float) = subtract(x, y, z, this)

    /**
     * Linearly interpolates between this point to [other] on [progress] position.
     *
     * @param[other] The other point.
     * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and [other].
     * @return This point for chaining.
     */
    fun lerp(other: Point, progress: Float) = lerp(other, progress, this)
    /**
     * Linearly interpolates between this point to ([x], [y], [z]) on [progress] position.
     *
     * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and ([x], [y], [z]).
     * @return This point for chaining.
     */
    fun lerp(x: Float, y: Float, z: Float, progress: Float) = lerp(x, y, z, progress, this)

    /**
     * Returns the point composed of the smallest components between this point and [other].
     *
     * @param[other] The other point.
     * @return This point for chaining.
     */
    fun min(other: Point) = min(other, this)
    /**
     * Returns the point composed of the smallest components between this point and ([x], [y], [z]).
     *
     * @return This point for chaining.
     */
    fun min(x: Float, y: Float, z: Float) = min(x, y, z, this)
    /**
     * Returns the point composed of the largest components between this point and [other].
     *
     * @param[other] The other point.
     * @return This point for chaining.
     */
    fun max(other: Point) = max(other, this)
    /**
     * Returns the point composed of the largest components between this point and ([x], [y], [z]).
     *
     * @return This point for chaining.
     */
    fun max(x: Float, y: Float, z: Float) = max(x, y, z, this)
}