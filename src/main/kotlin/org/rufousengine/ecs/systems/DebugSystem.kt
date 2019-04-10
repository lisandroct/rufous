package org.rufousengine.ecs.systems

import org.rufousengine.ecs.*
import org.rufousengine.ecs.components.Camera
import org.rufousengine.ecs.components.Model
import org.rufousengine.ecs.components.OmniLight
import org.rufousengine.ecs.components.Transform
import org.rufousengine.editor.TextureLoader
import org.rufousengine.files.Files
import org.rufousengine.graphics.Mesh
import org.rufousengine.graphics.VertexAttribute
import org.rufousengine.graphics.internal.Materials
import org.rufousengine.math.*
import org.rufousengine.system.GL

object DebugSystem : System(0) {
    private val quad = Mesh(
            floatArrayOf(
                    -0.5f, 0.5f, 0.0f, 0.0f, 0.0f,
                    -0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
                    0.5f, -0.5f, 0.0f, 1.0f, 1.0f,
                    0.5f, 0.5f, 0.0f, 1.0f, 0.0f
            ),
            intArrayOf(
                    0, 1, 3,
                    1, 2, 3
            ),
            VertexAttribute.mask(VertexAttribute.position, VertexAttribute.uv)
    )

    private var cameraEntities = mutableListOf<Entity>()
    private val entities = mutableListOf<Entity>()

    private val quadMaterial = Materials.DebugQuad()
    private val cQuadTransform = Matrix4()

    private val omniLightGizmo = TextureLoader.load(Files.local("textures/omni-light-gizmo.png"))

    init {
        GL.setClearColor(29 / 255f, 22 / 255f, 21 / 255f, 1f)
        EntityChangeEvent += ::onEntityChange
    }

    override fun update() {
        //GL.disableDepthTest()
        for(cameraEntity in cameraEntities) {
            val cameraTransform = cameraEntity.getUnsafe<Transform>()
            val camera = cameraEntity.getUnsafe<Camera>()

            for (entity in entities) {
                val transform = entity.getUnsafe<Transform>()

                val t = Translation(transform.worldPosition)
                //val s = Scale(transform.scale.x, transform.scale.y, transform.scale.z)
                val r = RotationZ(cameraTransform.worldRotation.z) * RotationY(cameraTransform.worldRotation.y) * RotationX(cameraTransform.worldRotation.x)
                multiply(t, r, cQuadTransform)

                entity.get<OmniLight>()?.let {
                    quadMaterial.texture = omniLightGizmo
                    //quadMaterial.tint.set(tint)

                    GL.useProgram(quadMaterial.program)
                    quadMaterial.setTransformParameters(cQuadTransform, cameraTransform.inverse, camera.projection)
                    quadMaterial.setParameters()

                    GL.bindVertexArray(quad.vao)
                    GL.drawElements(quad.count)
                    GL.unbindVertexArray()
                }
            }
        }
    }

    private fun onEntityChange(entity: Entity) {
        val transform = entity.get<Transform>()
        val camera = entity.get<Camera>()

        if(transform != null && camera != null) {
            cameraEntities.add(entity)
        } else {
            cameraEntities.remove(entity)
        }

        if(transform != null && camera == null) {
            entities.add(entity)
        } else {
            entities.remove(entity)
        }
    }
}