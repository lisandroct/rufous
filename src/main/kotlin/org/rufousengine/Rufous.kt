package org.rufousengine

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import org.rufousengine.files.Files
import org.rufousengine.graphics.Graphics
import org.rufousengine.windowing.Window
import org.rufousengine.windowing.*

class Rufous(width: Int, height: Int) {
    private val window = Window(width, height, "Rufous Application")

    init {
        Context.setCurrent(window)
        Context.enableVSync()

        Graphics.setViewport(0, 0, width, height)

        run()
    }

    private fun run() {
        Graphics.enableBlend()
        Graphics.enableFaceCulling()
        Graphics.enableMultisample()

        glDepthFunc(GL_LESS)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        try {
            mainLoop()
        } catch (e: Exception) {
            println(e.message)
        }

        Context.terminate()
    }

    private fun mainLoop() {
        while(!window.shouldClose) {
            if(window.isKeyPressed(GLFW_KEY_ESCAPE)) {
                window.shouldClose = true
            }

            // check and call events and swap the buffers
            Context.swapBuffers(window)
            glfwPollEvents()
        }
    }
}

fun main(args: Array<String>) {
    Rufous(1280, 720)
}