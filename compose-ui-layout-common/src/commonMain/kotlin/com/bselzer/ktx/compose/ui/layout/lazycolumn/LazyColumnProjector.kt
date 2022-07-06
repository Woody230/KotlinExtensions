package com.bselzer.ktx.compose.ui.layout.lazycolumn

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.LayoutOrientation
import com.bselzer.ktx.compose.ui.layout.LocalLayoutOrientation
import com.bselzer.ktx.compose.ui.layout.divider.DividerProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class LazyColumnProjector<T>(
    interactor: LazyColumnInteractor<T>,
    presenter: LazyColumnPresenter = LazyColumnPresenter.Default
) : Projector<LazyColumnInteractor<T>, LazyColumnPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable LazyItemScope.(Int, T) -> Unit
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        LazyColumn(
            modifier = combinedModifier,
            state = rememberSaveable(saver = LazyListState.Saver) { interactor.state },
            contentPadding = presenter.contentPadding,
            reverseLayout = presenter.reverseLayout.toBoolean(),
            verticalArrangement = presenter.verticalArrangement,
            horizontalAlignment = presenter.horizontalAlignment,
            flingBehavior = presenter.flingBehavior,
        ) {
            val dividerProjector = interactor.divider?.let { divider -> DividerProjector(divider, presenter.divider) }
            itemsIndexed(interactor.items) { index, item ->
                CompositionLocalProvider(
                    LocalLayoutOrientation provides LayoutOrientation.VERTICAL
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