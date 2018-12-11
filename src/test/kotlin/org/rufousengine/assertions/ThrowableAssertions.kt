package org.rufousengine.assertions

import java.lang.Exception
import kotlin.reflect.KClass

fun <T: Throwable> Assert<KClass<T>>.isThrownBy(body: () -> Unit) {
    try {
        body()
    } catch (e: Exception) {
        if(e::class == actual) {
            return
        }

        fail("${actual.simpleName} was not thrown by body. Instead ${e::class.simpleName} was thrown.")
    }

    fail("${actual.simpleName} was not thrown by body.")
}