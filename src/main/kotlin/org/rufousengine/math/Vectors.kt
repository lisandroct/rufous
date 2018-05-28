package org.rufousengine.math

import kotlin.math.*

operator fun Vector.times(other: Vector) = dot(other)

val Vector.magnitude: Float
    get() = sqrt(magnitudeSquared)
val Vector.magnitudeSquared: Float
    get() = dot(this)

/** Whether all components are zero. */
val Vector.isZero: Boolean
    get() = components.all { it.isZero() }
/** Whether this vector is a unit length vector. */
val Vector.isUnit: Boolean
    get() = magnitudeSquared.isOne()
/** Whether all components are 1. */
val Vector.isOne: Boolean
    get() = components.all { it.isOne() }

/** The smallest between all components. */
val Vector.minComponent : Float
    get() = components.min() ?: 0f
/** The largest between all components. */
val Vector.maxComponent : Float
    get() = components.max() ?: 0f
/** The dimension that contains the smallest component. */
val Vector.minDimension : Int
    get() = components.indexOf(minComponent)
/** The dimension that contains the largest component. */
val Vector.maxDimension : Int
    get() = components.indexOf(maxComponent)

/** Whether this vector is orthogonal to [other]. */
fun Vector.isOrthogonal(other: Vector) = dot(other).isZero() || getAngle(other).isCloseTo(90f)
/** Whether this vector is orthogonal to ([x], [y]). */
fun Vector.isOrthogonal(x: Float, y: Float) = isOrthogonal(cVector(x, y))
/** Whether this vector is orthogonal to ([x], [y], [z]). */
fun Vector.isOrthogonal(x: Float, y: Float, z: Float) = isOrthogonal(cVector(x, y, z))
/** Whether this vector is orthogonal to ([x], [y], [z], [w]). */
fun Vector.isOrthogonal(x: Float, y: Float, z: Float, w: Float) = isOrthogonal(cVector(x, y, z, w))

/** Whether this vector is orthogonal to [other] and both vectors are unit vectors. */
fun Vector.isOrthonormal(other: Vector) = isUnit && other.isUnit && isOrthogonal(other)
/** Whether this vector is orthogonal to ([x], [y]) and both vectors are unit vectors. */
fun Vector.isOrthonormal(x: Float, y: Float) = isOrthonormal(cVector(x, y))
/** Whether this vector is orthogonal to ([x], [y], [z]) and both vectors are unit vectors. */
fun Vector.isOrthonormal(x: Float, y: Float, z: Float) = isOrthonormal(cVector(x, y, z))
/** Whether this vector is orthogonal to ([x], [y], [z], [w]) and both vectors are unit vectors. */
fun Vector.isOrthonormal(x: Float, y: Float, z: Float, w: Float) = isOrthonormal(cVector(x, y, z, w))

/** Whether this vector is parallel to [other]. */
fun Vector.isParallel(other: Vector): Boolean {
    val dimensions = max(dimensions, other.dimensions)
    if(dimensions == 0) {
        return true
    }

    val reference = this[0] / other[0]

    for(i in 1 until dimensions) {
        if(this[i] / other[i] isNotCloseTo reference) {
            return false
        }
    }

    return true
}
/** Whether this vector is parallel to ([x], [y]). */
fun Vector.isParallel(x: Float, y: Float) = isParallel(cVector(x, y))
/** Whether this vector is parallel to ([x], [y], [z]). */
fun Vector.isParallel(x: Float, y: Float, z: Float) = isParallel(cVector(x, y, z))
/** Whether this vector is parallel to ([x], [y], [z], [w]). */
fun Vector.isParallel(x: Float, y: Float, z: Float, w: Float) = isParallel(cVector(x, y, z, w))

/**
 * Returns the min angle between this vector and [other].
 *
 * @param[other] The other vector.
 * @return The angle in degrees.
 */
fun Vector.getAngle(other: Vector) : Float {
    val cos = dot(other) / (magnitude * other.magnitude)
    val acos = acos(cos)

    return acos * RADIANS_TO_DEGREES
}
/**
 * Returns the min angle between this vector and ([x], [y]).
 *
 * @return The angle in degrees.
 */
