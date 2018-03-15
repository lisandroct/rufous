package org.rufousengine.math

import java.util.*
import kotlin.math.*

/**
 * An immutable three-dimensional vector.
 *
 * The purpose of the Vector classes is to represent directions. To represent positions, use [Point] instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @constructor Creates a three-dimensional vector pointing towards (x, y, z).
 */
open class Vector3(x: Float = 0f, y: Float = 0f, z: Float = 0f) {
    companion object {
        /** Vector(0, 0, 0) */
        val zero = Vector3()
        /** Vector(1, 1, 1) */
        val one = Vector3(1f, 1f, 1f)
        /** Vector(1, 0, 0) */
        val i = Vector3(1f, 0f, 0f)
        /** Vector(0, 1, 0) */
        val j = Vector3(0f, 1f, 0f)
        /** Vector(0, 0, 1) */
        val k = Vector3(0f, 0f, 1f)

        /** Vector(1, 0, 0) */
        val right = i
        /** Vector(-1, 0, 0) */
        val left = Vector3(-1f, 0f, 0f)
        /** Vector(0, 1, 0) */
        val up = j
        /** Vector(0, -1, 0) */
        val down = Vector3(0f, -1f, 0f)
        /** Vector(0, 0, 1) */
        val forward = k
        /** Vector(0, 0, -1) */
        val backward = Vector3(0f, 0f, -1f)
    }

    /** The components of this vector. Do not change its values directly unless you know what you're doing. */
    val components = floatArrayOf(x, y, z)

    constructor(point: Point) : this(point.components)
    constructor(other: Vector2) : this(other.x, other.y, 0f)
    constructor(other: Vector3) : this(other.components)
    constructor(other: Vector4) : this(other.components)
    constructor(components: FloatArray) : this(components[0], components[1], components[2])

    open val x: Float
        get() = components[0]
    open val y: Float
        get() = components[1]
    open val z: Float
        get() = components[2]

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
    /** Whether [x], [y] and [z] are equal to 1. */
    val isOne: Boolean
        get() = equals(one)

    /** The smallest between [x], [y] and [z] components. */
    val minComponent : Float
        get() = min(x, min(y, z))
    /** The largest between [x], [y] and [z] components. */
    val maxComponent : Float
        get() = max(x, max(y, z))
    /** 0, 1 or 2 whether [x], [y] or [z] is the smallest component. */
    val minDimension : Int
        get() = if(x < y) (if(x < z) 0 else 2) else (if(y < z) 1 else 2)
    /** 0, 1 or 2 whether [x], [y] or [z] is the largest component. */
    val maxDimension : Int
        get() = if(x > y) (if(x > z) 0 else 2) else (if(y > z) 1 else 2)

    operator fun get(index: Int) = components[index]

    /**
     * Creates an immutable copy of this vector.
     *
     * @return The new vector for chaining.
     */
    fun copyImmutable() = Vector3(this)
    /**
     * Creates a mutable copy of this vector.
     *
     * @return The new vector for chaining.
     */
    fun copyMutable() = MutableVector3(this)

    /**
     * Negates each component.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun negate(out: MutableVector3) = out.set(-x, -y, -z)
    /**
     * Normalizes the vector.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun normalize(out: MutableVector3) = scale(1 / magnitude, out)
    /**
     * Applies the absolute value to each component.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun abs(out: MutableVector3) = out.set(abs(x), abs(y), abs(z))
    /**
     * Scales this vector (i.e., multiplies each component with [scalar]).
     *
     * @param[scalar] The scalar to multiply the vector with.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun scale(scalar: Float, out: MutableVector3) = out.set(x * scalar, y * scalar, z * scalar)

    /** Whether this vector is orthogonal to [other]. */
    infix fun isOrthogonal(other: Vector3) = isOrthogonal(other.x, other.y, other.z)
    /** Whether this vector is orthogonal to ([x], [y], [z]). */
    fun isOrthogonal(x: Float, y: Float, z: Float) = dot(x, y, z).isZero() || getAngle(x, y, z).isCloseTo(90f)
    /** Whether this vector is orthogonal to [other] and both vectors are unit vectors. */
    infix fun isOrthonormal(other: Vector3) = isOrthonormal(other.x, other.y, other.z)
    /** Whether this vector is orthogonal to ([x], [y], [z]) and both vectors are unit vectors. */
    fun isOrthonormal(x: Float, y: Float, z: Float) = isUnit && (x * x + y * y + z * z).isOne() && isOrthogonal(x, y, z)
    /** Whether this vector is parallel to [other]. */
    infix fun isParallel(other: Vector3) = isParallel(other.x, other.y, other.z)
    /** Whether this vector is parallel to ([x], [y], [z]). */
    fun isParallel(x: Float, y: Float, z: Float) : Boolean {
        val x = this.x / x
        val y = this.y / y
        val z = this.z / z

        return x isCloseTo y && x isCloseTo z
    }

    /**
     * Returns the min angle between this vector and [other].
     *
     * @param[other] The other vector.
     * @return The angle in degrees.
     */
    fun getAngle(other: Vector3) = getAngle(other.x, other.y, other.z)
    /**
     * Returns the min angle between this vector and ([x], [y], [z]).
     *
     * @return The angle in degrees.
     */
    fun getAngle(x: Float, y: Float, z: Float) : Float {
        val otherMagnitude = sqrt(x * x + y * y + z * z)
        val cos = dot(x, y, z) / (magnitude * otherMagnitude)
        val acos = acos(cos)

        return acos * RADIANS_TO_DEGREES
    }

