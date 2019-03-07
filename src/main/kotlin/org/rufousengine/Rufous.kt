package org.rufousengine

import com.lisandroct.app.Application
import org.lwjgl.opengl.GL11.*
import org.rufousengine.system.*

class Rufous(name: String, width: Int, height: Int) {
    private val window = Context.Window(width, height, 4,true, name)
    private val app: Application

    init {
        Context.setCurrent(window)

        Context.setResizeCallback(window, ::resize)
        Context.setScrollCallback(window, ::scroll)

        Context.enableVSync()

        app = Application()
        app.resize(width, height)

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

            app.render()

            // check and call events and swap the buffers
            Context.swapBuffers(window)
            Context.pollEvents()
        }
    }

    private fun terminate() {
        app.destroy()

        Context.terminate(window)
    }

    private fun resize(window: Long, width: Int, height: Int) = app.resize(width, height)
    private fun scroll(window: Long, x: Double, y: Double) = app.scroll(x.toFloat(), y.toFloat())
}