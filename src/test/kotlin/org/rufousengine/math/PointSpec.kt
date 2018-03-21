package org.rufousengine.math

import assertk.assert
import assertk.assertions.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.rufousengine.assertions.isCloseTo
import kotlin.math.*

object PointSpec: Spek({
    describe("immutable constructors") {
        on("empty") {
            val point = Point()
            it("should be the origin point") {
                assert(point).isEqualTo(Point.origin)
            }
        }
        on("primary") {
            val x = getRandomValue()
            val y = getRandomValue()
            val z = getRandomValue()
            val point = Point(x, y, z)
            it("should have x set") {
                assert(point.x).isCloseTo(x)
            }
            it("should have y set") {
                assert(point.y).isCloseTo(y)
            }
            it("should have z set") {
                assert(point.z).isCloseTo(z)
            }
        }
        on("Point") {
            val vector = getRandomPoint()
            val point = Point(vector)
            it("should have x set") {
                assert(point.x).isCloseTo(vector.x)
            }
            it("should have y set") {
                assert(point.y).isCloseTo(vector.y)
            }
            it("should have z set") {
                assert(point.z).isCloseTo(vector.z)
            }
        }
        on("Vector2") {
            val vector = getRandomVector2()
            val point = Point(vector)
            it("should have x set") {
                assert(point.x).isCloseTo(vector.x)
            }
            it("should have y set") {
                assert(point.y).isCloseTo(vector.y)
            }
            it("z should be 0") {
                assert(point.z).isCloseTo(0f)
            }
        }
        on("Vector3") {
            val vector = getRandomVector3()
            val point = Point(vector)
            it("should have x set") {
                assert(point.x).isCloseTo(vector.x)
            }
            it("should have y set") {
                assert(point.y).isCloseTo(vector.y)
            }
            it("should have z set") {
                assert(point.z).isCloseTo(vector.z)
            }
        }
        on("Vector4") {
            val vector = getRandomVector4()
            val point = Point(vector)
            it("should have x set") {
                assert(point.x).isCloseTo(vector.x / vector.w)
            }
            it("should have y set") {
                assert(point.y).isCloseTo(vector.y / vector.w)
            }
            it("should have z set") {
                assert(point.z).isCloseTo(vector.z / vector.w)
            }
        }
    }

    describe("mutable constructors") {
        on("empty") {
            val point = MutablePoint { }
            it("should be the origin point") {
                assert(point).isEqualTo(Point.origin)
            }
            it("should have the observer set") {
                assert(point.observer).isNotNull()
            }
        }
        on("primary") {
            val x = getRandomValue()
            val y = getRandomValue()
            val z = getRandomValue()
            val point = MutablePoint(x, y, z) { }
            it("should have x set") {
                assert(point.x).isCloseTo(x)
            }
            it("should have y set") {
                assert(point.y).isCloseTo(y)
            }
            it("should have z set") {
                assert(point.z).isCloseTo(z)
            }
            it("should have the observer set") {
                assert(point.observer).isNotNull()
            }
        }
        on("Point") {
            val other = getRandomPoint()
            val point = MutablePoint(other) { }
            it("should have x set") {
                assert(point.x).isCloseTo(other.x)
            }
            it("should have y set") {
                assert(point.y).isCloseTo(other.y)
            }
            it("should have z set") {
                assert(point.z).isCloseTo(other.z)
            }
            it("should have the observer set") {
                assert(point.observer).isNotNull()
            }
        }
        on("Vector2") {
            val vector = getRandomVector2()
            val point = MutablePoint(vector) { }
            it("should have x set") {
                assert(point.x).isCloseTo(vector.x)
            }
            it("should have y set") {
                assert(point.y).isCloseTo(vector.y)
            }
            it("z should be 0") {
                assert(point.z).isCloseTo(0f)
            }
            it("should have the observer set") {
                assert(point.observer).isNotNull()
            }
        }
        on("Vector3") {
            val vector = getRandomVector3()
            val point = MutablePoint(vector) { }
            it("should have x set") {
                assert(point.x).isCloseTo(vector.x)
            }
            it("should have y set") {
                assert(point.y).isCloseTo(vector.y)
            }
            it("should have z set") {
                assert(point.z).isCloseTo(vector.z)
            }
            it("should have the observer set") {
                assert(point.observer).isNotNull()
            }
        }
        on("Vector4") {
            val vector = getRandomVector4()
            val point = MutablePoint(vector) { }
            it("should have x set") {
                assert(point.x).isCloseTo(vector.x / vector.w)
            }
            it("should have y set") {
                assert(point.y).isCloseTo(vector.y / vector.w)
            }
            it("should have z set") {
                assert(point.z).isCloseTo(vector.z / vector.w)
            }
            it("should have the observer set") {
                assert(point.observer).isNotNull()
            }
        }
    }

    describe("premade points") {
        on("origin") {
            val point = Point.origin
            it("x should be 0") {
                assert(point.x).isCloseTo(0f)
            }
            it("y should be 0") {
                assert(point.y).isCloseTo(0f)
            }
            it("z should be 0") {
                assert(point.z).isCloseTo(0f)
            }
            it("should be the origin point") {
                assert(point.isOrigin).isTrue()
            }
        }
    }

    given("a point") {
        val point by memoized { getRandomPoint() }

        on("copyImmutable") {
            val copy = point.copyImmutable()
            it("should be a new instance") {
                assert(copy).isNotSameAs(point)
            }
            it("should be equal to the original") {
                assert(copy).isEqualTo(point)
            }
            it("should be immutable") {
                assert(copy).isInstanceOf(Point::class)
            }
        }

        on("copyMutable") {
            val copy = point.copyMutable()
            it("should be a new instance") {
                assert(copy).isNotSameAs(point)
            }
            it("should be equal to the original") {
                assert(copy).isEqualTo(point)
            }
            it("should be mutable") {
                assert(copy).isInstanceOf(MutablePoint::class)
            }
        }

        on("floor") {
            val floor = point.floor(MutablePoint())
            it("should set every component to its floor") {
                for (i in 0 until 3) {
                    assert(floor[i]).isCloseTo(floor(point[i]))
                }
            }
        }

        on("ceil") {
            val ceil = point.ceil(MutablePoint())
            it("should set every component to its ceil") {
                for (i in 0 until 3) {
                    assert(ceil[i]).isCloseTo(ceil(point[i]))
                }
            }
        }

        on("abs") {
            val abs = point.abs(MutablePoint())
            it("should set every component to its absolute value") {
                for (i in 0 until 3) {
                    assert(abs[i]).isCloseTo(abs(point[i]))
                }
            }
        }

        on("scale") {
            val scalar = getRandomValue()
            val scaled = point.scale(scalar, MutablePoint())
            it("should scale every component") {
                for (i in 0 until 3) {
                    assert(scaled[i]).isCloseTo(point[i] * scalar)
                }
            }
        }

        on("distance") {
            val other = getRandomPoint()
            val distance = point.distance(other)
            it("should return the magnitude of the vector between the two points") {
                val vector = point.subtract(other, MutableVector3())
                assert(distance).isCloseTo(vector.magnitude)
            }
        }

        on("distanceSquared") {
            val other = getRandomPoint()
            val distanceSquared = point.distanceSquared(other)
            it("should return the square of the distance") {
                val distance = point.distance(other)
                val expected = distance * distance

                assert(distanceSquared).isCloseTo(expected)
            }
        }

        on("add (Point)") {
            val other = getRandomPoint()
            val add = point.add(other, MutablePoint())
            it("should add componentwise") {
                for (i in 0 until 3) {
                    assert(add[i]).isCloseTo(point[i] + other[i])
                }
            }
        }

        on("add (Vector)") {
            val vector = getRandomVector3()
            val add = point.add(vector, MutablePoint())
            it("should add componentwise") {
                for (i in 0 until 3) {
                    assert(add[i]).isCloseTo(point[i] + vector[i])
                }
            }
        }

        on("subtract (Point)") {
            val other = getRandomPoint()
            val subtract = point.subtract(other, MutablePoint())
            it("should subtract componentwise") {
                for (i in 0 until 3) {
                    assert(subtract[i]).isCloseTo(point[i] - other[i])
                }
            }
        }

        on("subtract (Vector3)") {
            val other = getRandomVector3()
            val subtract = point.subtract(other, MutablePoint())
            it("should subtract componentwise") {
                for (i in 0 until 3) {
                    assert(subtract[i]).isCloseTo(point[i] - other[i])
                }
            }
        }

        on("lerp") {
            val other = getRandomPoint()
            val progress = getRandomValue()
            val interpolated = point.lerp(other, progress, MutablePoint())
            it("should lerp componentwise") {
                for (i in 0 until 3) {
                    assert(interpolated[i]).isCloseTo(lerp(point[i], other[i], progress))
                }
            }
        }

        on("min") {
            val other = getRandomPoint()
            val min = point.min(other, MutablePoint())
            it("should perform min componentwise") {
                for (i in 0 until 3) {
                    assert(min[i]).isCloseTo(min(point[i], other[i]))
                }
            }
        }

        on("max") {
            val other = getRandomPoint()
            val max = point.max(other, MutablePoint())
            it("should perform max componentwise") {
                for (i in 0 until 3) {
                    assert(max[i]).isCloseTo(max(point[i], other[i]))
                }
            }
        }

        on("multiplyLeft (Projection)") {
            val matrix = getRandomProjection()
            val multiplied = point.multiplyLeft(matrix, MutablePoint())
            it("should give the same results as Projection::multiply") {
                val expected = matrix.multiply(point, MutablePoint())

                assert(multiplied).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Transformation)") {
            val matrix = getRandomTransformation()
            val multiplied = point.multiplyLeft(matrix, MutablePoint())
            it("should give the same results as Projection::multiply") {
                val expected = matrix.multiply(point, MutablePoint())

                assert(multiplied).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Matrix4)") {
            val matrix = getRandomMatrix4()
            val multiplied = point.multiplyLeft(matrix, MutablePoint())
            it("should give the same results as Matrix4::multiply") {
                val expected = matrix.multiply(point, MutablePoint())

                assert(multiplied).isEqualTo(expected)
            }
        }
    }

    given("a mutable point") {
        var counter = 0
        val point by memoized { getRandomMutable { counter++ } }

        describe("seters") {
            on("x") {
                counter = 0
                val value = getRandomValue()
                point.x = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(point.x).isCloseTo(value)
                }
            }

            on("y") {
                counter = 0
                val value = getRandomValue()
                point.y = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have y set") {
                    assert(point.y).isCloseTo(value)
                }
            }

            on("z") {
                counter = 0
                val value = getRandomValue()
                point.z = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have z set") {
                    assert(point.z).isCloseTo(value)
                }
            }

            on("Point") {
                counter = 0
                val other = getRandomPoint()
                point.set(other)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(point.x).isCloseTo(other.x)
                }
                it("should have y set") {
                    assert(point.y).isCloseTo(other.y)
                }
                it("should have z set") {
                    assert(point.z).isCloseTo(other.z)
                }
            }

            on("Vector2") {
                counter = 0
                val vector = getRandomVector2()
                point.set(vector)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(point.x).isCloseTo(vector.x)
                }
                it("should have y set") {
                    assert(point.y).isCloseTo(vector.y)
                }
                it("z should be 0") {
                    assert(point.z).isCloseTo(0f)
                }
            }

            on("Vector3") {
                counter = 0
                val vector = getRandomVector3()
                point.set(vector)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(point.x).isCloseTo(vector.x)
                }
                it("should have y set") {
                    assert(point.y).isCloseTo(vector.y)
                }
                it("should have z set") {
                    assert(point.z).isCloseTo(vector.z)
                }
            }

            on("Vector4") {
                counter = 0
                val vector = getRandomVector4()
                point.set(vector)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(point.x).isCloseTo(vector.x / vector.w)
                }
                it("should have y set") {
                    assert(point.y).isCloseTo(vector.y / vector.w)
                }
                it("should have z set") {
                    assert(point.z).isCloseTo(vector.z / vector.w)
                }
            }

            on("set") {
                counter = 0
                val x = getRandomValue()
                val y = getRandomValue()
                val z = getRandomValue()
                point.set(x, y, z)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have x set") {
                    assert(point.x).isCloseTo(x)
                }
                it("should have y set") {
                    assert(point.y).isCloseTo(y)
                }
                it("should have z set") {
                    assert(point.z).isCloseTo(z)
                }
            }

            on("operator") {
                counter = 0
                val x = getRandomValue()
                val y = getRandomValue()
                val z = getRandomValue()
                point[0] = x
                point[1] = y
                point[2] = z
                it("should have notified thrice") {
                    assert(counter).isEqualTo(3)
                }
                it("should have x set") {
                    assert(point.x).isCloseTo(x)
                }
                it("should have y set") {
                    assert(point.y).isCloseTo(y)
                }
                it("should have z set") {
                    assert(point.z).isCloseTo(z)
                }
            }
        }

        describe("operators") {
            on("plusAssign (Point)") {
                val original = point.copyMutable()
                val other = getRandomPoint()
                point += other
                it("should add and assign") {
                    assert(point).isEqualTo(original.add(other))
                }
            }
            on("plusAssign (Vector3)") {
                val original = point.copyMutable()
                val vector = getRandomVector3()
                point += vector
                it("should add and assign") {
                    assert(point).isEqualTo(original.add(vector))
                }
            }
            on("minusAssign (Point)") {
                val original = point.copyMutable()
                val other = getRandomPoint()
                point -= other
                it("should subtract and assign") {
                    assert(point).isEqualTo(original.subtract(other))
                }
            }
            on("minusAssign (Vector3)") {
                val original = point.copyMutable()
                val vector = getRandomVector3()
                point -= vector
                it("should subtract and assign") {
                    assert(point).isEqualTo(original.subtract(vector))
                }
            }
            on("timesAssign") {
                val original = point.copyMutable()
                val scalar = getRandomValue()
                point *= scalar
                it("should scale and assign") {
                    assert(point).isEqualTo(original.scale(scalar))
                }
            }
            on("divAssign") {
                val original = point.copyMutable()
                val scalar = getRandomValue()
                point /= scalar
                it("should scale and assign") {
                    assert(point).isEqualTo(original.scale(1 / scalar))
                }
            }
        }

        on("floor") {
            val original = point.copyImmutable()
            point.floor()
            it("should floor and assign") {
                val expected = original.floor(MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }

        on("ceil") {
            val original = point.copyImmutable()
            point.ceil()
            it("should ceil and assign") {
                val expected = original.ceil(MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }

        on("abs") {
            val original = point.copyImmutable()
            point.abs()
            it("should abs and assign") {
                val expected = original.abs(MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }

        on("scale") {
            val original = point.copyImmutable()
            val scalar = getRandomValue()
            point.scale(scalar)
            it("should scale and assign") {
                val expected = original.scale(scalar, MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }

        on("add (Point)") {
            val original = point.copyImmutable()
            val other = getRandomPoint()
            point.add(other)
            it("should add and assign") {
                val expected = original.add(other, MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }

        on("add (Vector3)") {
            val original = point.copyImmutable()
            val vector = getRandomVector3()
            point.add(vector)
            it("should add and assign") {
                val expected = original.add(vector, MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }

        on("subtract (Point)") {
            val original = point.copyImmutable()
            val other = getRandomPoint()
            point.subtract(other)
            it("should subtract and assign") {
                val expected = original.subtract(other, MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }

        on("subtract (Vector3)") {
            val original = point.copyImmutable()
            val vector = getRandomVector3()
            point.subtract(vector)
            it("should subtract and assign") {
                val expected = original.subtract(vector, MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }

        on("lerp") {
            val original = point.copyImmutable()
            val other = getRandomPoint()
            val progress = getRandomValue()
            point.lerp(other, progress)
            it("should lerp and assign") {
                val expected = original.lerp(other, progress, MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }

        on("min") {
            val original = point.copyImmutable()
            val other = getRandomPoint()
            point.min(other)
            it("should min and assign") {
                val expected = original.min(other, MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }

        on("max") {
            val original = point.copyImmutable()
            val other = getRandomPoint()
            point.max(other)
            it("should max and assign") {
                val expected = original.max(other, MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Projection)") {
            val original = point.copyImmutable()
            val matrix = getRandomProjection()
            point.multiplyLeft(matrix)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(matrix, MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Transformation)") {
            val original = point.copyImmutable()
            val matrix = getRandomTransformation()
            point.multiplyLeft(matrix)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(matrix, MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Matrix4)") {
            val original = point.copyImmutable()
            val matrix = getRandomMatrix4()
            point.multiplyLeft(matrix)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(matrix, MutablePoint())

                assert(point).isEqualTo(expected)
            }
        }
    }
})

private fun getRandomValue() = random(-100f, 100f)
private fun getRandomPoint() = Point(getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomVector2() = Vector2(getRandomValue(), getRandomValue())
private fun getRandomVector3() = Vector3(getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomVector4() = Vector4(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomMutable(observer: ((Point) -> Unit)) = MutablePoint(getRandomValue(), getRandomValue(), getRandomValue(), observer)
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