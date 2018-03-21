package org.rufousengine.math

/**
 * A mutable 4x4 transformation matrix.
 *
 * For a general purpose matrix, see [Matrix4] instead.
 *
 * @constructor Creates the identity matrix.
 */
class MutableTransformation : Transformation {
    val observer: ((Transformation) -> Unit)?

    override var scales: Boolean
        private set
    override var rotates: Boolean
        private set
    override var translates: Boolean
        private set

    constructor(observer: ((Transformation) -> Unit)? = null) : super() {
        this.observer = observer

        scales = false
        rotates = false
        translates = false
    }
    constructor(other: Transformation, observer: ((Transformation) -> Unit)? = null) : super(other) {
        this.observer = observer

        scales = other.scales
        rotates = other.rotates
        translates = other.translates
    }

    private var transposeDirty = true
    private val _transpose by lazy { MutableMatrix4() }
    /** The transpose of this matrix. It creates a new lazy Matrix4 instance. */
    override val transpose : Matrix4
        get() {
            if(transposeDirty) {
                transpose(_transpose)

                transposeDirty = false
            }

            return _transpose
        }
    private var inverseDirty = true
    private val _inverse by lazy { MutableTransformation() }
    /** The inverse of this matrix. It creates a new lazy Matrix4 instance. */
    override val inverse : Transformation
        get() {
            if(inverseDirty) {
                inverse(_inverse)

                inverseDirty = false
            }

            return _inverse
        }

    fun set(other: Transformation) = set(other.e00, other.e01, other.e02, other.e03, other.e10, other.e11, other.e12, other.e13, other.e20, other.e21, other.e22, other.e23)
    /** Do not set values directly unless you know what you're doing. */
    internal fun set(e00: Float, e01: Float, e02: Float, e03: Float, e10: Float, e11: Float, e12: Float, e13: Float, e20: Float, e21: Float, e22: Float, e23: Float) : MutableTransformation {
        if(equals(e00, e01, e02, e03, e10, e11, e12, e13, e20, e21, e22, e23)) {
            return this
        }

        components[0] = e00
        components[1] = e01
        components[2] = e02
        components[3] = e03

        components[4] = e10
        components[5] = e11
        components[6] = e12
        components[7] = e13

        components[8] = e20
        components[9] = e21
        components[10] = e22
        components[11] = e23

        // FIXME("scales will be true when only rotation")
        this.scales = !e00.isOne() || !e11.isOne() || !e22.isOne()
        this.rotates = !e01.isZero() || !e02.isZero() || !e10.isZero() || !e12.isZero() || !e20.isZero() || !e21.isZero()
        this.translates = !e03.isZero() || !e13.isZero() || !e23.isZero()

        transposeDirty = true
        inverseDirty = true

        observer?.invoke(this)

        return this
    }

    operator fun timesAssign(other: Transformation) { multiply(other) }

    /**
     * Sets this matrix to the identity.
     *
     * @return This matrix for chaining.
     */
    fun identity() = set(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f)

    /**
     * Inverts this matrix.
     *
     * @return This matrix for chaining.
     */
    fun inverse() = inverse(this)

    /**
     * Multiplies this matrix with [other].
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiply(other: Transformation) = multiply(other, this)
    /**
     * Multiplies [other] with this matrix.
     *
     * @param[other] The other matrix.
     * @return This matrix for chaining.
     */
    fun multiplyLeft(other: Transformation) = multiplyLeft(other, this)
}