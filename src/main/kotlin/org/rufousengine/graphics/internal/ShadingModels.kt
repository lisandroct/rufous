package org.rufousengine.graphics.internal

object ShadingModels {
    val unlit = shadingModel("unlit") {
        properties {
            property("color", color)
        }
        equation = """
            vec4 shade(MaterialInputs material)
            {
                return material.color;
            }
        """.trimIndent()
    }
}