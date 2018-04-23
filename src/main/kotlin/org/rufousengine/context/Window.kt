package org.rufousengine.context

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil.NULL

class Window(width: Int, height: Int, title: String) {
    val id = glfwCreateWindow(width, height, title, NULL, NULL)

    val width = width
    val height = height

    var shouldClose : Boolean
        get() = glfwWindowShouldClose(id)
        set(value) = glfwSetWindowShouldClose(id, value)

    init {
        if(id == NULL) {
            throw Exception("Failed to create GLFW window.")
        }
    }

    fun isKeyPressed(key: Int) = glfwGetKey(id, key) == GLFW_PRESS
}