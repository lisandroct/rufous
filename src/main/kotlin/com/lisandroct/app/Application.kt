package com.lisandroct.app

import org.rufousengine.Rufous
import org.rufousengine.ecs.*
import org.rufousengine.ecs.components.Camera
import org.rufousengine.ecs.components.Model
import org.rufousengine.ecs.components.Transform
import org.rufousengine.editor.MeshesLoader
import org.rufousengine.editor.TextureLoader
import org.rufousengine.events.mouse.ScrollEvent
import org.rufousengine.files.Files
import org.rufousengine.graphics.*
import org.rufousengine.graphics.internal.Materials
import org.rufousengine.math.*
import org.rufousengine.system.GL

class Application {
    private val frankieMeshes = MeshesLoader.load(Files.local("models/frankie/frankie.obj"))
    private val tex0 = TextureLoader.load(Files.local("models/frankie/frankie-diffuse.png"))
    private val tex1 = TextureLoader.load(Files.local("models/frankie/env-diffuse.png"))
    private val mat0 = Materials.Gouraud().apply {
        texture = tex0
        lightPosition.set(6f, 0f, -3f)
    }
    private val mat1 = Materials.Gouraud().apply {
        texture = tex1
        lightPosition.set(6f, 0f, -3f)
    }

    private val omniLightGizmo = TextureLoader.load(Files.local("textures/omni-light-gizmo.png"))

    var accum = 0f

    init {
        val frankie = Entity()
        frankie.add<Transform>()
        val frankieModel = frankie.add<Model>()
        frankieModel?.apply {
            addMesh(frankieMeshes[0])
            addMesh(frankieMeshes[1])
            addMaterial(mat0)
            addMaterial(mat1)
            setMaterial(0, 0)
            setMaterial(1, 1)
        }


        val camera = Entity()
        val cameraTransform = camera.add<Transform>()
        cameraTransform?.apply {
            position.set(0f, 32.5f * 6f, -120f * 6f)
            rotation.set(15f, -180f, 0f)
        }
        camera.add<Camera>()

        ScrollEvent += ::scroll
    }

    /*
    fun render() {
        rotationY(accum, world)
        accum += 1f

        mat0.lightPosition.y = 5f + sin(accum * 5f) * 3f
        mat1.lightPosition.y = 5f + sin(accum * 5f) * 3f

        GL.setClearColor(0.2f, 0.3f, 0.3f, 1f)
        GL.clear()

        if(projectionDirty) {
            perspective(fieldOfView, aspectRatio, NEAR, FAR, projection)
            projectionDirty = false
        }
        Graphics.render(model, world, view, projection)

        Graphics.render(omniLightGizmo, mat0.lightPosition, view, projection, mat0.lightColor)
    }*/

    fun resize(width: Int, height: Int) {
        GL.setViewport(0, 0, width, height)
    }

    private fun scroll(x: Float, y: Float) {
        //fieldOfView = clamp(fieldOfView - y, 0.1f, 180f)
    }
}

fun main() {
    Rufous("Rufous Application", 1280, 720)
}