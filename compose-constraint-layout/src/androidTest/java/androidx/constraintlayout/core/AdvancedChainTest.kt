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

import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import androidx.constraintlayout.core.widgets.*
import org.junit.Assert
import org.junit.Test
import java.util.ArrayList

class AdvancedChainTest {
    @Test
    fun testComplexChainWeights() {
        val root = ConstraintWidgetContainer(0, 0, 800, 800)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, 0)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 0)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 0)
        root.add(A)
        root.add(B)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("root: $root")
        println("A: $A")
        println("B: $B")
        Assert.assertEquals(A.width.toLong(), 800)
        Assert.assertEquals(B.width.toLong(), 800)
        Assert.assertEquals(A.height.toLong(), 400)
        Assert.assertEquals(B.height.toLong(), 400)
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(B.top.toLong(), 400)
        A.setDimensionRatio("16:3")
        root.layout()
        println("root: $root")
        println("A: $A")
        println("B: $B")
        Assert.assertEquals(A.width.toLong(), 800)
        Assert.assertEquals(B.width.toLong(), 800)
        Assert.assertEquals(A.height.toLong(), 150)
        Assert.assertEquals(B.height.toLong(), 150)
        Assert.assertEquals(A.top.toLong(), 167)
        Assert.assertEquals(B.top.toLong(), 483)
        B.setVerticalWeight(1f)
        root.layout()
        println("root: $root")
        println("A: $A")
        println("B: $B")
        Assert.assertEquals(A.width.toLong(), 800)
        Assert.assertEquals(B.width.toLong(), 800)
        Assert.assertEquals(A.height.toLong(), 150)
        Assert.assertEquals(B.height.toLong(), 650)
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(B.top.toLong(), 150)
        A.setVerticalWeight(1f)
        root.layout()
        println("root: $root")
        println("A: $A")
        println("B: $B")
        Assert.assertEquals(A.width.toLong(), 800)
        Assert.assertEquals(B.width.toLong(), 800)
        Assert.assertEquals(A.height.toLong(), 150)
        Assert.assertEquals(B.height.toLong(), 150)
        Assert.assertEquals(A.top.toLong(), 167)
        Assert.assertEquals(B.top.toLong(), 483)
    }

    @Test
    fun testTooSmall() {
        val root = ConstraintWidgetContainer(0, 0, 800, 800)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 100)
        C.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 100)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("A: $A")
        println("B: $B")
        println("C: $C")
        Assert.assertEquals(A.top.toLong(), 390)
        Assert.assertEquals(B.top.toLong(), 380)
        Assert.assertEquals(C.top.toLong(), 400)
    }

    @Test
    fun testChainWeights() {
        val root = ConstraintWidgetContainer(0, 0, 800, 800)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, 0)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 0)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        root.add(A)
        root.add(B)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalWeight(1f)
        B.setHorizontalWeight(0f)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("A: $A")
        println("B: $B")
        Assert.assertEquals(A.width.toFloat(), 800f, 1f)
        Assert.assertEquals(B.width.toFloat(), 0f, 1f)
        Assert.assertEquals(A.left.toFloat(), 0f, 1f)
        Assert.assertEquals(B.left.toFloat(), 800f, 1f)
    }

    @Test
    fun testChain3Weights() {
        val root = ConstraintWidgetContainer(0, 0, 800, 800)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, 0)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 0)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT, 0)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 0)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        root.add(A)
        root.add(B)
        root.add(C)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalWeight(1f)
        B.setHorizontalWeight(0f)
        C.setHorizontalWeight(1f)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("A: $A")
        println("B: $B")
        println("C: $C")
        Assert.assertEquals(A.width.toLong(), 400)
        Assert.assertEquals(B.width.toLong(), 0)
        Assert.assertEquals(C.width.toLong(), 400)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 400)
        Assert.assertEquals(C.left.toLong(), 400)
    }

    @Test
    fun testChainLastGone() {
        val root = ConstraintWidgetContainer(0, 0, 800, 800)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(100, 20)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        D.setDebugSolverName(root.system, "D")
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        D.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        D.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, 0)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 0)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP, 0)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM, 0)
        C.connect(ConstraintAnchor.Type.BOTTOM, D, ConstraintAnchor.Type.TOP, 0)
        D.connect(ConstraintAnchor.Type.TOP, C, ConstraintAnchor.Type.BOTTOM, 0)
        D.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 0)
        B.visibility = ConstraintWidget.GONE
        D.visibility = ConstraintWidget.GONE
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("A: $A")
        println("B: $B")
        println("C: $C")
        println("D: $D")
        Assert.assertEquals(A.top.toLong(), 253)
        Assert.assertEquals(C.top.toLong(), 527)
    }

    @Test
    fun testRatioChainGone() {
        val root = ConstraintWidgetContainer(0, 0, 800, 800)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val ratio = ConstraintWidget(100, 20)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        ratio.setDebugSolverName(root.system, "ratio")
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(ratio)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        ratio.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        ratio.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        ratio.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, 0)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 0)
        B.connect(ConstraintAnchor.Type.BOTTOM, ratio, ConstraintAnchor.Type.BOTTOM, 0)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.TOP, 0)
        C.connect(ConstraintAnchor.Type.BOTTOM, ratio, ConstraintAnchor.Type.BOTTOM, 0)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        ratio.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        ratio.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        ratio.setDimensionRatio("4:3")
        B.visibility = ConstraintWidget.GONE
        C.visibility = ConstraintWidget.GONE
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("A: $A")
        println("B: $B")
        println("C: $C")
        println("ratio: $ratio")
        Assert.assertEquals(A.height.toLong(), 600)
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("A: $A")
        println("B: $B")
        println("C: $C")
        println("ratio: $ratio")
        println("root: $root")
        Assert.assertEquals(A.height.toLong(), 600)
    }

    @Test
    fun testSimpleHorizontalChainPacked() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        widgets.add(root)
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 20)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 20)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, 0)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 0)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("a) A: $A B: $B")
        Assert.assertEquals((A.left - root.left).toFloat(), (root.right - B.right).toFloat(), 1f)
        Assert.assertEquals((B.left - A.right).toFloat(), 0f, 1f)
    }

    @Test
    fun testSimpleVerticalTChainPacked() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        widgets.add(root)
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 20)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 20)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, 0)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 0)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 0)
        A.verticalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("a) A: $A B: $B")
        Assert.assertEquals((A.top - root.top).toFloat(), (root.bottom - B.bottom).toFloat(), 1f)
        Assert.assertEquals((B.top - A.bottom).toFloat(), 0f, 1f)
    }

    @Test
    fun testHorizontalChainStyles() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        root.add(A)
        root.add(B)
        root.add(C)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, 0)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 0)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT, 0)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 0)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        root.layout()
        println("       spread) root: $root A: $A B: $B C: $C")
        var gap = (root.width - A.width - B.width - C.width) / 4
        val size = 100
        Assert.assertEquals(A.width.toLong(), size.toLong())
        Assert.assertEquals(B.width.toLong(), size.toLong())
        Assert.assertEquals(C.width.toLong(), size.toLong())
        Assert.assertEquals(gap.toLong(), A.left.toLong())
        Assert.assertEquals((A.right + gap).toLong(), B.left.toLong())
        Assert.assertEquals((root.width - gap - C.width).toLong(), C.left.toLong())
        A.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        root.layout()
        println("spread inside) root: $root A: $A B: $B C: $C")
        gap = (root.width - A.width - B.width - C.width) / 2
        Assert.assertEquals(A.width.toLong(), size.toLong())
        Assert.assertEquals(B.width.toLong(), size.toLong())
        Assert.assertEquals(C.width.toLong(), size.toLong())
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals((A.right + gap).toLong(), B.left.toLong())
        Assert.assertEquals(root.width.toLong(), C.right.toLong())
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("       packed) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toLong(), size.toLong())
        Assert.assertEquals(B.width.toLong(), size.toLong())
        Assert.assertEquals(C.width.toLong(), size.toLong())
        Assert.assertEquals(A.left.toLong(), gap.toLong())
        Assert.assertEquals((root.width - gap).toLong(), C.right.toLong())
    }

    @Test
    fun testVerticalChainStyles() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        root.add(A)
        root.add(B)
        root.add(C)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, 0)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 0)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP, 0)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM, 0)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 0)
        root.layout()
        println("       spread) root: $root A: $A B: $B C: $C")
        var gap = (root.height - A.height - B.height - C.height) / 4
        val size = 20
        Assert.assertEquals(A.height.toLong(), size.toLong())
        Assert.assertEquals(B.height.toLong(), size.toLong())
        Assert.assertEquals(C.height.toLong(), size.toLong())
        Assert.assertEquals(gap.toLong(), A.top.toLong())
        Assert.assertEquals((A.bottom + gap).toLong(), B.top.toLong())
        Assert.assertEquals((root.height - gap - C.height).toLong(), C.top.toLong())
        A.verticalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        root.layout()
        println("spread inside) root: $root A: $A B: $B C: $C")
        gap = (root.height - A.height - B.height - C.height) / 2
        Assert.assertEquals(A.height.toLong(), size.toLong())
        Assert.assertEquals(B.height.toLong(), size.toLong())
        Assert.assertEquals(C.height.toLong(), size.toLong())
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals((A.bottom + gap).toLong(), B.top.toLong())
        Assert.assertEquals(root.height.toLong(), C.bottom.toLong())
        A.verticalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("       packed) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.height.toLong(), size.toLong())
        Assert.assertEquals(B.height.toLong(), size.toLong())
        Assert.assertEquals(C.height.toLong(), size.toLong())
        Assert.assertEquals(A.top.toLong(), gap.toLong())
        Assert.assertEquals((root.height - gap).toLong(), C.bottom.toLong())
    }

    @Test
    fun testPacked() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.add(A)
        root.add(B)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, 0)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 0)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        val gap = (root.width - A.width - B.width) / 2
        val size = 100
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        root.optimizationLevel = 0
        println("       packed) root: $root A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), size.toLong())
        Assert.assertEquals(B.width.toLong(), size.toLong())
        Assert.assertEquals(A.left.toLong(), gap.toLong())
    }
}