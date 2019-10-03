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

    private val lineMesh = Mesh(
            floatArrayOf(
                    // Prev - Next - Current - Side
                    0f, 0f, 0f, 5f, 5f, 5f, 0f, 0f, 0f, -1f,
                    0f, 0f, 0f, 5f, 5f, 5f, 0f, 0f, 0f, 1f,
                    0f, 0f, 0f, 5f, 5f, 5f, 5f, 5f, 5f, -1f,
                    0f, 0f, 0f, 5f, 5f, 5f, 5f, 5f, 5f, 1f
            ),
            intArrayOf(
                    0, 2, 1,
                    1, 2, 3
            ),
            arrayOf(VertexAttribute(0, "previous", 3), VertexAttribute(1, "next", 3), VertexAttribute(2, "current", 3), VertexAttribute(3, "direction", 1))
    )
    private val vertex = GL.VertexShader("""
        |#version 330 core
        |layout (location = 0) in vec3 previous;
        |layout (location = 1) in vec3 next;
        |layout (location = 2) in vec3 current;
        |layout (location = 3) in float direction;
        |
        |uniform mat4 uView;
        |uniform mat4 uProjection;
        |uniform float uAspect;
        |uniform float uTFov;
        |uniform vec3 uCameraPosition;
        |uniform float uLineWidth;
        |
        |void main()
        |{
        |    float distance = length(uCameraPosition - current);
        |    float toScreen = (uTFov * distance * 2.0) / 720.0;
        |    
        |    mat4 projView = uProjection * uView;
        |    
        |    vec4 previousProjected = projView * vec4(previous, 1.0f);
        |    vec2 previousScreen = previousProjected.xy / previousProjected.w;
        |    previousScreen.x *= uAspect;
        |    
        |    vec4 nextProjected = projView * vec4(next, 1.0f);
        |    vec2 nextScreen = nextProjected.xy / nextProjected.w;
        |    nextScreen.x *= uAspect;
        |    
        |    vec4 currentProjected = projView * vec4(current, 1.0f);
        |    vec2 currentScreen = currentProjected.xy / currentProjected.w;
        |    currentScreen.x *= uAspect;
        |    
        |    float w = uLineWidth * toScreen;
        |            
        |    vec2 dir;
        |    if(nextScreen == currentScreen) dir = normalize(currentScreen - previousScreen);
        |    else if(previousScreen == currentScreen) dir = normalize(nextScreen - currentScreen);
        |    else dir = normalize(nextScreen - previousScreen);
        |    
        |    vec2 normal = vec2(-dir.y, dir.x);
        |    normal.x /= uAspect;
        |    normal *= .5 * w;
        |    
        |    vec2 offset = normal * direction;
        |    currentProjected.xy += offset;
        |    
        |    gl_Position = currentProjected;
        |}
        |""".trimMargin())
    private val fragment = GL.FragmentShader("""
        |#version 330 core
        |layout (location = 0) out vec4 FragColor;
        |
        |void main()
        |{
        |    FragColor = vec4(1.0, 1.0, 1.0, 1.0);
        |}
        |""".trimMargin())

    private val shader = GL.ShaderProgram(vertex, fragment)
    private val viewLocation = GL.getUniformLocation(shader, "uView")
    private val projectionLocation = GL.getUniformLocation(shader, "uProjection")
    private val aspectLocation = GL.getUniformLocation(shader, "uAspect")
    private val tfovLocation = GL.getUniformLocation(shader, "uTFov")
    private val cameraPositionLocation = GL.getUniformLocation(shader, "uCameraPosition")
    private val lineWidthLocation = GL.getUniformLocation(shader, "uLineWidth")

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

        GL.disableDepthTest()
        GL.useProgram(shader)
        GL.setUniformMatrix(viewLocation, cameraTransform.inverse)
        GL.setUniformMatrix(projectionLocation, camera.projection)
        GL.setUniformFloat(aspectLocation, camera.aspectRatio)
        GL.setUniformFloat(tfovLocation, tan(camera.fieldOfView * 0.5f))
        GL.setUniformPoint(cameraPositionLocation, cameraTransform.worldPosition)
        GL.setUniformFloat(lineWidthLocation, 40f)
        GL.bindVertexArray(lineMesh.vao)
        GL.drawElements(lineMesh.count)
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