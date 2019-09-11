package org.rufousengine.editor

import org.lwjgl.stb.STBImage.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.rufousengine.files.File
import org.rufousengine.graphics.FilterMode
import org.rufousengine.graphics.Texture
import org.rufousengine.graphics.TextureFormat

object TextureLoader {
    init {
        stbi_set_flip_vertically_on_load(false)
    }

    fun load(file: File, alpha: Boolean, sRGB: Boolean) : Texture {
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
            val image = stbi_load_from_memory(buffer, w, h, comp, if(alpha) 4 else 3) ?: throw RuntimeException(stbi_failure_reason())

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

        val format = if(alpha) {
            if(sRGB) {
                TextureFormat.sRGBA
            } else {
                TextureFormat.RGBA
            }
        } else {
            if(sRGB) {
                TextureFormat.sRGB
            } else {
                TextureFormat.RGB
            }
        }

        return Texture(width, height, pixels.toByteArray(), FilterMode.Trilinear, format)
    }
}