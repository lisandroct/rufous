package org.rufousengine.editor

import com.squareup.kotlinpoet.*
import org.rufousengine.graphics.Material
import org.rufousengine.graphics.Texture
import org.rufousengine.graphics.VertexAttribute
import org.rufousengine.math.*
import org.reflections.Reflections
import org.rufousengine.files.Files
import org.rufousengine.graphics.internal.GType
import org.rufousengine.graphics.internal.GValue
import org.rufousengine.graphics.internal.MaterialType
import org.rufousengine.graphics.internal.MaterialType.Requires
import org.rufousengine.graphics.internal.MaterialType.Vertex
import org.rufousengine.graphics.internal.MaterialType.Fragment
import org.rufousengine.graphics.internal.MaterialType.GValuesList
import org.rufousengine.graphics.internal.ShadingModel

private fun buildVertexShader(name: String, model: ShadingModel, requires: Requires, parameters: GValuesList, vertexShader: Vertex, outputs: GValuesList): PropertySpec {
    val builder = StringBuilder()

    builder.apply {
        appendln("#version 330 core")
        for (attribute in requires) {
            appendln("layout (location = ${attribute.location}) in ${attribute.glslType} ${attribute.glslLayoutName};")
        }
        appendln()
        appendln("uniform mat4 uWorld;")
        appendln("uniform mat4 uView;")
        appendln("uniform mat4 uProjection;")
        appendln()
        for (attribute in requires) {
            appendln("out ${attribute.glslType} ${attribute.name};")
        }
        appendln()
        if(outputs.isNotEmpty()) {
            appendln("out struct FSInputs {")
            for (output in outputs) {
                appendln("    ${type(output)} ${output.name};")
            }
            appendln("} fs;")
            appendln()
        }
        appendParameters(parameters)
        appendFunctions(parameters)
        if (vertexShader.glsl.isNotEmpty()) {
            appendln(vertexShader.glsl)
            appendln()
        }
        appendln("void main()")
        appendln("{")
        for (attribute in requires) {
            when (attribute) {
                VertexAttribute.position -> appendln("    ${attribute.name} = vec3(uWorld * vec4(${attribute.glslLayoutName}, 1.0));")
                VertexAttribute.normal -> appendln("    ${attribute.name} = mat3(transpose(inverse(uWorld))) * ${attribute.glslLayoutName};")
                else -> appendln("    ${attribute.name} = ${attribute.glslLayoutName};")
            }
        }
        if (vertexShader.glsl.isNotEmpty()) {
            appendln("    vertex();")
        }
        appendln("    gl_Position = uProjection * uView * vec4(${VertexAttribute.position.name}, 1.0f);")
        appendln("}")
    }

    return PropertySpec.builder("vs$name", String::class)
            .addModifiers(KModifier.PRIVATE)
            .initializer("%P", builder.toString())
            .build()
}

private fun buildFragmentShader(name: String, model: ShadingModel, requires: Requires, parameters: GValuesList, fragmentShader: Fragment): PropertySpec {
    val builder = StringBuilder()

    builder.apply {
        appendln("#version 330 core")
        appendln()
        for (attribute in requires) {
            appendln("in ${attribute.glslType} ${attribute.name};")
        }
        appendln("out vec4 color;")
        appendln()
        if(model.properties.isNotEmpty()) {
            appendln("struct Properties {")
            for (property in model.properties) {
                appendln("    ${type(property)} ${property.name};")
            }
            appendln("} properties;")
            appendln()
        }
        if(fragmentShader.inputs.isNotEmpty()) {
            appendln("in struct FSInputs {")
            for (input in fragmentShader.inputs) {
                appendln("    ${type(input)} ${input.name};")
            }
            appendln("} fs;")
            appendln()
        }
        appendParameters(parameters)
        appendFunctions(parameters)
        if (fragmentShader.glsl.isNotEmpty()) {
            appendln(fragmentShader.glsl)
            appendln()
        }
        appendln(model.glsl)
        appendln()
        appendln("void main()")
        appendln("{")
        if (fragmentShader.glsl.isNotEmpty()) {
            appendln("    fragment();")
        }
        appendln("    shade();")
        appendln("}")
    }

    return PropertySpec.builder("fs$name", String::class)
            .addModifiers(KModifier.PRIVATE)
            .initializer("%P", builder.toString())
            .build()
}

private fun StringBuilder.appendParameters(parameters: GValuesList) {
    if(parameters.isEmpty()) {
        return
    }

    if(parameters.any { it.type == GType.Texture }) {
        appendln("struct Texture {")
        appendln("    sampler2D sampler;")
        appendln("    vec4 color;")
        appendln("};")
        appendln()
    }

    appendln("uniform struct Parameters {")
    for(parameter in parameters) {
        appendln("    ${type(parameter)} ${parameter.name};")
    }
    appendln("} parameters;")
    appendln()
}

