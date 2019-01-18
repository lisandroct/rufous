package org.rufousengine.math

import java.util.*

sealed class Matrix(val components: FloatArray) {
    abstract val dimensions: Int

    operator fun get(row: Int, column: Int) = if(row in 0 until dimensions && column in 0 until dimensions)
        components[dimensions * row + column]
    else throw IllegalArgumentException("row and column must be in 0..${ dimensions - 1 }")

    operator fun set(row: Int, column: Int, value: Float) = if(row in 0 until dimensions && column in 0 until dimensions)
        components[dimensions * row + column] = value
    else throw IllegalArgumentException("row and column must be in 0..${ dimensions - 1 }")

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append('{')
        for ((index, element) in components.withIndex()) {
            if(index % dimensions == 0) {
                builder.append("{")
            }

            builder.append(element.toString())

            if(index % dimensions == dimensions - 1) {
                builder.append("}")
            }

            if (index < components.size - 1) builder.append(", ")
        }
        builder.append('}')

        return builder.toString()
    }

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Matrix) {
            return false
        }

        if(dimensions != other.dimensions) {
            return false
        }

        for(i in 0 until dimensions) {
            for(j in 0 until dimensions) {
                if (this[i, j] isNotCloseTo other[i, j]) {
                    return false
                }
            }
        }

        return true
    }

    override fun hashCode() = Arrays.hashCode(components)
}

class Matrix2(e00: Float, e01: Float, e10: Float, e11: Float): Matrix(floatArrayOf(e00, e01, e10, e11)) {
    override val dimensions = 2

    constructor() : this(1f, 0f, 0f, 1f)
    constructor(other: Matrix2) : this(other.e00, other.e01, other.e10, other.e11)
    constructor(c0: Vector2, c1: Vector2) : this(c0.x, c1.x, c0.y, c1.y)
    constructor(init: (Int, Int) -> Float) : this() {
        for(row in 0 until dimensions) {
            for (column in 0 until dimensions) {
                this[row, column] = init(row, column)
            }
        }
    }

    inline var e00: Float
        get() = components[0]
        set(value) { components[0] = value }
    inline var e01: Float
        get() = components[1]
        set(value) { components[1] = value }
    inline var e10: Float
        get() = components[2]
        set(value) { components[2] = value }
    inline var e11: Float
        get() = components[3]
        set(value) { components[3] = value }

    fun set(other: Matrix2) = set(other.e00, other.e01, other.e10, other.e11)
    fun set(c0: Vector2, c1: Vector2) = set(c0.x, c1.x, c0.y, c1.y)
    fun set(e00: Float, e01: Float, e10: Float, e11: Float): Matrix2 {
        this.e00 = e00
        this.e01 = e01
        this.e10 = e10
        this.e11 = e11

        return this
    }
    fun set(init: (Int, Int) -> Float): Matrix2 {
        for(row in 0 until dimensions) {
            for (column in 0 until dimensions) {
                this[row, column] = init(row, column)
            }
        }

        return this
    }

    fun copy() = Matrix2(this)
}

class Matrix3(e00: Float, e01: Float, e02: Float, e10: Float, e11: Float, e12: Float, e20: Float, e21: Float, e22: Float): Matrix(floatArrayOf(e00, e01, e02, e10, e11, e12, e20, e21, e22)) {
    override val dimensions = 3

    constructor() : this(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f)
    constructor(other: Matrix3) : this(other.e00, other.e01, other.e02, other.e10, other.e11, other.e12, other.e20, other.e21, other.e22)
    constructor(c0: Vector3, c1: Vector3, c2: Vector3) : this(c0.x, c1.x, c2.x, c0.y, c1.y, c2.y, c0.z, c1.z, c2.z)
    constructor(init: (Int, Int) -> Float) : this() {
        for(row in 0 until dimensions) {
            for (column in 0 until dimensions) {
                this[row, column] = init(row, column)
            }
        }
    }

    inline var e00: Float
        get() = components[0]
        set(value) { components[0] = value }
    inline var e01: Float
        get() = components[1]
        set(value) { components[1] = value }
    inline var e02: Float
        get() = components[2]
        set(value) { components[2] = value }
    inline var e10: Float
        get() = components[3]
        set(value) { components[3] = value }
    inline var e11: Float
        get() = components[4]
        set(value) { components[4] = value }
    inline var e12: Float
        get() = components[5]
        set(value) { components[5] = value }
    inline var e20: Float
        get() = components[6]
        set(value) { components[6] = value }
    inline var e21: Float
        get() = components[7]
        set(value) { components[7] = value }
    inline var e22: Float
        get() = components[8]
        set(value) { components[8] = value }

