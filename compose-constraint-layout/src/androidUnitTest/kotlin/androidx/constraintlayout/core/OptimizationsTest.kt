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

import androidx.constraintlayout.core.widgets.Barrier
import androidx.constraintlayout.core.widgets.ConstraintAnchor
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
import androidx.constraintlayout.core.widgets.Guideline
import androidx.constraintlayout.core.widgets.Optimizer
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure
import kotlinx.datetime.Clock
import org.junit.Assert
import kotlin.test.Test

class OptimizationsTest {
    @Test
    fun testGoneMatchConstraint() {
        val root = ConstraintWidgetContainer(0, 0, 600, 800)
        val A = ConstraintWidget("A", 0, 10)
        val B = ConstraintWidget("B", 10, 10)
        root.debugName = "root"
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 8)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 8)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 8)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 8)
        A.verticalBiasPercent = 0.2f
        A.horizontalBiasPercent = 0.2f
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        val metrics = Metrics()
        root.fillMetrics(metrics)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        root.layout()
        println("1) A: $A")
        Assert.assertEquals(A.left.toLong(), 8)
        Assert.assertEquals(A.top.toLong(), 163)
        Assert.assertEquals(A.right.toLong(), 592)
        Assert.assertEquals(A.bottom.toLong(), 173)
        A.visibility = ConstraintWidget.GONE
        root.layout()
        println("2) A: $A")
        Assert.assertEquals(A.left.toLong(), 120)
        Assert.assertEquals(A.top.toLong(), 160)
        Assert.assertEquals(A.right.toLong(), 120)
        Assert.assertEquals(A.bottom.toLong(), 160)
    }

    @Test
    fun test3EltsChain() {
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
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 40)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 30)
        val metrics = Metrics()
        root.fillMetrics(metrics)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        //        root.setOptimizationLevel(Optimizer.OPTIMIZATION_NONE);
        A.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        root.layout()
        println("1) root: $root A: $A B: $B C: $C")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), 40)
        Assert.assertEquals(B.left.toLong(), 255)
        Assert.assertEquals(C.left.toLong(), 470)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        C.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.layout()
        println("2) root: $root A: $A B: $B C: $C")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), 40)
        Assert.assertEquals(B.left.toFloat(), 217f, 1f)
        Assert.assertEquals(C.left.toLong(), 393)
        Assert.assertEquals(A.width.toFloat(), 177f, 1f)
        Assert.assertEquals(B.width.toFloat(), 176f, 1f)
        Assert.assertEquals(C.width.toFloat(), 177f, 1f)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, 7)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 3)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT, 7)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 3)
        root.layout()
        println("3) root: $root A: $A B: $B C: $C")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), 40)
        Assert.assertEquals(B.left.toLong(), 220)
        Assert.assertEquals(C.left.toFloat(), 400f, 1f)
        Assert.assertEquals(A.width.toFloat(), 170f, 1f)
        Assert.assertEquals(B.width.toFloat(), 170f, 1f)
        Assert.assertEquals(C.width.toFloat(), 170f, 1f)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        A.visibility = ConstraintWidget.GONE
        root.layout()
        println("4) root: $root A: $A B: $B C: $C")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 3)
        Assert.assertEquals(C.left.toFloat(), 292f, 1f)
        Assert.assertEquals(A.width.toLong(), 0)
        Assert.assertEquals(B.width.toFloat(), 279f, 1f)
        Assert.assertEquals(C.width.toFloat(), 278f, 1f)
    }

    @Test
    fun testBasicChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        val metrics = Metrics()
        root.fillMetrics(metrics)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        root.layout()
        println("1) root: $root A: $A B: $B")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), 133)
        Assert.assertEquals(B.left.toFloat(), 367f, 1f)
        val C = ConstraintWidget(100, 20)
        C.debugName = "C"
        root.add(C)
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        root.layout()
        println("2) root: $root A: $A B: $B")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), 133)
        Assert.assertEquals(B.left.toFloat(), 367f, 1f)
        Assert.assertEquals(C.left.toLong(), B.right.toLong())
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 40)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, 100)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        root.layout()
        println("3) root: $root A: $A B: $B")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), 170)
        Assert.assertEquals(B.left.toLong(), 370)
        A.horizontalBiasPercent = 0f
        root.layout()
        println("4) root: $root A: $A B: $B")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), 40)
        Assert.assertEquals(B.left.toLong(), 240)
        A.horizontalBiasPercent = 0.5f
        A.visibility = ConstraintWidget.GONE
        //        root.setOptimizationLevel(Optimizer.OPTIMIZATION_NONE);
        root.layout()
        println("5) root: $root A: $A B: $B")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), 250)
        Assert.assertEquals(B.left.toLong(), 250)
    }

    @Test
    fun testBasicChain2() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        val C = ConstraintWidget(100, 20)
        C.debugName = "C"
        root.add(C)
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 40)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT, 100)
        A.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
        A.horizontalBiasPercent = 0.5f
        A.visibility = ConstraintWidget.GONE
        //        root.setOptimizationLevel(Optimizer.OPTIMIZATION_NONE);
        root.layout()
        println("5) root: $root A: $A B: $B")
        Assert.assertEquals(A.left.toLong(), 250)
        Assert.assertEquals(B.left.toLong(), 250)
    }

    @Test
    fun testBasicRatio() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setDimensionRatio("1:1")
        val metrics = Metrics()
        root.fillMetrics(metrics)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        root.layout()
        println("1) root: $root A: $A B: $B")
        println(metrics)
        Assert.assertEquals(A.height.toLong(), A.width.toLong())
        Assert.assertEquals(B.top.toLong(), ((A.height - B.height) / 2).toLong())
    }

    @Test
    fun testBasicBaseline() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        A.baselineDistance = 8
        B.baselineDistance = 8
        root.add(A)
        root.add(B)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.BASELINE, A, ConstraintAnchor.Type.BASELINE)
        val metrics = Metrics()
        root.fillMetrics(metrics)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        root.layout()
        println("1) root: $root A: $A B: $B")
        println(metrics)
        Assert.assertEquals(A.top.toLong(), 290)
        Assert.assertEquals(B.top.toLong(), A.top.toLong())
    }

    @Test
    fun testBasicMatchConstraints() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        val metrics = Metrics()
        root.fillMetrics(metrics)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        root.layout()
        println("1) root: $root A: $A")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(A.right.toLong(), root.width.toLong())
        Assert.assertEquals(A.bottom.toLong(), root.height.toLong())
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 10)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 20)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 30)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 40)
        root.layout()
        println("2) root: $root A: $A")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), 30)
        Assert.assertEquals(A.top.toLong(), 10)
        Assert.assertEquals(A.right.toLong(), (root.width - 40).toLong())
        Assert.assertEquals(A.bottom.toLong(), (root.height - 20).toLong())
    }

    @Test
    fun testBasicCenteringPositioning() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        root.add(A)
        var time = Clock.System.now().epochSeconds
        val metrics = Metrics()
        root.fillMetrics(metrics)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        root.layout()
        time = Clock.System.now().epochSeconds - time
        println("A) execution time: $time")
        println("1) root: $root A: $A")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), ((root.width - A.width) / 2).toLong())
        Assert.assertEquals(A.top.toLong(), ((root.height - A.height) / 2).toLong())
        A.horizontalBiasPercent = 0.3f
        A.verticalBiasPercent = 0.3f
        root.layout()
        println("2) root: $root A: $A")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), ((root.width - A.width) * 0.3f).toLong() )
        Assert.assertEquals(A.top.toLong(), ((root.height - A.height) * 0.3f).toLong())
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 30)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 50)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 20)
        root.layout()
        println("3) root: $root A: $A")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), (((root.width - A.width - 40) * 0.3f).toInt() + 10).toLong())
        Assert.assertEquals(A.top.toLong(), (((root.height - A.height - 70) * 0.3f).toInt() + 50).toLong())
    }

    @Test
    fun testBasicVerticalPositioning() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        val margin = 13
        val marginR = 27
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 31)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 27)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 27)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 104)
        root.add(A)
        root.add(B)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        val time = Clock.System.now().epochSeconds
        //        root.layout();
