@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.math

import kotlin.math.*

inline val Vector.magnitude: Float
    get() = sqrt(magnitudeSquared)
inline val Vector.magnitudeSquared: Float
    get() = dot(this,this)

/** Whether all components are zero. */
inline val Vector.isZero: Boolean
    get() = components.all { it.isZero() }
/** Whether this vector is a unit length vector. */
inline val Vector.isUnit: Boolean
    get() = magnitudeSquared.isOne()
/** Whether all components are 1. */
inline val Vector.isOne: Boolean
    get() = components.all { it.isOne() }

/** The smallest between all components. */
inline val Vector.minComponent : Float
    get() = components.min() ?: 0f
/** The largest between all components. */
inline val Vector.maxComponent : Float
    get() = components.max() ?: 0f
/** The dimension that contains the smallest component. */
inline val Vector.minDimension : Int
    get() = components.indexOf(minComponent)
/** The dimension that contains the largest component. */
inline val Vector.maxDimension : Int
    get() = components.indexOf(maxComponent)

// ---

/** Whether [a] is orthogonal to [b]. */
inline fun orthogonal(a: Vector, b: Vector) = dot(a, b).isZero() || angle(a, b).isEqualTo(90f)

/** Whether [a] is orthogonal to [b] and both vectors are unit vectors. */
inline fun orthonormal(a: Vector, b: Vector) = a.isUnit && b.isUnit && orthogonal(a, b)

/** Whether [a] is parallel to [b]. */
fun parallel(a: Vector, b: Vector): Boolean {
    val dimensions = max(a.dimensions, b.dimensions)
    if(dimensions == 0) {
        return true
    }

    val reference = a[0] / b[0]

    for(i in 1 until dimensions) {
        if(a[i] / b[i] isNotCloseTo reference) {
            return false
        }
    }

    return true
}

/** Returns the min angle in degrees between [a] and [b]. */
fun angle(a: Vector, b: Vector) : Float {
    val cos = dot(a, b) / (a.magnitude * b.magnitude)
    val acos = acos(cos)

    return acos * RADIANS_TO_DEGREES
}

/** Returns the dot product between [a] and [b]. */
fun dot(a: Vector, b: Vector): Float {
    val dimensions = min(a.dimensions, b.dimensions)

    var dot = 0f
    for(i in 0 until dimensions) {
        dot += a.components[i] * b.components[i]
    }

    return dot
}

/** Returns the absolute value of the dot product between [a] and [b]. */
inline fun dotAbs(a: Vector, b: Vector) = abs(dot(a, b))

// ---

/** 
 * Negates each component of [vector] and stores the result in [out].
 * 
 * @return The [out] vector for chaining.
 */
inline fun negate(vector: Vector2, out: Vector2 = Vector2()) = out.set(-vector.x, -vector.y)
/** 
 * Negates each component of [vector] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun negate(vector: Vector3, out: Vector3 = Vector3()) = out.set(-vector.x, -vector.y, -vector.z)
/** 
 * Negates each component of [vector] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun negate(vector: Vector4, out: Vector4 = Vector4()) = out.set(-vector.x, -vector.y, -vector.z, -vector.w)

/** 
 * Normalizes [vector] and stores the result in [out].
 * If [vector] is the zero vector, the result is the zero vector.
 *
 * @return The [out] vector for chaining.
 */
inline fun normalize(vector: Vector2, out: Vector2 = Vector2()) = if(vector.isZero) {
    out.set(vector)
} else {
    scale(vector, 1 / vector.magnitude, out)
}
/**
 * Normalizes [vector] and stores the result in [out].
 * If [vector] is the zero vector, the result is the zero vector.
 *
 * @return The [out] vector for chaining.
 */
fun normalize(vector: Vector3, out: Vector3 = Vector3()) = if(vector.isZero) {
    out.set(vector)
} else {
    scale(vector, 1 / vector.magnitude, out)
}
/**
 *  Normalizes [vector] and stores the result in [out].
 * If [vector] is the zero vector, the result is the zero vector.
 *
 * @return The [out] vector for chaining.
 */
fun normalize(vector: Vector4, out: Vector4 = Vector4()) = if(vector.isZero) {
    out.set(vector)
} else {
    scale(vector, 1 / vector.magnitude, out)
}

