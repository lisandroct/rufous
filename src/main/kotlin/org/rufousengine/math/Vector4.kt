package org.rufousengine.math

import java.util.*
import kotlin.math.*

/**
 * An immutable four-dimensional vector.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @property[w] The w component.
 * @constructor Creates a four-dimensional vector pointing towards (x, y, z, w).
 */
open class Vector4(x: Float = 0f, y: Float = 0f, z: Float = 0f, w: Float = 0f) {
    companion object {
        /** Vector(0, 0, 0, 0) */
        val zero = Vector4()
        /** Vector(1, 1, 1, 1) */
        val one = Vector4(1f, 1f, 1f, 1f)
        /** Vector(1, 0, 0, 0) */
        val i = Vector4(1f, 0f, 0f, 0f)
        /** Vector(0, 1, 0, 0) */
        val j = Vector4(0f, 1f, 0f, 0f)
        /** Vector(0, 0, 1, 0) */
        val k = Vector4(0f, 0f, 1f, 0f)
        /** Vector(0, 0, 0, 1) */
        val l = Vector4(0f, 0f, 0f, 1f)
    }

    /** The components of this vector. Do not change its values directly unless you know what you're doing. */
    val components = floatArrayOf(x, y, z, w)

    constructor(other: Vector2) : this(other.x, other.y, 0f, 0f)
    constructor(other: Vector3) : this(other.x, other.y, other.z, 0f)
    constructor(other: Vector4) : this(other.components)
    constructor(components: FloatArray) : this(components[0], components[1], components[2], components[3])

    open val x: Float
        get() = components[0]
    open val y: Float
        get() = components[1]
    open val z: Float
        get() = components[2]
    open val w: Float
        get() = components[3]

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
    /** Whether [x], [y], [z] and [w] are equal to 1. */
    val isOne: Boolean
        get() = equals(one)

    /** The smallest between [x], [y], [z] and [w] components. */
    val minComponent : Float
        get() = min(x, min(y, min(z, w)))
    /** The largest between [x], [y], [z] and [w] components. */
    val maxComponent : Float
        get() = max(x, max(y, max(z, w)))
    /** 0, 1, 2 or 3 whether [x], [y], [z] or [w] is the smallest component. */
    val minDimension : Int
        get() = if(x < y) (if(x < z) (if(x < w) 0 else 3) else (if(z < w) 2 else 3)) else (if(y < z) (if(y < w) 1 else 3) else (if(z < w) 2 else 3))
    /** 0, 1, 2 or 3 whether [x], [y], [z] or [w] is the largest component. */
    val maxDimension : Int
        get() = if(x > y) (if(x > z) (if(x > w) 0 else 3) else (if(z > w) 2 else 3)) else (if(y > z) (if(y > w) 1 else 3) else (if(z > w) 2 else 3))

    operator fun get(index: Int) = components[index]

    /**
     * Creates an immutable copy of this vector.
     *
     * @return The new vector for chaining.
     */
    fun copyImmutable() = Vector4(this)
    /**
     * Creates a mutable copy of this vector.
     *
     * @return The new vector for chaining.
     */
    fun copyMutable() = MutableVector4(this)

    /**
     * Negates each component.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun negate(out: MutableVector4) = out.set(-x, -y, -z, -w)
    /**
     * Normalizes the vector.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun normalize(out: MutableVector4) = scale(1 / magnitude, out)
    /**
     * Applies the absolute value to each component.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun abs(out: MutableVector4) = out.set(abs(x), abs(y), abs(z), abs(w))
    /**
     * Scales this vector (i.e., multiplies each component with [scalar]).
     *
     * @param[scalar] The scalar to multiply the vector with.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun scale(scalar: Float, out: MutableVector4) = out.set(x * scalar, y * scalar, z * scalar, w * scalar)

    /** Whether this vector is orthogonal to [other]. */
    infix fun isOrthogonal(other: Vector4) = isOrthogonal(other.x, other.y, other.z, other.w)
    /** Whether this vector is orthogonal to ([x], [y], [z], [w]). */
    fun isOrthogonal(x: Float, y: Float, z: Float, w: Float) = dot(x, y, z, w).isZero() || getAngle(x, y, z, w).isCloseTo(90f)
    /** Whether this vector is orthogonal to [other] and both vectors are unit vectors. */
    infix fun isOrthonormal(other: Vector4) = isOrthonormal(other.x, other.y, other.z, other.w)
    /** Whether this vector is orthogonal to ([x], [y], [z], [w]) and both vectors are unit vectors. */
    fun isOrthonormal(x: Float, y: Float, z: Float, w: Float) = isUnit && (x * x + y * y + z * z + w * w).isOne() && isOrthogonal(x, y, z, w)
    /** Whether this vector is parallel to [other]. */
    infix fun isParallel(other: Vector4) = isParallel(other.x, other.y, other.z, other.w)
    /** Whether this vector is parallel to ([x], [y], [z], [w]). */
    fun isParallel(x: Float, y: Float, z: Float, w: Float) : Boolean {
        val x = this.x / x
        val y = this.y / y
        val z = this.z / z
        val w = this.w / w

        return x isCloseTo y && x isCloseTo z && x isCloseTo w
    }

