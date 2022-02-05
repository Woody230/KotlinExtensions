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
import org.junit.Test
import java.util.ArrayList

class BasicTest {
    @Test
    fun testWrapPercent() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        val A = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_PERCENT, BasicMeasure.WRAP_CONTENT, 0, 0.5f)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        root.add(A)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("A: $A")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(root.width.toLong(), (A.width * 2).toLong())
    }

    @Test
    fun testMiddleSplit() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(400, 30)
        val B = ConstraintWidget(400, 60)
        val guideline = Guideline()
        val divider = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        guideline.debugName = "guideline"
        divider.debugName = "divider"
        root.add(A)
        root.add(B)
        root.add(guideline)
        root.add(divider)
        guideline.orientation = Guideline.VERTICAL
        guideline.setGuidePercent(0.5f)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, guideline, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, guideline, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        divider.width = 1
        divider.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        divider.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        divider.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        //        root.layout();
        root.updateHierarchy()
        root.measure(Optimizer.OPTIMIZATION_NONE, BasicMeasure.EXACTLY, 600, BasicMeasure.EXACTLY, 800, 0, 0, 0, 0)
        println("root: $root")
        println("A: $A")
        println("B: $B")
        println("guideline: $guideline")
        println("divider: $divider")
        Assert.assertEquals(A.width.toLong(), 300)
        Assert.assertEquals(B.width.toLong(), 300)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 300)
        Assert.assertEquals(divider.height.toLong(), 60)
        Assert.assertEquals(root.width.toLong(), 600)
        Assert.assertEquals(root.height.toLong(), 60)
    }

    @Test
    fun testSimpleConstraint() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 8)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GRAPH
        root.measure(Optimizer.OPTIMIZATION_GRAPH, 0, 0, 0, 0, 0, 0, 0, 0)
        //        root.layout();
        println("1) A: $A")
    }

    @Test
    fun testSimpleWrapConstraint9() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        val margin = 8
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, margin)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, margin)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, margin)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, margin)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GRAPH_WRAP
        root.measure(Optimizer.OPTIMIZATION_GRAPH_WRAP, 0, 0, 0, 0, 0, 0, 0, 0)
        //        root.layout();
        println("root: $root")
        println("1) A: $A")
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.measure(Optimizer.OPTIMIZATION_GRAPH_WRAP, 0, 0, 0, 0, 0, 0, 0, 0)
        println("root: $root")
        println("1) A: $A")
        Assert.assertEquals(root.width.toLong(), 116)
        Assert.assertEquals(root.height.toLong(), 46)
    }

    @Test
    fun testSimpleWrapConstraint10() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        val margin = 8
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, margin)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, margin)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, margin)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, margin)


        //root.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        root.measure(Optimizer.OPTIMIZATION_NONE, 0, 0, 0, 0, 0, 0, 0, 0)
        root.layout()
        println("root: $root")
        println("1) A: $A")
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.measure(Optimizer.OPTIMIZATION_GRAPH, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.EXACTLY, 800, 0, 0, 0, 0)
        println("root: $root")
        println("1) A: $A")
        Assert.assertEquals(root.width.toLong(), 116)
        Assert.assertEquals(root.height.toLong(), 800)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 385)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 30)
    }

    @Test
    fun testSimpleWrapConstraint11() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(10, 30)
        val B = ConstraintWidget(800, 30)
        val C = ConstraintWidget(10, 30)
        val D = ConstraintWidget(800, 30)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        D.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        root.layout()
        println("root: $root")
        println("1) A: $A")
        println("1) B: $B")
        println("1) C: $C")
        println("1) D: $D")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.width.toLong(), 10)
        Assert.assertEquals(C.width.toLong(), 10)
        Assert.assertEquals(B.left.toLong(), A.right.toLong())
        Assert.assertEquals(B.width.toLong(), (root.width - A.width - C.width).toLong())
        Assert.assertEquals(C.left.toLong(), (root.width - C.width).toLong())
        Assert.assertEquals(D.width.toLong(), 800)
        Assert.assertEquals(D.left.toLong(), -99)
    }

    @Test
    fun testSimpleWrapConstraint() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        val B = ConstraintWidget(100, 60)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        B.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        B.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 8)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 8)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 8)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.measure(Optimizer.OPTIMIZATION_STANDARD, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        Assert.assertEquals(root.width.toLong(), 216)
        Assert.assertEquals(root.height.toLong(), 68)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 30)
        Assert.assertEquals(B.left.toLong(), 116)
        Assert.assertEquals(B.top.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 60)
        root.measure(Optimizer.OPTIMIZATION_GRAPH, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        Assert.assertEquals(root.width.toLong(), 216)
        Assert.assertEquals(root.height.toLong(), 68)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 30)
        Assert.assertEquals(B.left.toLong(), 116)
        Assert.assertEquals(B.top.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 60)
    }

    @Test
    fun testSimpleWrapConstraint2() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        val B = ConstraintWidget(120, 60)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        B.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        B.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 8)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 8)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 8)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.measure(Optimizer.OPTIMIZATION_STANDARD, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        //        root.layout();
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        Assert.assertEquals(root.width.toLong(), 128)
        Assert.assertEquals(root.height.toLong(), 114)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 30)
        Assert.assertEquals(B.left.toLong(), 8)
        Assert.assertEquals(B.top.toLong(), 46)
        Assert.assertEquals(B.width.toLong(), 120)
        Assert.assertEquals(B.height.toLong(), 60)
        root.measure(Optimizer.OPTIMIZATION_GRAPH, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        Assert.assertEquals(root.width.toLong(), 128)
        Assert.assertEquals(root.height.toLong(), 114)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 30)
        Assert.assertEquals(B.left.toLong(), 8)
        Assert.assertEquals(B.top.toLong(), 46)
        Assert.assertEquals(B.width.toLong(), 120)
        Assert.assertEquals(B.height.toLong(), 60)
    }

    @Test
    fun testSimpleWrapConstraint3() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 8)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 8)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 8)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.measure(Optimizer.OPTIMIZATION_STANDARD, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        Assert.assertEquals(root.width.toLong(), 116)
        Assert.assertEquals(root.height.toLong(), 46)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 30)
        root.measure(Optimizer.OPTIMIZATION_GRAPH, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        Assert.assertEquals(root.width.toLong(), 116)
        Assert.assertEquals(root.height.toLong(), 46)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 30)
    }

    @Test
    fun testSimpleWrapConstraint4() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        val B = ConstraintWidget(100, 30)
        val C = ConstraintWidget(100, 30)
        val D = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        B.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        B.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        C.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        C.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        D.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        D.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 8)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 8)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 8)
        C.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, 8)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 8)
        D.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP, 8)
        D.connect(ConstraintAnchor.Type.LEFT, C, ConstraintAnchor.Type.RIGHT, 8)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.measure(Optimizer.OPTIMIZATION_STANDARD, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        println("3) C: $C")
        println("4) D: $D")
        Assert.assertEquals(root.width.toLong(), 532)
        Assert.assertEquals(root.height.toLong(), 76)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 30)
        Assert.assertEquals(B.left.toLong(), 216)
        Assert.assertEquals(B.top.toLong(), 46)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 30)
        Assert.assertEquals(C.left.toLong(), 324)
        Assert.assertEquals(C.top.toLong(), 8)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(C.height.toLong(), 30)
        Assert.assertEquals(D.left.toLong(), 432)
        Assert.assertEquals(D.top.toFloat(), -28f, 2f)
        Assert.assertEquals(D.width.toLong(), 100)
        Assert.assertEquals(D.height.toLong(), 30)
        root.measure(Optimizer.OPTIMIZATION_GRAPH, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        println("3) C: $C")
        println("4) D: $D")
        Assert.assertEquals(root.width.toLong(), 532)
        Assert.assertEquals(root.height.toLong(), 76)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 30)
        Assert.assertEquals(B.left.toLong(), 216)
        Assert.assertEquals(B.top.toLong(), 46)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 30)
        Assert.assertEquals(C.left.toLong(), 324)
        Assert.assertEquals(C.top.toLong(), 8)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(C.height.toLong(), 30)
        Assert.assertEquals(D.left.toLong(), 432)
        Assert.assertEquals(D.top.toFloat(), -28f, 2f)
        Assert.assertEquals(D.width.toLong(), 100)
        Assert.assertEquals(D.height.toLong(), 30)
    }

    @Test
    fun testSimpleWrapConstraint5() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        val B = ConstraintWidget(100, 30)
        val C = ConstraintWidget(100, 30)
        val D = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        B.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        B.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        C.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        C.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        D.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        D.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 8)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 8)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 8)
        B.horizontalBiasPercent = 0.2f
        C.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, 8)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 8)
        D.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP, 8)
        D.connect(ConstraintAnchor.Type.LEFT, C, ConstraintAnchor.Type.RIGHT, 8)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.measure(Optimizer.OPTIMIZATION_STANDARD, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        println("3) C: $C")
        println("4) D: $D")
        Assert.assertEquals(root.width.toLong(), 376)
        Assert.assertEquals(root.height.toLong(), 76)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 30)
        Assert.assertEquals(B.left.toLong(), 60)
        Assert.assertEquals(B.top.toLong(), 46)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 30)
        Assert.assertEquals(C.left.toLong(), 168)
        Assert.assertEquals(C.top.toLong(), 8)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(C.height.toLong(), 30)
        Assert.assertEquals(D.left.toLong(), 276)
        Assert.assertEquals(D.top.toFloat(), -28f, 2f)
        Assert.assertEquals(D.width.toLong(), 100)
        Assert.assertEquals(D.height.toLong(), 30)
        root.measure(Optimizer.OPTIMIZATION_GRAPH, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        println("3) C: $C")
        println("4) D: $D")
        Assert.assertEquals(root.width.toLong(), 376)
        Assert.assertEquals(root.height.toLong(), 76)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 30)
        Assert.assertEquals(B.left.toLong(), 60)
        Assert.assertEquals(B.top.toLong(), 46)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 30)
        Assert.assertEquals(C.left.toLong(), 168)
        Assert.assertEquals(C.top.toLong(), 8)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(C.height.toLong(), 30)
        Assert.assertEquals(D.left.toLong(), 276)
        Assert.assertEquals(D.top.toFloat(), -28f, 2f)
        Assert.assertEquals(D.width.toLong(), 100)
        Assert.assertEquals(D.height.toLong(), 30)
    }

    @Test
    fun testSimpleWrapConstraint6() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        val B = ConstraintWidget(100, 30)
        val C = ConstraintWidget(100, 30)
        val D = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        B.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        B.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        C.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        C.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        D.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        D.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 8)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 8)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 33)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 16)
        B.horizontalBiasPercent = 0.15f
        C.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, 8)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 12)
        D.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP, 8)
        D.connect(ConstraintAnchor.Type.LEFT, C, ConstraintAnchor.Type.RIGHT, 8)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.measure(Optimizer.OPTIMIZATION_STANDARD, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        println("3) C: $C")
        println("4) D: $D")
        Assert.assertEquals(root.width.toLong(), 389)
        Assert.assertEquals(root.height.toLong(), 76)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 30)
        Assert.assertEquals(B.left.toLong(), 69)
        Assert.assertEquals(B.top.toLong(), 46)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 30)
        Assert.assertEquals(C.left.toLong(), 181)
        Assert.assertEquals(C.top.toLong(), 8)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(C.height.toLong(), 30)
        Assert.assertEquals(D.left.toLong(), 289)
        Assert.assertEquals(D.top.toFloat(), -28f, 2f)
        Assert.assertEquals(D.width.toLong(), 100)
        Assert.assertEquals(D.height.toLong(), 30)
        root.measure(Optimizer.OPTIMIZATION_GRAPH, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        println("3) C: $C")
        println("4) D: $D")
        Assert.assertEquals(root.width.toLong(), 389)
        Assert.assertEquals(root.height.toLong(), 76)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 30)
        Assert.assertEquals(B.left.toLong(), 69)
        Assert.assertEquals(B.top.toLong(), 46)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 30)
        Assert.assertEquals(C.left.toLong(), 181)
        Assert.assertEquals(C.top.toLong(), 8)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(C.height.toLong(), 30)
        Assert.assertEquals(D.left.toLong(), 289)
        Assert.assertEquals(D.top.toFloat(), -28f, 2f)
        Assert.assertEquals(D.width.toLong(), 100)
        Assert.assertEquals(D.height.toLong(), 30)
    }

    @Test
    fun testSimpleWrapConstraint7() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 8)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 8)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.measure(Optimizer.OPTIMIZATION_STANDARD, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        Assert.assertEquals(root.width.toLong(), 16)
        Assert.assertEquals(root.height.toLong(), 38)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 0)
        Assert.assertEquals(A.height.toLong(), 30)
        root.measure(Optimizer.OPTIMIZATION_GRAPH, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        Assert.assertEquals(root.width.toLong(), 16)
        Assert.assertEquals(root.height.toLong(), 38)
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 8)
        Assert.assertEquals(A.width.toLong(), 0)
        Assert.assertEquals(A.height.toLong(), 30)
    }

    @Test
    fun testSimpleWrapConstraint8() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        val B = ConstraintWidget(10, 30)
        val C = ConstraintWidget(10, 30)
        val D = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        D.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        B.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        C.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        D.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        applyChain(ConstraintWidget.HORIZONTAL, A, B, C, D)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.measure(Optimizer.OPTIMIZATION_STANDARD, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        println("3) C: $C")
        println("4) D: $D")
        Assert.assertEquals(root.width.toLong(), 110)
        Assert.assertEquals(root.height.toLong(), 30)
        root.measure(Optimizer.OPTIMIZATION_GRAPH, BasicMeasure.WRAP_CONTENT, 0, BasicMeasure.WRAP_CONTENT, 0, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        println("3) C: $C")
        println("4) D: $D")
        Assert.assertEquals(root.width.toLong(), 110)
        Assert.assertEquals(root.height.toLong(), 30)
    }

    @Test
    fun testSimpleCircleConstraint() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        val B = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        B.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        B.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 8)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        B.connectCircularConstraint(A, 30f, 50)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GRAPH
        root.measure(Optimizer.OPTIMIZATION_GRAPH, BasicMeasure.EXACTLY, 600, BasicMeasure.EXACTLY, 800, 0, 0, 0, 0)
        //        root.layout();
        println("1) A: $A")
        println("2) B: $B")
    }

    fun applyChain(widgets: ArrayList<ConstraintWidget>, direction: Int) {
        var previous = widgets[0]
        for (i in 1 until widgets.size) {
            val widget = widgets[i]
            if (direction == 0) { // horizontal
                widget.connect(ConstraintAnchor.Type.LEFT, previous, ConstraintAnchor.Type.RIGHT)
                previous.connect(ConstraintAnchor.Type.RIGHT, widget, ConstraintAnchor.Type.LEFT)
            } else {
                widget.connect(ConstraintAnchor.Type.TOP, previous, ConstraintAnchor.Type.BOTTOM)
                previous.connect(ConstraintAnchor.Type.BOTTOM, widget, ConstraintAnchor.Type.TOP)
            }
            previous = widget
        }
    }

    fun applyChain(direction: Int, vararg widgets: ConstraintWidget) {
        var previous = widgets[0]
        for (i in 1 until widgets.size) {
            val widget = widgets[i]
            if (direction == ConstraintWidget.HORIZONTAL) {
                widget.connect(ConstraintAnchor.Type.LEFT, previous, ConstraintAnchor.Type.RIGHT)
                previous.connect(ConstraintAnchor.Type.RIGHT, widget, ConstraintAnchor.Type.LEFT)
            } else {
                widget.connect(ConstraintAnchor.Type.TOP, previous, ConstraintAnchor.Type.BOTTOM)
                previous.connect(ConstraintAnchor.Type.BOTTOM, widget, ConstraintAnchor.Type.TOP)
            }
            previous = widget
        }
    }

    @Test
    fun testRatioChainConstraint() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        val B = ConstraintWidget(0, 30)
        val C = ConstraintWidget(0, 30)
        val D = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        B.setDimensionRatio("w,1:1")
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        B.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        C.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        D.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        applyChain(ConstraintWidget.HORIZONTAL, A, B, C, D)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GRAPH
        root.measure(Optimizer.OPTIMIZATION_GRAPH, BasicMeasure.EXACTLY, 600, BasicMeasure.EXACTLY, 800, 0, 0, 0, 0)
        //        root.layout();
        println("1) A: $A")
        println("2) B: $B")
        println("3) C: $C")
        println("4) D: $D")
    }

    @Test
    fun testCycleConstraints() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        val B = ConstraintWidget(40, 20)
        val C = ConstraintWidget(40, 20)
        val D = ConstraintWidget(30, 30)
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
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.LEFT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.LEFT, D, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.TOP)
        D.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.measure(Optimizer.OPTIMIZATION_NONE, BasicMeasure.EXACTLY, 600, BasicMeasure.EXACTLY, 800, 0, 0, 0, 0)
        println("1) A: $A")
        println("2) B: $B")
        println("3) C: $C")
        println("4) D: $D")
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(B.top.toLong(), 30)
        Assert.assertEquals(C.top.toLong(), 50)
        Assert.assertEquals(D.top.toLong(), 35)
    }

    @Test
    fun testGoneChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        val B = ConstraintWidget(100, 30)
        val C = ConstraintWidget(100, 30)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.visibility = ConstraintWidget.GONE
        C.visibility = ConstraintWidget.GONE
        root.measure(Optimizer.OPTIMIZATION_NONE, BasicMeasure.EXACTLY, 600, BasicMeasure.EXACTLY, 800, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        println("3) C: $C")
        Assert.assertEquals(B.width.toLong(), root.width.toLong())
    }

    @Test
    fun testGoneChainWithCenterWidget() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        val B = ConstraintWidget(100, 30)
        val C = ConstraintWidget(100, 30)
        val D = ConstraintWidget(100, 30)
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
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.visibility = ConstraintWidget.GONE
        C.visibility = ConstraintWidget.GONE
        D.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.RIGHT)
        D.visibility = ConstraintWidget.GONE
        root.measure(Optimizer.OPTIMIZATION_NONE, BasicMeasure.EXACTLY, 600, BasicMeasure.EXACTLY, 800, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        println("3) C: $C")
        println("4) D: $D")
        Assert.assertEquals(B.width.toLong(), root.width.toLong())
    }

    @Test
    fun testBarrier() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        root.measurer = sMeasurer
        val A = ConstraintWidget(100, 30)
        val B = ConstraintWidget(100, 30)
        val C = ConstraintWidget(100, 30)
        val D = ConstraintWidget(100, 30)
        val barrier1 = Barrier()
        //Barrier barrier2 = new Barrier();
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        barrier1.debugName = "barrier1"
        //barrier2.setDebugName("barrier2");
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        root.add(barrier1)
        //root.add(barrier2);
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        barrier1.add(A)
        barrier1.barrierType = Barrier.BOTTOM
        B.connect(ConstraintAnchor.Type.TOP, barrier1, ConstraintAnchor.Type.BOTTOM)
        //barrier2.add(B);
        //barrier2.setBarrierType(Barrier.TOP);
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.TOP, C, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.measure(Optimizer.OPTIMIZATION_NONE, BasicMeasure.EXACTLY, 600, BasicMeasure.EXACTLY, 800, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) barrier1: $barrier1")
        println("3) B: $B")
        //System.out.println("4) barrier2: " + barrier2);
        println("5) C: $C")
        println("6) D: $D")
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(B.top.toLong(), A.bottom.toLong())
        Assert.assertEquals(barrier1.top.toLong(), A.bottom.toLong())
        Assert.assertEquals(C.top.toLong(), B.bottom.toLong())
        Assert.assertEquals(D.top.toLong(), 430)
        //        assertEquals(barrier2.getTop(), B.getTop());
    }

    @Test
    fun testDirectCentering() {
        val root = ConstraintWidgetContainer(0, 0, 192, 168)
        root.measurer = sMeasurer
        val A = ConstraintWidget(43, 43)
        val B = ConstraintWidget(59, 59)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(B)
        root.add(A)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.measure(Optimizer.OPTIMIZATION_NONE, BasicMeasure.EXACTLY, 100, BasicMeasure.EXACTLY, 100, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        Assert.assertEquals(A.top.toLong(), 63)
        Assert.assertEquals(A.left.toLong(), 75)
        Assert.assertEquals(B.top.toLong(), 55)
        Assert.assertEquals(B.left.toLong(), 67)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        root.measure(Optimizer.OPTIMIZATION_STANDARD, BasicMeasure.EXACTLY, 100, BasicMeasure.EXACTLY, 100, 0, 0, 0, 0)
        println("0) root: $root")
        println("1) A: $A")
        println("2) B: $B")
        Assert.assertEquals(63, A.top.toLong())
        Assert.assertEquals(75, A.left.toLong())
        Assert.assertEquals(55, B.top.toLong())
        Assert.assertEquals(67, B.left.toLong())
    }

    companion object {
        var sMeasurer: BasicMeasure.Measurer = object : BasicMeasure.Measurer {
            override fun measure(widget: ConstraintWidget, measure: BasicMeasure.Measure) {
                val horizontalBehavior = measure.horizontalBehavior
                val verticalBehavior = measure.verticalBehavior
                val horizontalDimension = measure.horizontalDimension
                val verticalDimension = measure.verticalDimension
                if (horizontalBehavior === DimensionBehaviour.FIXED) {
                    measure.measuredWidth = horizontalDimension
                } else if (horizontalBehavior === DimensionBehaviour.MATCH_CONSTRAINT) {
                    measure.measuredWidth = horizontalDimension
                }
                if (verticalBehavior === DimensionBehaviour.FIXED) {
                    measure.measuredHeight = verticalDimension
                }
                widget.isMeasureRequested = false
            }

            override fun didMeasures() {}
        }
    }
}