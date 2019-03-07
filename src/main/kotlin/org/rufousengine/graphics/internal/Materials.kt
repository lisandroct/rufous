package org.rufousengine.graphics.internal

import kotlin.Float
import kotlin.Int
import kotlin.String
import org.rufousengine.graphics.Material
import org.rufousengine.graphics.Texture
import org.rufousengine.math.ColorFloat
import org.rufousengine.math.Point3D
import org.rufousengine.system.GL

object Materials {
    private val vsGouraud: String = """
            |#version 330 core
            |layout (location = 0) in vec3 aPosition;
            |layout (location = 2) in vec3 aNormal;
            |layout (location = 3) in vec2 aUv;
            |
            |uniform mat4 uWorld;
            |uniform mat4 uView;
            |uniform mat4 uProjection;
            |
            |out vec3 position;
            |out vec3 normal;
            |out vec2 uv;
            |
            |out struct FSInputs {
            |    vec3 ambient;
            |    vec3 diffuse;
            |    vec3 specular;
            |} fs;
            |
            |struct Texture {
            |    sampler2D sampler;
            |    vec4 color;
            |};
            |
            |uniform struct Parameters {
            |    vec3 cameraPosition;
            |    vec4 lightColor;
            |    vec3 lightPosition;
            |    Texture texture;
            |} parameters;
            |
            |vec4 textureColor(Texture tex, vec2 uv) {
            |    return texture(tex.sampler, uv) * tex.color;
            |};
            |
            |vec3 ambient() {
            |    float ambientStrength = 0.4;
            |
            |    return ambientStrength * parameters.lightColor.rgb;
            |}
            |
            |vec3 diffuse() {
            |    vec3 norm = normalize(normal);
            |    vec3 lightDirection = normalize(parameters.lightPosition - position);
            |
            |    float diff = max(dot(norm, lightDirection), 0.0);
            |
            |    return diff * parameters.lightColor.rgb;
            |}
            |
            |vec3 specular() {
            |    float specularStrength = 0.5;
            |
            |    vec3 norm = normalize(normal);
            |    vec3 lightDirection = normalize(parameters.lightPosition - position);
            |
            |    vec3 viewDirection = normalize(parameters.cameraPosition - position);
            |    vec3 reflectDirection = reflect(-lightDirection, norm);
            |    float spec = pow(max(dot(viewDirection, reflectDirection), 0.0), 32);
            |
            |    return specularStrength * spec * parameters.lightColor.rgb;
            |}
            |
            |void vertex() {
            |    fs.ambient = ambient();
            |    fs.diffuse = diffuse();
            |    fs.specular = specular();
            |}
            |
            |void main()
            |{
            |    position = vec3(uWorld * vec4(aPosition, 1.0));
            |    normal = mat3(transpose(inverse(uWorld))) * aNormal;
            |    uv = aUv;
            |    vertex();
            |    gl_Position = uProjection * uView * vec4(position, 1.0f);
            |}
            |""".trimMargin()

    private val fsGouraud: String = """
            |#version 330 core
            |
            |in vec3 position;
            |in vec3 normal;
            |in vec2 uv;
            |out vec4 color;
            |
            |struct Properties {
            |    vec3 ambient;
            |    vec3 diffuse;
            |    vec3 specular;
            |    vec4 objectColor;
            |} properties;
            |
            |in struct FSInputs {
            |    vec3 ambient;
            |    vec3 diffuse;
            |    vec3 specular;
            |} fs;
            |
            |struct Texture {
            |    sampler2D sampler;
            |    vec4 color;
            |};
            |
            |uniform struct Parameters {
            |    vec3 cameraPosition;
            |    vec4 lightColor;
            |    vec3 lightPosition;
            |    Texture texture;
            |} parameters;
            |
            |vec4 textureColor(Texture tex, vec2 uv) {
            |    return texture(tex.sampler, uv) * tex.color;
            |};
            |
            |void fragment() {
            |    properties.ambient = fs.ambient;
            |    properties.diffuse = fs.diffuse;
            |    properties.specular = fs.specular;
            |    properties.objectColor = textureColor(parameters.texture, uv);
            |}
            |
            |void shade() {
            |    color = vec4(properties.ambient + properties.diffuse + properties.specular, 1.0) * properties.objectColor;
            |}
            |
            |void main()
            |{
            |    fragment();
            |    shade();
            |}
            |""".trimMargin()

    private val vsDebugQuad: String = """
            |#version 330 core
            |layout (location = 0) in vec3 aPosition;
            |layout (location = 3) in vec2 aUv;
            |
            |uniform mat4 uWorld;
            |uniform mat4 uView;
            |uniform mat4 uProjection;
            |
            |out vec3 position;
            |out vec2 uv;
            |
            |struct Texture {
            |    sampler2D sampler;
            |    vec4 color;
            |};
            |
            |uniform struct Parameters {
            |    Texture texture;
            |    vec4 tint;
            |} parameters;
            |
            |vec4 textureColor(Texture tex, vec2 uv) {
            |    return texture(tex.sampler, uv) * tex.color;
            |};
            |
            |void main()
            |{
            |    position = vec3(uWorld * vec4(aPosition, 1.0));
            |    uv = aUv;
            |    gl_Position = uProjection * uView * vec4(position, 1.0f);
            |}
            |""".trimMargin()

