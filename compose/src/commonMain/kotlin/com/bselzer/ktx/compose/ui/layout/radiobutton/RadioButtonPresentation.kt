package com.bselzer.ktx.compose.ui.layout.radiobutton

import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel

data class RadioButtonPresentation(
    /**
     * [RadioButtonColors] that will be used to resolve the background and content color for this RadioButton in different states. See [RadioButtonDefaults.colors].
     */
    val colors: RadioButtonColors = ComposeMerger.radioButtonColors.default,
) : PresentationModel