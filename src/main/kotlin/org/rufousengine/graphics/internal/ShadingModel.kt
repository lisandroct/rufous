package org.rufousengine.graphics.internal

data class ShadingModel(val name: String, val properties: Array<GValue>, val equation: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShadingModel

        if (!properties.contentEquals(other.properties)) return false
        if (equation != other.equation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = properties.contentHashCode()
        result = 31 * result + equation.hashCode()
        return result
    }

    class Builder(val name: String) {
        class Properties: ArrayList<GValue>() {
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
        }

        val properties = Properties()
        var equation: String? = null

        fun build(): ShadingModel {
            val equation = equation ?: throw IllegalArgumentException("An equation is required.")

            return ShadingModel(name, properties.toTypedArray(), equation)
        }
    }
}

fun shadingModel(name: String, block: ShadingModel.Builder.() -> Unit) = ShadingModel.Builder(name).apply(block).build()

fun ShadingModel.Builder.properties(block: ShadingModel.Builder.Properties.() -> Unit) {
    properties.addAll(ShadingModel.Builder.Properties().apply(block))
}

fun ShadingModel.Builder.Properties.property(name: String, type: GType) = GValue(name, type)