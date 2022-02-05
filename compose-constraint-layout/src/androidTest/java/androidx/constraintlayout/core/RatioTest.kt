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
import kotlin.test.Test

class RatioTest {
    @Test
    fun testWrapRatio() {
        val root = ConstraintWidgetContainer(0, 0, 700, 1920)
        val A = ConstraintWidget(231, 126)
        val B = ConstraintWidget(231, 126)
        val C = ConstraintWidget(231, 126)
        root.debugName = "root"
        root.add(A)
        root.add(B)
        root.add(C)
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        A.horizontalBiasPercent = 0.3f
        A.verticalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT, 171)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("A: $A")
        println("B: $B")
        println("C: $C")
        Assert.assertEquals(A.left >= 0, true)
        Assert.assertEquals(A.width.toLong(), A.height.toLong())
        Assert.assertEquals(A.width.toLong(), 402)
        Assert.assertEquals(root.width.toLong(), 402)
        Assert.assertEquals(root.height.toLong(), 654)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.top.toLong(), 402)
        Assert.assertEquals(B.left.toLong(), 171)
        Assert.assertEquals(C.top.toLong(), 528)
        Assert.assertEquals(C.left.toLong(), 171)
    }

    @Test
    fun testGuidelineRatioChainWrap() {
        val root = ConstraintWidgetContainer(0, 0, 700, 1920)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val guideline = Guideline()
        guideline.orientation = Guideline.HORIZONTAL
        guideline.setGuideBegin(100)
        root.debugName = "root"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(guideline)
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, guideline, ConstraintAnchor.Type.TOP)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setDimensionRatio("1:1")
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.setDimensionRatio("1:1")
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.height = 0
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("A: $A")
        println("B: $B")
        println("C: $C")
        Assert.assertEquals(root.height.toLong(), 1500)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 700)
        Assert.assertEquals(B.height.toLong(), 700)
        Assert.assertEquals(C.width.toLong(), 700)
        Assert.assertEquals(C.height.toLong(), 700)
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(B.top.toLong(), A.bottom.toLong())
        Assert.assertEquals(C.top.toLong(), B.bottom.toLong())
        Assert.assertEquals(A.left.toLong(), 300)
        Assert.assertEquals(B.left.toLong(), 0)
        Assert.assertEquals(C.left.toLong(), 0)
        root.width = 0
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("A: $A")
        println("B: $B")
        println("C: $C")
        Assert.assertEquals(root.width.toLong(), 100)
        Assert.assertEquals(root.height.toLong(), 300)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(C.height.toLong(), 100)
    }

    @Test
    fun testComplexRatioChainWrap() {
        val root = ConstraintWidgetContainer(0, 0, 700, 1920)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(100, 40)
        val X = ConstraintWidget(100, 20)
        val Y = ConstraintWidget(100, 20)
        val Z = ConstraintWidget(100, 40)
        root.debugName = "root"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        root.add(X)
        root.add(Y)
        root.add(Z)
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        X.debugName = "X"
        Y.debugName = "Y"
        Z.debugName = "Z"
        X.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        X.connect(ConstraintAnchor.Type.BOTTOM, Y, ConstraintAnchor.Type.TOP)
        X.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        X.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        X.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        X.height = 40
        Y.connect(ConstraintAnchor.Type.TOP, X, ConstraintAnchor.Type.BOTTOM)
        Y.connect(ConstraintAnchor.Type.BOTTOM, Z, ConstraintAnchor.Type.TOP)
        Y.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        Y.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        Y.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        Y.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        Y.setDimensionRatio("1:1")
        Z.connect(ConstraintAnchor.Type.TOP, Y, ConstraintAnchor.Type.BOTTOM)
        Z.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        Z.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        Z.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        Z.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        Z.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        Z.setDimensionRatio("1:1")
        root.width = 700
        root.height = 0
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("X: $X")
        println("Y: $Y")
        println("Z: $Z")
        Assert.assertEquals(root.width.toLong(), 700)
        Assert.assertEquals(root.height.toLong(), 1440)
        Assert.assertEquals(X.left.toLong(), 0)
        Assert.assertEquals(X.top.toLong(), 0)
        Assert.assertEquals(X.width.toLong(), 700)
        Assert.assertEquals(X.height.toLong(), 40)
        Assert.assertEquals(Y.left.toLong(), 0)
        Assert.assertEquals(Y.top.toLong(), 40)
        Assert.assertEquals(Y.width.toLong(), 700)
        Assert.assertEquals(Y.height.toLong(), 700)
        Assert.assertEquals(Z.left.toLong(), 0)
        Assert.assertEquals(Z.top.toLong(), 740)
        Assert.assertEquals(Z.width.toLong(), 700)
        Assert.assertEquals(Z.height.toLong(), 700)
        A.connect(ConstraintAnchor.Type.TOP, X, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.LEFT, X, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        B.connect(ConstraintAnchor.Type.TOP, X, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setDimensionRatio("1:1")
        C.connect(ConstraintAnchor.Type.TOP, X, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, D, ConstraintAnchor.Type.LEFT)
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.setDimensionRatio("1:1")
        D.connect(ConstraintAnchor.Type.TOP, X, ConstraintAnchor.Type.TOP)
        D.connect(ConstraintAnchor.Type.LEFT, C, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.RIGHT, X, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.BOTTOM, X, ConstraintAnchor.Type.BOTTOM)
        D.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.setDimensionRatio("1:1")
        root.height = 0
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("root: $root")
        println("X: $X")
        println("Y: $Y")
        println("Z: $Z")
        Assert.assertEquals(root.width.toLong(), 700)
        Assert.assertEquals(root.height.toLong(), 1440)
        Assert.assertEquals(X.left.toLong(), 0)
        Assert.assertEquals(X.top.toLong(), 0)
        Assert.assertEquals(X.width.toLong(), 700)
        Assert.assertEquals(X.height.toLong(), 40)
        Assert.assertEquals(Y.left.toLong(), 0)
        Assert.assertEquals(Y.top.toLong(), 40)
        Assert.assertEquals(Y.width.toLong(), 700)
        Assert.assertEquals(Y.height.toLong(), 700)
        Assert.assertEquals(Z.left.toLong(), 0)
        Assert.assertEquals(Z.top.toLong(), 740)
        Assert.assertEquals(Z.width.toLong(), 700)
        Assert.assertEquals(Z.height.toLong(), 700)
    }

    @Test
    fun testRatioChainWrap() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 1000)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(100, 40)
        root.debugName = "root"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        D.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        D.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.LEFT, D, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, D, ConstraintAnchor.Type.TOP)
        A.setDimensionRatio("1:1")
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.TOP, D, ConstraintAnchor.Type.TOP)
        B.setDimensionRatio("1:1")
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, D, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.TOP, D, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.BOTTOM, D, ConstraintAnchor.Type.BOTTOM)
        C.setDimensionRatio("1:1")

