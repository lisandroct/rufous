package org.rufousengine.assertions

import org.rufousengine.math.isEqualTo
import org.rufousengine.math.isZero

fun Assert<Float>.isZero() = check(actual.isZero(), "$actual was not 0.")

fun Assert<Float>.isEqualTo(expected: Float) = check(actual.isEqualTo(expected), "$actual was not close to be $expected.")