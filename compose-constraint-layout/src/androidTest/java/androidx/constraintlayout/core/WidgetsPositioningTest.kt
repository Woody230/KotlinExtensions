/*
 * Copyright (C) 2015 The Android Open Source Project
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
import org.junit.Before
import kotlin.test.Test
import java.lang.Exception
import java.util.ArrayList

class WidgetsPositioningTest {
    var s = LinearSystem()
    var optimize = false
    @Before
    fun setUp() {
        s = LinearSystem()
        LinearEquation.Companion.resetNaming()
    }

    @Test
    fun testCentering() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(20, 100)
        val C = ConstraintWidget(100, 20)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 200)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP, 0)
        B.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM, 0)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.TOP, 0)
        C.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.BOTTOM, 0)
        root.add(A)
        root.add(B)
        root.add(C)
        root.layout()
        println("A: $A B: $B C: $C")
    }

    @Test
    fun testDimensionRatio() {
        val A = ConstraintWidget(0, 0, 600, 600)
        val B = ConstraintWidget(100, 100)
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        val margin = 10
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.LEFT, margin)
        B.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT, margin)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP, margin)
        B.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM, margin)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.debugName = "A"
        B.debugName = "B"
        val ratio = 0.3f
        // First, let's check vertical ratio
        B.setDimensionRatio(ratio, ConstraintWidget.VERTICAL)
        runTestOnWidgets(widgets) {
            println("a) A: $A B: $B")
            Assert.assertEquals(B.width.toLong(), (A.width - 2 * margin).toLong())
            Assert.assertEquals(B.height.toLong(), (ratio * B.width).toLong())
            Assert.assertEquals((B.top - A.top).toLong(), ((A.height - B.height) / 2).toLong())
            Assert.assertEquals((A.bottom - B.bottom).toLong(), ((A.height - B.height) / 2).toLong())
            Assert.assertEquals((B.top - A.top).toLong(), (A.bottom - B.bottom).toLong())
        }
        B.verticalBiasPercent = 1f
        runTestOnWidgets(widgets) {
            println("b) A: $A B: $B")
            Assert.assertEquals(B.width.toLong(), (A.width - 2 * margin).toLong())
            Assert.assertEquals(B.height.toLong(), (ratio * B.width).toLong())
            Assert.assertEquals(B.top.toLong(), (A.height - B.height - margin).toLong())
            Assert.assertEquals(A.bottom.toLong(), (B.bottom + margin).toLong())
        }
        B.verticalBiasPercent = 0f
        runTestOnWidgets(widgets) {
            println("c) A: $A B: $B")
            Assert.assertEquals(B.width.toLong(), (A.width - 2 * margin).toLong())
            Assert.assertEquals(B.height.toLong(), (ratio * B.width).toLong())
            Assert.assertEquals(B.top.toLong(), (A.top + margin).toLong())
            Assert.assertEquals(B.bottom.toLong(), (A.top + B.height + margin).toLong())
        }
        // Then, let's check horizontal ratio
        B.setDimensionRatio(ratio, ConstraintWidget.HORIZONTAL)
        runTestOnWidgets(widgets) {
            println("d) A: $A B: $B")
            Assert.assertEquals(B.height.toLong(), (A.height - 2 * margin).toLong())
            Assert.assertEquals(B.width.toLong(), (ratio * B.height).toLong() )
            Assert.assertEquals((B.left - A.left).toLong(), ((A.width - B.width) / 2).toLong())
            Assert.assertEquals((A.right - B.right).toLong(), ((A.width - B.width) / 2).toLong())
        }
        B.horizontalBiasPercent = 1f
        runTestOnWidgets(widgets) {
            println("e) A: $A B: $B")
            Assert.assertEquals(B.height.toLong(), (A.height - 2 * margin).toLong())
            Assert.assertEquals(B.width.toLong(), (ratio * B.height).toLong())
            Assert.assertEquals(B.right.toLong(), (A.right - margin).toLong())
            Assert.assertEquals(B.left.toLong(), (A.right - B.width - margin).toLong())
        }
        B.horizontalBiasPercent = 0f
        runTestOnWidgets(widgets) {
            println("f) A: $A B: $B")
            Assert.assertEquals(B.height.toLong(), (A.height - 2 * margin).toLong())
            Assert.assertEquals(B.width.toLong(), (ratio * B.height).toLong())
            Assert.assertEquals(B.right.toLong(), (A.left + margin + B.width).toLong())
            Assert.assertEquals(B.left.toLong(), (A.left + margin).toLong())
        }
    }

    @Test
    fun testCreateManyVariables() {
        val rootWidget = ConstraintWidgetContainer(0, 0, 600, 400)
        val previous = ConstraintWidget(0, 0, 100, 20)
        rootWidget.add(previous)
        for (i in 0..99) {
            val w = ConstraintWidget(0, 0, 100, 20)
            w.connect(ConstraintAnchor.Type.LEFT, previous, ConstraintAnchor.Type.RIGHT, 20)
            w.connect(ConstraintAnchor.Type.RIGHT, rootWidget, ConstraintAnchor.Type.RIGHT, 20)
            rootWidget.add(w)
        }
        rootWidget.layout()
    }

    @Test
    fun testWidgetCenterPositioning() {
        val x = 20
        val y = 30
        val rootWidget = ConstraintWidget(x, y, 600, 400)
        val centeredWidget = ConstraintWidget(100, 20)
        val widgets = ArrayList<ConstraintWidget>()
        centeredWidget.resetSolverVariables(s.cache)
        rootWidget.resetSolverVariables(s.cache)
        widgets.add(centeredWidget)
        widgets.add(rootWidget)
        centeredWidget.debugName = "A"
        rootWidget.debugName = "Root"
        centeredWidget.connect(ConstraintAnchor.Type.CENTER_X, rootWidget, ConstraintAnchor.Type.CENTER_X)
        centeredWidget.connect(ConstraintAnchor.Type.CENTER_Y, rootWidget, ConstraintAnchor.Type.CENTER_Y)
        runTestOnWidgets(widgets) {
            println("\n*** rootWidget: $rootWidget centeredWidget: $centeredWidget")
            val left = centeredWidget.left
            val top = centeredWidget.top
            val right = centeredWidget.right
            val bottom = centeredWidget.bottom
            Assert.assertEquals(left.toLong(), (x + 250).toLong())
            Assert.assertEquals(right.toLong(), (x + 350).toLong())
            Assert.assertEquals(top.toLong(), (y + 190).toLong())
            Assert.assertEquals(bottom.toLong(), (y + 210).toLong())
        }
    }

    @Test
    fun testBaselinePositioning() {
        val A = ConstraintWidget(20, 230, 200, 70)
        val B = ConstraintWidget(200, 60, 200, 100)
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        A.debugName = "A"
        B.debugName = "B"
        A.baselineDistance = 40
        B.baselineDistance = 60
        B.connect(ConstraintAnchor.Type.BASELINE, A, ConstraintAnchor.Type.BASELINE)
        val root = ConstraintWidgetContainer(0, 0, 1000, 1000)
        root.debugName = "root"
        root.add(A)
        root.add(B)
        root.layout()
        Assert.assertEquals(
            (B.top + B.baselineDistance).toLong(), (
                    A.top + A.baselineDistance).toLong()
        )
        runTestOnWidgets(widgets) {
            Assert.assertEquals(
                (B.top + B.baselineDistance).toLong(), (
                        A.top + A.baselineDistance).toLong()
            )
        }
    }

    //@Test
    fun testAddingWidgets() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 1000)
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        val widgetsA: ArrayList<ConstraintWidget?> = ArrayList()
        val widgetsB: ArrayList<ConstraintWidget?> = ArrayList()
        for (i in 0..999) {
            val A = ConstraintWidget(0, 0, 200, 20)
            val B = ConstraintWidget(0, 0, 200, 20)
            A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
            A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
            A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
            B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
            B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
            B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
            widgetsA.add(A)
            widgetsB.add(B)
            root.add(A)
            root.add(B)
        }
        root.layout()
        for (widget in widgetsA) {
            Assert.assertEquals(widget!!.left.toLong(), 200)
            Assert.assertEquals(widget.top.toLong(), 0)
        }
        for (widget in widgetsB) {
            Assert.assertEquals(widget!!.left.toLong(), 600)
            Assert.assertEquals(widget.top.toLong(), 980)
        }
    }

    @Test
    fun testWidgetTopRightPositioning() {
        // Easy to tweak numbers to test larger systems
        val numLoops = 10
        val numWidgets = 100
        for (j in 0 until numLoops) {
            s.reset()
            val widgets: ArrayList<ConstraintWidget?> = ArrayList()
            val w = 100 + j
            val h = 20 + j
            val first = ConstraintWidget(w, h)
            widgets.add(first)
            var previous: ConstraintWidget? = first
            val margin = 20
            for (i in 0 until numWidgets) {
                val widget = ConstraintWidget(w, h)
                widget.connect(ConstraintAnchor.Type.LEFT, previous!!, ConstraintAnchor.Type.RIGHT, margin)
                widget.connect(ConstraintAnchor.Type.TOP, previous, ConstraintAnchor.Type.BOTTOM, margin)
                widgets.add(widget)
                previous = widget
            }
            for (widget in widgets) {
                widget!!.addToSolver(s, optimize)
            }
            try {
                s.minimize()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            for (i in widgets.indices) {
                val widget = widgets[i]
                widget!!.updateFromSolver(s, optimize)
                val left = widget.left
                val top = widget.top
                val right = widget.right
                val bottom = widget.bottom
                Assert.assertEquals(left.toLong(), (i * (w + margin)).toLong())
                Assert.assertEquals(right.toLong(), (i * (w + margin) + w).toLong())
                Assert.assertEquals(top.toLong(), (i * (h + margin)).toLong())
                Assert.assertEquals(bottom.toLong(), (i * (h + margin) + h).toLong())
            }
        }
    }

    @Test
    fun testWrapSimpleWrapContent() {
        val root = ConstraintWidgetContainer(0, 0, 1000, 1000)
        val A = ConstraintWidget(0, 0, 200, 20)
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(root)
        widgets.add(A)
        root.setDebugSolverName(s, "root")
        A.setDebugSolverName(s, "A")
        root.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        runTestOnWidgets(widgets) {
            println("Simple Wrap: $root, $A")
            Assert.assertEquals(root.width.toLong(), A.width.toLong())
            Assert.assertEquals(root.height.toLong(), A.height.toLong())
            Assert.assertEquals(A.width.toLong(), 200)
            Assert.assertEquals(A.height.toLong(), 20)
        }
    }

    @Test
    fun testMatchConstraint() {
        val root = ConstraintWidgetContainer(50, 50, 500, 500)
        val A = ConstraintWidget(10, 20, 100, 30)
        val B = ConstraintWidget(150, 200, 100, 30)
        val C = ConstraintWidget(50, 50)
        val widgets = ArrayList<ConstraintWidget>()
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        root.debugName = "root"
        root.add(A)
        root.add(B)
        root.add(C)
        widgets.add(root)
        widgets.add(A)
        widgets.add(B)
        widgets.add(C)
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        runTestOnWidgets(widgets) {
            Assert.assertEquals(C.x.toLong(), A.right.toLong())
            Assert.assertEquals(C.right.toLong(), B.x.toLong())
            Assert.assertEquals(C.y.toLong(), A.bottom.toLong())
            Assert.assertEquals(C.bottom.toLong(), B.y.toLong())
        }
    }

    // Obsolete @Test
    fun testWidgetStrengthPositioning() {
        val root = ConstraintWidget(400, 400)
        val A = ConstraintWidget(20, 20)
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(root)
        widgets.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        println("Widget A centered inside Root")
        runTestOnWidgets(widgets) {
            Assert.assertEquals(A.left.toLong(), 190)
            Assert.assertEquals(A.right.toLong(), 210)
            Assert.assertEquals(A.top.toLong(), 190)
            Assert.assertEquals(A.bottom.toLong(), 210)
        }
        println("Widget A weak left, should move to the right")
        A.getAnchor(ConstraintAnchor.Type.LEFT) //.setStrength(ConstraintAnchor.Strength.WEAK);
        runTestOnWidgets(widgets) {
            Assert.assertEquals(A.left.toLong(), 380)
            Assert.assertEquals(A.right.toLong(), 400)
        }
        println("Widget A weak right, should go back to center")
        A.getAnchor(ConstraintAnchor.Type.RIGHT) //.setStrength(ConstraintAnchor.Strength.WEAK);
        runTestOnWidgets(widgets) {
            Assert.assertEquals(A.left.toLong(), 190)
            Assert.assertEquals(A.right.toLong(), 210)
        }
        println("Widget A strong left, should move to the left")
        A.getAnchor(ConstraintAnchor.Type.LEFT) //.setStrength(ConstraintAnchor.Strength.STRONG);
        runTestOnWidgets(widgets) {
            Assert.assertEquals(A.left.toLong(), 0)
            Assert.assertEquals(A.right.toLong(), 20)
            Assert.assertEquals(root.width.toLong(), 400)
        }
    }

    @Test
    fun testWidgetPositionMove() {
        val A = ConstraintWidget(0, 0, 100, 20)
        val B = ConstraintWidget(0, 30, 200, 20)
        val C = ConstraintWidget(0, 60, 100, 20)
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(A)
        widgets.add(B)
        widgets.add(C)
        A.setDebugSolverName(s, "A")
        B.setDebugSolverName(s, "B")
        C.setDebugSolverName(s, "C")
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        C.setOrigin(200, 0)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.RIGHT)
        val check = Runnable {
            Assert.assertEquals(A.width.toLong(), 100)
            Assert.assertEquals(B.width.toLong(), 200)
            Assert.assertEquals(C.width.toLong(), 100)
        }
        runTestOnWidgets(widgets, check)
        println("A: $A B: $B C: $C")
        C.setOrigin(100, 0)
        //        runTestOnUIWidgets(widgets);
        runTestOnWidgets(widgets, check)
        println("A: $A B: $B C: $C")
        C.setOrigin(50, 0)
        runTestOnWidgets(widgets, check)
        println("A: $A B: $B C: $C")
    }

    @Test
    fun testWrapProblem() {
        val root = ConstraintWidgetContainer(400, 400)
        val A = ConstraintWidget(80, 300)
        val B = ConstraintWidget(250, 80)
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(root)
        widgets.add(B)
        widgets.add(A)
        A.parent = root
        B.parent = root
        root.setDebugSolverName(s, "root")
        A.setDebugSolverName(s, "A")
        B.setDebugSolverName(s, "B")
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        //        B.getAnchor(ConstraintAnchor.Type.TOP).setStrength(ConstraintAnchor.Strength.WEAK);
        runTestOnWidgets(widgets) {
            Assert.assertEquals(A.width.toLong(), 80)
            Assert.assertEquals(A.height.toLong(), 300)
            Assert.assertEquals(B.width.toLong(), 250)
            Assert.assertEquals(B.height.toLong(), 80)
            Assert.assertEquals(A.y.toLong(), 0)
            Assert.assertEquals(B.y.toLong(), 110)
        }
    }

    @Test
    fun testGuideline() {
        val root = ConstraintWidgetContainer(400, 400)
        val A = ConstraintWidget(100, 20)
        val guideline = Guideline()
        root.add(guideline)
        root.add(A)
        guideline.setGuidePercent(0.50f)
        guideline.orientation = Guideline.VERTICAL
        root.debugName = "root"
        A.debugName = "A"
        guideline.debugName = "guideline"
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(root)
        widgets.add(A)
        widgets.add(guideline)
        A.connect(ConstraintAnchor.Type.LEFT, guideline, ConstraintAnchor.Type.LEFT)
        val check = Runnable {
            println("$root $A $guideline")
            Assert.assertEquals(A.width.toLong(), 100)
            Assert.assertEquals(A.height.toLong(), 20)
            Assert.assertEquals(A.x.toLong(), 200)
        }
        runTest(root, check)
        guideline.setGuidePercent(0)
        runTest(root) {
            println("$root $A $guideline")
            Assert.assertEquals(A.width.toLong(), 100)
            Assert.assertEquals(A.height.toLong(), 20)
            Assert.assertEquals(A.x.toLong(), 0)
        }
        guideline.setGuideBegin(150)
        runTest(root) {
            Assert.assertEquals(A.width.toLong(), 100)
            Assert.assertEquals(A.height.toLong(), 20)
            Assert.assertEquals(A.x.toLong(), 150)
        }
        println("$root $A $guideline")
        guideline.setGuideEnd(150)
        runTest(root) {
            Assert.assertEquals(A.width.toLong(), 100)
            Assert.assertEquals(A.height.toLong(), 20)
            Assert.assertEquals(A.x.toLong(), 250)
        }
        println("$root $A $guideline")
        guideline.orientation = Guideline.HORIZONTAL
        A.resetAnchors()
        A.connect(ConstraintAnchor.Type.TOP, guideline, ConstraintAnchor.Type.TOP)
        guideline.setGuideBegin(150)
        runTest(root) {
            println("$root $A $guideline")
            Assert.assertEquals(A.width.toLong(), 100)
            Assert.assertEquals(A.height.toLong(), 20)
            Assert.assertEquals(A.y.toLong(), 150)
        }
        println("$root $A $guideline")
        A.resetAnchors()
        A.connect(ConstraintAnchor.Type.TOP, guideline, ConstraintAnchor.Type.BOTTOM)
        runTest(root) {
            Assert.assertEquals(A.width.toLong(), 100)
            Assert.assertEquals(A.height.toLong(), 20)
            Assert.assertEquals(A.y.toLong(), 150)
        }
        println("$root $A $guideline")
    }

    private fun runTest(root: ConstraintWidgetContainer, check: Runnable) {
        root.layout()
        check.run()
    }

    @Test
    fun testGuidelinePosition() {
        val root = ConstraintWidgetContainer(800, 400)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val guideline = Guideline()
        root.add(guideline)
        root.add(A)
        root.add(B)
        guideline.setGuidePercent(0.651f)
        guideline.orientation = Guideline.VERTICAL
        root.setDebugSolverName(s, "root")
        A.setDebugSolverName(s, "A")
        B.setDebugSolverName(s, "B")
        guideline.setDebugSolverName(s, "guideline")
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(root)
        widgets.add(A)
        widgets.add(B)
        widgets.add(guideline)
        A.connect(ConstraintAnchor.Type.LEFT, guideline, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, guideline, ConstraintAnchor.Type.RIGHT)
        val check = Runnable {
            println("$root A: $A  B: $B guideline: $guideline")
            Assert.assertEquals(A.width.toLong(), 100)
            Assert.assertEquals(A.height.toLong(), 20)
            Assert.assertEquals(A.x.toLong(), 521)
            Assert.assertEquals(B.right.toLong(), 521)
        }
        runTestOnWidgets(widgets, check)
    }

    @Test
    fun testWidgetInfeasiblePosition() {
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(B)
        widgets.add(A)
        A.resetSolverVariables(s.cache)
        B.resetSolverVariables(s.cache)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.LEFT)
        runTestOnWidgets(widgets) {
            // TODO: this fail -- need to figure the best way to fix this.
//                assertEquals(A.getWidth(), 100);
//                assertEquals(B.getWidth(), 100);
        }
    }

    @Test
    fun testWidgetMultipleDependentPositioning() {
        val root = ConstraintWidget(400, 400)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.setDebugSolverName(s, "root")
        A.setDebugSolverName(s, "A")
        B.setDebugSolverName(s, "B")
        val widgets = ArrayList<ConstraintWidget>()
        widgets.add(root)
        widgets.add(B)
        widgets.add(A)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 10)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 10)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.resetSolverVariables(s.cache)
        A.resetSolverVariables(s.cache)
        B.resetSolverVariables(s.cache)
        runTestOnWidgets(widgets) {
            println("root: $root A: $A B: $B")
            Assert.assertEquals(root.height.toLong(), 400)
            Assert.assertEquals(root.height.toLong(), 400)
            Assert.assertEquals(A.height.toLong(), 20)
            Assert.assertEquals(B.height.toLong(), 20)
            Assert.assertEquals((A.top - root.top).toLong(), (root.bottom - A.bottom).toLong())
            Assert.assertEquals((B.top - A.bottom).toLong(), (root.bottom - B.bottom).toLong())
        }
    }

    @Test
    fun testMinSize() {
        val root = ConstraintWidgetContainer(600, 400)
        val A = ConstraintWidget(100, 20)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        root.optimizationLevel = 0
        root.layout()
        println("a) root: $root A: $A")
        Assert.assertEquals(root.width.toLong(), 600)
        Assert.assertEquals(root.height.toLong(), 400)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals((A.left - root.left).toLong(), (root.right - A.right).toLong())
        Assert.assertEquals((A.top - root.top).toLong(), (root.bottom - A.bottom).toLong())
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("b) root: $root A: $A")
        Assert.assertEquals(root.height.toLong(), A.height.toLong())
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals((A.left - root.left).toLong(), (root.right - A.right).toLong())
        Assert.assertEquals((A.top - root.top).toLong(), (root.bottom - A.bottom).toLong())
        root.minHeight = 200
        root.layout()
        println("c) root: $root A: $A")
        Assert.assertEquals(root.height.toLong(), 200)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals((A.left - root.left).toLong(), (root.right - A.right).toLong())
        Assert.assertEquals((A.top - root.top).toLong(), (root.bottom - A.bottom).toLong())
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("d) root: $root A: $A")
        Assert.assertEquals(root.width.toLong(), A.width.toLong())
        Assert.assertEquals(root.height.toLong(), 200)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals((A.left - root.left).toLong(), (root.right - A.right).toLong())
        Assert.assertEquals((A.top - root.top).toLong(), (root.bottom - A.bottom).toLong())
        root.minWidth = 300
        root.layout()
        println("e) root: $root A: $A")
        Assert.assertEquals(root.width.toLong(), 300)
        Assert.assertEquals(root.height.toLong(), 200)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals((A.left - root.left).toLong(), (root.right - A.right).toLong())
        Assert.assertEquals((A.top - root.top).toLong(), (root.bottom - A.bottom).toLong())
    }

    /*
     * Insert the widgets in all permutations
     * (to test that the insert order
     * doesn't impact the resolution)
     */
    private fun runTestOnWidgets(widgets: ArrayList<ConstraintWidget>, check: Runnable) {
        val tail = ArrayList<Int>()
        for (i in widgets.indices) {
            tail.add(i)
        }
        addToSolverWithPermutation(widgets, ArrayList(), tail, check)
    }

    private fun runTestOnUIWidgets(widgets: ArrayList<ConstraintWidget>) {
        for (i in widgets.indices) {
            val widget = widgets[i]
            if (widget.debugName != null) {
                widget.setDebugSolverName(s, widget.debugName!!)
            }
            widget.resetSolverVariables(s.cache)
            widget.addToSolver(s, optimize)
        }
        try {
            s.minimize()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        for (j in widgets.indices) {
            val w = widgets[j]
            w.updateFromSolver(s, optimize)
            println(" $w")
        }
    }

    private fun addToSolverWithPermutation(
        widgets: ArrayList<ConstraintWidget>,
        list: ArrayList<Int>, tail: ArrayList<Int>, check: Runnable
    ) {
        if (tail.size > 0) {
            val n = tail.size
            for (i in 0 until n) {
                list.add(tail[i])
                val permuted = ArrayList(tail)
                permuted.removeAt(i)
                addToSolverWithPermutation(widgets, list, permuted, check)
                list.removeAt(list.size - 1)
            }
        } else {
//            System.out.print("Adding widgets in order: ");
            s.reset()
            for (i in list.indices) {
                val index = list[i]
                //                System.out.print(" " + index);
                val widget = widgets[index]
                widget.resetSolverVariables(s.cache)
            }
            for (i in list.indices) {
                val index = list[i]
                //                System.out.print(" " + index);
                val widget = widgets[index]
                if (widget.debugName != null) {
                    widget.setDebugSolverName(s, widget.debugName!!)
                }
                widget.addToSolver(s, optimize)
            }
            //            System.out.println("");
//            s.displayReadableRows();
            try {
                s.minimize()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            for (j in widgets.indices) {
                val w = widgets[j]
                w.updateFromSolver(s, optimize)
            }
            //            try {
            check.run()
            //            } catch (AssertionError e) {
//                System.out.println("Assertion error: " + e);
//                runTestOnUIWidgets(widgets);
//            }
        }
    }
}