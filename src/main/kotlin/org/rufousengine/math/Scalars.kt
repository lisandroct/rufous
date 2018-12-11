@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.math

import org.badlogic.gdx.math.RandomXS128
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.ulp

// --- CONSTANTS -------------------------------------------------------------------------------------------------------

const val PI = 3.1415926536f
const val HALF_PI = PI * 0.5f
const val TWO_PI = PI * 2.0f
const val FOUR_PI = PI * 4.0f
const val INV_PI      = 1.0f / PI
const val INV_TWO_PI  = INV_PI * 0.5f
const val INV_FOUR_PI = INV_PI * 0.25f

const val RADIANS_TO_DEGREES = 180f * INV_PI
const val RAD_DEG = RADIANS_TO_DEGREES
const val DEGREES_TO_RADIANS = PI / 180f
const val DEG_RAD = DEGREES_TO_RADIANS

const val E = 2.7182818f

// --- TRIGONOMETRY ----------------------------------------------------------------------------------------------------

/** Returns the sine from a lookup table.  */
fun sin(degrees: Float) = Trigonometry.sin(degrees)

/** Returns the cosine from a lookup table.  */
fun cos(degrees: Float) = Trigonometry.cos(degrees)

/** Returns the tangent from a lookup table.  */
fun tan(degrees: Float) = Trigonometry.tan(degrees)

/** Returns the sine from a lookup table.  */
fun sinRad(radians: Float) = Trigonometry.sinRad(radians)

/** Returns the cosine from a lookup table.  */
fun cosRad(radians: Float) = Trigonometry.cosRad(radians)

/** Returns the tangent from a lookup table.  */
fun tanRad(radians: Float) = Trigonometry.tanRad(radians)

fun Float.toRadians() = this * DEGREES_TO_RADIANS
fun Float.toDegrees() = this * RADIANS_TO_DEGREES

// --- COMPARISONS -----------------------------------------------------------------------------------------------------

private const val TOLERANCE = 0.00001f
private const val ULPS_TOLERANCE = 5

fun Float.isZero(maxDifference: Float = TOLERANCE, maxUlpsDistance: Int = ULPS_TOLERANCE) = (this).isEqualTo(0f, maxDifference, maxUlpsDistance)
fun Float.isOne(maxDifference: Float = TOLERANCE, maxUlpsDistance: Int = ULPS_TOLERANCE) = this.isEqualTo(1f, maxDifference, maxUlpsDistance)

infix fun Float.isEqualTo(other: Float) = this.isEqualTo(other, TOLERANCE, ULPS_TOLERANCE)
fun Float.isEqualTo(other: Float, maxDifference: Float = TOLERANCE, maxUlpsDistance: Int = ULPS_TOLERANCE) = close(this, other, maxDifference, maxUlpsDistance)

infix fun Float.isNotCloseTo(other: Float) = !isEqualTo(other)
fun Float.isNotCloseTo(other: Float, maxDifference: Float = TOLERANCE, maxUlpsDistance: Int = ULPS_TOLERANCE) = !isEqualTo(other, maxDifference, maxUlpsDistance)

fun close(a: Float, b: Float, tolerance: Float = TOLERANCE, ulpsTolerance: Int = ULPS_TOLERANCE) : Boolean {
    val absDifference = abs(a - b)
    if(absDifference <= tolerance) {
        return true
    }

    val ulpsDistance = ulpsDistance(a, b)
    if(ulpsDistance <= ulpsTolerance) {
        return true
    }

    val largest = max(abs(a), abs(b))
    if (absDifference <= largest * tolerance) {
        return true
    }

    return false
}

private fun distance(a: Float, b: Float) = abs(a - b) / (abs(b) * b.ulp)

private fun ulpsDistance(a: Float, b: Float) : Int {
    val max = Int.MAX_VALUE

    if(!a.isFinite() || !b.isFinite()) {
        return max
    }

    val aBits = a.toBits()
    val bBits = b.toBits()

    if(aBits < 0 != bBits < 0) {
        return max
    }

    val distance = aBits - bBits
    return if(distance < 0) -distance else distance
}

private fun ulpsDistance2(a: Float, b: Float): Int {
    if(!a.isFinite() || !b.isFinite()) {
        return Int.MAX_VALUE
    }

    var aBits = a.toBits()
    if (aBits < 0) {
        aBits = (0b1000_0000_0000_0000_0000_0000_0000_0000 - aBits).toInt()
    }

    var bBits = b.toBits()
    if (bBits < 0) {
        (0b1000_0000_0000_0000_0000_0000_0000_0000 - bBits).toInt()
    }

    return if (aBits > bBits) aBits - bBits else bBits - aBits
}

