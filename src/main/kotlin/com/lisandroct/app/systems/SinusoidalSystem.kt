package com.lisandroct.app.systems

import com.lisandroct.app.components.Sinusoidal
import org.rufousengine.ecs.*
import org.rufousengine.ecs.components.Transform
import org.rufousengine.math.cos
import org.rufousengine.math.sin

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

            transform.position.y = cos(accum * sinusoidal.speed) * sinusoidal.amplitude
        }
    }
}