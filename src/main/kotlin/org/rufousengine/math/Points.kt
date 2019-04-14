@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.math

import kotlin.math.*

/** Whether this point is the origin point. */
inline val Point.isOrigin: Boolean
    get() = components.all { it.isZero() }

// ---

/** Returns the distance between [a] and [b]. */
inline fun distance(a: Point, b: Point) = sqrt(distanceSquared(a, b))
/** Returns the squared distance between [a] and [b]. */
fun distanceSquared(a: Point, b: Point): Float {
    val dimensions = min(a.dimensions, b.dimensions)

    var distance = 0f
    for(i in 0 until dimensions) {
        val component = a[i] - b[i]
        distance += component * component
    }

    return distance
}

/** Returns the distance between this point and [other]. */
inline fun Point.distanceTo(other: Point) = distance(this, other)
/** Returns the distance between this point and ([x], [y]). */
inline fun Point.distanceTo(x: Float, y: Float) = distanceTo(cPoint(x, y))
/** Returns the distance between this point and ([x], [y], [z]). */
inline fun Point.distanceTo(x: Float, y: Float, z: Float) = distanceTo(cPoint(x, y, z))
/** Returns the squared distance between this point and [other]. */
inline fun Point.distanceSquaredTo(other: Point) = distanceSquared(this, other)
/** Returns the squared distance between this point and ([x], [y]). */
inline fun Point.distanceSquaredTo(x: Float, y: Float) = distanceSquaredTo(cPoint(x, y))
/** Returns the squared distance between this point and ([x], [y], [z]). */
inline fun Point.distanceSquaredTo(x: Float, y: Float, z: Float) = distanceSquaredTo(cPoint(x, y, z))

// ---

/**
 * Negates each component of [point] and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun negate(point: Point2D, out: Point2D = Point2D()) = out.set(-point.x, -point.y)
/**
 * Negates each component of [point] and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun negate(point: Point3D, out: Point3D = Point3D()) = out.set(-point.x, -point.y, -point.z)

/**
 * Rounds each component of [point] to the largest Float value that is smaller than the given value and is a mathematical integer.
 * Stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun floor(point: Point2D, out: Point2D = Point2D()) = out.set(floor(point.x), floor(point.y))
/**
 * Rounds each component of [point] to the largest Float value that is smaller than the given value and is a mathematical integer.
 * Stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun floor(point: Point3D, out: Point3D = Point3D()) = out.set(floor(point.x), floor(point.y), floor(point.z))

/**
 * Rounds each component of [point] to the smallest Float value that is larger than the given value and is a mathematical integer.
 * Stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun ceil(point: Point2D, out: Point2D = Point2D()) = out.set(ceil(point.x), ceil(point.y))
/**
 * Rounds each component of [point] to the smallest Float value that is larger than the given value and is a mathematical integer.
 * Stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun ceil(point: Point3D, out: Point3D = Point3D()) = out.set(ceil(point.x), ceil(point.y), ceil(point.z))

/**
 * Applies the absolute value to each component of [point] and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun abs(point: Point2D, out: Point2D = Point2D()) = out.set(abs(point.x), abs(point.y))
/**
 * Applies the absolute value to each component of [point] and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun abs(point: Point3D, out: Point3D = Point3D()) = out.set(abs(point.x), abs(point.y), abs(point.z))

/**
 * Scales [point] (i.e., multiplies each component with [scalar]) and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun scale(point: Point2D, scalar: Float, out: Point2D = Point2D()) = out.set(point.x * scalar, point.y * scalar)
/**
 * Scales [point] (i.e., multiplies each component with [scalar]) and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun scale(point: Point3D, scalar: Float, out: Point3D = Point3D()) = out.set(point.x * scalar, point.y * scalar, point.z * scalar)

/**
 * Moves [point] towards the direction of [vector] and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun add(point: Point2D, vector: Vector2, out: Point2D = Point2D()) = out.set(point.x + vector.x, point.y + vector.y)
/**
 * Moves [point] towards the direction of [vector] and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun add(point: Point2D, vector: Vector3, out: Point3D = Point3D()) = out.set(point.x + vector.x, point.y + vector.y, vector.z)
/**
 * Moves [point] towards the direction of [vector] and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun add(point: Point3D, vector: Vector2, out: Point3D = Point3D()) = out.set(point.x + vector.x, point.y + vector.y, point.z)
/**
 * Moves [point] towards the direction of [vector] and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun add(point: Point3D, vector: Vector3, out: Point3D = Point3D()) = out.set(point.x + vector.x, point.y + vector.y, point.z + vector.z)

/**
 * Moves [point] towards the direction of -[vector] and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun subtract(point: Point2D, vector: Vector2, out: Point2D = Point2D()) = out.set(point.x - vector.x, point.y - vector.y)
/**
 * Moves [point] towards the direction of -[vector] and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun subtract(point: Point2D, vector: Vector3, out: Point3D = Point3D()) = out.set(point.x - vector.x, point.y - vector.y, -vector.z)
/**
 * Moves [point] towards the direction of -[vector] and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun subtract(point: Point3D, vector: Vector2, out: Point3D = Point3D()) = out.set(point.x - vector.x, point.y - vector.y, point.z)
/**
 * Moves [point] towards the direction of -[vector] and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun subtract(point: Point3D, vector: Vector3, out: Point3D = Point3D()) = out.set(point.x - vector.x, point.y - vector.y, point.z - vector.z)

/**
 * Stores in [out] the direction vector pointing from [b] towards [a].
 *
 * @return The [out] point for chaining.
 */
