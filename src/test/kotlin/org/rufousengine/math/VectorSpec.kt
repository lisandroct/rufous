package org.rufousengine.math

import org.rufousengine.assertions.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.math.abs

object VectorsSpec : Spek({
    describe("Vector2") {
        val a = rVector2()

        describe("negate") {
            val result = -a

            it("should negate each component") {
                for (i in 0 until 4) {
                    assert(result[i]).isEqualTo(-a[i])
                }
            }
        }

        describe("abs") {
            val result = abs(a)

            it("should apply the absolute value to each component") {
                for (i in 0 until 4) {
                    assert(result[i]).isEqualTo(abs(a[i]))
                }
            }
        }

        describe("normalize") {
            val result = normalize(a)

            it("should be a unit vector") {
                assert(result).isUnit()
            }

            it("should be scaled down by the vector's magnitude") {
                assert(result).isEqualTo(a / a.magnitude)
            }
        }

        describe("Scalar") {
            val v = rValue()

            describe("scale") {
                val result = a * v

                it("should multiply each component") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] * v)
                    }
                }
            }

            describe("addition") {
                val result = a + v

                it("should add the scalar to every component") {
                    for (i in 0 until 2) {
                        assert(result[i]).isEqualTo(a[i] + v)
                    }
                }
            }

            describe("subtraction") {
                val result = a - v

                it("should subtract the scalar to every component") {
                    for (i in 0 until 2) {
                        assert(result[i]).isEqualTo(a[i] - v)
                    }
                }
            }
        }

        describe("Vector2") {
            val b = rVector2()

            describe("dot product") {
                val result = dot(a, b)

                it("should multiply both vectors component-wise and add the results") {
                    var expected = 0f
                    for (i in 0 until 4) {
                        expected += a[i] * b[i]
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("addition") {
                val result = a + b

                it("should add both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] + b[i])
                    }
                }
            }

            describe("subtraction") {
                val result = a - b

                it("should subtract both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] - b[i])
                    }
                }
            }

            describe("projection") {
                val result = project(a, b)

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("rejection") {
                val result = reject(a, b)

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }

        describe("Vector3") {
            val b = rVector3()

            describe("dot product") {
                val result = dot(a, b)

                it("should multiply both vectors component-wise and add the results") {
                    var expected = 0f
                    for (i in 0 until 4) {
                        expected += a[i] * b[i]
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("addition") {
                val result = a + b

                it("should add both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] + b[i])
                    }
                }
            }

            describe("subtraction") {
                val result = a - b

                it("should subtract both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] - b[i])
                    }
                }
            }

            describe("projection") {
                val result = project(a, b)

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("rejection") {
                val result = reject(a, b)

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }

        describe("Vector4") {
            val b = rVector4()

            describe("dot product") {
                val result = dot(a, b)

                it("should multiply both vectors component-wise and add the results") {
                    var expected = 0f
                    for (i in 0 until 4) {
                        expected += a[i] * b[i]
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("addition") {
                val result = a + b

                it("should add both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] + b[i])
                    }
                }
            }

            describe("subtraction") {
                val result = a - b

                it("should subtract both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] - b[i])
                    }
                }
            }

            describe("projection") {
                val result = project(a, b)

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("rejection") {
                val result = reject(a, b)

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }
    }

    describe("Vector3") {
        val a = rVector3()

        describe("negate") {
            val result = -a

            it("should negate each component") {
                for (i in 0 until 4) {
                    assert(result[i]).isEqualTo(-a[i])
                }
            }
        }

        describe("abs") {
            val result = abs(a)

            it("should apply the absolute value to each component") {
                for (i in 0 until 4) {
                    assert(result[i]).isEqualTo(abs(a[i]))
                }
            }
        }

        describe("normalize") {
            val result = normalize(a)

            it("should be a unit vector") {
                assert(result).isUnit()
            }

            it("should be scaled down by the vector's magnitude") {
                assert(result).isEqualTo(a / a.magnitude)
            }
        }

        describe("Scalar") {
            val v = rValue()

            describe("scale") {
                val result = a * v

                it("should multiply each component") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] * v)
                    }
                }
            }

            describe("addition") {
                val result = a + v

                it("should add the scalar to every component") {
                    for (i in 0 until 3) {
                        assert(result[i]).isEqualTo(a[i] + v)
                    }
                }
            }

            describe("subtraction") {
                val result = a - v

                it("should subtract the scalar to every component") {
                    for (i in 0 until 3) {
                        assert(result[i]).isEqualTo(a[i] - v)
                    }
                }
            }
        }

        describe("Vector2") {
            val b = rVector2()

            describe("dot product") {
                val result = dot(a, b)

                it("should multiply both vectors component-wise and add the results") {
                    var expected = 0f
                    for (i in 0 until 4) {
                        expected += a[i] * b[i]
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("addition") {
                val result = a + b

                it("should add both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] + b[i])
                    }
                }
            }

            describe("subtraction") {
                val result = a - b

                it("should subtract both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] - b[i])
                    }
                }
            }

            describe("projection") {
                val result = project(a, b)

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("rejection") {
                val result = reject(a, b)

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }

        describe("Vector3") {
            val b = rVector3()

            describe("dot product") {
                val result = dot(a, b)

                it("should multiply both vectors component-wise and add the results") {
                    var expected = 0f
                    for (i in 0 until 4) {
                        expected += a[i] * b[i]
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("addition") {
                val result = a + b

                it("should add both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] + b[i])
                    }
                }
            }

            describe("subtraction") {
                val result = a - b

                it("should subtract both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] - b[i])
                    }
                }
            }

            describe("projection") {
                val result = project(a, b)

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("rejection") {
                val result = reject(a, b)

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }

            describe("cross product") {
                val c = cross(a, b)
                val d = cross(b, a)

                it("should be orthogonal to a and b") {
                    assert(c).isOrthogonalTo(a)
                    assert(c).isOrthogonalTo(b)
                    assert(d).isOrthogonalTo(a)
                    assert(d).isOrthogonalTo(b)
                }

                it("should change the direction if the order of the operands change") {
                    assert(c).isEqualTo(-d)
                }
            }
        }

        describe("Vector4") {
            val b = rVector4()

            describe("dot product") {
                val result = dot(a, b)

                it("should multiply both vectors component-wise and add the results") {
                    var expected = 0f
                    for (i in 0 until 4) {
                        expected += a[i] * b[i]
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("addition") {
                val result = a + b

                it("should add both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] + b[i])
                    }
                }
            }

            describe("subtraction") {
                val result = a - b

                it("should subtract both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] - b[i])
                    }
                }
            }

            describe("projection") {
                val result = project(a, b)

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("rejection") {
                val result = reject(a, b)

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }
    }

    describe("Vector4") {
        val a = rVector4()

        describe("negate") {
            val result = -a

            it("should negate each component") {
                for (i in 0 until 4) {
                    assert(result[i]).isEqualTo(-a[i])
                }
            }
        }

        describe("abs") {
            val result = abs(a)

            it("should apply the absolute value to each component") {
                for (i in 0 until 4) {
                    assert(result[i]).isEqualTo(abs(a[i]))
                }
            }
        }

        describe("normalize") {
            val result = normalize(a)

            it("should be a unit vector") {
                assert(result).isUnit()
            }

            it("should be scaled down by the vector's magnitude") {
                assert(result).isEqualTo(a / a.magnitude)
            }
        }

        describe("Scalar") {
            val v = rValue()

            describe("scale") {
                val result = a * v

                it("should multiply each component") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] * v)
                    }
                }
            }

            describe("addition") {
                val result = a + v

                it("should add the scalar to every component") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] + v)
                    }
                }
            }

            describe("subtraction") {
                val result = a - v

                it("should subtract the scalar to every component") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] - v)
                    }
                }
            }
        }

        describe("Vector2") {
            val b = rVector2()

            describe("dot product") {
                val result = dot(a, b)

                it("should multiply both vectors component-wise and add the results") {
                    var expected = 0f
                    for (i in 0 until 4) {
                        expected += a[i] * b[i]
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("addition") {
                val result = a + b

                it("should add both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] + b[i])
                    }
                }
            }

            describe("subtraction") {
                val result = a - b

                it("should subtract both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] - b[i])
                    }
                }
            }

            describe("projection") {
                val result = project(a, b)

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("rejection") {
                val result = reject(a, b)

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }

        describe("Vector3") {
            val b = rVector3()

            describe("dot product") {
                val result = dot(a, b)

                it("should multiply both vectors component-wise and add the results") {
                    var expected = 0f
                    for (i in 0 until 4) {
                        expected += a[i] * b[i]
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("addition") {
                val result = a + b

                it("should add both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] + b[i])
                    }
                }
            }

            describe("subtraction") {
                val result = a - b

                it("should subtract both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] - b[i])
                    }
                }
            }

            describe("projection") {
                val result = project(a, b)

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("rejection") {
                val result = reject(a, b)

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }

        describe("Vector4") {
            val b = rVector4()

            describe("dot product") {
                val result = dot(a, b)

                it("should multiply both vectors component-wise and add the results") {
                    var expected = 0f
                    for (i in 0 until 4) {
                        expected += a[i] * b[i]
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("addition") {
                val result = a + b

                it("should add both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] + b[i])
                    }
                }
            }

            describe("subtraction") {
                val result = a - b

                it("should subtract both vectors component-wise") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] - b[i])
                    }
                }
            }

            describe("projection") {
                val result = project(a, b)

                it("should be parallel to b") {
                    assert(result).isParallelTo(b)
                }
            }

            describe("rejection") {
                val result = reject(a, b)

                it("should be orthogonal to b") {
                    assert(result).isOrthogonalTo(b)
                }
            }
        }
    }
})

private fun rValue() = random(-100f, 100f)
private fun rVector2() = Vector2(random(-100f, 100f), random(-100f, 100f))
private fun rVector3() = Vector3(random(-100f, 100f), random(-100f, 100f), random(-100f, 100f))
private fun rVector4() = Vector4(random(-100f, 100f), random(-100f, 100f), random(-100f, 100f), random(-100f, 100f))