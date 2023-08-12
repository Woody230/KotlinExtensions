package com.bselzer.ktx.compose.ui.layout.modifier.presentable

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

sealed interface HeightConstraint : PresentableModifier

data class PreferredHeight(
    val min: Dp = Dp.Unspecified,
    val max: Dp = Dp.Unspecified,
) : HeightConstraint {
    constructor(size: Dp) : this(min = size, max = size)

    companion object {
        @Stable
        val Default = PreferredHeight()
    }

    override val modifier: Modifier = Modifier.heightIn(min = min, max = max)
}

data class RequiredHeight(
    val min: Dp = Dp.Unspecified,
    val max: Dp = Dp.Unspecified,
) : HeightConstraint {
    constructor(size: Dp) : this(min = size, max = size)

    override val modifier: Modifier = Modifier.requiredHeightIn(min = min, max = max)
}

data class FilledHeight(
    val fraction: Float = 1f
) : HeightConstraint {
    override val modifier: Modifier = Modifier.fillMaxHeight(fraction = fraction)
}

data class WrappedHeight(
    val align: Alignment.Vertical = Alignment.CenterVertically,
    val unbounded: Boolean = false
) : HeightConstraint {
    override val modifier: Modifier = Modifier.wrapContentHeight(align = align, unbounded = unbounded)
}

data class DefaultMinHeight(
    val min: Dp
) : HeightConstraint {
    override val modifier: Modifier = Modifier.defaultMinSize(minHeight = min)
}