package com.lisandroct.app.materials

import org.rufousengine.graphics.internal.*

private val model = ShadingModel {
    properties {
        color("color")
        float("alpha")
    }
    glsl = """
        void shade() {
            color = vec4(properties.color.rgb, properties.alpha);
        }
    """.trimIndent()
}

object Test : MaterialType(model, {
    requires { uv }

    parameters {
        texture("texture")
        float("time")
    }

    fragmentShader {
        glsl = """
            void fragment() {
                properties.color = textureColor(parameters.texture, uv);
                properties.alpha = sin(parameters.time);
            }
        """.trimIndent()
    }
})