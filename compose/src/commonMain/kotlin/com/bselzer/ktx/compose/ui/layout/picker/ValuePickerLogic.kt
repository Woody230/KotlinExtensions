package com.bselzer.ktx.compose.ui.layout.picker

import com.bselzer.ktx.compose.ui.layout.icon.IconLogic

data class ValuePickerLogic<T>(
    /**
     * The selected value.
     */
    val selected: T,

    /**
     * The values to select from.
     */
    val values: List<T>,

    override val getLabel: (T) -> String,
    override val onSelectionChanged: (T) -> Unit,
    override val upIcon: IconLogic,
    override val downIcon: IconLogic
) : PickerLogic<T> {
    override val selectedIndex: Int = values.indexOfFirst { value -> value == selected }
    override val getValue: (Int) -> T? = { index -> values.getOrNull(index) }
    override val range: IntRange = values.indices
}