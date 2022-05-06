package com.bselzer.ktx.compose.ui.layout.lazycolumn

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class LazyColumnProjection(
    override val logic: LazyColumnLogic = LazyColumnLogic(),
    override val presentation: LazyColumnPresentation = LazyColumnPresentation()
) : Projector<LazyColumnLogic, LazyColumnPresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: LazyListScope.() -> Unit
    ) = contextualize {
        LazyColumn(
            modifier = modifier,
            state = rememberSaveable(saver = LazyListState.Saver) { logic.state },
            contentPadding = contentPadding,
            reverseLayout = reverseLayout.toBoolean(),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            content = content
        )
    }
}