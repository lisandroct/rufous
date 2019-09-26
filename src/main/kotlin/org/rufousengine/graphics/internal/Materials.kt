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
    private val vsBlinnPhong: String = """
            |#version 330 core
            |layout (location = 0) in vec3 aPosition;
            |layout (location = 2) in vec3 aNormal;
            |layout (location = 3) in vec2 aUv;
            |
            |uniform mat4 uWorld;
            |uniform mat4 uInverse;
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
            |}
            |
            |void main()
            |{
            |    position = vec3(uWorld * vec4(aPosition, 1.0));
            |    normal = mat3(transpose(uInverse)) * aNormal;
            |    uv = aUv;
            |    gl_Position = uProjection * uView * vec4(position, 1.0f);
            |}
            |""".trimMargin()

    private val fsBlinnPhong: String = """
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
            |}
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
            |    vec3 lightDirection = normalize(parameters.lightPosition - position);
            |    vec3 viewDirection = normalize(parameters.cameraPosition - position);
            |    vec3 halfwayDirection = normalize(lightDirection + viewDirection);
            |
            |    float spec = pow(max(dot(normal, halfwayDirection), 0.0), 32);
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
            |
            |    float gamma = 2.2;
            |    color.rgb = pow(color.rgb, vec3(1.0/gamma));
            |}
            |
            |void main()
            |{
            |    fragment();
            |    shade();
            |}
            |""".trimMargin()

    private val vsGrid: String = """
            |#version 330 core
            |layout (location = 0) in vec3 aPosition;
            |
            |uniform mat4 uWorld;
            |uniform mat4 uInverse;
            |uniform mat4 uView;
            |uniform mat4 uProjection;
            |
            |out vec3 position;
            |
            |uniform struct Parameters {
            |    vec3 center;
            |    vec3 cameraPosition;
            |    float tfov;
            |    float radius;
            |    float distance;
            |    int size;
            |} parameters;
            |
            |void main()
            |{
            |    position = vec3(uWorld * vec4(aPosition, 1.0));
            |    gl_Position = uProjection * uView * vec4(position, 1.0f);
            |}
            |""".trimMargin()

    private val fsGrid: String = """
            |#version 330 core
            |
            |in vec3 position;
            |out vec4 color;
            |
            |struct Properties {
            |    vec4 color;
            |} properties;
            |
            |uniform struct Parameters {
            |    vec3 center;
            |    vec3 cameraPosition;
            |    float tfov;
            |    float radius;
            |    float distance;
            |    int size;
            |} parameters;
            |
            |float grid(vec2 pos, float dist) {
            |    float hdist = dist / 2;
            |    float x = hdist - abs(pos.x - dist * floor(pos.x / dist) - hdist);
            |    float y = hdist - abs(pos.y - dist * floor(pos.y / dist) - hdist);
            |    return min(x, y);
            |}
            |
            |float line(float value, float width) {
            |    if(value <= width) {
            |        return 1;
            |    } else if(value <= width + 1) {
            |        return 0.25;
            |    } else {
            |        return 0;
            |    }
            |}
            |
            |float fade(float distance, float a, float b) {
            |    if(distance < a) {
            |        return 1.0;
            |    } else if(distance > b) {
            |        return 0.0;
            |    }
            |    
            |    return 1 - (distance - a) / (b - a);
            |}
            |
            |vec4 drawAxis(float value, vec3 color, float distance, float maxDistance, float perspectiveRatio) {
            |    float line = line(value * perspectiveRatio, 1);
            |    float fade = fade(distance, maxDistance, maxDistance * 2);
            |    
            |    return vec4(color, fade * line);
            |}
            |
            |void fragment() {
            |    float distance = length(position - parameters.cameraPosition);
            |    float toScreen = 720 / (parameters.tfov * distance * 2);
            |    
            |    float a0 = line(grid(position.xz, parameters.distance / parameters.size) * toScreen, 1) * fade(distance, 25, 37.5);
            |    float a1 = line(grid(position.xz, parameters.distance) * toScreen, 1) * fade(distance, 40, 60);
            |    float a2 = line(grid(position.xz, parameters.distance * parameters.size) * toScreen, 1) * fade(distance, 60, 90);
            |    vec4 grid0 = vec4(0.25, 0.25, 0.25, a0 * 0.1);
            |    vec4 grid1 = vec4(0.25, 0.25, 0.25, a1 * 0.3);
            |    vec4 grid2 = vec4(0.25, 0.25, 0.25, a2 * 0.7);
            |    
            |    float a3 = line(abs(position.x) * toScreen, 1) * fade(distance, 90, 135);
            |    float a4 = line(abs(position.z) * toScreen, 1) * fade(distance, 90, 135);
            |    vec4 z = vec4(0.2, 0.2, 0.9, a3);
            |    vec4 x = vec4(0.9, 0.2, 0.2, a4);
            |    
            |    properties.color = mix(mix(mix(mix(grid0, grid1, grid1.a), grid2, grid2.a), z, z.a), x, x.a);
            |    
            |    properties.color.a *= fade(length(position - parameters.center), parameters.radius, parameters.radius * 2);
            |    
            |    //properties.color = vec4(vec3(fade(line(grid(position.xz, parameters.distance / parameters.size) * toScreen, 2), 10, 30)), 1.0);
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
            |uniform mat4 uInverse;
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
            |}
            |
            |void main()
            |{
            |    position = vec3(uWorld * vec4(aPosition, 1.0));
            |    normal = mat3(transpose(uInverse)) * aNormal;
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
            |}
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
            |
            |    float gamma = 2.2;
            |    color.rgb = pow(color.rgb, vec3(1.0/gamma));
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
            |uniform mat4 uInverse;
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

    private val vsTest: String = """
            |#version 330 core
            |layout (location = 0) in vec3 aPosition;
            |layout (location = 3) in vec2 aUv;
            |
            |uniform mat4 uWorld;
            |uniform mat4 uInverse;
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
            |}
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
            |}
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

    private val vsGouraud: String = """
            |#version 330 core
            |layout (location = 0) in vec3 aPosition;
            |layout (location = 2) in vec3 aNormal;
            |layout (location = 3) in vec2 aUv;
            |
            |uniform mat4 uWorld;
            |uniform mat4 uInverse;
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
            |}
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
            |    normal = mat3(transpose(uInverse)) * aNormal;
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
            |}
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
            |
            |    float gamma = 2.2;
            |    color.rgb = pow(color.rgb, vec3(1.0/gamma));
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
            |uniform mat4 uInverse;
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
            |}
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
            |}
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

    private val vsUnlit: String = """
            |#version 330 core
            |layout (location = 0) in vec3 aPosition;
            |layout (location = 3) in vec2 aUv;
            |
            |uniform mat4 uWorld;
            |uniform mat4 uInverse;
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
            |}
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
            |}
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

    class BlinnPhong : Material(vsBlinnPhong, fsBlinnPhong) {
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
            GL.bindTexture(_texture.name, 0)
        }
    }

    class Grid : Material(vsGrid, fsGrid) {
        private val centerLocation: Int = GL.getUniformLocation(program, "parameters.center")

        val center: Point3D = Point3D()

        private val cameraPositionLocation: Int = GL.getUniformLocation(program,
                "parameters.cameraPosition")

        val cameraPosition: Point3D = Point3D()

        private val tfovLocation: Int = GL.getUniformLocation(program, "parameters.tfov")

        var tfov: Float = 0f

        private val radiusLocation: Int = GL.getUniformLocation(program, "parameters.radius")

        var radius: Float = 0f

        private val distanceLocation: Int = GL.getUniformLocation(program, "parameters.distance")

        var distance: Float = 0f

        private val sizeLocation: Int = GL.getUniformLocation(program, "parameters.size")

        var size: Int = 0

        override fun setParameters() {
            GL.setUniformPoint(centerLocation, center)
            GL.setUniformPoint(cameraPositionLocation, cameraPosition)
            GL.setUniformFloat(tfovLocation, tfov)
            GL.setUniformFloat(radiusLocation, radius)
            GL.setUniformFloat(distanceLocation, distance)
            GL.setUniformInt(sizeLocation, size)
        }
    }

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
            GL.bindTexture(_texture.name, 0)
        }
    }

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
            GL.bindTexture(_texture.name, 0)
            GL.setUniformFloat(timeLocation, time)
        }
    }

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
            GL.bindTexture(_texture.name, 0)
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
            GL.bindTexture(_texture.name, 0)
            GL.setUniformColor(tintLocation, tint)
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
            GL.bindTexture(_texture.name, 0)
        }
    }
}
