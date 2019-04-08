package org.rufousengine.ecs

import org.rufousengine.Resource

/**
 * A bag of components in ECS.
 */
class Entity internal constructor(internal val index: Int): Resource() {
    override fun destroy() {
        World.destroyEntity(this)
    }
}