package org.rufousengine.math

import org.rufousengine.assertions.assert
import org.rufousengine.assertions.isEqualTo
import org.rufousengine.assertions.isIdentity
import org.rufousengine.assertions.isUnit
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object QuaternionSpec : Spek({
    describe("Quaternion") {
        val a = rQuaternion()

        describe("negate") {
            val result = -a

            it("should negate each component") {
                for(i in 0 until 4) {
                    assert(result[i]).isEqualTo(-a[i])
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

        describe("conjugate") {
            val result = conjugate(a)

            it("should negate the vector part") {
                assert(result.vectorPart).isEqualTo(-a.vectorPart)
            }

            it("should not modify w") {
                assert(result.w).isEqualTo(a.w)
            }
        }

        describe("inverse") {
            val result = inverse(a)

            it("should be inverse") {
                assert(a * result).isIdentity()
                assert(result * a).isIdentity()
            }
        }

        describe("Float") {
            val v = rValue()

            describe("scale") {
                val result = a * v

                it("should multiply each component") {
                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(a[i] * v)
                    }
                }
            }
        }

        describe("Quaternion") {
            val b = rQuaternion()

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

            describe("multiplication") {
                val result = a * b

                it("should suffice the definition") {
                    val v1 = a.vectorPart
                    val v2 = b.vectorPart
                    val s1 = a.w
                    val s2 = b.w
                    val expected = Quaternion(cross(v1, v2) + s1 * v2 + s2 * v1, s1 * s2 - dot(v1, v2))

                    for (i in 0 until 4) {
                        assert(result[i]).isEqualTo(expected[i])
                    }
                }
            }
        }
    }
})

private fun rValue() = random(-100f, 100f)
private fun rQuaternion() = Quaternion(rValue(), rValue(), rValue(), rValue())