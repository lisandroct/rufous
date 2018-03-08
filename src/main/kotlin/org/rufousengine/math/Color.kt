package org.rufousengine.math

import java.util.*

/**
 * An immutable RGBA color. Values should be in 0f..1f.
 *
 * @property[r] The r component.
 * @property[g] The g component.
 * @property[b] The b component.
 * @property[a] The a component.
 * @constructor Creates a color with the given parameters.
 */
open class Color(r: Float = 0f, g: Float = 0f, b: Float = 0f, a: Float = 0f) {
    companion object {
        val transparentWhite = Color(1f, 1f, 1f, 0f)
        val transparentBlack = Color()
        val white = Color(1f, 1f, 1f, 1f)
        val rufous = Color(0.65f, 0.1f, 0.02f, 1f)
    }

    /** The components of this color. Do not change its values directly unless you know what you're doing. */
    val components = floatArrayOf(r, g, b, a)

    constructor(other: Color) : this(other.components)
    constructor(components: FloatArray) : this(components[0], components[1], components[2], components[3])

    open val r: Float
        get() = components[0]
    open val g: Float
        get() = components[1]
    open val b: Float
        get() = components[2]
    open val a: Float
        get() = components[3]

    operator fun get(index: Int) = components[index]

    /**
     * Creates an immutable copy of this color.
     *
     * @return The new color for chaining.
     */
    fun copyImmutable() = Color(this)
    /**
     * Creates a mutable copy of this color.
     *
     * @return The new color for chaining.
     */
    fun copyMutable() = MutableColor(this)

    fun equals(other: Color) = equals(other.r, other.g, other.b, other.a)
    fun equals(r: Float, g: Float, b: Float, a: Float) = this.r isCloseTo r && this.g isCloseTo g && this.b isCloseTo b && this.a isCloseTo a

    override fun toString() = "($r, $g, $b, $a)"

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Color) {
            return false
        }

        return equals(other)
    }

    override fun hashCode() = Arrays.hashCode(components)
}