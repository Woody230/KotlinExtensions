package com.bselzer.ktx.compose.ui.layout.lazyrow

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.divider.DividerPresenter
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.TriState
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class LazyRowPresenter(
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
     * A padding around the whole content. This will add padding for the. content after it has been clipped, which is not possible via modifier param.
     * You can use it to add a padding before the first item or after the last one. If you want to add a spacing between each item use verticalArrangement.
     */
    val contentPadding: PaddingValues = ComposeMerger.paddingValues.default,

    /**
     * Reverse the direction of scrolling and layout, when true items will be composed from the bottom to the top and LazyListState.firstVisibleItemIndex == 0 will mean we scrolled to the bottom.
     */
    val reverseLayout: TriState = ComposeMerger.triState.default,

    /**
     * The horizontal arrangement of the layout's children.
     */
    val horizontalArrangement: Arrangement.Horizontal = ComposeMerger.horizontalArrangement.default,

    /**
     * The vertical alignment of the layout's children.
     */
    val verticalAlignment: Alignment.Vertical = ComposeMerger.verticalAlignment.default,

    /**
     * interaction describing fling behavior.
     */
    val flingBehavior: FlingBehavior = ComposeMerger.flingBehavior.default
) : Presenter<LazyRowPresenter>() {
    companion object {
        @Stable
        val Default = LazyRowPresenter()
    }

    override fun safeMerge(other: LazyRowPresenter) = LazyRowPresenter(
        divider = divider.merge(other.divider),
        prepend = ComposeMerger.triState.safeMerge(prepend, other.prepend),
        append = ComposeMerger.triState.safeMerge(append, other.append),
        contentPadding = ComposeMerger.paddingValues.safeMerge(contentPadding, other.contentPadding),
        reverseLayout = ComposeMerger.triState.safeMerge(reverseLayout, other.reverseLayout),
        horizontalArrangement = ComposeMerger.horizontalArrangement.safeMerge(horizontalArrangement, other.horizontalArrangement),
        verticalAlignment = ComposeMerger.verticalAlignment.safeMerge(verticalAlignment, other.verticalAlignment),
        flingBehavior = ComposeMerger.flingBehavior.safeMerge(flingBehavior, other.flingBehavior)
    )

    @Composable
    override fun localized() = LazyRowPresenter(
        prepend = TriState.FALSE,
        append = TriState.FALSE,
        contentPadding = PaddingValues(0.dp),
        reverseLayout = TriState.FALSE,
        verticalAlignment = Alignment.Top,
        flingBehavior = ScrollableDefaults.flingBehavior()
    ).merge(this).run {
        copy(
            divider = divider.localized(),
            horizontalArrangement = if (ComposeMerger.horizontalArrangement.isDefault(horizontalArrangement)) {
                if (!reverseLayout.toBoolean()) Arrangement.Start else Arrangement.End
            } else {
                horizontalArrangement
            }
        )
    }
}