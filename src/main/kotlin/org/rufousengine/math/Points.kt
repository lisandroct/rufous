package org.rufousengine.math

import kotlin.math.*

/** Whether this point is the origin point. */
val Point.isOrigin: Boolean
    get() = components.all { it.isZero() }

/** Returns the distance between this point and [other]. */
fun Point.distance(other: Point) = sqrt(distanceSquared(other))
/** Returns the distance between this point and ([x], [y]). */
fun Point.distance(x: Float, y: Float) = distance(cPoint(x, y))
/** Returns the distance between this point and ([x], [y], [z]). */
fun Point.distance(x: Float, y: Float, z: Float) = distance(cPoint(x, y, z))

/** Returns the squared distance between this point and [other]. */
fun Point.distanceSquared(other: Point): Float {
    val dimensions = min(dimensions, other.dimensions)

    var distance = 0f
    for(i in 0 until dimensions) {
        val component = this[i] - other[i]
        distance += component * component
    }

    return distance
}
/** Returns the squared distance between this point and ([x], [y]). */
fun Point.distanceSquared(x: Float, y: Float) = distanceSquared(cPoint(x, y))
/** Returns the squared distance between this point and ([x], [y], [z]). */
fun Point.distanceSquared(x: Float, y: Float, z: Float) = distanceSquared(cPoint(x, y, z))

private val p2 by lazy { Point2D() }
private val p3 by lazy { Point3D() }

fun cPoint(x: Float, y: Float) = p2.set(x, y)
fun cPoint(x: Float, y: Float, z: Float) = p3.set(x, y, z)

// ---

operator fun Point2D.unaryPlus() = Point2D(this)
operator fun Point2D.unaryMinus() = negate(Point2D())

operator fun Point2D.plus(vector: Vector2) = add(vector, Point2D())
operator fun Point2D.minus(vector: Vector2) = subtract(vector, Point2D())
operator fun Point2D.minus(other: Point2D) = subtract(other, Vector2())
operator fun Point2D.times(scalar: Float) = scale(scalar, Point2D())
operator fun Point2D.div(scalar: Float) = scale(1 / scalar, Point2D())

operator fun Point2D.plusAssign(vector: Vector2) { add(vector) }
operator fun Point2D.minusAssign(vector: Vector2) { subtract(vector) }
operator fun Point2D.timesAssign(scalar: Float) { scale(scalar) }
operator fun Point2D.divAssign(scalar: Float) { scale(1 / scalar) }

