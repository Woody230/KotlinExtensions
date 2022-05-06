package com.bselzer.ktx.compose.ui.layout.lazyrow

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

class LazyRowLogic(
    /**
     * The state object to be used to control or observe the list's state.
     */
    val state: LazyListState = LazyListState()
) : LogicModel {
    companion object {
        @Stable
        val Default = LazyRowLogic()
    }
}