/** 
 * Applies the absolute value to each component of [vector] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun abs(vector: Vector2, out: Vector2 = Vector2()) = out.set(abs(vector.x), abs(vector.y))
/**
 * Applies the absolute value to each component of [vector] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun abs(vector: Vector3, out: Vector3 = Vector3()) = out.set(abs(vector.x), abs(vector.y), abs(vector.z))
/**
 * Applies the absolute value to each component of [vector] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun abs(vector: Vector4, out: Vector4 = Vector4()) = out.set(abs(vector.x), abs(vector.y), abs(vector.z), abs(vector.w))

/**
 * Scales [vector] (i.e., multiplies each component with [scalar]) and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun scale(vector: Vector2, scalar: Float, out: Vector2 = Vector2()) = out.set(vector.x * scalar, vector.y * scalar)
/**
 * Scales [vector] (i.e., multiplies each component with [scalar]) and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun scale(vector: Vector3, scalar: Float, out: Vector3 = Vector3()) = out.set(vector.x * scalar, vector.y * scalar, vector.z * scalar)
/**
 * Scales [vector] (i.e., multiplies each component with [scalar]) and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun scale(vector: Vector4, scalar: Float, out: Vector4 = Vector4()) = out.set(vector.x * scalar, vector.y * scalar, vector.z * scalar, vector.w * scalar)

/** 
 * Adds [a] and [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun add(a: Vector2, b: Vector2, out: Vector2 = Vector2()) = out.set(a.x + b.x, a.y + b.y)
/** 
 * Adds [a] and [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun add(a: Vector2, b: Vector3, out: Vector3 = Vector3()) = out.set(a.x + b.x, a.y + b.y, b.z)
/** 
 * Adds [a] and [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun add(a: Vector2, b: Vector4, out: Vector4 = Vector4()) = out.set(a.x + b.x, a.y + b.y, b.z, b.w)
/**
 * Adds [a] and [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun add(a: Vector3, b: Vector2, out: Vector3 = Vector3()) = out.set(a.x + b.x, a.y + b.y, a.z)
/**
 * Adds [a] and [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun add(a: Vector3, b: Vector3, out: Vector3 = Vector3()) = out.set(a.x + b.x, a.y + b.y, a.z + b.z)
/** 
 * Adds [a] and [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun add(a: Vector3, b: Vector4, out: Vector4 = Vector4()) = out.set(a.x + b.x, a.y + b.y, a.z + b.z, b.w)
/** 
 * Adds [a] and [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun add(a: Vector4, b: Vector2, out: Vector4 = Vector4()) = out.set(a.x + b.x, a.y + b.y, a.z, a.w)
/** 
 * Adds [a] and [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun add(a: Vector4, b: Vector3, out: Vector4 = Vector4()) = out.set(a.x + b.x, a.y + b.y, a.z + b.z, a.w)
/** 
 * Adds [a] and [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun add(a: Vector4, b: Vector4, out: Vector4 = Vector4()) = out.set(a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w)

/**
 * Subtracts [b] from [a] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun subtract(a: Vector2, b: Vector2, out: Vector2 = Vector2()) = out.set(a.x - b.x, a.y - b.y)
/**
 * Subtracts [b] from [a] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun subtract(a: Vector2, b: Vector3, out: Vector3 = Vector3()) = out.set(a.x - b.x, a.y - b.y, -b.z)
/** 
 * Subtracts [b] from [a] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun subtract(a: Vector2, b: Vector4, out: Vector4 = Vector4()) = out.set(a.x - b.x, a.y - b.y, -b.z, -b.w)
/** 
 * Subtracts [b] from [a] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun subtract(a: Vector3, b: Vector2, out: Vector3 = Vector3()) = out.set(a.x - b.x, a.y - b.y, a.z)
/** 
 * Subtracts [b] from [a] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun subtract(a: Vector3, b: Vector3, out: Vector3 = Vector3()) = out.set(a.x - b.x, a.y - b.y, a.z - b.z)
/** 
 * Subtracts [b] from [a] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun subtract(a: Vector3, b: Vector4, out: Vector4 = Vector4()) = out.set(a.x - b.x, a.y - b.y, a.z - b.z, -b.w)
/** Subtracts [b] from [a] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun subtract(a: Vector4, b: Vector2, out: Vector4 = Vector4()) = out.set(a.x - b.x, a.y - b.y, a.z, a.w)
/** 
 * Subtracts [b] from [a] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun subtract(a: Vector4, b: Vector3, out: Vector4 = Vector4()) = out.set(a.x - b.x, a.y - b.y, a.z - b.z, a.w)
/** 
 * Subtracts [b] from [a] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun subtract(a: Vector4, b: Vector4, out: Vector4 = Vector4()) = out.set(a.x - b.x, a.y - b.y, a.z - b.z, a.w - b.w)

/** 
 * Stores in [out] the vector composed of the smallest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun min(a: Vector2, b: Vector2, out: Vector2 = Vector2()) = out.set(kotlin.math.min(a.x, b.x), kotlin.math.min(a.y, b.y))
/** 
 * Stores in [out] the vector composed of the smallest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun min(a: Vector2, b: Vector3, out: Vector3 = Vector3()) = out.set(kotlin.math.min(a.x, b.x), kotlin.math.min(a.y, b.y), kotlin.math.min(a.z, b.z))
/** 
 * Stores in [out] the vector composed of the smallest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun min(a: Vector2, b: Vector4, out: Vector4 = Vector4()) = out.set(kotlin.math.min(a.x, b.x), kotlin.math.min(a.y, b.y), kotlin.math.min(a.z, b.z), kotlin.math.min(a.w, b.w))
/** 
 * Stores in [out] the vector composed of the smallest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun min(a: Vector3, b: Vector2, out: Vector3 = Vector3()) = out.set(kotlin.math.min(a.x, b.x), kotlin.math.min(a.y, b.y), kotlin.math.min(a.z, b.z))
/** 
 * Stores in [out] the vector composed of the smallest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun min(a: Vector3, b: Vector3, out: Vector3 = Vector3()) = out.set(kotlin.math.min(a.x, b.x), kotlin.math.min(a.y, b.y), kotlin.math.min(a.z, b.z))
/** 
 * Stores in [out] the vector composed of the smallest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun min(a: Vector3, b: Vector4, out: Vector4 = Vector4()) = out.set(kotlin.math.min(a.x, b.x), kotlin.math.min(a.y, b.y), kotlin.math.min(a.z, b.z), kotlin.math.min(a.w, b.w))
/** 
 * Stores in [out] the vector composed of the smallest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun min(a: Vector4, b: Vector2, out: Vector4 = Vector4()) = out.set(kotlin.math.min(a.x, b.x), kotlin.math.min(a.y, b.y), kotlin.math.min(a.z, b.z), kotlin.math.min(a.w, b.w))
/** 
 * Stores in [out] the vector composed of the smallest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun min(a: Vector4, b: Vector3, out: Vector4 = Vector4()) = out.set(kotlin.math.min(a.x, b.x), kotlin.math.min(a.y, b.y), kotlin.math.min(a.z, b.z), kotlin.math.min(a.w, b.w))
/** 
 * Stores in [out] the vector composed of the smallest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun min(a: Vector4, b: Vector4, out: Vector4 = Vector4()) = out.set(kotlin.math.min(a.x, b.x), kotlin.math.min(a.y, b.y), kotlin.math.min(a.z, b.z), kotlin.math.min(a.w, b.w))

/** 
 * Stores in [out] the vector composed of the largest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun max(a: Vector2, b: Vector2, out: Vector2 = Vector2()) = out.set(kotlin.math.max(a.x, b.x), kotlin.math.max(a.y, b.y))
/** 
 * Stores in [out] the vector composed of the largest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun max(a: Vector2, b: Vector3, out: Vector3 = Vector3()) = out.set(kotlin.math.max(a.x, b.x), kotlin.math.max(a.y, b.y), kotlin.math.max(a.z, b.z))
/** 
 * Stores in [out] the vector composed of the largest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun max(a: Vector2, b: Vector4, out: Vector4 = Vector4()) = out.set(kotlin.math.max(a.x, b.x), kotlin.math.max(a.y, b.y), kotlin.math.max(a.z, b.z), kotlin.math.max(a.w, b.w))
/** 
 * Stores in [out] the vector composed of the largest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun max(a: Vector3, b: Vector2, out: Vector3 = Vector3()) = out.set(kotlin.math.max(a.x, b.x), kotlin.math.max(a.y, b.y), kotlin.math.max(a.z, b.z))
/** 
 * Stores in [out] the vector composed of the largest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun max(a: Vector3, b: Vector3, out: Vector3 = Vector3()) = out.set(kotlin.math.max(a.x, b.x), kotlin.math.max(a.y, b.y), kotlin.math.max(a.z, b.z))
/** 
 * Stores in [out] the vector composed of the largest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun max(a: Vector3, b: Vector4, out: Vector4 = Vector4()) = out.set(kotlin.math.max(a.x, b.x), kotlin.math.max(a.y, b.y), kotlin.math.max(a.z, b.z), kotlin.math.max(a.w, b.w))
/** 
 * Stores in [out] the vector composed of the largest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun max(a: Vector4, b: Vector2, out: Vector4 = Vector4()) = out.set(kotlin.math.max(a.x, b.x), kotlin.math.max(a.y, b.y), kotlin.math.max(a.z, b.z), kotlin.math.max(a.w, b.w))
/** 
 * Stores in [out] the vector composed of the largest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun max(a: Vector4, b: Vector3, out: Vector4 = Vector4()) = out.set(kotlin.math.max(a.x, b.x), kotlin.math.max(a.y, b.y), kotlin.math.max(a.z, b.z), kotlin.math.max(a.w, b.w))
/** 
 * Stores in [out] the vector composed of the largest components between [a] and [b].
 *
 * @return The [out] vector for chaining.
 */
