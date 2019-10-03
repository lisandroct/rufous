@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.math

import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.sign

/** Creates a rotation matrix through [angle] about the x axis.  */
fun RotationX(degrees: Float) : Matrix4 {
    if(degrees.isZero()) {
        return Matrix4.identity
    }

    val c = cos(degrees)
    val s = sin(degrees)

    return Matrix4(
            1f, 0f, 0f, 0f,
            0f, c, -s, 0f,
            0f, s, c, 0f,
            0f, 0f, 0f, 1f
    )
}

/** Creates a rotation matrix through [angle] about the y axis.  */
fun RotationY(degrees: Float) : Matrix4 {
    if(degrees.isZero()) {
        return Matrix4.identity
    }

    val c = cos(degrees)
    val s = sin(degrees)

    return Matrix4(
            c, 0f, s, 0f,
            0f, 1f, 0f, 0f,
            -s, 0f, c, 0f,
            0f, 0f, 0f, 1f
    )
}

/** Creates a rotation matrix through [angle] about the z axis.  */
fun RotationZ(degrees: Float) : Matrix4 {
    if(degrees.isZero()) {
        return Matrix4.identity
    }

    val c = cos(degrees)
    val s = sin(degrees)

    return Matrix4(
            c, -s, 0f, 0f,
            s, c, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Creates a rotation matrix through [degrees] about [axis].
 *
 * If [axis] is known to be a unit vector, [Rotation] is a cheaper alternative.
 */
fun RotationSafe(degrees: Float, axis: Vector2) =  Rotation(degrees, axis.normalized)

/**
 * Creates a rotation matrix through [degrees] about [axis].
 *
 * If [axis] is known to be a unit vector, [Rotation] is a cheaper alternative.
 */
fun RotationSafe(degrees: Float, axis: Vector3) =  Rotation(degrees, axis.normalized)

/**
 * Creates a rotation matrix through [degrees] about [axis].
 *
 * [axis] must be a unit vector.
 */
fun Rotation(degrees: Float, axis: Vector2): Matrix4 {
    if(degrees.isZero()) {
        return Matrix4.identity
    }

    val c = cos(degrees)
    val s = sin(degrees)
    val d = 1f - c

    val x = axis.x * d
    val y = axis.y * d
    val axay = x * axis.y
    val say = s * axis.y
    val sax = s * axis.x

    return Matrix4(
            c + x * axis.x, axay, say, 0f,
            axay, c + y * axis.y, -sax, 0f,
            -say, sax, c, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Creates a rotation matrix through [degrees] about [axis].
 *
 * [axis] must be a unit vector.
 */
fun Rotation(degrees: Float, axis: Vector3): Matrix4 {
    if(degrees.isZero()) {
        return Matrix4.identity
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

    return Matrix4(
            c + x * axis.x, axay - saz, axaz + say, 0f,
            axay + saz, c + y * axis.y, ayaz - sax, 0f,
            axaz - say, ayaz + sax, c + z * axis.z, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Creates a rotation matrix that corresponds to [quaternion].
 *
 * If [quaternion] is known to be a unit quaternion, [Rotation] is a cheaper alternative.
 */
fun RotationSafe(quaternion: Quaternion) = Rotation(quaternion.normalized)

/**
 * Creates a rotation matrix that corresponds to [quaternion].
 *
 * [quaternion] must be a unit quaternion.
 */
fun Rotation(quaternion: Quaternion): Matrix4 {
    if(quaternion.isIdentity) {
        return Matrix4.identity
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

    return Matrix4(
            1f - 2f * (y2 + z2), 2f * (xy - wz), 2f * (xz + wy), 0f,
            2f * (xy + wz), 1f - 2f * (x2 + z2), 2f * (yz - wx), 0f,
            2f * (xz - wy), 2f * (yz + wx), 1f - 2f * (x2 + y2), 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Creates a reflection matrix through the plane perpendicular to [axis].
 *
 * If [axis] is known to be a unit vector, [Reflection] is a cheaper alternative.
 */
fun ReflectionSafe(axis: Vector2) = Reflection(axis.normalized)

/**
 * Sets [matrix] as a reflection matrix through the plane perpendicular to [axis].
 *
 * If [axis] is known to be a unit vector, [Reflection] is a cheaper alternative.
 *
 * @return The [matrix] for chaining.
 */
fun ReflectionSafe(axis: Vector3) = Reflection(axis.normalized)

/**
 * Creates a reflection matrix through the plane perpendicular to [axis].
 *
 * [axis] must be a unit vector.
 */
fun Reflection(axis: Vector2): Matrix4 {
    val x = axis.x * -2f
    val y = axis.y * -2f
    val axay = x * axis.y

    return Matrix4(
            x * axis.x + 1f, axay, 0f, 0f,
            axay, y * axis.y + 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
    )
}
/**
 * Creates a reflection matrix through the plane perpendicular to [axis].
 *
 * [axis] must be a unit vector.
 */
fun Reflection(axis: Vector3): Matrix4 {
    val x = axis.x * -2f
    val y = axis.y * -2f
    val z = axis.z * -2f
    val axay = x * axis.y
    val axaz = x * axis.z
    val ayaz = y * axis.z

    return Matrix4(
            x * axis.x + 1f, axay, axaz, 0f,
            axay, y * axis.y + 1f, ayaz, 0f,
            axaz, ayaz, z * axis.z + 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Creates an involution matrix through the plane perpendicular to [axis].
 *
 * If [axis] is known to be a unit vector, [Involution] is a cheaper alternative.
 */
fun InvolutionSafe(axis: Vector2) = Involution(axis.normalized)

/**
 * Creates an involution matrix through the plane perpendicular to [axis].
 *
 * If [axis] is known to be a unit vector, [Involution] is a cheaper alternative.
 */
fun InvolutionSafe(axis: Vector3) = Involution(axis.normalized)

/**
 * Creates an involution matrix through the plane perpendicular to [axis].
 *
 * [axis] must be a unit vector.
 */
fun Involution(axis: Vector2): Matrix4 {
    val x = axis.x * 2f
    val y = axis.y * 2f
    val axay = x * axis.y

    return Matrix4(
            x * axis.x - 1f, axay, 0f, 0f,
            axay, y * axis.y - 1f, 0f, 0f,
            0f, 0f, -1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Creates an involution matrix through the plane perpendicular to [axis].
 *
 * [axis] must be a unit vector.
 */
fun Involution(axis: Vector3): Matrix4 {
    val x = axis.x * 2f
    val y = axis.y * 2f
    val z = axis.z * 2f
    val axay = x * axis.y
    val axaz = x * axis.z
    val ayaz = y * axis.z

    return Matrix4(
            x * axis.x - 1f, axay, axaz, 0f,
            axay, y * axis.y - 1f, ayaz, 0f,
            axaz, ayaz, z * axis.z - 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/** Creates a uniform scale matrix by [factor]. */
inline fun Scale(factor: Float) = Matrix4(
        factor, 0f, 0f, 0f,
        0f, factor, 0f, 0f,
        0f, 0f, factor, 0f,
        0f, 0f, 0f, 1f
)

/** Creates a nonuniform scale matrix by [xFactor] along the x axis, [yFactor] along the y axis and [zFactor] along the z axis. */
inline fun Scale(xFactor: Float, yFactor: Float, zFactor: Float) = Matrix4(
        xFactor, 0f, 0f, 0f,
        0f, yFactor, 0f, 0f,
        0f, 0f, zFactor, 0f,
        0f, 0f, 0f, 1f
)

/** Creates a nonuniform scale matrix by [factor].x along the x axis, [factor].y along the y axis and [factor].z along the z axis. */
inline fun Scale(factor: Vector3) = Matrix4(
        factor.x, 0f, 0f, 0f,
        0f, factor.y, 0f, 0f,
        0f, 0f, factor.z, 0f,
        0f, 0f, 0f, 1f
)

/**
 * Creates a scale matrix by [factor] along [axis].
 *
 * If [axis] is known to be a unit vector, [Scale] is a cheaper alternative.
 */
fun ScaleSafe(factor: Float, axis: Vector2) = Scale(factor, axis.normalized)

/**
 * Creates a scale matrix by [factor] along [axis].
 *
 * If [axis] is known to be a unit vector, [Scale] is a cheaper alternative.
 */
fun ScaleSafe(factor: Float, axis: Vector3) = Scale(factor, axis.normalized)

/**
 * Creates a scale matrix by [factor] along [axis].
 *
 * [axis] must be a unit vector.
 */
fun Scale(factor: Float, axis: Vector2): Matrix4 {
    val factor = factor - 1f

    val x = axis.x * factor
    val y = axis.y * factor
    val axay = x * axis.y

    return Matrix4(
            x * axis.x + 1f, axay, 0f, 0f,
            axay, y * axis.y + 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Creates a scale matrix by [factor] along [axis].
 *
 * [axis] must be a unit vector.
 */
fun Scale(factor: Float, axis: Vector3): Matrix4 {
    val factor = factor - 1f

    val x = axis.x * factor
    val y = axis.y * factor
    val z = axis.z * factor
    val axay = x * axis.y
    val axaz = x * axis.z
    val ayaz = y * axis.z

    return Matrix4(
            x * axis.x + 1f, axay, axaz, 0f,
            axay, y * axis.y + 1f, ayaz, 0f,
            axaz, ayaz, z * axis.z + 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Creates a skew matrix by [degrees] along the direction [a] based on the projected length along the direction [b].
 *
 * [a] and [b] must be orthonormal.
 */
fun Skew(degrees: Float, a: Vector2, b: Vector2): Matrix4 {
    val t = tan(degrees)
    val x = a.x * t
    val y = a.y * t

    return Matrix4(
            x * b.x + 1f, x * b.y, 0f, 0f,
            y * b.x, y * b.y + 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Creates a skew matrix by [degrees] along the direction [a] based on the projected length along the direction [b].
 *
 * [a] and [b] must be orthonormal.
 */
fun Skew(degrees: Float, a: Vector2, b: Vector3): Matrix4 {
    val t = tan(degrees)
    val x = a.x * t
    val y = a.y * t

    return Matrix4(
            x * b.x + 1f, x * b.y, x * b.z, 0f,
            y * b.x, y * b.y + 1f, y * b.z, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Creates a skew matrix by [degrees] along the direction [a] based on the projected length along the direction [b].
 *
 * [a] and [b] must be orthonormal.
 */
fun Skew(degrees: Float, a: Vector3, b: Vector2): Matrix4 {
    val t = tan(degrees)
    val x = a.x * t
    val y = a.y * t
    val z = a.z * t

    return Matrix4(
            x * b.x + 1f, x * b.y, 0f, 0f,
            y * b.x, y * b.y + 1f, 0f, 0f,
            z * b.x, z * b.y, 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/**
 * Creates a skew matrix by [degrees] along the direction [a] based on the projected length along the direction [b].
 *
 * [a] and [b] must be orthonormal.
 */
fun Skew(degrees: Float, a: Vector3, b: Vector3): Matrix4 {
    val t = tan(degrees)
    val x = a.x * t
    val y = a.y * t
    val z = a.z * t

    return Matrix4(
            x * b.x + 1f, x * b.y, x * b.z, 0f,
            y * b.x, y * b.y + 1f, y * b.z, 0f,
            z * b.x, z * b.y, z * b.z + 1f, 0f,
            0f, 0f, 0f, 1f
    )
}

/** Creates a translation matrix to [point]. */
inline fun Translation(point: Point2D) = Matrix4(
        1f, 0f, 0f, point.x,
        0f, 1f, 0f, point.y,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f
)

/** Creates a translation matrix to [point]. */
inline fun Translation(point: Point3D) = Matrix4(
        1f, 0f, 0f, point.x,
        0f, 1f, 0f, point.y,
        0f, 0f, 1f, point.z,
        0f, 0f, 0f, 1f
)

fun LookAt(eye: Point3D, target: Point3D): Quaternion {
    val forwardVector = normalize(target - eye)

    val rotAxis = cross(Vector3(0f, 0f, -1f), forwardVector)
    val dot = dot(Vector3(0f, 0f, -1f), forwardVector)

    return Quaternion(rotAxis.x, rotAxis.y, rotAxis.z, dot + 1).normalized
}

fun Quaternion(degrees: Float, axis: Vector3): Quaternion {
    if(degrees.isZero()) {
        return Quaternion.identity
    }

    val hdegrees = degrees * 0.5f
    val s = sin(hdegrees)
    val c = cos(hdegrees)

    return Quaternion(axis.x * s, axis.y * s, axis.z * s, c)
}

fun ToEuler(quaternion: Quaternion) : Vector3 {
    // roll (x-axis rotation)
    val sinrCosp = 2 * (quaternion.w * quaternion.x + quaternion.y * quaternion.z)
    val cosrCosp = 1 - 2 * (quaternion.x * quaternion.x + quaternion.y * quaternion.y)
    val x = atan2(sinrCosp, cosrCosp) * RADIANS_TO_DEGREES

    // pitch (y-axis rotation)
    val sinp = 2 * (quaternion.w * quaternion.y - quaternion.z * quaternion.x)
    var y: Float
    if (abs(sinp) >= 1)
        y = (PI / 2) * sinp.sign // use 90 degrees if out of range
    else
        y = asin(sinp)
    y *= RADIANS_TO_DEGREES

    // yaw (z-axis rotation)
    val sinyCosp = 2 * (quaternion.w * quaternion.z + quaternion.x * quaternion.y)
    val cosyCosp = 1 - 2 * (quaternion.y * quaternion.y + quaternion.z * quaternion.z)
    val z = atan2(sinyCosp, cosyCosp) * RADIANS_TO_DEGREES

    return Vector3(x, y, z)
}

fun ToQuaternion(euler: Vector3) : Quaternion {
    val cy = cos(euler.z * 0.5f)
    val sy = sin(euler.z * 0.5f)
    val cp = cos(euler.y * 0.5f)
    val sp = sin(euler.y * 0.5f)
    val cr = cos(euler.x * 0.5f)
    val sr = sin(euler.x * 0.5f)

    val w = cy * cp * cr + sy * sp * sr
    val x = cy * cp * sr - sy * sp * cr
    val y = sy * cp * sr + cy * sp * cr
    val z = sy * cp * cr - cy * sp * sr

    return Quaternion(x, y, z, w)
}

