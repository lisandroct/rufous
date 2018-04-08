package org.rufousengine.math

import kotlin.math.sqrt

/**
 * An immutable quaternion.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @property[w] The w component.
 * @property[observer] An observer that gets notified every time this quaternion changes. Can be null.
 * @constructor Creates a quaternion.
 */
class MutableQuaternion(x: Float = 0f, y: Float = 0f, z: Float = 0f, w: Float = 1f, observer: ((Quaternion) -> Unit)? = null) : Quaternion(x, y, z, w) {
    val observer = observer

    constructor(v: Vector3, s: Float, observer: ((Quaternion) -> Unit)? = null) : this(v.x, v.y, v.z, s, observer)
    constructor(other: Quaternion, observer: ((Quaternion) -> Unit)? = null) : this(other.components, observer)
    constructor(components: FloatArray, observer: ((Quaternion) -> Unit)? = null) : this(components[0], components[1], components[2], components[3], observer)

    override var x: Float
        get() = components[0]
        set(value) {
            if(value isCloseTo components[0]) {
                return
            }

            components[0] = value

            setDirty()
        }
    override var y: Float
        get() = components[1]
        set(value) {
            if(value isCloseTo components[1]) {
                return
            }

            components[1] = value

            setDirty()
        }
    override var z: Float
        get() = components[2]
        set(value) {
            if(value isCloseTo components[2]) {
                return
            }

            components[2] = value

            setDirty()
        }
    override var w: Float
        get() = components[3]
        set(value) {
            if(value isCloseTo components[3]) {
                return
            }

            components[3] = value

            setDirty()
        }

    private var conjugateDirty = true
    private val _conjugate by lazy { MutableQuaternion() }
    /** The conjugate of this quaternion. It creates a new lazy Quaternion instance. */
    override val conjugate : Quaternion
        get() {
            if(conjugateDirty) {
                conjugate(_conjugate)

                conjugateDirty = false
            }

            return _conjugate
        }
    private var inverseDirty = true
    private val _inverse by lazy { MutableQuaternion() }
    /** The inverse of this quaternion. It creates a new lazy Quaternion instance. */
    override val inverse : Quaternion
        get() {
            if(inverseDirty) {
                inverse(_inverse)

                inverseDirty = false
            }

            return _inverse
        }

    fun set(v: Vector3, s: Float) = set(v.x, v.y, v.z, s)
    fun set(other: Quaternion) = set(other.components)
    fun set(components: FloatArray) = set(components[0], components[1], components[2], components[3])
    fun set(x: Float = 0f, y: Float = 0f, z: Float = 0f, w: Float = 0f) : MutableQuaternion {
        if(equals(x, y, z, w)) {
            return this
        }

        components[0] = x
        components[1] = y
        components[2] = z
        components[3] = w

        setDirty()

        return this
    }

    operator fun set(index: Int, value: Float) {
        if(value isCloseTo components[index]) {
            return
        }

        components[index] = value

        setDirty()
    }

    private fun setDirty() {
        conjugateDirty = true
        inverseDirty = true

        observer?.invoke(this)
    }

    operator fun plusAssign(other: Quaternion) { add(other) }
    operator fun minusAssign(other: Quaternion) { subtract(other) }
    operator fun timesAssign(scalar: Float) { scale(scalar) }
    operator fun divAssign(scalar: Float) { scale(1 / scalar) }
    operator fun timesAssign(other: Quaternion) { multiply(other, this) }

