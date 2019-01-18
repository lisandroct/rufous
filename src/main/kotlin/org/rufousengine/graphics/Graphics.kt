package org.rufousengine.graphics

import org.rufousengine.graphics.internal.Materials
import org.rufousengine.math.Matrix4
import org.rufousengine.system.GL

object Graphics {
    val errorMaterial = Materials.Error()

    fun draw(mesh: Mesh) {
        GL.bindVertexArray(mesh.vao)
        GL.drawElements(mesh.count)
        GL.unbindVertexArray()
    }

    fun render(model: Model, world: Matrix4, view: Matrix4, projection: Matrix4) {
        for(mesh in model.meshes) {
            val material = model.getMaterial(mesh) ?: errorMaterial

            GL.useProgram(material.program)
            material.setTransformParameters(world, view, projection)
            material.setParameters()

            draw(mesh)
        }
    }
}