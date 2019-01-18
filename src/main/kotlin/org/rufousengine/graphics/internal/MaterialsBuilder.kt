package org.rufousengine.graphics.internal

import com.squareup.kotlinpoet.*
import org.rufousengine.graphics.Material
import org.rufousengine.graphics.Texture
import org.rufousengine.graphics.VertexAttribute
import org.rufousengine.math.*
import org.reflections.Reflections
import org.rufousengine.files.Files
import java.lang.IllegalArgumentException


object MaterialsBuilder {
    fun buildMaterials() {
        val fileBuilder = FileSpec.builder("org.rufousengine.graphics.internal", "Materials")
        fileBuilder.addImport("org.rufousengine.system", "GL")

        val parentType = TypeSpec.objectBuilder("Materials")

        for(javaType in Reflections().getSubTypesOf(MaterialType::class.java)) {
            val type = javaType.kotlin
            val instance = type.objectInstance ?: continue
            val name = type.simpleName ?: continue

            val vsPropertySpec = PropertySpec.builder("vs$name", String::class)
                    .addModifiers(KModifier.PRIVATE)
                    .initializer("%P", buildVertexShader(instance))
                    .build()
            val fsPropertySpec = PropertySpec.builder("fs$name", String::class)
                    .addModifiers(KModifier.PRIVATE)
                    .initializer("%P", buildFragmentShader(instance))
                    .build()

            val typeSpec = buildMaterial(name, instance, vsPropertySpec, fsPropertySpec)

            parentType.addProperty(vsPropertySpec)
                    .addProperty(fsPropertySpec)
                    .addType(typeSpec)
        }

        fileBuilder.addType(parentType.build()).build().writeTo(Files.local("../src/main/kotlin").native)
    }