//        root.layout();
//        System.out.println("a) root: " + root + " D: " + D + " A: " + A + " B: " + B + " C: " + C);
//
//        root.setWidth(0);
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("b) root: $root D: $D A: $A B: $B C: $C")
        Assert.assertEquals(root.width.toLong(), 120)
        Assert.assertEquals(D.width.toLong(), 120)
        Assert.assertEquals(A.width.toLong(), 40)
        Assert.assertEquals(A.height.toLong(), 40)
        Assert.assertEquals(B.width.toLong(), 40)
        Assert.assertEquals(B.height.toLong(), 40)
        Assert.assertEquals(C.width.toLong(), 40)
        Assert.assertEquals(C.height.toLong(), 40)
    }

    @Test
    fun testRatioChainWrap2() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 1536)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(100, 40)
        val E = ConstraintWidget(100, 40)
        val F = ConstraintWidget(100, 40)
        root.debugName = "root"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        root.add(E)
        root.add(F)
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        E.debugName = "E"
        F.debugName = "F"
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        E.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        E.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        F.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        F.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        D.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.BOTTOM, E, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.LEFT, D, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, D, ConstraintAnchor.Type.TOP)
        A.setDimensionRatio("1:1")
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.TOP, D, ConstraintAnchor.Type.TOP)
        B.setDimensionRatio("1:1")
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, D, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.TOP, D, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.BOTTOM, D, ConstraintAnchor.Type.BOTTOM)
        C.setDimensionRatio("1:1")
        E.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        E.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        E.connect(ConstraintAnchor.Type.TOP, D, ConstraintAnchor.Type.BOTTOM)
        E.connect(ConstraintAnchor.Type.BOTTOM, F, ConstraintAnchor.Type.TOP)
        E.setDimensionRatio("1:1")
        F.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        F.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        F.connect(ConstraintAnchor.Type.TOP, E, ConstraintAnchor.Type.BOTTOM)
        F.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        F.setDimensionRatio("1:1")
        root.layout()
        println("a) root: $root D: $D A: $A B: $B C: $C D: $D E: $E F: $F")
        root.width = 0
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("b) root: $root D: $D A: $A B: $B C: $C D: $D E: $E F: $F")

        //assertEquals(root.getWidth(), 748);
        Assert.assertEquals(D.width.toLong(), root.width.toLong())
        Assert.assertEquals(A.width.toLong(), D.height.toLong())
        Assert.assertEquals(A.height.toLong(), D.height.toLong())
        Assert.assertEquals(B.width.toLong(), D.height.toLong())
        Assert.assertEquals(B.height.toLong(), D.height.toLong())
        Assert.assertEquals(C.width.toLong(), D.height.toLong())
        Assert.assertEquals(C.height.toLong(), D.height.toLong())
    }

    @Test
    fun testRatioMax() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 1000)
        val A = ConstraintWidget(100, 100)
        root.debugName = "root"
        root.add(A)
        A.debugName = "A"
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setVerticalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_RATIO, 0, 150, 0f)
        A.setDimensionRatio("W,16:9")
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("a) root: $root A: $A")
        Assert.assertEquals(A.width.toLong(), 267)
        Assert.assertEquals(A.height.toLong(), 150)
        Assert.assertEquals(A.top.toLong(), 425)
    }

    @Test
    fun testRatioMax2() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 1000)
        val A = ConstraintWidget(100, 100)
        root.debugName = "root"
        root.add(A)
        A.debugName = "A"
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setVerticalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_RATIO, 0, 150, 0f)
        A.setDimensionRatio("16:9")
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("a) root: $root A: $A")
        Assert.assertEquals(A.width.toFloat(), 267f, 1f)
        Assert.assertEquals(A.height.toLong(), 150)
        Assert.assertEquals(A.top.toLong(), 425)
    }

    @Test
    fun testRatioSingleTarget() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 1000)
        val A = ConstraintWidget(100, 100)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        root.add(A)
        root.add(B)
        A.debugName = "A"
        B.debugName = "B"
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setDimensionRatio("2:3")
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT, 50)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("a) root: $root A: $A B: $B")
        Assert.assertEquals(B.height.toLong(), 150)
        Assert.assertEquals(B.top.toLong(), (A.bottom - B.height / 2).toLong())
    }

    @Test
    fun testSimpleWrapRatio() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 1000)
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        root.add(A)
        A.debugName = "A"
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("a) root: $root A: $A")
        Assert.assertEquals(root.width.toLong(), 1000)
        Assert.assertEquals(root.height.toLong(), 1000)
        Assert.assertEquals(A.width.toLong(), 1000)
        Assert.assertEquals(A.height.toLong(), 1000)
    }

    @Test
    fun testSimpleWrapRatio2() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 1000)
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        root.add(A)
        A.debugName = "A"
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("a) root: $root A: $A")
        Assert.assertEquals(root.width.toLong(), 1000)
        Assert.assertEquals(root.height.toLong(), 1000)
        Assert.assertEquals(A.width.toLong(), 1000)
        Assert.assertEquals(A.height.toLong(), 1000)
    }

    @Test
    fun testNestedRatio() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 1000)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        B.setDimensionRatio("1:1")
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("a) root: $root A: $A B: $B")
        Assert.assertEquals(root.width.toLong(), 500)
        Assert.assertEquals(A.width.toLong(), 500)
        Assert.assertEquals(B.width.toLong(), 500)
        Assert.assertEquals(root.height.toLong(), 1000)
        Assert.assertEquals(A.height.toLong(), 500)
        Assert.assertEquals(B.height.toLong(), 500)
    }

    @Test
    fun testNestedRatio2() {
        val root = ConstraintWidgetContainer(0, 0, 700, 1200)
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
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        D.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalBiasPercent = 0f
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalBiasPercent = 0.5f
        D.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        D.verticalBiasPercent = 1f
        A.setDimensionRatio("1:1")
        B.setDimensionRatio("4:1")
        C.setDimensionRatio("4:1")
        D.setDimensionRatio("4:1")
        root.layout()
        println("a) root: $root A: $A B: $B C: $C D: $D")
        Assert.assertEquals(A.width.toLong(), 700)
        Assert.assertEquals(A.height.toLong(), 700)
        Assert.assertEquals(B.width.toLong(), A.width.toLong())
        Assert.assertEquals(B.height.toLong(), (B.width / 4).toLong())
        Assert.assertEquals(B.top.toLong(), A.top.toLong())
        Assert.assertEquals(C.width.toLong(), A.width.toLong())
        Assert.assertEquals(C.height.toLong(), (C.width / 4).toLong())
        Assert.assertEquals(C.top.toFloat(), ((root.height - C.height) / 2).toFloat(), 1f)
        Assert.assertEquals(D.width.toLong(), A.width.toLong())
        Assert.assertEquals(D.height.toLong(), (D.width / 4).toLong())
        Assert.assertEquals(D.top.toLong(), (A.bottom - D.height).toLong())
        root.width = 300
        root.layout()
        println("b) root: $root A: $A B: $B C: $C D: $D")
        Assert.assertEquals(A.width.toLong(), root.width.toLong())
        Assert.assertEquals(A.height.toLong(), root.width.toLong())
        Assert.assertEquals(B.width.toLong(), A.width.toLong())
        Assert.assertEquals(B.height.toLong(), (B.width / 4).toLong())
        Assert.assertEquals(B.top.toLong(), A.top.toLong())
        Assert.assertEquals(C.width.toLong(), A.width.toLong())
        Assert.assertEquals(C.height.toLong(), (C.width / 4).toLong())
        Assert.assertEquals(C.top.toFloat(), ((root.height - C.height) / 2).toFloat(), 1f)
        Assert.assertEquals(D.width.toLong(), A.width.toLong())
        Assert.assertEquals(D.height.toLong(), (D.width / 4).toLong())
        Assert.assertEquals(D.top.toLong(), (A.bottom - D.height).toLong())
        root.width = 0
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("c) root: $root A: $A B: $B C: $C D: $D")
        Assert.assertTrue("root width should be bigger than zero", root.width > 0)
        Assert.assertEquals(A.width.toLong(), root.width.toLong())
        Assert.assertEquals(A.height.toLong(), root.width.toLong())
        Assert.assertEquals(B.width.toLong(), A.width.toLong())
        Assert.assertEquals(B.height.toLong(), (B.width / 4).toLong())
        Assert.assertEquals(B.top.toLong(), A.top.toLong())
        Assert.assertEquals(C.width.toLong(), A.width.toLong())
        Assert.assertEquals(C.height.toLong(), (C.width / 4).toLong())
        Assert.assertEquals(C.top.toFloat(), ((root.height - C.height) / 2).toFloat(), 1f)
        Assert.assertEquals(D.width.toLong(), A.width.toLong())
        Assert.assertEquals(D.height.toLong(), (D.width / 4).toLong())
        Assert.assertEquals(D.top.toLong(), (A.bottom - D.height).toLong())
        root.width = 700
        root.height = 0
        root.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("d) root: $root A: $A B: $B C: $C D: $D")
        Assert.assertTrue("root width should be bigger than zero", root.height > 0)
        Assert.assertEquals(A.width.toLong(), root.width.toLong())
        Assert.assertEquals(A.height.toLong(), root.width.toLong())
        Assert.assertEquals(B.width.toLong(), A.width.toLong())
        Assert.assertEquals(B.height.toLong(), (B.width / 4).toLong())
        Assert.assertEquals(B.top.toLong(), A.top.toLong())
        Assert.assertEquals(C.width.toLong(), A.width.toLong())
        Assert.assertEquals(C.height.toFloat(), (C.width / 4).toFloat(), 1f)
        Assert.assertEquals(C.top.toFloat(), ((root.height - C.height) / 2).toFloat(), 1f)
        Assert.assertEquals(D.width.toLong(), A.width.toLong())
        Assert.assertEquals(D.height.toLong(), (D.width / 4).toLong())
        Assert.assertEquals(D.top.toLong(), (A.bottom - D.height).toLong())
    }

    @Test
    fun testNestedRatio3() {
        val root = ConstraintWidgetContainer(0, 0, 1080, 1536)
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
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setDimensionRatio("3.5:1")
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.setDimensionRatio("5:2")
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        B.verticalBiasPercent = 0.9f
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.BOTTOM)
        C.verticalBiasPercent = 0.9f

