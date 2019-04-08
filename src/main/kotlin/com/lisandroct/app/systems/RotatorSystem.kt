package com.lisandroct.app.systems

import com.lisandroct.app.components.Rotator
import org.rufousengine.ecs.*
import org.rufousengine.ecs.components.Transform

object RotatorSystem : System(1) {
    private val entities = mutableListOf<Entity>()

    init {
        EntityChangeEvent += ::onEntityChange
    }

    override fun update() {
        for(entity in entities) {
            val transform = entity.getUnsafe<Transform>()
            val rotator = entity.getUnsafe<Rotator>()

            transform.rotation.y += rotator.speed
        }
    }

    private fun onEntityChange(entity: Entity) {
        val transform = entity.get<Transform>()
        val rotator = entity.get<Rotator>()

        if(transform == null || rotator == null) {
            entities.remove(entity)
        } else {
            entities.add(entity)
        }
    }
}