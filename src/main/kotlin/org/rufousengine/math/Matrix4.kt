package org.rufousengine.math

import java.util.*

/**
 * An immutable 4x4 row-major matrix.
 *
 * This is a general matrix representation. To represent transformations or projections, there are more optimized (and limited) alternatives.
 * See [Transformation] and [Projection] instead.
 *
 * @constructor Creates a 4x4 matrix with the given components.
 */
open class Matrix4(e00: Float, e01: Float, e02: Float, e03: Float, e10: Float, e11: Float, e12: Float, e13: Float, e20: Float, e21: Float, e22: Float, e23: Float, e30: Float, e31: Float, e32: Float, e33: Float) {
    companion object {
        val identity = Matrix4()
        val zero = Matrix4(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    }

    /** The components of this matrix. Do not change its values directly unless you know what you're doing. */
    val components = floatArrayOf(e00, e01, e02, e03, e10, e11, e12, e13, e20, e21, e22, e23, e30, e31, e32, e33)

    constructor() : this(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
    constructor(other: Matrix4) : this(other.components)
    constructor(components: FloatArray) : this(
            components[0], components[1], components[2], components[3],
            components[4], components[5], components[6], components[7],
            components[8], components[9], components[10], components[11],
            components[12], components[13], components[14], components[15]
    )
    constructor(a: Vector4, b: Vector4, c: Vector4, d: Vector4) : this(
            a.x, b.x, c.x, d.x,
            a.y, b.y, c.y, d.y,
            a.z, b.z, c.z, d.z,
            a.w, b.w, c.w, d.w
    )

    open val e00: Float
        get() = components[0]
    open val e01: Float
        get() = components[1]
    open val e02: Float
        get() = components[2]
    open val e03: Float
        get() = components[3]
    open val e10: Float
        get() = components[4]
    open val e11: Float
        get() = components[5]
    open val e12: Float
        get() = components[6]
    open val e13: Float
        get() = components[7]
    open val e20: Float
        get() = components[8]
    open val e21: Float
        get() = components[9]
    open val e22: Float
        get() = components[10]
    open val e23: Float
        get() = components[11]
    open val e30: Float
        get() = components[12]
    open val e31: Float
        get() = components[13]
    open val e32: Float
        get() = components[14]
    open val e33: Float
        get() = components[15]

    /** The transpose of this matrix. It creates a new lazy Matrix4 instance. */
    open val transpose : Matrix4 by lazy { transpose(MutableMatrix4()) }
    /** The inverse of this matrix. It creates a new lazy Matrix4 instance. */
    open val inverse : Matrix4 by lazy { inverse(MutableMatrix4()) }

    val determinant: Float
        get() {
            return if(isTransformation) {
                e00 * (e11 * e22 - e12 * e21) + e01 * (e12 * e20 - e10 * e22) + e02 * (e10 * e21 - e11 * e20)
            } else {
                val d0 = e11 * (e22 * e33 - e23 * e32) - e12 * (e21 * e33 - e23 * e31) + e13 * (e21 * e32 - e22 * e31)
                val d1 = e10 * (e22 * e33 - e23 * e32) - e12 * (e20 * e33 - e23 * e30) + e13 * (e20 * e32 - e22 * e30)
                val d2 = e10 * (e21 * e33 - e23 * e31) - e11 * (e20 * e33 - e23 * e30) + e13 * (e20 * e31 - e21 * e30)
                val d3 = e10 * (e21 * e32 - e22 * e31) - e11 * (e20 * e32 - e22 * e30) + e12 * (e20 * e31 - e21 * e30)

                e00 * d0 - e01 * d1 + e02 * d2 - e03 * d3
            }
        }

    /** Whether this matrix is orthogonal (i.e., all rows (or columns) are orthonormal between each other). */
    val isOrthogonal: Boolean
        get() {
            getRow0(Cached.c0)
            getRow1(Cached.c1)
            getRow2(Cached.c2)
            getRow3(Cached.c3)

            if(!Cached.c0.isUnit || !Cached.c1.isUnit || !Cached.c2.isUnit || !Cached.c3.isUnit) {
                return false
            }

            return Cached.c0 isOrthogonal Cached.c1 && Cached.c0 isOrthogonal Cached.c2 && Cached.c0 isOrthogonal Cached.c3 &&
                    Cached.c1 isOrthogonal Cached.c2 && Cached.c1 isOrthogonal Cached.c3 &&
                    Cached.c2 isOrthogonal Cached.c3
        }

    val isIdentity
        get() = equals(identity)
    val isZero
        get() = equals(zero)
    /** Whether the last row is (0, 0, 0, 1). */
    val isTransformation
        get() = e30.isZero() && e31.isZero() && e32.isZero() && e33.isOne()

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
    fun getRow3(out: MutableVector4) = out.set(e30, e31, e32, e33)

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
    fun getColumn0(out: MutableVector4) = out.set(e00, e10, e20, e30)
    /**
     * Gets the second column as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn1(out: MutableVector4) = out.set(e01, e11, e21, e31)
    /**
     * Gets the third column as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn2(out: MutableVector4) = out.set(e02, e12, e22, e32)
    /**
     * Gets the fourth column as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn3(out: MutableVector4) = out.set(e03, e13, e23, e33)

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
    fun copyImmutable() = Matrix4(this)
    /**
     * Creates a mutable copy of this matrix.
     *
     * @return The new matrix for chaining.
     */
    fun copyMutable() = MutableMatrix4(this)

    /**
     * Transposes this matrix.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun transpose(out: MutableMatrix4) = out.set(
            e00, e10, e20, e30,
            e01, e11, e21, e31,
            e02, e12, e22, e32,
            e03, e13, e23, e33
    )
    /**
     * Inverts this matrix.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun inverse(out: MutableMatrix4) : MutableMatrix4 {
        if(isOrthogonal) {
            transpose(out) // if M is orthogonal then inv(M) = conjugate(M)
        } else {
            if (isTransformation) {
                Cached.a.set(e00, e10, e20)
                Cached.b.set(e01, e11, e21)
                Cached.c.set(e02, e12, e22)
                Cached.d.set(e03, e13, e23)

                // s = a cross b
                Cached.a.cross(Cached.b, Cached.s)
                // t = c cross d
                Cached.c.cross(Cached.d, Cached.t)
                // u = a * g - b * r = 0
                // v = c * a - d * b = c

                // det = s . v + t . u = s . c
                val invDet = 1 / (Cached.s dot Cached.c)

                Cached.s *= invDet
                Cached.t *= invDet

                Cached.c.scale(invDet, Cached.v)

                // r0 = b cross v + t * g = b cross v
                Cached.b.cross(Cached.v, Cached.r0)
                // r1 = v cross a - t * cross = v cross a
                Cached.v.cross(Cached.a, Cached.r1)
                // r2 = d cross u + s * a = s
                // r3 = u cross c - s * b = 0

                out.set(
                        Cached.r0.x, Cached.r0.y, Cached.r0.z, -(Cached.b dot Cached.t),
                        Cached.r1.x, Cached.r1.y, Cached.r1.z, (Cached.a dot Cached.t),
                        Cached.s.x, Cached.s.y, Cached.s.z, -(Cached.d dot Cached.s),
                        0.0f, 0.0f, 0.0f, 1.0f
                )
            } else {
                Cached.a.set(e00, e10, e20)
                Cached.b.set(e01, e11, e21)
                Cached.c.set(e02, e12, e22)
                Cached.d.set(e03, e13, e23)

                val x = e30
                val y = e31
                val z = e32
                val w = e33

                // s = a cross b
                Cached.a.cross(Cached.b, Cached.s)
                // t = c cross d
                Cached.c.cross(Cached.d, Cached.t)
                // u = a * g - b * r
                Cached.a.scale(y, Cached.t0)
                Cached.b.scale(x, Cached.t1)
                Cached.t0.subtract(Cached.t1, Cached.u)
                // v = c * a - d * b
                Cached.c.scale(w, Cached.t0)
                Cached.d.scale(z, Cached.t1)
                Cached.t0.subtract(Cached.t1, Cached.v)

                // det = s . v + t . u
                val invDet = 1 / ((Cached.s dot Cached.v) + (Cached.t dot Cached.u))

                Cached.s *= invDet
                Cached.t *= invDet
                Cached.u *= invDet
                Cached.v *= invDet

                // r0 = b cross v + t * g
                Cached.b.cross(Cached.v, Cached.t0)
                Cached.t.scale(y, Cached.t1)
                Cached.t0.add(Cached.t1, Cached.r0)
                // r1 = v cross a - t * r
                Cached.v.cross(Cached.a, Cached.t0)
                Cached.t.scale(x, Cached.t1)
                Cached.t0.subtract(Cached.t1, Cached.r1)
                // r2 = d cross u + s * a
                Cached.d.cross(Cached.u, Cached.t0)
                Cached.s.scale(w, Cached.t1)
                Cached.t0.add(Cached.t1, Cached.r2)
                // r3 = u cross c - s * b
                Cached.u.cross(Cached.c, Cached.t0)
                Cached.s.scale(z, Cached.t1)
                Cached.t0.subtract(Cached.t1, Cached.r3)

                out.set(
                        Cached.r0.x, Cached.r0.y, Cached.r0.z, -(Cached.b dot Cached.t),
                        Cached.r1.x, Cached.r1.y, Cached.r1.z, (Cached.a dot Cached.t),
                        Cached.r2.x, Cached.r2.y, Cached.r2.z, -(Cached.d dot Cached.s),
                        Cached.r3.x, Cached.r3.y, Cached.r3.z, (Cached.c dot Cached.s)
                )
            }
        }

        return out
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
            e30 * scalar, e31 * scalar, e32 * scalar, e33 * scalar
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
            e30 + other.e30, e31 + other.e31, e32 + other.e32, e33 + other.e33
    )
    /**
     * Subtracts [other] from this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun subtract(other: Matrix4, out: MutableMatrix4) = out.set(
            e00 - other.e00, e01 - other.e01, e02 - other.e02, e03 - other.e03,
            e10 - other.e10, e11 - other.e11, e12 - other.e12, e13 - other.e13,
            e20 - other.e20, e21 - other.e21, e22 - other.e22, e23 - other.e23,
            e30 - other.e30, e31 - other.e31, e32 - other.e32, e33 - other.e33
    )

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

        val e30 = this.e30 * other.e00 + this.e31 * other.e10 + this.e32 * other.e20 + this.e33 * other.e30
        val e31 = this.e30 * other.e01 + this.e31 * other.e11 + this.e32 * other.e21 + this.e33 * other.e31
        val e32 = this.e30 * other.e02 + this.e31 * other.e12 + this.e32 * other.e22 + this.e33 * other.e32
        val e33 = this.e30 * other.e03 + this.e31 * other.e13 + this.e32 * other.e23 + this.e33 * other.e33

        return out.set(
                e00, e01, e02, e03,
                e10, e11, e12, e13,
                e20, e21, e22, e23,
                e30, e31, e32, e33
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
        val nw = e30 * x + e31 * y + e32 * z + e33 * w

        return out.set(nx, ny, nz, nw)
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
    
    fun equals(other: Matrix4) = equals(other.e00, other.e01, other.e02, other.e03, other.e10, other.e11, other.e12, other.e13, other.e20, other.e21, other.e22, other.e23, other.e30, other.e31, other.e32, other.e33)
    fun equals(e00: Float, e01: Float, e02: Float, e03: Float, e10: Float, e11: Float, e12: Float, e13: Float, e20: Float, e21: Float, e22: Float, e23: Float, e30: Float, e31: Float, e32: Float, e33: Float) : Boolean {
        return this.e00 isCloseTo e00 && this.e01 isCloseTo e01 && this.e02 isCloseTo e02 && this.e03 isCloseTo e03 &&
                this.e10 isCloseTo e10 && this.e11 isCloseTo e11 && this.e12 isCloseTo e12 && this.e13 isCloseTo e13 &&
                this.e20 isCloseTo e20 && this.e21 isCloseTo e21 && this.e22 isCloseTo e22 && this.e23 isCloseTo e23 &&
                this.e30 isCloseTo e30 && this.e31 isCloseTo e31 && this.e32 isCloseTo e32 && this.e33 isCloseTo e33
    }

    override fun toString() = "| ($e00, $e01, $e02, $e03) | ($e10, $e11, $e12, $e13) | ($e20, $e21, $e22, $e23) | ($e30, $e31, $e32, $e33) |"

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Matrix4) {
            return false
        }

        return equals(other)
    }

    override fun hashCode() = Arrays.hashCode(components)

    private object Cached {
        val t0 by lazy { MutableVector3() }
        val t1 by lazy { MutableVector3() }
        val a by lazy { MutableVector3() }
        val b by lazy { MutableVector3() }
        val c by lazy { MutableVector3() }
        val d by lazy { MutableVector3() }
        val s by lazy { MutableVector3() }
        val t by lazy { MutableVector3() }
        val u by lazy { MutableVector3() }
        val v by lazy { MutableVector3() }
        val r0 by lazy { MutableVector3() }
        val r1 by lazy { MutableVector3() }
        val r2 by lazy { MutableVector3() }
        val r3 by lazy { MutableVector3() }
        val c0 by lazy { MutableVector4() }
        val c1 by lazy { MutableVector4() }
        val c2 by lazy { MutableVector4() }
        val c3 by lazy { MutableVector4() }
    }
}