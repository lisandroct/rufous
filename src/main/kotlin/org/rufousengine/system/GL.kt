@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.system

import org.lwjgl.opengl.*
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL32.*
import org.lwjgl.system.MemoryUtil
import org.lwjgl.system.MemoryUtil.NULL
import org.rufousengine.math.*
import kotlin.IllegalArgumentException

object GL {
    init {
        createCapabilities()
    }

    inline fun setViewport(x: Int, y: Int, width: Int, height: Int) = glViewport(x, y, width, height)

    inline fun setClearColor(r: Float, g: Float, b: Float, a: Float) = glClearColor(r, g, b, a)
    inline fun clear(color: Boolean = true, depth: Boolean = true) {
        val flags = if(color && !depth) GL11.GL_COLOR_BUFFER_BIT else if(!color && depth) GL11.GL_DEPTH_BUFFER_BIT else if(color && depth) GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT else 0
        glClear(flags)
    }

    inline fun enableBlend() = glEnable(GL_BLEND)
    inline fun disableBlend() = glDisable(GL_BLEND)

    inline fun enableDepthTest() = glEnable(GL_DEPTH_TEST)
    inline fun disableDepthTest() = glDisable(GL_DEPTH_TEST)

    inline fun enableFaceCulling() = glEnable(GL_CULL_FACE)
    inline fun disableFaceCulling() = glDisable(GL_CULL_FACE)

    inline fun enableMultisample() = glEnable(GL_MULTISAMPLE)
    inline fun disableMultisample() = glDisable(GL_MULTISAMPLE)

// ---------------------------------------------------------------------------------------------------------------------

    inline fun generateVertexArray() = glGenVertexArrays()
    inline fun destroyVertexArray(vao: Int) = glDeleteVertexArrays(vao)

    inline fun bindVertexArray(vao: Int) = glBindVertexArray(vao)
    inline fun unbindVertexArray() = bindVertexArray(0)

    // -----------------------------------------------------------------------------------------------------------------

    inline fun enableVertexAttribute(index: Int) = glEnableVertexAttribArray(index)

    inline fun describeVertexAttribute(index: Int, size: Int, stride: Int, pointer: Long) = glVertexAttribPointer(index, size, GL_FLOAT, false, stride * 4, pointer * 4)

// ---------------------------------------------------------------------------------------------------------------------

    inline fun generateBuffer() = glGenBuffers()
    inline fun destroyBuffer(buffer: Int) = glDeleteBuffers(buffer)

    inline fun bindVertexBuffer(vbo: Int) = glBindBuffer(GL_ARRAY_BUFFER, vbo)
    inline fun unbindVertexBuffer() = bindVertexBuffer(0)

    inline fun setVertexBufferData(vertices: FloatArray)  = glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

    inline fun bindIndexBuffer(ibo: Int) = glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
    inline fun unbindIndexBuffer() = bindIndexBuffer(0)

    inline fun setIndexBufferData(indices: IntArray) = glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)

