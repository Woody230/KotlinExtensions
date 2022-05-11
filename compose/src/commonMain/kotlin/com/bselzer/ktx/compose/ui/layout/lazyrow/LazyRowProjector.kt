package com.bselzer.ktx.compose.ui.layout.lazyrow

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.divider.DividerProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class LazyRowProjector<T>(
    override val interactor: LazyRowInteractor<T>,
    override val presenter: LazyRowPresenter = LazyRowPresenter.Default
) : Projector<LazyRowInteractor<T>, LazyRowPresenter>() {
    private val dividerProjector = interactor.divider?.let { divider -> DividerProjector(divider, presenter.divider) }

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable LazyItemScope.(Int, T) -> Unit
    ) = contextualize(modifier) { combinedModifier ->
        LazyRow(
            modifier = combinedModifier,
            state = rememberSaveable(saver = LazyListState.Saver) { interactor.state },
            contentPadding = contentPadding,
            reverseLayout = reverseLayout.toBoolean(),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            flingBehavior = flingBehavior,
        ) {
            itemsIndexed(interactor.items) { index, item ->
                val isFirst = index == 0
                val shouldPrepend = isFirst && prepend.toBoolean()
                if (shouldPrepend) {
                    dividerProjector?.Projection()
                }

                content(index, item)

                val isLast = index == interactor.items.lastIndex
                val isIntermediate = !isFirst && !isLast
                val shouldAppend = isLast && append.toBoolean()
                if (isIntermediate || shouldAppend) {
                    dividerProjector?.Projection()
                }
            }
        }
    }
}