inline fun subtract(a: Point2D, b: Point2D, out: Vector2 = Vector2()) = out.set(a.x - b.x, a.y - b.y)
/**
 * Stores in [out] the direction vector pointing from [b] towards [a].
 *
 * @return The [out] point for chaining.
 */
inline fun subtract(a: Point2D, b: Point3D, out: Vector3 = Vector3()) = out.set(a.x - b.x, a.y - b.y, -b.z)
/**
 * Stores in [out] the direction vector pointing from [b] towards [a].
 *
 * @return The [out] point for chaining.
 */
inline fun subtract(a: Point3D, b: Point2D, out: Vector3 = Vector3()) = out.set(a.x - b.x, a.y - b.y, a.z)
/**
 * Stores in [out] the direction vector pointing from [b] towards [a].
 *
 * @return The [out] point for chaining.
 */
inline fun subtract(a: Point3D, b: Point3D, out: Vector3 = Vector3()) = out.set(a.x - b.x, a.y - b.y, a.z - b.z)

/**
 * Linearly interpolates between [a] to [b] on [progress] position and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun lerp(a: Point2D, b: Point2D, progress: Float, out: Point2D = Point2D()) = out.set(org.rufousengine.math.lerp(a.x, b.x, progress), org.rufousengine.math.lerp(a.y, b.y, progress))
/**
 * Linearly interpolates between [a] to [b] on [progress] position and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun lerp(a: Point2D, b: Point3D, progress: Float, out: Point3D = Point3D()) = out.set(org.rufousengine.math.lerp(a.x, b.x, progress), org.rufousengine.math.lerp(a.y, b.y, progress), b.z * progress)
/**
 * Linearly interpolates between [a] to [b] on [progress] position and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun lerp(a: Point3D, b: Point2D, progress: Float, out: Point3D = Point3D()) = out.set(org.rufousengine.math.lerp(a.x, b.x, progress), org.rufousengine.math.lerp(a.y, b.y, progress), a.z * (1f - progress))
/**
 * Linearly interpolates between [a] to [b] on [progress] position and stores the result in [out].
 *
 * @return The [out] point for chaining.
 */
inline fun lerp(a: Point3D, b: Point3D, progress: Float, out: Point3D = Point3D()) = out.set(org.rufousengine.math.lerp(a.x, b.x, progress), org.rufousengine.math.lerp(a.y, b.y, progress), org.rufousengine.math.lerp(a.z, b.z, progress))

/**
 * Stores in [out] the point composed of the smallest components between [a] and [b].
 *
 * @return The [out] point for chaining.
 */
inline fun min(a: Point2D, b: Point2D, out: Point2D = Point2D()) = out.set(kotlin.math.min(a.x, b.x), kotlin.math.min(a.y, b.y))
/**
 * Stores in [out] the point composed of the smallest components between [a] and [b].
 *
 * @return The [out] point for chaining.
 */
inline fun min(a: Point2D, b: Point3D, out: Point3D = Point3D()) = out.set(kotlin.math.min(a.x, b.x), kotlin.math.min(a.y, b.y), kotlin.math.min(a.z, b.z))
/**
 * Stores in [out] the point composed of the smallest components between [a] and [b].
 *
 * @return The [out] point for chaining.
 */
