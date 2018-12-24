package org.rufousengine.internal

import org.rufousengine.graphics.Material

class DefaultMaterial: Material(
        """
            #version 330 core
            layout (location = 0) in vec3 aPos;

            uniform mat4 uWorld;
            uniform mat4 uView;
            uniform mat4 uProjection;

            void main()
            {
                gl_Position = uProjection * uView * uWorld * vec4(aPos, 1.0f);
            }
        """.trimIndent(),
        """
            #version 330 core

            out vec4 color;

            void main()
            {
                color = vec4(1.0, 0.0, 1.0, 1.0);
            }
        """.trimIndent()
) {
    override fun setParameters() { }
}