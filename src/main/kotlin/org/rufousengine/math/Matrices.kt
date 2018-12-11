@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.math

import java.lang.IllegalArgumentException

val Matrix.isIdentity: Boolean
    get() {
        for(row in 0 until dimensions) {
            for(column in 0 until dimensions) {
                if((row == column && !this[row, column].isOne()) || (row != column && !this[row, column].isZero())) {
                    return false
                }
            }
        }

        return true
    }

/** Whether all components are zero. */
inline val Matrix.isZero: Boolean
    get() = components.all { it.isZero() }

// ---

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

inline fun zero(matrix: Matrix2) = matrix.set(
        0f, 0f,
        0f, 0f
)
inline fun zero(matrix: Matrix3) = matrix.set(
        0f, 0f, 0f,
        0f, 0f, 0f,
        0f, 0f, 0f
)
inline fun zero(matrix: Matrix4) = matrix.set(
        0f, 0f, 0f, 0f,
        0f, 0f, 0f, 0f,
        0f, 0f, 0f, 0f,
        0f, 0f, 0f, 0f
)

inline fun identity(matrix: Matrix2) = matrix.set(
        1f, 0f,
        0f, 1f
)
inline fun identity(matrix: Matrix3) = matrix.set(
        1f, 0f, 0f,
        0f, 1f, 0f,
        0f, 0f, 1f
)
inline fun identity(matrix: Matrix4) = matrix.set(
        1f, 0f, 0f, 0f,
        0f, 1f, 0f, 0f,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f
)