inline fun min(a: Point3D, b: Point2D, out: Point3D = Point3D()) = out.set(kotlin.math.min(a.x, b.x), kotlin.math.min(a.y, b.y), kotlin.math.min(a.z, b.z))
/**
 * Stores in [out] the point composed of the smallest components between [a] and [b].
 *
 * @return The [out] point for chaining.
 */
inline fun min(a: Point3D, b: Point3D, out: Point3D = Point3D()) = out.set(kotlin.math.min(a.x, b.x), kotlin.math.min(a.y, b.y), kotlin.math.min(a.z, b.z))

/**
 * Stores in [out] the point composed of the largest components between [a] and [b].
 *
 * @return The [out] point for chaining.
 */
inline fun max(a: Point2D, b: Point2D, out: Point2D = Point2D()) = out.set(kotlin.math.max(a.x, b.x), kotlin.math.max(a.y, b.y))
/**
 * Stores in [out] the point composed of the largest components between [a] and [b].
 *
 * @return The [out] point for chaining.
 */
inline fun max(a: Point2D, b: Point3D, out: Point3D = Point3D()) = out.set(kotlin.math.max(a.x, b.x), kotlin.math.max(a.y, b.y), kotlin.math.max(a.z, b.z))
/**
 * Stores in [out] the point composed of the largest components between [a] and [b].
 *
 * @return The [out] point for chaining.
 */
inline fun max(a: Point3D, b: Point2D, out: Point3D = Point3D()) = out.set(kotlin.math.max(a.x, b.x), kotlin.math.max(a.y, b.y), kotlin.math.max(a.z, b.z))
/**
 * Stores in [out] the point composed of the largest components between [a] and [b].
 *
 * @return The [out] point for chaining.
 */
inline fun max(a: Point3D, b: Point3D, out: Point3D = Point3D()) = out.set(kotlin.math.max(a.x, b.x), kotlin.math.max(a.y, b.y), kotlin.math.max(a.z, b.z))

// ---

inline operator fun Point2D.unaryPlus() = this.copy()
inline operator fun Point2D.unaryMinus() = negate(this, Point2D())

inline operator fun Point2D.plus(vector: Vector2) = add(this, vector, Point2D())
inline operator fun Point2D.plus(vector: Vector3) = add(this, vector, Point3D())
inline operator fun Point2D.minus(vector: Vector2) = subtract(this, vector, Point2D())
inline operator fun Point2D.minus(vector: Vector3) = subtract(this, vector, Point3D())
inline operator fun Point2D.minus(other: Point2D) = subtract(this, other, Vector2())
inline operator fun Point2D.minus(other: Point3D) = subtract(this, other, Vector3())
inline operator fun Point2D.times(scalar: Float) = scale(this, scalar, Point2D())
inline operator fun Point2D.div(scalar: Float) = times(1 / scalar)

inline operator fun Point2D.plusAssign(vector: Vector2) { add(vector) }
inline operator fun Point2D.minusAssign(vector: Vector2) { subtract(vector) }
inline operator fun Point2D.timesAssign(scalar: Float) { scale(scalar) }
inline operator fun Point2D.divAssign(scalar: Float) = timesAssign(1 / scalar)

/**
 * Negates each component.
 *
 * @return This point for chaining.
 */
inline fun Point2D.negate() = negate(this, this)
/**
 * Rounds each component to the largest Float value that is smaller than the given value and is a mathematical integer.
 *
 * @return This point for chaining.
 */
inline fun Point2D.floor() = floor(this, this)
/**
 * Rounds each component to the smallest Float value that is larger than the given value and is a mathematical integer.
 *
 * @return This point for chaining.
 */
inline fun Point2D.ceil() = ceil(this, this)
/**
 * Applies the absolute value to each component.
 *
 * @return This point for chaining.
 */
inline fun Point2D.abs() = abs(this, this)
/**
 * Scales this point (i.e., multiplies each component with [scalar]).
 *
 * @return This point for chaining.
 */
inline fun Point2D.scale(scalar: Float) = scale(this, scalar, this)

/**
 * Moves this point towards the direction of [vector].
 *
 * @return This point for chaining.
 */
inline fun Point2D.add(vector: Vector2) = add(this, vector, this)
/**
 * Moves this point towards the direction of ([x], [y]).
 *
 * @return This point for chaining.
 */
inline fun Point2D.add(x: Float, y: Float) = add(cVector(x, y))

