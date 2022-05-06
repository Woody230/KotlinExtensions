package com.bselzer.ktx.compose.ui.layout.row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class RowPresentation(
    /**
     * The horizontal arrangement of the layout's children.
     */
    val horizontalArrangement: Arrangement.Horizontal = ComposeMerger.horizontalArrangement.default,

    /**
     * The vertical alignment of the layout's children.
     */
    val verticalAlignment: Alignment.Vertical = ComposeMerger.verticalAlignment.default,
) : Presenter<RowPresentation>() {
    companion object {
        @Stable
        val Default = RowPresentation()
    }

    override fun safeMerge(other: RowPresentation) = RowPresentation(
        horizontalArrangement = ComposeMerger.horizontalArrangement.safeMerge(horizontalArrangement, other.horizontalArrangement),
        verticalAlignment = ComposeMerger.verticalAlignment.safeMerge(verticalAlignment, other.verticalAlignment)
    )

    @Composable
    override fun createLocalization() = RowPresentation(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    )
}