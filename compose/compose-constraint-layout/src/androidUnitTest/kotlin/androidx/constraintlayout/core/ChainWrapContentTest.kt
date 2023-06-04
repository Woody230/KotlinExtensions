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

import androidx.constraintlayout.core.widgets.ConstraintAnchor
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
import androidx.constraintlayout.core.widgets.Optimizer
import org.junit.Assert
import kotlin.test.Test

class ChainWrapContentTest {
    @Test
    fun testVerticalWrapContentChain() {
        testVerticalWrapContentChain(Optimizer.OPTIMIZATION_NONE)
        testVerticalWrapContentChain(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testVerticalWrapContentChain(directResolution: Int) {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = directResolution
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
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 10)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 32)
        root.layout()
        println(
            "res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C
        )
        Assert.assertEquals(A.top.toLong(), 10)
        Assert.assertEquals(B.top.toLong(), 30)
        Assert.assertEquals(C.top.toLong(), 30)
        Assert.assertEquals(root.height.toLong(), 82)
    }

    @Test
    fun testHorizontalWrapContentChain() {
        testHorizontalWrapContentChain(Optimizer.OPTIMIZATION_NONE)
        testHorizontalWrapContentChain(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testHorizontalWrapContentChain(directResolution: Int) {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = directResolution
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
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 32)
        root.layout()
        println(
            "1/ res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C
        )
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println(
            "2/ res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C
        )
        Assert.assertEquals(A.left.toLong(), 10)
        Assert.assertEquals(B.left.toLong(), 110)
        Assert.assertEquals(C.left.toLong(), 110)
        Assert.assertEquals(root.width.toLong(), 242)
        root.minWidth = 400
        root.layout()
        println(
            "3/ res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C
        )
        Assert.assertEquals(A.left.toLong(), 10)
        Assert.assertEquals(B.left.toLong(), 110)
        Assert.assertEquals(C.left.toLong(), 268)
        Assert.assertEquals(root.width.toLong(), 400)
    }

    @Test
    fun testVerticalWrapContentChain3Elts() {
        testVerticalWrapContentChain3Elts(Optimizer.OPTIMIZATION_NONE)
        testVerticalWrapContentChain3Elts(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testVerticalWrapContentChain3Elts(directResolution: Int) {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = directResolution
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
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 10)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, D, ConstraintAnchor.Type.TOP)
        D.connect(ConstraintAnchor.Type.TOP, C, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 32)
        root.layout()
        println(
            "res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C + " D: " + D
        )
        Assert.assertEquals(A.top.toLong(), 10)
        Assert.assertEquals(B.top.toLong(), 30)
        Assert.assertEquals(C.top.toLong(), 30)
        Assert.assertEquals(D.top.toLong(), 30)
        Assert.assertEquals(root.height.toLong(), 82)
        root.minHeight = 300
        root.layout()
        println(
            "res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C + " D: " + D
        )
        Assert.assertEquals(A.top.toLong(), 10)
        Assert.assertEquals(B.top.toLong(), 30)
        Assert.assertEquals(C.top.toLong(), 139)
        Assert.assertEquals(D.top.toLong(), 248)
        Assert.assertEquals(root.height.toLong(), 300)
        root.height = 600
        root.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        root.layout()
        println(
            "res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C + " D: " + D
        )
        Assert.assertEquals(A.top.toLong(), 10)
        Assert.assertEquals(B.top.toLong(), 30)
        Assert.assertEquals(C.top.toLong(), 289)
        Assert.assertEquals(D.top.toLong(), 548)
        Assert.assertEquals(root.height.toLong(), 600)
    }

    @Test
    fun testHorizontalWrapContentChain3Elts() {
        testHorizontalWrapContentChain3Elts(Optimizer.OPTIMIZATION_NONE)
        testHorizontalWrapContentChain3Elts(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testHorizontalWrapContentChain3Elts(directResolution: Int) {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = directResolution
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
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, D, ConstraintAnchor.Type.LEFT)
        D.connect(ConstraintAnchor.Type.LEFT, C, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 32)
        root.layout()
        println(
            "res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C + " D: " + D
        )
        Assert.assertEquals(A.left.toLong(), 10)
        Assert.assertEquals(B.left.toLong(), 110)
        Assert.assertEquals(C.left.toLong(), 110)
        Assert.assertEquals(D.left.toLong(), 110)
        Assert.assertEquals(root.width.toLong(), 242)
        root.minWidth = 300
        root.layout()
        println(
            "res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C + " D: " + D
        )
        Assert.assertEquals(A.left.toLong(), 10)
        Assert.assertEquals(B.left.toLong(), 110)
        Assert.assertEquals(C.left.toLong(), 139)
        Assert.assertEquals(D.left.toLong(), 168)
        Assert.assertEquals(root.width.toLong(), 300)
        root.width = 600
        root.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        root.layout()
        println(
            "res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C + " D: " + D
        )
        Assert.assertEquals(A.left.toLong(), 10)
        Assert.assertEquals(B.left.toLong(), 110)
        Assert.assertEquals(C.left.toLong(), 289)
        Assert.assertEquals(D.left.toLong(), 468)
        Assert.assertEquals(root.width.toLong(), 600)
    }

    @Test
    fun testHorizontalWrapChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 1000)
        val A = ConstraintWidget(20, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(20, 20)
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
        B.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_WRAP, 0, 0, 0f)
        B.width = 600
        root.layout()
        println("a) A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 20)
        Assert.assertEquals(C.left.toLong(), 580)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        B.width = 600
        root.layout()
        println("b) A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 20)
        Assert.assertEquals(C.left.toLong(), 580) // doesn't expand beyond
        B.width = 100
        root.layout()
        println("c) A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 230)
        Assert.assertEquals(B.left.toLong(), 250)
        Assert.assertEquals(C.left.toLong(), 350)
        B.width = 600
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        root.layout()
        println("d) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(root.height.toLong(), 20)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 20)
        Assert.assertEquals(C.left.toLong(), 580)
        B.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        B.width = 600
        root.width = 0
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("e) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(root.height.toLong(), 20)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 20)
        Assert.assertEquals(C.left.toLong(), 620)
    }

    @Test
    fun testWrapChain() {
        val root = ConstraintWidgetContainer(0, 0, 1440, 1944)
        val A = ConstraintWidget(308, 168)
        val B = ConstraintWidget(308, 168)
        val C = ConstraintWidget(308, 168)
        val D = ConstraintWidget(308, 168)
        val E = ConstraintWidget(308, 168)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        E.debugName = "E"
        root.add(E)
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
        E.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        E.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        E.connect(ConstraintAnchor.Type.TOP, C, ConstraintAnchor.Type.BOTTOM)
        root.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("a) root: $root A: $A B: $B C: $C D: $D E: $E")
        Assert.assertEquals(root.width.toLong(), 1440)
        Assert.assertEquals(root.height.toLong(), 336)
    }

    @Test
    fun testWrapDanglingChain() {
        val root = ConstraintWidgetContainer(0, 0, 1440, 1944)
        val A = ConstraintWidget(308, 168)
        val B = ConstraintWidget(308, 168)
        val C = ConstraintWidget(308, 168)
        val D = ConstraintWidget(308, 168)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("a) root: $root A: $A B: $B")
        Assert.assertEquals(root.width.toLong(), 616)
        Assert.assertEquals(root.height.toLong(), 168)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 308)
        Assert.assertEquals(A.width.toLong(), 308)
        Assert.assertEquals(B.width.toLong(), 308)
    }
}