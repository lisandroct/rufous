package org.rufousengine.events

object MouseClickEvent {
    var x = 0f
        private set
    var y = 0f
        private set

    operator fun invoke(x: Float, y: Float): MouseClickEvent {
        this.x = x
        this.y = y

        return this
    }
}