/**
 * Moves this point towards the direction of -[vector].
 *
 * @return This point for chaining.
 */
inline fun Point2D.subtract(vector: Vector2) = subtract(this, vector, this)
/**
 * Moves this point towards the direction of -([x], [y]).
 *
 * @return This point for chaining.
 */
inline fun Point2D.subtract(x: Float, y: Float) = subtract(cVector(x, y))

/**
 * Linearly interpolates between this point to [other] on [progress] position.
 *
 * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and [other].
 * @return This point for chaining.
 */
inline fun Point2D.lerp(other: Point2D, progress: Float) = lerp(this, other, progress, this)
/**
 * Linearly interpolates between this point to ([x], [y]) on [progress] position.
 *
 * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and ([x], [y]).
 * @return This point for chaining.
 */
inline fun Point2D.lerp(x: Float, y: Float, progress: Float) = lerp(cPoint(x, y), progress)

/**
 * Composes this point with the smallest components between this point and [other].
 *
 * @return This point for chaining.
 */
inline fun Point2D.min(other: Point2D) = min(this, other, this)
/**
 * Composes this point with the smallest components between this point and ([x], [y]).
 *
 * @return This point for chaining.
 */
inline fun Point2D.min(x: Float, y: Float) = min(cPoint(x, y))

/**
 * Composes this point with the largest components between this point and [other].
 *
 * @return This point for chaining.
 */
inline fun Point2D.max(other: Point2D) = max(this, other, this)
/**
 * Composes this point with the largest components between this point and ([x], [y]).
 *
 * @return This point for chaining.
 */
inline fun Point2D.max(x: Float, y: Float) = max(cPoint(x, y))

/**
 * Multiplies [matrix] with this point. Use this method only if [matrix] is known to be a 2D transformation.
 *
 * @return This vector for chaining.
 */
inline fun Point2D.multiplyLeft(matrix: Matrix4) = multiply(matrix, this, this)

// ---

inline operator fun Point3D.unaryPlus() = this.copy()
inline operator fun Point3D.unaryMinus() = negate(this, Point3D())

inline operator fun Point3D.plus(vector: Vector2) = add(this, vector, Point3D())
inline operator fun Point3D.plus(vector: Vector3) = add(this, vector, Point3D())
inline operator fun Point3D.minus(vector: Vector2) = subtract(this, vector, Point3D())
inline operator fun Point3D.minus(vector: Vector3) = subtract(this, vector, Point3D())
inline operator fun Point3D.minus(other: Point2D) = subtract(this, other, Vector3())
inline operator fun Point3D.minus(other: Point3D) = subtract(this, other, Vector3())
inline operator fun Point3D.times(scalar: Float) = scale(this, scalar, Point3D())
inline operator fun Point3D.div(scalar: Float) = times(1 / scalar)

inline operator fun Point3D.plusAssign(vector: Vector2) { add(vector) }
inline operator fun Point3D.plusAssign(vector: Vector3) { add(vector) }
inline operator fun Point3D.minusAssign(vector: Vector2) { subtract(vector) }
inline operator fun Point3D.minusAssign(vector: Vector3) { subtract(vector) }
inline operator fun Point3D.timesAssign(scalar: Float) { scale(scalar) }
inline operator fun Point3D.divAssign(scalar: Float) = timesAssign(1 / scalar)

/**
 * Negates each component.
 *
 * @return This point for chaining.
 */
inline fun Point3D.negate() = negate(this, this)
/**
 * Rounds each component to the largest Float value that is smaller than the given value and is a mathematical integer.
 *
 * @return This point for chaining.
 */
inline fun Point3D.floor() = floor(this, this)
/**
 * Rounds each component to the smallest Float value that is larger than the given value and is a mathematical integer.
 *
 * @return This point for chaining.
 */
inline fun Point3D.ceil() = ceil(this, this)
/**
 * Applies the absolute value to each component.
 *
 * @return This point for chaining.
 */
inline fun Point3D.abs() = abs(this, this)
/**
 * Scales this point (i.e., multiplies each component with [scalar]).
 *
 * @return This point for chaining.
 */
inline fun Point3D.scale(scalar: Float) = scale(this, scalar, this)

/**
 * Moves this point towards the direction of [vector].
 *
 * @return This point for chaining.
 */
inline fun Point3D.add(vector: Vector2) = add(this, vector, this)
/**
 * Moves this point towards the direction of [vector].
 *
 * @return This point for chaining.
 */
