package org.rufousengine.internal

import org.rufousengine.graphics.Material
import org.rufousengine.graphics.Texture
import org.rufousengine.system.GL

class UnlitMaterial(val texture: Texture?): Material(
        """
            #version 330 core
            layout (location = 0) in vec3 aPos;
            layout (location = 3) in vec2 aUV;

            uniform mat4 uWorld;
            uniform mat4 uView;
            uniform mat4 uProjection;

            out vec2 uv;

            void main()
            {
                gl_Position = uProjection * uView * uWorld * vec4(aPos, 1.0f);
                uv = aUV;
            }
        """.trimIndent(),
        """
            #version 330 core

            in vec2 uv;

            uniform sampler2D uTexture;

            out vec4 color;

            void main()
            {
                color = texture(uTexture, uv);
            }
        """.trimIndent()
) {
    private val textureLocation = GL.getUniformLocation(program, "uTexture")

    init {
        if(texture != null) {
            GL.useProgram(program)
            GL.setUniformInt(textureLocation, 0)
        }
    }

    override fun setParameters() {
        if(texture == null) {
            return
        }

        GL.bindTexture(texture.id, 0)
    }
}