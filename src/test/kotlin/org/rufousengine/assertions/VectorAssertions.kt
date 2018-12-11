package org.rufousengine.assertions

import org.rufousengine.math.*

fun Assert<Vector>.isZero() = check(actual.isZero, "$actual wasn't the zero vector.")

fun Assert<Vector>.isUnit() = check(actual.isUnit, "$actual wasn't a unit vector. Its magnitude was ${actual.magnitude}.")

fun Assert<Vector>.isParallelTo(other: Vector) = check(parallel(actual, other), "$actual and $other were not parallel. The angle between them is: ${angle(actual, other)}.")

fun Assert<Vector>.isOrthogonalTo(other: Vector) = check(orthogonal(actual, other), "$actual and $other were not orthogonal. The angle between them is: ${angle(actual, other)}.")