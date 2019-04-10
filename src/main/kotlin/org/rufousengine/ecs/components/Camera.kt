package org.rufousengine.ecs.components

import org.rufousengine.ecs.Component
import org.rufousengine.math.Matrix4
import org.rufousengine.math.Perspective
import org.rufousengine.math.perspective

class Camera : Component() {
    var fieldOfView = 70f
    var aspectRatio = 16 / 9f
    var near = 0.1f
    var far = 1000f

    val projection: Matrix4
        get() = Perspective(fieldOfView, aspectRatio, near, far)
}