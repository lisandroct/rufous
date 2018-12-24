package org.rufousengine.graphics

import org.rufousengine.system.GL

class Mesh(vertices: FloatArray, indices: IntArray, vertexLayout: Int) {
    val vao = GL.generateVertexArray()
    private val vbo = GL.generateBuffer()
    private val ibo = GL.generateBuffer()

    val count = indices.size

    init {
        GL.bindVertexArray(vao)

        GL.bindVertexBuffer(vbo)
        GL.setVertexBufferData(vertices)

        val positions = VertexAttribute.Position in vertexLayout
        val colors = VertexAttribute.Color in vertexLayout
        val normals = VertexAttribute.Normal in vertexLayout
        val uvs = VertexAttribute.UV in vertexLayout

        var stride = 0
        if(positions) {
            stride += VertexAttribute.Position.size
        }
        if(colors) {
            stride += VertexAttribute.Color.size
        }
        if(normals) {
            stride += VertexAttribute.Normal.size
        }
        if(uvs) {
            stride += VertexAttribute.UV.size
        }

        var pointer = 0L
        if(positions) {
            pointer += enableAttribute(0, VertexAttribute.Position.size, stride, pointer)
        }
        if(colors) {
            pointer += enableAttribute(1, VertexAttribute.Color.size, stride, pointer)
        }
        if(normals) {
            pointer += enableAttribute(2, VertexAttribute.Normal.size, stride, pointer)
        }
        if(uvs) {
            pointer += enableAttribute(3, VertexAttribute.UV.size, stride, pointer)
        }

        GL.bindIndexBuffer(ibo)
        GL.setIndexBufferData(indices)

        GL.unbindVertexBuffer()
        GL.unbindVertexArray()
    }

    fun destroy() {
        GL.destroyVertexArray(vao)
        GL.destroyBuffer(vbo)
        GL.destroyBuffer(ibo)
    }

    private fun enableAttribute(index: Int, size: Int, stride: Int, pointer: Long): Int {
        GL.enableVertexAttribute(index)
        GL.describeVertexAttribute(index, size, stride, pointer)

        return size
    }
}