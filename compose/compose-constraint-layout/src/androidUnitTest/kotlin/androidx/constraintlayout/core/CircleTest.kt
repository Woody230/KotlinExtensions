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
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
import org.junit.Assert
import kotlin.test.Test

class CircleTest {
    @Test
    fun basic() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 600)
        val A = ConstraintWidget(100, 20)
        val w1 = ConstraintWidget(10, 10)
        val w2 = ConstraintWidget(10, 10)
        val w3 = ConstraintWidget(10, 10)
        val w4 = ConstraintWidget(10, 10)
        val w5 = ConstraintWidget(10, 10)
        val w6 = ConstraintWidget(10, 10)
        val w7 = ConstraintWidget(10, 10)
        val w8 = ConstraintWidget(10, 10)
        val w9 = ConstraintWidget(10, 10)
        val w10 = ConstraintWidget(10, 10)
        val w11 = ConstraintWidget(10, 10)
        val w12 = ConstraintWidget(10, 10)
        root.debugName = "root"
        A.debugName = "A"
        w1.debugName = "w1"
        w2.debugName = "w2"
        w3.debugName = "w3"
        w4.debugName = "w4"
        w5.debugName = "w5"
        w6.debugName = "w6"
        w7.debugName = "w7"
        w8.debugName = "w8"
        w9.debugName = "w9"
        w10.debugName = "w10"
        w11.debugName = "w11"
        w12.debugName = "w12"
        root.add(A)
        root.add(w1)
        root.add(w2)
        root.add(w3)
        root.add(w4)
        root.add(w5)
        root.add(w6)
        root.add(w7)
        root.add(w8)
        root.add(w9)
        root.add(w10)
        root.add(w11)
        root.add(w12)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        w1.connectCircularConstraint(A, 30f, 50)
        w2.connectCircularConstraint(A, 60f, 50)
        w3.connectCircularConstraint(A, 90f, 50)
        w4.connectCircularConstraint(A, 120f, 50)
        w5.connectCircularConstraint(A, 150f, 50)
        w6.connectCircularConstraint(A, 180f, 50)
        w7.connectCircularConstraint(A, 210f, 50)
        w8.connectCircularConstraint(A, 240f, 50)
        w9.connectCircularConstraint(A, 270f, 50)
        w10.connectCircularConstraint(A, 300f, 50)
        w11.connectCircularConstraint(A, 330f, 50)
        w12.connectCircularConstraint(A, 360f, 50)
        root.layout()
        println("w1: $w1")
        println("w2: $w2")
        println("w3: $w3")
        println("w4: $w4")
        println("w5: $w5")
        println("w6: $w6")
        println("w7: $w7")
        println("w8: $w8")
        println("w9: $w9")
        println("w10: $w10")
        println("w11: $w11")
        println("w12: $w12")
        Assert.assertEquals(w1.left.toLong(), 520)
        Assert.assertEquals(w1.top.toLong(), 252)
        Assert.assertEquals(w2.left.toLong(), 538)
        Assert.assertEquals(w2.top.toLong(), 270)
        Assert.assertEquals(w3.left.toLong(), 545)
        Assert.assertEquals(w3.top.toLong(), 295)
        Assert.assertEquals(w4.left.toLong(), 538)
        Assert.assertEquals(w4.top.toLong(), 320)
        Assert.assertEquals(w5.left.toLong(), 520)
        Assert.assertEquals(w5.top.toLong(), 338)
        Assert.assertEquals(w6.left.toLong(), 495)
        Assert.assertEquals(w6.top.toLong(), 345)
        Assert.assertEquals(w7.left.toLong(), 470)
        Assert.assertEquals(w7.top.toLong(), 338)
        Assert.assertEquals(w8.left.toLong(), 452)
        Assert.assertEquals(w8.top.toLong(), 320)
        Assert.assertEquals(w9.left.toLong(), 445)
        Assert.assertEquals(w9.top.toLong(), 295)
        Assert.assertEquals(w10.left.toLong(), 452)
        Assert.assertEquals(w10.top.toLong(), 270)
        Assert.assertEquals(w11.left.toLong(), 470)
        Assert.assertEquals(w11.top.toLong(), 252)
        Assert.assertEquals(w12.left.toLong(), 495)
        Assert.assertEquals(w12.top.toLong(), 245)
    }
}