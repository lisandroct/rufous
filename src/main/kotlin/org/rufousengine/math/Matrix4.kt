package org.rufousengine.math

import java.util.*
import kotlin.math.sqrt

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
    constructor(other: Projection) : this(other.components)
    constructor(other: Transformation) : this(other.components)
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
        return when {
            isIdentity -> out.set(this)
            isOrthogonal -> transpose(out) // if M is orthogonal then inv(M) = transpose(M)
            isTransformation -> {
                val a = Cached.a
                val b = Cached.b
                val c = Cached.c
                val d = Cached.d
                val s = Cached.s
                val t = Cached.t
                val v = Cached.v
                val r0 = Cached.r0
                val r1 = Cached.r1

                a.set(e00, e10, e20)
                b.set(e01, e11, e21)
                c.set(e02, e12, e22)
                d.set(e03, e13, e23)

                // s = a.cross(b)
                a.cross(b, s)
                // t = c.cross(d)
                c.cross(d, t)
                // u = a.scale(y) - b.scale(x) = 0
                // v = c.scale(w) - d.scale(z) = c

                // det = s.dot(v) + t.dot(u) = s.dot(c)
                val invDet = 1 / (s dot c)

                s *= invDet
                t *= invDet

                c.scale(invDet, v)

                // r0 = b.cross(v) + t.scale(y) = b.cross(v)
                b.cross(v, r0)
                // r1 = v.cross(a) - t.scale(x) = v.cross(a)
                v.cross(a, r1)
                // r2 = d.cross(u) + s.scale(w) = s
                // r3 = u.cross(c) - s.scale(z) = 0

                out.set(
                        r0.x, r0.y, r0.z, -(b dot t),
                        r1.x, r1.y, r1.z, (a dot t),
                        s.x, s.y, s.z, -(d dot s),
                        0.0f, 0.0f, 0.0f, 1.0f
                )
            }
            else -> {
                val a = Cached.a
                val b = Cached.b
                val c = Cached.c
                val d = Cached.d
                val s = Cached.s
                val t = Cached.t
                val u = Cached.u
                val v = Cached.v
                val r0 = Cached.r0
                val r1 = Cached.r1
                val r2 = Cached.r2
                val r3 = Cached.r3
                val t0 = Cached.t0
                val t1 = Cached.t1

                a.set(e00, e10, e20)
                b.set(e01, e11, e21)
                c.set(e02, e12, e22)
                d.set(e03, e13, e23)

                val x = e30
                val y = e31
                val z = e32
                val w = e33

                // s = a.cross(b)
                a.cross(b, s)
                // t = c.cross(d)
                c.cross(d, t)
                // u = a.scale(y) - b.scale(x)
                a.scale(y, t0)
                b.scale(x, t1)
                t0.subtract(t1, u)
                // v = c.scale(w) - d.scale(z)
                c.scale(w, t0)
                d.scale(z, t1)
                t0.subtract(t1, v)

                // det = s.dot(v) + t.dot(u)
                val invDet = 1 / ((s dot v) + (t dot u))

                s *= invDet
                t *= invDet
                u *= invDet
                v *= invDet

                // r0 = b.cross(v) + t.scale(y)
                b.cross(v, t0)
                t.scale(y, t1)
                t0.add(t1, r0)
                // r1 = v.cross(a) - t.scale(x)
                v.cross(a, t0)
                t.scale(x, t1)
                t0.subtract(t1, r1)
                // r2 = d.cross(u) + s.scale(w)
                d.cross(u, t0)
                s.scale(w, t1)
                t0.add(t1, r2)
                // r3 = u.cross(c) - s.scale(z)
                u.cross(c, t0)
                s.scale(z, t1)
                t0.subtract(t1, r3)

                out.set(
                        r0.x, r0.y, r0.z, -(b dot t),
                        r1.x, r1.y, r1.z, (a dot t),
                        r2.x, r2.y, r2.z, -(d dot s),
                        r3.x, r3.y, r3.z, (c dot s)
                )
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
            e30 * scalar, e31 * scalar, e32 * scalar, e33 * scalar
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
            e30, e31, e32 + other.e32, e33 + other.e33
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
            e30, e31, e32, e33 + 1f
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
            e30, e31, e32 - other.e32, e33 - other.e33
    )
    /**
     * Subtracts [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun subtract(other: Transformation, out: MutableMatrix4) = out.set(
            e00 - other.e00, e01 - other.e01, e02 - other.e02, e03 - other.e03,
            e10 - other.e10, e11 - other.e11, e12 - other.e12, e13 - other.e13,
            e20 - other.e20, e21 - other.e21, e22 - other.e22, e23 - other.e23,
            e30, e31, e32, e33 - 1f
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

        val e30 = this.e30 * other.e00
        val e31 = this.e31 * other.e11
        val e32 = this.e32 * other.e22 + this.e33 * other.e32
        val e33 = this.e32 * other.e23 + this.e33 * other.e33

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
    fun multiplyLeft(other: Projection, out: MutableMatrix4) = other.multiply(this, out)

    /**
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun multiply(other: Transformation, out: MutableMatrix4) : MutableMatrix4 {
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

        val e30 = this.e30 * other.e00 + this.e31 * other.e10 + this.e32 * other.e20
        val e31 = this.e30 * other.e01 + this.e31 * other.e11 + this.e32 * other.e21
        val e32 = this.e30 * other.e02 + this.e31 * other.e12 + this.e32 * other.e22
        val e33 = this.e30 * other.e03 + this.e31 * other.e13 + this.e32 * other.e23 + this.e33

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
    fun multiplyLeft(other: Transformation, out: MutableMatrix4) = other.multiply(this, out)

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
        val nw = e30 * x + e31 * y + e32 * z + e33

        return out.set(nx, ny, nz).scale(1 / nw)
    }

    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about the x axis.
     *
     * @param[angle] The angle in degrees.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun rotateX(angle: Float, out: MutableMatrix4) : MutableMatrix4 {
        if(angle.isZero()) {
            return out.set(this)
        }

        val c = cos(angle)
        val s = sin(angle)

        val e10 = c * this.e10 + -s * this.e20
        val e11 = c * this.e11 + -s * this.e21
        val e12 = c * this.e12 + -s * this.e22
        val e13 = c * this.e13 + -s * this.e23

        val e20 = s * this.e10 + c * this.e20
        val e21 = s * this.e11 + c * this.e21
        val e22 = s * this.e12 + c * this.e22
        val e23 = s * this.e13 + c * this.e23

        return out.set(
                this.e00, this.e01, this.e02, this.e03,
                e10, e11, e12, e13,
                e20, e21, e22, e23,
                this.e30, this.e31, this.e32, this.e33
        )
    }
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about the y axis.
     *
     * @param[angle] The angle in degrees.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun rotateY(angle: Float, out: MutableMatrix4) : MutableMatrix4 {
        if(angle.isZero()) {
            return out.set(this)
        }

        val c = cos(angle)
        val s = sin(angle)

        val e00 = c * this.e00 + s * this.e20
        val e01 = c * this.e01 + s * this.e21
        val e02 = c * this.e02 + s * this.e22
        val e03 = c * this.e03 + s * this.e23

        val e20 = -s * this.e00 + c * this.e20
        val e21 = -s * this.e01 + c * this.e21
        val e22 = -s * this.e02 + c * this.e22
        val e23 = -s * this.e03 + c * this.e23

        return out.set(
                e00, e01, e02, e03,
                this.e10, this.e11, this.e12, this.e13,
                e20, e21, e22, e23,
                this.e30, this.e31, this.e32, this.e33
        )
    }
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about the z axis.
     *
     * @param[angle] The angle in degrees.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun rotateZ(angle: Float, out: MutableMatrix4) : MutableMatrix4 {
        if(angle.isZero()) {
            return out.set(this)
        }

        val c = cos(angle)
        val s = sin(angle)

        val e00 = c * this.e00 + -s * this.e10
        val e01 = c * this.e01 + -s * this.e11
        val e02 = c * this.e02 + -s * this.e12
        val e03 = c * this.e03 + -s * this.e13

        val e10 = s * this.e00 + c * this.e10
        val e11 = s * this.e01 + c * this.e11
        val e12 = s * this.e02 + c * this.e12
        val e13 = s * this.e03 + c * this.e13

        return out.set(
                e00, e01, e02, e03,
                e10, e11, e12, e13,
                this.e20, this.e21, this.e22, this.e23,
                this.e30, this.e31, this.e32, this.e33
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
    fun rotateSafe(angle: Float, axis: Vector3, out: MutableMatrix4) = rotateSafe(angle, axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [rotate] is a cheaper alternative.
     *
     * @param[angle] The angle in degrees.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun rotateSafe(angle: Float, aX: Float, aY: Float, aZ: Float, out: MutableMatrix4) : MutableMatrix4 {
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
    fun rotate(angle: Float, axis: Vector3, out: MutableMatrix4) = rotate(angle, axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents a rotation through [angle] about ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @param[angle] The angle in degrees.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun rotate(angle: Float, aX: Float, aY: Float, aZ: Float, out: MutableMatrix4) : MutableMatrix4 {
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
        val e03 = r00 * this.e03 + r01 * this.e13 + r02 * this.e23

        val e10 = r10 * this.e00 + r11 * this.e10 + r12 * this.e20
        val e11 = r10 * this.e01 + r11 * this.e11 + r12 * this.e21
        val e12 = r10 * this.e02 + r11 * this.e12 + r12 * this.e22
        val e13 = r10 * this.e03 + r11 * this.e13 + r12 * this.e23

        val e20 = r20 * this.e00 + r21 * this.e10 + r22 * this.e20
        val e21 = r20 * this.e01 + r21 * this.e11 + r22 * this.e21
        val e22 = r20 * this.e02 + r21 * this.e12 + r22 * this.e22
        val e23 = r20 * this.e03 + r21 * this.e13 + r22 * this.e23

        return out.set(
                e00, e01, e02, e03,
                e10, e11, e12, e13,
                e20, e21, e22, e23,
                this.e30, this.e31, this.e32, this.e33
        )
    }
    /**
     * Left multiplies this matrix with a matrix that represents the same rotation as [quaternion].
     *
     * @param[quaternion] The rotation quaternion.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun rotate(quaternion: Quaternion, out: MutableMatrix4) : MutableMatrix4 {
        if(isIdentity) {
            return quaternion.getMatrixRepresentation(out)
        }

        if(quaternion.isIdentity) {
            return out.set(this)
        }

        val invMagnitude = 1 / quaternion.magnitude
        val x = quaternion.x * invMagnitude
        val y = quaternion.y * invMagnitude
        val z = quaternion.z * invMagnitude
        val w = quaternion.w * invMagnitude

        val x2 = x * x
        val y2 = y * y
        val z2 = z * z
        val xy = x * y
        val xz = x * z
        val yz = y * z
        val wx = w * x
        val wy = w * y
        val wz = w * z

        val r00 = 1f - 2f * (y2 + z2)
        val r01 = 2f * (xy - wz)
        val r02 = 2f * (xz + wy)

        val r10 = 2f * (xy + wz)
        val r11 = 1f - 2f * (x2 + z2)
        val r12 = 2f * (yz - wx)

        val r20 = 2f * (xz - wy)
        val r21 = 2f * (yz + wx)
        val r22 = 1f - 2f * (x2 + y2)

        val e00 = r00 * this.e00 + r01 * this.e10 + r02 * this.e20
        val e01 = r00 * this.e01 + r01 * this.e11 + r02 * this.e21
        val e02 = r00 * this.e02 + r01 * this.e12 + r02 * this.e22
        val e03 = r00 * this.e03 + r01 * this.e13 + r02 * this.e23

        val e10 = r10 * this.e00 + r11 * this.e10 + r12 * this.e20
        val e11 = r10 * this.e01 + r11 * this.e11 + r12 * this.e21
        val e12 = r10 * this.e02 + r11 * this.e12 + r12 * this.e22
        val e13 = r10 * this.e03 + r11 * this.e13 + r12 * this.e23

        val e20 = r20 * this.e00 + r21 * this.e10 + r22 * this.e20
        val e21 = r20 * this.e01 + r21 * this.e11 + r22 * this.e21
        val e22 = r20 * this.e02 + r21 * this.e12 + r22 * this.e22
        val e23 = r20 * this.e03 + r21 * this.e13 + r22 * this.e23

        return out.set(
                e00, e01, e02, e03,
                e10, e11, e12, e13,
                e20, e21, e22, e23,
                this.e30, this.e31, this.e32, this.e33
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
    fun reflectSafe(axis: Vector3, out: MutableMatrix4) = reflectSafe(axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents a reflection through a plane perpendicular to ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [reflect] is a cheaper alternative.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun reflectSafe(aX: Float, aY: Float, aZ: Float, out: MutableMatrix4) : MutableMatrix4 {
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
    fun reflect(axis: Vector3, out: MutableMatrix4) = reflect(axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents a reflection through a plane perpendicular to ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun reflect(aX: Float, aY: Float, aZ: Float, out: MutableMatrix4) : MutableMatrix4 {
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
        val e03 = r00 * this.e03 + axay * this.e13 + axaz * this.e23

        val e10 = axay * this.e00 + r11 * this.e10 + ayaz * this.e20
        val e11 = axay * this.e01 + r11 * this.e11 + ayaz * this.e21
        val e12 = axay * this.e02 + r11 * this.e12 + ayaz * this.e22
        val e13 = axay * this.e03 + r11 * this.e13 + ayaz * this.e23

        val e20 = axaz * this.e00 + ayaz * this.e10 + r22 * this.e20
        val e21 = axaz * this.e01 + ayaz * this.e11 + r22 * this.e21
        val e22 = axaz * this.e02 + ayaz * this.e12 + r22 * this.e22
        val e23 = axaz * this.e03 + ayaz * this.e13 + r22 * this.e23

        return out.set(
                e00, e01, e02, e03,
                e10, e11, e12, e13,
                e20, e21, e22, e23,
                this.e30, this.e31, this.e32, this.e33
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
    fun involuteSafe(axis: Vector3, out: MutableMatrix4) = involuteSafe(axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents an involution through ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [involute] is a cheaper alternative.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun involuteSafe(aX: Float, aY: Float, aZ: Float, out: MutableMatrix4) : MutableMatrix4 {
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
    fun involute(axis: Vector3, out: MutableMatrix4) = involute(axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents an involution through ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun involute(aX: Float, aY: Float, aZ: Float, out: MutableMatrix4) : MutableMatrix4 {
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
        val e03 = r00 * this.e03 + axay * this.e13 + axaz * this.e23

        val e10 = axay * this.e00 + r11 * this.e10 + ayaz * this.e20
        val e11 = axay * this.e01 + r11 * this.e11 + ayaz * this.e21
        val e12 = axay * this.e02 + r11 * this.e12 + ayaz * this.e22
        val e13 = axay * this.e03 + r11 * this.e13 + ayaz * this.e23

        val e20 = axaz * this.e00 + ayaz * this.e10 + r22 * this.e20
        val e21 = axaz * this.e01 + ayaz * this.e11 + r22 * this.e21
        val e22 = axaz * this.e02 + ayaz * this.e12 + r22 * this.e22
        val e23 = axaz * this.e03 + ayaz * this.e13 + r22 * this.e23

        return out.set(
                e00, e01, e02, e03,
                e10, e11, e12, e13,
                e20, e21, e22, e23,
                this.e30, this.e31, this.e32, this.e33
        )
    }

    /**
     * Left multiplies this matrix with a matrix that represents a non uniform scale by [factorX], [factorY] and [factorZ].
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun scale(factorX: Float, factorY: Float, factorZ: Float, out: MutableMatrix4) : MutableMatrix4 {
        val e00 = factorX * this.e00
        val e01 = factorX * this.e01
        val e02 = factorX * this.e02
        val e03 = factorX * this.e03

        val e10 = factorY * this.e10
        val e11 = factorY * this.e11
        val e12 = factorY * this.e12
        val e13 = factorY * this.e13

        val e20 = factorZ * this.e20
        val e21 = factorZ * this.e21
        val e22 = factorZ * this.e22
        val e23 = factorZ * this.e23

        return out.set(
                e00, e01, e02, e03,
                e10, e11, e12, e13,
                e20, e21, e22, e23,
                this.e30, this.e31, this.e32, this.e33
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
    fun scaleSafe(factor: Float, axis: Vector3, out: MutableMatrix4) = scaleSafe(factor, axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents a scale by [factor] along ([aX], [aY], [aZ]).
     *
     * If ([aX], [aY], [aZ]) is known to be a unit vector, [scale] is a cheaper alternative.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun scaleSafe(factor: Float, aX: Float, aY: Float, aZ: Float, out: MutableMatrix4) : MutableMatrix4 {
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
    fun scale(factor: Float, axis: Vector3, out: MutableMatrix4) = scale(factor, axis.x, axis.y, axis.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents a scale by [factor] along ([aX], [aY], [aZ]).
     *
     * ([aX], [aY], [aZ]) must be a unit vector.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun scale(factor: Float, aX: Float, aY: Float, aZ: Float, out: MutableMatrix4) : MutableMatrix4 {
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
        val e03 = r00 * this.e03 + axay * this.e13 + axaz * this.e23

        val e10 = axay * this.e00 + r11 * this.e10 + ayaz * this.e20
        val e11 = axay * this.e01 + r11 * this.e11 + ayaz * this.e21
        val e12 = axay * this.e02 + r11 * this.e12 + ayaz * this.e22
        val e13 = axay * this.e03 + r11 * this.e13 + ayaz * this.e23

        val e20 = axaz * this.e00 + ayaz * this.e10 + r22 * this.e20
        val e21 = axaz * this.e01 + ayaz * this.e11 + r22 * this.e21
        val e22 = axaz * this.e02 + ayaz * this.e12 + r22 * this.e22
        val e23 = axaz * this.e03 + ayaz * this.e13 + r22 * this.e23

        return out.set(
                e00, e01, e02, e03,
                e10, e11, e12, e13,
                e20, e21, e22, e23,
                this.e30, this.e31, this.e32, this.e33
        )
    }

    /**
     * Left multiplies this matrix with a matrix that represents a translation to [point].
     *
     * @param[point] The point to translate to.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun translate(point: Point, out: MutableMatrix4) = translate(point.x, point.y, point.z, out)
    /**
     * Left multiplies this matrix with a matrix that represents a translation to ([x], [y], [z]).
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun translate(x: Float, y: Float, z: Float, out: MutableMatrix4) : MutableMatrix4 {
        val e00 = this.e00 + x * this.e30
        val e01 = this.e01 + x * this.e31
        val e02 = this.e02 + x * this.e32
        val e03 = this.e03 + x * this.e33

        val e10 = this.e10 + y * this.e30
        val e11 = this.e11 + y * this.e31
        val e12 = this.e12 + y * this.e32
        val e13 = this.e13 + y * this.e33

        val e20 = this.e20 + z * this.e30
        val e21 = this.e21 + z * this.e31
        val e22 = this.e22 + z * this.e32
        val e23 = this.e23 + z * this.e33

        return out.set(
                e00, e01, e02, e03,
                e10, e11, e12, e13,
                e20, e21, e22, e23,
                this.e30, this.e31, this.e32, this.e33
        )
    }
/*
    fun ABBA(aX: Float, aY: Float, aZ: Float, out: MutableMatrix4) : MutableMatrix4 {
        val e00 = r00 * this.e00 + r01 * this.e10 + r02 * this.e20 + r03 * this.e30
        val e01 = r00 * this.e01 + r01 * this.e11 + r02 * this.e21 + r03 * this.e31
        val e02 = r00 * this.e02 + r01 * this.e12 + r02 * this.e22 + r03 * this.e32
        val e03 = r00 * this.e03 + r01 * this.e13 + r02 * this.e23 + r03 * this.e33

        val e10 = r10 * this.e00 + r11 * this.e10 + r12 * this.e20 + r13 * this.e30
        val e11 = r10 * this.e01 + r11 * this.e11 + r12 * this.e21 + r13 * this.e31
        val e12 = r10 * this.e02 + r11 * this.e12 + r12 * this.e22 + r13 * this.e32
        val e13 = r10 * this.e03 + r11 * this.e13 + r12 * this.e23 + r13 * this.e33

        val e20 = r20 * this.e00 + r21 * this.e10 + r22 * this.e20 + r23 * this.e30
        val e21 = r20 * this.e01 + r21 * this.e11 + r22 * this.e21 + r23 * this.e31
        val e22 = r20 * this.e02 + r21 * this.e12 + r22 * this.e22 + r23 * this.e32
        val e23 = r20 * this.e03 + r21 * this.e13 + r22 * this.e23 + r23 * this.e33

        return out.set(
                e00, e01, e02, e03,
                e10, e11, e12, e13,
                e20, e21, e22, e23,
                this.e30, this.e31, this.e32, this.e33
        )
    }*/

    fun equals(other: Projection) = equals(other.e00, 0f, 0f, 0f, 0f, other.e11, 0f, 0f, 0f, 0f, other.e22, other.e23, 0f, 0f, other.e32, other.e33)
    fun equals(other: Transformation) = equals(other.e00, other.e01, other.e02, other.e03, other.e10, other.e11, other.e12, other.e13, other.e20, other.e21, other.e22, other.e23, 0f, 0f, 0f, 1f)
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

        return when(other) {
            is Projection -> equals(other)
            is Transformation -> equals(other)
            is Matrix4 -> equals(other)
            else -> false
        }
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