//        root.layout();
//        System.out.println("A: " + A);
//        System.out.println("B: " + B);
//        System.out.println("C: " + C);
//
//        assertEquals((float)A.getWidth() / A.getHeight(), 1f, .1f);
//        assertEquals((float)B.getWidth() / B.getHeight(), 3.5f, .1f);
//        assertEquals((float)C.getWidth() / C.getHeight(), 2.5f, .1f);
//        assertEquals(B.getTop() >= A.getTop(), true);
//        assertEquals(B.getTop() <= A.getBottom(), true);
//        assertEquals(C.getTop() >= B.getTop(), true);
//        assertEquals(C.getBottom() <= B.getBottom(), true);
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("\nA: $A")
        println("B: $B")
        println("C: $C")
        Assert.assertEquals(A.width.toFloat() / A.height, 1f, .1f)
        Assert.assertEquals(B.width.toFloat() / B.height, 3.5f, .1f)
        Assert.assertEquals(C.width.toFloat() / C.height, 2.5f, .1f)
    }

    @Test
    fun testNestedRatio4() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(264, 144)
        val B = ConstraintWidget(264, 144)
        val verticalGuideline = Guideline()
        verticalGuideline.setGuidePercent(0.34f)
        verticalGuideline.orientation = Guideline.VERTICAL
        val horizontalGuideline = Guideline()
        horizontalGuideline.setGuidePercent(0.66f)
        horizontalGuideline.orientation = Guideline.HORIZONTAL
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        horizontalGuideline.debugName = "hGuideline"
        verticalGuideline.debugName = "vGuideline"
        root.add(A)
        root.add(B)
        root.add(verticalGuideline)
        root.add(horizontalGuideline)
        A.width = 200
        A.height = 200
        A.connect(ConstraintAnchor.Type.BOTTOM, horizontalGuideline, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.LEFT, verticalGuideline, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, verticalGuideline, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, horizontalGuideline, ConstraintAnchor.Type.TOP)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setDimensionRatio("H,1:1")
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_PERCENT, 0, 0, 0.3f)
        B.connect(ConstraintAnchor.Type.BOTTOM, horizontalGuideline, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.LEFT, verticalGuideline, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, verticalGuideline, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, horizontalGuideline, ConstraintAnchor.Type.TOP)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("\nroot: $root")
        println("A: $A")
        println("B: $B")
        println("hG: $horizontalGuideline")
        println("vG: $verticalGuideline")
        Assert.assertEquals(verticalGuideline.left.toFloat(), 0.34f * root.width, 1f)
        Assert.assertEquals(horizontalGuideline.top.toFloat(), 0.66f * root.height, 1f)
        Assert.assertTrue(A.left >= 0)
        Assert.assertTrue(B.left >= 0)
        Assert.assertEquals(A.left.toLong(), (verticalGuideline.left - A.width / 2).toLong())
        Assert.assertEquals(A.top.toLong(), (horizontalGuideline.top - A.height / 2).toLong())
        Assert.assertEquals(B.left.toLong(), (verticalGuideline.left - B.width / 2).toLong())
        Assert.assertEquals(B.top.toLong(), (horizontalGuideline.top - B.height / 2).toLong())
    }

    @Test
    fun testBasicCenter() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("a) root: $root A: $A")
        Assert.assertEquals(A.left.toLong(), 450)
        Assert.assertEquals(A.top.toLong(), 290)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        root.layout()
        println("b) root: $root A: $A")
        Assert.assertEquals(A.left.toLong(), 450)
        Assert.assertEquals(A.top.toLong(), 290)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
    }

    @Test
    fun testBasicCenter2() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setVerticalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_RATIO, 0, 150, 0f)
        A.setDimensionRatio("W,16:9")
        root.layout()
        println("a) root: $root A: $A")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.width.toFloat() / A.height, 16f / 9f, .1f)
        Assert.assertEquals(A.height.toLong(), 150)
        Assert.assertEquals(A.top.toFloat(), (root.height - A.height) / 2f, 0f)
    }

    @Test
    fun testBasicRatio() {
        val root = ConstraintWidgetContainer(0, 0, 600, 1000)
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.verticalBiasPercent = 0f
        A.horizontalBiasPercent = 0f
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("a) root: $root A: $A")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(A.width.toLong(), 600)
        Assert.assertEquals(A.height.toLong(), 600)
        A.verticalBiasPercent = 1f
        root.layout()
        println("b) root: $root A: $A")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 400)
        Assert.assertEquals(A.width.toLong(), 600)
        Assert.assertEquals(A.height.toLong(), 600)
        A.verticalBiasPercent = 0f
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        root.layout()
        println("c) root: $root A: $A")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(A.width.toLong(), 600)
        Assert.assertEquals(A.height.toLong(), 600)
    }

    @Test
    fun testBasicRatio2() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("a) root: $root A: $A")
        Assert.assertEquals(A.left.toLong(), 450)
        Assert.assertEquals(A.top.toLong(), 250)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 100)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        root.layout()
        println("b) root: $root A: $A")
        Assert.assertEquals(A.left.toLong(), 450)
        Assert.assertEquals(A.top.toLong(), 250)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 100)
    }

    @Test
    fun testSimpleRatio() {
        val root = ConstraintWidgetContainer(0, 0, 200, 600)
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
        A.setDimensionRatio("3:2")
        root.layout()
        println("a) root: $root A: $A")
        Assert.assertEquals(A.width.toFloat() / A.height, 3f / 2f, .1f)
        Assert.assertTrue("A.top > 0", A.top >= 0)
        Assert.assertTrue("A.left > 0", A.left >= 0)
        Assert.assertEquals("A vertically centered", A.top.toLong(), (root.height - A.bottom).toLong())
        Assert.assertEquals("A horizontally centered", A.left.toLong(), (root.right - A.right).toLong())
        A.setDimensionRatio("1:2")
        root.layout()
        println("b) root: $root A: $A")
        Assert.assertEquals(A.width.toFloat() / A.height, 1f / 2f, .1f)
        Assert.assertTrue("A.top > 0", A.top >= 0)
        Assert.assertTrue("A.left > 0", A.left >= 0)
        Assert.assertEquals("A vertically centered", A.top.toLong(), (root.height - A.bottom).toLong())
        Assert.assertEquals("A horizontally centered", A.left.toLong(), (root.right - A.right).toLong())
    }

    @Test
    fun testRatioGuideline() {
        val root = ConstraintWidgetContainer(0, 0, 400, 600)
        val A = ConstraintWidget(100, 20)
        val guideline = Guideline()
        guideline.orientation = ConstraintWidget.VERTICAL
        guideline.setGuideBegin(200)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        root.add(guideline)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, guideline, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("3:2")
        root.layout()
        println("a) root: $root guideline: $guideline A: $A")
        Assert.assertEquals((A.width / A.height).toLong(), (3 / 2).toLong())
        Assert.assertTrue("A.top > 0", A.top >= 0)
        Assert.assertTrue("A.left > 0", A.left >= 0)
        Assert.assertEquals("A vertically centered", A.top.toLong(), (root.height - A.bottom).toLong())
        Assert.assertEquals("A horizontally centered", A.left.toLong(), (guideline.left - A.right).toLong())
        A.setDimensionRatio("1:2")
        root.layout()
        println("b) root: $root guideline: $guideline A: $A")
        Assert.assertEquals((A.width / A.height).toLong(), (1 / 2).toLong())
        Assert.assertTrue("A.top > 0", A.top >= 0)
        Assert.assertTrue("A.left > 0", A.left >= 0)
        Assert.assertEquals("A vertically centered", A.top.toLong(), (root.height - A.bottom).toLong())
        Assert.assertEquals("A horizontally centered", A.left.toLong(), (guideline.left - A.right).toLong())
    }

    @Test
    fun testRatioWithMinimum() {
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
        A.setDimensionRatio("16:9")
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.width = 0
        root.height = 0
        root.layout()
        println("a) root: $root A: $A")
        Assert.assertEquals(root.width.toLong(), 0)
        Assert.assertEquals(root.height.toLong(), 0)
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 100, 0, 0f)
        root.width = 0
        root.height = 0
        root.layout()
        println("b) root: $root A: $A")
        Assert.assertEquals(root.width.toLong(), 100)
        Assert.assertEquals(root.height.toLong(), 56)
        A.setVerticalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_SPREAD, 100, 0, 0f)
        root.width = 0
        root.height = 0
        root.layout()
        println("c) root: $root A: $A")
        Assert.assertEquals(root.width.toLong(), 178)
        Assert.assertEquals(root.height.toLong(), 100)
    }

    @Test
    fun testRatioWithPercent() {
        val root = ConstraintWidgetContainer(0, 0, 600, 1000)
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
        A.setDimensionRatio("1:1")
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_PERCENT, 0, 0, 0.7f)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        root.layout()
        println("a) root: $root A: $A")
        val w = (0.7 * root.width).toInt()
        Assert.assertEquals(A.width.toLong(), w.toLong())
        Assert.assertEquals(A.height.toLong(), w.toLong())
        Assert.assertEquals(A.left.toLong(), ((root.width - w) / 2).toLong())
        Assert.assertEquals(A.top.toLong(), ((root.height - w) / 2).toLong())
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        root.layout()
        println("b) root: $root A: $A")
        Assert.assertEquals(A.width.toLong(), w.toLong())
        Assert.assertEquals(A.height.toLong(), w.toLong())
        Assert.assertEquals(A.left.toLong(), ((root.width - w) / 2).toLong())
        Assert.assertEquals(A.top.toLong(), ((root.height - w) / 2).toLong())
    }

    @Test
    fun testRatio() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("16:9")
        root.layout()
        println("a) root: $root A: $A")
        Assert.assertEquals(A.width.toLong(), 1067)
        Assert.assertEquals(A.height.toLong(), 600)
    }

    @Test
    fun testRatio2() {
        val root = ConstraintWidgetContainer(0, 0, 1080, 1920)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalBiasPercent = 0.9f
        A.setDimensionRatio("3.5:1")
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalBiasPercent = 0.5f
        B.verticalBiasPercent = 0.9f
        B.setDimensionRatio("4:2")
        root.layout()
        println("a) root: $root A: $A B: $B")
        // A: id: A (0, 414) - (600 x 172) B: (129, 414) - (342 x 172)
        Assert.assertEquals((A.width / A.height.toFloat()).toDouble(), 3.5, 0.1)
        Assert.assertEquals((B.width / B.height.toFloat()).toDouble(), 2.0, 0.1)
        Assert.assertEquals(A.width.toFloat(), 1080f, 1f)
        Assert.assertEquals(A.height.toFloat(), 309f, 1f)
        Assert.assertEquals(B.width.toFloat(), 618f, 1f)
        Assert.assertEquals(B.height.toFloat(), 309f, 1f)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 1450)
        Assert.assertEquals(B.left.toLong(), 231)
        Assert.assertEquals(B.top.toLong(), A.top.toLong())
    }

    @Test
    fun testRatio3() {
        val root = ConstraintWidgetContainer(0, 0, 1080, 1920)
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
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalBiasPercent = 0.5f
        A.setDimensionRatio("1:1")
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalBiasPercent = 0.5f
        B.verticalBiasPercent = 0.9f
        B.setDimensionRatio("3.5:1")
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.BOTTOM)
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalBiasPercent = 0.5f
        C.verticalBiasPercent = 0.9f
        C.setDimensionRatio("5:2")
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        // A: id: A (0, 414) - (600 x 172) B: (129, 414) - (342 x 172)
        Assert.assertEquals((A.width / A.height.toFloat()).toDouble(), 1.0, 0.1)
        Assert.assertEquals((B.width / B.height.toFloat()).toDouble(), 3.5, 0.1)
        Assert.assertEquals((C.width / C.height.toFloat()).toDouble(), 2.5, 0.1)
        Assert.assertEquals(A.width.toFloat(), 1080f, 1f)
        Assert.assertEquals(A.height.toFloat(), 1080f, 1f)
        Assert.assertEquals(B.width.toFloat(), 1080f, 1f)
        Assert.assertEquals(B.height.toFloat(), 309f, 1f)
        Assert.assertEquals(C.width.toFloat(), 772f, 1f)
        Assert.assertEquals(C.height.toFloat(), 309f, 1f)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 420)
        Assert.assertEquals(B.top.toLong(), 1114)
        Assert.assertEquals(C.left.toLong(), 154)
        Assert.assertEquals(C.top.toLong(), B.top.toLong())
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
        A.setDimensionRatio("1:1")
        //        root.layout();
        println("a) root: $root A: $A")
        //        assertEquals(A.getWidth(), 1000);
