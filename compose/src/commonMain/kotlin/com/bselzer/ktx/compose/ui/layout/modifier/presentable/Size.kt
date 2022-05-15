package com.bselzer.ktx.compose.ui.layout.modifier.presentable

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

sealed interface Size : PresentableModifier

data class ModularSize(
    val width: WidthConstraint = PreferredWidth.Default,
    val height: HeightConstraint = PreferredHeight.Default
) : Size {
    constructor(width: Dp, height: Dp) : this(PreferredWidth(width), PreferredHeight(height))

    companion object {
        @Stable
        val Default = ModularSize()

        @Stable
        val FillSize = ModularSize(width = FilledWidth(), height = FilledHeight())

        @Stable
        val FillWidth = ModularSize(width = FilledWidth())

        @Stable
        val FillHeight = ModularSize(height = FilledHeight())

        @Stable
        val WrapSize = ModularSize(width = WrappedWidth(), height = WrappedHeight())

        @Stable
        val WrapWidth = ModularSize(width = WrappedWidth())

        @Stable
        val WrapHeight = ModularSize(height = WrappedHeight())
    }

    override val modifier: Modifier = Modifier
        .then(width.modifier)
        .then(height.modifier)
}

data class MatchParent(
    val scope: BoxScope
) : Size {
    override val modifier: Modifier = with(scope) { Modifier.matchParentSize() }
}