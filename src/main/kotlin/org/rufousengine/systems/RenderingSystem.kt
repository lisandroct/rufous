package org.rufousengine.systems

import org.lwjgl.opengl.GL11.*
import org.rufousengine.ecs.*
import org.rufousengine.components.Camera
import org.rufousengine.components.Model
import org.rufousengine.components.OmniLight
import org.rufousengine.components.Transform
import org.rufousengine.graphics.*
import org.rufousengine.graphics.internal.Materials
import org.rufousengine.system.GL

object RenderingSystem : System(0) {
    private val cameras = Family(arrayOf(Transform::class, Camera::class))
    private val lights = Family(arrayOf(Transform::class, OmniLight::class))
    private val models = Family(arrayOf(Transform::class, Model::class))

    private val errorMaterial = Materials.Error()

    private val quad = Mesh(
            floatArrayOf(
                    -1f, 1f, 0.0f, 0.0f, 1.0f,
                    -1f, -1f, 0.0f, 0.0f, 0.0f,
                    1f, -1f, 0.0f, 1.0f, 0.0f,
                    1f, 1f, 0.0f, 1.0f, 1.0f
            ),
            intArrayOf(
                    0, 1, 3,
                    1, 2, 3
            ),
            VertexAttribute.mask(VertexAttribute.position, VertexAttribute.uv)
    )
    private val vertex = GL.VertexShader("""
        |#version 330 core
        |layout (location = 0) in vec3 aPos;
        |layout (location = 3) in vec2 aTexCoords;
        |
        |out vec2 TexCoords;
        |
        |void main()
        |{
        |    gl_Position = vec4(aPos.x, aPos.y, 0.0, 1.0);
        |    TexCoords = aTexCoords;
        |}
        |""".trimMargin())
    private val fragment = GL.FragmentShader("""
        |#version 330 core
        |layout (location = 0) out vec4 FragColor;
        |
        |in vec2 TexCoords;
        |
        |uniform sampler2D screenTexture;
        |
        |void main()
        |{
        |    FragColor = texture(screenTexture, TexCoords);
        |}
        |""".trimMargin())

    private val shader = GL.ShaderProgram(vertex, fragment)

    private val framebuffer = Framebuffer(1280, 720, arrayOf(AttachmentDefinition(TextureFormat.RGB, true), AttachmentDefinition(TextureFormat.DepthStencil, false)))

    init {
        GL.setClearColor(29 / 255f, 22 / 255f, 21 / 255f, 1f)
        cameras.subscribe()
        lights.subscribe()
        models.subscribe()

        GL.useProgram(shader)
        GL.setUniformInt(GL.getUniformLocation(shader, "screenTexture"), 0)
    }

    override fun update() {
        GL.bindFrameBuffer(framebuffer.name)
        GL.enableDepthTest()
        GL.clear()
        for(cameraEntity in cameras) {
            val cameraTransform = cameraEntity.getUnsafe<Transform>()
            val camera = cameraEntity.getUnsafe<Camera>()

            for (entity in models) {
                val transform = entity.getUnsafe<Transform>()
                val model = entity.getUnsafe<Model>()

                for (mesh in model.meshes) {
                    val material = model.getMaterial(mesh) ?: errorMaterial
                    if(material is Materials.Gouraud) {
                        material.lightPosition = lights[0].getUnsafe<Transform>().worldPosition
                    }
                    if(material is Materials.Phong) {
                        material.lightPosition = lights[0].getUnsafe<Transform>().worldPosition
                    }
                    if(material is Materials.BlinnPhong) {
                        material.lightPosition = lights[0].getUnsafe<Transform>().worldPosition
                    }

                    GL.useProgram(material.program)
                    material.setTransformParameters(transform.world, transform.inverse, cameraTransform.inverse, camera.projection)
                    material.setParameters()

                    GL.bindVertexArray(mesh.vao)
                    GL.drawElements(mesh.count, GL.DrawModes.LINES)
                    GL.unbindVertexArray()
                }
            }
        }

        GL.unbindFrameBuffer(read = false)
        GL.blitFramebuffer(0, 0, 1280, 720, 0, 0, 1280, 720, GL.TextureFilter.Nearest, color = false, stencil = false)
        GL.unbindFrameBuffer()

        GL.disableDepthTest()
        GL.clear(depth = false)
        GL.useProgram(shader)
        GL.bindTexture(framebuffer.attachments[0].name, 0)
        GL.bindVertexArray(quad.vao)
        GL.drawElements(quad.count)
        GL.unbindVertexArray()
    }
}