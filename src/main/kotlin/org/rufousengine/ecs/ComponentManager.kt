package org.rufousengine.ecs

import org.rufousengine.Resources
import org.rufousengine.events.Event2
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/**
 * Manager of an specific type of component.
 */
class ComponentManager<E: Component>(internal val bitIndex: Int, private val type: KClass<E>) {
    private val list = mutableListOf<E>()
    private val free = mutableListOf<Int>()

    private val indicesByEntity = hashMapOf<Entity, Int>()

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
        entity.componentBits.set(bitIndex)

        Resources.register(component)
        ComponentAddedEvent(entity, component)

        return component
    }

    fun get(entity: Entity): E? {
        val index = indicesByEntity[entity] ?: return null
        return list[index]
    }

    fun remove(entity: Entity): E? {
        val index = indicesByEntity.remove(entity) ?: return null
        entity.componentBits.clear(bitIndex)

        val component = list[index]

        free.add(index)

        Resources.unregister(component)
        ComponentRemovedEvent(entity, component)

        return component
    }
}

object ComponentAddedEvent : Event2<Entity, Component>()
object ComponentRemovedEvent : Event2<Entity, Component>()