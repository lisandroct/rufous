package org.rufousengine.assertions

import org.rufousengine.math.*

fun Assert<Matrix>.isIdentity() {
    if(actual.isIdentity) {
        return
    }

    for(row in 0 until actual.dimensions) {
        for(column in 0 until actual.dimensions) {
            if(row == column) {
                if(!actual[row, column].isOne()) {
                    val a = actual[row, column]
                    val b = 1f
                    fail("It wasn't the identity matrix. The ($row, $column) element was ${ actual[row, column] } instead of 1.0.")
                }
            } else {
                if(!actual[row, column].isZero()) {
                    val a = actual[row, column]
                    val b = 0f
                    fail("It wasn't the identity matrix. The ($row, $column) element was ${ actual[row, column] } instead of 0.0.")
                }
            }
        }
    }
}

fun Assert<Matrix4>.isProjection() {
    if(actual.isProjection) {
        return
    }

    fail("$actual wasn't a projection matrix.")
}

fun Assert<Matrix4>.isTransformation() {
    if(actual.isTransformation) {
        return
    }

    fail("$actual wasn't a projection matrix.")
}