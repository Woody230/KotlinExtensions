package com.bselzer.ktx.compose.ui.layout.modifier

import androidx.compose.ui.Modifier

data class PresentableModifiers(
    val size: Size? = null
) : Modifiable {
    override val modifier: Modifier = Modifier.then(size)
}