package me.jacoblewis.spaceshooters.gengine.sim

import me.jacoblewis.spaceshooters.gengine.Bounds
import me.jacoblewis.spaceshooters.gengine.Renderable
import kotlin.math.abs
import kotlin.math.pow

interface HitTestable {

    fun <T1 : Renderable, T2 : Renderable> convexHitTest(itemA: T1, itemB: T2): Boolean {
        val hitField = getMyHitField(itemA, itemB) + getOtherHitField(itemA, itemB)
        return (itemB.x - itemA.x).pow(2) + (itemB.y - itemA.y).pow(2) < hitField.pow(2)
    }

    fun <T : Renderable> getMyHitField(myItem: T, otherItem: T): Float {
        val b = myItem.bounds
        val oB = otherItem.bounds
        return when (b) {
            is Bounds.CircularBound -> b.radius
            is Bounds.RectBound -> {
                val slope = abs((otherItem.y - myItem.y) / (otherItem.x - myItem.x))
                slope
            }
            else -> 0f
        }
    }

    fun <T : Renderable> getOtherHitField(myItem: T, otherItem: T): Float {
        val b = myItem.bounds
        val oB = otherItem.bounds
        return when (oB) {
            is Bounds.CircularBound -> oB.radius
            else -> 0f
        }
    }
}