inline fun max(a: Vector4, b: Vector4, out: Vector4 = Vector4()) = out.set(kotlin.math.max(a.x, b.x), kotlin.math.max(a.y, b.y), kotlin.math.max(a.z, b.z), kotlin.math.max(a.w, b.w))

/**
 * Projects [a] onto [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun project(a: Vector2, b: Vector2, out: Vector2 = Vector2()) : Vector2 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return out.set(b).scale(scalar)
}
/**
 * Projects [a] onto [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun project(a: Vector2, b: Vector3, out: Vector3 = Vector3()) : Vector3 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return out.set(b).scale(scalar)
}
/**
 * Projects [a] onto [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun project(a: Vector2, b: Vector4, out: Vector4 = Vector4()) : Vector4 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return out.set(b).scale(scalar)
}
/**
 * Projects [a] onto [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun project(a: Vector3, b: Vector2, out: Vector2) : Vector2 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return out.set(b).scale(scalar)
}
/**
 * Projects [a] onto [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun project(a: Vector3, b: Vector2, out: Vector3 = Vector3()) : Vector3 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return out.set(b).scale(scalar)
}
/**
 * Projects [a] onto [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun project(a: Vector3, b: Vector3, out: Vector3 = Vector3()) : Vector3 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return out.set(b).scale(scalar)
}
/**
 * Projects [a] onto [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun project(a: Vector3, b: Vector4, out: Vector4 = Vector4()) : Vector4 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return out.set(b).scale(scalar)
}
/**
 * Projects [a] onto [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun project(a: Vector4, b: Vector2, out: Vector2) : Vector2 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return out.set(b).scale(scalar)
}
/**
 * Projects [a] onto [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun project(a: Vector4, b: Vector2, out: Vector4 = Vector4()) : Vector4 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return out.set(b).scale(scalar)
}
/**
 * Projects [a] onto [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun project(a: Vector4, b: Vector3, out: Vector3) : Vector3 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return out.set(b).scale(scalar)
}
/**
 * Projects [a] onto [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun project(a: Vector4, b: Vector3, out: Vector4 = Vector4()) : Vector4 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return out.set(b).scale(scalar)
}
/**
 * Projects [a] onto [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun project(a: Vector4, b: Vector4, out: Vector4 = Vector4()) : Vector4 {
    val scalar = dot(a, b) / b.magnitudeSquared

    return out.set(b).scale(scalar)
}

/**
 * Rejects [a] from [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun reject(a: Vector2, b: Vector2, out: Vector2 = Vector2()) : Vector2 {
    val oX = a.x
    val oY = a.y

    project(a, b, out)

    return out.set(oX - out.x, oY - out.y)
}
/**
 * Rejects [a] from [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun reject(a: Vector2, b: Vector3, out: Vector3 = Vector3()) : Vector3 {
    val oX = a.x
    val oY = a.y

    project(a, b, out)

    return out.set(oX - out.x, oY - out.y, -out.z)
}
/**
 * Rejects [a] from [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun reject(a: Vector2, b: Vector4, out: Vector4 = Vector4()) : Vector4 {
    val oX = a.x
    val oY = a.y

    project(a, b, out)

    return out.set(oX - out.x, oY - out.y, -out.z, -out.w)
}
/**
 * Rejects [a] from [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun reject(a: Vector3, b: Vector2, out: Vector3 = Vector3()) : Vector3 {
    val oX = a.x
    val oY = a.y
    val oZ = a.z

    project(a, b, out)

    return out.set(oX - out.x, oY - out.y, oZ)
}
/**
 * Rejects [a] from [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun reject(a: Vector3, b: Vector3, out: Vector3 = Vector3()) : Vector3 {
    val oX = a.x
    val oY = a.y
    val oZ = a.z

    project(a, b, out)

    return out.set(oX - out.x, oY - out.y, oZ - out.z)
}
/**
 * Rejects [a] from [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun reject(a: Vector3, b: Vector4, out: Vector4 = Vector4()) : Vector4 {
    val oX = a.x
    val oY = a.y
    val oZ = a.z

    project(a, b, out)

    return out.set(oX - out.x, oY - out.y, oZ - out.z, -out.w)
}

/**
 * Rejects [a] from [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun reject(a: Vector4, b: Vector2, out: Vector4 = Vector4()) : Vector4 {
    val oX = a.x
    val oY = a.y
    val oZ = a.z
    val oW = a.w

    project(a, b, out)

    return out.set(oX - out.x, oY - out.y, oZ, oW)
}
/**
 * Rejects [a] from [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun reject(a: Vector4, b: Vector3, out: Vector4 = Vector4()) : Vector4 {
    val oX = a.x
    val oY = a.y
    val oZ = a.z
    val oW = a.w

    project(a, b, out)

    return out.set(oX - out.x, oY - out.y, oZ - out.z, oW)
}
/**
 * Rejects [a] from [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
fun reject(a: Vector4, b: Vector4, out: Vector4 = Vector4()) : Vector4 {
    val oX = a.x
    val oY = a.y
    val oZ = a.z
    val oW = a.w

    project(a, b, out)

    return out.set(oX - out.x, oY - out.y, oZ - out.z, oW - out.w)
}

/**
 * Cross product between [a] and [b] and stores the result in [out].
 *
 * @return The [out] vector for chaining.
 */
