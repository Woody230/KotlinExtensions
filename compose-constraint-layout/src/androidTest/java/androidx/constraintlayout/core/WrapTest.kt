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

import androidx.constraintlayout.core.widgets.*
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import org.junit.Assert
import kotlin.test.Test

/**
 * Basic wrap test
 */
class WrapTest {
    @Test
    fun testBasic() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("a) root: $root A: $A")
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 100, 0f)
        A.setVerticalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 60, 0f)
        root.layout()
        println("b) root: $root A: $A")
    }

    @Test
    fun testBasic2() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 100, 1f)
        B.setVerticalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 60, 1f)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("root: $root A: $A B: $B C: $C")
        Assert.assertEquals(root.width.toLong(), 200)
        Assert.assertEquals(root.height.toLong(), 40)
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 20, 100, 1f)
        B.setVerticalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 30, 60, 1f)
        root.width = 0
        root.height = 0
        root.layout()
        println("root: $root A: $A B: $B C: $C")
        Assert.assertEquals(root.width.toLong(), 220)
        Assert.assertEquals(root.height.toLong(), 70)
    }

    @Test
    fun testRatioWrap() {
        val root = ConstraintWidgetContainer(0, 0, 100, 600)
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        root.height = 0
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("root: $root A: $A")
        Assert.assertEquals(root.width.toLong(), 100)
        Assert.assertEquals(root.height.toLong(), 100)
        root.height = 600
        root.width = 0
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        root.layout()
        println("root: $root A: $A")
        Assert.assertEquals(root.width.toLong(), 600)
        Assert.assertEquals(root.height.toLong(), 600)
        root.width = 100
        root.height = 600
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root A: $A")
        Assert.assertEquals(root.width.toLong(), 0)
        Assert.assertEquals(root.height.toLong(), 0)
    }

    @Test
    fun testRatioWrap2() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setDimensionRatio("1:1")
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("root: $root A: $A B: $B")
        Assert.assertEquals(root.width.toLong(), 100)
        Assert.assertEquals(root.height.toLong(), 120)
    }

    @Test
    fun testRatioWrap3() {
        val root = ConstraintWidgetContainer(0, 0, 500, 600)
        val A = ConstraintWidget(100, 60)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        root.add(A)
        root.add(B)
        root.add(C)
        A.baselineDistance = 100
        B.baselineDistance = 10
        C.baselineDistance = 10
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        A.verticalBiasPercent = 0f
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.BASELINE, A, ConstraintAnchor.Type.BASELINE)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.BASELINE, B, ConstraintAnchor.Type.BASELINE)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toLong(), 300)
        Assert.assertEquals(A.height.toLong(), 300)
        Assert.assertEquals(B.left.toLong(), 300)
        Assert.assertEquals(B.top.toLong(), 90)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 20)
        Assert.assertEquals(C.left.toLong(), 400)
        Assert.assertEquals(C.top.toLong(), 90)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(C.height.toLong(), 20)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        A.baselineDistance = 10
        root.layout()
        println("root: $root A: $A B: $B C: $C")
        Assert.assertEquals(root.width.toLong(), 220)
        Assert.assertEquals(root.height.toLong(), 20)
    }

    @Test
    fun testGoneChainWrap() {
        val root = ConstraintWidgetContainer(0, 0, 500, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, D, ConstraintAnchor.Type.TOP)
        D.connect(ConstraintAnchor.Type.TOP, C, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("root: $root A: $A B: $B C: $C D: $D")
        Assert.assertEquals(root.height.toLong(), 40)
        A.verticalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("root: $root A: $A B: $B C: $C D: $D")
        Assert.assertEquals(root.height.toLong(), 40)
    }

    @Test
    fun testWrap() {
        val root = ConstraintWidgetContainer(0, 0, 500, 600)
        val A = ConstraintWidget(100, 0)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(100, 40)
        val E = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        E.debugName = "E"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        root.add(E)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        E.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        E.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        E.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("root: $root A: $A B: $B C: $C D: $D E: $E")
        Assert.assertEquals(root.height.toLong(), 80)
        Assert.assertEquals(E.top.toLong(), 30)
    }

    @Test
    fun testWrap2() {
        val root = ConstraintWidgetContainer(0, 0, 500, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        D.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.TOP, C, ConstraintAnchor.Type.BOTTOM, 30)
        A.connect(ConstraintAnchor.Type.BOTTOM, D, ConstraintAnchor.Type.TOP, 40)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root A: $A B: $B C: $C D: $D")
        Assert.assertEquals(C.top.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), (C.bottom + 30).toLong())
        Assert.assertEquals(D.top.toLong(), (A.bottom + 40).toLong())
        Assert.assertEquals(root.height.toLong(), (20 + 30 + 20 + 40 + 20).toLong())
    }

    @Test
    fun testWrap3() {
        val root = ConstraintWidgetContainer(0, 0, 500, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 200)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT, 250)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root A: $A B: $B")
        Assert.assertEquals(root.width.toLong(), (A.width + 200).toLong())
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 250)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.right > root.width, true)
    }

    @Test
    fun testWrap4() {
        val root = ConstraintWidgetContainer(0, 0, 500, 600)
        val A = ConstraintWidget(80, 80)
        val B = ConstraintWidget(60, 60)
        val C = ConstraintWidget(50, 100)
        val barrier1 = Barrier()
        barrier1.barrierType = Barrier.BOTTOM
        val barrier2 = Barrier()
        barrier2.barrierType = Barrier.BOTTOM
        barrier1.add(A)
        barrier1.add(B)
        barrier2.add(C)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        barrier1.debugName = "B1"
        barrier2.debugName = "B2"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(barrier1)
        root.add(barrier2)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, barrier1, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, barrier1, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.TOP, barrier1, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, barrier2, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("A: $A")
        println("B: $B")
        println("C: $C")
        println("B1: $barrier1")
        println("B2: $barrier2")
        Assert.assertEquals(A.top >= 0, true)
        Assert.assertEquals(B.top >= 0, true)
        Assert.assertEquals(C.top >= 0, true)
        Assert.assertEquals(root.height.toLong(), (Math.max(A.height, B.height) + C.height).toLong())
    }

    @Test
    fun testWrap5() {
        val root = ConstraintWidgetContainer(0, 0, 500, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 8)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 8)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 8)
        C.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        D.horizontalBiasPercent = 0.557f
        D.verticalBiasPercent = 0.8f
        D.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        D.horizontalBiasPercent = 0.557f
        D.verticalBiasPercent = 0.28f
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("A: $A")
        println("B: $B")
        println("C: $C")
        println("D: $D")
    }

    @Test
    fun testWrap6() {
        val root = ConstraintWidgetContainer(0, 0, 500, 600)
        val A = ConstraintWidget(100, 20)
        val guideline = Guideline()
        guideline.orientation = ConstraintWidget.VERTICAL
        guideline.setGuidePercent(0.5f)
        root.debugName = "root"
        A.debugName = "A"
        guideline.debugName = "guideline"
        root.add(A)
        root.add(guideline)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 8)
        A.connect(ConstraintAnchor.Type.LEFT, guideline, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("A: $A")
        println("guideline: $guideline")
        Assert.assertEquals(root.width.toLong(), (A.width * 2).toLong())
        Assert.assertEquals(root.height.toLong(), (A.height + 8).toLong())
        Assert.assertEquals(guideline.left.toFloat(), root.width / 2f, 0f)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
    }

    @Test
    fun testWrap7() {
        val root = ConstraintWidgetContainer(0, 0, 500, 600)
        val A = ConstraintWidget(100, 20)
        val divider = ConstraintWidget(1, 20)
        val guideline = Guideline()
        guideline.orientation = ConstraintWidget.VERTICAL
        guideline.setGuidePercent(0.5f)
        root.debugName = "root"
        A.debugName = "A"
        divider.debugName = "divider"
        guideline.debugName = "guideline"
        root.add(A)
        root.add(divider)
        root.add(guideline)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.LEFT, guideline, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        divider.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        divider.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        divider.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        divider.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        divider.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("A: $A")
        println("divider: $divider")
        println("guideline: $guideline")
        Assert.assertEquals(root.width.toLong(), (A.width * 2).toLong())
        Assert.assertEquals(root.height.toLong(), A.height.toLong())
        Assert.assertEquals(guideline.left.toFloat(), root.width / 2f, 0f)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
    }

    @Test
    fun testWrap8() {
        // check_048
        val root = ConstraintWidgetContainer(0, 0, 1080, 1080)
        val button56 = ConstraintWidget(231, 126)
        val button60 = ConstraintWidget(231, 126)
        val button63 = ConstraintWidget(368, 368)
        val button65 = ConstraintWidget(231, 126)
        button56.debugName = "button56"
        button60.debugName = "button60"
        button63.debugName = "button63"
        button65.debugName = "button65"
        root.add(button56)
        root.add(button60)
        root.add(button63)
        root.add(button65)
        button56.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 42)
        button56.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 42)
        //button56.setBaselineDistance(77);
        button60.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 42)
        button60.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 79)
        //button60.setBaselineDistance(77);
        button63.connect(ConstraintAnchor.Type.LEFT, button56, ConstraintAnchor.Type.RIGHT, 21)
        button63.connect(ConstraintAnchor.Type.RIGHT, button60, ConstraintAnchor.Type.LEFT, 21)
        button63.connect(ConstraintAnchor.Type.TOP, button56, ConstraintAnchor.Type.BOTTOM, 21)
        button63.connect(ConstraintAnchor.Type.BOTTOM, button60, ConstraintAnchor.Type.TOP, 21)
        //button63.setBaselineDistance(155);
        button63.verticalBiasPercent = 0.8f
        button65.connect(ConstraintAnchor.Type.LEFT, button56, ConstraintAnchor.Type.RIGHT, 21)
        button65.connect(ConstraintAnchor.Type.RIGHT, button60, ConstraintAnchor.Type.LEFT, 21)
        button65.connect(ConstraintAnchor.Type.TOP, button56, ConstraintAnchor.Type.BOTTOM, 21)
        button65.connect(ConstraintAnchor.Type.BOTTOM, button60, ConstraintAnchor.Type.TOP, 21)
        //button65.setBaselineDistance(77);
        button65.verticalBiasPercent = 0.28f
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("button56: $button56")
        println("button60: $button60")
        println("button63: $button63")
        println("button65: $button65")
        Assert.assertEquals(root.width.toLong(), 1080)
        Assert.assertEquals(root.height.toLong(), 783)
        Assert.assertEquals(button56.left.toLong(), 42)
        Assert.assertEquals(button56.top.toLong(), 42)
        Assert.assertEquals(button60.left.toLong(), 807)
        Assert.assertEquals(button60.top.toLong(), 578)
        Assert.assertEquals(button63.left.toLong(), 356)
        Assert.assertEquals(button63.top.toLong(), 189)
        Assert.assertEquals(button65.left.toLong(), 425)
        Assert.assertEquals(button65.top.toLong(), 257)
    }

    @Test
    fun testWrap9() {
        // b/161826272
        val root = ConstraintWidgetContainer(0, 0, 1080, 1080)
        val text = ConstraintWidget(270, 30)
        val view = ConstraintWidget(10, 10)
        root.debugName = "root"
        text.debugName = "text"
        view.debugName = "view"
        root.add(text)
        root.add(view)
        text.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        text.connect(ConstraintAnchor.Type.TOP, view, ConstraintAnchor.Type.TOP)
        view.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        view.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        view.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        view.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        view.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        view.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        view.setVerticalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_PERCENT, 0, 0, 0.2f)
        view.setDimensionRatio("1:1")
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("text: $text")
        println("view: $view")
        Assert.assertEquals(view.width.toLong(), view.height.toLong())
        Assert.assertEquals(view.height.toLong(), (0.2 * root.height).toLong())
        Assert.assertEquals(root.width.toLong(), Math.max(text.width, view.width).toLong())
    }

    @Test
    fun testBarrierWrap() {
        // b/165028374
        val root = ConstraintWidgetContainer(0, 0, 1080, 1080)
        val view = ConstraintWidget(200, 200)
        val space = ConstraintWidget(50, 50)
        val button = ConstraintWidget(100, 80)
        val text = ConstraintWidget(90, 30)
        val barrier = Barrier()
        barrier.barrierType = Barrier.BOTTOM
        barrier.add(button)
        barrier.add(space)
        root.debugName = "root"
        view.debugName = "view"
        space.debugName = "space"
        button.debugName = "button"
        text.debugName = "text"
        barrier.debugName = "barrier"
        root.add(view)
        root.add(space)
        root.add(button)
        root.add(text)
        root.add(barrier)
        view.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        space.connect(ConstraintAnchor.Type.TOP, view, ConstraintAnchor.Type.BOTTOM)
        button.connect(ConstraintAnchor.Type.TOP, view, ConstraintAnchor.Type.BOTTOM)
        button.connect(ConstraintAnchor.Type.BOTTOM, text, ConstraintAnchor.Type.TOP)
        text.connect(ConstraintAnchor.Type.TOP, barrier, ConstraintAnchor.Type.BOTTOM)
        text.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        button.verticalBiasPercent = 1f
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("view: $view")
        println("space: $space")
        println("button: $button")
        println("barrier: $barrier")
        println("text: $text")
        Assert.assertEquals(view.top.toLong(), 0)
        Assert.assertEquals(view.bottom.toLong(), 200)
        Assert.assertEquals(space.top.toLong(), 200)
        Assert.assertEquals(space.bottom.toLong(), 250)
        Assert.assertEquals(button.top.toLong(), 200)
        Assert.assertEquals(button.bottom.toLong(), 280)
        Assert.assertEquals(barrier.top.toLong(), 280)
        Assert.assertEquals(text.top.toLong(), barrier.top.toLong())
    }
}