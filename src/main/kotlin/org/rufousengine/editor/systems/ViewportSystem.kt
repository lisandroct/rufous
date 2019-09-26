package org.rufousengine.editor.systems

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.glLineWidth
import org.rufousengine.components.Camera
import org.rufousengine.components.Model
import org.rufousengine.components.Transform
import org.rufousengine.ecs.*
import org.rufousengine.editor.components.EditorOnly
import org.rufousengine.events.mouse.MouseButtonPressed
import org.rufousengine.events.mouse.MouseButtonReleased
import org.rufousengine.events.mouse.MouseMoveEvent
import org.rufousengine.events.mouse.ScrollEvent
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
    }

    private fun mouseMove(x: Float, y: Float) {
        if(middleClick) {
            val cameraTransform = cameras[0].getUnsafe<Transform>()
            val target = cameraTransform.parent
            val c = cVector(target!!.left).scale(-x * 0.01f)
            cameraTransform.position.add(c)
            target?.position?.add(c)
            //c.set(cameraTransform.up).scale(y * 0.01f)
            //cameraTransform.position.add(c)
            //target?.position?.add(c)
        } else if(rightClick) {
            val cameraTransform = cameras[0].getUnsafe<Transform>()
            val target = cameraTransform.parent
            val q = rotation(-x * 0.1f, Vector3(0f, 1f, 0f), Quaternion())
            target!!.rotation.multiplyLeft(q)
            rotation(-y * 0.1f, target!!.left, q)
            target!!.rotation.multiplyLeft(q)
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
        val target = cameraTransform.parent
        val c = cVector(cameraTransform.forward).scale(-y * 0.5f)
        cameraTransform.position.add(c)
    }
}