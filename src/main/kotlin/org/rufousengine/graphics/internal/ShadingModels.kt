package org.rufousengine.graphics.internal

object ShadingModels {
    val unlit = ShadingModel {
        properties {
            color("color")
        }
        glsl = """
            void shade() {
                color = properties.color;
            }
        """.trimIndent()
    }

    val phong = ShadingModel {
        properties {
            vector3("ambient")
            vector3("diffuse")
            vector3("specular")
            color("objectColor")
        }
        glsl = """
        void shade() {
            color = vec4(properties.ambient + properties.diffuse + properties.specular, 1.0) * properties.objectColor;
        }
    """.trimIndent()
    }
}