package org.rufousengine.ecs.systems

import org.rufousengine.ecs.*
import org.rufousengine.ecs.components.Camera
import org.rufousengine.ecs.components.Model
import org.rufousengine.ecs.components.Transform
import org.rufousengine.graphics.internal.Materials
import org.rufousengine.system.GL

object RenderingSystem : System(0) {
    private var cameraEntities = mutableListOf<Entity>()
    private val entities = mutableListOf<Entity>()

    private val errorMaterial = Materials.Error()

    init {
        EntityChangeEvent += ::onEntityChange
    }

    override fun update() {
        for(cameraEntity in cameraEntities) {
            val cameraTransform = cameraEntity.getUnsafe<Transform>()
            val camera = cameraEntity.getUnsafe<Camera>()

            for (entity in entities) {
                val transform = entity.getUnsafe<Transform>()
                val model = entity.getUnsafe<Model>()

                for (mesh in model.meshes) {
                    val material = model.getMaterial(mesh) ?: errorMaterial

                    GL.useProgram(material.program)
                    material.setTransformParameters(transform.local, cameraTransform.inverse, camera.projection)
                    material.setParameters()

                    GL.bindVertexArray(mesh.vao)
                    GL.drawElements(mesh.count)
                    GL.unbindVertexArray()
                }
            }
        }
    }

    private fun onEntityChange(entity: Entity) {
        val transform = entity.get<Transform>()
        val camera = entity.get<Camera>()
        val model = entity.get<Model>()

        if(transform != null && camera != null) {
            cameraEntities.add(entity)
        } else {
            cameraEntities.remove(entity)
        }

        if(transform != null && model != null) {
            entities.add(entity)
        } else {
            entities.remove(entity)
        }
    }
}