    /**
     * Set this quaternion to represent the same rotation as [matrix].
     *
     * @param[matrix] The rotation matrix.
     * @return This quaternion for chaining.
     */
    fun setFromMatrixRepresentation(matrix: Matrix3) : Quaternion {
        val m00 = matrix.e00
        val m11 = matrix.e11
        val m22 = matrix.e22
        val sum = m00 + m11 + m22

        val x: Float
        val y: Float
        val z: Float
        val w: Float

        if(sum > 0f) {
            w = sqrt(sum + 1f) * 0.5f
            val f = 0.25f / w
            x = (matrix.e21 - matrix.e12) * f
            y = (matrix.e02 - matrix.e20) * f
            z = (matrix.e10 - matrix.e01) * f
        } else if(m00 > m11 && m00 > m22) {
            x = sqrt(m00 - m11 - m22 + 1f) * 0.5f
            val f = 0.25f / x
            y = (matrix.e10 + matrix.e01) * f
            z = (matrix.e02 + matrix.e20) * f
            w = (matrix.e21 - matrix.e12) * f
        } else if(m11 > m22) {
            y = sqrt(m11 - m00 - m22 + 1f) * 0.5f
            val f = 0.25f / y
            x = (matrix.e10 + matrix.e01) * f
            z = (matrix.e21 + matrix.e12) * f
            w = (matrix.e02 - matrix.e20) * f
        } else {
            z = sqrt(m22 - m00 - m11 + 1f) * 0.5f
            val f = 0.25f / z
            x = (matrix.e02 + matrix.e20) * f
            y = (matrix.e21 + matrix.e12) * f
            w = (matrix.e10 - matrix.e01) * f
        }

        return set(x, y, z, w)
    }
    /**
     * Set this quaternion to represent the same rotation as [matrix].
     *
     * @param[matrix] The rotation matrix.
     * @return This quaternion for chaining.
     */
    fun setFromMatrixRepresentation(matrix: Matrix4) : Quaternion {
        val m00 = matrix.e00
        val m11 = matrix.e11
        val m22 = matrix.e22
        val sum = m00 + m11 + m22

        val x: Float
        val y: Float
        val z: Float
        val w: Float

        if(sum > 0f) {
            w = sqrt(sum + 1f) * 0.5f
            val f = 0.25f / w
            x = (matrix.e21 - matrix.e12) * f
            y = (matrix.e02 - matrix.e20) * f
            z = (matrix.e10 - matrix.e01) * f
        } else if(m00 > m11 && m00 > m22) {
            x = sqrt(m00 - m11 - m22 + 1f) * 0.5f
            val f = 0.25f / x
            y = (matrix.e10 + matrix.e01) * f
            z = (matrix.e02 + matrix.e20) * f
            w = (matrix.e21 - matrix.e12) * f
        } else if(m11 > m22) {
            y = sqrt(m11 - m00 - m22 + 1f) * 0.5f
            val f = 0.25f / y
            x = (matrix.e10 + matrix.e01) * f
            z = (matrix.e21 + matrix.e12) * f
            w = (matrix.e02 - matrix.e20) * f
        } else {
            z = sqrt(m22 - m00 - m11 + 1f) * 0.5f
            val f = 0.25f / z
            x = (matrix.e02 + matrix.e20) * f
            y = (matrix.e21 + matrix.e12) * f
            w = (matrix.e10 - matrix.e01) * f
        }

        return set(x, y, z, w)
    }

    /**
     * Sets this quaternion to the identity.
     *
     * @return This quaternion for chaining.
     */
    fun identity() = set(0f, 0f, 0f, 1f)
    /**
     * Sets this quaternion to the zero quaternion.
     *
     * @return This quaternion for chaining.
     */
    fun zero() = set(0f, 0f, 0f, 1f)

    /**
     * Normalizes this quaternion.
     *
     * @return This quaternion for chaining.
     */
    fun normalize() = normalize(this)
    /**
     * Conjugates this quaternion (i.e., it negates the vector part).
     *
     * @return This quaternion for chaining.
     */
    fun conjugate() = conjugate(this)
    /**
     * Inverts this quaternion.
     *
     * @return This quaternion for chaining.
     */
    fun inverse() = inverse(this)

    /**
     * Scales this quaternion (i.e., multiplies each component with [scalar]).
     *
     * @param[scalar] The scalar to multiply the quaternion with.
     * @return This quaternion for chaining.
     */
    fun scale(scalar: Float) = scale(scalar, this)

    /**
     * Adds [other] to this quaternion.
     *
     * @param[other] The other quaternion.
     * @return This quaternion for chaining.
     */
    fun add(other: Quaternion) = add(other, this)
    /**
     * Adds ([x], [y], [z], [w]) to this quaternion.
     *
     * @return This quaternion for chaining.
     */
    fun add(x: Float, y: Float, z: Float, w: Float) = add(x, y, z, w, this)

