package com.bselzer.ktx.compose.ui.layout.lazyrow

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.TriState
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class LazyRowPresentation(
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
     * Logic describing fling behavior.
     */
    val flingBehavior: FlingBehavior = ComposeMerger.flingBehavior.default
) : Presenter<LazyRowPresentation>() {
    @Composable
    override fun safeMerge(other: LazyRowPresentation) = LazyRowPresentation(
        contentPadding = ComposeMerger.paddingValues.safeMerge(contentPadding, other.contentPadding),
        reverseLayout = ComposeMerger.triState.safeMerge(reverseLayout, other.reverseLayout),
        horizontalArrangement = ComposeMerger.horizontalArrangement.safeMerge(horizontalArrangement, other.horizontalArrangement),
        verticalAlignment = ComposeMerger.verticalAlignment.safeMerge(verticalAlignment, other.verticalAlignment),
        flingBehavior = ComposeMerger.flingBehavior.safeMerge(flingBehavior, other.flingBehavior)
    )

    @Composable
    override fun localized(): LazyRowPresentation {
        val localized = super.localized()

        return if (ComposeMerger.horizontalArrangement.isDefault(localized.horizontalArrangement)) {
            localized.copy(horizontalArrangement = if (!localized.reverseLayout.toBoolean()) Arrangement.Start else Arrangement.End)
        } else {
            localized
        }
    }

    @Composable
    override fun createLocalization() = LazyRowPresentation(
        contentPadding = PaddingValues(0.dp),
        reverseLayout = TriState.FALSE,
        verticalAlignment = Alignment.Top,
        flingBehavior = ScrollableDefaults.flingBehavior()
    )
}