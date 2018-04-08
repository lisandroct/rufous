package org.rufousengine.math

import java.util.*
import kotlin.math.sqrt

/**
 * An immutable 3x3 row-major matrix.
 *
 * @constructor Creates a 3x3 matrix with the given components.
 */
open class Matrix3(e00: Float, e01: Float, e02: Float, e10: Float, e11: Float, e12: Float, e20: Float, e21: Float, e22: Float) {
    companion object {
        val identity = Matrix3()
        val zero = Matrix3(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    }

    /** The components of this matrix. Do not change its values directly unless you know what you're doing. */
    val components = floatArrayOf(e00, e01, e02, e10, e11, e12, e20, e21, e22)

    constructor() : this(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f)
    constructor(other: Matrix3) : this(other.components)
    constructor(components: FloatArray) : this(
            components[0], components[1], components[2],
            components[3], components[4], components[5],
            components[6], components[7], components[8]
    )
    constructor(a: Vector3, b: Vector3, c: Vector3) : this(
            a.x, b.x, c.x,
            a.y, b.y, c.y,
            a.z, b.z, c.z
    )

    open val e00: Float
        get() = components[0]
    open val e01: Float
        get() = components[1]
    open val e02: Float
        get() = components[2]
    open val e10: Float
        get() = components[3]
    open val e11: Float
        get() = components[4]
    open val e12: Float
        get() = components[5]
    open val e20: Float
        get() = components[6]
    open val e21: Float
        get() = components[7]
    open val e22: Float
        get() = components[8]

    /** The transpose of this matrix. It creates a new lazy Matrix3 instance. */
    open val transpose : Matrix3 by lazy { transpose(MutableMatrix3()) }
    /** The inverse of this matrix. It creates a new lazy Matrix3 instance. */
    open val inverse : Matrix3 by lazy { inverse(MutableMatrix3()) }

    val determinant: Float
        get() = e00 * (e11 * e22 - e12 * e21) -
                e01 * (e10 * e22 - e12 * e20) +
                e02 * (e10 * e21 - e11 * e20)

    /** Whether this matrix is orthogonal (i.e., all rows (or columns) are orthonormal between each other). */
    val isOrthogonal: Boolean
        get() {
            getRow0(Cached.a)
            getRow1(Cached.b)
            getRow2(Cached.c)

            if(!Cached.a.isUnit || !Cached.b.isUnit || !Cached.c.isUnit) {
                return false
            }

            return Cached.a isOrthogonal Cached.b && Cached.a isOrthogonal Cached.c && Cached.b isOrthogonal Cached.c
        }

    val isIdentity
        get() = equals(identity)
    val isZero
        get() = equals(zero)

    operator fun get(row: Int, column: Int) = components[3 * row + column]

    /**
     * Gets the desired row as a Vector3.
     *
     * @param[index] The index of the desired row.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow(index: Int, out: MutableVector3) : MutableVector3 {
        return when(index) {
            0 -> getRow0(out)
            1 -> getRow1(out)
            2 -> getRow2(out)
            else -> {
                throw IndexOutOfBoundsException(if(index < 0) "$index < 0" else "$index > 2")
            }
        }
    }
    /**
     * Gets the first row as a Vector3.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow0(out: MutableVector3) = out.set(e00, e01, e02)
    /**
     * Gets the second row as a Vector3.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow1(out: MutableVector3) = out.set(e10, e11, e12)
    /**
     * Gets the third row as a Vector3.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow2(out: MutableVector3) = out.set(e20, e21, e22)

    /**
     * Gets the desired column as a Vector3.
     *
     * @param[index] The index of the desired column.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn(index: Int, out: MutableVector3) : MutableVector3 {
        return when(index) {
            0 -> getColumn0(out)
            1 -> getColumn1(out)
            2 -> getColumn2(out)
            else -> {
                throw IndexOutOfBoundsException(if(index < 0) "$index < 0" else "$index > 2")
            }
        }
    }
    /**
     * Gets the first column as a Vector3.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn0(out: MutableVector3) = out.set(e00, e10, e20)
    /**
     * Gets the second column as a Vector3.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn1(out: MutableVector3) = out.set(e01, e11, e21)
    /**
     * Gets the third column as a Vector3.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn2(out: MutableVector3) = out.set(e02, e12, e22)

    /**
     * Returns the 2x2 submatrix of this matrix that excludes the [row] row and the [column] column.
     *
     * @param[row] The row to exclude.
     * @param[column] The column to exclude.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun getSubmatrix(row: Int, column: Int, out: MutableMatrix2) : MutableMatrix2 {
        for(i in 0 until 2) {
            for(j in 0 until 2) {
                val row = if(i >= row) i + 1 else i
                val column = if(j >= column) j + 1 else j
                out[i, j] = this[row, column]
            }
        }

        return out
    }

    /**
     * Creates an immutable copy of this matrix.
     *
     * @return The new matrix for chaining.
     */
    fun copyImmutable() = Matrix3(this)
    /**
     * Creates a mutable copy of this matrix.
     *
     * @return The new matrix for chaining.
     */
    fun copyMutable() = MutableMatrix3(this)

    /**
     * Transposes this matrix.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun transpose(out: MutableMatrix3) = out.set(
            e00, e10, e20,
            e01, e11, e21,
            e02, e12, e22
    )
    /**
     * Inverts this matrix.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun inverse(out: MutableMatrix3) : MutableMatrix3 {
        getColumn0(Cached.a)
        getColumn1(Cached.b)
        getColumn2(Cached.c)

        Cached.b.cross(Cached.c, Cached.r0)
        Cached.c.cross(Cached.a, Cached.r1)
        Cached.a.cross(Cached.b, Cached.r2)

        val invDet = 1 / (Cached.r2 dot Cached.c)

        Cached.r0 *= invDet
        Cached.r1 *= invDet
        Cached.r2 *= invDet

        return out.set(Cached.r0, Cached.r1, Cached.r2).transpose()
    }

    /**
     * Scales this matrix (i.e., multiplies each component with [scalar]).
     *
     * @param[scalar] The scalar to multiply the matrix with.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun scale(scalar: Float, out: MutableMatrix3) = out.set(
            e00 * scalar, e01 * scalar, e02 * scalar,
            e10 * scalar, e11 * scalar, e12 * scalar,
            e20 * scalar, e21 * scalar, e22 * scalar
    )

    /**
     * Adds [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun add(other: Matrix3, out: MutableMatrix3) = out.set(
            e00 + other.e00, e01 + other.e01, e02 + other.e02,
            e10 + other.e10, e11 + other.e11, e12 + other.e12,
            e20 + other.e20, e21 + other.e21, e22 + other.e22
    )
    /**
     * Subtracts [other] from this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun subtract(other: Matrix3, out: MutableMatrix3) = out.set(
            e00 - other.e00, e01 - other.e01, e02 - other.e02,
            e10 - other.e10, e11 - other.e11, e12 - other.e12,
            e20 - other.e20, e21 - other.e21, e22 - other.e22
    )

    /**
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun multiply(other: Matrix3, out: MutableMatrix3) : MutableMatrix3 {
        if(isIdentity) {
            return out.set(other)
        }

        if(other.isIdentity) {
            return out.set(this)
        }

        val e00 = this.e00 * other.e00 + this.e01 * other.e10 + this.e02 * other.e20
        val e01 = this.e00 * other.e01 + this.e01 * other.e11 + this.e02 * other.e21
        val e02 = this.e00 * other.e02 + this.e01 * other.e12 + this.e02 * other.e22

        val e10 = this.e10 * other.e00 + this.e11 * other.e10 + this.e12 * other.e20
        val e11 = this.e10 * other.e01 + this.e11 * other.e11 + this.e12 * other.e21
        val e12 = this.e10 * other.e02 + this.e11 * other.e12 + this.e12 * other.e22

        val e20 = this.e20 * other.e00 + this.e21 * other.e10 + this.e22 * other.e20
        val e21 = this.e20 * other.e01 + this.e21 * other.e11 + this.e22 * other.e21
        val e22 = this.e20 * other.e02 + this.e21 * other.e12 + this.e22 * other.e22

        return out.set(
                e00, e01, e02,
                e10, e11, e12,
                e20, e21, e22
        )
    }
    /**
     * Multiplies [other] with this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun multiplyLeft(other: Matrix3, out: MutableMatrix3) = other.multiply(this, out)

    /**
     * Multiplies this matrix with [vector].
     *
     * @param[vector] The vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun multiply(vector: Vector3, out: MutableVector3) = multiply(vector.x, vector.y, vector.z, out)
    /**
     * Multiplies this matrix with ([x], [y], [z]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun multiply(x: Float, y: Float, z: Float, out: MutableVector3) : MutableVector3 {
        if(isIdentity) {
            return out.set(x, y, z)
        }

        val nx = e00 * x + e01 * y + e02 * z
        val ny = e10 * x + e11 * y + e12 * z
        val nz = e20 * x + e21 * y + e22 * z

        return out.set(nx, ny, nz)
    }

    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about the x axis.
     *
     * @param[angle] The angle in degrees.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun rotateX(angle: Float, out: MutableMatrix3) : MutableMatrix3 {
        if(angle.isZero()) {
            return out.set(this)
        }

        val c = cos(angle)
        val s = sin(angle)

        val e10 = c * this.e10 + -s * this.e20
        val e11 = c * this.e11 + -s * this.e21
        val e12 = c * this.e12 + -s * this.e22

        val e20 = s * this.e10 + c * this.e20
        val e21 = s * this.e11 + c * this.e21
        val e22 = s * this.e12 + c * this.e22

        return out.set(
                this.e00, this.e01, this.e02,
                e10, e11, e12,
                e20, e21, e22
        )
    }
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about the y axis.
     *
     * @param[angle] The angle in degrees.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun rotateY(angle: Float, out: MutableMatrix3) : MutableMatrix3 {
        if(angle.isZero()) {
            return out.set(this)
        }

        val c = cos(angle)
        val s = sin(angle)

        val e00 = c * this.e00 + s * this.e20
        val e01 = c * this.e01 + s * this.e21
        val e02 = c * this.e02 + s * this.e22

        val e20 = -s * this.e00 + c * this.e20
        val e21 = -s * this.e01 + c * this.e21
        val e22 = -s * this.e02 + c * this.e22

        return out.set(
                e00, e01, e02,
                this.e10, this.e11, this.e12,
                e20, e21, e22
        )
    }
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about the z axis.
     *
     * @param[angle] The angle in degrees.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun rotateZ(angle: Float, out: MutableMatrix3) : MutableMatrix3 {
        if(angle.isZero()) {
            return out.set(this)
        }

        val c = cos(angle)
        val s = sin(angle)

        val e00 = c * this.e00 + -s * this.e10
        val e01 = c * this.e01 + -s * this.e11
        val e02 = c * this.e02 + -s * this.e12

        val e10 = s * this.e00 + c * this.e10
        val e11 = s * this.e01 + c * this.e11
        val e12 = s * this.e02 + c * this.e12

        return out.set(
                e00, e01, e02,
                e10, e11, e12,
                this.e20, this.e21, this.e22
        )
    }
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about [axis].
     *
     * If [axis] is known to be a unit vector, [rotate] is a cheaper alternative.
     *
     * @param[angle] The angle in degrees.
     * @param[axis] The axis.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun rotateSafe(angle: Float, axis: Vector3, out: MutableMatrix3) = rotateSafe(angle, axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [rotate] is a cheaper alternative.
     *
     * @param[angle] The angle in degrees.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun rotateSafe(angle: Float, aX: Float, aY: Float, aZ: Float, out: MutableMatrix3) : MutableMatrix3 {
        if(angle.isZero()) {
            return out.set(this)
        }

        val invMagnitude = 1f / sqrt(aX * aX + aY * aY + aZ * aZ)

        return rotate(angle, aX * invMagnitude, aY * invMagnitude, aZ * invMagnitude, out)
    }
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about [axis].
     *
     * [axis] must be a unit vector.
     *
     * @param[angle] The angle in degrees.
     * @param[axis] The unit-length axis.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun rotate(angle: Float, axis: Vector3, out: MutableMatrix3) = rotate(angle, axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @param[angle] The angle in degrees.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun rotate(angle: Float, aX: Float, aY: Float, aZ: Float, out: MutableMatrix3) : MutableMatrix3 {
        if(angle.isZero()) {
            return out.set(this)
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

        val r00 = c + x * aX
        val r01 = axay - saz
        val r02 = axaz + say
        val r10 = axay + saz
        val r11 = c + y * aY
        val r12 = ayaz - sax
        val r20 = axaz - say
        val r21 = ayaz + sax
        val r22 = c + z * aZ

        val e00 = r00 * this.e00 + r01 * this.e10 + r02 * this.e20
        val e01 = r00 * this.e01 + r01 * this.e11 + r02 * this.e21
        val e02 = r00 * this.e02 + r01 * this.e12 + r02 * this.e22

        val e10 = r10 * this.e00 + r11 * this.e10 + r12 * this.e20
        val e11 = r10 * this.e01 + r11 * this.e11 + r12 * this.e21
        val e12 = r10 * this.e02 + r11 * this.e12 + r12 * this.e22

        val e20 = r20 * this.e00 + r21 * this.e10 + r22 * this.e20
        val e21 = r20 * this.e01 + r21 * this.e11 + r22 * this.e21
        val e22 = r20 * this.e02 + r21 * this.e12 + r22 * this.e22

        return out.set(
                e00, e01, e02,
                e10, e11, e12,
                e20, e21, e22
        )
    }

    /**
     * Left multiplies this matrix with a matrix that represents a reflection through a plane perpendicular to [axis].
     *
     * If [axis] is known to be a unit vector, [reflect] is a cheaper alternative.
     *
     * @param[axis] The unit vector.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun reflectSafe(axis: Vector3, out: MutableMatrix3) = reflectSafe(axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents a reflection through a plane perpendicular to ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [reflect] is a cheaper alternative.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun reflectSafe(aX: Float, aY: Float, aZ: Float, out: MutableMatrix3) : MutableMatrix3 {
        val invMagnitude = 1f / sqrt(aX * aX + aY * aY + aZ * aZ)

        return reflect(aX * invMagnitude, aY * invMagnitude, aZ * invMagnitude, out)
    }
    /**
     * Left multiplies this matrix with a matrix that represents a reflection through a plane perpendicular to [axis].
     *
     * [axis] must be a unit vector.
     *
     * @param[axis] The unit vector.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun reflect(axis: Vector3, out: MutableMatrix3) = reflect(axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents a reflection through a plane perpendicular to ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun reflect(aX: Float, aY: Float, aZ: Float, out: MutableMatrix3) : MutableMatrix3 {
        val x = aX * -2f
        val y = aY * -2f
        val z = aZ * -2f
        val axay = x * aY
        val axaz = x * aZ
        val ayaz = y * aZ

        val r00 = x * aX + 1f
        val r11 = y * aY + 1f
        val r22 = z * aZ + 1f

        val e00 = r00 * this.e00 + axay * this.e10 + axaz * this.e20
        val e01 = r00 * this.e01 + axay * this.e11 + axaz * this.e21
        val e02 = r00 * this.e02 + axay * this.e12 + axaz * this.e22

        val e10 = axay * this.e00 + r11 * this.e10 + ayaz * this.e20
        val e11 = axay * this.e01 + r11 * this.e11 + ayaz * this.e21
        val e12 = axay * this.e02 + r11 * this.e12 + ayaz * this.e22

        val e20 = axaz * this.e00 + ayaz * this.e10 + r22 * this.e20
        val e21 = axaz * this.e01 + ayaz * this.e11 + r22 * this.e21
        val e22 = axaz * this.e02 + ayaz * this.e12 + r22 * this.e22

        return out.set(
                e00, e01, e02,
                e10, e11, e12,
                e20, e21, e22
        )
    }

    /**
     * Left multiplies this matrix with a matrix that represents an involution through [axis].
     *
     * If [axis] is known to be a unit vector, [involute] is a cheaper alternative.
     *
     * @param[axis] The unit vector.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun involuteSafe(axis: Vector3, out: MutableMatrix3) = involuteSafe(axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents an involution through ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [involute] is a cheaper alternative.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun involuteSafe(aX: Float, aY: Float, aZ: Float, out: MutableMatrix3) : MutableMatrix3 {
        val invMagnitude = 1f / sqrt(aX * aX + aY * aY + aZ * aZ)

        return involute(aX * invMagnitude, aY * invMagnitude, aZ * invMagnitude, out)
    }
    /**
     * Left multiplies this matrix with a matrix that represents an involution through [axis].
     *
     * [axis] must be a unit vector.
     *
     * @param[axis] The unit vector.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun involute(axis: Vector3, out: MutableMatrix3) = involute(axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents an involution through ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun involute(aX: Float, aY: Float, aZ: Float, out: MutableMatrix3) : MutableMatrix3 {
        val x = aX * 2f
        val y = aY * 2f
        val z = aZ * 2f
        val axay = x * aY
        val axaz = x * aZ
        val ayaz = y * aZ

        val r00 = x * aX - 1f
        val r11 = y * aY - 1f
        val r22 = z * aZ - 1f

        val e00 = r00 * this.e00 + axay * this.e10 + axaz * this.e20
        val e01 = r00 * this.e01 + axay * this.e11 + axaz * this.e21
        val e02 = r00 * this.e02 + axay * this.e12 + axaz * this.e22

        val e10 = axay * this.e00 + r11 * this.e10 + ayaz * this.e20
        val e11 = axay * this.e01 + r11 * this.e11 + ayaz * this.e21
        val e12 = axay * this.e02 + r11 * this.e12 + ayaz * this.e22

        val e20 = axaz * this.e00 + ayaz * this.e10 + r22 * this.e20
        val e21 = axaz * this.e01 + ayaz * this.e11 + r22 * this.e21
        val e22 = axaz * this.e02 + ayaz * this.e12 + r22 * this.e22

        return out.set(
                e00, e01, e02,
                e10, e11, e12,
                e20, e21, e22
        )
    }

    /**
     * Left multiplies this matrix with a matrix that represents a non uniform scale by [factorX], [factorY] and [factorZ].
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun scale(factorX: Float, factorY: Float, factorZ: Float, out: MutableMatrix3) : MutableMatrix3 {
        val e00 = factorX * this.e00
        val e01 = factorX * this.e01
        val e02 = factorX * this.e02

        val e10 = factorY * this.e10
        val e11 = factorY * this.e11
        val e12 = factorY * this.e12

        val e20 = factorZ * this.e20
        val e21 = factorZ * this.e21
        val e22 = factorZ * this.e22

        return out.set(
                e00, e01, e02,
                e10, e11, e12,
                e20, e21, e22
        )
    }
    /**
     * Left multiplies this matrix with a matrix that represents a scale by [factor] along [axis].
     *
     * If [axis] is known to be a unit vector, [scale] is a cheaper alternative.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun scaleSafe(factor: Float, axis: Vector3, out: MutableMatrix3) = scaleSafe(factor, axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents a scale by [factor] along ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [scale] is a cheaper alternative.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun scaleSafe(factor: Float, aX: Float, aY: Float, aZ: Float, out: MutableMatrix3) : MutableMatrix3 {
        val invMagnitude = 1f / sqrt(aX * aX + aY * aY + aZ * aZ)

        return scale(factor, aX * invMagnitude, aY * invMagnitude, aZ * invMagnitude, out)
    }
    /**
     * Left multiplies this matrix with a matrix that represents a scale by [factor] along [axis].
     *
     * [axis] must be a unit vector.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun scale(factor: Float, axis: Vector3, out: MutableMatrix3) = scale(factor, axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents a scale by [factor] along ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun scale(factor: Float, aX: Float, aY: Float, aZ: Float, out: MutableMatrix3) : MutableMatrix3 {
        val factor = factor - 1f

        val x = aX * factor
        val y = aY * factor
        val z = aZ * factor
        val axay = x * aY
        val axaz = x * aZ
        val ayaz = y * aZ

        val r00 = x * aX + 1f
        val r11 = y * aY + 1f
        val r22 = z * aZ + 1f

        val e00 = r00 * this.e00 + axay * this.e10 + axaz * this.e20
        val e01 = r00 * this.e01 + axay * this.e11 + axaz * this.e21
        val e02 = r00 * this.e02 + axay * this.e12 + axaz * this.e22

        val e10 = axay * this.e00 + r11 * this.e10 + ayaz * this.e20
        val e11 = axay * this.e01 + r11 * this.e11 + ayaz * this.e21
        val e12 = axay * this.e02 + r11 * this.e12 + ayaz * this.e22

        val e20 = axaz * this.e00 + ayaz * this.e10 + r22 * this.e20
        val e21 = axaz * this.e01 + ayaz * this.e11 + r22 * this.e21
        val e22 = axaz * this.e02 + ayaz * this.e12 + r22 * this.e22

        return out.set(
                e00, e01, e02,
                e10, e11, e12,
                e20, e21, e22
        )
    }
    
    fun equals(other: Matrix3) = equals(other.e00, other.e01, other.e02, other.e10, other.e11, other.e12, other.e20, other.e21, other.e22)
    fun equals(e00: Float, e01: Float, e02: Float, e10: Float, e11: Float, e12: Float, e20: Float, e21: Float, e22: Float) : Boolean {
        return this.e00 isCloseTo e00 && this.e01 isCloseTo e01 && this.e02 isCloseTo e02 &&
                this.e10 isCloseTo e10 && this.e11 isCloseTo e11 && this.e12 isCloseTo e12 &&
                this.e20 isCloseTo e20 && this.e21 isCloseTo e21 && this.e22 isCloseTo e22
    }

    override fun toString() = "| ($e00, $e01, $e02) | ($e10, $e11, $e12) | ($e20, $e21, $e22) |"

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Matrix3) {
            return false
        }

        return equals(other)
    }

    override fun hashCode() = Arrays.hashCode(components)

    private object Cached {
        val a by lazy { MutableVector3() }
        val b by lazy { MutableVector3() }
        val c by lazy { MutableVector3() }
        val r0 by lazy { MutableVector3() }
        val r1 by lazy { MutableVector3() }
        val r2 by lazy { MutableVector3() }
    }
}