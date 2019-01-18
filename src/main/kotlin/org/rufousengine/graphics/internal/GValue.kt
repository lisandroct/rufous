package org.rufousengine.graphics.internal

enum class GType { Boolean, Int, Float, Vector2, Vector3, Vector4, Point2D, Point3D, Color, Matrix2, Matrix3, Matrix4, Texture }

data class GValue(val name: String, val type: GType)