package com.bselzer.ktx.compose.ui.layout.modifier

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

sealed interface Size : Modifiable

data class ModularSize(
    val width: WidthConstraint = PreferredWidth.Default,
    val height: HeightConstraint = PreferredHeight.Default
) : Size {
    constructor(width: Dp, height: Dp) : this(PreferredWidth(width), PreferredHeight(height))

    companion object {
        @Stable
        val Default = ModularSize()

        @Stable
        val Filled = ModularSize(FilledWidth(), FilledHeight())

        @Stable
        val Wrapped = ModularSize(WrappedWidth(), WrappedHeight())
    }

    override val modifier: Modifier = Modifier
        .then(width)
        .then(height)
}

data class MatchParent(
    val scope: BoxScope
) : Size {
    override val modifier: Modifier = with(scope) { Modifier.matchParentSize() }
}