inline fun cross(a: Vector3, b: Vector3, out: Vector3 = Vector3()) = out.set(
        a.y * b.z - a.z * b.y,
        a.z * b.x - a.x * b.z,
        a.x * b.y - a.y * b.x
)

// ---

inline operator fun Vector2.unaryPlus() = this.copy()
inline operator fun Vector2.unaryMinus() = negate(this, Vector2())

inline operator fun Vector2.plus(other: Vector2) = add(this, other, Vector2())
inline operator fun Vector2.plus(other: Vector3) = add(this, other, Vector3())
inline operator fun Vector2.plus(other: Vector4) = add(this, other, Vector4())
inline operator fun Vector2.minus(other: Vector2) = subtract(this, other, Vector2())
inline operator fun Vector2.minus(other: Vector3) = subtract(this, other, Vector3())
inline operator fun Vector2.minus(other: Vector4) = subtract(this, other, Vector4())
inline operator fun Vector2.times(scalar: Float) = scale(this, scalar, Vector2())
inline operator fun Vector2.div(scalar: Float) = times(1 / scalar)

inline operator fun Vector2.plusAssign(other: Vector2) { add(other) }
inline operator fun Vector2.minusAssign(other: Vector2) { subtract(other) }
inline operator fun Vector2.timesAssign(scalar: Float) { scale(scalar) }
inline operator fun Vector2.divAssign(scalar: Float) = timesAssign(1 / scalar)

