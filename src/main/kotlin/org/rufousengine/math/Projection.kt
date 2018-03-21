package org.rufousengine.math

import java.util.*

/**
 * An immutable 4x4 projection matrix with symmetric frustum.
 *
 * For a general purpose matrix, see [Matrix4] instead.
 *
 * @constructor Creates the identity projection matrix.
 */
open class Projection {
    companion object {
        val identity by lazy { Projection() }
        val zero by lazy {
            val projection = Projection()
            projection.components[0] = 0f
            projection.components[5] = 0f
            projection.components[10] = 0f
            projection.components[15] = 0f

            projection
        }
    }

    /** The components of this matrix. Do not change its values directly unless you know what you're doing. */
    val components: FloatArray

    constructor() {
        components = floatArrayOf(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
    }
    constructor(other: Projection) {
        components = other.components.copyOf()
    }

    val e00: Float
        get() = components[0]
    val e11: Float
        get() = components[5]
    val e22: Float
        get() = components[10]
    val e23: Float
        get() = components[11]
    val e32: Float
        get() = components[14]
    val e33: Float
        get() = components[15]

    /** The transpose of this matrix. It creates a new lazy Projection instance. */
    open val transpose : Projection by lazy { transpose(MutableProjection()) }
    /** The inverse of this matrix. It creates a new lazy Projection instance. */
    open val inverse : Projection by lazy { inverse(MutableProjection()) }

    val determinant: Float
        get() = e00 * (e11 * (e22 * e33 - e23 * e32))

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
        get() = e32.isZero() && e33.isOne()

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
    fun getRow0(out: MutableVector4) = out.set(e00, 0f, 0f, 0f)
    /**
     * Gets the second row as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow1(out: MutableVector4) = out.set(0f, e11, 0f, 0f)
    /**
     * Gets the third row as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow2(out: MutableVector4) = out.set(0f, 0f, e22, e23)
    /**
     * Gets the fourth row as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow3(out: MutableVector4) = out.set(0f, 0f, e32, e33)

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
    fun getColumn0(out: MutableVector4) = out.set(e00, 0f, 0f, 0f)
    /**
     * Gets the second column as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn1(out: MutableVector4) = out.set(0f, e11, 0f, 0f)
    /**
     * Gets the third column as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn2(out: MutableVector4) = out.set(0f, 0f, e22, e32)
    /**
     * Gets the fourth column as a Vector4.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn3(out: MutableVector4) = out.set(0f, 0f, e23, e33)

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
    fun copyImmutable() = Projection(this)
    /**
     * Creates a mutable copy of this matrix.
     *
     * @return The new matrix for chaining.
     */
    fun copyMutable() = MutableProjection(this)

    /**
     * Transposes this matrix.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun transpose(out: MutableProjection) = out.set(
            e00,
            e11,
            e22, e32,
            e23, e33
    )
    /**
     * Inverts this matrix.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun inverse(out: MutableProjection) : MutableProjection {
        return when {
            isIdentity -> out.set(this)
            isOrthogonal -> transpose(out) // if M is orthogonal then inv(M) = conjugate(M)
            else -> {
                if (isTransformation) {
                    val f = 1 / e22
                    out.set(
                            1 / e00,
                            1 / e11,
                            1 * f, -e23 * f,
                            0f, 1f
                    )
                } else {
                    /*
                    val a = Cached.a
                    val b = Cached.b
                    val c = Cached.c
                    val d = Cached.d
                    val s = Cached.s
                    val v = Cached.v
                    val r0 = Cached.r0
                    val r1 = Cached.r1
                    val r2 = Cached.r2
                    val r3 = Cached.r3
                    val t0 = Cached.t0
                    val t1 = Cached.t1

                    a.set(e00, 0f, 0f)
                    b.set(0f, e11, 0f)
                    c.set(0f, 0f, e22)
                    d.set(0f, 0f, e23)

                    val z = e32
                    val w = e33

                    // s = a.cross(b) = (0f, 0f, e00 * e11)
                    a.cross(b, s) // = (0f, 0f, e00 * e11)
                    // t = c.cross(d) = 0
                    // u = a.scale(y) - b.scale(x) = 0
                    // v = c.scale(w) - d.scale(z) = (0, 0, e22 * e33 - e23 * e32)
                    c.scale(w, t0) // = (e22 * e33)
                    d.scale(z, t1) // = (e23 * e32)
                    t0.subtract(t1, v) // = (0, 0, e22 * e33 - e23 * e32)

                    // det = s.dot(v) + t.dot(u) = s.dot(v) = (e00 * e11) * (e22 * e33 - e23 * e32)
                    val invDet = 1 / (s dot v) // = 1 / ((e00 * e11) * (e22 * e33 - e23 * e32))

                    s *= invDet // = (0f, 0f, 1 / (e22 * e33 - e23 * e32))
                    v *= invDet // = (0f, 0f, 1 / (e00 * e11))

                    // r0 = b.cross(v) + t.scale(y) = b.cross(v) = (1 / e00, 0f, 0f)
                    b.cross(v, r0) // = (1 / e00, 0f, 0f)
                    // r1 = v.cross(a) - t.scale(x) = v.cross(a) = (0f, 1 / e11, 0f)
                    v.cross(a, r1) // = (0f, 1 / e11, 0f)
                    // r2 = d.cross(u) + s.scale(w) = s.scale(w) = (0f, 0f, e33 / (e22 * e33 - e23 * e32))
                    s.scale(w, r2) // = (0f, 0f, e33 / (e22 * e33 - e23 * e32))
                    // r3 = u.cross(c) - s.scale(z) = -s.scale(z) = -(0f, 0f, e32 / (e22 * e33 - e23 * e32))
                    s.scale(z, r3).negate() // = -(0f, 0f, e32 / (e22 * e33 - e23 * e32))

                    out.set(
                            r0.x,
                            r1.y,
                            r2.z, -(d dot s),
                            r3.z, (c dot s)
                    )
                    */

                    val f = 1 / (e22 * e33 - e23 * e32)
                    out.set(
                            1 / e00,
                            1 / e11,
                            e33 * f, -e23 * f,
                            -e32 * f, e22 * f
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
    fun scale(scalar: Float, out: MutableProjection) = out.set(
            e00 * scalar,
            e11 * scalar,
            e22 * scalar, e23 * scalar,
            e32 * scalar, e33 * scalar
    )

    /**
     * Adds [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun add(other: Projection, out: MutableProjection) = out.set(
            e00 + other.e00,
            e11 + other.e11,
            e22 + other.e22, e23 + other.e23,
            e32 + other.e32, e33 + other.e33
    )
    /**
     * Adds [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun add(other: Transformation, out: MutableMatrix4) = out.set(
            e00 + other.e00, other.e01, other.e02, other.e03,
            other.e10, e11 + other.e11, other.e12, other.e13,
            other.e20, other.e21, e22 + other.e22, e23 + other.e23,
            0f, 0f, e32, e33 + 1f
    )
    /**
     * Adds [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun add(other: Matrix4, out: MutableMatrix4) = out.set(
            e00 + other.e00, other.e01, other.e02, other.e03,
            other.e10, e11 + other.e11, other.e12, other.e13,
            other.e20, other.e21, e22 + other.e22, e23 + other.e23,
            other.e30, other.e31, e32 + other.e32, e33 + other.e33
    )
    /**
     * Subtracts [other] from this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun subtract(other: Projection, out: MutableProjection) = out.set(
            e00 - other.e00,
            e11 - other.e11,
            e22 - other.e22, e23 - other.e23,
            e32 - other.e32, e33 - other.e33
    )
    /**
     * Subtracts [other] from this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun subtract(other: Transformation, out: MutableMatrix4) = out.set(
            e00 - other.e00, -other.e01, -other.e02, -other.e03,
            -other.e10, e11 - other.e11, -other.e12, -other.e13,
            -other.e20, -other.e21, e22 - other.e22, e23 - other.e23,
            0f, 0f, e32, e33 - 1f
    )
    /**
     * Subtracts [other] from this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun subtract(other: Matrix4, out: MutableMatrix4) = out.set(
            e00 - other.e00, -other.e01, -other.e02, -other.e03,
            -other.e10, e11 - other.e11, -other.e12, -other.e13,
            -other.e20, -other.e21, e22 - other.e22, e23 - other.e23,
            -other.e30, -other.e31, e32 - other.e32, e33 - other.e33
    )

    /**
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun multiply(other: Projection, out: MutableProjection) : MutableProjection {
        if(isIdentity) {
            return out.set(other)
        }

        if(other.isIdentity) {
            return out.set(this)
        }

        val e00 = this.e00 * other.e00

        val e11 = this.e11 * other.e11

        val e22 = this.e22 * other.e22 + this.e23 * other.e32
        val e23 = this.e22 * other.e23 + this.e23 * other.e33

        val e32 = this.e32 * other.e22 + this.e33 * other.e32
        val e33 = this.e32 * other.e23 + this.e33 * other.e33

        return out.set(
                e00,
                e11,
                e22, e23,
                e32, e33
        )
    }
    /**
     * Multiplies [other] with this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun multiplyLeft(other: Projection, out: MutableProjection) = other.multiply(this, out)

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

        val e00 = this.e00 * other.e00
        val e01 = this.e00 * other.e01
        val e02 = this.e00 * other.e02
        val e03 = this.e00 * other.e03

        val e10 = this.e11 * other.e10
        val e11 = this.e11 * other.e11
        val e12 = this.e11 * other.e12
        val e13 = this.e11 * other.e13

        val e20 = this.e22 * other.e20
        val e21 = this.e22 * other.e21
        val e22 = this.e22 * other.e22
        val e23 = this.e22 * other.e23 + this.e23

        val e30 = this.e32 * other.e20
        val e31 = this.e32 * other.e21
        val e32 = this.e32 * other.e22
        val e33 = this.e32 * other.e23 + this.e33

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

        val e00 = this.e00 * other.e00
        val e01 = this.e00 * other.e01
        val e02 = this.e00 * other.e02
        val e03 = this.e00 * other.e03

        val e10 = this.e11 * other.e10
        val e11 = this.e11 * other.e11
        val e12 = this.e11 * other.e12
        val e13 = this.e11 * other.e13

        val e20 = this.e22 * other.e20 + this.e23 * other.e30
        val e21 = this.e22 * other.e21 + this.e23 * other.e31
        val e22 = this.e22 * other.e22 + this.e23 * other.e32
        val e23 = this.e22 * other.e23 + this.e23 * other.e33

        val e30 = this.e32 * other.e20 + this.e33 * other.e30
        val e31 = this.e32 * other.e21 + this.e33 * other.e31
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

        val nx = e00 * x
        val ny = e11 * y
        val nz = e22 * z + e23 * w
        val nw = e32 * z + e33 * w

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

        val nx = e00 * x
        val ny = e11 * y
        val nz = e22 * z

        return out.set(nx, ny, nz)
    }

    /**
     * Multiplies this matrix with [point].
     *
     * @param[point] The point.
     * @param[out] The output point.
     * @return The output point for chaining.
     */
    fun multiply(point: Point, out: MutablePoint) = multiply(point.x, point.y, point.z, out)
    /**
     * Multiplies this matrix with ([x], [y], [z], 1).
     *
     * @param[out] The point.
     * @return The output point for chaining.
     */
    fun multiply(x: Float, y: Float, z: Float, out: MutablePoint) : MutablePoint {
        if(isIdentity) {
            return out.set(x, y, z)
        }

        val nx = e00 * x
        val ny = e11 * y
        val nz = e22 * z + e23
        val nw = e32 * z + e33

        return out.set(nx, ny, nz).scale(1 / nw)
    }

    fun equals(other: Projection) = equals(other.e00, other.e11, other.e22, other.e23, other.e32, other.e33)
    fun equals(other: Transformation) : Boolean {
        return other.e01.isZero() && other.e02.isZero() && other.e03.isZero() &&
                other.e10.isZero() && other.e12.isZero() && other.e13.isZero() &&
                other.e20.isZero() && other.e21.isZero() &&
                equals(other.e00, other.e11, other.e22, other.e23, 0f, 1f)
    }
    fun equals(other: Matrix4) : Boolean {
        return other.e01.isZero() && other.e02.isZero() && other.e03.isZero() &&
                other.e10.isZero() && other.e12.isZero() && other.e13.isZero() &&
                other.e20.isZero() && other.e21.isZero() &&
                other.e30.isZero() && other.e31.isZero() &&
                equals(other.e00, other.e11, other.e22, other.e23, other.e32, other.e33)
    }
    fun equals(e00: Float, e11: Float, e22: Float, e23: Float, e32: Float, e33: Float) : Boolean {
        return this.e00 isCloseTo e00 &&
                this.e11 isCloseTo e11 &&
                this.e22 isCloseTo e22 && this.e23 isCloseTo e23 &&
                this.e32 isCloseTo e32 && this.e33 isCloseTo e33
    }

    override fun toString() = "| ($e00, ${0f}, ${0f}, ${0f}) | (${0f}, $e11, ${0f}, ${0f}) | (${0f}, ${0f}, $e22, $e23) | (${0f}, ${0f}, $e32, $e33) |"

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
        val c0 by lazy { MutableVector4() }
        val c1 by lazy { MutableVector4() }
        val c2 by lazy { MutableVector4() }
        val c3 by lazy { MutableVector4() }
    }
}