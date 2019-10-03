package org.rufousengine.graphics

data class VertexAttribute(val location: Int, val name: String, val size: Int) {
    companion object {
        val position = VertexAttribute(0, "position", 3)
        val color = VertexAttribute(1, "color", 4)
        val normal = VertexAttribute(2, "normal", 3)
        val uv = VertexAttribute(3, "uv", 2)
    }
}