// ---------------------------------------------------------------------------------------------------------------------

    inline fun generateTexture() = glGenTextures()

    inline fun deleteTexture(id: Int) = glDeleteTextures(id)

    inline fun bindTexture(id: Int, slot: Int) {
        glActiveTexture(GL_TEXTURE0 + slot)
        glBindTexture(GL_TEXTURE_2D, id)
    }

    inline fun unbindTexture(slot: Int) = bindTexture(0, slot)

    inline fun generateTextureImage(width: Int, height: Int, pixels: ByteArray?, format: TextureFormat) {
        if(pixels == null) {
            glTexImage2D(GL_TEXTURE_2D, 0, format.nativeInternal, width, height, 0, format.native, format.nativeType, NULL)
        } else {
            val buffer = MemoryUtil.memAlloc(pixels.size)
            buffer.put(pixels)
            buffer.flip()

            glTexImage2D(GL_TEXTURE_2D, 0, format.nativeInternal, width, height, 0, format.native, format.nativeType, buffer)

            MemoryUtil.memFree(buffer)
        }
    }

    inline fun setTextureWrap(s: TextureWrap, t: TextureWrap) {
        setTextureWrap(s, s = true)
        setTextureWrap(t, t = true)
    }

    inline fun setTextureWrap(wrap: TextureWrap, s: Boolean = false, t: Boolean = false) {
        if(s) {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap.native)
        }
        if(t) {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap.native)
        }
    }

    inline fun setTextureFilter(min: TextureFilter, mag: TextureFilter) {
        setTextureFilter(min, min = true)
        setTextureFilter(mag, mag = true)
    }

    inline fun setTextureFilter(filter: TextureFilter, min: Boolean = false, mag: Boolean = false) {
        if(min) {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter.native)
        }
        if(mag) {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter.native)
        }
    }

    inline fun setAutomaticMipmap(value: Boolean) = glTexParameteri(GL_TEXTURE_2D, GL_GENERATE_MIPMAP, if(value) GL_TRUE else GL_FALSE)

    inline fun generateMipmaps() {
        ARBFramebufferObject.glGenerateMipmap(GL_TEXTURE_2D)
    }

    enum class TextureFormat(val native: Int, val nativeInternal: Int, val nativeType: Int) {
        RGB(GL_RGB, GL_RGB8, GL_UNSIGNED_BYTE),
        RGBA(GL_RGBA, GL_RGBA8, GL_UNSIGNED_BYTE),
        SRGB(GL_RGB, GL_SRGB8, GL_UNSIGNED_BYTE),
        SRGBA(GL_RGBA, GL_SRGB8_ALPHA8, GL_UNSIGNED_BYTE),
        Depth(GL_DEPTH_COMPONENT, GL_DEPTH_COMPONENT, GL_UNSIGNED_BYTE),
        Stencil(GL_STENCIL_ATTACHMENT, GL_DEPTH_COMPONENT, GL_UNSIGNED_BYTE),
        DepthStencil(GL_DEPTH_STENCIL, GL_DEPTH24_STENCIL8, GL_UNSIGNED_INT_24_8)
    }

    enum class TextureWrap(val native: Int) {
        Repeat(GL_REPEAT), Mirror(GL_MIRRORED_REPEAT), Clamp(GL_CLAMP_TO_EDGE)
    }
    enum class TextureFilter(val native: Int) {
        Linear(GL_LINEAR),
        Nearest(GL_NEAREST),
        MipmapLinearLinear(GL_LINEAR_MIPMAP_LINEAR),
        MipmapLinearNearest(GL_LINEAR_MIPMAP_NEAREST),
        MipmapNearestLinear(GL_NEAREST_MIPMAP_LINEAR),
        MipmapNearestNearest(GL_NEAREST_MIPMAP_NEAREST)
    }

    // -----------------------------------------------------------------------------------------------------------------

    inline fun VertexShader(source: String): Int {
        val shader = createVertexShader()
        setShaderSource(shader, source)
        compileShader(shader)

        return shader
    }

    inline fun FragmentShader(source: String): Int {
        val shader = createFragmentShader()
        setShaderSource(shader, source)
        compileShader(shader)

        return shader
    }

    inline fun createVertexShader() = glCreateShader(GL_VERTEX_SHADER)
    inline fun createFragmentShader() = glCreateShader(GL_FRAGMENT_SHADER)

    inline fun setShaderSource(shader: Int, source: String) = glShaderSource(shader, source)

    inline fun compileShader(shader: Int) {
        glCompileShader(shader)

        val success = glGetShaderi(shader, GL_COMPILE_STATUS) != 0
        if(!success) {
            val log = glGetShaderInfoLog(shader)

            throw ShaderCompilationError(log)
        }
    }

    inline fun deleteShader(id: Int) = glDeleteShader(id)

    class ShaderCompilationError(message: String) : Exception(message)

    // -----------------------------------------------------------------------------------------------------------------

    inline fun ShaderProgram(vertexShader: Int, fragmentShader: Int): Int {
        val program = createProgram()

        attachShaderToProgram(program, vertexShader)
        attachShaderToProgram(program, fragmentShader)
        linkProgram(program)
        validateProgram(program)

        deleteShader(vertexShader)
        deleteShader(fragmentShader)

        return program
    }

    inline fun createProgram() = glCreateProgram()
    inline fun deleteProgram(program: Int) = glDeleteProgram(program)

    inline fun attachShaderToProgram(program: Int, shader: Int) = glAttachShader(program, shader)

    inline fun linkProgram(id: Int) {
        glLinkProgram(id)

        val success = glGetProgrami(id, GL_LINK_STATUS) != 0
        if(!success) {
            val log = glGetProgramInfoLog(id)

            throw ProgramLinkingError(log)
        }
    }

    inline fun validateProgram(program: Int) = glValidateProgram(program)

    inline fun useProgram(program: Int) = glUseProgram(program)

    class ProgramLinkingError(message: String) : Exception(message)

    // -----------------------------------------------------------------------------------------------------------------

    inline fun drawElements(indicesCount: Int, mode: DrawModes = DrawModes.TRIANGLES) = glDrawElements(mode.native, indicesCount, GL_UNSIGNED_INT, 0)

    enum class DrawModes(val native: Int) {
        TRIANGLES(GL_TRIANGLES),
        LINES(GL_LINES),
        POINTS(GL_POINTS)
    }

    // -----------------------------------------------------------------------------------------------------------------

    inline fun getUniformLocation(program: Int, name: String) = glGetUniformLocation(program, name)

    inline fun setUniformBoolean(location: Int, value: Boolean) = setUniformInt(location, if (value) 1 else 0)
    inline fun setUniformInt(location: Int, value: Int) = glUniform1i(location, value)
    inline fun setUniformFloat(location: Int, value: Float) = glUniform1f(location, value)
    inline fun setUniformVector(location: Int, value: Vector) = when(value) {
        is Vector2 -> glUniform2fv(location, value.components)
        is Vector3 -> glUniform3fv(location, value.components)
        is Vector4 -> glUniform4fv(location, value.components)
    }
    inline fun setUniformPoint(location: Int, value: Point) = when(value) {
        is Point2D -> glUniform2fv(location, value.components)
        is Point3D -> glUniform3fv(location, value.components)
    }
    inline fun setUniformMatrix(location: Int, value: Matrix) = when(value) {
        is Matrix2 -> GL20.glUniformMatrix2fv(location, true, value.components)
        is Matrix3 -> GL20.glUniformMatrix3fv(location, true, value.components)
        is Matrix4 -> GL20.glUniformMatrix4fv(location, true, value.components)
    }
    inline fun setUniformColor(location: Int, color: ColorFloat) = glUniform4fv(location, color.components)

