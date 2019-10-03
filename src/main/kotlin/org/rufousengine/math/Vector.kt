@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.math

import kotlin.math.abs
import kotlin.math.acos

/**
 * A two-dimensional vector.
 *
 * The purpose of the Vector classes is to represent directions. To represent positions, use Points instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @constructor Creates a two-dimensional vector pointing towards ([x], [y]).
 */
data class Vector2(val x: Float, val y: Float) {
    constructor() : this(0f, 0f)
    constructor(v: Float) : this(v, v)
    constructor(other: Vector2) : this(other.x, other.y)
    constructor(point: Point2D) : this(point.x, point.y)

    internal val components by lazy { floatArrayOf(x, y) }

    inline val z: Float
        get() = 0f
    inline val w: Float
        get() = 0f

    inline operator fun get(index: Int) = when(index) {
        0 -> x
        1 -> y
        else -> 0f
    }

    inline operator fun invoke(index: Int) = get(index - 1)

    override fun toString() = "($x, $y)"

    companion object {
        val zero = Vector2()
        val one = Vector2(1f, 1f)
        val x = Vector2(1f, 0f)
        val y = Vector2(0f, 1f)
    }
}

inline val Vector2.magnitude: Float
    get() = sqrt(magnitudeSquared)
inline val Vector2.magnitudeSquared: Float
    get() = dot(this,this)

/** Whether all components are zero. */
inline val Vector2.isZero: Boolean
    get() = x.isZero() && y.isZero()
/** Whether this vector is a unit length vector. */
inline val Vector2.isUnit: Boolean
    get() = magnitudeSquared.isOne()
/** Whether all components are 1. */
inline val Vector2.isOne: Boolean
    get() = x.isOne() && y.isOne()
/** Whether all components are 1. */
inline val Vector2.isValid: Boolean
    get() = !x.isNaN() && x.isFinite() &&
            !y.isNaN() && y.isFinite()

inline val Vector2.normalized: Vector2
    get() = normalize(this)

inline operator fun Vector2.unaryPlus() = this
inline operator fun Vector2.unaryMinus() = Vector2(-x, -y)
inline operator fun Vector2.times(scalar: Float) = scale(this, scalar)
inline operator fun Float.times(vector: Vector2) = vector.times(this)
inline operator fun Vector2.div(scalar: Float) = times(1 / scalar)
inline operator fun Vector2.plus(other: Vector2) = add(this, other)
inline operator fun Vector2.plus(other: Vector3) = add(this, other)
inline operator fun Vector2.plus(other: Vector4) = add(this, other)
inline operator fun Vector2.minus(other: Vector2) = subtract(this, other)
inline operator fun Vector2.minus(other: Vector3) = subtract(this, other)
inline operator fun Vector2.minus(other: Vector4) = subtract(this, other)

inline operator fun Vector2.plus(scalar: Float) = add(this, scalar)
inline operator fun Vector2.minus(scalar: Float) = add(this, -scalar)

/**
 * A three-dimensional vector.
 *
 * The purpose of the Vector classes is to represent directions. To represent positions, use Points instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @constructor Creates a three-dimensional vector pointing towards ([x], [y], [z]).
 */
data class Vector3(val x: Float, val y: Float, val z: Float) {
    constructor() : this(0f, 0f, 0f)
    constructor(v: Float) : this(v, v, v)
    constructor(other: Vector2) : this(other.x, other.y, 0f)
    constructor(other: Vector2, z: Float) : this(other.x, other.y, z)
    constructor(x: Float, other: Vector2) : this(x, other.x, other.y)
    constructor(other: Vector3) : this(other.x, other.y, other.z)
    constructor(point: Point2D) : this(point.x, point.y, 0f)
    constructor(point: Point2D, z: Float) : this(point.x, point.y, z)
    constructor(x: Float, point: Point2D) : this(x, point.x, point.y)
    constructor(point: Point3D) : this(point.x, point.y, point.z)

    internal val components by lazy { floatArrayOf(x, y, z) }

    inline val w: Float
        get() = 0f

