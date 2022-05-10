package com.bselzer.ktx.compose.ui.layout.picker

import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor

data class ValuePickerInteractor<T>(
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
    override val upIcon: IconInteractor,
    override val downIcon: IconInteractor
) : PickerInteractor<T> {
    override val selectedIndex: Int = values.indexOfFirst { value -> value == selected }
    override val getValue: (Int) -> T? = { index -> values.getOrNull(index) }
    override val range: IntRange = values.indices
}