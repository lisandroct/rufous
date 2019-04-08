package org.rufousengine.graphics

data class VertexAttribute(val location: Int, val name: String, val size: Int, private val flag: Int) {
    companion object {
        val position = VertexAttribute(0, "position", 3, 0b0001)
        val color = VertexAttribute(1, "color", 4, 0b0010)
        val normal = VertexAttribute(2, "normal", 3, 0b0100)
        val uv = VertexAttribute(3, "uv", 2, 0b1000)

        val all = listOf(position, color, normal, uv)

        fun mask(vararg vertexAttributes: VertexAttribute): Int {
            var mask = 0
            for(attribute in vertexAttributes) {
                mask = mask or attribute.flag
            }

            return mask
        }

        fun mask(vertexAttributes: Iterable<VertexAttribute>): Int {
            var mask = 0
            for(attribute in vertexAttributes) {
                mask = mask or attribute.flag
            }

            return mask
        }
    }

    fun isIn(mask: Int) = flag and mask != 0
}