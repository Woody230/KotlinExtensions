package com.bselzer.ktx.compose.ui.layout.modifier

import androidx.compose.ui.Modifier

data class InteractableModifiers(
    val clickable: Clickable? = null,
    val horizontalScroll: HorizontalScroll? = null,
    val verticalScroll: VerticalScroll? = null
) : Modifiable {
    override val modifier: Modifier = Modifier
        .then(clickable)
        .then(horizontalScroll)
        .then(verticalScroll)
}