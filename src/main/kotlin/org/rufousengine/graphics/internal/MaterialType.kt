package org.rufousengine.graphics.internal

import org.rufousengine.graphics.VertexAttribute

abstract class MaterialType(builder: MaterialType.() -> Unit) {
    var model: ShadingModel? = null
    val requires = Requires()
    val parameters = Parameters()
    var fragment: String? = null

    init {
        apply(builder)
    }

    class Requires: ArrayList<VertexAttribute>() {
        val position = VertexAttribute.position
        val color = VertexAttribute.color
        val normal = VertexAttribute.normal
        val uv = VertexAttribute.uv

        operator fun invoke(block: Requires.() -> Unit) {
            addAll(Requires().apply(block))
        }

        fun vertexAttribute(type: VertexAttribute) = add(type)
    }

    class Parameters: ArrayList<GValue>() {
        val boolean = GType.Boolean
        val int = GType.Int
        val float = GType.Float
        val vector2 = GType.Vector2
        val vector3 = GType.Vector3
        val vector4 = GType.Vector4
        val point2D = GType.Point2D
        val point3D = GType.Point3D
        val color = GType.Color
        val matrix2 = GType.Matrix2
        val matrix3 = GType.Matrix3
        val matrix4 = GType.Matrix4
        val texture = GType.Texture

        operator fun invoke(block: Parameters.() -> Unit) = addAll(Parameters().apply(block))

        fun parameter(name: String, type: GType) = add(GValue(name, type))
    }
}