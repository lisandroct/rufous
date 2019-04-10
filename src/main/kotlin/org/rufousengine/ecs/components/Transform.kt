package org.rufousengine.ecs.components

import org.rufousengine.ecs.Component
import org.rufousengine.math.*
import org.rufousengine.utils.DirtyFlag
import kotlin.properties.Delegates

class Transform : Component() {
    val position = Point3D()
    val scale = Vector3(1f, 1f, 1f)
    val rotation = Vector3()

    private val _worldPosition = Point3D()
    val worldPosition: Point3D
        get() {
            _worldPosition.set(position)

            val parent = parent?.worldPosition
            if(parent != null) {
                _worldPosition.add(parent.x, parent.y, parent.z)
            }

            return _worldPosition
        }
    private val _worldRotation = Vector3()
    val worldRotation: Vector3
        get() {
            _worldRotation.set(rotation)

            val parent = parent?.worldRotation
            if(parent != null) {
                _worldRotation.add(parent)
            }

            return _worldRotation
        }

    var parent by Delegates.observable<Transform?>(null) { _, old, new ->
        old?._children?.remove(this)
        new?._children?.add(this)

        worldDirty = true
    }
    private val _children = mutableListOf<Transform>()
    val children: List<Transform> = _children

    private var localDirty: Boolean by Delegates.observable(true) { _, _, value ->
        if(value) {
            worldDirty = true
        }
    }
    private var worldDirty: Boolean by Delegates.observable(true) { _, _, value ->
        if(value) {
            for(child in children) {
                child.worldDirty = true
            }
            inverseDirty = true
        }
    }
    private var inverseDirty by DirtyFlag()

    private val _local = Matrix4()
    val local: Matrix4
        get() {
            checkDirty()

            if(localDirty) {
                val t = Translation(position)
                val s = Scale(scale.x, scale.y, scale.z)
                val r = RotationZ(rotation.z) * RotationY(rotation.y) * RotationX(rotation.x)

                multiply(t, s, r, _local)

                localDirty = false
            }

            return _local
        }

    private val _world = Matrix4()
    val world: Matrix4
        get() {
            checkDirty()
            parent?.checkDirty()

            if(worldDirty) {
                val parent = parent?.world
                if(parent != null) {
                    multiply(parent, local, _world)
                } else {
                    _world.set(local)
                }

                worldDirty = false
            }

            return _world
        }

    private val _inverse = Matrix4()
    val inverse: Matrix4
        get() {
            checkDirty()
            parent?.checkDirty()

            if(inverseDirty) {
                inverse(world, _inverse)
            }

            return _inverse
        }

    private fun checkDirty() {
        localDirty = localDirty or position.dirty or scale.dirty or rotation.dirty
    }
}