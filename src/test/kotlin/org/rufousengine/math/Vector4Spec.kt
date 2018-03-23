package org.rufousengine.math

import org.jetbrains.spek.api.Spek
import assertk.assert
import assertk.assertions.*
import org.jetbrains.spek.api.dsl.*
import org.rufousengine.assertions.isCloseTo
import kotlin.math.*

object Vector4Spec: Spek({
    describe("immutable constructors") {
        on("empty") {
            val vector = Vector4()
            it("should be the zero vector") {
                assert(vector).isEqualTo(Vector4.zero)
            }
        }
        on("primary") {
            val x = getRandomValue()
            val y = getRandomValue()
            val z = getRandomValue()
            val w = getRandomValue()
            val vector = Vector4(x, y, z, w)
            it("should have x set") {
                assert(vector.x).isCloseTo(x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(y)
            }
            it("should have z set") {
                assert(vector.z).isCloseTo(z)
            }
            it("should have w set") {
                assert(vector.w).isCloseTo(w)
            }
        }
        on("Point") {
            val other = getRandomPoint()
            val vector = Vector4(other)
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("should have z set") {
                assert(vector.z).isCloseTo(other.z)
            }
            it("w should be 1") {
                assert(vector.w).isCloseTo(1f)
            }
        }
        on("Vector2") {
            val other = getRandomVector2()
            val vector = Vector4(other)
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("z should be 0") {
                assert(vector.z).isCloseTo(0f)
            }
            it("w should be 0") {
                assert(vector.w).isCloseTo(0f)
            }
        }
        on("Vector3") {
            val other = getRandomVector3()
            val vector = Vector4(other)
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("should have z set") {
                assert(vector.z).isCloseTo(other.z)
            }
            it("w should be 0") {
                assert(vector.w).isCloseTo(0f)
            }
        }
        on("Vector4") {
            val other = getRandomVector4()
            val vector = Vector4(other)
            it("should be equal to other") {
                assert(vector).isEqualTo(other)
            }
        }
    }

    describe("mutable constructors") {
        on("empty") {
            val vector = MutableVector4 { }
            it("should be the zero vector") {
                assert(vector).isEqualTo(Vector4.zero)
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
            val vector = MutableVector4(x, y, z, w) { }
            it("should have x set") {
                assert(vector.x).isCloseTo(x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(y)
            }
            it("should have z set") {
                assert(vector.z).isCloseTo(z)
            }
            it("should have w set") {
                assert(vector.w).isCloseTo(w)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
        on("Vector3") {
            val other = getRandomPoint()
            val vector = MutableVector4(other) { }
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("should have z set") {
                assert(vector.z).isCloseTo(other.z)
            }
            it("w should be 1") {
                assert(vector.w).isCloseTo(1f)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
        on("Vector2") {
            val other = getRandomVector2()
            val vector = MutableVector4(other) { }
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("z should be 0") {
                assert(vector.z).isCloseTo(0f)
            }
            it("w should be 0") {
                assert(vector.w).isCloseTo(0f)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
        on("Vector3") {
            val other = getRandomVector3()
            val vector = MutableVector4(other) { }
            it("should have x set") {
                assert(vector.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(vector.y).isCloseTo(other.y)
            }
            it("should have z set") {
                assert(vector.z).isCloseTo(other.z)
            }
            it("w should be 0") {
                assert(vector.w).isCloseTo(0f)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
        on("Vector4") {
            val other = getRandomVector4()
            val vector = MutableVector4(other) { }
            it("should be equal to other") {
                assert(vector).isEqualTo(other)
            }
            it("should have the observer set") {
                assert(vector.observer).isNotNull()
            }
        }
    }

    describe("premade vectors") {
        on("zero") {
            val vector = Vector4.zero
            it("x should be 0") {
                assert(vector.x).isCloseTo(0f)
            }
            it("y should be 0") {
                assert(vector.y).isCloseTo(0f)
            }
            it("z should be 0") {
                assert(vector.z).isCloseTo(0f)
            }
            it("w should be 0") {
                assert(vector.w).isCloseTo(0f)
            }
            it("should be the zero vector") {
                assert(vector.isZero).isTrue()
            }
        }
        on("one") {
            val vector = Vector4.one
            it("x should be 1") {
                assert(vector.x).isCloseTo(1f)
            }
            it("y should be 1") {
                assert(vector.y).isCloseTo(1f)
            }
            it("z should be 1") {
                assert(vector.z).isCloseTo(1f)
            }
            it("w should be 1") {
                assert(vector.w).isCloseTo(1f)
            }
            it("should be the one vector") {
                assert(vector.isOne).isTrue()
            }
        }
        on("i") {
            val vector = Vector4.i
            it("x should be 1") {
                assert(vector.x).isCloseTo(1f)
            }
            it("y should be 0") {
                assert(vector.y).isCloseTo(0f)
            }
            it("z should be 0") {
                assert(vector.z).isCloseTo(0f)
            }
            it("w should be 0") {
                assert(vector.w).isCloseTo(0f)
            }
        }
        on("j") {
            val vector = Vector4.j
            it("x should be 0") {
                assert(vector.x).isCloseTo(0f)
            }
            it("y should be 1") {
                assert(vector.y).isCloseTo(1f)
            }
            it("z should be 0") {
                assert(vector.z).isCloseTo(0f)
            }
            it("w should be 0") {
                assert(vector.w).isCloseTo(0f)
            }
        }
        on("k") {
            val vector = Vector4.k
            it("x should be 0") {
                assert(vector.x).isCloseTo(0f)
            }
            it("y should be 0") {
                assert(vector.y).isCloseTo(0f)
            }
            it("z should be 1") {
                assert(vector.z).isCloseTo(1f)
            }
            it("w should be 0") {
                assert(vector.w).isCloseTo(0f)
            }
        }
        on("k") {
            val vector = Vector4.l
            it("x should be 0") {
                assert(vector.x).isCloseTo(0f)
            }
            it("y should be 0") {
                assert(vector.y).isCloseTo(0f)
            }
            it("z should be 0") {
                assert(vector.z).isCloseTo(0f)
            }
            it("w should be 1") {
                assert(vector.w).isCloseTo(1f)
            }
        }
    }

    given("a vector") {
        val vector by memoized { getRandomVector4() }

        on("magnitude") {
            val magnitude = vector.magnitude
            it("should be the squared root of the sum of its components squares") {
                var expected = 0f
                for(i in 0 until 4) {
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
                for (i in 1 until 4) {
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
                for (i in 1 until 4) {
                    if (vector[i] > expected) {
                        expected = vector[i]
                    }
                }

                assert(component).isCloseTo(expected)
            }
        }

        on("minDimension") {
            val dimension = vector.minDimension
            it("should return the smallest dimension (0 if x, 1 if y, 2 if z or 3 if w)") {
                var expected = 0
                for (i in 1 until 4) {
                    if (vector[i] < vector[expected]) {
                        expected = i
                    }
                }

                assert(dimension).isEqualTo(expected)
            }
        }

        on("maxComponent") {
            val dimension = vector.maxDimension
            it("should return the largest dimension (0 if x, 1 if y, 2 if z or 3 if w)") {
                var expected = 0
                for (i in 1 until 4) {
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
                assert(copy).isInstanceOf(Vector4::class)
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
                assert(copy).isInstanceOf(MutableVector4::class)
            }
        }

        on("negate") {
            val negative = vector.negate(MutableVector4())
            it("should negate every component") {
                for (i in 0 until 4) {
                    assert(negative[i]).isCloseTo(-vector[i])
                }
            }
        }

        on("normalize") {
            val normalized = vector.normalize(MutableVector4())
            it("should be unit") {
                assert(normalized.isUnit).isTrue()
            }
            it("should be the original vector scaled") {
                for (i in 0 until 4) {
                    assert(normalized[i] * vector.magnitude).isCloseTo(vector[i])
                }
            }
        }

        on("abs") {
            val abs = vector.abs(MutableVector4())
            it("should set every component to its absolute value") {
                for (i in 0 until 4) {
                    assert(abs[i]).isCloseTo(abs(vector[i]))
                }
            }
        }

        on("scale") {
            val scalar = getRandomValue()
            val scaled = vector.scale(scalar, MutableVector4())
            it("should scale every component") {
                for (i in 0 until 4) {
                    assert(scaled[i]).isCloseTo(vector[i] * scalar)
                }
            }
        }

        on("getAngle") {
            val other = getRandomVector4()
            val angle = vector.getAngle(other)
            it("should satisfy the dot product definition") {
                val dot = vector dot other
                val expected = acos(dot / (vector.magnitude * other.magnitude)).toDegrees()

                assert(angle).isCloseTo(expected)
            }
        }

        on("dot") {
            val other = getRandomVector4()
            val dot = vector dot other
            it("should be the sum of the product of the corresponding components") {
                var expected = 0f
                for (i in 0 until 4) {
                    expected += vector[i] * other[i]
                }

                assert(dot).isCloseTo(expected)
            }
        }

        on("dotAbs") {
            val other = getRandomVector4()
            val dotAbs = vector dotAbs other
            it("should be the absolute value of the dot product") {
                assert(dotAbs).isCloseTo(abs(vector dot other))
            }
        }

        on("add") {
            val other = getRandomVector4()
            val add = vector.add(other, MutableVector4())
            it("should add componentwise") {
                for (i in 0 until 4) {
                    assert(add[i]).isCloseTo(vector[i] + other[i])
                }
            }
        }

        on("subtract") {
            val other = getRandomVector4()
            val subtract = vector.subtract(other, MutableVector4())
            it("should subtract componentwise") {
                for (i in 0 until 4) {
                    assert(subtract[i]).isCloseTo(vector[i] - other[i])
                }
            }
        }

        on("min") {
            val other = getRandomVector4()
            val min = vector.min(other, MutableVector4())
            it("should perform min componentwise") {
                for (i in 0 until 4) {
                    assert(min[i]).isCloseTo(min(vector[i], other[i]))
                }
            }
        }

        on("max") {
            val other = getRandomVector4()
            val max = vector.max(other, MutableVector4())
            it("should perform max componentwise") {
                for (i in 0 until 4) {
                    assert(max[i]).isCloseTo(max(vector[i], other[i]))
                }
            }
        }

        on("projectOnto") {
            val other = getRandomVector4()
            val projected = vector.projectOnto(other, MutableVector4())
            it("should project vector onto other") {
                val expected = other.scale(vector.dot(other) / other.magnitudeSquared, MutableVector4())

                assert(projected).isEqualTo(expected)
            }
        }

        on("rejectFrom") {
            val other = getRandomVector4()
            val rejected = vector.rejectFrom(other, MutableVector4())
            it("should reject vector from other") {
                val expected = vector.copyMutable().subtract(vector.projectOnto(other, MutableVector4()))

                assert(rejected).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Projection)") {
            val matrix = getRandomProjection()
            val multiplied = vector.multiplyLeft(matrix, MutableVector4())
            it("should give the same results as Projection::multiply") {
                val expected = matrix.multiply(vector, MutableVector4())

                assert(multiplied).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Transformation)") {
            val matrix = getRandomTransformation()
            val multiplied = vector.multiplyLeft(matrix, MutableVector4())
            it("should give the same results as Transformation::multiply") {
                val expected = matrix.multiply(vector, MutableVector4())

                assert(multiplied).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Matrix4)") {
            val matrix = getRandomMatrix4()
            val multiplied = vector.multiplyLeft(matrix, MutableVector4())
            it("should give the same results as Matrix4::multiply") {
                val expected = matrix.multiply(vector, MutableVector4())

                assert(multiplied).isEqualTo(expected)
            }
        }
    }

    given("a mutable vector") {
        var counter = 0
        val vector by memoized { getRandomMutableVector4 { counter++ } }

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

            on("w") {
                counter = 0
                val value = getRandomValue()
                vector.w = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have w set") {
                    assert(vector.w).isCloseTo(value)
                }
            }

            on("Point") {
                counter = 0
                val other = getRandomPoint()
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
                it("w should be 1") {
                    assert(vector.w).isCloseTo(1f)
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
                it("w should be 0") {
                    assert(vector.w).isCloseTo(0f)
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
                it("w should be 0") {
                    assert(vector.w).isCloseTo(0f)
                }
            }

            on("Vector4") {
                counter = 0
                val other = getRandomVector4()
                vector.set(other)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should be equal to other") {
                    assert(vector).isEqualTo(other)
                }
            }

            on("set") {
                counter = 0
                val x = getRandomValue()
                val y = getRandomValue()
                val z = getRandomValue()
                val w = getRandomValue()
                vector.set(x, y, z, w)
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
                it("should have w set") {
                    assert(vector.w).isCloseTo(w)
                }
            }

            on("operator") {
                counter = 0
                val x = getRandomValue()
                val y = getRandomValue()
                val z = getRandomValue()
                val w = getRandomValue()
                vector[0] = x
                vector[1] = y
                vector[2] = z
                vector[3] = w
                it("should have notified four times") {
                    assert(counter).isEqualTo(4)
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
                it("should have w set") {
                    assert(vector.w).isCloseTo(w)
                }
            }
        }

        describe("operators") {
            on("plusAssign") {
                val original = vector.copyMutable()
                val other = getRandomVector4()
                vector += other
                it("should add and assign") {
                    assert(vector).isEqualTo(original.add(other))
                }
            }
            on("minusAssign") {
                val original = vector.copyMutable()
                val other = getRandomVector4()
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
                val expected = original.negate(MutableVector4())

                assert(vector).isEqualTo(expected)
            }
        }

        on("normalize") {
            val original = vector.copyImmutable()
            vector.normalize()
            it("should normalize and assign") {
                val expected = original.normalize(MutableVector4())

                assert(vector).isEqualTo(expected)
            }
        }

        on("abs") {
            val original = vector.copyImmutable()
            vector.abs()
            it("should abs and assign") {
                val expected = original.abs(MutableVector4())

                assert(vector).isEqualTo(expected)
            }
        }

        on("scale") {
            val original = vector.copyImmutable()
            val scalar = getRandomValue()
            vector.scale(scalar)
            it("should scale and assign") {
                val expected = original.scale(scalar, MutableVector4())

                assert(vector).isEqualTo(expected)
            }
        }

        on("add") {
            val original = vector.copyImmutable()
            val other = getRandomVector4()
            vector.add(other)
            it("should add and assign") {
                val expected = original.add(other, MutableVector4())

                assert(vector).isEqualTo(expected)
            }
        }

        on("subtract") {
            val original = vector.copyImmutable()
            val other = getRandomVector4()
            vector.subtract(other)
            it("should subtract and assign") {
                val expected = original.subtract(other, MutableVector4())

                assert(vector).isEqualTo(expected)
            }
        }

        on("min") {
            val original = vector.copyImmutable()
            val other = getRandomVector4()
            vector.min(other)
            it("should min and assign") {
                val expected = original.min(other, MutableVector4())

                assert(vector).isEqualTo(expected)
            }
        }

        on("max") {
            val original = vector.copyImmutable()
            val other = getRandomVector4()
            vector.max(other)
            it("should max and assign") {
                val expected = original.max(other, MutableVector4())

                assert(vector).isEqualTo(expected)
            }
        }

        on("projectOnto") {
            val original = vector.copyImmutable()
            val other = getRandomVector4()
            vector.projectOnto(other)
            it("should projectOnto and assign") {
                val expected = original.projectOnto(other, MutableVector4())

                assert(vector).isEqualTo(expected)
            }
        }

        on("rejectFrom") {
            val original = vector.copyImmutable()
            val other = getRandomVector4()
            vector.rejectFrom(other)
            it("should rejectFrom and assign") {
                val expected = original.rejectFrom(other, MutableVector4())

                assert(vector).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Projection)") {
            val original = vector.copyImmutable()
            val matrix = getRandomProjection()
            vector.multiplyLeft(matrix)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(matrix, MutableVector4())

                assert(vector).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Transformation)") {
            val original = vector.copyImmutable()
            val matrix = getRandomTransformation()
            vector.multiplyLeft(matrix)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(matrix, MutableVector4())

                assert(vector).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Matrix4)") {
            val original = vector.copyImmutable()
            val matrix = getRandomMatrix4()
            vector.multiplyLeft(matrix)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(matrix, MutableVector4())

                assert(vector).isEqualTo(expected)
            }
        }
    }
})

private fun getRandomValue() = random(-100f, 100f)
private fun getRandomPoint() = Point(getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomVector2() = Vector2(getRandomValue(), getRandomValue())
private fun getRandomVector3() = Vector3(getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomVector4() = Vector4(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomMutableVector4(observer: ((Vector4) -> Unit)) = MutableVector4(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(), observer)
private fun getRandomProjection() : Projection {
    val p = MutableProjection()
    p.set(
            getRandomValue(),
            getRandomValue(),
            getRandomValue(), getRandomValue(),
            getRandomValue(), getRandomValue()
    )

    return Projection(p)
}
private fun getRandomTransformation() : Transformation {
    val t = MutableTransformation()
    t.set(
            getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
            getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
            getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()
    )

    return Transformation(t)
}
private fun getRandomMatrix4() = Matrix4(
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()
)