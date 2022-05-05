package com.bselzer.ktx.compose.ui.layout.picker

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.icon.IconPresentation
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel

data class PickerPresentation(
    /**
     * The style of the text for displaying the value.
     */
    val textStyle: TextStyle = TextStyle.Default,

    /**
     * The offset of the new value within the scrolling animation.
     */
    val animationOffset: Dp = 18.dp,

    /**
     * The [PresentationModel] for the up-directional icon.
     */
    val upIcon: IconPresentation = IconPresentation(),

    /**
     * The [PresentationModel] for the down-directional icon.
     */
    val downIcon: IconPresentation = IconPresentation()
) : PresentationModel