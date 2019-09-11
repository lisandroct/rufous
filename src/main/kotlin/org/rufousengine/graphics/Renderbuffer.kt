package org.rufousengine.graphics

import org.rufousengine.Disposable
import org.rufousengine.ManagedResource
import org.rufousengine.system.GL

class Renderbuffer(val width: Int, val height: Int, val format: TextureFormat): ManagedResource(), Disposable {
    val name = GL.generateRenderbuffer()

    init {
        GL.bindRenderbuffer(name)

        val format = when(format) {
            TextureFormat.RGB -> GL.TextureFormat.RGB
            TextureFormat.sRGB -> GL.TextureFormat.SRGB
            TextureFormat.RGBA -> GL.TextureFormat.RGBA
            TextureFormat.sRGBA -> GL.TextureFormat.SRGBA
            TextureFormat.DepthStencil -> GL.TextureFormat.DepthStencil
        }

        GL.generateRenderbufferStorage(width, height, format)

        GL.unbindRenderbuffer()
    }

    override fun dispose() {
        GL.deleteRenderbuffer(name)
    }
}