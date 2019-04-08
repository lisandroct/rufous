package org.rufousengine.ecs

import org.rufousengine.Resource

/**
 * Base class of all components in ECS.
 */
abstract class Component : Resource() {
    var active = true

    final override fun destroy() {
        World.destroyComponent(this)
    }
}