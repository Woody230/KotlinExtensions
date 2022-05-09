package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import com.bselzer.ktx.compose.ui.layout.divider.DividerPresentation
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.TriState
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class ColumnPresentation(
    /**
     * The [PresentationModel] of the divider between items.
     */
    val divider: DividerPresentation = DividerPresentation.Default,

    /**
     * Whether a divider should be added before the items.
     */
    val prepend: TriState = ComposeMerger.triState.default,

    /**
     * Whether a divider should be added after the items.
     */
    val append: TriState = ComposeMerger.triState.default,

    /**
     * The vertical arrangement of the layout's children.
     */
    val verticalArrangement: Arrangement.Vertical = ComposeMerger.verticalArrangement.default,

    /**
     * The horizontal alignment of the layout's children.
     */
    val horizontalAlignment: Alignment.Horizontal = ComposeMerger.horizontalAlignment.default,
) : Presenter<ColumnPresentation>() {
    companion object {
        @Stable
        val Default = ColumnPresentation()
    }

    override fun safeMerge(other: ColumnPresentation) = ColumnPresentation(
        divider = divider.merge(other.divider),
        prepend = ComposeMerger.triState.safeMerge(prepend, other.prepend),
        append = ComposeMerger.triState.safeMerge(append, other.append),
        verticalArrangement = ComposeMerger.verticalArrangement.safeMerge(verticalArrangement, other.verticalArrangement),
        horizontalAlignment = ComposeMerger.horizontalAlignment.safeMerge(horizontalAlignment, other.horizontalAlignment)
    )

    @Composable
    override fun localized() = ColumnPresentation(
        prepend = TriState.FALSE,
        append = TriState.FALSE,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ).merge(this).run {
        copy(divider = divider.localized())
    }
}