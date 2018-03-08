package org.rufousengine.math

import java.util.*

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