/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.constraintlayout.core.scout

import androidx.constraintlayout.core.widgets.WidgetContainer
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.Guideline
import androidx.constraintlayout.core.widgets.ConstraintAnchor
import java.util.*

/**
 * Main Wrapper class for Constraint Widgets
 */
class ScoutWidget(constraintWidget: ConstraintWidget, parent: ScoutWidget?) : Comparable<ScoutWidget> {
    /**
     * simple accessor for the X position
     *
     * @return the X position of the widget
     */
    var x: Float
        private set

    /**
     * simple accessor for the Y position
     *
     * @return the Y position of the widget
     */
    var y: Float
        private set

    /**
     * simple accessor for the width
     *
     * @return the width of the widget
     */
    var width: Float
        private set

    /**
     * simple accessor for the height
     *
     * @return the height of the widget
     */
    var height: Float
        private set
    private val mBaseLine: Float
    val parent: ScoutWidget?
    private var mRootDistance = 0f
    private val mDistToRootCache = floatArrayOf(-1f, -1f, -1f, -1f)
    var mConstraintWidget: ConstraintWidget?
    private val mKeepExistingConnections = true
    //private var mRectangle: Rectangle? = null

    /**
     * Sets the order root first
     * followed by outside to inside, top to bottom, left to right
     *
     * @param scoutWidget
     * @return
     */
    override fun compareTo(scoutWidget: ScoutWidget): Int {
        if (parent == null) {
            return -1
        }
        if (mRootDistance != scoutWidget.mRootDistance) {
            return java.lang.Float.compare(mRootDistance, scoutWidget.mRootDistance)
        }
        if (y != scoutWidget.y) {
            return java.lang.Float.compare(y, scoutWidget.y)
        }
        return if (x != scoutWidget.x) {
            java.lang.Float.compare(x, scoutWidget.x)
        } else 0
    }

    override fun toString(): String {
        return mConstraintWidget!!.debugName!!
    }

    val isRoot: Boolean
        get() = parent == null

    /**
     * is this a guideline
     *
     * @return
     */
    val isGuideline: Boolean
        get() = mConstraintWidget is Guideline

    /**
     * is guideline vertical
     *
     * @return
     */
    val isVerticalGuideline: Boolean
        get() {
            if (mConstraintWidget is Guideline) {
                val g = mConstraintWidget as Guideline
                return g.orientation == Guideline.VERTICAL
            }
            return false
        }

    /**
     * is this a horizontal guide line on the image
     *
     * @return
     */
    val isHorizontalGuideline: Boolean
        get() {
            if (mConstraintWidget is Guideline) {
                val g = mConstraintWidget as Guideline
                return g.orientation == Guideline.HORIZONTAL
            }
            return false
        }

    // above = 0, below = 1, left = 2, right = 3
    fun getLocation(dir: Direction?): Float {
        when (dir) {
            Direction.NORTH -> return y
            Direction.SOUTH -> return y + height
            Direction.WEST -> return x
            Direction.EAST -> return x + width
            Direction.BASE -> return mBaseLine
        }
        return mBaseLine
    }

