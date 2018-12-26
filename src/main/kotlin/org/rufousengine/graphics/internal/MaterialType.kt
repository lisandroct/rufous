package org.rufousengine.graphics.internal

import org.rufousengine.graphics.VertexAttribute
import java.lang.IllegalArgumentException

data class MaterialType(val name: String, val shadingModel: ShadingModel, val requires: Array<VertexAttribute>, val parameters: Array<GValue>, val fragment: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MaterialType

        if (name != other.name) return false
        if (shadingModel != other.shadingModel) return false
        if (!requires.contentEquals(other.requires)) return false
        if (!parameters.contentEquals(other.parameters)) return false
        if (fragment != other.fragment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + shadingModel.hashCode()
        result = 31 * result + requires.contentHashCode()
        result = 31 * result + parameters.contentHashCode()
        result = 31 * result + fragment.hashCode()
        return result
    }

    class Builder(val name: String) {
        class Requires: ArrayList<VertexAttribute>() {
            val position = VertexAttribute.Position
            val uv = VertexAttribute.UV
        }
        class Parameters: ArrayList<GValue>() {
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

        var shadingModel: ShadingModel? = null
        val requires = Requires()
        val parameters = Parameters()
        var fragment: String? = null

        fun build(): MaterialType {
            val shadingModel = shadingModel ?: throw IllegalArgumentException()
            val fragment = fragment ?: throw IllegalArgumentException()

            return MaterialType(name, shadingModel, requires.toTypedArray(), parameters.toTypedArray(), fragment)
        }
    }
}

fun material(name: String, block: MaterialType.Builder.() -> Unit) = MaterialType.Builder(name).apply(block).build()

fun MaterialType.Builder.requires(block: MaterialType.Builder.Requires.() -> Unit) {
    requires.addAll(MaterialType.Builder.Requires().apply(block))
}
fun MaterialType.Builder.Requires.vertexAttribute(type: VertexAttribute) = type

fun MaterialType.Builder.parameters(block: MaterialType.Builder.Parameters.() -> Unit) {
    parameters.addAll(MaterialType.Builder.Parameters().apply(block))
}
fun MaterialType.Builder.Parameters.parameter(name: String, type: GType) = GValue(name, type)