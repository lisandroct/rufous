package org.rufousengine.math

import assertk.assert
import assertk.assertions.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.rufousengine.assertions.isCloseTo
import kotlin.math.pow
import kotlin.math.tan

object Matrix4Spec: Spek({
    describe("immutable constructors") {
        on("empty") {
            val matrix = Matrix4()
            it("should be the identity matrix") {
                assert(matrix).isEqualTo(Matrix4.identity)
            }
        }
        on("primary") {
            val e00 = getRandomValue()
            val e01 = getRandomValue()
            val e02 = getRandomValue()
            val e03 = getRandomValue()
            val e10 = getRandomValue()
            val e11 = getRandomValue()
            val e12 = getRandomValue()
            val e13 = getRandomValue()
            val e20 = getRandomValue()
            val e21 = getRandomValue()
            val e22 = getRandomValue()
            val e23 = getRandomValue()
            val e30 = getRandomValue()
            val e31 = getRandomValue()
            val e32 = getRandomValue()
            val e33 = getRandomValue()
            val matrix = Matrix4(e00, e01, e02, e03, e10, e11, e12, e13, e20, e21, e22, e23, e30, e31, e32, e33)
            it("should have e00 set") {
                assert(matrix.e00).isCloseTo(e00)
            }
            it("should have e01 set") {
                assert(matrix.e01).isCloseTo(e01)
            }
            it("should have e02 set") {
                assert(matrix.e02).isCloseTo(e02)
            }
            it("should have e03 set") {
                assert(matrix.e03).isCloseTo(e03)
            }
            it("should have e10 set") {
                assert(matrix.e10).isCloseTo(e10)
            }
            it("should have e11 set") {
                assert(matrix.e11).isCloseTo(e11)
            }
            it("should have e12 set") {
                assert(matrix.e12).isCloseTo(e12)
            }
            it("should have e13 set") {
                assert(matrix.e13).isCloseTo(e13)
            }
            it("should have e20 set") {
                assert(matrix.e20).isCloseTo(e20)
            }
            it("should have e21 set") {
                assert(matrix.e21).isCloseTo(e21)
            }
            it("should have e22 set") {
                assert(matrix.e22).isCloseTo(e22)
            }
            it("should have e23 set") {
                assert(matrix.e23).isCloseTo(e23)
            }
            it("should have e30 set") {
                assert(matrix.e30).isCloseTo(e30)
            }
            it("should have e31 set") {
                assert(matrix.e31).isCloseTo(e31)
            }
            it("should have e32 set") {
                assert(matrix.e32).isCloseTo(e32)
            }
            it("should have e33 set") {
                assert(matrix.e33).isCloseTo(e33)
            }
        }
        on("Projection") {
            val other = getRandomProjection()
            val matrix = Matrix4(other)
            it("should be equal to other") {
                assert(matrix).isEqualTo(other)
            }
        }
        on("Matrix4") {
            val other = getRandomMatrix4()
            val matrix = Matrix4(other)
            it("should be equal to other") {
                assert(matrix).isEqualTo(other)
            }
        }
        on("columns") {
            val column0 = getRandomVector4()
            val column1 = getRandomVector4()
            val column2 = getRandomVector4()
            val column3 = getRandomVector4()
            val matrix = Matrix4(column0, column1, column2, column3)
            it("should have first column set") {
                val column = matrix.getColumn0(MutableVector4())
                assert(column).isEqualTo(column0)
            }
            it("should have second column set") {
                val column = matrix.getColumn1(MutableVector4())
                assert(column).isEqualTo(column1)
            }
            it("should have third column set") {
                val column = matrix.getColumn2(MutableVector4())
                assert(column).isEqualTo(column2)
            }
            it("should have fourth column set") {
                val column = matrix.getColumn3(MutableVector4())
                assert(column).isEqualTo(column3)
            }
        }
    }

    describe("mutable constructors") {
        on("empty") {
            val matrix = MutableMatrix4 { }
            it("should be the identity matrix") {
                assert(matrix).isEqualTo(Matrix4.identity)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
        on("primary") {
            val e00 = getRandomValue()
            val e01 = getRandomValue()
            val e02 = getRandomValue()
            val e03 = getRandomValue()
            val e10 = getRandomValue()
            val e11 = getRandomValue()
            val e12 = getRandomValue()
            val e13 = getRandomValue()
            val e20 = getRandomValue()
            val e21 = getRandomValue()
            val e22 = getRandomValue()
            val e23 = getRandomValue()
            val e30 = getRandomValue()
            val e31 = getRandomValue()
            val e32 = getRandomValue()
            val e33 = getRandomValue()
            val matrix = MutableMatrix4(e00, e01, e02, e03, e10, e11, e12, e13, e20, e21, e22, e23, e30, e31, e32, e33) { }
            it("should have e00 set") {
                assert(matrix.e00).isCloseTo(e00)
            }
            it("should have e01 set") {
                assert(matrix.e01).isCloseTo(e01)
            }
            it("should have e02 set") {
                assert(matrix.e02).isCloseTo(e02)
            }
            it("should have e03 set") {
                assert(matrix.e03).isCloseTo(e03)
            }
            it("should have e10 set") {
                assert(matrix.e10).isCloseTo(e10)
            }
            it("should have e11 set") {
                assert(matrix.e11).isCloseTo(e11)
            }
            it("should have e12 set") {
                assert(matrix.e12).isCloseTo(e12)
            }
            it("should have e13 set") {
                assert(matrix.e13).isCloseTo(e13)
            }
            it("should have e20 set") {
                assert(matrix.e20).isCloseTo(e20)
            }
            it("should have e21 set") {
                assert(matrix.e21).isCloseTo(e21)
            }
            it("should have e22 set") {
                assert(matrix.e22).isCloseTo(e22)
            }
            it("should have e23 set") {
                assert(matrix.e23).isCloseTo(e23)
            }
            it("should have e30 set") {
                assert(matrix.e30).isCloseTo(e30)
            }
            it("should have e31 set") {
                assert(matrix.e31).isCloseTo(e31)
            }
            it("should have e32 set") {
                assert(matrix.e32).isCloseTo(e32)
            }
            it("should have e33 set") {
                assert(matrix.e33).isCloseTo(e33)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
        on("Projection") {
            val other = getRandomProjection()
            val matrix = MutableMatrix4(other) { }
            it("should be equal to other") {
                assert(matrix).isEqualTo(other)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
        on("Matrix4") {
            val other = getRandomMatrix4()
            val matrix = MutableMatrix4(other) { }
            it("should be equal to other") {
                assert(matrix).isEqualTo(other)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
        on("columns") {
            val column0 = getRandomVector4()
            val column1 = getRandomVector4()
            val column2 = getRandomVector4()
            val column3 = getRandomVector4()
            val matrix = MutableMatrix4(column0, column1, column2, column3) { }
            it("should have first column set") {
                val column = matrix.getColumn0(MutableVector4())
                assert(column).isEqualTo(column0)
            }
            it("should have second column set") {
                val column = matrix.getColumn1(MutableVector4())
                assert(column).isEqualTo(column1)
            }
            it("should have third column set") {
                val column = matrix.getColumn2(MutableVector4())
                assert(column).isEqualTo(column2)
            }
            it("should have fourth column set") {
                val column = matrix.getColumn3(MutableVector4())
                assert(column).isEqualTo(column3)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
    }

    describe("premade matrices") {
        on("identity") {
            val matrix = Matrix4.identity
            it("e00 should be 1") {
                assert(matrix.e00).isCloseTo(1f)
            }
            it("e01 should be 0") {
                assert(matrix.e01).isCloseTo(0f)
            }
            it("e02 should be 0") {
                assert(matrix.e02).isCloseTo(0f)
            }
            it("e03 should be 0") {
                assert(matrix.e03).isCloseTo(0f)
            }
            it("e10 should be 0") {
                assert(matrix.e10).isCloseTo(0f)
            }
            it("e11 should be 1") {
                assert(matrix.e11).isCloseTo(1f)
            }
            it("e12 should be 0") {
                assert(matrix.e12).isCloseTo(0f)
            }
            it("e13 should be 0") {
                assert(matrix.e13).isCloseTo(0f)
            }
            it("e20 should be 0") {
                assert(matrix.e20).isCloseTo(0f)
            }
            it("e21 should be 0") {
                assert(matrix.e21).isCloseTo(0f)
            }
            it("e22 should be 1") {
                assert(matrix.e22).isCloseTo(1f)
            }
            it("e23 should be 0") {
                assert(matrix.e23).isCloseTo(0f)
            }
            it("e30 should be 0") {
                assert(matrix.e30).isCloseTo(0f)
            }
            it("e31 should be 0") {
                assert(matrix.e31).isCloseTo(0f)
            }
            it("e32 should be 0") {
                assert(matrix.e32).isCloseTo(0f)
            }
            it("e33 should be 1") {
                assert(matrix.e33).isCloseTo(1f)
            }
            it("should be the identity matrix") {
                assert(matrix.isIdentity).isTrue()
            }
        }
        on("zero") {
            val matrix = Matrix4.zero
            it("e00 should be 0") {
                assert(matrix.e00).isCloseTo(0f)
            }
            it("e01 should be 0") {
                assert(matrix.e01).isCloseTo(0f)
            }
            it("e02 should be 0") {
                assert(matrix.e02).isCloseTo(0f)
            }
            it("e03 should be 0") {
                assert(matrix.e03).isCloseTo(0f)
            }
            it("e10 should be 0") {
                assert(matrix.e10).isCloseTo(0f)
            }
            it("e11 should be 0") {
                assert(matrix.e11).isCloseTo(0f)
            }
            it("e12 should be 0") {
                assert(matrix.e12).isCloseTo(0f)
            }
            it("e13 should be 0") {
                assert(matrix.e13).isCloseTo(0f)
            }
            it("e20 should be 0") {
                assert(matrix.e20).isCloseTo(0f)
            }
            it("e21 should be 0") {
                assert(matrix.e21).isCloseTo(0f)
            }
            it("e22 should be 0") {
                assert(matrix.e22).isCloseTo(0f)
            }
            it("e23 should be 0") {
                assert(matrix.e23).isCloseTo(0f)
            }
            it("e30 should be 0") {
                assert(matrix.e30).isCloseTo(0f)
            }
            it("e31 should be 0") {
                assert(matrix.e31).isCloseTo(0f)
            }
            it("e32 should be 0") {
                assert(matrix.e32).isCloseTo(0f)
            }
            it("e33 should be 0") {
                assert(matrix.e33).isCloseTo(0f)
            }
            it("should be the zero matrix") {
                assert(matrix.isZero).isTrue()
            }
        }
    }

    given("a matrix") {
        val matrix by memoized { getRandomMatrix4() }

        on("transpose (val)") {
            val transpose = matrix.transpose
            it("should reflects the matrix on its main diagonal") {
                for(i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(matrix[i, j]).isCloseTo(transpose[j, i])
                    }
                }
            }
        }

        on("inverse (val)") {
            val inverse = matrix.inverse
            it("should be the multiplicative inverse to the right") {
                assert(matrix.multiply(inverse, MutableMatrix4())).isEqualTo(Matrix4.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(matrix, MutableMatrix4())).isEqualTo(Matrix4.identity)
            }
        }

        on("determinant") {
            val determinant = matrix.determinant
            it("should suffice the expansion by minors test (iterating over the 0-column)") {
                var expected = 0f
                for (i in 0 until 4) {
                    expected += matrix[i, 0] * (-1f).pow(i) * matrix.getSubmatrix(i, 0, MutableMatrix3()).determinant
                }

                assert(determinant).isCloseTo(expected)
            }
        }

        on("copyImmutable") {
            val copy = matrix.copyImmutable()
            it("should be a new instance") {
                assert(copy).isNotSameAs(matrix)
            }
            it("should be equal to the original") {
                assert(copy).isEqualTo(matrix)
            }
            it("should be immutable") {
                assert(copy).isInstanceOf(Matrix4::class)
            }
        }

        on("copyMutable") {
            val copy = matrix.copyMutable()
            it("should be a new instance") {
                assert(copy).isNotSameAs(matrix)
            }
            it("should be equal to the original") {
                assert(copy).isEqualTo(matrix)
            }
            it("should be mutable") {
                assert(copy).isInstanceOf(MutableMatrix4::class)
            }
        }

        describe("rows") {
            on("getRow") {
                val row0 = matrix.getRow(0, MutableVector4())
                val row1 = matrix.getRow(1, MutableVector4())
                val row2 = matrix.getRow(2, MutableVector4())
                val row3 = matrix.getRow(3, MutableVector4())
                it("should return the proper rows") {
                    assert(row0).isEqualTo(matrix.getRow0(MutableVector4()))
                    assert(row1).isEqualTo(matrix.getRow1(MutableVector4()))
                    assert(row2).isEqualTo(matrix.getRow2(MutableVector4()))
                    assert(row3).isEqualTo(matrix.getRow3(MutableVector4()))
                }
            }
            on("getRow0") {
                val row = matrix.getRow0(MutableVector4())
                it("should return the proper row") {
                    assert(row.x).isEqualTo(matrix.e00)
                    assert(row.y).isEqualTo(matrix.e01)
                    assert(row.z).isEqualTo(matrix.e02)
                    assert(row.w).isEqualTo(matrix.e03)
                }
            }
            on("getRow1") {
                val row = matrix.getRow1(MutableVector4())
                it("should return the proper row") {
                    assert(row.x).isEqualTo(matrix.e10)
                    assert(row.y).isEqualTo(matrix.e11)
                    assert(row.z).isEqualTo(matrix.e12)
                    assert(row.w).isEqualTo(matrix.e13)
                }
            }
            on("getRow2") {
                val row = matrix.getRow2(MutableVector4())
                it("should return the proper row") {
                    assert(row.x).isEqualTo(matrix.e20)
                    assert(row.y).isEqualTo(matrix.e21)
                    assert(row.z).isEqualTo(matrix.e22)
                    assert(row.w).isEqualTo(matrix.e23)
                }
            }
            on("getRow3") {
                val row = matrix.getRow3(MutableVector4())
                it("should return the proper row") {
                    assert(row.x).isEqualTo(matrix.e30)
                    assert(row.y).isEqualTo(matrix.e31)
                    assert(row.z).isEqualTo(matrix.e32)
                    assert(row.w).isEqualTo(matrix.e33)
                }
            }
        }

        describe("columns") {
            on("getColumn") {
                val column0 = matrix.getColumn(0, MutableVector4())
                val column1 = matrix.getColumn(1, MutableVector4())
                val column2 = matrix.getColumn(2, MutableVector4())
                val column3 = matrix.getColumn(3, MutableVector4())
                it("should return the proper columns") {
                    assert(column0).isEqualTo(matrix.getColumn0(MutableVector4()))
                    assert(column1).isEqualTo(matrix.getColumn1(MutableVector4()))
                    assert(column2).isEqualTo(matrix.getColumn2(MutableVector4()))
                    assert(column3).isEqualTo(matrix.getColumn3(MutableVector4()))
                }
            }
            on("getColumn0") {
                val column = matrix.getColumn0(MutableVector4())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(matrix.e00)
                    assert(column.y).isEqualTo(matrix.e10)
                    assert(column.z).isEqualTo(matrix.e20)
                    assert(column.w).isEqualTo(matrix.e30)
                }
            }
            on("getColumn1") {
                val column = matrix.getColumn1(MutableVector4())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(matrix.e01)
                    assert(column.y).isEqualTo(matrix.e11)
                    assert(column.z).isEqualTo(matrix.e21)
                    assert(column.w).isEqualTo(matrix.e31)
                }
            }
            on("getColumn2") {
                val column = matrix.getColumn2(MutableVector4())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(matrix.e02)
                    assert(column.y).isEqualTo(matrix.e12)
                    assert(column.z).isEqualTo(matrix.e22)
                    assert(column.w).isEqualTo(matrix.e32)
                }
            }
            on("getColumn3") {
                val column = matrix.getColumn3(MutableVector4())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(matrix.e03)
                    assert(column.y).isEqualTo(matrix.e13)
                    assert(column.z).isEqualTo(matrix.e23)
                    assert(column.w).isEqualTo(matrix.e33)
                }
            }
        }

        on("transpose") {
            val transpose = matrix.transpose(MutableMatrix4())
            it("should reflects the matrix on its main diagonal") {
                for(i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(matrix[i, j]).isCloseTo(transpose[j, i])
                    }
                }
            }
        }

        on("inverse") {
            val inverse = matrix.inverse(MutableMatrix4())
            it("should be the multiplicative inverse to the right") {
                assert(matrix.multiply(inverse, MutableMatrix4())).isEqualTo(Matrix4.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(matrix, MutableMatrix4())).isEqualTo(Matrix4.identity)
            }
        }

        on("scale") {
            val scalar = getRandomValue()
            val scaled = matrix.scale(scalar, MutableMatrix4())
            it("should scale every component") {
                for(i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(scaled[i, j]).isCloseTo(matrix[i, j] * scalar)
                    }
                }
            }
        }

        on("add (Projection)") {
            val other = getRandomProjection()
            val add = matrix.add(other, MutableMatrix4())
            it("should add componentwise") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(add[i, j]).isCloseTo(matrix[i, j] + other[i, j])
                    }
                }
            }
        }

        on("add (Transformation)") {
            val other = getRandomTransformation()
            val add = matrix.add(other, MutableMatrix4())
            it("should add componentwise") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(add[i, j]).isCloseTo(matrix[i, j] + other[i, j])
                    }
                }
            }
        }

        on("add (Matrix4)") {
            val other = getRandomMatrix4()
            val add = matrix.add(other, MutableMatrix4())
            it("should add componentwise") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(add[i, j]).isCloseTo(matrix[i, j] + other[i, j])
                    }
                }
            }
        }

        on("subtract (Projection)") {
            val other = getRandomProjection()
            val subtract = matrix.subtract(other, MutableMatrix4())
            it("should subtract componentwise") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(subtract[i, j]).isCloseTo(matrix[i, j] - other[i, j])
                    }
                }
            }
        }

        on("subtract (Transformation)") {
            val other = getRandomTransformation()
            val subtract = matrix.subtract(other, MutableMatrix4())
            it("should subtract componentwise") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(subtract[i, j]).isCloseTo(matrix[i, j] - other[i, j])
                    }
                }
            }
        }

        on("subtract (Matrix4)") {
            val other = getRandomMatrix4()
            val subtract = matrix.subtract(other, MutableMatrix4())
            it("should subtract componentwise") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(subtract[i, j]).isCloseTo(matrix[i, j] - other[i, j])
                    }
                }
            }
        }

        on("multiply (Projection)") {
            val other = getRandomProjection()
            val multiply = matrix.multiply(other, MutableMatrix4())
            it("should has every (i, j) entry as a linear combination of A's i-row and B's j-column") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        var expected = 0f
                        for(k in 0 until 4) {
                            expected += matrix[i, k] * other[k, j]
                        }

                        assert(multiply[i, j]).isCloseTo(expected)
                    }
                }
            }
        }

        on("multiplyLeft (Projection)") {
            val other = getRandomProjection()
            val multiply = matrix.multiplyLeft(other, MutableMatrix4())
            it("should has every (i, j) entry as a linear combination of B's i-row and A's j-column") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        var expected = 0f
                        for(k in 0 until 4) {
                            expected += other[i, k] * matrix[k, j]
                        }

                        assert(multiply[i, j]).isCloseTo(expected)
                    }
                }
            }
        }

        on("multiply (Transformation)") {
            val other = getRandomTransformation()
            val multiply = matrix.multiply(other, MutableMatrix4())
            it("should has every (i, j) entry as a linear combination of A's i-row and B's j-column") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        var expected = 0f
                        for(k in 0 until 4) {
                            expected += matrix[i, k] * other[k, j]
                        }

                        assert(multiply[i, j]).isCloseTo(expected)
                    }
                }
            }
        }

        on("multiplyLeft (Transformation)") {
            val other = getRandomTransformation()
            val multiply = matrix.multiplyLeft(other, MutableMatrix4())
            it("should has every (i, j) entry as a linear combination of B's i-row and A's j-column") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        var expected = 0f
                        for(k in 0 until 4) {
                            expected += other[i, k] * matrix[k, j]
                        }

                        assert(multiply[i, j]).isCloseTo(expected)
                    }
                }
            }
        }

        on("multiply (Matrix4)") {
            val other = getRandomMatrix4()
            val multiply = matrix.multiply(other, MutableMatrix4())
            it("should has every (i, j) entry as a linear combination of A's i-row and B's j-column") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        var expected = 0f
                        for(k in 0 until 4) {
                            expected += matrix[i, k] * other[k, j]
                        }

                        assert(multiply[i, j]).isCloseTo(expected)
                    }
                }
            }
        }

        on("multiplyLeft (Matrix4)") {
            val other = getRandomMatrix4()
            val multiply = matrix.multiplyLeft(other, MutableMatrix4())
            it("should has every (i, j) entry as a linear combination of B's i-row and A's j-column") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        var expected = 0f
                        for(k in 0 until 4) {
                            expected += other[i, k] * matrix[k, j]
                        }

                        assert(multiply[i, j]).isCloseTo(expected)
                    }
                }
            }
        }

        on("multiply (Vector4)") {
            val vector = getRandomVector4()
            val new = matrix.multiply(vector, MutableVector4())
            it("should has every (i, j) entry as a linear combination of matrix' i-row and vector's components") {
                for (i in 0 until 4) {
                    var expected = 0f
                    for(k in 0 until 4) {
                        expected += matrix[i, k] * vector[k]
                    }

                    assert(new[i]).isCloseTo(expected)
                }
            }
        }

        on("multiply (Vector3)") {
            val vector = getRandomVector3()
            val new = matrix.multiply(vector, MutableVector3())
            it("should has every (i, j) entry as a linear combination of matrix' i-row and vector's components") {
                for (i in 0 until 3) {
                    var expected = 0f
                    for(k in 0 until 3) {
                        expected += matrix[i, k] * vector[k]
                    }

                    assert(new[i]).isCloseTo(expected)
                }
            }
        }

        on("multiply (Point)") {
            val point = getRandomPoint()
            val new = matrix.multiply(point, MutablePoint())
            it("should has every (i, j) entry as a linear combination of matrix' i-row and point's components") {
                var w = 0f
                for(k in 0 until 3) {
                    w += matrix[3, k] * point[k]
                }
                w += matrix[3, 3]

                for (i in 0 until 3) {
                    var expected = 0f
                    for(k in 0 until 3) {
                        expected += matrix[i, k] * point[k]
                    }
                    expected += matrix[i, 3]

                    assert(new[i]).isCloseTo(expected / w)
                }
            }
        }

        on("rotateX") {
            val angle = getRandomValue()
            val rotated = matrix.rotateX(angle, MutableMatrix4())
            it("should be left multiplied by a rotation matrix") {
                val rotation = MutableMatrix4().makeRotationX(angle)
                val expected = matrix.multiplyLeft(rotation, MutableMatrix4())

                assert(rotated).isEqualTo(expected)
            }
        }

        on("rotateY") {
            val angle = getRandomValue()
            val rotated = matrix.rotateY(angle, MutableMatrix4())
            it("should be left multiplied by a rotation matrix") {
                val rotation = MutableMatrix4().makeRotationY(angle)
                val expected = matrix.multiplyLeft(rotation, MutableMatrix4())

                assert(rotated).isEqualTo(expected)
            }
        }

        on("rotateZ") {
            val angle = getRandomValue()
            val rotated = matrix.rotateZ(angle, MutableMatrix4())
            it("should be left multiplied by a rotation matrix") {
                val rotation = MutableMatrix4().makeRotationZ(angle)
                val expected = matrix.multiplyLeft(rotation, MutableMatrix4())

                assert(rotated).isEqualTo(expected)
            }
        }

        on("rotateSafe") {
            val angle = getRandomValue()
            val axis = getRandomVector3()
            val rotated = matrix.rotateSafe(angle, axis, MutableMatrix4())
            it("should be left multiplied by a rotation matrix") {
                val rotation = MutableMatrix4().makeRotationSafe(angle, axis)
                val expected = matrix.multiplyLeft(rotation, MutableMatrix4())

                assert(rotated).isEqualTo(expected)
            }
        }

        on("rotate") {
            val angle = getRandomValue()
            val axis = getRandomVector3().normalize(MutableVector3())
            val rotated = matrix.rotate(angle, axis, MutableMatrix4())
            it("should be left multiplied by a rotation matrix") {
                val rotation = MutableMatrix4().makeRotation(angle, axis)
                val expected = matrix.multiplyLeft(rotation, MutableMatrix4())

                assert(rotated).isEqualTo(expected)
            }
        }

        on("reflectSafe") {
            val axis = getRandomVector3()
            val reflected = matrix.reflectSafe(axis, MutableMatrix4())
            it("should be left multiplied by a reflection matrix") {
                val reflection = MutableMatrix4().makeReflectionSafe(axis)
                val expected = matrix.multiplyLeft(reflection, MutableMatrix4())

                assert(reflected).isEqualTo(expected)
            }
        }

        on("reflect") {
            val axis = getRandomVector3().normalize(MutableVector3())
            val reflected = matrix.reflect(axis, MutableMatrix4())
            it("should be left multiplied by a reflection matrix") {
                val reflection = MutableMatrix4().makeReflection(axis)
                val expected = matrix.multiplyLeft(reflection, MutableMatrix4())

                assert(reflected).isEqualTo(expected)
            }
        }

        on("involuteSafe") {
            val axis = getRandomVector3()
            val involuted = matrix.involuteSafe(axis, MutableMatrix4())
            it("should be left multiplied by an involution matrix") {
                val involution = MutableMatrix4().makeInvolutionSafe(axis)
                val expected = matrix.multiplyLeft(involution, MutableMatrix4())

                assert(involuted).isEqualTo(expected)
            }
        }

        on("involute") {
            val axis = getRandomVector3().normalize(MutableVector3())
            val involuted = matrix.involute(axis, MutableMatrix4())
            it("should be left multiplied by an involution matrix") {
                val involution = MutableMatrix4().makeInvolution(axis)
                val expected = matrix.multiplyLeft(involution, MutableMatrix4())

                assert(involuted).isEqualTo(expected)
            }
        }

        on("scale (nonuniform)") {
            val factorX = getRandomValue()
            val factorY = getRandomValue()
            val factorZ = getRandomValue()
            val scaled = matrix.scale(factorX, factorY, factorZ, MutableMatrix4())
            it("should be left multiplied by a scale matrix") {
                val scale = MutableMatrix4().makeScale(factorX, factorY, factorZ)
                val expected = matrix.multiplyLeft(scale, MutableMatrix4())

                assert(scaled).isEqualTo(expected)
            }
        }

        on("scaleSafe") {
            val factor = getRandomValue()
            val axis = getRandomVector3()
            val scaled = matrix.scaleSafe(factor, axis, MutableMatrix4())
            it("should be left multiplied by a scale matrix") {
                val scale = MutableMatrix4().makeScaleSafe(factor, axis)
                val expected = matrix.multiplyLeft(scale, MutableMatrix4())

                assert(scaled).isEqualTo(expected)
            }
        }

        on("scale (transformation)") {
            val factor = getRandomValue()
            val axis = getRandomVector3().normalize(MutableVector3())
            val scaled = matrix.scale(factor, axis, MutableMatrix4())
            it("should be left multiplied by a scale matrix") {
                val scale = MutableMatrix4().makeScale(factor, axis)
                val expected = matrix.multiplyLeft(scale, MutableMatrix4())

                assert(scaled).isEqualTo(expected)
            }
        }
    }
    
    given("a mutable matrix") {
        var counter = 0
        val matrix by memoized { getRandomMutableMatrix4 { counter++ } }

        describe("seters") {
            on("e00") {
                counter = 0
                val value = getRandomValue()
                matrix.e00 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e00 set") {
                    assert(matrix.e00).isCloseTo(value)
                }
            }

            on("e01") {
                counter = 0
                val value = getRandomValue()
                matrix.e01 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e01 set") {
                    assert(matrix.e01).isCloseTo(value)
                }
            }

            on("e02") {
                counter = 0
                val value = getRandomValue()
                matrix.e02 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e02 set") {
                    assert(matrix.e02).isCloseTo(value)
                }
            }

            on("e03") {
                counter = 0
                val value = getRandomValue()
                matrix.e03 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e03 set") {
                    assert(matrix.e03).isCloseTo(value)
                }
            }

            on("e10") {
                counter = 0
                val value = getRandomValue()
                matrix.e10 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e10 set") {
                    assert(matrix.e10).isCloseTo(value)
                }
            }

            on("e11") {
                counter = 0
                val value = getRandomValue()
                matrix.e11 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e11 set") {
                    assert(matrix.e11).isCloseTo(value)
                }
            }

            on("e12") {
                counter = 0
                val value = getRandomValue()
                matrix.e12 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e12 set") {
                    assert(matrix.e12).isCloseTo(value)
                }
            }

            on("e13") {
                counter = 0
                val value = getRandomValue()
                matrix.e13 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e13 set") {
                    assert(matrix.e13).isCloseTo(value)
                }
            }

            on("e20") {
                counter = 0
                val value = getRandomValue()
                matrix.e20 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e20 set") {
                    assert(matrix.e20).isCloseTo(value)
                }
            }

            on("e21") {
                counter = 0
                val value = getRandomValue()
                matrix.e21 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e21 set") {
                    assert(matrix.e21).isCloseTo(value)
                }
            }

            on("e22") {
                counter = 0
                val value = getRandomValue()
                matrix.e22 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e22 set") {
                    assert(matrix.e22).isCloseTo(value)
                }
            }

            on("e23") {
                counter = 0
                val value = getRandomValue()
                matrix.e23 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e23 set") {
                    assert(matrix.e23).isCloseTo(value)
                }
            }

            on("e30") {
                counter = 0
                val value = getRandomValue()
                matrix.e30 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e30 set") {
                    assert(matrix.e30).isCloseTo(value)
                }
            }

            on("e31") {
                counter = 0
                val value = getRandomValue()
                matrix.e31 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e31 set") {
                    assert(matrix.e31).isCloseTo(value)
                }
            }

            on("e32") {
                counter = 0
                val value = getRandomValue()
                matrix.e32 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e32 set") {
                    assert(matrix.e32).isCloseTo(value)
                }
            }

            on("e33") {
                counter = 0
                val value = getRandomValue()
                matrix.e33 = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should has e33 set") {
                    assert(matrix.e33).isCloseTo(value)
                }
            }

            on("Projection") {
                counter = 0
                val other = getRandomProjection()
                matrix.set(other)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should be equal to other") {
                    assert(matrix).isEqualTo(other)
                }
            }

            on("Matrix4") {
                counter = 0
                val other = getRandomMatrix4()
                matrix.set(other)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should be equal to other") {
                    assert(matrix).isEqualTo(other)
                }
            }

            on("columns") {
                counter = 0
                val column0 = getRandomVector4()
                val column1 = getRandomVector4()
                val column2 = getRandomVector4()
                val column3 = getRandomVector4()
                matrix.set(column0, column1, column2, column3)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have first column set") {
                    val column = matrix.getColumn0(MutableVector4())
                    assert(column).isEqualTo(column0)
                }
                it("should have second column set") {
                    val column = matrix.getColumn1(MutableVector4())
                    assert(column).isEqualTo(column1)
                }
                it("should have third column set") {
                    val column = matrix.getColumn2(MutableVector4())
                    assert(column).isEqualTo(column2)
                }
                it("should have fourth column set") {
                    val column = matrix.getColumn3(MutableVector4())
                    assert(column).isEqualTo(column3)
                }
            }

            on("set") {
                counter = 0
                val e00 = getRandomValue()
                val e01 = getRandomValue()
                val e02 = getRandomValue()
                val e03 = getRandomValue()
                val e10 = getRandomValue()
                val e11 = getRandomValue()
                val e12 = getRandomValue()
                val e13 = getRandomValue()
                val e20 = getRandomValue()
                val e21 = getRandomValue()
                val e22 = getRandomValue()
                val e23 = getRandomValue()
                val e30 = getRandomValue()
                val e31 = getRandomValue()
                val e32 = getRandomValue()
                val e33 = getRandomValue()
                matrix.set(e00, e01, e02, e03, e10, e11, e12, e13, e20, e21, e22, e23, e30, e31, e32, e33)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have e00 set") {
                    assert(matrix.e00).isCloseTo(e00)
                }
                it("should have e01 set") {
                    assert(matrix.e01).isCloseTo(e01)
                }
                it("should have e02 set") {
                    assert(matrix.e02).isCloseTo(e02)
                }
                it("should have e03 set") {
                    assert(matrix.e03).isCloseTo(e03)
                }
                it("should have e10 set") {
                    assert(matrix.e10).isCloseTo(e10)
                }
                it("should have e11 set") {
                    assert(matrix.e11).isCloseTo(e11)
                }
                it("should have e12 set") {
                    assert(matrix.e12).isCloseTo(e12)
                }
                it("should have e13 set") {
                    assert(matrix.e13).isCloseTo(e13)
                }
                it("should have e20 set") {
                    assert(matrix.e20).isCloseTo(e20)
                }
                it("should have e21 set") {
                    assert(matrix.e21).isCloseTo(e21)
                }
                it("should have e22 set") {
                    assert(matrix.e22).isCloseTo(e22)
                }
                it("should have e23 set") {
                    assert(matrix.e23).isCloseTo(e23)
                }
                it("should have e30 set") {
                    assert(matrix.e30).isCloseTo(e30)
                }
                it("should have e31 set") {
                    assert(matrix.e31).isCloseTo(e31)
                }
                it("should have e32 set") {
                    assert(matrix.e32).isCloseTo(e32)
                }
                it("should have e33 set") {
                    assert(matrix.e33).isCloseTo(e33)
                }
            }

            on("operator") {
                counter = 0
                val e00 = getRandomValue()
                val e01 = getRandomValue()
                val e02 = getRandomValue()
                val e03 = getRandomValue()
                val e10 = getRandomValue()
                val e11 = getRandomValue()
                val e12 = getRandomValue()
                val e13 = getRandomValue()
                val e20 = getRandomValue()
                val e21 = getRandomValue()
                val e22 = getRandomValue()
                val e23 = getRandomValue()
                val e30 = getRandomValue()
                val e31 = getRandomValue()
                val e32 = getRandomValue()
                val e33 = getRandomValue()
                matrix[0, 0] = e00
                matrix[0, 1] = e01
                matrix[0, 2] = e02
                matrix[0, 3] = e03
                matrix[1, 0] = e10
                matrix[1, 1] = e11
                matrix[1, 2] = e12
                matrix[1, 3] = e13
                matrix[2, 0] = e20
                matrix[2, 1] = e21
                matrix[2, 2] = e22
                matrix[2, 3] = e23
                matrix[3, 0] = e30
                matrix[3, 1] = e31
                matrix[3, 2] = e32
                matrix[3, 3] = e33
                it("should have notified 16 times") {
                    assert(counter).isEqualTo(16)
                }
                it("should have e00 set") {
                    assert(matrix.e00).isCloseTo(e00)
                }
                it("should have e01 set") {
                    assert(matrix.e01).isCloseTo(e01)
                }
                it("should have e02 set") {
                    assert(matrix.e02).isCloseTo(e02)
                }
                it("should have e03 set") {
                    assert(matrix.e03).isCloseTo(e03)
                }
                it("should have e10 set") {
                    assert(matrix.e10).isCloseTo(e10)
                }
                it("should have e11 set") {
                    assert(matrix.e11).isCloseTo(e11)
                }
                it("should have e12 set") {
                    assert(matrix.e12).isCloseTo(e12)
                }
                it("should have e13 set") {
                    assert(matrix.e13).isCloseTo(e13)
                }
                it("should have e20 set") {
                    assert(matrix.e20).isCloseTo(e20)
                }
                it("should have e21 set") {
                    assert(matrix.e21).isCloseTo(e21)
                }
                it("should have e22 set") {
                    assert(matrix.e22).isCloseTo(e22)
                }
                it("should have e23 set") {
                    assert(matrix.e23).isCloseTo(e23)
                }
                it("should have e30 set") {
                    assert(matrix.e30).isCloseTo(e30)
                }
                it("should have e31 set") {
                    assert(matrix.e31).isCloseTo(e31)
                }
                it("should have e32 set") {
                    assert(matrix.e32).isCloseTo(e32)
                }
                it("should have e33 set") {
                    assert(matrix.e33).isCloseTo(e33)
                }
            }
        }

        describe("operators") {
            on("plusAssign (Projection)") {
                val original = matrix.copyMutable()
                val other = getRandomProjection()
                matrix += other
                it("should add and assign") {
                    assert(matrix).isEqualTo(original.add(other))
                }
            }
            on("plusAssign (Transformation)") {
                val original = matrix.copyMutable()
                val other = getRandomTransformation()
                matrix += other
                it("should add and assign") {
                    assert(matrix).isEqualTo(original.add(other))
                }
            }
            on("plusAssign (Matrix4)") {
                val original = matrix.copyMutable()
                val other = getRandomMatrix4()
                matrix += other
                it("should add and assign") {
                    assert(matrix).isEqualTo(original.add(other))
                }
            }
            on("minusAssign (Projection)") {
                val original = matrix.copyMutable()
                val other = getRandomProjection()
                matrix -= other
                it("should subtract and assign") {
                    assert(matrix).isEqualTo(original.subtract(other))
                }
            }
            on("minusAssign (Transformation)") {
                val original = matrix.copyMutable()
                val other = getRandomTransformation()
                matrix -= other
                it("should subtract and assign") {
                    assert(matrix).isEqualTo(original.subtract(other))
                }
            }
            on("minusAssign (Matrix4)") {
                val original = matrix.copyMutable()
                val other = getRandomMatrix4()
                matrix -= other
                it("should subtract and assign") {
                    assert(matrix).isEqualTo(original.subtract(other))
                }
            }
            on("timesAssign") {
                val original = matrix.copyMutable()
                val scalar = getRandomValue()
                matrix *= scalar
                it("should scale and assign") {
                    assert(matrix).isEqualTo(original.scale(scalar))
                }
            }
            on("divAssign") {
                val original = matrix.copyMutable()
                val scalar = getRandomValue()
                matrix /= scalar
                it("should scale and assign") {
                    assert(matrix).isEqualTo(original.scale(1 / scalar))
                }
            }
            on("timesAssign (Projection)") {
                val original = matrix.copyMutable()
                val other = getRandomProjection()
                matrix *= other
                it("should scale and assign") {
                    assert(matrix).isEqualTo(original.multiply(other))
                }
            }
            on("timesAssign (Transformation)") {
                val original = matrix.copyMutable()
                val other = getRandomTransformation()
                matrix *= other
                it("should scale and assign") {
                    assert(matrix).isEqualTo(original.multiply(other))
                }
            }
            on("timesAssign (Matrix4)") {
                val original = matrix.copyMutable()
                val other = getRandomMatrix4()
                matrix *= other
                it("should scale and assign") {
                    assert(matrix).isEqualTo(original.multiply(other))
                }
            }
        }

        on("transpose (val)") {
            val transpose = matrix.transpose
            it("should reflects the matrix on its main diagonal") {
                for(i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(matrix[i, j]).isCloseTo(transpose[j, i])
                    }
                }
            }
            it("should update") {
                val original = transpose.copyImmutable()
                matrix.randomize()
                assert(original).isNotEqualTo(matrix.transpose)
            }
            it("should always be the same reference") {
                assert(transpose).isSameAs(matrix.transpose)
            }
        }

        on("inverse (val)") {
            val inverse = matrix.inverse
            it("should be the multiplicative inverse to the right") {
                assert(matrix.multiply(inverse, MutableMatrix4())).isEqualTo(Matrix4.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(matrix, MutableMatrix4())).isEqualTo(Matrix4.identity)
            }
            it("should update") {
                val original = inverse.copyImmutable()
                matrix.randomize()
                assert(original).isNotEqualTo(matrix.inverse)
            }
            it("should always be the same reference") {
                assert(inverse).isSameAs(matrix.inverse)
            }
        }

        on("identity") {
            matrix.identity()
            it("should be the identity matrix") {
                assert(matrix).isEqualTo(Matrix4.identity)
            }
        }

        on("zero") {
            matrix.zero()
            it("should be the zero matrix") {
                assert(matrix).isEqualTo(Matrix4.zero)
            }
        }

        on("transpose") {
            val original = matrix.copyImmutable()
            matrix.transpose()
            it("should transpose and assign") {
                val expected = original.transpose(MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("inverse") {
            val original = matrix.copyImmutable()
            matrix.inverse()
            it("should inverse and assign") {
                val expected = original.inverse(MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("scale") {
            val original = matrix.copyImmutable()
            val scalar = getRandomValue()
            matrix.scale(scalar)
            it("should scale and assign") {
                val expected = original.scale(scalar, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("add (Projection)") {
            val original = matrix.copyImmutable()
            val other = getRandomProjection()
            matrix.add(other)
            it("should add and assign") {
                val expected = original.add(other, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("add (Transformation)") {
            val original = matrix.copyImmutable()
            val other = getRandomTransformation()
            matrix.add(other)
            it("should add and assign") {
                val expected = original.add(other, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("add (Matrix4)") {
            val original = matrix.copyImmutable()
            val other = getRandomMatrix4()
            matrix.add(other)
            it("should add and assign") {
                val expected = original.add(other, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("subtract (Projection)") {
            val original = matrix.copyImmutable()
            val other = getRandomProjection()
            matrix.subtract(other)
            it("should subtract and assign") {
                val expected = original.subtract(other, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("subtract (Transformation)") {
            val original = matrix.copyImmutable()
            val other = getRandomTransformation()
            matrix.subtract(other)
            it("should subtract and assign") {
                val expected = original.subtract(other, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("subtract (Matrix4)") {
            val original = matrix.copyImmutable()
            val other = getRandomMatrix4()
            matrix.subtract(other)
            it("should subtract and assign") {
                val expected = original.subtract(other, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiply (Projection)") {
            val original = matrix.copyImmutable()
            val other = getRandomProjection()
            matrix.multiply(other)
            it("should multiply and assign") {
                val expected = original.multiply(other, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Projection)") {
            val original = matrix.copyImmutable()
            val other = getRandomProjection()
            matrix.multiplyLeft(other)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(other, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiply (Transformation)") {
            val original = matrix.copyImmutable()
            val other = getRandomTransformation()
            matrix.multiply(other)
            it("should multiply and assign") {
                val expected = original.multiply(other, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Transformation)") {
            val original = matrix.copyImmutable()
            val other = getRandomTransformation()
            matrix.multiplyLeft(other)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(other, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiply (Matrix4)") {
            val original = matrix.copyImmutable()
            val other = getRandomMatrix4()
            matrix.multiply(other)
            it("should multiply and assign") {
                val expected = original.multiply(other, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Matrix4)") {
            val original = matrix.copyImmutable()
            val other = getRandomMatrix4()
            matrix.multiplyLeft(other)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(other, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("rotateX") {
            val original = matrix.copyImmutable()
            val angle = getRandomValue()
            matrix.rotateX(angle)
            it("should rotateX and assign") {
                val expected = original.rotateX(angle, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("rotateY") {
            val original = matrix.copyImmutable()
            val angle = getRandomValue()
            matrix.rotateY(angle)
            it("should rotateY and assign") {
                val expected = original.rotateY(angle, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("rotateZ") {
            val original = matrix.copyImmutable()
            val angle = getRandomValue()
            matrix.rotateZ(angle)
            it("should rotateZ and assign") {
                val expected = original.rotateZ(angle, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("rotateSafe") {
            val original = matrix.copyImmutable()
            val angle = getRandomValue()
            val axis = getRandomVector3()
            matrix.rotateSafe(angle, axis)
            it("should rotateSafe and assign") {
                val expected = original.rotateSafe(angle, axis, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("rotate") {
            val original = matrix.copyImmutable()
            val angle = getRandomValue()
            val axis = getRandomVector3().normalize(MutableVector3())
            matrix.rotate(angle, axis)
            it("should rotate and assign") {
                val expected = original.rotate(angle, axis, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("reflectSafe") {
            val original = matrix.copyImmutable()
            val axis = getRandomVector3()
            matrix.reflectSafe(axis)
            it("should reflectSafe and assign") {
                val expected = original.reflectSafe(axis, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("reflect") {
            val original = matrix.copyImmutable()
            val axis = getRandomVector3().normalize(MutableVector3())
            matrix.reflect(axis)
            it("should reflect and assign") {
                val expected = original.reflect(axis, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("involuteSafe") {
            val original = matrix.copyImmutable()
            val axis = getRandomVector3()
            matrix.involuteSafe(axis)
            it("should involuteSafe and assign") {
                val expected = original.involuteSafe(axis, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("involute") {
            val original = matrix.copyImmutable()
            val axis = getRandomVector3().normalize(MutableVector3())
            matrix.involute(axis)
            it("should involute and assign") {
                val expected = original.involute(axis, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("scale (nonuniform)") {
            val original = matrix.copyImmutable()
            val factorX = getRandomValue()
            val factorY = getRandomValue()
            val factorZ = getRandomValue()
            matrix.scale(factorX, factorY, factorZ)
            it("should scale and assign") {
                val expected = original.scale(factorX, factorY, factorZ, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }
        on("scaleSafe") {
            val original = matrix.copyImmutable()
            val factor = getRandomValue()
            val axis = getRandomVector3()
            matrix.scaleSafe(factor, axis)
            it("should scaleSafe and assign") {
                val expected = original.scaleSafe(factor, axis, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }
        on("scale (transformation)") {
            val original = matrix.copyImmutable()
            val factor = getRandomValue()
            val axis = getRandomVector3().normalize(MutableVector3())
            matrix.scale(factor, axis)
            it("should scale and assign") {
                val expected = original.scale(factor, axis, MutableMatrix4())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("makeOrthographic (complete)") {
            val top = getPositiveValue()
            val bottom = getNegativeValue()
            val right = getPositiveValue()
            val left = getNegativeValue()
            val near = getPositiveValue()
            val far = near * getPositiveValue()

            matrix.makeOrthographic(top, bottom, right, left, near, far)
            it("should suffice the definition") {
                val expected = Matrix4(
                        2f / (right - left), 0f, 0f, 0f,
                        0f, 2f / (top - bottom), 0f, 0f,
                        0f, 0f, -2f / (far - near), 0f,
                        -(right + left) / (right - left), -(top + bottom) / (top - bottom), -(far + near) / (far - near), 1f
                )

                assert(matrix).isEqualTo(expected)
            }
        }

        on("makeOrthographic (convenient)") {
            val width = getPositiveValue()
            val height = getPositiveValue()
            val near = getPositiveValue()
            val far = near * getPositiveValue()

            matrix.makeOrthographic(width, height, near, far)
            it("should be equal to complete alternative") {
                val top = height * 0.5f
                val bottom = -top
                val right = width * 0.5f
                val left = -right
                val expected = MutableMatrix4().makeOrthographic(top, bottom, right, left, near, far)

                assert(matrix).isEqualTo(expected)
            }
        }

        on("makePerspective (complete)") {
            val top = getPositiveValue()
            val bottom = getNegativeValue()
            val right = getPositiveValue()
            val left = getNegativeValue()
            val near = getPositiveValue()
            val far = near * getPositiveValue()

            matrix.makePerspective(top, bottom, right, left, near, far)
            it("should suffice the definition") {
                val expected = Matrix4(
                        (2f * near) / (right - left), 0f, 0f, 0f,
                        0f, (2f * near) / (top - bottom), 0f, 0f,
                        (right + left) / (right - left), (top + bottom) / (top - bottom), -(far + near) / (far - near), -1f,
                        0f, 0f, -(2f * far * near) / (far - near), 0f
                )

                assert(matrix).isEqualTo(expected)
            }
        }

        on("makePerspective (convenient)") {
            val fieldOfView = random(30f, 160f)
            val aspectRatio = getPositiveValue()
            val near = getPositiveValue()
            val far = near * getPositiveValue()

            matrix.makePerspective(fieldOfView, aspectRatio, near, far)
            it("should be equal to complete alternative") {
                val top = tan(fieldOfView.toRadians() / 2f) * near
                val bottom = -top
                val right = top * aspectRatio
                val left = -right
                val expected = MutableMatrix4().makePerspective(top, bottom, right, left, near, far)

                assert(matrix).isEqualTo(expected)
            }
        }

        on("makeRotationX") {
            val angle = getRandomValue()
            matrix.makeRotationX(angle)
            it("should suffice the definition") {
                assert(matrix.e00).isCloseTo(1f)
                assert(matrix.e01).isCloseTo(0f)
                assert(matrix.e02).isCloseTo(0f)
                assert(matrix.e03).isCloseTo(0f)
                assert(matrix.e10).isCloseTo(0f)
                assert(matrix.e11).isCloseTo(cos(angle))
                assert(matrix.e12).isCloseTo(-sin(angle))
                assert(matrix.e13).isCloseTo(0f)
                assert(matrix.e20).isCloseTo(0f)
                assert(matrix.e21).isCloseTo(sin(angle))
                assert(matrix.e22).isCloseTo(cos(angle))
                assert(matrix.e23).isCloseTo(0f)
                assert(matrix.e30).isCloseTo(0f)
                assert(matrix.e31).isCloseTo(0f)
                assert(matrix.e32).isCloseTo(0f)
                assert(matrix.e33).isCloseTo(1f)
            }
        }

        on("makeRotationY") {
            val angle = getRandomValue()
            matrix.makeRotationY(angle)
            it("should suffice the definition") {
                assert(matrix.e00).isCloseTo(cos(angle))
                assert(matrix.e01).isCloseTo(0f)
                assert(matrix.e02).isCloseTo(sin(angle))
                assert(matrix.e03).isCloseTo(0f)
                assert(matrix.e10).isCloseTo(0f)
                assert(matrix.e11).isCloseTo(1f)
                assert(matrix.e12).isCloseTo(0f)
                assert(matrix.e13).isCloseTo(0f)
                assert(matrix.e20).isCloseTo(-sin(angle))
                assert(matrix.e21).isCloseTo(0f)
                assert(matrix.e22).isCloseTo(cos(angle))
                assert(matrix.e23).isCloseTo(0f)
                assert(matrix.e30).isCloseTo(0f)
                assert(matrix.e31).isCloseTo(0f)
                assert(matrix.e32).isCloseTo(0f)
                assert(matrix.e33).isCloseTo(1f)
            }
        }

        on("makeRotationZ") {
            val angle = getRandomValue()
            matrix.makeRotationZ(angle)
            it("should suffice the definition") {
                assert(matrix.e00).isCloseTo(cos(angle))
                assert(matrix.e01).isCloseTo(-sin(angle))
                assert(matrix.e02).isCloseTo(0f)
                assert(matrix.e03).isCloseTo(0f)
                assert(matrix.e10).isCloseTo(sin(angle))
                assert(matrix.e11).isCloseTo(cos(angle))
                assert(matrix.e12).isCloseTo(0f)
                assert(matrix.e13).isCloseTo(0f)
                assert(matrix.e20).isCloseTo(0f)
                assert(matrix.e21).isCloseTo(0f)
                assert(matrix.e22).isCloseTo(1f)
                assert(matrix.e23).isCloseTo(0f)
                assert(matrix.e30).isCloseTo(0f)
                assert(matrix.e31).isCloseTo(0f)
                assert(matrix.e32).isCloseTo(0f)
                assert(matrix.e33).isCloseTo(1f)
            }
        }

        on("makeRotationSafe") {
            val angle = getRandomValue()
            val axis = getRandomVector3()
            matrix.makeRotationSafe(angle, axis)
            it("should represent a rotation through angle about axis") {
                val vector = getRandomVector3()
                val rotated = vector.multiplyLeft(matrix, MutableVector3())
                val unitAxis = axis.copyMutable().normalize()
                val expected = vector.projectOnto(unitAxis, MutableVector3())
                expected += vector.rejectFrom(unitAxis, MutableVector3()).scale(cos(angle))
                expected += unitAxis.cross(vector, MutableVector3()).scale(sin(angle))

                assert(rotated).isEqualTo(expected)
            }
        }

        on("makeRotation") {
            val angle = getRandomValue()
            val axis = getRandomVector3().normalize(MutableVector3())
            matrix.makeRotation(angle, axis)
            it("should represent a rotation through angle about axis") {
                val vector = getRandomVector3()
                val rotated = vector.multiplyLeft(matrix, MutableVector3())
                val expected = vector.projectOnto(axis, MutableVector3())
                expected += vector.rejectFrom(axis, MutableVector3()).scale(cos(angle))
                expected += axis.cross(vector, MutableVector3()).scale(sin(angle))

                assert(rotated).isEqualTo(expected)
            }
        }

        on("makeReflectionSafe") {
            val axis = getRandomVector3()
            matrix.makeReflectionSafe(axis)
            it("should represent a reflection through a plane perpendicular to axis") {
                val vector = getRandomVector3()
                val reflected = vector.multiplyLeft(matrix, MutableVector3())
                val expected = vector.rejectFrom(axis, MutableVector3()).subtract(vector.projectOnto(axis, MutableVector3()))

                assert(reflected).isEqualTo(expected)
            }
        }

        on("makeReflection") {
            val axis = getRandomVector3().normalize(MutableVector3())
            matrix.makeReflection(axis)
            it("should represent a reflection through a plane perpendicular to axis") {
                val vector = getRandomVector3()
                val reflected = vector.multiplyLeft(matrix, MutableVector3())
                val expected = vector.rejectFrom(axis, MutableVector3()).subtract(vector.projectOnto(axis, MutableVector3()))

                assert(reflected).isEqualTo(expected)
            }
        }

        on("makeInvolutionSafe") {
            val axis = getRandomVector3()
            matrix.makeInvolutionSafe(axis)
            it("should represent an involution through axis") {
                val vector = getRandomVector3()
                val reflected = vector.multiplyLeft(matrix, MutableVector3())
                val expected = vector.projectOnto(axis, MutableVector3()).subtract(vector.rejectFrom(axis, MutableVector3()))

                assert(reflected).isEqualTo(expected)
            }
        }

        on("makeInvolution") {
            val axis = getRandomVector3().normalize(MutableVector3())
            matrix.makeInvolution(axis)
            it("should represent an involution through axis") {
                val vector = getRandomVector3()
                val reflected = vector.multiplyLeft(matrix, MutableVector3())
                val expected = vector.projectOnto(axis, MutableVector3()).subtract(vector.rejectFrom(axis, MutableVector3()))

                assert(reflected).isEqualTo(expected)
            }
        }

        on("makeScale (uniform)") {
            val factor = getRandomValue()
            matrix.makeScale(factor)
            it("should represent a uniform scale") {
                val vector = getRandomVector3()
                val scaled = vector.multiplyLeft(matrix, MutableVector3())
                val expected = vector.scale(factor, MutableVector3())

                assert(scaled).isEqualTo(expected)
            }
        }

        on("makeScale (nonuniform)") {
            val factorX = getRandomValue()
            val factorY = getRandomValue()
            val factorZ = getRandomValue()
            matrix.makeScale(factorX, factorY, factorZ)
            it("should represent a uniform scale") {
                val vector = getRandomVector3()
                val scaled = vector.multiplyLeft(matrix, MutableVector3())
                val expected = Vector3(vector.x * factorX, vector.y * factorY, vector.z * factorZ)

                assert(scaled).isEqualTo(expected)
            }
        }

        on("makeScaleSafe") {
            val factor = getRandomValue()
            val axis = getRandomVector3()
            matrix.makeScaleSafe(factor, axis)
            it("should represent a scale by factor along axis") {
                val vector = getRandomVector3()
                val scaled = vector.multiplyLeft(matrix, MutableVector3())
                val expected = vector.projectOnto(axis, MutableVector3()).scale(factor).add(vector.rejectFrom(axis, MutableVector3()))

                assert(scaled).isEqualTo(expected)
            }
        }

        on("makeScale") {
            val factor = getRandomValue()
            val axis = getRandomVector3().normalize(MutableVector3())
            matrix.makeScale(factor, axis)
            it("should represent a scale by factor along axis") {
                val vector = getRandomVector3()
                val scaled = vector.multiplyLeft(matrix, MutableVector3())
                val expected = vector.projectOnto(axis, MutableVector3()).scale(factor).add(vector.rejectFrom(axis, MutableVector3()))

                assert(scaled).isEqualTo(expected)
            }
        }

        on("makeTranslation") {
            val point = getRandomPoint()
            matrix.makeTranslation(point)
            it("should translate") {
                val p = getRandomPoint()
                val translated = p.multiplyLeft(matrix, MutablePoint())
                val expected = p.add(point, MutablePoint())

                assert(translated).isEqualTo(expected)
            }
        }
    }
})

private fun getRandomValue() = random(-100f, 100f)
private fun getPositiveValue() = random(1f, 100f)
private fun getNegativeValue() = random(-100f, -1f)
private fun getRandomVector4() = Vector4(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomVector3() = Vector3(getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomPoint() = Point(getRandomValue(), getRandomValue(), getRandomValue())
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
private fun getRandomMutableMatrix4(observer: ((Matrix4) -> Unit)) = MutableMatrix4(
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        observer
)
private fun MutableMatrix4.randomize() = this.set(
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()
)