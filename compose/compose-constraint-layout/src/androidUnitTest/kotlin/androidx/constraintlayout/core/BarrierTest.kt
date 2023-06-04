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

import androidx.constraintlayout.core.widgets.Barrier
import androidx.constraintlayout.core.widgets.ConstraintAnchor
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
import androidx.constraintlayout.core.widgets.Guideline
import androidx.constraintlayout.core.widgets.Optimizer
import org.junit.Assert
import kotlin.test.Test

/**
 * Tests for Barriers
 */
class BarrierTest {
    @Test
    fun barrierConstrainedWidth() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(200, 20)
        val barrier = Barrier()
        val guidelineStart = Guideline()
        val guidelineEnd = Guideline()
        guidelineStart.orientation = ConstraintWidget.VERTICAL
        guidelineEnd.orientation = ConstraintWidget.VERTICAL
        guidelineStart.setGuideBegin(30)
        guidelineEnd.setGuideEnd(20)
        root.setDebugSolverName(root.system, "root")
        guidelineStart.setDebugSolverName(root.system, "guidelineStart")
        guidelineEnd.setDebugSolverName(root.system, "guidelineEnd")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        barrier.setDebugSolverName(root.system, "Barrier")
        barrier.barrierType = Barrier.LEFT
        barrier.add(A)
        barrier.add(B)
        root.add(A)
        root.add(B)
        root.add(guidelineStart)
        root.add(guidelineEnd)
        root.add(barrier)
        A.connect(ConstraintAnchor.Type.LEFT, guidelineStart, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, guidelineEnd, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.LEFT, guidelineStart, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, guidelineEnd, ConstraintAnchor.Type.RIGHT)
        A.horizontalBiasPercent = 1f
        B.horizontalBiasPercent = 1f
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("guidelineStart: $guidelineStart")
        println("guidelineEnd: $guidelineEnd")
        println("A: $A")
        println("B: $B")
        println("barrier: $barrier")
        Assert.assertEquals(root.width.toLong(), 250)
        Assert.assertEquals(guidelineStart.left.toLong(), 30)
        Assert.assertEquals(guidelineEnd.left.toLong(), 230)
        Assert.assertEquals(A.left.toLong(), 130)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.left.toLong(), 30)
        Assert.assertEquals(B.width.toLong(), 200)
        Assert.assertEquals(barrier.left.toLong(), 30)
    }

    @Test
    fun barrierImage() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(200, 20)
        val C = ConstraintWidget(60, 60)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        barrier.setDebugSolverName(root.system, "Barrier")
        barrier.barrierType = Barrier.RIGHT
        barrier.add(A)
        barrier.add(B)
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(barrier)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.verticalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        C.horizontalBiasPercent = 1f
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.LEFT, barrier, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        root.layout()
        println("A: $A B: $B C: $C barrier: $barrier")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 0)
        Assert.assertEquals(B.top.toLong(), 580)
        Assert.assertEquals(C.left.toLong(), 740)
        Assert.assertEquals(C.top.toLong(), 270)
        Assert.assertEquals(barrier.left.toLong(), 200)
    }

    @Test
    fun barrierTooStrong() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(60, 60)
        val B = ConstraintWidget(100, 200)
        val C = ConstraintWidget(100, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        barrier.setDebugSolverName(root.system, "Barrier")
        barrier.barrierType = Barrier.BOTTOM
        barrier.add(B)
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(barrier)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, C, ConstraintAnchor.Type.BOTTOM)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_PARENT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_PARENT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("A: $A B: $B C: $C barrier: $barrier")
        Assert.assertEquals(A.left.toLong(), 740)
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 0)
        Assert.assertEquals(B.top.toLong(), 60)
        Assert.assertEquals(B.width.toLong(), 800)
        Assert.assertEquals(B.height.toLong(), 200)
        Assert.assertEquals(C.left.toLong(), 0)
        Assert.assertEquals(C.top.toLong(), 0)
        Assert.assertEquals(C.width.toLong(), 800)
        Assert.assertEquals(C.height.toLong(), 60)
        Assert.assertEquals(barrier.bottom.toLong(), 260)
    }

    @Test
    fun barrierMax() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(150, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        barrier.setDebugSolverName(root.system, "Barrier")
        barrier.add(A)
        root.add(A)
        root.add(barrier)
        root.add(B)
        barrier.barrierType = Barrier.RIGHT
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, barrier, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.horizontalBiasPercent = 0f
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 0, 150, 1f)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("A: $A B: $B barrier: $barrier")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(barrier.left.toLong(), 100)
        Assert.assertEquals(B.left.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 150)
    }

    @Test
    fun barrierCenter() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        barrier.setDebugSolverName(root.system, "Barrier")
        barrier.add(A)
        root.add(A)
        root.add(barrier)
        barrier.barrierType = Barrier.RIGHT
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        A.connect(ConstraintAnchor.Type.RIGHT, barrier, ConstraintAnchor.Type.RIGHT, 30)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        root.layout()
        println("A: $A barrier: $barrier")
        Assert.assertEquals(A.left.toLong(), 10)
        Assert.assertEquals(barrier.left.toLong(), 140)
    }

    @Test
    fun barrierCenter2() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        barrier.setDebugSolverName(root.system, "Barrier")
        barrier.add(A)
        root.add(A)
        root.add(barrier)
        barrier.barrierType = Barrier.LEFT
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 10)
        A.connect(ConstraintAnchor.Type.LEFT, barrier, ConstraintAnchor.Type.LEFT, 30)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        root.layout()
        println("A: $A barrier: $barrier")
        Assert.assertEquals(A.right.toLong(), (root.width - 10).toLong())
        Assert.assertEquals(barrier.left.toLong(), (A.left - 30).toLong())
    }

    @Test
    fun barrierCenter3() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        barrier.setDebugSolverName(root.system, "Barrier")
        barrier.add(A)
        barrier.add(B)
        root.add(A)
        root.add(B)
        root.add(barrier)
        barrier.barrierType = Barrier.LEFT
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        A.width = 100
        B.width = 200
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        A.horizontalBiasPercent = 1f
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        B.horizontalBiasPercent = 1f
        root.layout()
        println("A: $A B: $B barrier: $barrier")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 200)
        Assert.assertEquals(barrier.left.toLong(), B.left.toLong())
    }

    @Test
    fun barrierCenter4() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(150, 20)
        val B = ConstraintWidget(100, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        barrier.setDebugSolverName(root.system, "Barrier")
        barrier.add(A)
        barrier.add(B)
        root.add(A)
        root.add(B)
        root.add(barrier)
        barrier.barrierType = Barrier.LEFT
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.LEFT, barrier, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.LEFT, barrier, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        A.horizontalBiasPercent = 0f
        B.horizontalBiasPercent = 0f
        root.layout()
        println("A: $A B: $B barrier: $barrier")
        Assert.assertEquals(A.right.toLong(), root.width.toLong())
        Assert.assertEquals(barrier.left.toLong(), Math.min(A.left, B.left).toLong())
        Assert.assertEquals(A.left.toLong(), barrier.left.toLong())
        Assert.assertEquals(B.left.toLong(), barrier.left.toLong())
    }

    @Test
    fun barrierCenter5() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(150, 20)
        val C = ConstraintWidget(200, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        barrier.setDebugSolverName(root.system, "Barrier")
        barrier.add(A)
        barrier.add(B)
        barrier.add(C)
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(barrier)
        barrier.barrierType = Barrier.RIGHT
        A.connect(ConstraintAnchor.Type.RIGHT, barrier, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.RIGHT, barrier, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.RIGHT, barrier, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        A.horizontalBiasPercent = 0f
        B.horizontalBiasPercent = 0f
        C.horizontalBiasPercent = 0f
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        C.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        root.layout()
        println("A: $A B: $B C: $C barrier: $barrier")
        Assert.assertEquals(barrier.right.toLong(), Math.max(Math.max(A.right, B.right), C.right).toLong())
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 150)
        Assert.assertEquals(C.width.toLong(), 200)
    }

    @Test
    fun basic() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(150, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        barrier.setDebugSolverName(root.system, "Barrier")
        root.add(A)
        root.add(B)
        root.add(barrier)
        barrier.barrierType = Barrier.LEFT
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 50)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 20)
        barrier.add(A)
        barrier.add(B)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("A: $A B: $B barrier: $barrier")
        Assert.assertEquals(barrier.left.toLong(), B.left.toLong())
        barrier.barrierType = Barrier.RIGHT
        root.layout()
        println("A: $A B: $B barrier: $barrier")
        Assert.assertEquals(barrier.right.toLong(), B.right.toLong())
        barrier.barrierType = Barrier.LEFT
        B.width = 10
        root.layout()
        println("A: $A B: $B barrier: $barrier")
        Assert.assertEquals(barrier.left.toLong(), A.left.toLong())
    }

    @Test
    fun basic2() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(150, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        barrier.setDebugSolverName(root.system, "Barrier")
        root.add(A)
        root.add(B)
        root.add(barrier)
        barrier.barrierType = Barrier.BOTTOM
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setVerticalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.TOP, barrier, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        barrier.add(A)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("A: $A B: $B barrier: $barrier")
        Assert.assertEquals(barrier.top.toLong(), A.bottom.toLong())
        val actual = barrier.bottom + (root.bottom - barrier.bottom - B.height) / 2f
        Assert.assertEquals(B.top.toFloat(), actual, 1f)
    }

    @Test
    fun basic3() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(150, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        barrier.setDebugSolverName(root.system, "Barrier")
        root.add(A)
        root.add(B)
        root.add(barrier)
        barrier.barrierType = Barrier.RIGHT
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, barrier, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        barrier.add(A)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root A: $A B: $B barrier: $barrier")
        Assert.assertEquals(barrier.right.toLong(), A.right.toLong())
        Assert.assertEquals(root.width.toLong(), (A.width + B.width).toLong())
    }

    @Test
    fun basic4() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        barrier.setDebugSolverName(root.system, "Barrier")
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(barrier)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.visibility = ConstraintWidget.GONE
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.TOP, barrier, ConstraintAnchor.Type.TOP)
        barrier.add(A)
        barrier.add(B)
        barrier.barrierType = Barrier.BOTTOM
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("root: $root A: $A B: $B C: $C barrier: $barrier")
        Assert.assertEquals(B.top.toLong(), A.bottom.toLong())
        Assert.assertEquals(barrier.top.toLong(), B.bottom.toLong())
        Assert.assertEquals(C.top.toLong(), barrier.top.toLong())
    }

    @Test
    fun growArray() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(150, 20)
        val C = ConstraintWidget(175, 20)
        val D = ConstraintWidget(200, 20)
        val E = ConstraintWidget(125, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        D.setDebugSolverName(root.system, "D")
        E.setDebugSolverName(root.system, "E")
        barrier.setDebugSolverName(root.system, "Barrier")
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        root.add(E)
        root.add(barrier)
        barrier.barrierType = Barrier.LEFT
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 50)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 20)
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM, 20)
        D.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.TOP, C, ConstraintAnchor.Type.BOTTOM, 20)
        E.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        E.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        E.connect(ConstraintAnchor.Type.TOP, D, ConstraintAnchor.Type.BOTTOM, 20)
        barrier.add(A)
        barrier.add(B)
        barrier.add(C)
        barrier.add(D)
        barrier.add(E)
        root.layout()
        println("A: $A B: $B C: $C D: $D E: $E barrier: $barrier")
        Assert.assertEquals(A.left.toFloat(), ((root.width - A.width) / 2).toFloat(), 1f)
        Assert.assertEquals(B.left.toFloat(), ((root.width - B.width) / 2).toFloat(), 1f)
        Assert.assertEquals(C.left.toFloat(), ((root.width - C.width) / 2).toFloat(), 1f)
        Assert.assertEquals(D.left.toFloat(), ((root.width - D.width) / 2).toFloat(), 1f)
        Assert.assertEquals(E.left.toFloat(), ((root.width - E.width) / 2).toFloat(), 1f)
        Assert.assertEquals(barrier.left.toLong(), D.left.toLong())
    }

    @Test
    fun connection() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(150, 20)
        val C = ConstraintWidget(100, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        barrier.setDebugSolverName(root.system, "Barrier")
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(barrier)
        barrier.barrierType = Barrier.LEFT
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 50)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 20)
        C.connect(ConstraintAnchor.Type.LEFT, barrier, ConstraintAnchor.Type.LEFT, 0)
        barrier.add(A)
        barrier.add(B)
        root.layout()
        println("A: $A B: $B C: $C barrier: $barrier")
        Assert.assertEquals(barrier.left.toLong(), B.left.toLong())
        Assert.assertEquals(C.left.toLong(), barrier.left.toLong())
    }

    @Test
    fun withGuideline() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val barrier = Barrier()
        val guideline = Guideline()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        barrier.setDebugSolverName(root.system, "Barrier")
        guideline.setDebugSolverName(root.system, "Guideline")
        guideline.orientation = ConstraintWidget.VERTICAL
        guideline.setGuideBegin(200)
        barrier.barrierType = Barrier.RIGHT
        root.add(A)
        root.add(barrier)
        root.add(guideline)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 50)
        barrier.add(A)
        barrier.add(guideline)
        root.layout()
        println("A: $A guideline: $guideline barrier: $barrier")
        Assert.assertEquals(barrier.left.toLong(), guideline.left.toLong())
    }

    @Test
    fun wrapIssue() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val barrier = Barrier()
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        barrier.setDebugSolverName(root.system, "Barrier")
        barrier.barrierType = Barrier.BOTTOM
        root.add(A)
        root.add(B)
        root.add(barrier)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        barrier.add(A)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        B.connect(ConstraintAnchor.Type.TOP, barrier, ConstraintAnchor.Type.BOTTOM, 0)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 0)
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("1/ root: $root A: $A B: $B barrier: $barrier")
        Assert.assertEquals(barrier.top.toLong(), A.bottom.toLong())
        Assert.assertEquals(B.top.toLong(), barrier.bottom.toLong())
        Assert.assertEquals(root.height.toLong(), (A.height + B.height).toLong())
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setVerticalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        root.layout()
        println("2/ root: $root A: $A B: $B barrier: $barrier")
        Assert.assertEquals(barrier.top.toLong(), A.bottom.toLong())
        Assert.assertEquals(B.top.toLong(), barrier.bottom.toLong())
        Assert.assertEquals(root.height.toLong(), (A.height + B.height).toLong())
    }
}