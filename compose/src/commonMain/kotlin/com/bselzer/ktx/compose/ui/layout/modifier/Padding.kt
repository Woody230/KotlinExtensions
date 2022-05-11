package com.bselzer.ktx.compose.ui.layout.modifier

import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed interface Padding : Modifiable

data class ModularPadding(
    val start: Dp = 0.dp,
    val top: Dp = 0.dp,
    val end: Dp = 0.dp,
    val bottom: Dp = 0.dp,
    val type: PaddingType = PaddingType.RELATIVE
) : Padding {
    companion object {
        @Stable
        val Default = ModularPadding()
    }

    constructor(
        all: Dp,
        type: PaddingType = PaddingType.RELATIVE
    ) : this(
        start = all,
        top = all,
        end = all,
        bottom = all,
        type = type
    )

    constructor(
        horizontal: Dp = 0.dp,
        vertical: Dp = 0.dp,
        type: PaddingType = PaddingType.RELATIVE
    ) : this(
        start = horizontal,
        top = vertical,
        end = horizontal,
        bottom = vertical,
        type = type
    )

    override val modifier: Modifier = when (type) {
        PaddingType.ABSOLUTE -> Modifier.absolutePadding(
            left = start,
            top = top,
            right = end,
            bottom = bottom
        )
        PaddingType.RELATIVE -> Modifier.padding(
            start = start,
            top = top,
            end = end,
            bottom = bottom
        )
    }
}

enum class PaddingType {
    RELATIVE,
    ABSOLUTE
}

