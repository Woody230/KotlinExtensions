package com.bselzer.ktx.compose.ui.layout.row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel

data class RowPresentation(
    /**
     * The horizontal arrangement of the layout's children.
     */
    val horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,

    /**
     * The vertical alignment of the layout's children.
     */
    val verticalAlignment: Alignment.Vertical = Alignment.Top,
) : PresentationModel