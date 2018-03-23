package org.rufousengine.math

import kotlin.math.sqrt

/**
 * A mutable 4x4 transformation matrix.
 *
 * For a general purpose matrix, see [Matrix4] instead.
 *
 * @constructor Creates the identity matrix.
 */
class MutableTransformation : Transformation {
    val observer: ((Transformation) -> Unit)?

    override var scales: Boolean
        private set
    override var rotates: Boolean
        private set
    override var translates: Boolean
        private set

    constructor(observer: ((Transformation) -> Unit)? = null) : super() {
        this.observer = observer

        scales = false
        rotates = false
        translates = false
    }
    constructor(other: Transformation, observer: ((Transformation) -> Unit)? = null) : super(other) {
        this.observer = observer

        scales = other.scales
        rotates = other.rotates
        translates = other.translates
    }

    private var transposeDirty = true
    private val _transpose by lazy { MutableMatrix4() }
    /** The transpose of this matrix. It creates a new lazy Matrix4 instance. */
    override val transpose : Matrix4
        get() {
            if(transposeDirty) {
                transpose(_transpose)

                transposeDirty = false
            }

            return _transpose
        }
    private var inverseDirty = true
    private val _inverse by lazy { MutableTransformation() }
    /** The inverse of this matrix. It creates a new lazy Matrix4 instance. */
    override val inverse : Transformation
        get() {
            if(inverseDirty) {
                inverse(_inverse)

                inverseDirty = false
            }

            return _inverse
        }

    fun set(other: Transformation) = set(other.e00, other.e01, other.e02, other.e03, other.e10, other.e11, other.e12, other.e13, other.e20, other.e21, other.e22, other.e23)
    /** Do not set values directly unless you know what you're doing. */
    internal fun set(e00: Float, e01: Float, e02: Float, e03: Float, e10: Float, e11: Float, e12: Float, e13: Float, e20: Float, e21: Float, e22: Float, e23: Float) : MutableTransformation {
        if(equals(e00, e01, e02, e03, e10, e11, e12, e13, e20, e21, e22, e23)) {
            return this
        }

        components[0] = e00
        components[1] = e01
        components[2] = e02
        components[3] = e03

        components[4] = e10
        components[5] = e11
        components[6] = e12
        components[7] = e13

        components[8] = e20
        components[9] = e21
        components[10] = e22
        components[11] = e23

        // FIXME("scales will be true when only rotation")
        this.scales = !e00.isOne() || !e11.isOne() || !e22.isOne()
        this.rotates = !e01.isZero() || !e02.isZero() || !e10.isZero() || !e12.isZero() || !e20.isZero() || !e21.isZero()
        this.translates = !e03.isZero() || !e13.isZero() || !e23.isZero()

        transposeDirty = true
        inverseDirty = true

        observer?.invoke(this)

        return this
    }

    operator fun timesAssign(other: Transformation) { multiply(other) }

    /**
     * Sets this matrix to the identity.
     *
     * @return This matrix for chaining.
     */
    fun identity() = set(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f)

    /**
     * Inverts this matrix.
     *
     * @return This matrix for chaining.
     */
    fun inverse() = inverse(this)

