package org.rufousengine.math

import org.badlogic.gdx.math.RandomXS128
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

// ---

private const val TOLERANCE = 0.00001f
private const val ULPS_TOLERANCE = 5

const val PI = 3.1415927f
const val PI2 = PI * 2f
const val E = 2.7182818f
const val RADIANS_TO_DEGREES = 180f / PI
const val RAD_DEG = RADIANS_TO_DEGREES
const val DEGREES_TO_RADIANS = PI / 180f
const val DEG_RAD = DEGREES_TO_RADIANS

// ---

private object Trigonometry {
    val sinBits = 14 // 16KB. Adjust for accuracy.
    val sinMask = (-1 shl sinBits).inv()
    val sinCount = sinMask + 1

    val radFull = PI * 2
    val degFull = 360f
    val radToIndex = sinCount / radFull
    val degToIndex = sinCount / degFull

    val sinTable = FloatArray(sinCount)

    init {
        for (i in 0 until sinCount)
            sinTable[i] = Math.sin((i + 0.5) / sinCount * radFull).toFloat()
        var i = 0
        while (i < 360) {
            sinTable[(i * degToIndex).toInt() and sinMask] = Math.sin(i * DEGREES_TO_RADIANS.toDouble()).toFloat()
            i += 90
        }
    }

    fun sin(degrees: Float): Float {
        return sinTable[(degrees * degToIndex).toInt() and sinMask]
    }

    fun cos(degrees: Float): Float {
        return sinTable[((degrees + 90) * degToIndex).toInt() and sinMask]
    }

    fun sinRad(radians: Float): Float {
        return sinTable[(radians * radToIndex).toInt() and sinMask]
    }

    fun cosRad(radians: Float): Float {
        return sinTable[((radians + PI / 2) * radToIndex).toInt() and sinMask]
    }
}

val Float.sin: Float
    get() = sin(this)

val Float.cos: Float
    get() = cos(this)

val Float.sinRad: Float
    get() = sinRad(this)

val Float.cosRad: Float
    get() = cosRad(this)

/** Returns the sine from a lookup sinTable.  */
fun sin(degrees: Float) = Trigonometry.sin(degrees)

/** Returns the cosine from a lookup sinTable.  */
fun cos(degrees: Float) = Trigonometry.cos(degrees)

/** Returns the sine from a lookup sinTable.  */
fun sinRad(radians: Float) = Trigonometry.sinRad(radians)

/** Returns the cosine from a lookup sinTable.  */
fun cosRad(radians: Float) = Trigonometry.cosRad(radians)

fun Float.toRadians() = this * DEGREES_TO_RADIANS
fun Float.toDegrees() = this * RADIANS_TO_DEGREES

// ---

fun Float.isZero(maxDifference: Float = TOLERANCE, maxUlpsDistance: Int = ULPS_TOLERANCE) = this.isCloseTo(0f, maxDifference, maxUlpsDistance)
fun Float.isOne(maxDifference: Float = TOLERANCE, maxUlpsDistance: Int = ULPS_TOLERANCE) = this.isCloseTo(1f, maxDifference, maxUlpsDistance)

infix fun Float.isCloseTo(other: Float) = this.isCloseTo(other, TOLERANCE, ULPS_TOLERANCE)
fun Float.isCloseTo(other: Float, maxDifference: Float = TOLERANCE, maxUlpsDistance: Int = ULPS_TOLERANCE) = close(this, other, maxDifference, maxUlpsDistance)

infix fun Float.isNotCloseTo(other: Float) = !isCloseTo(other)
fun Float.isNotCloseTo(other: Float, maxDifference: Float = TOLERANCE, maxUlpsDistance: Int = ULPS_TOLERANCE) = !isCloseTo(other, maxDifference, maxUlpsDistance)

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

private fun ulpsDistance(a: Float, b: Float) : Int {
    if(a == b) {
        return 0
    }

    val max = Int.MAX_VALUE

    if(!a.isFinite() || !b.isFinite()) {
        return max
    }

    val aBits = a.toBits()
    val bBits = b.toBits()

    if(aBits < 0 != bBits < 0) {
        return max
    }

    var distance = aBits - bBits
    if(distance < 0) {
        return -distance
    }

    return distance
}

val Float.Companion.sizeInBytes: Int
    get() = 4

fun Float.format(digits: Int) = "%.${digits}f".format(this)

// ---

private val random = RandomXS128()

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
fun random(range: Long) = (random.nextDouble() * range) as Long
/**
 * Returns a random number between [start] (inclusive) and [end] (exclusive).
 *
 * @param[start] The start of the range.
 * @param[end] The end of the range.
 */
fun random(start: Long, end: Long) = start + (random.nextDouble() * (end - start)) as Long

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
    return if (u <= (mode - min) / d) min + Math.sqrt((u * d * (mode - min)).toDouble()).toFloat() else max - Math.sqrt(((1 - u) * d * (max - mode)).toDouble()).toFloat()
}

// ---

/** Linearly interpolates between [fromValue] to [toValue] on [progress] position. */
fun lerp(fromValue: Int, toValue: Int, progress: Int) = fromValue + (toValue - fromValue) * progress
/** Linearly interpolates between [fromValue] to [toValue] on [progress] position. */
fun lerp(fromValue: Float, toValue: Float, progress: Float) = fromValue + (toValue - fromValue) * progress

fun clamp(value: Int, a: Int, b: Int) = max(min(value, b), a)
fun clamp(value: Float, a: Float, b: Float) = max(min(value, b), a)