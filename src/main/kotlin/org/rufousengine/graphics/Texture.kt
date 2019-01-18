package org.rufousengine.graphics

import org.lwjgl.opengl.ARBFramebufferObject
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL21.GL_SRGB8_ALPHA8
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.rufousengine.system.GL

class Texture(width: Int, height: Int, pixels: ByteArray, sRGB: Boolean = true) {
    val id = GL.generateTexture()

    init {
        GL.bindTexture(id, 0)

        val buffer = MemoryUtil.memAlloc(pixels.size)
        buffer.put(pixels)
        buffer.flip()

        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_REPEAT)
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_REPEAT)
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR_MIPMAP_LINEAR)
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR)

        GL30.glTexImage2D(GL_TEXTURE_2D, 0, if (sRGB) GL_SRGB8_ALPHA8 else GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
        ARBFramebufferObject.glGenerateMipmap(GL30.GL_TEXTURE_2D)

        MemoryUtil.memFree(buffer)

        GL.unbindTexture(0)
    }

    fun destroy() {
        GL.deleteTexture(id)
    }

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        if(other !is Texture) {
            return false
        }

        return id == other.id
    }
}