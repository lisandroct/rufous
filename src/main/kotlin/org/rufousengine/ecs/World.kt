package org.rufousengine.ecs

import com.lisandroct.app.systems.RotatorSystem
import com.lisandroct.app.systems.SinusoidalSystem
import org.rufousengine.Resources
import org.rufousengine.systems.DebugSystem
import org.rufousengine.systems.RenderingSystem
import org.rufousengine.events.Event1
import kotlin.reflect.KClass

object World {
    private val entities = mutableListOf<Entity>()
    private val freeEntities = mutableListOf<Int>()
    private val freeEntitiesSet = hashSetOf<Int>()

    private val systems = mutableListOf<System>()

    private val componentManagers = hashMapOf<KClass<out Component>, ComponentManager<out Component>>()
    private val componentTypes = mutableListOf<KClass<out Component>>()

    private val entitiesByComponent = hashMapOf<Long, Int>()

    fun subscribeSystem(system: System) = systems.add(system)

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
        EntityAddedEvent(entity)

        return entity
    }

    fun destroyEntity(entity: Entity): Boolean {
        val index = entity.index
        if(freeEntitiesSet.contains(index)) {
            return false
        }

        for(type in componentTypes) {
            removeComponent(entity, type)
        }

        freeEntities.add(index)
        freeEntitiesSet.add(index)

        Resources.unregister(entity)
        EntityDestroyedEvent(entity)

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
        component.active = true
        entitiesByComponent[component.id] = entity.index

        return component
    }

    fun <T: Component> removeComponent(entity: Entity, type: KClass<T>): Boolean {
        val manager = getManager(type)

        val component = manager.remove(entity) ?: return false
        component.active = false
        entitiesByComponent.remove(component.id)

        ComponentRemovedEvent(entity, component)

        return true
    }

    fun getEntity(component: Component) : Entity? {
        val index = entitiesByComponent[component.id] ?: return null
        return entities[index]
    }

    internal fun getBitIndex(type: KClass<out Component>): Int {
        val manager = getManager(type)
        return manager.bitIndex
    }

    internal fun removeComponent(component: Component): Boolean {
        val entity = getEntity(component) ?: return false

        return removeComponent(entity, component::class)
    }

    private fun <T: Component> getManager(type: KClass<T>): ComponentManager<T> {
        val manager: ComponentManager<T>
        if(componentManagers.containsKey(type)) {
            manager = componentManagers[type] as ComponentManager<T>
        } else {
            manager = ComponentManager(componentTypes.size, type)
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
inline fun <reified T: Component> Entity.remove() = World.removeComponent(this, T::class)

inline val Component.entity
    get() = World.getEntity(this)

object EntityAddedEvent : Event1<Entity>()
object EntityDestroyedEvent : Event1<Entity>()