/**
 * Negates each component.
 * 
 * @return This vector for chaining.
 */
inline fun Vector2.negate() = negate(this, this)

/**
 * Normalizes the vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector2.normalize() = normalize(this, this)

/**
 * Applies the absolute value to each component.
 *
 * @return This vector for chaining.
 */
inline fun Vector2.abs() = abs(this, this)

/**
 * Scales this vector (i.e., multiplies each component with [scalar]).
 * 
 * @return This vector for chaining.
 */
inline fun Vector2.scale(scalar: Float) = scale(this, scalar, this)

/**
 * Adds [other] to this vector.
 * 
 * @return This vector for chaining.
 */
inline fun Vector2.add(other: Vector2) = add(this, other, this)
/**
 * Adds ([x], [y]) to this vector.
 * 
 * @return This vector for chaining.
 */
inline fun Vector2.add(x: Float, y: Float) = add(cVector(x, y))

/**
 * Subtracts [other] from this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector2.subtract(other: Vector2) = subtract(this, other, this)
/**
 * Subtracts ([x], [y]) from this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector2.subtract(x: Float, y: Float) = subtract(cVector(x, y))

/**
 * Composes this vector with the smallest components between this vector and [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector2.min(other: Vector2) = min(this, other, this)
/**
 * Composes this vector with the smallest components between this vector and ([x], [y]).
 *
 * @return This vector for chaining.
 */
inline fun Vector2.min(x: Float, y: Float) = min(cVector(x, y))

/**
 * Composes this vector with the largest components between this vector and [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector2.max(other: Vector2) = max(this, other, this)
/**
 * Composes this vector with the largest components between this vector and ([x], [y]).
 *
 * @return This vector for chaining.
 */
inline fun Vector2.max(x: Float, y: Float) = max(cVector(x, y))

/**
 * Projects this vector onto [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector2.projectOnto(other: Vector2) = project(this, other, this)
/**
 * Projects this vector onto ([x], [y]).
 *
 * @return This vector for chaining.
 */
inline fun Vector2.projectOnto(x: Float, y: Float) = projectOnto(cVector(x, y))

/**
 * Rejects this vector from [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector2.rejectFrom(other: Vector2) = reject(this, other, this)
/**
 * Rejects this vector from ([x], [y]).
 *
 * @return This vector for chaining.
 */
inline fun Vector2.rejectFrom(x: Float, y: Float) = rejectFrom(cVector(x, y))

/**
 * Multiplies [matrix] with this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector2.multiplyLeft(matrix: Matrix2) = multiply(matrix, this, this)

/**
 * Multiplies [matrix] with this vector. Use this method only if [matrix] is known to be a 2D transformation.
 *
 * @return This vector for chaining.
 */
inline fun Vector2.multiplyLeft(matrix: Matrix4) = multiply(matrix, this, this)

// ---

inline operator fun Vector3.unaryPlus() = this.copy()
inline operator fun Vector3.unaryMinus() = negate(this, Vector3())

