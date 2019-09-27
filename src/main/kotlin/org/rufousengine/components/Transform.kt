package org.rufousengine.components

import org.rufousengine.ecs.Component
import org.rufousengine.math.*
import org.rufousengine.utils.DirtyFlag
import kotlin.properties.Delegates

class Transform : Component() {
    var position = Point3D()
    var scale = Vector3(1f, 1f, 1f)
    var rotation = Quaternion()

    private val _worldPosition = Point3D()
    val worldPosition: Point3D
        get() {
            val parentPosition = parent?.worldPosition ?: Point3D()
            val parentRotation = parent?.worldRotation ?: Quaternion()

            _worldPosition.set(parentPosition)

            val c = Vector3(position.x, position.y, position.z)
            c.rotate(parentRotation)

            _worldPosition.add(c)

            return _worldPosition
        }
    private val _worldRotation = Quaternion()
    val worldRotation: Quaternion
        get() {
            _worldRotation.set(rotation)

            val parent = parent?.worldRotation
            if(parent != null) {
                _worldRotation.multiply(parent)
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
                val r = Rotation(rotation)

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

    private val _up = Vector3()
    val up: Vector3
        get() {
            Vector3.y.rotate(worldRotation)

            return _up
        }

    private val _forward = Vector3()
    val forward: Vector3
        get() {
            Vector3.z.rotate(worldRotation)

            return _forward
        }

    private val _left = Vector3()
    val left: Vector3
        get() {
            Vector3.x.rotate(worldRotation)

            return _left
        }

    private fun checkDirty() {
        localDirty = localDirty or position.dirty or scale.dirty or rotation.dirty
    }
}