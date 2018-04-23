package org.rufousengine

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_MULTISAMPLE
import org.lwjgl.system.MemoryUtil

class Rufous {
    private val window: Long

    init {
        glfwInit()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
        glfwWindowHint(GLFW_SAMPLES, 4)

        // For Mac
        //glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        window = glfwCreateWindow(1280, 720, "Rufous Application", MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) {
            throw Exception("Failed to create GLFW window")
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window)
        // Enable v-sync
        glfwSwapInterval(1)

        createCapabilities()

        glViewport(0, 0, 1280, 720)

        run()
    }

    private fun run() {
        glEnable(GL_BLEND)
        glEnable(GL_CULL_FACE)
        glEnable(GL_MULTISAMPLE)

        glDepthFunc(GL_LESS)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        try {
            mainLoop()
        } catch (e: Exception) {
            println(e.message)
        }

        glfwTerminate()
    }

    private fun mainLoop() {
        while(!glfwWindowShouldClose(window)) {
            // input
            processInput(window)

            // check and call events and swap the buffers
            glfwSwapBuffers(window)
            glfwPollEvents()
        }
    }

    private fun processInput(window: Long) {
        if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            glfwSetWindowShouldClose(window, true)
        }
    }
}

fun main(args: Array<String>) {
    Rufous()
}