    /**
     * set a centered constraint if possible return true if it did
     *
     * @param dir   direction 0 = vertical
     * @param to1   first widget  to connect to
     * @param to2   second widget to connect to
     * @param cDir1 the side of first widget to connect to
     * @param cDir2 the sed of the second widget to connect to
     * @param gap   the gap
     * @return true if it was able to connect
     */
    fun setCentered(
        dir: Int, to1: ScoutWidget?, to2: ScoutWidget?, cDir1: Direction?, cDir2: Direction?,
        gap: Float
    ): Boolean {
        var gap = gap
        val ori = if (dir == 0) Direction.NORTH else Direction.WEST
        val anchor1 = mConstraintWidget!!.getAnchor(lookupType(ori))
        val anchor2 = mConstraintWidget!!.getAnchor(lookupType(ori.opposite))
        if (mKeepExistingConnections && (anchor1!!.isConnected || anchor2!!.isConnected)) {
            if (anchor1.isConnected xor anchor2!!.isConnected) {
                return false
            }
            if (anchor1.isConnected
                && anchor1.target!!.owner !== to1!!.mConstraintWidget
            ) {
                return false
            }
            if (anchor2.isConnected
                && anchor2.target!!.owner !== to2!!.mConstraintWidget
            ) {
                return false
            }
        }
        return if (anchor1!!.isConnectionAllowed(to1!!.mConstraintWidget!!) &&
            anchor2!!.isConnectionAllowed(to2!!.mConstraintWidget!!)
        ) {
            // Resize
            if (!isResizable(dir)) {
                if (dir == 0) {
                    val height = mConstraintWidget!!.height
                    val stretchRatio = gap * 2 / height.toFloat()
                    if (isCandidateResizable(dir) && stretchRatio < MAXIMUM_STRETCH_GAP) {
                        mConstraintWidget!!.verticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
                    } else {
                        gap = 0f
                    }
                } else {
                    val width = mConstraintWidget!!.width
                    val stretchRatio = gap * 2 / width.toFloat()
                    if (isCandidateResizable(dir) && stretchRatio < MAXIMUM_STRETCH_GAP) {
                        mConstraintWidget!!.horizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
                    } else {
                        gap = 0f
                    }
                }
            }
            if (to1 == to2) {
                connect(
                    mConstraintWidget, lookupType(cDir1), to1.mConstraintWidget,
                    lookupType(cDir1), gap.toInt()
                )
                connect(
                    mConstraintWidget, lookupType(cDir2), to2.mConstraintWidget,
                    lookupType(cDir2), gap.toInt()
                )
            } else {
                val pos1 = to1.getLocation(cDir1)
                val pos2 = to2.getLocation(cDir2)
                val c1 = if (pos1 < pos2) ori else ori.opposite
                val c2 = if (pos1 > pos2) ori else ori.opposite
                val gap1 = gap(mConstraintWidget, c1, to1.mConstraintWidget, cDir1)
                val gap2 = gap(mConstraintWidget, c2, to2.mConstraintWidget, cDir2)
                connect(
                    mConstraintWidget, lookupType(c1), to1.mConstraintWidget, lookupType(cDir1),
                    Math.max(0, gap1)
                )
                connect(
                    mConstraintWidget, lookupType(c2), to2.mConstraintWidget, lookupType(cDir2),
                    Math.max(0, gap2)
                )
            }
            true
        } else {
            false
        }
    }

    /**
     * set a centered constraint if possible return true if it did
     *
     * @param dir   direction 0 = vertical
     * @param to1   first widget  to connect to
     * @param cDir1 the side of first widget to connect to
     * @return true if it was able to connect
     */
    fun setEdgeCentered(dir: Int, to1: ScoutWidget?, cDir1: Direction?): Boolean {
        val ori = if (dir == 0) Direction.NORTH else Direction.WEST
        val anchor1 = mConstraintWidget!!.getAnchor(lookupType(ori))
        val anchor2 = mConstraintWidget!!.getAnchor(lookupType(ori.opposite))
        if (mKeepExistingConnections && (anchor1!!.isConnected || anchor2!!.isConnected)) {
            if (anchor1.isConnected xor anchor2!!.isConnected) {
                return false
            }
            if (anchor1.isConnected
                && anchor1.target!!.owner !== to1!!.mConstraintWidget
            ) {
                return false
            }
        }
        if (anchor1!!.isConnectionAllowed(to1!!.mConstraintWidget!!)) {
            connect(
                mConstraintWidget, lookupType(ori), to1.mConstraintWidget, lookupType(cDir1),
                0
            )
            connect(
                mConstraintWidget, lookupType(ori.opposite), to1.mConstraintWidget,
                lookupType(cDir1),
                0
            )
        }
        return true
    }

