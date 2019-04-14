@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.math

import java.util.*
import kotlin.math.sqrt

/**
 * An immutable quaternion.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @property[w] The w component.
 * @constructor Creates a quaternion.
 */
class Quaternion(x: Float = 0f, y: Float = 0f, z: Float = 0f, w: Float = 1f) {
    val components = floatArrayOf(x, y, z, w)

    constructor(other: Quaternion) : this(other.x, other.y, other.z, other.w)

    inline var x: Float
        get() = components[0]
        set(value) { components[0] = value }
    inline var y: Float
        get() = components[1]
        set(value) { components[1] = value }
    inline var z: Float
        get() = components[2]
        set(value) { components[2] = value }
    inline var w: Float
        get() = components[3]
        set(value) { components[3] = value }

    operator fun component1() = get(0)
    operator fun component2() = get(1)
    operator fun component3() = get(2)
    operator fun component4() = get(3)

    operator fun get(index: Int) = if(index !in 0..3) { throw IllegalArgumentException("index must be in 0..3") }
    else {
        components[index]
    }

    operator fun set(index: Int, value: Float) = if(index !in 0..3) { throw IllegalArgumentException("index must be in 0..3") }
    else {
        components[index] = value
    }

    fun set(other: Quaternion) = set(other.x, other.y, other.z, other.w)
    fun set(x: Float = this.x, y: Float = this.y, z: Float = this.z, w: Float = this.w): Quaternion {
        this.x = x
        this.y = y
        this.z = z
        this.w = w

        return this
    }

    fun copy() = Quaternion(this)

    override fun toString() = components.joinToString(prefix = "(", postfix = ")")

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Quaternion) {
            return false
        }

        for(i in 0..3) {
            if(this[i] isNotCloseTo other[i]) {
                return false
            }
        }

        return true
    }

    override fun hashCode() = Arrays.hashCode(components)
}

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
    get() = components.all { it.isZero() }

inline fun Quaternion.getVectorPart(out: Vector3 = Vector3()) = out.set(x, y, z)

/**
 * Negates every component of [quaternion] and stores the result in [out].
 *
 * @return The [out] quaternion for chaining.
 */
inline fun negate(quaternion: Quaternion, out: Quaternion = Quaternion()) = out.set(-quaternion.x, -quaternion.y, -quaternion.z, -quaternion.w)

/**
 * Normalizes [quaternion] and stores the result in [out].
 *
 * @return The [out] quaternion for chaining.
 */
inline fun normalize(quaternion: Quaternion, out: Quaternion = Quaternion()) = scale(quaternion, 1 / quaternion.magnitude, out)

/**
 * Conjugates [quaternion] (i.e., it negates the vector part) and stores the result in [out].
 *
 * @return The [out] quaternion for chaining.
 */
inline fun conjugate(quaternion: Quaternion, out: Quaternion = Quaternion()) = out.set(-quaternion.x, -quaternion.y, -quaternion.z, quaternion.w)

/**
 * Inverts [quaternion] and stores the result in [out].
 *
 * @return The [out] quaternion for chaining.
 */
fun inverse(quaternion: Quaternion, out: Quaternion = Quaternion()): Quaternion {
    conjugate(quaternion, out)

    scale(out, 1 / out.magnitudeSquared, out)

    return out
}

/**
 * Scales [quaternion] (i.e., multiplies each component with [scalar]) and stores the result in [out].
 *
 * @return The [out] quaternion for chaining.
 */
inline fun scale(quaternion: Quaternion, scalar: Float, out: Quaternion = Quaternion()) = out.set(quaternion.x * scalar, quaternion.y * scalar, quaternion.z * scalar, quaternion.w * scalar)

/**
 * Adds [b] to [a] and stores the result in [out].
 *
 * @return The [out] quaternion for chaining.
 */
inline fun add(a: Quaternion, b: Quaternion, out: Quaternion = Quaternion()) = out.set(a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w)

/**
 * Subtracts [b] from [a] and stores the result in [out].
 *
 * @return The [out] quaternion for chaining.
 */
inline fun subtract(a: Quaternion, b: Quaternion, out: Quaternion = Quaternion()) = out.set(a.x - b.x, a.y - b.y, a.z - b.z, a.w - b.w)

/**
 * Multiplies [a] with [b] and stores the result in [out].
 *
 * @return The [out] quaternion for chaining.
 */
inline fun multiply(a: Quaternion, b: Quaternion, out: Quaternion = Quaternion()) = out.set(
        a.w * b.x + a.x * b.w + a.y * b.z - a.z * b.y,
        a.w * b.y - a.x * b.z + a.y * b.w + a.z * b.x,
        a.w * b.z + a.x * b.y - a.y * b.x + a.z * b.w,
        a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z
)

// ---------------------------------------------------------------------------------------------------------------------

inline operator fun Quaternion.unaryPlus() = this.copy()
inline operator fun Quaternion.unaryMinus() = negate(this, Quaternion())

inline operator fun Quaternion.plus(other: Quaternion) = add(this, other, Quaternion())
inline operator fun Quaternion.minus(other: Quaternion) = subtract(this, other, Quaternion())
inline operator fun Quaternion.times(scalar: Float) = scale(this, scalar, Quaternion())
inline operator fun Quaternion.times(other: Quaternion) = multiply(this, other, Quaternion())
inline operator fun Quaternion.div(scalar: Float) = times(1 / scalar)

inline operator fun Quaternion.plusAssign(other: Quaternion) { add(other) }
inline operator fun Quaternion.minusAssign(other: Quaternion) { subtract(other) }
inline operator fun Quaternion.timesAssign(scalar: Float) { scale(scalar) }
inline operator fun Quaternion.timesAssign(other: Quaternion) { multiply(other) }
inline operator fun Quaternion.divAssign(scalar: Float) = timesAssign(1 / scalar)


/**
 * Negates every component of this quaternion.
 *
 * @return This quaternion for chaining.
 */
inline fun Quaternion.negate() = negate(this, this)

/**
 * Normalizes this quaternion.
 *
 * @return This quaternion for chaining.
 */
inline fun Quaternion.normalize() = normalize(this, this)
/**
 * Conjugates this quaternion (i.e., it negates the vector part).
 *
 * @return This quaternion for chaining.
 */
inline fun Quaternion.conjugate() = conjugate(this, this)
/**
 * Inverts this quaternion.
 *
 * @return This quaternion for chaining.
 */
inline fun Quaternion.inverse() = inverse(this, this)

/**
 * Scales this quaternion (i.e., multiplies each component with [scalar]).
 *
 * @return This quaternion for chaining.
 */
inline fun Quaternion.scale(scalar: Float) = scale(this, scalar, this)

/**
 * Adds [other] to this quaternion.
 *
 * @return This quaternion for chaining.
 */
inline fun Quaternion.add(other: Quaternion) = add(this, other, this)

/**
 * Subtracts [other] from this quaternion.
 *
 * @return This quaternion for chaining.
 */
inline fun Quaternion.subtract(other: Quaternion) = subtract(this, other, this)

/**
 * Multiplies this quaternion with [other].
 *
 * @return This quaternion for chaining.
 */
inline fun Quaternion.multiply(other: Quaternion) = multiply(this, other, this)
/**
 * Multiplies [other] with this quaternion.
 *
 * @return This quaternion for chaining.
 */
inline fun Quaternion.multiplyLeft(other: Quaternion) = multiply(other, this, this)