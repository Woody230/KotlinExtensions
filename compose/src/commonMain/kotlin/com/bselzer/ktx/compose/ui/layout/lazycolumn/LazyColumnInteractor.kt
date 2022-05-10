package com.bselzer.ktx.compose.ui.layout.lazycolumn

import androidx.compose.foundation.lazy.LazyListState
import com.bselzer.ktx.compose.ui.layout.divider.DividerInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.modifier.Modifiable
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class LazyColumnInteractor<T>(
    /**
     * The [Modifiable] components.
     */
    val modifiers: InteractableModifiers? = null,

    /**
     * The [Interactor] of the divider.
     */
    val divider: DividerInteractor? = null,

    /**
     * The state object to be used to control or observe the list's state.
     */
    val state: LazyListState = LazyListState(),

    /**
     * The items.
     */
    val items: List<T>
) : Interactor()