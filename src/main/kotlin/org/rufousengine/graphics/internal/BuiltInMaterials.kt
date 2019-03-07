package org.rufousengine.graphics.internal

object Error : MaterialType(ShadingModels.unlit, {
    fragmentShader {
        glsl = """
            void fragment() {
                properties.color = vec4(1.0, 0.0, 1.0, 1.0);
            }
        """.trimIndent()
    }
})

object DebugQuad : MaterialType(ShadingModels.unlit, {
    requires { uv }

    parameters {
        texture("texture")
        color("tint")
    }

    fragmentShader {
        glsl = """
            void fragment() {
                properties.color = textureColor(parameters.texture, uv) * parameters.tint;
            }
        """.trimIndent()
    }
})

object Unlit : MaterialType(ShadingModels.unlit, {
    requires { uv }

    parameters {
        texture("texture")
    }

    fragmentShader {
        glsl = """
        void fragment() {
            properties.color = textureColor(parameters.texture, uv);
        }
    """.trimIndent()
    }
})

object Phong : MaterialType(ShadingModels.phong, {
    requires {
        normal
        uv
    }

    parameters {
        point3D("cameraPosition")
        color("lightColor")
        point3D("lightPosition")
        texture("texture")
    }

    fragmentShader {
        glsl = """
            vec3 ambient() {
                float ambientStrength = 0.4;

                return ambientStrength * parameters.lightColor.rgb;
            }

            vec3 diffuse() {
                vec3 norm = normalize(normal);
                vec3 lightDirection = normalize(parameters.lightPosition - position);

                float diff = max(dot(norm, lightDirection), 0.0);

                return diff * parameters.lightColor.rgb;
            }

            vec3 specular() {
                float specularStrength = 0.5;

                vec3 norm = normalize(normal);
                vec3 lightDirection = normalize(parameters.lightPosition - position);

                vec3 viewDirection = normalize(parameters.cameraPosition - position);
                vec3 reflectDirection = reflect(-lightDirection, norm);
                float spec = pow(max(dot(viewDirection, reflectDirection), 0.0), 32);

                return specularStrength * spec * parameters.lightColor.rgb;
            }

            void fragment() {
                properties.ambient = ambient();
                properties.diffuse = diffuse();
                properties.specular = specular();
                properties.objectColor = textureColor(parameters.texture, uv);
            }
        """.trimIndent()
    }
})

object Gouraud : MaterialType(ShadingModels.phong, {
    requires {
        normal
        uv
    }

    parameters {
        point3D("cameraPosition")
        color("lightColor")
        point3D("lightPosition")
        texture("texture")
    }

    vertexShader {
        glsl = """
            vec3 ambient() {
                float ambientStrength = 0.4;

                return ambientStrength * parameters.lightColor.rgb;
            }

            vec3 diffuse() {
                vec3 norm = normalize(normal);
                vec3 lightDirection = normalize(parameters.lightPosition - position);

                float diff = max(dot(norm, lightDirection), 0.0);

                return diff * parameters.lightColor.rgb;
            }

            vec3 specular() {
                float specularStrength = 0.5;

                vec3 norm = normalize(normal);
                vec3 lightDirection = normalize(parameters.lightPosition - position);

                vec3 viewDirection = normalize(parameters.cameraPosition - position);
                vec3 reflectDirection = reflect(-lightDirection, norm);
                float spec = pow(max(dot(viewDirection, reflectDirection), 0.0), 32);

                return specularStrength * spec * parameters.lightColor.rgb;
            }

            void vertex() {
                fs.ambient = ambient();
                fs.diffuse = diffuse();
                fs.specular = specular();
            }
        """.trimIndent()
    }

    fragmentShader {
        inputs {
            vector3("ambient")
            vector3("diffuse")
            vector3("specular")
        }
        glsl = """
            void fragment() {
                properties.ambient = fs.ambient;
                properties.diffuse = fs.diffuse;
                properties.specular = fs.specular;
                properties.objectColor = textureColor(parameters.texture, uv);
            }
        """.trimIndent()
    }
})