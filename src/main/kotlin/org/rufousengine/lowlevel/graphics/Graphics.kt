package org.rufousengine.lowlevel.graphics

import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_MULTISAMPLE

object Graphics {
    init {
        createCapabilities()
    }

    fun setViewport(x: Int, y: Int, width: Int, height: Int) {
        glViewport(x, y, width, height)
    }

    fun enableBlend() {
        glEnable(GL_BLEND)
    }

    fun disableBlend() {
        glDisable(GL_BLEND)
    }

    fun enableFaceCulling() {
        glEnable(GL_CULL_FACE)
    }

    fun disableFaceCulling() {
        glDisable(GL_CULL_FACE)
    }

    fun enableMultisample() {
        glEnable(GL_MULTISAMPLE)
    }

    fun disableMultisample() {
        glDisable(GL_MULTISAMPLE)
    }
}