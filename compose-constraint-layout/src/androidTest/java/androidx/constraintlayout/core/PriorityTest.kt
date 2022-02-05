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

import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.ConstraintAnchor
import org.junit.Assert
import kotlin.test.Test

class PriorityTest {
    @Test
    fun testPriorityChainHorizontal() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(400, 20)
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
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT)
        B.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toLong(), 400)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(A.left.toLong(), 300)
        Assert.assertEquals(B.left.toLong(), 400)
        Assert.assertEquals(C.left.toLong(), 500)
        B.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD
        root.layout()
        println("b) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toLong(), 400)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(A.left.toLong(), 300)
        Assert.assertEquals(B.left.toLong(), 367)
        Assert.assertEquals(C.left.toLong(), 533)
        B.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        root.layout()
        println("c) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toLong(), 400)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(A.left.toLong(), 300)
        Assert.assertEquals(B.left.toLong(), 300)
        Assert.assertEquals(C.left.toLong(), 600)
    }

    @Test
    fun testPriorityChainVertical() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 1000)
        val A = ConstraintWidget(400, 400)
        val B = ConstraintWidget(100, 100)
        val C = ConstraintWidget(100, 100)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        B.verticalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("a) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.height.toLong(), 400)
        Assert.assertEquals(B.height.toLong(), 100)
        Assert.assertEquals(C.height.toLong(), 100)
        Assert.assertEquals(A.top.toLong(), 300)
        Assert.assertEquals(B.top.toLong(), 400)
        Assert.assertEquals(C.top.toLong(), 500)
        B.verticalChainStyle = ConstraintWidget.CHAIN_SPREAD
        root.layout()
        println("b) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.height.toLong(), 400)
        Assert.assertEquals(B.height.toLong(), 100)
        Assert.assertEquals(C.height.toLong(), 100)
        Assert.assertEquals(A.top.toLong(), 300)
        Assert.assertEquals(B.top.toLong(), 367)
        Assert.assertEquals(C.top.toLong(), 533)
        B.verticalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        root.layout()
        println("c) root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.height.toLong(), 400)
        Assert.assertEquals(B.height.toLong(), 100)
        Assert.assertEquals(C.height.toLong(), 100)
        Assert.assertEquals(A.top.toLong(), 300)
        Assert.assertEquals(B.top.toLong(), 300)
        Assert.assertEquals(C.top.toLong(), 600)
    }
}