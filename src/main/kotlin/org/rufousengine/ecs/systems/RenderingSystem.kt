package org.rufousengine.ecs.systems

import org.rufousengine.ecs.*
import org.rufousengine.ecs.components.Camera
import org.rufousengine.ecs.components.Model
import org.rufousengine.ecs.components.OmniLight
import org.rufousengine.ecs.components.Transform
import org.rufousengine.graphics.internal.Materials
import org.rufousengine.system.GL

object RenderingSystem : System(0) {
    private val cameras = Family(arrayOf(Transform::class, Camera::class))
    private val lights = Family(arrayOf(Transform::class, OmniLight::class))
    private val models = Family(arrayOf(Transform::class, Model::class))

    private val errorMaterial = Materials.Error()

    init {
        GL.setClearColor(29 / 255f, 22 / 255f, 21 / 255f, 1f)
        cameras.subscribe()
        lights.subscribe()
        models.subscribe()
    }

    override fun update() {
        GL.clear(true)
        GL.enableDepthTest()
        for(cameraEntity in cameras) {
            val cameraTransform = cameraEntity.getUnsafe<Transform>()
            val camera = cameraEntity.getUnsafe<Camera>()

            for (entity in models) {
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
                    material.setTransformParameters(transform.world, transform.inverse, cameraTransform.inverse, camera.projection)
                    material.setParameters()

                    GL.bindVertexArray(mesh.vao)
                    GL.drawElements(mesh.count)
                    GL.unbindVertexArray()
                }
            }
        }
    }
}