package org.rufousengine.lowlevel.windowing

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil.NULL

class Window(width: Int, height: Int, title: String, samples: Int = 4, resizable: Boolean = false) {
    val id = Context.createWindow(width, height, samples, resizable, title)

    val width = width
    val height = height

    var shouldClose : Boolean
        get() = Context.windowShouldClose(this)
        set(value) = Context.windowSetShouldClose(this, value)

    fun isKeyPressed(key: Int) = glfwGetKey(id, key) == GLFW_PRESS
}