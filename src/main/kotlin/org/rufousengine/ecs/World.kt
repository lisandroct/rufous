package org.rufousengine.ecs

import org.rufousengine.events.dispatch
import kotlin.reflect.KClass

object World {
    private val entities = mutableListOf<Entity>()
    private val freeEntities = mutableListOf<Int>()
    private val freeEntitiesSet = mutableSetOf<Int>()
    private val systems = mutableListOf<System>()
    private val managers: HashMap<KClass<out Component>, ComponentManager<out Component>> = hashMapOf()

    fun createEntity(): Entity {
        val entity: Entity
        if(freeEntities.isNotEmpty()) {
            val id = freeEntities.removeAt(0)
            freeEntitiesSet.remove(id)
            entity = entities[id]
        } else {
            entity = Entity(entities.size)
            entities.add(entity)
        }

        dispatch<EntityEvent>(EntityEvent.Created(entity))

        return entity
    }

    fun destroyEntity(entity: Entity): Boolean {
        val id = entity.id
        if(freeEntitiesSet.contains(id)) {
            return false
        }

        freeEntities.add(id)
        freeEntitiesSet.add(id)

        dispatch<EntityEvent>(EntityEvent.Destroyed(entity))

        return true
    }

    fun <T: Component> addComponent(entity: Entity, type: KClass<T>) {
        val manager = getManager(type)
        manager.add(entity)
    }

    fun <T: Component> removeComponent(entity: Entity, type: KClass<T>) {
        val manager = getManager(type)
        manager.remove(entity)
    }

    private fun <T: Component> getManager(type: KClass<T>): ComponentManager<T> {
        val manager: ComponentManager<T>
        if(managers.containsKey(type)) {
            manager = managers[type] as ComponentManager<T>
        } else {
            manager = ComponentManager(type)
            managers[type] = manager
        }

        return manager
    }
}

sealed class EntityEvent {
    lateinit var entity: Entity
        protected set

    object Created : EntityEvent() {
        operator fun invoke(entity: Entity): Created {
            this.entity = entity

            return this
        }
    }

    object Destroyed : EntityEvent() {
        operator fun invoke(entity: Entity): Destroyed {
            this.entity = entity

            return this
        }
    }
}

fun Entity() = World.createEntity()
fun Entity.destroy() = World.destroyEntity(this)

inline fun <reified T: Component> Entity.add() = World.addComponent(this, T::class)
inline fun <reified T: Component> Entity.remove() = World.removeComponent(this, T::class)