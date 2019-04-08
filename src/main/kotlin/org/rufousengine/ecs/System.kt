package org.rufousengine.ecs

abstract class System(val priority: Int) {
    var active = true

    abstract fun update()
}