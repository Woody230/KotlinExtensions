package com.bselzer.ktx.compose.ui.layout.modifier.presentable

import androidx.compose.ui.Modifier

internal data class CombinedPresentableModifier(
    val outer: PresentableModifier,
    val inner: PresentableModifier
) : PresentableModifier {
    override val modifier: Modifier = outer.modifier.then(inner.modifier)
}