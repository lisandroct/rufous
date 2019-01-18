package org.rufousengine.app.materials

import org.rufousengine.graphics.internal.*

object Test : MaterialType({
    model = ShadingModel {
        properties {
            property("color", color)
            property("alpha", float)
        }
        function = "color = vec4(material.color.rgb, material.alpha);"
    }

    requires {
        vertexAttribute(position)
        vertexAttribute(uv)
    }
    parameters {
        parameter("texture", texture)
        parameter("time", float)
    }
    fragment = """
        material.color = textureColor(parameters.texture, uv);
        material.alpha = sin(parameters.time);
    """.trimIndent()
})