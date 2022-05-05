package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonElevation
import androidx.compose.material.RadioButtonColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit

object ComposeMerger {
    val borderStroke: ComponentMerger<BorderStroke> = BorderStrokeMerger()
    val buttonColors: ComponentMerger<ButtonColors> = ButtonColorsMerger()
    val buttonElevation: ComponentMerger<ButtonElevation> = ButtonElevationMerger()
    val color: ComponentMerger<Color> = ColorMerger()
    val dpOffset: ComponentMerger<DpOffset> = DpOffsetMerger()
    val flingBehavior: ComponentMerger<FlingBehavior> = FlingBehaviorMerger()
    val indication: ComponentMerger<Indication> = IndicationMerger()
    val paddingValues: ComponentMerger<PaddingValues> = PaddingValuesMerger()
    val radioButtonColors: ComponentMerger<RadioButtonColors> = RadioButtonColorsMerger()
    val shape: ComponentMerger<Shape> = ShapeMerger()
    val textUnit: ComponentMerger<TextUnit> = TextUnitMerger()
}