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
open class Quaternion(x: Float = 0f, y: Float = 0f, z: Float = 0f, w: Float = 1f) {
    companion object {
        val identity = Quaternion()
        val zero = Quaternion(0f, 0f, 0f, 0f)
    }

    /** The components of this quaternion. Do not change its values directly unless you know what you're doing. */
    val components = floatArrayOf(x, y, z, w)

    constructor(v: Vector3, s: Float) : this(v.x, v.y, v.z, s)
    constructor(other: Quaternion) : this(other.components)
    constructor(components: FloatArray) : this(components[0], components[1], components[2], components[3])

    open val x: Float
        get() = components[0]
    open val y: Float
        get() = components[1]
    open val z: Float
        get() = components[2]
    open val w: Float
        get() = components[3]

    /** The conjugate of this quaternion. It creates a new lazy Quaternion instance. */
    open val conjugate : Quaternion by lazy { conjugate(MutableQuaternion()) }
    /** The inverse of this quaternion. It creates a new lazy Quaternion instance. */
    open val inverse : Quaternion by lazy { inverse(MutableQuaternion()) }

    val magnitude: Float
        get() = sqrt(magnitudeSquared)
    val magnitudeSquared: Float
        get() = x * x + y * y + z * z + w * w

    /** Whether this quaternion is a unit quaternion. */
    val isUnit: Boolean
        get() = magnitudeSquared.isOne()
    /** Whether this quaternion is the identity quaternion. */
    val isIdentity: Boolean
        get() = equals(identity)
    /** Whether this quaternion is the zero quaternion. */
    val isZero: Boolean
        get() = equals(zero)

    operator fun get(index: Int) = components[index]

    /**
     * Creates an immutable copy of this quaternion.
     *
     * @return The new quaternion for chaining.
     */
    fun copyImmutable() = Quaternion(this)
    /**
     * Creates a mutable copy of this quaternion.
     *
     * @return The new quaternion for chaining.
     */
    fun copyMutable() = MutableQuaternion(this)

    /**
     * Normalizes this quaternion.
     *
     * @param[out] The output quaternion.
     * @return The output quaternion for chaining.
     */
    fun normalize(out: MutableQuaternion) = scale(1 / magnitude, out)
    /**
     * Conjugates this quaternion (i.e., it negates the vector part).
     *
     * @param[out] The output quaternion.
     * @return The output quaternion for chaining.
     */
    fun conjugate(out: MutableQuaternion) = out.set(-x, -y, -z, w)
    /**
     * Inverts this quaternion.
     *
     * @param[out] The output quaternion.
     * @return The output quaternion for chaining.
     */
    fun inverse(out: MutableQuaternion) = conjugate(out).scale(1 / magnitudeSquared)

    fun getVectorPart(out: MutableVector3) = out.set(x, y, z)

    /**
     * Scales this quaternion (i.e., multiplies each component with [scalar]).
     *
     * @param[scalar] The scalar to multiply the quaternion with.
     * @param[out] The output quaternion.
     * @return The output quaternion for chaining.
     */
    fun scale(scalar: Float, out: MutableQuaternion) = out.set(x * scalar, y * scalar, z * scalar, w * scalar)

    /**
     * Adds [other] to this quaternion.
     *
     * @param[other] The other quaternion.
     * @param[out] The output quaternion.
     * @return The output quaternion for chaining.
     */
    fun add(other: Quaternion, out: MutableQuaternion) = add(other.x, other.y, other.z, other.w, out)
    /**
     * Adds ([x], [y], [z], [w]) to this quaternion.
     *
     * @param[out] The output quaternion.
     * @return The output quaternion for chaining.
     */
    fun add(x: Float, y: Float, z: Float, w: Float, out: MutableQuaternion) = out.set(this.x + x, this.y + y, this.z + z, this.w + w)

