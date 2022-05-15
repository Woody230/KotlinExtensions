package com.bselzer.ktx.compose.ui.layout.modifier.presentable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

sealed interface WidthConstraint : PresentableModifier

data class PreferredWidth(
    val min: Dp = Dp.Unspecified,
    val max: Dp = Dp.Unspecified,
) : WidthConstraint {
    constructor(size: Dp) : this(min = size, max = size)

    companion object {
        @Stable
        val Default = PreferredWidth()
    }

    override val modifier: Modifier = Modifier.widthIn(min = min, max = max)
}

data class RequiredWidth(
    val min: Dp = Dp.Unspecified,
    val max: Dp = Dp.Unspecified,
) : WidthConstraint {
    constructor(size: Dp) : this(min = size, max = size)

    override val modifier: Modifier = Modifier.requiredWidthIn(min = min, max = max)
}

data class FilledWidth(
    val fraction: Float = 1f
) : WidthConstraint {
    override val modifier: Modifier = Modifier.fillMaxWidth(fraction = fraction)
}

data class WrappedWidth(
    val align: Alignment.Horizontal = Alignment.CenterHorizontally,
    val unbounded: Boolean = false
) : WidthConstraint {
    override val modifier: Modifier = Modifier.wrapContentWidth(align = align, unbounded = unbounded)
}