    /**
     * set a constraint if possible return true if it did
     *
     * @param dir  the direction of the connection
     * @param to   the widget to connect to
     * @param cDir the direction of
     * @param gap
     * @return false if unable to apply
     */
    fun setConstraint(dir: Int, to: ScoutWidget?, cDir: Int, gap: Float): Boolean {
        var cDir = cDir
        val anchorType = lookupType(dir)
        if (to!!.isGuideline) {
            cDir = cDir and 0x2
        }
        val anchor = mConstraintWidget!!.getAnchor(anchorType)
        if (mKeepExistingConnections) {
            if (anchor!!.isConnected) {
                return if (anchor.target!!.owner !== to.mConstraintWidget) {
                    false
                } else true
            }
            if (dir == Direction.BASE.direction) {
                if (mConstraintWidget!!.getAnchor(ConstraintAnchor.Type.BOTTOM)!!.isConnected) {
                    return false
                }
                if (mConstraintWidget!!.getAnchor(ConstraintAnchor.Type.TOP)!!.isConnected) {
                    return false
                }
            } else if (dir == Direction.NORTH.direction) {
                if (mConstraintWidget!!.getAnchor(ConstraintAnchor.Type.BOTTOM)!!.isConnected) {
                    return false
                }
                if (mConstraintWidget!!.getAnchor(ConstraintAnchor.Type.BASELINE)!!.isConnected) {
                    return false
                }
            } else if (dir == Direction.SOUTH.direction) {
                if (mConstraintWidget!!.getAnchor(ConstraintAnchor.Type.TOP)!!.isConnected) {
                    return false
                }
                if (mConstraintWidget!!.getAnchor(ConstraintAnchor.Type.BASELINE)!!.isConnected) {
                    return false
                }
            } else if (dir == Direction.WEST.direction) {
                if (mConstraintWidget!!.getAnchor(ConstraintAnchor.Type.RIGHT)!!.isConnected) {
                    return false
                }
            } else if (dir == Direction.EAST.direction) {
                if (mConstraintWidget!!.getAnchor(ConstraintAnchor.Type.LEFT)!!.isConnected) {
                    return false
                }
            }
        }
        return if (anchor!!.isConnectionAllowed(to.mConstraintWidget!!)) {
            connect(
                mConstraintWidget, lookupType(dir), to.mConstraintWidget, lookupType(cDir),
                gap.toInt()
            )
            true
        } else {
            false
        }
    }

    /**
     * set a Weak constraint if possible return true if it did
     *
     * @param dir  the direction of the connection
     * @param to   the widget to connect to
     * @param cDir the direction of
     * @return false if unable to apply
     */
    fun setWeakConstraint(dir: Int, to: ScoutWidget?, cDir: Int): Boolean {
        val anchor = mConstraintWidget!!.getAnchor(lookupType(dir))
        val gap = 8f
        if (mKeepExistingConnections && anchor!!.isConnected) {
            return if (anchor.target!!.owner !== to!!.mConstraintWidget) {
                false
            } else true
        }
        return if (anchor!!.isConnectionAllowed(to!!.mConstraintWidget!!)) {
            if (DEBUG) {
                println(
                    "WEAK CONSTRAINT " + mConstraintWidget + " to " + to.mConstraintWidget
                )
            }
            connectWeak(
                mConstraintWidget, lookupType(dir), to.mConstraintWidget, lookupType(cDir),
                gap.toInt()
            )
            true
        } else {
            false
        }
    }