    inline operator fun get(index: Int) = when(index) {
        0 -> x
        1 -> y
        2 -> z
        else -> 0f
    }

    inline operator fun invoke(index: Int) = get(index - 1)

    override fun toString() = "($x, $y, $z)"

    companion object {
        val zero = Vector3()
        val one = Vector3(1f, 1f, 1f)
        val x = Vector3(1f, 0f, 0f)
        val y = Vector3(0f, 1f, 0f)
        val z = Vector3(0f, 0f, 1f)
    }
}

inline val Vector3.magnitude: Float
    get() = sqrt(magnitudeSquared)
inline val Vector3.magnitudeSquared: Float
    get() = dot(this,this)

/** Whether all components are zero. */
inline val Vector3.isZero: Boolean
    get() = x.isZero() && y.isZero() && z.isZero()
/** Whether this vector is a unit length vector. */
inline val Vector3.isUnit: Boolean
    get() = magnitudeSquared.isOne()
/** Whether all components are 1. */
inline val Vector3.isOne: Boolean
    get() = x.isOne() && y.isOne() && z.isOne()
/** Whether all components are 1. */
inline val Vector3.isValid: Boolean
    get() = !x.isNaN() && x.isFinite() &&
            !y.isNaN() && y.isFinite() &&
            !z.isNaN() && z.isFinite()

inline val Vector3.normalized: Vector3
    get() = normalize(this)

inline operator fun Vector3.unaryPlus() = this
inline operator fun Vector3.unaryMinus() = Vector3(-x, -y, -z)
inline operator fun Vector3.times(scalar: Float) = scale(this, scalar)
inline operator fun Float.times(vector: Vector3) = vector.times(this)
inline operator fun Vector3.div(scalar: Float) = times(1 / scalar)
inline operator fun Vector3.plus(other: Vector2) = add(this, other)
inline operator fun Vector3.plus(other: Vector3) = add(this, other)
inline operator fun Vector3.plus(other: Vector4) = add(this, other)
inline operator fun Vector3.minus(other: Vector2) = subtract(this, other)
inline operator fun Vector3.minus(other: Vector3) = subtract(this, other)
inline operator fun Vector3.minus(other: Vector4) = subtract(this, other)

inline operator fun Vector3.plus(scalar: Float) = add(this, scalar)
inline operator fun Vector3.minus(scalar: Float) = add(this, -scalar)

/**
 * A four-dimensional vector.
 *
 * The purpose of the Vector classes is to represent directions. To represent positions, use Points instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @property[w] The w component.
 * @constructor Creates a four-dimensional vector pointing towards ([x], [y], [z], [w]).
 */
data class Vector4(val x: Float, val y: Float, val z: Float, val w: Float) {
    constructor() : this(0f, 0f, 0f, 0f)
    constructor(v: Float) : this(v, v, v, v)
    constructor(other: Vector2) : this(other.x, other.y, 0f, 0f)
    constructor(a: Vector2, b: Vector2) : this(a.x, b.y, b.x, b.y)
    constructor(other: Vector2, z: Float, w: Float) : this(other.x, other.y, z, w)
    constructor(x: Float, other: Vector2, w: Float) : this(x, other.x, other.y, w)
    constructor(x: Float, y: Float, other: Vector2) : this(x, y, other.x, other.y)
    constructor(other: Vector3) : this(other.x, other.y, other.z, 0f)
    constructor(other: Vector3, w: Float) : this(other.x, other.y, other.z, w)
    constructor(x: Float, other: Vector3) : this(x, other.x, other.y, other.z)
    constructor(other: Vector4) : this(other.x, other.y, other.z, other.w)
    constructor(point: Point2D) : this(point.x, point.y, 0f, 0f)
    constructor(a: Point2D, b: Point2D) : this(a.x, b.y, b.x, b.y)
    constructor(point: Point2D, z: Float, w: Float) : this(point.x, point.y, z, w)
    constructor(x: Float, point: Point2D, w: Float) : this(x, point.x, point.y, w)
    constructor(x: Float, y: Float, point: Point2D) : this(x, y, point.x, point.y)
    constructor(point: Point3D) : this(point.x, point.y, point.z, 0f)
    constructor(point: Point3D, w: Float) : this(point.x, point.y, point.z, w)
    constructor(x: Float, point: Point3D) : this(x, point.x, point.y, point.z)

