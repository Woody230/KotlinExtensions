package com.bselzer.ktx.compose.ui.dropdown

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import com.bselzer.ktx.compose.ui.style.ModifiableStyle
import com.bselzer.ktx.function.objects.nullMerge

actual data class DropdownStyle(
    override val modifier: Modifier = Modifier,

    /**
     * [DpOffset] to be added to the position of the menu
     */
    val offset: DpOffset? = null,

    /**
     * Whether the dropdown is focusable.
     */
    val focusable: Boolean? = null
): ModifiableStyle<DropdownStyle> {
    actual constructor(modifier: Modifier): this(modifier = modifier, offset = null)

    override fun merge(other: DropdownStyle?): DropdownStyle = if (other == null) this else DropdownStyle(
        modifier = modifier.then(other.modifier),
        offset = offset.nullMerge(other.offset),
        focusable = focusable.nullMerge(other.focusable)
    )
}