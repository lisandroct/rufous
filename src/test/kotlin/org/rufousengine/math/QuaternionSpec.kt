package org.rufousengine.math

import org.rufousengine.assertions.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object QuaternionsSpec: Spek({
    repeat(100) {
        val q = rQuaternion()
        describe("Quaternion: $q") {
            val quaternion by memoized { q.copy() }

            for (i in -1..4) {
                describe("Get $i index") {
                    if(i !in 0..3) {
                        it("throws an IllegalArgumentException") {
                            assert(IllegalArgumentException::class).isThrownBy{ quaternion[i] }
                        }
                    } else {
                        it("returns the component value") {
                            assert(quaternion[i]).isEqualTo(quaternion.components[i])
                        }
                    }
                }
            }

            for (i in -1..4) {
                describe("Set $i index") {
                    val value = rScalar()
                    if(i !in 0..3) {
                        it("throws an IllegalArgumentException") {
                            assert(IllegalArgumentException::class).isThrownBy { quaternion[i] = value }
                        }
                    } else {
                        it("changes the component value") {
                            quaternion[i] = value
                            assert(quaternion[i]).isEqualTo(value)
                        }
                    }
                }
            }

            describe("Magnitude") {
                it("should be the square root of the sum of the components' squares") {
                    var expected = 0f
                    for (component in quaternion.components) {
                        expected += component * component
                    }
                    expected = sqrt(expected)

                    assert(quaternion.magnitude).isEqualTo(expected)
                }
            }

            describe("Squared Magnitude") {
                it("should be the square of the magnitude") {
                    val expected = quaternion.magnitude * quaternion.magnitude

                    assert(quaternion.magnitudeSquared).isEqualTo(expected)
                }
            }

            describe("Negate") {
                val result by memoized { negate(quaternion) }

                for (i in 0..3) {
                    it("should negate the $i component") {
                        assert(result[i]).isEqualTo(-quaternion[i])
                    }
                }
            }

            describe("Normalize") {
                val result by memoized { normalize(quaternion) }

                it("returns the quaternion scaled by 1/magnitude") {
                    val expected = scale(quaternion, 1 / quaternion.magnitude)

                    assert(result).isEqualTo(expected)
                }
            }

            describe("Conjugate") {
                val result by memoized { conjugate(quaternion) }

                it("negates the x component") {
                    assert(result.x).isEqualTo(-quaternion.x)
                }
                it("negates the y component") {
                    assert(result.y).isEqualTo(-quaternion.y)
                }
                it("negates the z component") {
                    assert(result.z).isEqualTo(-quaternion.z)
                }
                it("doesn't negates the w component") {
                    assert(result.w).isEqualTo(quaternion.w)
                }
            }

            describe("Inverse") {
                val result by memoized { inverse(quaternion) }

                it("quaternion * inverse returns the identity") {
                    assert(quaternion * result).isIdentity()
                }

                it("inverse * quaternion returns the identity") {
                    assert(result * quaternion).isIdentity()
                }
            }

            describe("Scale") {
                val scalar = rScalar()
                val result by memoized { scale(quaternion, scalar) }

                for (i in 0..3) {
                    it("should scale the $i component") {
                        assert(result[i]).isEqualTo(quaternion[i] * scalar)
                    }
                }
            }

            describe("Operators") {
                describe("UnaryPlus") {
                    val result by memoized { +quaternion }

                    it("should be equal to quaternion") {
                        assert(result).isEqualTo(quaternion)
                    }
                }

                describe("UnaryMinus") {
                    val result by memoized { -quaternion }

                    it("should be equal to quaternion negated") {
                        assert(result).isEqualTo(negate(quaternion))
                    }
                }

                describe("Plus") {
                    val other = rQuaternion()
                    val result by memoized { quaternion + other }

                    it("should be equal to the added quaternions") {
                        assert(result).isEqualTo(add(quaternion, other))
                    }
                }

                describe("Minus") {
                    val other = rQuaternion()
                    val result by memoized { quaternion - other }

                    it("should be equal to the subtracted quaternions") {
                        assert(result).isEqualTo(subtract(quaternion, other))
                    }
                }

                describe("Times") {
                    val scalar = rScalar()
                    val result by memoized { quaternion * scalar }

                    it("should be equal to the scaled quaternion") {
                        assert(result).isEqualTo(scale(quaternion, scalar))
                    }
                }

                describe("Times") {
                    val other = rQuaternion()
                    val result by memoized { quaternion * other }

                    it("should be equal to the multiplied quaternions") {
                        assert(result).isEqualTo(multiply(quaternion, other))
                    }
                }

                describe("Div") {
                    val scalar = rScalar()
                    val result by memoized { quaternion / scalar }

                    it("should be equal to the scaled quaternion") {
                        assert(result).isEqualTo(scale(quaternion, 1 / scalar))
                    }
                }

                describe("PlusAssign") {
                    val other = rQuaternion()

                    it("should update quaternion to be equal to the added quaternions") {
                        val expected = quaternion + other

                        quaternion += other
                        assert(quaternion).isEqualTo(expected)
                    }
                }

                describe("MinusAssign") {
                    val other = rQuaternion()

                    it("should update quaternionto be equal to the subtracted quaternions") {
                        val expected = quaternion - other

                        quaternion -= other
                        assert(quaternion).isEqualTo(expected)
                    }
                }

                describe("TimesAssign") {
                    val scalar = rScalar()

                    it("should update quaternion to be equal to the scaled quaternion") {
                        val expected = quaternion * scalar

                        quaternion *= scalar
                        assert(quaternion).isEqualTo(expected)
                    }
                }

                describe("TimesAssign") {
                    val other = rQuaternion()

                    it("should update quaternion to be equal to the multiplied quaternions") {
                        val expected = quaternion * other

                        quaternion *= other
                        assert(quaternion).isEqualTo(expected)
                    }
                }

                describe("DivAssign") {
                    val scalar = rScalar()

                    it("should update quaternion to be equal to the scaled quaternion") {
                        val expected = quaternion / scalar

                        quaternion /= scalar
                        assert(quaternion).isEqualTo(expected)
                    }
                }
            }

            describe("Extension functions") {
                describe("Negate") {
                    it("should update quaternion to be equal to the negated quaternion") {
                        val expected = negate(quaternion)

                        quaternion.negate()
                        assert(quaternion).isEqualTo(expected)
                    }
                }

                describe("Normalize") {
                    it("should update quaternion to be equal to the normalized quaternion") {
                        val expected = normalize(quaternion)

                        quaternion.normalize()
                        assert(quaternion).isEqualTo(expected)
                    }
                }

                describe("Conjugate") {
                    it("should update quaternion to be equal to the conjugated quaternion") {
                        val expected = conjugate(quaternion)

                        quaternion.conjugate()
                        assert(quaternion).isEqualTo(expected)
                    }
                }

                describe("Inverse") {
                    it("should update quaternion to be equal to the inverse quaternion") {
                        val expected = inverse(quaternion)

                        quaternion.inverse()
                        assert(quaternion).isEqualTo(expected)
                    }
                }

                describe("Scale") {
                    val scalar = rScalar()

                    it("should update quaternion to be equal to the scaled quaternion") {
                        val expected = scale(quaternion, scalar)

                        quaternion.scale(scalar)
                        assert(quaternion).isEqualTo(expected)
                    }
                }

                describe("Add") {
                    val other = rQuaternion()

                    it("should update quaternion to be equal to the added quaternions") {
                        val expected = add(quaternion, other)

                        quaternion.add(other)
                        assert(quaternion).isEqualTo(expected)
                    }
                }

                describe("Subtract") {
                    val other = rQuaternion()

                    it("should update quaternion to be equal to the subtracted quaternions") {
                        val expected = subtract(quaternion, other)

                        quaternion.subtract(other)
                        assert(quaternion).isEqualTo(expected)
                    }
                }

                describe("Multiply") {
                    val other = rQuaternion()

                    it("should update quaternion to be the result of the quaternion multiplication") {
                        val expected = multiply(quaternion, other)

                        quaternion.multiply(other)
                        assert(quaternion).isEqualTo(expected)
                    }
                }

                describe("MultiplyLeft") {
                    val other = rQuaternion()

                    it("should update quaternion to be the result of the quaternion multiplication") {
                        val expected = multiply(other, quaternion)

                        quaternion.multiplyLeft(other)
                        assert(quaternion).isEqualTo(expected)
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
private fun rMatrix2() = Matrix2 { _, _ -> rScalar() }
private fun rMatrix3() = Matrix3 { _, _ -> rScalar() }
private fun rMatrix4() = Matrix4 { _, _ -> rScalar() }
private fun rQuaternion() = Quaternion(rScalar(), rScalar(), rScalar(), rScalar())