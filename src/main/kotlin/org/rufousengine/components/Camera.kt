package org.rufousengine.components

import org.rufousengine.ecs.Component
import org.rufousengine.math.Matrix4
import org.rufousengine.math.Perspective

class Camera : Component() {
    var fieldOfView = 45f
    var aspectRatio = 16 / 9f
    var near = 0.1f
    var far = 1000f

    val projection: Matrix4
        get() = Perspective(fieldOfView, aspectRatio, near, far)
}