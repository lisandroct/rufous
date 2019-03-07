package org.rufousengine.events

import kotlin.reflect.KClass

object EventSystem {
    private val manager: HashMap<KClass<out Any>, EventManager<out Any>> = hashMapOf()

    fun <T: Any> subscribe(type: KClass<T>, handler: (T) -> Unit) {
        val manager = getManager(type)
        manager.subscribe(handler)
    }

    fun <T: Any> dispatch(type: KClass<T>, event: T) {
        val manager = getManager(type)
        manager.dispatch(event)
    }

    private fun <T: Any> getManager(type: KClass<T>): EventManager<T> {
        val manager: EventManager<T>
        if(this.manager.containsKey(type)) {
            manager = this.manager[type] as EventManager<T>
        } else {
            manager = EventManager()
            this.manager[type] = manager
        }

        return manager
    }
}

inline fun <reified T: Any> subscribeTo(noinline handler: (T) -> Unit) = EventSystem.subscribe(T::class, handler)

inline fun <reified T: Any> dispatch(event: T) = EventSystem.dispatch(T::class, event)