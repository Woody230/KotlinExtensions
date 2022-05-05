package com.bselzer.ktx.compose.ui.layout.lazyrow

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Projectable

class LazyRowProjection(
    override val logic: LazyRowLogic = LazyRowLogic(),
    override val presentation: LazyRowPresentation = LazyRowPresentation()
) : Projectable<LazyRowLogic, LazyRowPresentation> {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: LazyListScope.() -> Unit
    ) = LazyRow(
        modifier = modifier,
        state = rememberSaveable(saver = LazyListState.Saver) { logic.state },
        contentPadding = presentation.contentPadding,
        reverseLayout = presentation.reverseLayout,
        horizontalArrangement = presentation.horizontalArrangement,
        verticalAlignment = presentation.verticalAlignment,
        flingBehavior = ComposeMerger.flingBehavior.safeTake(presentation.flingBehavior, ScrollableDefaults.flingBehavior()),
        content = content
    )
}