// --- RANDOM ----------------------------------------------------------------------------------------------------------

private val random = RandomXS128()

fun setRandomSeed(seed: Long) = random.setSeed(seed)

/**
 * Returns a random number between 0 (inclusive) and the specified value (exclusive).
 *
 * @param[range] The size of the range.
 */
fun random(range: Int) = random.nextInt(range + 1)
/**
 * Returns a random number between [start] (inclusive) and [end] (exclusive).
 *
 * @param[start] The start of the range.
 * @param[end] The end of the range.
 */
fun random(start: Int, end: Int)= start + random.nextInt(end - start + 1)

/**
 * Returns a random number between 0 (inclusive) and the specified value (exclusive).
 *
 * @param[range] The size of the range.
 */
fun random(range: Long) = (random.nextDouble() * range).toLong()
/**
 * Returns a random number between [start] (inclusive) and [end] (exclusive).
 *
 * @param[start] The start of the range.
 * @param[end] The end of the range.
 */
fun random(start: Long, end: Long) = start + (random.nextDouble() * (end - start)).toLong()

/** Returns a random boolean value. */
fun randomBoolean() = random.nextBoolean()
/** Returns true if a random value between 0 and 1 is less than the specified value. */
fun randomBoolean(chance: Float) = random() < chance

/** Returns random number between 0.0 (inclusive) and 1.0 (exclusive). */
fun random() = random.nextFloat()
/**
 * Returns a random number between 0 (inclusive) and the specified value (exclusive).
 *
 * @param[range] The size of the range.
 */
fun random(range: Float) = random.nextFloat() * range
/**
 * Returns a random number between [start] (inclusive) and [end] (exclusive).
 *
 * @param[start] The start of the range.
 * @param[end] The end of the range.
 */
fun random(start: Float, end: Float) = start + random.nextFloat() * (end - start)

/** Returns -1 or 1, randomly. */
fun randomSign() = 1 or (random.nextInt() shr 31)

/**
 * Returns a triangularly distributed random number between -1.0 (exclusive) and 1.0 (exclusive), where values around zero are
 * more likely.
 */
fun randomTriangular() = random.nextFloat() - random.nextFloat()
/**
 * Returns a triangularly distributed random number between -[max] (exclusive) and [max] (exclusive), where values
 * around zero are more likely.
 *
 * @param[max] The upper limit.
 */
fun randomTriangular(max: Float) = (random.nextFloat() - random.nextFloat()) * max
/**
 * Returns a triangularly distributed random number between [min] (inclusive) and [max] (exclusive), where the
 * [mode] argument defaults to the midpoint between the bounds, giving a symmetric distribution.
 *
 * @param[min] The lower limit.
 * @param[max] The upper limit.
 */
fun randomTriangular(min: Float, max: Float) = randomTriangular(min, max, (min + max) * 0.5f)
/**
 * Returns a triangularly distributed random number between [min] (inclusive) and [max] (exclusive), where values
 * around [mode] are more likely.
 *
 * @param[min] The lower limit.
 * @param[max] The upper limit.
 * @param[mode] The point around which the values are more likely.
 */
fun randomTriangular(min: Float, max: Float, mode: Float): Float {
    val u = random.nextFloat()
    val d = max - min
    return if (u <= (mode - min) / d) min + sqrt(u * d * (mode - min)) else max - sqrt((1 - u) * d * (max - mode))
}

// --- OTHER UTILITIES -------------------------------------------------------------------------------------------------

/** Linearly interpolates between [fromValue] to [toValue] on [progress] position. */
inline fun lerp(fromValue: Int, toValue: Int, progress: Int) = fromValue + (toValue - fromValue) * progress
/** Linearly interpolates between [fromValue] to [toValue] on [progress] position. */
inline fun lerp(fromValue: Float, toValue: Float, progress: Float) = fromValue + (toValue - fromValue) * progress

inline fun clamp(value: Int, a: Int, b: Int) = max(min(value, b), a)
inline fun clamp(value: Float, a: Float, b: Float) = max(min(value, b), a)

// ---------------------------------------------------------------------------------------------------------------------

inline fun sqrt(value: Float) = kotlin.math.sqrt(value)

inline fun pow(a: Int, b: Int) = Math.pow(a.toDouble(), b.toDouble()).toInt()
inline fun pow(a: Float, b: Int) = Math.pow(a.toDouble(), b.toDouble()).toFloat()
inline fun pow(a: Int, b: Float) = Math.pow(a.toDouble(), b.toDouble()).toFloat()
inline fun pow(a: Float, b: Float) = Math.pow(a.toDouble(), b.toDouble()).toFloat()