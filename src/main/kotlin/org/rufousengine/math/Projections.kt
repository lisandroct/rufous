@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.math

/**
 * Creates a symmetric orthographic projection.
 *
 * @param[width] The width of the frustum in world units.
 * @param[height] The height of the frustum in world units.
 * @param[near] The distanceTo to the near plane in world units. Must be positive.
 * @param[far] The distanceTo to the far plan in world units. Must be greater than [near].
 */
inline fun Orthographic(width: Float, height: Float, near: Float, far: Float) : Matrix4 {
    require(!(width < 0f || height < 0f || near <= 0f)) { "width, height and near must be positives." }
    require(far > near) { "far must be greater than near." }

    val r = width * 0.5f
    val t = height * 0.5f

    val e00 = 1 / r
    val e11 = 1 / t
    val e22 = -2f / (far - near)
    val e23 = -(far + near) / (far - near)

    return Matrix4(
            e00, 0f, 0f, 0f,
            0f, e11, 0f, 0f,
            0f, 0f, e22, e23,
            0f, 0f, 0f, 1f
    )
}

/**
 * Creates a symmetric perspective projection.
 *
 * @param[fieldOfView] The vertical field of view in degrees.
 * @param[aspectRatio] The aspect ratio.
 * @param[near] The distanceTo to the near plane in world units. Must be positive.
 * @param[far] The distanceTo to the far plan in world units. Must be greater than [near].
 */
inline fun Perspective(fieldOfView: Float, aspectRatio: Float, near: Float, far: Float) : Matrix4 {
    require(!(fieldOfView <= 0f || fieldOfView > 180f)) { "fieldOfView must be in range (0, 180]" }
    require(!(aspectRatio < 0f || near <= 0f)) { "aspectRatio and near must be positives." }
    require(far > near) { "far must be greater than near." }

    val tan = tan(fieldOfView * 0.5f)
    val t = tan * near
    val r = t * aspectRatio

    val e00 = near / r
    val e11 = near / t
    val a = 1 / (far - near)
    val e22 = -(far + near) * a
    val e23 = (-2f * far * near) * a

    return Matrix4(
            e00, 0f, 0f, 0f,
            0f, e11, 0f, 0f,
            0f, 0f, e22, e23,
            0f, 0f, -1f, 0f
    )
}