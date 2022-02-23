package com.bselzer.ktx.compose.ui.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import com.bselzer.ktx.compose.ui.style.DefaultDpOffset
import com.bselzer.ktx.compose.ui.style.ModifiableStyle
import com.bselzer.ktx.compose.ui.style.localized
import com.bselzer.ktx.compose.ui.style.merge

actual data class DropdownStyle(
    override val modifier: Modifier = Modifier,

    /**
     * An offset from the original aligned position of the popup. Offset respects the
     * Ltr/Rtl context, thus in Ltr it will be added to the original aligned position and in Rtl it
     * will be subtracted from it.
     */
    val offset: DpOffset = DefaultDpOffset,

    /**
     * [PopupProperties] for further customization of this popup's behavior.
     */
    val properties: PopupProperties = PopupProperties()
): ModifiableStyle<DropdownStyle> {
    actual constructor(modifier: Modifier): this(modifier = modifier, offset = DefaultDpOffset)

    override fun merge(other: DropdownStyle?): DropdownStyle = if (other == null) this else DropdownStyle(
        modifier = modifier.then(other.modifier),
        offset = offset.merge(other.offset),
        properties = properties.merge(other.properties)
    )

    @Composable
    override fun localized() = DropdownStyle(properties = LocalPopupStyle.localized())
}