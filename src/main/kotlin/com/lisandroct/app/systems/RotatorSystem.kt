package com.lisandroct.app.systems

import com.lisandroct.app.components.Rotator
import org.rufousengine.ecs.*
import org.rufousengine.components.Transform
import org.rufousengine.math.ToEuler
import org.rufousengine.math.ToQuaternion
import org.rufousengine.math.Vector3
import org.rufousengine.math.plus

object RotatorSystem : System(1) {
    private val entities = Family(arrayOf(Transform::class, Rotator::class))

    init {
        entities.subscribe()
    }

    override fun update() {
        for(entity in entities) {
            val transform = entity.getUnsafe<Transform>()
            val rotator = entity.getUnsafe<Rotator>()

            val euler = ToEuler(transform.rotation)
            val rotation = euler + Vector3(0f, rotator.speed, 0f)
            transform.rotation = ToQuaternion(rotation)
        }
    }
}