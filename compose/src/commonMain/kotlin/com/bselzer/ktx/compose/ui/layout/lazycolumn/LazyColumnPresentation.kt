package com.bselzer.ktx.compose.ui.layout.lazycolumn

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel

data class LazyColumnPresentation(
    /**
     * A padding around the whole content. This will add padding for the. content after it has been clipped, which is not possible via modifier param.
     * You can use it to add a padding before the first item or after the last one. If you want to add a spacing between each item use verticalArrangement.
     */
    val contentPadding: PaddingValues = PaddingValues(0.dp),

    /**
     * Reverse the direction of scrolling and layout, when true items will be composed from the bottom to the top and LazyListState.firstVisibleItemIndex == 0 will mean we scrolled to the bottom.
     */
    val reverseLayout: Boolean = false,

    /**
     * The vertical arrangement of the layout's children.
     */
    val verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,

    /**
     * The horizontal alignment of the layout's children.
     */
    val horizontalAlignment: Alignment.Horizontal = Alignment.Start,

    /**
     * Logic describing fling behavior.
     */
    val flingBehavior: FlingBehavior = ComposeMerger.flingBehavior.default
) : PresentationModel