inline operator fun Vector3.plus(other: Vector2) = add(this, other, Vector3())
inline operator fun Vector3.plus(other: Vector3) = add(this, other, Vector3())
inline operator fun Vector3.plus(other: Vector4) = add(this, other, Vector4())
inline operator fun Vector3.minus(other: Vector2) = subtract(this, other, Vector3())
inline operator fun Vector3.minus(other: Vector3) = subtract(this, other, Vector3())
inline operator fun Vector3.minus(other: Vector4) = subtract(this, other, Vector4())
inline operator fun Vector3.times(scalar: Float) = scale(this, scalar, Vector3())
inline operator fun Vector3.div(scalar: Float) = times(1 / scalar)

inline operator fun Vector3.plusAssign(other: Vector2) { add(other) }
inline operator fun Vector3.plusAssign(other: Vector3) { add(other) }
inline operator fun Vector3.minusAssign(other: Vector2) { subtract(other) }
inline operator fun Vector3.minusAssign(other: Vector3) { subtract(other) }
inline operator fun Vector3.timesAssign(scalar: Float) { scale(scalar) }
inline operator fun Vector3.divAssign(scalar: Float) = timesAssign(1 / scalar)

/**
 * Negates each component.
 *
 * @return This vector for chaining.
 */
inline fun Vector3.negate() = negate(this, this)

/**
 * Normalizes the vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector3.normalize() = normalize(this, this)

/**
 * Applies the absolute value to each component.
 *
 * @return This vector for chaining.
 */
inline fun Vector3.abs() = abs(this, this)

/**
 * Scales this vector (i.e., multiplies each component with [scalar]).
 *
 * @return This vector for chaining.
 */
inline fun Vector3.scale(scalar: Float) = scale(this, scalar, this)

/**
 * Adds [other] to this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector3.add(other: Vector2) = add(this, other, this)
/**
 * Adds [other] to this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector3.add(other: Vector3) = add(this, other, this)
/**
 * Adds ([x], [y]) to this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector3.add(x: Float, y: Float) = add(cVector(x, y))
/**
 * Adds ([x], [y], [z]) to this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector3.add(x: Float, y: Float, z: Float) = add(cVector(x, y, z))

/**
 * Subtracts [other] from this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector3.subtract(other: Vector2) = subtract(this, other, this)
/**
 * Subtracts [other] from this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector3.subtract(other: Vector3) = subtract(this, other, this)
/**
 * Subtracts ([x], [y]) from this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector3.subtract(x: Float, y: Float) = subtract(cVector(x, y))
/**
 * Subtracts ([x], [y], [z]) from this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector3.subtract(x: Float, y: Float, z: Float) = subtract(cVector(x, y, z))

/**
 * Composes this vector with the smallest components between this vector and [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector3.min(other: Vector2) = min(this, other, this)
/**
 * Composes this vector with the smallest components between this vector and [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector3.min(other: Vector3) = min(this, other, this)
/**
 * Composes this vector with the smallest components between this vector and ([x], [y]).
 *
 * @return This vector for chaining.
 */
inline fun Vector3.min(x: Float, y: Float) = min(cVector(x, y))
/**
 * Composes this vector with the smallest components between this vector and ([x], [y], [z]).
 *
 * @return This vector for chaining.
 */
inline fun Vector3.min(x: Float, y: Float, z: Float) = min(cVector(x, y, z))

/**
 * Composes this vector with the largest components between this vector and [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector3.max(other: Vector2) = max(this, other, this)
/**
 * Composes this vector with the largest components between this vector and [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector3.max(other: Vector3) = max(this, other, this)
/**
 * Composes this vector with the largest components between this vector and ([x], [y]).
 *
 * @return This vector for chaining.
 */
inline fun Vector3.max(x: Float, y: Float) = max(cVector(x, y))
/**
 * Composes this vector with the largest components between this vector and ([x], [y], [z]).
 *
 * @return This vector for chaining.
 */
inline fun Vector3.max(x: Float, y: Float, z: Float) = max(cVector(x, y, z))

/**
 * Projects this vector onto [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector3.projectOnto(other: Vector2) = project(this, other, this)
/**
 * Projects this vector onto [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector3.projectOnto(other: Vector3) = project(this, other, this)
/**
 * Projects this vector onto ([x], [y]).
 *
 * @return This vector for chaining.
 */
inline fun Vector3.projectOnto(x: Float, y: Float) = projectOnto(cVector(x, y))
/**
 * Projects this vector onto ([x], [y], [z]).
 *
 * @return This vector for chaining.
 */
inline fun Vector3.projectOnto(x: Float, y: Float, z: Float) = projectOnto(cVector(x, y, z))

