package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import com.bselzer.ktx.compose.ui.layout.LayoutOrientation

object ComposeMerger {
    val alignment: ComponentMerger<Alignment> = AlignmentMerger()
    val borderStroke: ComponentMerger<BorderStroke> = BorderStrokeMerger()
    val buttonColors: ComponentMerger<ButtonColors> = ButtonColorsMerger()
    val buttonElevation: ComponentMerger<ButtonElevation> = ButtonElevationMerger()
    val checkboxColors: ComponentMerger<CheckboxColors> = CheckboxColorsMerger()
    val colorFilter: ComponentMerger<ColorFilter> = ColorFilterMerger()
    val color: ComponentMerger<Color> = ColorMerger()
    val contentScale: ComponentMerger<ContentScale> = ContentScaleMerger()
    val dp: ComponentMerger<Dp> = DpMerger()
    val dpOffset: ComponentMerger<DpOffset> = DpOffsetMerger()
    val flingBehavior: ComponentMerger<FlingBehavior> = FlingBehaviorMerger()
    val floatingActionButtonElevation: ComponentMerger<FloatingActionButtonElevation> = FloatingActionButtonElevationMerger()
    val float: ComponentMerger<Float> = FloatMerger()
    val horizontalAlignment: ComponentMerger<Alignment.Horizontal> = HorizontalAlignmentMerger()
    val horizontalArrangement: ComponentMerger<Arrangement.Horizontal> = HorizontalArrangementMerger()
    val indication: ComponentMerger<Indication> = IndicationMerger()
    val int: ComponentMerger<Int> = IntMerger()
    val layoutOrientation: ComponentMerger<LayoutOrientation> = LayoutOrientationMerger()
    val paddingValues: ComponentMerger<PaddingValues> = PaddingValuesMerger()
    val radioButtonColors: ComponentMerger<RadioButtonColors> = RadioButtonColorsMerger()
    val role: ComponentMerger<Role?> = RoleMerger()
    val shape: ComponentMerger<Shape> = ShapeMerger()
    val switchColors: ComponentMerger<SwitchColors> = SwitchColorsMerger()
    val textFieldColors: ComponentMerger<TextFieldColors> = TextFieldColorsMerger()
    val textUnit: ComponentMerger<TextUnit> = TextUnitMerger()
    val triState: ComponentMerger<TriState> = TriStateMerger()
    val verticalAlignment: ComponentMerger<Alignment.Vertical> = VerticalAlignmentMerger()
    val verticalArrangement: ComponentMerger<Arrangement.Vertical> = VerticalArrangementMerger()
    val visualTransformation: ComponentMerger<VisualTransformation> = VisualTransformationMerger()
}