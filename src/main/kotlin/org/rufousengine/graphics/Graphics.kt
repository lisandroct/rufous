package org.rufousengine.graphics

import org.rufousengine.graphics.internal.Colors
import org.rufousengine.graphics.internal.Materials
import org.rufousengine.math.ColorFloat
import org.rufousengine.math.Matrix4
import org.rufousengine.math.Point3D
import org.rufousengine.math.translation
import org.rufousengine.system.GL

object Graphics {
    private val debugQuad = Mesh(
            floatArrayOf(
                    -0.5f, 0.5f, 0.0f, 0.0f, 0.0f,
                    -0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
                    0.5f, -0.5f, 0.0f, 1.0f, 1.0f,
                    0.5f, 0.5f, 0.0f, 1.0f, 0.0f
            ),
            intArrayOf(
                    0, 3, 1,
                    1, 3, 2
            ),
            VertexAttribute.mask(VertexAttribute.position, VertexAttribute.uv)
    )

    private val errorMaterial = Materials.Error()
    private val debugQuadMaterial = Materials.DebugQuad()
    private val debugQuadTransform = Matrix4()

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

    fun render(texture: Texture, position: Point3D, view: Matrix4, projection: Matrix4, tint: ColorFloat = Colors.white) {
        val aspectRatio = texture.width / texture.height.toFloat()

        translation(position, debugQuadTransform)

        debugQuadMaterial.texture = texture
        debugQuadMaterial.tint.set(tint)

        GL.useProgram(debugQuadMaterial.program)
        debugQuadMaterial.setTransformParameters(debugQuadTransform, view, projection)
        debugQuadMaterial.setParameters()

        draw(debugQuad)
    }
}