    fun set(other: Matrix3) = set(other.e00, other.e01, other.e02, other.e10, other.e11, other.e12, other.e20, other.e21, other.e22)
    fun set(c0: Vector3, c1: Vector3, c2: Vector3) = set(c0.x, c1.x, c2.x, c0.y, c1.y, c2.y, c0.z, c1.z, c2.z)
    fun set(e00: Float, e01: Float, e02: Float, e10: Float, e11: Float, e12: Float, e20: Float, e21: Float, e22: Float): Matrix3 {
        this.e00 = e00
        this.e01 = e01
        this.e02 = e02
        this.e10 = e10
        this.e11 = e11
        this.e12 = e12
        this.e20 = e20
        this.e21 = e21
        this.e22 = e22

        return this
    }
    fun set(init: (Int, Int) -> Float): Matrix3 {
        for(row in 0 until dimensions) {
            for (column in 0 until dimensions) {
                this[row, column] = init(row, column)
            }
        }

        return this
    }

    fun copy() = Matrix3(this)
}

class Matrix4(e00: Float, e01: Float, e02: Float, e03: Float, e10: Float, e11: Float, e12: Float, e13: Float, e20: Float, e21: Float, e22: Float, e23: Float, e30: Float, e31: Float, e32: Float, e33: Float): Matrix(floatArrayOf(e00, e01, e02, e03, e10, e11, e12, e13, e20, e21, e22, e23, e30, e31, e32, e33)) {
    override val dimensions = 4

    constructor() : this(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
    constructor(c0: Vector4, c1: Vector4, c2: Vector4, c3: Vector4) : this(c0.x, c1.x, c2.x, c3.x, c0.y, c1.y, c2.y, c3.y, c0.z, c1.z, c2.z, c3.z, c0.w, c1.w, c2.w, c3.w)
    constructor(other: Matrix4) : this(other.e00, other.e01, other.e02, other.e03, other.e10, other.e11, other.e12, other.e13, other.e20, other.e21, other.e22, other.e23, other.e30, other.e31, other.e32, other.e33)
    constructor(init: (Int, Int) -> Float) : this() {
        for(row in 0 until dimensions) {
            for (column in 0 until dimensions) {
                this[row, column] = init(row, column)
            }
        }
    }

    inline var e00: Float
        get() = components[0]
        set(value) { components[0] = value }
    inline var e01: Float
        get() = components[1]
        set(value) { components[1] = value }
    inline var e02: Float
        get() = components[2]
        set(value) { components[2] = value }
    inline var e03: Float
        get() = components[3]
        set(value) { components[3] = value }
    inline var e10: Float
        get() = components[4]
        set(value) { components[4] = value }
    inline var e11: Float
        get() = components[5]
        set(value) { components[5] = value }
    inline var e12: Float
        get() = components[6]
        set(value) { components[6] = value }
    inline var e13: Float
        get() = components[7]
        set(value) { components[7] = value }
    inline var e20: Float
        get() = components[8]
        set(value) { components[8] = value }
    inline var e21: Float
        get() = components[9]
        set(value) { components[9] = value }
    inline var e22: Float
        get() = components[10]
        set(value) { components[10] = value }
    inline var e23: Float
        get() = components[11]
        set(value) { components[11] = value }
    inline var e30: Float
        get() = components[12]
        set(value) { components[12] = value }
    inline var e31: Float
        get() = components[13]
        set(value) { components[13] = value }
    inline var e32: Float
        get() = components[14]
        set(value) { components[14] = value }
    inline var e33: Float
        get() = components[15]
        set(value) { components[15] = value }

    fun set(other: Matrix4) = set(other.e00, other.e01, other.e02, other.e03, other.e10, other.e11, other.e12, other.e13, other.e20, other.e21, other.e22, other.e23, other.e30, other.e31, other.e32, other.e33)
    fun set(c0: Vector4, c1: Vector4, c2: Vector4, c3: Vector4) = set(c0.x, c1.x, c2.x, c3.x, c0.y, c1.y, c2.y, c3.y, c0.z, c1.z, c2.z, c3.z, c0.w, c1.w, c2.w, c3.w)
    fun set(e00: Float, e01: Float, e02: Float, e03: Float, e10: Float, e11: Float, e12: Float, e13: Float, e20: Float, e21: Float, e22: Float, e23: Float, e30: Float, e31: Float, e32: Float, e33: Float): Matrix4 {
        this.e00 = e00
        this.e01 = e01
        this.e02 = e02
        this.e03 = e03
        this.e10 = e10
        this.e11 = e11
        this.e12 = e12
        this.e13 = e13
        this.e20 = e20
        this.e21 = e21
        this.e22 = e22
        this.e23 = e23
        this.e30 = e30
        this.e31 = e31
        this.e32 = e32
        this.e33 = e33

        return this
    }
    fun set(init: (Int, Int) -> Float): Matrix4 {
        for(row in 0 until dimensions) {
            for (column in 0 until dimensions) {
                this[row, column] = init(row, column)
            }
        }

        return this
    }

    fun copy() = Matrix4(this)
}