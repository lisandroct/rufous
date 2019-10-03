package org.rufousengine.editor.systems

import org.rufousengine.components.Camera
import org.rufousengine.components.Model
import org.rufousengine.components.Transform
import org.rufousengine.ecs.*
import org.rufousengine.editor.components.EditorOnly
import org.rufousengine.editor.components.Grid
import org.rufousengine.graphics.internal.Materials
import org.rufousengine.math.Point3D
import org.rufousengine.math.Trigonometry.tan
import org.rufousengine.math.Vector3

object GridSystem : System(0) {
    private val cameras = Family(arrayOf(Transform::class, Camera::class, EditorOnly::class))
    private val grids = Family(arrayOf(Transform::class, Grid::class, Model::class, EditorOnly::class))

    init {
        cameras.subscribe()
        grids.subscribe()
    }

    override fun update() {
        val camera = cameras[0]
        val entity = grids[0]
        val grid = entity.getUnsafe<Grid>()
        val material =  entity.getUnsafe<Model>().materials[0] as Materials.Grid

        val cameraTransform = camera.getUnsafe<Transform>()
        val cameraPosition = cameraTransform.worldPosition
        val cameraForward = cameraTransform.forward
        val gridTransform = entity.getUnsafe<Transform>()

        if(cameraForward.y <= 0) {
            gridTransform.position = Point3D(cameraTransform.position.x, 0f, cameraTransform.position.z)
        } else {
            val t = -cameraPosition.y / cameraForward.y

            gridTransform.position = Point3D(cameraPosition.x + t * cameraForward.x, 0f, cameraPosition.z + t * cameraForward.z)
        }
        gridTransform.scale = Vector3(grid.radius * 2f, 1f, grid.radius * 2f)

        material.center = gridTransform.position
        material.tfov = tan(camera.getUnsafe<Camera>().fieldOfView * 0.5f)
        material.cameraPosition = cameraTransform.worldPosition
        material.radius = grid.radius
        material.distance = grid.distance
        material.size = grid.size
    }
}