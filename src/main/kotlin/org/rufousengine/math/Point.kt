package org.rufousengine.math

import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.math.floor
import kotlin.math.ceil
import kotlin.math.abs

/**
 * An immutable point in space.
 *
 * The purpose of the Point classes is to represent positions. To represent directions, use [Vector2], [Vector3] or [Vector4] instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @constructor Creates a point in (x, y, z).
 */
open class Point(x: Float = 0f, y: Float = 0f, z: Float = 0f) {
    companion object {
        /** Point(0, 0, 0) */
        val origin = Point()
    }

    /** The components of this point. Do not change its values directly unless you know what you're doing. */
    val components = floatArrayOf(x, y, z)

    constructor(other: Point) : this(other.components)
    constructor(vector: Vector2) : this(vector.x, vector.y, 0f)
    constructor(vector: Vector3) : this(vector.components)
    constructor(vector: Vector4) : this(vector.x / vector.w, vector.y / vector.w, vector.z / vector.w)
    constructor(components: FloatArray) : this(components[0], components[1], components[2])

    open val x: Float
        get() = components[0]
    open val y: Float
        get() = components[1]
    open val z: Float
        get() = components[2]

    /** Whether this point is the origin point. */
    val isOrigin: Boolean
        get() = equals(origin)

    operator fun get(index: Int) = components[index]

    /**
     * Creates an immutable copy of this point.
     *
     * @return The new point for chaining.
     */
    fun copyImmutable() = Point(this)
    /**
     * Creates a mutable copy of this point.
     *
     * @return The new point for chaining.
     */
    fun copyMutable() = MutablePoint(this)

    /**
     * Rounds each component to the largest Float value that is smaller than the given value and is a mathematical integer.
     *
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun floor(out: MutablePoint) = out.set(floor(x), floor(y), floor(z))
    /**
     * Rounds each component to the smallest Float value that is larger than the given value and is a mathematical integer.
     *
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun ceil(out: MutablePoint) = out.set(ceil(x), ceil(y), ceil(z))
    /**
     * Applies the absolute value to each component.
     *
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun abs(out: MutablePoint) = out.set(abs(x), abs(y), abs(z))
    /**
     * Scales this point (i.e., multiplies each component with [scalar]).
     *
     * @param[scalar] The scalar to multiply the point with.
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun scale(scalar: Float, out: MutablePoint) = out.set(x * scalar, y * scalar, z * scalar)

    /** Returns the distance between this point and [other]. */
    fun distance(other: Point) = distance(other.x, other.y, other.z)
    /** Returns the distance between this point and ([x], [y], [z]). */
    fun distance(x: Float, y: Float, z: Float) = sqrt(distanceSquared(x, y, z))
    /** Returns the squared distance between this point and [other]. */
    fun distanceSquared(other: Point) = distanceSquared(other.x, other.y, other.z)
    /** Returns the squared distance between this point and ([x], [y], [z]). */
    fun distanceSquared(x: Float, y: Float, z: Float) : Float {
        val x = this.x - x
        val y = this.y - y
        val z = this.z - z

        return x * x + y * y + z * z
    }

    /**
     * Adds [other] to this point.
     *
     * @param[other] The other point.
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun add(other: Point, out: MutablePoint) = add(other.x, other.y, other.z, out)
    /**
     * Moves this point towards the direction of [vector].
     *
     * @param[vector] The direction vector.
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun add(vector: Vector3, out: MutablePoint) = add(vector.x, vector.y, vector.z, out)
    /**
     * Adds ([x], [y], [z]) to this point.
     *
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun add(x: Float, y: Float, z: Float, out: MutablePoint) = out.set(this.x + x, this.y + y, this.z + z)

    /**
     * Subtracts [other] from this point.
     *
     * @param[other] The other point.
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun subtract(other: Point, out: MutablePoint) = subtract(other.x, other.y, other.z, out)
    /**
     * Moves this point towards the direction of -[vector].
     *
     * @param[vector] The direction vector.
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun subtract(vector: Vector3, out: MutablePoint) = subtract(vector.x, vector.y, vector.z, out)
    /**
     * Subtracts ([x], [y], [z]) from this point.
     *
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun subtract(x: Float, y: Float, z: Float, out: MutablePoint) = out.set(this.x - x, this.y - y, this.z - z)

    /**
     * Returns the direction vector pointing from [other] towards this point.
     *
     * @param[other] The other point.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun subtract(other: Point, out: MutableVector3) = subtract(other.x, other.y, other.z, out)
    /**
     * Returns the direction vector pointing from ([x], [y], [z]) towards this point.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun subtract(x: Float, y: Float, z: Float, out: MutableVector3) = out.set(this.x - x, this.y - y, this.z - z)

    /**
     * Linearly interpolates between this point to [other] on [progress] position.
     *
     * @param[other] The other point.
     * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and [other].
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun lerp(other: Point, progress: Float, out: MutablePoint) = lerp(other.x, other.y, other.z, progress, out)
    /**
     * Linearly interpolates between this point to ([x], [y], [z]) on [progress] position.
     *
     * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and ([x], [y], [z]).
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun lerp(x: Float, y: Float, z: Float, progress: Float, out: MutablePoint) = out.set(lerp(this.x, x, progress), lerp(this.y, y, progress), lerp(this.z, z, progress))

    /**
     * Returns the point composed of the smallest components between this point and [other].
     *
     * @param[other] The other point.
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun min(other: Point, out: MutablePoint) = min(other.x, other.y, other.z, out)
    /**
     * Returns the point composed of the smallest components between this point and ([x], [y], [z]).
     *
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun min(x: Float, y: Float, z: Float, out: MutablePoint) = out.set(min(this.x, x), min(this.y, y), min(this.z, z))
    /**
     * Returns the point composed of the largest components between this point and [other].
     *
     * @param[other] The other point.
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun max(other: Point, out: MutablePoint) = max(other.x, other.y, other.z, out)
    /**
     * Returns the point composed of the largest components between this point and ([x], [y], [z]).
     *
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun max(x: Float, y: Float, z: Float, out: MutablePoint) = out.set(max(this.x, x), max(this.y, y), max(this.z, z))

    /**
     * Multiplies [matrix] with this point.
     *
     * Wrapper to [Matrix4.multiply].
     *
     * @param[matrix] The matrix.
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun multiplyLeft(matrix: Matrix4, out: MutablePoint) = matrix.multiply(this, out)
    /**
     * Multiplies [projection] with this point.
     *
     * Wrapper to [Projection.multiply].
     *
     * @param[projection] The projection.
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun multiplyLeft(projection: Projection, out: MutablePoint) = projection.multiply(this, out)

    fun equals(other: Point) = equals(other.x, other.y, other.z)
    fun equals(x: Float, y: Float, z: Float) = this.x isCloseTo x && this.y isCloseTo y && this.z isCloseTo z

    override fun toString() = "($x, $y, $z)"

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Point) {
            return false
        }

        return equals(other)
    }

    override fun hashCode() = Arrays.hashCode(components)
}