package org.rufousengine.editor

import org.rufousengine.App
import org.rufousengine.Rufous
import org.rufousengine.components.Camera
import org.rufousengine.components.Model
import org.rufousengine.components.Transform
import org.rufousengine.ecs.*
import org.rufousengine.editor.components.EditorOnly
import org.rufousengine.editor.components.Grid
import org.rufousengine.editor.systems.GridSystem
import org.rufousengine.editor.systems.ViewportSystem
import org.rufousengine.files.Files
import org.rufousengine.graphics.Mesh
import org.rufousengine.graphics.VertexAttribute
import org.rufousengine.graphics.internal.Materials
import org.rufousengine.math.*
import org.rufousengine.system.GL

class Editor : App("Editor") {
    init {
        World.subscribeSystem(GridSystem)
        World.subscribeSystem(ViewportSystem)

        /*
        val frankie = Entity()
        frankie.add<Transform>()?.apply {
            rotation = ToQuaternion(Vector3(-2f, 45f, -2f))
            position = Point3D(2f, 0f, 0f)
        }
        frankie.add<Model>()?.apply {
            val frankieMeshes = MeshesLoader.load(Files.local("models/frankie/frankie.obj"))
            val tex0 = TextureLoader.load(Files.local("models/frankie/frankie-diffuse.png"), alpha = false, sRGB = true)
            val tex1 = TextureLoader.load(Files.local("models/frankie/env-diffuse.png"), alpha = false, sRGB = true)
            val mat0 = Materials.BlinnPhong().apply {
                texture = tex0
            }
            val mat1 = Materials.BlinnPhong().apply {
                texture = tex1
            }

            addMesh(frankieMeshes[0])
            addMesh(frankieMeshes[1])
            addMaterial(mat0)
            addMaterial(mat1)
            setMaterial(0, 0)
            setMaterial(1, 1)
        }
         */

        val cameraParent = Entity()
        val cameraParentTransform = cameraParent.add<Transform>()
        cameraParent.add<EditorOnly>()
        val camera = Entity()
        camera.add<Transform>()?.apply {
            position = Point3D(0f, 0f, 20f)
            parent = cameraParentTransform
        }
        camera.add<Camera>()
        camera.add<EditorOnly>()
        cameraParentTransform?.apply {
            position = Point3D(0f, 5f, 0f)
            //rotation = Quaternion(45f, Vector3.x) * Quaternion(45f, Vector3.y)
        }
        val grid = Entity()
        grid.add<Transform>()
        grid.add<Grid>()?.apply {
            radius = 50f
        }
        grid.add<Model>()?.apply {
            val quad = Mesh(
                    floatArrayOf(
                            -1f, 0f, 1f,
                            -1f, 0f, -1f,
                            1f, 0f, -1f,
                            1f, 0f, 1f
                    ),
                    intArrayOf(
                            0, 3, 1,
                            1, 3, 2
                    ),
                    VertexAttribute.mask(VertexAttribute.position)
            )
            val material = Materials.Grid()

            addMesh(quad)
            addMaterial(material)
            setMaterial(0, 0)
        }
        grid.add<EditorOnly>()
    }

    override fun resize(width: Int, height: Int) {
        GL.setViewport(0, 0, width, height)
    }
}

fun main() {
    Rufous(Editor::class, 1280, 720)
}