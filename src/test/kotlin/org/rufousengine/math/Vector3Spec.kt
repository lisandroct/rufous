package org.rufousengine.math

import org.jetbrains.spek.api.Spek
import assertk.assert
import assertk.assertions.*
import org.jetbrains.spek.api.dsl.*
import org.rufousengine.assertions.isCloseTo
import kotlin.math.*

object Vector3Spec: Spek({
    describe("immutable constructors") {
        on("empty") {
            val vector = Vector3()
            it("should be the zero vector") {
                assert(vector).isEqualTo(Vector3.zero)
            }
        }
        on("primary") {
            val x = getRandomValue()
            val y = getRandomValue()
            val z = getRandomValue()
            val vector = Vector3(x, y, z)
            it("should have x set") {
                assert(vector.x).isCloseTo(x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(y)
            }
            it("should have z set") {
                assert(vector.z).isCloseTo(z)
            }
        }
        on("Vector2") {
            val other = getRandomVector2()
            val vector = Vector3(other)
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("z should be 0") {
                assert(vector.z).isCloseTo(0f)
            }
        }
        on("Vector3") {
            val other = getRandomVector3()
            val vector = Vector3(other)
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("should have z set") {
                assert(vector.z).isCloseTo(other.z)
            }
        }
        on("Vector4") {
            val other = getRandomVector4()
            val vector = Vector3(other)
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("should have z set") {
                assert(vector.z).isCloseTo(other.z)
            }
        }
    }

    describe("mutable constructors") {
        on("empty") {
            val vector = MutableVector3 { }
            it("should be the zero vector") {
                assert(vector).isEqualTo(Vector3.zero)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
        on("primary") {
            val x = getRandomValue()
            val y = getRandomValue()
            val z = getRandomValue()
            val vector = MutableVector3(x, y, z) { }
            it("should have x set") {
                assert(vector.x).isCloseTo(x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(y)
            }
            it("should have z set") {
                assert(vector.z).isCloseTo(z)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
        on("Vector2") {
            val other = getRandomVector2()
            val vector = MutableVector3(other) { }
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("z should be 0") {
                assert(vector.z).isCloseTo(0f)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
        on("Vector3") {
            val other = getRandomVector3()
            val vector = MutableVector3(other) { }
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("should have z set") {
                assert(vector.z).isCloseTo(other.z)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
        on("Vector4") {
            val other = getRandomVector4()
            val vector = MutableVector3(other) { }
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("should have z set") {
                assert(vector.z).isCloseTo(other.z)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
    }

    describe("premade vectors") {
        on("zero") {
            val vector = Vector3.zero
            it("x should be 0") {
                assert(vector.x).isCloseTo(0f)
            }
            it("y should be 0") {
                assert(vector.y).isCloseTo(0f)
            }
            it("z should be 0") {
                assert(vector.z).isCloseTo(0f)
            }
            it("should be the zero vector") {
                assert(vector.isZero).isTrue()
            }
        }
        on("one") {
            val vector = Vector3.one
            it("x should be 1") {
                assert(vector.x).isCloseTo(1f)
            }
            it("y should be 1") {
                assert(vector.y).isCloseTo(1f)
            }
            it("z should be 1") {
                assert(vector.z).isCloseTo(1f)
            }
            it("should be the one vector") {
                assert(vector.isOne).isTrue()
            }
        }
        on("i") {
            val vector = Vector3.i
            it("x should be 1") {
                assert(vector.x).isCloseTo(1f)
            }
            it("y should be 0") {
                assert(vector.y).isCloseTo(0f)
            }
            it("z should be 0") {
                assert(vector.z).isCloseTo(0f)
            }
        }
        on("j") {
            val vector = Vector3.j
            it("x should be 0") {
                assert(vector.x).isCloseTo(0f)
            }
            it("y should be 1") {
                assert(vector.y).isCloseTo(1f)
            }
            it("z should be 0") {
                assert(vector.z).isCloseTo(0f)
            }
        }
        on("k") {
            val vector = Vector3.k
            it("x should be 0") {
                assert(vector.x).isCloseTo(0f)
            }
            it("y should be 0") {
                assert(vector.y).isCloseTo(0f)
            }
            it("z should be 1") {
                assert(vector.z).isCloseTo(1f)
            }
        }
        on("right") {
            val vector = Vector3.right
            it("should be the same as i") {
                assert(vector).isSameAs(Vector3.i)
            }
        }
        on("left") {
            val vector = Vector3.left
            it("should be -right") {
                assert(vector).isEqualTo(Vector3.right.negate(MutableVector3()))
            }
        }
        on("up") {
            val vector = Vector3.up
            it("should be the same as j") {
                assert(vector).isSameAs(Vector3.j)
            }
        }
        on("down") {
            val vector = Vector3.down
            it("should be -up") {
                assert(vector).isEqualTo(Vector3.up.negate(MutableVector3()))
            }
        }
        on("forward") {
            val vector = Vector3.forward
            it("should be the same as k") {
                assert(vector).isSameAs(Vector3.k)
            }
        }
        on("backward") {
            val vector = Vector3.backward
            it("should be -forward") {
                assert(vector).isEqualTo(Vector3.forward.negate(MutableVector3()))
            }
        }
    }

    given("a vector") {
        val vector by memoized { getRandomVector3() }

        on("magnitude") {
            val magnitude = vector.magnitude
            it("should be the squared root of the sum of its components squares") {
                var expected = 0f
                for(i in 0 until 3) {
                    expected += vector[i] * vector[i]
                }
                expected = sqrt(expected)

                assert(magnitude).isCloseTo(expected)
            }
        }

        on("magnitudeSquared") {
            val magnitudeSquared = vector.magnitudeSquared
            it("should be the its magnitude squared") {
                assert(magnitudeSquared).isCloseTo(vector.magnitude * vector.magnitude)
            }
        }

        on("minComponent") {
            val component = vector.minComponent
            it("should return the smallest component") {
                var expected = vector[0]
                for (i in 1 until 3) {
                    if (vector[i] < expected) {
                        expected = vector[i]
                    }
                }

                assert(component).isCloseTo(expected)
            }
        }

        on("maxComponent") {
            val component = vector.maxComponent
            it("should return the largest component") {
                var expected = vector[0]
                for (i in 1 until 3) {
                    if (vector[i] > expected) {
                        expected = vector[i]
                    }
                }

                assert(component).isCloseTo(expected)
            }
        }

        on("minDimension") {
            val dimension = vector.minDimension
            it("should return the smallest dimension (0 if x, 1 if y or 2 if z)") {
                var expected = 0
                for (i in 1 until 3) {
                    if (vector[i] < vector[expected]) {
                        expected = i
                    }
                }

                assert(dimension).isEqualTo(expected)
            }
        }

        on("maxComponent") {
            val dimension = vector.maxDimension
            it("should return the largest dimension (0 if x, 1 if y or 2 if z)") {
                var expected = 0
                for (i in 1 until 3) {
                    if (vector[i] > vector[expected]) {
                        expected = i
                    }
                }

                assert(dimension).isEqualTo(expected)
            }
        }

        on("copyImmutable") {
            val copy = vector.copyImmutable()
            it("should be a new instance") {
                assert(copy).isNotSameAs(vector)
            }
            it("should be equal to the original") {
                assert(copy).isEqualTo(vector)
            }
            it("should be immutable") {
                assert(copy).isInstanceOf(Vector3::class)
            }
        }

        on("copyMutable") {
            val copy = vector.copyMutable()
            it("should be a new instance") {
                assert(copy).isNotSameAs(vector)
            }
            it("should be equal to the original") {
                assert(copy).isEqualTo(vector)
            }
            it("should be mutable") {
                assert(copy).isInstanceOf(MutableVector3::class)
            }
        }

        on("negate") {
            val negative = vector.negate(MutableVector3())
            it("should negate every component") {
                for (i in 0 until 3) {
                    assert(negative[i]).isCloseTo(-vector[i])
                }
            }
        }

        on("normalize") {
            val normalized = vector.normalize(MutableVector3())
            it("should be unit") {
                assert(normalized.isUnit).isTrue()
            }
            it("should be the original vector scaled") {
                for (i in 0 until 3) {
                    assert(normalized[i] * vector.magnitude).isCloseTo(vector[i])
                }
            }
        }

        on("abs") {
            val abs = vector.abs(MutableVector3())
            it("should set every component to its absolute value") {
                for (i in 0 until 3) {
                    assert(abs[i]).isCloseTo(abs(vector[i]))
                }
            }
        }

        on("scale") {
            val scalar = getRandomValue()
            val scaled = vector.scale(scalar, MutableVector3())
            it("should scale every component") {
                for (i in 0 until 3) {
                    assert(scaled[i]).isCloseTo(vector[i] * scalar)
                }
            }
        }

        on("multiplyLeft (Matrix3)") {
            val matrix = getRandomMatrix3()
            val multiplied = vector.multiplyLeft(matrix, MutableVector3())
            it("should give the same results as Matrix3::multiply") {
                val expected = matrix.multiply(vector, MutableVector3())

                assert(multiplied).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Matrix4)") {
            val matrix = getRandomMatrix4()
            val multiplied = vector.multiplyLeft(matrix, MutableVector3())
            it("should give the same results as Matrix4::multiply") {
                val expected = matrix.multiply(vector, MutableVector3())

                assert(multiplied).isEqualTo(expected)
            }
        }

        on("transformSafe") {
            val quaternion = getRandomQuaternion()
            val transformed = vector.transformSafe(quaternion, MutableVector3())
            it("should give the same results as Quaternion::transformSafe") {
                val expected = quaternion.transformSafe(vector, MutableVector3())

                assert(transformed).isEqualTo(expected)
            }
        }

        on("transform") {
            val quaternion = getRandomQuaternion().normalize(MutableQuaternion())
            val transformed = vector.transform(quaternion, MutableVector3())
            it("should give the same results as Quaternion::transform") {
                val expected = quaternion.transform(vector, MutableVector3())

                assert(transformed).isEqualTo(expected)
            }
        }
    }

    given("two vectors") {
        val a by memoized { getRandomVector3() }
        val b by memoized { getRandomVector3() }

        on("getAngle") {
            val angle = a.getAngle(b)
            it("should satisfy the dot product definition") {
                val dot = a dot b
                val expected = acos(dot / (a.magnitude * b.magnitude)).toDegrees()

                assert(angle).isCloseTo(expected)
            }
        }

        on("dot") {
            val dot = a dot b
            it("should be the sum of the product of the corresponding components") {
                var expected = 0f
                for (i in 0 until 3) {
                    expected += a[i] * b[i]
                }

                assert(dot).isCloseTo(expected)
            }
        }

        on("dotAbs") {
            val dotAbs = a dotAbs b
            it("should be the absolute value of the dot product") {
                assert(dotAbs).isCloseTo(abs(a dot b))
            }
        }

        on("add") {
            val add = a.add(b, MutableVector3())
            it("should add componentwise") {
                for (i in 0 until 3) {
                    assert(add[i]).isCloseTo(a[i] + b[i])
                }
            }
        }

        on("subtract") {
            val subtract = a.subtract(b, MutableVector3())
            it("should subtract componentwise") {
                for (i in 0 until 3) {
                    assert(subtract[i]).isCloseTo(a[i] - b[i])
                }
            }
        }

        on("min") {
            val min = a.min(b, MutableVector3())
            it("should perform min componentwise") {
                for (i in 0 until 3) {
                    assert(min[i]).isCloseTo(min(a[i], b[i]))
                }
            }
        }

        on("max") {
            val max = a.max(b, MutableVector3())
            it("should perform max componentwise") {
                for (i in 0 until 3) {
                    assert(max[i]).isCloseTo(max(a[i], b[i]))
                }
            }
        }

        on("projectOnto") {
            val projected = a.projectOnto(b, MutableVector3())
            it("should be parallel to the vector projected onto") {
                assert(projected.isParallel(b)).isTrue()
            }
        }

        on("rejectFrom") {
            val rejected = a.rejectFrom(b, MutableVector3())
            it("should be orthogonal to the vector rejected from") {
                assert(rejected.isOrthogonal(b)).isTrue()
            }
        }

        on("cross") {
            val cross = a.cross(b, MutableVector3())
            it("should be orthogonal to a") {
                assert(cross.isOrthogonal(a)).isTrue()
            }
            it("should be orthogonal to b") {
                assert(cross.isOrthogonal(b)).isTrue()
            }
        }
    }

    given("a mutable vector") {
        var counter = 0
        val vector by memoized { getRandomMutable { counter++ } }

        describe("seters") {
            on("x") {
                counter = 0
                val value = getRandomValue()
                vector.x = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(vector.x).isCloseTo(value)
                }
            }

            on("y") {
                counter = 0
                val value = getRandomValue()
                vector.y = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have y set") {
                    assert(vector.y).isCloseTo(value)
                }
            }

            on("z") {
                counter = 0
                val value = getRandomValue()
                vector.z = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have z set") {
                    assert(vector.z).isCloseTo(value)
                }
            }

            on("Vector2") {
                counter = 0
                val other = getRandomVector2()
                vector.set(other)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(vector.x).isCloseTo(other.x)
                }
                it("should have y set") {
                    assert(vector.y).isCloseTo(other.y)
                }
                it("z should be 0") {
                    assert(vector.z).isCloseTo(0f)
                }
            }

            on("Vector3") {
                counter = 0
                val other = getRandomVector3()
                vector.set(other)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(vector.x).isCloseTo(other.x)
                }
                it("should have y set") {
                    assert(vector.y).isCloseTo(other.y)
                }
                it("should have z set") {
                    assert(vector.z).isCloseTo(other.z)
                }
            }

            on("Vector4") {
                counter = 0
                val other = getRandomVector4()
                vector.set(other)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(vector.x).isCloseTo(other.x)
                }
                it("should have y set") {
                    assert(vector.y).isCloseTo(other.y)
                }
                it("should have z set") {
                    assert(vector.z).isCloseTo(other.z)
                }
            }

            on("set") {
                counter = 0
                val x = getRandomValue()
                val y = getRandomValue()
                val z = getRandomValue()
                vector.set(x, y, z)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(vector.x).isCloseTo(x)
                }
                it("should have y set") {
                    assert(vector.y).isCloseTo(y)
                }
                it("should have z set") {
                    assert(vector.z).isCloseTo(z)
                }
            }

            on("operator") {
                counter = 0
                val x = getRandomValue()
                val y = getRandomValue()
                val z = getRandomValue()
                vector[0] = x
                vector[1] = y
                vector[2] = z
                it("should have notified thrice") {
                    assert(counter).isEqualTo(3)
                }
                it("should have x set") {
                    assert(vector.x).isCloseTo(x)
                }
                it("should have y set") {
                    assert(vector.y).isCloseTo(y)
                }
                it("should have z set") {
                    assert(vector.z).isCloseTo(z)
                }
            }
        }

        describe("operators") {
            on("plusAssign") {
                val original = vector.copyMutable()
                val other = getRandomVector3()
                vector += other
                it("should add and assign") {
                    assert(vector).isEqualTo(original.add(other))
                }
            }
            on("minusAssign") {
                val original = vector.copyMutable()
                val other = getRandomVector3()
                vector -= other
                it("should subtract and assign") {
                    assert(vector).isEqualTo(original.subtract(other))
                }
            }
            on("timesAssign") {
                val original = vector.copyMutable()
                val scalar = getRandomValue()
                vector *= scalar
                it("should scale and assign") {
                    assert(vector).isEqualTo(original.scale(scalar))
                }
            }
            on("divAssign") {
                val original = vector.copyMutable()
                val scalar = getRandomValue()
                vector /= scalar
                it("should scale and assign") {
                    assert(vector).isEqualTo(original.scale(1 / scalar))
                }
            }
        }

        on("negate") {
            val original = vector.copyImmutable()
            vector.negate()
            it("should negate and assign") {
                val expected = original.negate(MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("normalize") {
            val original = vector.copyImmutable()
            vector.normalize()
            it("should normalize and assign") {
                val expected = original.normalize(MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("abs") {
            val original = vector.copyImmutable()
            vector.abs()
            it("should abs and assign") {
                val expected = original.abs(MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("scale") {
            val original = vector.copyImmutable()
            val scalar = getRandomValue()
            vector.scale(scalar)
            it("should scale and assign") {
                val expected = original.scale(scalar, MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("add") {
            val original = vector.copyImmutable()
            val other = getRandomVector3()
            vector.add(other)
            it("should add and assign") {
                val expected = original.add(other, MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("subtract") {
            val original = vector.copyImmutable()
            val other = getRandomVector3()
            vector.subtract(other)
            it("should subtract and assign") {
                val expected = original.subtract(other, MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("min") {
            val original = vector.copyImmutable()
            val other = getRandomVector3()
            vector.min(other)
            it("should min and assign") {
                val expected = original.min(other, MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("max") {
            val original = vector.copyImmutable()
            val other = getRandomVector3()
            vector.max(other)
            it("should max and assign") {
                val expected = original.max(other, MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("projectOnto") {
            val original = vector.copyImmutable()
            val other = getRandomVector3()
            vector.projectOnto(other)
            it("should projectOnto and assign") {
                val expected = original.projectOnto(other, MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("rejectFrom") {
            val original = vector.copyImmutable()
            val other = getRandomVector3()
            vector.rejectFrom(other)
            it("should rejectFrom and assign") {
                val expected = original.rejectFrom(other, MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("cross") {
            val original = vector.copyImmutable()
            val other = getRandomVector3()
            vector.cross(other)
            it("should cross and assign") {
                val expected = original.cross(other, MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Matrix3)") {
            val original = vector.copyImmutable()
            val matrix = getRandomMatrix3()
            vector.multiplyLeft(matrix)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(matrix, MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Matrix4)") {
            val original = vector.copyImmutable()
            val matrix = getRandomMatrix4()
            vector.multiplyLeft(matrix)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(matrix, MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("transformSafe") {
            val original = vector.copyImmutable()
            val quaternion = getRandomQuaternion()
            vector.transformSafe(quaternion)
            it("should transformSafe and assign") {
                val expected = original.transformSafe(quaternion, MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }

        on("transform") {
            val original = vector.copyImmutable()
            val quaternion = getRandomQuaternion()
            vector.transform(quaternion)
            it("should transform and assign") {
                val expected = original.transform(quaternion, MutableVector3())

                assert(vector).isEqualTo(expected)
            }
        }
    }
})

private fun getRandomValue() = random(-100f, 100f)
private fun getRandomVector2() = Vector2(getRandomValue(), getRandomValue())
private fun getRandomVector3() = Vector3(getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomVector4() = Vector4(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomMutable(observer: ((Vector3) -> Unit)) = MutableVector3(getRandomValue(), getRandomValue(), getRandomValue(), observer)
private fun getRandomQuaternion() = Quaternion(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomMatrix3() = Matrix3(
        getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue()
)
private fun getRandomMatrix4() = Matrix4(
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()
)