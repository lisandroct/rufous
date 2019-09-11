package org.rufousengine.graphics.internal

import org.rufousengine.editor.TextureLoader
import org.rufousengine.files.Files

object Textures {
    val white = TextureLoader.load(Files.local("textures/white.jpg"), alpha = false, sRGB = false)
}