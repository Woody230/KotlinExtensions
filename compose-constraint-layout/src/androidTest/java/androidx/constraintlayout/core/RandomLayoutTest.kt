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
package androidx.constraintlayout.core

import androidx.constraintlayout.core.scout.Scout.inferConstraints
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import androidx.constraintlayout.core.widgets.Guideline
import org.junit.Assert
import kotlin.test.Test
import java.util.*

/**
 * This test creates a random set of non overlapping rectangles uses the scout
 * to add a sequence of constraints. Verify that the constraint engine will then layout the
 * rectangles to within 12 pixels.
 * It uses
 */
class RandomLayoutTest {
    /* TODO rectangle
    @Test
    fun testRandomLayouts() {
        val r = Random(4567890)
        for (test in 0 until LOOP_FOR) {
            val seed = r.nextLong()
            println("seed = $seed")
            val list: ArrayList<Rectangle> = random(seed, MAX_WIDGETS, PERCENT_BIG_WIDGETS, LAYOUT_WIDTH, LAYOUT_HEIGHT)
            val root = ConstraintWidgetContainer(0, 0, LAYOUT_WIDTH, LAYOUT_HEIGHT)
            root.verticalDimensionBehaviour = DimensionBehaviour.FIXED
            root.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
            root.width = LAYOUT_WIDTH
            root.height = LAYOUT_HEIGHT
            var k = 0
            for (rec in list) {
                val widget = ConstraintWidget()
                widget.type = "TextView"
                val text = "TextView" + k++
                widget.debugName = text
                widget.setOrigin(rec.x, rec.y)
                widget.width = widget.minWidth
                widget.height = widget.minHeight
                widget.width = widget.width
                widget.height = widget.height
                widget.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
                widget.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
                root.add(widget)
                widget.x = rec.x
                widget.y = rec.y
                if (widget.minWidth < rec.width) {
                    widget.minWidth = rec.width
                }
                if (widget.minHeight < rec.height) {
                    widget.minHeight = rec.height
                }
                widget.setDimension(rec.width, rec.height)
                //                widget.setWrapHeight(rec.height);
//                widget.setWrapHeight(rec.width);
            }
            val widgetList = root.children
            inferConstraints(root)
            for (widget in widgetList) {
                widget.setDimension(10, 10)
                widget.setOrigin(10, 10)
            }
            var allOk = true
            root.layout()
            var layout = "\n"
            var ok = true
            for (i in widgetList.indices) {
                val widget = widgetList[i]
                val rect: Rectangle = list[i]
                ok = isSame(dim(widget), dim(rect))
                allOk = allOk and ok
                layout += """
                    ${rightPad(dim(widget), 15)}${if (ok) " == " else " != "}${dim(rect)}
                    
                    """.trimIndent()
            }
            Assert.assertTrue(layout, allOk)
        }
    }

    /**
     * Compare two string containing comer separated integers
     *
     * @param a
     * @param b
     * @return
     */
    private fun isSame(a: String?, b: String?): Boolean {
        if (a == null || b == null) {
            return false
        }
        val a_split = a.split(",".toRegex()).toTypedArray()
        val b_split = b.split(",".toRegex()).toTypedArray()
        if (a_split.size != b_split.size) {
            return false
        }
        for (i in a_split.indices) {
            if (a_split[i].length == 0) {
                return false
            }
            var error = ALLOWED_POSITION_ERROR
            if (b_split[i].startsWith("+")) {
                error += 10
            }
            val a_value = a_split[i].toInt()
            val b_value = b_split[i].toInt()
            if (Math.abs(a_value - b_value) > error) {
                return false
            }
        }
        return true
    }

    fun dim(r: Rectangle): String {
        return r.x.toString() + "," + r.y + "," + r.width + "," + r.height
    }

    fun dim(w: ConstraintWidget): String {
        if (w is Guideline) {
            return w.left.toString() + "," + w.top + "," + 0 + "," + 0
        }
        return if (w.visibility == ConstraintWidget.GONE) {
            0.toString() + "," + 0 + "," + 0 + "," + 0
        } else w.left.toString() + "," + w.top + "," + w.width + "," + w.height
    }

    companion object {
        private const val ALLOWED_POSITION_ERROR = 12
        const val MIN_WIDTH = 100
        const val MIN_HEIGHT = 40
        const val MIN_GAP = 40
        const val MAX_TRIES = 100
        const val LAYOUT_WIDTH = 1024
        const val LAYOUT_HEIGHT = 512
        const val MAX_WIDGETS = 20
        const val PERCENT_BIG_WIDGETS = 70
        const val LOOP_FOR = 1000

        /**
         * Create a collection of rectangles
         *
         * @param count     the number of rectangles to try and generate
         * @param sizeRatio 0 = all small ones, 100 = all big ones
         * @param width     the width of the bounding rectangle
         * @param height    the height of the bounding rectangle
         * @return
         */
        fun random(seed: Long, count: Int, sizeRatio: Int, width: Int, height: Int): ArrayList<Rectangle> {
            val recs: ArrayList<Rectangle> = ArrayList<Rectangle>()
            val minWidth = MIN_WIDTH
            val minHeight = MIN_HEIGHT
            val minGap = MIN_GAP
            val gapBy2 = MIN_GAP * 2
            val rand = Random(seed)
            val test = Rectangle()
            for (i in 0 until count) {
                val rn = Rectangle()
                var found = false
                var attempt = 0
                while (!found) {
                    if (rand.nextInt(100) < sizeRatio) {
                        rn.x = rand.nextInt(width - minWidth - gapBy2) + minGap
                        rn.y = rand.nextInt(height - minHeight - gapBy2) + minGap
                        rn.width = minWidth + rand.nextInt(width - rn.x - minWidth - minGap)
                        rn.height = minHeight + rand.nextInt(height - rn.y - minHeight - minGap)
                    } else {
                        rn.x = rand.nextInt(width - minWidth - gapBy2) + minGap
                        rn.y = rand.nextInt(height - minHeight - gapBy2) + minGap
                        rn.width = minWidth
                        rn.height = minHeight
                    }
                    test.x = rn.x - minGap
                    test.y = rn.y - minGap
                    test.width = rn.width + gapBy2
                    test.height = rn.height + gapBy2
                    found = true
                    val size = recs.size
                    for (j in 0 until size) {
                        if (recs[j].intersects(test)) {
                            found = false
                            break
                        }
                    }
                    attempt++
                    if (attempt > MAX_TRIES) {
                        break
                    }
                }
                if (found) {
                    recs.add(rn)
                }
            }
            return recs
        }

        private fun rightPad(s: String, n: Int): String {
            var s = s
            s = s + String(ByteArray(n)).replace('\u0000', ' ')
            return s.substring(0, n)
        }
    }*/
}