    internal val components by lazy { floatArrayOf(x, y, z, w) }

    inline operator fun get(index: Int) = when(index) {
        0 -> x
        1 -> y
        2 -> z
        3 -> w
        else -> 0f
    }

    inline operator fun invoke(index: Int) = get(index - 1)

    override fun toString() = "($x, $y, $z, $w)"

    companion object {
        val zero = Vector4()
        val one = Vector4(1f, 1f, 1f, 1f)
        val x = Vector4(1f, 0f, 0f, 0f)
        val y = Vector4(0f, 1f, 0f, 0f)
        val z = Vector4(0f, 0f, 1f, 0f)
        val w = Vector4(0f, 0f, 1f, 1f)
    }
}

inline val Vector4.magnitude: Float
    get() = sqrt(magnitudeSquared)
inline val Vector4.magnitudeSquared: Float
    get() = dot(this,this)

/** Whether all components are zero. */
inline val Vector4.isZero: Boolean
    get() = x.isZero() && y.isZero() && z.isZero() && w.isZero()
/** Whether this vector is a unit length vector. */
inline val Vector4.isUnit: Boolean
    get() = magnitudeSquared.isOne()
/** Whether all components are 1. */
inline val Vector4.isOne: Boolean
    get() = x.isOne() && y.isOne() && z.isOne() && w.isOne()
/** Whether all components are 1. */
inline val Vector4.isValid: Boolean
    get() = !x.isNaN() && x.isFinite() &&
            !y.isNaN() && y.isFinite() &&
            !z.isNaN() && z.isFinite() &&
            !w.isNaN() && w.isFinite()

inline val Vector4.normalized: Vector4
    get() = normalize(this)

inline operator fun Vector4.unaryPlus() = this
inline operator fun Vector4.unaryMinus() = Vector4(-x, -y, -z, -w)
inline operator fun Vector4.times(scalar: Float) = scale(this, scalar)
inline operator fun Float.times(vector: Vector4) = vector.times(this)
inline operator fun Vector4.div(scalar: Float) = times(1 / scalar)
inline operator fun Vector4.plus(other: Vector2) = add(this, other)
inline operator fun Vector4.plus(other: Vector3) = add(this, other)
inline operator fun Vector4.plus(other: Vector4) = add(this, other)
inline operator fun Vector4.minus(other: Vector2) = subtract(this, other)
inline operator fun Vector4.minus(other: Vector3) = subtract(this, other)
inline operator fun Vector4.minus(other: Vector4) = subtract(this, other)

inline operator fun Vector4.plus(scalar: Float) = add(this, scalar)
inline operator fun Vector4.minus(scalar: Float) = add(this, -scalar)

// ---------------------------------------------------------------------------------------------------------------------

/** Returns a new vector with the absolute value applied to each component of [vector]. */
inline fun abs(vector: Vector2) = Vector2(abs(vector.x), abs(vector.y))

/** Returns a new vector with the absolute value applied to each component of [vector]. */
inline fun abs(vector: Vector3) = Vector3(abs(vector.x), abs(vector.y), abs(vector.z))

/** Returns a new vector with the absolute value applied to each component of [vector]. */
inline fun abs(vector: Vector4) = Vector4(abs(vector.x), abs(vector.y), abs(vector.z), abs(vector.w))

/** Scales [vector] (i.e., multiplies each component with [scalar]). */
inline fun scale(vector: Vector2, scalar: Float) = Vector2(vector.x * scalar, vector.y * scalar)

/** Scales [vector] (i.e., multiplies each component with [scalar]). */
inline fun scale(vector: Vector3, scalar: Float) = Vector3(vector.x * scalar, vector.y * scalar, vector.z * scalar)

/** Scales [vector] (i.e., multiplies each component with [scalar]). */
inline fun scale(vector: Vector4, scalar: Float) = Vector4(vector.x * scalar, vector.y * scalar, vector.z * scalar, vector.w * scalar)

