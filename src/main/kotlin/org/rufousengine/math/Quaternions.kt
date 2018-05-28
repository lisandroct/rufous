package org.rufousengine.math

import kotlin.math.sqrt

val Quaternion.magnitude: Float
    get() = sqrt(magnitudeSquared)
val Quaternion.magnitudeSquared: Float
    get() = x * x + y * y + z * z + w * w

/** Whether this quaternion is a unit quaternion. */
val Quaternion.isUnit: Boolean
    get() = magnitudeSquared.isOne()
/** Whether this quaternion is the identity quaternion. */
val Quaternion.isIdentity: Boolean
    get() = x.isZero() && y.isZero() && z.isZero() && w.isOne()
/** Whether this quaternion is the zero quaternion. */
val Quaternion.isZero: Boolean
    get() = components.all { it.isZero() }

/**
 * Normalizes this quaternion.
 *
 * @return This quaternion for chaining.
 */
fun Quaternion.normalize() = scale(1 / magnitude)
/**
 * Conjugates this quaternion (i.e., it negates the vector part).
 *
 * @return This quaternion for chaining.
 */
fun Quaternion.conjugate() = set(-x, -y, -z, w)
/**
 * Inverts this quaternion.
 *
 * @return This quaternion for chaining.
 */
fun Quaternion.inverse() = conjugate().scale(1 / magnitudeSquared)

fun Quaternion.getVectorPart(out: Vector3) = out.set(x, y, z)

/**
 * Scales this quaternion (i.e., multiplies each component with [scalar]).
 *
 * @param[scalar] The scalar to multiply the quaternion with.
 * @return This quaternion for chaining.
 */
fun Quaternion.scale(scalar: Float) = set(x * scalar, y * scalar, z * scalar, w * scalar)

/**
 * Adds [other] to this quaternion.
 *
 * @param[other] The other quaternion.
 * @return This quaternion for chaining.
 */
fun Quaternion.add(other: Quaternion) = set(x + other.x, y + other.y, z + other.z, w + other.w)

/**
 * Subtracts [other] from this quaternion.
 *
 * @param[other] The other quaternion.
 * @return This quaternion for chaining.
 */
fun Quaternion.subtract(other: Quaternion) = set(x - other.x, y - other.y, z - other.z, w - other.w)

/**
 * Multiplies this quaternion with [other].
 *
 * @param[other] The other quaternion.
 * @return This quaternion for chaining.
 */
fun Quaternion.multiply(other: Quaternion) = set(
        w * other.x + x * other.w + y * other.z - z * other.y,
        w * other.y - x * other.z + y * other.w + z * other.x,
        w * other.z + x * other.y - y * other.x + z * other.w,
        w * other.w - x * other.x - y * other.y - z * other.z
)
/**
 * Multiplies [other] with this quaternion.
 *
 * @param[other] The other quaternion.
 * @return This quaternion for chaining.
 */
fun Quaternion.multiplyLeft(other: Quaternion) = set(
        other.w * x + other.x * w + other.y * z - other.z * y,
        other.w * y - other.x * z + other.y * w + other.z * x,
        other.w * z + other.x * y - other.y * x + other.z * w,
        other.w * w - other.x * x - other.y * y - other.z * z
)