package org.rufousengine.graphics.internal

object Error : MaterialType({
    requires {
        vertexAttribute(position)
    }
    model = ShadingModel {
        function = "color = vec4(1.0, 0.0, 1.0, 1.0);"
    }
})