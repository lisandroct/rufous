package org.rufousengine.math

import assertk.assert
import assertk.assertions.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.rufousengine.assertions.isCloseTo

object TransformationSpec: Spek({
    describe("immutable constructors") {
        on("empty") {
            val matrix = Transformation()
            it("should be the identity matrix") {
                assert(matrix).isEqualTo(Transformation.identity)
            }
        }
        on("Transformation") {
            val other = getRandomTransformation()
            val matrix = Transformation(other)
            it("should be equal to other") {
                assert(matrix).isEqualTo(other)
            }
        }
    }

    describe("mutable constructors") {
        on("empty") {
            val matrix = MutableTransformation { }
            it("should be the identity matrix") {
                assert(matrix).isEqualTo(Transformation.identity)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
        on("Transformation") {
            val other = getRandomTransformation()
            val matrix = MutableTransformation(other) { }
            it("should be equal to other") {
                assert(matrix).isEqualTo(other)
            }
            it("should have the observer set") {
                assert(matrix.observer).isNotNull()
            }
        }
    }

    describe("premade matrices") {
        on("identity") {
            val matrix = Transformation.identity
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
            it("should be the identity matrix") {
                assert(matrix.isIdentity).isTrue()
            }
        }
    }

    given("a matrix") {
        val matrix by memoized { getRandomTransformation() }

        on("transpose (val)") {
            val transpose = matrix.transpose
            it("should be the same as Matrix4::transpose") {
                val expected = Matrix4(matrix).transpose

                assert(transpose).isEqualTo(expected)
            }
        }

        on("inverse (val)") {
            val inverse = matrix.inverse
            it("should be the same as Matrix4::inverse") {
                val expected = Matrix4(matrix).inverse

                assert(inverse).isEqualTo(expected)
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
                assert(copy).isInstanceOf(Transformation::class)
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
                assert(copy).isInstanceOf(MutableTransformation::class)
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
                    assert(row.x).isEqualTo(0f)
                    assert(row.y).isEqualTo(0f)
                    assert(row.z).isEqualTo(0f)
                    assert(row.w).isEqualTo(1f)
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
                    assert(column.w).isEqualTo(0f)
                }
            }
            on("getColumn1") {
                val column = matrix.getColumn1(MutableVector4())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(matrix.e01)
                    assert(column.y).isEqualTo(matrix.e11)
                    assert(column.z).isEqualTo(matrix.e21)
                    assert(column.w).isEqualTo(0f)
                }
            }
            on("getColumn2") {
                val column = matrix.getColumn2(MutableVector4())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(matrix.e02)
                    assert(column.y).isEqualTo(matrix.e12)
                    assert(column.z).isEqualTo(matrix.e22)
                    assert(column.w).isEqualTo(0f)
                }
            }
            on("getColumn3") {
                val column = matrix.getColumn3(MutableVector4())
                it("should return the proper column") {
                    assert(column.x).isEqualTo(matrix.e03)
                    assert(column.y).isEqualTo(matrix.e13)
                    assert(column.z).isEqualTo(matrix.e23)
                    assert(column.w).isEqualTo(1f)
                }
            }
        }

        on("transpose") {
            val transpose = matrix.transpose(MutableMatrix4())
            it("should be the same as Matrix4::transpose") {
                val expected = Matrix4(matrix).transpose(MutableMatrix4())

                assert(transpose).isEqualTo(expected)
            }
        }

        on("inverse") {
            val inverse = matrix.inverse(MutableTransformation())
            it("should be the same as Matrix4::inverse") {
                val expected = Matrix4(matrix).inverse(MutableMatrix4())

                assert(inverse).isEqualTo(expected)
            }
        }

        on("scale") {
            val scalar = getRandomValue()
            val scaled = matrix.scale(scalar, MutableMatrix4())
            it("should be the same as Matrix4::scale") {
                val expected = Matrix4(matrix).scale(scalar, MutableMatrix4())

                assert(scaled).isEqualTo(expected)
            }
        }

        on("add (Projection)") {
            val other = getRandomProjection()
            val add = matrix.add(other, MutableMatrix4())
            it("should be the same as Matrix4::add") {
                val expected = Matrix4(matrix).add(other, MutableMatrix4())

                assert(add).isEqualTo(expected)
            }
        }

        on("add (Transformation)") {
            val other = getRandomTransformation()
            val add = matrix.add(other, MutableMatrix4())
            it("should be the same as Matrix4::add") {
                val expected = Matrix4(matrix).add(other, MutableMatrix4())

                assert(add).isEqualTo(expected)
            }
        }

        on("add (Matrix4)") {
            val other = getRandomMatrix4()
            val add = matrix.add(other, MutableMatrix4())
            it("should be the same as Matrix4::add") {
                val expected = Matrix4(matrix).add(other, MutableMatrix4())

                assert(add).isEqualTo(expected)
            }
        }

        on("subtract (Projection)") {
            val other = getRandomProjection()
            val subtract = matrix.subtract(other, MutableMatrix4())
            it("should be the same as Matrix4::subtract") {
                val expected = Matrix4(matrix).subtract(other, MutableMatrix4())

                assert(subtract).isEqualTo(expected)
            }
        }

        on("subtract (Transformation)") {
            val other = getRandomTransformation()
            val subtract = matrix.subtract(other, MutableMatrix4())
            it("should be the same as Matrix4::subtract") {
                val expected = Matrix4(matrix).subtract(other, MutableMatrix4())

                assert(subtract).isEqualTo(expected)
            }
        }

        on("subtract (Matrix4)") {
            val other = getRandomMatrix4()
            val subtract = matrix.subtract(other, MutableMatrix4())
            it("should be the same as Matrix4::subtract") {
                val expected = Matrix4(matrix).subtract(other, MutableMatrix4())

                assert(subtract).isEqualTo(expected)
            }
        }

        on("multiply (Projection)") {
            val other = getRandomProjection()
            val multiply = matrix.multiply(other, MutableMatrix4())
            it("should be the same as Matrix4::multiply") {
                val expected = Matrix4(matrix).multiply(other, MutableMatrix4())

                assert(multiply).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Projection)") {
            val other = getRandomProjection()
            val multiply = matrix.multiplyLeft(other, MutableMatrix4())
            it("should be the same as Matrix4::multiplyLeft") {
                val expected = Matrix4(matrix).multiplyLeft(other, MutableMatrix4())

                assert(multiply).isEqualTo(expected)
            }
        }

        on("multiply (Transformation)") {
            val other = getRandomTransformation()
            val multiply = matrix.multiply(other, MutableTransformation())
            it("should be the same as Matrix4::multiply") {
                val expected = Matrix4(matrix).multiply(other, MutableMatrix4())

                assert(multiply).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Transformation)") {
            val other = getRandomTransformation()
            val multiply = matrix.multiplyLeft(other, MutableTransformation())
            it("should be the same as Matrix4::multiplyLeft") {
                val expected = Matrix4(matrix).multiplyLeft(other, MutableMatrix4())

                assert(multiply).isEqualTo(expected)
            }
        }

        on("multiply (Matrix4)") {
            val other = getRandomMatrix4()
            val multiply = matrix.multiply(other, MutableMatrix4())
            it("should be the same as Matrix4::multiply") {
                val expected = Matrix4(matrix).multiply(other, MutableMatrix4())

                assert(multiply).isEqualTo(expected)
            }
        }

        on("multiplyLeft (Matrix4)") {
            val other = getRandomMatrix4()
            val multiply = matrix.multiplyLeft(other, MutableMatrix4())
            it("should be the same as Matrix4::multiplyLeft") {
                val expected = Matrix4(matrix).multiplyLeft(other, MutableMatrix4())

                assert(multiply).isEqualTo(expected)
            }
        }

        on("multiply (Vector4)") {
            val vector = getRandomVector4()
            val new = matrix.multiply(vector, MutableVector4())
            it("should be the same as Matrix4::multiply") {
                val expected = Matrix4(matrix).multiply(vector, MutableVector4())

                assert(new).isEqualTo(expected)
            }
        }

        on("multiply (Vector3)") {
            val vector = getRandomVector3()
            val new = matrix.multiply(vector, MutableVector3())
            it("should be the same as Matrix4::multiply") {
                val expected = Matrix4(matrix).multiply(vector, MutableVector3())

                assert(new).isEqualTo(expected)
            }
        }

        on("multiply (Point)") {
            val point = getRandomPoint()
            val new = matrix.multiply(point, MutablePoint())
            it("should be the same as Matrix4::multiply") {
                val expected = Matrix4(matrix).multiply(point, MutablePoint())

                assert(new).isEqualTo(expected)
            }
        }
    }
    
    given("a mutable matrix") {
        var counter = 0
        val matrix by memoized { getRandomMutableTransformation { counter++ } }

        describe("seters") {
            on("Transformation") {
                counter = 0
                val other = getRandomTransformation()
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
                matrix.set(e00, e01, e02, e03, e10, e11, e12, e13, e20, e21, e22, e23)
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
            }
        }

        describe("operators") {
            on("timesAssign (Transformation)") {
                val original = matrix.copyMutable()
                val other = getRandomTransformation()
                matrix *= other
                it("should scale and assign") {
                    assert(matrix).isEqualTo(original.multiply(other))
                }
            }
        }

        on("transpose (val)") {
            val transpose = matrix.transpose
            it("should be the same as Matrix4::transpose") {
                val expected = Matrix4(matrix).transpose

                assert(transpose).isEqualTo(expected)
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
            it("should be the same as Matrix4::inverse") {
                val expected = Matrix4(matrix).inverse

                assert(inverse).isEqualTo(expected)
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
                assert(matrix).isEqualTo(Transformation.identity)
            }
        }

        on("inverse") {
            val original = matrix.copyImmutable()
            matrix.inverse()
            it("should inverse and assign") {
                val expected = original.inverse(MutableTransformation())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiply") {
            val original = matrix.copyImmutable()
            val other = getRandomTransformation()
            matrix.multiply(other)
            it("should multiply and assign") {
                val expected = original.multiply(other, MutableTransformation())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("multiplyLeft") {
            val original = matrix.copyImmutable()
            val other = getRandomTransformation()
            matrix.multiplyLeft(other)
            it("should multiplyLeft and assign") {
                val expected = original.multiplyLeft(other, MutableTransformation())

                assert(matrix).isEqualTo(expected)
            }
        }

        on("makeRotationX") {
            val angle = getRandomValue()
            matrix.makeRotationX(angle)
            it("should give the same results as Matrix4::makeRotationZ") {
                val expected = MutableMatrix4().makeRotationX(angle)

                assert(matrix).isEqualTo(expected)
            }
        }

        on("makeRotationY") {
            val angle = getRandomValue()
            matrix.makeRotationY(angle)
            it("should give the same results as Matrix4::makeRotationZ") {
                val expected = MutableMatrix4().makeRotationY(angle)

                assert(matrix).isEqualTo(expected)
            }
        }

        on("makeRotationZ") {
            val angle = getRandomValue()
            matrix.makeRotationZ(angle)
            it("should give the same results as Matrix4::makeRotationZ") {
                val expected = MutableMatrix4().makeRotationZ(angle)

                assert(matrix).isEqualTo(expected)
            }
        }

        on("makeRotationSafe") {
            val angle = getRandomValue()
            val axis = getRandomVector3()
            matrix.makeRotationSafe(angle, axis)
            it("should give the same results as Matrix4::makeRotationSafe") {
                val expected = MutableMatrix4().makeRotationSafe(angle, axis)

                assert(matrix).isEqualTo(expected)
            }
        }

        on("makeRotation") {
            val angle = getRandomValue()
            val axis = getRandomVector3().normalize(MutableVector3())
            matrix.makeRotation(angle, axis)
            it("should give the same results as Matrix4::makeRotation") {
                val expected = MutableMatrix4().makeRotation(angle, axis)

                assert(matrix).isEqualTo(expected)
            }
        }

        on("makeScale (uniform)") {
            val factor = getRandomValue()
            matrix.makeScale(factor)
            it("should give the same results as Matrix4::makeScale") {
                val expected = MutableMatrix4().makeScale(factor)

                assert(matrix).isEqualTo(expected)
            }
        }

        on("makeScale (nonuniform)") {
            val factorX = getRandomValue()
            val factorY = getRandomValue()
            val factorZ = getRandomValue()
            matrix.makeScale(factorX, factorY, factorZ)
            it("should give the same results as Matrix4::makeScale") {
                val expected = MutableMatrix4().makeScale(factorX, factorY, factorZ)

                assert(matrix).isEqualTo(expected)
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
private fun getRandomMutableTransformation(observer: ((Transformation) -> Unit)) : MutableTransformation {
    val t = MutableTransformation()
    t.set(
            getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
            getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
            getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()
    )

    return MutableTransformation(t, observer)
}
private fun MutableTransformation.randomize() = this.set(
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(),
        getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()
)