    private val fsDebugQuad: String = """
            |#version 330 core
            |
            |in vec3 position;
            |in vec2 uv;
            |out vec4 color;
            |
            |struct Properties {
            |    vec4 color;
            |} properties;
            |
            |struct Texture {
            |    sampler2D sampler;
            |    vec4 color;
            |};
            |
            |uniform struct Parameters {
            |    Texture texture;
            |    vec4 tint;
            |} parameters;
            |
            |vec4 textureColor(Texture tex, vec2 uv) {
            |    return texture(tex.sampler, uv) * tex.color;
            |};
            |
            |void fragment() {
            |    properties.color = textureColor(parameters.texture, uv) * parameters.tint;
            |}
            |
            |void shade() {
            |    color = properties.color;
            |}
            |
            |void main()
            |{
            |    fragment();
            |    shade();
            |}
            |""".trimMargin()

    private val vsError: String = """
            |#version 330 core
            |layout (location = 0) in vec3 aPosition;
            |
            |uniform mat4 uWorld;
            |uniform mat4 uView;
            |uniform mat4 uProjection;
            |
            |out vec3 position;
            |
            |void main()
            |{
            |    position = vec3(uWorld * vec4(aPosition, 1.0));
            |    gl_Position = uProjection * uView * vec4(position, 1.0f);
            |}
            |""".trimMargin()

    private val fsError: String = """
            |#version 330 core
            |
            |in vec3 position;
            |out vec4 color;
            |
            |struct Properties {
            |    vec4 color;
            |} properties;
            |
            |void fragment() {
            |    properties.color = vec4(1.0, 0.0, 1.0, 1.0);
            |}
            |
            |void shade() {
            |    color = properties.color;
            |}
            |
            |void main()
            |{
            |    fragment();
            |    shade();
            |}
            |""".trimMargin()

    private val vsPhong: String = """
            |#version 330 core
            |layout (location = 0) in vec3 aPosition;
            |layout (location = 2) in vec3 aNormal;
            |layout (location = 3) in vec2 aUv;
            |
            |uniform mat4 uWorld;
            |uniform mat4 uView;
            |uniform mat4 uProjection;
            |
            |out vec3 position;
            |out vec3 normal;
            |out vec2 uv;
            |
            |struct Texture {
            |    sampler2D sampler;
            |    vec4 color;
            |};
            |
            |uniform struct Parameters {
            |    vec3 cameraPosition;
            |    vec4 lightColor;
            |    vec3 lightPosition;
            |    Texture texture;
            |} parameters;
            |
            |vec4 textureColor(Texture tex, vec2 uv) {
            |    return texture(tex.sampler, uv) * tex.color;
            |};
            |
            |void main()
            |{
            |    position = vec3(uWorld * vec4(aPosition, 1.0));
            |    normal = mat3(transpose(inverse(uWorld))) * aNormal;
            |    uv = aUv;
            |    gl_Position = uProjection * uView * vec4(position, 1.0f);
            |}
            |""".trimMargin()

    private val fsPhong: String = """
            |#version 330 core
            |
            |in vec3 position;
            |in vec3 normal;
            |in vec2 uv;
            |out vec4 color;
            |
            |struct Properties {
            |    vec3 ambient;
            |    vec3 diffuse;
            |    vec3 specular;
            |    vec4 objectColor;
            |} properties;
            |
            |struct Texture {
            |    sampler2D sampler;
            |    vec4 color;
            |};
            |
            |uniform struct Parameters {
            |    vec3 cameraPosition;
            |    vec4 lightColor;
            |    vec3 lightPosition;
            |    Texture texture;
            |} parameters;
            |
            |vec4 textureColor(Texture tex, vec2 uv) {
            |    return texture(tex.sampler, uv) * tex.color;
            |};
            |
            |vec3 ambient() {
            |    float ambientStrength = 0.4;
            |
            |    return ambientStrength * parameters.lightColor.rgb;
            |}
            |
            |vec3 diffuse() {
            |    vec3 norm = normalize(normal);
            |    vec3 lightDirection = normalize(parameters.lightPosition - position);
            |
            |    float diff = max(dot(norm, lightDirection), 0.0);
            |
            |    return diff * parameters.lightColor.rgb;
            |}
            |
            |vec3 specular() {
            |    float specularStrength = 0.5;
            |
            |    vec3 norm = normalize(normal);
            |    vec3 lightDirection = normalize(parameters.lightPosition - position);
            |
            |    vec3 viewDirection = normalize(parameters.cameraPosition - position);
            |    vec3 reflectDirection = reflect(-lightDirection, norm);
            |    float spec = pow(max(dot(viewDirection, reflectDirection), 0.0), 32);
            |
            |    return specularStrength * spec * parameters.lightColor.rgb;
            |}
            |
            |void fragment() {
            |    properties.ambient = ambient();
            |    properties.diffuse = diffuse();
            |    properties.specular = specular();
            |    properties.objectColor = textureColor(parameters.texture, uv);
            |}
            |
            |void shade() {
            |    color = vec4(properties.ambient + properties.diffuse + properties.specular, 1.0) * properties.objectColor;
            |}
            |
            |void main()
            |{
            |    fragment();
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
            |out vec3 position;
            |out vec2 uv;
            |
            |struct Texture {
            |    sampler2D sampler;
            |    vec4 color;
            |};
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
            |void main()
            |{
            |    position = vec3(uWorld * vec4(aPosition, 1.0));
            |    uv = aUv;
            |    gl_Position = uProjection * uView * vec4(position, 1.0f);
            |}
            |""".trimMargin()