    /**
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiply(other: Transformation) = multiply(other, this)
    /**
     * Multiplies [other] with this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiplyLeft(other: Transformation) = multiplyLeft(other, this)

    /**
     * Sets this matrix as a rotation matrix.
     *
     * Performs the rotation about the x axis.
     *
     * @param[angle] The angle in degrees.
     * @return This matrix for chaining.
     */
    fun makeRotationX(angle: Float) : MutableTransformation {
        val c = cos(angle)
        val s = sin(angle)

        return set(
                1f, 0f, 0f, 0f,
                0f, c, -s, 0f,
                0f, s, c, 0f
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
    fun makeRotationY(angle: Float) : MutableTransformation {
        val c = cos(angle)
        val s = sin(angle)

        return set(
                c, 0f, s, 0f,
                0f, 1f, 0f, 0f,
                -s, 0f, c, 0f
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
    fun makeRotationZ(angle: Float) : MutableTransformation {
        val c = cos(angle)
        val s = sin(angle)

        return set(
                c, -s, 0f, 0f,
                s, c, 0f, 0f,
                0f, 0f, 1f, 0f
        )
    }

    /**
     * Sets this matrix as a rotation matrix through [angle] about [axis].
     *
     * If [axis] is known to be a unit vector, [makeRotationSafe] is a cheaper alternative.
     *
     * @param[angle] The angle in degrees.
     * @param[axis] The axis.
     * @return This matrix for chaining.
     */
    fun makeRotationSafe(angle: Float, axis: Vector3) = makeRotationSafe(angle, axis.x, axis.y, axis.z)
    /**
     * Sets this matrix as a rotation matrix through [angle] about ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [makeRotationSafe] is a cheaper alternative.
     *
     * @param[angle] The angle in degrees.
     * @return This matrix for chaining.
     */
    fun makeRotationSafe(angle: Float, aX: Float, aY: Float, aZ: Float) : MutableTransformation {
        val invMagnitude = 1f / sqrt(aX * aX + aY * aY + aZ * aZ)

        return makeRotation(angle, aX * invMagnitude, aY * invMagnitude, aZ * invMagnitude)
    }

    /**
     * Sets this matrix as a rotation matrix through [angle] about [axis].
     *
     * [axis] must be a unit vector.
     *
     * @param[angle] The angle in degrees.
     * @param[axis] The axis.
     * @return This matrix for chaining.
     */
    fun makeRotation(angle: Float, axis: Vector3) = makeRotation(angle, axis.x, axis.y, axis.z)
    /**
     * Sets this matrix as a rotation matrix through [angle] about ([aX], [aY], [aZ]).
     *
     * [axis] must be a unit vector.
     *
     * @param[angle] The angle in degrees.
     * @return This matrix for chaining.
     */
    fun makeRotation(angle: Float, aX: Float, aY: Float, aZ: Float) : MutableTransformation {
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
                c + x * aX, axay - saz, axaz + say, 0f,
                axay + saz, c + y * aY, ayaz - sax, 0f,
                axaz - say, ayaz + sax, c + z * aZ, 0f
        )
    }

    /**
     * Sets this matrix as a uniform scale matrix by [factor].
     *
     * @param[factor] The factor of the scale.
     * @return This matrix for chaining
     */
    fun makeScale(factor: Float) = set(
            factor, 0f, 0f, 0f,
            0f, factor, 0f, 0f,
            0f, 0f, factor, 0f
    )

    /**
     * Sets this matrix as a nonuniform scale matrix by [xFactor] along the x axis,
     * [yFactor] along the y axis and [zFactor] along the z axis.
     *
     * @return This matrix for chaining
     */
    fun makeScale(xFactor: Float, yFactor: Float, zFactor: Float) = set(
            xFactor, 0f, 0f, 0f,
            0f, yFactor, 0f, 0f,
            0f, 0f, zFactor, 0f
    )

    /**
     * Sets this matrix as a translation matrix to [vector].
     *
     * @param[vector] The direction vector pointing to the position to translate to.
     * @return This matrix for chaining.
     */
    fun makeTranslation(vector: Vector3) = makeTranslation(vector.x, vector.y, vector.z)
    /**
     * Sets this matrix as a translation matrix to [point].
     *
     * @param[point] The point to translate to.
     * @return This matrix for chaining.
     */
    fun makeTranslation(point: Point) = makeTranslation(point.x, point.y, point.z)
    /**
     * Sets this matrix as a translation matrix to ([x], [y], [z]).
     *
     * @return This matrix for chaining.
     */
    fun makeTranslation(x: Float, y: Float, z: Float) = set(
            1f, 0f, 0f, x,
            0f, 1f, 0f, y,
            0f, 0f, 1f, z
    )
}