/**
 * Negates each component of [matrix] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun negate(matrix: Matrix2, out: Matrix2 = Matrix2()) = out.set(
        -matrix.e00, -matrix.e01,
        -matrix.e10, -matrix.e11
)
/**
 * Negates each component of [matrix] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun negate(matrix: Matrix3, out: Matrix3 = Matrix3()) = out.set(
        -matrix.e00, -matrix.e01, -matrix.e02,
        -matrix.e10, -matrix.e11, -matrix.e12,
        -matrix.e20, -matrix.e21, -matrix.e22
)
/**
 * Negates each component of [matrix] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun negate(matrix: Matrix4, out: Matrix4 = Matrix4()) = out.set(
        -matrix.e00, -matrix.e01, -matrix.e02, -matrix.e03,
        -matrix.e10, -matrix.e11, -matrix.e12, -matrix.e13,
        -matrix.e20, -matrix.e21, -matrix.e22, -matrix.e23,
        -matrix.e30, -matrix.e31, -matrix.e32, -matrix.e33
)

/**
 * Transposes [matrix] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun transpose(matrix: Matrix2, out: Matrix2 = Matrix2()) = out.set(
        matrix.e00, matrix.e10,
        matrix.e01, matrix.e11
)
/**
 * Transposes [matrix] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun transpose(matrix: Matrix3, out: Matrix3 = Matrix3()) = out.set(
        matrix.e00, matrix.e10, matrix.e20,
        matrix.e01, matrix.e11, matrix.e21,
        matrix.e02, matrix.e12, matrix.e22
)
/**
 * Transposes [matrix] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun transpose(matrix: Matrix4, out: Matrix4 = Matrix4()) = out.set(
        matrix.e00, matrix.e10, matrix.e20, matrix.e30,
        matrix.e01, matrix.e11, matrix.e21, matrix.e31,
        matrix.e02, matrix.e12, matrix.e22, matrix.e32,
        matrix.e03, matrix.e13, matrix.e23, matrix.e33
)

/**
 * Inverts [matrix] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
fun inverse(matrix: Matrix2, out: Matrix2 = Matrix2()) : Matrix2 {
    val invDet = 1 / determinant(matrix)
    out.set(matrix.e11, -matrix.e01, -matrix.e10, matrix.e00)
    out.scale(invDet)

    return out
}
/**
 * Inverts [matrix] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
fun inverse(matrix: Matrix3, out: Matrix3 = Matrix3()) : Matrix3 {
    matrix.getColumn0(a3)
    matrix.getColumn1(b3)
    matrix.getColumn2(c3)

    cross(b3, c3, r0)
    cross(c3, a3, r1)
    cross(a3, b3, r2)

    val invDet = 1 / dot(r2, c3)

    r0 *= invDet
    r1 *= invDet
    r2 *= invDet

    return out.set(r0, r1, r2).transpose()
}
/**
 * Inverts [matrix] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
fun inverse(matrix: Matrix4, out: Matrix4 = Matrix4()) = when {
    matrix.isIdentity -> out.set(matrix)
    matrix.isOrthogonal -> transpose(matrix, out) // if Matrix is orthogonal then inv(Matrix) = transpose(Matrix)
    matrix.isTransformation -> {
        val a = a3
        val b = b3
        val c = c3
        val d = d3
        val s = s
        val t = t
        val v = v
        val r0 = r0
        val r1 = r1

        a.set(matrix.e00, matrix.e10, matrix.e20)
        b.set(matrix.e01, matrix.e11, matrix.e21)
        c.set(matrix.e02, matrix.e12, matrix.e22)
        d.set(matrix.e03, matrix.e13, matrix.e23)

        // s = a.cross(b)
        cross(a, b, s)
        // t = c.cross(d)
        cross(c, d, t)
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

        scale(c, invDet, v)

        // r0 = b.cross(v) + t.scale(y) = b.cross(v)
        cross(b, v, r0)
        // r1 = v.cross(a) - t.scale(x) = v.cross(a)
        cross(v, a, r1)
        // r2 = d.cross(u) + s.scale(w) = s
        // r3 = u.cross(c) - s.scale(z) = 0

        out.set(
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

        out.set(
                e00, 0f, 0f, 0f,
                0f, e11, 0f, 0f,
                0f, 0f, e22, e23,
                0f, 0f, e32, e33
        )
    }
    else -> {
        val a = a3
        val b = b3
        val c = c3
        val d = d3
        val s = s
        val t = t
        val u = u
        val v = v
        val r0 = r0
        val r1 = r1
        val r2 = r2
        val r3 = r3
        val t0 = t0
        val t1 = t1

        a.set(matrix.e00, matrix.e10, matrix.e20)
        b.set(matrix.e01, matrix.e11, matrix.e21)
        c.set(matrix.e02, matrix.e12, matrix.e22)
        d.set(matrix.e03, matrix.e13, matrix.e23)

        val x = matrix.e30
        val y = matrix.e31
        val z = matrix.e32
        val w = matrix.e33

        // s = a.cross(b)
        cross(a, b, s)
        // t = c.cross(d)
        cross(c, d, t)
        // u = a.scale(y) - b.scale(x)
        scale(a, y, t0)
        scale(b, x, t1)
        subtract(t0, t1, u)
        // v = c.scale(w) - d.scale(z)
        scale(c, w, t0)
        scale(d, z, t1)
        subtract(t0, t1, v)

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
        cross(b, v, t0)
        scale(t, y, t1)
        add(t0, t1, r0)
        // r1 = v.cross(a) - t.scale(x)
        cross(v, a, t0)
        scale(t, x, t1)
        subtract(t0, t1, r1)
        // r2 = d.cross(u) + s.scale(w)
        cross(d, u, t0)
        scale(s, w, t1)
        add(t0, t1, r2)
        // r3 = u.cross(c) - s.scale(z)
        cross(u, c, t0)
        scale(s, z, t1)
        subtract(t0, t1, r3)

        out.set(
                r0.x, r0.y, r0.z, -dot(b, t),
                r1.x, r1.y, r1.z, dot(a, t),
                r2.x, r2.y, r2.z, -dot(d, s),
                r3.x, r3.y, r3.z, dot(c, s)
        )
    }
}

/**
 * Scales [matrix] (i.e., multiplies each component with [scalar]) and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun scale(matrix: Matrix2, scalar: Float, out: Matrix2 = Matrix2()) = out.set(
        matrix.e00 * scalar, matrix.e01 * scalar,
        matrix.e10 * scalar, matrix.e11 * scalar
)
/**
 * Scales [matrix] (i.e., multiplies each component with [scalar]) and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun scale(matrix: Matrix3, scalar: Float, out: Matrix3 = Matrix3()) = out.set(
        matrix.e00 * scalar, matrix.e01 * scalar, matrix.e02 * scalar,
        matrix.e10 * scalar, matrix.e11 * scalar, matrix.e12 * scalar,
        matrix.e20 * scalar, matrix.e21 * scalar, matrix.e22 * scalar
)
/**
 * Scales [matrix] (i.e., multiplies each component with [scalar]) and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun scale(matrix: Matrix4, scalar: Float, out: Matrix4 = Matrix4()) = out.set(
        matrix.e00 * scalar, matrix.e01 * scalar, matrix.e02 * scalar, matrix.e03 * scalar,
        matrix.e10 * scalar, matrix.e11 * scalar, matrix.e12 * scalar, matrix.e13 * scalar,
        matrix.e20 * scalar, matrix.e21 * scalar, matrix.e22 * scalar, matrix.e23 * scalar,
        matrix.e30 * scalar, matrix.e31 * scalar, matrix.e32 * scalar, matrix.e33 * scalar
)

/**
 * Adds [b] to [a] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun add(a: Matrix2, b: Matrix2, out: Matrix2 = Matrix2()) = out.set(
        a.e00 + b.e00, a.e01 + b.e01,
        a.e10 + b.e10, a.e11 + b.e11
)
/**
 * Adds [b] to [a] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun add(a: Matrix3, b: Matrix3, out: Matrix3 = Matrix3()) = out.set(
        a.e00 + b.e00, a.e01 + b.e01, a.e02 + b.e02,
        a.e10 + b.e10, a.e11 + b.e11, a.e12 + b.e12,
        a.e20 + b.e20, a.e21 + b.e21, a.e22 + b.e22
)
/**
 * Adds [b] to [a] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun add(a: Matrix4, b: Matrix4, out: Matrix4 = Matrix4()) = out.set(
        a.e00 + b.e00, a.e01 + b.e01, a.e02 + b.e02, a.e03 + b.e03,
        a.e10 + b.e10, a.e11 + b.e11, a.e12 + b.e12, a.e13 + b.e13,
        a.e20 + b.e20, a.e21 + b.e21, a.e22 + b.e22, a.e23 + b.e23,
        a.e30 + b.e30, a.e31 + b.e31, a.e32 + b.e32, a.e33 + b.e33
)

/**
 * Subtracts [b] from [a] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun subtract(a: Matrix2, b: Matrix2, out: Matrix2 = Matrix2()) = out.set(
        a.e00 - b.e00, a.e01 - b.e01,
        a.e10 - b.e10, a.e11 - b.e11
)
/**
 * Subtracts [b] from [a] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun subtract(a: Matrix3, b: Matrix3, out: Matrix3 = Matrix3()) = out.set(
        a.e00 - b.e00, a.e01 - b.e01, a.e02 - b.e02,
        a.e10 - b.e10, a.e11 - b.e11, a.e12 - b.e12,
        a.e20 - b.e20, a.e21 - b.e21, a.e22 - b.e22
)
/**
 * Subtracts [b] from [a] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
inline fun subtract(a: Matrix4, b: Matrix4, out: Matrix4 = Matrix4()) = out.set(
        a.e00 - b.e00, a.e01 - b.e01, a.e02 - b.e02, a.e03 - b.e03,
        a.e10 - b.e10, a.e11 - b.e11, a.e12 - b.e12, a.e13 - b.e13,
        a.e20 - b.e20, a.e21 - b.e21, a.e22 - b.e22, a.e23 - b.e23,
        a.e30 - b.e30, a.e31 - b.e31, a.e32 - b.e32, a.e33 - b.e33
)

/**
 * Multiplies [a] with [b] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
fun multiply(a: Matrix2, b: Matrix2, out: Matrix2 = Matrix2()) : Matrix2 {
    if(a.isIdentity) {
        return out.set(b)
    }

    if(b.isIdentity) {
        return out.set(a)
    }

    val e00 = a.e00 * b.e00 + a.e01 * b.e10
    val e01 = a.e00 * b.e01 + a.e01 * b.e11

    val e10 = a.e10 * b.e00 + a.e11 * b.e10
    val e11 = a.e10 * b.e01 + a.e11 * b.e11

    return out.set(
            e00, e01,
            e10, e11
    )
}
/**
 * Multiplies [a] with [b] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
fun multiply(a: Matrix3, b: Matrix3, out: Matrix3 = Matrix3()) : Matrix3 {
    if(a.isIdentity) {
        return out.set(b)
    }

    if(b.isIdentity) {
        return out.set(a)
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

    return out.set(
            e00, e01, e02,
            e10, e11, e12,
            e20, e21, e22
    )
}
/**
 * Multiplies [a] with [b] and stores the result in [out].
 *
 * @return The [out] matrix for chaining.
 */