private fun StringBuilder.appendFunctions(parameters: GValuesList) {
    if(parameters.any { it.type == GType.Texture }) {
        appendln("vec4 textureColor(Texture tex, vec2 uv) {")
        appendln("    return texture(tex.sampler, uv) * tex.color;")
        appendln("};")
        appendln()
    }
}

private fun buildClass(name: String, vsName: String, fsName: String, materialType: MaterialType): TypeSpec {
    val typeBuilder = TypeSpec.classBuilder(name)
    typeBuilder.apply {
        superclass(Material::class)
        addSuperclassConstructorParameter(vsName)
        addSuperclassConstructorParameter(fsName)

        for(parameter in materialType.parameters) {
            when(parameter.type) {
                GType.Boolean -> {
                    addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                            .build())
                    addProperty(PropertySpec.builder(parameter.name, Boolean::class)
                            .mutable()
                            .initializer("false")
                            .build())
                }
                GType.Int -> {
                    addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                            .build())
                    addProperty(PropertySpec.builder(parameter.name, Int::class)
                            .mutable()
                            .initializer("0")
                            .build())
                }
                GType.Float -> {
                    addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                            .build())
                    addProperty(PropertySpec.builder(parameter.name, Float::class)
                            .mutable()
                            .initializer("0f")
                            .build())
                }
                GType.Vector2 -> {
                    addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                            .build())
                    addProperty(PropertySpec.builder(parameter.name, Vector2::class)
                            .initializer("Vector2()")
                            .build())
                }
                GType.Vector3 -> {
                    addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                            .build())
                    addProperty(PropertySpec.builder(parameter.name, Vector3::class)
                            .initializer("Vector3()")
                            .build())
                }
                GType.Vector4 -> {
                    addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                            .build())
                    addProperty(PropertySpec.builder(parameter.name, Vector4::class)
                            .initializer("Vector4()")
                            .build())
                }
                GType.Point2D -> {
                    addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                            .build())
                    addProperty(PropertySpec.builder(parameter.name, Point2D::class)
                            .initializer("Point2D()")
                            .build())
                }
                GType.Point3D -> {
                    addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                            .build())
                    addProperty(PropertySpec.builder(parameter.name, Point3D::class)
                            .initializer("Point3D()")
                            .build())
                }
                GType.Color -> {
                    addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                            .build())
                    addProperty(PropertySpec.builder(parameter.name, ColorFloat::class)
                            .initializer("ColorFloat()")
                            .build())
                }
                GType.Matrix2 -> {
                    addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                            .build())
                    addProperty(PropertySpec.builder(parameter.name, Matrix2::class)
                            .initializer("Matrix2()")
                            .build())
                }
                GType.Matrix3 -> {
                    addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                            .build())
                    addProperty(PropertySpec.builder(parameter.name, Matrix3::class)
                            .initializer("Matrix3()")
                            .build())
                }
                GType.Matrix4 -> {
                    addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                            .build())
                    addProperty(PropertySpec.builder(parameter.name, Matrix4::class)
                            .initializer("Matrix4()")
                            .build())
                }
                GType.Texture -> {
                    addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}.sampler\")")
                            .build())
                    addProperty(PropertySpec.builder("_${parameter.name}", Texture::class)
                            .mutable()
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("Textures.white")
                            .build())
                    addProperty(PropertySpec.builder(parameter.name, Texture::class.asTypeName().copy(nullable = true))
                            .mutable()
                            .getter(FunSpec.getterBuilder().addStatement("return if(_${parameter.name} != Textures.white) _${parameter.name} else null").build())
                            .setter(FunSpec.setterBuilder().addParameter("value", Texture::class).addStatement("_${parameter.name} = value ?: Textures.white").build())
                            .build())
                    addProperty(PropertySpec.builder("${parameter.name}ColorLocation", Int::class)
                            .addModifiers(KModifier.PRIVATE)
                            .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}.color\")")
                            .build())
                    addProperty(PropertySpec.builder("${parameter.name}Color", ColorFloat::class)
                            .initializer("ColorFloat()")
                            .build())
                }
            }
        }

        if(materialType.parameters.any { it.type == GType.Texture }) {
            val builder = StringBuilder()
            builder.appendln("GL.useProgram(program)")
            var slot = 0
            for(parameter in materialType.parameters) {
                if(parameter.type != GType.Texture) {
                    continue
                }
                builder.appendln("GL.setUniformInt(${parameter.name}Location, ${slot++})")
            }
            addInitializerBlock(CodeBlock.of(builder.toString()))
        }

        if(!materialType.parameters.isEmpty()) {
            val funBuilder = FunSpec.builder("setParameters")
            funBuilder.addModifiers(KModifier.OVERRIDE)
            var textureSlot = 0
            for(parameter in materialType.parameters) {
                when(parameter.type) {
                    GType.Boolean -> {
                        funBuilder.addStatement("GL.setUniformBoolean(${parameter.name}Location, ${parameter.name})")
                    }
                    GType.Int -> {
                        funBuilder.addStatement("GL.setUniformInt(${parameter.name}Location, ${parameter.name})")
                    }
                    GType.Float -> {
                        funBuilder.addStatement("GL.setUniformFloat(${parameter.name}Location, ${parameter.name})")
                    }
                    GType.Vector2 -> {
                        funBuilder.addStatement("GL.setUniformVector(${parameter.name}Location, ${parameter.name})")
                    }
                    GType.Vector3 -> {
                        funBuilder.addStatement("GL.setUniformVector(${parameter.name}Location, ${parameter.name})")
                    }
                    GType.Vector4 -> {
                        funBuilder.addStatement("GL.setUniformVector(${parameter.name}Location, ${parameter.name})")
                    }
                    GType.Point2D -> {
                        funBuilder.addStatement("GL.setUniformPoint(${parameter.name}Location, ${parameter.name})")
                    }
                    GType.Point3D -> {
                        funBuilder.addStatement("GL.setUniformPoint(${parameter.name}Location, ${parameter.name})")
                    }
                    GType.Color -> {
                        funBuilder.addStatement("GL.setUniformColor(${parameter.name}Location, ${parameter.name})")
                    }
                    GType.Matrix2 -> {
                        funBuilder.addStatement("GL.setUniformMatrix(${parameter.name}Location, ${parameter.name})")
                    }
                    GType.Matrix3 -> {
                        funBuilder.addStatement("GL.setUniformMatrix(${parameter.name}Location, ${parameter.name})")
                    }
                    GType.Matrix4 -> {
                        funBuilder.addStatement("GL.setUniformMatrix(${parameter.name}Location, ${parameter.name})")
                    }
                    GType.Texture -> {
                        funBuilder.addStatement("val ${parameter.name} = ${parameter.name}")
                        funBuilder.beginControlFlow("if(${parameter.name} != null) {")
                        funBuilder.addStatement("GL.setUniformColor(${parameter.name}ColorLocation, Colors.white)")
                        funBuilder.nextControlFlow("else")
                        funBuilder.addStatement("GL.setUniformColor(${parameter.name}ColorLocation, ${parameter.name}Color)")
                        funBuilder.endControlFlow()
                        funBuilder.addStatement("GL.bindTexture(_${parameter.name}.name, ${textureSlot++})")
                    }
                }
            }
            addFunction(funBuilder.build())
        }
    }

    return typeBuilder.build()
}

