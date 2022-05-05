package com.bselzer.ktx.compose.ui.layout.picker

import com.bselzer.ktx.compose.ui.layout.icon.IconLogic
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

interface PickerLogic<T> : LogicModel {
    /**
     * The index of the selected value.
     */
    val selectedIndex: Int

    /**
     * The range of values.
     */
    val range: IntRange?

    /**
     * Gets the value associated with the index.
     */
    val getValue: (Int) -> T?

    /**
     * Converts the value into a displayable label.
     */
    val getLabel: (T) -> String

    /**
     * The callback for persisting a selection.
     */
    val onSelectionChanged: (T) -> Unit

    /**
     * The [LogicModel] for the up-directional icon.
     */
    val upIcon: IconLogic

    /**
     * The [LogicModel] for the down-directional icon.
     */
    val downIcon: IconLogic
}