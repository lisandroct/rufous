@file:Suppress("NOTHING_TO_INLINE")

package org.rufousengine.system

import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30.*
import org.rufousengine.math.*

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

    inline fun unbindTexture(slot: Int) {
        glActiveTexture(GL_TEXTURE0 + slot)
        glBindTexture(GL_TEXTURE_2D, 0)
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

    inline fun drawElements(indicesCount: Int) = glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0)

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
}