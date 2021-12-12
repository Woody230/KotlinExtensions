package com.bselzer.ktx.geometry.dimension.bi.polygon

import com.bselzer.ktx.geometry.dimension.bi.position.Point2D

abstract class Polygon(
    /**
     * The points forming the shape.
     */
    internal val points: List<Point2D>
)