//        time = Clock.System.now().epochSeconds - time;
//        System.out.println("A) execution time: " + time);
//        System.out.println("a - root: " + root + " A: " + A + " B: " + B);
//
//        assertEquals(A.getLeft(), 27);
//        assertEquals(A.getTop(), 31);
//        assertEquals(B.getLeft(), 27);
//        assertEquals(B.getTop(), 155);
        A.visibility = ConstraintWidget.GONE
        val metrics = Metrics()
        root.fillMetrics(metrics)
        root.layout()
        println("b - root: $root A: $A B: $B")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 27)
        Assert.assertEquals(B.top.toLong(), 104)
        // root: id: root (0, 0) - (600 x 600) wrap: (0 x 0) A: id: A (27, 31) - (100 x 20) wrap: (0 x 0) B: id: B (27, 155) - (100 x 20) wrap: (0 x 0)
    }

    @Test
    fun testBasicVerticalGuidelinePositioning() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val guidelineA = Guideline()
        guidelineA.orientation = Guideline.HORIZONTAL
        guidelineA.setGuideEnd(67)
        root.debugName = "root"
        A.debugName = "A"
        guidelineA.debugName = "guideline"
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 31)
        A.connect(ConstraintAnchor.Type.BOTTOM, guidelineA, ConstraintAnchor.Type.TOP, 12)
        root.add(A)
        root.add(guidelineA)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        var time = Clock.System.now().epochSeconds
        root.layout()
        time = Clock.System.now().epochSeconds - time
        println("A) execution time: $time")
        println("root: $root A: $A guide: $guidelineA")
        Assert.assertEquals(A.top.toLong(), 266)
        Assert.assertEquals(guidelineA.top.toLong(), 533)
    }

    @Test
    fun testSimpleCenterPositioning() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        val margin = 13
        val marginR = 27
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, margin)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, -margin)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, margin)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, -marginR)
        root.add(A)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        var time = Clock.System.now().epochSeconds
        root.layout()
        time = Clock.System.now().epochSeconds - time
        println("A) execution time: $time")
        println("root: $root A: $A")
        Assert.assertEquals(A.left.toFloat(), 270f, 1f)
        Assert.assertEquals(A.top.toFloat(), 303f, 1f)
    }

    @Test
    fun testSimpleGuideline() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val guidelineA = Guideline()
        val A = ConstraintWidget(100, 20)
        guidelineA.orientation = Guideline.VERTICAL
        guidelineA.setGuideBegin(100)
        root.debugName = "root"
        A.debugName = "A"
        guidelineA.debugName = "guidelineA"
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 32)
        A.connect(ConstraintAnchor.Type.LEFT, guidelineA, ConstraintAnchor.Type.LEFT, 2)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 7)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.add(guidelineA)
        root.add(A)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        val metrics = Metrics()
        root.fillMetrics(metrics)
        var time = Clock.System.now().epochSeconds
        root.layout()
        Assert.assertEquals(A.left.toLong(), 102)
        Assert.assertEquals(A.top.toLong(), 32)
        Assert.assertEquals(A.width.toLong(), 491)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(guidelineA.left.toLong(), 100)
        time = Clock.System.now().epochSeconds - time
        println("A) execution time: $time")
        println("root: $root A: $A guideline: $guidelineA")
        println(metrics)
        root.width = 700
        time = Clock.System.now().epochSeconds
        root.layout()
        time = Clock.System.now().epochSeconds - time
        println("B) execution time: $time")
        println("root: $root A: $A guideline: $guidelineA")
        println(metrics)
        Assert.assertEquals(A.left.toLong(), 102)
        Assert.assertEquals(A.top.toLong(), 32)
        Assert.assertEquals(A.width.toLong(), 591)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(guidelineA.left.toLong(), 100)
    }

    @Test
    fun testSimple() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 20)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 10)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 20)
        C.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 30)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM, 20)
        root.add(A)
        root.add(B)
        root.add(C)
        root.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        var time = Clock.System.now().epochSeconds
        root.layout()
        time = Clock.System.now().epochSeconds - time
        println("execution time: $time")
        println("root: $root A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 10)
        Assert.assertEquals(A.top.toLong(), 20)
        Assert.assertEquals(B.left.toLong(), 120)
        Assert.assertEquals(B.top.toLong(), 60)
        Assert.assertEquals(C.left.toLong(), 140)
        Assert.assertEquals(C.top.toLong(), 100)
    }

    @Test
    fun testGuideline() {
        testVerticalGuideline(Optimizer.OPTIMIZATION_NONE)
        testVerticalGuideline(Optimizer.OPTIMIZATION_STANDARD)
        testHorizontalGuideline(Optimizer.OPTIMIZATION_NONE)
        testHorizontalGuideline(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testVerticalGuideline(directResolution: Int) {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = directResolution
        val A = ConstraintWidget(100, 20)
        val guideline = Guideline()
        guideline.orientation = Guideline.VERTICAL
        root.debugName = "root"
        A.debugName = "A"
        guideline.debugName = "guideline"
        root.add(A)
        root.add(guideline)
        A.connect(ConstraintAnchor.Type.LEFT, guideline, ConstraintAnchor.Type.LEFT, 16)
        guideline.setGuideBegin(100)
        root.layout()
        println("res: $directResolution root: $root A: $A guideline: $guideline")
        Assert.assertEquals(guideline.left.toLong(), 100)
        Assert.assertEquals(A.left.toLong(), 116)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(A.top.toLong(), 0)
        guideline.setGuidePercent(0.5f)
        root.layout()
        println("res: $directResolution root: $root A: $A guideline: $guideline")
        Assert.assertEquals(guideline.left.toLong(), (root.width / 2).toLong())
        Assert.assertEquals(A.left.toLong(), 316)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(A.top.toLong(), 0)
        guideline.setGuideEnd(100)
        root.layout()
        println("res: $directResolution root: $root A: $A guideline: $guideline")
        Assert.assertEquals(guideline.left.toLong(), 500)
        Assert.assertEquals(A.left.toLong(), 516)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(A.top.toLong(), 0)
    }

    fun testHorizontalGuideline(directResolution: Int) {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = directResolution
        val A = ConstraintWidget(100, 20)
        val guideline = Guideline()
        guideline.orientation = Guideline.HORIZONTAL
        root.debugName = "root"
        A.debugName = "A"
        guideline.debugName = "guideline"
        root.add(A)
        root.add(guideline)
        A.connect(ConstraintAnchor.Type.TOP, guideline, ConstraintAnchor.Type.TOP, 16)
        guideline.setGuideBegin(100)
        root.layout()
        println("res: $directResolution root: $root A: $A guideline: $guideline")
        Assert.assertEquals(guideline.top.toLong(), 100)
        Assert.assertEquals(A.top.toLong(), 116)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(A.left.toLong(), 0)
        guideline.setGuidePercent(0.5f)
        root.layout()
        println("res: $directResolution root: $root A: $A guideline: $guideline")
        Assert.assertEquals(guideline.top.toLong(), (root.height / 2).toLong())
        Assert.assertEquals(A.top.toLong(), 316)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(A.left.toLong(), 0)
        guideline.setGuideEnd(100)
        root.layout()
        println("res: $directResolution root: $root A: $A guideline: $guideline")
        Assert.assertEquals(guideline.top.toLong(), 500)
        Assert.assertEquals(A.top.toLong(), 516)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 20)
        Assert.assertEquals(A.left.toLong(), 0)
    }

    @Test
    fun testBasicCentering() {
        testBasicCentering(Optimizer.OPTIMIZATION_NONE)
        testBasicCentering(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testBasicCentering(directResolution: Int) {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = directResolution
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 10)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 10)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 10)
        root.layout()
        println("res: $directResolution root: $root A: $A")
        Assert.assertEquals(A.left.toLong(), 250)
        Assert.assertEquals(A.top.toLong(), 290)
    }

    @Test
    fun testPercent() {
        testPercent(Optimizer.OPTIMIZATION_NONE)
        testPercent(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testPercent(directResolution: Int) {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = directResolution
        val A = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        root.add(A)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 10)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.setHorizontalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_PERCENT, 0, 0, 0.5f)
        A.setVerticalMatchStyle(ConstraintWidget.MATCH_CONSTRAINT_PERCENT, 0, 0, 0.5f)
        root.layout()
        println("res: $directResolution root: $root A: $A")
        Assert.assertEquals(A.left.toLong(), 10)
        Assert.assertEquals(A.top.toLong(), 10)
        Assert.assertEquals(A.width.toLong(), 300)
        Assert.assertEquals(A.height.toLong(), 300)
    }

    @Test
    fun testDependency() {
        testDependency(Optimizer.OPTIMIZATION_NONE)
        testDependency(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testDependency(directResolution: Int) {
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
        A.baselineDistance = 8
        B.baselineDistance = 8
        C.baselineDistance = 8
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        A.connect(ConstraintAnchor.Type.BASELINE, B, ConstraintAnchor.Type.BASELINE)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 16)
        B.connect(ConstraintAnchor.Type.BASELINE, C, ConstraintAnchor.Type.BASELINE)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 48)
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 32)
        root.layout()
        println(
            "res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C
        )
        Assert.assertEquals(A.left.toLong(), 10)
        Assert.assertEquals(A.top.toLong(), 32)
        Assert.assertEquals(B.left.toLong(), 126)
        Assert.assertEquals(B.top.toLong(), 32)
        Assert.assertEquals(C.left.toLong(), 274)
        Assert.assertEquals(C.top.toLong(), 32)
    }

    @Test
    fun testDependency2() {
        testDependency2(Optimizer.OPTIMIZATION_NONE)
        testDependency2(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testDependency2(directResolution: Int) {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = directResolution
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        root.add(A)
        root.add(B)
        root.add(C)
        A.baselineDistance = 8
        B.baselineDistance = 8
        C.baselineDistance = 8
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        A.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 12)
        root.layout()
        println(
            "res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C
        )
        Assert.assertEquals(A.left.toLong(), 12)
        Assert.assertEquals(A.top.toLong(), 580)
        Assert.assertEquals(B.left.toLong(), 12)
        Assert.assertEquals(B.top.toLong(), 560)
        Assert.assertEquals(C.left.toLong(), 12)
        Assert.assertEquals(C.top.toLong(), 540)
    }

    @Test
    fun testDependency3() {
        testDependency3(Optimizer.OPTIMIZATION_NONE)
        testDependency3(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testDependency3(directResolution: Int) {
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
        A.baselineDistance = 8
        B.baselineDistance = 8
        C.baselineDistance = 8
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 20)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 30)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 60)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 10)
        C.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 20)
        root.layout()
        println(
            "res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C
        )
        Assert.assertEquals(A.left.toLong(), 10)
        Assert.assertEquals(A.top.toLong(), 20)
        Assert.assertEquals(B.left.toLong(), 260)
        Assert.assertEquals(B.top.toLong(), 520)
        Assert.assertEquals(C.left.toLong(), 380)
        Assert.assertEquals(C.top.toLong(), 500)
    }

    @Test
    fun testDependency4() {
        testDependency4(Optimizer.OPTIMIZATION_NONE)
        testDependency4(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testDependency4(directResolution: Int) {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = directResolution
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        root.add(A)
        root.add(B)
        A.baselineDistance = 8
        B.baselineDistance = 8
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 20)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 10)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 20)
        B.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.RIGHT, 30)
        B.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM, 60)
        root.layout()
        println(
            "res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B
        )
        Assert.assertEquals(A.left.toLong(), 250)
        Assert.assertEquals(A.top.toLong(), 290)
        Assert.assertEquals(B.left.toLong(), 220)
        Assert.assertEquals(B.top.toLong(), 230)
    }

    @Test
    fun testDependency5() {
        testDependency5(Optimizer.OPTIMIZATION_NONE)
        testDependency5(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testDependency5(directResolution: Int) {
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
        A.baselineDistance = 8
        B.baselineDistance = 8
        C.baselineDistance = 8
        D.baselineDistance = 8
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 10)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 10)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 20)
        A.connect(ConstraintAnchor.Type.BOTTOM, B, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 10)
        C.connect(ConstraintAnchor.Type.TOP, B, ConstraintAnchor.Type.BOTTOM)
        C.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.RIGHT, 20)
        D.connect(ConstraintAnchor.Type.TOP, C, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.RIGHT, 20)
        root.layout()
        println(
            "res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C + " D: " + D
        )
        Assert.assertEquals(A.left.toLong(), 250)
        Assert.assertEquals(A.top.toLong(), 197)
        Assert.assertEquals(B.left.toLong(), 250)
        Assert.assertEquals(B.top.toLong(), 393)
        Assert.assertEquals(C.left.toLong(), 230)
        Assert.assertEquals(C.top.toLong(), 413)
        Assert.assertEquals(D.left.toLong(), 210)
        Assert.assertEquals(D.top.toLong(), 433)
    }

    @Test
    fun testUnconstrainedDependency() {
        testUnconstrainedDependency(Optimizer.OPTIMIZATION_NONE)
        testUnconstrainedDependency(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testUnconstrainedDependency(directResolution: Int) {
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
        A.baselineDistance = 8
        B.baselineDistance = 8
        C.baselineDistance = 8
        A.setFrame(142, 96, 242, 130)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 10)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP, 100)
        C.connect(ConstraintAnchor.Type.RIGHT, A, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.BASELINE, A, ConstraintAnchor.Type.BASELINE)
        root.layout()
        println(
            "res: " + directResolution + " root: " + root
                    + " A: " + A + " B: " + B + " C: " + C
        )
        Assert.assertEquals(A.left.toLong(), 142)
        Assert.assertEquals(A.top.toLong(), 96)
        Assert.assertEquals(A.width.toLong(), 100)
        Assert.assertEquals(A.height.toLong(), 34)
        Assert.assertEquals(B.left.toLong(), 252)
        Assert.assertEquals(B.top.toLong(), 196)
        Assert.assertEquals(C.left.toLong(), 42)
        Assert.assertEquals(C.top.toLong(), 96)
    }

    @Test
    fun testFullLayout() {
        testFullLayout(Optimizer.OPTIMIZATION_NONE)
        testFullLayout(Optimizer.OPTIMIZATION_STANDARD)
    }

    fun testFullLayout(directResolution: Int) {
        // Horizontal :
        // r <- A
        // r <- B <- C <- D
        //      B <- E
        // r <- F
        // r <- G
        // Vertical:
        // r <- A <- B <- C <- D <- E
        // r <- F <- G
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = directResolution
        val A = ConstraintWidget(100, 20)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(100, 20)
        val E = ConstraintWidget(100, 20)
        val F = ConstraintWidget(100, 20)
        val G = ConstraintWidget(100, 20)
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        E.debugName = "E"
        F.debugName = "F"
        G.debugName = "G"
        root.add(G)
        root.add(A)
        root.add(B)
        root.add(E)
        root.add(C)
        root.add(D)
        root.add(F)
        A.baselineDistance = 8
        B.baselineDistance = 8
        C.baselineDistance = 8
        D.baselineDistance = 8
        E.baselineDistance = 8
        F.baselineDistance = 8
        G.baselineDistance = 8
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 20)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM, 40)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 16)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT, 16)
        C.connect(ConstraintAnchor.Type.BASELINE, B, ConstraintAnchor.Type.BASELINE)
        D.connect(ConstraintAnchor.Type.TOP, C, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.LEFT, C, ConstraintAnchor.Type.LEFT)
        E.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.RIGHT)
        E.connect(ConstraintAnchor.Type.BASELINE, D, ConstraintAnchor.Type.BASELINE)
        F.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        F.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        G.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 16)
        G.connect(ConstraintAnchor.Type.BASELINE, F, ConstraintAnchor.Type.BASELINE)
        root.layout()
        println(" direct: $directResolution -> A: $A B: $B C: $C D: $D E: $E F: $F G: $G")
        Assert.assertEquals(A.left.toLong(), 250)
        Assert.assertEquals(A.top.toLong(), 20)
        Assert.assertEquals(B.left.toLong(), 16)
        Assert.assertEquals(B.top.toLong(), 80)
        Assert.assertEquals(C.left.toLong(), 132)
        Assert.assertEquals(C.top.toLong(), 80)
        Assert.assertEquals(D.left.toLong(), 132)
        Assert.assertEquals(D.top.toLong(), 100)
        Assert.assertEquals(E.left.toLong(), 16)
        Assert.assertEquals(E.top.toLong(), 100)
        Assert.assertEquals(F.left.toLong(), 500)
        Assert.assertEquals(F.top.toLong(), 580)
        Assert.assertEquals(G.left.toLong(), 16)
        Assert.assertEquals(G.top.toLong(), 580)
    }

    @Test
    fun testComplexLayout() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GROUPING
        val A = ConstraintWidget(100, 100)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(30, 30)
        val E = ConstraintWidget(30, 30)
        val F = ConstraintWidget(30, 30)
        val G = ConstraintWidget(100, 20)
        val H = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        E.debugName = "E"
        F.debugName = "F"
        G.debugName = "G"
        H.debugName = "H"
        root.add(G)
        root.add(A)
        root.add(B)
        root.add(E)
        root.add(C)
        root.add(D)
        root.add(F)
        root.add(H)
        B.baselineDistance = 8
        C.baselineDistance = 8
        D.baselineDistance = 8
        E.baselineDistance = 8
        F.baselineDistance = 8
        G.baselineDistance = 8
        H.baselineDistance = 8
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 16)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 16)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 16)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 16)
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 16)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 16)
        E.connect(ConstraintAnchor.Type.BOTTOM, D, ConstraintAnchor.Type.BOTTOM)
        E.connect(ConstraintAnchor.Type.LEFT, D, ConstraintAnchor.Type.RIGHT, 16)
        F.connect(ConstraintAnchor.Type.BOTTOM, E, ConstraintAnchor.Type.BOTTOM)
        F.connect(ConstraintAnchor.Type.LEFT, E, ConstraintAnchor.Type.RIGHT, 16)
        G.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        G.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 16)
        G.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        H.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 16)
        H.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 16)
        root.measurer = sMeasurer
        root.layout()
        println(" direct: -> A: $A B: $B C: $C D: $D E: $E F: $F G: $G H: $H")
        Assert.assertEquals(A.left.toLong(), 16)
        Assert.assertEquals(A.top.toLong(), 250)
        Assert.assertEquals(B.left.toLong(), 132)
        Assert.assertEquals(B.top.toLong(), 250)
        Assert.assertEquals(C.left.toLong(), 132)
        Assert.assertEquals(C.top.toLong(), 290)
        Assert.assertEquals(D.left.toLong(), 132)
        Assert.assertEquals(D.top.toLong(), 320)
        Assert.assertEquals(E.left.toLong(), 178)
        Assert.assertEquals(E.top.toLong(), 320)
        Assert.assertEquals(F.left.toLong(), 224)
        Assert.assertEquals(F.top.toLong(), 320)
        Assert.assertEquals(G.left.toLong(), 484)
        Assert.assertEquals(G.top.toLong(), 290)
        Assert.assertEquals(H.left.toLong(), 484)
        Assert.assertEquals(H.top.toLong(), 564)
    }

    @Test
    fun testComplexLayoutWrap() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = Optimizer.OPTIMIZATION_DIRECT
        val A = ConstraintWidget(100, 100)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        val D = ConstraintWidget(30, 30)
        val E = ConstraintWidget(30, 30)
        val F = ConstraintWidget(30, 30)
        val G = ConstraintWidget(100, 20)
        val H = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        E.debugName = "E"
        F.debugName = "F"
        G.debugName = "G"
        H.debugName = "H"
        root.add(G)
        root.add(A)
        root.add(B)
        root.add(E)
        root.add(C)
        root.add(D)
        root.add(F)
        root.add(H)
        B.baselineDistance = 8
        C.baselineDistance = 8
        D.baselineDistance = 8
        E.baselineDistance = 8
        F.baselineDistance = 8
        G.baselineDistance = 8
        H.baselineDistance = 8
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 16)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 16)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 16)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 16)
        C.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 16)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.BOTTOM, A, ConstraintAnchor.Type.BOTTOM)
        D.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT, 16)
        E.connect(ConstraintAnchor.Type.BOTTOM, D, ConstraintAnchor.Type.BOTTOM)
        E.connect(ConstraintAnchor.Type.LEFT, D, ConstraintAnchor.Type.RIGHT, 16)
        F.connect(ConstraintAnchor.Type.BOTTOM, E, ConstraintAnchor.Type.BOTTOM)
        F.connect(ConstraintAnchor.Type.LEFT, E, ConstraintAnchor.Type.RIGHT, 16)
        G.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        G.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 16)
        G.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        H.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 16)
        H.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 16)
        root.measurer = sMeasurer
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println(" direct: -> A: $A B: $B C: $C D: $D E: $E F: $F G: $G H: $H")
        Assert.assertEquals(A.left.toLong(), 16)
        Assert.assertEquals(A.top.toLong(), 16)
        Assert.assertEquals(B.left.toLong(), 132)
        Assert.assertEquals(B.top.toLong(), 16)
        Assert.assertEquals(C.left.toLong(), 132)
        Assert.assertEquals(C.top.toLong(), 56)
        Assert.assertEquals(D.left.toLong(), 132)
        Assert.assertEquals(D.top.toLong(), 86)
        Assert.assertEquals(E.left.toLong(), 178)
        Assert.assertEquals(E.top.toLong(), 86)
        Assert.assertEquals(F.left.toLong(), 224)
        Assert.assertEquals(F.top.toLong(), 86)
        Assert.assertEquals(G.left.toLong(), 484)
        Assert.assertEquals(G.top.toLong(), 56)
        Assert.assertEquals(H.left.toLong(), 484)
        Assert.assertEquals(H.top.toLong(), 96)
    }

    @Test
    fun testChainLayoutWrap() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GROUPING
        val A = ConstraintWidget(100, 100)
        val B = ConstraintWidget(100, 20)
        val C = ConstraintWidget(100, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        root.add(A)
        root.add(B)
        root.add(C)
        A.baselineDistance = 28
        B.baselineDistance = 8
        C.baselineDistance = 8
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 16)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 16)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 16)
        B.connect(ConstraintAnchor.Type.BASELINE, A, ConstraintAnchor.Type.BASELINE)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.BASELINE, B, ConstraintAnchor.Type.BASELINE)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 16)
        root.measurer = sMeasurer
        //root.setWidth(332);
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        //root.setVerticalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
        root.layout()
        println(" direct: -> A: $A B: $B C: $C")
        Assert.assertEquals(A.left.toLong(), 16)
        Assert.assertEquals(A.top.toLong(), 250)
        Assert.assertEquals(B.left.toLong(), 116)
        Assert.assertEquals(B.top.toLong(), 270)
        Assert.assertEquals(C.left.toLong(), 216)
        Assert.assertEquals(C.top.toLong(), 270)
    }

    @Test
    fun testChainLayoutWrap2() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GROUPING
        val A = ConstraintWidget(100, 100)
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
        A.baselineDistance = 28
        B.baselineDistance = 8
        C.baselineDistance = 8
        D.baselineDistance = 8
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 16)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 16)
        A.connect(ConstraintAnchor.Type.RIGHT, B, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 16)
        B.connect(ConstraintAnchor.Type.BASELINE, A, ConstraintAnchor.Type.BASELINE)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.BASELINE, B, ConstraintAnchor.Type.BASELINE)
        C.connect(ConstraintAnchor.Type.LEFT, B, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, D, ConstraintAnchor.Type.LEFT, 16)
        D.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        D.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        root.measurer = sMeasurer
        //root.setWidth(332);
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        //root.setVerticalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
        root.layout()
        println(" direct: -> A: $A B: $B C: $C D: $D")
        Assert.assertEquals(A.left.toLong(), 16)
        Assert.assertEquals(A.top.toLong(), 250)
        Assert.assertEquals(B.left.toLong(), 116)
        Assert.assertEquals(B.top.toLong(), 270)
        Assert.assertEquals(C.left.toLong(), 216)
        Assert.assertEquals(C.top.toLong(), 270)
        Assert.assertEquals(D.left.toLong(), 332)
        Assert.assertEquals(D.top.toLong(), 580)
    }

    @Test
    fun testChainLayoutWrapGuideline() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GROUPING
        val A = ConstraintWidget(100, 20)
        val guideline = Guideline()
        guideline.orientation = Guideline.VERTICAL
        guideline.setGuideEnd(100)
        root.debugName = "root"
        A.debugName = "A"
        guideline.debugName = "guideline"
        root.add(A)
        root.add(guideline)
        A.baselineDistance = 28
        A.connect(ConstraintAnchor.Type.LEFT, guideline, ConstraintAnchor.Type.LEFT, 16)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 16)
        root.measurer = sMeasurer
        //root.setHorizontalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println(" direct: -> A: $A guideline: $guideline")
        Assert.assertEquals(A.left.toLong(), 516)
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(guideline.left.toLong(), 500)
    }

    @Test
    fun testChainLayoutWrapGuidelineChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GROUPING
        val A = ConstraintWidget(20, 20)
        val B = ConstraintWidget(20, 20)
        val C = ConstraintWidget(20, 20)
        val D = ConstraintWidget(20, 20)
        val A2 = ConstraintWidget(20, 20)
        val B2 = ConstraintWidget(20, 20)
        val C2 = ConstraintWidget(20, 20)
        val D2 = ConstraintWidget(20, 20)
        val guidelineStart = Guideline()
        val guidelineEnd = Guideline()
        guidelineStart.orientation = Guideline.VERTICAL
        guidelineEnd.orientation = Guideline.VERTICAL
        guidelineStart.setGuideBegin(30)
        guidelineEnd.setGuideEnd(30)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        A2.debugName = "A2"
        B2.debugName = "B2"
        C2.debugName = "C2"
        D2.debugName = "D2"
        guidelineStart.debugName = "guidelineStart"
        guidelineEnd.debugName = "guidelineEnd"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        root.add(A2)
        root.add(B2)
        root.add(C2)
        root.add(D2)
        root.add(guidelineStart)
        root.add(guidelineEnd)
        C.visibility = ConstraintWidget.GONE
        ChainConnect(ConstraintAnchor.Type.LEFT, guidelineStart, ConstraintAnchor.Type.RIGHT, guidelineEnd, A, B, C, D)
        ChainConnect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.RIGHT, root, A2, B2, C2, D2)
        root.measurer = sMeasurer
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        //root.setVerticalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
        root.layout()
        println(" direct: -> A: $A guideline: $guidelineStart ebnd $guidelineEnd B: $B C: $C D: $D")
        println(" direct: -> A2: $A2 B2: $B2 C2: $C2 D2: $D2")
        Assert.assertEquals(A.left.toLong(), 30)
        Assert.assertEquals(B.left.toLong(), 50)
        Assert.assertEquals(C.left.toLong(), 70)
        Assert.assertEquals(D.left.toLong(), 70)
        Assert.assertEquals(guidelineStart.left.toLong(), 30)
        Assert.assertEquals(guidelineEnd.left.toLong(), 90)
        Assert.assertEquals(A2.left.toLong(), 8)
        Assert.assertEquals(B2.left.toLong(), 36)
        Assert.assertEquals(C2.left.toLong(), 64)
        Assert.assertEquals(D2.left.toLong(), 92)
    }

    private fun ChainConnect(
        start: ConstraintAnchor.Type, startTarget: ConstraintWidget, end: ConstraintAnchor.Type,
        endTarget: ConstraintWidget, vararg widgets: ConstraintWidget
    ) {
        widgets[0].connect(start, startTarget, start)
        var previousWidget: ConstraintWidget? = null
        for (i in 0 until widgets.size) {
            if (previousWidget != null) {
                widgets[i].connect(start, previousWidget, end)
            }
            if (i < widgets.size - 1) {
                widgets[i].connect(end, widgets[i + 1], start)
            }
            previousWidget = widgets[i]
        }
        previousWidget?.connect(end, endTarget, end)
    }

    @Test
    fun testChainLayoutWrapGuidelineChainVertical() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GROUPING
        val A = ConstraintWidget(20, 20)
        val B = ConstraintWidget(20, 20)
        val C = ConstraintWidget(20, 20)
        val D = ConstraintWidget(20, 20)
        val A2 = ConstraintWidget(20, 20)
        val B2 = ConstraintWidget(20, 20)
        val C2 = ConstraintWidget(20, 20)
        val D2 = ConstraintWidget(20, 20)
        val guidelineStart = Guideline()
        val guidelineEnd = Guideline()
        guidelineStart.orientation = Guideline.HORIZONTAL
        guidelineEnd.orientation = Guideline.HORIZONTAL
        guidelineStart.setGuideBegin(30)
        guidelineEnd.setGuideEnd(30)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        D.debugName = "D"
        A2.debugName = "A2"
        B2.debugName = "B2"
        C2.debugName = "C2"
        D2.debugName = "D2"
        guidelineStart.debugName = "guidelineStart"
        guidelineEnd.debugName = "guidelineEnd"
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(D)
        root.add(A2)
        root.add(B2)
        root.add(C2)
        root.add(D2)
        root.add(guidelineStart)
        root.add(guidelineEnd)
        C.visibility = ConstraintWidget.GONE
        ChainConnect(ConstraintAnchor.Type.TOP, guidelineStart, ConstraintAnchor.Type.BOTTOM, guidelineEnd, A, B, C, D)
        ChainConnect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.BOTTOM, root, A2, B2, C2, D2)
        root.measurer = sMeasurer
        //root.setHorizontalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println(" direct: -> A: $A guideline: $guidelineStart ebnd $guidelineEnd B: $B C: $C D: $D")
        println(" direct: -> A2: $A2 B2: $B2 C2: $C2 D2: $D2")
        Assert.assertEquals(A.top.toLong(), 30)
        Assert.assertEquals(B.top.toLong(), 50)
        Assert.assertEquals(C.top.toLong(), 70)
        Assert.assertEquals(D.top.toLong(), 70)
        Assert.assertEquals(guidelineStart.top.toLong(), 30)
        Assert.assertEquals(guidelineEnd.top.toLong(), 90)
        Assert.assertEquals(A2.top.toLong(), 8)
        Assert.assertEquals(B2.top.toLong(), 36)
        Assert.assertEquals(C2.top.toLong(), 64)
        Assert.assertEquals(D2.top.toLong(), 92)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 0)
        Assert.assertEquals(C.left.toLong(), 0)
        Assert.assertEquals(D.left.toLong(), 0)
        Assert.assertEquals(A2.left.toLong(), 0)
        Assert.assertEquals(B2.left.toLong(), 0)
        Assert.assertEquals(C2.left.toLong(), 0)
        Assert.assertEquals(D2.left.toLong(), 0)
    }

    @Test
    fun testChainLayoutWrapRatioChain() {
        val root = ConstraintWidgetContainer(0, 0, 600, 600)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GROUPING
        val A = ConstraintWidget(20, 20)
        val B = ConstraintWidget(20, 20)
        val C = ConstraintWidget(20, 20)
        root.debugName = "root"
        A.debugName = "A"
        B.debugName = "B"
        C.debugName = "C"
        root.add(A)
        root.add(B)
        root.add(C)
        ChainConnect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.BOTTOM, root, A, B, C)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        C.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        A.verticalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.setDimensionRatio("1:1")
        root.measurer = sMeasurer
        //root.setHorizontalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
