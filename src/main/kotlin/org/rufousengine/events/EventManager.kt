package org.rufousengine.events

class EventManager<E: Any> {
    private val handlers = mutableListOf<(E) -> Unit>()

    fun subscribe(body: (E) -> Unit) {
        handlers.add(body)
    }

    fun dispatch(event: E) {
        for(subscriber in handlers) {
            subscriber(event)
        }
    }
}