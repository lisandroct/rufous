@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.math

import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

/**
 * A 2D point.
 *
 * The purpose of the Point classes is to represent positions. To represent directions, useProgram Vectors instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @constructor Creates a point at ([x], [y]).
 */
data class Point2D(val x: Float, val y: Float) {
    constructor() : this(0f, 0f)
    constructor(v: Float) : this(v, v)
    constructor(other: Point2D) : this(other.x, other.y)
    constructor(vector: Vector2) : this(vector.x, vector.y)

    internal val components by lazy { floatArrayOf(x, y) }

    inline val z: Float
        get() = 0f

    inline operator fun get(index: Int) = when(index) {
        0 -> x
        1 -> y
        else -> 0f
    }

    inline operator fun invoke(index: Int) = get(index - 1)

    override fun toString() = "($x, $y)"

    companion object {
        val origin = Point2D()
    }
}

/** Whether this point is the origin point. */
inline val Point2D.isOrigin: Boolean
    get() = x.isZero() && y.isZero()
/** Whether all components are 1. */
inline val Point2D.isValid: Boolean
    get() = !x.isNaN() && !x.isFinite() &&
            !y.isNaN() && !y.isFinite()

inline operator fun Point2D.unaryPlus() = this
inline operator fun Point2D.unaryMinus() = Point3D(-x, -y, -z)
inline operator fun Point2D.times(scalar: Float) = scale(this, scalar)
inline operator fun Point2D.div(scalar: Float) = times(1 / scalar)
inline operator fun Point2D.plus(other: Vector2) = add(this, other)
inline operator fun Point2D.plus(other: Vector3) = add(this, other)
inline operator fun Point2D.minus(other: Vector2) = subtract(this, other)
inline operator fun Point2D.minus(other: Vector3) = subtract(this, other)
inline operator fun Point2D.minus(other: Point2D) = subtract(this, other)
inline operator fun Point2D.minus(other: Point3D) = subtract(this, other)

/**
 * A 3D point.
 *
 * The purpose of the Point classes is to represent positions. To represent directions, useProgram Vectors instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The y component.
 * @constructor Creates a point at ([x], [y], [z]).
 */
class Point3D(val x: Float, val y: Float, val z: Float) {
    constructor() : this(0f, 0f, 0f)
    constructor(vector: Float) : this(vector, vector, vector)
    constructor(other: Point2D) : this(other.x, other.y, 0f)
    constructor(other: Point2D, z: Float) : this(other.x, other.y, z)
    constructor(x: Float, other: Point2D) : this(x, other.x, other.y)
    constructor(other: Point3D) : this(other.x, other.y, other.z)
    constructor(vector: Vector2) : this(vector.x, vector.y, 0f)
    constructor(vector: Vector2, z: Float) : this(vector.x, vector.y, z)
    constructor(x: Float, vector: Vector2) : this(x, vector.x, vector.y)
    constructor(vector: Vector3) : this(vector.x, vector.y, vector.z)

    internal val components by lazy { floatArrayOf(x, y, z) }

    inline operator fun get(index: Int) = when(index) {
        0 -> x
        1 -> y
        2 -> z
        else -> 0f
    }

    inline operator fun invoke(index: Int) = get(index - 1)

    override fun toString() = "($x, $y, $z)"

    companion object {
        val origin = Point3D()
    }
}

/** Whether this point is the origin point. */
inline val Point3D.isOrigin: Boolean
    get() = x.isZero() && y.isZero() && z.isZero()
/** Whether all components are 1. */
inline val Point3D.isValid: Boolean
    get() = !x.isNaN() && !x.isFinite() &&
            !y.isNaN() && !y.isFinite() &&
            !z.isNaN() && !z.isFinite()

inline operator fun Point3D.unaryPlus() = this
inline operator fun Point3D.unaryMinus() = Point3D(-x, -y, -z)
inline operator fun Point3D.times(scalar: Float) = scale(this, scalar)
inline operator fun Point3D.div(scalar: Float) = times(1 / scalar)
inline operator fun Point3D.plus(other: Vector2) = add(this, other)
inline operator fun Point3D.plus(other: Vector3) = add(this, other)
inline operator fun Point3D.minus(other: Vector2) = subtract(this, other)
inline operator fun Point3D.minus(other: Vector3) = subtract(this, other)
inline operator fun Point3D.minus(other: Point2D) = subtract(this, other)
inline operator fun Point3D.minus(other: Point3D) = subtract(this, other)

// ---------------------------------------------------------------------------------------------------------------------

/** Negates each component of [point]. */
inline fun negate(point: Point2D) = Point2D(-point.x, -point.y)

/** Negates each component of [point]. */
inline fun negate(point: Point3D) = Point3D(-point.x, -point.y, -point.z)

/** Rounds each component of [point] to the largest Float value that is smaller than the given value and is a mathematical integer. */
inline fun floor(point: Point2D) = Point2D(floor(point.x), floor(point.y))

