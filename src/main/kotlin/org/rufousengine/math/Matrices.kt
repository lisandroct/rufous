package org.rufousengine.math

/** Whether all components are zero. */
val M.isZero: Boolean
    get() = components.all { it.isZero() }

// ---



// ---

/**
 * Transposes this matrix.
 *
 * @param[out] The output matrix.
 * @return The output matrix for chaining.
 */
fun P.transpose() = set(
        e00,
        e11,
        e22, e32,
        e23, e33
)