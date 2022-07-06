package com.bselzer.ktx.compose.ui.layout.lazyrow

import androidx.compose.foundation.lazy.LazyListState
import com.bselzer.ktx.compose.ui.layout.divider.DividerInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

class LazyRowInteractor<T>(
    override val modifier: InteractableModifier = InteractableModifier,

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
) : Interactor(modifier)