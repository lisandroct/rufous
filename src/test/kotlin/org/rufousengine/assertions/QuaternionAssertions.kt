package org.rufousengine.assertions

import org.rufousengine.math.Quaternion
import org.rufousengine.math.isIdentity

fun Assert<Quaternion>.isIdentity() {
    if(actual.isIdentity) {
        return
    }

    fail("It wasn't the identity quaternion, instead it was $actual.")
}