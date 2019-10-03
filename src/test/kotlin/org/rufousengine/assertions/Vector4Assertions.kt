package org.rufousengine.assertions

import org.rufousengine.math.*

fun Assert<Vector4>.isZero() = check(actual.isZero, "$actual wasn't the zero vector.")

fun Assert<Vector4>.isUnit() = check(actual.isUnit, "$actual wasn't a unit vector. Its magnitude was ${actual.magnitude}.")

fun Assert<Vector4>.isParallelTo(other: Vector2) = check(parallel(actual, other), "$actual and $other were not parallel. The angle between them is: ${angle(actual, other)}.")
fun Assert<Vector4>.isParallelTo(other: Vector3) = check(parallel(actual, other), "$actual and $other were not parallel. The angle between them is: ${angle(actual, other)}.")
fun Assert<Vector4>.isParallelTo(other: Vector4) = check(parallel(actual, other), "$actual and $other were not parallel. The angle between them is: ${angle(actual, other)}.")

fun Assert<Vector4>.isOrthogonalTo(other: Vector2) = check(orthogonal(actual, other), "$actual and $other were not orthogonal. The angle between them is: ${angle(actual, other)}.")
fun Assert<Vector4>.isOrthogonalTo(other: Vector3) = check(orthogonal(actual, other), "$actual and $other were not orthogonal. The angle between them is: ${angle(actual, other)}.")
fun Assert<Vector4>.isOrthogonalTo(other: Vector4) = check(orthogonal(actual, other), "$actual and $other were not orthogonal. The angle between them is: ${angle(actual, other)}.")