package org.rufousengine.graphics

import org.rufousengine.Disposable
import org.rufousengine.ManagedResource
import org.rufousengine.system.GL

class Texture(val width: Int, val height: Int, pixels: ByteArray, sRGB: Boolean = true) : ManagedResource(), Disposable {
    val name = GL.generateTexture()

    init {
        GL.bindTexture(name, 0)

        GL.setTextureWrap(GL.TextureWrap.Repeat, GL.TextureWrap.Repeat)
        GL.setTextureFilter(GL.TextureFilter.MipmapLinearLinear, GL.TextureFilter.Linear)

        GL.generateTextureImage(width, height, pixels, if(sRGB) GL.TextureFormat.SRGBA else GL.TextureFormat.RGBA)
        GL.generateMipmaps()

        GL.unbindTexture(0)
    }

    override fun dispose() {
        GL.deleteTexture(name)
    }
}