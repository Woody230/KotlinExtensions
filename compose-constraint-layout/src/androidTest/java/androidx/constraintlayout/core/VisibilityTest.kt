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

/**
 * Basic visibility behavior test in the solver
 */
class VisibilityTest {
    @Test
    fun testGoneSingleConnection() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        val margin = 175
        val goneMargin = 42
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, margin)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, margin)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, margin)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, margin)
        root.layout()
        println("a) A: $A B: $B")
        Assert.assertEquals(root.width.toLong(), 800)
        Assert.assertEquals(root.height.toLong(), 600)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 20)
        Assert.assertEquals(A.left.toLong(), (root.left + margin).toLong())
        Assert.assertEquals(A.top.toLong(), (root.top + margin).toLong())
        Assert.assertEquals(B.left.toLong(), (A.right + margin).toLong())
        Assert.assertEquals(B.top.toLong(), (A.bottom + margin).toLong())
        A.visibility = ConstraintWidget.GONE
        root.layout()
        println("b) A: $A B: $B")
        Assert.assertEquals(root.width.toLong(), 800)
        Assert.assertEquals(root.height.toLong(), 600)
        Assert.assertEquals(A.width.toLong(), 0)
        Assert.assertEquals(A.height.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 20)
        Assert.assertEquals(A.left.toLong(), root.left.toLong())
        Assert.assertEquals(A.top.toLong(), root.top.toLong())
        Assert.assertEquals(B.left.toLong(), (A.right + margin).toLong())
        Assert.assertEquals(B.top.toLong(), (A.bottom + margin).toLong())
        B.setGoneMargin(ConstraintAnchor.Type.LEFT, goneMargin)
        B.setGoneMargin(ConstraintAnchor.Type.TOP, goneMargin)
        root.layout()
        println("c) A: $A B: $B")
        Assert.assertEquals(root.width.toLong(), 800)
        Assert.assertEquals(root.height.toLong(), 600)
        Assert.assertEquals(A.width.toLong(), 0)
        Assert.assertEquals(A.height.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 20)
        Assert.assertEquals(A.left.toLong(), root.left.toLong())
        Assert.assertEquals(A.top.toLong(), root.top.toLong())
        Assert.assertEquals(B.left.toLong(), (A.right + goneMargin).toLong())
        Assert.assertEquals(B.top.toLong(), (A.bottom + goneMargin).toLong())
    }

    @Test
    fun testGoneDualConnection() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val guideline = Guideline()
        guideline.setGuidePercent(0.5f)
        guideline.orientation = ConstraintWidget.HORIZONTAL
        root.setDebugSolverName(root.system, "root")
        A.setDebugSolverName(root.system, "A")
        B.setDebugSolverName(root.system, "B")
        root.add(A)
        root.add(B)
        root.add(guideline)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, guideline, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.layout()
        println("a) A: $A B: $B guideline $guideline")
        Assert.assertEquals(root.width.toLong(), 800)
        Assert.assertEquals(root.height.toLong(), 600)
        Assert.assertEquals(A.left.toLong(), root.left.toLong())
        Assert.assertEquals(A.right.toLong(), root.right.toLong())
        Assert.assertEquals(B.left.toLong(), root.left.toLong())
        Assert.assertEquals(B.right.toLong(), root.right.toLong())
        Assert.assertEquals(guideline.top.toLong(), (root.height / 2).toLong())
        Assert.assertEquals(A.top.toLong(), root.top.toLong())
        Assert.assertEquals(A.bottom.toLong(), guideline.top.toLong())
        Assert.assertEquals(B.top.toLong(), A.bottom.toLong())
        Assert.assertEquals(B.bottom.toLong(), root.bottom.toLong())
        A.visibility = ConstraintWidget.GONE
        root.layout()
        println("b) A: $A B: $B guideline $guideline")
        Assert.assertEquals(root.width.toLong(), 800)
        Assert.assertEquals(root.height.toLong(), 600)
        Assert.assertEquals(A.width.toLong(), 0)
        Assert.assertEquals(A.height.toLong(), 0)
        Assert.assertEquals(A.left.toLong(), 400)
        Assert.assertEquals(A.right.toLong(), 400)
        Assert.assertEquals(B.left.toLong(), root.left.toLong())
        Assert.assertEquals(B.right.toLong(), root.right.toLong())
        Assert.assertEquals(guideline.top.toLong(), (root.height / 2).toLong())
        Assert.assertEquals(A.top.toLong(), 150)
        Assert.assertEquals(A.bottom.toLong(), 150)
        Assert.assertEquals(B.top.toLong(), 150)
        Assert.assertEquals(B.bottom.toLong(), root.bottom.toLong())
    }
}