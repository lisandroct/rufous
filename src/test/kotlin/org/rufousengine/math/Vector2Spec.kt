package org.rufousengine.math

import org.jetbrains.spek.api.Spek
import assertk.assert
import assertk.assertions.*
import org.jetbrains.spek.api.dsl.*
import org.rufousengine.assertions.isCloseTo
import kotlin.math.*

object Vector2Spec: Spek({
    describe("immutable constructors") {
        on("empty") {
            val vector = Vector2()
            it("should be the zero vector") {
                assert(vector).isEqualTo(Vector2.zero)
            }
        }
        on("primary") {
            val x = getRandomValue()
            val y = getRandomValue()
            val vector = Vector2(x, y)
            it("should have x set") {
                assert(vector.x).isCloseTo(x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(y)
            }
        }
        on("Vector2") {
            val other = getRandomVector2()
            val vector = Vector2(other)
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
        }
        on("Vector3") {
            val other = getRandomVector3()
            val vector = Vector2(other)
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
        }
        on("Vector4") {
            val other = getRandomVector4()
            val vector = Vector2(other)
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
        }
    }

    describe("mutable constructors") {
        on("empty") {
            val vector = MutableVector2 { }
            it("should be the zero vector") {
                assert(vector).isEqualTo(Vector2.zero)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
        on("primary") {
            val x = getRandomValue()
            val y = getRandomValue()
            val vector = MutableVector2(x, y) { }
            it("should have x set") {
                assert(vector.x).isCloseTo(x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(y)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
        on("Vector2") {
            val other = getRandomVector2()
            val vector = MutableVector2(other) { }
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
        on("Vector3") {
            val other = getRandomVector3()
            val vector = MutableVector2(other) { }
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
        on("Vector4") {
            val other = getRandomVector4()
            val vector = MutableVector2(other) { }
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
    }

    describe("premade vectors") {
        on("zero") {
            val vector = Vector2.zero
            it("x should be 0") {
                assert(vector.x).isCloseTo(0f)
            }
            it("y should be 0") {
                assert(vector.y).isCloseTo(0f)
            }
            it("should be the zero vector") {
                assert(vector.isZero).isTrue()
            }
        }
        on("one") {
            val vector = Vector2.one
            it("x should be 1") {
                assert(vector.x).isCloseTo(1f)
            }
            it("y should be 1") {
                assert(vector.y).isCloseTo(1f)
            }
            it("should be the one vector") {
                assert(vector.isOne).isTrue()
            }
        }
        on("i") {
            val vector = Vector2.i
            it("x should be 1") {
                assert(vector.x).isCloseTo(1f)
            }
            it("y should be 0") {
                assert(vector.y).isCloseTo(0f)
            }
        }
        on("j") {
            val vector = Vector2.j
            it("x should be 0") {
                assert(vector.x).isCloseTo(0f)
            }
            it("y should be 1") {
                assert(vector.y).isCloseTo(1f)
            }
        }
        on("right") {
            val vector = Vector2.right
            it("should be the same as i") {
                assert(vector).isSameAs(Vector2.i)
            }
        }
        on("left") {
            val vector = Vector2.left
            it("should be -right") {
                assert(vector).isEqualTo(Vector2.right.negate(MutableVector2()))
            }
        }
        on("up") {
            val vector = Vector2.up
            it("should be the same as j") {
                assert(vector).isSameAs(Vector2.j)
            }
        }
        on("down") {
            val vector = Vector2.down
            it("should be -up") {
                assert(vector).isEqualTo(Vector2.up.negate(MutableVector2()))
            }
        }
    }

    given("a vector") {
        val vector by memoized { getRandomVector2() }

        on("magnitude") {
            val magnitude = vector.magnitude
            it("should be the squared root of the sum of its components squares") {
                var expected = 0f
                for(i in 0 until 2) {
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
                for (i in 1 until 2) {
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
                for (i in 1 until 2) {
                    if (vector[i] > expected) {
                        expected = vector[i]
                    }
                }

                assert(component).isCloseTo(expected)
            }
        }

        on("minDimension") {
            val dimension = vector.minDimension
            it("should return the smallest dimension (0 if x or 1 if y)") {
                var expected = 0
                for (i in 1 until 2) {
                    if (vector[i] < vector[expected]) {
                        expected = i
                    }
                }

                assert(dimension).isEqualTo(expected)
            }
        }

        on("maxComponent") {
            val dimension = vector.maxDimension
            it("should return the largest dimension (0 if x or 1 if y)") {
                var expected = 0
                for (i in 1 until 2) {
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
                assert(copy).isInstanceOf(Vector2::class)
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
                assert(copy).isInstanceOf(MutableVector2::class)
            }
        }

        on("negate") {
            val negative = vector.negate(MutableVector2())
            it("should negate every component") {
                for (i in 0 until 2) {
                    assert(negative[i]).isCloseTo(-vector[i])
                }
            }
        }

        on("normalize") {
            val normalized = vector.normalize(MutableVector2())
            it("should be unit") {
                assert(normalized.isUnit).isTrue()
            }
            it("should be the original vector scaled") {
                for (i in 0 until 2) {
                    assert(normalized[i] * vector.magnitude).isCloseTo(vector[i])
                }
            }
        }

        on("abs") {
            val abs = vector.abs(MutableVector2())
            it("should set every component to its absolute value") {
                for (i in 0 until 2) {
                    assert(abs[i]).isCloseTo(abs(vector[i]))
                }
            }
        }

        on("scale") {
            val scalar = getRandomValue()
            val scaled = vector.scale(scalar, MutableVector2())
            it("should scale every component") {
                for (i in 0 until 2) {
                    assert(scaled[i]).isCloseTo(vector[i] * scalar)
                }
            }
        }

        on("getAngle") {
            val other = getRandomVector2()
            val angle = vector.getAngle(other)
            it("should satisfy the dot product definition") {
                val dot = vector dot other
                val expected = acos(dot / (vector.magnitude * other.magnitude)).toDegrees()

                assert(angle).isCloseTo(expected)
            }
        }

        on("dot") {
            val other = getRandomVector2()
            val dot = vector dot other
            it("should be the sum of the product of the corresponding components") {
                var expected = 0f
                for (i in 0 until 2) {
                    expected += vector[i] * other[i]
                }

                assert(dot).isCloseTo(expected)
            }
        }

        on("dotAbs") {
            val other = getRandomVector2()
            val dotAbs = vector dotAbs other
            it("should be the absolute value of the dot product") {
                assert(dotAbs).isCloseTo(abs(vector dot other))
            }
        }

        on("add") {
            val other = getRandomVector2()
            val add = vector.add(other, MutableVector2())
            it("should add componentwise") {
                for (i in 0 until 2) {
                    assert(add[i]).isCloseTo(vector[i] + other[i])
                }
            }
        }

        on("subtract") {
            val other = getRandomVector2()
            val subtract = vector.subtract(other, MutableVector2())
            it("should subtract componentwise") {
                for (i in 0 until 2) {
                    assert(subtract[i]).isCloseTo(vector[i] - other[i])
                }
            }
        }

        on("min") {
            val other = getRandomVector2()
            val min = vector.min(other, MutableVector2())
            it("should perform min componentwise") {
                for (i in 0 until 2) {
                    assert(min[i]).isCloseTo(min(vector[i], other[i]))
                }
            }
        }

        on("max") {
            val other = getRandomVector2()
            val max = vector.max(other, MutableVector2())
            it("should perform max componentwise") {
                for (i in 0 until 2) {
                    assert(max[i]).isCloseTo(max(vector[i], other[i]))
                }
            }
        }

        on("projectOnto") {
            val other = getRandomVector2()
            val projected = vector.projectOnto(other, MutableVector2())
            it("should be parallel to the vector projected onto") {
                assert(projected.isParallel(other)).isTrue()
            }
        }

        on("rejectFrom") {
            val other = getRandomVector2()
            val rejected = vector.rejectFrom(other, MutableVector2())
            it("should be orthogonal to the vector rejected from") {
                assert(rejected.isOrthogonal(other)).isTrue()
            }
        }

        on("multiplyLeft") {
            val matrix = getRandomMatrix2()
            val multiplied = vector.multiplyLeft(matrix, MutableVector2())
            it("should give the same results as Matrix4::multiply") {
                val expected = matrix.multiply(vector, MutableVector2())

                assert(multiplied).isEqualTo(expected)
            }
        }
    }

    given("a mutable vector") {
        var counter = 0
        val vector by memoized { getRandomMutableVector2 { counter++ } }

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
            }

            on("set") {
                counter = 0
                val x = getRandomValue()
                val y = getRandomValue()
                vector.set(x, y)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(vector.x).isCloseTo(x)
                }
                it("should have y set") {
                    assert(vector.y).isCloseTo(y)
                }
            }

            on("operator") {
                counter = 0
                val x = getRandomValue()
                val y = getRandomValue()
                vector[0] = x
                vector[1] = y
                it("should have notified twice") {
                    assert(counter).isEqualTo(2)
                }
                it("should have x set") {
                    assert(vector.x).isCloseTo(x)
                }
                it("should have y set") {
                    assert(vector.y).isCloseTo(y)
                }
            }
        }

        describe("operators") {
            on("plusAssign") {
                val original = vector.copyMutable()
                val other = getRandomVector2()
                vector += other
                it("should add and assign") {
                    assert(vector).isEqualTo(original.add(other))
                }
            }
            on("minusAssign") {
                val original = vector.copyMutable()
                val other = getRandomVector2()
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
                val expected = original.negate(MutableVector2())

                assert(vector).isEqualTo(expected)
            }
        }

        on("normalize") {
            val original = vector.copyImmutable()
            vector.normalize()
            it("should normalize and assign") {
                val expected = original.normalize(MutableVector2())

                assert(vector).isEqualTo(expected)
            }
        }

        on("abs") {
            val original = vector.copyImmutable()
            vector.abs()
            it("should abs and assign") {
                val expected = original.abs(MutableVector2())

                assert(vector).isEqualTo(expected)
            }
        }

        on("scale") {
            val original = vector.copyImmutable()
            val scalar = getRandomValue()
            vector.scale(scalar)
            it("should scale and assign") {
                val expected = original.scale(scalar, MutableVector2())

                assert(vector).isEqualTo(expected)
            }
        }

        on("add") {
            val original = vector.copyImmutable()
            val other = getRandomVector2()
            vector.add(other)
            it("should add and assign") {
                val expected = original.add(other, MutableVector2())

                assert(vector).isEqualTo(expected)
            }
        }

        on("subtract") {
            val original = vector.copyImmutable()
            val other = getRandomVector2()
            vector.subtract(other)
            it("should subtract and assign") {
                val expected = original.subtract(other, MutableVector2())

                assert(vector).isEqualTo(expected)
            }
        }

        on("min") {
            val original = vector.copyImmutable()
            val other = getRandomVector2()
            vector.min(other)
            it("should min and assign") {
                val expected = original.min(other, MutableVector2())

                assert(vector).isEqualTo(expected)
            }
        }

        on("max") {
            val original = vector.copyImmutable()
            val other = getRandomVector2()
            vector.max(other)
            it("should max and assign") {
                val expected = original.max(other, MutableVector2())

                assert(vector).isEqualTo(expected)
            }
        }

        on("projectOnto") {
            val original = vector.copyImmutable()
            val other = getRandomVector2()
            vector.projectOnto(other)
            it("should projectOnto and assign") {
                val expected = original.projectOnto(other, MutableVector2())

                assert(vector).isEqualTo(expected)
            }
        }

        on("rejectFrom") {
            val original = vector.copyImmutable()
            val other = getRandomVector2()
            vector.rejectFrom(other)
            it("should rejectFrom and assign") {
                val expected = original.rejectFrom(other, MutableVector2())

                assert(vector).isEqualTo(expected)
            }
        }

        on("multiplyLeft") {
            val original = vector.copyImmutable()
            val matrix = getRandomMatrix2()
            vector.multiplyLeft(matrix)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(matrix, MutableVector2())

                assert(vector).isEqualTo(expected)
            }
        }
    }
})

private fun getRandomValue() = random(-100f, 100f)
private fun getRandomVector2() = Vector2(getRandomValue(), getRandomValue())
private fun getRandomVector3() = Vector3(getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomVector4() = Vector4(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomMutableVector2(observer: ((Vector2) -> Unit)) = MutableVector2(getRandomValue(), getRandomValue(), observer)
private fun getRandomMatrix2() = Matrix2(
        getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue()
)