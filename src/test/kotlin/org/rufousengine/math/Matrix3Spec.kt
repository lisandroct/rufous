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

object Matrix3Spec: Spek({
    describe("immutable constructors") {
        on("empty") {
            val matrix = Matrix3()
            it("should be the identity matrix") {
                assert(matrix).isEqualTo(Matrix3.identity)
            }
        }
        on("primary") {
            val e00 = getRandomValue()
            val e01 = getRandomValue()
            val e02 = getRandomValue()
            val e10 = getRandomValue()
            val e11 = getRandomValue()
            val e12 = getRandomValue()
            val e20 = getRandomValue()
            val e21 = getRandomValue()
            val e22 = getRandomValue()
            val matrix = Matrix3(e00, e01, e02, e10, e11, e12, e20, e21, e22)
            it("should have e00 set") {
                assert(matrix.e00).isCloseTo(e00)
            }
            it("should have e01 set") {
                assert(matrix.e01).isCloseTo(e01)
            }
            it("should have e02 set") {
                assert(matrix.e02).isCloseTo(e02)
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
            it("should have e20 set") {
                assert(matrix.e20).isCloseTo(e20)
            }
            it("should have e21 set") {
                assert(matrix.e21).isCloseTo(e21)
            }
            it("should have e22 set") {
                assert(matrix.e22).isCloseTo(e22)
            }
        }
        on("Matrix3") {
            val other = getRandomMatrix()
            val matrix = Matrix3(other)
            it("should be equal to other") {
                assert(matrix).isEqualTo(other)
            }
        }
        on("columns") {
            val column0 = getRandomVector()
            val column1 = getRandomVector()
            val column2 = getRandomVector()
            val matrix = Matrix3(column0, column1, column2)
            it("should have first column set") {
                val column = matrix.getColumn0(MutableVector3())
                assert(column).isEqualTo(column0)
            }
            it("should have second column set") {
                val column = matrix.getColumn1(MutableVector3())
                assert(column).isEqualTo(column1)
            }
            it("should have third column set") {
                val column = matrix.getColumn2(MutableVector3())
                assert(column).isEqualTo(column2)
            }
        }
    }

    describe("mutable constructors") {
        on("empty") {
            val matrix = MutableMatrix3 { }
            it("should be the identity matrix") {
                assert(matrix).isEqualTo(Matrix3.identity)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
        on("primary") {
            val e00 = getRandomValue()
            val e01 = getRandomValue()
            val e02 = getRandomValue()
            val e10 = getRandomValue()
            val e11 = getRandomValue()
            val e12 = getRandomValue()
            val e20 = getRandomValue()
            val e21 = getRandomValue()
            val e22 = getRandomValue()
            val matrix = MutableMatrix3(e00, e01, e02, e10, e11, e12, e20, e21, e22) { }
            it("should have e00 set") {
                assert(matrix.e00).isCloseTo(e00)
            }
            it("should have e01 set") {
                assert(matrix.e01).isCloseTo(e01)
            }
            it("should have e02 set") {
                assert(matrix.e02).isCloseTo(e02)
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
            it("should have e20 set") {
                assert(matrix.e20).isCloseTo(e20)
            }
            it("should have e21 set") {
                assert(matrix.e21).isCloseTo(e21)
            }
            it("should have e22 set") {
                assert(matrix.e22).isCloseTo(e22)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
        on("Matrix3") {
            val other = getRandomMatrix()
            val matrix = MutableMatrix3(other) { }
            it("should be equal to other") {
                assert(matrix).isEqualTo(other)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
        on("columns") {
            val column0 = getRandomVector()
            val column1 = getRandomVector()
            val column2 = getRandomVector()
            val matrix = MutableMatrix3(column0, column1, column2) { }
            it("should have first column set") {
                val column = matrix.getColumn0(MutableVector3())
                assert(column).isEqualTo(column0)
            }
            it("should have second column set") {
                val column = matrix.getColumn1(MutableVector3())
                assert(column).isEqualTo(column1)
            }
            it("should have third column set") {
                val column = matrix.getColumn2(MutableVector3())
                assert(column).isEqualTo(column2)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
    }

    describe("premade matrices") {
        on("identity") {
            val matrix = Matrix3.identity
            it("e00 should be 1") {
                assert(matrix.e00).isCloseTo(1f)
            }
            it("e01 should be 0") {
                assert(matrix.e01).isCloseTo(0f)
            }
            it("e02 should be 0") {
                assert(matrix.e02).isCloseTo(0f)
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
            it("e20 should be 0") {
                assert(matrix.e20).isCloseTo(0f)
            }
            it("e21 should be 0") {
                assert(matrix.e21).isCloseTo(0f)
            }
            it("e22 should be 1") {
                assert(matrix.e22).isCloseTo(1f)
            }
            it("should be the identity matrix") {
                assert(matrix.isIdentity).isTrue()
            }
        }
        on("zero") {
            val matrix = Matrix3.zero
            it("e00 should be 0") {
                assert(matrix.e00).isCloseTo(0f)
            }
            it("e01 should be 0") {
                assert(matrix.e01).isCloseTo(0f)
            }
            it("e02 should be 0") {
                assert(matrix.e02).isCloseTo(0f)
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
            it("e20 should be 0") {
                assert(matrix.e20).isCloseTo(0f)
            }
            it("e21 should be 0") {
                assert(matrix.e21).isCloseTo(0f)
            }
            it("e22 should be 0") {
                assert(matrix.e22).isCloseTo(0f)
            }
            it("should be the zero matrix") {
                assert(matrix.isZero).isTrue()
            }
        }
    }

    given("a matrix") {
        val matrix by memoized { getRandomMatrix() }

        on("transpose (val)") {
            val transpose = matrix.transpose
            it("should reflects the matrix on its main diagonal") {
                for(i in 0 until 3) {
                    for(j in 0 until 3) {
                        assert(matrix[i, j]).isCloseTo(transpose[j, i])
                    }
                }
            }
            it("should always be the same reference") {
                assert(transpose).isSameAs(matrix.transpose)
            }
        }

        on("inverse (val)") {
            val inverse = matrix.inverse
            it("should be the multiplicative inverse to the right") {
                assert(matrix.multiply(inverse, MutableMatrix3())).isEqualTo(Matrix3.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(matrix, MutableMatrix3())).isEqualTo(Matrix3.identity)
            }
            it("should always be the same reference") {
                assert(inverse).isSameAs(matrix.inverse)
            }
        }

        on("determinant") {
            val determinant = matrix.determinant
            it("should suffice the expansion by minors test (iterating over the 0-column)") {
                var expected = 0f
                for (i in 0 until 3) {
                    expected += matrix[i, 0] * (-1f).pow(i) * matrix.getSubmatrix(i, 0, MutableMatrix2()).determinant
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
                assert(copy).isInstanceOf(Matrix3::class)
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
                assert(copy).isInstanceOf(MutableMatrix3::class)
            }
        }

        describe("rows") {
            on("getRow") {
                val row0 = matrix.getRow(0, MutableVector3())
                val row1 = matrix.getRow(1, MutableVector3())
                val row2 = matrix.getRow(2, MutableVector3())
                it("should return the proper rows") {
                    assert(row0).isEqualTo(matrix.getRow0(MutableVector3()))
                    assert(row1).isEqualTo(matrix.getRow1(MutableVector3()))
                    assert(row2).isEqualTo(matrix.getRow2(MutableVector3()))
                }
            }
            on("getRow0") {
                val row = matrix.getRow0(MutableVector3())
                it("should return the proper row") {
                    assert(row.x).isEqualTo(matrix.e00)
                    assert(row.y).isEqualTo(matrix.e01)
                    assert(row.z).isEqualTo(matrix.e02)
                }
            }
            on("getRow1") {
                val row = matrix.getRow1(MutableVector3())
                it("should return the proper row") {
                    assert(row.x).isEqualTo(matrix.e10)
                    assert(row.y).isEqualTo(matrix.e11)
                    assert(row.z).isEqualTo(matrix.e12)
                }
            }
            on("getRow2") {
                val row = matrix.getRow2(MutableVector3())
                it("should return the proper row") {
                    assert(row.x).isEqualTo(matrix.e20)
                    assert(row.y).isEqualTo(matrix.e21)
                    assert(row.z).isEqualTo(matrix.e22)
                }
            }
        }

        describe("columns") {
            on("getColumn") {
                val column0 = matrix.getColumn(0, MutableVector3())
                val column1 = matrix.getColumn(1, MutableVector3())
                val column2 = matrix.getColumn(2, MutableVector3())
                it("should return the proper columns") {
                    assert(column0).isEqualTo(matrix.getColumn0(MutableVector3()))
                    assert(column1).isEqualTo(matrix.getColumn1(MutableVector3()))
                    assert(column2).isEqualTo(matrix.getColumn2(MutableVector3()))
                }
            }
            on("getColumn0") {
                val column = matrix.getColumn0(MutableVector3())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(matrix.e00)
                    assert(column.y).isEqualTo(matrix.e10)
                    assert(column.z).isEqualTo(matrix.e20)
                }
            }
            on("getColumn1") {
                val column = matrix.getColumn1(MutableVector3())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(matrix.e01)
                    assert(column.y).isEqualTo(matrix.e11)
                    assert(column.z).isEqualTo(matrix.e21)
                }
            }
            on("getColumn2") {
                val column = matrix.getColumn2(MutableVector3())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(matrix.e02)
                    assert(column.y).isEqualTo(matrix.e12)
                    assert(column.z).isEqualTo(matrix.e22)
                }
            }
        }

        on("transpose") {
            val transpose = matrix.transpose(MutableMatrix3())
            it("should reflects the matrix on its main diagonal") {
                for(i in 0 until 3) {
                    for(j in 0 until 3) {
                        assert(matrix[i, j]).isCloseTo(transpose[j, i])
                    }
                }
            }
        }

        on("inverse") {
            val inverse = matrix.inverse(MutableMatrix3())
            it("should be the multiplicative inverse to the right") {
                assert(matrix.multiply(inverse, MutableMatrix3())).isEqualTo(Matrix3.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(matrix, MutableMatrix3())).isEqualTo(Matrix3.identity)
            }
        }

        on("scale") {
            val scalar = getRandomValue()
            val scaled = matrix.scale(scalar, MutableMatrix3())
            it("should scale every component") {
                for(i in 0 until 3) {
                    for(j in 0 until 3) {
                        assert(scaled[i, j]).isCloseTo(matrix[i, j] * scalar)
                    }
                }
            }
        }

        on("multiply") {
            val vector = getRandomVector()
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
    }

    given("two matrices") {
        val a by memoized { getRandomMatrix() }
        val b by memoized { getRandomMatrix() }

        on("add") {
            val add = a.add(b, MutableMatrix3())
            it("should add componentwise") {
                for (i in 0 until 3) {
                    for(j in 0 until 3) {
                        assert(add[i, j]).isCloseTo(a[i, j] + b[i, j])
                    }
                }
            }
        }

        on("subtract") {
            val subtract = a.subtract(b, MutableMatrix3())
            it("should subtract componentwise") {
                for (i in 0 until 3) {
                    for(j in 0 until 3) {
                        assert(subtract[i, j]).isCloseTo(a[i, j] - b[i, j])
                    }
                }
            }
        }

        on("multiply") {
            val multiply = a.multiply(b, MutableMatrix3())
            it("should has every (i, j) entry as a linear combination of A's i-row and B's j-column") {
                for (i in 0 until 3) {
                    for(j in 0 until 3) {
                        var expected = 0f
                        for(k in 0 until 3) {
                            expected += a[i, k] * b[k, j]
                        }

                        assert(multiply[i, j]).isCloseTo(expected)
                    }
                }
            }
        }

        on("multiplyLeft") {
            val multiply = a.multiplyLeft(b, MutableMatrix3())
            it("should has every (i, j) entry as a linear combination of B's i-row and A's j-column") {
                for (i in 0 until 3) {
                    for(j in 0 until 3) {
                        var expected = 0f
                        for(k in 0 until 3) {
                            expected += b[i, k] * a[k, j]
                        }

                        assert(multiply[i, j]).isCloseTo(expected)
                    }
                }
            }
        }
    }

    given("a mutable matrix") {
        var counter = 0
        val matrix by memoized { getRandomMutable { counter++ } }

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

            on("Matrix3") {
                counter = 0
                val other = getRandomMatrix()
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
                val column0 = getRandomVector()
                val column1 = getRandomVector()
                val column2 = getRandomVector()
                matrix.set(column0, column1, column2)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have first column set") {
                    val column = matrix.getColumn0(MutableVector3())
                    assert(column).isEqualTo(column0)
                }
                it("should have second column set") {
                    val column = matrix.getColumn1(MutableVector3())
                    assert(column).isEqualTo(column1)
                }
                it("should have third column set") {
                    val column = matrix.getColumn2(MutableVector3())
                    assert(column).isEqualTo(column2)
                }
            }

            on("set") {
                counter = 0
                val e00 = getRandomValue()
                val e01 = getRandomValue()
                val e02 = getRandomValue()
                val e10 = getRandomValue()
                val e11 = getRandomValue()
                val e12 = getRandomValue()
                val e20 = getRandomValue()
                val e21 = getRandomValue()
                val e22 = getRandomValue()
                matrix.set(e00, e01, e02, e10, e11, e12, e20, e21, e22)
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
                it("should have e10 set") {
                    assert(matrix.e10).isCloseTo(e10)
                }
                it("should have e11 set") {
                    assert(matrix.e11).isCloseTo(e11)
                }
                it("should have e12 set") {
                    assert(matrix.e12).isCloseTo(e12)
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
            }

            on("operator") {
                counter = 0
                val e00 = getRandomValue()
                val e01 = getRandomValue()
                val e02 = getRandomValue()
                val e10 = getRandomValue()
                val e11 = getRandomValue()
                val e12 = getRandomValue()
                val e20 = getRandomValue()
                val e21 = getRandomValue()
                val e22 = getRandomValue()
                matrix[0, 0] = e00
                matrix[0, 1] = e01
                matrix[0, 2] = e02
                matrix[1, 0] = e10
                matrix[1, 1] = e11
                matrix[1, 2] = e12
                matrix[2, 0] = e20
                matrix[2, 1] = e21
                matrix[2, 2] = e22
                it("should have notified 9 times") {
                    assert(counter).isEqualTo(9)
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
                it("should have e10 set") {
                    assert(matrix.e10).isCloseTo(e10)
                }
                it("should have e11 set") {
                    assert(matrix.e11).isCloseTo(e11)
                }
                it("should have e12 set") {
                    assert(matrix.e12).isCloseTo(e12)
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
            }
        }

        describe("operators") {
            on("plusAssign") {
                val original = matrix.copyMutable()
                val other = getRandomMatrix()
                matrix += other
                it("should add and assign") {
                    assert(matrix).isEqualTo(original.add(other))
                }
            }
            on("minusAssign") {
                val original = matrix.copyMutable()
                val other = getRandomMatrix()
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
        }

        on("transpose (val)") {
            val transpose = matrix.transpose
            it("should reflects the matrix on its main diagonal") {
                for(i in 0 until 3) {
                    for(j in 0 until 3) {
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
                assert(matrix.multiply(inverse, MutableMatrix3())).isEqualTo(Matrix3.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(matrix, MutableMatrix3())).isEqualTo(Matrix3.identity)
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

        on("transpose") {
            val original = matrix.copyImmutable()
            matrix.transpose()
            it("should transpose and assign") {
                val expected = original.transpose(MutableMatrix3())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("inverse") {
            val original = matrix.copyImmutable()
            matrix.inverse()
            it("should inverse and assign") {
                val expected = original.inverse(MutableMatrix3())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("scale") {
            val original = matrix.copyImmutable()
            val scalar = getRandomValue()
            matrix.scale(scalar)
            it("should scale and assign") {
                val expected = original.scale(scalar, MutableMatrix3())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("add") {
            val original = matrix.copyImmutable()
            val other = getRandomMatrix()
            matrix.add(other)
            it("should add and assign") {
                val expected = original.add(other, MutableMatrix3())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("subtract") {
            val original = matrix.copyImmutable()
            val other = getRandomMatrix()
            matrix.subtract(other)
            it("should subtract and assign") {
                val expected = original.subtract(other, MutableMatrix3())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiply") {
            val original = matrix.copyImmutable()
            val other = getRandomMatrix()
            matrix.multiply(other)
            it("should multiply and assign") {
                val expected = original.multiply(other, MutableMatrix3())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiplyLeft") {
            val original = matrix.copyImmutable()
            val other = getRandomMatrix()
            matrix.multiplyLeft(other)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(other, MutableMatrix3())

                assert(matrix).isEqualTo(expected)
            }
        }
    }
})

private fun getRandomValue() = random(-100f, 100f)
private fun getRandomVector() = Vector3(getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomMatrix() = Matrix3(
        getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue()
)
private fun getRandomMutable(observer: ((Matrix3) -> Unit)) = MutableMatrix3(
        getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(),
        observer
)
private fun MutableMatrix3.randomize() = this.set(
        getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue()
)