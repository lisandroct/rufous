package org.rufousengine.app.materials

import org.rufousengine.graphics.internal.MaterialType

object Unlit : MaterialType({
    model = ShadingModels.unlit
    requires {
        vertexAttribute(position)
        vertexAttribute(uv)
    }
    parameters {
        parameter("texture", texture)
    }
    fragment = "material.color = textureColor(parameters.texture, uv);"
})