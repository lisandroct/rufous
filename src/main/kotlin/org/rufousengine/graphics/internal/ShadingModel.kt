package org.rufousengine.graphics.internal

class ShadingModel(builder: ShadingModel.() -> Unit) {
    val properties = Properties()
    var function: String? = null

    init {
        apply(builder)
    }

    class Properties: ArrayList<GValue>() {
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

        operator fun invoke(block: Properties.() -> Unit) = addAll(Properties().apply(block))

        fun property(name: String, type: GType) = add(GValue(name, type))
    }
}