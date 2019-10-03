package org.rufousengine.components

import org.rufousengine.ecs.Component
import org.rufousengine.math.*
import org.rufousengine.utils.DirtyFlag
import kotlin.properties.Delegates.observable

class Transform : Component() {
    var position by observable(Point3D.origin) { _, _, _ ->
        localDirty = true
        wpDirty = true
        worldDirty = true
        inverseDirty = true
    }
    var scale by observable(Vector3.one) { _, _, _ ->
        localDirty = true
        wpDirty = true
        worldDirty = true
        inverseDirty = true
    }
    var rotation by observable(Quaternion.identity) { _, _, _ ->
        localDirty = true
        wpDirty = true
        worldDirty = true
        inverseDirty = true
    }

    private var wpDirty by DirtyFlag()
    private var _worldPosition = Point3D()
    val worldPosition: Point3D
        get() {
            //if(wpDirty) {
                val parentPosition = parent?.worldPosition ?: Point3D.origin
                val parentRotation = parent?.worldRotation ?: Quaternion.identity

                _worldPosition = parentPosition + transform(parentRotation, Vector3(position))
            //}

            return _worldPosition
        }
    private var wrDirty by DirtyFlag()
    private var _worldRotation = Quaternion()
    val worldRotation: Quaternion
        get() {
            //if(wrDirty) {
                val parentRotation = parent?.worldRotation ?: Quaternion.identity
                _worldRotation = rotation * parentRotation
            //}

            return _worldRotation
        }

    var parent by observable<Transform?>(null) { _, old, new ->
        old?._children?.remove(this)
        new?._children?.add(this)

        wpDirty = true
        wrDirty = true
        worldDirty = true
        for(child in children) {
            child.wpDirty = true
            child.wrDirty = true
            child.worldDirty = true
        }
    }
    private val _children = mutableListOf<Transform>()
    val children: List<Transform> = _children

    private var inverseDirty by DirtyFlag()

    private var localDirty by DirtyFlag()
    private var _local = Matrix4()
    val local: Matrix4
        get() {
            //if(localDirty) {
                _local = Translation(position) * Scale(scale) * Rotation(rotation)
            //}

            return _local
        }

    private var worldDirty by DirtyFlag()
    private var _world = Matrix4()
    val world: Matrix4
        get() {
            //if(worldDirty) {
                val parentWorld = parent?.world ?: Matrix4.identity

                _world = parentWorld * local
            //}

            return _world
        }

    private var _inverse = Matrix4()
    val inverse: Matrix4
        get() {
            //if(inverseDirty) {
                _inverse = world.inverse
            //}

            return _inverse
        }

    val up: Vector3
        get() = transform(worldRotation, Vector3.y)

    val forward: Vector3
        get() = transform(worldRotation, Vector3.z)

    val left: Vector3
        get() = transform(worldRotation, Vector3.x)
}