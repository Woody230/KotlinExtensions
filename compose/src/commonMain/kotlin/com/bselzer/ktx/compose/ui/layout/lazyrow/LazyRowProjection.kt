package com.bselzer.ktx.compose.ui.layout.lazyrow

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class LazyRowProjection(
    override val logic: LazyRowLogic = LazyRowLogic(),
    override val presentation: LazyRowPresentation = LazyRowPresentation()
) : Projector<LazyRowLogic, LazyRowPresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: LazyListScope.() -> Unit
    ) = contextualize {
        LazyRow(
            modifier = modifier,
            state = rememberSaveable(saver = LazyListState.Saver) { logic.state },
            contentPadding = contentPadding,
            reverseLayout = reverseLayout.toBoolean(),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            flingBehavior = flingBehavior,
            content = content
        )
    }
}