    /**
     * Return true if the widget is a candidate to be marked
     * as resizable (ANY) -- i.e. if the current dimension is bigger than its minimum.
     *
     * @param dimension the dimension (vertical == 0, horizontal == 1) we are looking at
     * @return true if the widget is a good candidate for resize
     */
    fun isCandidateResizable(dimension: Int): Boolean {
        return if (dimension == 0) {
            (mConstraintWidget!!.verticalDimensionBehaviour ===
                    ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
                    || (mConstraintWidget!!.verticalDimensionBehaviour ===
                    ConstraintWidget.DimensionBehaviour.FIXED
                    && mConstraintWidget!!.height > mConstraintWidget!!.minHeight))
        } else {
            (mConstraintWidget!!.horizontalDimensionBehaviour ===
                    ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
                    || (mConstraintWidget!!.horizontalDimensionBehaviour ===
                    ConstraintWidget.DimensionBehaviour.FIXED
                    && mConstraintWidget!!.width > mConstraintWidget!!.minWidth))
        }
    }

    fun isResizable(horizontal: Int): Boolean {
        return if (horizontal == 0) {
            mConstraintWidget!!.verticalDimensionBehaviour ===
                    ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
        } else {
            mConstraintWidget!!.horizontalDimensionBehaviour ===
                    ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
        }
    }

    fun hasBaseline(): Boolean {
        return mConstraintWidget!!.hasBaseline()
    }

    /**
     * Gets the neighbour in that direction or root
     * TODO better support for large widgets with several neighbouring widgets
     *
     * @param dir
     * @param list
     * @return
     */
    fun getNeighbor(dir: Direction?, list: Array<ScoutWidget>): ScoutWidget? {
        var neigh: ScoutWidget? = list[0]
        var minDist = Float.MAX_VALUE
        return when (dir) {
            Direction.WEST -> {
                val ay1 = getLocation(Direction.NORTH)
                val ay2 = getLocation(Direction.SOUTH)
                val ax = getLocation(Direction.WEST)
                var i = 1
                while (i < list.size) {
                    val iw = list[i]
                    if (iw === this) {
                        i++
                        continue
                    }
                    val by1 = iw.getLocation(Direction.NORTH)
                    val by2 = iw.getLocation(Direction.SOUTH)
                    if (Math.max(ay1, by1) <= Math.min(ay2, by2)) { // overlap
                        val bx = iw.getLocation(Direction.EAST)
                        if (bx < ax && ax - bx < minDist) {
                            minDist = ax - bx
                            neigh = iw
                        }
                    }
                    i++
                }
                neigh
            }
            Direction.EAST -> {
                val ay1 = getLocation(Direction.NORTH)
                val ay2 = getLocation(Direction.SOUTH)
                val ax = getLocation(Direction.EAST)
                var i = 1
                while (i < list.size) {
                    val iw = list[i]
                    if (iw === this) {
                        i++
                        continue
                    }
                    val by1 = iw.getLocation(Direction.NORTH)
                    val by2 = iw.getLocation(Direction.SOUTH)
                    if (Math.max(ay1, by1) <= Math.min(ay2, by2)) { // overlap
                        val bx = iw.getLocation(Direction.WEST)
                        if (bx > ax && bx - ax < minDist) {
                            minDist = bx - ax
                            neigh = iw
                        }
                    }
                    i++
                }
                neigh
            }
            Direction.SOUTH -> {
                val ax1 = getLocation(Direction.WEST)
                val ax2 = getLocation(Direction.EAST)
                val ay = getLocation(Direction.SOUTH)
                var i = 1
                while (i < list.size) {
                    val iw = list[i]
                    if (iw === this) {
                        i++
                        continue
                    }
                    val bx1 = iw.getLocation(Direction.WEST)
                    val bx2 = iw.getLocation(Direction.EAST)
                    if (Math.max(ax1, bx1) <= Math.min(ax2, bx2)) { // overlap
                        val by = iw.getLocation(Direction.NORTH)
                        if (by > ay && by - ay < minDist) {
                            minDist = by - ay
                            neigh = iw
                        }
                    }
                    i++
                }
                neigh
            }
            Direction.NORTH -> {
                val ax1 = getLocation(Direction.WEST)
                val ax2 = getLocation(Direction.EAST)
                val ay = getLocation(Direction.NORTH)
                var i = 1
                while (i < list.size) {
                    val iw = list[i]
                    if (iw === this) {
                        i++
                        continue
                    }
                    val bx1 = iw.getLocation(Direction.WEST)
                    val bx2 = iw.getLocation(Direction.EAST)
                    if (Math.max(ax1, bx1) <= Math.min(ax2, bx2)) { // overlap
                        val by = iw.getLocation(Direction.SOUTH)
                        if (ay > by && ay - by < minDist) {
                            minDist = ay - by
                            neigh = iw
                        }
                    }
                    i++
                }
                neigh
            }
            Direction.BASE -> null
            else -> null
        }
    }