fun Vector.getAngle(x: Float, y: Float) = getAngle(cVector(x, y))
/**
 * Returns the min angle between this vector and ([x], [y], [z]).
 *
 * @return The angle in degrees.
 */
fun Vector.getAngle(x: Float, y: Float, z: Float) = getAngle(cVector(x, y, z))
/**
 * Returns the min angle between this vector and ([x], [y], [z], [w]).
 *
 * @return The angle in degrees.
 */
fun Vector.getAngle(x: Float, y: Float, z: Float, w: Float) = getAngle(cVector(x, y, z, w))

/**
 * Returns the dot product between this vector and [other].
 *
 * @param[other] The other vector.
 * @return The dot product.
 */
fun Vector.dot(other: Vector): Float {
    val dimensions = min(dimensions, other.dimensions)

    var dot = 0f
    for(i in 0 until dimensions) {
        dot += components[i] * other.components[i]
    }

    return dot
}
/**
 * Returns the dot product between this vector and ([x], [y]).
 *
 * @return The dot product.
 */
fun Vector.dot(x: Float, y: Float) = dot(cVector(x, y))
/**
 * Returns the dot product between this vector and ([x], [y], [z]).
 *
 * @return The dot product.
 */
fun Vector.dot(x: Float, y: Float, z: Float) = dot(cVector(x, y, z))
/**
 * Returns the dot product between this vector and ([x], [y], [z], [w]).
 *
 * @return The dot product.
 */
fun Vector.dot(x: Float, y: Float, z: Float, w: Float) = dot(cVector(x, y, z, w))

/**
 * Returns the absolute value of the dot product between this vector and [other].
 *
 * @param[other] The other vector.
 * @return The dot product.
 */
fun Vector.dotAbs(other: Vector) = abs(dot(other))
/**
 * Returns the absolute value of the dot product between this vector and ([x], [y]).
 *
 * @return The dot product.
 */
fun Vector.dotAbs(x: Float, y: Float) = dotAbs(cVector(x, y))
/**
 * Returns the absolute value of the dot product between this vector and ([x], [y], [z]).
 *
 * @return The dot product.
 */
fun Vector.dotAbs(x: Float, y: Float, z: Float) = dotAbs(cVector(x, y, z))
/**
 * Returns the absolute value of the dot product between this vector and ([x], [y], [z], [w]).
 *
 * @return The dot product.
 */
fun Vector.dotAbs(x: Float, y: Float, z: Float, w: Float) = dotAbs(cVector(x, y, z, w))

private val v2 by lazy { Vector2() }
private val v3 by lazy { Vector3() }
private val v4 by lazy { Vector4() }

fun cVector(x: Float, y: Float) = v2.set(x, y)
fun cVector(x: Float, y: Float, z: Float) = v3.set(x, y, z)
fun cVector(x: Float, y: Float, z: Float, w: Float) = v4.set(x, y, z, w)

// ---

operator fun Vector2.unaryPlus() = Vector2(this)
operator fun Vector2.unaryMinus() = negate(Vector2())

operator fun Vector2.plus(other: Vector2) = add(other, Vector2())
operator fun Vector2.plus(other: Vector3) = other.add(this, Vector3())
operator fun Vector2.plus(other: Vector4) = other.add(this, Vector4())
operator fun Vector2.minus(other: Vector2) = subtract(other, Vector2())
operator fun Vector2.minus(other: Vector3) = other.negate(Vector3()).add(this)
operator fun Vector2.minus(other: Vector4) = other.negate(Vector4()).add(this)
operator fun Vector2.times(scalar: Float) = scale(scalar, Vector2())
operator fun Vector2.div(scalar: Float) = scale(1 / scalar, Vector2())

operator fun Vector2.plusAssign(other: Vector2) { add(other) }
operator fun Vector2.minusAssign(other: Vector2) { subtract(other) }
operator fun Vector2.timesAssign(scalar: Float) { scale(scalar) }
operator fun Vector2.divAssign(scalar: Float) { scale(1 / scalar) }

