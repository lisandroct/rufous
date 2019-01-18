package org.rufousengine.graphics.internal

import kotlin.Float
import kotlin.Int
import kotlin.String
import org.rufousengine.graphics.Material
import org.rufousengine.graphics.Texture
import org.rufousengine.math.ColorFloat
import org.rufousengine.system.GL

object Materials {
    private val vsError: String = """
            |#version 330 core
            |layout (location = 0) in vec3 aPosition;
            |
            |uniform mat4 uWorld;
            |uniform mat4 uView;
            |uniform mat4 uProjection;
            |
            |void main()
            |{
            |    gl_Position = uProjection * uView * uWorld * vec4(aPosition, 1.0f);
            |}
            |""".trimMargin()

    private val fsError: String = """
            |#version 330 core
            |
            |out vec4 color;
            |
            |void shade() {
            |    color = vec4(1.0, 0.0, 1.0, 1.0);
            |}
            |
            |void main()
            |{
            |    shade();
            |}
            |""".trimMargin()

    private val vsTest: String = """
            |#version 330 core
            |layout (location = 0) in vec3 aPosition;
            |layout (location = 3) in vec2 aUv;
            |
            |uniform mat4 uWorld;
            |uniform mat4 uView;
            |uniform mat4 uProjection;
            |
            |out vec2 uv;
            |
            |void main()
            |{
            |    uv = aUv;
            |    gl_Position = uProjection * uView * uWorld * vec4(aPosition, 1.0f);
            |}
            |""".trimMargin()

    private val fsTest: String = """
            |#version 330 core
            |
            |in vec2 uv;
            |out vec4 color;
            |
            |struct Texture {
            |    sampler2D sampler;
            |    vec4 color;
            |};
            |
            |struct Properties {
            |    vec4 color;
            |    float alpha;
            |} material;
            |
            |uniform struct Parameters {
            |    Texture texture;
            |    float time;
            |} parameters;
            |
            |vec4 textureColor(Texture tex, vec2 uv) {
            |    return texture(tex.sampler, uv) * tex.color;
            |};
            |
            |void fragment() {
            |    material.color = textureColor(parameters.texture, uv);
            |    material.alpha = sin(parameters.time);
            |}
            |
            |void shade() {
            |    color = vec4(material.color.rgb, material.alpha);
            |}
            |
            |void main()
            |{
            |    fragment();
            |    shade();
            |}
            |""".trimMargin()

    private val vsUnlit: String = """
            |#version 330 core
            |layout (location = 0) in vec3 aPosition;
            |layout (location = 3) in vec2 aUv;
            |
            |uniform mat4 uWorld;
            |uniform mat4 uView;
            |uniform mat4 uProjection;
            |
            |out vec2 uv;
            |
            |void main()
            |{
            |    uv = aUv;
            |    gl_Position = uProjection * uView * uWorld * vec4(aPosition, 1.0f);
            |}
            |""".trimMargin()

    private val fsUnlit: String = """
            |#version 330 core
            |
            |in vec2 uv;
            |out vec4 color;
            |
            |struct Texture {
            |    sampler2D sampler;
            |    vec4 color;
            |};
            |
            |struct Properties {
            |    vec4 color;
            |} material;
            |
            |uniform struct Parameters {
            |    Texture texture;
            |} parameters;
            |
            |vec4 textureColor(Texture tex, vec2 uv) {
            |    return texture(tex.sampler, uv) * tex.color;
            |};
            |
            |void fragment() {
            |    material.color = textureColor(parameters.texture, uv);
            |}
            |
            |void shade() {
            |    color = material.color;
            |}
            |
            |void main()
            |{
            |    fragment();
            |    shade();
            |}
            |""".trimMargin()

    class Error : Material(vsError, fsError)

    class Test : Material(vsTest, fsTest) {
        private val textureLocation: Int = GL.getUniformLocation(program,
                "parameters.texture.sampler")

        private var _texture: Texture = Textures.white

        var texture: Texture?
            get() = if(_texture != Textures.white) _texture else null
            set(value) {
                _texture = value ?: Textures.white
            }

        private val textureColorLocation: Int = GL.getUniformLocation(program,
                "parameters.texture.color")

        val textureColor: ColorFloat = ColorFloat()

        private val timeLocation: Int = GL.getUniformLocation(program, "parameters.time")

        var time: Float = 0f

        init {
            GL.useProgram(program)
            GL.setUniformInt(textureLocation, 0)
        }

        override fun setParameters() {
            val texture = texture
            if(texture != null) {
                GL.setUniformColor(textureColorLocation, Colors.white)
            } else {
                GL.setUniformColor(textureColorLocation, textureColor)
            }
            GL.bindTexture(_texture.id, 0)
            GL.setUniformFloat(timeLocation, time)
        }
    }

    class Unlit : Material(vsUnlit, fsUnlit) {
        private val textureLocation: Int = GL.getUniformLocation(program,
                "parameters.texture.sampler")

        private var _texture: Texture = Textures.white

        var texture: Texture?
            get() = if(_texture != Textures.white) _texture else null
            set(value) {
                _texture = value ?: Textures.white
            }

        private val textureColorLocation: Int = GL.getUniformLocation(program,
                "parameters.texture.color")

        val textureColor: ColorFloat = ColorFloat()

        init {
            GL.useProgram(program)
            GL.setUniformInt(textureLocation, 0)
        }

        override fun setParameters() {
            val texture = texture
            if(texture != null) {
                GL.setUniformColor(textureColorLocation, Colors.white)
            } else {
                GL.setUniformColor(textureColorLocation, textureColor)
            }
            GL.bindTexture(_texture.id, 0)
        }
    }
}