//        root.layout();
//
//        System.out.println(" direct: -> A: " + A + " B: " + B +  " C: "  + C);
//
//        assertEquals(A.getTop(), 0);
//        assertEquals(B.getTop(), 20);
//        assertEquals(C.getTop(), 580);
//        assertEquals(A.getLeft(), 290);
//        assertEquals(B.getLeft(), 20);
//        assertEquals(C.getLeft(), 290);
//        assertEquals(B.getWidth(), 560);
//        assertEquals(B.getHeight(), B.getWidth());
//
//        //root.setHorizontalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
//        root.setVerticalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
//        root.layout();
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.verticalDimensionBehaviour = DimensionBehaviour.FIXED
        root.height = 600
        root.layout()
        println(" direct: -> A: $A B: $B C: $C")
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(B.top.toLong(), 290)
        Assert.assertEquals(C.top.toLong(), 580)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 0)
        Assert.assertEquals(C.left.toLong(), 0)
        Assert.assertEquals(B.width.toLong(), 20)
        Assert.assertEquals(B.height.toLong(), B.width.toLong())
    }

    @Test
    fun testLayoutWrapBarrier() {
        val root = ConstraintWidgetContainer("root", 600, 600)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GROUPING
        val A = ConstraintWidget("A", 20, 20)
        val B = ConstraintWidget("B", 20, 20)
        val C = ConstraintWidget("C", 20, 20)
        val barrier = Barrier("Barrier")
        barrier.barrierType = Barrier.BOTTOM
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(barrier)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.visibility = ConstraintWidget.GONE
        C.connect(ConstraintAnchor.Type.TOP, barrier, ConstraintAnchor.Type.TOP)
        barrier.add(A)
        barrier.add(B)
        root.measurer = sMeasurer
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println(" direct: -> root: " + root + " A: " + A + " B: " + B + " C: " + C + " Barrier: " + barrier.top)
        Assert.assertEquals(A.left.toLong(), 0)
        Assert.assertEquals(A.top.toLong(), 0)
        Assert.assertEquals(B.left.toLong(), 0)
        Assert.assertEquals(B.top.toLong(), 20)
        Assert.assertEquals(C.left.toLong(), 0)
        Assert.assertEquals(C.top.toLong(), 20)
        Assert.assertEquals(barrier.top.toLong(), 20)
        Assert.assertEquals(root.height.toLong(), 40)
    }

    @Test
    fun testLayoutWrapGuidelinesMatch() {
        val root = ConstraintWidgetContainer("root", 600, 600)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GROUPING
        //root.setOptimizationLevel(Optimizer.OPTIMIZATION_NONE);
        val A = ConstraintWidget("A", 20, 20)
        val left = Guideline()
        left.orientation = Guideline.VERTICAL
        left.setGuideBegin(30)
        left.debugName = "L"
        val right = Guideline()
        right.orientation = Guideline.VERTICAL
        right.setGuideEnd(30)
        right.debugName = "R"
        val top = Guideline()
        top.orientation = Guideline.HORIZONTAL
        top.setGuideBegin(30)
        top.debugName = "T"
        val bottom = Guideline()
        bottom.orientation = Guideline.HORIZONTAL
        bottom.setGuideEnd(30)
        bottom.debugName = "B"
        root.add(A)
        root.add(left)
        root.add(right)
        root.add(top)
        root.add(bottom)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.verticalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.connect(ConstraintAnchor.Type.LEFT, left, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, right, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.TOP, top, ConstraintAnchor.Type.TOP)
        A.connect(ConstraintAnchor.Type.BOTTOM, bottom, ConstraintAnchor.Type.BOTTOM)
        root.measurer = sMeasurer
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println(
            " direct: -> root: " + root + " A: " + A + " L: " + left + " R: " + right
                    + " T: " + top + " B: " + bottom
        )
        Assert.assertEquals(root.height.toLong(), 60)
        Assert.assertEquals(A.left.toLong(), 30)
        Assert.assertEquals(A.top.toLong(), 30)
        Assert.assertEquals(A.width.toLong(), 540)
        Assert.assertEquals(A.height.toLong(), 0)
    }

    @Test
    fun testLayoutWrapMatch() {
        val root = ConstraintWidgetContainer("root", 600, 600)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GROUPING
        //        root.setOptimizationLevel(Optimizer.OPTIMIZATION_NONE);
        val A = ConstraintWidget("A", 50, 20)
        val B = ConstraintWidget("B", 50, 30)
        val C = ConstraintWidget("C", 50, 20)
        root.add(A)
        root.add(B)
        root.add(C)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP)
        B.connect(ConstraintAnchor.Type.LEFT, A, ConstraintAnchor.Type.RIGHT)
        B.connect(ConstraintAnchor.Type.RIGHT, C, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.TOP, A, ConstraintAnchor.Type.BOTTOM)
        B.connect(ConstraintAnchor.Type.BOTTOM, C, ConstraintAnchor.Type.TOP)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM)
        B.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        B.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.measurer = sMeasurer
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println(" direct: -> root: $root A: $A B: $B C: $C")
        Assert.assertEquals(B.top.toLong(), 20)
        Assert.assertEquals(B.bottom.toLong(), 50)
        Assert.assertEquals(B.left.toLong(), 50)
        Assert.assertEquals(B.right.toLong(), 550)
        Assert.assertEquals(root.height.toLong(), 70)
    }

    @Test
    fun testLayoutWrapBarrier2() {
        val root = ConstraintWidgetContainer("root", 600, 600)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GROUPING
        //root.setOptimizationLevel(Optimizer.OPTIMIZATION_NONE);
        val A = ConstraintWidget("A", 50, 20)
        val B = ConstraintWidget("B", 50, 30)
        val C = ConstraintWidget("C", 50, 20)
        val guideline = Guideline()
        guideline.debugName = "end"
        guideline.setGuideEnd(40)
        guideline.orientation = ConstraintWidget.VERTICAL
        val barrier = Barrier()
        barrier.barrierType = Barrier.LEFT
        barrier.debugName = "barrier"
        barrier.add(B)
        barrier.add(C)
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(barrier)
        root.add(guideline)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, barrier, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, guideline, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        root.measurer = sMeasurer
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println(" direct: -> root: $root A: $A B: $B C: $C")
        Assert.assertEquals(root.width.toLong(), 140)
    }

    @Test
    fun testLayoutWrapBarrier3() {
        val root = ConstraintWidgetContainer("root", 600, 600)
        root.optimizationLevel = Optimizer.OPTIMIZATION_GROUPING
        root.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        val A = ConstraintWidget("A", 50, 20)
        val B = ConstraintWidget("B", 50, 30)
        val C = ConstraintWidget("C", 50, 20)
        val guideline = Guideline()
        guideline.debugName = "end"
        guideline.setGuideEnd(40)
        guideline.orientation = ConstraintWidget.VERTICAL
        val barrier = Barrier()
        barrier.barrierType = Barrier.LEFT
        barrier.debugName = "barrier"
        barrier.add(B)
        barrier.add(C)
        root.add(A)
        root.add(B)
        root.add(C)
        root.add(barrier)
        root.add(guideline)
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, barrier, ConstraintAnchor.Type.LEFT)
        B.connect(ConstraintAnchor.Type.RIGHT, guideline, ConstraintAnchor.Type.RIGHT)
        C.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT)
        root.measurer = sMeasurer
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println(" direct: -> root: $root A: $A B: $B C: $C")
        Assert.assertEquals(root.width.toLong(), 140)
    }

    @Test
    fun testSimpleGuideline2() {
        val root = ConstraintWidgetContainer("root", 600, 600)
        val guidelineStart = Guideline()
        guidelineStart.debugName = "start"
        guidelineStart.setGuidePercent(0.1f)
        guidelineStart.orientation = ConstraintWidget.VERTICAL
        val guidelineEnd = Guideline()
        guidelineEnd.debugName = "end"
        guidelineEnd.setGuideEnd(40)
        guidelineEnd.orientation = ConstraintWidget.VERTICAL
        val A = ConstraintWidget("A", 50, 20)
        root.add(A)
        root.add(guidelineStart)
        root.add(guidelineEnd)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        A.connect(ConstraintAnchor.Type.LEFT, guidelineStart, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, guidelineEnd, ConstraintAnchor.Type.RIGHT)
        root.measurer = sMeasurer
        //root.setHorizontalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
        root.layout()
        println(" root: $root")
        println("guideline start: $guidelineStart")
        println("guideline end: $guidelineEnd")
    }

    companion object {
        var sMeasurer: BasicMeasure.Measurer = object : BasicMeasure.Measurer {
            override fun measure(widget: ConstraintWidget, measure: BasicMeasure.Measure) {
                val horizontalBehavior = measure.horizontalBehavior
                val verticalBehavior = measure.verticalBehavior
                val horizontalDimension = measure.horizontalDimension
                val verticalDimension = measure.verticalDimension
                println("*** MEASURE $widget ***")
                if (horizontalBehavior === DimensionBehaviour.FIXED) {
                    measure.measuredWidth = horizontalDimension
                } else if (horizontalBehavior === DimensionBehaviour.MATCH_CONSTRAINT) {
                    measure.measuredWidth = horizontalDimension
                }
                if (verticalBehavior === DimensionBehaviour.FIXED) {
                    measure.measuredHeight = verticalDimension
                    measure.measuredBaseline = 8
                } else {
                    measure.measuredHeight = verticalDimension
                    measure.measuredBaseline = 8
                }
            }

            override fun didMeasures() {}
        }
    }
}