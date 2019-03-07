package org.rufousengine.graphics.internal

import org.rufousengine.graphics.VertexAttribute

class ShadingModel(builder: ShadingModel.() -> Unit) {
    val properties = MaterialType.GValuesList()
    var glsl = ""

    init {
        apply(builder)
    }
}

abstract class MaterialType(val model: ShadingModel, builder: MaterialType.() -> Unit) {
    val requires = Requires()
    val parameters = GValuesList()
    val vertexShader = Vertex()
    val fragmentShader = Fragment()

    init {
        apply(builder)
    }

    class Requires : Iterable<VertexAttribute> {
        private val attributes = mutableListOf<VertexAttribute>()
        private val locations = hashSetOf<Int>()

        val position
            get() = position()
        val color
            get() = color()
        val normal
            get() = normal()
        val uv
            get() = uv()

        init {
            position()
        }

        private fun position() = add(VertexAttribute.position)
        private fun color() = add(VertexAttribute.color)
        private fun normal() = add(VertexAttribute.normal)
        private fun uv() = add(VertexAttribute.uv)

        private fun add(attribute: VertexAttribute) {
            if(attribute.location in locations) {
                return
            }

            attributes.add(attribute)
            locations.add(attribute.location)
        }

        override fun iterator() = attributes.iterator()

        operator fun invoke(block: Requires.() -> Unit) = apply(block)
    }

    class Vertex {
        var glsl = ""

        operator fun invoke(block: Vertex.() -> Unit) = apply(block)
    }

    class Fragment {
        val inputs = GValuesList()
        var glsl = ""

        operator fun invoke(block: Fragment.() -> Unit) = apply(block)
    }

    class GValuesList : Iterable<GValue> {
        private val values = mutableListOf<GValue>()

        fun boolean(name: String) = add(name, GType.Boolean)
        fun int(name: String) = add(name, GType.Int)
        fun float(name: String) = add(name, GType.Float)
        fun vector2(name: String) = add(name, GType.Vector2)
        fun vector3(name: String) = add(name, GType.Vector3)
        fun vector4(name: String) = add(name, GType.Vector4)
        fun point2D(name: String) = add(name, GType.Point2D)
        fun point3D(name: String) = add(name, GType.Point3D)
        fun color(name: String) = add(name, GType.Color)
        fun matrix2(name: String) = add(name, GType.Matrix2)
        fun matrix3(name: String) = add(name, GType.Matrix3)
        fun matrix4(name: String) = add(name, GType.Matrix4)
        fun texture(name: String) = add(name, GType.Texture)

        private fun add(name: String, type: GType) = values.add(GValue(name, type))

        fun isEmpty() = values.isEmpty()
        fun isNotEmpty() = values.isNotEmpty()
        override fun iterator() = values.iterator()

        operator fun invoke(block: GValuesList.() -> Unit) = apply(block)
    }
}