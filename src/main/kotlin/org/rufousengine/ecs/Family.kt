package org.rufousengine.ecs

import org.rufousengine.utils.Bits
import kotlin.reflect.KClass

class Family(all: Array<KClass<out Component>>, one: Array<KClass<out Component>>? = null, exclude: Array<KClass<out Component>>? = null) : Iterable<Entity> {
    private val all: Bits = Bits()
    private val one: Bits = Bits()
    private val exclude: Bits = Bits()

    private val entities = mutableListOf<Entity>()
    private val entitiesSet = hashSetOf<Long>()

    init {
        for(type in all) {
            this.all.set(World.getBitIndex(type))
        }
        if(one != null) {
            for(type in one) {
                this.one.set(World.getBitIndex(type))
            }
        }
        if(exclude != null) {
            for(type in exclude) {
                this.exclude.set(World.getBitIndex(type))
            }
        }
    }

    fun subscribe() {
        ComponentAddedEvent += ::onEntityChange
        ComponentRemovedEvent += ::onEntityChange
    }

    fun unsubscribe() {
        ComponentAddedEvent -= ::onEntityChange
        ComponentRemovedEvent -= ::onEntityChange
    }

    private fun matches(entity: Entity) : Boolean {
        val entityComponentBits = entity.componentBits

        if (!entityComponentBits.containsAll(all)) {
            return false
        }

        if (!one.isEmpty && !one.intersects(entityComponentBits)) {
            return false
        }

        if (!exclude.isEmpty && exclude.intersects(entityComponentBits)) {
            return false
        }

        return true
    }

    private fun onEntityChange(entity: Entity, component: Component) {
        val id = entity.id
        if(matches(entity)) {
            if(!entitiesSet.contains(id)) {
                entities.add(entity)
                entitiesSet.add(id)
            }
        } else {
            if(entitiesSet.contains(id)) {
                entities.remove(entity)
                entitiesSet.remove( id)
            }
        }
    }

    override fun iterator() = entities.iterator()

    operator fun get(index: Int) = entities[index]
}