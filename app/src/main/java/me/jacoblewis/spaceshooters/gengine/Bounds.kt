package me.jacoblewis.spaceshooters.gengine

import android.graphics.Path

sealed class Bounds {
    data class PathBound(val path: Path, val centerX: Float, val centerY: Float) : Bounds()
    data class CircularBound(val radius: Float) :
        Bounds()

    data class RectBound(
        val width: Float,
        val height: Float,
        val centerX: Float = width / 2,
        val centerY: Float = height / 2
    ) : Bounds()

    object None : Bounds()
}