package com.bselzer.ktx.compose.ui.layout.lazycolumn

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.divider.DividerProjection
import com.bselzer.ktx.compose.ui.layout.modifier.then
import com.bselzer.ktx.compose.ui.layout.project.Projector

class LazyColumnProjection<T>(
    override val logic: LazyColumnLogic<T>,
    override val presentation: LazyColumnPresentation = LazyColumnPresentation.Default
) : Projector<LazyColumnLogic<T>, LazyColumnPresentation>() {
    private val dividerProjection = logic.divider?.let { divider -> DividerProjection(divider, presentation.divider) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable LazyItemScope.(Int, T) -> Unit
    ) = contextualize {
        LazyColumn(
            modifier = modifier.then(logic.modifiers),
            state = rememberSaveable(saver = LazyListState.Saver) { logic.state },
            contentPadding = contentPadding,
            reverseLayout = reverseLayout.toBoolean(),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
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