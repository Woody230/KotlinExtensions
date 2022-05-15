package com.bselzer.ktx.compose.ui.layout.icon

import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class IconPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * Tint to be applied to imageVector. If Color.Unspecified is provided, then no tint is applied
     */
    val tint: Color = ComposeMerger.color.default
) : Presenter<IconPresenter>(modifier) {
    companion object {
        @Stable
        val Default = IconPresenter()
    }

    override fun safeMerge(other: IconPresenter) = IconPresenter(
        modifier = modifier.merge(other.modifier),
        tint = ComposeMerger.color.safeMerge(tint, other.tint)
    )

    @Composable
    override fun localized() = IconPresenter(
        tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
    ).merge(this)
}