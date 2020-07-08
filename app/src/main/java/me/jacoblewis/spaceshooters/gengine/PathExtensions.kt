package me.jacoblewis.spaceshooters.gengine

import android.graphics.Matrix
import android.graphics.Path
import me.jacoblewis.spaceshooters.gengine.PathExtensions.translateMatrix
import me.jacoblewis.spaceshooters.gengine.PathExtensions.translatePath

object PathExtensions {
    internal val translatePath = Path()
    internal val translateMatrix = Matrix()
}

@Synchronized
fun Path.translate(dx: Float, dy: Float): Path {
    translatePath.rewind()
    translateMatrix.reset()
    translateMatrix.postTranslate(dx, dy)
    transform(translateMatrix, translatePath)
    return translatePath
}