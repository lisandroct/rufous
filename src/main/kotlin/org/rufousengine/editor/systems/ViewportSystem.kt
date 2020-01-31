package org.rufousengine.editor.systems

import org.rufousengine.components.Camera
import org.rufousengine.components.Model
import org.rufousengine.components.Transform
import org.rufousengine.ecs.*
import org.rufousengine.editor.components.EditorOnly
import org.rufousengine.events.mouse.MouseButtonPressed
import org.rufousengine.events.mouse.MouseButtonReleased
import org.rufousengine.events.mouse.MouseMoveEvent
import org.rufousengine.events.mouse.ScrollEvent
import org.rufousengine.graphics.Mesh
import org.rufousengine.graphics.VertexAttribute
import org.rufousengine.graphics.internal.Materials
import org.rufousengine.math.*
import org.rufousengine.system.GL
import org.rufousengine.utils.MouseButtons

object ViewportSystem : System(0) {
    private val cameras = Family(arrayOf(Transform::class, Camera::class, EditorOnly::class))
    private val models = Family(arrayOf(Transform::class, Model::class))

    private val errorMaterial = Materials.Error()

    private var middleClick = false
    private var rightClick = false

    private val xMesh = Mesh(
            floatArrayOf(
                    0f, 0f, 0f,
                    1f, 0f, 0f
            ),
            intArrayOf(
                    0, 1
            ),
            arrayOf(VertexAttribute(0, "aPosition", 3))
    )
    private val yMesh = Mesh(
            floatArrayOf(
                    0f, 0f, 0f,
                    0f, 1f, 0f
            ),
            intArrayOf(
                    0, 1
            ),
            arrayOf(VertexAttribute(0, "aPosition", 3))
    )
    private val zMesh = Mesh(
            floatArrayOf(
                    0f, 0f, 0f,
                    0f, 0f, 1f
            ),
            intArrayOf(
                    0, 1
            ),
            arrayOf(VertexAttribute(0, "aPosition", 3))
    )
    private val vertex = GL.VertexShader("""
        |#version 330 core
        |layout (location = 0) in vec3 aPosition;
        |
        |uniform mat4 uView;
        |uniform mat4 uProjection;
        |
        |void main()
        |{
        |    gl_Position = uProjection * uView * vec4(aPosition, 1.0);
        |}
        |""".trimMargin())
    private val geometry = GL.GeometryShader("""
        |#version 330 core
        |layout (lines) in;
        |layout (triangle_strip, max_vertices = 4) out;
        |
        |uniform float uThickness;
        |uniform vec2 uViewport;
        |
        |void main()
        |{
        |    vec2 thick = uThickness / uViewport;
        |    
        |    vec4 p0 = gl_in[0].gl_Position;
        |    vec4 p1 = gl_in[1].gl_Position;
        |    
        |    vec2 dir = normalize(p0.w * p1.xy - p1.w * p0.xy);
        |    vec2 normal = vec2(-dir.y, dir.x);
        |    
        |    vec4 offset = vec4(thick * normal, 0.0, 0.0);
        |    if (p0.w < 0) {
        |        gl_Position = (p0.w * p1 - p1.w * p0) / (p0.w - p1.w);
        |        EmitVertex();
        |    } else {
        |        gl_Position = p0 + offset * p0.w;
        |        EmitVertex();
        |        gl_Position = p0 - offset * p0.w;
        |        EmitVertex();
        |    }
        |    if (p1.w < 0) {
        |        gl_Position = (p0.w * p1 - p1.w * p0) / (p0.w - p1.w);
        |        EmitVertex();
        |    } else {
        |        gl_Position = p1 + offset * p1.w;
        |        EmitVertex();
        |        gl_Position = p1 - offset * p1.w;
        |        EmitVertex();
        |    }
        |    
        |    EndPrimitive();
        |}
        |""".trimMargin())
    private val fragment = GL.FragmentShader("""
        |#version 330 core
        |layout (location = 0) out vec4 FragColor;
        |
        |uniform vec4 uColor;
        |
        |void main()
        |{
        |    FragColor = uColor;
        |}
        |""".trimMargin())

