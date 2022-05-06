package com.bselzer.ktx.compose.ui.layout.icon

import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class IconPresentation(
    /**
     * Tint to be applied to imageVector. If Color.Unspecified is provided, then no tint is applied
     */
    val tint: Color = ComposeMerger.color.default
) : Presenter<IconPresentation>() {
    @Composable
    override fun safeMerge(other: IconPresentation) = IconPresentation(
        tint = ComposeMerger.color.safeMerge(tint, other.tint)
    )

    @Composable
    override fun createLocalization() = IconPresentation(
        tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
    )
}