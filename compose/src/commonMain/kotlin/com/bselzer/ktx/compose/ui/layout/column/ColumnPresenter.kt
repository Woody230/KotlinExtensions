package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import com.bselzer.ktx.compose.ui.layout.divider.DividerPresenter
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.TriState
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class ColumnPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * The [Presentable] of the divider between items.
     */
    val divider: DividerPresenter = DividerPresenter.Default,

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
) : Presenter<ColumnPresenter>(modifier) {
    companion object {
        @Stable
        val Default = ColumnPresenter()

        @Stable
        val Centered = ColumnPresenter(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)

        @Stable
        val CenteredHorizontally = ColumnPresenter(horizontalAlignment = Alignment.CenterHorizontally)
    }

    override fun safeMerge(other: ColumnPresenter) = ColumnPresenter(
        modifier = modifier.merge(other.modifier),
        divider = divider.merge(other.divider),
        prepend = ComposeMerger.triState.safeMerge(prepend, other.prepend),
        append = ComposeMerger.triState.safeMerge(append, other.append),
        verticalArrangement = ComposeMerger.verticalArrangement.safeMerge(verticalArrangement, other.verticalArrangement),
        horizontalAlignment = ComposeMerger.horizontalAlignment.safeMerge(horizontalAlignment, other.horizontalAlignment)
    )

    @Composable
    override fun localized() = ColumnPresenter(
        prepend = TriState.FALSE,
        append = TriState.FALSE,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ).merge(this).run {
        copy(divider = divider.localized())
    }
}