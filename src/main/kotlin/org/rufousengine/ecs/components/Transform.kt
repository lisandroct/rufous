package org.rufousengine.ecs.components

import org.rufousengine.ecs.Component
import org.rufousengine.math.*

class Transform : Component() {
    private val _local = Matrix4()
    val local: Matrix4
        get() {
            val translation = Translation(position)
            val scale = Scale(scale.x, scale.y, scale.z)
            val rotation = RotationX(rotation.x) * RotationY(rotation.y) * RotationZ(rotation.z)

            _local.set(translation * scale * rotation)

            return _local
        }

    val position = Point3D()
    val scale = Vector3(1f, 1f, 1f)
    val rotation = Vector3()

    val inverse: Matrix4
        get() = _local.inverse()
}