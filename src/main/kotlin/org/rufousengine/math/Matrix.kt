@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.math

import org.rufousengine.utils.DirtyFlag
import java.util.*

data class Matrix2(val e00: Float, val e01: Float, val e10: Float, val e11: Float) {
    constructor() : this(1f, 0f, 0f, 1f)
    constructor(other: Matrix2) : this(other.e00, other.e01, other.e10, other.e11)
    constructor(c0: Vector2, c1: Vector2) : this(c0.x, c1.x, c0.y, c1.y)

    internal val components by lazy { floatArrayOf(e00, e01, e10, e11) }

    inline operator fun get(column: Int) = when(column) {
        0 -> Vector2(e00, e10)
        1 -> Vector2(e01, e11)
        else -> throw IllegalArgumentException("column must be in 0..1")
    }
    inline operator fun get(row: Int, column: Int) = this[column][row]

    inline fun invoke(column: Int) = get(column - 1)
    inline fun invoke(row: Int, column: Int) = get(row - 1, column - 1)
    
    override fun toString() = "{${this[0]} | ${this[1]}}"

    companion object {
        val identity = Matrix2()
        val zero = Matrix2(0f, 0f, 0f, 0f)
    }
}

/** Whether its determinant is zero. */
inline val Matrix2.isSingular
    get() = determinant.isZero()
inline val Matrix2.determinant
    get() = determinant(this)
inline val Matrix2.inverse
    get() = inverse(this)
inline val Matrix2.transpose
    get() = transpose(this)

inline val Matrix2.isIdentity: Boolean
    get() = this == Matrix2.identity
/** Whether all elements are zero except. */
inline val Matrix2.isZero: Boolean
    get() = this == Matrix2.zero
/** Whether this matrix is orthogonal (i.e., all rows (or columns) are orthonormal between each other). */
val Matrix2.isOrthogonal: Boolean
    get() {
        val a = this[0]
        val b = this[1]

        return orthonormal(a, b)
    }

inline operator fun Matrix2.unaryPlus() = this
inline operator fun Matrix2.unaryMinus() = Matrix2(-e00, -e01, -e10, -e11)
inline operator fun Matrix2.times(scalar: Float) = scale(this, scalar)
inline operator fun Matrix2.div(scalar: Float) = times(1 / scalar)
inline operator fun Matrix2.plus(other: Matrix2) = add(this, other)
inline operator fun Matrix2.minus(other: Matrix2) = subtract(this, other)
inline operator fun Matrix2.times(other: Matrix2) = multiply(this, other)
inline operator fun Matrix2.times(vector: Vector2) = multiply(this, vector)

class Matrix3(val e00: Float, val e01: Float, val e02: Float, val e10: Float, val e11: Float, val e12: Float, val e20: Float, val e21: Float, val e22: Float) {
    constructor() : this(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f)
    constructor(other: Matrix3) : this(other.e00, other.e01, other.e02, other.e10, other.e11, other.e12, other.e20, other.e21, other.e22)
    constructor(c0: Vector3, c1: Vector3, c2: Vector3) : this(c0.x, c1.x, c2.x, c0.y, c1.y, c2.y, c0.z, c1.z, c2.z)

    internal val components by lazy { floatArrayOf(e00, e01, e02, e10, e11, e12, e20, e21, e22) }

    inline operator fun get(column: Int) = when(column) {
        0 -> Vector3(e00, e10, e20)
        1 -> Vector3(e01, e11, e21)
        2 -> Vector3(e02, e12, e22)
        else -> throw IllegalArgumentException("column must be in 0..2")
    }
    inline operator fun get(row: Int, column: Int) = this[column][row]

    inline fun invoke(column: Int) = get(column - 1)
    inline fun invoke(row: Int, column: Int) = get(row - 1, column - 1)

    override fun toString() = "{${this[0]} | ${this[1]} | ${this[2]}}"

