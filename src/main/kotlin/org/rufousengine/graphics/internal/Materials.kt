package org.rufousengine.graphics.internal

import org.rufousengine.graphics.Material
import org.rufousengine.graphics.Texture
import org.rufousengine.system.GL

class ErrorMaterial: Material(
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

            struct MaterialInputs {
                vec4 color;
            } inputs;

            void material(inout MaterialInputs material)
            {
                material.color = vec4(1.0, 0.0, 1.0, 1.0);
            }

            vec4 shade(MaterialInputs material)
            {
                return material.color;
            }

            void main()
            {
                material(inputs);
                color = shade(inputs);
            }
        """.trimIndent()
) {
    override fun setParameters() { }
}

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
            out vec4 color;

            struct MaterialInputs {
                vec4 color;
            } inputs;

            uniform struct Parameters {
                sampler2D texture;
            } parameters;

            void material(inout MaterialInputs material)
            {
                material.color = texture(parameters.texture, uv);
            }

            vec4 shade(MaterialInputs material)
            {
                return material.color;
            }

            void main()
            {
                material(inputs);
                color = shade(inputs);
            }
        """.trimIndent()
) {
    private val textureLocation = GL.getUniformLocation(program, "parameters.texture")

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