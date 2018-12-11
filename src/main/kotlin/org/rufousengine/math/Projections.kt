@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.math

/**
 * Sets [matrix] as a symmetric orthographic projection.
 *
 * @param[width] The width of the frustum in world units.
 * @param[height] The height of the frustum in world units.
 * @param[near] The distanceTo to the near plane in world units. Must be positive.
 * @param[far] The distanceTo to the far plan in world units. Must be greater than [near].
 * @return The [matrix] for chaining.
 */
fun orthographic(width: Float, height: Float, near: Float, far: Float, matrix: Matrix4) : Matrix4 {
    if(width < 0f || height < 0f || near <= 0f) {
        throw IllegalArgumentException("width, height and near must be positives.")
    }
    if(far <= near) {
        throw IllegalArgumentException("far must be greater than near.")
    }

    val a = 1 / (far - near)
    val r = width * 0.5f
    val t = height * 0.5f

    val e00 = 1 / r
    val e11 = 1 / t
    val e22 = -2 * a
    val e32 = -(far + near) * a

    return matrix.set(
            e00, 0f, 0f, 0f,
            0f, e11, 0f, 0f,
            0f, 0f, e22, 0f,
            0f, 0f, e32, 1f
    )
}
/*
/**
 * Sets [matrix] as an orthographic projection.
 *
 * @return The [matrix] for chaining.
 */
fun orthographic(top: Float, bottom: Float, right: Float, left: Float, near: Float, far: Float, matrix: Matrix4) : Matrix4 {
    if(top < 0f || right < 0f || near <= 0f) {
        throw IllegalArgumentException("top, right and near must be positives.")
    }
    if(bottom > 0f || left > 0f) {
        throw IllegalArgumentException("bottom and left must be negatives.")
    }
    if(far <= near) {
        throw IllegalArgumentException("far must be greater than near.")
    }

    val a = 1 / (right - left)
    val b = 1 / (top - bottom)
    val c = 1 / (far - near)

    val e00 = 2 * a
    val e11 = 2 * b
    val e22 = -2f * c
    val e30 = -(right + left) * a
    val e31 = -(top + bottom) * b
    val e32 = -(far + near) * c
    val e33 = 1f

    return matrix.set(
            e00, 0f, 0f, 0f,
            0f, e11, 0f, 0f,
            0f, 0f, e22, 0f,
            e30, e31, e32, e33
    )
}
*/

/**
 * Sets [matrix] as a symmetric perspective projection.
 *
 * @param[fieldOfView] The vertical field of view in degrees.
 * @param[aspectRatio] The aspect ratio.
 * @param[near] The distanceTo to the near plane in world units. Must be positive.
 * @param[far] The distanceTo to the far plan in world units. Must be greater than [near].
 * @return The [matrix] for chaining.
 */
fun perspective(fieldOfView: Float, aspectRatio: Float, near: Float, far: Float, matrix: Matrix4) : Matrix4 {
    if(fieldOfView <= 0f || fieldOfView > 180f) {
        throw IllegalArgumentException("fieldOfView must be in range (0, 180]")
    }
    if(aspectRatio < 0f || near <= 0f) {
        throw IllegalArgumentException("aspectRatio and near must be positives.")
    }
    if(far <= near) {
        throw IllegalArgumentException("far must be greater than near.")
    }

    val a = 1f / tan(fieldOfView.toRadians() * 0.5f)
    val b = 1f / (far - near)
    val e00 = a / aspectRatio
    val e22 = -(far + near) * b
    val e32 = -2f * far * near * b

    return matrix.set(
            e00, 0f, 0f, 0f,
            0f, a, 0f, 0f,
            0f, 0f, e22, -1f,
            0f, 0f, e32, 0f
    )
}
/*
/**
 * Sets [matrix] as a perspective projection.
 *
 * @return The [matrix] for chaining.
 */
fun perspective(top: Float, bottom: Float, right: Float, left: Float, near: Float, far: Float, matrix: Matrix4) : Matrix4 {
    if(top < 0f || right < 0f || near <= 0f) {
        throw IllegalArgumentException("top, right and near must be positives.")
    }
    if(bottom > 0f || left > 0f) {
        throw IllegalArgumentException("bottom and left must be negatives.")
    }
    if(far <= near) {
        throw IllegalArgumentException("far must be greater than near.")
    }

    val a = 2f * near
    val b = 1 / (right - left)
    val c = 1 / (top - bottom)
    val d = 1 / (far - near)

    val e00 = a * b
    val e11 = a * c
    val e20 = (right + left) * b
    val e21 = (top + bottom) * c
    val e22 = -(far + near) * d
    val e32 = -a * far * d

    return matrix.set(
            e00, 0f, 0f, 0f,
            0f, e11, 0f, 0f,
            e20, e21, e22, -1f,
            0f, 0f, e32, 0f
    )
}
*/

// ---------------------------------------------------------------------------------------------------------------------

/**
 * Creates a symmetric orthographic projection.
 *
 * @param[width] The width of the frustum in world units.
 * @param[height] The height of the frustum in world units.
 * @param[near] The distanceTo to the near plane in world units. Must be positive.
 * @param[far] The distanceTo to the far plan in world units. Must be greater than [near].
 */
inline fun Orthographic(width: Float, height: Float, near: Float, far: Float) = orthographic(width, height, near, far, Matrix4())
/*
/** Creates an orthographic projection. */
inline fun Orthographic(top: Float, bottom: Float, right: Float, left: Float, near: Float, far: Float) = orthographic(top, bottom, right, left, near, far, Matrix4())
*/

/**
 * Creates a symmetric perspective projection.
 *
 * @param[fieldOfView] The vertical field of view in degrees.
 * @param[aspectRatio] The aspect ratio.
 * @param[near] The distanceTo to the near plane in world units. Must be positive.
 * @param[far] The distanceTo to the far plan in world units. Must be greater than [near].
 */
inline fun Perspective(fieldOfView: Float, aspectRatio: Float, near: Float, far: Float) = perspective(fieldOfView, aspectRatio, near, far, Matrix4())
/*
/** Creates a perspective projection. */
inline fun Perspective(top: Float, bottom: Float, right: Float, left: Float, near: Float, far: Float) = perspective(top, bottom, right, left, near, far, Matrix4())
*/