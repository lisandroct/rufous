package org.rufousengine.math

import java.util.*

/**
 * An immutable 2x2 row-major matrix.
 *
 * @constructor Creates a 2x2 matrix with the given components.
 */
open class Matrix2(e00: Float, e01: Float, e10: Float, e11: Float) {
    companion object {
        val identity = Matrix2()
        val zero = Matrix2(0f, 0f, 0f, 0f)
    }

    /** The components of this matrix. Do not change its values directly unless you know what you're doing. */
    val components = floatArrayOf(e00, e01, e10, e11)

    constructor() : this(1f, 0f, 0f, 1f)
    constructor(other: Matrix2) : this(other.components)
    constructor(components: FloatArray) : this(
            components[0], components[1],
            components[2], components[3]
    )
    constructor(a: Vector2, b: Vector2) : this(
            a.x, b.x,
            a.y, b.y
    )

    open val e00: Float
        get() = components[0]
    open val e01: Float
        get() = components[1]
    open val e10: Float
        get() = components[2]
    open val e11: Float
        get() = components[3]

    /** The transpose of this matrix. It creates a new lazy Matrix2 instance. */
    open val transpose : Matrix2 by lazy { transpose(MutableMatrix2()) }
    /** The inverse of this matrix. It creates a new lazy Matrix2 instance. */
    open val inverse : Matrix2 by lazy { inverse(MutableMatrix2()) }

    val determinant: Float
        get() = e00 * e11 - e01 * e10

    /** Whether this matrix is orthogonal (i.e., all rows (or columns) are orthonormal between each other). */
    val isOrthogonal: Boolean
        get() {
            getRow0(Cached.a)
            getRow1(Cached.b)

            if(!Cached.a.isUnit || !Cached.b.isUnit) {
                return false
            }

            return Cached.a isOrthogonal Cached.b
        }

    val isIdentity
        get() = equals(identity)
    val isZero
        get() = equals(zero)

    operator fun get(row: Int, column: Int) = components[2 * row + column]

    /**
     * Gets the desired row as a Vector2.
     *
     * @param[index] The index of the desired row.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow(index: Int, out: MutableVector2) : MutableVector2 {
        return when(index) {
            0 -> getRow0(out)
            1 -> getRow1(out)
            else -> {
                throw IndexOutOfBoundsException(if(index < 0) "$index < 0" else "$index > 1")
            }
        }
    }
    /**
     * Gets the first row as a Vector2.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow0(out: MutableVector2) = out.set(e00, e01)
    /**
     * Gets the second row as a Vector2.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getRow1(out: MutableVector2) = out.set(e10, e11)

    /**
     * Gets the desired column as a Vector2.
     *
     * @param[index] The index of the desired column.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn(index: Int, out: MutableVector2) : MutableVector2 {
        return when(index) {
            0 -> getColumn0(out)
            1 -> getColumn1(out)
            else -> {
                throw IndexOutOfBoundsException(if(index < 0) "$index < 0" else "$index > 1")
            }
        }
    }
    /**
     * Gets the first column as a Vector2.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn0(out: MutableVector2) = out.set(e00, e10)
    /**
     * Gets the second column as a Vector2.
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun getColumn1(out: MutableVector2) = out.set(e01, e11)

    /**
     * Creates an immutable copy of this matrix.
     *
     * @return The new matrix for chaining.
     */
    fun copyImmutable() = Matrix2(this)
    /**
     * Creates a mutable copy of this matrix.
     *
     * @return The new matrix for chaining.
     */
    fun copyMutable() = MutableMatrix2(this)

    /**
     * Transposes this matrix.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun transpose(out: MutableMatrix2) = out.set(
            e00, e10,
            e01, e11
    )
    /**
     * Inverts this matrix.
     *
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun inverse(out: MutableMatrix2) : MutableMatrix2 {
        val invDet = 1 / determinant
        return out.set(e11, -e01, -e10, e00).scale(invDet)
    }

    /**
     * Scales this matrix (i.e., multiplies each component with [scalar]).
     *
     * @param[scalar] The scalar to multiply the matrix with.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun scale(scalar: Float, out: MutableMatrix2) = out.set(
            e00 * scalar, e01 * scalar,
            e10 * scalar, e11 * scalar
    )

    /**
     * Adds [other] to this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun add(other: Matrix2, out: MutableMatrix2) = out.set(
            e00 + other.e00, e01 + other.e01,
            e10 + other.e10, e11 + other.e11
    )
    /**
     * Subtracts [other] from this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun subtract(other: Matrix2, out: MutableMatrix2) = out.set(
            e00 - other.e00, e01 - other.e01,
            e10 - other.e10, e11 - other.e11
    )

    /**
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun multiply(other: Matrix2, out: MutableMatrix2) : MutableMatrix2 {
        if(isIdentity) {
            return out.set(other)
        }

        if(other.isIdentity) {
            return out.set(this)
        }

        val e00 = this.e00 * other.e00 + this.e01 * other.e10
        val e01 = this.e00 * other.e01 + this.e01 * other.e11

        val e10 = this.e10 * other.e00 + this.e11 * other.e10
        val e11 = this.e10 * other.e01 + this.e11 * other.e11

        return out.set(
                e00, e01,
                e10, e11
        )
    }
    /**
     * Multiplies [other] with this matrix.
     *
     * @param[other] The other matrix.
     * @param[out] The output matrix.
     * @return The output matrix for chaining.
     */
    fun multiplyLeft(other: Matrix2, out: MutableMatrix2) = other.multiply(this, out)

    /**
     * Multiplies this matrix with [vector].
     *
     * @param[vector] The vector.
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun multiply(vector: Vector2, out: MutableVector2) = multiply(vector.x, vector.y, out)
    /**
     * Multiplies this matrix with ([x], [y]).
     *
     * @param[out] The output vector.
     * @return The output vector for chaining.
     */
    fun multiply(x: Float, y: Float, out: MutableVector2) : MutableVector2 {
        if(isIdentity) {
            return out.set(x, y)
        }

        val nx = e00 * x + e01 * y
        val ny = e10 * x + e11 * y

        return out.set(nx, ny)
    }
    
    fun equals(other: Matrix2) = equals(other.e00, other.e01, other.e10, other.e11)
    fun equals(e00: Float, e01: Float, e10: Float, e11: Float) : Boolean {
        return this.e00 isCloseTo e00 && this.e01 isCloseTo e01 &&
                this.e10 isCloseTo e10 && this.e11 isCloseTo e11
    }

    override fun toString() = "| ($e00, $e01) | ($e10, $e11) |"

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Matrix2) {
            return false
        }

        return equals(other)
    }

    override fun hashCode() = Arrays.hashCode(components)

    private object Cached {
        val a by lazy { MutableVector2() }
        val b by lazy { MutableVector2() }
    }
}