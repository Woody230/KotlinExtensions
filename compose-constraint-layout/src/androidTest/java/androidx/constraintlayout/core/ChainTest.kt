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

class ChainTest {
    @Test
    fun testCenteringElementsWithSpreadChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(100, 20)
        val E = ConstraintWidget(600, 20)
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
        A.connect(ConstraintAnchor.Type.LEFT, E, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, E, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        root.layout()
        println("A: $A B: $B C: $C D: $D E: $E")
        Assert.assertEquals(A.width.toLong(), 300)
        Assert.assertEquals(B.width.toLong(), A.width.toLong())
    }

    @Test
    fun testBasicChainMatch() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
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
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.baselineDistance = 8
        B.baselineDistance = 8
        C.baselineDistance = 8
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD or Optimizer.OPTIMIZATION_CHAIN
        root.layout()
        println("A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.right.toLong(), 200)
        Assert.assertEquals(B.left.toLong(), 200)
        Assert.assertEquals(B.right.toLong(), 400)
        Assert.assertEquals(C.left.toLong(), 400)
        Assert.assertEquals(C.right.toLong(), 600)
    }

    @Test
    fun testSpreadChainGone() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
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
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD
        A.visibility = ConstraintWidget.GONE
        root.layout()
        println("A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.right.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 133)
        Assert.assertEquals(B.right.toLong(), 233)
        Assert.assertEquals(C.left.toLong(), 367)
        Assert.assertEquals(C.right.toLong(), 467)
    }

