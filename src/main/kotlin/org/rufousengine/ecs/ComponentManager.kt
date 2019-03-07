package org.rufousengine.ecs

import org.rufousengine.events.dispatch
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class ComponentManager<E: Component>(private val type: KClass<E>) {
    private val list = mutableListOf<E>()
    private val free = mutableListOf<Int>()

    private val indicesMap = hashMapOf<Entity, Int>()

    fun get(entity: Entity): E? {
        val index = indicesMap[entity] ?: return null
        return list[index]
    }

    fun add(entity: Entity): E? {
        if(indicesMap.containsKey(entity)) {
            return null
        }

        val index: Int
        if(free.isNotEmpty()) {
            index = free.removeAt(0)
        } else {
            index = list.size
            list.add(type.createInstance())
        }

        indicesMap[entity] = index

        dispatch<ComponentEvent>(ComponentEvent.Added(entity, list[index]))

        return list[index]
    }

    fun remove(entity: Entity): Boolean {
        val index = indicesMap.remove(entity) ?: return false

        free.add(index)

        dispatch<ComponentEvent>(ComponentEvent.Removed(entity, list[index]))

        return true
    }
}

sealed class ComponentEvent {
    lateinit var entity: Entity
        protected set
    lateinit var component: Component
        protected set

    object Added : ComponentEvent() {
        operator fun invoke(entity: Entity, component: Component): Added {
            this.entity = entity
            this.component = component

            return this
        }
    }

    object Removed : ComponentEvent() {
        operator fun invoke(entity: Entity, component: Component): Removed {
            this.entity = entity
            this.component = component

            return this
        }
    }
}