/**
 * Returns the normalized vector.
 * If [vector] is the zero vector, the result is the zero vector.
 */
inline fun normalize(vector: Vector2) = if(vector.isZero) vector else vector / vector.magnitude

/**
 * Returns the normalized vector.
 * If [vector] is the zero vector, the result is the zero vector.
 */
inline fun normalize(vector: Vector3) = if(vector.isZero) vector else vector / vector.magnitude

/**
 * Returns the normalized vector.
 * If [vector] is the zero vector, the result is the zero vector.
 */
inline fun normalize(vector: Vector4) = if(vector.isZero) vector else vector / vector.magnitude

// ---------------------------------------------------------------------------------------------------------------------

/** Returns the dot product between [a] and [b]. */
inline fun dot(a: Vector2, b: Vector2) = a.x * b.x + a.y * b.y

/** Returns the dot product between [a] and [b]. */
inline fun dot(a: Vector2, b: Vector3) = a.x * b.x + a.y * b.y

/** Returns the dot product between [a] and [b]. */
inline fun dot(a: Vector2, b: Vector4) = a.x * b.x + a.y * b.y

/** Returns the dot product between [a] and [b]. */
inline fun dot(a: Vector3, b: Vector2) = a.x * b.x + a.y * b.y

/** Returns the dot product between [a] and [b]. */
inline fun dot(a: Vector3, b: Vector3) = a.x * b.x + a.y * b.y + a.z * b.z

/** Returns the dot product between [a] and [b]. */
inline fun dot(a: Vector3, b: Vector4) = a.x * b.x + a.y * b.y + a.z * b.z

/** Returns the dot product between [a] and [b]. */
inline fun dot(a: Vector4, b: Vector2) = a.x * b.x + a.y * b.y

/** Returns the dot product between [a] and [b]. */
inline fun dot(a: Vector4, b: Vector3) = a.x * b.x + a.y * b.y + a.z * b.z

/** Returns the dot product between [a] and [b]. */
inline fun dot(a: Vector4, b: Vector4) = a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w

/** Returns the min angle in degrees between [a] and [b]. */
inline fun angle(a: Vector2, b: Vector2) : Float {
    val cos = dot(a, b) / (a.magnitude * b.magnitude)
    val acos = acos(cos)

    return acos * RADIANS_TO_DEGREES
}

/** Returns the min angle in degrees between [a] and [b]. */
inline fun angle(a: Vector2, b: Vector3) : Float {
    val cos = dot(a, b) / (a.magnitude * b.magnitude)
    val acos = acos(cos)

    return acos * RADIANS_TO_DEGREES
}

/** Returns the min angle in degrees between [a] and [b]. */
inline fun angle(a: Vector2, b: Vector4) : Float {
    val cos = dot(a, b) / (a.magnitude * b.magnitude)
    val acos = acos(cos)

    return acos * RADIANS_TO_DEGREES
}

/** Returns the min angle in degrees between [a] and [b]. */
inline fun angle(a: Vector3, b: Vector2) : Float {
    val cos = dot(a, b) / (a.magnitude * b.magnitude)
    val acos = acos(cos)

    return acos * RADIANS_TO_DEGREES
}

/** Returns the min angle in degrees between [a] and [b]. */
inline fun angle(a: Vector3, b: Vector3) : Float {
    val cos = dot(a, b) / (a.magnitude * b.magnitude)
    val acos = acos(cos)

    return acos * RADIANS_TO_DEGREES
}

/** Returns the min angle in degrees between [a] and [b]. */
inline fun angle(a: Vector3, b: Vector4) : Float {
    val cos = dot(a, b) / (a.magnitude * b.magnitude)
    val acos = acos(cos)

    return acos * RADIANS_TO_DEGREES
}

/** Returns the min angle in degrees between [a] and [b]. */
inline fun angle(a: Vector4, b: Vector2) : Float {
    val cos = dot(a, b) / (a.magnitude * b.magnitude)
    val acos = acos(cos)

    return acos * RADIANS_TO_DEGREES
}

