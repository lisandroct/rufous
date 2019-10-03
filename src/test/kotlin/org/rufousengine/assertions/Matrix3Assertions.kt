package org.rufousengine.assertions

import org.rufousengine.math.Matrix3
import org.rufousengine.math.isIdentity

fun Assert<Matrix3>.isIdentity() = check(actual.isIdentity, "$actual wasn't the identity matrix.")