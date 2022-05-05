package com.bselzer.ktx.compose.ui.layout.lazycolumn

import androidx.compose.foundation.lazy.LazyListState
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class LazyColumnLogic(
    /**
     * The state object to be used to control or observe the list's state.
     */
    val state: LazyListState = LazyListState()
) : LogicModel