/** Returns the min angle in degrees between [a] and [b]. */
inline fun angle(a: Vector4, b: Vector3) : Float {
    val cos = dot(a, b) / (a.magnitude * b.magnitude)
    val acos = acos(cos)

    return acos * RADIANS_TO_DEGREES
}

/** Returns the min angle in degrees between [a] and [b]. */
inline fun angle(a: Vector4, b: Vector4) : Float {
    val cos = dot(a, b) / (a.magnitude * b.magnitude)
    val acos = acos(cos)

    return acos * RADIANS_TO_DEGREES
}

/** Whether [a] is orthogonal to [b]. */
inline fun orthogonal(a: Vector2, b: Vector2) = dot(a, b).isZero() || angle(a, b).isEqualTo(90f)

/** Whether [a] is orthogonal to [b]. */
inline fun orthogonal(a: Vector2, b: Vector3) = dot(a, b).isZero() || angle(a, b).isEqualTo(90f)

/** Whether [a] is orthogonal to [b]. */
inline fun orthogonal(a: Vector2, b: Vector4) = dot(a, b).isZero() || angle(a, b).isEqualTo(90f)

/** Whether [a] is orthogonal to [b] and both vectors are unit vectors. */
inline fun orthonormal(a: Vector2, b: Vector2) = a.isUnit && b.isUnit && orthogonal(a, b)

/** Whether [a] is orthogonal to [b] and both vectors are unit vectors. */
inline fun orthonormal(a: Vector2, b: Vector3) = a.isUnit && b.isUnit && orthogonal(a, b)

/** Whether [a] is orthogonal to [b] and both vectors are unit vectors. */
inline fun orthonormal(a: Vector2, b: Vector4) = a.isUnit && b.isUnit && orthogonal(a, b)

/** Whether [a] is orthogonal to [b]. */
inline fun orthogonal(a: Vector3, b: Vector2) = dot(a, b).isZero() || angle(a, b).isEqualTo(90f)

/** Whether [a] is orthogonal to [b]. */
inline fun orthogonal(a: Vector3, b: Vector3) = dot(a, b).isZero() || angle(a, b).isEqualTo(90f)

/** Whether [a] is orthogonal to [b]. */
inline fun orthogonal(a: Vector3, b: Vector4) = dot(a, b).isZero() || angle(a, b).isEqualTo(90f)

/** Whether [a] is orthogonal to [b] and both vectors are unit vectors. */
inline fun orthonormal(a: Vector3, b: Vector2) = a.isUnit && b.isUnit && orthogonal(a, b)

/** Whether [a] is orthogonal to [b] and both vectors are unit vectors. */
inline fun orthonormal(a: Vector3, b: Vector3) = a.isUnit && b.isUnit && orthogonal(a, b)

/** Whether [a] is orthogonal to [b] and both vectors are unit vectors. */
inline fun orthonormal(a: Vector3, b: Vector4) = a.isUnit && b.isUnit && orthogonal(a, b)

/** Whether [a] is orthogonal to [b]. */
inline fun orthogonal(a: Vector4, b: Vector2) = dot(a, b).isZero() || angle(a, b).isEqualTo(90f)

/** Whether [a] is orthogonal to [b]. */
inline fun orthogonal(a: Vector4, b: Vector3) = dot(a, b).isZero() || angle(a, b).isEqualTo(90f)

/** Whether [a] is orthogonal to [b]. */
inline fun orthogonal(a: Vector4, b: Vector4) = dot(a, b).isZero() || angle(a, b).isEqualTo(90f)

/** Whether [a] is orthogonal to [b] and both vectors are unit vectors. */
inline fun orthonormal(a: Vector4, b: Vector2) = a.isUnit && b.isUnit && orthogonal(a, b)

/** Whether [a] is orthogonal to [b] and both vectors are unit vectors. */
inline fun orthonormal(a: Vector4, b: Vector3) = a.isUnit && b.isUnit && orthogonal(a, b)

/** Whether [a] is orthogonal to [b] and both vectors are unit vectors. */
inline fun orthonormal(a: Vector4, b: Vector4) = a.isUnit && b.isUnit && orthogonal(a, b)

