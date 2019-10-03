package com.lisandroct.app.systems

import com.lisandroct.app.components.Sinusoidal
import org.rufousengine.ecs.*
import org.rufousengine.components.Transform
import org.rufousengine.math.Point3D
import org.rufousengine.math.Vector3
import org.rufousengine.math.cos

object SinusoidalSystem : System(1) {
    private val entities = Family(arrayOf(Transform::class, Sinusoidal::class))

    private var accum = 0f

    init {
        entities.subscribe()
    }

    override fun update() {
        accum += 1
        for(entity in entities) {
            val transform = entity.getUnsafe<Transform>()
            val sinusoidal = entity.getUnsafe<Sinusoidal>()

            transform.position = Point3D(transform.position.x, cos(accum * sinusoidal.speed) * sinusoidal.amplitude, transform.position.z)
        }
    }
}