    /**
     * Subtracts [other] from this quaternion.
     *
     * @param[other] The other quaternion.
     * @param[out] The output quaternion.
     * @return The output quaternion for chaining.
     */
    fun subtract(other: Quaternion, out: MutableQuaternion) = subtract(other.x, other.y, other.z, other.w, out)
    /**
     * Subtracts ([x], [y], [z], [w]) from this quaternion.
     *
     * @param[out] The output quaternion.
     * @return The output quaternion for chaining.
     */
    fun subtract(x: Float, y: Float, z: Float, w: Float, out: MutableQuaternion) = out.set(this.x - x, this.y - y, this.z - z, this.w - w)

    /**
     * Multiplies this quaternion with [other].
     *
     * @param[other] The other quaternion.
     * @param[out] The output quaternion.
     * @return The output quaternion for chaining.
     */
    fun multiply(other: Quaternion, out: MutableQuaternion) = multiply(other.x, other.y, other.z, other.w, out)
    /**
     * Multiplies this quaternion with ([x], [y], [z], [w]).
     *
     * @param[out] The output quaternion.
     * @return The output quaternion for chaining.
     */
    fun multiply(x: Float, y: Float, z: Float, w: Float, out: MutableQuaternion) = out.set(
            this.w * x + this.x * w + this.y * z - this.z * y,
            this.w * y - this.x * z + this.y * w + this.z * x,
            this.w * z + this.x * y - this.y * x + this.z * w,
            this.w * w - this.x * x - this.y * y - this.z * z
    )
    /**
     * Multiplies [other] with this quaternion.
     *
     * @param[other] The other quaternion.
     * @param[out] The output quaternion.
     * @return The output quaternion for chaining.
     */
    fun multiplyLeft(other: Quaternion, out: MutableQuaternion) = multiplyLeft(other.x, other.y, other.z, other.w, out)
    /**
     * Multiplies ([x], [y], [z], [w]) with this quaternion.
     *
     * @param[out] The output quaternion.
     * @return The output quaternion for chaining.
     */
    fun multiplyLeft(x: Float, y: Float, z: Float, w: Float, out: MutableQuaternion) = out.set(
            w * this.x + x * this.w + y * this.z - z * this.y,
            w * this.y - x * this.z + y * this.w + z * this.x,
            w * this.z + x * this.y - y * this.x + z * this.w,
            w * this.w - x * this.x - y * this.y - z * this.z
    )

    /**
     * Rotates [vector] using this quaternion.
     *
     * If this quaternion is known to be a unit quaternion, [transform] is a bit cheaper.
     *
     * @param[vector] The vector to transform.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun transformSafe(vector: Vector3, out: MutableVector3) = transformSafe(vector.x, vector.y, vector.z, out)
    /**
     * Rotates ([x], [y], [z]) using this quaternion.
     *
     * If this quaternion is known to be a unit quaternion, [transform] is a bit cheaper.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun transformSafe(x: Float, y: Float, z: Float, out: MutableVector3) : MutableVector3 {
        transform(x, y, z, out)

        return out.scale(1 / magnitudeSquared)
    }

    /**
     * Rotates [vector] using this quaternion.
     *
     * @param[vector] The vector to transform.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun transform(vector: Vector3, out: MutableVector3) = transform(vector.x, vector.y, vector.z, out)
    /**
     * Rotates ([x], [y], [z]) using this quaternion.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun transform(x: Float, y: Float, z: Float, out: MutableVector3) : MutableVector3 {
        val a = Cached.a
        val b = Cached.b

        getVectorPart(a)
        a.cross(x, y, z, b)

        val s0 = w * w - a.magnitudeSquared
        val s1 = a.dot(x, y, z) * 2f
        val s2 = w * 2f

        a.scale(s1)
        b.scale(s2)

        return out.set(x, y, z).scale(s0).add(a).add(b)
    }

    fun equals(other: Quaternion) = equals(other.x, other.y, other.z, other.w)
    fun equals(x: Float, y: Float, z: Float, w: Float) = this.x isCloseTo x && this.y isCloseTo y && this.z isCloseTo z && this.w isCloseTo w

    override fun toString() = "($x, $y, $z, $w)"

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Quaternion) {
            return false
        }

        return equals(other)
    }

    override fun hashCode() = Arrays.hashCode(components)

    private object Cached {
        val a by lazy { MutableVector3() }
        val b by lazy { MutableVector3() }
    }
}