//        assertEquals(A.getHeight(), 1000);
        A.width = 100
        A.height = 20
        A.setDimensionRatio("W,1:1")
        root.layout()
        println("b) root: $root A: $A")
        Assert.assertEquals(A.width.toLong(), 1000)
        Assert.assertEquals(A.height.toLong(), 1000)
    }

    @Test
    fun testDanglingRatio2() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(300, 200)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        B.debugName = "B"
        root.add(B)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 20)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 100)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 15)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setDimensionRatio("1:1")
        root.layout()
        println("a) root: $root A: $A B: $B")
        Assert.assertEquals(B.left.toLong(), 335)
        Assert.assertEquals(B.top.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 200)
        Assert.assertEquals(B.height.toLong(), 200)
    }

    @Test
    fun testDanglingRatio3() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(300, 200)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        B.debugName = "B"
        root.add(B)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 20)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 100)
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("h,1:1")
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 15)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setDimensionRatio("w,1:1")
        root.layout()
        println("a) root: $root A: $A B: $B")
        Assert.assertEquals(A.left.toLong(), 20)
        Assert.assertEquals(A.top.toLong(), 100)
        Assert.assertEquals(A.width.toLong(), 300)
        Assert.assertEquals(A.height.toLong(), 300)
        Assert.assertEquals(B.left.toLong(), 335)
        Assert.assertEquals(B.top.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 300)
        Assert.assertEquals(B.height.toLong(), 300)
    }

    @Test
    fun testChainRatio() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(300, 20)
        val C = ConstraintWidget(300, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 100)
        Assert.assertEquals(A.width.toLong(), 400)
        Assert.assertEquals(A.height.toLong(), 400)
        Assert.assertEquals(B.left.toLong(), 400)
        Assert.assertEquals(B.top.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 300)
        Assert.assertEquals(B.height.toLong(), 20)
        Assert.assertEquals(C.left.toLong(), 700)
        Assert.assertEquals(C.top.toLong(), 0)
        Assert.assertEquals(C.width.toLong(), 300)
        Assert.assertEquals(C.height.toLong(), 20)
    }

    @Test
    fun testChainRatio2() {
        val root = ConstraintWidgetContainer(0, 0, 600, 1000)
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
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 300)
        Assert.assertEquals(A.width.toLong(), 400)
        Assert.assertEquals(A.height.toLong(), 400)
        Assert.assertEquals(B.left.toLong(), 400)
        Assert.assertEquals(B.top.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 20)
        Assert.assertEquals(C.left.toLong(), 500)
        Assert.assertEquals(C.top.toLong(), 0)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(C.height.toLong(), 20)
    }

    @Test
    fun testChainRatio3() {
        val root = ConstraintWidgetContainer(0, 0, 600, 1000)
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
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 90)
        Assert.assertEquals(A.width.toLong(), 600)
        Assert.assertEquals(A.height.toLong(), 600)
        Assert.assertEquals(B.left.toLong(), 0)
        Assert.assertEquals(B.top.toLong(), 780)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 20)
        Assert.assertEquals(C.left.toLong(), 0)
        Assert.assertEquals(C.top.toLong(), 890)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(C.height.toLong(), 20)
    }

    @Test
    fun testChainRatio4() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setDimensionRatio("4:3")
        root.layout()
        println("a) root: $root A: $A B: $B")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toFloat(), 113f, 1f)
        Assert.assertEquals(A.width.toLong(), 500)
        Assert.assertEquals(A.height.toLong(), 375)
        Assert.assertEquals(B.left.toLong(), 500)
        Assert.assertEquals(B.top.toFloat(), 113f, 1f)
        Assert.assertEquals(B.width.toLong(), 500)
        Assert.assertEquals(B.height.toLong(), 375)
    }

    @Test
    fun testChainRatio5() {
        val root = ConstraintWidgetContainer(0, 0, 700, 1200)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(B)
        root.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_RATIO, 60, 0, 0f)
        root.layout()
        println("a) root: $root A: $A B: $B")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 300)
        Assert.assertEquals(A.width.toLong(), 600)
        Assert.assertEquals(A.height.toLong(), 600)
        Assert.assertEquals(B.left.toLong(), 600)
        Assert.assertEquals(B.top.toLong(), 590)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 20)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        root.layout()
        println("b) root: $root A: $A B: $B")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 300)
        Assert.assertEquals(A.width.toLong(), 600)
        Assert.assertEquals(A.height.toLong(), 600)
        Assert.assertEquals(B.left.toLong(), 600)
        Assert.assertEquals(B.top.toLong(), 590)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(B.height.toLong(), 20)
        root.width = 1080
        root.height = 1536
        A.width = 180
        A.height = 180
        B.width = 900
        B.height = 106
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_RATIO, 180, 0, 0f)
        root.layout()
        println("c) root: $root A: $A B: $B")
    }

    @Test
    fun testChainRatio6() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(264, 144)
        val B = ConstraintWidget(264, 144)
        val C = ConstraintWidget(264, 144)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        root.add(A)
        root.add(B)
        root.add(C)
        A.verticalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.horizontalBiasPercent = 0.501f
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setDimensionRatio("1:1")
        A.baselineDistance = 88
        C.baselineDistance = 88
        root.width = 1080
        root.height = 2220
        //        root.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
//        root.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
//        root.layout();
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("a) root: $root")
        println(" A: $A")
        println(" B: $B")
        println(" C: $C")
        Assert.assertEquals(A.width.toLong(), B.width.toLong())
        Assert.assertEquals(B.width.toLong(), B.height.toLong())
        Assert.assertEquals(root.width.toLong(), C.width.toLong())
        Assert.assertEquals(root.height.toLong(), (A.height + B.height + C.height).toLong())
    }
}