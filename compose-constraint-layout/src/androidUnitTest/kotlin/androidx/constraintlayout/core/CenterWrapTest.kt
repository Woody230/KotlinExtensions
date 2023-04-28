/*
 * Copyright (C) 2017 The Android Open Source Project
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
import org.junit.Assert
import kotlin.test.Test

class CenterWrapTest {
    @Test
    fun testRatioCenter() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "Root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio(0.3f, ConstraintWidget.VERTICAL)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setDimensionRatio(1f, ConstraintWidget.VERTICAL)
        //        root.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        root.optimizationLevel = 0
        root.layout()
        println("root: $root A: $A")
    }

    @Test
    fun testSimpleWrap() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        root.debugName = "Root"
        A.debugName = "A"
        root.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.optimizationLevel = 0
        root.layout()
        println("root: $root A: $A")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(root.width.toLong(), 100)
        Assert.assertEquals(root.height.toLong(), 20)
    }

    @Test
    fun testSimpleWrap2() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        root.debugName = "Root"
        A.debugName = "A"
        root.add(A)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.optimizationLevel = 0
        root.layout()
        println("root: $root A: $A")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(root.width.toLong(), 100)
        Assert.assertEquals(root.height.toLong(), 20)
    }

    @Test
    fun testWrap() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        root.debugName = "Root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.optimizationLevel = 0
        root.layout()
        println("root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(B.height.toLong(), 20)
        Assert.assertEquals(C.height.toLong(), 20)
    }

    @Test
    fun testWrapHeight() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val TL = ConstraintWidget(100, 20)
        val TRL = ConstraintWidget(100, 20)
        val TBL = ConstraintWidget(100, 20)
        val IMG = ConstraintWidget(100, 100)
        root.debugName = "root"
        TL.debugName = "TL"
        TRL.debugName = "TRL"
        TBL.debugName = "TBL"
        IMG.debugName = "IMG"

        // vertical
        TL.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        TL.connect(ConstraintAnchor.Type.BOTTOM, TBL, ConstraintAnchor.Type.BOTTOM)
        TRL.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        //TRL.connect(ConstraintAnchor.Type.BOTTOM, TBL, ConstraintAnchor.Type.TOP);
        TBL.connect(ConstraintAnchor.Type.TOP, TRL, ConstraintAnchor.Type.BOTTOM)
        IMG.connect(ConstraintAnchor.Type.TOP, TBL, ConstraintAnchor.Type.BOTTOM)
        IMG.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.add(TL)
        root.add(TRL)
        root.add(TBL)
        root.add(IMG)
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("a) root: $root TL: $TL TRL: $TRL TBL: $TBL IMG: $IMG")
        Assert.assertEquals(root.height.toLong(), 140)
    }

    @Test
    fun testComplexLayout() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val IMG = ConstraintWidget(100, 100)
        val margin = 16
        val BUTTON = ConstraintWidget(50, 50)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        IMG.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        IMG.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        IMG.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        IMG.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        BUTTON.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, margin)
        BUTTON.connect(ConstraintAnchor.Type.TOP, IMG, ConstraintAnchor.Type.BOTTOM)
        BUTTON.connect(ConstraintAnchor.Type.BOTTOM, IMG, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, margin)
        A.connect(ConstraintAnchor.Type.TOP, BUTTON, ConstraintAnchor.Type.BOTTOM, margin)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, margin)
        B.connect(ConstraintAnchor.Type.TOP, BUTTON, ConstraintAnchor.Type.BOTTOM, margin)
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, margin)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, margin)
        C.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, margin)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.add(IMG)
        root.add(BUTTON)
        root.add(A)
        root.add(B)
        root.add(C)
        root.debugName = "root"
        IMG.debugName = "IMG"
        BUTTON.debugName = "BUTTON"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        root.layout()
        println("a) root: $root IMG: $IMG BUTTON: $BUTTON A: $A B: $B C: $C")
        Assert.assertEquals(root.width.toLong(), 800)
        Assert.assertEquals(root.height.toLong(), 600)
        Assert.assertEquals(IMG.width.toLong(), root.width.toLong())
        Assert.assertEquals(BUTTON.width.toLong(), 50)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(IMG.height.toLong(), 100)
        Assert.assertEquals(BUTTON.height.toLong(), 50)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(B.height.toLong(), 20)
        Assert.assertEquals(C.height.toLong(), 20)
        Assert.assertEquals(IMG.left.toLong(), 0)
        Assert.assertEquals(IMG.right.toLong(), root.right.toLong())
        Assert.assertEquals(BUTTON.left.toLong(), 734)
        Assert.assertEquals(BUTTON.top.toLong(), (IMG.bottom - BUTTON.height / 2).toLong())
        Assert.assertEquals(A.left.toLong(), margin.toLong())
        Assert.assertEquals(A.top.toLong(), (BUTTON.bottom + margin).toLong())
        Assert.assertEquals(B.right.toLong(), (root.right - margin).toLong())
        Assert.assertEquals(B.top.toLong(), A.top.toLong())
        Assert.assertEquals(C.left.toLong(), 350)
        Assert.assertEquals(C.right.toLong(), 450)
        Assert.assertEquals(C.top.toFloat(), 379f, 1f)
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        root.optimizationLevel = 0
        println("b) root: $root IMG: $IMG BUTTON: $BUTTON A: $A B: $B C: $C")
        Assert.assertEquals(root.width.toLong(), 800)
        Assert.assertEquals(root.height.toLong(), 197)
        Assert.assertEquals(IMG.width.toLong(), root.width.toLong())
        Assert.assertEquals(BUTTON.width.toLong(), 50)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 100)
        Assert.assertEquals(C.width.toLong(), 100)
        Assert.assertEquals(IMG.height.toLong(), 100)
        Assert.assertEquals(BUTTON.height.toLong(), 50)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(B.height.toLong(), 20)
        Assert.assertEquals(C.height.toLong(), 20)
        Assert.assertEquals(IMG.left.toLong(), 0)
        Assert.assertEquals(IMG.right.toLong(), root.right.toLong())
        Assert.assertEquals(BUTTON.left.toLong(), 734)
        Assert.assertEquals(BUTTON.top.toLong(), (IMG.bottom - BUTTON.height / 2).toLong())
        Assert.assertEquals(A.left.toLong(), margin.toLong())
        Assert.assertEquals(A.top.toLong(), (BUTTON.bottom + margin).toLong())
        Assert.assertEquals(B.right.toLong(), (root.right - margin).toLong())
        Assert.assertEquals(B.top.toLong(), A.top.toLong())
        Assert.assertEquals(C.left.toLong(), 350)
        Assert.assertEquals(C.right.toLong(), 450)
        Assert.assertEquals(C.top.toLong(), (A.bottom + margin).toLong())
    }

    @Test
    fun testWrapCenter() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val TextBox = ConstraintWidget(100, 50)
        val TextBoxGone = ConstraintWidget(100, 50)
        val ValueBox = ConstraintWidget(20, 20)
        root.debugName = "root"
        TextBox.debugName = "TextBox"
        TextBoxGone.debugName = "TextBoxGone"
        ValueBox.debugName = "ValueBox"

        // vertical
        TextBox.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        TextBox.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 10)
        TextBox.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        TextBox.connect(ConstraintAnchor.Type.RIGHT, ValueBox, ConstraintAnchor.Type.LEFT, 10)
        ValueBox.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 10)
        ValueBox.connect(ConstraintAnchor.Type.TOP, TextBox, ConstraintAnchor.Type.TOP)
        ValueBox.connect(ConstraintAnchor.Type.BOTTOM, TextBox, ConstraintAnchor.Type.BOTTOM)
        TextBoxGone.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        TextBoxGone.connect(ConstraintAnchor.Type.TOP, TextBox, ConstraintAnchor.Type.BOTTOM, 10)
        TextBoxGone.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        TextBoxGone.connect(ConstraintAnchor.Type.RIGHT, TextBox, ConstraintAnchor.Type.RIGHT)
        TextBoxGone.visibility = ConstraintWidget.GONE
        root.add(TextBox)
        root.add(ValueBox)
        root.add(TextBoxGone)
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("a) root: $root TextBox: $TextBox ValueBox: $ValueBox TextBoxGone: $TextBoxGone")
        Assert.assertEquals(ValueBox.top.toLong(), (TextBox.top + (TextBox.height - ValueBox.height) / 2).toLong())
        Assert.assertEquals(root.height.toLong(), 60)
    }
}