/**
 * Rejects this vector from [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector3.rejectFrom(other: Vector2) = reject(this, other, this)
/**
 * Rejects this vector from [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector3.rejectFrom(other: Vector3) = reject(this, other, this)
/**
 * Rejects this vector from ([x], [y]).
 *
 * @return This vector for chaining.
 */
inline fun Vector3.rejectFrom(x: Float, y: Float) = rejectFrom(cVector(x, y))
/**
 * Rejects this vector from ([x], [y], [z]).
 *
 * @return This vector for chaining.
 */
inline fun Vector3.rejectFrom(x: Float, y: Float, z: Float) = rejectFrom(cVector(x, y, z))

/**
 * Cross product between this vector and [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector3.cross(other: Vector3) = cross(this, other, this)
/**
 * Cross product between this vector and ([x], [y], [z]).
 *
 * @return This vector for chaining.
 */
inline fun Vector3.cross(x: Float, y: Float, z: Float) = cross(cVector(x, y, z))

/**
 * Multiplies [matrix] with this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector3.multiplyLeft(matrix: Matrix3) = multiply(matrix, this, this)

/**
 * Multiplies [matrix] with this vector. Use this method only if [matrix] is known to be a transformation.
 *
 * @return This vector for chaining.
 */
inline fun Vector3.multiplyLeft(matrix: Matrix4) = multiply(matrix, this, this)

// ---

inline operator fun Vector4.unaryPlus() = this.copy()
inline operator fun Vector4.unaryMinus() = negate(this, Vector4())

inline operator fun Vector4.plus(other: Vector2) = add(this, other, Vector4())
inline operator fun Vector4.plus(other: Vector3) = add(this, other, Vector4())
inline operator fun Vector4.plus(other: Vector4) = add(this, other, Vector4())
inline operator fun Vector4.minus(other: Vector2) = subtract(this, other, Vector4())
inline operator fun Vector4.minus(other: Vector3) = subtract(this, other, Vector4())
inline operator fun Vector4.minus(other: Vector4) = subtract(this, other, Vector4())
inline operator fun Vector4.times(scalar: Float) = scale(this, scalar, Vector4())
inline operator fun Vector4.div(scalar: Float) = times(1 / scalar)

inline operator fun Vector4.plusAssign(other: Vector2) { add(other) }
inline operator fun Vector4.plusAssign(other: Vector3) { add(other) }
inline operator fun Vector4.plusAssign(other: Vector4) { add(other) }
inline operator fun Vector4.minusAssign(other: Vector2) { subtract(other) }
inline operator fun Vector4.minusAssign(other: Vector3) { subtract(other) }
inline operator fun Vector4.minusAssign(other: Vector4) { subtract(other) }
inline operator fun Vector4.timesAssign(scalar: Float) { scale(scalar) }
inline operator fun Vector4.divAssign(scalar: Float) = timesAssign(1 / scalar)

/**
 * Negates each component.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.negate() = negate(this, this)

/**
 * Normalizes the vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.normalize() = normalize(this, this)

/**
 * Applies the absolute value to each component.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.abs() = abs(this, this)

/**
 * Scales this vector (i.e., multiplies each component with [scalar]).
 *
 * @return This vector for chaining.
 */
inline fun Vector4.scale(scalar: Float) = scale(this, scalar, this)

/**
 * Adds [other] to this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.add(other: Vector2) = add(this, other, this)
/**
 * Adds [other] to this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.add(other: Vector3) = add(this, other, this)
/**
 * Adds [other] to this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.add(other: Vector4) = add(this, other, this)
/**
 * Adds ([x], [y]) to this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.add(x: Float, y: Float) = add(cVector(x, y))
/**
 * Adds ([x], [y], [z]) to this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.add(x: Float, y: Float, z: Float) = add(cVector(x, y, z))
/**
 * Adds ([x], [y], [z], [w]) to this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.add(x: Float, y: Float, z: Float, w: Float) = add(cVector(x, y, z, w))

/**
 * Subtracts [other] from this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.subtract(other: Vector2) = subtract(this, other, this)
/**
 * Subtracts [other] from this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.subtract(other: Vector3) = subtract(this, other, this)
/**
 * Subtracts [other] from this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.subtract(other: Vector4) = subtract(this, other, this)
/**
 * Subtracts ([x], [y]) from this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.subtract(x: Float, y: Float) = subtract(cVector(x, y))
/**
 * Subtracts ([x], [y], [z]) from this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.subtract(x: Float, y: Float, z: Float) = subtract(cVector(x, y, z))
/**
 * Subtracts ([x], [y], [z], [w]) from this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.subtract(x: Float, y: Float, z: Float, w: Float) = subtract(cVector(x, y, z, w))

/**
 * Composes this vector with the smallest components between this vector and [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector4.min(other: Vector2) = min(this, other, this)
/**
 * Composes this vector with the smallest components between this vector and [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector4.min(other: Vector3) = min(this, other, this)
/**
 * Composes this vector with the smallest components between this vector and [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector4.min(other: Vector4) = min(this, other, this)
/**
 * Composes this vector with the smallest components between this vector and ([x], [y]).
 *
 * @return This vector for chaining.
 */