/**
 * Negates each component.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.negate(out: Vector2 = this) = out.set(-x, -y)

/**
 * Normalizes the vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.normalize(out: Vector2 = this) = out.scale(1 / magnitude)

/**
 * Applies the absolute value to each component.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.abs(out: Vector2 = this) = out.set(abs(x), abs(y))

/**
 * Scales this vector (i.e., multiplies each component with [scalar]).
 *
 * @param[scalar] The scalar to multiply the vector with.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.scale(scalar: Float, out: Vector2 = this) = out.set(x * scalar, y * scalar)

/**
 * Adds [other] to this vector.
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.add(other: Vector2, out: Vector2 = this) = out.set(x + other.x, y + other.y)
/**
 * Adds ([x], [y]) to this vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.add(x: Float, y: Float, out: Vector2 = this) = add(cVector(x, y), out)

/**
 * Subtracts [other] from this vector.
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.subtract(other: Vector2, out: Vector2 = this) = out.set(x - other.x, y - other.y)
/**
 * Subtracts ([x], [y]) from this vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.subtract(x: Float, y: Float, out: Vector2 = this) = subtract(cVector(x, y), out)

/**
 * Returns the vector composed of the smallest components between this vector and [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.min(other: Vector2, out: Vector2 = this) = out.set(kotlin.math.min(x, other.x), kotlin.math.min(y, other.y))
/**
 * Returns the vector composed of the smallest components between this vector and ([x], [y]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.min(x: Float, y: Float, out: Vector2 = this) = min(cVector(x, y), out)

/**
 * Returns the vector composed of the largest components between this vector and [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.max(other: Vector2, out: Vector2 = this) = out.set(kotlin.math.max(x, other.x), kotlin.math.max(y, other.y))
/**
 * Returns the vector composed of the largest components between this vector and ([x], [y]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.max(x: Float, y: Float, out: Vector2 = this) = max(cVector(x, y), out)

/**
 * Projects this vector onto [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.projectOnto(other: Vector2, out: Vector2 = this) : Vector2 {
    val scalar = dot(other) / other.magnitudeSquared

    return out.set(other).scale(scalar)
}
/**
 * Projects this vector onto ([x], [y]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.projectOnto(x: Float, y: Float, out: Vector2 = this) = projectOnto(cVector(x, y), out)

/**
 * Rejects this vector from [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.rejectFrom(other: Vector2, out: Vector2 = this) : Vector2 {
    val oX = this.x
    val oY = this.y

    projectOnto(other, out)

    return out.set(oX - out.x, oY - out.y)
}
/**
 * Rejects this vector from ([x], [y]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector2.rejectFrom(x: Float, y: Float, out: Vector2 = this) = rejectFrom(cVector(x, y), out)

// ---

operator fun Vector3.unaryPlus() = Vector3(this)
operator fun Vector3.unaryMinus() = negate(Vector3())

operator fun Vector3.plus(other: Vector2) = add(other, Vector3())
operator fun Vector3.plus(other: Vector3) = add(other, Vector3())
operator fun Vector3.plus(other: Vector4) = other.add(this, Vector4())
operator fun Vector3.minus(other: Vector2) = subtract(other, Vector3())
operator fun Vector3.minus(other: Vector3) = subtract(other, Vector3())
operator fun Vector3.minus(other: Vector4) = other.negate(Vector4()).add(this)
operator fun Vector3.times(scalar: Float) = scale(scalar, Vector3())
operator fun Vector3.div(scalar: Float) = scale(1 / scalar, Vector3())

operator fun Vector3.plusAssign(other: Vector3) { add(other) }
operator fun Vector3.minusAssign(other: Vector3) { subtract(other) }
operator fun Vector3.timesAssign(scalar: Float) { scale(scalar) }
operator fun Vector3.divAssign(scalar: Float) { scale(1 / scalar) }

infix fun Vector3.x(other: Vector3) = cross(other, Vector3())

/**
 * Negates each component.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.negate(out: Vector3 = this) = out.set(-x, -y, -z)

/**
 * Normalizes the vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.normalize(out: Vector3 = this) = out.scale(1 / magnitude)

/**
 * Applies the absolute value to each component.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.abs(out: Vector3 = this) = out.set(abs(x), abs(y), abs(z))

/**
 * Scales this vector (i.e., multiplies each component with [scalar]).
 *
 * @param[scalar] The scalar to multiply the vector with.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.scale(scalar: Float, out: Vector3 = this) = out.set(x * scalar, y * scalar, z * scalar)

/**
 * Adds [other] to this vector.
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.add(other: Vector2, out: Vector3 = this) = out.set(x + other.x, y + other.y, z)
/**
 * Adds ([x], [y]) to this vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.add(x: Float, y: Float, out: Vector3 = this) = add(cVector(x, y), out)
/**
 * Adds [other] to this vector.
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.add(other: Vector3, out: Vector3 = this) = out.set(x + other.x, y + other.y, z + other.z)
/**
 * Adds ([x], [y], [z]) to this vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.add(x: Float, y: Float, z: Float, out: Vector3 = this) = add(cVector(x, y, z), out)

/**
 * Subtracts [other] from this vector.
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.subtract(other: Vector2, out: Vector3 = this) = out.set(x - other.x, y - other.y, z)
/**
 * Subtracts ([x], [y]) from this vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.subtract(x: Float, y: Float, out: Vector3 = this) = subtract(cVector(x, y), out)
/**
 * Subtracts [other] from this vector.
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.subtract(other: Vector3, out: Vector3 = this) = out.set(x - other.x, y - other.y, z - other.z)
/**
 * Subtracts ([x], [y], [z]) from this vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.subtract(x: Float, y: Float, z: Float, out: Vector3 = this) = subtract(cVector(x, y, z), out)

/**
 * Returns the vector composed of the smallest components between this vector and [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.min(other: Vector2, out: Vector3 = this) = out.set(kotlin.math.min(x, other.x), kotlin.math.min(y, other.y), kotlin.math.min(z, other.z))
/**
 * Returns the vector composed of the smallest components between this vector and ([x], [y]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.min(x: Float, y: Float, out: Vector3 = this) = min(cVector(x, y), out)
/**
 * Returns the vector composed of the smallest components between this vector and [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.min(other: Vector3, out: Vector3 = this) = out.set(kotlin.math.min(x, other.x), kotlin.math.min(y, other.y), kotlin.math.min(z, other.z))
/**
 * Returns the vector composed of the smallest components between this vector and ([x], [y], [z]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.min(x: Float, y: Float, z: Float, out: Vector3 = this) = min(cVector(x, y, z), out)

/**
 * Returns the vector composed of the largest components between this vector and [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.max(other: Vector2, out: Vector3 = this) = out.set(kotlin.math.max(x, other.x), kotlin.math.max(y, other.y), kotlin.math.max(z, other.z))
/**
 * Returns the vector composed of the largest components between this vector and ([x], [y]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.max(x: Float, y: Float, out: Vector3 = this) = max(cVector(x, y), out)
/**
 * Returns the vector composed of the largest components between this vector and [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.max(other: Vector3, out: Vector3 = this) = out.set(kotlin.math.max(x, other.x), kotlin.math.max(y, other.y), kotlin.math.max(z, other.z))
/**
 * Returns the vector composed of the largest components between this vector and ([x], [y], [z]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.max(x: Float, y: Float, z: Float, out: Vector3 = this) = max(cVector(x, y, z), out)

/**
 * Projects this vector onto [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.projectOnto(other: Vector2, out: Vector2): Vector2 {
    val scalar = dot(other) / other.magnitudeSquared

    return out.set(other).scale(scalar)
}
/**
 * Projects this vector onto ([x], [y]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.projectOnto(x: Float, y: Float, out: Vector2) = projectOnto(cVector(x, y), out)
/**
 * Projects this vector onto [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.projectOnto(other: Vector2, out: Vector3 = this): Vector3 {
    val scalar = dot(other) / other.magnitudeSquared

    return out.set(other).scale(scalar)
}
/**
 * Projects this vector onto ([x], [y]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.projectOnto(x: Float, y: Float, out: Vector3) = projectOnto(cVector(x, y), out)

/**
 * Projects this vector onto [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.projectOnto(other: Vector3, out: Vector3 = this) : Vector3 {
    val scalar = dot(other) / other.magnitudeSquared

    return out.set(other).scale(scalar)
}
/**
 * Projects this vector onto ([x], [y], [z]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.projectOnto(x: Float, y: Float, z: Float, out: Vector3 = this) = projectOnto(cVector(x, y, z), out)

/**
 * Rejects this vector from [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.rejectFrom(other: Vector2, out: Vector3 = this) : Vector3 {
    val oX = this.x
    val oY = this.y
    val oZ = this.z

    projectOnto(other, out)

    return out.set(oX - out.x, oY - out.y, oZ)
}
/**
 * Rejects this vector from ([x], [y]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.rejectFrom(x: Float, y: Float, out: Vector3 = this) = rejectFrom(cVector(x, y), out)

/**
 * Rejects this vector from [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.rejectFrom(other: Vector3, out: Vector3 = this) : Vector3 {
    val oX = this.x
    val oY = this.y
    val oZ = this.z

    projectOnto(other, out)

    return out.set(oX - out.x, oY - out.y, oZ - out.z)
}
/**
 * Rejects this vector from ([x], [y], [z]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.rejectFrom(x: Float, y: Float, z: Float, out: Vector3 = this) = rejectFrom(cVector(x, y, z), out)

/**
 * Cross product between this vector and [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.cross(other: Vector3, out: Vector3 = this) = out.set(
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        x * other.y - y * other.x
)
/**
 * Cross product between this vector and ([x], [y], [z]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector3.cross(x: Float, y: Float, z: Float, out: Vector3 = this) = cross(cVector(x, y, z), out)

// ---

operator fun Vector4.unaryPlus() = Vector4(this)
operator fun Vector4.unaryMinus() = negate(Vector4())

operator fun Vector4.plus(other: Vector2) = add(other, Vector4())
operator fun Vector4.plus(other: Vector3) = add(other, Vector4())
operator fun Vector4.plus(other: Vector4) = add(other, Vector4())
operator fun Vector4.minus(other: Vector2) = subtract(other, Vector4())
operator fun Vector4.minus(other: Vector3) = subtract(other, Vector4())
operator fun Vector4.minus(other: Vector4) = subtract(other, Vector4())
operator fun Vector4.times(scalar: Float) = scale(scalar, Vector4())
operator fun Vector4.div(scalar: Float) = scale(1 / scalar, Vector4())

operator fun Vector4.plusAssign(other: Vector4) { add(other) }
operator fun Vector4.minusAssign(other: Vector4) { subtract(other) }
operator fun Vector4.timesAssign(scalar: Float) { scale(scalar) }
operator fun Vector4.divAssign(scalar: Float) { scale(1 / scalar) }

/**
 * Negates each component.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.negate(out: Vector4 = this) = out.set(-x, -y, -z, -w)

/**
 * Normalizes the vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.normalize(out: Vector4 = this) = out.scale(1 / magnitude)

/**
 * Applies the absolute value to each component.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.abs(out: Vector4 = this) = out.set(abs(x), abs(y), abs(z), abs(w))

/**
 * Scales this vector (i.e., multiplies each component with [scalar]).
 *
 * @param[scalar] The scalar to multiply the vector with.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.scale(scalar: Float, out: Vector4 = this) = out.set(x * scalar, y * scalar, z * scalar, w * scalar)

/**
 * Adds [other] to this vector.
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.add(other: Vector2, out: Vector4 = this) = out.set(x + other.x, y + other.y, z, w)
/**
 * Adds ([x], [y]) to this vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.add(x: Float, y: Float, out: Vector4 = this) = add(cVector(x, y), out)

/**
 * Adds [other] to this vector.
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.add(other: Vector3, out: Vector4 = this) = out.set(x + other.x, y + other.y, z + other.z, w)
/**
 * Adds ([x], [y], [z]) to this vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.add(x: Float, y: Float, z: Float, out: Vector4 = this) = add(cVector(x, y, z), out)

/**
 * Adds [other] to this vector.
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.add(other: Vector4, out: Vector4 = this) = out.set(x + other.x, y + other.y, z + other.z, w + other.w)
/**
 * Adds ([x], [y], [z], [w]) to this vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.add(x: Float, y: Float, z: Float, w: Float, out: Vector4 = this) = add(cVector(x, y, z, w), out)

/**
 * Subtracts [other] from this vector.
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.subtract(other: Vector2, out: Vector4 = this) = out.set(x - other.x, y - other.y, z, w)
/**
 * Subtracts ([x], [y]) from this vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.subtract(x: Float, y: Float, out: Vector4 = this) = subtract(cVector(x, y), out)

/**
 * Subtracts [other] from this vector.
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.subtract(other: Vector3, out: Vector4 = this) = out.set(x - other.x, y - other.y, z - other.z, w)
/**
 * Subtracts ([x], [y], [z]) from this vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.subtract(x: Float, y: Float, z: Float, out: Vector4 = this) = subtract(cVector(x, y, z), out)

/**
 * Subtracts [other] from this vector.
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.subtract(other: Vector4, out: Vector4 = this) = out.set(x - other.x, y - other.y, z - other.z, w - other.w)
/**
 * Subtracts ([x], [y], [z], [w]) from this vector.
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.subtract(x: Float, y: Float, z: Float, w: Float, out: Vector4 = this) = subtract(cVector(x, y, z, w), out)

/**
 * Returns the vector composed of the smallest components between this vector and [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.min(other: Vector2, out: Vector4 = this) = out.set(kotlin.math.min(x, other.x), kotlin.math.min(y, other.y), kotlin.math.min(z, other.z), kotlin.math.min(w, other.w))
/**
 * Returns the vector composed of the smallest components between this vector and ([x], [y]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.min(x: Float, y: Float, out: Vector4 = this) = min(cVector(x, y), out)

/**
 * Returns the vector composed of the smallest components between this vector and [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.min(other: Vector3, out: Vector4 = this) = out.set(kotlin.math.min(x, other.x), kotlin.math.min(y, other.y), kotlin.math.min(z, other.z), kotlin.math.min(w, other.w))
/**
 * Returns the vector composed of the smallest components between this vector and ([x], [y], [z]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.min(x: Float, y: Float, z: Float, out: Vector4 = this) = min(cVector(x, y, z), out)

/**
 * Returns the vector composed of the smallest components between this vector and [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.min(other: Vector4, out: Vector4 = this) = out.set(kotlin.math.min(x, other.x), kotlin.math.min(y, other.y), kotlin.math.min(z, other.z), kotlin.math.min(w, other.w))
/**
 * Returns the vector composed of the smallest components between this vector and ([x], [y], [z], [w]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.min(x: Float, y: Float, z: Float, w: Float, out: Vector4 = this) = min(cVector(x, y, z, w), out)

/**
 * Returns the vector composed of the largest components between this vector and [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.max(other: Vector2, out: Vector4 = this) = out.set(kotlin.math.max(x, other.x), kotlin.math.max(y, other.y), kotlin.math.max(z, other.z), kotlin.math.max(w, other.w))
/**
 * Returns the vector composed of the largest components between this vector and ([x], [y]).
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.max(x: Float, y: Float, out: Vector4 = this) = max(cVector(x, y), out)

/**
 * Returns the vector composed of the largest components between this vector and [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.max(other: Vector3, out: Vector4 = this) = out.set(kotlin.math.max(x, other.x), kotlin.math.max(y, other.y), kotlin.math.max(z, other.z), kotlin.math.max(w, other.w))
/**
 * Returns the vector composed of the largest components between this vector and ([x], [y], [z]).
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.max(x: Float, y: Float, z: Float, out: Vector4 = this) = max(cVector(x, y, z), out)

/**
 * Returns the vector composed of the largest components between this vector and [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.max(other: Vector4, out: Vector4 = this) = out.set(kotlin.math.max(x, other.x), kotlin.math.max(y, other.y), kotlin.math.max(z, other.z), kotlin.math.max(w, other.w))
/**
 * Returns the vector composed of the largest components between this vector and ([x], [y], [z], [w]).
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.max(x: Float, y: Float, z: Float, w: Float, out: Vector4 = this) = max(cVector(x, y, z, w), out)

/**
 * Projects this vector onto [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.projectOnto(other: Vector2, out: Vector2) : Vector2 {
    val scalar = dot(other) / other.magnitudeSquared

    return out.set(other).scale(scalar)
}
/**
 * Projects this vector onto ([x], [y]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.projectOnto(x: Float, y: Float, out: Vector2) = projectOnto(cVector(x, y), out)
/**
 * Projects this vector onto [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.projectOnto(other: Vector2, out: Vector4 = this) : Vector4 {
    val scalar = dot(other) / other.magnitudeSquared

    return out.set(other).scale(scalar)
}
/**
 * Projects this vector onto ([x], [y]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.projectOnto(x: Float, y: Float, out: Vector4 = this) = projectOnto(cVector(x, y), out)

/**
 * Projects this vector onto [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.projectOnto(other: Vector3, out: Vector3) : Vector3 {
    val scalar = dot(other) / other.magnitudeSquared

    return out.set(other).scale(scalar)
}
/**
 * Projects this vector onto ([x], [y], [z]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.projectOnto(x: Float, y: Float, z: Float, out: Vector3) = projectOnto(cVector(x, y, z), out)
/**
 * Projects this vector onto [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.projectOnto(other: Vector3, out: Vector4 = this) : Vector4 {
    val scalar = dot(other) / other.magnitudeSquared

    return out.set(other).scale(scalar)
}
/**
 * Projects this vector onto ([x], [y], [z]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.projectOnto(x: Float, y: Float, z: Float, out: Vector4 = this) = projectOnto(cVector(x, y, z), out)

/**
 * Projects this vector onto [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.projectOnto(other: Vector4, out: Vector4 = this) : Vector4 {
    val scalar = dot(other) / other.magnitudeSquared

    return out.set(other).scale(scalar)
}
/**
 * Projects this vector onto ([x], [y], [z], [w]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.projectOnto(x: Float, y: Float, z: Float, w: Float, out: Vector4 = this) = projectOnto(cVector(x, y, z, w), out)

/**
 * Rejects this vector from [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.rejectFrom(other: Vector2, out: Vector4 = this) : Vector4 {
    val oX = this.x
    val oY = this.y
    val oZ = this.z
    val oW = this.w

    projectOnto(other, out)

    return out.set(oX - x, oY - y, oZ, oW)
}
/**
 * Rejects this vector from ([x], [y]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.rejectFrom(x: Float, y: Float, out: Vector4 = this) = rejectFrom(cVector(x, y), out)

/**
 * Rejects this vector from [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.rejectFrom(other: Vector3, out: Vector4 = this) : Vector4 {
    val oX = this.x
    val oY = this.y
    val oZ = this.z
    val oW = this.w

    projectOnto(other, out)

    return out.set(oX - x, oY - y, oZ - z, oW)
}
/**
 * Rejects this vector from ([x], [y], [z]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.rejectFrom(x: Float, y: Float, z: Float, out: Vector4 = this) = rejectFrom(cVector(x, y, z), out)

/**
 * Rejects this vector from [other].
 *
 * @param[other] The other vector.
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.rejectFrom(other: Vector4, out: Vector4 = this) : Vector4 {
    val oX = this.x
    val oY = this.y
    val oZ = this.z
    val oW = this.w

    projectOnto(other, out)

    return out.set(oX - x, oY - y, oZ - z, oW - w)
}
/**
 * Rejects this vector from ([x], [y], [z], [w]).
 *
 * @param[out] The vector that stores the result.
 * @return The output vector for chaining.
 */
fun Vector4.rejectFrom(x: Float, y: Float, z: Float, w: Float, out: Vector4 = this) = rejectFrom(cVector(x, y, z, w), out)