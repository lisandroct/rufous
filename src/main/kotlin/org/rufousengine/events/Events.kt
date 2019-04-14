package org.rufousengine.events

sealed class Event<T: Function<Unit>> {
    protected val handlers = mutableListOf<T>()

    operator fun plusAssign(handler: T) {
        handlers.add(handler)
    }

    operator fun minusAssign(handler: T) {
        handlers.remove(handler)
    }
}

open class Event0 : Event<() -> Unit>() {
    operator fun invoke() {
        for(handler in handlers) {
            handler()
        }
    }
}

open class Event1<A> : Event<(A) -> Unit>() {
    operator fun invoke(a: A) {
        for(handler in handlers) {
            handler(a)
        }
    }
}

open class Event2<A, B> : Event<(A, B) -> Unit>() {
    operator fun invoke(a: A, b: B) {
        for(handler in handlers) {
            handler(a, b)
        }
    }
}

open class Event3<A, B, C> : Event<(A, B, C) -> Unit>() {
    operator fun invoke(a: A, b: B, c: C) {
        for(handler in handlers) {
            handler(a, b, c)
        }
    }
}

open class Event4<A, B, C, D> : Event<(A, B, C, D) -> Unit>() {
    operator fun invoke(a: A, b: B, c: C, d: D) {
        for(handler in handlers) {
            handler(a, b, c, d)
        }
    }
}

open class Event5<A, B, C, D, E> : Event<(A, B, C, D, E) -> Unit>() {
    operator fun invoke(a: A, b: B, c: C, d: D, e: E) {
        for(handler in handlers) {
            handler(a, b, c, d, e)
        }
    }
}