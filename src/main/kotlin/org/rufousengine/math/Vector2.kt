package org.rufousengine.math

import java.util.*
import kotlin.math.*

/**
 * An immutable two-dimensional vector.
 *
 * The purpose of the Vector classes is to represent directions. To represent positions, use [Point] instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @constructor Creates a two-dimensional vector pointing towards (x, y).
 */
open class Vector2(x: Float = 0f, y: Float = 0f) {
    companion object {
        /** Vector(0, 0) */
        val zero = Vector2()
        /** Vector(1, 1) */
        val one = Vector2(1f, 1f)
        /** Vector(1, 0) */
        val i = Vector2(1f, 0f)
        /** Vector(0, 1) */
        val j = Vector2(0f, 1f)

        /** Vector(1, 0) */
        val right = i
        /** Vector(-1, 0) */
        val left = Vector2(-1f, 0f)
        /** Vector(0, 1) */
        val up = j
        /** Vector(0, -1) */
        val down = Vector2(0f, -1f)
    }

    /** The components of this vector. Do not change its values directly unless you know what you're doing. */
    val components = floatArrayOf(x, y)

    constructor(other: Vector2) : this(other.components)
    constructor(other: Vector3) : this(other.components)
    constructor(other: Vector4) : this(other.components)
    constructor(components: FloatArray) : this(components[0], components[1])

    open val x: Float
        get() = components[0]
    open val y: Float
        get() = components[1]

    val magnitude: Float
        get() = sqrt(magnitudeSquared)
    val magnitudeSquared: Float
        get() = this dot this

    /** Whether this vector is a zero vector. */
    val isZero: Boolean
        get() = equals(zero)
    /** Whether this vector is a unit length vector. */
    val isUnit: Boolean
        get() = magnitudeSquared.isOne()
    /** Whether [x] and [y] are equal to 1. */
    val isOne: Boolean
        get() = equals(one)

    /** The smallest between [x] and [y] components. */
    val minComponent : Float
        get() = min(x, y)
    /** The largest between [x] and [y] components. */
    val maxComponent : Float
        get() = max(x, y)
    /** 0 or 1 whether [x] or [y] is the smallest component. */
    val minDimension : Int
        get() = if(x < y)  0 else 1
    /** 0 or 1 whether [x] or [y] is the largest component. */
    val maxDimension : Int
        get() = if(x > y)  0 else 1

    operator fun get(index: Int) = components[index]

    /**
     * Creates an immutable copy of this vector.
     *
     * @return The new vector for chaining.
     */
    fun copyImmutable() = Vector2(x, y)
    /**
     * Creates a mutable copy of this vector.
     *
     * @return The new vector for chaining.
     */
    fun copyMutable() = MutableVector2(x, y)

    /**
     * Negates each component.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun negate(out: MutableVector2) = out.set(-x, -y)
    /**
     * Normalizes the vector.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun normalize(out: MutableVector2) = scale(1 / magnitude, out)
    /**
     * Applies the absolute value to each component.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun abs(out: MutableVector2) = out.set(abs(x), abs(y))
    /**
     * Scales this vector (i.e., multiplies each component with [scalar]).
     *
     * @param[scalar] The scalar to multiply the vector with.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun scale(scalar: Float, out: MutableVector2) = out.set(x * scalar, y * scalar)

    /** Whether this vector is orthogonal to [other]. */
    infix fun isOrthogonal(other: Vector2) = isOrthogonal(other.x, other.y)
    /** Whether this vector is orthogonal to ([x], [y]). */
    fun isOrthogonal(x: Float, y: Float) = dot(x, y).isZero() || getAngle(x, y).isCloseTo(90f)
    /** Whether this vector is orthogonal to [other] and both vectors are unit vectors. */
    infix fun isOrthonormal(other: Vector2) = isOrthonormal(other.x, other.y)
    /** Whether this vector is orthogonal to ([x], [y]) and both vectors are unit vectors. */
    fun isOrthonormal(x: Float, y: Float) = isUnit && (x * x + y * y).isOne() && isOrthogonal(x, y)
    /** Whether this vector is parallel to [other]. */
    infix fun isParallel(other: Vector2) = isParallel(other.x, other.y)
    /** Whether this vector is parallel to ([x], [y]). */
    fun isParallel(x: Float, y: Float) = (this.x / x) isCloseTo (this.y / y)

