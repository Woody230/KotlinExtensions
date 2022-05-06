package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class ColumnPresentation(
    /**
     * The vertical arrangement of the layout's children.
     */
    val verticalArrangement: Arrangement.Vertical = ComposeMerger.verticalArrangement.default,

    /**
     * The horizontal alignment of the layout's children.
     */
    val horizontalAlignment: Alignment.Horizontal = ComposeMerger.horizontalAlignment.default,
) : Presenter<ColumnPresentation>() {
    @Composable
    override fun safeMerge(other: ColumnPresentation) = ColumnPresentation(
        verticalArrangement = ComposeMerger.verticalArrangement.safeMerge(verticalArrangement, other.verticalArrangement),
        horizontalAlignment = ComposeMerger.horizontalAlignment.safeMerge(horizontalAlignment, other.horizontalAlignment)
    )

    @Composable
    override fun createLocalization() = ColumnPresentation(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    )
}