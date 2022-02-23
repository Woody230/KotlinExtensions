package com.bselzer.ktx.compose.ui.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import com.bselzer.ktx.compose.ui.style.DefaultDpOffset
import com.bselzer.ktx.compose.ui.style.ModifiableStyle
import com.bselzer.ktx.compose.ui.style.merge
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
): ModifiableStyle<DropdownStyle> {
    actual constructor(modifier: Modifier): this(modifier = modifier, offset = DefaultDpOffset)

    override fun merge(other: DropdownStyle?): DropdownStyle = if (other == null) this else DropdownStyle(
        modifier = modifier.then(other.modifier),
        offset = offset.merge(other.offset),
        focusable = focusable.safeMerge(other.focusable, true)
    )

    @Composable
    override fun localized(): DropdownStyle = this
}