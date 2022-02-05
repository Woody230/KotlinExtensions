/*
 * Copyright (C) 2018 The Android Open Source Project
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
package androidx.constraintlayout.core.widgets

import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import org.junit.Assert
import kotlin.test.Test

class ChainHeadTest {
    @Test
    fun basicHorizontalChainHeadTest() {
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
        var chainHead = ChainHead(A, ConstraintWidget.HORIZONTAL, false)
        chainHead.define()
        Assert.assertEquals(chainHead.head, A)
        Assert.assertEquals(chainHead.first, A)
        Assert.assertEquals(chainHead.firstVisibleWidget, A)
        Assert.assertEquals(chainHead.last, C)
        Assert.assertEquals(chainHead.lastVisibleWidget, C)
        A.visibility = ConstraintWidget.GONE
        chainHead = ChainHead(A, ConstraintWidget.HORIZONTAL, false)
        chainHead.define()
        Assert.assertEquals(chainHead.head, A)
        Assert.assertEquals(chainHead.first, A)
        Assert.assertEquals(chainHead.firstVisibleWidget, B)
        chainHead = ChainHead(A, ConstraintWidget.HORIZONTAL, true)
        chainHead.define()
        Assert.assertEquals(chainHead.head, C)
        Assert.assertEquals(chainHead.first, A)
        Assert.assertEquals(chainHead.firstVisibleWidget, B)
    }

    @Test
    fun basicVerticalChainHeadTest() {
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
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        var chainHead = ChainHead(A, ConstraintWidget.VERTICAL, false)
        chainHead.define()
        Assert.assertEquals(chainHead.head, A)
        Assert.assertEquals(chainHead.first, A)
        Assert.assertEquals(chainHead.firstVisibleWidget, A)
        Assert.assertEquals(chainHead.last, C)
        Assert.assertEquals(chainHead.lastVisibleWidget, C)
        A.visibility = ConstraintWidget.GONE
        chainHead = ChainHead(A, ConstraintWidget.VERTICAL, false)
        chainHead.define()
        Assert.assertEquals(chainHead.head, A)
        Assert.assertEquals(chainHead.first, A)
        Assert.assertEquals(chainHead.firstVisibleWidget, B)
        chainHead = ChainHead(A, ConstraintWidget.VERTICAL, true)
        chainHead.define()
        Assert.assertEquals(chainHead.head, A)
        Assert.assertEquals(chainHead.first, A)
        Assert.assertEquals(chainHead.firstVisibleWidget, B)
    }

    @Test
    fun basicMatchConstraintTest() {
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
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalWeight(1f)
        B.setHorizontalWeight(2f)
        C.setHorizontalWeight(3f)
        var chainHead = ChainHead(A, ConstraintWidget.HORIZONTAL, false)
        chainHead.define()
        Assert.assertEquals(chainHead.firstMatchConstraintWidget, A)
        Assert.assertEquals(chainHead.lastMatchConstraintWidget, C)
        Assert.assertEquals(chainHead.totalWeight, 6f, 0f)
        C.visibility = ConstraintWidget.GONE
        chainHead = ChainHead(A, ConstraintWidget.HORIZONTAL, false)
        chainHead.define()
        Assert.assertEquals(chainHead.firstMatchConstraintWidget, A)
        Assert.assertEquals(chainHead.lastMatchConstraintWidget, B)
        Assert.assertEquals(chainHead.totalWeight, 3f, 0f)
    }

    @Test
    fun chainOptimizerValuesTest() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(50, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(200, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        root.add(A, B, C)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 5)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, 5)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 1)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT, 1)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 10)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 10)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        var chainHead = ChainHead(A, ConstraintWidget.HORIZONTAL, false)
        chainHead.define()
        Assert.assertEquals(chainHead.mVisibleWidgets, 3)
        Assert.assertEquals(chainHead.mTotalSize, 367) // Takes all but first and last margins.
        Assert.assertEquals(chainHead.mTotalMargins, 32)
        Assert.assertEquals(chainHead.mWidgetsMatchCount, 0)
        Assert.assertTrue(chainHead.mOptimizable)
        B.visibility = ConstraintWidget.GONE
        chainHead = ChainHead(A, ConstraintWidget.HORIZONTAL, false)
        chainHead.define()
        Assert.assertEquals(chainHead.mVisibleWidgets, 2)
        Assert.assertEquals(chainHead.mTotalSize, 265)
        Assert.assertEquals(chainHead.mTotalMargins, 30)
        Assert.assertEquals(chainHead.mWidgetsMatchCount, 0)
        Assert.assertTrue(chainHead.mOptimizable)
        A.visibility = ConstraintWidget.GONE
        B.visibility = ConstraintWidget.VISIBLE
        C.visibility = ConstraintWidget.GONE
        chainHead = ChainHead(A, ConstraintWidget.HORIZONTAL, false)
        chainHead.define()
        Assert.assertEquals(chainHead.mVisibleWidgets, 1)
        Assert.assertEquals(chainHead.mTotalSize, 100)
        Assert.assertEquals(chainHead.mTotalMargins, 2)
        Assert.assertEquals(chainHead.mWidgetsMatchCount, 0)
        Assert.assertTrue(chainHead.mOptimizable)
        A.visibility = ConstraintWidget.VISIBLE
        B.visibility = ConstraintWidget.VISIBLE
        C.visibility = ConstraintWidget.VISIBLE
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.mMatchConstraintDefaultWidth = ConstraintWidget.MATCH_CONSTRAINT_PERCENT
        chainHead = ChainHead(A, ConstraintWidget.HORIZONTAL, false)
        chainHead.define()
        Assert.assertEquals(chainHead.mVisibleWidgets, 3)
        Assert.assertEquals(chainHead.mTotalSize, 317)
        Assert.assertEquals(chainHead.mTotalMargins, 32)
        Assert.assertEquals(chainHead.mWidgetsMatchCount, 1)
        Assert.assertFalse(chainHead.mOptimizable)
    }
}