    /**
     * is the widet connected in that direction
     *
     * @param direction
     * @return true if connected
     */
    fun isConnected(direction: Direction?): Boolean {
        return mConstraintWidget!!.getAnchor(lookupType(direction))!!.isConnected
    }

    /**
     * is the distance to the Root Cached
     *
     * @param direction
     * @return true if distance to root has been cached
     */
    private fun isDistanceToRootCache(direction: Direction): Boolean {
        val directionOrdinal = direction.direction
        val f = mDistToRootCache[directionOrdinal]
        return if (f < 0) {  // depends on any comparison involving Float.NaN returns false
            false
        } else true
    }

    /**
     * Get the cache distance to the root
     *
     * @param d
     * @param value
     */
    private fun cacheRootDistance(d: Direction, value: Float) {
        mDistToRootCache[d.direction] = value
    }

    /**
     * get distance to the container in a direction
     * caches the distance
     *
     * @param list      list of widgets (container is list[0]
     * @param direction direction to check in
     * @return distance root or NaN if no connection available
     */
    fun connectedDistanceToRoot(list: Array<ScoutWidget>, direction: Direction): Float {
        val value = recursiveConnectedDistanceToRoot(list, direction)
        cacheRootDistance(direction, value)
        return value
    }

    /**
     * Walk the widget connections to get the distance to the container in a direction
     *
     * @param list      list of widgets (container is list[0]
     * @param direction direction to check in
     * @return distance root or NaN if no connection available
     */
    private fun recursiveConnectedDistanceToRoot(list: Array<ScoutWidget>, direction: Direction): Float {
        if (isDistanceToRootCache(direction)) {
            return mDistToRootCache[direction.direction]
        }
        val anchorType = lookupType(direction)
        val anchor = mConstraintWidget!!.getAnchor(anchorType)
        if (anchor == null || !anchor.isConnected) {
            return Float.NaN
        }
        var margin = anchor.margin.toFloat()
        val toAnchor = anchor.target
        val toWidget = toAnchor!!.owner
        if (list[0].mConstraintWidget === toWidget) { // found the base return;
            return margin
        }

        // if atached to the same side
        if (toAnchor.type === anchorType) {
            for (scoutWidget in list) {
                if (scoutWidget.mConstraintWidget === toWidget) {
                    val dist = scoutWidget.recursiveConnectedDistanceToRoot(list, direction)
                    scoutWidget.cacheRootDistance(direction, dist)
                    return margin + dist
                }
            }
        }
        // if atached to the other side (you will need to add the length of the widget
        if (toAnchor.type === lookupType(direction.opposite)) {
            for (scoutWidget in list) {
                if (scoutWidget.mConstraintWidget === toWidget) {
                    margin += scoutWidget.getLength(direction)
                    val dist = scoutWidget.recursiveConnectedDistanceToRoot(list, direction)
                    scoutWidget.cacheRootDistance(direction, dist)
                    return margin + dist
                }
            }
        }
        return Float.NaN
    }