    private fun buildMaterial(name: String, materialType: MaterialType, vsProperty: PropertySpec, fsProperty: PropertySpec): TypeSpec {
        val typeBuilder = TypeSpec.classBuilder(name)
        typeBuilder.apply {
            superclass(Material::class)
            addSuperclassConstructorParameter(vsProperty.name)
            addSuperclassConstructorParameter(fsProperty.name)

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
                        addProperty(PropertySpec.builder(parameter.name, Vector2::class)
                                .initializer("Vector3()")
                                .build())
                    }
                    GType.Vector4 -> {
                        addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                                .addModifiers(KModifier.PRIVATE)
                                .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                                .build())
                        addProperty(PropertySpec.builder(parameter.name, Vector2::class)
                                .initializer("Vector4()")
                                .build())
                    }
                    GType.Point2D -> {
                        addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                                .addModifiers(KModifier.PRIVATE)
                                .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                                .build())
                        addProperty(PropertySpec.builder(parameter.name, Vector2::class)
                                .initializer("Point2D()")
                                .build())
                    }
                    GType.Point3D -> {
                        addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                                .addModifiers(KModifier.PRIVATE)
                                .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                                .build())
                        addProperty(PropertySpec.builder(parameter.name, Vector2::class)
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
                        addProperty(PropertySpec.builder(parameter.name, Vector2::class)
                                .initializer("Matrix2()")
                                .build())
                    }
                    GType.Matrix3 -> {
                        addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                                .addModifiers(KModifier.PRIVATE)
                                .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                                .build())
                        addProperty(PropertySpec.builder(parameter.name, Vector2::class)
                                .initializer("Matrix3()")
                                .build())
                    }
                    GType.Matrix4 -> {
                        addProperty(PropertySpec.builder("${parameter.name}Location", Int::class)
                                .addModifiers(KModifier.PRIVATE)
                                .initializer("GL.getUniformLocation(program, \"parameters.${parameter.name}\")")
                                .build())
                        addProperty(PropertySpec.builder(parameter.name, Vector2::class)
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
                            funBuilder.addStatement("GL.bindTexture(_${parameter.name}.id, ${textureSlot++})")
                        }
                    }
                }
                addFunction(funBuilder.build())
            }
        }

        return typeBuilder.build()
    }

    private fun buildVertexShader(materialType: MaterialType): String {
        val builder = StringBuilder()
        builder.appendln("#version 330 core")
        for(attribute in materialType.requires) {
            builder.appendln("layout (location = ${attribute.index}) in ${type(attribute)} ${layoutName(attribute)};")
        }
        builder.appendln()
        builder.appendln("uniform mat4 uWorld;")
        builder.appendln("uniform mat4 uView;")
        builder.appendln("uniform mat4 uProjection;")
        builder.appendln()
        if(materialType.requires.size > 1) {
            for (attribute in materialType.requires) {
                if(attribute == VertexAttribute.position) {
                    continue
                }
                builder.appendln("out ${type(attribute)} ${attribute.name};")
            }
            builder.appendln()
        }
        builder.appendln("void main()")
        builder.appendln("{")
        if(materialType.requires.size > 1) {
            for (attribute in materialType.requires) {
                if(attribute == VertexAttribute.position) {
                    continue
                }
                builder.appendln("    ${attribute.name} = ${layoutName(attribute)};")
            }
        }
        builder.appendln("    gl_Position = uProjection * uView * uWorld * vec4(aPosition, 1.0f);")
        builder.appendln("}")

        return builder.toString()
    }

    private fun buildFragmentShader(materialType: MaterialType): String {
        if(materialType.model == null) {
            throw IllegalArgumentException("${materialType::class.simpleName}'s model is null.")
        }

        val builder = StringBuilder()
        builder.appendln("#version 330 core")
        builder.appendln()
        if(materialType.requires.size > 1) {
            for (attribute in materialType.requires) {
                if(attribute == VertexAttribute.position) {
                    continue
                }
                builder.appendln("in ${type(attribute)} ${attribute.name};")
            }
        }
        builder.appendln("out vec4 color;")
        builder.appendln()
        if(materialType.parameters.any { it.type == GType.Texture }) {
            builder.appendln("struct Texture {")
            builder.appendln("    sampler2D sampler;")
            builder.appendln("    vec4 color;")
            builder.appendln("};")
            builder.appendln()
        }
        if(materialType.model!!.properties.isNotEmpty()) {
            builder.appendln("struct Properties {")
            for (property in materialType.model!!.properties) {
                builder.appendln("    ${type(property)} ${property.name};")
            }
            builder.appendln("} material;")
            builder.appendln()
        }
        if(!materialType.parameters.isEmpty()) {
            builder.appendln("uniform struct Parameters {")
            for(parameter in materialType.parameters) {
                builder.appendln("    ${type(parameter)} ${parameter.name};")
            }
            builder.appendln("} parameters;")
            builder.appendln()
        }
        if(materialType.parameters.any { it.type == GType.Texture }) {
            builder.appendln("vec4 textureColor(Texture tex, vec2 uv) {")
            builder.appendln("    return texture(tex.sampler, uv) * tex.color;")
            builder.appendln("};")
            builder.appendln()
        }
        if(materialType.fragment != null) {
            builder.appendln("void fragment() {")
            builder.appendln(materialType.fragment!!.prependIndent("    "))
            builder.appendln("}")
            builder.appendln()
        }
        builder.appendln("void shade() {")
        builder.appendln(materialType.model!!.function!!.prependIndent("    "))
        builder.appendln("}")
        builder.appendln()
        builder.appendln("void main()")
        builder.appendln("{")
        if(materialType.fragment != null) {
            builder.appendln("    fragment();")
        }
        builder.appendln("    shade();")
        builder.appendln("}")

        return builder.toString()
    }

    private fun layoutName(attribute: VertexAttribute) = "a${attribute.name.capitalize()}"
    private fun type(attribute: VertexAttribute) = "vec${attribute.size}"
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
    private fun ktype(value: GValue) = when(value.type) {
        GType.Boolean -> Boolean::class
        GType.Int -> Int::class
        GType.Float -> Float::class
        GType.Vector2 -> Vector2::class
        GType.Vector3 -> Vector3::class
        GType.Vector4 -> Vector4::class
        GType.Point2D -> Point2D::class
        GType.Point3D -> Point3D::class
        GType.Color -> Color::class
        GType.Matrix2 -> Matrix2::class
        GType.Matrix3 -> Matrix4::class
        GType.Matrix4 -> Matrix4::class
        GType.Texture -> Texture::class
    }
}

fun main() {
    MaterialsBuilder.buildMaterials()
}