package org.rufousengine.math

import org.rufousengine.assertions.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object MatricesSpec : Spek({
    val seed = random(Double.MIN_VALUE.toLong(), Double.MAX_VALUE.toLong())
    //val seed = 39589156570048512L
    setRandomSeed(seed)

    repeat(100) {
        val m = rMatrix2()
        describe("Matrix2: $m") {
            val matrix by memoized { m.copy() }

            for (row in -1..4) {
                for (column in -1..4) {
                    describe("Get ($row, $column) index") {
                        when {
                            row in 0 until matrix.dimensions && column in 0 until matrix.dimensions -> it("returns the element value") {
                                assert(matrix[row, column]).isEqualTo(matrix.components[matrix.dimensions * row + column])
                            }
                            else -> it("throws an IllegalArgumentException") {
                                assert(IllegalArgumentException::class).isThrownBy { matrix[row, column] }
                            }
                        }
                    }
                }
            }

            for (row in -1..4) {
                for (column in -1..4) {
                    describe("Set ($row, $column) index") {
                        val value = rScalar()
                        when {
                            row in 0 until matrix.dimensions && column in 0 until matrix.dimensions -> it("changes the element value") {
                                matrix[row, column] = value
                                assert(matrix[row, column]).isEqualTo(value)
                            }
                            else -> it("throws an IllegalArgumentException") {
                                assert(IllegalArgumentException::class).isThrownBy { matrix[row, column] = value }
                            }
                        }
                    }
                }
            }

            describe("Determinant") {
                val result by memoized { determinant(matrix) }

                it("satisfies the expansion by minors method") {
                    var expected = 0f
                    for(j in 0 until matrix.dimensions) {
                        expected += matrix[0, j] * pow(-1, j) * determinant(submatrix(matrix, 0, j))
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("Negate") {
                val result by memoized { negate(matrix) }

                for (row in 0 until matrix.dimensions) {
                    for (column in 0 until matrix.dimensions) {
                        it("should negate the ($row, $column) element") {
                            assert(result[row, column]).isEqualTo(-matrix[row, column])
                        }
                    }
                }
            }

            describe("Transpose") {
                val result by memoized { transpose(matrix) }

                for (row in 0 until matrix.dimensions) {
                    for (column in 0 until matrix.dimensions) {
                        it("should swap the ($row, $column) and ($column, $row) elements") {
                            assert(result[row, column]).isEqualTo(matrix[column, row])
                        }
                    }
                }
            }

            describe("Inverse") {
                if(matrix.isSingular) {
                    it("throws SingularMatrixException") {
                        assert(SingularMatrixException::class).isThrownBy { inverse(matrix) }
                    }
                } else {
                    val result by memoized { inverse(matrix) }

                    it("matrix * inverse returns the identity") {
                        assert(matrix * result).isIdentity()
                    }

                    it("inverse * matrix returns the identity") {
                        assert(result * matrix).isIdentity()
                    }
                }
            }

            describe("Scale") {
                val scalar = rScalar()
                val result by memoized { scale(matrix, scalar) }

                for (row in 0 until matrix.dimensions) {
                    for (column in 0 until matrix.dimensions) {
                        it("should scale the ($row, $column) component") {
                            assert(result[row, column]).isEqualTo(matrix[row, column] * scalar)
                        }
                    }
                }
            }

            describe("Operators") {
                describe("UnaryPlus") {
                    val result by memoized { +matrix }

                    it("should be equal to matrix") {
                        assert(result).isEqualTo(matrix)
                    }
                }

                describe("UnaryMinus") {
                    val result by memoized { -matrix }

                    it("should be equal to matrix negated") {
                        assert(result).isEqualTo(negate(matrix))
                    }
                }

                describe("Plus") {
                    val other = rMatrix2()
                    val result by memoized { matrix + other }

                    it("should be equal to the added matrices") {
                        assert(result).isEqualTo(add(matrix, other))
                    }
                }

                describe("Minus") {
                    val other = rMatrix2()
                    val result by memoized { matrix - other }

                    it("should be equal to the subtracted matrices") {
                        assert(result).isEqualTo(subtract(matrix, other))
                    }
                }

                describe("Scale") {
                    val scalar = rScalar()
                    val result by memoized { matrix * scalar }

                    it("should be equal to the scaled matrix") {
                        assert(result).isEqualTo(scale(matrix, scalar))
                    }
                }

                describe("Scale") {
                    val vector = rVector2()
                    val result by memoized { matrix * vector }

                    it("should be equal to the multiplied matrices") {
                        assert(result).isEqualTo(multiply(matrix, vector))
                    }
                }

                describe("Scale") {
                    val other = rMatrix2()
                    val result by memoized { matrix * other }

                    it("should be equal to the multiplied matrices") {
                        assert(result).isEqualTo(multiply(matrix, other))
                    }
                }

                describe("Div") {
                    val scalar = rScalar()
                    val result by memoized { matrix / scalar }

                    it("should be equal to the scaled matrix") {
                        assert(result).isEqualTo(scale(matrix, 1 / scalar))
                    }
                }

                describe("PlusAssign") {
                    val other = rMatrix2()

                    it("updates matrix to be equal to the added matrices") {
                        val expected = matrix + other

                        matrix += other
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("MinusAssign") {
                    val other = rMatrix2()

                    it("updates matrix to be equal to the subtracted matrices") {
                        val expected = matrix - other

                        matrix -= other
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("TimesAssign") {
                    val scalar = rScalar()

                    it("updates matrix to be equal to the scaled matrix") {
                        val expected = matrix * scalar

                        matrix *= scalar
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("TimesAssign") {
                    val other = rMatrix2()

                    it("updates matrix to be equal to the mulitiplied matrices") {
                        val expected = matrix * other

                        matrix *= other
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("DivAssign") {
                    val scalar = rScalar()

                    it("updates matrix to be equal to the scaled matrix") {
                        val expected = matrix / scalar

                        matrix /= scalar
                        assert(matrix).isEqualTo(expected)
                    }
                }
            }

            describe("Extension functions") {
                describe("Negate") {
                    it("updates the original matrix properly") {
                        val expected = negate(matrix)

                        matrix.negate()
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Transpose") {
                    it("updates the original matrix properly") {
                        val expected = transpose(matrix)

                        matrix.transpose()
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Inverse") {
                    if(matrix.isSingular) {
                        it("throws SingularMatrixException") {
                            assert(SingularMatrixException::class).isThrownBy { matrix.inverse() }
                        }
                    } else {
                        it("updates the original matrix properly") {
                            val expected = inverse(matrix)

                            matrix.inverse()
                            assert(matrix).isEqualTo(expected)
                        }
                    }
                }

                describe("Transpose") {
                    val scalar = rScalar()

                    it("updates the original matrix properly") {
                        val expected = scale(matrix, scalar)

                        matrix.scale(scalar)
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Add") {
                    val other = rMatrix2()

                    it("updates the original matrix properly") {
                        val expected = add(matrix, other)

                        matrix.add(other)
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Subtract") {
                    val other = rMatrix2()

                    it("updates the original matrix properly") {
                        val expected = subtract(matrix, other)

                        matrix.subtract(other)
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Multiply") {
                    val other = rMatrix2()

                    it("updates the original matrix properly") {
                        val expected = multiply(matrix, other)

                        matrix.multiply(other)
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("MultiplyLeft") {
                    val other = rMatrix2()

                    it("updates the original matrix properly") {
                        val expected = multiply(other, matrix)

                        matrix.multiplyLeft(other)
                        assert(matrix).isEqualTo(expected)
                    }
                }
            }
        }
    }

    repeat(100) {
        val m0 = rMatrix2()
        val m1 = rMatrix2()
        describe("Matrix2: $m0 - Matrix2: $m1") {
            val a by memoized { m0.copy() }
            val b by memoized { m1.copy() }

            describe("Add") {
                val result by memoized { add(a, b) }

                for (row in 0 until result.dimensions) {
                    for (column in 0 until result.dimensions) {
                        it("adds the two matrices component wisely") {
                            assert(result[row, column]).isEqualTo(a[row, column] + b[row, column])
                        }
                    }
                }
            }

            describe("Subtract") {
                val result by memoized { subtract(a, b) }

                for (row in 0 until result.dimensions) {
                    for (column in 0 until result.dimensions) {
                        it("subtracts the two matrices component wisely") {
                            assert(result[row, column]).isEqualTo(a[row, column] - b[row, column])
                        }
                    }
                }
            }

            describe("Multiply") {
                val result by memoized { multiply(a, b) }

                for (row in 0 until result.dimensions) {
                    for (column in 0 until result.dimensions) {
                        it("sets each component following matrices multiplication definition") {
                            var expected = 0f
                            for(k in 0 until result.dimensions) {
                                expected += a[row, k] * b[k, column]
                            }

                            assert(result[row, column]).isEqualTo(expected)
                        }
                    }
                }
            }
        }
    }

    repeat(100) {
        val m = rMatrix2()
        val v = rVector2()
        describe("Matrix2: $m - Vector2: $v") {
            val matrix by memoized { m.copy() }
            val vector by memoized { v.copy() }

            describe("Multiply") {
                val result by memoized { multiply(matrix, vector) }

                for (i in 0 until result.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until result.dimensions) {
                            expected += matrix[i, k] * vector[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    repeat(100) {
        val m = rMatrix3()
        describe("Matrix3: $m") {
            val matrix by memoized { m.copy() }

            for (row in -1..4) {
                for (column in -1..4) {
                    describe("Get ($row, $column) index") {
                        when {
                            row in 0 until matrix.dimensions && column in 0 until matrix.dimensions -> it("returns the element value") {
                                assert(matrix[row, column]).isEqualTo(matrix.components[matrix.dimensions * row + column])
                            }
                            else -> it("throws an IllegalArgumentException") {
                                assert(IllegalArgumentException::class).isThrownBy { matrix[row, column] }
                            }
                        }
                    }
                }
            }

            for (row in -1..4) {
                for (column in -1..4) {
                    describe("Set ($row, $column) index") {
                        val value = rScalar()
                        when {
                            row in 0 until matrix.dimensions && column in 0 until matrix.dimensions -> it("changes the element value") {
                                matrix[row, column] = value
                                assert(matrix[row, column]).isEqualTo(value)
                            }
                            else -> it("throws an IllegalArgumentException") {
                                assert(IllegalArgumentException::class).isThrownBy { matrix[row, column] = value }
                            }
                        }
                    }
                }
            }

            describe("Determinant") {
                val result by memoized { determinant(matrix) }

                it("satisfies the expansion by minors method") {
                    var expected = 0f
                    for(j in 0 until matrix.dimensions) {
                        expected += matrix[0, j] * pow(-1, j) * determinant(submatrix(matrix, 0, j))
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("Negate") {
                val result by memoized { negate(matrix) }

                for (row in 0 until matrix.dimensions) {
                    for (column in 0 until matrix.dimensions) {
                        it("should negate the ($row, $column) element") {
                            assert(result[row, column]).isEqualTo(-matrix[row, column])
                        }
                    }
                }
            }

            describe("Transpose") {
                val result by memoized { transpose(matrix) }

                for (row in 0 until matrix.dimensions) {
                    for (column in 0 until matrix.dimensions) {
                        it("should swap the ($row, $column) and ($column, $row) elements") {
                            assert(result[row, column]).isEqualTo(matrix[column, row])
                        }
                    }
                }
            }

            describe("Inverse") {
                if(matrix.isSingular) {
                    it("throws SingularMatrixException") {
                        assert(SingularMatrixException::class).isThrownBy { inverse(matrix) }
                    }
                } else {
                    val result by memoized { inverse(matrix) }

                    it("matrix * inverse returns the identity") {
                        assert(matrix * result).isIdentity()
                    }

                    it("inverse * matrix returns the identity") {
                        assert(result * matrix).isIdentity()
                    }
                }
            }

            describe("Scale") {
                val scalar = rScalar()
                val result by memoized { scale(matrix, scalar) }

                for (row in 0 until matrix.dimensions) {
                    for (column in 0 until matrix.dimensions) {
                        it("should scale the ($row, $column) component") {
                            assert(result[row, column]).isEqualTo(matrix[row, column] * scalar)
                        }
                    }
                }
            }

            describe("Operators") {
                describe("UnaryPlus") {
                    val result by memoized { +matrix }

                    it("should be equal to matrix") {
                        assert(result).isEqualTo(matrix)
                    }
                }

                describe("UnaryMinus") {
                    val result by memoized { -matrix }

                    it("should be equal to matrix negated") {
                        assert(result).isEqualTo(negate(matrix))
                    }
                }

                describe("Plus") {
                    val other = rMatrix3()
                    val result by memoized { matrix + other }

                    it("should be equal to the added matrices") {
                        assert(result).isEqualTo(add(matrix, other))
                    }
                }

                describe("Minus") {
                    val other = rMatrix3()
                    val result by memoized { matrix - other }

                    it("should be equal to the subtracted matrices") {
                        assert(result).isEqualTo(subtract(matrix, other))
                    }
                }

                describe("Scale") {
                    val scalar = rScalar()
                    val result by memoized { matrix * scalar }

                    it("should be equal to the scaled matrix") {
                        assert(result).isEqualTo(scale(matrix, scalar))
                    }
                }

                describe("Scale") {
                    val vector = rVector3()
                    val result by memoized { matrix * vector }

                    it("should be equal to the multiplied matrices") {
                        assert(result).isEqualTo(multiply(matrix, vector))
                    }
                }

                describe("Scale") {
                    val other = rMatrix3()
                    val result by memoized { matrix * other }

                    it("should be equal to the multiplied matrices") {
                        assert(result).isEqualTo(multiply(matrix, other))
                    }
                }

                describe("Div") {
                    val scalar = rScalar()
                    val result by memoized { matrix / scalar }

                    it("should be equal to the scaled matrix") {
                        assert(result).isEqualTo(scale(matrix, 1 / scalar))
                    }
                }

                describe("PlusAssign") {
                    val other = rMatrix3()

                    it("updates matrix to be equal to the added matrices") {
                        val expected = matrix + other

                        matrix += other
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("MinusAssign") {
                    val other = rMatrix3()

                    it("updates matrix to be equal to the subtracted matrices") {
                        val expected = matrix - other

                        matrix -= other
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("TimesAssign") {
                    val scalar = rScalar()

                    it("updates matrix to be equal to the scaled matrix") {
                        val expected = matrix * scalar

                        matrix *= scalar
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("TimesAssign") {
                    val other = rMatrix3()

                    it("updates matrix to be equal to the mulitiplied matrices") {
                        val expected = matrix * other

                        matrix *= other
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("DivAssign") {
                    val scalar = rScalar()

                    it("updates matrix to be equal to the scaled matrix") {
                        val expected = matrix / scalar

                        matrix /= scalar
                        assert(matrix).isEqualTo(expected)
                    }
                }
            }

            describe("Extension functions") {
                describe("Negate") {
                    it("updates the original matrix properly") {
                        val expected = negate(matrix)

                        matrix.negate()
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Transpose") {
                    it("updates the original matrix properly") {
                        val expected = transpose(matrix)

                        matrix.transpose()
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Inverse") {
                    if(matrix.isSingular) {
                        it("throws SingularMatrixException") {
                            assert(SingularMatrixException::class).isThrownBy { matrix.inverse() }
                        }
                    } else {
                        it("updates the original matrix properly") {
                            val expected = inverse(matrix)

                            matrix.inverse()
                            assert(matrix).isEqualTo(expected)
                        }
                    }
                }

                describe("Transpose") {
                    val scalar = rScalar()

                    it("updates the original matrix properly") {
                        val expected = scale(matrix, scalar)

                        matrix.scale(scalar)
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Add") {
                    val other = rMatrix3()

                    it("updates the original matrix properly") {
                        val expected = add(matrix, other)

                        matrix.add(other)
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Subtract") {
                    val other = rMatrix3()

                    it("updates the original matrix properly") {
                        val expected = subtract(matrix, other)

                        matrix.subtract(other)
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Multiply") {
                    val other = rMatrix3()

                    it("updates the original matrix properly") {
                        val expected = multiply(matrix, other)

                        matrix.multiply(other)
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("MultiplyLeft") {
                    val other = rMatrix3()

                    it("updates the original matrix properly") {
                        val expected = multiply(other, matrix)

                        matrix.multiplyLeft(other)
                        assert(matrix).isEqualTo(expected)
                    }
                }
            }
        }
    }

    repeat(100) {
        val m0 = rMatrix3()
        val m1 = rMatrix3()
        describe("Matrix3: $m0 - Matrix3: $m1") {
            val a by memoized { m0.copy() }
            val b by memoized { m1.copy() }

            describe("Add") {
                val result by memoized { add(a, b) }

                for (row in 0 until result.dimensions) {
                    for (column in 0 until result.dimensions) {
                        it("adds the two matrices component wisely") {
                            assert(result[row, column]).isEqualTo(a[row, column] + b[row, column])
                        }
                    }
                }
            }

            describe("Subtract") {
                val result by memoized { subtract(a, b) }

                for (row in 0 until result.dimensions) {
                    for (column in 0 until result.dimensions) {
                        it("subtracts the two matrices component wisely") {
                            assert(result[row, column]).isEqualTo(a[row, column] - b[row, column])
                        }
                    }
                }
            }

            describe("Multiply") {
                val result by memoized { multiply(a, b) }

                for (row in 0 until result.dimensions) {
                    for (column in 0 until result.dimensions) {
                        it("sets each component following matrices multiplication definition") {
                            var expected = 0f
                            for(k in 0 until result.dimensions) {
                                expected += a[row, k] * b[k, column]
                            }

                            assert(result[row, column]).isEqualTo(expected)
                        }
                    }
                }
            }
        }
    }

    repeat(100) {
        val m = rMatrix3()
        val v = rVector2()
        describe("Matrix3: $m - Vector2: $v") {
            val matrix by memoized { m.copy() }
            val vector by memoized { v.copy() }

            describe("Multiply") {
                val result by memoized { multiply(matrix, vector) }

                for (i in 0 until result.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until result.dimensions) {
                            expected += matrix[i, k] * vector[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }
    }

    repeat(100) {
        val m = rMatrix3()
        val v = rVector3()
        describe("Matrix3: $m - Vector3: $v") {
            val matrix by memoized { m.copy() }
            val vector by memoized { v.copy() }

            describe("Multiply") {
                val result by memoized { multiply(matrix, vector) }

                for (i in 0 until vector.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until matrix.dimensions) {
                            expected += matrix[i, k] * vector[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    repeat(100) {
        val m = rMatrix4()
        describe("Matrix4: $m") {
            val matrix by memoized { m.copy() }

            for (row in -1..4) {
                for (column in -1..4) {
                    describe("Get ($row, $column) index") {
                        when {
                            row in 0 until matrix.dimensions && column in 0 until matrix.dimensions -> it("returns the element value") {
                                assert(matrix[row, column]).isEqualTo(matrix.components[matrix.dimensions * row + column])
                            }
                            else -> it("throws an IllegalArgumentException") {
                                assert(IllegalArgumentException::class).isThrownBy { matrix[row, column] }
                            }
                        }
                    }
                }
            }

            for (row in -1..4) {
                for (column in -1..4) {
                    describe("Set ($row, $column) index") {
                        val value = rScalar()
                        when {
                            row in 0 until matrix.dimensions && column in 0 until matrix.dimensions -> it("changes the element value") {
                                matrix[row, column] = value
                                assert(matrix[row, column]).isEqualTo(value)
                            }
                            else -> it("throws an IllegalArgumentException") {
                                assert(IllegalArgumentException::class).isThrownBy { matrix[row, column] = value }
                            }
                        }
                    }
                }
            }

            describe("Determinant") {
                val result by memoized { determinant(matrix) }

                it("satisfies the expansion by minors method") {
                    var expected = 0f
                    for(j in 0 until matrix.dimensions) {
                        expected += matrix[0, j] * pow(-1, j) * determinant(submatrix(matrix, 0, j))
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("Negate") {
                val result by memoized { negate(matrix) }

                for (row in 0 until matrix.dimensions) {
                    for (column in 0 until matrix.dimensions) {
                        it("should negate the ($row, $column) element") {
                            assert(result[row, column]).isEqualTo(-matrix[row, column])
                        }
                    }
                }
            }

            describe("Transpose") {
                val result by memoized { transpose(matrix) }

                for (row in 0 until matrix.dimensions) {
                    for (column in 0 until matrix.dimensions) {
                        it("should swap the ($row, $column) and ($column, $row) elements") {
                            assert(result[row, column]).isEqualTo(matrix[column, row])
                        }
                    }
                }
            }

            describe("Inverse") {
                if(matrix.isSingular) {
                    it("throws SingularMatrixException") {
                        assert(SingularMatrixException::class).isThrownBy { inverse(matrix) }
                    }
                } else {
                    val result by memoized { inverse(matrix) }

                    it("matrix * inverse returns the identity") {
                        assert(matrix * result).isIdentity()
                    }

                    it("inverse * matrix returns the identity") {
                        assert(result * matrix).isIdentity()
                    }
                }
            }

            describe("Scale") {
                val scalar = rScalar()
                val result by memoized { scale(matrix, scalar) }

                for (row in 0 until matrix.dimensions) {
                    for (column in 0 until matrix.dimensions) {
                        it("should scale the ($row, $column) component") {
                            assert(result[row, column]).isEqualTo(matrix[row, column] * scalar)
                        }
                    }
                }
            }

            describe("Operators") {
                describe("UnaryPlus") {
                    val result by memoized { +matrix }

                    it("should be equal to matrix") {
                        assert(result).isEqualTo(matrix)
                    }
                }

                describe("UnaryMinus") {
                    val result by memoized { -matrix }

                    it("should be equal to matrix negated") {
                        assert(result).isEqualTo(negate(matrix))
                    }
                }

                describe("Plus") {
                    val other = rMatrix4()
                    val result by memoized { matrix + other }

                    it("should be equal to the added matrices") {
                        assert(result).isEqualTo(add(matrix, other))
                    }
                }

                describe("Minus") {
                    val other = rMatrix4()
                    val result by memoized { matrix - other }

                    it("should be equal to the subtracted matrices") {
                        assert(result).isEqualTo(subtract(matrix, other))
                    }
                }

                describe("Scale") {
                    val scalar = rScalar()
                    val result by memoized { matrix * scalar }

                    it("should be equal to the scaled matrix") {
                        assert(result).isEqualTo(scale(matrix, scalar))
                    }
                }

                describe("Scale") {
                    val vector = rVector4()
                    val result by memoized { matrix * vector }

                    it("should be equal to the multiplied matrices") {
                        assert(result).isEqualTo(multiply(matrix, vector))
                    }
                }

                describe("Scale") {
                    val other = rMatrix4()
                    val result by memoized { matrix * other }

                    it("should be equal to the multiplied matrices") {
                        assert(result).isEqualTo(multiply(matrix, other))
                    }
                }

                describe("Div") {
                    val scalar = rScalar()
                    val result by memoized { matrix / scalar }

                    it("should be equal to the scaled matrix") {
                        assert(result).isEqualTo(scale(matrix, 1 / scalar))
                    }
                }

                describe("PlusAssign") {
                    val other = rMatrix4()

                    it("updates matrix to be equal to the added matrices") {
                        val expected = matrix + other

                        matrix += other
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("MinusAssign") {
                    val other = rMatrix4()

                    it("updates matrix to be equal to the subtracted matrices") {
                        val expected = matrix - other

                        matrix -= other
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("TimesAssign") {
                    val scalar = rScalar()

                    it("updates matrix to be equal to the scaled matrix") {
                        val expected = matrix * scalar

                        matrix *= scalar
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("TimesAssign") {
                    val other = rMatrix4()

                    it("updates matrix to be equal to the mulitiplied matrices") {
                        val expected = matrix * other

                        matrix *= other
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("DivAssign") {
                    val scalar = rScalar()

                    it("updates matrix to be equal to the scaled matrix") {
                        val expected = matrix / scalar

                        matrix /= scalar
                        assert(matrix).isEqualTo(expected)
                    }
                }
            }

            describe("Extension functions") {
                describe("Negate") {
                    it("updates the original matrix properly") {
                        val expected = negate(matrix)

                        matrix.negate()
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Transpose") {
                    it("updates the original matrix properly") {
                        val expected = transpose(matrix)

                        matrix.transpose()
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Inverse") {
                    if(matrix.isSingular) {
                        it("throws SingularMatrixException") {
                            assert(SingularMatrixException::class).isThrownBy { matrix.inverse() }
                        }
                    } else {
                        it("updates the original matrix properly") {
                            val expected = inverse(matrix)

                            matrix.inverse()
                            assert(matrix).isEqualTo(expected)
                        }
                    }
                }

                describe("Transpose") {
                    val scalar = rScalar()

                    it("updates the original matrix properly") {
                        val expected = scale(matrix, scalar)

                        matrix.scale(scalar)
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Add") {
                    val other = rMatrix4()

                    it("updates the original matrix properly") {
                        val expected = add(matrix, other)

                        matrix.add(other)
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Subtract") {
                    val other = rMatrix4()

                    it("updates the original matrix properly") {
                        val expected = subtract(matrix, other)

                        matrix.subtract(other)
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("Multiply") {
                    val other = rMatrix4()

                    it("updates the original matrix properly") {
                        val expected = multiply(matrix, other)

                        matrix.multiply(other)
                        assert(matrix).isEqualTo(expected)
                    }
                }

                describe("MultiplyLeft") {
                    val other = rMatrix4()

                    it("updates the original matrix properly") {
                        val expected = multiply(other, matrix)

                        matrix.multiplyLeft(other)
                        assert(matrix).isEqualTo(expected)
                    }
                }
            }
        }
    }

    repeat(100) {
        val m0 = rMatrix2()
        val m1 = rMatrix2()
        describe("Matrix4: $m0 - Matrix4: $m1") {
            val a by memoized { m0.copy() }
            val b by memoized { m1.copy() }

            describe("Add") {
                val result by memoized { add(a, b) }

                for (row in 0 until result.dimensions) {
                    for (column in 0 until result.dimensions) {
                        it("adds the two matrices component wisely") {
                            assert(result[row, column]).isEqualTo(a[row, column] + b[row, column])
                        }
                    }
                }
            }

            describe("Subtract") {
                val result by memoized { subtract(a, b) }

                for (row in 0 until result.dimensions) {
                    for (column in 0 until result.dimensions) {
                        it("subtracts the two matrices component wisely") {
                            assert(result[row, column]).isEqualTo(a[row, column] - b[row, column])
                        }
                    }
                }
            }

            describe("Multiply") {
                val result by memoized { multiply(a, b) }

                for (row in 0 until result.dimensions) {
                    for (column in 0 until result.dimensions) {
                        it("sets each component following matrices multiplication definition") {
                            var expected = 0f
                            for(k in 0 until result.dimensions) {
                                expected += a[row, k] * b[k, column]
                            }

                            assert(result[row, column]).isEqualTo(expected)
                        }
                    }
                }
            }
        }
    }

    repeat(100) {
        val m = rMatrix4()
        val v = rVector2()
        describe("Matrix4: $m - Vector2: $v") {
            val matrix by memoized { m.copy() }
            val vector by memoized { v.copy() }

            describe("Multiply") {
                val result by memoized { multiply(matrix, vector, Vector2()) }

                for (i in 0 until result.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until matrix.dimensions) {
                            expected += matrix[i, k] * vector[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }

            describe("Multiply") {
                val result by memoized { multiply(matrix, vector, Vector3()) }

                for (i in 0 until result.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until matrix.dimensions) {
                            expected += matrix[i, k] * vector[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }

            describe("Multiply") {
                val result by memoized { multiply(matrix, vector) }

                for (i in 0 until result.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until result.dimensions) {
                            expected += matrix[i, k] * vector[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }
    }

    repeat(100) {
        val m = rMatrix4()
        val v = rVector3()
        describe("Matrix4: $m - Vector3: $v") {
            val matrix by memoized { m.copy() }
            val vector by memoized { v.copy() }

            describe("Multiply") {
                val result by memoized { multiply(matrix, vector, Vector3()) }

                for (i in 0 until result.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until matrix.dimensions) {
                            expected += matrix[i, k] * vector[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }

            describe("Multiply") {
                val result by memoized { multiply(matrix, vector) }

                for (i in 0 until result.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until result.dimensions) {
                            expected += matrix[i, k] * vector[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }
    }

    repeat(100) {
        val m = rMatrix4()
        val v = rVector4()
        describe("Matrix4: $m - Vector4: $v") {
            val matrix by memoized { m.copy() }
            val vector by memoized { v.copy() }

            describe("Multiply") {
                val result by memoized { multiply(matrix, vector) }

                for (i in 0 until result.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until result.dimensions) {
                            expected += matrix[i, k] * vector[k]
                        }

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }
    }

    repeat(100) {
        val m = rMatrix4()
        val p = rPoint2D()
        describe("Matrix4: $m - Point2D: $p") {
            val matrix by memoized { m.copy() }
            val point by memoized { p.copy() }

            describe("Multiply") {
                val result by memoized { multiply(matrix, point, Point2D()) }

                for (i in 0 until result.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until matrix.dimensions - 1) {
                            expected += matrix[i, k] * point[k]
                        }
                        expected += matrix[i, 3]

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }

            describe("Multiply") {
                val result by memoized { multiply(matrix, point, Point3D()) }

                for (i in 0 until result.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until matrix.dimensions - 1) {
                            expected += matrix[i, k] * point[k]
                        }
                        expected += matrix[i, 3]

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }

            describe("Multiply") {
                val result by memoized { multiply(matrix, point) }

                for (i in 0 until result.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until result.dimensions - 1) {
                            expected += matrix[i, k] * point[k]
                        }
                        expected += matrix[i, 3]

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }
    }

    repeat(100) {
        val m = rMatrix4()
        val p = rPoint3D()
        describe("Matrix4: $m - Point3D: $p") {
            val matrix by memoized { m.copy() }
            val point by memoized { p.copy() }

            describe("Multiply") {
                val result by memoized { multiply(matrix, point, Point3D()) }

                for (i in 0 until result.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until matrix.dimensions - 1) {
                            expected += matrix[i, k] * point[k]
                        }
                        expected += matrix[i, 3]

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }

            describe("Multiply") {
                val result by memoized { multiply(matrix, point) }

                for (i in 0 until result.dimensions) {
                    it("sets each component following matrices multiplication definition") {
                        var expected = 0f
                        for(k in 0 until result.dimensions - 1) {
                            expected += matrix[i, k] * point[k]
                        }
                        expected += matrix[i, 3]

                        assert(result[i]).isEqualTo(expected)
                    }
                }
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    repeat(200) {
        val m = when(it % 2) {
            0 -> Orthographic(random(5f, 100f), random(5f, 100f), random(1f, 5f), random(20f, 1000f))
            1 -> Perspective(random(5f, 100f), random(1 / 3f, 3f), random(1f, 5f), random(20f, 1000f))
            else -> throw Exception("Not valid.")
        }
        describe("Projection (type ${it % 2}): $m") {
            val matrix by memoized { m.copy() }

            describe("Is Projection") {
                it("is a projection matrix") {
                    assert(matrix).isProjection()
                }
            }

            describe("Determinant") {
                val result by memoized { determinant(matrix) }

                it("satisfies the expansion by minors method") {
                    var expected = 0f
                    for(j in 0 until matrix.dimensions) {
                        expected += matrix[0, j] * pow(-1, j) * determinant(submatrix(matrix, 0, j))
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("Inverse") {
                if(matrix.isSingular) {
                    it("throws SingularMatrixException") {
                        assert(SingularMatrixException::class).isThrownBy { inverse(matrix) }
                    }
                } else {
                    val result by memoized { inverse(matrix) }

                    it("matrix * inverse returns the identity") {
                        assert(matrix * result).isIdentity()
                    }

                    it("inverse * matrix returns the identity") {
                        assert(result * matrix).isIdentity()
                    }
                }
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    repeat(1700) {
        val m = when(it % 17) {
            0 -> RotationX(random(-360f, 360f))
            1 -> RotationY(random(-360f, 360f))
            2 -> RotationZ(random(-360f, 360f))
            3 -> Rotation(random(-360f, 360f), rVector2())
            4 -> Rotation(random(-360f, 360f), rVector3())
            5 -> Reflection(rVector2())
            6 -> Reflection(rVector3())
            7 -> Involution(rVector2())
            8 -> Involution(rVector3())
            9 -> Scale(rScalar())
            10 -> Scale(rScalar(), rScalar(), rScalar())
            11 -> Scale(rScalar(), rVector2())
            12 -> Scale(rScalar(), rVector3())
            13 -> Skew(rScalar(), rVector2(), rVector2())
            14 -> Skew(rScalar(), rVector3(), rVector3())
            15 -> Translation(rPoint2D())
            16 -> Translation(rPoint3D())
            else -> throw Exception("Not valid.")
        }
        describe("Transformation (type ${it % 17}): $m") {
            val matrix by memoized { m.copy() }

            describe("Is Transformation") {
                it("is a transformation matrix") {
                    assert(matrix).isTransformation()
                }
            }

            describe("Determinant") {
                val result by memoized { determinant(matrix) }

                it("satisfies the expansion by minors method") {
                    var expected = 0f
                    for(j in 0 until matrix.dimensions) {
                        expected += matrix[3, j] * pow(-1, 3 + j) * determinant(submatrix(matrix, 3, j))
                    }

                    assert(result).isEqualTo(expected)
                }
            }

            describe("Inverse") {
                if(matrix.isSingular) {
                    it("throws SingularMatrixException") {
                        assert(SingularMatrixException::class).isThrownBy { inverse(matrix) }
                    }
                } else {
                    val result by memoized { inverse(matrix) }

                    it("matrix * inverse returns the identity") {
                        assert(matrix * result).isIdentity()
                    }

                    it("inverse * matrix returns the identity") {
                        assert(result * matrix).isIdentity()
                    }
                }
            }
        }
    }
})

private fun rScalar() = random(-10f, 10f)
private fun rVector2() = Vector2 { rScalar() }
private fun rVector3() = Vector3 { rScalar() }
private fun rVector4() = Vector4 { rScalar() }
private fun rPoint2D() = Point2D { rScalar() }
private fun rPoint3D() = Point3D { rScalar() }
private fun rMatrix2() = Matrix2 { _, _ -> rScalar() }
private fun rMatrix3() = Matrix3 { _, _ -> rScalar() }
private fun rMatrix4() = Matrix4 { _, _ -> rScalar() }