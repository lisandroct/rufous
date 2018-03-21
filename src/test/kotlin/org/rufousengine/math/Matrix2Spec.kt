package org.rufousengine.math

import assertk.assert
import assertk.assertions.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.rufousengine.assertions.isCloseTo

object Matrix2Spec: Spek({
    describe("immutable constructors") {
        on("empty") {
            val matrix = Matrix2()
            it("should be the identity matrix") {
                assert(matrix).isEqualTo(Matrix2.identity)
            }
        }
        on("primary") {
            val e00 = getRandomValue()
            val e01 = getRandomValue()
            val e10 = getRandomValue()
            val e11 = getRandomValue()
            val matrix = Matrix2(e00, e01, e10, e11)
            it("should have e00 set") {
                assert(matrix.e00).isCloseTo(e00)
            }
            it("should have e01 set") {
                assert(matrix.e01).isCloseTo(e01)
            }
            it("should have e10 set") {
                assert(matrix.e10).isCloseTo(e10)
            }
            it("should have e11 set") {
                assert(matrix.e11).isCloseTo(e11)
            }
        }
        on("Matrix2") {
            val other = getRandomMatrix2()
            val matrix = Matrix2(other)
            it("should be equal to other") {
                assert(matrix).isEqualTo(other)
            }
        }
        on("columns") {
            val column0 = getRandomVector2()
            val column1 = getRandomVector2()
            val matrix = Matrix2(column0, column1)
            it("should have first column set") {
                val column = matrix.getColumn0(MutableVector2())
                assert(column).isEqualTo(column0)
            }
            it("should have second column set") {
                val column = matrix.getColumn1(MutableVector2())
                assert(column).isEqualTo(column1)
            }
        }
    }

    describe("mutable constructors") {
        on("empty") {
            val matrix = MutableMatrix2 { }
            it("should be the identity matrix") {
                assert(matrix).isEqualTo(Matrix2.identity)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
        on("primary") {
            val e00 = getRandomValue()
            val e01 = getRandomValue()
            val e10 = getRandomValue()
            val e11 = getRandomValue()
            val matrix = MutableMatrix2(e00, e01, e10, e11) { }
            it("should have e00 set") {
                assert(matrix.e00).isCloseTo(e00)
            }
            it("should have e01 set") {
                assert(matrix.e01).isCloseTo(e01)
            }
            it("should have e10 set") {
                assert(matrix.e10).isCloseTo(e10)
            }
            it("should have e11 set") {
                assert(matrix.e11).isCloseTo(e11)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
        on("Matrix2") {
            val other = getRandomMatrix2()
            val matrix = MutableMatrix2(other) { }
            it("should be equal to other") {
                assert(matrix).isEqualTo(other)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
        on("columns") {
            val column0 = getRandomVector2()
            val column1 = getRandomVector2()
            val matrix = MutableMatrix2(column0, column1) { }
            it("should have first column set") {
                val column = matrix.getColumn0(MutableVector2())
                assert(column).isEqualTo(column0)
            }
            it("should have second column set") {
                val column = matrix.getColumn1(MutableVector2())
                assert(column).isEqualTo(column1)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
    }

    describe("premade matrices") {
        on("identity") {
            val matrix = Matrix2.identity
            it("e00 should be 1") {
                assert(matrix.e00).isCloseTo(1f)
            }
            it("e01 should be 0") {
                assert(matrix.e01).isCloseTo(0f)
            }
            it("e10 should be 0") {
                assert(matrix.e10).isCloseTo(0f)
            }
            it("e11 should be 1") {
                assert(matrix.e11).isCloseTo(1f)
            }
            it("should be the identity matrix") {
                assert(matrix.isIdentity).isTrue()
            }
        }
        on("zero") {
            val matrix = Matrix2.zero
            it("e00 should be 0") {
                assert(matrix.e00).isCloseTo(0f)
            }
            it("e01 should be 0") {
                assert(matrix.e01).isCloseTo(0f)
            }
            it("e10 should be 0") {
                assert(matrix.e10).isCloseTo(0f)
            }
            it("e11 should be 0") {
                assert(matrix.e11).isCloseTo(0f)
            }
            it("should be the zero matrix") {
                assert(matrix.isZero).isTrue()
            }
        }
    }

    given("a matrix") {
        val matrix by memoized { getRandomMatrix2() }

        on("transpose (val)") {
            val transpose = matrix.transpose
            it("should reflects the matrix on its main diagonal") {
                for(i in 0 until 2) {
                    for(j in 0 until 2) {
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
                assert(matrix.multiply(inverse, MutableMatrix2())).isEqualTo(Matrix2.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(matrix, MutableMatrix2())).isEqualTo(Matrix2.identity)
            }
            it("should always be the same reference") {
                assert(inverse).isSameAs(matrix.inverse)
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
                assert(copy).isInstanceOf(Matrix2::class)
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
                assert(copy).isInstanceOf(MutableMatrix2::class)
            }
        }

        describe("rows") {
            on("getRow") {
                val row0 = matrix.getRow(0, MutableVector2())
                val row1 = matrix.getRow(1, MutableVector2())
                it("should return the proper rows") {
                    assert(row0).isEqualTo(matrix.getRow0(MutableVector2()))
                    assert(row1).isEqualTo(matrix.getRow1(MutableVector2()))
                }
            }
            on("getRow0") {
                val row = matrix.getRow0(MutableVector2())
                it("should return the proper row") {
                    assert(row.x).isEqualTo(matrix.e00)
                    assert(row.y).isEqualTo(matrix.e01)
                }
            }
            on("getRow1") {
                val row = matrix.getRow1(MutableVector2())
                it("should return the proper row") {
                    assert(row.x).isEqualTo(matrix.e10)
                    assert(row.y).isEqualTo(matrix.e11)
                }
            }
        }

        describe("columns") {
            on("getColumn") {
                val column0 = matrix.getColumn(0, MutableVector2())
                val column1 = matrix.getColumn(1, MutableVector2())
                it("should return the proper columns") {
                    assert(column0).isEqualTo(matrix.getColumn0(MutableVector2()))
                    assert(column1).isEqualTo(matrix.getColumn1(MutableVector2()))
                }
            }
            on("getColumn0") {
                val column = matrix.getColumn0(MutableVector2())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(matrix.e00)
                    assert(column.y).isEqualTo(matrix.e10)
                }
            }
            on("getColumn1") {
                val column = matrix.getColumn1(MutableVector2())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(matrix.e01)
                    assert(column.y).isEqualTo(matrix.e11)
                }
            }
        }

        on("transpose") {
            val transpose = matrix.transpose(MutableMatrix2())
            it("should reflects the matrix on its main diagonal") {
                for(i in 0 until 2) {
                    for(j in 0 until 2) {
                        assert(matrix[i, j]).isCloseTo(transpose[j, i])
                    }
                }
            }
        }

        on("inverse") {
            val inverse = matrix.inverse(MutableMatrix2())
            it("should be the multiplicative inverse to the right") {
                assert(matrix.multiply(inverse, MutableMatrix2())).isEqualTo(Matrix2.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(matrix, MutableMatrix2())).isEqualTo(Matrix2.identity)
            }
        }

        on("scale") {
            val scalar = getRandomValue()
            val scaled = matrix.scale(scalar, MutableMatrix2())
            it("should scale every component") {
                for(i in 0 until 2) {
                    for(j in 0 until 2) {
                        assert(scaled[i, j]).isCloseTo(matrix[i, j] * scalar)
                    }
                }
            }
        }

        on("add") {
            val other = getRandomMatrix2()
            val add = matrix.add(other, MutableMatrix2())
            it("should add componentwise") {
                for (i in 0 until 2) {
                    for(j in 0 until 2) {
                        assert(add[i, j]).isCloseTo(matrix[i, j] + other[i, j])
                    }
                }
            }
        }

        on("subtract") {
            val other = getRandomMatrix2()
            val subtract = matrix.subtract(other, MutableMatrix2())
            it("should subtract componentwise") {
                for (i in 0 until 2) {
                    for(j in 0 until 2) {
                        assert(subtract[i, j]).isCloseTo(matrix[i, j] - other[i, j])
                    }
                }
            }
        }

        on("multiply (Matrix2)") {
            val other = getRandomMatrix2()
            val multiply = matrix.multiply(other, MutableMatrix2())
            it("should has every (i, j) entry as a linear combination of A's i-row and B's j-column") {
                for (i in 0 until 2) {
                    for(j in 0 until 2) {
                        var expected = 0f
                        for(k in 0 until 2) {
                            expected += matrix[i, k] * other[k, j]
                        }

                        assert(multiply[i, j]).isCloseTo(expected)
                    }
                }
            }
        }

        on("multiplyLeft (Matrix2)") {
            val other = getRandomMatrix2()
            val multiply = matrix.multiplyLeft(other, MutableMatrix2())
            it("should has every (i, j) entry as a linear combination of B's i-row and A's j-column") {
                for (i in 0 until 2) {
                    for(j in 0 until 2) {
                        var expected = 0f
                        for(k in 0 until 2) {
                            expected += other[i, k] * matrix[k, j]
                        }

                        assert(multiply[i, j]).isCloseTo(expected)
                    }
                }
            }
        }

        on("multiply (Vector2)") {
            val vector = getRandomVector2()
            val new = matrix.multiply(vector, MutableVector2())
            it("should has every (i, j) entry as a linear combination of matrix' i-row and vector's components") {
                for (i in 0 until 2) {
                    var expected = 0f
                    for(k in 0 until 2) {
                        expected += matrix[i, k] * vector[k]
                    }

                    assert(new[i]).isCloseTo(expected)
                }
            }
        }
    }

    given("a mutable matrix") {
        var counter = 0
        val matrix by memoized { getRandomMutableMatrix2 { counter++ } }

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

            on("Matrix2") {
                counter = 0
                val other = getRandomMatrix2()
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
                val column0 = getRandomVector2()
                val column1 = getRandomVector2()
                matrix.set(column0, column1)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have first column set") {
                    val column = matrix.getColumn0(MutableVector2())
                    assert(column).isEqualTo(column0)
                }
                it("should have second column set") {
                    val column = matrix.getColumn1(MutableVector2())
                    assert(column).isEqualTo(column1)
                }
            }

            on("set") {
                counter = 0
                val e00 = getRandomValue()
                val e01 = getRandomValue()
                val e10 = getRandomValue()
                val e11 = getRandomValue()
                matrix.set(e00, e01, e10, e11)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have e00 set") {
                    assert(matrix.e00).isCloseTo(e00)
                }
                it("should have e01 set") {
                    assert(matrix.e01).isCloseTo(e01)
                }
                it("should have e10 set") {
                    assert(matrix.e10).isCloseTo(e10)
                }
                it("should have e11 set") {
                    assert(matrix.e11).isCloseTo(e11)
                }
            }

            on("operator") {
                counter = 0
                val e00 = getRandomValue()
                val e01 = getRandomValue()
                val e10 = getRandomValue()
                val e11 = getRandomValue()
                matrix[0, 0] = e00
                matrix[0, 1] = e01
                matrix[1, 0] = e10
                matrix[1, 1] = e11
                it("should have notified 4 times") {
                    assert(counter).isEqualTo(4)
                }
                it("should have e00 set") {
                    assert(matrix.e00).isCloseTo(e00)
                }
                it("should have e01 set") {
                    assert(matrix.e01).isCloseTo(e01)
                }
                it("should have e10 set") {
                    assert(matrix.e10).isCloseTo(e10)
                }
                it("should have e11 set") {
                    assert(matrix.e11).isCloseTo(e11)
                }
            }
        }

        describe("operators") {
            on("plusAssign") {
                val original = matrix.copyMutable()
                val other = getRandomMatrix2()
                matrix += other
                it("should add and assign") {
                    assert(matrix).isEqualTo(original.add(other))
                }
            }
            on("minusAssign") {
                val original = matrix.copyMutable()
                val other = getRandomMatrix2()
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
            on("timesAssign (Matrix2)") {
                val original = matrix.copyMutable()
                val other = getRandomMatrix2()
                matrix *= other
                it("should scale and assign") {
                    assert(matrix).isEqualTo(original.multiply(other))
                }
            }
        }

        on("transpose (val)") {
            val transpose = matrix.transpose
            it("should reflects the matrix on its main diagonal") {
                for(i in 0 until 2) {
                    for(j in 0 until 2) {
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
                assert(matrix.multiply(inverse, MutableMatrix2())).isEqualTo(Matrix2.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(matrix, MutableMatrix2())).isEqualTo(Matrix2.identity)
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
                assert(matrix).isEqualTo(Matrix2.identity)
            }
        }

        on("zero") {
            matrix.zero()
            it("should be the zero matrix") {
                assert(matrix).isEqualTo(Matrix2.zero)
            }
        }

        on("transpose") {
            val original = matrix.copyImmutable()
            matrix.transpose()
            it("should transpose and assign") {
                val expected = original.transpose(MutableMatrix2())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("inverse") {
            val original = matrix.copyImmutable()
            matrix.inverse()
            it("should inverse and assign") {
                val expected = original.inverse(MutableMatrix2())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("scale") {
            val original = matrix.copyImmutable()
            val scalar = getRandomValue()
            matrix.scale(scalar)
            it("should scale and assign") {
                val expected = original.scale(scalar, MutableMatrix2())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("add") {
            val original = matrix.copyImmutable()
            val other = getRandomMatrix2()
            matrix.add(other)
            it("should add and assign") {
                val expected = original.add(other, MutableMatrix2())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("subtract") {
            val original = matrix.copyImmutable()
            val other = getRandomMatrix2()
            matrix.subtract(other)
            it("should subtract and assign") {
                val expected = original.subtract(other, MutableMatrix2())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiply") {
            val original = matrix.copyImmutable()
            val other = getRandomMatrix2()
            matrix.multiply(other)
            it("should multiply and assign") {
                val expected = original.multiply(other, MutableMatrix2())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiplyLeft") {
            val original = matrix.copyImmutable()
            val other = getRandomMatrix2()
            matrix.multiplyLeft(other)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(other, MutableMatrix2())

                assert(matrix).isEqualTo(expected)
            }
        }
    }
})

private fun getRandomValue() = random(-100f, 100f)
private fun getRandomVector2() = Vector2(getRandomValue(), getRandomValue())
private fun getRandomMatrix2() = Matrix2(
        getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue()
)
private fun getRandomMutableMatrix2(observer: ((Matrix2) -> Unit)) = MutableMatrix2(
        getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(),
        observer
)
private fun MutableMatrix2.randomize() = this.set(
        getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue()
)