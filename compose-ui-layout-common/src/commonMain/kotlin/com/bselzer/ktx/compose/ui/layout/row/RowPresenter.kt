package com.bselzer.ktx.compose.ui.layout.row

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

data class RowPresenter(
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
     * The horizontal arrangement of the layout's children.
     */
    val horizontalArrangement: Arrangement.Horizontal = ComposeMerger.horizontalArrangement.default,

    /**
     * The vertical alignment of the layout's children.
     */
    val verticalAlignment: Alignment.Vertical = ComposeMerger.verticalAlignment.default,
) : Presenter<RowPresenter>(modifier) {
    companion object {
        @Stable
        val Default = RowPresenter()

        @Stable
        val Centered = RowPresenter(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically)

        @Stable
        val CenteredVertically = RowPresenter(verticalAlignment = Alignment.CenterVertically)
    }

    override fun safeMerge(other: RowPresenter) = RowPresenter(
        modifier = modifier.merge(other.modifier),
        divider = divider.merge(other.divider),
        prepend = ComposeMerger.triState.safeMerge(prepend, other.prepend),
        append = ComposeMerger.triState.safeMerge(append, other.append),
        horizontalArrangement = ComposeMerger.horizontalArrangement.safeMerge(horizontalArrangement, other.horizontalArrangement),
        verticalAlignment = ComposeMerger.verticalAlignment.safeMerge(verticalAlignment, other.verticalAlignment)
    )

    @Composable
    override fun localized() = RowPresenter(
        prepend = TriState.FALSE,
        append = TriState.FALSE,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ).merge(this)
}