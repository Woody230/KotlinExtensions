package com.bselzer.ktx.compose.ui.layout.modifier.presentable

import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed interface Offset : PresentableModifier

data class ModularOffset(
    val x: Dp = 0.dp,
    val y: Dp = 0.dp,
    val type: OffsetType = OffsetType.RELATIVE
) : Offset {
    companion object {
        @Stable
        val Default = ModularOffset()
    }

    override val modifier: Modifier = when (type) {
        OffsetType.ABSOLUTE -> Modifier.absoluteOffset(
            x = x,
            y = y
        )
        OffsetType.RELATIVE -> Modifier.offset(
            x = x,
            y = y
        )
    }
}

enum class OffsetType {
    RELATIVE,
    ABSOLUTE
}