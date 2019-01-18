package org.rufousengine.graphics

import org.rufousengine.math.Matrix4
import org.rufousengine.system.GL

abstract class Material(vertexShaderSource: String, fragmentShaderSource: String) {
    val program = GL.ShaderProgram(GL.VertexShader(vertexShaderSource), GL.FragmentShader(fragmentShaderSource))

    private val worldLocation = GL.getUniformLocation(program, "uWorld")
    private val viewLocation = GL.getUniformLocation(program, "uView")
    private val projectionLocation = GL.getUniformLocation(program, "uProjection")

    fun setTransformParameters(world: Matrix4, view: Matrix4, projection: Matrix4) {
        GL.setUniformMatrix(worldLocation, world)
        GL.setUniformMatrix(viewLocation, view)
        GL.setUniformMatrix(projectionLocation, projection)
    }

    open fun setParameters() { }
    fun destroy() = GL.deleteProgram(program)
}