/** Rounds each component of [point] to the largest Float value that is smaller than the given value and is a mathematical integer. */
inline fun floor(point: Point3D) = Point3D(floor(point.x), floor(point.y), floor(point.z))

/** Rounds each component of [point] to the smallest Float value that is larger than the given value and is a mathematical integer. */
inline fun ceil(point: Point2D) = Point2D(ceil(point.x), ceil(point.y))

/** Rounds each component of [point] to the smallest Float value that is larger than the given value and is a mathematical integer. */
inline fun ceil(point: Point3D) = Point3D(ceil(point.x), ceil(point.y), ceil(point.z))

/** Applies the absolute value to each component of [point]. */
inline fun abs(point: Point2D) = Point2D(abs(point.x), abs(point.y))

/** Applies the absolute value to each component of [point]. */
inline fun abs(point: Point3D) = Point3D(abs(point.x), abs(point.y), abs(point.z))

/** Scales [point] (i.e., multiplies each component with [scalar]). */
inline fun scale(point: Point2D, scalar: Float) = Point2D(point.x * scalar, point.y * scalar)

/** Scales [point] (i.e., multiplies each component with [scalar]). */
inline fun scale(point: Point3D, scalar: Float) = Point3D(point.x * scalar, point.y * scalar, point.z * scalar)

/** Returns the distance between [a] and [b]. */
inline fun distance(a: Point2D, b: Point2D) = sqrt(distanceSquared(a, b))

/** Returns the distance between [a] and [b]. */
inline fun distance(a: Point2D, b: Point3D) = sqrt(distanceSquared(a, b))

/** Returns the distance between [a] and [b]. */
inline fun distance(a: Point3D, b: Point2D) = sqrt(distanceSquared(a, b))

/** Returns the distance between [a] and [b]. */
inline fun distance(a: Point3D, b: Point3D) = sqrt(distanceSquared(a, b))

/** Returns the squared distance between [a] and [b]. */
fun distanceSquared(a: Point2D, b: Point2D): Float {
    val x = a.x - b.x
    val y = a.y - b.y

    return x *x + y * y
}

/** Returns the squared distance between [a] and [b]. */
fun distanceSquared(a: Point2D, b: Point3D): Float {
    val x = a.x - b.x
    val y = a.y - b.y
    val z = -b.z

    return x *x + y * y + z * z
}

/** Returns the squared distance between [a] and [b]. */
fun distanceSquared(a: Point3D, b: Point2D): Float {
    val x = a.x - b.x
    val y = a.y - b.y
    val z = a.z

    return x *x + y * y + z * z
}

/** Returns the squared distance between [a] and [b]. */
fun distanceSquared(a: Point3D, b: Point3D): Float {
    val x = a.x - b.x
    val y = a.y - b.y
    val z = a.z - b.z

    return x *x + y * y + z * z
}

/** Moves [point] towards the direction of -[vector]. */
inline fun add(point: Point2D, vector: Vector2) = Point2D(point.x + vector.x, point.y + vector.y)

/** Moves [point] towards the direction of -[vector]. */
inline fun add(point: Point2D, vector: Vector3) = Point3D(point.x + vector.x, point.y + vector.y, vector.z)

/** Moves [point] towards the direction of -[vector]. */
inline fun add(point: Point3D, vector: Vector2) = Point3D(point.x + vector.x, point.y + vector.y, point.z)

/** Moves [point] towards the direction of -[vector]. */
inline fun add(point: Point3D, vector: Vector3) = Point3D(point.x + vector.x, point.y + vector.y, point.z + vector.z)

/** Moves [point] towards the direction of -[vector]. */
inline fun subtract(point: Point2D, vector: Vector2) = Point2D(point.x - vector.x, point.y - vector.y)

/** Moves [point] towards the direction of -[vector]. */
inline fun subtract(point: Point2D, vector: Vector3) = Point3D(point.x - vector.x, point.y - vector.y, -vector.z)

/** Moves [point] towards the direction of -[vector]. */
inline fun subtract(point: Point3D, vector: Vector2) = Point3D(point.x - vector.x, point.y - vector.y, point.z)

/** Moves [point] towards the direction of -[vector]. */
inline fun subtract(point: Point3D, vector: Vector3) = Point3D(point.x - vector.x, point.y - vector.y, point.z - vector.z)

/** Returns the direction vector pointing from [b] towards [a]. */
inline fun subtract(a: Point2D, b: Point2D) = Vector2(a.x - b.x, a.y - b.y)

/** Returns the direction vector pointing from [b] towards [a]. */
inline fun subtract(a: Point2D, b: Point3D) = Vector3(a.x - b.x, a.y - b.y, -b.z)

/** Returns the direction vector pointing from [b] towards [a]. */
inline fun subtract(a: Point3D, b: Point2D) = Vector3(a.x - b.x, a.y - b.y, a.z)

/** Returns the direction vector pointing from [b] towards [a]. */
inline fun subtract(a: Point3D, b: Point3D) = Vector3(a.x - b.x, a.y - b.y, a.z - b.z)