package org.rufousengine.graphics

class Texture(width: Int, height: Int, pixels: ByteArray, sRGB: Boolean = true) {
    val id = Graphics.createTexture()
    val width = width
    val height = height
    val sRGB = sRGB

    init {
        Graphics.specifyTexture(id, width, height, pixels, sRGB)
    }

    fun bind(slot: Int = 0) = Graphics.bindTexture(id, slot)

    fun destroy() = Graphics.deleteTexture(id)
}