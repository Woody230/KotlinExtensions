package com.bselzer.ktx.compose.ui.dropdown

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.style.ModifiableStyle

actual data class DropdownStyle(
    override val modifier: Modifier = Modifier,

    /**
     * An offset from the original aligned position of the popup. Offset respects the
     * Ltr/Rtl context, thus in Ltr it will be added to the original aligned position and in Rtl it
     * will be subtracted from it.
     */
    val offset: DpOffset = DpOffset(0.dp, 0.dp),

    /**
     * [PopupProperties] for further customization of this popup's behavior.
     */
    val properties: PopupProperties = PopupProperties()
): ModifiableStyle

