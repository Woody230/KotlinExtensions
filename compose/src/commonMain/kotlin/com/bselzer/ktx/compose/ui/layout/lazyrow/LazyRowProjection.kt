package com.bselzer.ktx.compose.ui.layout.lazyrow

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.divider.DividerProjection
import com.bselzer.ktx.compose.ui.layout.project.Projector

class LazyRowProjection<T>(
    override val logic: LazyRowLogic<T>,
    override val presentation: LazyRowPresentation = LazyRowPresentation.Default
) : Projector<LazyRowLogic<T>, LazyRowPresentation>() {
    private val dividerProjection = logic.divider?.let { divider -> DividerProjection(divider, presentation.divider) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable LazyItemScope.(Int, T) -> Unit
    ) = contextualize {
        LazyRow(
            modifier = modifier,
            state = rememberSaveable(saver = LazyListState.Saver) { logic.state },
            contentPadding = contentPadding,
            reverseLayout = reverseLayout.toBoolean(),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            flingBehavior = flingBehavior,
        ) {
            itemsIndexed(logic.items) { index, item ->
                val isFirst = index == 0
                val shouldPrepend = isFirst && prepend.toBoolean()
                if (shouldPrepend) {
                    dividerProjection?.project()
                }

                content(index, item)

                val isLast = index == logic.items.lastIndex
                val isIntermediate = !isFirst && !isLast
                val shouldAppend = isLast && append.toBoolean()
                if (isIntermediate || shouldAppend) {
                    dividerProjection?.project()
                }
            }
        }
    }
}