    @Test
    fun testPackChainGone() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
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
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 100)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 20)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        B.setGoneMargin(ConstraintAnchor.Type.RIGHT, 100)
        C.visibility = ConstraintWidget.GONE
        root.layout()
        println("A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 200)
        Assert.assertEquals(B.left.toLong(), 300)
        Assert.assertEquals(C.left.toLong(), 500)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 0)
    }

    @Test
    fun testSpreadInsideChain2() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
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
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 25)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.right.toLong(), 100)
        Assert.assertEquals(B.left.toLong(), 100)
        Assert.assertEquals(B.right.toLong(), 475)
        Assert.assertEquals(C.left.toLong(), 500)
        Assert.assertEquals(C.right.toLong(), 600)
    }

    @Test
    fun testPackChain2() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 1f)
        root.layout()
        println("e) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(A.left.toLong(), (root.width - B.right).toLong())
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        // e) A: id: A (200, 0) - (100 x 20) B: id: B (300, 0) - (100 x 20) - pass
        // e) A: id: A (0, 0) - (100 x 20) B: id: B (100, 0) - (100 x 20)
    }

    @Test
    fun testPackChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("a) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(A.left.toLong(), (root.width - B.right).toLong())
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        A.visibility = ConstraintWidget.GONE
        root.layout()
        println("b) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(A.left.toLong(), (root.width - B.right).toLong())
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        B.visibility = ConstraintWidget.GONE
        root.layout()
        println("c) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 0)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        A.visibility = ConstraintWidget.VISIBLE
        A.width = 100
        root.layout()
        println("d) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 0)
        Assert.assertEquals(A.left.toLong(), (root.width - B.right).toLong())
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        A.visibility = ConstraintWidget.VISIBLE
        A.width = 100
        A.height = 20
        B.visibility = ConstraintWidget.VISIBLE
        B.width = 100
        B.height = 20
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 1f)
        root.layout()
        println("e) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(A.left.toLong(), (root.width - B.right).toLong())
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 0, 1f)
        root.layout()
        println("f) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 500)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 100)
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 50, 1f)
        root.layout()
        println("g) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 50)
        Assert.assertEquals(A.left.toLong(), (root.width - B.right).toLong())
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_PERCENT, 0, 0, 0.3f)
        root.layout()
        println("h) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), (0.3f * 600))
        Assert.assertEquals(A.left.toLong(), (root.width - B.right).toLong())
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        B.setDimensionRatio("16:9")
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_RATIO, 0, 0, 1f)
        root.layout()
        println("i) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toFloat(), (16f / 9f * 20), 1f)
        Assert.assertEquals(A.left.toFloat(), (root.width - B.right).toFloat(), 1f)
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 0, 1f)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 0, 1f)
        B.setDimensionRatio(0f, 0)
        A.visibility = ConstraintWidget.VISIBLE
        A.width = 100
        A.height = 20
        B.visibility = ConstraintWidget.VISIBLE
        B.width = 100
        B.height = 20
        root.layout()
        println("j) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), B.width.toLong())
        Assert.assertEquals((A.width + B.width).toLong(), root.width.toLong())
        A.setHorizontalWeight(1f)
        B.setHorizontalWeight(3f)
        root.layout()
        println("k) A: $A B: $B")
        Assert.assertEquals((A.width * 3).toLong(), B.width.toLong())
        Assert.assertEquals((A.width + B.width).toLong(), root.width.toLong())
    }

    /**
     * testPackChain with current Chain Optimizations.
     */
    @Test
    fun testPackChainOpt() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        root.optimizationLevel = (Optimizer.OPTIMIZATION_DIRECT or Optimizer.OPTIMIZATION_BARRIER
                or Optimizer.OPTIMIZATION_CHAIN)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("a) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(A.left.toLong(), (root.width - B.right).toLong())
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        A.visibility = ConstraintWidget.GONE
        root.layout()
        println("b) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(A.left.toLong(), (root.width - B.right).toLong())
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        B.visibility = ConstraintWidget.GONE
        root.layout()
        println("c) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 0)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        A.visibility = ConstraintWidget.VISIBLE
        A.width = 100
        root.layout()
        println("d) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 0)
        Assert.assertEquals(A.left.toLong(), (root.width - B.right).toLong())
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        A.visibility = ConstraintWidget.VISIBLE
        A.width = 100
        A.height = 20
        B.visibility = ConstraintWidget.VISIBLE
        B.width = 100
        B.height = 20
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 1f)
        root.layout()
        println("e) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(A.left.toLong(), (root.width - B.right).toLong())
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 0, 1f)
        root.layout()
        println("f) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 500)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 100)
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 50, 1f)
        root.layout()
        println("g) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 50)
        Assert.assertEquals(A.left.toLong(), (root.width - B.right).toLong())
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_PERCENT, 0, 0, 0.3f)
        root.layout()
        println("h) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), (0.3f * 600))
        Assert.assertEquals(A.left.toLong(), (root.width - B.right).toLong())
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        B.setDimensionRatio("16:9")
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_RATIO, 0, 0, 1f)
        root.layout()
        println("i) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toFloat(), (16f / 9f * 20), 1f)
        Assert.assertEquals(A.left.toFloat(), (root.width - B.right).toFloat(), 1f)
        Assert.assertEquals(B.left.toLong(), (A.left + A.width).toLong())
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 0, 1f)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 0, 1f)
        B.setDimensionRatio(0f, 0)
        A.visibility = ConstraintWidget.VISIBLE
        A.width = 100
        A.height = 20
        B.visibility = ConstraintWidget.VISIBLE
        B.width = 100
        B.height = 20
        root.layout()
        println("j) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), B.width.toLong())
        Assert.assertEquals((A.width + B.width).toLong(), root.width.toLong())
        A.setHorizontalWeight(1f)
        B.setHorizontalWeight(3f)
        root.layout()
        println("k) A: $A B: $B")
        Assert.assertEquals((A.width * 3).toLong(), B.width.toLong())
        Assert.assertEquals((A.width + B.width).toLong(), root.width.toLong())
    }

    @Test
    fun testSpreadChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD
        root.layout()
        println("a) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(A.left.toFloat(), (B.left - A.right).toFloat(), 1f)
        Assert.assertEquals((B.left - A.right).toFloat(), (root.width - B.right).toFloat(), 1f)
        B.visibility = ConstraintWidget.GONE
        root.layout()
        println("b) A: $A B: $B")
    }

    @Test
    fun testSpreadInsideChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
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
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        root.layout()
        println("a) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.right.toLong(), root.width.toLong())
        B.reset()
        root.add(B)
        B.debugName = "B"
        B.width = 100
        B.height = 20
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        root.layout()
        println("b) A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals((B.left - A.right).toLong(), (C.left - B.right).toLong())
        val gap = (root.width - A.width - B.width - C.width) / 2
        Assert.assertEquals(B.left.toLong(), (A.right + gap).toLong())
    }

    @Test
    fun testBasicChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        widgets.add(root)
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        root.layout()
        println("a) A: $A B: $B")
        Assert.assertEquals(A.width.toFloat(), B.width.toFloat(), 1f)
        Assert.assertEquals((A.left - root.left).toFloat(), (root.right - B.right).toFloat(), 1f)
        Assert.assertEquals((A.left - root.left).toFloat(), (B.left - A.right).toFloat(), 1f)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("b) A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), (root.width - B.width).toLong())
        Assert.assertEquals(B.width.toLong(), 100)
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        A.width = 100
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("a) A: $A B: $B")
        Assert.assertEquals(B.width.toLong(), (root.width - A.width).toLong())
        Assert.assertEquals(A.width.toLong(), 100)
    }

    @Test
    fun testBasicVerticalChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        A.debugName = "A"
        B.debugName = "B"
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        widgets.add(root)
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.layout()
        println("a) A: $A B: $B")
        Assert.assertEquals(A.height.toFloat(), B.height.toFloat(), 1f)
        Assert.assertEquals((A.top - root.top).toFloat(), (root.bottom - B.bottom).toFloat(), 1f)
        Assert.assertEquals((A.top - root.top).toFloat(), (B.top - A.bottom).toFloat(), 1f)
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("b) A: $A B: $B")
        Assert.assertEquals(A.height.toLong(), (root.height - B.height).toLong())
        Assert.assertEquals(B.height.toLong(), 20)
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        A.height = 20
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("c) A: $A B: $B")
        Assert.assertEquals(B.height.toLong(), (root.height - A.height).toLong())
        Assert.assertEquals(A.height.toLong(), 20)
    }

    @Test
    fun testBasicChainThreeElements1() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val marginL = 7
        val marginR = 27
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        widgets.add(C)
        widgets.add(root)
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, 0)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 0)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT, 0)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 0)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        root.layout()
        println("a) A: $A B: $B C: $C")
        // all elements spread equally
        Assert.assertEquals(A.width.toFloat(), B.width.toFloat(), 1f)
        Assert.assertEquals(B.width.toFloat(), C.width.toFloat(), 1f)
        Assert.assertEquals((A.left - root.left).toFloat(), (root.right - C.right).toFloat(), 1f)
        Assert.assertEquals((A.left - root.left).toFloat(), (B.left - A.right).toFloat(), 1f)
        Assert.assertEquals((B.left - A.right).toFloat(), (C.left - B.right).toFloat(), 1f)
        // a) A: id: A (125, 0) - (100 x 20) B: id: B (350, 0) - (100 x 20) C: id: C (575, 0) - (100 x 20)
        // a) A: id: A (0, 0) - (100 x 20) B: id: B (100, 0) - (100 x 20) C: id: C (450, 0) - (100 x 20)
    }

    @Test
    fun testBasicChainThreeElements() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val marginL = 7
        val marginR = 27
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        widgets.add(C)
        widgets.add(root)
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, 0)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 0)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT, 0)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 0)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        root.layout()
        println("a) A: $A B: $B C: $C")
        // all elements spread equally
        Assert.assertEquals(A.width.toFloat(), B.width.toFloat(), 1f)
        Assert.assertEquals(B.width.toFloat(), C.width.toFloat(), 1f)
        Assert.assertEquals((A.left - root.left).toFloat(), (root.right - C.right).toFloat(), 1f)
        Assert.assertEquals((A.left - root.left).toFloat(), (B.left - A.right).toFloat(), 1f)
        Assert.assertEquals((B.left - A.right).toFloat(), (C.left - B.right).toFloat(), 1f)
        // A marked as 0dp, B == C, A takes the rest
        A.getAnchor(ConstraintAnchor.Type.LEFT)!!.margin = marginL
        A.getAnchor(ConstraintAnchor.Type.RIGHT)!!.margin = marginR
        B.getAnchor(ConstraintAnchor.Type.LEFT)!!.margin = marginL
        B.getAnchor(ConstraintAnchor.Type.RIGHT)!!.margin = marginR
        C.getAnchor(ConstraintAnchor.Type.LEFT)!!.margin = marginL
        C.getAnchor(ConstraintAnchor.Type.RIGHT)!!.margin = marginR
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("b) A: $A B: $B C: $C")
        Assert.assertEquals((A.left - root.left - marginL).toLong(), (root.right - C.right - marginR).toLong())
        Assert.assertEquals((C.left - B.right).toLong(), (B.left - A.right).toLong())
        val matchWidth = root.width - B.width - C.width - marginL - marginR - 4 * (B.left - A.right)
        Assert.assertEquals(A.width.toLong(), 498)
        Assert.assertEquals(B.width.toLong(), C.width.toLong())
        Assert.assertEquals(B.width.toLong(), 100)
        checkPositions(A, B, C)
        // B marked as 0dp, A == C, B takes the rest
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        A.width = 100
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("c) A: $A B: $B C: $C")
        Assert.assertEquals(B.width.toLong(), 498)
        Assert.assertEquals(A.width.toLong(), C.width.toLong())
        Assert.assertEquals(A.width.toLong(), 100)
        checkPositions(A, B, C)
        // C marked as 0dp, A == B, C takes the rest
        B.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        B.width = 100
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("d) A: $A B: $B C: $C")
        Assert.assertEquals(C.width.toLong(), 498)
        Assert.assertEquals(A.width.toLong(), B.width.toLong())
        Assert.assertEquals(A.width.toLong(), 100)
        checkPositions(A, B, C)
        // A & B marked as 0dp, C == 100
        C.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        C.width = 100
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("e) A: $A B: $B C: $C")
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(A.width.toLong(), B.width.toLong()) // L
        Assert.assertEquals(A.width.toLong(), 299)
        checkPositions(A, B, C)
        // A & C marked as 0dp, B == 100
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        B.width = 100
        root.layout()
        println("f) A: $A B: $B C: $C")
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(A.width.toLong(), C.width.toLong())
        Assert.assertEquals(A.width.toLong(), 299)
        checkPositions(A, B, C)
        // B & C marked as 0dp, A == 100
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        A.width = 100
        root.layout()
        println("g) A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), C.width.toLong())
        Assert.assertEquals(B.width.toLong(), 299)
        checkPositions(A, B, C)
        // A == 0dp, B & C == 100, C is gone
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.width = 100
        B.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        B.width = 100
        C.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        C.width = 100
        C.visibility = ConstraintWidget.GONE
        root.layout()
        println("h) A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toLong(), 632)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 0)
        checkPositions(A, B, C)
    }

    private fun checkPositions(A: ConstraintWidget, B: ConstraintWidget, C: ConstraintWidget) {
        Assert.assertEquals(A.left <= A.right, true)
        Assert.assertEquals(A.right <= B.left, true)
        Assert.assertEquals(B.left <= B.right, true)
        Assert.assertEquals(B.right <= C.left, true)
        Assert.assertEquals(C.left <= C.right, true)
    }

    @Test
    fun testBasicVerticalChainThreeElements() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val marginT = 7
        val marginB = 27
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        widgets.add(C)
        widgets.add(root)
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, 0)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 0)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP, 0)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM, 0)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 0)
        root.layout()
        println("a) A: $A B: $B C: $C")
        // all elements spread equally
        Assert.assertEquals(A.height.toFloat(), B.height.toFloat(), 1f)
        Assert.assertEquals(B.height.toFloat(), C.height.toFloat(), 1f)
        Assert.assertEquals((A.top - root.top).toFloat(), (root.bottom - C.bottom).toFloat(), 1f)
        Assert.assertEquals((A.top - root.top).toFloat(), (B.top - A.bottom).toFloat(), 1f)
        Assert.assertEquals((B.top - A.bottom).toFloat(), (C.top - B.bottom).toFloat(), 1f)
        // A marked as 0dp, B == C, A takes the rest
        A.getAnchor(ConstraintAnchor.Type.TOP)!!.margin = marginT
        A.getAnchor(ConstraintAnchor.Type.BOTTOM)!!.margin = marginB
        B.getAnchor(ConstraintAnchor.Type.TOP)!!.margin = marginT
        B.getAnchor(ConstraintAnchor.Type.BOTTOM)!!.margin = marginB
        C.getAnchor(ConstraintAnchor.Type.TOP)!!.margin = marginT
        C.getAnchor(ConstraintAnchor.Type.BOTTOM)!!.margin = marginB
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("b) A: $A B: $B C: $C")
        Assert.assertEquals(A.top.toLong(), 7)
        Assert.assertEquals(C.bottom.toLong(), 573)
        Assert.assertEquals(B.bottom.toLong(), 519)
        Assert.assertEquals(A.height.toLong(), 458)
        Assert.assertEquals(B.height.toLong(), C.height.toLong())
        Assert.assertEquals(B.height.toLong(), 20)
        checkVerticalPositions(A, B, C)
        // B marked as 0dp, A == C, B takes the rest
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        A.height = 20
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("c) A: $A B: $B C: $C")
        Assert.assertEquals(B.height.toLong(), 458)
        Assert.assertEquals(A.height.toLong(), C.height.toLong())
        Assert.assertEquals(A.height.toLong(), 20)
        checkVerticalPositions(A, B, C)
        // C marked as 0dp, A == B, C takes the rest
        B.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        B.height = 20
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("d) A: $A B: $B C: $C")
        Assert.assertEquals(C.height.toLong(), 458)
        Assert.assertEquals(A.height.toLong(), B.height.toLong())
        Assert.assertEquals(A.height.toLong(), 20)
        checkVerticalPositions(A, B, C)
        // A & B marked as 0dp, C == 20
        C.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        C.height = 20
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("e) A: $A B: $B C: $C")
        Assert.assertEquals(C.height.toLong(), 20)
        Assert.assertEquals(A.height.toLong(), B.height.toLong()) // L
        Assert.assertEquals(A.height.toLong(), 239)
        checkVerticalPositions(A, B, C)
        // A & C marked as 0dp, B == 20
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        B.height = 20
        root.layout()
        println("f) A: $A B: $B C: $C")
        Assert.assertEquals(B.height.toLong(), 20)
        Assert.assertEquals(A.height.toLong(), C.height.toLong())
        Assert.assertEquals(A.height.toLong(), 239)
        checkVerticalPositions(A, B, C)
        // B & C marked as 0dp, A == 20
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        A.height = 20
        root.layout()
        println("g) A: $A B: $B C: $C")
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(B.height.toLong(), C.height.toLong())
        Assert.assertEquals(B.height.toLong(), 239)
        checkVerticalPositions(A, B, C)
        // A == 0dp, B & C == 20, C is gone
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.height = 20
        B.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        B.height = 20
        C.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        C.height = 20
        C.visibility = ConstraintWidget.GONE
        root.layout()
        println("h) A: $A B: $B C: $C")
        Assert.assertEquals(A.height.toLong(), 512)
        Assert.assertEquals(B.height.toLong(), 20)
        Assert.assertEquals(C.height.toLong(), 0)
        checkVerticalPositions(A, B, C)
    }

    private fun checkVerticalPositions(A: ConstraintWidget, B: ConstraintWidget, C: ConstraintWidget) {
        Assert.assertEquals(A.top <= A.bottom, true)
        Assert.assertEquals(A.bottom <= B.top, true)
        Assert.assertEquals(B.top <= B.bottom, true)
        Assert.assertEquals(B.bottom <= C.top, true)
        Assert.assertEquals(C.top <= C.bottom, true)
    }

    @Test
    fun testHorizontalChainWeights() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val marginL = 7
        val marginR = 27
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        widgets.add(C)
        widgets.add(root)
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, marginL)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, marginR)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, marginL)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT, marginR)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, marginL)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, marginR)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalWeight(1f)
        B.setHorizontalWeight(1f)
        C.setHorizontalWeight(1f)
        root.layout()
        println("a) A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toFloat(), B.width.toFloat(), 1f)
        Assert.assertEquals(B.width.toFloat(), C.width.toFloat(), 1f)
        A.setHorizontalWeight(1f)
        B.setHorizontalWeight(2f)
        C.setHorizontalWeight(1f)
        root.layout()
        println("b) A: $A B: $B C: $C")
        Assert.assertEquals((2 * A.width).toFloat(), B.width.toFloat(), 1f)
        Assert.assertEquals(A.width.toFloat(), C.width.toFloat(), 1f)
    }

    @Test
    fun testVerticalChainWeights() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val marginT = 7
        val marginB = 27
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        widgets.add(C)
        widgets.add(root)
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, marginT)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, marginB)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, marginT)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP, marginB)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM, marginT)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, marginB)
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setVerticalWeight(1f)
        B.setVerticalWeight(1f)
        C.setVerticalWeight(1f)
        root.layout()
        println("a) A: $A B: $B C: $C")
        Assert.assertEquals(A.height.toFloat(), B.height.toFloat(), 1f)
        Assert.assertEquals(B.height.toFloat(), C.height.toFloat(), 1f)
        A.setVerticalWeight(1f)
        B.setVerticalWeight(2f)
        C.setVerticalWeight(1f)
        root.layout()
        println("b) A: $A B: $B C: $C")
        Assert.assertEquals((2 * A.height).toFloat(), B.height.toFloat(), 1f)
        Assert.assertEquals(A.height.toFloat(), C.height.toFloat(), 1f)
    }

    @Test
    fun testHorizontalChainPacked() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val marginL = 7
        val marginR = 27
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        widgets.add(C)
        widgets.add(root)
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, marginL)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, marginR)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, marginL)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT, marginR)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, marginL)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, marginR)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("a) A: $A B: $B C: $C")
        Assert.assertEquals((A.left - root.left - marginL).toFloat(), (root.right - marginR - C.right).toFloat(), 1f)
    }

    @Test
    fun testVerticalChainPacked() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val marginT = 7
        val marginB = 27
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        widgets.add(C)
        widgets.add(root)
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, marginT)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, marginB)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, marginT)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP, marginB)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM, marginT)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, marginB)
        A.verticalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("a) A: $A B: $B C: $C")
        Assert.assertEquals((A.top - root.top - marginT).toFloat(), (root.bottom - marginB - C.bottom).toFloat(), 1f)
    }

    @Test
    fun testHorizontalChainComplex() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(50, 20)
        val E = ConstraintWidget(50, 20)
        val F = ConstraintWidget(50, 20)
        val marginL = 7
        val marginR = 19
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        D.setDebugSolverName(root.system, "D")
        E.setDebugSolverName(root.system, "E")
        F.setDebugSolverName(root.system, "F")
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        root.add(E)
        root.add(F)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, marginL)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, marginR)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, marginL)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT, marginR)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, marginL)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, marginR)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT, 0)
        D.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT, 0)
        E.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.LEFT, 0)
        E.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.RIGHT, 0)
        F.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT, 0)
        F.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT, 0)
        root.layout()
        println("a) A: $A B: $B C: $C")
        println("a) D: $D E: $E F: $F")
        Assert.assertEquals(A.width.toFloat(), B.width.toFloat(), 1f)
        Assert.assertEquals(B.width.toFloat(), C.width.toFloat(), 1f)
        Assert.assertEquals(A.width.toFloat(), 307f, 1f)
    }

    @Test
    fun testVerticalChainComplex() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(50, 20)
        val E = ConstraintWidget(50, 20)
        val F = ConstraintWidget(50, 20)
        val marginT = 7
        val marginB = 19
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        D.setDebugSolverName(root.system, "D")
        E.setDebugSolverName(root.system, "E")
        F.setDebugSolverName(root.system, "F")
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        root.add(E)
        root.add(F)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, marginT)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, marginB)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, marginT)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP, marginB)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM, marginT)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, marginB)
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP, 0)
        D.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM, 0)
        E.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.TOP, 0)
        E.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.BOTTOM, 0)
        F.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP, 0)
        F.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM, 0)
        root.layout()
        println("a) A: $A B: $B C: $C")
        println("a) D: $D E: $E F: $F")
        Assert.assertEquals(A.height.toFloat(), B.height.toFloat(), 1f)
        Assert.assertEquals(B.height.toFloat(), C.height.toFloat(), 1f)
        Assert.assertEquals(A.height.toFloat(), 174f, 1f)
    }

    @Test
    fun testHorizontalChainComplex2() {
        val root = ConstraintWidgetContainer(0, 0, 379, 591)
        val A = ConstraintWidget(100, 185)
        val B = ConstraintWidget(100, 185)
        val C = ConstraintWidget(100, 185)
        val D = ConstraintWidget(53, 17)
        val E = ConstraintWidget(42, 17)
        val F = ConstraintWidget(47, 17)
        val marginL = 0
        val marginR = 0
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        D.setDebugSolverName(root.system, "D")
        E.setDebugSolverName(root.system, "E")
        F.setDebugSolverName(root.system, "F")
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        root.add(E)
        root.add(F)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 16)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 16)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, marginL)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, marginR)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, marginL)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT, marginR)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP, 0)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, marginL)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, marginR)
        C.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP, 0)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT, 0)
        D.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT, 0)
        D.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 0)
        E.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.LEFT, 0)
        E.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.RIGHT, 0)
        E.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 0)
        F.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT, 0)
        F.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT, 0)
        F.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 0)
        root.layout()
        println("a) A: $A B: $B C: $C")
        println("a) D: $D E: $E F: $F")
        Assert.assertEquals(A.width.toFloat(), B.width.toFloat(), 1f)
        Assert.assertEquals(B.width.toFloat(), C.width.toFloat(), 1f)
        Assert.assertEquals(A.width.toLong(), 126)
    }

    @Test
    fun testVerticalChainBaseline() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        root.add(A)
        root.add(B)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, 0)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 0)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 0)
        root.layout()
        println("a) root: $root A: $A B: $B")
        val Ay = A.top
        val By = B.top
        Assert.assertEquals((A.top - root.top).toFloat(), (root.bottom - B.bottom).toFloat(), 1f)
        Assert.assertEquals((B.top - A.bottom).toFloat(), (A.top - root.top).toFloat(), 1f)
        root.add(C)
        A.baselineDistance = 7
        C.baselineDistance = 7
        C.connect(ConstraintAnchor.Type.BASELINE, A, ConstraintAnchor.Type.BASELINE, 0)
        root.layout()
        println("b) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(Ay.toFloat(), C.top.toFloat(), 1f)
        A.verticalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("c) root: $root A: $A B: $B C: $C")
    }

    @Test
    fun testWrapHorizontalChain() {
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
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 0)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 0)
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 0)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, 0)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 0)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT, 0)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 0)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("b) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(root.height.toLong(), A.height.toLong())
        Assert.assertEquals(root.height.toLong(), B.height.toLong())
        Assert.assertEquals(root.height.toLong(), C.height.toLong())
        Assert.assertEquals(root.width.toLong(), (A.width + B.width + C.width).toLong())
    }

    @Test
    fun testWrapVerticalChain() {
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
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, 0)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 0)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP, 0)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM, 0)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 0)
        root.layout()
        println("a) root: $root A: $A B: $B")
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("b) root: $root A: $A B: $B")
        Assert.assertEquals(root.width.toLong(), A.width.toLong())
        Assert.assertEquals(root.width.toLong(), B.width.toLong())
        Assert.assertEquals(root.width.toLong(), C.width.toLong())
        Assert.assertEquals(root.height.toLong(), (A.height + B.height + C.height).toLong())
    }

    @Test
    fun testPackWithBaseline() {
        val root = ConstraintWidgetContainer(0, 0, 411, 603)
        val A = ConstraintWidget(118, 93, 88, 48)
        val B = ConstraintWidget(206, 93, 88, 48)
        val C = ConstraintWidget(69, 314, 88, 48)
        val D = ConstraintWidget(83, 458, 88, 48)
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        D.setDebugSolverName(root.system, "D")
        A.baselineDistance = 29
        B.baselineDistance = 29
        C.baselineDistance = 29
        D.baselineDistance = 29
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 100)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.BASELINE, A, ConstraintAnchor.Type.BASELINE)
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, D, ConstraintAnchor.Type.TOP)
        D.connect(ConstraintAnchor.Type.TOP, C, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        C.verticalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("a) root: $root A: $A B: $B")
        println("a) root: $root C: $C D: $D")
        C.getAnchor(ConstraintAnchor.Type.TOP)!!.reset()
        root.layout()
        C.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        root.layout()
        println("a) root: $root A: $A B: $B")
        println("a) root: $root C: $C D: $D")
        Assert.assertEquals(C.bottom.toLong(), D.top.toLong())
    }

    @Test
    fun testBasicGoneChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
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
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, D, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.LEFT, C, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        B.visibility = ConstraintWidget.GONE
        root.layout()
        println("a) A: $A B: $B C: $C D: $D")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(C.left.toLong(), 250)
        Assert.assertEquals(D.left.toLong(), 500)
        B.visibility = ConstraintWidget.VISIBLE
        D.visibility = ConstraintWidget.GONE
        root.layout()
        println("b) A: $A B: $B C: $C D: $D")
    }

    @Test
    fun testGonePackChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val guideline = Guideline()
        val D = ConstraintWidget(100, 20)
        guideline.orientation = Guideline.VERTICAL
        guideline.setGuideBegin(200)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        guideline.debugName = "guideline"
        D.debugName = "D"
        root.add(A)
        root.add(B)
        root.add(guideline)
        root.add(D)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, guideline, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.LEFT, guideline, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        A.visibility = ConstraintWidget.GONE
        B.visibility = ConstraintWidget.GONE
        root.layout()
        println("a) A: $A B: $B guideline: $guideline D: $D")
        Assert.assertEquals(A.width.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 0)
        Assert.assertEquals(guideline.left.toLong(), 200)
        Assert.assertEquals(D.left.toLong(), 350)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD
        root.layout()
        println("b) A: $A B: $B guideline: $guideline D: $D")
        Assert.assertEquals(A.width.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 0)
        Assert.assertEquals(guideline.left.toLong(), 200)
        Assert.assertEquals(D.left.toLong(), 350)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        root.layout()
        println("c) A: $A B: $B guideline: $guideline D: $D")
        Assert.assertEquals(A.width.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 0)
        Assert.assertEquals(guideline.left.toLong(), 200)
        Assert.assertEquals(D.left.toLong(), 350)
    }

    @Test
    fun testVerticalGonePackChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val guideline = Guideline()
        val D = ConstraintWidget(100, 20)
        guideline.orientation = Guideline.HORIZONTAL
        guideline.setGuideBegin(200)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        guideline.debugName = "guideline"
        D.debugName = "D"
        root.add(A)
        root.add(B)
        root.add(guideline)
        root.add(D)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, guideline, ConstraintAnchor.Type.TOP)
        D.connect(ConstraintAnchor.Type.TOP, guideline, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.verticalChainStyle = ConstraintWidget.CHAIN_PACKED
        A.visibility = ConstraintWidget.GONE
        B.visibility = ConstraintWidget.GONE
        root.layout()
        println("a) A: $A B: $B guideline: $guideline D: $D")
        Assert.assertEquals(A.height.toLong(), 0)
        Assert.assertEquals(B.height.toLong(), 0)
        Assert.assertEquals(guideline.top.toLong(), 200)
        Assert.assertEquals(D.top.toLong(), 390)
        A.verticalChainStyle = ConstraintWidget.CHAIN_SPREAD
        root.layout()
        println("b) A: $A B: $B guideline: $guideline D: $D")
        Assert.assertEquals(A.height.toLong(), 0)
        Assert.assertEquals(B.height.toLong(), 0)
        Assert.assertEquals(guideline.top.toLong(), 200)
        Assert.assertEquals(D.top.toLong(), 390)
        A.verticalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        root.layout()
        println("c) A: $A B: $B guideline: $guideline D: $D")
        Assert.assertEquals(A.height.toLong(), 0)
        Assert.assertEquals(B.height.toLong(), 0)
        Assert.assertEquals(guideline.top.toLong(), 200)
        Assert.assertEquals(D.top.toLong(), 390)
    }

    @Test
    fun testVerticalDanglingChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 1000)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP, 7)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 9)
        root.layout()
        println("a) A: $A B: $B")
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(B.top.toLong(), (A.height + Math.max(7, 9)).toLong())
    }

    @Test
    fun testHorizontalWeightChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 1000)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val guidelineLeft = Guideline()
        val guidelineRight = Guideline()
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        guidelineLeft.debugName = "guidelineLeft"
        guidelineRight.debugName = "guidelineRight"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(guidelineLeft)
        root.add(guidelineRight)
        guidelineLeft.orientation = Guideline.VERTICAL
        guidelineRight.orientation = Guideline.VERTICAL
        guidelineLeft.setGuideBegin(20)
        guidelineRight.setGuideEnd(20)
        A.connect(ConstraintAnchor.Type.LEFT, guidelineLeft, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, guidelineRight, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalWeight(1f)
        B.setHorizontalWeight(1f)
        C.setHorizontalWeight(1f)
        root.layout()
        println("a) A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 20)
        Assert.assertEquals(B.left.toLong(), 207)
        Assert.assertEquals(C.left.toLong(), 393)
        Assert.assertEquals(A.width.toLong(), 187)
        Assert.assertEquals(B.width.toLong(), 186)
        Assert.assertEquals(C.width.toLong(), 187)
        C.visibility = ConstraintWidget.GONE
        root.layout()
        println("b) A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 20)
        Assert.assertEquals(B.left.toLong(), 300)
        Assert.assertEquals(C.left.toLong(), 580)
        Assert.assertEquals(A.width.toLong(), 280)
        Assert.assertEquals(B.width.toLong(), 280)
        Assert.assertEquals(C.width.toLong(), 0)
    }

    @Test
    fun testVerticalGoneChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        widgets.add(root)
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 16)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        A.getAnchor(ConstraintAnchor.Type.BOTTOM)!!.setGoneMargin(16)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 16)
        A.verticalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("a) root: $root A: $A B: $B")
        Assert.assertEquals(A.height.toFloat(), B.height.toFloat(), 1f)
        Assert.assertEquals((A.top - root.top).toFloat(), (root.bottom - B.bottom).toFloat(), 1f)
        Assert.assertEquals(A.bottom.toLong(), B.top.toLong())
        B.visibility = ConstraintWidget.GONE
        root.layout()
        println("b) root: $root A: $A B: $B")
        Assert.assertEquals((A.top - root.top).toLong(), (root.bottom - A.bottom).toLong())
        Assert.assertEquals(root.height.toLong(), 52)
    }

    @Test
    fun testVerticalGoneChain2() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
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
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 16)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        B.getAnchor(ConstraintAnchor.Type.TOP)!!.setGoneMargin(16)
        B.getAnchor(ConstraintAnchor.Type.BOTTOM)!!.setGoneMargin(16)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 16)
        A.verticalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        Assert.assertEquals((A.top - root.top).toFloat(), (root.bottom - C.bottom).toFloat(), 1f)
        Assert.assertEquals(A.bottom.toLong(), B.top.toLong())
        A.visibility = ConstraintWidget.GONE
        C.visibility = ConstraintWidget.GONE
        root.layout()
        println("b) root: $root A: $A B: $B C: $C")
        Assert.assertEquals((B.top - root.top).toLong(), (root.bottom - B.bottom).toLong())
        Assert.assertEquals(root.height.toLong(), 52)
    }

    @Test
    fun testVerticalSpreadInsideChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
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
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 16)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 16)
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.height.toFloat(), B.height.toFloat(), 1f)
        Assert.assertEquals(B.height.toFloat(), C.height.toFloat(), 1f)
        Assert.assertEquals(A.height.toFloat(), ((root.height - 32) / 3).toFloat(), 1f)
    }

    @Test
    fun testHorizontalSpreadMaxChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
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
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toFloat(), B.width.toFloat(), 1f)
        Assert.assertEquals(B.width.toFloat(), C.width.toFloat(), 1f)
        Assert.assertEquals(A.width.toFloat(), 200f, 1f)
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 50, 1f)
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 50, 1f)
        C.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 50, 1f)
        root.layout()
        println("b) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toFloat(), B.width.toFloat(), 1f)
        Assert.assertEquals(B.width.toFloat(), C.width.toFloat(), 1f)
        Assert.assertEquals(A.width.toFloat(), 50f, 1f)
    }

    @Test
    fun testPackCenterChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
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
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 16)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 16)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 16)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.verticalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.minHeight = 300
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(root.height.toLong(), 300)
        Assert.assertEquals(C.top.toLong(), ((root.height - C.height) / 2).toLong())
        Assert.assertEquals(A.top.toLong(), ((root.height - A.height - B.height) / 2).toLong())
    }

    @Test
    fun testPackCenterChainGone() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
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
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 16)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 16)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 16)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.verticalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(600, root.height.toLong())
        Assert.assertEquals(20, A.height.toLong())
        Assert.assertEquals(20, B.height.toLong())
        Assert.assertEquals(20, C.height.toLong())
        Assert.assertEquals(270, A.top.toLong())
        Assert.assertEquals(290, B.top.toLong())
        Assert.assertEquals(310, C.top.toLong())
        A.visibility = ConstraintWidget.GONE
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(600, root.height.toLong())
        Assert.assertEquals(0, A.height.toLong())
        Assert.assertEquals(20, B.height.toLong())
        Assert.assertEquals(20, C.height.toLong()) // todo not done
        Assert.assertEquals(A.top.toLong(), B.top.toLong())
        Assert.assertEquals(((600 - 40) / 2).toLong(), B.top.toLong())
        Assert.assertEquals((B.top + B.height).toLong(), C.top.toLong())
    }

    @Test
    fun testSpreadInsideChainWithMargins() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
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
        var marginOut = 0
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, marginOut)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, marginOut)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), marginOut.toLong())
        Assert.assertEquals(C.right.toLong(), (root.width - marginOut).toLong())
        Assert.assertEquals(B.left.toLong(), (A.right + (C.left - A.right - B.width) / 2).toLong())
        marginOut = 20
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, marginOut)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, marginOut)
        root.layout()
        println("b) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), marginOut.toLong())
        Assert.assertEquals(C.right.toLong(), (root.width - marginOut).toLong())
        Assert.assertEquals(B.left.toLong(), (A.right + (C.left - A.right - B.width) / 2).toLong())
    }
}