    companion object {
        val identity = Matrix3()
        val zero = Matrix3(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    }
}

/** Whether its determinant is zero. */
inline val Matrix3.isSingular
    get() = determinant.isZero()
inline val Matrix3.determinant
    get() = determinant(this)
inline val Matrix3.inverse
    get() = inverse(this)
inline val Matrix3.transpose
    get() = transpose(this)

inline val Matrix3.isIdentity: Boolean
    get() = this == Matrix3.identity
/** Whether all elements are zero except. */
inline val Matrix3.isZero: Boolean
    get() = this == Matrix3.zero
/** Whether this matrix is orthogonal (i.e., all rows (or columns) are orthonormal between each other). */
val Matrix3.isOrthogonal: Boolean
    get() {
        val a = this[0]
        val b = this[1]
        val c = this[2]

        if(!a.isUnit || !b.isUnit || !c.isUnit) {
            return false
        }

        return orthogonal(a, b) && orthogonal(a, c) && orthogonal(b, c)
    }

inline operator fun Matrix3.unaryPlus() = this
inline operator fun Matrix3.unaryMinus() = Matrix3(-e00, -e01, -e02, -e10, -e11, -e12, -e20, -e21, -e22)
inline operator fun Matrix3.times(scalar: Float) = scale(this, scalar)
inline operator fun Matrix3.div(scalar: Float) = times(1 / scalar)
inline operator fun Matrix3.plus(other: Matrix3) = add(this, other)
inline operator fun Matrix3.minus(other: Matrix3) = subtract(this, other)
inline operator fun Matrix3.times(other: Matrix3) = multiply(this, other)
inline operator fun Matrix3.times(vector: Vector2) = multiply(this, vector)
inline operator fun Matrix3.times(vector: Vector3) = multiply(this, vector)

class Matrix4(val e00: Float, val e01: Float, val e02: Float, val e03: Float, val e10: Float, val e11: Float, val e12: Float, val e13: Float, val e20: Float, val e21: Float, val e22: Float, val e23: Float, val e30: Float, val e31: Float, val e32: Float, val e33: Float) {
    constructor() : this(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
    constructor(other: Matrix4) : this(other.e00, other.e01, other.e02, other.e03, other.e10, other.e11, other.e12, other.e13, other.e20, other.e21, other.e22, other.e23, other.e30, other.e31, other.e32, other.e33)
    constructor(c0: Vector4, c1: Vector4, c2: Vector4, c3: Vector4) : this(c0.x, c1.x, c2.x, c3.x, c0.y, c1.y, c2.y, c3.y, c0.z, c1.z, c2.z, c3.z, c0.w, c1.w, c2.w, c3.w)

    internal val components by lazy { floatArrayOf(e00, e01, e02, e03, e10, e11, e12, e13, e20, e21, e22, e23, e30, e31, e32, e33) }

    inline operator fun get(column: Int) = when(column) {
        0 -> Vector4(e00, e10, e20, e30)
        1 -> Vector4(e01, e11, e21, e31)
        2 -> Vector4(e02, e12, e22, e32)
        3 -> Vector4(e02, e12, e22, e33)
        else -> throw IllegalArgumentException("column must be in 0..3")
    }
    inline operator fun get(row: Int, column: Int) = this[column][row]

    inline fun invoke(column: Int) = get(column - 1)
    inline fun invoke(row: Int, column: Int) = get(row - 1, column - 1)

    override fun toString() = "{${this[0]} | ${this[1]} | ${this[2]} | ${this[3]}}"

    companion object {
        val identity = Matrix4()
        val zero = Matrix4(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    }
}

/** Whether its determinant is zero. */
inline val Matrix4.isSingular
    get() = if(isProjection) {
        (e22 * e33 - e23 * e32).isZero()
    } else {
        determinant.isZero()
    }
inline val Matrix4.determinant
    get() = determinant(this)
inline val Matrix4.inverse
    get() = inverse(this)
inline val Matrix4.transpose
    get() = transpose(this)

inline val Matrix4.isIdentity: Boolean
    get() = this == Matrix4.identity
/** Whether all elements are zero except. */
inline val Matrix4.isZero: Boolean
    get() = this == Matrix4.zero
/** Whether the last row is (0, 0, 0, 1) */
val Matrix4.isTransformation: Boolean
    get() = e30.isZero() && e31.isZero() && e32.isZero() && e33.isOne()
/** Whether all elements are zero except e00, e11, e22, e23, e32 and e33. */
val Matrix4.isProjection: Boolean
    get() = e01.isZero() && e02.isZero() && e03.isZero() &&
            e10.isZero() && e12.isZero() && e13.isZero() &&
            e20.isZero() && e21.isZero() &&
            e30.isZero() && e31.isZero()
/** Whether this matrix is orthogonal (i.e., all rows (or columns) are orthonormal between each other). */
val Matrix4.isOrthogonal: Boolean
    get() {
        val a = this[0]
        val b = this[1]
        val c = this[2]
        val d = this[3]

        if(!a.isUnit || !b.isUnit || !c.isUnit || !d.isUnit) {
            return false
        }

        return orthogonal(a, b) && orthogonal(a, c) && orthogonal(a, d) && orthogonal(b, c) && orthogonal(b, d) && orthogonal(c, d)
    }

inline operator fun Matrix4.unaryPlus() = this
inline operator fun Matrix4.unaryMinus() = Matrix4(-e00, -e01, -e02, -e03, -e10, -e11, -e12, -e13, -e20, -e21, -e22, -e23, -e30, -e31, -e32, -e33)
inline operator fun Matrix4.times(scalar: Float) = scale(this, scalar)
inline operator fun Matrix4.div(scalar: Float) = times(1 / scalar)
inline operator fun Matrix4.plus(other: Matrix4) = add(this, other)
inline operator fun Matrix4.minus(other: Matrix4) = subtract(this, other)
inline operator fun Matrix4.times(other: Matrix4) = multiply(this, other)
inline operator fun Matrix4.times(vector: Vector2) = multiply(this, vector)
inline operator fun Matrix4.times(vector: Vector3) = multiply(this, vector)
inline operator fun Matrix4.times(vector: Vector4) = multiply(this, vector)
inline operator fun Matrix4.times(point: Point2D) = multiply(this, point)
inline operator fun Matrix4.times(point: Point3D) = multiply(this, point)

// ---------------------------------------------------------------------------------------------------------------------

inline fun determinant(scalar: Float) = scalar

inline fun determinant(matrix: Matrix2) = matrix.e00 * matrix.e11 - matrix.e01 * matrix.e10

inline fun determinant(matrix: Matrix3) = matrix.e00 * (matrix.e11 * matrix.e22 - matrix.e12 * matrix.e21) -
        matrix.e01 * (matrix.e10 * matrix.e22 - matrix.e12 * matrix.e20) +
        matrix.e02 * (matrix.e10 * matrix.e21 - matrix.e11 * matrix.e20)

inline fun determinant(matrix: Matrix4) = when {
    matrix.isTransformation -> matrix.e00 * (matrix.e11 * matrix.e22 - matrix.e12 * matrix.e21) - matrix.e01 * (matrix.e10 * matrix.e22 - matrix.e12 * matrix.e20) + matrix.e02 * (matrix.e10 * matrix.e21 - matrix.e11 * matrix.e20)
    matrix.isProjection -> matrix.e00 * (matrix.e11 * (matrix.e22 * matrix.e33 - matrix.e23 * matrix.e32) - matrix.e12 * (matrix.e23 * matrix.e31 - matrix.e21 * matrix.e33) + matrix.e13 * (matrix.e21 * matrix.e32 - matrix.e22 * matrix.e31))
    else -> {
        val d0 = matrix.e11 * (matrix.e22 * matrix.e33 - matrix.e23 * matrix.e32) - matrix.e12 * (matrix.e21 * matrix.e33 - matrix.e23 * matrix.e31) + matrix.e13 * (matrix.e21 * matrix.e32 - matrix.e22 * matrix.e31)
        val d1 = matrix.e10 * (matrix.e22 * matrix.e33 - matrix.e23 * matrix.e32) - matrix.e12 * (matrix.e20 * matrix.e33 - matrix.e23 * matrix.e30) + matrix.e13 * (matrix.e20 * matrix.e32 - matrix.e22 * matrix.e30)
        val d2 = matrix.e10 * (matrix.e21 * matrix.e33 - matrix.e23 * matrix.e31) - matrix.e11 * (matrix.e20 * matrix.e33 - matrix.e23 * matrix.e30) + matrix.e13 * (matrix.e20 * matrix.e31 - matrix.e21 * matrix.e30)
        val d3 = matrix.e10 * (matrix.e21 * matrix.e32 - matrix.e22 * matrix.e31) - matrix.e11 * (matrix.e20 * matrix.e32 - matrix.e22 * matrix.e30) + matrix.e12 * (matrix.e20 * matrix.e31 - matrix.e21 * matrix.e30)

        matrix.e00 * d0 - matrix.e01 * d1 + matrix.e02 * d2 - matrix.e03 * d3
    }
}

/** Negates each component of [matrix]. */
inline fun negate(matrix: Matrix2) = Matrix2(
        -matrix.e00, -matrix.e01,
        -matrix.e10, -matrix.e11
)
/** Negates each component of [matrix]. */
inline fun negate(matrix: Matrix3) = Matrix3(
        -matrix.e00, -matrix.e01, -matrix.e02,
        -matrix.e10, -matrix.e11, -matrix.e12,
        -matrix.e20, -matrix.e21, -matrix.e22
)

/** Negates each component of [matrix]. */
inline fun negate(matrix: Matrix4) = Matrix4(
        -matrix.e00, -matrix.e01, -matrix.e02, -matrix.e03,
        -matrix.e10, -matrix.e11, -matrix.e12, -matrix.e13,
        -matrix.e20, -matrix.e21, -matrix.e22, -matrix.e23,
        -matrix.e30, -matrix.e31, -matrix.e32, -matrix.e33
)

/** Transposes [matrix]. */
inline fun transpose(matrix: Matrix2) = Matrix2(
        matrix.e00, matrix.e10,
        matrix.e01, matrix.e11
)

/** Transposes [matrix]. */
inline fun transpose(matrix: Matrix3) = Matrix3(
        matrix.e00, matrix.e10, matrix.e20,
        matrix.e01, matrix.e11, matrix.e21,
        matrix.e02, matrix.e12, matrix.e22
)

/** Transposes [matrix]. */
inline fun transpose(matrix: Matrix4) = Matrix4(
        matrix.e00, matrix.e10, matrix.e20, matrix.e30,
        matrix.e01, matrix.e11, matrix.e21, matrix.e31,
        matrix.e02, matrix.e12, matrix.e22, matrix.e32,
        matrix.e03, matrix.e13, matrix.e23, matrix.e33
)

/** Inverts [matrix]. */
fun inverse(matrix: Matrix2) = Matrix2(matrix.e11, -matrix.e01, -matrix.e10, matrix.e00) / determinant(matrix)

/** Inverts [matrix]. */
fun inverse(matrix: Matrix3) : Matrix3 {
    val a = matrix[0]
    val b = matrix[1]
    val c = matrix[2]

    var r0 = b X c
    var r1 = c X a
    var r2 = a X b

    val invDet = 1 / dot(r2, c)

    r0 *= invDet
    r1 *= invDet
    r2 *= invDet

    return Matrix3(r0, r1, r2).transpose
}

/** Inverts [matrix]. */
fun inverse(matrix: Matrix4) = when {
    matrix.isIdentity -> matrix
    matrix.isOrthogonal -> transpose(matrix) // if Matrix is orthogonal then inv(Matrix) = transpose(Matrix)
    matrix.isTransformation -> {
        val a = Vector3(matrix.e00, matrix.e10, matrix.e20)
        val b = Vector3(matrix.e01, matrix.e11, matrix.e21)
        val c = Vector3(matrix.e02, matrix.e12, matrix.e22)
        val d = Vector3(matrix.e03, matrix.e13, matrix.e23)

        // s = a.cross(b)
        var s = a X b
        // t = c.cross(d)
        var t = c X d
        // u = a.scale(y) - b.scale(x) = 0
        // v = c.scale(w) - d.scale(z) = c

        // det = s.dot(v) + t.dot(u) = s.dot(c)
        val det = dot(s, c)
        if(det.isZero()) {
            throw SingularMatrixException("matrix's determinant is 0 and the matrix is non invertible.")
        }
        val invDet = 1 / det

        s *= invDet
        t *= invDet

        val v = c * invDet

        // r0 = b.cross(v) + t.scale(y) = b.cross(v)
        val r0 = b X v
        // r1 = v.cross(a) - t.scale(x) = v.cross(a)
        val r1 = v X a
        // r2 = d.cross(u) + s.scale(w) = s
        // r3 = u.cross(c) - s.scale(z) = 0

        Matrix4(
                r0.x, r0.y, r0.z, -dot(b, t),
                r1.x, r1.y, r1.z, dot(a, t),
                s.x, s.y, s.z, -dot(d, s),
                0.0f, 0.0f, 0.0f, 1.0f
        )
    }
    matrix.isProjection -> {
        // a = (e00, 0, 0)
        // b = (0, e11, 0)
        // c = (0, 0, e22)
        // d = (0, 0, e23)

        // x = 0
        // y = 0
        // z = e32
        // w = e33

        // s = a.cross(b) = (0, 0, a.x * b.y) = (0, 0, e00 * e11)
        // t = c.cross(d) = 0
        // u = a.scale(y) - b.scale(x) = 0 - 0 = 0
        // v = c.scale(w) - d.scale(z) = (0, 0, c.z * w - d.z * z) = (0, 0, e22 * e33 - e23 * e32)

        // det = s.dot(v) + t.dot(u) = s.dot(v) + 0 = s.dot(v) = s.z * v.z = (e00 * e11) * (e22 * e33 - e23 * e32)
        // invDet = 1 / det

        // s = (0, 0, 1 / (e22 * e33 - e23 * e32))
        // t = 0
        // u = 0
        // v = (0, 0, 1 / (e00 * e11))

        // r0 = b.cross(v) + t.scale(y) = b.cross(v) = (b.y * v.z, 0, 0) = (e11 / (e00 * e11), 0, 0) = (1 / e00, 0, 0)
        // r1 = v.cross(a) - t.scale(x) = v.cross(a) = (0, v.z * a.x, 0) = (0, e00 / (e00 * e11), 0) = (0, 1 / e11, 0)
        // r2 = d.cross(u) + s.scale(w) = s.scale(w) = (0, 0, e33 / (e22 * e33 - e23 * e32))
        // r3 = u.cross(c) - s.scale(z) = -s.scale(z) = (0, 0, -e32 / (e22 * e33 - e23 * e32))

        val denominator = matrix.e22 * matrix.e33 - matrix.e23 * matrix.e32
        if(denominator.isZero()) {
            throw SingularMatrixException("matrix's determinant is 0 and the matrix is non invertible.")
        }
        val invDenominator = 1 / denominator

        // e00 = r0.x
        val e00 = 1 / matrix.e00
        // e01 = r0.y = 0
        // e02 = r0.z = 0
        // e03 = -b.dot(t) = 0
        // e10 = r1.x = 0
        // e11 = r1.y
        val e11 = 1 / matrix.e11
        // e12 = r1.z = 0
        // e13 = a.dot(t) = 0
        // e20 = r2.x = 0
        // e21 = r2.y = 0
        // e22 = r2.z = e33 / (e22 * e33 - e23 * e32)
        val e22 = matrix.e33 * invDenominator
        // e23 = -d.dot(s) = -e23 / (e22 * e33 - e23 * e32)
        val e23 = -matrix.e23 * invDenominator
        // e30 = r3.x = 0
        // e31 = r3.y = 0
        // e32 = r3.z = -e32 / (e22 * e33 - e23 * e32)
        val e32 = -matrix.e32 * invDenominator
        // e33 = c.dot(s) = e22 / (e22 * e33 - e23 * e32)
        val e33 = matrix.e22 * invDenominator

        Matrix4(
                e00, 0f, 0f, 0f,
                0f, e11, 0f, 0f,
                0f, 0f, e22, e23,
                0f, 0f, e32, e33
        )
    }
    else -> {
        val a = Vector3(matrix.e00, matrix.e10, matrix.e20)
        val b = Vector3(matrix.e01, matrix.e11, matrix.e21)
        val c = Vector3(matrix.e02, matrix.e12, matrix.e22)
        val d = Vector3(matrix.e03, matrix.e13, matrix.e23)

        val x = matrix.e30
        val y = matrix.e31
        val z = matrix.e32
        val w = matrix.e33

        // s = a.cross(b)
        var s = a X b
        // t = c.cross(d)
        var t = c X d
        // u = a.scale(y) - b.scale(x)
        var u = a * y - b * x
        // v = c.scale(w) - d.scale(z)
        var v = c * w - d * z

        // det = s.dot(v) + t.dot(u)
        val det = dot(s, v) + dot(t, u)
        if(det.isZero()) {
            throw SingularMatrixException("matrix's determinant is 0 and the matrix is non invertible.")
        }
        val invDet = 1 / det

        s *= invDet
        t *= invDet
        u *= invDet
        v *= invDet

        // r0 = b.cross(v) + t.scale(y)
        val r0 = b X v + t * y
        // r1 = v.cross(a) - t.scale(x)
        val r1 = v X a - t * x
        // r2 = d.cross(u) + s.scale(w)
        val r2 = d X u + s * w
        // r3 = u.cross(c) - s.scale(z)
        val r3 = u X c - s * z

        Matrix4(
                r0.x, r0.y, r0.z, -dot(b, t),
                r1.x, r1.y, r1.z, dot(a, t),
                r2.x, r2.y, r2.z, -dot(d, s),
                r3.x, r3.y, r3.z, dot(c, s)
        )
    }
}

/** Scales [matrix] (i.e., multiplies each component with [scalar]). */
inline fun scale(matrix: Matrix2, scalar: Float) = Matrix2(
        matrix.e00 * scalar, matrix.e01 * scalar,
        matrix.e10 * scalar, matrix.e11 * scalar
)

/** Scales [matrix] (i.e., multiplies each component with [scalar]). */
inline fun scale(matrix: Matrix3, scalar: Float) = Matrix3(
        matrix.e00 * scalar, matrix.e01 * scalar, matrix.e02 * scalar,
        matrix.e10 * scalar, matrix.e11 * scalar, matrix.e12 * scalar,
        matrix.e20 * scalar, matrix.e21 * scalar, matrix.e22 * scalar
)

/** Scales [matrix] (i.e., multiplies each component with [scalar]). */
inline fun scale(matrix: Matrix4, scalar: Float) = Matrix4(
        matrix.e00 * scalar, matrix.e01 * scalar, matrix.e02 * scalar, matrix.e03 * scalar,
        matrix.e10 * scalar, matrix.e11 * scalar, matrix.e12 * scalar, matrix.e13 * scalar,
        matrix.e20 * scalar, matrix.e21 * scalar, matrix.e22 * scalar, matrix.e23 * scalar,
        matrix.e30 * scalar, matrix.e31 * scalar, matrix.e32 * scalar, matrix.e33 * scalar
)

/** Adds [b] to [a]. */
inline fun add(a: Matrix2, b: Matrix2) = Matrix2(
        a.e00 + b.e00, a.e01 + b.e01,
        a.e10 + b.e10, a.e11 + b.e11
)

/** Adds [b] to [a]. */
inline fun add(a: Matrix3, b: Matrix3) = Matrix3(
        a.e00 + b.e00, a.e01 + b.e01, a.e02 + b.e02,
        a.e10 + b.e10, a.e11 + b.e11, a.e12 + b.e12,
        a.e20 + b.e20, a.e21 + b.e21, a.e22 + b.e22
)

/** Adds [b] to [a]. */
inline fun add(a: Matrix4, b: Matrix4) = Matrix4(
        a.e00 + b.e00, a.e01 + b.e01, a.e02 + b.e02, a.e03 + b.e03,
        a.e10 + b.e10, a.e11 + b.e11, a.e12 + b.e12, a.e13 + b.e13,
        a.e20 + b.e20, a.e21 + b.e21, a.e22 + b.e22, a.e23 + b.e23,
        a.e30 + b.e30, a.e31 + b.e31, a.e32 + b.e32, a.e33 + b.e33
)

/** Subtracts [b] from [a].*/
inline fun subtract(a: Matrix2, b: Matrix2) = Matrix2(
        a.e00 - b.e00, a.e01 - b.e01,
        a.e10 - b.e10, a.e11 - b.e11
)

/** Subtracts [b] from [a].*/
inline fun subtract(a: Matrix3, b: Matrix3) = Matrix3(
        a.e00 - b.e00, a.e01 - b.e01, a.e02 - b.e02,
        a.e10 - b.e10, a.e11 - b.e11, a.e12 - b.e12,
        a.e20 - b.e20, a.e21 - b.e21, a.e22 - b.e22
)

/** Subtracts [b] from [a].*/
inline fun subtract(a: Matrix4, b: Matrix4) = Matrix4(
        a.e00 - b.e00, a.e01 - b.e01, a.e02 - b.e02, a.e03 - b.e03,
        a.e10 - b.e10, a.e11 - b.e11, a.e12 - b.e12, a.e13 - b.e13,
        a.e20 - b.e20, a.e21 - b.e21, a.e22 - b.e22, a.e23 - b.e23,
        a.e30 - b.e30, a.e31 - b.e31, a.e32 - b.e32, a.e33 - b.e33
)

/** Multiplies [a] with [b]. */
fun multiply(a: Matrix2, b: Matrix2) : Matrix2 {
    if(a.isIdentity) {
        return b
    }

    if(b.isIdentity) {
        return a
    }

    val e00 = a.e00 * b.e00 + a.e01 * b.e10
    val e01 = a.e00 * b.e01 + a.e01 * b.e11

    val e10 = a.e10 * b.e00 + a.e11 * b.e10
    val e11 = a.e10 * b.e01 + a.e11 * b.e11

    return Matrix2(
            e00, e01,
            e10, e11
    )
}

/** Multiplies [a] with [b]. */
fun multiply(a: Matrix3, b: Matrix3) : Matrix3 {
    if(a.isIdentity) {
        return b
    }

    if(b.isIdentity) {
        return a
    }

    val e00 = a.e00 * b.e00 + a.e01 * b.e10 + a.e02 * b.e20
    val e01 = a.e00 * b.e01 + a.e01 * b.e11 + a.e02 * b.e21
    val e02 = a.e00 * b.e02 + a.e01 * b.e12 + a.e02 * b.e22

    val e10 = a.e10 * b.e00 + a.e11 * b.e10 + a.e12 * b.e20
    val e11 = a.e10 * b.e01 + a.e11 * b.e11 + a.e12 * b.e21
    val e12 = a.e10 * b.e02 + a.e11 * b.e12 + a.e12 * b.e22

    val e20 = a.e20 * b.e00 + a.e21 * b.e10 + a.e22 * b.e20
    val e21 = a.e20 * b.e01 + a.e21 * b.e11 + a.e22 * b.e21
    val e22 = a.e20 * b.e02 + a.e21 * b.e12 + a.e22 * b.e22

    return Matrix3(
            e00, e01, e02,
            e10, e11, e12,
            e20, e21, e22
    )
}

/** Multiplies [a] with [b]. */
fun multiply(a: Matrix4, b: Matrix4) : Matrix4 {
    if(a.isIdentity) {
        return b
    }

    if(b.isIdentity) {
        return a
    }

    val e00 = a.e00 * b.e00 + a.e01 * b.e10 + a.e02 * b.e20 + a.e03 * b.e30
    val e01 = a.e00 * b.e01 + a.e01 * b.e11 + a.e02 * b.e21 + a.e03 * b.e31
    val e02 = a.e00 * b.e02 + a.e01 * b.e12 + a.e02 * b.e22 + a.e03 * b.e32
    val e03 = a.e00 * b.e03 + a.e01 * b.e13 + a.e02 * b.e23 + a.e03 * b.e33

    val e10 = a.e10 * b.e00 + a.e11 * b.e10 + a.e12 * b.e20 + a.e13 * b.e30
    val e11 = a.e10 * b.e01 + a.e11 * b.e11 + a.e12 * b.e21 + a.e13 * b.e31
    val e12 = a.e10 * b.e02 + a.e11 * b.e12 + a.e12 * b.e22 + a.e13 * b.e32
    val e13 = a.e10 * b.e03 + a.e11 * b.e13 + a.e12 * b.e23 + a.e13 * b.e33

    val e20 = a.e20 * b.e00 + a.e21 * b.e10 + a.e22 * b.e20 + a.e23 * b.e30
    val e21 = a.e20 * b.e01 + a.e21 * b.e11 + a.e22 * b.e21 + a.e23 * b.e31
    val e22 = a.e20 * b.e02 + a.e21 * b.e12 + a.e22 * b.e22 + a.e23 * b.e32
    val e23 = a.e20 * b.e03 + a.e21 * b.e13 + a.e22 * b.e23 + a.e23 * b.e33

    val e30 = a.e30 * b.e00 + a.e31 * b.e10 + a.e32 * b.e20 + a.e33 * b.e30
    val e31 = a.e30 * b.e01 + a.e31 * b.e11 + a.e32 * b.e21 + a.e33 * b.e31
    val e32 = a.e30 * b.e02 + a.e31 * b.e12 + a.e32 * b.e22 + a.e33 * b.e32
    val e33 = a.e30 * b.e03 + a.e31 * b.e13 + a.e32 * b.e23 + a.e33 * b.e33

    return Matrix4(
            e00, e01, e02, e03,
            e10, e11, e12, e13,
            e20, e21, e22, e23,
            e30, e31, e32, e33
    )
}

/** Multiplies [matrix] with [vector]. */
fun multiply(matrix: Matrix2, vector: Vector2) : Vector2 {
    if(matrix.isIdentity) {
        return vector
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y

    return Vector2(nx, ny)
}

/** Multiplies [matrix] with [vector]. */
fun multiply(matrix: Matrix3, vector: Vector2) : Vector3 {
    if(matrix.isIdentity) {
        return Vector3(vector)
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y
    val nz = matrix.e20 * vector.x + matrix.e21 * vector.y

    return Vector3(nx, ny, nz)
}

/** Multiplies [matrix] with [vector]. */
fun multiply(matrix: Matrix3, vector: Vector3) : Vector3 {
    if(matrix.isIdentity) {
        return vector
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y + matrix.e02 * vector.z
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y + matrix.e12 * vector.z
    val nz = matrix.e20 * vector.x + matrix.e21 * vector.y + matrix.e22 * vector.z

    return Vector3(nx, ny, nz)
}

/** Multiplies [matrix] with [vector]. */
fun multiply(matrix: Matrix4, vector: Vector2) : Vector4 {
    if(matrix.isIdentity) {
        return Vector4(vector)
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y
    val nz = matrix.e20 * vector.x + matrix.e21 * vector.y
    val nw = matrix.e30 * vector.x + matrix.e31 * vector.y

    return Vector4(nx, ny, nz, nw)
}

/** Multiplies [matrix] with [vector]. */
fun multiply(matrix: Matrix4, vector: Vector3) : Vector4 {
    if(matrix.isIdentity) {
        return Vector4(vector)
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y + matrix.e02 * vector.z
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y + matrix.e12 * vector.z
    val nz = matrix.e20 * vector.x + matrix.e21 * vector.y + matrix.e22 * vector.z
    val nw = matrix.e30 * vector.x + matrix.e31 * vector.y + matrix.e32 * vector.z

    return Vector4(nx, ny, nz, nw)
}

/** Multiplies [matrix] with [vector]. */
fun multiply(matrix: Matrix4, vector: Vector4) : Vector4 {
    if(matrix.isIdentity) {
        return Vector4(vector)
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y + matrix.e02 * vector.z + matrix.e03 * vector.w
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y + matrix.e12 * vector.z + matrix.e13 * vector.w
    val nz = matrix.e20 * vector.x + matrix.e21 * vector.y + matrix.e22 * vector.z + matrix.e23 * vector.w
    val nw = matrix.e30 * vector.x + matrix.e31 * vector.y + matrix.e32 * vector.z + matrix.e33 * vector.w

    return Vector4(nx, ny, nz, nw)
}

/** Multiplies [matrix] with [point]. [point]'s w component is 1. */
fun multiply(matrix: Matrix4, point: Point2D) = multiply(matrix, Vector4(point.x, point.y, 0f, 1f))

/** Multiplies [matrix] with [point]. [point]'s w component is 1. */
fun multiply(matrix: Matrix4, point: Point3D) = multiply(matrix, Vector4(point.x, point.y, point.z, 1f))

/** Returns the submatrix of [matrix] that excludes [row] and [column]. */
fun submatrix(matrix: Matrix2, row: Int, column: Int) : Float {
    require(row in 0..1) { "row has to be in ${0..1}. It was $row." }
    require(column in 0..1) { "column has to be in ${0..1}. It was $column." }

    val r = if(row == 0) 1 else 0
    val c = if(column == 0) 1 else 0

    return matrix[r, c]
}

/** Returns the submatrix of [matrix] that excludes [row] and [column]. */
fun submatrix(matrix: Matrix3, row: Int, column: Int) : Matrix2 {
    val r0: Vector2
    val r1: Vector2
    val r2: Vector2
    when(column) {
        0 -> {
            r0 = Vector2(matrix.e01, matrix.e02)
            r1 = Vector2(matrix.e11, matrix.e12)
            r2 = Vector2(matrix.e21, matrix.e22)
        }
        1 -> {
            r0 = Vector2(matrix.e00, matrix.e02)
            r1 = Vector2(matrix.e10, matrix.e12)
            r2 = Vector2(matrix.e20, matrix.e22)
        }
        2 -> {
            r0 = Vector2(matrix.e00, matrix.e01)
            r1 = Vector2(matrix.e10, matrix.e11)
            r2 = Vector2(matrix.e20, matrix.e21)
        }
        else -> throw IllegalArgumentException("column has to be in ${0..2}. It was $column.")
    }
    val c0: Vector2
    val c1: Vector2
    when(row) {
        0 -> {
            c0 = Vector2(r1.x, r2.x)
            c1 = Vector2(r1.y, r2.y)
        }
        1 -> {
            c0 = Vector2(r0.x, r2.x)
            c1 = Vector2(r0.y, r2.y)
        }
        2 -> {
            c0 = Vector2(r0.x, r1.x)
            c1 = Vector2(r0.y, r1.y)
        }
        else -> throw IllegalArgumentException("row has to be in ${0..2}. It was $row.")
    }

    return Matrix2(c0, c1)
}

/** Returns the submatrix of [matrix] that excludes [row] and [column]. */
fun submatrix(matrix: Matrix4, row: Int, column: Int) : Matrix3 {
    val r0: Vector3
    val r1: Vector3
    val r2: Vector3
    val r3: Vector3
    when(column) {
        0 -> {
            r0 = Vector3(matrix.e01, matrix.e02, matrix.e03)
            r1 = Vector3(matrix.e11, matrix.e12, matrix.e13)
            r2 = Vector3(matrix.e21, matrix.e22, matrix.e23)
            r3 = Vector3(matrix.e31, matrix.e32, matrix.e33)
        }
        1 -> {
            r0 = Vector3(matrix.e00, matrix.e02, matrix.e03)
            r1 = Vector3(matrix.e10, matrix.e12, matrix.e13)
            r2 = Vector3(matrix.e20, matrix.e22, matrix.e23)
            r3 = Vector3(matrix.e30, matrix.e32, matrix.e33)
        }
        2 -> {
            r0 = Vector3(matrix.e00, matrix.e01, matrix.e03)
            r1 = Vector3(matrix.e10, matrix.e11, matrix.e13)
            r2 = Vector3(matrix.e20, matrix.e21, matrix.e23)
            r3 = Vector3(matrix.e30, matrix.e31, matrix.e33)
        }
        3 -> {
            r0 = Vector3(matrix.e00, matrix.e01, matrix.e02)
            r1 = Vector3(matrix.e10, matrix.e11, matrix.e12)
            r2 = Vector3(matrix.e20, matrix.e21, matrix.e22)
            r3 = Vector3(matrix.e30, matrix.e31, matrix.e32)
        }
        else -> throw IllegalArgumentException("column has to be in ${0..3}. It was $column.")
    }
    val c0: Vector3
    val c1: Vector3
    val c2: Vector3
    when(row) {
        0 -> {
            c0 = Vector3(r1.x, r2.x, r3.x)
            c1 = Vector3(r1.y, r2.y, r3.y)
            c2 = Vector3(r1.z, r2.z, r3.z)
        }
        1 -> {
            c0 = Vector3(r0.x, r2.x, r3.x)
            c1 = Vector3(r0.y, r2.y, r3.y)
            c2 = Vector3(r0.z, r2.z, r3.z)
        }
        2 -> {
            c0 = Vector3(r0.x, r1.x, r3.x)
            c1 = Vector3(r0.y, r1.y, r3.y)
            c2 = Vector3(r0.z, r1.z, r3.z)
        }
        3 -> {
            c0 = Vector3(r0.x, r1.x, r2.x)
            c1 = Vector3(r0.y, r1.y, r2.y)
            c2 = Vector3(r0.z, r1.z, r2.z)
        }
        else -> throw IllegalArgumentException("row has to be in ${0..3}. It was $row.")
    }

    return Matrix3(c0, c1, c2)
}