    /**
     * Get size of widget
     *
     * @param direction the direction north/south gets height east/west gets width
     * @return size of widget in that dimension
     */
    private fun getLength(direction: Direction): Float {
        return when (direction) {
            Direction.NORTH, Direction.SOUTH -> height
            Direction.EAST, Direction.WEST -> width
            else -> 0f
        }
    }

    /**
     * is the widget centered
     *
     * @param orientationVertical 1 = checking if vertical
     * @return true if centered
     */
    fun isCentered(orientationVertical: Int): Boolean {
        if (isGuideline) return false
        return if (orientationVertical == Direction.Companion.ORIENTATION_VERTICAL) {
            mConstraintWidget!!.getAnchor(ConstraintAnchor.Type.TOP)!!.isConnected &&
                    mConstraintWidget!!.getAnchor(ConstraintAnchor.Type.BOTTOM)!!.isConnected
        } else mConstraintWidget!!.getAnchor(ConstraintAnchor.Type.LEFT)!!.isConnected &&
                mConstraintWidget!!.getAnchor(ConstraintAnchor.Type.RIGHT)!!.isConnected
    }

    fun hasConnection(dir: Direction?): Boolean {
        val anchor = mConstraintWidget!!.getAnchor(lookupType(dir))
        return anchor != null && anchor.isConnected
    }

    /*
    val rectangle: Rectangle
        get() {
            if (mRectangle == null) {
                mRectangle = Rectangle()
            }
            mRectangle.x = mConstraintWidget!!.x
            mRectangle.y = mConstraintWidget!!.y
            mRectangle.width = mConstraintWidget!!.width
            mRectangle.height = mConstraintWidget!!.height
            return mRectangle
        }

    /**
     * Calculate the gap in to the nearest widget
     *
     * @param direction  the direction to check
     * @param list list of other widgets (root == list[0])
     * @return the distance on that side
     */
    fun gap(direction: Direction?, list: Array<ScoutWidget>): Int {
        val rootWidth = list[0].mConstraintWidget!!.width
        val rootHeight = list[0].mConstraintWidget!!.height
        val rect = Rectangle()
        when (direction) {
            Direction.NORTH -> {
                rect.y = 0
                rect.x = mConstraintWidget!!.x + 1
                rect.width = mConstraintWidget!!.width - 2
                rect.height = mConstraintWidget!!.y
            }
            Direction.SOUTH -> {
                rect.y = mConstraintWidget!!.y + mConstraintWidget!!.height
                rect.x = mConstraintWidget!!.x + 1
                rect.width = mConstraintWidget!!.width - 2
                rect.height = rootHeight - rect.y
            }
            Direction.WEST -> {
                rect.y = mConstraintWidget!!.y + 1
                rect.x = 0
                rect.width = mConstraintWidget!!.x
                rect.height = mConstraintWidget!!.height - 2
            }
            Direction.EAST -> {
                rect.y = mConstraintWidget!!.y + 1
                rect.x = mConstraintWidget!!.x + mConstraintWidget!!.width
                rect.width = rootWidth - rect.x
                rect.height = mConstraintWidget!!.height - 2
            }
        }
        var min = Int.MAX_VALUE
        for (i in 1 until list.size) {
            val scoutWidget = list[i]
            if (scoutWidget === this) {
                continue
            }
            val r: Rectangle = scoutWidget.rectangle
            if (r.intersects(rect)) {
                val dist = distance(scoutWidget, this).toInt()
                if (min > dist) {
                    min = dist
                }
            }
        }
        if (min > Math.max(rootHeight, rootWidth)) {
            when (direction) {
                Direction.NORTH -> return mConstraintWidget!!.y
                Direction.SOUTH -> return rootHeight - (mConstraintWidget!!.y + mConstraintWidget!!.height)
                Direction.WEST -> return mConstraintWidget!!.x
                Direction.EAST -> return rootWidth - (mConstraintWidget!!.x + mConstraintWidget!!.width)
            }
        }
        return min
    }*/

