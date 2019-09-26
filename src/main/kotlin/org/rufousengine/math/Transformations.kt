@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.math

import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.sign

/**
 * Sets [matrix] as a rotation matrix through [angle] about the x axis.
 *
 * @return The [matrix] for chaining.
 */
fun rotationX(degrees: Float, matrix: Matrix4) : Matrix4 {
    if(degrees.isZero()) {
        return identity(matrix)
    }

    val c = cos(degrees)
    val s = sin(degrees)

    return matrix.set(
            1f, 0f, 0f, 0f,
            0f, c, -s, 0f,
            0f, s, c, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Sets [matrix] as a rotation matrix through [angle] about the y axis.
 *
 * @return The [matrix] for chaining.
 */
fun rotationY(degrees: Float, matrix: Matrix4) : Matrix4 {
    if(degrees.isZero()) {
        return identity(matrix)
    }

    val c = cos(degrees)
    val s = sin(degrees)

    return matrix.set(
            c, 0f, s, 0f,
            0f, 1f, 0f, 0f,
            -s, 0f, c, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Sets [matrix] as a rotation matrix through [angle] about the z axis.
 *
 * @return The [matrix] for chaining.
 */
fun rotationZ(degrees: Float, matrix: Matrix4) : Matrix4 {
    if(degrees.isZero()) {
        return identity(matrix)
    }

    val c = cos(degrees)
    val s = sin(degrees)

    return matrix.set(
            c, -s, 0f, 0f,
            s, c, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Sets [matrix] as a rotation matrix through [degrees] about [axis].
 *
 * If [axis] is known to be a unit vector, [rotationUnsafe] is a cheaper alternative.
 *
 * @return The [matrix] for chaining.
 */
fun rotation(degrees: Float, axis: Vector2, matrix: Matrix4): Matrix4 {
    if(degrees.isZero()) {
        return identity(matrix)
    }

    val axis = cVector(axis).normalize()

    return rotationUnsafe(degrees, axis, matrix)
}
/**
 * Sets [matrix] as a rotation matrix through [degrees] about [axis].
 *
 * If [axis] is known to be a unit vector, [rotationUnsafe] is a cheaper alternative.
 *
 * @return The [matrix] for chaining.
 */
fun rotation(degrees: Float, axis: Vector3, matrix: Matrix4): Matrix4 {
    if(degrees.isZero()) {
        return identity(matrix)
    }

    val axis = cVector(axis).normalize()

    return rotationUnsafe(degrees, axis, matrix)
}
/**
 * Sets [matrix] as a rotation matrix that corresponds to [quaternion].
 *
 * If [quaternion] is known to be a unit quaternion, [rotationUnsafe] is a cheaper alternative.
 *
 * @return The [matrix] for chaining.
 */
fun rotation(quaternion: Quaternion, matrix: Matrix4): Matrix4 {
    if(quaternion.isIdentity) {
        return identity(matrix)
    }

    val quaternion = cQuaternion(quaternion).normalize()

    return rotationUnsafe(quaternion, matrix)
}

/**
 * Sets [matrix] as a rotation matrix through [degrees] about [axis].
 *
 * [axis] must be a unit vector.
 *
 * @return The [matrix] for chaining.
 */
fun rotationUnsafe(degrees: Float, axis: Vector2, matrix: Matrix4): Matrix4 {
    if(degrees.isZero()) {
        return identity(matrix)
    }

    val c = cos(degrees)
    val s = sin(degrees)
    val d = 1f - c

    val x = axis.x * d
    val y = axis.y * d
    val axay = x * axis.y
    val say = s * axis.y
    val sax = s * axis.x

    return matrix.set(
            c + x * axis.x, axay, say, 0f,
            axay, c + y * axis.y, -sax, 0f,
            -say, sax, c, 0f,
            0f, 0f, 0f, 1f
    )
}
/**
 * Sets [matrix] as a rotation matrix through [degrees] about [axis].
 *
 * [axis] must be a unit vector.
 *
 * @return The [matrix] for chaining.
 */
fun rotationUnsafe(degrees: Float, axis: Vector3, matrix: Matrix4): Matrix4 {
    if(degrees.isZero()) {
        return identity(matrix)
    }

    val c = cos(degrees)
    val s = sin(degrees)
    val d = 1f - c

    val x = axis.x * d
    val y = axis.y * d
    val z = axis.z * d
    val axay = x * axis.y
    val axaz = x * axis.z
    val ayaz = y * axis.z
    val saz = s * axis.z
    val say = s * axis.y
    val sax = s * axis.x

    return matrix.set(
            c + x * axis.x, axay - saz, axaz + say, 0f,
            axay + saz, c + y * axis.y, ayaz - sax, 0f,
            axaz - say, ayaz + sax, c + z * axis.z, 0f,
            0f, 0f, 0f, 1f
    )
}
/**
 * Sets [matrix] as a rotation matrix that corresponds to [quaternion].
 *
 * @return The [matrix] for chaining.
 */
fun rotationUnsafe(quaternion: Quaternion, matrix: Matrix4): Matrix4 {
    if(quaternion.isIdentity) {
        return identity(matrix)
    }

    val x2 = quaternion.x * quaternion.x
    val y2 = quaternion.y * quaternion.y
    val z2 = quaternion.z * quaternion.z
    val xy = quaternion.x * quaternion.y
    val xz = quaternion.x * quaternion.z
    val yz = quaternion.y * quaternion.z
    val wx = quaternion.w * quaternion.x
    val wy = quaternion.w * quaternion.y
    val wz = quaternion.w * quaternion.z

    return matrix.set(
            1f - 2f * (y2 + z2), 2f * (xy - wz), 2f * (xz + wy), 0f,
            2f * (xy + wz), 1f - 2f * (x2 + z2), 2f * (yz - wx), 0f,
            2f * (xz - wy), 2f * (yz + wx), 1f - 2f * (x2 + y2), 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Sets [matrix] as a reflection matrix through the plane perpendicular to [axis].
 *
 * If [axis] is known to be a unit vector, [reflectionUnsafe] is a cheaper alternative.
 *
 * @return The [matrix] for chaining.
 */
fun reflection(axis: Vector2, matrix: Matrix4): Matrix4 {
    val axis = cVector(axis).normalize()

    return reflectionUnsafe(axis, matrix)
}
/**
 * Sets [matrix] as a reflection matrix through the plane perpendicular to [axis].
 *
 * If [axis] is known to be a unit vector, [reflectionUnsafe] is a cheaper alternative.
 *
 * @return The [matrix] for chaining.
 */
fun reflection(axis: Vector3, matrix: Matrix4): Matrix4 {
    val axis = cVector(axis).normalize()

    return reflectionUnsafe(axis, matrix)
}

/**
 * Sets [matrix] as a reflection matrix through the plane perpendicular to [axis].
 *
 * [axis] must be a unit vector.
 *
 * @return The [matrix] for chaining.
 */
fun reflectionUnsafe(axis: Vector2, matrix: Matrix4): Matrix4 {
    val x = axis.x * -2f
    val y = axis.y * -2f
    val axay = x * axis.y

    return matrix.set(
            x * axis.x + 1f, axay, 0f, 0f,
            axay, y * axis.y + 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
    )
}
/**
 * Sets [matrix] as a reflection matrix through the plane perpendicular to [axis].
 *
 * [axis] must be a unit vector.
 *
 * @return The [matrix] for chaining.
 */
fun reflectionUnsafe(axis: Vector3, matrix: Matrix4): Matrix4 {
    val x = axis.x * -2f
    val y = axis.y * -2f
    val z = axis.z * -2f
    val axay = x * axis.y
    val axaz = x * axis.z
    val ayaz = y * axis.z

    return matrix.set(
            x * axis.x + 1f, axay, axaz, 0f,
            axay, y * axis.y + 1f, ayaz, 0f,
            axaz, ayaz, z * axis.z + 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Sets [matrix] as an involution matrix through the plane perpendicular to [axis].
 *
 * If [axis] is known to be a unit vector, [involutionUnsafe] is a cheaper alternative.
 *
 * @return The [matrix] for chaining.
 */
fun involution(axis: Vector2, matrix: Matrix4): Matrix4 {
    val axis = cVector(axis).normalize()

    return involutionUnsafe(axis, matrix)
}
/**
 * Sets [matrix] as an involution matrix through the plane perpendicular to [axis].
 *
 * If [axis] is known to be a unit vector, [involutionUnsafe] is a cheaper alternative.
 *
 * @return The [matrix] for chaining.
 */
fun involution(axis: Vector3, matrix: Matrix4): Matrix4 {
    val axis = cVector(axis).normalize()

    return involutionUnsafe(axis, matrix)
}

/**
 * Sets [matrix] as an involution matrix through the plane perpendicular to [axis].
 *
 * [axis] must be a unit vector.
 *
 * @return The [matrix] for chaining.
 */
fun involutionUnsafe(axis: Vector2, matrix: Matrix4): Matrix4 {
    val x = axis.x * 2f
    val y = axis.y * 2f
    val axay = x * axis.y

    return matrix.set(
            x * axis.x - 1f, axay, 0f, 0f,
            axay, y * axis.y - 1f, 0f, 0f,
            0f, 0f, -1f, 0f,
            0f, 0f, 0f, 1f
    )
}
/**
 * Sets [matrix] as an involution matrix through the plane perpendicular to [axis].
 *
 * [axis] must be a unit vector.
 *
 * @return The [matrix] for chaining.
 */
fun involutionUnsafe(axis: Vector3, matrix: Matrix4): Matrix4 {
    val x = axis.x * 2f
    val y = axis.y * 2f
    val z = axis.z * 2f
    val axay = x * axis.y
    val axaz = x * axis.z
    val ayaz = y * axis.z

    return matrix.set(
            x * axis.x - 1f, axay, axaz, 0f,
            axay, y * axis.y - 1f, ayaz, 0f,
            axaz, ayaz, z * axis.z - 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Sets [matrix] as a uniform scale matrix by [factor].
 *
 * @return The [matrix] for chaining
 */
inline fun scale(factor: Float, matrix: Matrix4) = matrix.set(
        factor, 0f, 0f, 0f,
        0f, factor, 0f, 0f,
        0f, 0f, factor, 0f,
        0f, 0f, 0f, 1f
)

/**
 * Sets [matrix] as a nonuniform scale matrix by [xFactor] along the x axis, [yFactor] along the y axis and [zFactor] along the z axis.
 *
 * @return The [matrix] for chaining
 */
inline fun scale(xFactor: Float, yFactor: Float, zFactor: Float, matrix: Matrix4) = matrix.set(
        xFactor, 0f, 0f, 0f,
        0f, yFactor, 0f, 0f,
        0f, 0f, zFactor, 0f,
        0f, 0f, 0f, 1f
)

/**
 * Sets [matrix] as a scale matrix by [factor] along [axis].
 *
 * If [axis] is known to be a unit vector, [scaleUnsafe] is a cheaper alternative.
 *
 * @return The [matrix] for chaining
 */
fun scale(factor: Float, axis: Vector2, matrix: Matrix4): Matrix4 {
    val axis = cVector(axis).normalize()

    return scaleUnsafe(factor, axis, matrix)
}
/**
 * Sets [matrix] as a scale matrix by [factor] along [axis].
 *
 * If [axis] is known to be a unit vector, [scaleUnsafe] is a cheaper alternative.
 *
 * @return The [matrix] for chaining
 */
fun scale(factor: Float, axis: Vector3, matrix: Matrix4): Matrix4 {
    val axis = cVector(axis).normalize()

    return scaleUnsafe(factor, axis, matrix)
}

/**
 * Sets this matrix as a scale matrix by [factor] along [axis].
 *
 * [axis] must be a unit vector.
 *
 * @return The [matrix] for chaining
 */
fun scaleUnsafe(factor: Float, axis: Vector2, matrix: Matrix4): Matrix4 {
    val factor = factor - 1f

    val x = axis.x * factor
    val y = axis.y * factor
    val axay = x * axis.y

    return matrix.set(
            x * axis.x + 1f, axay, 0f, 0f,
            axay, y * axis.y + 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
    )
}
/**
 * Sets this matrix as a scale matrix by [factor] along [axis].
 *
 * [axis] must be a unit vector.
 *
 * @return The [matrix] for chaining
 */
fun scaleUnsafe(factor: Float, axis: Vector3, matrix: Matrix4): Matrix4 {
    val factor = factor - 1f

    val x = axis.x * factor
    val y = axis.y * factor
    val z = axis.z * factor
    val axay = x * axis.y
    val axaz = x * axis.z
    val ayaz = y * axis.z

    return matrix.set(
            x * axis.x + 1f, axay, axaz, 0f,
            axay, y * axis.y + 1f, ayaz, 0f,
            axaz, ayaz, z * axis.z + 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Sets [matrix] as a skew matrix by [degrees] along the direction [a] based on the projected length along the direction [b].
 *
 * [a] and [b] must be orthonormal.
 *
 * @return The [matrix] for chaining.
 */
fun skewUnsafe(degrees: Float, a: Vector2, b: Vector2, matrix: Matrix4): Matrix4 {
    val t = tan(degrees)
    val x = a.x * t
    val y = a.y * t

    return matrix.set(
            x * b.x + 1f, x * b.y, 0f, 0f,
            y * b.x, y * b.y + 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
    )
}
/**
 * Sets [matrix] as a skew matrix by [degrees] along the direction [a] based on the projected length along the direction [b].
 *
 * [a] and [b] must be orthonormal.
 *
 * @return The [matrix] for chaining.
 */
fun skewUnsafe(degrees: Float, a: Vector3, b: Vector3, matrix: Matrix4): Matrix4 {
    val t = tan(degrees)
    val x = a.x * t
    val y = a.y * t
    val z = a.z * t

    return matrix.set(
            x * b.x + 1f, x * b.y, x * b.z, 0f,
            y * b.x, y * b.y + 1f, y * b.z, 0f,
            z * b.x, z * b.y, z * b.z + 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Sets [matrix] as a translation matrix to [point].
 *
 * @return The [matrix] for chaining.
 */
inline fun translation(point: Point2D, matrix: Matrix4) = matrix.set(
        1f, 0f, 0f, point.x,
        0f, 1f, 0f, point.y,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f
)
/**
 * Sets [matrix] as a translation matrix to [point].
 *
 * @return The [matrix] for chaining.
 */
inline fun translation(point: Point3D, matrix: Matrix4) = matrix.set(
        1f, 0f, 0f, point.x,
        0f, 1f, 0f, point.y,
        0f, 0f, 1f, point.z,
        0f, 0f, 0f, 1f
)

fun lookAt(eye: Point3D, target: Point3D, quaternion: Quaternion): Quaternion {
    val forwardVector = normalize(target - eye)

    val rotAxis = cross(Vector3(0f, 0f, -1f), forwardVector)
    val dot = dot(Vector3(0f, 0f, -1f), forwardVector)

    return quaternion.set(rotAxis.x, rotAxis.y, rotAxis.z, dot + 1).normalize()
}
fun rotation(degrees: Float, axis: Vector3, quaternion: Quaternion): Quaternion {
    if(degrees.isZero()) {
        return identity(quaternion)
    }

    val hdegrees = degrees * 0.5f
    val s = sin(hdegrees)
    val c = cos(hdegrees)

    return quaternion.set(axis.x * s, axis.y * s, axis.z * s, c)
}
fun euler(quaternion: Quaternion, vector: Vector3) : Vector3 {
    // roll (x-axis rotation)
    val sinrCosp = 2 * (quaternion.w * quaternion.x + quaternion.y * quaternion.z)
    val cosrCosp = 1 - 2 * (quaternion.x * quaternion.x + quaternion.y * quaternion.y)
    vector.x = atan2(sinrCosp, cosrCosp)

    // pitch (y-axis rotation)
    val sinp = 2 * (quaternion.w * quaternion.y - quaternion.z * quaternion.x)
    if (abs(sinp) >= 1)
        vector.y = (PI / 2) * sinp.sign // use 90 degrees if out of range
    else
        vector.y = asin(sinp)

    // yaw (z-axis rotation)
    val sinyCosp = 2 * (quaternion.w * quaternion.z + quaternion.x * quaternion.y)
    val cosyCosp = 1 - 2 * (quaternion.y * quaternion.y + quaternion.z * quaternion.z)
    vector.z = atan2(sinyCosp, cosyCosp)

    return vector.scale(RADIANS_TO_DEGREES)
}
fun quaternion(euler: Vector3, quaternion: Quaternion) : Quaternion {
    val cy = cos(euler.z * 0.5f)
    val sy = sin(euler.z * 0.5f)
    val cp = cos(euler.y * 0.5f)
    val sp = sin(euler.y * 0.5f)
    val cr = cos(euler.x * 0.5f)
    val sr = sin(euler.x * 0.5f)

    quaternion.w = cy * cp * cr + sy * sp * sr
    quaternion.x = cy * cp * sr - sy * sp * cr
    quaternion.y = sy * cp * sr + cy * sp * cr
    quaternion.z = sy * cp * cr - cy * sp * sr

    return quaternion
}

// ---------------------------------------------------------------------------------------------------------------------

/** Creates a rotation matrix through [angle] about the x axis. */
inline fun RotationX(degrees: Float) = rotationX(degrees, Matrix4())
/** Creates a rotation matrix through [angle] about the y axis. */
inline fun RotationY(degrees: Float) = rotationY(degrees, Matrix4())
/** Creates a rotation matrix through [angle] about the z axis. */
inline fun RotationZ(degrees: Float) = rotationZ(degrees, Matrix4())
/** Creates a rotation matrix through [degrees] about [axis]. */
inline fun Rotation(degrees: Float, axis: Vector2) = rotation(degrees, axis, Matrix4())
/** Creates a rotation matrix through [degrees] about [axis]. */
inline fun Rotation(degrees: Float, axis: Vector3) = rotation(degrees, axis, Matrix4())
/** Creates a rotation matrix that corresponds to [quaternion]. */
inline fun Rotation(quaternion: Quaternion) = rotation(quaternion, Matrix4())

/** Creates a reflection matrix through the plane perpendicular to [axis]. */
inline fun Reflection(axis: Vector2) = reflection(axis, Matrix4())
/** Creates a reflection matrix through the plane perpendicular to [axis]. */
inline fun Reflection(axis: Vector3) = reflection(axis, Matrix4())

/** Creates an involution matrix through the plane perpendicular to [axis]. */
inline fun Involution(axis: Vector2) = involution(axis, Matrix4())
/** Creates an involution matrix through the plane perpendicular to [axis]. */
inline fun Involution(axis: Vector3) = involution(axis, Matrix4())

/** Creates a uniform scale matrix by [factor]. */
inline fun Scale(factor: Float) = scale(factor, Matrix4())
/** Creates a nonuniform scale matrix by [xFactor] along the x axis, [yFactor] along the y axis and [zFactor] along the z axis. */
inline fun Scale(xFactor: Float, yFactor: Float, zFactor: Float) = scale(xFactor, yFactor, zFactor, Matrix4())
/** Creates a scale matrix by [factor] along [axis]. */
inline fun Scale(factor: Float, axis: Vector2) = scale(factor, axis, Matrix4())
/** Creates a scale matrix by [factor] along [axis]. */
inline fun Scale(factor: Float, axis: Vector3) = scale(factor, axis, Matrix4())

/**
 * Creates a skew matrix by [degrees] along the direction [a] based on the projected length along the direction [b].
 *
 * [a] and [b] must be orthonormal.
 */
fun Skew(degrees: Float, a: Vector2, b: Vector2) = skewUnsafe(degrees, a, b, Matrix4())
/**
 * Creates a skew matrix by [degrees] along the direction [a] based on the projected length along the direction [b].
 *
 * [a] and [b] must be orthonormal.
 */
fun Skew(degrees: Float, a: Vector3, b: Vector3) = skewUnsafe(degrees, a, b, Matrix4())

/** Creates a translation matrix to [point]. */
inline fun Translation(point: Point2D) = translation(point, Matrix4())
/** Creates a translation matrix to [point]. */
inline fun Translation(point: Point3D) = translation(point, Matrix4())

