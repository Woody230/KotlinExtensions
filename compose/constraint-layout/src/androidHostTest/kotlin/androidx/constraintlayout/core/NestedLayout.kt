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

import androidx.constraintlayout.core.widgets.ConstraintAnchor
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
import org.junit.Assert

/**
 * Test nested layout
 */
class NestedLayout {
    // @Test
    fun testNestedLayout() {
        val root = ConstraintWidgetContainer(20, 20, 1000, 1000)
        val container = ConstraintWidgetContainer(0, 0, 100, 100)
        root.debugName = "root"
        container.debugName = "container"
        container.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        container.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        root.add(container)
        root.layout()
        println("container: $container")
        Assert.assertEquals(container.left.toLong(), 450)
        Assert.assertEquals(container.width.toLong(), 100)
        val A = ConstraintWidget(0, 0, 100, 20)
        val B = ConstraintWidget(0, 0, 50, 20)
        container.add(A)
        container.add(B)
        container.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        A.connect(ConstraintAnchor.Type.LEFT, container, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, container, ConstraintAnchor.Type.RIGHT)
        root.layout()
        println("container: $container")
        println("A: $A")
        println("B: $B")
        Assert.assertEquals(container.width.toLong(), 150)
        Assert.assertEquals(container.left.toLong(), 425)
        Assert.assertEquals(A.left.toLong(), 425)
        Assert.assertEquals(B.left.toLong(), 525)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(B.width.toLong(), 50)
    }
}