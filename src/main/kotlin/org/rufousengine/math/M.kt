package org.rufousengine.math

import java.util.*

sealed class M(val components: FloatArray) {
    abstract val dimensions: Int

    operator fun get(row: Int, column: Int) = if(row >= 0 && column >= 0 || row < dimensions || column < dimensions)
        components[dimensions * row + column]
    else throw IllegalArgumentException("row and column must be in 0..${ dimensions - 1 }")

    operator fun set(row: Int, column: Int, value: Float) = if(row >= 0 && column >= 0 || row < dimensions || column < dimensions)
        components[dimensions * row + column] = value
    else throw IllegalArgumentException("row and column must be in 0..${ dimensions - 1 }")

    override fun toString() = components.joinToString(prefix = "(", postfix = ")")

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is M) {
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

class M2(e00: Float, e01: Float, e10: Float, e11: Float): M(floatArrayOf(e00, e01, e10, e11)) {
    override val dimensions = 2

    constructor(other: M2) : this(other.e00, other.e01, other.e10, other.e11)

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

    fun set(other: Vector2) = set(other.x, other.y)
    fun set(e00: Float = this.e00, e01: Float = this.e01, e10: Float = this.e10, e11: Float = this.e11): M2 {
        this.e00 = e00
        this.e01 = e01
        this.e10 = e10
        this.e11 = e11

        return this
    }

    fun copy() = M2(this)
}

class M3(e00: Float, e01: Float, e02: Float, e10: Float, e11: Float, e12: Float, e20: Float, e21: Float, e22: Float): M(floatArrayOf(e00, e01, e02, e10, e11, e12, e20, e21, e22)) {
    override val dimensions = 3

    constructor(other: M3) : this(other.e00, other.e01, other.e02, other.e10, other.e11, other.e12, other.e20, other.e21, other.e22)

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

    fun set(other: M3) = set(other.e00, other.e01, other.e02, other.e10, other.e11, other.e12, other.e20, other.e21, other.e22)
    fun set(e00: Float = this.e00, e01: Float = this.e01, e02: Float = this.e02, e10: Float = this.e10, e11: Float = this.e11, e12: Float = this.e12, e20: Float = this.e20, e21: Float = this.e21, e22: Float = this.e22): M3 {
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

    fun copy() = M3(this)
}

class M4(e00: Float, e01: Float, e02: Float, e03: Float, e10: Float, e11: Float, e12: Float, e13: Float, e20: Float, e21: Float, e22: Float, e23: Float, e30: Float, e31: Float, e32: Float, e33: Float): M(floatArrayOf(e00, e01, e02, e03, e10, e11, e12, e13, e20, e21, e22, e23, e30, e31, e32, e33)) {
    override val dimensions = 4

    constructor(other: M4) : this(other.e00, other.e01, other.e02, other.e03, other.e10, other.e11, other.e12, other.e13, other.e20, other.e21, other.e22, other.e23, other.e30, other.e31, other.e32, other.e33)

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

    fun set(other: M4) = set(other.e00, other.e01, other.e02, other.e03, other.e10, other.e11, other.e12, other.e13, other.e20, other.e21, other.e22, other.e23, other.e30, other.e31, other.e32, other.e33)
    fun set(e00: Float = this.e00, e01: Float = this.e01, e02: Float = this.e02, e03: Float = this.e03, e10: Float = this.e10, e11: Float = this.e11, e12: Float = this.e12, e13: Float = this.e13, e20: Float = this.e20, e21: Float = this.e21, e22: Float = this.e22, e23: Float = this.e23, e30: Float = this.e30, e31: Float = this.e31, e32: Float = this.e32, e33: Float = this.e33): M4 {
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

    fun copy() = M4(this)
}

class T(e00: Float, e01: Float, e02: Float, e03: Float, e10: Float, e11: Float, e12: Float, e13: Float, e20: Float, e21: Float, e22: Float, e23: Float): M(floatArrayOf(e00, e01, e02, e03, e10, e11, e12, e13, e20, e21, e22, e23, 0f, 0f, 0f, 1f)) {
    override val dimensions = 4

    constructor(other: T) : this(other.e00, other.e01, other.e02, other.e03, other.e10, other.e11, other.e12, other.e13, other.e20, other.e21, other.e22, other.e23)

    inline var e00: Float
        get() = components[0]
        private set(value) {
            components[0] = value
        }
    inline var e01: Float
        get() = components[1]
        private set(value) {
            components[1] = value
        }
    inline var e02: Float
        get() = components[2]
        private set(value) {
            components[2] = value
        }
    inline var e03: Float
        get() = components[3]
        private set(value) {
            components[3] = value
        }
    inline var e10: Float
        get() = components[4]
        private set(value) {
            components[4] = value
        }
    inline var e11: Float
        get() = components[5]
        private set(value) {
            components[5] = value
        }
    inline var e12: Float
        get() = components[6]
        private set(value) {
            components[6] = value
        }
    inline var e13: Float
        get() = components[7]
        private set(value) {
            components[7] = value
        }
    inline var e20: Float
        get() = components[8]
        private set(value) {
            components[8] = value
        }
    inline var e21: Float
        get() = components[9]
        private set(value) {
            components[9] = value
        }
    inline var e22: Float
        get() = components[10]
        private set(value) {
            components[10] = value
        }
    inline var e23: Float
        get() = components[11]
        private set(value) {
            components[11] = value
        }
    inline val e30: Float
        get() = 0f
    inline val e31: Float
        get() = 0f
    inline val e32: Float
        get() = 0f
    inline val e33: Float
        get() = 1f

    fun set(other: T) = set(other.e00, other.e01, other.e02, other.e03, other.e10, other.e11, other.e12, other.e13, other.e20, other.e21, other.e22, other.e23)
    fun set(e00: Float = this.e00, e01: Float = this.e01, e02: Float = this.e02, e03: Float = this.e03, e10: Float = this.e10, e11: Float = this.e11, e12: Float = this.e12, e13: Float = this.e13, e20: Float = this.e20, e21: Float = this.e21, e22: Float = this.e22, e23: Float = this.e23): T {
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

        return this
    }

    fun copy() = T(this)
}

class P(e00: Float, e11: Float, e22: Float, e23: Float, e32: Float, e33: Float): M(floatArrayOf(e00, 0f, 0f, 0f, 0f, e11, 0f, 0f, 0f, 0f, e22, e23, 0f, 0f, e32, e33)) {
    override val dimensions = 4

    constructor(other: P) : this(other.e00, other.e11, other.e22, other.e23, other.e32, other.e33)

    inline var e00: Float
        get() = components[0]
        set(value) {
            components[0] = value
        }
    inline val e01: Float
        get() = 0f
    inline val e02: Float
        get() = 0f
    inline val e03: Float
        get() = 0f
    inline val e10: Float
        get() = 0f
    inline var e11: Float
        get() = components[5]
        set(value) {
            components[5] = value
        }
    inline val e12: Float
        get() = 0f
    inline val e13: Float
        get() = 0f
    inline val e20: Float
        get() = 0f
    inline val e21: Float
        get() = 0f
    inline var e22: Float
        get() = components[10]
        set(value) {
            components[10] = value
        }
    inline var e23: Float
        get() = components[11]
        set(value) {
            components[11] = value
        }
    inline val e30: Float
        get() = 0f
    inline val e31: Float
        get() = 0f
    inline var e32: Float
        get() = components[14]
        set(value) {
            components[14] = value
        }
    inline var e33: Float
        get() = components[15]
        set(value) {
            components[15] = value
        }

    fun set(other: P) = set(other.e00, other.e11, other.e22, other.e23, other.e32, other.e33)
    internal fun set(e00: Float = this.e00, e11: Float = this.e11, e22: Float = this.e22, e23: Float = this.e23, e32: Float = this.e32, e33: Float = this.e33): P {
        this.e00 = e00
        this.e11 = e11
        this.e22 = e22
        this.e23 = e23
        this.e32 = e32
        this.e33 = e33

        return this
    }

    fun copy() = P(this)
}