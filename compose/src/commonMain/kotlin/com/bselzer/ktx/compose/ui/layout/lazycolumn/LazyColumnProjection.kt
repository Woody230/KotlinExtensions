package com.bselzer.ktx.compose.ui.layout.lazycolumn

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Projectable

class LazyColumnProjection(
    override val logic: LazyColumnLogic = LazyColumnLogic(),
    override val presentation: LazyColumnPresentation = LazyColumnPresentation()
) : Projectable<LazyColumnLogic, LazyColumnPresentation> {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: LazyListScope.() -> Unit
    ) = LazyColumn(
        modifier = modifier,
        state = rememberSaveable(saver = LazyListState.Saver) { logic.state },
        contentPadding = presentation.contentPadding,
        reverseLayout = presentation.reverseLayout,
        verticalArrangement = presentation.verticalArrangement,
        horizontalAlignment = presentation.horizontalAlignment,
        flingBehavior = ComposeMerger.flingBehavior.resolve(presentation.flingBehavior, ScrollableDefaults.flingBehavior()),
        content = content
    )
}