package com.bselzer.ktx.compose.ui.dropdown

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import com.bselzer.ktx.compose.ui.layout.merge.DefaultDpOffset
import com.bselzer.ktx.compose.ui.layout.merge.merge
import com.bselzer.ktx.compose.ui.style.ModifierStyle
import com.bselzer.ktx.function.objects.safeMerge

actual data class DropdownStyle(
    override val modifier: Modifier = Modifier,

    /**
     * [DpOffset] to be added to the position of the menu
     */
    val offset: DpOffset = DefaultDpOffset,

    /**
     * Whether the dropdown is focusable.
     */
    val focusable: Boolean = true
) : ModifierStyle<DropdownStyle>() {
    actual constructor(modifier: Modifier) : this(modifier = modifier, offset = DefaultDpOffset)

    override fun safeMerge(other: DropdownStyle): DropdownStyle = DropdownStyle(
        modifier = modifier.then(other.modifier),
        offset = offset.merge(other.offset),
        focusable = focusable.safeMerge(other.focusable, true)
    )

    override fun modify(modifier: Modifier): DropdownStyle = copy(modifier = modifier)
}