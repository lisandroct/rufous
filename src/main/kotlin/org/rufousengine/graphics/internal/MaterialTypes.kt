package org.rufousengine.graphics.internal

object MaterialTypes {
    val error = material("Error") {
        shadingModel = ShadingModels.unlit
        requires {
            vertexAttribute(position)
        }
        fragment = """
            void material(inout MaterialInputs material) {
                material.color = vec4(1.0, 0.0, 1.0, 1.0);
            }
        """.trimIndent()
    }
    val unlit = material("Unlit") {
        shadingModel = ShadingModels.unlit
        requires {
            vertexAttribute(position)
            vertexAttribute(uv)
        }
        parameters {
            parameter("texture", texture)
        }
        fragment = """
            void material(inout MaterialInputs material) {
                material.color = texture(parameters.texture, uv);
            }
        """.trimIndent()
    }
}