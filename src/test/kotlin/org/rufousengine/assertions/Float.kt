package org.rufousengine.assertions

import assertk.Assert
import assertk.assertions.support.show
import assertk.assertions.support.expected
import org.rufousengine.math.isCloseTo

fun Assert<Float>.isCloseTo(expected: Float) {
    if(actual isCloseTo expected) {
        return
    }

    expected("to be ${show(expected)} but was ${show(actual)}")
}