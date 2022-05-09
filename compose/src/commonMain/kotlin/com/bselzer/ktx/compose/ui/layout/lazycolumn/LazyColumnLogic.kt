package com.bselzer.ktx.compose.ui.layout.lazycolumn

import androidx.compose.foundation.lazy.LazyListState
import com.bselzer.ktx.compose.ui.layout.divider.DividerLogic
import com.bselzer.ktx.compose.ui.layout.modifier.Modifiable
import com.bselzer.ktx.compose.ui.layout.modifier.Modifiables
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class LazyColumnLogic<T>(
    /**
     * The [LogicModel] of the divider.
     */
    val divider: DividerLogic? = null,

    /**
     * The [Modifiable] components.
     */
    val modifiers: Modifiables? = null,

    /**
     * The state object to be used to control or observe the list's state.
     */
    val state: LazyListState = LazyListState(),

    /**
     * The items.
     */
    val items: List<T>
) : LogicModel