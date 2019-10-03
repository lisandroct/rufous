package org.rufousengine.math

import org.rufousengine.assertions.assert
import org.rufousengine.assertions.isEqualTo
import org.rufousengine.assertions.isIdentity
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object MatrixSpec : Spek({
    describe("Matrix2") {
        val a = rMatrix2()

        describe("determinant") {
            val result = determinant(a)

            it("should suffice the definition") {
                var expected = 0f
                for(i in 0 until 2) {
                    expected += a[1, i] * pow(-1, 1 + i) * determinant(submatrix(a, 1, i))
                }

                assert(result).isEqualTo(expected)
            }
        }

        describe("negate") {
            val result = -a

            it("should negate every component") {
                for(i in 0 until 2) {
                    for(j in 0 until 2) {
                        assert(result[i, j]).isEqualTo(-a[i, j])
                    }
                }
            }
        }

        describe("transpose") {
            val result = transpose(a)

            it("should reflect through the diagonal") {
                for(i in 0 until 2) {
                    for(j in 0 until 2) {
                        assert(result[i, j]).isEqualTo(a[j, i])
                    }
                }
            }
        }

        describe("inverse") {
            val result = inverse(a)

            it("should be inverse") {
                assert(a * result).isIdentity()
                assert(result * a).isIdentity()
            }
        }

        describe("Scalar") {
            val scalar = rValue()

            describe("scale") {
                val result = a * scalar

                it("should multiply each component") {
                    for(i in 0 until 2) {
                        for(j in 0 until 2) {
                            assert(result[i, j]).isEqualTo(a[i, j] * scalar)
                        }
                    }
                }
            }
        }

        describe("Matrix2") {
            val b = rMatrix2()

            describe("addition") {
                val result = a + b

                it("should add both matrices component-wise") {
                    for(i in 0 until 2) {
                        for(j in 0 until 2) {
                            assert(result[i, j]).isEqualTo(a[i, j] + b[i, j])
                        }
                    }
                }
            }

            describe("subtraction") {
                val result = a - b

                it("should subtract both matrices component-wise") {
                    for(i in 0 until 2) {
                        for(j in 0 until 2) {
                            assert(result[i, j]).isEqualTo(a[i, j] - b[i, j])
                        }
                    }
                }
            }

            describe("multiplication") {
                val result = a * b

                it("should suffice the definition") {
                    for(i in 0 until 2) {
                        for(j in 0 until 2) {
                            var expected = 0f
                            for(k in 0 until 2) {
                                expected += a[i, k] * b[k, j]
                            }

                            assert(result[i, j]).isEqualTo(expected)
                        }
                    }
                }
            }
        }

        describe("Vector2") {
            val b = rVector2()

            describe("multiplication") {
                val result = a * b

                it("should suffice the definition") {
                    for(i in 0 until 2) {
                        var expected = 0f
                        for(k in 0 until 2) {
                            expected += a[i, k] * b[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }
    }

    describe("Matrix3") {
        val a = rMatrix3()

        describe("determinant") {
            val result = determinant(a)

            it("should suffice the definition") {
                var expected = 0f
                for(i in 0 until 3) {
                    expected += a[2, i] * pow(-1, 2 + i) * determinant(submatrix(a, 2, i))
                }

                assert(result).isEqualTo(expected)
            }
        }

        describe("negate") {
            val result = -a

            it("should negate every component") {
                for(i in 0 until 3) {
                    for(j in 0 until 3) {
                        assert(result[i, j]).isEqualTo(-a[i, j])
                    }
                }
            }
        }

        describe("transpose") {
            val result = transpose(a)

            it("should reflect through the diagonal") {
                for(i in 0 until 3) {
                    for(j in 0 until 3) {
                        assert(result[i, j]).isEqualTo(a[j, i])
                    }
                }
            }
        }

        describe("inverse") {
            val result = inverse(a)

            it("should be inverse") {
                assert(a * result).isIdentity()
                assert(result * a).isIdentity()
            }
        }

        describe("Scalar") {
            val scalar = rValue()

            describe("scale") {
                val result = a * scalar

                it("should multiply each component") {
                    for(i in 0 until 3) {
                        for(j in 0 until 3) {
                            assert(result[i, j]).isEqualTo(a[i, j] * scalar)
                        }
                    }
                }
            }
        }

        describe("Matrix3") {
            val b = rMatrix3()

            describe("addition") {
                val result = a + b

                it("should add both matrices component-wise") {
                    for(i in 0 until 3) {
                        for(j in 0 until 3) {
                            assert(result[i, j]).isEqualTo(a[i, j] + b[i, j])
                        }
                    }
                }
            }

            describe("subtraction") {
                val result = a - b

                it("should subtract both matrices component-wise") {
                    for(i in 0 until 3) {
                        for(j in 0 until 3) {
                            assert(result[i, j]).isEqualTo(a[i, j] - b[i, j])
                        }
                    }
                }
            }

            describe("multiplication") {
                val result = a * b

                it("should suffice the definition") {
                    for(i in 0 until 3) {
                        for(j in 0 until 3) {
                            var expected = 0f
                            for(k in 0 until 3) {
                                expected += a[i, k] * b[k, j]
                            }

                            assert(result[i, j]).isEqualTo(expected)
                        }
                    }
                }
            }
        }

        describe("Vector2") {
            val b = rVector2()

            describe("multiplication") {
                val result = a * b

                it("should suffice the definition") {
                    for(i in 0 until 3) {
                        var expected = 0f
                        for(k in 0 until 3) {
                            expected += a[i, k] * b[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }

        describe("Vector3") {
            val b = rVector3()

            describe("multiplication") {
                val result = a * b

                it("should suffice the definition") {
                    for(i in 0 until 3) {
                        var expected = 0f
                        for(k in 0 until 3) {
                            expected += a[i, k] * b[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }
    }

    describe("Matrix4") {
        val a = rMatrix4()

        describe("determinant") {
            val result = determinant(a)

            it("should suffice the definition") {
                var expected = 0f
                for(i in 0 until 4) {
                    expected += a[3, i] * pow(-1, 3 + i) * determinant(submatrix(a, 3, i))
                }

                assert(result).isEqualTo(expected)
            }
        }

        describe("negate") {
            val result = -a

            it("should negate every component") {
                for(i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(result[i, j]).isEqualTo(-a[i, j])
                    }
                }
            }
        }

        describe("transpose") {
            val result = transpose(a)

            it("should reflect through the diagonal") {
                for(i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(result[i, j]).isEqualTo(a[j, i])
                    }
                }
            }
        }

        describe("inverse") {
            val result = inverse(a)

            it("should be inverse") {
                assert(a * result).isIdentity()
                assert(result * a).isIdentity()
            }
        }

        describe("Scalar") {
            val scalar = rValue()

            describe("scale") {
                val result = a * scalar

                it("should multiply each component") {
                    for(i in 0 until 4) {
                        for(j in 0 until 4) {
                            assert(result[i, j]).isEqualTo(a[i, j] * scalar)
                        }
                    }
                }
            }
        }

        describe("Matrix4") {
            val b = rMatrix4()

            describe("addition") {
                val result = a + b

                it("should add both matrices component-wise") {
                    for(i in 0 until 4) {
                        for(j in 0 until 4) {
                            assert(result[i, j]).isEqualTo(a[i, j] + b[i, j])
                        }
                    }
                }
            }

            describe("subtraction") {
                val result = a - b

                it("should subtract both matrices component-wise") {
                    for(i in 0 until 4) {
                        for(j in 0 until 4) {
                            assert(result[i, j]).isEqualTo(a[i, j] - b[i, j])
                        }
                    }
                }
            }

            describe("multiplication") {
                val result = a * b

                it("should suffice the definition") {
                    for(i in 0 until 4) {
                        for(j in 0 until 4) {
                            var expected = 0f
                            for(k in 0 until 4) {
                                expected += a[i, k] * b[k, j]
                            }

                            assert(result[i, j]).isEqualTo(expected)
                        }
                    }
                }
            }
        }

        describe("Vector2") {
            val b = rVector2()

            describe("multiplication") {
                val result = a * b

                it("should suffice the definition") {
                    for(i in 0 until 4) {
                        var expected = 0f
                        for(k in 0 until 4) {
                            expected += a[i, k] * b[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }

        describe("Vector3") {
            val b = rVector3()

            describe("multiplication") {
                val result = a * b

                it("should suffice the definition") {
                    for(i in 0 until 4) {
                        var expected = 0f
                        for(k in 0 until 4) {
                            expected += a[i, k] * b[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }

        describe("Vector4") {
            val b = rVector4()

            describe("multiplication") {
                val result = a * b

                it("should suffice the definition") {
                    for(i in 0 until 4) {
                        var expected = 0f
                        for(k in 0 until 4) {
                            expected += a[i, k] * b[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }

        describe("Point2D") {
            val b = rPoint2D()

            describe("multiplication") {
                val result = a * b

                it("should suffice the definition") {
                    for(i in 0 until 4) {
                        var expected = 0f
                        for(k in 0 until 3) {
                            expected += a[i, k] * b[k]
                        }
                        expected += a[i, 3]

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }

        describe("Point3D") {
            val b = rPoint3D()

            describe("multiplication") {
                val result = a * b

                it("should suffice the definition") {
                    for(i in 0 until 4) {
                        var expected = 0f
                        for(k in 0 until 3) {
                            expected += a[i, k] * b[k]
                        }
                        expected += a[i, 3]

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }
    }
})

private fun rValue() = random(-100f, 100f)
private fun rVector2() = Vector2(rValue(), rValue())
private fun rVector3() = Vector3(rValue(), rValue(), rValue())
private fun rVector4() = Vector4(rValue(), rValue(), rValue(), rValue())
private fun rMatrix2() = Matrix2(rVector2(), rVector2())
private fun rMatrix3() = Matrix3(rVector3(), rVector3(), rVector3())
private fun rMatrix4() = Matrix4(rVector4(), rVector4(), rVector4(), rVector4())
private fun rPoint2D() = Point2D(rVector2())
private fun rPoint3D() = Point3D(rVector3())