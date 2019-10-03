package com.lisandroct.app

import com.lisandroct.app.components.Rotator
import com.lisandroct.app.components.Sinusoidal
import com.lisandroct.app.systems.RotatorSystem
import com.lisandroct.app.systems.SinusoidalSystem
import org.rufousengine.App
import org.rufousengine.Rufous
import org.rufousengine.ecs.*
import org.rufousengine.components.Camera
import org.rufousengine.components.Model
import org.rufousengine.components.OmniLight
import org.rufousengine.components.Transform
import org.rufousengine.editor.MeshesLoader
import org.rufousengine.editor.TextureLoader
import org.rufousengine.events.mouse.ScrollEvent
import org.rufousengine.files.Files
import org.rufousengine.graphics.internal.Materials
import org.rufousengine.math.*
import org.rufousengine.system.GL
import org.rufousengine.systems.DebugSystem
import org.rufousengine.systems.RenderingSystem

class Application : App("Rufous Application") {
    private val frankieMeshes = MeshesLoader.load(Files.local("models/frankie/frankie.obj"))
    private val tex0 = TextureLoader.load(Files.local("models/frankie/frankie-diffuse.png"), alpha = false, sRGB = true)
    private val tex1 = TextureLoader.load(Files.local("models/frankie/env-diffuse.png"), alpha = false, sRGB = true)
    private val mat0 = Materials.BlinnPhong().apply {
        texture = tex0
    }
    private val mat1 = Materials.BlinnPhong().apply {
        texture = tex1
    }

    init {
        World.subscribeSystem(RenderingSystem)
        World.subscribeSystem(DebugSystem)
        World.subscribeSystem(RotatorSystem)
        World.subscribeSystem(SinusoidalSystem)

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

        val cameraHolder = Entity()
        cameraHolder.add<Transform>()
        cameraHolder.add<Rotator>()

        val camera = Entity()
        val cameraTransform = camera.add<Transform>()
        cameraTransform?.apply {
            position = Point3D(0f, 197f, 720f)
            rotation = Quaternion(-15f, Vector3.y)
            parent = cameraHolder.get()
        }
        val cam = camera.add<Camera>()
        cam?.apply {
            near = 700f
            far = 760f
        }

        val omniLight = Entity()
        val omniLightTransform = omniLight.add<Transform>()
        omniLightTransform?.apply {
            position = Point3D(6f, 0f, -3f)
        }
        omniLight.add<OmniLight>()
        omniLight.add<Sinusoidal>()?.apply {
            speed = 3f
            amplitude = 10f
        }

        if(cam != null) {
            ScrollEvent += { _, y ->
                cam.fieldOfView = clamp(cam.fieldOfView - y, 1f, 179.5f)
            }
        }
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

    override fun resize(width: Int, height: Int) {
        GL.setViewport(0, 0, width, height)
    }
}

fun main() {
    Rufous(Application::class, 1280, 720)
}