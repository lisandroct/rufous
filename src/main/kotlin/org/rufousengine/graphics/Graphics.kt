@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.graphics

import org.rufousengine.math.Matrix4
import org.rufousengine.system.GL

object Graphics {
    fun draw(mesh: Mesh) {
        GL.bindVertexArray(mesh.vao)
        GL.drawElements(mesh.count)
        GL.unbindVertexArray()
    }

    fun render(model: Model, world: Matrix4, view: Matrix4, projection: Matrix4) {
        for(mesh in model.meshes) {
            val material = model.getMaterial(mesh) ?: Materials.default

            GL.useProgram(material.program)
            material.setTransformParameters(world, view, projection)
            material.setParameters()

            draw(mesh)
        }
    }
}