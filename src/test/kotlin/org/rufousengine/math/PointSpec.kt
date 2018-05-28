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
    describe("constructors") {
        on("empty") {
            val point = Point3D()
            it("should be the origin point") {
                assert(point.isOrigin).isTrue()
            }
        }
        on("primary") {
            val x = getRandomValue()
            val y = getRandomValue()
            val z = getRandomValue()
            val point = Point3D(x, y, z)
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
            val vector = getRandomPoint3D()
            val point = Point3D(vector)
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
    }

    given("a point") {
        val point by memoized { getRandomPoint3D() }

        describe("seters") {
            on("x") {
                val value = getRandomValue()
                point.x = value
                it("should have x set") {
                    assert(point.x).isCloseTo(value)
                }
            }

            on("y") {
                val value = getRandomValue()
                point.y = value
                it("should have y set") {
                    assert(point.y).isCloseTo(value)
                }
            }

            on("z") {
                val value = getRandomValue()
                point.z = value
                it("should have z set") {
                    assert(point.z).isCloseTo(value)
                }
            }

            on("Point") {
                val other = getRandomPoint3D()
                point.set(other)
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

            on("set") {
                val x = getRandomValue()
                val y = getRandomValue()
                val z = getRandomValue()
                point.set(x, y, z)
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
                val x = getRandomValue()
                val y = getRandomValue()
                val z = getRandomValue()
                point[0] = x
                point[1] = y
                point[2] = z
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
            on("plus (Vector2)") {
                val vector = getRandomVector2()
                val new = point + vector
                it("should add and create new one") {
                    assert(new).isEqualTo(point.add(vector))
                }
            }
            on("plus (Vector3)") {
                val vector = getRandomVector3()
                val new = point + vector
                it("should add and create new one") {
                    assert(new).isEqualTo(point.add(vector))
                }
            }
            on("minus (Vector2)") {
                val vector = getRandomVector2()
                val new = point - vector
                it("should subtract and create new one") {
                    assert(new).isEqualTo(point.subtract(vector))
                }
            }
            on("minus (Vector3)") {
                val vector = getRandomVector3()
                val new = point - vector
                it("should subtract and create new one") {
                    assert(new).isEqualTo(point.subtract(vector))
                }
            }
            /*
            on("minus (Point2D)") {
                val other = getRandomPoint2D()
                val new = point - other
                it("should subtract and create new one") {
                    assert(new).isEqualTo(point.subtract(other))
                }
            }
            */
            on("minus (Point3D)") {
                val other = getRandomPoint3D()
                val new = point - other
                it("should subtract and create new one") {
                    assert(new).isEqualTo(point.subtract(other, Vector3()))
                }
            }
            on("times") {
                val scalar = getRandomValue()
                val new = point * scalar
                it("should scale and assign") {
                    assert(new).isEqualTo(point.scale(scalar))
                }
            }
            on("divAssign") {
                val scalar = getRandomValue()
                val new = point / scalar
                it("should scale and assign") {
                    assert(new).isEqualTo(point.scale(1 / scalar))
                }
            }

            on("plusAssign (Vector2)") {
                val original = point.copy()
                val vector = getRandomVector2()
                point += vector
                it("should add and assign") {
                    assert(point).isEqualTo(original.add(vector))
                }
            }
            on("plusAssign (Vector3)") {
                val original = point.copy()
                val vector = getRandomVector3()
                point += vector
                it("should add and assign") {
                    assert(point).isEqualTo(original.add(vector))
                }
            }
            on("minusAssign (Vector2)") {
                val original = point.copy()
                val vector = getRandomVector2()
                point -= vector
                it("should subtract and assign") {
                    assert(point).isEqualTo(original.subtract(vector))
                }
            }
            on("minusAssign (Vector3)") {
                val original = point.copy()
                val vector = getRandomVector3()
                point -= vector
                it("should subtract and assign") {
                    assert(point).isEqualTo(original.subtract(vector))
                }
            }
            on("timesAssign") {
                val original = point.copy()
                val scalar = getRandomValue()
                point *= scalar
                it("should scale and assign") {
                    assert(point).isEqualTo(original.scale(scalar))
                }
            }
            on("divAssign") {
                val original = point.copy()
                val scalar = getRandomValue()
                point /= scalar
                it("should scale and assign") {
                    assert(point).isEqualTo(original.scale(1 / scalar))
                }
            }
        }

        on("copy") {
            val copy = point.copy()
            it("should be a new instance") {
                assert(copy).isNotSameAs(point)
            }
            it("should be equal to the original") {
                assert(copy).isEqualTo(point)
            }
        }

        on("floor") {
            val floor = point.copy().floor()
            it("should set every component to its floor") {
                for (i in 0 until 3) {
                    assert(floor[i]).isCloseTo(floor(point[i]))
                }
            }
        }

        on("ceil") {
            val ceil = point.copy().ceil()
            it("should set every component to its ceil") {
                for (i in 0 until 3) {
                    assert(ceil[i]).isCloseTo(ceil(point[i]))
                }
            }
        }

        on("abs") {
            val abs = point.copy().abs()
            it("should set every component to its absolute value") {
                for (i in 0 until 3) {
                    assert(abs[i]).isCloseTo(abs(point[i]))
                }
            }
        }

        on("scale") {
            val scalar = getRandomValue()
            val scaled = point.copy().scale(scalar)
            it("should scale every component") {
                for (i in 0 until 3) {
                    assert(scaled[i]).isCloseTo(point[i] * scalar)
                }
            }
        }

        on("distance") {
            val other = getRandomPoint3D()
            val distance = point.distance(other)
            it("should return the magnitude of the vector between the two points") {
                val vector = point - other
                assert(distance).isCloseTo(vector.magnitude)
            }
        }

        on("distanceSquared") {
            val other = getRandomPoint3D()
            val distanceSquared = point.distanceSquared(other)
            it("should return the square of the distance") {
                val distance = point.distance(other)
                val expected = distance * distance

                assert(distanceSquared).isCloseTo(expected)
            }
        }

        on("add (Vector)") {
            val vector = getRandomVector3()
            val add = point.copy().add(vector)
            it("should add componentwise") {
                for (i in 0 until 3) {
                    assert(add[i]).isCloseTo(point[i] + vector[i])
                }
            }
        }

        on("subtract (Point)") {
            val other = getRandomPoint3D()
            val subtract = point.subtract(other, Vector3())
            it("should subtract componentwise") {
                for (i in 0 until 3) {
                    assert(subtract[i]).isCloseTo(point[i] - other[i])
                }
            }
        }

        on("subtract (Vector3)") {
            val other = getRandomVector3()
            val subtract = point.copy().subtract(other)
            it("should subtract componentwise") {
                for (i in 0 until 3) {
                    assert(subtract[i]).isCloseTo(point[i] - other[i])
                }
            }
        }

        on("lerp") {
            val other = getRandomPoint3D()
            val progress = getRandomValue()
            val interpolated = point.copy().lerp(other, progress)
            it("should lerp componentwise") {
                for (i in 0 until 3) {
                    assert(interpolated[i]).isCloseTo(lerp(point[i], other[i], progress))
                }
            }
        }

        on("min") {
            val other = getRandomPoint3D()
            val min = point.copy().min(other)
            it("should perform min componentwise") {
                for (i in 0 until 3) {
                    assert(min[i]).isCloseTo(min(point[i], other[i]))
                }
            }
        }

        on("max") {
            val other = getRandomPoint3D()
            val max = point.copy().max(other)
            it("should perform max componentwise") {
                for (i in 0 until 3) {
                    assert(max[i]).isCloseTo(max(point[i], other[i]))
                }
            }
        }
    }
})

private fun getRandomValue() = random(-100f, 100f)
private fun getRandomPoint3D() = Point3D(getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomVector2() = Vector2(getRandomValue(), getRandomValue())
private fun getRandomVector3() = Vector3(getRandomValue(), getRandomValue(), getRandomValue())
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