    /**
     * Returns the min angle between this vector and [other].
     *
     * @param[other] The other vector.
     * @return The angle in degrees.
     */
    fun getAngle(other: Vector4) = getAngle(other.x, other.y, other.z, other.w)
    /**
     * Returns the min angle between this vector and ([x], [y], [z], [w]).
     *
     * @return The angle in degrees.
     */
    fun getAngle(x: Float, y: Float, z: Float, w: Float) : Float {
        val otherMagnitude = sqrt(x * x + y * y + z * z + w * w)
        val cos = dot(x, y, z, w) / (magnitude * otherMagnitude)
        val acos = acos(cos)

        return acos * RADIANS_TO_DEGREES
    }

    /**
     * Returns the dot product between this vector and [other].
     *
     * @param[other] The other vector.
     * @return The dot product.
     */
    infix fun dot(other: Vector4) = dot(other.x, other.y, other.z, other.w)
    /**
     * Returns the dot product between this vector and ([x], [y], [z], [w]).
     *
     * @return The dot product.
     */
    fun dot(x: Float, y: Float, z: Float, w: Float) = this.x * x + this.y * y + this.z * z + this.w * w
    /**
     * Returns the absolute value of the dot product between this vector and [other].
     *
     * @param[other] The other vector.
     * @return The dot product.
     */
    infix fun dotAbs(other: Vector4) = dotAbs(other.x, other.y, other.z, other.w)
    /**
     * Returns the absolute value of the dot product between this vector and ([x], [y], [z], [w]).
     *
     * @return The dot product.
     */
    fun dotAbs(x: Float, y: Float, z: Float, w: Float) = abs(dot(x, y, z, w))

    /**
     * Adds [other] to this vector.
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun add(other: Vector4, out: MutableVector4) = add(other.x, other.y, other.z, other.w, out)
    /**
     * Adds ([x], [y], [z], [w]) to this vector.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun add(x: Float, y: Float, z: Float, w: Float, out: MutableVector4) = out.set(this.x + x, this.y + y, this.z + z, this.w + w)
    /**
     * Subtracts [other] from this vector.
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun subtract(other: Vector4, out: MutableVector4) = subtract(other.x, other.y, other.z, other.w, out)
    /**
     * Subtracts ([x], [y], [z], [w]) from this vector.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun subtract(x: Float, y: Float, z: Float, w: Float, out: MutableVector4) = out.set(this.x - x, this.y - y, this.z - z, this.w - w)

    /**
     * Returns the vector composed of the smallest components between this vector and [other].
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun min(other: Vector4, out: MutableVector4) = min(other.x, other.y, other.z, other.w, out)
    /**
     * Returns the vector composed of the smallest components between this vector and ([x], [y], [z], [w]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun min(x: Float, y: Float, z: Float, w: Float, out: MutableVector4) = out.set(min(this.x, x), min(this.y, y), min(this.z, z), min(this.w, w))
    /**
     * Returns the vector composed of the largest components between this vector and [other].
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun max(other: Vector4, out: MutableVector4) = max(other.x, other.y, other.z, other.w, out)
    /**
     * Returns the vector composed of the largest components between this vector and ([x], [y], [z], [w]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun max(x: Float, y: Float, z: Float, w: Float, out: MutableVector4) = out.set(max(this.x, x), max(this.y, y), max(this.z, z), max(this.w, w))

    /**
     * Projects this vector onto [other].
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun projectOnto(other: Vector4, out: MutableVector4) = projectOnto(other.x, other.y, other.z, other.w, out)
    /**
     * Projects this vector onto ([x], [y], [z], [w]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun projectOnto(x: Float, y: Float, z: Float, w: Float, out: MutableVector4) : MutableVector4 {
        val scalar = this.dot(x, y, z, w) / (x * x + y * y + z * z + w * w)

        val x = x * scalar
        val y = y * scalar
        val z = z * scalar
        val w = w * scalar

        return out.set(x, y, z, w)
    }

    /**
     * Rejects this vector from [other].
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun rejectFrom(other: Vector4, out: MutableVector4) = rejectFrom(other.x, other.y, other.z, other.w, out)
    /**
     * Rejects this vector from ([x], [y], [z], [w]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun rejectFrom(x: Float, y: Float, z: Float, w: Float, out: MutableVector4) = out.set(this).subtract(projectOnto(x, y, z, w, out), out)

    /**
     * Multiplies [matrix] with this vector.
     *
     * Wrapper to [Matrix4.multiply].
     *
     * @param[matrix] The matrix.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun multiplyLeft(matrix: Matrix4, out: MutableVector4) = matrix.multiply(this, out)

    fun equals(other: Vector2) = equals(other.x, other.y, 0f, 0f)
    fun equals(other: Vector3) = equals(other.x, other.y, other.z, 0f)
    fun equals(other: Vector4) = equals(other.x, other.y, other.z, other.w)
    fun equals(x: Float, y: Float, z: Float, w: Float) = this.x isCloseTo x && this.y isCloseTo y && this.z isCloseTo z && this.w isCloseTo w

    override fun toString() = "($x, $y, $z, $w)"

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Vector4) {
            return false
        }

        return equals(other)
    }

    override fun hashCode() = Arrays.hashCode(components)
}