// ---------------------------------------------------------------------------------------------------------------------

    inline fun generateRenderbuffer() = glGenRenderbuffers()
    inline fun deleteRenderbuffer(buffer: Int) = glDeleteRenderbuffers(buffer)

    inline fun bindRenderbuffer(buffer: Int) = glBindRenderbuffer(GL_RENDERBUFFER, buffer)

    inline fun unbindRenderbuffer() = bindRenderbuffer(0)

    inline fun generateRenderbufferStorage(width: Int, height: Int, format: TextureFormat) = glRenderbufferStorage(GL_RENDERBUFFER, format.nativeInternal, width, height)
    inline fun generateRenderbufferStorageMultisample(width: Int, height: Int, format: TextureFormat, samples: Int) = glRenderbufferStorageMultisample(GL_RENDERBUFFER, samples, format.nativeInternal, width, height)

// ---------------------------------------------------------------------------------------------------------------------

    inline fun generateFramebuffer() = glGenFramebuffers()
    inline fun deleteFramebuffer(buffer: Int) = glDeleteFramebuffers(buffer)

    inline fun bindFrameBuffer(buffer: Int, read: Boolean = true, write: Boolean = true) {
        val target = when {
            read && write -> GL_FRAMEBUFFER
            read -> GL_READ_FRAMEBUFFER
            write -> GL_DRAW_FRAMEBUFFER
            else -> throw IllegalArgumentException("The framebuffer must be set to readable or write")
        }

        glBindFramebuffer(target, buffer)
    }

    inline fun unbindFrameBuffer(read: Boolean = true, write: Boolean = true) = bindFrameBuffer(0, read, write)

    inline fun attachTextureToFramebuffer(texture: Int, attachment: FramebufferAttachment, read: Boolean = true, write: Boolean = true) {
        val target = when {
            read && write -> GL_FRAMEBUFFER
            read -> GL_READ_FRAMEBUFFER
            write -> GL_DRAW_FRAMEBUFFER
            else -> throw IllegalArgumentException("The framebuffer must be set to readable or write")
        }

        glFramebufferTexture2D(target, attachment.native, GL_TEXTURE_2D, texture, 0)
    }

    inline fun attachRenderbufferToFramebuffer(buffer: Int, attachment: FramebufferAttachment, read: Boolean = true, write: Boolean = true) {
        val target = when {
            read && write -> GL_FRAMEBUFFER
            read -> GL_READ_FRAMEBUFFER
            write -> GL_DRAW_FRAMEBUFFER
            else -> throw IllegalArgumentException("The framebuffer must be set to readable or write")
        }

        glFramebufferRenderbuffer(target, attachment.native, GL_RENDERBUFFER, buffer)
    }

    enum class FramebufferAttachment(val native: Int) {
        Color0(GL_COLOR_ATTACHMENT0),
        Color1(GL_COLOR_ATTACHMENT1),
        Color2(GL_COLOR_ATTACHMENT2),
        Color3(GL_COLOR_ATTACHMENT3),
        Color4(GL_COLOR_ATTACHMENT4),
        Color5(GL_COLOR_ATTACHMENT5),
        Color6(GL_COLOR_ATTACHMENT6),
        Color7(GL_COLOR_ATTACHMENT7),
        Color8(GL_COLOR_ATTACHMENT8),
        Color9(GL_COLOR_ATTACHMENT9),
        Color10(GL_COLOR_ATTACHMENT10),
        Color11(GL_COLOR_ATTACHMENT11),
        Color12(GL_COLOR_ATTACHMENT12),
        Color13(GL_COLOR_ATTACHMENT13),
        Color14(GL_COLOR_ATTACHMENT14),
        Color15(GL_COLOR_ATTACHMENT15),
        Color16(GL_COLOR_ATTACHMENT16),
        Color17(GL_COLOR_ATTACHMENT17),
        Color18(GL_COLOR_ATTACHMENT18),
        Color19(GL_COLOR_ATTACHMENT19),
        Color20(GL_COLOR_ATTACHMENT20),
        Color21(GL_COLOR_ATTACHMENT21),
        Color22(GL_COLOR_ATTACHMENT22),
        Color23(GL_COLOR_ATTACHMENT23),
        Color24(GL_COLOR_ATTACHMENT24),
        Color25(GL_COLOR_ATTACHMENT25),
        Color26(GL_COLOR_ATTACHMENT26),
        Color27(GL_COLOR_ATTACHMENT27),
        Color28(GL_COLOR_ATTACHMENT28),
        Color29(GL_COLOR_ATTACHMENT29),
        Color30(GL_COLOR_ATTACHMENT30),
        Color31(GL_COLOR_ATTACHMENT31),
        Depth(GL_DEPTH_ATTACHMENT),
        Stencil(GL_STENCIL_ATTACHMENT),
        DepthStencil(GL_DEPTH_STENCIL_ATTACHMENT)
    }

    inline fun isFramebufferComplete(read: Boolean = true, write: Boolean = true) = getFramebufferStatus(read, write) == FramebufferStatus.Complete

    inline fun getFramebufferStatus(read: Boolean = true, write: Boolean = true) : FramebufferStatus {
        val target = when {
            read && write -> GL_FRAMEBUFFER
            read -> GL_READ_FRAMEBUFFER
            write -> GL_DRAW_FRAMEBUFFER
            else -> throw IllegalArgumentException("The framebuffer must be set to readable or write")
        }

        return when(glCheckFramebufferStatus(target)) {
            GL_FRAMEBUFFER_COMPLETE -> FramebufferStatus.Complete
            GL_FRAMEBUFFER_UNDEFINED -> FramebufferStatus.Undefined
            GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT -> FramebufferStatus.IncompleteAttachment
            GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT -> FramebufferStatus.MissingAttachment
            GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER -> FramebufferStatus.IncompleteDrawBuffer
            GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER -> FramebufferStatus.IncompleteReadBuffer
            GL_FRAMEBUFFER_UNSUPPORTED -> FramebufferStatus.Unsupported
            GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE -> FramebufferStatus.IncompleteMultisample
            GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS -> FramebufferStatus.IncompleteLayerTargets
            else -> FramebufferStatus.Undefined
        }
    }

    inline fun blitFramebuffer(srcX0: Int, srcY0: Int, srcX1: Int, srcY1: Int, dstX0: Int, dstY0: Int, dstX1: Int, dstY2: Int, filter: TextureFilter, color: Boolean = true, depth: Boolean = true, stencil: Boolean = true) {
        if(filter != TextureFilter.Nearest && filter != TextureFilter.Linear) {
            throw IllegalArgumentException("filter must be either Nearest or Linear")
        }

        var mask = 0
        if(color) {
            mask = mask or GL_COLOR_BUFFER_BIT
        }
        if(depth) {
            mask = mask or GL_DEPTH_BUFFER_BIT
        }
        if(stencil) {
            mask = mask or GL_STENCIL_BUFFER_BIT
        }

        glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY2, mask, filter.native)
    }

    enum class FramebufferStatus(val native: Int) {
        Complete(GL_FRAMEBUFFER_COMPLETE),
        Undefined(GL_FRAMEBUFFER_UNDEFINED),
        IncompleteAttachment(GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT),
        MissingAttachment(GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT),
        IncompleteDrawBuffer(GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER),
        IncompleteReadBuffer(GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER),
        Unsupported(GL_FRAMEBUFFER_UNSUPPORTED),
        IncompleteMultisample(GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE),
        IncompleteLayerTargets(GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS)
    }
    /*inline fun generateBuffer() = glGenBuffers()
    inline fun destroyBuffer(buffer: Int) = glDeleteBuffers(buffer)

    inline fun bindVertexBuffer(vbo: Int) = glBindBuffer(GL_ARRAY_BUFFER, vbo)
    inline fun unbindVertexBuffer() = bindVertexBuffer(0)

    inline fun setVertexBufferData(vertices: FloatArray)  = glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

    inline fun bindIndexBuffer(ibo: Int) = glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
    inline fun unbindIndexBuffer() = bindIndexBuffer(0)

    inline fun setIndexBufferData(indices: IntArray) = glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)*/
}