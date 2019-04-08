package org.rufousengine.ecs

import com.lisandroct.app.systems.RotatorSystem
import org.rufousengine.Resources
import org.rufousengine.ecs.systems.RenderingSystem
import org.rufousengine.events.Event1
import kotlin.reflect.KClass

object World {
    private val entities = mutableListOf<Entity>()
    private val freeEntities = mutableListOf<Int>()
    private val freeEntitiesSet = hashSetOf<Int>()

    private val systems = mutableListOf<System>()

    private val componentManagers = hashMapOf<KClass<out Component>, ComponentManager<out Component>>()
    private val componentTypes = mutableListOf<KClass<out Component>>()

    private val entitiesByComponent = hashMapOf<Component, Entity>()

    init {
        systems.add(RenderingSystem)
        systems.add(RotatorSystem)
    }

    fun update() {
        for(system in systems) {
            system.update()
        }
    }

    fun createEntity(): Entity {
        val entity: Entity
        if(freeEntities.isNotEmpty()) {
            val index = freeEntities.removeAt(0)
            freeEntitiesSet.remove(index)
            entity = entities[index]
        } else {
            entity = Entity(entities.size)
            entities.add(entity)
        }

        Resources.register(entity)

        return entity
    }

    fun destroyEntity(entity: Entity): Boolean {
        val index = entity.index
        if(freeEntitiesSet.contains(index)) {
            return false
        }

        for(type in componentTypes) {
            destroyComponent(entity, type)
        }

        freeEntities.add(index)
        freeEntitiesSet.add(index)

        Resources.unregister(entity)

        return true
    }

    fun <T: Component> getComponent(entity: Entity, type: KClass<T>): T? {
        val manager = getManager(type)
        return manager.get(entity)
    }

    fun <T: Component> getComponentUnsafe(entity: Entity, type: KClass<T>): T {
        val manager = getManager(type)
        return manager.get(entity)!!
    }

    fun <T: Component> addComponent(entity: Entity, type: KClass<T>): T? {
        val manager = getManager(type)

        val component = manager.add(entity) ?: return null
        entitiesByComponent[component] = entity

        EntityChangeEvent(entity)

        return component
    }

    fun <T: Component> destroyComponent(entity: Entity, type: KClass<T>): Boolean {
        val manager = getManager(type)

        val component = manager.remove(entity) ?: return false
        entitiesByComponent.remove(component)

        EntityChangeEvent(entity)

        return true
    }

    internal fun destroyComponent(component: Component): Boolean {
        val entity = entitiesByComponent[component] ?: return false

        return destroyComponent(entity, component::class)
    }

    private fun <T: Component> getManager(type: KClass<T>): ComponentManager<T> {
        val manager: ComponentManager<T>
        if(componentManagers.containsKey(type)) {
            manager = componentManagers[type] as ComponentManager<T>
        } else {
            manager = ComponentManager(type)
            componentTypes.add(type)
            componentManagers[type] = manager
        }

        return manager
    }
}

fun Entity() = World.createEntity()

inline fun <reified T: Component> Entity.get() = World.getComponent(this, T::class)
inline fun <reified T: Component> Entity.getUnsafe() = World.getComponentUnsafe(this, T::class)
inline fun <reified T: Component> Entity.add() = World.addComponent(this, T::class)
inline fun <reified T: Component> Entity.remove() = World.destroyComponent(this, T::class)

object EntityChangeEvent : Event1<Entity>()