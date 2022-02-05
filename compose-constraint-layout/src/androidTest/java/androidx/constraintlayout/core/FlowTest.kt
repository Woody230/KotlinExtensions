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
import androidx.constraintlayout.core.widgets.*
import org.junit.Assert
import kotlin.test.Test

class FlowTest {
    @Test
    fun testFlowBaseline() {
        val root = ConstraintWidgetContainer(0, 0, 1080, 1536)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(20, 15)
        val flow = Flow()
        root.measurer = sMeasurer
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        flow.debugName = "Flow"
        flow.setVerticalAlign(Flow.VERTICAL_ALIGN_BASELINE)
        flow.add(A)
        flow.add(B)
        A.baselineDistance = 15
        flow.height = 30
        flow.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        flow.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        flow.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        flow.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        flow.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        flow.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.add(flow)
        root.add(A)
        root.add(B)
        root.measure(
            Optimizer.OPTIMIZATION_NONE, 0, 0, 0, 0, 0, 0, 0, 0
        )
        root.layout()
        println("a) root: $root")
        println("flow: $flow")
        println("A: $A")
        println("B: $B")
        Assert.assertEquals(flow.width.toLong(), 1080)
        Assert.assertEquals(flow.height.toLong(), 30)
        Assert.assertEquals(flow.top.toLong(), 753)
        Assert.assertEquals(A.left.toLong(), 320)
        Assert.assertEquals(A.top.toLong(), 758)
        Assert.assertEquals(B.left.toLong(), 740)
        Assert.assertEquals(B.top.toLong(), 761)
    }

    @Test
    fun testComplexChain() {
        val root = ConstraintWidgetContainer(0, 0, 1080, 1536)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val flow = Flow()
        root.measurer = sMeasurer
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        flow.debugName = "Flow"
        flow.setWrapMode(Flow.WRAP_CHAIN)
        flow.setMaxElementsWrap(2)
        flow.add(A)
        flow.add(B)
        flow.add(C)
        root.add(flow)
        root.add(A)
        root.add(B)
        root.add(C)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        flow.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        flow.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        flow.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        flow.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        flow.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_PARENT
        flow.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.measure(
            Optimizer.OPTIMIZATION_NONE, 0, 0, 0, 0, 0, 0, 0, 0
        )
        root.layout()
        println("a) root: $root")
        println("flow: $flow")
        println("A: $A")
        println("B: $B")
        println("C: $C")
        Assert.assertEquals(A.width.toLong(), 540)
        Assert.assertEquals(B.width.toLong(), 540)
        Assert.assertEquals(C.width.toLong(), 1080)
        Assert.assertEquals(flow.width.toLong(), root.width.toLong())
        Assert.assertEquals(flow.height.toLong(), (Math.max(A.height, B.height) + C.height).toLong())
        Assert.assertEquals(flow.top.toLong(), 748)
    }

    @Test
    fun testFlowText() {
        val root = ConstraintWidgetContainer(20, 5)
        val A = ConstraintWidget(7, 1)
        val B = ConstraintWidget(6, 1)
        A.debugName = "A"
        B.debugName = "B"
        val flow = Flow()
        flow.debugName = "flow"
        flow.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        flow.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        flow.width = 20
        flow.height = 2
        flow.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        flow.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        flow.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        flow.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        flow.add(A)
        flow.add(B)
        root.add(flow)
        root.add(A)
        root.add(B)
        root.measurer = object : BasicMeasure.Measurer {
            override fun measure(widget: ConstraintWidget, measure: BasicMeasure.Measure) {
                measure.measuredWidth = widget.width
                measure.measuredHeight = widget.height
            }

            override fun didMeasures() {}
        }
        root.measurer = sMeasurer
        root.measure(
            Optimizer.OPTIMIZATION_NONE, 0, 0, 0, 0, 0, 0, 0, 0
        )
        //root.layout();
        println("root: $root")
        println("flow: $flow")
        println("A: $A")
        println("B: $B")
    }

    companion object {
        var sMeasurer: BasicMeasure.Measurer = object : BasicMeasure.Measurer {
            override fun measure(widget: ConstraintWidget, measure: BasicMeasure.Measure) {
                val horizontalBehavior = measure.horizontalBehavior
                val verticalBehavior = measure.verticalBehavior
                val horizontalDimension = measure.horizontalDimension
                val verticalDimension = measure.verticalDimension
                if (widget is VirtualLayout) {
                    val layout = widget
                    var widthMode = BasicMeasure.UNSPECIFIED
                    var heightMode = BasicMeasure.UNSPECIFIED
                    var widthSize = 0
                    var heightSize = 0
                    if (layout.horizontalDimensionBehaviour === DimensionBehaviour.MATCH_PARENT) {
                        widthSize = if (layout.parent != null) layout.parent!!.width else 0
                        widthMode = BasicMeasure.EXACTLY
                    } else if (horizontalBehavior === DimensionBehaviour.FIXED) {
                        widthSize = horizontalDimension
                        widthMode = BasicMeasure.EXACTLY
                    }
                    if (layout.verticalDimensionBehaviour === DimensionBehaviour.MATCH_PARENT) {
                        heightSize = if (layout.parent != null) layout.parent!!.height else 0
                        heightMode = BasicMeasure.EXACTLY
                    } else if (verticalBehavior === DimensionBehaviour.FIXED) {
                        heightSize = verticalDimension
                        heightMode = BasicMeasure.EXACTLY
                    }
                    layout.measure(widthMode, widthSize, heightMode, heightSize)
                    measure.measuredWidth = layout.measuredWidth
                    measure.measuredHeight = layout.measuredHeight
                } else {
                    if (horizontalBehavior === DimensionBehaviour.FIXED) {
                        measure.measuredWidth = horizontalDimension
                    }
                    if (verticalBehavior === DimensionBehaviour.FIXED) {
                        measure.measuredHeight = verticalDimension
                    }
                }
            }

            override fun didMeasures() {}
        }
    }
}