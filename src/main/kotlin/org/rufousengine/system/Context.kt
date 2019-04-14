package org.rufousengine.system

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryUtil.NULL

typealias ResizeCallback = (Long, Int, Int) -> Unit
typealias ScrollCallback = (Long, Double, Double) -> Unit

object Context {
    private const val glVersionMajor = 3
    private const val glVersionMinor = 3

    init {
        GLFWErrorCallback.createPrint(System.err).set()

        if (!glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, glVersionMajor)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, glVersionMinor)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)

        // For Mac
        //glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE)
    }

    fun terminate(window: Long) {
        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

    fun Window(width: Int, height: Int, samples: Int, resizable: Boolean, title: String) : Long {
        glfwWindowHint(GLFW_SAMPLES, samples)
        glfwWindowHint(GLFW_RESIZABLE, if(resizable) GLFW_TRUE else GLFW_FALSE)

        val window = glfwCreateWindow(width, height, title, NULL, NULL)

        if(window == NULL) {
            throw Exception("Failed to create window.")
        }

        return window
    }

    fun setResizeCallback(window: Long, callback: ResizeCallback) = glfwSetFramebufferSizeCallback(window, callback)
    fun setScrollCallback(window: Long, callback: ScrollCallback) = glfwSetScrollCallback(window, callback)

    fun getShouldClose(window: Long) = glfwWindowShouldClose(window)

    fun setShouldClose(window: Long, value: Boolean) = glfwSetWindowShouldClose(window, value)

    fun isEscapePressed(window: Long) = glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS

    fun setCurrent(window: Long) = glfwMakeContextCurrent(window)

    fun enableVSync() = glfwSwapInterval(1)

    fun swapBuffers(window: Long) = glfwSwapBuffers(window)

    fun pollEvents() = glfwPollEvents()
}