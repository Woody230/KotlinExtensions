/*
 * Copyright (C) 2021 The Android Open Source Project
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

import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure
import androidx.constraintlayout.core.state.Dimension
import androidx.constraintlayout.core.state.State
import androidx.constraintlayout.core.widgets.*
import org.junit.Assert
import kotlin.test.Test

class ComposeLayoutsTest {
    @Test
    fun dividerMatchTextHeight_inWrapConstraintLayout_longText() {
        val state = State()
        val parent = state.constraints(State.PARENT)
        state.verticalGuideline("guideline").percent(0.5f)
        state.constraints("box")!!
            .centerHorizontally(parent)
            .centerVertically(parent)
            .startToEnd("guideline")
            .width(Dimension.Suggested(Dimension.WRAP_DIMENSION))
            .height(Dimension.Wrap())
            .view = "box"
        state.constraints("divider")!!
            .centerHorizontally(parent)
            .centerVertically(parent)
            .width(Dimension.Fixed(1))
            .height(Dimension.Percent(0, 0.8f).suggested(0))
            .view = "divider"
        val root = ConstraintWidgetContainer(0, 0, 1080, 1977)
        state.apply(root)
        root.width = 1080
        root.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        val box = state.constraints("box")!!.constraintWidget
        val guideline = state.guideline("guideline", ConstraintWidget.VERTICAL).constraintWidget
        val divider = state.constraints("divider")!!.constraintWidget
        root.debugName = "root"
        box!!.debugName = "box"
        guideline!!.debugName = "guideline"
        divider!!.debugName = "divider"
        root.measurer = sMeasurer
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        //root.setOptimizationLevel(Optimizer.OPTIMIZATION_NONE);
        root.measure(root.optimizationLevel, 0, 0, 0, 0, 0, 0, 0, 0)
        println("root: $root")
        println("box: $box")
        println("guideline: $guideline")
        println("divider: $divider")
        Assert.assertEquals((root.width / 2).toLong(), box.width.toLong())
        Assert.assertEquals((root.width / 2 / 2).toLong(), box.height.toLong())
        Assert.assertEquals(1, divider.width.toLong())
        Assert.assertEquals((box.height * 0.8).toLong(), divider.height.toLong())
    }

    companion object {
        var sMeasurer: BasicMeasure.Measurer = object : BasicMeasure.Measurer {
            override fun measure(widget: ConstraintWidget, measure: BasicMeasure.Measure) {
                val horizontalBehavior = measure.horizontalBehavior
                val verticalBehavior = measure.verticalBehavior
                val horizontalDimension = measure.horizontalDimension
                val verticalDimension = measure.verticalDimension
                println(
                    "Measure (strategy : " + measure.measureStrategy + ") : "
                            + widget.companionWidget
                            + " " + horizontalBehavior + " (" + horizontalDimension + ") x "
                            + verticalBehavior + " (" + verticalDimension + ")"
                )
                if (horizontalBehavior === DimensionBehaviour.FIXED) {
                    measure.measuredWidth = horizontalDimension
                } else if (horizontalBehavior === DimensionBehaviour.MATCH_CONSTRAINT) {
                    measure.measuredWidth = horizontalDimension
                    if (widget.companionWidget == "box" && measure.measureStrategy == BasicMeasure.Measure.SELF_DIMENSIONS) {
                        measure.measuredWidth = 1080
                    }
                } else if (horizontalBehavior === DimensionBehaviour.WRAP_CONTENT) {
                    if (widget.companionWidget == "box") {
                        measure.measuredWidth = 1080
                    }
                }
                if (verticalBehavior === DimensionBehaviour.FIXED) {
                    measure.measuredHeight = verticalDimension
                } else if (verticalBehavior === DimensionBehaviour.WRAP_CONTENT) {
                    if (widget.companionWidget == "box") {
                        measure.measuredHeight = measure.measuredWidth / 2
                    }
                }
                println("Measure widget " + widget.companionWidget + " => " + measure.measuredWidth + " x " + measure.measuredHeight)
            }

            override fun didMeasures() {}
        }
    }
}