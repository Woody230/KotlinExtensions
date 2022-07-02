package com.bselzer.ktx.compose.ui.layout.lazyrow

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.LayoutOrientation
import com.bselzer.ktx.compose.ui.layout.LocalLayoutOrientation
import com.bselzer.ktx.compose.ui.layout.divider.DividerProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class LazyRowProjector<T>(
    interactor: LazyRowInteractor<T>,
    presenter: LazyRowPresenter = LazyRowPresenter.Default
) : Projector<LazyRowInteractor<T>, LazyRowPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable LazyItemScope.(Int, T) -> Unit
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        LazyRow(
            modifier = combinedModifier,
            state = rememberSaveable(saver = LazyListState.Saver) { interactor.state },
            contentPadding = presenter.contentPadding,
            reverseLayout = presenter.reverseLayout.toBoolean(),
            horizontalArrangement = presenter.horizontalArrangement,
            verticalAlignment = presenter.verticalAlignment,
            flingBehavior = presenter.flingBehavior,
        ) {
            val dividerProjector = interactor.divider?.let { divider -> DividerProjector(divider, presenter.divider) }
            itemsIndexed(interactor.items) { index, item ->
                CompositionLocalProvider(
                    LocalLayoutOrientation provides LayoutOrientation.HORIZONTAL
                ) {
                    val isFirst = index == 0
                    val shouldPrepend = isFirst && presenter.prepend.toBoolean()
                    if (shouldPrepend) {
                        dividerProjector?.Projection()
                    }

                    content(index, item)

                    val isLast = index == interactor.items.lastIndex
                    val isIntermediate = !isLast
                    val shouldAppend = isLast && presenter.append.toBoolean()
                    if (isIntermediate || shouldAppend) {
                        dividerProjector?.Projection()
                    }
                }
            }
        }
    }
}