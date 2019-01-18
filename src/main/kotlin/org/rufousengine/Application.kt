package org.rufousengine

import org.rufousengine.editor.MeshesLoader
import org.rufousengine.editor.TextureLoader
import org.rufousengine.files.Files
import org.rufousengine.graphics.*
import org.rufousengine.graphics.internal.Materials
import org.rufousengine.math.*
import org.rufousengine.system.GL

private const val NEAR = 0.1f
private const val FAR = 1000f

class Application {
    private val meshes = MeshesLoader.load(Files.local("models/frankie/frankie.obj"))
    private val tex0 = TextureLoader.load(Files.local("models/frankie/frankie-diffuse.png"))
    private val tex1 = TextureLoader.load(Files.local("models/frankie/env-diffuse.png"))
    private val mat0 = Materials.Test().apply {
        texture = tex0
        textureColor.set(0.7f, 0.3f, 0.5f, 1.0f)
    }
    private val mat1 = Materials.Unlit().apply { texture = tex1 }

    private val model = Model(meshes, arrayOf(mat0, mat1))

    private val world = Matrix4()
    private val view = (Translation(Point3D(0f, 32.5f * 6f, -120f * 6f)) * RotationX(15f) * RotationY(180f)).inverse()
    //private val view = (Translation(Point3D(0f, 0f, 120f * 5))).inverse()

    private var projectionDirty = true
    private var width = 1
        set(value) {
            field = value
            projectionDirty = true
        }
    private var height = 1
        set(value) {
            field = value
            projectionDirty = true
        }
    private val aspectRatio
        get() = width / height.toFloat()
    private var fieldOfView = 120f
        set(value) {
            field = value
            projectionDirty = true
        }

    private val projection = Matrix4()

    var accum = 0f
    var smallAccum = 0f

    init {
        model.setMaterial(meshes[0], mat0)
        model.setMaterial(meshes[1], mat1)
    }

    fun render() {
        rotationY(accum, world)
        accum += 1f
        GL.setClearColor(0.2f, 0.3f, 0.3f, 1f)
        GL.clear()

        smallAccum += 0.01f
        mat0.time = smallAccum

        if(projectionDirty) {
            perspective(fieldOfView, aspectRatio, NEAR, FAR, projection)
            projectionDirty = false
        }
        Graphics.render(model, world, view, projection)
    }

    fun resize(width: Int, height: Int) {
        this.width = width
        this.height = height

        GL.setViewport(0, 0, width, height)
    }

    fun scroll(x: Float, y: Float) {
        fieldOfView = clamp(fieldOfView - y, 0.1f, 180f)
    }

    fun destroy() {
        mat0.destroy()
        mat1.destroy()
        tex0.destroy()
        tex1.destroy()
        for(mesh in meshes) {
            mesh.destroy()
        }
    }
}

fun main(args: Array<String>) {
    Rufous("Rufous Application", 1280, 720)
}