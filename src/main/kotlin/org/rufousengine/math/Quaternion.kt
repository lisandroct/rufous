@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.math

import kotlin.math.sqrt

/**
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @property[w] The w component.
 * @constructor Creates a quaternion.
 */
data class Quaternion(val x: Float, val y: Float, val z: Float, val w: Float) {
    constructor() : this(0f, 0f, 0f, 1f)
    constructor(other: Quaternion) : this(other.x, other.y, other.z, other.w)
    constructor(vector: Vector3, scalar: Float) : this(vector.x, vector.y, vector.z, scalar)

    val components = lazy { floatArrayOf(x, y, z, w) }

    inline operator fun get(index: Int) = when(index) {
        0 -> x
        1 -> y
        2 -> z
        3 -> w
        else -> throw IllegalArgumentException("index must be in ${0..3}")
    }

    inline operator fun invoke(index: Int) = get(index - 1)

    override fun toString() = "($x, $y, $z, $w)"

    companion object {
        val identity = Quaternion()
        val zero = Quaternion(0f, 0f, 0f, 0f)
    }
}

inline val Quaternion.normalized: Quaternion
    get() = normalize(this)
inline val Quaternion.conjugated: Quaternion
    get() = conjugate(this)

inline val Quaternion.magnitude: Float
    get() = sqrt(magnitudeSquared)
inline val Quaternion.magnitudeSquared: Float
    get() = x * x + y * y + z * z + w * w

/** Whether this quaternion is a unit quaternion. */
inline val Quaternion.isUnit: Boolean
    get() = magnitudeSquared.isOne()
/** Whether this quaternion is the identity quaternion. */
inline val Quaternion.isIdentity: Boolean
    get() = x.isZero() && y.isZero() && z.isZero() && w.isOne()
/** Whether this quaternion is the zero quaternion. */
inline val Quaternion.isZero: Boolean
    get() = x.isZero() && y.isZero() && z.isZero() && w.isZero()

inline val Quaternion.vectorPart
    get() = Vector3(x, y, z)

inline operator fun Quaternion.unaryPlus() = this
inline operator fun Quaternion.unaryMinus() = negate(this)
inline operator fun Quaternion.plus(other: Quaternion) = add(this, other)
inline operator fun Quaternion.minus(other: Quaternion) = subtract(this, other)
inline operator fun Quaternion.times(scalar: Float) = scale(this, scalar)
inline operator fun Float.times(quaternion: Quaternion) = quaternion.times(this)
inline operator fun Quaternion.div(scalar: Float) = times(1 / scalar)
inline operator fun Quaternion.times(other: Quaternion) = multiply(this, other)
inline operator fun Quaternion.times(vector: Vector3) = transformSafe(this, vector)

// ---------------------------------------------------------------------------------------------------------------------

/** Negates every component of [quaternion]. */
inline fun negate(quaternion: Quaternion) = Quaternion(-quaternion.x, -quaternion.y, -quaternion.z, -quaternion.w)

/** Normalizes [quaternion]. */
inline fun normalize(quaternion: Quaternion) = quaternion / quaternion.magnitude

/** Conjugates [quaternion] (i.e., it negates the vector part). */
inline fun conjugate(quaternion: Quaternion) = Quaternion(-quaternion.x, -quaternion.y, -quaternion.z, quaternion.w)

/** Inverts [quaternion]. */
fun inverse(quaternion: Quaternion): Quaternion {
    val conjugated = conjugate(quaternion)

    return conjugated / conjugated.magnitudeSquared
}

/** Scales [quaternion] (i.e., multiplies each component with [scalar]). */
inline fun scale(quaternion: Quaternion, scalar: Float) = Quaternion(quaternion.x * scalar, quaternion.y * scalar, quaternion.z * scalar, quaternion.w * scalar)

/** Adds [b] to [a]. */
inline fun add(a: Quaternion, b: Quaternion) = Quaternion(a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w)

/** Subtracts [b] from [a]. */
inline fun subtract(a: Quaternion, b: Quaternion) = Quaternion(a.x - b.x, a.y - b.y, a.z - b.z, a.w - b.w)

/** Multiplies [a] with [b]. */
inline fun multiply(a: Quaternion, b: Quaternion) = Quaternion(
        a.x * b.w + a.y * b.z - a.z * b.y + a.w * b.x,
        a.y * b.w + a.z * b.x + a.w * b.y - a.x * b.z,
        a.z * b.w + a.w * b.z + a.x * b.y - a.y * b.x,
        a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z
)

/**
 * Rotates [v] with [q].
 *
 * If [q] is known to be a unit quaternion, [transform] is a cheaper alternative.
 */
fun transformSafe(q: Quaternion, v: Vector3) = transform(q.normalized, v)

/**
 * Rotates [v] with [q].
 *
 * [q] must be a unit quaternion.
 */
fun transform(q: Quaternion, v: Vector3) : Vector3 {
    val b = q.vectorPart

    return v * (q.w * q.w - b.magnitudeSquared) + b * (dot(v, b) * 2f) + b X v * (q.w * 2f)
}