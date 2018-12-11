package org.rufousengine.assertions

fun <T> assert(actual: T) = Assert(actual)

fun check(condition: Boolean, message: String = "Assertion Failed.") {
    if(!condition) { fail(message) }
}

fun fail(message: String) {
    throw AssertionError(message)
}

data class Assert<out T>(val actual: T)