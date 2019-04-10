package org.rufousengine.ecs.systems

import org.rufousengine.ecs.*
import org.rufousengine.ecs.components.Camera
import org.rufousengine.ecs.components.Model
import org.rufousengine.ecs.components.OmniLight
import org.rufousengine.ecs.components.Transform
import org.rufousengine.graphics.internal.Gouraud
import org.rufousengine.graphics.internal.Materials
import org.rufousengine.system.GL

object RenderingSystem : System(0) {
    private var cameraEntities = mutableListOf<Entity>()
    private val lights = mutableListOf<Entity>()
    private val entities = mutableListOf<Entity>()

    private val errorMaterial = Materials.Error()

    init {
        GL.setClearColor(29 / 255f, 22 / 255f, 21 / 255f, 1f)
        EntityChangeEvent += ::onEntityChange
    }

    override fun update() {
        GL.clear(true)
        GL.enableDepthTest()
        for(cameraEntity in cameraEntities) {
            val cameraTransform = cameraEntity.getUnsafe<Transform>()
            val camera = cameraEntity.getUnsafe<Camera>()

            for (entity in entities) {
                val transform = entity.getUnsafe<Transform>()
                val model = entity.getUnsafe<Model>()

                for (mesh in model.meshes) {
                    val material = model.getMaterial(mesh) ?: errorMaterial
                    if(material is Materials.Gouraud) {
                        material.lightPosition.set(lights[0].getUnsafe<Transform>().worldPosition)
                    }
                    if(material is Materials.Phong) {
                        material.lightPosition.set(lights[0].getUnsafe<Transform>().worldPosition)
                    }
                    if(material is Materials.BlinnPhong) {
                        material.lightPosition.set(lights[0].getUnsafe<Transform>().worldPosition)
                    }

                    GL.useProgram(material.program)
                    material.setTransformParameters(transform.world, cameraTransform.inverse, camera.projection)
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
        val light = entity.get<OmniLight>()

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

        if(transform != null && light != null) {
            lights.add(entity)
        } else {
            lights.remove(entity)
        }
    }
}