    /**
     * Subtracts [other] from this quaternion.
     *
     * @param[other] The other quaternion.
     * @return This quaternion for chaining.
     */
    fun subtract(other: Quaternion) = subtract(other, this)
    /**
     * Subtracts ([x], [y], [z], [w]) from this quaternion.
     *
     * @return This quaternion for chaining.
     */
    fun subtract(x: Float, y: Float, z: Float, w: Float) = subtract(x, y, z, w, this)

    /**
     * Multiplies this quaternion with [other].
     *
     * @param[other] The other quaternion.
     * @return This quaternion for chaining.
     */
    fun multiply(other: Quaternion) = multiply(other, this)
    /**
     * Multiplies this quaternion with ([x], [y], [z], [w]).
     *
     * @return This quaternion for chaining.
     */
    fun multiply(x: Float, y: Float, z: Float, w: Float) = multiply(x, y, z, w, this)
    /**
     * Multiplies [other] with this quaternion.
     *
     * @param[other] The other quaternion.
     * @return This quaternion for chaining.
     */
    fun multiplyLeft(other: Quaternion) = multiplyLeft(other, this)
    /**
     * Multiplies ([x], [y], [z], [w]) with this quaternion.
     *
     * @return This quaternion for chaining.
     */
    fun multiplyLeft(x: Float, y: Float, z: Float, w: Float) = multiplyLeft(x, y, z, w, this)

    /**
     * Sets this quaternion as a rotation quaternion.
     *
     * Performs the rotation about the x axis.
     *
     * @param[angle] The angle in degrees.
     * @return This quaternion for chaining.
     */
    fun makeRotationX(angle: Float) = makeRotation(angle, 1f, 0f, 0f)

    /**
     * Sets this quaternion as a rotation quaternion.
     *
     * Performs the rotation about the y axis.
     *
     * @param[angle] The angle in degrees.
     * @return This quaternion for chaining.
     */
    fun makeRotationY(angle: Float) = makeRotation(angle, 0f, 1f, 0f)

    /**
     * Sets this quaternion as a rotation quaternion.
     *
     * Performs the rotation about the z axis.
     *
     * @param[angle] The angle in degrees.
     * @return This quaternion for chaining.
     */
    fun makeRotationZ(angle: Float) = makeRotation(angle, 0f, 0f, 1f)

    /**
     * Sets this quaternion as a rotation quaternion through [angle] about [axis].
     *
     * If [axis] is known to be a unit vector, [makeRotation] is a cheaper alternative.
     *
     * @param[angle] The angle in degrees.
     * @param[axis] The axis.
     * @return This quaternion for chaining.
     */
    fun makeRotationSafe(angle: Float, axis: Vector3) = makeRotationSafe(angle, axis.x, axis.y, axis.z)
    /**
     * Sets this quaternion as a rotation quaternion through [angle] about ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [makeRotation] is a cheaper alternative.
     *
     * @param[angle] The angle in degrees.
     * @return This quaternion for chaining.
     */
    fun makeRotationSafe(angle: Float, aX: Float, aY: Float, aZ: Float) : MutableQuaternion {
        if(angle.isZero()) {
            return identity()
        }

        val invMagnitude = 1f / sqrt(aX * aX + aY * aY + aZ * aZ)

        return makeRotation(angle, aX * invMagnitude, aY * invMagnitude, aZ * invMagnitude)
    }

    /**
     * Sets this quaternion as a rotation quaternion through [angle] about [axis].
     *
     * [axis] must be a unit vector.
     *
     * @param[angle] The angle in degrees.
     * @param[axis] The unit-length axis.
     * @return This quaternion for chaining.
     */
    fun makeRotation(angle: Float, axis: Vector3) = makeRotationSafe(angle, axis.x, axis.y, axis.z)
    /**
     * Sets this quaternion as a rotation quaternion through [angle] about ([aX], [aY], [aZ]).
     *
     * [axis] must be a unit vector.
     *
     * @param[angle] The angle in degrees.
     * @return This quaternion for chaining.
     */
    fun makeRotation(angle: Float, aX: Float, aY: Float, aZ: Float) : MutableQuaternion {
        if(angle.isZero()) {
            return identity()
        }

        val halfAngle = angle * 0.5f
        val s = sin(halfAngle)

        return set(aX * s, aY * s, aZ * s, cos(halfAngle))
    }
}