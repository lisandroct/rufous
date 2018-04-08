package org.rufousengine.math

import kotlin.math.sqrt

/**
 * A mutable 3x3 row-major matrix.
 *
 * @property[observer] An observer that gets notified every time this matrix changes. Can be null.
 * @constructor Creates a 3x3 matrix with the given components.
 */
class MutableMatrix3(e00: Float, e01: Float, e02: Float, e10: Float, e11: Float, e12: Float, e20: Float, e21: Float, e22: Float, observer: ((Matrix3) -> Unit)? = null) : Matrix3(e00, e01, e02, e10, e11, e12, e20, e21, e22) {
    val observer = observer

    constructor(observer: ((Matrix3) -> Unit)? = null) : this(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f, observer)
    constructor(other: Matrix3, observer: ((Matrix3) -> Unit)? = null) : this(other.components, observer)
    constructor(components: FloatArray, observer: ((Matrix3) -> Unit)? = null) : this(
            components[0], components[1], components[2],
            components[3], components[4], components[5],
            components[6], components[7], components[8],
            observer
    )
    constructor(a: Vector3, b: Vector3, c: Vector3, observer: ((Matrix3) -> Unit)? = null) : this(
            a.x, b.x, c.x,
            a.y, b.y, c.y,
            a.z, b.z, c.z,
            observer
    )

    override var e00: Float
        get() = components[0]
        set(value) {
            if(value isCloseTo components[0]) {
                return
            }

            components[0] = value

            setDirty()
        }
    override var e01: Float
        get() = components[1]
        set(value) {
            if(value isCloseTo components[1]) {
                return
            }

            components[1] = value

            setDirty()
        }
    override var e02: Float
        get() = components[2]
        set(value) {
            if(value isCloseTo components[2]) {
                return
            }

            components[2] = value

            setDirty()
        }
    override var e10: Float
        get() = components[3]
        set(value) {
            if(value isCloseTo components[3]) {
                return
            }

            components[3] = value

            setDirty()
        }
    override var e11: Float
        get() = components[4]
        set(value) {
            if(value isCloseTo components[4]) {
                return
            }

            components[4] = value

            setDirty()
        }
    override var e12: Float
        get() = components[5]
        set(value) {
            if(value isCloseTo components[5]) {
                return
            }

            components[5] = value

            setDirty()
        }
    override var e20: Float
        get() = components[6]
        set(value) {
            if(value isCloseTo components[6]) {
                return
            }

            components[6] = value

            setDirty()
        }
    override var e21: Float
        get() = components[7]
        set(value) {
            if(value isCloseTo components[7]) {
                return
            }

            components[7] = value

            setDirty()
        }
    override var e22: Float
        get() = components[8]
        set(value) {
            if(value isCloseTo components[8]) {
                return
            }

            components[8] = value

            setDirty()
        }

    private var transposeDirty = true
    private val _transpose by lazy { MutableMatrix3() }
    /** The transpose of this matrix. It creates a new lazy Matrix4 instance. */
    override val transpose : Matrix3
        get() {
            if(transposeDirty) {
                transpose(_transpose)

                transposeDirty = false
            }

            return _transpose
        }
    private var inverseDirty = true
    private val _inverse by lazy { MutableMatrix3() }
    /** The inverse of this matrix. It creates a new lazy Matrix4 instance. */
    override val inverse : Matrix3
        get() {
            if(inverseDirty) {
                inverse(_inverse)

                inverseDirty = false
            }

            return _inverse
        }

    fun set(other: Matrix3) = set(other.components)
    fun set(components: FloatArray) = set(
            components[0], components[1], components[2],
            components[3], components[4], components[5],
            components[6], components[7], components[8]
    )
    fun set(a: Vector3, b: Vector3, c: Vector3) = set(
            a.x, b.x, c.x,
            a.y, b.y, c.y,
            a.z, b.z, c.z
    )
    fun set(e00: Float, e01: Float, e02: Float, e10: Float, e11: Float, e12: Float, e20: Float, e21: Float, e22: Float) : MutableMatrix3 {
        if(equals(e00, e01, e02, e10, e11, e12, e20, e21, e22)) {
            return this
        }

        components[0] = e00
        components[1] = e01
        components[2] = e02

        components[3] = e10
        components[4] = e11
        components[5] = e12

        components[6] = e20
        components[7] = e21
        components[8] = e22

        setDirty()

        return this
    }

