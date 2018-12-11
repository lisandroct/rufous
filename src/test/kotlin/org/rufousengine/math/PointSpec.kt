package org.rufousengine.math

import org.rufousengine.assertions.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.math.*

object PointsSpec: Spek({
    setRandomSeed(5815762546420015104L)

    repeat(100) {
        val p = rPoint2D()
        describe("Point2D: $p") {
            val point by memoized { p.copy() }

            for (i in -1..3) {
                describe("Get $i index") {
                    when {
                        i < 0 || i >= 3 -> it("throws an IllegalArgumentException") {
                            assert(IllegalArgumentException::class).isThrownBy { point[i] }
                        }
                        i >= point.dimensions -> it("returns 0") {
                            assert(point[i]).isZero()
                        }
                        else -> it("returns the component value") {
                            assert(point[i]).isEqualTo(point.components[i])
                        }
                    }
                }
            }

            for (i in -1..3) {
                describe("Set $i index") {
                    val value = rScalar()
                    when {
                        i < 0 || i >= point.dimensions -> it("throws an IllegalArgumentException") {
                            assert(IllegalArgumentException::class).isThrownBy { point[i] = value }
                        }
                        else -> it("changes the component value") {
                            point[i] = value
                            assert(point[i]).isEqualTo(value)
                        }
                    }
                }
            }

            describe("Negate") {
                val result by memoized { negate(point) }

                for (i in 0 until point.dimensions) {
                    it("should negate the $i component") {
                        assert(result[i]).isEqualTo(-point[i])
                    }
                }
            }

            describe("Floor") {
                val result by memoized { floor(point) }

                for (i in 0 until point.dimensions) {
                    it("should compute the floor of the $i component") {
                        assert(result[i]).isEqualTo(floor(point[i]))
                    }
                }
            }

            describe("Ceil") {
                val result by memoized { ceil(point) }

                for (i in 0 until point.dimensions) {
                    it("should compute the ceil of the $i component") {
                        assert(result[i]).isEqualTo(ceil(point[i]))
                    }
                }
            }

            describe("Abs") {
                val result by memoized { abs(point) }

                for (i in 0 until point.dimensions) {
                    it("should compute the absolute value of the $i component") {
                        assert(result[i]).isEqualTo(abs(point[i]))
                    }
                }
            }

            describe("Scale") {
                val scalar = rScalar()
                val result by memoized { scale(point, scalar) }

                for (i in 0 until point.dimensions) {
                    it("should scale the $i component") {
                        assert(result[i]).isEqualTo(point[i] * scalar)
                    }
                }
            }

            describe("Operators") {
                describe("UnaryPlus") {
                    val result by memoized { +point }

                    it("should be equal to point") {
                        assert(result).isEqualTo(point)
                    }
                }

                describe("UnaryMinus") {
                    val result by memoized { -point }

                    it("should be equal to point negated") {
                        assert(result).isEqualTo(negate(point))
                    }
                }

                describe("Plus") {
                    val vector = rVector2()
                    val result by memoized { point + vector }

                    it("should be equal to the added points") {
                        assert(result).isEqualTo(add(point, vector))
                    }
                }

                describe("Plus") {
                    val vector = rVector3()
                    val result by memoized { point + vector }

                    it("should be equal to the added points") {
                        assert(result).isEqualTo(add(point, vector))
                    }
                }

                describe("Minus") {
                    val vector = rVector2()
                    val result by memoized { point - vector }

                    it("should be equal to the subtracted points") {
                        assert(result).isEqualTo(subtract(point, vector))
                    }
                }

                describe("Minus") {
                    val vector = rVector3()
                    val result by memoized { point - vector }

                    it("should be equal to the subtracted points") {
                        assert(result).isEqualTo(subtract(point, vector))
                    }
                }

                describe("Minus") {
                    val other = rPoint2D()
                    val result by memoized { point - other }

                    it("should be equal to the subtracted points") {
                        assert(result).isEqualTo(subtract(point, other))
                    }
                }

                describe("Minus") {
                    val other = rPoint3D()
                    val result by memoized { point - other }

                    it("should be equal to the subtracted points") {
                        assert(result).isEqualTo(subtract(point, other))
                    }
                }

                describe("Times") {
                    val scalar = rScalar()
                    val result by memoized { point * scalar }

                    it("should be equal to the scaled point") {
                        assert(result).isEqualTo(scale(point, scalar))
                    }
                }

                describe("Div") {
                    val scalar = rScalar()
                    val result by memoized { point / scalar }

                    it("should be equal to the scaled point") {
                        assert(result).isEqualTo(scale(point, 1 / scalar))
                    }
                }

                describe("PlusAssign") {
                    val other = rVector2()

                    it("should update point to be equal to the added points") {
                        val expected = point + other

                        point += other
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("MinusAssign") {
                    val other = rVector2()

                    it("should update point to be equal to the subtracted points") {
                        val expected = point - other

                        point -= other
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("TimesAssign") {
                    val scalar = rScalar()

                    it("should update point to be equal to the scaled point") {
                        val expected = point * scalar

                        point *= scalar
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("DivAssign") {
                    val scalar = rScalar()

                    it("should update point to be equal to the scaled point") {
                        val expected = point / scalar

                        point /= scalar
                        assert(point).isEqualTo(expected)
                    }
                }
            }

            describe("Extension functions") {
                describe("Negate") {
                    it("should update point to be equal to the negated point") {
                        val expected = negate(point)

                        point.negate()
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Floor") {
                    it("should update point to be equal to the floor point") {
                        val expected = floor(point)

                        point.floor()
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Ceil") {
                    it("should update point to be equal to the ceil point") {
                        val expected = ceil(point)

                        point.ceil()
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Abs") {
                    it("should update point to be equal to the abs point") {
                        val expected = abs(point)

                        point.abs()
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Scale") {
                    val scalar = rScalar()

                    it("should update point to be equal to the scaled point") {
                        val expected = scale(point, scalar)

                        point.scale(scalar)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Add") {
                    val other = rVector2()

                    it("should update point to be equal to the added points") {
                        val expected = add(point, other)

                        point.add(other)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Subtract") {
                    val other = rVector2()

                    it("should update point to be equal to the subtracted points") {
                        val expected = subtract(point, other)

                        point.subtract(other)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Lerp") {
                    val progress = rScalar()
                    val other = rPoint2D()

                    it("should update point to be equal to the lerp between the two points") {
                        val expected = lerp(point, other, progress)

                        point.lerp(other, progress)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Min") {
                    val other = rPoint2D()

                    it("should update point to be equal to the min between the two points") {
                        val expected = min(point, other)

                        point.min(other)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Max") {
                    val other = rPoint2D()

                    it("should update point to be equal to the max between the two points") {
                        val expected = max(point, other)

                        point.max(other)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("MultiplyLeft") {
                    val matrix = rMatrix4()

                    it("should update point to be the result of the matrix multiplication") {
                        val expected = multiply(matrix, point, Point2D())

                        point.multiplyLeft(matrix)
                        assert(point).isEqualTo(expected)
                    }
                }
            }
        }
    }

    repeat(100) {
        val p = rPoint2D()
        val v = rVector2()
        describe("Point2D: $p - Vector2: $v") {
            val a by memoized { p.copy() }
            val b by memoized { v.copy() }
            val dimensions by memoized { max(p.dimensions, v.dimensions) }

            describe("Add") {
                val result by memoized { add(a, b) }

                for(i in 0 until dimensions) {
                    it("should add the $i components") {
                        assert(result[i]).isEqualTo(a[i] + b[i])
                    }
                }
            }

            describe("Subtract") {
                val result by memoized { subtract(a, b) }

                for(i in 0 until dimensions) {
                    it("should subtract the $i components") {
                        assert(result[i]).isEqualTo(a[i] - b[i])
                    }
                }
            }
        }
    }

    repeat(100) {
        val p = rPoint2D()
        val v = rVector3()
        describe("Point2D: $p - Vector2: $v") {
            val a by memoized { p.copy() }
            val b by memoized { v.copy() }
            val dimensions by memoized { max(p.dimensions, v.dimensions) }

            describe("Add") {
                val result by memoized { add(a, b) }

                for(i in 0 until dimensions) {
                    it("should add the $i components") {
                        assert(result[i]).isEqualTo(a[i] + b[i])
                    }
                }
            }

            describe("Subtract") {
                val result by memoized { subtract(a, b) }

                for(i in 0 until dimensions) {
                    it("should subtract the $i components") {
                        assert(result[i]).isEqualTo(a[i] - b[i])
                    }
                }
            }
        }
    }

    repeat(100) {
        val p0 = rPoint2D()
        val p1 = rPoint2D()
        describe("Point2D: $p0 - Point2D: $p1") {
            val a by memoized { p0.copy() }
            val b by memoized { p1.copy() }
            val dimensions by memoized { max(p0.dimensions, p1.dimensions) }

            describe("Lerp") {
                val progress = rScalar()
                val result by memoized { lerp(a, b, progress) }

                for(i in 0 until dimensions) {
                    it("should set the $i component to the lerp between the two") {
                        assert(result[i]).isEqualTo(lerp(a[i], b[i], progress))
                    }
                }
            }

            describe("Min") {
                val result by memoized { min(a, b) }

                for(i in 0 until dimensions) {
                    it("should set the $i component to the smallest one between the two") {
                        assert(result[i]).isEqualTo(min(a[i], b[i]))
                    }
                }
            }

            describe("Max") {
                val result by memoized { max(a, b) }

                for(i in 0 until dimensions) {
                    it("should set the $i component to the largest one between the two") {
                        assert(result[i]).isEqualTo(max(a[i], b[i]))
                    }
                }
            }
        }
    }

    repeat(100) {
        val p0 = rPoint2D()
        val p1 = rPoint3D()
        describe("Point2D: $p0 - Point3D: $p1") {
            val a by memoized { p0.copy() }
            val b by memoized { p1.copy() }
            val dimensions by memoized { max(p0.dimensions, p1.dimensions) }

            describe("Lerp") {
                val progress = rScalar()
                val result by memoized { lerp(a, b, progress) }

                for(i in 0 until dimensions) {
                    it("should set the $i component to the lerp between the two") {
                        assert(result[i]).isEqualTo(lerp(a[i], b[i], progress))
                    }
                }
            }

            describe("Min") {
                val result by memoized { min(a, b) }

                for(i in 0 until dimensions) {
                    it("should set the $i component to the smallest one between the two") {
                        assert(result[i]).isEqualTo(min(a[i], b[i]))
                    }
                }
            }

            describe("Max") {
                val result by memoized { max(a, b) }

                for(i in 0 until dimensions) {
                    it("should set the $i component to the largest one between the two") {
                        assert(result[i]).isEqualTo(max(a[i], b[i]))
                    }
                }
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    repeat(100) {
        val p = rPoint3D()
        describe("Point3D: $p") {
            val point by memoized { p.copy() }

            for (i in -1..3) {
                describe("Get $i index") {
                    when {
                        i < 0 || i >= 3 -> it("throws an IllegalArgumentException") {
                            assert(IllegalArgumentException::class).isThrownBy { point[i] }
                        }
                        i >= point.dimensions -> it("returns 0") {
                            assert(point[i]).isZero()
                        }
                        else -> it("returns the component value") {
                            assert(point[i]).isEqualTo(point.components[i])
                        }
                    }
                }
            }

            for (i in -1..3) {
                describe("Set $i index") {
                    val value = rScalar()
                    when {
                        i < 0 || i >= point.dimensions -> it("throws an IllegalArgumentException") {
                            assert(IllegalArgumentException::class).isThrownBy { point[i] = value }
                        }
                        else -> it("changes the component value") {
                            point[i] = value
                            assert(point[i]).isEqualTo(value)
                        }
                    }
                }
            }

            describe("Negate") {
                val result by memoized { negate(point) }

                for (i in 0 until point.dimensions) {
                    it("should negate the $i component") {
                        assert(result[i]).isEqualTo(-point[i])
                    }
                }
            }

            describe("Floor") {
                val result by memoized { floor(point) }

                for (i in 0 until point.dimensions) {
                    it("should compute the floor of the $i component") {
                        assert(result[i]).isEqualTo(floor(point[i]))
                    }
                }
            }

            describe("Ceil") {
                val result by memoized { ceil(point) }

                for (i in 0 until point.dimensions) {
                    it("should compute the ceil of the $i component") {
                        assert(result[i]).isEqualTo(ceil(point[i]))
                    }
                }
            }

            describe("Abs") {
                val result by memoized { abs(point) }

                for (i in 0 until point.dimensions) {
                    it("should compute the absolute value of the $i component") {
                        assert(result[i]).isEqualTo(abs(point[i]))
                    }
                }
            }

            describe("Scale") {
                val scalar = rScalar()
                val result by memoized { scale(point, scalar) }

                for (i in 0 until point.dimensions) {
                    it("should scale the $i component") {
                        assert(result[i]).isEqualTo(point[i] * scalar)
                    }
                }
            }

            describe("Operators") {
                describe("UnaryPlus") {
                    val result by memoized { +point }

                    it("should be equal to point") {
                        assert(result).isEqualTo(point)
                    }
                }

                describe("UnaryMinus") {
                    val result by memoized { -point }

                    it("should be equal to point negated") {
                        assert(result).isEqualTo(negate(point))
                    }
                }

                describe("Plus") {
                    val vector = rVector2()
                    val result by memoized { point + vector }

                    it("should be equal to the added points") {
                        assert(result).isEqualTo(add(point, vector))
                    }
                }

                describe("Plus") {
                    val vector = rVector3()
                    val result by memoized { point + vector }

                    it("should be equal to the added points") {
                        assert(result).isEqualTo(add(point, vector))
                    }
                }

                describe("Minus") {
                    val vector = rVector2()
                    val result by memoized { point - vector }

                    it("should be equal to the subtracted points") {
                        assert(result).isEqualTo(subtract(point, vector))
                    }
                }

                describe("Minus") {
                    val vector = rVector3()
                    val result by memoized { point - vector }

                    it("should be equal to the subtracted points") {
                        assert(result).isEqualTo(subtract(point, vector))
                    }
                }

                describe("Minus") {
                    val other = rPoint2D()
                    val result by memoized { point - other }

                    it("should be equal to the subtracted points") {
                        assert(result).isEqualTo(subtract(point, other))
                    }
                }

                describe("Minus") {
                    val other = rPoint3D()
                    val result by memoized { point - other }

                    it("should be equal to the subtracted points") {
                        assert(result).isEqualTo(subtract(point, other))
                    }
                }

                describe("Times") {
                    val scalar = rScalar()
                    val result by memoized { point * scalar }

                    it("should be equal to the scaled point") {
                        assert(result).isEqualTo(scale(point, scalar))
                    }
                }

                describe("Div") {
                    val scalar = rScalar()
                    val result by memoized { point / scalar }

                    it("should be equal to the scaled point") {
                        assert(result).isEqualTo(scale(point, 1 / scalar))
                    }
                }

                describe("PlusAssign") {
                    val other = rVector2()

                    it("should update point to be equal to the added points") {
                        val expected = point + other

                        point += other
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("PlusAssign") {
                    val other = rVector3()

                    it("should update point to be equal to the added points") {
                        val expected = point + other

                        point += other
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("MinusAssign") {
                    val other = rVector2()

                    it("should update point to be equal to the subtracted points") {
                        val expected = point - other

                        point -= other
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("MinusAssign") {
                    val other = rVector3()

                    it("should update point to be equal to the subtracted points") {
                        val expected = point - other

                        point -= other
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("TimesAssign") {
                    val scalar = rScalar()

                    it("should update point to be equal to the scaled point") {
                        val expected = point * scalar

                        point *= scalar
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("DivAssign") {
                    val scalar = rScalar()

                    it("should update point to be equal to the scaled point") {
                        val expected = point / scalar

                        point /= scalar
                        assert(point).isEqualTo(expected)
                    }
                }
            }

            describe("Extension functions") {
                describe("Negate") {
                    it("should update point to be equal to the negated point") {
                        val expected = negate(point)

                        point.negate()
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Floor") {
                    it("should update point to be equal to the floor point") {
                        val expected = floor(point)

                        point.floor()
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Ceil") {
                    it("should update point to be equal to the ceil point") {
                        val expected = ceil(point)

                        point.ceil()
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Abs") {
                    it("should update point to be equal to the abs point") {
                        val expected = abs(point)

                        point.abs()
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Scale") {
                    val scalar = rScalar()

                    it("should update point to be equal to the scaled point") {
                        val expected = scale(point, scalar)

                        point.scale(scalar)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Add") {
                    val other = rVector2()

                    it("should update point to be equal to the added points") {
                        val expected = add(point, other)

                        point.add(other)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Add") {
                    val other = rVector3()

                    it("should update point to be equal to the added points") {
                        val expected = add(point, other)

                        point.add(other)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Subtract") {
                    val other = rVector2()

                    it("should update point to be equal to the subtracted points") {
                        val expected = subtract(point, other)

                        point.subtract(other)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Subtract") {
                    val other = rVector3()

                    it("should update point to be equal to the subtracted points") {
                        val expected = subtract(point, other)

                        point.subtract(other)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Lerp") {
                    val progress = rScalar()
                    val other = rPoint2D()

                    it("should update point to be equal to the lerp between the two points") {
                        val expected = lerp(point, other, progress)

                        point.lerp(other, progress)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Lerp") {
                    val progress = rScalar()
                    val other = rPoint3D()

                    it("should update point to be equal to the lerp between the two points") {
                        val expected = lerp(point, other, progress)

                        point.lerp(other, progress)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Min") {
                    val other = rPoint2D()

                    it("should update point to be equal to the min between the two points") {
                        val expected = min(point, other)

                        point.min(other)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Min") {
                    val other = rPoint3D()

                    it("should update point to be equal to the min between the two points") {
                        val expected = min(point, other)

                        point.min(other)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Max") {
                    val other = rPoint2D()

                    it("should update point to be equal to the max between the two points") {
                        val expected = max(point, other)

                        point.max(other)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("Max") {
                    val other = rPoint3D()

                    it("should update point to be equal to the max between the two points") {
                        val expected = max(point, other)

                        point.max(other)
                        assert(point).isEqualTo(expected)
                    }
                }

                describe("MultiplyLeft") {
                    val matrix = rMatrix4()

                    it("should update point to be the result of the matrix multiplication") {
                        val expected = multiply(matrix, point, Point3D())

                        point.multiplyLeft(matrix)
                        assert(point).isEqualTo(expected)
                    }
                }
            }
        }
    }

    repeat(100) {
        val p0 = rPoint3D()
        val p1 = rPoint2D()
        describe("Point3D: $p0 - Point2D: $p1") {
            val a by memoized { p0.copy() }
            val b by memoized { p1.copy() }
            val dimensions by memoized { max(p0.dimensions, p1.dimensions) }

            describe("Lerp") {
                val progress = rScalar()
                val result by memoized { lerp(a, b, progress) }

                for(i in 0 until dimensions) {
                    it("should set the $i component to the lerp between the two") {
                        assert(result[i]).isEqualTo(lerp(a[i], b[i], progress))
                    }
                }
            }

            describe("Min") {
                val result by memoized { min(a, b) }

                for(i in 0 until dimensions) {
                    it("should set the $i component to the smallest one between the two") {
                        assert(result[i]).isEqualTo(min(a[i], b[i]))
                    }
                }
            }

            describe("Max") {
                val result by memoized { max(a, b) }

                for(i in 0 until dimensions) {
                    it("should set the $i component to the largest one between the two") {
                        assert(result[i]).isEqualTo(max(a[i], b[i]))
                    }
                }
            }
        }
    }

    repeat(100) {
        val p0 = rPoint3D()
        val p1 = rPoint3D()
        describe("Point3D: $p0 - Point3D: $p1") {
            val a by memoized { p0.copy() }
            val b by memoized { p1.copy() }
            val dimensions by memoized { max(p0.dimensions, p1.dimensions) }

            describe("Lerp") {
                val progress = rScalar()
                val result by memoized { lerp(a, b, progress) }

                for(i in 0 until dimensions) {
                    it("should set the $i component to the lerp between the two") {
                        assert(result[i]).isEqualTo(lerp(a[i], b[i], progress))
                    }
                }
            }

            describe("Min") {
                val result by memoized { min(a, b) }

                for(i in 0 until dimensions) {
                    it("should set the $i component to the smallest one between the two") {
                        assert(result[i]).isEqualTo(min(a[i], b[i]))
                    }
                }
            }

            describe("Max") {
                val result by memoized { max(a, b) }

                for(i in 0 until dimensions) {
                    it("should set the $i component to the largest one between the two") {
                        assert(result[i]).isEqualTo(max(a[i], b[i]))
                    }
                }
            }
        }
    }
})

private fun rScalar() = random(-10f, 10f)
private fun rVector2() = Vector2 { rScalar() }
private fun rVector3() = Vector3 { rScalar() }
private fun rPoint2D() = Point2D { rScalar() }
private fun rPoint3D() = Point3D { rScalar() }
private fun rMatrix4() = Matrix4 { _, _ -> rScalar() }