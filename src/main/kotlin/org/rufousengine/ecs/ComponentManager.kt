package org.rufousengine.ecs

import org.rufousengine.Resources
import org.rufousengine.events.Event1
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/**
 * Manager of an specific type of component.
 */
class ComponentManager<E: Component>(private val type: KClass<E>) {
    private val list = mutableListOf<E>()
    private val free = mutableListOf<Int>()

    private val indicesByEntity = hashMapOf<Entity, Int>()

    fun get(entity: Entity): E? {
        val index = indicesByEntity[entity] ?: return null
        return list[index]
    }

    fun add(entity: Entity): E? {
        if(indicesByEntity.containsKey(entity)) {
            return null
        }

        val index: Int
        if(free.isNotEmpty()) {
            index = free.removeAt(0)
        } else {
            index = list.size
            list.add(type.createInstance())
        }

        val component = list[index]

        indicesByEntity[entity] = index

        Resources.register(component)

        return component
    }

    fun remove(entity: Entity): E? {
        val index = indicesByEntity.remove(entity) ?: return null
        val component = list[index]

        free.add(index)

        Resources.unregister(component)

        return component
    }
}