    fun setX(x: Int) {
        mConstraintWidget!!.x = x
        this.x = mConstraintWidget!!.x.toFloat()
    }

    fun setY(y: Int) {
        mConstraintWidget!!.y = y
        this.y = mConstraintWidget!!.y.toFloat()
    }

    fun setWidth(width: Int) {
        mConstraintWidget!!.width = width
        this.width = mConstraintWidget!!.width.toFloat()
    }

    fun setHeight(height: Int) {
        mConstraintWidget!!.height = height
        this.height = mConstraintWidget!!.height.toFloat()
    }

    fun rootDistanceY(): Int {
        if (mConstraintWidget == null || mConstraintWidget!!.parent == null) {
            return 0
        }
        val rootHeight = mConstraintWidget!!.parent!!.height
        val aY = mConstraintWidget!!.y
        val aHeight = mConstraintWidget!!.height
        return Math.min(aY, rootHeight - (aY + aHeight))
    }

    companion object {
        private const val DEBUG = false
        private const val MAXIMUM_STRETCH_GAP = 0.6f // percentage

        /**
         * Wrap an array of ConstraintWidgets into an array of InferWidgets
         *
         * @param array
         * @return
         */
        fun create(array: Array<ConstraintWidget>): Array<ScoutWidget?> {
            val ret = arrayOfNulls<ScoutWidget>(array.size)
            val root = array[0]
            val rootwidget = ScoutWidget(root, null)
            ret[0] = rootwidget
            var count = 1
            for (i in ret.indices) {
                if (array[i] !== root) {
                    ret[count++] = ScoutWidget(array[i], rootwidget)
                }
            }
            Arrays.sort(ret)
            if (DEBUG) {
                for (i in ret.indices) {
                    println(
                        "[" + i + "] -> " + ret[i]!!.mConstraintWidget + "    " +
                                ret[i]!!.mRootDistance
                    )
                }
            }
            return ret
        }

        /**
         * This calculates a constraint tables and applies them to the widgets
         * TODO break up into creation of a constraint table and apply
         *
         * @param list ordered list of widgets root must be list[0]
         */
        fun computeConstraints(list: Array<ScoutWidget?>) {
            val table = ScoutProbabilities()
            table.computeConstraints(list)
            table.applyConstraints(list)
        }

        private fun lookupType(dir: Int): ConstraintAnchor.Type {
            return lookupType(Direction.Companion.get(dir))
        }

        /**
         * map integer direction to ConstraintAnchor.Type
         *
         * @param dir integer direction
         * @return
         */
        private fun lookupType(dir: Direction?): ConstraintAnchor.Type {
            when (dir) {
                Direction.NORTH -> return ConstraintAnchor.Type.TOP
                Direction.SOUTH -> return ConstraintAnchor.Type.BOTTOM
                Direction.WEST -> return ConstraintAnchor.Type.LEFT
                Direction.EAST -> return ConstraintAnchor.Type.RIGHT
                Direction.BASE -> return ConstraintAnchor.Type.BASELINE
            }
            return ConstraintAnchor.Type.NONE
        }

        /**
         * Get the gap between two specific edges of widgets
         * @param widget1
         * @param direction1
         * @param widget2
         * @param direction2
         * @return distance in dp
         */
        private fun gap(
            widget1: ConstraintWidget?, direction1: Direction,
            widget2: ConstraintWidget?, direction2: Direction?
        ): Int {
            when (direction1) {
                Direction.NORTH, Direction.WEST -> return getPos(widget1, direction1) - getPos(widget2, direction2)
                Direction.SOUTH, Direction.EAST -> return getPos(widget2, direction2) - getPos(widget1, direction1)
            }
            return 0
        }

        /**
         * Get the position of a edge of a widget
         * @param widget
         * @param direction
         * @return
         */
        private fun getPos(widget: ConstraintWidget?, direction: Direction?): Int {
            when (direction) {
                Direction.NORTH -> return widget!!.y
                Direction.SOUTH -> return widget!!.y + widget.height
                Direction.WEST -> return widget!!.x
                Direction.EAST -> return widget!!.x + widget.width
            }
            return 0
        }

        private fun connect(
            fromWidget: ConstraintWidget?, fromType: ConstraintAnchor.Type,
            toWidget: ConstraintWidget?, toType: ConstraintAnchor.Type, gap: Int
        ) {
            fromWidget!!.connect(fromType, toWidget!!, toType, gap)
            //        fromWidget.getAnchor(fromType).setConnectionCreator(ConstraintAnchor.SCOUT_CREATOR);
        }

        private fun connectWeak(
            fromWidget: ConstraintWidget?, fromType: ConstraintAnchor.Type,
            toWidget: ConstraintWidget?, toType: ConstraintAnchor.Type, gap: Int
        ) {
            fromWidget!!.connect(fromType, toWidget!!, toType, gap)
            //        fromWidget.connect(fromType, toWidget, toType, gap, ConstraintAnchor.Strength.WEAK);
//        fromWidget.getAnchor(fromType).setConnectionCreator(ConstraintAnchor.SCOUT_CREATOR);
        }

        /**
         * calculates the distance between two widgets (assumed to be rectangles)
         *
         * @param a
         * @param b
         * @return the distance between two widgets at there closest point to each other
         */
        fun distance(a: ScoutWidget?, b: ScoutWidget?): Float {
            val ax1: Float
            val ax2: Float
            val ay1: Float
            val ay2: Float
            val bx1: Float
            val bx2: Float
            val by1: Float
            val by2: Float
            ax1 = a!!.x
            ax2 = a.x + a.width
            ay1 = a.y
            ay2 = a.y + a.height
            bx1 = b!!.x
            bx2 = b.x + b.width
            by1 = b.y
            by2 = b.y + b.height
            val xdiff11 = Math.abs(ax1 - bx1)
            val xdiff12 = Math.abs(ax1 - bx2)
            val xdiff21 = Math.abs(ax2 - bx1)
            val xdiff22 = Math.abs(ax2 - bx2)
            val ydiff11 = Math.abs(ay1 - by1)
            val ydiff12 = Math.abs(ay1 - by2)
            val ydiff21 = Math.abs(ay2 - by1)
            val ydiff22 = Math.abs(ay2 - by2)
            val xmin = Math.min(Math.min(xdiff11, xdiff12), Math.min(xdiff21, xdiff22))
            val ymin = Math.min(Math.min(ydiff11, ydiff12), Math.min(ydiff21, ydiff22))
            val yOverlap = ay1 <= by2 && by1 <= ay2
            val xOverlap = ax1 <= bx2 && bx1 <= ax2
            val xReturn = if (yOverlap) xmin else Math.hypot(xmin.toDouble(), ymin.toDouble()).toFloat()
            val yReturn = if (xOverlap) ymin else Math.hypot(xmin.toDouble(), ymin.toDouble()).toFloat()
            return Math.min(xReturn, yReturn)
        }

        fun getWidgetArray(base: WidgetContainer): Array<ScoutWidget?> {
            val list = ArrayList(base.children)
            list.add(0, base)
            return create(list.toTypedArray())
        }

        /**
         * Comparator to sort widgets by y
         */
        var sSortY = Comparator<ScoutWidget> { o1, o2 -> o1.mConstraintWidget!!.y - o2.mConstraintWidget!!.y }
    }

    init {
        mConstraintWidget = constraintWidget
        this.parent = parent
        x = constraintWidget.x.toFloat()
        y = constraintWidget.y.toFloat()
        width = constraintWidget.width.toFloat()
        height = constraintWidget.height.toFloat()
        mBaseLine = (mConstraintWidget!!.baselineDistance + constraintWidget.y).toFloat()
        if (parent != null) {
            mRootDistance = distance(parent, this)
        }
    }
}