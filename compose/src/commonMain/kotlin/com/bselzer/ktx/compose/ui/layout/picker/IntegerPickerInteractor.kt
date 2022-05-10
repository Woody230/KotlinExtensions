package com.bselzer.ktx.compose.ui.layout.picker

import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor

data class IntegerPickerInteractor(
    /**
     * The selected number.
     */
    val selected: Int,

    override val range: IntRange,
    override val onSelectionChanged: (Int) -> Unit,
    override val upIcon: IconInteractor,
    override val downIcon: IconInteractor
) : PickerInteractor<Int> {
    override val selectedIndex: Int = range.toList().binarySearch(selected)
    override val getValue: (Int) -> Int? = { index -> range.elementAtOrNull(index) }
    override val getLabel: (Int) -> String = { number -> number.toString() }
}