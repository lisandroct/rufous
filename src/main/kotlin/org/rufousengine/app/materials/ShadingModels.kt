package org.rufousengine.app.materials

import org.rufousengine.graphics.internal.ShadingModel

object ShadingModels {
    val unlit = ShadingModel {
        properties {
            property("color", color)
        }
        function = "color = material.color;"
    }
}