inline fun Vector4.min(x: Float, y: Float) = min(cVector(x, y))
/**
 * Composes this vector with the smallest components between this vector and ([x], [y], [z]).
 *
 * @return This vector for chaining.
 */
inline fun Vector4.min(x: Float, y: Float, z: Float) = min(cVector(x, y, z))
/**
 * Composes this vector with the smallest components between this vector and ([x], [y], [z], [w]).
 *
 * @return This vector for chaining.
 */
inline fun Vector4.min(x: Float, y: Float, z: Float, w: Float) = min(cVector(x, y, z, w))

/**
 * Composes this vector with the largest components between this vector and [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector4.max(other: Vector2) = max(this, other, this)
/**
 * Composes this vector with the largest components between this vector and [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector4.max(other: Vector3) = max(this, other, this)
/**
 * Composes this vector with the largest components between this vector and [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector4.max(other: Vector4) = max(this, other, this)
/**
 * Composes this vector with the largest components between this vector and ([x], [y]).
 *
 * @return This vector for chaining.
 */
inline fun Vector4.max(x: Float, y: Float) = max(cVector(x, y))
/**
 * Composes this vector with the largest components between this vector and ([x], [y], [z]).
 *
 * @return This vector for chaining.
 */
inline fun Vector4.max(x: Float, y: Float, z: Float) = max(cVector(x, y, z))
/**
 * Composes this vector with the largest components between this vector and ([x], [y], [z], [w]).
 *
 * @return This vector for chaining.
 */
inline fun Vector4.max(x: Float, y: Float, z: Float, w: Float) = max(cVector(x, y, z, w))

/**
 * Projects this vector onto [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector4.projectOnto(other: Vector2) = project(this, other, this)
/**
 * Projects this vector onto [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector4.projectOnto(other: Vector3) = project(this, other, this)
/**
 * Projects this vector onto [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector4.projectOnto(other: Vector4) = project(this, other, this)
/**
 * Projects this vector onto ([x], [y]).
 *
 * @return This vector for chaining.
 */
inline fun Vector4.projectOnto(x: Float, y: Float) = projectOnto(cVector(x, y))
/**
 * Projects this vector onto ([x], [y], [z]).
 *
 * @return This vector for chaining.
 */
inline fun Vector4.projectOnto(x: Float, y: Float, z: Float) = projectOnto(cVector(x, y, z))
/**
 * Projects this vector onto ([x], [y], [z], [w]).
 *
 * @return This vector for chaining.
 */
inline fun Vector4.projectOnto(x: Float, y: Float, z: Float, w: Float) = projectOnto(cVector(x, y, z, w))

/**
 * Rejects this vector from [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector4.rejectFrom(other: Vector2) = reject(this, other, this)
/**
 * Rejects this vector from [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector4.rejectFrom(other: Vector3) = reject(this, other, this)
/**
 * Rejects this vector from [other].
 *
 * @return This vector for chaining.
 */
inline fun Vector4.rejectFrom(other: Vector4) = reject(this, other, this)
/**
 * Rejects this vector from ([x], [y]).
 *
 * @return This vector for chaining.
 */
inline fun Vector4.rejectFrom(x: Float, y: Float) = rejectFrom(cVector(x, y))
/**
 * Rejects this vector from ([x], [y], [z]).
 *
 * @return This vector for chaining.
 */
inline fun Vector4.rejectFrom(x: Float, y: Float, z: Float) = rejectFrom(cVector(x, y, z))
/**
 * Rejects this vector from ([x], [y], [z], [w]).
 *
 * @return This vector for chaining.
 */
inline fun Vector4.rejectFrom(x: Float, y: Float, z: Float, w: Float) = rejectFrom(cVector(x, y, z, w))

/**
 * Multiplies [matrix] with this vector.
 *
 * @return This vector for chaining.
 */
inline fun Vector4.multiplyLeft(matrix: Matrix4) = multiply(matrix, this, this)

// ---

private val v2 by lazy { Vector2() }
private val v3 by lazy { Vector3() }
private val v4 by lazy { Vector4() }

fun cVector(x: Float, y: Float) = v2.set(x, y)
fun cVector(x: Float, y: Float, z: Float) = v3.set(x, y, z)
fun cVector(x: Float, y: Float, z: Float, w: Float) = v4.set(x, y, z, w)
fun cVector(other: Vector2) = v2.set(other)
fun cVector(other: Vector3) = v3.set(other)
fun cVector(other: Vector4) = v4.set(other)