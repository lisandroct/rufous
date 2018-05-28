package org.rufousengine.math

import java.util.*
import kotlin.math.sqrt

/**
 * An immutable quaternion.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @property[w] The w component.
 * @constructor Creates a quaternion.
 */
class Quaternion(x: Float = 0f, y: Float = 0f, z: Float = 0f, w: Float = 1f) {
    val components = floatArrayOf(x, y, z, w)

    constructor(other: Quaternion) : this(other.x, other.y, other.z, other.w)

    inline var x: Float
        get() = components[0]
        set(value) { components[0] = value }
    inline var y: Float
        get() = components[1]
        set(value) { components[1] = value }
    inline var z: Float
        get() = components[2]
        set(value) { components[2] = value }
    inline var w: Float
        get() = components[3]
        set(value) { components[3] = value }

    operator fun component1() = get(0)
    operator fun component2() = get(1)
    operator fun component3() = get(2)
    operator fun component4() = get(3)

    operator fun get(index: Int) = when(index) {
        0 -> x
        1 -> y
        2 -> z
        3 -> w
        else -> throw IllegalArgumentException("index must be in 0..3")
    }

    operator fun set(index: Int, value: Float) = when(index) {
        0 -> x = value
        1 -> y = value
        2 -> z = value
        3 -> w = value
        else -> throw IllegalArgumentException("index must be in 0..3")
    }

    fun set(other: Quaternion) = set(other.x, other.y, other.z, other.w)
    fun set(x: Float = this.x, y: Float = this.y, z: Float = this.z, w: Float = this.w): Quaternion {
        this.x = x
        this.y = y
        this.z = z
        this.w = w

        return this
    }

    fun copy() = Quaternion(this)

    override fun toString() = components.joinToString(prefix = "(", postfix = ")")

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Vector) {
            return false
        }

        for(i in 0..3) {
            if(this[i] isNotCloseTo other[i]) {
                return false
            }
        }

        return true
    }

    override fun hashCode() = Arrays.hashCode(components)
}