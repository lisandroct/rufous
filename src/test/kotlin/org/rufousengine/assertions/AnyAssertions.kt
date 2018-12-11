package org.rufousengine.assertions

fun Assert<Any?>.isEqualTo(other: Any?) = check(actual == other, "$actual was not equal to $other.")