fun multiply(a: Matrix4, b: Matrix4, out: Matrix4 = Matrix4()) : Matrix4 {
    if(a.isIdentity) {
        return out.set(b)
    }

    if(b.isIdentity) {
        return out.set(a)
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

    return out.set(
            e00, e01, e02, e03,
            e10, e11, e12, e13,
            e20, e21, e22, e23,
            e30, e31, e32, e33
    )
}

/**
 * Multiplies [matrix] with [vector] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun multiply(matrix: Matrix2, vector: Vector2, out: Vector2 = Vector2()) : Vector2 {
    if(matrix.isIdentity) {
        return out.set(vector)
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y

    return out.set(nx, ny)
}
/**
 * Multiplies [matrix] with [vector] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun multiply(matrix: Matrix3, vector: Vector2, out: Vector3 = Vector3()) : Vector3 {
    if(matrix.isIdentity) {
        return out.set(vector)
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y
    val nz = matrix.e20 * vector.x + matrix.e21 * vector.y

    return out.set(nx, ny, nz)
}
/**
 * Multiplies [matrix] with [vector] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun multiply(matrix: Matrix3, vector: Vector3, out: Vector3 = Vector3()) : Vector3 {
    if(matrix.isIdentity) {
        return out.set(vector)
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y + matrix.e02 * vector.z
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y + matrix.e12 * vector.z
    val nz = matrix.e20 * vector.x + matrix.e21 * vector.y + matrix.e22 * vector.z

    return out.set(nx, ny, nz)
}
/**
 * Multiplies [matrix] with [vector] and stores the result in [out]. [vector]'s w component is 0.
 * It ignores the z and w components calculations. Use this method only if [matrix] is a 2D transformation.
 *
 * @return The [out] vector for chaining.
 */
fun multiply(matrix: Matrix4, vector: Vector2, out: Vector2) : Vector2 {
    if(matrix.isIdentity) {
        return out.set(vector)
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y

    return out.set(nx, ny)
}
/**
 * Multiplies [matrix] with [vector] and stores the result in [out]. [vector]'s w component is 0.
 * It ignores the w component calculations. Use this method only if [matrix] is a transformation.
 *
 * @return The [out] vector for chaining.
 */
fun multiply(matrix: Matrix4, vector: Vector2, out: Vector3) : Vector3 {
    if(matrix.isIdentity) {
        return out.set(vector)
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y
    val nz = matrix.e20 * vector.x + matrix.e21 * vector.y

    return out.set(nx, ny, nz)
}
/**
 * Multiplies [matrix] with [vector] and stores the result in [out]. [vector]'s w component is 0.
 *
 * @return The [out] vector for chaining.
 */
fun multiply(matrix: Matrix4, vector: Vector2, out: Vector4 = Vector4()) : Vector4 {
    if(matrix.isIdentity) {
        return out.set(vector)
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y
    val nz = matrix.e20 * vector.x + matrix.e21 * vector.y
    val nw = matrix.e30 * vector.x + matrix.e31 * vector.y

    return out.set(nx, ny, nz, nw)
}
/**
 * Multiplies [matrix] with [vector] and stores the result in [out]. [vector]'s w component is 0.
 * It ignores the w component calculations. Use this method only if [matrix] is a transformation.
 *
 * @return The [out] vector for chaining.
 */
fun multiply(matrix: Matrix4, vector: Vector3, out: Vector3) : Vector3 {
    if(matrix.isIdentity) {
        return out.set(vector)
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y + matrix.e02 * vector.z
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y + matrix.e12 * vector.z
    val nz = matrix.e20 * vector.x + matrix.e21 * vector.y + matrix.e22 * vector.z

    return out.set(nx, ny, nz)
}
/**
 * Multiplies [matrix] with [vector] and stores the result in [out]. [vector]'s w component is 0.
 *
 * @return The [out] vector for chaining.
 */
fun multiply(matrix: Matrix4, vector: Vector3, out: Vector4 = Vector4()) : Vector4 {
    if(matrix.isIdentity) {
        return out.set(vector)
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y + matrix.e02 * vector.z
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y + matrix.e12 * vector.z
    val nz = matrix.e20 * vector.x + matrix.e21 * vector.y + matrix.e22 * vector.z
    val nw = matrix.e30 * vector.x + matrix.e31 * vector.y + matrix.e32 * vector.z

    return out.set(nx, ny, nz, nw)
}
/**
 * Multiplies [matrix] with [vector] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun multiply(matrix: Matrix4, vector: Vector4, out: Vector4 = Vector4()) : Vector4 {
    if(matrix.isIdentity) {
        return out.set(vector)
    }

    val nx = matrix.e00 * vector.x + matrix.e01 * vector.y + matrix.e02 * vector.z + matrix.e03 * vector.w
    val ny = matrix.e10 * vector.x + matrix.e11 * vector.y + matrix.e12 * vector.z + matrix.e13 * vector.w
    val nz = matrix.e20 * vector.x + matrix.e21 * vector.y + matrix.e22 * vector.z + matrix.e23 * vector.w
    val nw = matrix.e30 * vector.x + matrix.e31 * vector.y + matrix.e32 * vector.z + matrix.e33 * vector.w

    return out.set(nx, ny, nz, nw)
}
/**
 * Multiplies [matrix] with [point] and stores the result in [out]. [point]'s w component is 1.
 *
 * @return The [out] vector for chaining.
 */
fun multiply(matrix: Matrix4, point: Point2D, out: Vector4 = Vector4()) : Vector4 {
    if(matrix.isIdentity) {
        return out.set(point.x, point.y, 0f, 0f)
    }

    val nx = matrix.e00 * point.x + matrix.e01 * point.y + matrix.e03
    val ny = matrix.e10 * point.x + matrix.e11 * point.y + matrix.e13
    val nz = matrix.e20 * point.x + matrix.e21 * point.y + matrix.e23
    val nw = matrix.e30 * point.x + matrix.e31 * point.y + matrix.e33

    return out.set(nx, ny, nz, nw)
}
/**
 * Multiplies [matrix] with [point] and stores the result in [out]. [point]'s w component is 1.
 * It ignores the z and w components calculations. Use this method only if [matrix] is a 2D transformation.
 *
 * @return The [out] point for chaining.
 */
fun multiply(matrix: Matrix4, point: Point2D, out: Point2D) : Point2D {
    if(matrix.isIdentity) {
        return out.set(point)
    }

    val nx = matrix.e00 * point.x + matrix.e01 * point.y + matrix.e03
    val ny = matrix.e10 * point.x + matrix.e11 * point.y + matrix.e13

    return out.set(nx, ny)
}
/**
 * Multiplies [matrix] with [point] and stores the result in [out]. [point]'s w component is 1.
 * It ignores the w component calculations. Use this method only if [matrix] is a transformation.
 *
 * @return The [out] point for chaining.
 */
fun multiply(matrix: Matrix4, point: Point2D, out: Point3D) : Point3D {
    if(matrix.isIdentity) {
        return out.set(point)
    }

    val nx = matrix.e00 * point.x + matrix.e01 * point.y + matrix.e03
    val ny = matrix.e10 * point.x + matrix.e11 * point.y + matrix.e13
    val nz = matrix.e20 * point.x + matrix.e21 * point.y + matrix.e23

    return out.set(nx, ny, nz)
}
/**
 * Multiplies [matrix] with [point] and stores the result in [out]. [point]'s w component is 1.
 *
 * @return The [out] vector for chaining.
 */
fun multiply(matrix: Matrix4, point: Point3D, out: Vector4 = Vector4()) : Vector4 {
    if(matrix.isIdentity) {
        return out.set(point.x, point.y, point.z, 0f)
    }

    val nx = matrix.e00 * point.x + matrix.e01 * point.y + matrix.e02 * point.z + matrix.e03
    val ny = matrix.e10 * point.x + matrix.e11 * point.y + matrix.e12 * point.z + matrix.e13
    val nz = matrix.e20 * point.x + matrix.e21 * point.y + matrix.e22 * point.z + matrix.e23
    val nw = matrix.e30 * point.x + matrix.e31 * point.y + matrix.e32 * point.z + matrix.e33

    return out.set(nx, ny, nz, nw)
}
/**
 * Multiplies [matrix] with [point] and stores the result in [out]. [point]'s w component is 1.
 * It ignores the w component calculations. Use this method only if [matrix] is a transformation.
 *
 * @return The [out] point for chaining.
 */
fun multiply(matrix: Matrix4, point: Point3D, out: Point3D) : Point3D {
    if(matrix.isIdentity) {
        return out.set(point)
    }

    val nx = matrix.e00 * point.x + matrix.e01 * point.y + matrix.e02 * point.z + matrix.e03
    val ny = matrix.e10 * point.x + matrix.e11 * point.y + matrix.e12 * point.z + matrix.e13
    val nz = matrix.e20 * point.x + matrix.e21 * point.y + matrix.e22 * point.z + matrix.e23

    return out.set(nx, ny, nz)
}

/**
 * Stores in [out] the submatrix of [matrix] that excludes the [row] and the [column].
 *
 * @return The [out] matrix for chaining.
 */
fun submatrix(matrix: Matrix2, row: Int, column: Int) : Float {
    if(row !in 0..1) {
        throw IllegalArgumentException("row has to be in ${0..1}. It was $row.")
    }
    if(column !in 0..1) {
        throw IllegalArgumentException("column has to be in ${0..1}. It was $column.")
    }

    val r = if(row == 0) 1 else 0
    val c = if(column == 0) 1 else 0

    return matrix[r, c]
}
/**
 * Stores in [out] the submatrix of [matrix] that excludes the [row] and the [column].
 *
 * @return The [out] matrix for chaining.
 */
fun submatrix(matrix: Matrix3, row: Int, column: Int, out: Matrix2 = Matrix2()) : Matrix2 {
    if(row !in 0..2) {
        throw IllegalArgumentException("row has to be in ${0..2}. It was $row.")
    }
    if(column !in 0..2) {
        throw IllegalArgumentException("column has to be in ${0..2}. It was $column.")
    }

    for(i in 0..1) {
        for(j in 0..1) {
            val r = if(i >= row) i + 1 else i
            val c = if(j >= column) j + 1 else j
            out[i, j] = matrix[r, c]
        }
    }

    return out
}
/**
 * Stores in [out] the submatrix of [matrix] that excludes the [row] and the [column].
 *
 * @return The [out] matrix for chaining.
 */
fun submatrix(matrix: Matrix4, row: Int, column: Int, out: Matrix3 = Matrix3()) : Matrix3 {
    if(row !in 0..3) {
        throw IllegalArgumentException("row has to be in ${0..3}. It was $row.")
    }
    if(column !in 0..3) {
        throw IllegalArgumentException("column has to be in ${0..3}. It was $column.")
    }

    for(i in 0..2) {
        for(j in 0..2) {
            val r = if(i >= row) i + 1 else i
            val c = if(j >= column) j + 1 else j
            out[i, j] = matrix[r, c]
        }
    }

    return out
}

// ---

inline operator fun Matrix2.unaryPlus() = this.copy()
inline operator fun Matrix2.unaryMinus() = negate(this, Matrix2())

inline operator fun Matrix2.plus(other: Matrix2) = add(this, other, Matrix2())
inline operator fun Matrix2.minus(other: Matrix2) = subtract(this, other, Matrix2())
inline operator fun Matrix2.times(scalar: Float) = scale(this, scalar, Matrix2())
inline operator fun Matrix2.times(vector: Vector2) = multiply(this, vector, Vector2())
inline operator fun Matrix2.times(other: Matrix2) = multiply(this, other, Matrix2())
inline operator fun Matrix2.div(scalar: Float) = times(1 / scalar)

inline operator fun Matrix2.plusAssign(other: Matrix2) { add(other) }
inline operator fun Matrix2.minusAssign(other: Matrix2) { subtract(other) }
inline operator fun Matrix2.timesAssign(scalar: Float) { scale(scalar) }
inline operator fun Matrix2.timesAssign(other: Matrix2) { multiply(other) }
inline operator fun Matrix2.divAssign(scalar: Float) = timesAssign(1 / scalar)

inline val Matrix2.isSingular
    get() = determinant.isZero()

inline val Matrix2.determinant
    get() = determinant(this)

/**
 * Negates each component.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix2.negate() = negate(this, this)

/**
 * Transposes this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix2.transpose() = transpose(this, this)

/**
 * Inverts this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix2.inverse() = inverse(this, this)

/**
 * Scales this matrix (i.e., multiplies each component with [scalar]).
 *
 * @return This matrix for chaining.
 */
inline fun Matrix2.scale(scalar: Float) = scale(this, scalar, this)

/**
 * Adds [other] to this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix2.add(other: Matrix2) = add(this, other, this)
/**
 * Subtracts [other] from this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix2.subtract(other: Matrix2) = subtract(this, other, this)

/**
 * Multiplies this matrix with [other].
 *
 * @return This matrix for chaining.
 */
inline fun Matrix2.multiply(other: Matrix2) = multiply(this, other, this)
/**
 * Multiplies [other] with this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix2.multiplyLeft(other: Matrix2) = multiply(other, this, this)

// ---

inline operator fun Matrix3.unaryPlus() = this.copy()
inline operator fun Matrix3.unaryMinus() = negate(this, Matrix3())

inline operator fun Matrix3.plus(other: Matrix3) = add(this, other, Matrix3())
inline operator fun Matrix3.minus(other: Matrix3) = subtract(this, other, Matrix3())
inline operator fun Matrix3.times(scalar: Float) = scale(this, scalar, Matrix3())
inline operator fun Matrix3.times(vector: Vector3) = multiply(this, vector, Vector3())
inline operator fun Matrix3.times(other: Matrix3) = multiply(this, other, Matrix3())
inline operator fun Matrix3.div(scalar: Float) = times(1 / scalar)

inline operator fun Matrix3.plusAssign(other: Matrix3) { add(other) }
inline operator fun Matrix3.minusAssign(other: Matrix3) { subtract(other) }
inline operator fun Matrix3.timesAssign(scalar: Float) { scale(scalar) }
inline operator fun Matrix3.timesAssign(other: Matrix3) { multiply(other) }
inline operator fun Matrix3.divAssign(scalar: Float) = timesAssign(1 / scalar)

inline val Matrix3.isSingular
    get() = determinant.isZero()

inline val Matrix3.determinant
    get() = determinant(this)

/**
 * Negates each component.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix3.negate() = negate(this, this)

/**
 * Transposes this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix3.transpose() = transpose(this, this)

/**
 * Inverts this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix3.inverse() = inverse(this, this)

/**
 * Scales this matrix (i.e., multiplies each component with [scalar]).
 *
 * @return This matrix for chaining.
 */
inline fun Matrix3.scale(scalar: Float) = scale(this, scalar, this)

/**
 * Adds [other] to this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix3.add(other: Matrix3) = add(this, other, this)
/**
 * Subtracts [other] from this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix3.subtract(other: Matrix3) = subtract(this, other, this)

/**
 * Multiplies this matrix with [other].
 *
 * @return This matrix for chaining.
 */
inline fun Matrix3.multiply(other: Matrix3) = multiply(this, other, this)
/**
 * Multiplies [other] with this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix3.multiplyLeft(other: Matrix3) = multiply(other, this, this)

// ---

inline operator fun Matrix4.unaryPlus() = this.copy()
inline operator fun Matrix4.unaryMinus() = negate(this, Matrix4())

inline operator fun Matrix4.plus(other: Matrix4) = add(this, other, Matrix4())
inline operator fun Matrix4.minus(other: Matrix4) = subtract(this, other, Matrix4())
inline operator fun Matrix4.times(scalar: Float) = scale(this, scalar, Matrix4())
inline operator fun Matrix4.times(vector: Vector2) = multiply(this, vector, Vector4())
inline operator fun Matrix4.times(vector: Vector3) = multiply(this, vector, Vector4())
inline operator fun Matrix4.times(vector: Vector4) = multiply(this, vector, Vector4())
inline operator fun Matrix4.times(vector: Point2D) = multiply(this, vector, Vector4())
inline operator fun Matrix4.times(vector: Point3D) = multiply(this, vector, Vector4())
inline operator fun Matrix4.times(other: Matrix4) = multiply(this, other, Matrix4())
inline operator fun Matrix4.div(scalar: Float) = times(1 / scalar)

inline operator fun Matrix4.plusAssign(other: Matrix4) { add(other) }
inline operator fun Matrix4.minusAssign(other: Matrix4) { subtract(other) }
inline operator fun Matrix4.timesAssign(scalar: Float) { scale(scalar) }
inline operator fun Matrix4.timesAssign(other: Matrix4) { multiply(other) }
inline operator fun Matrix4.divAssign(scalar: Float) = timesAssign(1 / scalar)

inline val Matrix4.isSingular
    get() = if(isProjection) {
        (e22 * e33 - e23 * e32).isZero()
    } else {
        determinant.isZero()
    }

inline val Matrix4.determinant
    get() = determinant(this)

/**
 * Negates each component.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix4.negate() = negate(this, this)

/**
 * Transposes this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix4.transpose() = transpose(this, this)

/**
 * Inverts this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix4.inverse() = inverse(this, this)

/**
 * Scales this matrix (i.e., multiplies each component with [scalar]).
 *
 * @return This matrix for chaining.
 */
inline fun Matrix4.scale(scalar: Float) = scale(this, scalar, this)

/**
 * Adds [other] to this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix4.add(other: Matrix4) = add(this, other, this)
/**
 * Subtracts [other] from this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix4.subtract(other: Matrix4) = subtract(this, other, this)

/**
 * Multiplies this matrix with [other].
 *
 * @return This matrix for chaining.
 */
inline fun Matrix4.multiply(other: Matrix4) = multiply(this, other, this)
/**
 * Multiplies [other] with this matrix.
 *
 * @return This matrix for chaining.
 */
inline fun Matrix4.multiplyLeft(other: Matrix4) = multiply(other, this, this)

// ---

/** Whether this matrix is orthogonal (i.e., all rows (or columns) are orthonormal between each other). */
val Matrix2.isOrthogonal
    get() = orthonormal(getRow0(a2), getRow1(b2))

/**
 * Gets the desired row as a Vector2.
 *
 * @return The [out] vector for chaining.
 */
inline fun Matrix2.getRow(index: Int, out: Vector2) = when(index) {
    0 -> getRow0(out)
    1 -> getRow1(out)
    else -> {
        throw IndexOutOfBoundsException(if(index < 0) "$index < 0" else "$index > $dimensions")
    }
}
/**
 * Gets the first row as a Vector2.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix2.getRow0(out: Vector2 = Vector2()) = out.set(e00, e01)
/**
 * Gets the second row as a Vector2.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix2.getRow1(out: Vector2 = Vector2()) = out.set(e10, e11)

/**
 * Gets the desired column as a Vector2.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix2.getColumn(index: Int, out: Vector2) = when(index) {
    0 -> getColumn0(out)
    1 -> getColumn1(out)
    else -> {
        throw IndexOutOfBoundsException(if(index < 0) "$index < 0" else "$index > $dimensions")
    }
}
/**
 * Gets the first column as a Vector2.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix2.getColumn0(out: Vector2 = Vector2()) = out.set(e00, e10)
/**
 * Gets the second column as a Vector2.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix2.getColumn1(out: Vector2 = Vector2()) = out.set(e01, e11)

// ---

/** Whether this matrix is orthogonal (i.e., all rows (or columns) are orthonormal between each other). */
val Matrix3.isOrthogonal: Boolean
    get() {
        getRow0(a3)
        getRow1(b3)
        getRow2(c3)

        if(!a3.isUnit || !b3.isUnit || !c3.isUnit) {
            return false
        }

        return orthogonal(a3, b3) && orthogonal(a3, c3) && orthogonal(b3, c3)
    }

/**
 * Gets the desired row as a Vector3.
 *
 * @return The [out] vector for chaining.
 */
inline fun Matrix3.getRow(index: Int, out: Vector3) : Vector3 {
    return when(index) {
        0 -> getRow0(out)
        1 -> getRow1(out)
        2 -> getRow2(out)
        else -> {
            throw IndexOutOfBoundsException(if(index < 0) "$index < 0" else "$index > $dimensions")
        }
    }
}
/**
 * Gets the first row as a Vector3.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix3.getRow0(out: Vector3 = Vector3()) = out.set(e00, e01, e02)
/**
 * Gets the second row as a Vector3.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix3.getRow1(out: Vector3 = Vector3()) = out.set(e10, e11, e12)
/**
 * Gets the third row as a Vector3.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix3.getRow2(out: Vector3 = Vector3()) = out.set(e20, e21, e22)

/**
 * Gets the desired column as a Vector3.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix3.getColumn(index: Int, out: Vector3) = when(index) {
    0 -> getColumn0(out)
    1 -> getColumn1(out)
    2 -> getColumn2(out)
    else -> {
        throw IndexOutOfBoundsException(if(index < 0) "$index < 0" else "$index > $dimensions")
    }
}
/**
 * Gets the first column as a Vector3.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix3.getColumn0(out: Vector3 = Vector3()) = out.set(e00, e10, e20)
/**
 * Gets the second column as a Vector3.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix3.getColumn1(out: Vector3 = Vector3()) = out.set(e01, e11, e21)
/**
 * Gets the third column as a Vector3.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix3.getColumn2(out: Vector3 = Vector3()) = out.set(e02, e12, e22)

// ---

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
        getRow0(a4)
        getRow1(b4)
        getRow2(c4)
        getRow3(d4)

        if(!a4.isUnit || !b4.isUnit || !c4.isUnit || !d4.isUnit) {
            return false
        }

        return orthogonal(a4, b4) && orthogonal(a4, c4) && orthogonal(a4, d4) && orthogonal(b4, c4) && orthogonal(b4, d4) && orthogonal(c4, d4)
    }

/**
 * Gets the desired row as a Vector4.
 *
 * @return The [out] vector for chaining.
 */
inline fun Matrix4.getRow(index: Int, out: Vector4) = when(index) {
    0 -> getRow0(out)
    1 -> getRow1(out)
    2 -> getRow2(out)
    3 -> getRow3(out)
    else -> {
        throw IndexOutOfBoundsException(if(index < 0) "$index < 0" else "$index > $dimensions")
    }
}
/**
 * Gets the first row as a Vector4.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix4.getRow0(out: Vector4 = Vector4()) = out.set(e00, e01, e02, e03)
/**
 * Gets the second row as a Vector4.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix4.getRow1(out: Vector4 = Vector4()) = out.set(e10, e11, e12, e13)
/**
 * Gets the third row as a Vector4.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix4.getRow2(out: Vector4 = Vector4()) = out.set(e20, e21, e22, e23)
/**
 * Gets the fourth row as a Vector4.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix4.getRow3(out: Vector4 = Vector4()) = out.set(e30, e31, e32, e33)

/**
 * Gets the desired column as a Vector4.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix4.getColumn(index: Int, out: Vector4) = when(index) {
    0 -> getColumn0(out)
    1 -> getColumn1(out)
    2 -> getColumn2(out)
    3 -> getColumn3(out)
    else -> {
        throw IndexOutOfBoundsException(if(index < 0) "$index < 0" else "$index > $dimensions")
    }
}
/**
 * Gets the first column as a Vector4.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix4.getColumn0(out: Vector4 = Vector4()) = out.set(e00, e10, e20, e30)
/**
 * Gets the second column as a Vector4.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix4.getColumn1(out: Vector4 = Vector4()) = out.set(e01, e11, e21, e31)
/**
 * Gets the third column as a Vector4.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix4.getColumn2(out: Vector4 = Vector4()) = out.set(e02, e12, e22, e32)
/**
 * Gets the fourth column as a Vector4.
 *
 * @return The [out] vector for chaining.
 */
fun Matrix4.getColumn3(out: Vector4 = Vector4()) = out.set(e03, e13, e23, e33)

// ---

private val a2 by lazy { Vector2() }
private val b2 by lazy { Vector2() }

private val a3 by lazy { Vector3() }
private val b3 by lazy { Vector3() }
private val c3 by lazy { Vector3() }
private val d3 by lazy { Vector3() }
private val r0 by lazy { Vector3() }
private val r1 by lazy { Vector3() }
private val r2 by lazy { Vector3() }
private val r3 by lazy { Vector3() }
private val t0 by lazy { Vector3() }
private val t1 by lazy { Vector3() }
private val s by lazy { Vector3() }
private val t by lazy { Vector3() }
private val u by lazy { Vector3() }
private val v by lazy { Vector3() }

private val a4 by lazy { Vector4() }
private val b4 by lazy { Vector4() }
private val c4 by lazy { Vector4() }
private val d4 by lazy { Vector4() }

// ---------------------------------------------------------------------------------------------------------------------

class SingularMatrixException(message: String) : Exception(message)