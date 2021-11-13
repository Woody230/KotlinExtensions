package com.bselzer.library.kotlin.extension.geometry.dimension.bi.polygon

import com.bselzer.library.kotlin.extension.geometry.dimension.bi.position.Point2D

abstract class Polygon(
    /**
     * The points forming the shape.
     */
    internal val points: List<Point2D>
)