private fun saveFile(typeSpec: TypeSpec) {
    val fileBuilder = FileSpec.builder("org.rufousengine.graphics.internal", "Materials")
    fileBuilder.addImport("org.rufousengine.system", "GL")
    fileBuilder.addType(typeSpec).build().writeTo(Files.local("../src/main/kotlin").native)
}

private val VertexAttribute.glslLayoutName
    get() = "a${name.capitalize()}"
private val VertexAttribute.glslType
    get() = "vec$size"

private fun type(value: GValue) = when(value.type) {
    GType.Boolean -> "int"
    GType.Int -> "int"
    GType.Float -> "float"
    GType.Vector2 -> "vec2"
    GType.Vector3 -> "vec3"
    GType.Vector4 -> "vec4"
    GType.Point2D -> "vec2"
    GType.Point3D -> "vec3"
    GType.Color -> "vec4"
    GType.Matrix2 -> "mat2"
    GType.Matrix3 -> "mat3"
    GType.Matrix4 -> "mat4"
    GType.Texture -> "Texture"
}

fun main() {
    val materialsBuilder = TypeSpec.objectBuilder("Materials")

    for(javaType in Reflections().getSubTypesOf(MaterialType::class.java)) {
        val type = javaType.kotlin
        val instance = type.objectInstance ?: continue
        val name = type.simpleName ?: continue

        val vsSpec = buildVertexShader(name, instance.model, instance.requires, instance.parameters, instance.vertexShader, instance.fragmentShader.inputs)
        val fsSpec = buildFragmentShader(name, instance.model, instance.requires, instance.parameters, instance.fragmentShader)
        val typeSpec = buildClass(name, vsSpec.name, fsSpec.name, instance)

        materialsBuilder.addProperty(vsSpec)
                .addProperty(fsSpec)
                .addType(typeSpec)
    }

    val materialsSpec = materialsBuilder.build()
    saveFile(materialsSpec)
}