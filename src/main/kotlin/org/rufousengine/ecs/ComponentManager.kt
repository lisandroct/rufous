package org.rufousengine.ecs

import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class ComponentManager<E: Component>(private val type: KClass<E>) {
    //private val components = arrayOfNulls<E>(2048)

    private val entitiesMap = hashMapOf<Entity, E>()

    fun get(entity: Entity) = entitiesMap[entity]
    fun add(entity: Entity) {
        entitiesMap[entity] = type.createInstance()
    }
    fun remove(entity: Entity) = entitiesMap.remove(entity)
}