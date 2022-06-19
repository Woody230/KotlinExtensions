package com.bselzer.ktx.compose.ui.layout.iconbutton

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.icon.IconPresenter
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class IconButtonPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * The [Presentable] for the icon.
     */
    val icon: IconPresenter = IconPresenter.Default
) : Presenter<IconButtonPresenter>(modifier) {
    companion object {
        @Stable
        val Default = IconButtonPresenter()
    }

    override fun safeMerge(other: IconButtonPresenter) = IconButtonPresenter(
        modifier = modifier.merge(other.modifier),
        icon = icon.merge(other.icon)
    )
}