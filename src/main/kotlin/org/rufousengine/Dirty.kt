package org.rufousengine

import org.rufousengine.components.Transform
import org.rufousengine.math.*
import org.rufousengine.utils.DirtyFlag
import kotlin.properties.Delegates.observable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <T> dirty(initialValue: T, crossinline onDirty: () -> Unit):
        ReadOnlyProperty<Any?, T> =
        object : Dirty<T>(initialValue) {
            override fun onDirty() = onDirty()
        }

abstract class Dirty<T>(initialValue: T) : ReadOnlyProperty<Any?, T> {
    private var value = initialValue

    var flag = true
        private set

    abstract fun onDirty() : Unit

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if(flag) {
            onDirty()
        }

        flag = false

        return value
    }
}

var p by observable(Point3D()) { _, _, _ ->
    tFlag = true
}
var r by observable(Quaternion()) { _, _, _ ->
    tFlag = true
}
var s by observable(Vector3()) { _, _, _ ->
    tFlag = true
}

var tFlag by DirtyFlag()
var t = Matrix4()
    get() {
        if(tFlag) {
            field = Translation(p) * Scale(s.x, s.y, s.z) * Rotation(r)
        }
        return field
    }