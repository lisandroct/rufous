package org.rufousengine.math

import java.util.*

/**
 * An immutable 4x4 transformation matrix.
 *
 * For a general purpose matrix, see [Matrix4] instead.
 *
 * @constructor Creates the identity matrix.
 */
open class Transformation {
    companion object {
        val identity by lazy { Transformation() }
    }

    /** The components of this matrix. Do not change its values directly unless you know what you're doing. */
    val components: FloatArray

    open val scales: Boolean
    open val rotates: Boolean
    open val translates: Boolean

    constructor() {
        components = floatArrayOf(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
        scales = false
        rotates = false
        translates = false
    }
    constructor(other: Transformation) {
        components = other.components.copyOf()
        scales = other.scales
        rotates = other.rotates
        translates = other.translates
    }

    val e00: Float
        get() = components[0]
    val e01: Float
        get() = components[1]
    val e02: Float
        get() = components[2]
    val e03: Float
        get() = components[3]
    val e10: Float
        get() = components[4]
    val e11: Float
        get() = components[5]
    val e12: Float
        get() = components[6]
    val e13: Float
        get() = components[7]
    val e20: Float
        get() = components[8]
    val e21: Float
        get() = components[9]
    val e22: Float
        get() = components[10]
    val e23: Float
        get() = components[11]

    /** The transpose of this matrix. It creates a new lazy Transformation instance. */
    open val transpose : Matrix4 by lazy { transpose(MutableMatrix4()) }
    /** The inverse of this matrix. It creates a new lazy Transformation instance. */
    open val inverse : Transformation by lazy { inverse(MutableTransformation()) }

    val determinant : Float
        get() = e00 * (e11 * e22 - e12 * e21) + e01 * (e12 * e20 - e10 * e22) + e02 * (e10 * e21 - e11 * e20)

    val isOrthogonal : Boolean
        get() = !scales && !translates

    val isIdentity : Boolean
        get() = !scales && !rotates && !translates
    val isZero : Boolean
        get() = false
    /** Whether the last row is (0, 0, 0, 1). */
    val isTransformation
        get() = true

    operator fun get(row: Int, column: Int) = components[4 * row + column]

    /**
     * Gets the desired row as a Vector4.
     *
     * @param[index] The index of the desired row.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow(index: Int, out: MutableVector4) : MutableVector4 {
        return when(index) {
            0 -> getRow0(out)
            1 -> getRow1(out)
            2 -> getRow2(out)
            3 -> getRow3(out)
            else -> {
                throw IndexOutOfBoundsException(if(index < 0) "$index < 0" else "$index > 3")
            }
        }
    }
    /**
     * Gets the first row as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow0(out: MutableVector4) = out.set(e00, e01, e02, e03)
    /**
     * Gets the second row as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow1(out: MutableVector4) = out.set(e10, e11, e12, e13)
    /**
     * Gets the third row as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow2(out: MutableVector4) = out.set(e20, e21, e22, e23)
    /**
     * Gets the fourth row as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow3(out: MutableVector4) = out.set(0f, 0f, 0f, 1f)

    /**
     * Gets the desired column as a Vector4.
     *
     * @param[index] The index of the desired column.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn(index: Int, out: MutableVector4) : MutableVector4 {
        return when(index) {
            0 -> getColumn0(out)
            1 -> getColumn1(out)
            2 -> getColumn2(out)
            3 -> getColumn3(out)
            else -> {
                throw IndexOutOfBoundsException(if(index < 0) "$index < 0" else "$index > 3")
            }
        }
    }
    /**
     * Gets the first column as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn0(out: MutableVector4) = out.set(e00, e10, e20, 0f)
    /**
     * Gets the second column as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn1(out: MutableVector4) = out.set(e01, e11, e21, 0f)
    /**
     * Gets the third column as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn2(out: MutableVector4) = out.set(e02, e12, e22, 0f)
    /**
     * Gets the fourth column as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn3(out: MutableVector4) = out.set(e03, e13, e23, 1f)

    /**
     * Returns the 3x3 submatrix of this matrix that excludes the [row] row and the [column] column.
     *
     * @param[row] The row to exclude.
     * @param[column] The column to exclude.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun getSubmatrix(row: Int, column: Int, out: MutableMatrix3) : MutableMatrix3 {
        for(i in 0 until 3) {
            for(j in 0 until 3) {
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
    fun copyImmutable() = Transformation(this)
    /**
     * Creates a mutable copy of this matrix.
     *
     * @return The new matrix for chaining.
     */
    fun copyMutable() = MutableTransformation(this)

    /**
     * Transposes this matrix.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun transpose(out: MutableMatrix4) = out.set(
            e00, e10, e20, 0f,
            e01, e11, e21, 0f,
            e02, e12, e22, 0f,
            e03, e13, e23, 1f
    )
    /**
     * Inverts this matrix.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun inverse(out: MutableTransformation) : MutableTransformation {
        return when {
            isIdentity -> out.set(this)
            isOrthogonal -> out.set(e00, e10, e20, 0f, e01, e11, e21, 0f, e02, e12, e22, 0f) // if M is orthogonal then inv(M) = conjugate(M)
            scales && !rotates && !translates -> out.set(1 / e00, 0f, 0f, 0f, 0f, 1 / e11, 0f, 0f, 0f, 0f, 1 / e22, 0f)
            translates && !scales && !rotates -> out.set(1f, 0f, 0f, -e03, 0f, 1f, 0f, -e13, 0f, 0f, 1f, -e23)
            translates && scales && !rotates -> {
                val a = 1 / e00
                val b = 1 / e11
                val c = 1 / e22

                out.set(
                        a, 0f, 0f, -a * e03,
                        0f, b, 0f, -b * e13,
                        0f, 0f, c, -c * e23
                )
            }
            rotates && translates && !scales -> {
                val x = -e00 * e03 - e10 * e13 - e20 * e23
                val y = -e01 * e03 - e11 * e13 - e21 * e23
                val z = -e02 * e03 - e12 * e13 - e22 * e23

                out.set(
                        e00, e10, e20, x,
                        e01, e11, e21, y,
                        e02, e12, e22, z
                )
            }
            else -> {
                val a = Cached.a
                val b = Cached.b
                val c = Cached.c
                val r0 = Cached.r0
                val r1 = Cached.r1
                val r2 = Cached.r2

                a.set(e00, e10, e20)
                b.set(e01, e11, e21)
                c.set(e02, e12, e22)

                b.cross(c, r0)
                c.cross(a, r1)
                a.cross(b, r2)

                val invDet = 1 / (r2 dot c)

                r0 *= invDet
                r1 *= invDet
                r2 *= invDet

                if(!translates) {
                    out.set(
                            r0.x, r0.y, r0.z, 0f,
                            r1.x, r1.y, r1.z, 0f,
                            r2.x, r2.y, r2.z, 0f
                    )
                } else {
                    val x = -r0.x * e03 - r0.y * e13 - r0.z * e23
                    val y = -r1.x * e03 - r1.y * e13 - r1.z * e23
                    val z = -r2.x * e03 - r2.y * e13 - r2.z * e23

                    out.set(
                            r0.x, r0.y, r0.z, x,
                            r1.x, r1.y, r1.z, y,
                            r2.x, r2.y, r2.z, z
                    )
                }
            }
        }
    }

    /**
     * Scales this matrix (i.e., multiplies each component with [scalar]).
     *
     * @param[scalar] The scalar to multiply the matrix with.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun scale(scalar: Float, out: MutableMatrix4) = out.set(
            e00 * scalar, e01 * scalar, e02 * scalar, e03 * scalar,
            e10 * scalar, e11 * scalar, e12 * scalar, e13 * scalar,
            e20 * scalar, e21 * scalar, e22 * scalar, e23 * scalar,
            0f, 0f, 0f, scalar
    )

    /**
     * Adds [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun add(other: Projection, out: MutableMatrix4) = out.set(
            e00 + other.e00, e01, e02, e03,
            e10, e11 + other.e11, e12, e13,
            e20, e21, e22 + other.e22, e23 + other.e23,
            0f, 0f, other.e32, 1f + other.e33
    )
    /**
     * Adds [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun add(other: Transformation, out: MutableMatrix4) = out.set(
            e00 + other.e00, e01 + other.e01, e02 + other.e02, e03 + other.e03,
            e10 + other.e10, e11 + other.e11, e12 + other.e12, e13 + other.e13,
            e20 + other.e20, e21 + other.e21, e22 + other.e22, e23 + other.e23,
            0f, 0f, 0f, 2f
    )
    /**
     * Adds [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun add(other: Matrix4, out: MutableMatrix4) = out.set(
            e00 + other.e00, e01 + other.e01, e02 + other.e02, e03 + other.e03,
            e10 + other.e10, e11 + other.e11, e12 + other.e12, e13 + other.e13,
            e20 + other.e20, e21 + other.e21, e22 + other.e22, e23 + other.e23,
            other.e30, other.e31, other.e32, 1f + other.e33
    )

    /**
     * Subtracts [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun subtract(other: Projection, out: MutableMatrix4) = out.set(
            e00 - other.e00, e01, e02, e03,
            e10, e11 - other.e11, e12, e13,
            e20, e21, e22 - other.e22, e23 - other.e23,
            0f, 0f, -other.e32, 1f - other.e33
    )
    /**
     * Subtracts [other] from this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun subtract(other: Transformation, out: MutableMatrix4) = out.set(
            e00 - other.e00, e01 - other.e01, e02 - other.e02, e03 - other.e03,
            e10 - other.e10, e11 - other.e11, e12 - other.e12, e13 - other.e13,
            e20 - other.e20, e21 - other.e21, e22 - other.e22, e23 - other.e23,
            0f, 0f, 0f, 0f
    )
    /**
     * Subtracts [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun subtract(other: Matrix4, out: MutableMatrix4) = out.set(
            e00 - other.e00, e01 - other.e01, e02 - other.e02, e03 - other.e03,
            e10 - other.e10, e11 - other.e11, e12 - other.e12, e13 - other.e13,
            e20 - other.e20, e21 - other.e21, e22 - other.e22, e23 - other.e23,
            -other.e30, -other.e31, -other.e32, 1f - other.e33
    )

    /**
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun multiply(other: Projection, out: MutableMatrix4) : MutableMatrix4 {
        if(isIdentity) {
            return out.set(other)
        }

        if(other.isIdentity) {
            return out.set(this)
        }

        val e00 = this.e00 * other.e00
        val e01 = this.e01 * other.e11
        val e02 = this.e02 * other.e22 + this.e03 * other.e32
        val e03 = this.e02 * other.e23 + this.e03 * other.e33

        val e10 = this.e10 * other.e00
        val e11 = this.e11 * other.e11
        val e12 = this.e12 * other.e22 + this.e13 * other.e32
        val e13 = this.e12 * other.e23 + this.e13 * other.e33

        val e20 = this.e20 * other.e00
        val e21 = this.e21 * other.e11
        val e22 = this.e22 * other.e22 + this.e23 * other.e32
        val e23 = this.e22 * other.e23 + this.e23 * other.e33

        return out.set(
                e00, e01, e02, e03,
                e10, e11, e12, e13,
                e20, e21, e22, e23,
                0f, 0f, other.e32, other.e33
        )
    }
    /**
     * Multiplies [other] with this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun multiplyLeft(other: Projection, out: MutableMatrix4) = other.multiply(this, out)

    /**
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun multiply(other: Transformation, out: MutableTransformation) : MutableTransformation {
        if(isIdentity) {
            return out.set(other)
        }

        if(other.isIdentity) {
            return out.set(this)
        }

        val e00 = this.e00 * other.e00 + this.e01 * other.e10 + this.e02 * other.e20
        val e01 = this.e00 * other.e01 + this.e01 * other.e11 + this.e02 * other.e21
        val e02 = this.e00 * other.e02 + this.e01 * other.e12 + this.e02 * other.e22
        val e03 = this.e00 * other.e03 + this.e01 * other.e13 + this.e02 * other.e23 + this.e03

        val e10 = this.e10 * other.e00 + this.e11 * other.e10 + this.e12 * other.e20
        val e11 = this.e10 * other.e01 + this.e11 * other.e11 + this.e12 * other.e21
        val e12 = this.e10 * other.e02 + this.e11 * other.e12 + this.e12 * other.e22
        val e13 = this.e10 * other.e03 + this.e11 * other.e13 + this.e12 * other.e23 + this.e13

        val e20 = this.e20 * other.e00 + this.e21 * other.e10 + this.e22 * other.e20
        val e21 = this.e20 * other.e01 + this.e21 * other.e11 + this.e22 * other.e21
        val e22 = this.e20 * other.e02 + this.e21 * other.e12 + this.e22 * other.e22
        val e23 = this.e20 * other.e03 + this.e21 * other.e13 + this.e22 * other.e23 + this.e23

        return out.set(
                e00, e01, e02, e03,
                e10, e11, e12, e13,
                e20, e21, e22, e23
        )
    }
    /**
     * Multiplies [other] with this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun multiplyLeft(other: Transformation, out: MutableTransformation) = other.multiply(this, out)

    /**
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun multiply(other: Matrix4, out: MutableMatrix4) : MutableMatrix4 {
        if(isIdentity) {
            return out.set(other)
        }

        if(other.isIdentity) {
            return out.set(this)
        }

        val e00 = this.e00 * other.e00 + this.e01 * other.e10 + this.e02 * other.e20 + this.e03 * other.e30
        val e01 = this.e00 * other.e01 + this.e01 * other.e11 + this.e02 * other.e21 + this.e03 * other.e31
        val e02 = this.e00 * other.e02 + this.e01 * other.e12 + this.e02 * other.e22 + this.e03 * other.e32
        val e03 = this.e00 * other.e03 + this.e01 * other.e13 + this.e02 * other.e23 + this.e03 * other.e33

        val e10 = this.e10 * other.e00 + this.e11 * other.e10 + this.e12 * other.e20 + this.e13 * other.e30
        val e11 = this.e10 * other.e01 + this.e11 * other.e11 + this.e12 * other.e21 + this.e13 * other.e31
        val e12 = this.e10 * other.e02 + this.e11 * other.e12 + this.e12 * other.e22 + this.e13 * other.e32
        val e13 = this.e10 * other.e03 + this.e11 * other.e13 + this.e12 * other.e23 + this.e13 * other.e33

        val e20 = this.e20 * other.e00 + this.e21 * other.e10 + this.e22 * other.e20 + this.e23 * other.e30
        val e21 = this.e20 * other.e01 + this.e21 * other.e11 + this.e22 * other.e21 + this.e23 * other.e31
        val e22 = this.e20 * other.e02 + this.e21 * other.e12 + this.e22 * other.e22 + this.e23 * other.e32
        val e23 = this.e20 * other.e03 + this.e21 * other.e13 + this.e22 * other.e23 + this.e23 * other.e33

        return out.set(
                e00, e01, e02, e03,
                e10, e11, e12, e13,
                e20, e21, e22, e23,
                other.e30, other.e31, other.e32, other.e33
        )
    }
    /**
     * Multiplies [other] with this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun multiplyLeft(other: Matrix4, out: MutableMatrix4) = other.multiply(this, out)

    /**
     * Multiplies this matrix with [vector].
     *
     * @param[vector] The vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun multiply(vector: Vector4, out: MutableVector4) = multiply(vector.x, vector.y, vector.z, vector.w, out)
    /**
     * Multiplies this matrix with ([x], [y], [z], [w]).
     *
     * @param[out] The vector.
     * @return The output vector for chaining.
     */
    fun multiply(x: Float, y: Float, z: Float, w: Float, out: MutableVector4) : MutableVector4 {
        if(isIdentity) {
            return out.set(x, y, z, w)
        }

        val nx = e00 * x + e01 * y + e02 * z + e03 * w
        val ny = e10 * x + e11 * y + e12 * z + e13 * w
        val nz = e20 * x + e21 * y + e22 * z + e23 * w

        return out.set(nx, ny, nz, w)
    }

    /**
     * Multiplies this matrix with [vector]. It assumes vector's fourth component to be 0.
     *
     * @param[vector] The vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun multiply(vector: Vector3, out: MutableVector3) = multiply(vector.x, vector.y, vector.z, out)
    /**
     * Multiplies this matrix with ([x], [y], [z], 0).
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
     * Multiplies this matrix with [point]. It assumes vector's fourth component to be 0.
     *
     * @param[point] The point.
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun multiply(point: Point, out: MutablePoint) = multiply(point.x, point.y, point.z, out)
    /**
     * Multiplies this matrix with ([x], [y], [z], 1).
     *
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun multiply(x: Float, y: Float, z: Float, out: MutablePoint) : MutablePoint {
        if(isIdentity) {
            return out.set(x, y, z)
        }

        val nx = e00 * x + e01 * y + e02 * z + e03
        val ny = e10 * x + e11 * y + e12 * z + e13
        val nz = e20 * x + e21 * y + e22 * z + e23

        return out.set(nx, ny, nz)
    }

    fun equals(other: Projection) = other.isTransformation && equals(other.e00, 0f, 0f, 0f, 0f, other.e11, 0f, 0f, 0f, 0f, other.e22, other.e23)
    fun equals(other: Transformation) = scales == other.scales && rotates == other.rotates && translates == other.translates && equals(other.e00, other.e01, other.e02, other.e03, other.e10, other.e11, other.e12, other.e13, other.e20, other.e21, other.e22, other.e23)
    fun equals(other: Matrix4) = other.isTransformation && equals(other.e00, other.e01, other.e02, other.e03, other.e10, other.e11, other.e12, other.e13, other.e20, other.e21, other.e22, other.e23)
    fun equals(e00: Float, e01: Float, e02: Float, e03: Float, e10: Float, e11: Float, e12: Float, e13: Float, e20: Float, e21: Float, e22: Float, e23: Float) : Boolean {
        return this.e00 isCloseTo e00 && this.e01 isCloseTo e01 && this.e02 isCloseTo e02 && this.e03 isCloseTo e03 &&
                this.e10 isCloseTo e10 && this.e11 isCloseTo e11 && this.e12 isCloseTo e12 && this.e13 isCloseTo e13 &&
                this.e20 isCloseTo e20 && this.e21 isCloseTo e21 && this.e22 isCloseTo e22 && this.e23 isCloseTo e23
    }

    override fun toString() = "| ($e00, $e01, $e02, $e03) | ($e10, $e11, $e12, $e13) | ($e20, $e21, $e22, $e23) | (${0f}, ${0f}, ${0f}, ${1f}) |"

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        return when(other) {
            is Projection -> equals(other)
            is Transformation -> equals(other)
            is Matrix4 -> equals(other)
            else -> false
        }
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