/** Whether [a] is parallel to [b]. */
fun parallel(a: Vector2, b: Vector2) = a.x / b.x isEqualTo a.y / b.y

/** Whether [a] is parallel to [b]. */
fun parallel(a: Vector2, b: Vector3) = b.z.isZero() && a.x / b.x isEqualTo a.y / b.y

/** Whether [a] is parallel to [b]. */
fun parallel(a: Vector2, b: Vector4) = b.z.isZero() && b.w.isZero() && a.x / b.x isEqualTo a.y / b.y

/** Whether [a] is parallel to [b]. */
fun parallel(a: Vector3, b: Vector2) = a.z.isZero() && a.x / b.x isEqualTo a.y / b.y

/** Whether [a] is parallel to [b]. */
fun parallel(a: Vector3, b: Vector3): Boolean {
    val ref = a.x / b.x
    return a.y / b.y isEqualTo ref && a.z / b.z isEqualTo ref
}

/** Whether [a] is parallel to [b]. */
fun parallel(a: Vector3, b: Vector4): Boolean {
    if(!b.w.isZero()) {
        return false
    }

    val ref = a.x / b.x
    return a.y / b.y isEqualTo ref && a.z / b.z isEqualTo ref
}

/** Whether [a] is parallel to [b]. */
fun parallel(a: Vector4, b: Vector2) = a.z.isZero() && a.w.isZero() && a.x / b.x isEqualTo a.y / b.y

/** Whether [a] is parallel to [b]. */
fun parallel(a: Vector4, b: Vector3): Boolean {
    if(!a.w.isZero()) {
        return false
    }

    val ref = a.x / b.x
    return a.y / b.y isEqualTo ref && a.z / b.z isEqualTo ref
}

/** Whether [a] is parallel to [b]. */
fun parallel(a: Vector4, b: Vector4): Boolean {
    val ref = a.x / b.x
    return a.y / b.y isEqualTo ref && a.z / b.z isEqualTo ref && a.w / b.w isEqualTo ref
}

/** Adds [scalar] to every component of [vector]. */
inline fun add(vector: Vector2, scalar: Float) = Vector2(vector.x + scalar, vector.y + scalar)

/** Adds [a] and [b]. */
inline fun add(a: Vector2, b: Vector2) = Vector2(a.x + b.x, a.y + b.y)

/** Adds [a] and [b]. */
inline fun add(a: Vector2, b: Vector3) = Vector3(a.x + b.x, a.y + b.y, b.z)

/** Adds [a] and [b]. */
inline fun add(a: Vector2, b: Vector4) = Vector4(a.x + b.x, a.y + b.y, b.z, b.w)

/** Adds [scalar] to every component of [vector]. */
inline fun add(vector: Vector3, scalar: Float) = Vector3(vector.x + scalar, vector.y + scalar, vector.z + scalar)

/** Adds [a] and [b]. */
inline fun add(a: Vector3, b: Vector2) = Vector3(a.x + b.x, a.y + b.y, a.z)

/** Adds [a] and [b]. */
inline fun add(a: Vector3, b: Vector3) = Vector3(a.x + b.x, a.y + b.y, a.z + b.z)

/** Adds [a] and [b]. */
inline fun add(a: Vector3, b: Vector4) = Vector4(a.x + b.x, a.y + b.y, a.z + b.z, b.w)

/** Adds [scalar] to every component of [vector]. */
inline fun add(vector: Vector4, scalar: Float) = Vector4(vector.x + scalar, vector.y + scalar, vector.z + scalar, vector.w + scalar)

/** Adds [a] and [b]. */
inline fun add(a: Vector4, b: Vector2) = Vector4(a.x + b.x, a.y + b.y, a.z, a.w)

/** Adds [a] and [b]. */
inline fun add(a: Vector4, b: Vector3) = Vector4(a.x + b.x, a.y + b.y, a.z + b.z, a.w)

/** Adds [a] and [b]. */
inline fun add(a: Vector4, b: Vector4) = Vector4(a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w)

