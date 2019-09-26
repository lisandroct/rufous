package com.lisandroct.app.systems

import com.lisandroct.app.components.Rotator
import org.rufousengine.ecs.*
import org.rufousengine.components.Transform

object RotatorSystem : System(1) {
    private val entities = Family(arrayOf(Transform::class, Rotator::class))

    init {
        entities.subscribe()
    }

    override fun update() {
        for(entity in entities) {
            val transform = entity.getUnsafe<Transform>()
            val rotator = entity.getUnsafe<Rotator>()

            transform.rotation.y += rotator.speed
        }
    }
}