inline fun Point3D.add(vector: Vector3) = add(this, vector, this)
/**
 * Moves this point towards the direction of ([x], [y]).
 *
 * @return This point for chaining.
 */
inline fun Point3D.add(x: Float, y: Float) = add(cVector(x, y))
/**
 * Moves this point towards the direction of ([x], [y], [z]).
 *
 * @return This point for chaining.
 */
inline fun Point3D.add(x: Float, y: Float, z: Float) = add(cVector(x, y, z))

/**
 * Moves this point towards the direction of -[vector].
 *
 * @return This point for chaining.
 */
inline fun Point3D.subtract(vector: Vector2) = subtract(this, vector, this)
/**
 * Moves this point towards the direction of -[vector].
 *
 * @return This point for chaining.
 */
inline fun Point3D.subtract(vector: Vector3) = subtract(this, vector, this)
/**
 * Moves this point towards the direction of -([x], [y]).
 *
 * @return This point for chaining.
 */
inline fun Point3D.subtract(x: Float, y: Float) = subtract(cVector(x, y))
/**
 * Moves this point towards the direction of -([x], [y], [z]).
 *
 * @return This point for chaining.
 */
inline fun Point3D.subtract(x: Float, y: Float, z: Float) = subtract(cVector(x, y, z))

/**
 * Linearly interpolates between this point to [other] on [progress] position.
 *
 * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and [other].
 * @return This point for chaining.
 */
inline fun Point3D.lerp(other: Point2D, progress: Float) = lerp(this, other, progress, this)
/**
 * Linearly interpolates between this point to [other] on [progress] position.
 *
 * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and [other].
 * @return This point for chaining.
 */
inline fun Point3D.lerp(other: Point3D, progress: Float) = lerp(this, other, progress, this)
/**
 * Linearly interpolates between this point to ([x], [y]) on [progress] position.
 *
 * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and ([x], [y]).
 * @return This point for chaining.
 */
inline fun Point3D.lerp(x: Float, y: Float, progress: Float) = lerp(cPoint(x, y), progress)
/**
 * Linearly interpolates between this point to ([x], [y], [z]) on [progress] position.
 *
 * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and ([x], [y]).
 * @return This point for chaining.
 */
inline fun Point3D.lerp(x: Float, y: Float, z: Float, progress: Float) = lerp(cPoint(x, y, z), progress)

/**
 * Composes this point with the smallest components between this point and [other].
 *
 * @return This point for chaining.
 */
inline fun Point3D.min(other: Point2D) = min(this, other, this)
/**
 * Composes this point with the smallest components between this point and [other].
 *
 * @return This point for chaining.
 */
inline fun Point3D.min(other: Point3D) = min(this, other, this)
/**
 * Composes this point with the smallest components between this point and ([x], [y]).
 *
 * @return This point for chaining.
 */
inline fun Point3D.min(x: Float, y: Float) = min(cPoint(x, y))
/**
 * Composes this point with the smallest components between this point and ([x], [y], [z]).
 *
 * @return This point for chaining.
 */
inline fun Point3D.min(x: Float, y: Float, z: Float) = min(cPoint(x, y, z))

/**
 * Composes this point with the largest components between this point and [other].
 *
 * @return This point for chaining.
 */
inline fun Point3D.max(other: Point2D) = max(this, other, this)
/**
 * Composes this point with the largest components between this point and [other].
 *
 * @return This point for chaining.
 */
inline fun Point3D.max(other: Point3D) = max(this, other, this)
/**
 * Composes this point with the largest components between this point and ([x], [y]).
 *
 * @return This point for chaining.
 */
inline fun Point3D.max(x: Float, y: Float) = max(cPoint(x, y))
/**
 * Composes this point with the largest components between this point and ([x], [y], [z]).
 *
 * @return This point for chaining.
 */
inline fun Point3D.max(x: Float, y: Float, z: Float) = max(cPoint(x, y, z))

/**
 * Multiplies [matrix] with this point. Use this method only if [matrix] is known to be a transformation.
 *
 * @return This vector for chaining.
 */
inline fun Point3D.multiplyLeft(matrix: Matrix4) = multiply(matrix, this, this)

// ---

private val p2 by lazy { Point2D() }
private val p3 by lazy { Point3D() }

fun cPoint(x: Float, y: Float) = p2.set(x, y)
fun cPoint(x: Float, y: Float, z: Float) = p3.set(x, y, z)