package org.rufousengine.math

internal object Trigonometry {
    private const val sinBits = 14
    private const val sinMask = (-1 shl sinBits).inv()
    private const val sinCount = sinMask + 1

    private const val radFull = TWO_PI
    private const val degFull = 360f
    private const val radToIndex = sinCount / radFull
    private const val degToIndex = sinCount / degFull

    private val sinTable = FloatArray(sinCount)

    init {
        for (i in 0 until sinCount) {
            sinTable[i] = Math.sin((i + 0.5) / sinCount * radFull).toFloat()
        }

        var i = 0
        while (i < 360) {
            sinTable[(i * degToIndex).toInt() and sinMask] = Math.sin(i * DEGREES_TO_RADIANS.toDouble()).toFloat()
            i += 90
        }
    }

    fun sin(degrees: Float) = sinRad(degrees.toRadians())

    fun cos(degrees: Float) = cosRad(degrees.toRadians())

    fun tan(degrees: Float) = tanRad(degrees.toRadians())

    fun sinRad(radians: Float) = sinTable[(radians * radToIndex).toInt() and sinMask]

    fun cosRad(radians: Float) = sinTable[((radians + PI / 2) * radToIndex).toInt() and sinMask]

    fun tanRad(radians: Float) = sinRad(radians) / cosRad(radians)
}