package org.rufousengine.math

/**
 * A mutable RGBA color. Values should be in 0f..1f.
 *
 * @property[r] The r component.
 * @property[g] The g component.
 * @property[b] The b component.
 * @property[a] The a component.
 * @property[observer] An observer that gets notified every time this color changes. Can be null.
 * @constructor Creates a color with the given parameters.
 */
class MutableColor(r: Float = 0f, g: Float = 0f, b: Float = 0f, a: Float = 0f, observer: ((Color) -> Unit)? = null) : Color(r, g, b, a) {
    val observer = observer

    constructor(other: Color, observer: ((Color) -> Unit)? = null) : this(other.components, observer)
    constructor(components: FloatArray, observer: ((Color) -> Unit)? = null) : this(components[0], components[1], components[2], components[3], observer)

    override var r: Float
        get() = components[0]
        set(value) {
            if(value isCloseTo components[0]) {
                return
            }

            components[0] = value

            observer?.invoke(this)
        }
    override var g: Float
        get() = components[1]
        set(value) {
            if(value isCloseTo components[1]) {
                return
            }

            components[1] = value

            observer?.invoke(this)
        }
    override var b: Float
        get() = components[2]
        set(value) {
            if(value isCloseTo components[2]) {
                return
            }

            components[2] = value

            observer?.invoke(this)
        }
    override var a: Float
        get() = components[3]
        set(value) {
            if(value isCloseTo components[3]) {
                return
            }

            components[3] = value

            observer?.invoke(this)
        }

    fun set(other: Color) = set(other.components)
    fun set(components: FloatArray) = set(components[0], components[1], components[2], components[3])
    fun set(r: Float = 0f, g: Float = 0f, b: Float = 0f, a: Float = 0f) : MutableColor {
        if(equals(r, g, b, a)) {
            return this
        }

        components[0] = r
        components[1] = g
        components[2] = b
        components[3] = a

        observer?.invoke(this)

        return this
    }

    operator fun set(index: Int, value: Float) {
        if(value isCloseTo components[index]) {
            return
        }

        components[index] = value

        observer?.invoke(this)
    }
}