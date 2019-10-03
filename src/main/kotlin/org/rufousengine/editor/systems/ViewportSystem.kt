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
            val target = cameraTransform.parent!!
            var c = target.right * (-x * 0.01f)
            //cameraTransform.position += c
            target.position += c
            c = target.up * (y * 0.01f)
            //cameraTransform.position += c
            target.position += c
        } else if(rightClick) {
            val cameraTransform = cameras[0].getUnsafe<Transform>()
            val target = cameraTransform.parent
            var q = Quaternion(-x * 0.1f, Vector3(0f, 1f, 0f))
            target!!.rotation = q * target.rotation
            q = Quaternion(-y * 0.1f, target.right)
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
        val target = cameraTransform.parent
        val d = distance(cameraTransform.position, target!!.position)
        //val c = cameraTransform.forward * (-y * 0.5f)
        cameraTransform.position += Vector3.z * (-y * d * 0.05f)
    }
}