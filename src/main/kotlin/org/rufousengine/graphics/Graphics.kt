package org.rufousengine.graphics

import org.lwjgl.opengl.ARBFramebufferObject.glGenerateMipmap
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL21
import org.lwjgl.opengl.GL21.GL_SRGB8_ALPHA8
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryStack

object Graphics {
    init {
        createCapabilities()
    }

    fun setViewport(x: Int, y: Int, width: Int, height: Int) {
        glViewport(x, y, width, height)
    }

    fun enableBlend() = glEnable(GL_BLEND)
    fun disableBlend() = glDisable(GL_BLEND)

    fun enableFaceCulling() = glEnable(GL_CULL_FACE)
    fun disableFaceCulling() = glDisable(GL_CULL_FACE)

    fun enableMultisample() = glEnable(GL_MULTISAMPLE)
    fun disableMultisample() = glDisable(GL_MULTISAMPLE)

    fun createTexture() = glGenTextures()
    fun deleteTexture(id: Int) = glDeleteTextures(id)
    fun specifyTexture(id: Int, width: Int, height: Int, pixels: ByteArray, sRGB: Boolean) {
        MemoryStack.stackPush().use { stack ->
            val buffer = stack.malloc(pixels.size)
            buffer.put(pixels)
            buffer.flip()

            bindTexture(id)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

            glTexImage2D(GL_TEXTURE_2D, 0, if (sRGB) GL_SRGB8_ALPHA8 else GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
            glGenerateMipmap(GL_TEXTURE_2D)
        }
    }
    fun bindTexture(id: Int, slot: Int = 0) {
        glActiveTexture(GL_TEXTURE0 + slot)
        glBindTexture(GL_TEXTURE_2D, id)
    }
    /*
    fun unbindTexture(slot: Int = 0) {
        glActiveTexture(GL_TEXTURE0 + slot)
        glBindTexture(GL_TEXTURE_2D, 0)
    }
    */
}