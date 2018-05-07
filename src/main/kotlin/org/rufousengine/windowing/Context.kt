package org.rufousengine.windowing

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil.NULL

object Context {
    private val glVersionMajor = 4
    private val glVersionMinor = 2

    init {
        glfwInit()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, glVersionMajor)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, glVersionMinor)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)

        // For Mac
        //glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE)
    }

    fun terminate() {
        glfwTerminate()
    }

    fun createWindow(width: Int, height: Int, samples: Int, resizable: Boolean, title: String) : Long {
        glfwWindowHint(GLFW_SAMPLES, samples)
        glfwWindowHint(GLFW_RESIZABLE, if(resizable) GLFW_TRUE else GLFW_FALSE)

        val id = glfwCreateWindow(width, height, title, NULL, NULL)

        if(id == NULL) {
            throw Exception("Failed to create window.")
        }

        return id
    }

    fun windowShouldClose(window: Window) : Boolean {
        return glfwWindowShouldClose(window.id)
    }

    fun windowSetShouldClose(window: Window, value: Boolean) {
        glfwSetWindowShouldClose(window.id, value)
    }

    fun setCurrent(window: Window) {
        glfwMakeContextCurrent(window.id)
    }

    fun enableVSync() {
        glfwSwapInterval(1)
    }

    fun swapBuffers(window: Window) {
        glfwSwapBuffers(window.id)
    }
}