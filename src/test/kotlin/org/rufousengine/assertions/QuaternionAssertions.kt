package org.rufousengine.assertions

import org.rufousengine.math.Quaternion
import org.rufousengine.math.isIdentity
import org.rufousengine.math.isUnit
import org.rufousengine.math.magnitude

fun Assert<Quaternion>.isIdentity() = check(actual.isIdentity, "$actual wasn't the identity quaternion.")

fun Assert<Quaternion>.isUnit() = check(actual.isUnit, "$actual wasn't a unit quaternion. Its magnitude was ${actual.magnitude}.")