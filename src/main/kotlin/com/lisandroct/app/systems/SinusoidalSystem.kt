package com.lisandroct.app.systems

import com.lisandroct.app.components.Sinusoidal
import org.rufousengine.ecs.*
import org.rufousengine.ecs.components.Transform
import org.rufousengine.math.cos
import org.rufousengine.math.sin

object SinusoidalSystem : System(1) {
    private val entities = mutableListOf<Entity>()

    private var accum = 0f

    init {
        EntityChangeEvent += ::onEntityChange
    }

    override fun update() {
        accum += 1
        for(entity in entities) {
            val transform = entity.getUnsafe<Transform>()
            val sinusoidal = entity.getUnsafe<Sinusoidal>()

            transform.position.y = cos(accum * sinusoidal.speed) * sinusoidal.amplitude
        }
    }

    private fun onEntityChange(entity: Entity) {
        val transform = entity.get<Transform>()
        val sinusoidal = entity.get<Sinusoidal>()

        if(transform == null || sinusoidal == null) {
            entities.remove(entity)
        } else {
            entities.add(entity)
        }
    }
}