package org.rufousengine.math

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.math.*
import assertk.assert
import assertk.assertions.*
import org.jetbrains.spek.api.dsl.describe
import org.rufousengine.assertions.isCloseTo

object QuaternionSpec: Spek({
    describe("immutable constructors") {
        on("empty") {
            val quaternion = Quaternion()
            it("should be the identity quaternion") {
                assert(quaternion).isEqualTo(Quaternion.identity)
            }
        }
        on("primary") {
            val x = getRandomValue()
            val y = getRandomValue()
            val z = getRandomValue()
            val w = getRandomValue()
            val quaternion = Quaternion(x, y, z, w)
            it("should have x set") {
                assert(quaternion.x).isCloseTo(x)
            }
            it("should have y set") {
                assert(quaternion.y).isCloseTo(y)
            }
            it("should have z set") {
                assert(quaternion.z).isCloseTo(z)
            }
            it("should have w set") {
                assert(quaternion.w).isCloseTo(w)
            }
        }
        on("Quaternion") {
            val other = getRandomQuaternion()
            val vector = Quaternion(other)
            it("should be equal to other") {
                assert(vector).isEqualTo(other)
            }
        }
        on("Vector3-Float") {
            val vector = getRandomVector3()
            val scalar = getRandomValue()
            val quaternion = Quaternion(vector, scalar)
            it("should have x set") {
                assert(quaternion.x).isCloseTo(vector.x)
            }
            it("should have y set") {
                assert(quaternion.y).isCloseTo(vector.y)
            }
            it("should have z set") {
                assert(quaternion.z).isCloseTo(vector.z)
            }
            it("w should be 0") {
                assert(quaternion.w).isCloseTo(scalar)
            }
        }
    }

    describe("mutable constructors") {
        on("empty") {
            val vector = MutableQuaternion { }
            it("should be the identity quaternion") {
                assert(vector).isEqualTo(Quaternion.identity)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
        on("primary") {
            val x = getRandomValue()
            val y = getRandomValue()
            val z = getRandomValue()
            val w = getRandomValue()
            val quaternion = MutableQuaternion(x, y, z, w) { }
            it("should have x set") {
                assert(quaternion.x).isCloseTo(x)
            }
            it("should have y set") {
                assert(quaternion.y).isCloseTo(y)
            }
            it("should have z set") {
                assert(quaternion.z).isCloseTo(z)
            }
            it("should have w set") {
                assert(quaternion.w).isCloseTo(w)
            }
            it("should have the observer set") {
                assert(quaternion.observer).isNotNull()
            }
        }
        on("Quaternion") {
            val other = getRandomQuaternion()
            val quaternion = MutableQuaternion(other) { }
            it("should be equal to other") {
                assert(quaternion).isEqualTo(other)
            }
            it("should have the observer set") {
                assert(quaternion.observer).isNotNull()
            }
        }
        on("Vector3-Float") {
            val vector = getRandomVector3()
            val scalar = getRandomValue()
            val quaternion = MutableQuaternion(vector, scalar) { }
            it("should have x set") {
                assert(quaternion.x).isCloseTo(vector.x)
            }
            it("should have y set") {
                assert(quaternion.y).isCloseTo(vector.y)
            }
            it("should have z set") {
                assert(quaternion.z).isCloseTo(vector.z)
            }
            it("w should be 0") {
                assert(quaternion.w).isCloseTo(scalar)
            }
            it("should have the observer set") {
                assert(quaternion.observer).isNotNull()
            }
        }
    }

    describe("premade quaternions") {
        on("identity") {
            val quaternion = Quaternion.identity
            it("x should be 0") {
                assert(quaternion.x).isCloseTo(0f)
            }
            it("y should be 0") {
                assert(quaternion.y).isCloseTo(0f)
            }
            it("z should be 0") {
                assert(quaternion.z).isCloseTo(0f)
            }
            it("w should be 1") {
                assert(quaternion.w).isCloseTo(1f)
            }
            it("should be the identity quaternion") {
                assert(quaternion.isIdentity).isTrue()
            }
        }
        on("zero") {
            val quaternion = Quaternion.zero
            it("x should be 0") {
                assert(quaternion.x).isCloseTo(0f)
            }
            it("y should be 0") {
                assert(quaternion.y).isCloseTo(0f)
            }
            it("z should be 0") {
                assert(quaternion.z).isCloseTo(0f)
            }
            it("w should be 0") {
                assert(quaternion.w).isCloseTo(0f)
            }
            it("should be the zero quaternion") {
                assert(quaternion.isZero).isTrue()
            }
        }
    }

    given("a quaternion") {
        val quaternion by memoized { getRandomQuaternion() }

        on("conjugate (val)") {
            val conjugate = quaternion.conjugate
            it("should negate its x") {
                assert(conjugate.x).isCloseTo(-quaternion.x)
            }
            it("should negate its y") {
                assert(conjugate.y).isCloseTo(-quaternion.y)
            }
            it("should negate its z") {
                assert(conjugate.z).isCloseTo(-quaternion.z)
            }
            it("should preserve its w") {
                assert(conjugate.w).isCloseTo(quaternion.w)
            }
            it("should always be the same reference") {
                assert(conjugate).isSameAs(quaternion.conjugate)
            }
        }

        on("inverse (val)") {
            val inverse = quaternion.inverse
            it("should be the multiplicative inverse to the right") {
                assert(quaternion.multiply(inverse, MutableQuaternion())).isEqualTo(Quaternion.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(quaternion, MutableQuaternion())).isEqualTo(Quaternion.identity)
            }
            it("should always be the same reference") {
                assert(inverse).isSameAs(quaternion.inverse)
            }
        }

        on("magnitude") {
            val magnitude = quaternion.magnitude
            it("should return the squared root of the squared magnitude") {
                assert(magnitude).isCloseTo(sqrt(quaternion.magnitudeSquared))
            }
        }

        on("magnitudeSquared") {
            val magnitudeSquared = quaternion.magnitudeSquared
            it("should return the result of multiplying with its conjugate") {
                val expected = quaternion.multiply(quaternion.conjugate, MutableQuaternion()).w
                assert(magnitudeSquared).isCloseTo(expected)
            }
        }

        on("copyImmutable") {
            val copy = quaternion.copyImmutable()
            it("should be a new instance") {
                assert(copy).isNotSameAs(quaternion)
            }
            it("should be equal to the original") {
                assert(copy).isEqualTo(quaternion)
            }
            it("should be immutable") {
                assert(copy).isInstanceOf(Quaternion::class)
            }
        }

        on("copyMutable") {
            val copy = quaternion.copyMutable()
            it("should be a new instance") {
                assert(copy).isNotSameAs(quaternion)
            }
            it("should be equal to the original") {
                assert(copy).isEqualTo(quaternion)
            }
            it("should be mutable") {
                assert(copy).isInstanceOf(MutableQuaternion::class)
            }
        }

        on("normalize") {
            val normalized = quaternion.normalize(MutableQuaternion())
            it("should be unit") {
                assert(normalized.isUnit).isTrue()
            }
            it("should be the original vector scaled") {
                for (i in 0 until 4) {
                    assert(normalized[i] * quaternion.magnitude).isCloseTo(quaternion[i])
                }
            }
        }

        on("conjugate") {
            val conjugate = quaternion.conjugate(MutableQuaternion())
            it("should negate its x") {
                assert(conjugate.x).isCloseTo(-quaternion.x)
            }
            it("should negate its y") {
                assert(conjugate.y).isCloseTo(-quaternion.y)
            }
            it("should negate its z") {
                assert(conjugate.z).isCloseTo(-quaternion.z)
            }
            it("should preserve its w") {
                assert(conjugate.w).isCloseTo(quaternion.w)
            }
        }

        on("inverse") {
            val inverse = quaternion.inverse(MutableQuaternion())
            it("should be the multiplicative inverse to the right") {
                assert(quaternion.multiply(inverse, MutableQuaternion())).isEqualTo(Quaternion.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(quaternion, MutableQuaternion())).isEqualTo(Quaternion.identity)
            }
        }

        on("getVectorPart") {
            val vector = quaternion.getVectorPart(MutableVector3())
            it("should have x set") {
                assert(vector.x).isCloseTo(quaternion.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(quaternion.y)
            }
            it("should have z set") {
                assert(vector.z).isCloseTo(quaternion.z)
            }
        }

        on("getMatrixRepresentation (Matrix3)") {
            val matrix = quaternion.getMatrixRepresentation(MutableMatrix3())
            val vector = getRandomVector3()
            val rotated = matrix.multiply(vector, MutableVector3())
            it("should represent the same transformation as the quaternion") {
                val expected = quaternion.transformSafe(vector, MutableVector3())

                assert(rotated).isEqualTo(expected)
            }
        }

        on("getMatrixRepresentation (Matrix4)") {
            val matrix = quaternion.getMatrixRepresentation(MutableMatrix4())
            val vector = getRandomVector3()
            val rotated = matrix.multiply(vector, MutableVector3())
            it("should give the same results as the quaternion") {
                val expected = quaternion.transformSafe(vector, MutableVector3())

                assert(rotated).isEqualTo(expected)
            }
        }

        on("getMatrixRepresentation (Transformation)") {
            val matrix = quaternion.getMatrixRepresentation(MutableTransformation())
            val vector = getRandomVector3()
            val rotated = matrix.multiply(vector, MutableVector3())
            it("should give the same results as the quaternion") {
                val expected = quaternion.transformSafe(vector, MutableVector3())

                assert(rotated).isEqualTo(expected)
            }
        }

        on("scale") {
            val scalar = getRandomValue()
            val scaled = quaternion.scale(scalar, MutableQuaternion())
            it("should scale every component") {
                for (i in 0 until 4) {
                    assert(scaled[i]).isCloseTo(quaternion[i] * scalar)
                }
            }
        }

        on("add") {
            val other = getRandomQuaternion()
            val add = quaternion.add(other, MutableQuaternion())
            it("should add componentwise") {
                for (i in 0 until 4) {
                    assert(add[i]).isCloseTo(quaternion[i] + other[i])
                }
            }
        }

        on("subtract") {
            val other = getRandomQuaternion()
            val subtract = quaternion.subtract(other, MutableQuaternion())
            it("should subtract componentwise") {
                for (i in 0 until 4) {
                    assert(subtract[i]).isCloseTo(quaternion[i] - other[i])
                }
            }
        }

        on("multiply") {
            val other = getRandomQuaternion()
            val multiply = quaternion.multiply(other, MutableQuaternion())
            it("should satisfy the equation q1q2 = v1 x v2 + s1v2 + s2v1 + s1s2 - v1 · v2") {
                val va = quaternion.getVectorPart(MutableVector3())
                val sa = quaternion.w
                val vb = other.getVectorPart(MutableVector3())
                val sb = other.w

                val s = (sa * sb) - (va dot vb)
                val v = va.cross(vb, MutableVector3()).add(va.scale(sb)).add(vb.scale(sa))

                val expected = Quaternion(v, s)

                assert(multiply).isEqualTo(expected)
            }
        }

        on("multiplyLeft") {
            val other = getRandomQuaternion()
            val multiply = quaternion.multiplyLeft(other, MutableQuaternion())
            it("should satisfy the equation q2q1 = v2 x v1 + s2v1 + s1v2 + s2s1 - v2 · v1") {
                val vb = other.getVectorPart(MutableVector3())
                val sb = other.w
                val va = quaternion.getVectorPart(MutableVector3())
                val sa = quaternion.w

                val s = (sb * sa) - (vb dot va)
                val v = vb.cross(va, MutableVector3()).add(vb.scale(sa)).add(va.scale(sb))

                val expected = Quaternion(v, s)

                assert(multiply).isEqualTo(expected)
            }
        }

        on("transformSafe") {
            val vector = getRandomVector3()
            val rotated = quaternion.transformSafe(vector, MutableVector3())
            it("should satisfy the equation v' = qvq-1") {
                val expected = quaternion.multiply(Quaternion(vector, 0f), MutableQuaternion()).multiply(quaternion.inverse).getVectorPart(MutableVector3())

                assert(rotated).isEqualTo(expected)
            }
        }

        on("transform") {
            val vector = getRandomVector3()
            val rotatedSafe = quaternion.transformSafe(vector, MutableVector3())
            val rotatedUnsafe = quaternion.transform(vector, MutableVector3())
            val unit = quaternion.normalize(MutableQuaternion())
            val rotated = unit.transform(vector, MutableVector3())
            it("should not work when is not unit") {
                assert(rotatedSafe).isNotEqualTo(rotatedUnsafe)
            }
            it("should work when is unit") {
                assert(rotated).isEqualTo(rotatedSafe)
            }
        }
    }

    given("a mutable quaternion") {
        var counter = 0
        val quaternion by memoized { getRandomMutableQuaternion { counter++ } }

        describe("seters") {
            on("x") {
                counter = 0
                val value = getRandomValue()
                quaternion.x = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(quaternion.x).isCloseTo(value)
                }
            }

            on("y") {
                counter = 0
                val value = getRandomValue()
                quaternion.y = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have y set") {
                    assert(quaternion.y).isCloseTo(value)
                }
            }

            on("z") {
                counter = 0
                val value = getRandomValue()
                quaternion.z = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have z set") {
                    assert(quaternion.z).isCloseTo(value)
                }
            }

            on("w") {
                counter = 0
                val value = getRandomValue()
                quaternion.w = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have w set") {
                    assert(quaternion.w).isCloseTo(value)
                }
            }

            on("Quaternion") {
                counter = 0
                val other = getRandomQuaternion()
                quaternion.set(other)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should be equal to other") {
                    assert(quaternion).isEqualTo(other)
                }
            }

            on("Vector-Float") {
                counter = 0
                val vector = getRandomVector3()
                val scalar = getRandomValue()
                quaternion.set(vector, scalar)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(quaternion.x).isCloseTo(vector.x)
                }
                it("should have y set") {
                    assert(quaternion.y).isCloseTo(vector.y)
                }
                it("should have z set") {
                    assert(quaternion.z).isCloseTo(vector.z)
                }
                it("w should be 0") {
                    assert(quaternion.w).isCloseTo(scalar)
                }
            }

            on("set") {
                counter = 0
                val x = getRandomValue()
                val y = getRandomValue()
                val z = getRandomValue()
                val w = getRandomValue()
                quaternion.set(x, y, z, w)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(quaternion.x).isCloseTo(x)
                }
                it("should have y set") {
                    assert(quaternion.y).isCloseTo(y)
                }
                it("should have z set") {
                    assert(quaternion.z).isCloseTo(z)
                }
                it("should have w set") {
                    assert(quaternion.w).isCloseTo(w)
                }
            }

            on("operator") {
                counter = 0
                val x = getRandomValue()
                val y = getRandomValue()
                val z = getRandomValue()
                val w = getRandomValue()
                quaternion[0] = x
                quaternion[1] = y
                quaternion[2] = z
                quaternion[3] = w
                it("should have notified four times") {
                    assert(counter).isEqualTo(4)
                }
                it("should have x set") {
                    assert(quaternion.x).isCloseTo(x)
                }
                it("should have y set") {
                    assert(quaternion.y).isCloseTo(y)
                }
                it("should have z set") {
                    assert(quaternion.z).isCloseTo(z)
                }
                it("should have w set") {
                    assert(quaternion.w).isCloseTo(w)
                }
            }
        }

        describe("operators") {
            on("plusAssign") {
                val original = quaternion.copyMutable()
                val other = getRandomQuaternion()
                quaternion += other
                it("should add and assign") {
                    assert(quaternion).isEqualTo(original.add(other))
                }
            }
            on("minusAssign") {
                val original = quaternion.copyMutable()
                val other = getRandomQuaternion()
                quaternion -= other
                it("should subtract and assign") {
                    assert(quaternion).isEqualTo(original.subtract(other))
                }
            }
            on("timesAssign") {
                val original = quaternion.copyMutable()
                val scalar = getRandomValue()
                quaternion *= scalar
                it("should scale and assign") {
                    assert(quaternion).isEqualTo(original.scale(scalar))
                }
            }
            on("divAssign") {
                val original = quaternion.copyMutable()
                val scalar = getRandomValue()
                quaternion /= scalar
                it("should scale and assign") {
                    assert(quaternion).isEqualTo(original.scale(1 / scalar))
                }
            }
            on("timesAssign (Quaternion)") {
                val original = quaternion.copyMutable()
                val other = getRandomQuaternion()
                quaternion *= other
                it("should multiply and assign") {
                    assert(quaternion).isEqualTo(original.multiply(other))
                }
            }
        }

        on("conjugate (val)") {
            val conjugate = quaternion.conjugate
            it("should negate its x") {
                assert(conjugate.x).isCloseTo(-quaternion.x)
            }
            it("should negate its y") {
                assert(conjugate.y).isCloseTo(-quaternion.y)
            }
            it("should negate its z") {
                assert(conjugate.z).isCloseTo(-quaternion.z)
            }
            it("should preserve its w") {
                assert(conjugate.w).isCloseTo(quaternion.w)
            }
            it("should update") {
                val original = conjugate.copyImmutable()
                quaternion.randomize()
                assert(original).isNotEqualTo(quaternion.conjugate)
            }
            it("should always be the same reference") {
                assert(conjugate).isSameAs(quaternion.conjugate)
            }
        }

        on("inverse (val)") {
            val inverse = quaternion.inverse
            it("should be the multiplicative inverse to the right") {
                assert(quaternion.multiply(inverse, MutableQuaternion())).isEqualTo(Quaternion.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(quaternion, MutableQuaternion())).isEqualTo(Quaternion.identity)
            }
            it("should update") {
                val original = inverse.copyImmutable()
                quaternion.randomize()
                assert(original).isNotEqualTo(quaternion.inverse)
            }
            it("should always be the same reference") {
                assert(inverse).isSameAs(quaternion.inverse)
            }
        }

        on("setFromMatrixRepresentation (Matrix3)") {
            val original = quaternion.copyImmutable()
            val matrix = quaternion.getMatrixRepresentation(MutableMatrix3())
            quaternion.setFromMatrixRepresentation(matrix)
            it("should represent the same transformation") {
                val vector = getRandomVector3()
                val result = quaternion.transformSafe(vector, MutableVector3())
                val expected = original.transformSafe(vector, MutableVector3())

                assert(result).isEqualTo(expected)
            }
        }

        on("setFromMatrixRepresentation (Matrix4)") {
            val original = quaternion.copyImmutable()
            val matrix = quaternion.getMatrixRepresentation(MutableMatrix4())
            quaternion.setFromMatrixRepresentation(matrix)
            it("should represent the same transformation") {
                val vector = getRandomVector3()
                val result = quaternion.transformSafe(vector, MutableVector3())
                val expected = original.transformSafe(vector, MutableVector3())

                assert(result).isEqualTo(expected)
            }
        }

        on("normalize") {
            val original = quaternion.copyImmutable()
            quaternion.normalize()
            it("should normalize and assign") {
                val expected = original.normalize(MutableQuaternion())

                assert(quaternion).isEqualTo(expected)
            }
        }

        on("conjugate") {
            val original = quaternion.copyImmutable()
            quaternion.conjugate()
            it("should conjugate and assign") {
                val expected = original.conjugate(MutableQuaternion())

                assert(quaternion).isEqualTo(expected)
            }
        }

        on("inverse") {
            val original = quaternion.copyImmutable()
            quaternion.inverse()
            it("should inverse and assign") {
                val expected = original.inverse(MutableQuaternion())

                assert(quaternion).isEqualTo(expected)
            }
        }

        on("scale") {
            val original = quaternion.copyImmutable()
            val scalar = getRandomValue()
            quaternion.scale(scalar)
            it("should scale and assign") {
                val expected = original.scale(scalar, MutableQuaternion())

                assert(quaternion).isEqualTo(expected)
            }
        }

        on("add") {
            val original = quaternion.copyImmutable()
            val other = getRandomQuaternion()
            quaternion.add(other)
            it("should add and assign") {
                val expected = original.add(other, MutableQuaternion())

                assert(quaternion).isEqualTo(expected)
            }
        }

        on("subtract") {
            val original = quaternion.copyImmutable()
            val other = getRandomQuaternion()
            quaternion.subtract(other)
            it("should subtract and assign") {
                val expected = original.subtract(other, MutableQuaternion())

                assert(quaternion).isEqualTo(expected)
            }
        }

        on("multiply") {
            val original = quaternion.copyImmutable()
            val other = getRandomQuaternion()
            quaternion.multiply(other)
            it("should multiply and assign") {
                val expected = original.multiply(other, MutableQuaternion())

                assert(quaternion).isEqualTo(expected)
            }
        }

        on("multiplyLeft") {
            val original = quaternion.copyImmutable()
            val other = getRandomQuaternion()
            quaternion.multiplyLeft(other)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(other, MutableQuaternion())

                assert(quaternion).isEqualTo(expected)
            }
        }

        on("makeRotationX") {
            val angle = getRandomValue()
            quaternion.makeRotationX(angle)
            it("should rotate about the x axis") {
                val rotation = MutableQuaternion().makeRotation(angle, 1f, 0f, 0f)
                val vector = getRandomVector3()
                val rotated = vector.transform(quaternion, MutableVector3())
                val expected = vector.transform(rotation, MutableVector3())

                assert(rotated).isEqualTo(expected)
            }
        }

        on("makeRotationY") {
            val angle = getRandomValue()
            quaternion.makeRotationY(angle)
            it("should rotate about the x axis") {
                val rotation = MutableQuaternion().makeRotation(angle, 0f, 1f, 0f)
                val vector = getRandomVector3()
                val rotated = vector.transform(quaternion, MutableVector3())
                val expected = vector.transform(rotation, MutableVector3())

                assert(rotated).isEqualTo(expected)
            }
        }

        on("makeRotationZ") {
            val angle = getRandomValue()
            quaternion.makeRotationZ(angle)
            it("should rotate about the x axis") {
                val rotation = MutableQuaternion().makeRotation(angle, 0f, 0f, 1f)
                val vector = getRandomVector3()
                val rotated = vector.transform(quaternion, MutableVector3())
                val expected = vector.transform(rotation, MutableVector3())

                assert(rotated).isEqualTo(expected)
            }
        }

        on("makeRotationSafe") {
            val angle = getRandomValue()
            val axis = getRandomVector3()
            quaternion.makeRotationSafe(angle, axis)
            it("should represent a rotation through angle about axis") {
                val vector = getRandomVector3()
                val rotated = vector.transform(quaternion, MutableVector3())
                val unitAxis = axis.copyMutable().normalize()
                val expected = vector.projectOnto(unitAxis, MutableVector3())
                expected += vector.rejectFrom(unitAxis, MutableVector3()).scale(cos(angle))
                expected += unitAxis.cross(vector, MutableVector3()).scale(sin(angle))

                // FIXME("Huge loss of precision")
                assert(rotated.x).isCloseTo(expected.x, 0.1f)
                assert(rotated.y).isCloseTo(expected.y, 0.1f)
                assert(rotated.z).isCloseTo(expected.z, 0.1f)
            }
        }

        on("makeRotation") {
            val angle = getRandomValue()
            val axis = getRandomVector3().normalize(MutableVector3())
            quaternion.makeRotation(angle, axis)
            it("should represent a rotation through angle about axis") {
                val vector = getRandomVector3()
                val rotated = vector.transform(quaternion, MutableVector3())
                val expected = vector.projectOnto(axis, MutableVector3())
                expected += vector.rejectFrom(axis, MutableVector3()).scale(cos(angle))
                expected += axis.cross(vector, MutableVector3()).scale(sin(angle))

                // FIXME("Huge loss of precision")
                assert(rotated.x).isCloseTo(expected.x, 0.1f)
                assert(rotated.y).isCloseTo(expected.y, 0.1f)
                assert(rotated.z).isCloseTo(expected.z, 0.1f)
            }
        }
    }
})

private fun getRandomValue() = random(-100f, 100f)
private fun getRandomVector3() = Vector3(getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomQuaternion() = Quaternion(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomMutableQuaternion(observer: ((Quaternion) -> Unit)) = MutableQuaternion(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(), observer)
private fun MutableQuaternion.randomize() = this.set(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue())