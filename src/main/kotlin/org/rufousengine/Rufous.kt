package org.rufousengine

import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.opengl.GL11.*
import org.rufousengine.ecs.World
import org.rufousengine.events.mouse.MouseButtonPressed
import org.rufousengine.events.mouse.MouseButtonReleased
import org.rufousengine.events.mouse.MouseMoveEvent
import org.rufousengine.events.mouse.ScrollEvent
import org.rufousengine.math.Vector
import org.rufousengine.math.Vector2
import org.rufousengine.math.isValid
import org.rufousengine.system.*
import org.rufousengine.utils.MouseButtons
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class Rufous(appClass: KClass<out App>, width: Int, height: Int) {
    private val window = Context.Window(width, height, 4,true, "Rufous")
    private val app: App
    private val mousePosition = Vector2(Float.NaN, Float.NaN)

    init {
        Context.setCurrent(window)

        Context.setResizeCallback(window, ::resize)
        Context.setScrollCallback(window, ::scroll)
        Context.setMouseMoveCallback(window, ::mouseMove)
        Context.setMouseButtonCallback(window, ::mouseButton)

        Context.enableVSync()

        app = appClass.createInstance()
        app.resize(width, height)

        Context.setTitle(window, app.title)

        run()

        terminate()
    }

    private fun run() {
        GL.enableBlend()
        GL.enableDepthTest()
        GL.enableFaceCulling()
        GL.enableMultisample()

        glDepthFunc(GL_LESS)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        try {
            mainLoop()
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun mainLoop() {
        while(!Context.getShouldClose(window)) {
            if(Context.isEscapePressed(window)) {
                Context.setShouldClose(window, true)
            }

            World.update()

            // check and call events and swap the buffers
            Context.swapBuffers(window)
            Context.pollEvents()
        }
    }

    private fun terminate() {
        Resources.destroyAll()

        Context.terminate(window)
    }

    private fun resize(window: Long, width: Int, height: Int) = app.resize(width, height)
    private fun scroll(window: Long, x: Double, y: Double) {
        ScrollEvent(x.toFloat(), y.toFloat())
    }
    private fun mouseMove(window: Long, x: Double, y: Double) {
        val x = x.toFloat()
        val y = y.toFloat()
        if(mousePosition.isValid) {
            MouseMoveEvent(x - mousePosition.x, y - mousePosition.y)
        }

        mousePosition.set(x, y)
    }
    private fun mouseButton(window: Long, button: Int, action: Int, mod: Int) {
        if(action == GLFW_PRESS) {
            MouseButtonPressed(when (button) {
                0 -> MouseButtons.LEFT
                1 -> MouseButtons.RIGHT
                2 -> MouseButtons.MIDDLE
                else -> MouseButtons.LEFT
            })
        } else if(action == GLFW_RELEASE) {
            MouseButtonReleased(when(button) {
                0 -> MouseButtons.LEFT
                1 -> MouseButtons.RIGHT
                2 -> MouseButtons.MIDDLE
                else -> MouseButtons.LEFT
            })
        }
    }
}