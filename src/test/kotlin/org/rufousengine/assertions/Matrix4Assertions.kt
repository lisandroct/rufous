package org.rufousengine.assertions

import org.rufousengine.math.Matrix4
import org.rufousengine.math.isIdentity

fun Assert<Matrix4>.isIdentity() = check(actual.isIdentity, "$actual wasn't the identity matrix.")