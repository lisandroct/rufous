package org.rufousengine

import kotlin.reflect.KClass
import kotlin.reflect.full.safeCast

/**
 * The central core of all the resources in the engine. Its [Resources] responsibility to save a copy of all active
 * resources and unregister and dispose native resources.
 */
object Resources {
    private val resources = hashMapOf<Long, Resource>()
    private var nextId = 0L

    /** Generate an unused ID. */
    fun generateId() = nextId++

    /**
     * Registers [resource] and saves a copy. A registered [Resource] means it's active and in use.
     *
     * Only use this if you know what you're doing.
     */
    fun register(resource: Resource): Boolean {
        if(resources.containsKey(resource.id)) {
            return false
        }

        resources[resource.id] = resource

        return true
    }

    /** Returns the registered [Resource] with the specified [id]. */
    fun <T: Resource> get(id: Long, type: KClass<T>): T? {
        val resource = resources[id] ?: return null

        return type.safeCast(resource)
    }

    /**
     * Unregisters [resource] and destroys its copy. A registered [Resource] means it's active and in use.
     * If [resource] is [Disposable], [Disposable.dispose] will be automatically called.
     *
     * Only use this if you know what you're doing.
     */
    fun unregister(resource: Resource) = Resources.unregister(resource.id)

    private fun unregister(id: Long): Boolean {
        val resource = resources[id] ?: return false

        resources.remove(id)

        if(resource is Disposable) {
            resource.dispose()
        }

        return true
    }

    /** Unregisters all active resources. */
    fun destroyAll() {
        for((_, resource) in resources) {
            if(resource is Disposable) {
                resource.dispose()
            }
        }

        resources.clear()
    }
}

/**
 * A not automatically managed resource. Its developers responsibility to call [Resources.register] and [Resources.unregister].
 */
abstract class Resource {
    val id = Resources.generateId()

    /** Called when the resource will not be used nor processed any more. */
    abstract fun destroy()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Resource) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

/**
 * An automatically managed resource. It's registered and unregistered from [Resources] automatically when created and when [destroy] is called.
 */
abstract class ManagedResource : Resource() {
    init {
        Resources.register(this)
    }

    final override fun destroy() {
        Resources.unregister(this)
    }
}