package org.rufousengine.math

import java.util.*
import kotlin.math.*

/**
 * An abstract vector.
 *
 * The purpose of the [Vector] classes is to represent directions. To represent positions, useProgram [Point] instead.
 *
 * @property[components] The components.
 * @constructor Creates a vector pointing towards ([components].[0], [components].[1], ..., [components].[size - 1]).
 */
sealed class Vector(val components: FloatArray) {
    val dimensions = components.size

    operator fun component1() = get(0)
    operator fun component2() = get(1)
    operator fun component3() = get(2)
    operator fun component4() = get(3)

    operator fun get(index: Int) = when {
        index < 0 || index >= 4 -> throw IllegalArgumentException("index must be in 0..3")
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

        if(other !is Vector) {
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
 * A two-dimensional vector.
 *
 * The purpose of the [Vector] classes is to represent directions. To represent positions, useProgram [Point] instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @constructor Creates a two-dimensional vector pointing towards ([x], [y]).
 */
class Vector2(x: Float = 0f, y: Float = 0f): Vector(floatArrayOf(x, y)) {
    constructor(other: Vector2) : this(other.x, other.y)
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
    inline val w: Float
        get() = 0f

    fun set(other: Vector2) = set(other.x, other.y)
    fun set(x: Float = this.x, y: Float = this.y): Vector2 {
        this.x = x
        this.y = y

        return this
    }
    fun set(init: (Int) -> Float): Vector2 {
        repeat(dimensions) { i ->
            this[i] = init(i)
        }

        return this
    }

    fun copy() = Vector2(this)
}

/**
 * A three-dimensional vector.
 *
 * The purpose of the [Vector] classes is to represent directions. To represent positions, useProgram [Point] instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @constructor Creates a three-dimensional vector pointing towards ([x], [y], [z]).
 */
class Vector3(x: Float = 0f, y: Float = 0f, z: Float = 0f): Vector(floatArrayOf(x, y, z)) {
    constructor(other: Vector2) : this(other.x, other.y)
    constructor(other: Vector3) : this(other.x, other.y, other.z)
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
    inline val w: Float
        get() = 0f

    fun set(other: Vector2) = set(other.x, other.y, 0f)
    fun set(other: Vector3) = set(other.x, other.y, other.z)
    fun set(x: Float = this.x, y: Float = this.y, z: Float = this.z): Vector3 {
        this.x = x
        this.y = y
        this.z = z

        return this
    }
    fun set(init: (Int) -> Float): Vector3 {
        repeat(dimensions) { i ->
            this[i] = init(i)
        }

        return this
    }

    fun copy() = Vector3(this)
}

/**
 * A four-dimensional vector.
 *
 * The purpose of the [Vector] classes is to represent directions. To represent positions, useProgram [Point] instead.
 *
 * @property[x] The x component.
 * @property[y] The y component.
 * @property[z] The z component.
 * @property[w] The w component.
 * @constructor Creates a four-dimensional vector pointing towards ([x], [y], [z], [w]).
 */
class Vector4(x: Float = 0f, y: Float = 0f, z: Float = 0f, w: Float = 0f): Vector(floatArrayOf(x, y, z, w)) {
    constructor(other: Vector2) : this(other.x, other.y)
    constructor(other: Vector3) : this(other.x, other.y, other.z)
    constructor(other: Vector4) : this(other.x, other.y, other.z, other.w)
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
    inline var w: Float
        get() = components[3]
        set(value) { components[3] = value }

    fun set(other: Vector2) = set(other.x, other.y, 0f, 0f)
    fun set(other: Vector3) = set(other.x, other.y, other.z, 0f)
    fun set(other: Vector4) = set(other.x, other.y, other.z, other.w)
    fun set(x: Float = this.x, y: Float = this.y, z: Float = this.z, w: Float = this.w): Vector4 {
        this.x = x
        this.y = y
        this.z = z
        this.w = w

        return this
    }
    fun set(init: (Int) -> Float): Vector4 {
        repeat(dimensions) { i ->
            this[i] = init(i)
        }

        return this
    }

    fun copy() = Vector4(this)
}