/** Subtracts [b] from [a]. */
inline fun subtract(a: Vector2, b: Vector2) = Vector2(a.x - b.x, a.y - b.y)

/** Subtracts [b] from [a]. */
inline fun subtract(a: Vector2, b: Vector3) = Vector3(a.x - b.x, a.y - b.y, -b.z)

/** Subtracts [b] from [a]. */
inline fun subtract(a: Vector2, b: Vector4) = Vector4(a.x - b.x, a.y - b.y, -b.z, -b.w)

/** Subtracts [b] from [a]. */
inline fun subtract(a: Vector3, b: Vector2) = Vector3(a.x - b.x, a.y - b.y, a.z)

/** Subtracts [b] from [a]. */
inline fun subtract(a: Vector3, b: Vector3) = Vector3(a.x - b.x, a.y - b.y, a.z - b.z)

/** Subtracts [b] from [a]. */
inline fun subtract(a: Vector3, b: Vector4) = Vector4(a.x - b.x, a.y - b.y, a.z - b.z, -b.w)

/** Subtracts [b] from [a]. */
inline fun subtract(a: Vector4, b: Vector2) = Vector4(a.x - b.x, a.y - b.y, a.z, a.w)

/** Subtracts [b] from [a]. */
inline fun subtract(a: Vector4, b: Vector3) = Vector4(a.x - b.x, a.y - b.y, a.z - b.z, a.w)

/** Subtracts [b] from [a]. */
inline fun subtract(a: Vector4, b: Vector4) = Vector4(a.x - b.x, a.y - b.y, a.z - b.z, a.w - b.w)

/** Returns the cross product between [a] and [b]. */
inline fun cross(a: Vector3, b: Vector3) = Vector3(
        a.y * b.z - a.z * b.y,
        a.z * b.x - a.x * b.z,
        a.x * b.y - a.y * b.x
)

/** Projects [a] onto [b]. */
inline fun project(a: Vector2, b: Vector2) : Vector2 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return b * scalar
}

/** Projects [a] onto [b]. */
inline fun project(a: Vector2, b: Vector3) : Vector3 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return b * scalar
}

/** Projects [a] onto [b]. */
inline fun project(a: Vector2, b: Vector4) : Vector4 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return b * scalar
}

/** Projects [a] onto [b]. */
inline fun project(a: Vector3, b: Vector2) : Vector2 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return b * scalar
}

/** Projects [a] onto [b]. */
inline fun project(a: Vector3, b: Vector3) : Vector3 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return b * scalar
}

/** Projects [a] onto [b]. */
inline fun project(a: Vector3, b: Vector4) : Vector4 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return b * scalar
}

/** Projects [a] onto [b]. */
inline fun project(a: Vector4, b: Vector2) : Vector2 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return b * scalar
}

/** Projects [a] onto [b]. */
inline fun project(a: Vector4, b: Vector3) : Vector3 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return b * scalar
}

/** Projects [a] onto [b]. */
inline fun project(a: Vector4, b: Vector4) : Vector4 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return b * scalar
}

/** Rejects [a] from [b]. */
fun reject(a: Vector2, b: Vector2) = a - project(a, b)

/** Rejects [a] from [b]. */
fun reject(a: Vector2, b: Vector3) = a - project(a, b)

/** Rejects [a] from [b]. */
fun reject(a: Vector2, b: Vector4) = a - project(a, b)

/** Rejects [a] from [b]. */
fun reject(a: Vector3, b: Vector2) = a - project(a, b)

/** Rejects [a] from [b]. */
fun reject(a: Vector3, b: Vector3) = a - project(a, b)

/** Rejects [a] from [b]. */
fun reject(a: Vector3, b: Vector4) = a - project(a, b)

/** Rejects [a] from [b]. */
fun reject(a: Vector4, b: Vector2) = a - project(a, b)

/** Rejects [a] from [b]. */
fun reject(a: Vector4, b: Vector3) = a - project(a, b)

/** Rejects [a] from [b]. */
fun reject(a: Vector4, b: Vector4) = a - project(a, b)