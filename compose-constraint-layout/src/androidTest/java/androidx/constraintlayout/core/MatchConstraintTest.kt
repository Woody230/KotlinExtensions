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

import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import androidx.constraintlayout.core.widgets.Guideline
import androidx.constraintlayout.core.widgets.ConstraintAnchor
import org.junit.Assert
import org.junit.Test

class MatchConstraintTest {
    @Test
    fun testSimpleMinMatch() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 150, 200, 1f)
        root.add(A)
        root.add(B)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("a) root: $root A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 150)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(root.width.toLong(), 150)
        B.width = 200
        root.width = 0
        root.layout()
        println("b) root: $root A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 200)
        Assert.assertEquals(B.width.toLong(), 200)
        Assert.assertEquals(root.width.toLong(), 200)
        B.width = 300
        root.width = 0
        root.layout()
        println("c) root: $root A: $A B: $B")
        Assert.assertEquals(A.width.toLong(), 200)
        Assert.assertEquals(B.width.toLong(), 300)
        Assert.assertEquals(root.width.toLong(), 300)
    }

    @Test
    fun testMinMaxMatch() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val guidelineA = Guideline()
        guidelineA.orientation = Guideline.VERTICAL
        guidelineA.setGuideBegin(100)
        val guidelineB = Guideline()
        guidelineB.orientation = Guideline.VERTICAL
        guidelineB.setGuideEnd(100)
        root.add(guidelineA)
        root.add(guidelineB)
        val A = ConstraintWidget(100, 20)
        A.connect(ConstraintAnchor.Type.LEFT, guidelineA, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, guidelineB, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 150, 200, 1f)
        root.add(A)
        root.debugName = "root"
        guidelineA.debugName = "guideline A"
        guidelineB.debugName = "guideline B"
        A.debugName = "A"
        root.layout()
        println("a) root: $root guideA: $guidelineA A: $A guideB: $guidelineB")
        Assert.assertEquals(root.width.toLong(), 800)
        Assert.assertEquals(A.width.toLong(), 200)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        A.width = 100
        root.layout()
        println("b) root: $root guideA: $guidelineA A: $A guideB: $guidelineB")
        Assert.assertEquals(root.width.toLong(), 350)
        Assert.assertEquals(A.width.toLong(), 150)
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 150, 200, 1f)
        root.layout()
        println("c) root: $root guideA: $guidelineA A: $A guideB: $guidelineB")
        Assert.assertEquals(root.width.toLong(), 350)
        Assert.assertEquals(A.width.toLong(), 150)
        root.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        root.width = 800
        root.layout()
        println("d) root: $root guideA: $guidelineA A: $A guideB: $guidelineB")
        Assert.assertEquals(root.width.toLong(), 800)
        Assert.assertEquals(A.width.toLong(), 150) // because it's wrap
        A.width = 250
        root.layout()
        println("e) root: $root guideA: $guidelineA A: $A guideB: $guidelineB")
        Assert.assertEquals(root.width.toLong(), 800)
        Assert.assertEquals(A.width.toLong(), 200)
        A.width = 700
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 150, 0, 1f)
        root.layout()
        println("f) root: $root guideA: $guidelineA A: $A guideB: $guidelineB")
        Assert.assertEquals(root.width.toLong(), 800)
        Assert.assertEquals(A.width.toLong(), 600)
        A.width = 700
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 150, 0, 1f)
        root.layout()
        println("g) root: $root guideA: $guidelineA A: $A guideB: $guidelineB")
        Assert.assertEquals(root.width.toLong(), 800)
        Assert.assertEquals(A.width.toLong(), 600)
        A.width = 700
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.width = 0
        root.layout()
        println("h) root: $root guideA: $guidelineA A: $A guideB: $guidelineB")
        Assert.assertEquals(root.width.toLong(), 900)
        Assert.assertEquals(A.width.toLong(), 700)
        A.width = 700
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 150, 0, 1f)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        Assert.assertEquals(root.width.toLong(), 350)
        Assert.assertEquals(A.width.toLong(), 150)
        println("i) root: $root guideA: $guidelineA A: $A guideB: $guidelineB")
    }

    @Test
    fun testSimpleHorizontalMatch() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        C.setDebugSolverName(root.system, "C")
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        C.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 0)
        C.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, 0)
        root.add(A)
        root.add(B)
        root.add(C)
        root.layout()
        println("a) A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertTrue(C.left >= A.right)
        Assert.assertTrue(C.right <= B.left)
        Assert.assertEquals((C.left - A.right).toLong(), (B.left - C.right).toLong())
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("b) A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 600)
        Assert.assertTrue(C.left >= A.right)
        Assert.assertTrue(C.right <= B.left)
        Assert.assertEquals((C.left - A.right).toLong(), (B.left - C.right).toLong())
        C.width = 144
        C.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        root.layout()
        println("c) A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 144)
        Assert.assertTrue(C.left >= A.right)
        Assert.assertTrue(C.right <= B.left)
        Assert.assertEquals((C.left - A.right).toLong(), (B.left - C.right).toLong())
        C.width = 1000
        C.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        root.layout()
        println("d) A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 600)
        Assert.assertTrue(C.left >= A.right)
        Assert.assertTrue(C.right <= B.left)
        Assert.assertEquals((C.left - A.right).toLong(), (B.left - C.right).toLong())
    }

    @Test
    fun testDanglingRatio() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        root.layout()
        println("a) root: $root A: $A")
    }
}