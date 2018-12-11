package org.rufousengine.math

import org.rufousengine.assertions.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

object VectorsSpec : Spek({
    setRandomSeed(1978851555463230753L)

    describe("Vector2: (0.0, 0.0)") {
        val vector by memoized { Vector2() }

        describe("Normalize") {
            val result by memoized { normalize(vector) }

            it("shouldn't change") {
                assert(result).isEqualTo(vector)
            }
        }
    }

    describe("Vector3: (0.0, 0.0, 0.0)") {
        val vector by memoized { Vector3() }

        describe("Normalize") {
            val result by memoized { normalize(vector) }

            it("shouldn't change") {
                assert(result).isEqualTo(vector)
            }
        }
    }

    describe("Vector4: (0.0, 0.0, 0.0, 0.0)") {
        val vector by memoized { Vector4() }

        describe("Normalize") {
            val result by memoized { normalize(vector) }

            it("shouldn't change") {
                assert(result).isEqualTo(vector)
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    repeat(100) {
        val v = rVector2()
        describe("Vector2: $v") {
            val vector by memoized { v.copy() }

            for (i in -1..4) {
                describe("Get $i index") {
                    when {
                        i < 0 || i >= 4 -> it("throws an IllegalArgumentException") {
                            assert(IllegalArgumentException::class).isThrownBy{ vector[i] }
                        }
                        i >= vector.dimensions -> it("returns 0") {
                            assert(vector[i]).isZero()
                        }
                        else -> it("returns the component value") {
                            assert(vector[i]).isEqualTo(vector.components[i])
                        }
                    }
                }
            }

            for (i in -1..4) {
                describe("Set $i index") {
                    val value = rScalar()
                    when {
                        i < 0 || i >= vector.dimensions -> it("throws an IllegalArgumentException") {
                            assert(IllegalArgumentException::class).isThrownBy{ vector[i] = value}
                        }
                        else -> it("changes the component value") {
                            vector[i] = value
                            assert(vector[i]).isEqualTo(value)
                        }
                    }
                }
            }

            describe("Magnitude") {
                it("should be the square root of the sum of the components' squares") {
                    var expected = 0f
                    for (component in vector.components) {
                        expected += component * component
                    }
                    expected = sqrt(expected)

                    assert(vector.magnitude).isEqualTo(expected)
                }
            }

            describe("Squared Magnitude") {
                it("should be the square of the magnitude") {
                    val expected = vector.magnitude * vector.magnitude

                    assert(vector.magnitudeSquared).isEqualTo(expected)
                }
            }

            describe("Negate") {
                val result by memoized { negate(vector) }

                for (i in 0 until vector.dimensions) {
                    it("should negate the $i component") {
                        assert(result[i]).isEqualTo(-vector[i])
                    }
                }
            }

            describe("Normalize") {
                val result by memoized { normalize(vector) }

                it("should be parallel to the original vector") {
                    assert(result).isParallelTo(vector)
                }

                it("should be a unit vector") {
                    assert(result).isUnit()
                }
            }

            describe("Abs") {
                val result by memoized { abs(vector) }

                for (i in 0 until vector.dimensions) {
                    it("should compute the absolute value of the $i component") {
                        assert(result[i]).isEqualTo(abs(vector[i]))
                    }
                }
            }

            describe("Scale") {
                val scalar = rScalar()
                val result by memoized { scale(vector, scalar) }

                for (i in 0 until vector.dimensions) {
                    it("should scale the $i component") {
                        assert(result[i]).isEqualTo(vector[i] * scalar)
                    }
                }
            }

            describe("Operators") {
                describe("UnaryPlus") {
                    val result by memoized { +vector }

                    it("should be equal to vector") {
                        assert(result).isEqualTo(vector)
                    }
                }

                describe("UnaryMinus") {
                    val result by memoized { -vector }

                    it("should be equal to vector negated") {
                        assert(result).isEqualTo(negate(vector))
                    }
                }

                describe("Plus") {
                    val other = rVector2()
                    val result by memoized { vector + other }

                    it("should be equal to the added vectors") {
                        assert(result).isEqualTo(add(vector, other))
                    }
                }

                describe("Plus") {
                    val other = rVector3()
                    val result by memoized { vector + other }

                    it("should be equal to the added vectors") {
                        assert(result).isEqualTo(add(vector, other))
                    }
                }

                describe("Plus") {
                    val other = rVector4()
                    val result by memoized { vector + other }

                    it("should be equal to the added vectors") {
                        assert(result).isEqualTo(add(vector, other))
                    }
                }

                describe("Minus") {
                    val other = rVector2()
                    val result by memoized { vector - other }

                    it("should be equal to the subtracted vectors") {
                        assert(result).isEqualTo(subtract(vector, other))
                    }
                }

                describe("Minus") {
                    val other = rVector3()
                    val result by memoized { vector - other }

                    it("should be equal to the subtracted vectors") {
                        assert(result).isEqualTo(subtract(vector, other))
                    }
                }

                describe("Minus") {
                    val other = rVector4()
                    val result by memoized { vector - other }

                    it("should be equal to the subtracted vectors") {
                        assert(result).isEqualTo(subtract(vector, other))
                    }
                }

                describe("Times") {
                    val scalar = rScalar()
                    val result by memoized { vector * scalar }

                    it("should be equal to the scaled vector") {
                        assert(result).isEqualTo(scale(vector, scalar))
                    }
                }

                describe("Div") {
                    val scalar = rScalar()
                    val result by memoized { vector / scalar }

                    it("should be equal to the scaled vector") {
                        assert(result).isEqualTo(scale(vector, 1 / scalar))
                    }
                }

                describe("PlusAssign") {
                    val other = rVector2()

                    it("should update vector to be equal to the added vectors") {
                        val expected = vector + other

                        vector += other
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("MinusAssign") {
                    val other = rVector2()

                    it("should update vector to be equal to the subtracted vectors") {
                        val expected = vector - other

                        vector -= other
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("TimesAssign") {
                    val scalar = rScalar()

                    it("should update vector to be equal to the scaled vector") {
                        val expected = vector * scalar

                        vector *= scalar
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("DivAssign") {
                    val scalar = rScalar()

                    it("should update vector to be equal to the scaled vector") {
                        val expected = vector / scalar

                        vector /= scalar
                        assert(vector).isEqualTo(expected)
                    }
                }
            }

            describe("Extension functions") {
                describe("Negate") {
                    it("should update vector to be equal to the negated vector") {
                        val expected = negate(vector)

                        vector.negate()
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Normalize") {
                    it("should update vector to be equal to the normalized vector") {
                        val expected = normalize(vector)

                        vector.normalize()
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Abs") {
                    it("should update vector to be equal to the abs vector") {
                        val expected = abs(vector)

                        vector.abs()
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Scale") {
                    val scalar = rScalar()

                    it("should update vector to be equal to the scaled vector") {
                        val expected = scale(vector, scalar)

                        vector.scale(scalar)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Add") {
                    val other = rVector2()

                    it("should update vector to be equal to the added vectors") {
                        val expected = add(vector, other)

                        vector.add(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Subtract") {
                    val other = rVector2()

                    it("should update vector to be equal to the subtracted vectors") {
                        val expected = subtract(vector, other)

                        vector.subtract(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Min") {
                    val other = rVector2()

                    it("should update vector to be equal to the min between the two vectors") {
                        val expected = min(vector, other)

                        vector.min(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Max") {
                    val other = rVector2()

                    it("should update vector to be equal to the max between the two vectors") {
                        val expected = max(vector, other)

                        vector.max(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("ProjectOnto") {
                    val other = rVector2()

                    it("should update vector to be equal to the projected vector") {
                        val expected = project(vector, other)

                        vector.projectOnto(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("RejectFrom") {
                    val other = rVector2()

                    it("should update vector to be equal to the rejected vector") {
                        val expected = reject(vector, other)

                        vector.rejectFrom(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("MultiplyLeft") {
                    val matrix = rMatrix2()

                    it("should update vector to be the result of the matrix multiplication") {
                        val expected = multiply(matrix, vector)

                        vector.multiplyLeft(matrix)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("MultiplyLeft") {
                    val matrix = rMatrix4()

                    it("should update vector to be the result of the matrix multiplication") {
                        val expected = multiply(matrix, vector, Vector2())

                        vector.multiplyLeft(matrix)
                        assert(vector).isEqualTo(expected)
                    }
                }
            }
        }
    }

    repeat(100) {
        val v0 = rVector2()
        val v1 = rVector2()
        describe("Vector2: $v0 - Vector2: $v1") {
            val a by memoized { v0.copy() }
            val b by memoized { v1.copy() }
            val dimensions by memoized { max(v0.dimensions, v1.dimensions) }

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

            describe("Project") {
                val result by memoized { project(a, b) }

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("Reject") {
                val result by memoized { reject(a, b) }

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }
    }

    repeat(100) {
        val v0 = rVector2()
        val v1 = rVector3()
        describe("Vector2: $v0 - Vector3: $v1") {
            val a by memoized { v0.copy() }
            val b by memoized { v1.copy() }
            val dimensions by memoized { max(v0.dimensions, v1.dimensions) }

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

            describe("Project") {
                val result by memoized { project(a, b) }

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("Reject") {
                val result by memoized { reject(a, b) }

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }
    }

    repeat(100) {
        val v0 = rVector2()
        val v1 = rVector4()
        describe("Vector2: $v0 - Vector4: $v1") {
            val a by memoized { v0.copy() }
            val b by memoized { v1.copy() }
            val dimensions by memoized { max(v0.dimensions, v1.dimensions) }

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

            describe("Project") {
                val result by memoized { project(a, b) }

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("Reject") {
                val result by memoized { reject(a, b) }

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    repeat(100) {
        val v = rVector3()
        describe("Vector3: $v") {
            val vector by memoized { v.copy() }

            for (i in -1..4) {
                describe("Get $i index") {
                    when {
                        i < 0 || i >= 4 -> it("throws an IllegalArgumentException") {
                            assert(IllegalArgumentException::class).isThrownBy{ vector[i] }
                        }
                        i >= vector.dimensions -> it("returns 0") {
                            assert(vector[i]).isZero()
                        }
                        else -> it("returns the component value") {
                            assert(vector[i]).isEqualTo(vector.components[i])
                        }
                    }
                }
            }

            for (i in -1..4) {
                describe("Set $i index") {
                    val value = rScalar()
                    when {
                        i < 0 || i >= vector.dimensions -> it("throws an IllegalArgumentException") {
                            assert(IllegalArgumentException::class).isThrownBy{ vector[i] = value }
                        }
                        else -> it("changes the component value") {
                            vector[i] = value
                            assert(vector[i]).isEqualTo(value)
                        }
                    }
                }
            }

            describe("Magnitude") {
                it("should be the square root of the sum of the components' squares") {
                    var expected = 0f
                    for (component in vector.components) {
                        expected += component * component
                    }
                    expected = sqrt(expected)

                    assert(vector.magnitude).isEqualTo(expected)
                }
            }

            describe("Squared Magnitude") {
                it("should be the square of the magnitude") {
                    val expected = vector.magnitude * vector.magnitude

                    assert(vector.magnitudeSquared).isEqualTo(expected)
                }
            }

            describe("Negate") {
                val result by memoized { negate(vector) }

                for (i in 0 until vector.dimensions) {
                    it("should negate the $i component") {
                        assert(result[i]).isEqualTo(-vector[i])
                    }
                }
            }

            describe("Normalize") {
                val result by memoized { normalize(vector) }

                it("should be parallel to the original vector") {
                    assert(result).isParallelTo(vector)
                }

                it("should be a unit vector") {
                    assert(result).isUnit()
                }
            }

            describe("Abs") {
                val result by memoized { abs(vector) }

                for (i in 0 until vector.dimensions) {
                    it("should compute the absolute value of the $i component") {
                        assert(result[i]).isEqualTo(abs(vector[i]))
                    }
                }
            }

            describe("Scale") {
                val scalar = rScalar()
                val result by memoized { scale(vector, scalar) }

                for (i in 0 until vector.dimensions) {
                    it("should scale the $i component") {
                        assert(result[i]).isEqualTo(vector[i] * scalar)
                    }
                }
            }

            describe("Operators") {
                describe("UnaryPlus") {
                    val result by memoized { +vector }

                    it("should be equal to vector") {
                        assert(result).isEqualTo(vector)
                    }
                }

                describe("UnaryMinus") {
                    val result by memoized { -vector }

                    it("should be equal to vector negated") {
                        assert(result).isEqualTo(negate(vector))
                    }
                }

                describe("Plus") {
                    val other = rVector2()
                    val result by memoized { vector + other }

                    it("should be equal to the added vectors") {
                        assert(result).isEqualTo(add(vector, other))
                    }
                }

                describe("Plus") {
                    val other = rVector3()
                    val result by memoized { vector + other }

                    it("should be equal to the added vectors") {
                        assert(result).isEqualTo(add(vector, other))
                    }
                }

                describe("Plus") {
                    val other = rVector4()
                    val result by memoized { vector + other }

                    it("should be equal to the added vectors") {
                        assert(result).isEqualTo(add(vector, other))
                    }
                }

                describe("Minus") {
                    val other = rVector2()
                    val result by memoized { vector - other }

                    it("should be equal to the subtracted vectors") {
                        assert(result).isEqualTo(subtract(vector, other))
                    }
                }

                describe("Minus") {
                    val other = rVector3()
                    val result by memoized { vector - other }

                    it("should be equal to the subtracted vectors") {
                        assert(result).isEqualTo(subtract(vector, other))
                    }
                }

                describe("Minus") {
                    val other = rVector4()
                    val result by memoized { vector - other }

                    it("should be equal to the subtracted vectors") {
                        assert(result).isEqualTo(subtract(vector, other))
                    }
                }

                describe("Times") {
                    val scalar = rScalar()
                    val result by memoized { vector * scalar }

                    it("should be equal to the scaled vector") {
                        assert(result).isEqualTo(scale(vector, scalar))
                    }
                }

                describe("Div") {
                    val scalar = rScalar()
                    val result by memoized { vector / scalar }

                    it("should be equal to the scaled vector") {
                        assert(result).isEqualTo(scale(vector, 1 / scalar))
                    }
                }

                describe("PlusAssign") {
                    val other = rVector2()

                    it("should update vector to be equal to the added vectors") {
                        val expected = vector + other

                        vector += other
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("PlusAssign") {
                    val other = rVector3()

                    it("should update vector to be equal to the added vectors") {
                        val expected = vector + other

                        vector += other
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("MinusAssign") {
                    val other = rVector2()

                    it("should update vector to be equal to the subtracted vectors") {
                        val expected = vector - other

                        vector -= other
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("MinusAssign") {
                    val other = rVector3()

                    it("should update vector to be equal to the subtracted vectors") {
                        val expected = vector - other

                        vector -= other
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("TimesAssign") {
                    val scalar = rScalar()

                    it("should update vector to be equal to the scaled vector") {
                        val expected = vector * scalar

                        vector *= scalar
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("DivAssign") {
                    val scalar = rScalar()

                    it("should update vector to be equal to the scaled vector") {
                        val expected = vector / scalar

                        vector /= scalar
                        assert(vector).isEqualTo(expected)
                    }
                }
            }

            describe("Extension functions") {
                describe("Negate") {
                    it("should update vector to be equal to the negated vector") {
                        val expected = negate(vector)

                        vector.negate()
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Normalize") {
                    it("should update vector to be equal to the normalized vector") {
                        val expected = normalize(vector)

                        vector.normalize()
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Abs") {
                    it("should update vector to be equal to the abs vector") {
                        val expected = abs(vector)

                        vector.abs()
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Scale") {
                    val scalar = rScalar()

                    it("should update vector to be equal to the scaled vector") {
                        val expected = scale(vector, scalar)

                        vector.scale(scalar)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Add") {
                    val other = rVector2()

                    it("should update vector to be equal to the added vectors") {
                        val expected = add(vector, other)

                        vector.add(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Add") {
                    val other = rVector3()

                    it("should update vector to be equal to the added vectors") {
                        val expected = add(vector, other)

                        vector.add(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Subtract") {
                    val other = rVector2()

                    it("should update vector to be equal to the subtracted vectors") {
                        val expected = subtract(vector, other)

                        vector.subtract(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Subtract") {
                    val other = rVector3()

                    it("should update vector to be equal to the subtracted vectors") {
                        val expected = subtract(vector, other)

                        vector.subtract(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Min") {
                    val other = rVector2()

                    it("should update vector to be equal to the min between the two vectors") {
                        val expected = min(vector, other)

                        vector.min(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Min") {
                    val other = rVector3()

                    it("should update vector to be equal to the min between the two vectors") {
                        val expected = min(vector, other)

                        vector.min(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Max") {
                    val other = rVector2()

                    it("should update vector to be equal to the max between the two vectors") {
                        val expected = max(vector, other)

                        vector.max(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Max") {
                    val other = rVector3()

                    it("should update vector to be equal to the max between the two vectors") {
                        val expected = max(vector, other)

                        vector.max(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("ProjectOnto") {
                    val other = rVector2()

                    it("should update vector to be equal to the projected vector") {
                        val expected = project(vector, other)

                        vector.projectOnto(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("ProjectOnto") {
                    val other = rVector3()

                    it("should update vector to be equal to the projected vector") {
                        val expected = project(vector, other)

                        vector.projectOnto(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("RejectFrom") {
                    val other = rVector2()

                    it("should update vector to be equal to the rejected vector") {
                        val expected = reject(vector, other)

                        vector.rejectFrom(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("RejectFrom") {
                    val other = rVector3()

                    it("should update vector to be equal to the rejected vector") {
                        val expected = reject(vector, other)

                        vector.rejectFrom(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Cross") {
                    val other = rVector3()

                    it("should update vector to be equal to the cross product") {
                        val expected = cross(vector, other)

                        vector.cross(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("MultiplyLeft") {
                    val matrix = rMatrix3()

                    it("should update vector to be the result of the matrix multiplication") {
                        val expected = multiply(matrix, vector)

                        vector.multiplyLeft(matrix)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("MultiplyLeft") {
                    val matrix = rMatrix4()

                    it("should update vector to be the result of the matrix multiplication") {
                        val expected = multiply(matrix, vector, Vector3())

                        vector.multiplyLeft(matrix)
                        assert(vector).isEqualTo(expected)
                    }
                }
            }
        }
    }

    repeat(100) {
        val v0 = rVector3()
        val v1 = rVector2()
        describe("Vector3: $v0 - Vector2: $v1") {
            val a by memoized { v0.copy() }
            val b by memoized { v1.copy() }
            val dimensions by memoized { max(v0.dimensions, v1.dimensions) }

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

            describe("Project") {
                val result by memoized { project(a, b, Vector2()) }

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("Reject") {
                val result by memoized { reject(a, b) }

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }
    }

    repeat(100) {
        val v0 = rVector3()
        val v1 = rVector3()
        describe("Vector3: $v0 - Vector3: $v1") {
            val a by memoized { v0.copy() }
            val b by memoized { v1.copy() }
            val dimensions by memoized { max(v0.dimensions, v1.dimensions) }

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

            describe("Project") {
                val result by memoized { project(a, b) }

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("Reject") {
                val result by memoized { reject(a, b) }

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }

            describe("Cross") {
                val result by memoized { cross(a, b) }

                it("should be orthogonal to a") {
                    assert(result).isOrthogonalTo(a)
                }

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }
    }

    repeat(100) {
        val v0 = rVector3()
        val v1 = rVector4()
        describe("Vector3: $v0 - Vector3: $v1") {
            val a by memoized { v0.copy() }
            val b by memoized { v1.copy() }
            val dimensions by memoized { max(v0.dimensions, v1.dimensions) }

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

            describe("Project") {
                val result by memoized { project(a, b) }

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("Reject") {
                val result by memoized { reject(a, b) }

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    repeat(100) {
        val v = rVector4()
        describe("Vector4: $v") {
            val vector by memoized { v.copy() }

            for (i in -1..4) {
                describe("Get $i index") {
                    when {
                        i < 0 || i >= 4 -> it("throws an IllegalArgumentException") {
                            assert(IllegalArgumentException::class).isThrownBy{ vector[i] }
                        }
                        i >= vector.dimensions -> it("returns 0") {
                            assert(vector[i]).isZero()
                        }
                        else -> it("returns the component value") {
                            assert(vector[i]).isEqualTo(vector.components[i])
                        }
                    }
                }
            }

            for (i in -1..4) {
                describe("Set $i index") {
                    val value = rScalar()
                    when {
                        i < 0 || i >= vector.dimensions -> it("throws an IllegalArgumentException") {
                            assert(IllegalArgumentException::class).isThrownBy{ vector[i] = value }
                        }
                        else -> it("changes the component value") {
                            vector[i] = value
                            assert(vector[i]).isEqualTo(value)
                        }
                    }
                }
            }

            describe("Magnitude") {
                it("should be the square root of the sum of the components' squares") {
                    var expected = 0f
                    for (component in vector.components) {
                        expected += component * component
                    }
                    expected = sqrt(expected)

                    assert(vector.magnitude).isEqualTo(expected)
                }
            }

            describe("Squared Magnitude") {
                it("should be the square of the magnitude") {
                    val expected = vector.magnitude * vector.magnitude

                    assert(vector.magnitudeSquared).isEqualTo(expected)
                }
            }

            describe("Negate") {
                val result by memoized { negate(vector) }

                for (i in 0 until vector.dimensions) {
                    it("should negate the $i component") {
                        assert(result[i]).isEqualTo(-vector[i])
                    }
                }
            }

            describe("Normalize") {
                val result by memoized { normalize(vector) }

                it("should be parallel to the original vector") {
                    assert(result).isParallelTo(vector)
                }

                it("should be a unit vector") {
                    assert(result).isUnit()
                }
            }

            describe("Abs") {
                val result by memoized { abs(vector) }

                for (i in 0 until vector.dimensions) {
                    it("should compute the absolute value of the $i component") {
                        assert(result[i]).isEqualTo(abs(vector[i]))
                    }
                }
            }

            describe("Scale") {
                val scalar = rScalar()
                val result by memoized { scale(vector, scalar) }

                for (i in 0 until vector.dimensions) {
                    it("should scale the $i component") {
                        assert(result[i]).isEqualTo(vector[i] * scalar)
                    }
                }
            }

            describe("Operators") {
                describe("UnaryPlus") {
                    val result by memoized { +vector }

                    it("should be equal to vector") {
                        assert(result).isEqualTo(vector)
                    }
                }

                describe("UnaryMinus") {
                    val result by memoized { -vector }

                    it("should be equal to vector negated") {
                        assert(result).isEqualTo(negate(vector))
                    }
                }

                describe("Plus") {
                    val other = rVector2()
                    val result by memoized { vector + other }

                    it("should be equal to the added vectors") {
                        assert(result).isEqualTo(add(vector, other))
                    }
                }

                describe("Plus") {
                    val other = rVector3()
                    val result by memoized { vector + other }

                    it("should be equal to the added vectors") {
                        assert(result).isEqualTo(add(vector, other))
                    }
                }

                describe("Plus") {
                    val other = rVector4()
                    val result by memoized { vector + other }

                    it("should be equal to the added vectors") {
                        assert(result).isEqualTo(add(vector, other))
                    }
                }

                describe("Minus") {
                    val other = rVector2()
                    val result by memoized { vector - other }

                    it("should be equal to the subtracted vectors") {
                        assert(result).isEqualTo(subtract(vector, other))
                    }
                }

                describe("Minus") {
                    val other = rVector3()
                    val result by memoized { vector - other }

                    it("should be equal to the subtracted vectors") {
                        assert(result).isEqualTo(subtract(vector, other))
                    }
                }

                describe("Minus") {
                    val other = rVector4()
                    val result by memoized { vector - other }

                    it("should be equal to the subtracted vectors") {
                        assert(result).isEqualTo(subtract(vector, other))
                    }
                }

                describe("Times") {
                    val scalar = rScalar()
                    val result by memoized { vector * scalar }

                    it("should be equal to the scaled vector") {
                        assert(result).isEqualTo(scale(vector, scalar))
                    }
                }

                describe("Div") {
                    val scalar = rScalar()
                    val result by memoized { vector / scalar }

                    it("should be equal to the scaled vector") {
                        assert(result).isEqualTo(scale(vector, 1 / scalar))
                    }
                }

                describe("PlusAssign") {
                    val other = rVector2()

                    it("should update vector to be equal to the added vectors") {
                        val expected = vector + other

                        vector += other
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("PlusAssign") {
                    val other = rVector3()

                    it("should update vector to be equal to the added vectors") {
                        val expected = vector + other

                        vector += other
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("PlusAssign") {
                    val other = rVector4()

                    it("should update vector to be equal to the added vectors") {
                        val expected = vector + other

                        vector += other
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("MinusAssign") {
                    val other = rVector2()

                    it("should update vector to be equal to the subtracted vectors") {
                        val expected = vector - other

                        vector -= other
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("MinusAssign") {
                    val other = rVector3()

                    it("should update vector to be equal to the subtracted vectors") {
                        val expected = vector - other

                        vector -= other
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("MinusAssign") {
                    val other = rVector4()

                    it("should update vector to be equal to the subtracted vectors") {
                        val expected = vector - other

                        vector -= other
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("TimesAssign") {
                    val scalar = rScalar()

                    it("should update vector to be equal to the scaled vector") {
                        val expected = vector * scalar

                        vector *= scalar
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("DivAssign") {
                    val scalar = rScalar()

                    it("should update vector to be equal to the scaled vector") {
                        val expected = vector / scalar

                        vector /= scalar
                        assert(vector).isEqualTo(expected)
                    }
                }
            }

            describe("Extension functions") {
                describe("Negate") {
                    it("should update vector to be equal to the negated vector") {
                        val expected = negate(vector)

                        vector.negate()
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Normalize") {
                    it("should update vector to be equal to the normalized vector") {
                        val expected = normalize(vector)

                        vector.normalize()
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Abs") {
                    it("should update vector to be equal to the abs vector") {
                        val expected = abs(vector)

                        vector.abs()
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Scale") {
                    val scalar = rScalar()

                    it("should update vector to be equal to the scaled vector") {
                        val expected = scale(vector, scalar)

                        vector.scale(scalar)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Add") {
                    val other = rVector2()

                    it("should update vector to be equal to the added vectors") {
                        val expected = add(vector, other)

                        vector.add(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Add") {
                    val other = rVector3()

                    it("should update vector to be equal to the added vectors") {
                        val expected = add(vector, other)

                        vector.add(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Add") {
                    val other = rVector4()

                    it("should update vector to be equal to the added vectors") {
                        val expected = add(vector, other)

                        vector.add(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Subtract") {
                    val other = rVector2()

                    it("should update vector to be equal to the subtracted vectors") {
                        val expected = subtract(vector, other)

                        vector.subtract(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Subtract") {
                    val other = rVector3()

                    it("should update vector to be equal to the subtracted vectors") {
                        val expected = subtract(vector, other)

                        vector.subtract(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Subtract") {
                    val other = rVector4()

                    it("should update vector to be equal to the subtracted vectors") {
                        val expected = subtract(vector, other)

                        vector.subtract(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Min") {
                    val other = rVector2()

                    it("should update vector to be equal to the min between the two vectors") {
                        val expected = min(vector, other)

                        vector.min(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Min") {
                    val other = rVector3()

                    it("should update vector to be equal to the min between the two vectors") {
                        val expected = min(vector, other)

                        vector.min(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Min") {
                    val other = rVector4()

                    it("should update vector to be equal to the min between the two vectors") {
                        val expected = min(vector, other)

                        vector.min(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Max") {
                    val other = rVector2()

                    it("should update vector to be equal to the max between the two vectors") {
                        val expected = max(vector, other)

                        vector.max(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Max") {
                    val other = rVector3()

                    it("should update vector to be equal to the max between the two vectors") {
                        val expected = max(vector, other)

                        vector.max(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("Max") {
                    val other = rVector4()

                    it("should update vector to be equal to the max between the two vectors") {
                        val expected = max(vector, other)

                        vector.max(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("ProjectOnto") {
                    val other = rVector2()

                    it("should update vector to be equal to the projected vector") {
                        val expected = project(vector, other)

                        vector.projectOnto(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("ProjectOnto") {
                    val other = rVector3()

                    it("should update vector to be equal to the projected vector") {
                        val expected = project(vector, other)

                        vector.projectOnto(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("ProjectOnto") {
                    val other = rVector4()

                    it("should update vector to be equal to the projected vector") {
                        val expected = project(vector, other)

                        vector.projectOnto(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("RejectFrom") {
                    val other = rVector2()

                    it("should update vector to be equal to the rejected vector") {
                        val expected = reject(vector, other)

                        vector.rejectFrom(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("RejectFrom") {
                    val other = rVector3()

                    it("should update vector to be equal to the rejected vector") {
                        val expected = reject(vector, other)

                        vector.rejectFrom(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("RejectFrom") {
                    val other = rVector4()

                    it("should update vector to be equal to the rejected vector") {
                        val expected = reject(vector, other)

                        vector.rejectFrom(other)
                        assert(vector).isEqualTo(expected)
                    }
                }

                describe("MultiplyLeft") {
                    val matrix = rMatrix4()

                    it("should update vector to be the result of the matrix multiplication") {
                        val expected = multiply(matrix, vector)

                        vector.multiplyLeft(matrix)
                        assert(vector).isEqualTo(expected)
                    }
                }
            }
        }
    }

    repeat(100) {
        val v0 = rVector4()
        val v1 = rVector2()
        describe("Vector4: $v0 - Vector2: $v1") {
            val a by memoized { v0.copy() }
            val b by memoized { v1.copy() }
            val dimensions by memoized { max(v0.dimensions, v1.dimensions) }

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

            describe("Project") {
                val result by memoized { project(a, b, Vector2()) }

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("Reject") {
                val result by memoized { reject(a, b) }

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }
    }

    repeat(100) {
        val v0 = rVector4()
        val v1 = rVector3()
        describe("Vector4: $v0 - Vector3: $v1") {
            val a by memoized { v0.copy() }
            val b by memoized { v1.copy() }
            val dimensions by memoized { max(v0.dimensions, v1.dimensions) }

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

            describe("Project") {
                val result by memoized { project(a, b, Vector3()) }

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("Reject") {
                val result by memoized { reject(a, b) }

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }
    }

    repeat(100) {
        val v0 = rVector4()
        val v1 = rVector4()
        describe("Vector4: $v0 - Vector4: $v1") {
            val a by memoized { v0.copy() }
            val b by memoized { v1.copy() }
            val dimensions by memoized { max(v0.dimensions, v1.dimensions) }

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

            describe("Project") {
                val result by memoized { project(a, b) }

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("Reject") {
                val result by memoized { reject(a, b) }

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }
    }
})

private fun rScalar() = random(-10f, 10f)
private fun rVector2() = Vector2 { rScalar() }
private fun rVector3() = Vector3 { rScalar() }
private fun rVector4() = Vector4 { rScalar() }
private fun rMatrix2() = Matrix2 { _, _ -> rScalar() }
private fun rMatrix3() = Matrix3 { _, _ -> rScalar() }
private fun rMatrix4() = Matrix4 { _, _ -> rScalar() }