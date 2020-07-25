package me.jacoblewis.spaceshooters.gengine

/**
 * Jiggle between the range
 */
fun jiggleR(low: Float = -1f, high: Float = 1f): Float {
    return low + (high - low) * Math.random().toFloat()
}

/**
 * Jiggle between zero and amount
 */
fun jiggle(amount: Float = 1f): Float {
    return jiggleR(0f, amount)
}

/**
 * Jiggle around zero with the given amplitude
 */
fun jiggleW(amplitude: Float): Float {
    return jiggleR(-amplitude, amplitude)
}