package org.rufousengine.math

import java.util.*

sealed class Color(r: Float, g: Float, b: Float, a: Float) {
    val components = floatArrayOf(r, g, b, a)

    operator fun component1() = components[0]
    operator fun component2() = components[1]
    operator fun component3() = components[2]
    operator fun component4() = components[3]

    override fun toString() = components.joinToString(prefix = "(", postfix = ")")

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Color) {
            return false
        }

        return components[0] == other.components[0] && components[1] == other.components[1] && components[2] == other.components[2] && components[3] == other.components[3]
    }

    override fun hashCode() = Arrays.hashCode(components)
}

class ColorFloat(r: Float = 1f, g: Float = 1f, b: Float = 1f, a: Float = 1f): Color(r, g, b, a) {
    constructor(other: Color) : this(other.components[0], other.components[1], other.components[2], other.components[3])

    inline var r: Float
        get() = components[0]
        set(value) { components[0] = clamp(value, 0f, 1f) }
    inline var g: Float
        get() = components[1]
        set(value) { components[1] = clamp(value, 0f, 1f) }
    inline var b: Float
        get() = components[2]
        set(value) { components[2] = clamp(value, 0f, 1f) }
    inline var a: Float
        get() = components[3]
        set(value) { components[3] = clamp(value, 0f, 1f) }

    operator fun get(index: Int) = when(index) {
        0 -> r
        1 -> g
        2 -> b
        3 -> a
        else -> throw IllegalArgumentException("index must be in 0..3")
    }

    operator fun set(index: Int, value: Float) = when(index) {
        0 -> r = value
        1 -> g = value
        2 -> b = value
        3 -> a = value
        else -> throw IllegalArgumentException("index must be in 0..3")
    }

    fun set(other: Color): ColorFloat {
        System.arraycopy(other.components, 0, components, 0, 4)

        return this
    }
    fun set(r: Float = this.r, g: Float = this.g, b: Float = this.b, a: Float = this.a): ColorFloat {
        this.r = r
        this.g = g
        this.b = b
        this.a = a

        return this
    }

    fun copy() = ColorFloat(this)
}

class ColorInt(r: Float = 1f, g: Float = 1f, b: Float = 1f, a: Float = 1f, val bitDepth: Int = 8) : Color(r, g, b, a) {
    val max = (1 shl bitDepth) - 1
    val invMax = 1f / max

    constructor(other: Color) : this(other.components[0], other.components[1], other.components[2], other.components[3])

    inline var r: Int
        get() = (components[0] * max).toInt()
        set(value) { components[0] = clamp(value, 0, max) * invMax }
    inline var g: Int
        get() = (components[1] * max).toInt()
        set(value) { components[1] = clamp(value, 0, max) * invMax }
    inline var b: Int
        get() = (components[2] * max).toInt()
        set(value) { components[2] = clamp(value, 0, max) * invMax }
    inline var a: Int
        get() = (components[3] * max).toInt()
        set(value) { components[3] = clamp(value, 0, max) * invMax }

    operator fun get(index: Int) = when(index) {
        0 -> r
        1 -> g
        2 -> b
        3 -> a
        else -> throw IllegalArgumentException("index must be in 0..3")
    }

    operator fun set(index: Int, value: Int) = when(index) {
        0 -> r = value
        1 -> g = value
        2 -> b = value
        3 -> a = value
        else -> throw IllegalArgumentException("index must be in 0..3")
    }

    fun set(other: Color): ColorInt {
        System.arraycopy(other.components, 0, components, 0, 4)

        return this
    }
    fun set(r: Int = this.r, g: Int = this.g, b: Int = this.b, a: Int = this.a): ColorInt {
        this.r = r
        this.g = g
        this.b = b
        this.a = a

        return this
    }

    fun copy() = ColorInt(this)
}