    private val fsTest: String = """
            |#version 330 core
            |
            |in vec3 position;
            |in vec2 uv;
            |out vec4 color;
            |
            |struct Properties {
            |    vec4 color;
            |    float alpha;
            |} properties;
            |
            |struct Texture {
            |    sampler2D sampler;
            |    vec4 color;
            |};
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
            |    properties.color = textureColor(parameters.texture, uv);
            |    properties.alpha = sin(parameters.time);
            |}
            |
            |void shade() {
            |    color = vec4(properties.color.rgb, properties.alpha);
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
            |out vec3 position;
            |out vec2 uv;
            |
            |struct Texture {
            |    sampler2D sampler;
            |    vec4 color;
            |};
            |
            |uniform struct Parameters {
            |    Texture texture;
            |} parameters;
            |
            |vec4 textureColor(Texture tex, vec2 uv) {
            |    return texture(tex.sampler, uv) * tex.color;
            |};
            |
            |void main()
            |{
            |    position = vec3(uWorld * vec4(aPosition, 1.0));
            |    uv = aUv;
            |    gl_Position = uProjection * uView * vec4(position, 1.0f);
            |}
            |""".trimMargin()

    private val fsUnlit: String = """
            |#version 330 core
            |
            |in vec3 position;
            |in vec2 uv;
            |out vec4 color;
            |
            |struct Properties {
            |    vec4 color;
            |} properties;
            |
            |struct Texture {
            |    sampler2D sampler;
            |    vec4 color;
            |};
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
            |    properties.color = textureColor(parameters.texture, uv);
            |}
            |
            |void shade() {
            |    color = properties.color;
            |}
            |
            |void main()
            |{
            |    fragment();
            |    shade();
            |}
            |""".trimMargin()

    class Gouraud : Material(vsGouraud, fsGouraud) {
        private val cameraPositionLocation: Int = GL.getUniformLocation(program,
                "parameters.cameraPosition")

        val cameraPosition: Point3D = Point3D()

        private val lightColorLocation: Int = GL.getUniformLocation(program,
                "parameters.lightColor")

        val lightColor: ColorFloat = ColorFloat()

        private val lightPositionLocation: Int = GL.getUniformLocation(program,
                "parameters.lightPosition")

        val lightPosition: Point3D = Point3D()

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
            GL.setUniformPoint(cameraPositionLocation, cameraPosition)
            GL.setUniformColor(lightColorLocation, lightColor)
            GL.setUniformPoint(lightPositionLocation, lightPosition)
            val texture = texture
            if(texture != null) {
                GL.setUniformColor(textureColorLocation, Colors.white)
            } else {
                GL.setUniformColor(textureColorLocation, textureColor)
            }
            GL.bindTexture(_texture.id, 0)
        }
    }

    class DebugQuad : Material(vsDebugQuad, fsDebugQuad) {
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

        private val tintLocation: Int = GL.getUniformLocation(program, "parameters.tint")

        val tint: ColorFloat = ColorFloat()

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
            GL.setUniformColor(tintLocation, tint)
        }
    }

    class Error : Material(vsError, fsError)

    class Phong : Material(vsPhong, fsPhong) {
        private val cameraPositionLocation: Int = GL.getUniformLocation(program,
                "parameters.cameraPosition")

        val cameraPosition: Point3D = Point3D()

        private val lightColorLocation: Int = GL.getUniformLocation(program,
                "parameters.lightColor")

        val lightColor: ColorFloat = ColorFloat()

        private val lightPositionLocation: Int = GL.getUniformLocation(program,
                "parameters.lightPosition")

        val lightPosition: Point3D = Point3D()

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
            GL.setUniformPoint(cameraPositionLocation, cameraPosition)
            GL.setUniformColor(lightColorLocation, lightColor)
            GL.setUniformPoint(lightPositionLocation, lightPosition)
            val texture = texture
            if(texture != null) {
                GL.setUniformColor(textureColorLocation, Colors.white)
            } else {
                GL.setUniformColor(textureColorLocation, textureColor)
            }
            GL.bindTexture(_texture.id, 0)
        }
    }

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