    /**
     * Returns the dot product between this vector and [other].
     *
     * @param[other] The other vector.
     * @return The dot product.
     */
    infix fun dot(other: Vector3) = dot(other.x, other.y, other.z)
    /**
     * Returns the dot product between this vector and ([x], [y], [z]).
     *
     * @return The dot product.
     */
    fun dot(x: Float, y: Float, z: Float) = this.x * x + this.y * y + this.z * z
    /**
     * Returns the absolute value of the dot product between this vector and [other].
     *
     * @param[other] The other vector.
     * @return The dot product.
     */
    infix fun dotAbs(other: Vector3) = dotAbs(other.x, other.y, other.z)
    /**
     * Returns the absolute value of the dot product between this vector and ([x], [y], [z]).
     *
     * @return The dot product.
     */
    fun dotAbs(x: Float, y: Float, z: Float) = abs(dot(x, y, z))

    /**
     * Adds [other] to this vector.
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun add(other: Vector3, out: MutableVector3) = add(other.x, other.y, other.z, out)
    /**
     * Adds ([x], [y], [z]) to this vector.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun add(x: Float, y: Float, z: Float, out: MutableVector3) = out.set(this.x + x, this.y + y, this.z + z)
    /**
     * Subtracts [other] from this vector.
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun subtract(other: Vector3, out: MutableVector3) = subtract(other.x, other.y, other.z, out)
    /**
     * Subtracts ([x], [y], [z]) from this vector.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun subtract(x: Float, y: Float, z: Float, out: MutableVector3) = out.set(this.x - x, this.y - y, this.z - z)

    /**
     * Returns the vector composed of the smallest components between this vector and [other].
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun min(other: Vector3, out: MutableVector3) = min(other.x, other.y, other.z, out)
    /**
     * Returns the vector composed of the smallest components between this vector and ([x], [y], [z]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun min(x: Float, y: Float, z: Float, out: MutableVector3) = out.set(min(this.x, x), min(this.y, y), min(this.z, z))
    /**
     * Returns the vector composed of the largest components between this vector and [other].
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun max(other: Vector3, out: MutableVector3) = max(other.x, other.y, other.z, out)
    /**
     * Returns the vector composed of the largest components between this vector and ([x], [y], [z]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun max(x: Float, y: Float, z: Float, out: MutableVector3) = out.set(max(this.x, x), max(this.y, y), max(this.z, z))

    /**
     * Projects this vector onto [other].
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun projectOnto(other: Vector3, out: MutableVector3) = projectOnto(other.x, other.y, other.z, out)
    /**
     * Projects this vector onto ([x], [y], [z]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun projectOnto(x: Float, y: Float, z: Float, out: MutableVector3) : MutableVector3 {
        val scalar = this.dot(x, y, z) / (x * x + y * y + z * z)

        val x = x * scalar
        val y = y * scalar
        val z = z * scalar

        return out.set(x, y, z)
    }

    /**
     * Rejects this vector from [other].
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun rejectFrom(other: Vector3, out: MutableVector3) = rejectFrom(other.x, other.y, other.z, out)
    /**
     * Rejects this vector from ([x], [y], [z]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun rejectFrom(x: Float, y: Float, z: Float, out: MutableVector3) = out.set(this).subtract(projectOnto(x, y, z, out))

    /**
     * Cross product between this vector and [other].
     *
     * @param[other] The other vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun cross(other: Vector3, out: MutableVector3) = cross(other.x, other.y, other.z, out)
    /**
     * Cross product between this vector and ([x], [y], [z]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun cross(x: Float, y: Float, z: Float, out: MutableVector3) = out.set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
    )

    /**
     * Multiplies [matrix] with this vector.
     *
     * Wrapper to [Matrix3.multiply].
     *
     * @param[matrix] The matrix.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun multiplyLeft(matrix: Matrix3, out: MutableVector3) = matrix.multiply(this, out)
    /**
     * Multiplies [matrix] with this vector.
     *
     * Wrapper to [Matrix4.multiply].
     *
     * @param[matrix] The matrix.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun multiplyLeft(matrix: Matrix4, out: MutableVector3) = matrix.multiply(this, out)
    /**
     * Multiplies [projection] with this vector.
     *
     * Wrapper to [Projection.multiply].
     *
     * @param[projection] The projection.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun multiplyLeft(projection: Projection, out: MutableVector3) = projection.multiply(this, out)

    /**
     * Rotates this vector with [quaternion].
     *
     * Wrapper to[Quaternion.transformSafe].
     *
     * If the quaternion is known to be a unit quaternion, [transform] is a cheaper alternative.
     *
     * @param[quaternion] The rotation quaternion.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun transformSafe(quaternion: Quaternion, out: MutableVector3) = quaternion.transformSafe(this, out)
    /**
     * Rotates this vector with [quaternion].
     *
     * Wrapper to[Quaternion.transform].
     *
     * @param[quaternion] The rotation quaternion. Must be unit.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun transform(quaternion: Quaternion, out: MutableVector3) = quaternion.transform(this, out)

    fun equals(other: Vector2) = equals(other.x, other.y, 0f)
    fun equals(other: Vector3) = equals(other.x, other.y, other.z)
    fun equals(other: Vector4) = other.w.isZero() && equals(other.x, other.y, other.z)
    fun equals(x: Float, y: Float, z: Float) = this.x isCloseTo x && this.y isCloseTo y && this.z isCloseTo z

    override fun toString() = "($x, $y, $z)"

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        return when(other) {
            is Vector2 -> equals(other)
            is Vector3 -> equals(other)
            is Vector4 -> equals(other)
            else -> false
        }

        return equals(other)
    }

    override fun hashCode() = Arrays.hashCode(components)
}