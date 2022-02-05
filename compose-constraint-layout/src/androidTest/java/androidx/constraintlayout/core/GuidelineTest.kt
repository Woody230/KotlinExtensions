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
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import androidx.constraintlayout.core.widgets.Guideline
import androidx.constraintlayout.core.widgets.ConstraintAnchor
import org.junit.Assert
import kotlin.test.Test

class GuidelineTest {
    @Test
    fun testWrapGuideline() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val guidelineRight = Guideline()
        guidelineRight.orientation = Guideline.VERTICAL
        val guidelineBottom = Guideline()
        guidelineBottom.orientation = Guideline.HORIZONTAL
        guidelineRight.setGuidePercent(0.64f)
        guidelineBottom.setGuideEnd(60)
        root.debugName = "Root"
        A.debugName = "A"
        guidelineRight.debugName = "GuidelineRight"
        guidelineBottom.debugName = "GuidelineBottom"
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT)
        A.connect(ConstraintAnchor.Type.RIGHT, guidelineRight, ConstraintAnchor.Type.RIGHT)
        A.connect(ConstraintAnchor.Type.BOTTOM, guidelineBottom, ConstraintAnchor.Type.TOP)
        root.add(A)
        root.add(guidelineRight)
        root.add(guidelineBottom)
        root.layout()
        println("a) root: $root guideline right: $guidelineRight guideline bottom: $guidelineBottom A: $A")
        root.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("b) root: $root guideline right: $guidelineRight guideline bottom: $guidelineBottom A: $A")
        Assert.assertEquals(root.height.toLong(), 80)
    }

    @Test
    fun testWrapGuideline2() {
        val root = ConstraintWidgetContainer(0, 0, 800, 600)
        val A = ConstraintWidget(100, 20)
        val guideline = Guideline()
        guideline.orientation = Guideline.VERTICAL
        guideline.setGuideBegin(60)
        root.debugName = "Root"
        A.debugName = "A"
        guideline.debugName = "Guideline"
        A.connect(ConstraintAnchor.Type.LEFT, guideline, ConstraintAnchor.Type.LEFT, 5)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 5)
        A.horizontalDimensionBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
        root.add(A)
        root.add(guideline)
        //        root.layout();
        println("a) root: $root guideline: $guideline A: $A")
        root.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        root.layout()
        println("b) root: $root guideline: $guideline A: $A")
        Assert.assertEquals(root.width.toLong(), 70)
    }
}