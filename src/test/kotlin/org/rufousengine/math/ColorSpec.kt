package org.rufousengine.math

import org.jetbrains.spek.api.Spek
import assertk.assert
import assertk.assertions.*
import org.jetbrains.spek.api.dsl.*
import org.rufousengine.assertions.isCloseTo

object ColorSpec: Spek({
    describe("immutable constructors") {
        on("empty") {
            val color = Color()
            it("should be the transparent black Color") {
                assert(color).isEqualTo(Color.transparentBlack)
            }
        }
        on("primary") {
            val r = getRandomValue()
            val g = getRandomValue()
            val b = getRandomValue()
            val a = getRandomValue()
            val color = Color(r, g, b, a)
            it("should have r set") {
                assert(color.r).isCloseTo(r)
            }
            it("should have g set") {
                assert(color.g).isCloseTo(g)
            }
            it("should have b set") {
                assert(color.b).isCloseTo(b)
            }
            it("should have a set") {
                assert(color.a).isCloseTo(a)
            }
        }
        on("Color") {
            val other = getRandomColor()
            val vector = Color(other)
            it("should be equal to other") {
                assert(vector).isEqualTo(other)
            }
        }
    }

    describe("mutable constructors") {
        on("empty") {
            val color = MutableColor { }
            it("should be the transparent black Color") {
                assert(color).isEqualTo(Color.transparentBlack)
            }
            it("should have the observer set") {
                assert(color.observer).isNotNull()
            }
        }
        on("primary") {
            val r = getRandomValue()
            val g = getRandomValue()
            val b = getRandomValue()
            val a = getRandomValue()
            val color = MutableColor(r, g, b, a) { }
            it("should have r set") {
                assert(color.r).isCloseTo(r)
            }
            it("should have g set") {
                assert(color.g).isCloseTo(g)
            }
            it("should have b set") {
                assert(color.b).isCloseTo(b)
            }
            it("should have a set") {
                assert(color.a).isCloseTo(a)
            }
            it("should have the observer set") {
                assert(color.observer).isNotNull()
            }
        }
        on("Vector4") {
            val other = getRandomColor()
            val color = MutableColor(other) { }
            it("should be equal to other") {
                assert(color).isEqualTo(other)
            }
            it("should have the observer set") {
                assert(color.observer).isNotNull()
            }
        }
    }

    describe("premade colors") {
        on("transparent black") {
            val color = Color.transparentBlack
            it("x should be 0") {
                assert(color.r).isCloseTo(0f)
            }
            it("y should be 0") {
                assert(color.g).isCloseTo(0f)
            }
            it("z should be 0") {
                assert(color.b).isCloseTo(0f)
            }
            it("w should be 0") {
                assert(color.a).isCloseTo(0f)
            }
        }
        on("transparent white") {
            val color = Color.transparentWhite
            it("x should be 1") {
                assert(color.r).isCloseTo(1f)
            }
            it("y should be 1") {
                assert(color.g).isCloseTo(1f)
            }
            it("z should be 1") {
                assert(color.b).isCloseTo(1f)
            }
            it("w should be 0") {
                assert(color.a).isCloseTo(0f)
            }
        }
        on("white") {
            val color = Color.white
            it("x should be 1") {
                assert(color.r).isCloseTo(1f)
            }
            it("y should be 1") {
                assert(color.g).isCloseTo(1f)
            }
            it("z should be 1") {
                assert(color.b).isCloseTo(1f)
            }
            it("w should be 1") {
                assert(color.a).isCloseTo(1f)
            }
        }
    }

    given("a Color") {
        val color by memoized { getRandomColor() }

        on("copyImmutable") {
            val copy = color.copyImmutable()
            it("should be a new instance") {
                assert(copy).isNotSameAs(color)
            }
            it("should be equal to the original") {
                assert(copy).isEqualTo(color)
            }
            it("should be immutable") {
                assert(copy).isInstanceOf(Color::class)
            }
        }

        on("copyMutable") {
            val copy = color.copyMutable()
            it("should be a new instance") {
                assert(copy).isNotSameAs(color)
            }
            it("should be equal to the original") {
                assert(copy).isEqualTo(color)
            }
            it("should be mutable") {
                assert(copy).isInstanceOf(MutableColor::class)
            }
        }
    }

    given("a mutable Color") {
        var counter = 0
        val color by memoized { getRandomMutableColor { counter++ } }

        describe("seters") {
            on("r") {
                counter = 0
                val value = getRandomValue()
                color.r = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have r set") {
                    assert(color.r).isCloseTo(value)
                }
            }

            on("g") {
                counter = 0
                val value = getRandomValue()
                color.g = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have g set") {
                    assert(color.g).isCloseTo(value)
                }
            }

            on("b") {
                counter = 0
                val value = getRandomValue()
                color.b = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have b set") {
                    assert(color.b).isCloseTo(value)
                }
            }

            on("a") {
                counter = 0
                val value = getRandomValue()
                color.a = value
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have w set") {
                    assert(color.a).isCloseTo(value)
                }
            }

            on("Color") {
                counter = 0
                val other = getRandomColor()
                color.set(other)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should be equal to other") {
                    assert(color).isEqualTo(other)
                }
            }

            on("set") {
                counter = 0
                val r = getRandomValue()
                val g = getRandomValue()
                val b = getRandomValue()
                val a = getRandomValue()
                color.set(r, g, b, a)
                it("should have notified once") {
                    assert(counter).isEqualTo(1)
                }
                it("should have r set") {
                    assert(color.r).isCloseTo(r)
                }
                it("should have g set") {
                    assert(color.g).isCloseTo(g)
                }
                it("should have b set") {
                    assert(color.b).isCloseTo(b)
                }
                it("should have a set") {
                    assert(color.a).isCloseTo(a)
                }
            }

            on("operator") {
                counter = 0
                val r = getRandomValue()
                val g = getRandomValue()
                val b = getRandomValue()
                val a = getRandomValue()
                color[0] = r
                color[1] = g
                color[2] = b
                color[3] = a
                it("should have notified four times") {
                    assert(counter).isEqualTo(4)
                }
                it("should have r set") {
                    assert(color.r).isCloseTo(r)
                }
                it("should have g set") {
                    assert(color.g).isCloseTo(g)
                }
                it("should have b set") {
                    assert(color.b).isCloseTo(b)
                }
                it("should have a set") {
                    assert(color.a).isCloseTo(a)
                }
            }
        }
    }
})

private fun getRandomValue() = random(0f, 1f)
private fun getRandomColor() = Color(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue())
private fun getRandomMutableColor(observer: ((Color) -> Unit)) = MutableColor(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(), observer)