/**
 * Negates each component.
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.negate(out: Point2D = this) = out.set(-x, -y)
/**
 * Rounds each component to the largest Float value that is smaller than the given value and is a mathematical integer.
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.floor(out: Point2D = this) = out.set(floor(x), floor(y))
/**
 * Rounds each component to the smallest Float value that is larger than the given value and is a mathematical integer.
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.ceil(out: Point2D = this) = out.set(ceil(x), ceil(y))
/**
 * Applies the absolute value to each component.
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.abs(out: Point2D = this) = out.set(abs(x), abs(y))
/**
 * Scales this point (i.e., multiplies each component with [scalar]).
 *
 * @param[scalar] The scalar to multiply the point with.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.scale(scalar: Float, out: Point2D = this) = out.set(x * scalar, y * scalar)

/**
 * Moves this point towards the direction of [vector].
 *
 * @param[vector] The direction vector.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.add(vector: Vector2, out: Point2D = this) = out.set(x + vector.x, y + vector.y)
/**
 * Moves this point towards the direction of ([x], [y]).
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.add(x: Float, y: Float, out: Point2D = this) = add(cVector(x, y), out)

/**
 * Moves this point towards the direction of -[vector].
 *
 * @param[vector] The direction vector.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.subtract(vector: Vector2, out: Point2D = this) = out.set(x - vector.x, y - vector.y)
/**
 * Moves this point towards the direction of -([x], [y]).
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.subtract(x: Float, y: Float, out: Point2D = this) = subtract(cVector(x, y), out)

/**
 * Returns the direction vector pointing from [other] towards this point.
 *
 * @param[other] The other point.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.subtract(other: Point2D, out: Vector2) = out.set(x - other.x, y - other.y)
/**
 * Returns the direction vector pointing from ([x], [y]) towards this point.
 *
 * @param[other] The other point.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Point2D.subtract(x: Float, y: Float, out: Vector2) = subtract(cPoint(x, y), out)

/**
 * Linearly interpolates between this point to [other] on [progress] position.
 *
 * @param[other] The other point.
 * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and [other].
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.lerp(other: Point2D, progress: Float, out: Point2D = this) = out.set(org.rufousengine.math.lerp(x, other.x, progress), org.rufousengine.math.lerp(y, other.y, progress))
/**
 * Linearly interpolates between this point to ([x], [y]) on [progress] position.
 *
 * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and [other].
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.lerp(x: Float, y: Float, progress: Float, out: Point2D = this) = lerp(cPoint(x, y), progress, out)

/**
 * Returns the point composed of the smallest components between this point and [other].
 *
 * @param[other] The other point.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.min(other: Point2D, out: Point2D = this) = out.set(kotlin.math.min(x, other.x), kotlin.math.min(y, other.y))
/**
 * Returns the point composed of the smallest components between this point and ([x], [y]).
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.min(x: Float, y: Float, out: Point2D = this) = min(cPoint(x, y), out)

/**
 * Returns the point composed of the largest components between this point and [other].
 *
 * @param[other] The other point.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.max(other: Point2D, out: Point2D = this) = set(kotlin.math.max(x, other.x), kotlin.math.max(y, other.y))
/**
 * Returns the point composed of the largest components between this point and ([x], [y]).
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point2D.max(x: Float, y: Float, out: Point2D = this) = max(cPoint(x, y), out)

// ---

operator fun Point3D.unaryPlus() = Point3D(this)
operator fun Point3D.unaryMinus() = negate(Point3D())

operator fun Point3D.plus(vector: Vector2) = add(vector, Point3D())
operator fun Point3D.plus(vector: Vector3) = add(vector, Point3D())
operator fun Point3D.minus(vector: Vector2) = subtract(vector, Point3D())
operator fun Point3D.minus(vector: Vector3) = subtract(vector, Point3D())
operator fun Point3D.minus(other: Point2D) = subtract(other, Vector3())
operator fun Point3D.minus(other: Point3D) = subtract(other, Vector3())
operator fun Point3D.times(scalar: Float) = scale(scalar, Point3D())
operator fun Point3D.div(scalar: Float) = scale(1 / scalar, Point3D())

operator fun Point3D.plusAssign(vector: Vector2) { add(vector) }
operator fun Point3D.plusAssign(vector: Vector3) { add(vector) }
operator fun Point3D.minusAssign(vector: Vector2) { subtract(vector) }
operator fun Point3D.minusAssign(vector: Vector3) { subtract(vector) }
operator fun Point3D.timesAssign(scalar: Float) { scale(scalar) }
operator fun Point3D.divAssign(scalar: Float) { scale(1 / scalar) }

/**
 * Negates each component.
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.negate(out: Point3D = this) = out.set(-x, -y, -z)
/**
 * Rounds each component to the largest Float value that is smaller than the given value and is a mathematical integer.
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.floor(out: Point3D = this) = out.set(floor(x), floor(y), floor(z))
/**
 * Rounds each component to the smallest Float value that is larger than the given value and is a mathematical integer.
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.ceil(out: Point3D = this) = out.set(ceil(x), ceil(y), ceil(z))
/**
 * Applies the absolute value to each component.
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.abs(out: Point3D = this) = out.set(abs(x), abs(y), abs(z))
/**
 * Scales this point (i.e., multiplies each component with [scalar]).
 *
 * @param[scalar] The scalar to multiply the point with.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.scale(scalar: Float, out: Point3D = this) = out.set(x * scalar, y * scalar, z * scalar)

/**
 * Moves this point towards the direction of [vector].
 *
 * @param[vector] The direction vector.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.add(vector: Vector2, out: Point3D = this) = out.set(x + vector.x, y + vector.y, z)
/**
 * Moves this point towards the direction of ([x], [y]).
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.add(x: Float, y: Float, out: Point3D = this) = add(cVector(x, y), out)

/**
 * Moves this point towards the direction of [vector].
 *
 * @param[vector] The direction vector.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.add(vector: Vector3, out: Point3D = this) = out.set(x + vector.x, y + vector.y, z + vector.z)
/**
 * Moves this point towards the direction of ([x], [y], [z]).
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.add(x: Float, y: Float, z: Float, out: Point3D = this) = add(cVector(x, y, z), out)

/**
 * Moves this point towards the direction of -[vector].
 *
 * @param[vector] The direction vector.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.subtract(vector: Vector2, out: Point3D = this) = out.set(x - vector.x, y - vector.y, z)
/**
 * Moves this point towards the direction of -([x], [y]).
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.subtract(x: Float, y: Float, out: Point3D = this) = subtract(cVector(x, y), out)

/**
 * Moves this point towards the direction of -[vector].
 *
 * @param[vector] The direction vector.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.subtract(vector: Vector3, out: Point3D = this) = out.set(x - vector.x, y - vector.y, z - vector.z)
/**
 * Moves this point towards the direction of -([x], [y], [z]).
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.subtract(x: Float, y: Float, z: Float, out: Point3D = this) = subtract(cVector(x, y, z), out)

/**
 * Returns the direction vector pointing from [other] towards this point.
 *
 * @param[other] The other point.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Point3D.subtract(other: Point2D, out: Vector3) = out.set(x - other.x, y - other.y, z)
/**
 * Returns the direction vector pointing from ([x], [y]) towards this point.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Point3D.subtract(x: Float, y: Float, out: Vector3) = subtract(cPoint(x, y), out)

/**
 * Returns the direction vector pointing from [other] towards this point.
 *
 * @param[other] The other point.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Point3D.subtract(other: Point3D, out: Vector3) = out.set(x - other.x, y - other.y, z - other.z)
/**
 * Returns the direction vector pointing from ([x], [y], [z]) towards this point.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Point3D.subtract(x: Float, y: Float, z: Float, out: Vector3) = subtract(cPoint(x, y, z), out)

/**
 * Linearly interpolates between this point to [other] on [progress] position.
 *
 * @param[other] The other point.
 * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and [other].
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.lerp(other: Point2D, progress: Float, out: Point3D = this) = out.set(org.rufousengine.math.lerp(x, other.x, progress), org.rufousengine.math.lerp(y, other.y, progress), z * progress)
/**
 * Linearly interpolates between this point to ([x], [y]) on [progress] position.
 *
 * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and [other].
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.lerp(x: Float, y: Float, progress: Float, out: Point3D = this) = lerp(cPoint(x, y), progress, out)

/**
 * Linearly interpolates between this point to [other] on [progress] position.
 *
 * @param[other] The other point.
 * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and [other].
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.lerp(other: Point3D, progress: Float, out: Point3D = this) = out.set(org.rufousengine.math.lerp(x, other.x, progress), org.rufousengine.math.lerp(y, other.y, progress), org.rufousengine.math.lerp(z, other.z, progress))
/**
 * Linearly interpolates between this point to ([x], [y], [z]) on [progress] position.
 *
 * @param[progress] The interpolation progress. If it is in between 0 and 1, the resulting point will be in between this point and [other].
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.lerp(x: Float, y: Float, z: Float, progress: Float, out: Point3D = this) = lerp(cPoint(x, y, z), progress, out)

/**
 * Returns the point composed of the smallest components between this point and [other].
 *
 * @param[other] The other point.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.min(other: Point2D, out: Point3D = this) = out.set(kotlin.math.min(x, other.x), kotlin.math.min(y, other.y), kotlin.math.min(z, other.z))
/**
 * Returns the point composed of the smallest components between this point and ([x], [y]).
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.min(x: Float, y: Float, out: Point3D = this) = min(cPoint(x, y), out)

/**
 * Returns the point composed of the smallest components between this point and [other].
 *
 * @param[other] The other point.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.min(other: Point3D, out: Point3D = this) = out.set(kotlin.math.min(x, other.x), kotlin.math.min(y, other.y), kotlin.math.min(z, other.z))
/**
 * Returns the point composed of the smallest components between this point and ([x], [y], [z]).
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.min(x: Float, y: Float, z: Float, out: Point3D = this) = min(cPoint(x, y, z), out)

/**
 * Returns the point composed of the largest components between this point and [other].
 *
 * @param[other] The other point.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.max(other: Point2D, out: Point3D = this) = out.set(kotlin.math.max(x, other.x), kotlin.math.max(y, other.y), kotlin.math.max(z, other.z))
/**
 * Returns the point composed of the largest components between this point and ([x], [y]).
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.max(x: Float, y: Float, out: Point3D = this) = max(cPoint(x, y), out)

/**
 * Returns the point composed of the largest components between this point and [other].
 *
 * @param[other] The other point.
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.max(other: Point3D, out: Point3D = this) = out.set(kotlin.math.max(x, other.x), kotlin.math.max(y, other.y), kotlin.math.max(z, other.z))
/**
 * Returns the point composed of the largest components between this point and ([x], [y], [z]).
 *
 * @param[out] The point that stores the result.
 * @return The output point for chaining.
 */
fun Point3D.max(x: Float, y: Float, z: Float, out: Point3D = this) = max(cPoint(x, y, z), out)