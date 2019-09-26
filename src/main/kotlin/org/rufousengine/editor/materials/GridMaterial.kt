package org.rufousengine.editor.materials

import org.rufousengine.graphics.internal.MaterialType
import org.rufousengine.graphics.internal.ShadingModels

object Grid : MaterialType(ShadingModels.unlit, {
    parameters {
        point3D("center")
        point3D("cameraPosition")
        float("tfov")
        float("radius")
        float("distance")
        int("size")
    }

    fragmentShader {
        glsl = """             
            float grid(vec2 pos, float dist) {
                float hdist = dist / 2;
                float x = hdist - abs(pos.x - dist * floor(pos.x / dist) - hdist);
                float y = hdist - abs(pos.y - dist * floor(pos.y / dist) - hdist);
                return min(x, y);
            }
            
            float line(float value, float width) {
                if(value <= width) {
                    return 1;
                } else if(value <= width + 1) {
                    return 0.25;
                } else {
                    return 0;
                }
            }
            
            float fade(float distance, float a, float b) {
                if(distance < a) {
                    return 1.0;
                } else if(distance > b) {
                    return 0.0;
                }
                
                return 1 - (distance - a) / (b - a);
            }
            
            vec4 drawAxis(float value, vec3 color, float distance, float maxDistance, float perspectiveRatio) {
                float line = line(value * perspectiveRatio, 1);
                float fade = fade(distance, maxDistance, maxDistance * 2);
                
                return vec4(color, fade * line);
            }
            
            void fragment() {
                float distance = length(position - parameters.cameraPosition);
                float toScreen = 720 / (parameters.tfov * distance * 2);
                
                float a0 = line(grid(position.xz, parameters.distance / parameters.size) * toScreen, 1) * fade(distance, 25, 37.5);
                float a1 = line(grid(position.xz, parameters.distance) * toScreen, 1) * fade(distance, 40, 60);
                float a2 = line(grid(position.xz, parameters.distance * parameters.size) * toScreen, 1) * fade(distance, 60, 90);
                vec4 grid0 = vec4(0.25, 0.25, 0.25, a0 * 0.1);
                vec4 grid1 = vec4(0.25, 0.25, 0.25, a1 * 0.3);
                vec4 grid2 = vec4(0.25, 0.25, 0.25, a2 * 0.7);
                
                float a3 = line(abs(position.x) * toScreen, 1) * fade(distance, 90, 135);
                float a4 = line(abs(position.z) * toScreen, 1) * fade(distance, 90, 135);
                vec4 z = vec4(0.2, 0.2, 0.9, a3);
                vec4 x = vec4(0.9, 0.2, 0.2, a4);
                
                properties.color = mix(mix(mix(mix(grid0, grid1, grid1.a), grid2, grid2.a), z, z.a), x, x.a);
                
                properties.color.a *= fade(length(position - parameters.center), parameters.radius, parameters.radius * 2);
            }
        """.trimIndent()
    }
})