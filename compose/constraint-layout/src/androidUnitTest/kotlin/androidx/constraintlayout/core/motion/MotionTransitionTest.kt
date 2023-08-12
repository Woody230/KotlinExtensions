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
package androidx.constraintlayout.core.motion

import androidx.constraintlayout.core.state.Transition
import androidx.constraintlayout.core.widgets.ConstraintAnchor
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
import org.junit.Assert
import kotlin.test.Test

class MotionTransitionTest {
    fun makeLayout1(): ConstraintWidgetContainer {
        val root = ConstraintWidgetContainer(1000, 1000)
        root.debugName = "root"
        root.stringId = "root"
        val button0 = ConstraintWidget(200, 20)
        button0.debugName = "button0"
        button0.stringId = "button0"
        val button1 = ConstraintWidget(200, 20)
        button1.debugName = "button1"
        button1.stringId = "button1"
        button0.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        button0.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        button1.connect(ConstraintAnchor.Type.LEFT, button0, ConstraintAnchor.Type.LEFT)
        button1.connect(ConstraintAnchor.Type.TOP, button0, ConstraintAnchor.Type.BOTTOM)
        button1.connect(ConstraintAnchor.Type.RIGHT, button0, ConstraintAnchor.Type.RIGHT)
        root.add(button0)
        root.add(button1)
        root.layout()
        return root
    }

    fun makeLayout2(): ConstraintWidgetContainer {
        val root = ConstraintWidgetContainer(1000, 1000)
        root.debugName = "root"
        val button0 = ConstraintWidget(200, 20)
        button0.debugName = "button0"
        button0.stringId = "button0"
        val button1 = ConstraintWidget(200, 20)
        button1.debugName = "button1"
        button1.stringId = "button1"
        button0.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        button0.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        button1.connect(ConstraintAnchor.Type.LEFT, button0, ConstraintAnchor.Type.LEFT)
        button1.connect(ConstraintAnchor.Type.BOTTOM, button0, ConstraintAnchor.Type.TOP)
        button1.connect(ConstraintAnchor.Type.RIGHT, button0, ConstraintAnchor.Type.RIGHT)
        root.add(button0)
        root.add(button1)
        root.layout()
        return root
    }

    @Test
    fun testTransition() {
        val transition = Transition()
        val cwc1 = makeLayout1()
        val cwc2 = makeLayout2()
        for (child in cwc1.children) {
            val wf = transition.getStart(child)
            wf.widget = child
        }
        transition.updateFrom(cwc1, Transition.START)
        for (child in cwc2.children) {
            val wf = transition.getEnd(child)
            wf.widget = child
        }
        transition.updateFrom(cwc2, Transition.END)
        transition.interpolate(cwc1.width, cwc1.height, 0.5f)
        val s1 = transition.getStart("button1")
        val e1 = transition.getEnd("button1")
        val f1 = transition.getInterpolated("button1")
        Assert.assertNotNull(f1)
        Assert.assertEquals(s1!!.top, 20)
        Assert.assertEquals(s1.left, 0)
        Assert.assertEquals(e1!!.top, 960)
        Assert.assertEquals(e1.left, 800)
        println(s1.top.toString() + " ," + s1.left + " ----  " + s1.widget!!.top + " ," + s1.widget!!.left)
        println(e1.top.toString() + " ," + e1.left + " ----  " + e1.widget!!.top + " ," + e1.widget!!.left)
        println(f1!!.top.toString() + " ," + f1.left)
        Assert.assertEquals(f1!!.top, 490)
        Assert.assertEquals(f1.left, 400)
        println(s1.top.toString() + " ," + s1.left + " ----  " + s1.widget!!.top + " ," + s1.widget!!.left)
        println(e1.top.toString() + " ," + e1.left + " ----  " + e1.widget!!.top + " ," + e1.widget!!.left)
        println(f1.top.toString() + " ," + f1.left)
    }
}