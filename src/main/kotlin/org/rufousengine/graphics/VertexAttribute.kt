package org.rufousengine.graphics

sealed class VertexAttribute(private val flag: Int, val size: Int) {
    operator fun invoke() = flag

    object Position: VertexAttribute(0b0001, 3)
    object Color: VertexAttribute(0b0010, 4)
    object Normal: VertexAttribute(0b0100, 3)
    object UV: VertexAttribute(0b1000, 2)
}

operator fun Int.contains(attribute: VertexAttribute) = attribute() and this != 0