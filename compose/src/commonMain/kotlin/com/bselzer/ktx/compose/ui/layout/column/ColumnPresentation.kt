package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel

data class ColumnPresentation(
    /**
     * The vertical arrangement of the layout's children.
     */
    val verticalArrangement: Arrangement.Vertical = Arrangement.Top,

    /**
     * The horizontal alignment of the layout's children.
     */
    val horizontalAlignment: Alignment.Horizontal = Alignment.Start,
) : PresentationModel