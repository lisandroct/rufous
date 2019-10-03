package org.rufousengine.graphics

import org.rufousengine.Disposable
import org.rufousengine.ManagedResource
import org.rufousengine.system.GL

class Mesh(vertices: FloatArray, indices: IntArray, vertexLayout: Array<VertexAttribute>) : ManagedResource(), Disposable {
    val vao = GL.generateVertexArray()
    private val vbo = GL.generateBuffer()
    private val ibo = GL.generateBuffer()

    val count = indices.size

    init {
        GL.bindVertexArray(vao)

        GL.bindVertexBuffer(vbo)
        GL.setVertexBufferData(vertices)

        var stride = 0
        for(vertexAttribute in vertexLayout) {
            stride += vertexAttribute.size
        }

        var pointer = 0L
        for(vertexAttribute in vertexLayout) {
            enableAttribute(vertexAttribute, stride, pointer)
            pointer += vertexAttribute.size
        }

        GL.bindIndexBuffer(ibo)
        GL.setIndexBufferData(indices)

        GL.unbindVertexBuffer()
        GL.unbindVertexArray()
    }

    private fun enableAttribute(attribute: VertexAttribute, stride: Int, pointer: Long) {
        GL.enableVertexAttribute(attribute.location)
        GL.describeVertexAttribute(attribute.location, attribute.size, stride, pointer)
    }

    override fun dispose() {
        GL.destroyVertexArray(vao)
        GL.destroyBuffer(vbo)
        GL.destroyBuffer(ibo)
    }
}