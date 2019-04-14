package org.rufousengine.ecs

import org.rufousengine.Resource
import org.rufousengine.utils.Bits

/**
 * A bag of components in ECS.
 */
class Entity internal constructor(internal val index: Int): Resource() {
    internal val componentBits = Bits()

    override fun destroy() {
        World.destroyEntity(this)
    }
}