    /**
     * Returns the min angle between this vector and [other].
     *
     * @param[other] The other vector.
     * @return The angle in degrees.
     */
    fun getAngle(other: Vector2) = getAngle(other.x, other.y)
    /**
     * Returns the min angle between this vector and ([x], [y]).
     *
     * @return The angle in degrees.
     */
    fun getAngle(x: Float, y: Float) : Float {
        val otherMagnitude = sqrt(x * x + y * y)
        val cos = dot(x, y) / (magnitude * otherMagnitude)
        val acos = acos(cos)

        return acos * RADIANS_TO_DEGREES
    }

    /**
     * Returns the dot product between this vector and [other].
     *
     * @param[other] The other vector.
     * @return The dot product.
     */
    infix fun dot(other: Vector2) = dot(other.x, other.y)
    /**
     * Returns the dot product between this vector and ([x], [y]).
     *
     * @return The dot product.
     */
    fun dot(x: Float, y: Float) = this.x * x + this.y * y
    /**
     * Returns the absolute value of the dot product between this vector and [other].
     *
     * @param[other] The other vector.
     * @return The dot product.
     */
    infix fun dotAbs(other: Vector2) = dotAbs(other.x, other.y)
    /**
     * Returns the absolute value of the dot product between this vector and ([x], [y]).
     *
     * @return The dot product.
     */
    fun dotAbs(x: Float, y: Float) = abs(dot(x, y))

    /**
     * Adds [other] to this vector.
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun add(other: Vector2, out: MutableVector2) = add(other.x, other.y, out)
    /**
     * Adds ([x], [y]) to this vector.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun add(x: Float, y: Float, out: MutableVector2) = out.set(this.x + x, this.y + y)
    /**
     * Subtracts [other] from this vector.
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun subtract(other: Vector2, out: MutableVector2) = subtract(other.x, other.y, out)
    /**
     * Subtracts ([x], [y]) from this vector.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun subtract(x: Float, y: Float, out: MutableVector2) = out.set(this.x - x, this.y - y)

    /**
     * Returns the vector composed of the smallest components between this vector and [other].
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun min(other: Vector2, out: MutableVector2) = min(other.x, other.y, out)
    /**
     * Returns the vector composed of the smallest components between this vector and ([x], [y]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun min(x: Float, y: Float, out: MutableVector2) = out.set(min(this.x, x), min(this.y, y))
    /**
     * Returns the vector composed of the largest components between this vector and [other].
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun max(other: Vector2, out: MutableVector2) = max(other.x, other.y, out)
    /**
     * Returns the vector composed of the largest components between this vector and ([x], [y]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun max(x: Float, y: Float, out: MutableVector2) = out.set(max(this.x, x), max(this.y, y))

    /**
     * Projects this vector onto [other].
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun projectOnto(other: Vector2, out: MutableVector2) = projectOnto(other.x, other.y, out)
    /**
     * Projects this vector onto ([x], [y]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun projectOnto(x: Float, y: Float, out: MutableVector2) : MutableVector2 {
        val scalar = this.dot(x, y) / (x * x + y * y)

        val nx = x * scalar
        val ny = y * scalar

        return out.set(nx, ny)
    }

    /**
     * Rejects this vector from [other].
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun rejectFrom(other: Vector2, out: MutableVector2) = rejectFrom(other.x, other.y, out)
    /**
     * Rejects this vector from ([x], [y]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun rejectFrom(x: Float, y: Float, out: MutableVector2) = out.set(this).subtract(projectOnto(x, y, out))

    /**
     * Multiplies matrix with this vector.
     *
     * @param[matrix] The matrix.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun multiplyLeft(matrix: Matrix2, out: MutableVector2) = matrix.multiply(this, out)

    fun equals(other: Vector2) = equals(other.x, other.y)
    fun equals(other: Vector3) = other.z.isZero() && equals(other.x, other.y)
    fun equals(other: Vector4) = other.z.isZero() && other.w.isZero() && equals(other.x, other.y)
    fun equals(x: Float, y: Float) = this.x isCloseTo x && this.y isCloseTo y

    override fun toString() = "($x, $y)"

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Vector2) {
            return false
        }

        return equals(other)
    }

    override fun hashCode() = Arrays.hashCode(components)
}