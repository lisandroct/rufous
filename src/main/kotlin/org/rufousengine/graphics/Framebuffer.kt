package org.rufousengine.graphics

import org.rufousengine.Disposable
import org.rufousengine.ManagedResource
import org.rufousengine.system.GL

class Framebuffer(val width: Int, val height: Int, attachmentDefinitions: Array<AttachmentDefinition>) : ManagedResource(), Disposable {
    val name = GL.generateFramebuffer()

    val attachments: List<Attachment>

    init {
        GL.bindFrameBuffer(name)

        val attachments = mutableListOf<Attachment>()
        var colorCount = 0
        for(definition in attachmentDefinitions) {
            val location = if(definition.format != TextureFormat.DepthStencil) GL.FramebufferAttachment.values()[colorCount++] else GL.FramebufferAttachment.DepthStencil
            val attachment = Attachment(width, height, definition.format, definition.readable, definition.filterMode)
            if(definition.readable) {
                GL.attachTextureToFramebuffer(attachment.name, location)
            } else {
                GL.attachRenderbufferToFramebuffer(attachment.name, location)
            }
            attachments.add(attachment)
        }

        this.attachments = attachments

        if(!GL.isFramebufferComplete()) {
            GL.unbindFrameBuffer()
            throw IllegalStateException("Framebuffer is not complete")
        }

        GL.unbindFrameBuffer()
    }

    override fun dispose() {
        GL.deleteFramebuffer(name)
    }
}

data class AttachmentDefinition(val format: TextureFormat, val readable: Boolean, val filterMode: FilterMode = FilterMode.Point)

class Attachment(width: Int, height: Int, val format: TextureFormat, val readable: Boolean, filterMode: FilterMode = FilterMode.Point) {
    val name: Int
    val texture: Texture?
    val renderbuffer: Renderbuffer?

    init {
        if(readable) {
            texture = Texture(width, height, null, filterMode, format)
            renderbuffer = null

            name = texture.name
        } else {
            texture = null
            renderbuffer = Renderbuffer(width, height, format)

            name = renderbuffer.name
        }
    }
}