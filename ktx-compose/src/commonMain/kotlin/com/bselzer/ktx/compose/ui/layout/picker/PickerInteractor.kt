package com.bselzer.ktx.compose.ui.layout.picker

import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

abstract class PickerInteractor<T>(
    override val modifier: InteractableModifier
) : Interactor(modifier) {
    /**
     * The index of the selected value.
     */
    abstract val selectedIndex: Int

    /**
     * The range of values.
     */
    abstract val range: IntRange?

    /**
     * Gets the value associated with the index.
     */
    abstract val getValue: (Int) -> T?

    /**
     * Converts the value into a displayable label.
     */
    abstract val getLabel: (T) -> String

    /**
     * The callback for persisting a selection.
     */
    abstract val onSelectionChanged: (T) -> Unit

    /**
     * The [Interactor] for the up-directional icon.
     */
    abstract val upIcon: IconInteractor

    /**
     * The [Interactor] for the down-directional icon.
     */
    abstract val downIcon: IconInteractor
}