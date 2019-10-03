package org.rufousengine.assertions

import org.rufousengine.math.Matrix2
import org.rufousengine.math.isIdentity

fun Assert<Matrix2>.isIdentity() = check(actual.isIdentity, "$actual wasn't the identity matrix.")