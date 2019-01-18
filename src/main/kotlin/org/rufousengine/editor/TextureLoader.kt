package org.rufousengine.editor

import org.lwjgl.stb.STBImage.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.rufousengine.files.File
import org.rufousengine.graphics.Texture
import java.nio.ByteBuffer

object TextureLoader {
    fun load(file: File, sRGB: Boolean = true) : Texture {
        if(!file.exists || !file.isFile) {
            throw Exception("Error loading function")
        }

        val fileBytes = file.readBytes()
        val buffer = MemoryUtil.memAlloc(fileBytes.size)
        buffer.put(fileBytes)
        buffer.flip()

        var width = 0
        var height = 0
        val pixels = mutableListOf<Byte>()

        MemoryStack.stackPush().use { stack ->
            val w = stack.mallocInt(1)
            val h = stack.mallocInt(1)
            val comp = stack.mallocInt(1)

            /* Load image */
            stbi_set_flip_vertically_on_load(false)

            val image = stbi_load_from_memory(buffer, w, h, comp, 4) ?: throw RuntimeException(stbi_failure_reason())

            /* Get width and height of image */
            width = w[0]
            height = h[0]

            while(image.remaining() > 0) {
                pixels.add(image.get())
            }

            image.rewind()

            stbi_image_free(image)
        }

        MemoryUtil.memFree(buffer)

        return Texture(width, height, pixels.toByteArray(), sRGB)
    }
}