package org.rufousengine.math

import java.util.*
import kotlin.math.max

/**
 * An abstract point.
 *
 * The purpose of the [Point] classes is to represent positions. To represent directions, useProgram [Vector] instead.
 *
 * @property[components] The components.
 * @constructor Creates a point at ([components].[0], [components].[1], ..., [components].[size - 1]).
 */
sealed class Point(val components: FloatArray) {
    abstract val dimensions: Int

    operator fun component1() = get(0)
    operator fun component2() = get(1)
    operator fun component3() = get(2)

    operator fun get(index: Int) = when {
        index < 0 || index >= 3 -> throw IllegalArgumentException("index must be in 0..2")
        index >= dimensions -> 0f
        else -> components[index]
    }

    operator fun set(index: Int, value: Float) = if(index in 0..(dimensions - 1))
        components[index] = value
    else throw IllegalArgumentException("index must be in 0..${ dimensions - 1 }")

    override fun toString() = components.joinToString(prefix = "(", postfix = ")")

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Point) {
            return false
        }

        val dimensions = max(dimensions, other.dimensions)

        for(i in 0 until dimensions) {
            if(this[i] isNotCloseTo other[i]) {
                return false
            }
        }

        return true
    }

    override fun hashCode() = Arrays.hashCode(components)
}

/**
 * A 2D point.
 *
 * The purpose of the [Point] classes is to represent positions. To represent directions, useProgram [Vector] instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @constructor Creates a point at ([x], [y]).
 */
class Point2D(x: Float = 0f, y: Float = 0f): Point(floatArrayOf(x, y)) {
    override val dimensions = 2

    constructor(other: Point2D) : this(other.x, other.y)
    constructor(init: (Int) -> Float) : this() {
        repeat(dimensions) { i ->
            this[i] = init(i)
        }
    }

    inline var x: Float
        get() = components[0]
        set(value) { components[0] = value }
    inline var y: Float
        get() = components[1]
        set(value) { components[1] = value }
    inline val z: Float
        get() = 0f

    fun set(other: Point2D) = set(other.x, other.y)
    fun set(x: Float = this.x, y: Float = this.y): Point2D {
        this.x = x
        this.y = y

        return this
    }
    fun set(init: (Int) -> Float): Point2D {
        repeat(dimensions) { i ->
            this[i] = init(i)
        }

        return this
    }

    fun copy() = Point2D(this)
}

/**
 * A 3D point.
 *
 * The purpose of the [Point] classes is to represent positions. To represent directions, useProgram [Vector] instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The y component.
 * @constructor Creates a point at ([x], [y], [z]).
 */
class Point3D(x: Float = 0f, y: Float = 0f, z: Float = 0f): Point(floatArrayOf(x, y, z)) {
    override val dimensions = 3

    constructor(other: Point2D) : this(other.x, other.y, 0f)
    constructor(other: Point3D) : this(other.x, other.y, other.z)
    constructor(init: (Int) -> Float) : this() {
        repeat(dimensions) { i ->
            this[i] = init(i)
        }
    }

    inline var x: Float
        get() = components[0]
        set(value) { components[0] = value }
    inline var y: Float
        get() = components[1]
        set(value) { components[1] = value }
    inline var z: Float
        get() = components[2]
        set(value) { components[2] = value }

    fun set(other: Point2D) = set(other.x, other.y, 0f)
    fun set(other: Point3D) = set(other.x, other.y, other.z)
    fun set(x: Float = this.x, y: Float = this.y, z: Float = this.z): Point3D {
        this.x = x
        this.y = y
        this.z = z

        return this
    }
    fun set(init: (Int) -> Float): Point3D {
        repeat(dimensions) { i ->
            this[i] = init(i)
        }

        return this
    }

    fun copy() = Point3D(this)
}