    private val shader = GL.ShaderProgram(vertex, geometry, fragment)
    private val viewLocation = GL.getUniformLocation(shader, "uView")
    private val projectionLocation = GL.getUniformLocation(shader, "uProjection")
    private val viewportLocation = GL.getUniformLocation(shader, "uViewport")
    private val thicknessLocation = GL.getUniformLocation(shader, "uThickness")
    private val colorLocation = GL.getUniformLocation(shader, "uColor")

    init {
        GL.setClearColor(29 / 255f, 22 / 255f, 21 / 255f, 1f)
        cameras.subscribe()
        models.subscribe()

        MouseMoveEvent += ::mouseMove
        MouseButtonPressed += ::mouseClicked
        MouseButtonReleased += ::mouseReleased
        ScrollEvent += :: scroll
    }

    override fun update() {
        val camera = cameras[0].getUnsafe<Camera>()
        val cameraTransform = cameras[0].getUnsafe<Transform>()

        GL.enableFaceCulling()
        GL.enableDepthTest()
        GL.clear()

        for (entity in models) {
            val transform = entity.getUnsafe<Transform>()
            val model = entity.getUnsafe<Model>()

            for (mesh in model.meshes) {
                val material = model.getMaterial(mesh) ?: errorMaterial

                GL.useProgram(material.program)
                material.setTransformParameters(transform.world, transform.inverse, cameraTransform.inverse, camera.projection)
                material.setParameters()

                GL.bindVertexArray(mesh.vao)
                GL.drawElements(mesh.count)
                GL.unbindVertexArray()
            }
        }

        GL.disableFaceCulling()
        GL.disableDepthTest()
        GL.useProgram(shader)
        GL.setUniformMatrix(viewLocation, cameraTransform.inverse)
        GL.setUniformMatrix(projectionLocation, camera.projection)
        GL.setUniformVector(viewportLocation, Vector2(1280f, 720f))
        GL.setUniformFloat(thicknessLocation, 2f)
        GL.bindVertexArray(xMesh.vao)
        GL.setUniformVector(colorLocation, Vector4(1f, 0f, 0f, 1f))
        GL.drawElements(xMesh.count, GL.DrawModes.LINES)
        GL.bindVertexArray(yMesh.vao)
        GL.setUniformVector(colorLocation, Vector4(0f, 1f, 0f, 1f))
        GL.drawElements(yMesh.count, GL.DrawModes.LINES)
        GL.bindVertexArray(zMesh.vao)
        GL.setUniformVector(colorLocation, Vector4(0f, 0f, 1f, 1f))
        GL.drawElements(zMesh.count, GL.DrawModes.LINES)
        GL.unbindVertexArray()
    }

    private fun mouseMove(x: Float, y: Float) {
        if(middleClick) {
            val target = cameras[0].getUnsafe<Transform>().parent!!
            val d = target.right * (-x * 0.01f) + target.up * (y * 0.01f)
            target.position += d
        } else if(rightClick) {
            val target = cameras[0].getUnsafe<Transform>().parent!!
            val q = Quaternion(-y * 0.1f, target.right) * Quaternion(-x * 0.1f, Vector3(0f, 1f, 0f))
            target.rotation = q * target.rotation
        }
    }

    private fun mouseClicked(button: MouseButtons) {
        when(button) {
            MouseButtons.MIDDLE -> middleClick = true
            MouseButtons.RIGHT -> rightClick = true
            else -> Unit
        }
    }

    private fun mouseReleased(button: MouseButtons) {
        when(button) {
            MouseButtons.MIDDLE -> middleClick = false
            MouseButtons.RIGHT -> rightClick = false
            else -> Unit
        }
    }

    private fun scroll(x: Float, y: Float) {
        val cameraTransform = cameras[0].getUnsafe<Transform>()
        val target = cameraTransform.parent!!
        val d = distance(cameraTransform.position, target.position)
        cameraTransform.position += Vector3.z * (-y * d * 0.05f)
    }
}