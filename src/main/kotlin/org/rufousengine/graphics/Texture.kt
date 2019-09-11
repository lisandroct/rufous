package org.rufousengine.graphics

import org.rufousengine.Disposable
import org.rufousengine.ManagedResource
import org.rufousengine.system.GL

class Texture(val width: Int, val height: Int, pixels: ByteArray?, val filter: FilterMode, val format: TextureFormat) : ManagedResource(), Disposable {
    val name = GL.generateTexture()

    init {
        GL.bindTexture(name, 0)

        GL.setTextureWrap(GL.TextureWrap.Repeat, GL.TextureWrap.Repeat)

        val mipmapping = filter == FilterMode.Trilinear
        val min: GL.TextureFilter
        val mag: GL.TextureFilter
        when(filter) {
            FilterMode.Point -> {
                min = GL.TextureFilter.Nearest
                mag = GL.TextureFilter.Nearest
            }
            FilterMode.Bilinear -> {
                min = GL.TextureFilter.Linear
                mag = GL.TextureFilter.Linear
            }
            FilterMode.Trilinear -> {
                min = GL.TextureFilter.MipmapLinearLinear
                mag = GL.TextureFilter.Linear
            }
        }

        GL.setTextureFilter(min, mag)
        GL.setAutomaticMipmap(mipmapping)

        val format = when(format) {
            TextureFormat.RGB -> GL.TextureFormat.RGB
            TextureFormat.sRGB -> GL.TextureFormat.SRGB
            TextureFormat.RGBA -> GL.TextureFormat.RGBA
            TextureFormat.sRGBA -> GL.TextureFormat.SRGBA
            TextureFormat.DepthStencil -> GL.TextureFormat.DepthStencil
        }

        GL.generateTextureImage(width, height, pixels, format)

        if(mipmapping) {
            GL.generateMipmaps()
        }

        GL.unbindTexture(0)
    }

    override fun dispose() {
        GL.deleteTexture(name)
    }
}

enum class FilterMode { Point, Bilinear, Trilinear }
enum class TextureFormat { RGB, sRGB, RGBA, sRGBA, DepthStencil }