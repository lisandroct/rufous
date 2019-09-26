package org.rufousengine.utils

import kotlin.reflect.KProperty

class DirtyFlag {
    var flag = true

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        val value = flag
        flag = false
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        if(!value) {
            return
        }

        flag = value
    }
}