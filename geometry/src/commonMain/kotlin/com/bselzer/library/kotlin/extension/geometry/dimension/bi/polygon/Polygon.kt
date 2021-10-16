package com.bselzer.library.kotlin.extension.geometry.dimension.bi.polygon

import com.bselzer.library.kotlin.extension.geometry.dimension.bi.position.Point

abstract class Polygon(
    /**
     * The points forming the shape.
     */
    internal val points: List<Point>
) {
    internal companion object {
        /**
         * @return the point at the [index] or a default point
         */
        fun List<Point>.extract(index: Int): Point = getOrElse(index) { Point() }
    }
}