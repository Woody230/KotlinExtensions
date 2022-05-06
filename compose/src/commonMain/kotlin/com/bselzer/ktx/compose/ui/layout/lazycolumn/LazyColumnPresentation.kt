package com.bselzer.ktx.compose.ui.layout.lazycolumn

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.TriState
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class LazyColumnPresentation(
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
     * The vertical arrangement of the layout's children.
     */
    val verticalArrangement: Arrangement.Vertical = ComposeMerger.verticalArrangement.default,

    /**
     * The horizontal alignment of the layout's children.
     */
    val horizontalAlignment: Alignment.Horizontal = ComposeMerger.horizontalAlignment.default,

    /**
     * Logic describing fling behavior.
     */
    val flingBehavior: FlingBehavior = ComposeMerger.flingBehavior.default
) : Presenter<LazyColumnPresentation>() {
    companion object {
        @Stable
        val Default = LazyColumnPresentation()
    }

    override fun safeMerge(other: LazyColumnPresentation) = LazyColumnPresentation(
        contentPadding = ComposeMerger.paddingValues.safeMerge(contentPadding, other.contentPadding),
        reverseLayout = ComposeMerger.triState.safeMerge(reverseLayout, other.reverseLayout),
        verticalArrangement = ComposeMerger.verticalArrangement.safeMerge(verticalArrangement, other.verticalArrangement),
        horizontalAlignment = ComposeMerger.horizontalAlignment.safeMerge(horizontalAlignment, other.horizontalAlignment),
        flingBehavior = ComposeMerger.flingBehavior.safeMerge(flingBehavior, other.flingBehavior)
    )

    @Composable
    override fun localized(): LazyColumnPresentation {
        val localized = super.localized()

        return if (ComposeMerger.verticalArrangement.isDefault(localized.verticalArrangement)) {
            localized.copy(verticalArrangement = if (!localized.reverseLayout.toBoolean()) Arrangement.Top else Arrangement.Bottom)
        } else {
            localized
        }
    }

    @Composable
    override fun createLocalization() = LazyColumnPresentation(
        contentPadding = PaddingValues(0.dp),
        reverseLayout = TriState.FALSE,
        horizontalAlignment = Alignment.Start,
        flingBehavior = ScrollableDefaults.flingBehavior()
    )
}