    operator fun set(row: Int, column: Int, value: Float) {
        val index = 3 * row + column

        if(value isCloseTo components[index]) {
            return
        }

        components[index] = value

        setDirty()
    }

    private fun setDirty() {
        transposeDirty = true
        inverseDirty = true

        observer?.invoke(this)
    }

    operator fun plusAssign(other: Matrix3) { add(other) }
    operator fun minusAssign(other: Matrix3) { subtract(other) }
    operator fun timesAssign(scalar: Float) { scale(scalar) }
    operator fun divAssign(scalar: Float) { scale(1 / scalar) }
    operator fun timesAssign(other: Matrix3) { multiply(other) }

    /**
     * Sets this matrix to the identity.
     *
     * @return This matrix for chaining.
     */
    fun identity() = set(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f)
    /**
     * Sets every component to 0.
     *
     * @return This matrix for chaining.
     */
    fun zero() = set(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

    /**
     * Transposes this matrix.
     *
     * @return This matrix for chaining.
     */
    fun transpose() = transpose(this)
    /**
     * Inverts this matrix.
     *
     * @return This matrix for chaining.
     */
    fun inverse() = inverse(this)

    /**
     * Scales this matrix (i.e., multiplies each component with [scalar]).
     *
     * @param[scalar] The scalar to multiply the matrix with.
     * @return This matrix for chaining.
     */
    fun scale(scalar: Float) = scale(scalar, this)
    /**
     * Adds [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun add(other: Matrix3) = add(other, this)
    /**
     * Subtracts [other] from this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun subtract(other: Matrix3) = subtract(other, this)

    /**
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiply(other: Matrix3) = multiply(other, this)
    /**
     * Multiplies [other] with this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiplyLeft(other: Matrix3) = multiplyLeft(other, this)

    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about the x axis.
     *
     * @param[angle] The angle in degrees.
     * @return This matrix for chaining.
     */
    fun rotateX(angle: Float) = rotateX(angle, this)
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about the y axis.
     *
     * @param[angle] The angle in degrees.
     * @return This matrix for chaining.
     */
    fun rotateY(angle: Float) = rotateY(angle, this)
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about the z axis.
     *
     * @param[angle] The angle in degrees.
     * @return This matrix for chaining.
     */
    fun rotateZ(angle: Float) = rotateZ(angle, this)
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about [axis].
     *
     * If [axis] is known to be a unit vector, [rotate] is a cheaper alternative.
     *
     * @param[angle] The angle in degrees.
     * @param[axis] The axis.
     * @return This matrix for chaining.
     */
    fun rotateSafe(angle: Float, axis: Vector3) = rotateSafe(angle, axis, this)
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [rotate] is a cheaper alternative.
     *
     * @param[angle] The angle in degrees.
     * @return This matrix for chaining.
     */
    fun rotateSafe(angle: Float, aX: Float, aY: Float, aZ: Float) = rotateSafe(angle, aX, aY, aZ, this)
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about [axis].
     *
     * [axis] must be a unit vector.
     *
     * @param[angle] The angle in degrees.
     * @param[axis] The unit-length axis.
     * @return This matrix for chaining.
     */
    fun rotate(angle: Float, axis: Vector3) = rotate(angle, axis, this)
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @param[angle] The angle in degrees.
     * @return This matrix for chaining.
     */
    fun rotate(angle: Float, aX: Float, aY: Float, aZ: Float) = rotate(angle, aX, aY, aZ, this)

    /**
     * Left multiplies this matrix with a matrix that represents a reflection through a plane perpendicular to [axis].
     *
     * If [axis] is known to be a unit vector, [reflect] is a cheaper alternative.
     *
     * @param[axis] The unit vector.
     * @return This matrix for chaining.
     */
    fun reflectSafe(axis: Vector3) = reflectSafe(axis, this)
    /**
     * Left multiplies this matrix with a matrix that represents a reflection through a plane perpendicular to ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [reflect] is a cheaper alternative.
     *
     * @return This matrix for chaining.
     */
    fun reflectSafe(aX: Float, aY: Float, aZ: Float) = reflectSafe(aX, aY, aZ, this)
    /**
     * Left multiplies this matrix with a matrix that represents a reflection through a plane perpendicular to [axis].
     *
     * [axis] must be a unit vector.
     *
     * @param[axis] The unit vector.
     * @return This matrix for chaining.
     */
    fun reflect(axis: Vector3) = reflect(axis, this)
    /**
     * Left multiplies this matrix with a matrix that represents a reflection through a plane perpendicular to ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @return This matrix for chaining.
     */
    fun reflect(aX: Float, aY: Float, aZ: Float) = reflect(aX, aY, aZ, this)

    /**
     * Left multiplies this matrix with a matrix that represents an involution through [axis].
     *
     * If [axis] is known to be a unit vector, [involute] is a cheaper alternative.
     *
     * @param[axis] The unit vector.
     * @return This matrix for chaining.
     */
    fun involuteSafe(axis: Vector3) = involuteSafe(axis, this)
    /**
     * Left multiplies this matrix with a matrix that represents an involution through ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [involute] is a cheaper alternative.
     *
     * @return This matrix for chaining.
     */
    fun involuteSafe(aX: Float, aY: Float, aZ: Float) = involuteSafe(aX, aY, aZ, this)
    /**
     * Left multiplies this matrix with a matrix that represents an involution through [axis].
     *
     * [axis] must be a unit vector.
     *
     * @param[axis] The unit vector.
     * @return This matrix for chaining.
     */
    fun involute(axis: Vector3) = involute(axis, this)
    /**
     * Left multiplies this matrix with a matrix that represents an involution through ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @return This matrix for chaining.
     */
    fun involute(aX: Float, aY: Float, aZ: Float) = involute(aX, aY, aZ, this)

    /**
     * Left multiplies this matrix with a matrix that represents a non uniform scale by [factorX], [factorY] and [factorZ].
     *
     * @return This matrix for chaining.
     */
    fun scale(factorX: Float, factorY: Float, factorZ: Float) = scale(factorX, factorY, factorZ, this)
    /**
     * Left multiplies this matrix with a matrix that represents a scale by [factor] along [axis].
     *
     * If [axis] is known to be a unit vector, [scale] is a cheaper alternative.
     *
     * @return This matrix for chaining.
     */
    fun scaleSafe(factor: Float, axis: Vector3) = scaleSafe(factor, axis, this)
    /**
     * Left multiplies this matrix with a matrix that represents a scale by [factor] along ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [scale] is a cheaper alternative.
     *
     * @return This matrix for chaining.
     */
    fun scaleSafe(factor: Float, aX: Float, aY: Float, aZ: Float) = scaleSafe(factor, aX, aY, aZ, this)
    /**
     * Left multiplies this matrix with a matrix that represents a scale by [factor] along [axis].
     *
     * [axis] must be a unit vector.
     *
     * @return This matrix for chaining.
     */
    fun scale(factor: Float, axis: Vector3) = scale(factor, axis, this)
    /**
     * Left multiplies this matrix with a matrix that represents a scale by [factor] along ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @return This matrix for chaining.
     */
    fun scale(factor: Float, aX: Float, aY: Float, aZ: Float) = scale(factor, aX, aY, aZ, this)

    /**
     * Sets this matrix as a rotation matrix.
     *
     * Performs the rotation about the x axis.
     *
     * @param[angle] The angle in degrees.
     * @return This matrix for chaining.
     */
    fun makeRotationX(angle: Float) : MutableMatrix3 {
        if(angle.isZero()) {
            return identity()
        }

        val c = cos(angle)
        val s = sin(angle)

        return set(
                1f, 0f, 0f,
                0f, c, -s,
                0f, s, c
        )
    }

    /**
     * Sets this matrix as a rotation matrix.
     *
     * Performs the rotation about the y axis.
     *
     * @param[angle] The angle in degrees.
     * @return This matrix for chaining.
     */
    fun makeRotationY(angle: Float) : MutableMatrix3 {
        if(angle.isZero()) {
            return identity()
        }

        val c = cos(angle)
        val s = sin(angle)

        return set(
                c, 0f, s,
                0f, 1f, 0f,
                -s, 0f, c
        )
    }

    /**
     * Sets this matrix as a rotation matrix.
     *
     * Performs the rotation about the z axis.
     *
     * @param[angle] The angle in degrees.
     * @return This matrix for chaining.
     */
    fun makeRotationZ(angle: Float) : MutableMatrix3 {
        if(angle.isZero()) {
            return identity()
        }

        val c = cos(angle)
        val s = sin(angle)

        return set(
                c, -s, 0f,
                s, c, 0f,
                0f, 0f, 1f
        )
    }

    /**
     * Sets this matrix as a rotation matrix through [angle] about [axis].
     *
     * If [axis] is known to be a unit vector, [makeRotation] is a cheaper alternative.
     *
     * @param[angle] The angle in degrees.
     * @param[axis] The axis.
     * @return This matrix for chaining.
     */
    fun makeRotationSafe(angle: Float, axis: Vector3) = makeRotationSafe(angle, axis.x, axis.y, axis.z)

    /**
     * Sets this matrix as a rotation matrix through [angle] about ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [makeRotation] is a cheaper alternative.
     *
     * @param[angle] The angle in degrees.
     * @return This matrix for chaining.
     */
    fun makeRotationSafe(angle: Float, aX: Float, aY: Float, aZ: Float) : MutableMatrix3 {
        if(angle.isZero()) {
            return identity()
        }

        val invMagnitude = 1f / sqrt(aX * aX + aY * aY + aZ * aZ)

        return makeRotation(angle, aX * invMagnitude, aY * invMagnitude, aZ * invMagnitude)
    }

    /**
     * Sets this matrix as a rotation matrix through [angle] about [axis].
     *
     * [axis] must be a unit vector.
     *
     * @param[angle] The angle in degrees.
     * @param[axis] The unit-length axis.
     * @return This matrix for chaining.
     */
    fun makeRotation(angle: Float, axis: Vector3) = makeRotation(angle, axis.x, axis.y, axis.z)

    /**
     * Sets this matrix as a rotation matrix through [angle] about ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @param[angle] The angle in degrees.
     * @return This matrix for chaining.
     */
    fun makeRotation(angle: Float, aX: Float, aY: Float, aZ: Float) : MutableMatrix3 {
        if(angle.isZero()) {
            return identity()
        }

        val c = cos(angle)
        val s = sin(angle)
        val d = 1f - c

        val x = aX * d
        val y = aY * d
        val z = aZ * d
        val axay = x * aY
        val axaz = x * aZ
        val ayaz = y * aZ
        val saz = s * aZ
        val say = s * aY
        val sax = s * aX

        return set(
                c + x * aX, axay - saz, axaz + say,
                axay + saz, c + y * aY, ayaz - sax,
                axaz - say, ayaz + sax, c + z * aZ
        )
    }

    /**
     * Sets this matrix as a rotation matrix representing the same rotation as [quaternion].
     *
     * @param[quaternion] The rotation quaternion.
     * @return This matrix for chaining.
     */
    fun makeRotation(quaternion: Quaternion) = quaternion.getMatrixRepresentation(this)

    /**
     * Sets this matrix as a reflection matrix through the plane perpendicular to [axis].
     *
     * If [axis] is known to be a unit vector, [makeReflection] is a cheaper alternative.
     *
     * @param[axis] The vector perpendicular to the plane through which the reflection is made.
     * @return This matrix for chaining.
     */
    fun makeReflectionSafe(axis: Vector3) = makeReflectionSafe(axis.x, axis.y, axis.z)
    /**
     * Sets this matrix as a reflection matrix through the plane perpendicular to ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [makeReflection] is a cheaper alternative.
     *
     * @return This matrix for chaining.
     */
    fun makeReflectionSafe(aX: Float, aY: Float, aZ: Float) : MutableMatrix3 {
        val invMagnitude = 1f / sqrt(aX * aX + aY * aY + aZ * aZ)

        return makeReflection(aX * invMagnitude, aY * invMagnitude, aZ * invMagnitude)
    }

    /**
     * Sets this matrix as a reflection matrix through the plane perpendicular to [axis].
     *
     * [axis] must be a unit vector.
     *
     * @param[axis] The vector perpendicular to the plane through which the reflection is made.
     * @return This matrix for chaining.
     */
    fun makeReflection(axis: Vector3) = makeReflection(axis.x, axis.y, axis.z)
    /**
     * Sets this matrix as a reflection matrix through the plane perpendicular to ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @return This matrix for chaining.
     */
    fun makeReflection(aX: Float, aY: Float, aZ: Float) : MutableMatrix3 {
        val x = aX * -2f
        val y = aY * -2f
        val z = aZ * -2f
        val axay = x * aY
        val axaz = x * aZ
        val ayaz = y * aZ

        return set(
                x * aX + 1f, axay, axaz,
                axay, y * aY + 1f, ayaz,
                axaz, ayaz, z * aZ + 1f
        )
    }

    /**
     * Sets this matrix as an involution matrix through [axis].
     *
     * If [axis] is known to be a unit vector, [makeInvolution] is a cheaper alternative.
     *
     * @param[axis] The vector perpendicular to the plane through which the reflection is made.
     * @return This matrix for chaining.
     */
    fun makeInvolutionSafe(axis: Vector3) = makeInvolutionSafe(axis.x, axis.y, axis.z)
    /**
     * Sets this matrix as an involution matrix through ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [makeInvolution] is a cheaper alternative.
     *
     * @return This matrix for chaining.
     */
    fun makeInvolutionSafe(aX: Float, aY: Float, aZ: Float) : MutableMatrix3 {
        val invMagnitude = 1f / sqrt(aX * aX + aY * aY + aZ * aZ)

        return makeInvolution(aX * invMagnitude, aY * invMagnitude, aZ * invMagnitude)
    }

    /**
     * Sets this matrix as an involution matrix through [axis].
     *
     * [axis] must be a unit vector.
     *
     * @param[axis] The vector perpendicular to the plane through which the reflection is made.
     * @return This matrix for chaining.
     */
    fun makeInvolution(axis: Vector3) = makeInvolution(axis.x, axis.y, axis.z)
    /**
     * Sets this matrix as an involution matrix through ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @return This matrix for chaining.
     */
    fun makeInvolution(aX: Float, aY: Float, aZ: Float) : MutableMatrix3 {
        val x = aX * 2f
        val y = aY * 2f
        val z = aZ * 2f
        val axay = x * aY
        val axaz = x * aZ
        val ayaz = y * aZ

        return set(
                x * aX - 1f, axay, axaz,
                axay, y * aY - 1f, ayaz,
                axaz, ayaz, z * aZ - 1f
        )
    }

    /**
     * Sets this matrix as a uniform scale matrix by [factor].
     *
     * @param[factor] The factor of the scale.
     * @return This matrix for chaining
     */
    fun makeScale(factor: Float) = set(factor, 0f, 0f, 0f, factor, 0f, 0f, 0f, factor)

    /**
     * Sets this matrix as a nonuniform scale matrix by [xFactor] along the x axis,
     * [yFactor] along the y axis and [zFactor] along the z axis.
     *
     * @return This matrix for chaining
     */
    fun makeScale(xFactor: Float, yFactor: Float, zFactor: Float) = set(xFactor, 0f, 0f, 0f, yFactor, 0f, 0f, 0f, zFactor)

    /**
     * Sets this matrix as a scale matrix by [factor] along [axis].
     *
     * If [axis] is known to be a unit vector, [makeScale] is a cheaper alternative.
     *
     * @param[factor] The factor of the scale.
     * @param[axis] The axis along the scale is made.
     * @return This matrix for chaining
     */
    fun makeScaleSafe(factor: Float, axis: Vector3) = makeScaleSafe(factor, axis.x, axis.y, axis.z)
    /**
     * Sets this matrix as a scale matrix by [factor] along ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [makeScale] is a cheaper alternative.
     *
     * @param[factor] The factor of the scale.
     * @param[axis] The axis along the scale is made.
     * @return This matrix for chaining
     */
    fun makeScaleSafe(factor: Float, aX: Float, aY: Float, aZ: Float) : MutableMatrix3 {
        val invMagnitude = 1f / sqrt(aX * aX + aY * aY + aZ * aZ)

        return makeScale(factor, aX * invMagnitude, aY * invMagnitude, aZ * invMagnitude)
    }
    /**
     * Sets this matrix as a scale matrix by [factor] along [axis].
     *
     * [axis] must be a unit vector.
     *
     * @param[factor] The factor of the scale.
     * @param[axis] The axis along the scale is made.
     * @return This matrix for chaining
     */
    fun makeScale(factor: Float, axis: Vector3) = makeScale(factor, axis.x, axis.y, axis.z)
    /**
     * Sets this matrix as a scale matrix by [factor] along ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @param[factor] The factor of the scale.
     * @return This matrix for chaining
     */
    fun makeScale(factor: Float, aX: Float, aY: Float, aZ: Float) : MutableMatrix3 {
        val factor = factor - 1f

        val x = aX * factor
        val y = aY * factor
        val z = aZ * factor
        val axay = x * aY
        val axaz = x * aZ
        val ayaz = y * aZ

        return set(
                x * aX + 1f, axay, axaz,
                axay, y * aY + 1f, ayaz,
                axaz, ayaz, z * aZ + 1f
        )
    }
}