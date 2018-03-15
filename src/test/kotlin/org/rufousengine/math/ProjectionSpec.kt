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

object ProjectionSpec: Spek({
    describe("immutable constructors") {
        on("empty") {
            val matrix = Projection()
            it("should be the identity matrix") {
                assert(matrix.equals(Matrix4.identity)).isTrue()
            }
        }
        on("Projection") {
            val other = getRandomProjection()
            val matrix = Projection(other)
            it("should be equal to other") {
                assert(matrix).isEqualTo(other)
            }
        }
    }

    describe("mutable constructors") {
        on("empty") {
            val matrix = MutableProjection { }
            it("should be the identity matrix") {
                assert(matrix.equals(Matrix4.identity)).isTrue()
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
        on("Projection") {
            val other = getRandomProjection()
            val matrix = MutableProjection(other) { }
            it("should be equal to other") {
                assert(matrix).isEqualTo(other)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
    }

    given("a matrix") {
        val matrix by memoized { getRandomProjection() }

        on("transpose (val)") {
            val transpose = matrix.transpose
            it("should reflects the matrix on its main diagonal") {
                for(i in 0 until 4) {
                    for(j in 0 until 4) {
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
                assert(matrix.multiply(inverse, MutableProjection())).isEqualTo(Matrix4.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(matrix, MutableProjection())).isEqualTo(Matrix4.identity)
            }
            it("should always be the same reference") {
                assert(inverse).isSameAs(matrix.inverse)
            }
        }

        on("determinant") {
            val determinant = matrix.determinant
            it("should be the same as Matrix4::determinant") {
                val expected = Matrix4(matrix).determinant

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
                assert(copy).isInstanceOf(Projection::class)
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
                assert(copy).isInstanceOf(MutableProjection::class)
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
                    assert(row.y).isEqualTo(0f)
                    assert(row.z).isEqualTo(0f)
                    assert(row.w).isEqualTo(0f)
                }
            }
            on("getRow1") {
                val row = matrix.getRow1(MutableVector4())
                it("should return the proper row") {
                    assert(row.x).isEqualTo(0f)
                    assert(row.y).isEqualTo(matrix.e11)
                    assert(row.z).isEqualTo(0f)
                    assert(row.w).isEqualTo(0f)
                }
            }
            on("getRow2") {
                val row = matrix.getRow2(MutableVector4())
                it("should return the proper row") {
                    assert(row.x).isEqualTo(0f)
                    assert(row.y).isEqualTo(0f)
                    assert(row.z).isEqualTo(matrix.e22)
                    assert(row.w).isEqualTo(matrix.e23)
                }
            }
            on("getRow3") {
                val row = matrix.getRow3(MutableVector4())
                it("should return the proper row") {
                    assert(row.x).isEqualTo(0f)
                    assert(row.y).isEqualTo(0f)
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
                    assert(column.y).isEqualTo(0f)
                    assert(column.z).isEqualTo(0f)
                    assert(column.w).isEqualTo(0f)
                }
            }
            on("getColumn1") {
                val column = matrix.getColumn1(MutableVector4())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(0f)
                    assert(column.y).isEqualTo(matrix.e11)
                    assert(column.z).isEqualTo(0f)
                    assert(column.w).isEqualTo(0f)
                }
            }
            on("getColumn2") {
                val column = matrix.getColumn2(MutableVector4())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(0f)
                    assert(column.y).isEqualTo(0f)
                    assert(column.z).isEqualTo(matrix.e22)
                    assert(column.w).isEqualTo(matrix.e32)
                }
            }
            on("getColumn3") {
                val column = matrix.getColumn3(MutableVector4())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(0f)
                    assert(column.y).isEqualTo(0f)
                    assert(column.z).isEqualTo(matrix.e23)
                    assert(column.w).isEqualTo(matrix.e33)
                }
            }
        }

        on("transpose") {
            val transpose = matrix.transpose(MutableProjection())
            it("should reflects the matrix on its main diagonal") {
                for(i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(matrix[i, j]).isCloseTo(transpose[j, i])
                    }
                }
            }
        }

        on("inverse") {
            val inverse = matrix.inverse(MutableProjection())
            it("should be the multiplicative inverse to the right") {
                assert(matrix.multiply(inverse, MutableProjection())).isEqualTo(Matrix4.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(matrix, MutableProjection())).isEqualTo(Matrix4.identity)
            }
        }

        on("scale") {
            val scalar = getRandomValue()
            val scaled = matrix.scale(scalar, MutableProjection())
            it("should scale every component") {
                for(i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(scaled[i, j]).isCloseTo(matrix[i, j] * scalar)
                    }
                }
            }
        }

        on("multiply (Matrix4)") {
            val other = getRandomMatrix()
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
            val other = getRandomMatrix()
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
            val new = matrix.multiply(vector, MutableVector4())
            it("should has every (i, j) entry as a linear combination of matrix' i-row and vector's components") {
                for (i in 0 until 4) {
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
    }

    given("two matrices") {
        val a by memoized { getRandomProjection() }
        val b by memoized { getRandomProjection() }

        on("add") {
            val add = a.add(b, MutableProjection())
            it("should add componentwise") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(add[i, j]).isCloseTo(a[i, j] + b[i, j])
                    }
                }
            }
        }

        on("subtract") {
            val subtract = a.subtract(b, MutableProjection())
            it("should subtract componentwise") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        assert(subtract[i, j]).isCloseTo(a[i, j] - b[i, j])
                    }
                }
            }
        }

        on("multiply") {
            val multiply = a.multiply(b, MutableProjection())
            it("should has every (i, j) entry as a linear combination of A's i-row and B's j-column") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        var expected = 0f
                        for(k in 0 until 4) {
                            expected += a[i, k] * b[k, j]
                        }

                        assert(multiply[i, j]).isCloseTo(expected)
                    }
                }
            }
        }

        on("multiplyLeft") {
            val multiply = a.multiplyLeft(b, MutableProjection())
            it("should has every (i, j) entry as a linear combination of B's i-row and A's j-column") {
                for (i in 0 until 4) {
                    for(j in 0 until 4) {
                        var expected = 0f
                        for(k in 0 until 4) {
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

            on("set") {
                counter = 0
                val e00 = getRandomValue()
                val e11 = getRandomValue()
                val e22 = getRandomValue()
                val e23 = getRandomValue()
                val e32 = getRandomValue()
                val e33 = getRandomValue()
                matrix.set(e00, e11, e22, e23, e32, e33)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have e00 set") {
                    assert(matrix.e00).isCloseTo(e00)
                }
                it("should have e11 set") {
                    assert(matrix.e11).isCloseTo(e11)
                }
                it("should have e22 set") {
                    assert(matrix.e22).isCloseTo(e22)
                }
                it("should have e23 set") {
                    assert(matrix.e23).isCloseTo(e23)
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
            on("plusAssign") {
                val original = matrix.copyMutable()
                val other = getRandomProjection()
                matrix += other
                it("should add and assign") {
                    assert(matrix).isEqualTo(original.add(other))
                }
            }
            on("minusAssign") {
                val original = matrix.copyMutable()
                val other = getRandomProjection()
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
                assert(matrix.multiply(inverse, MutableProjection())).isEqualTo(Matrix4.identity)
            }
            it("should be the multiplicative inverse from the left") {
                assert(inverse.multiply(matrix, MutableProjection())).isEqualTo(Matrix4.identity)
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
                val expected = original.transpose(MutableProjection())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("inverse") {
            val original = matrix.copyImmutable()
            matrix.inverse()
            it("should inverse and assign") {
                val expected = original.inverse(MutableProjection())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("scale") {
            val original = matrix.copyImmutable()
            val scalar = getRandomValue()
            matrix.scale(scalar)
            it("should scale and assign") {
                val expected = original.scale(scalar, MutableProjection())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("add") {
            val original = matrix.copyImmutable()
            val other = getRandomProjection()
            matrix.add(other)
            it("should add and assign") {
                val expected = original.add(other, MutableProjection())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("subtract") {
            val original = matrix.copyImmutable()
            val other = getRandomProjection()
            matrix.subtract(other)
            it("should subtract and assign") {
                val expected = original.subtract(other, MutableProjection())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiply") {
            val original = matrix.copyImmutable()
            val other = getRandomProjection()
            matrix.multiply(other)
            it("should multiply and assign") {
                val expected = original.multiply(other, MutableProjection())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiplyLeft") {
            val original = matrix.copyImmutable()
            val other = getRandomProjection()
            matrix.multiplyLeft(other)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(other, MutableProjection())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("setOrthographic") {
            val width = getPositiveValue()
            val height = getPositiveValue()
            val near = getPositiveValue()
            val far = near * getPositiveValue()

            matrix.setOrthographic(width, height, near, far)
            it("should give the same results as Matrix4::setOrthographic") {
                val top = height * 0.5f
                val bottom = -top
                val right = width * 0.5f
                val left = -right
                val expected = MutableMatrix4().setOrthographic(top, bottom, right, left, near, far)

                assert(matrix).isEqualTo(expected)
            }
        }

        on("setPerspective") {
            val fieldOfView = random(30f, 160f)
            val aspectRatio = getPositiveValue()
            val near = getPositiveValue()
            val far = near * getPositiveValue()

            matrix.setPerspective(fieldOfView, aspectRatio, near, far)
            it("should give the same results as Matrix4::setPerspective") {
                val top = tan(fieldOfView.toRadians() / 2f) * near
                val bottom = -top
                val right = top * aspectRatio
                val left = -right
                val expected = MutableMatrix4().setPerspective(top, bottom, right, left, near, far)

                assert(matrix).isEqualTo(expected)
            }
        }
    }
})

private fun getRandomValue() = random(-100f, 100f)
private fun getPositiveValue() = random(1f, 100f)
private fun getRandomVector3() = Vector3(getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomVector4() = Vector4(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue())
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
private fun getRandomMatrix() = Matrix4(
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()
)
private fun getRandomMutable(observer: ((Projection) -> Unit)) : MutableProjection {
    val p = MutableProjection()
    p.set(
            getRandomValue(),
            getRandomValue(),
            getRandomValue(), getRandomValue(),
            getRandomValue(), getRandomValue()
    )

    return MutableProjection(p, observer)
}
private fun MutableProjection.randomize() = this.set(
        getRandomValue(),
        getRandomValue(),
        getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue()
)