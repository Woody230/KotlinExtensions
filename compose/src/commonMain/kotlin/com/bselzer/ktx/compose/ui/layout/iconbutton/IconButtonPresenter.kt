package com.bselzer.ktx.compose.ui.layout.iconbutton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.icon.IconPresenter
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class IconButtonPresenter(
    /**
     * The [Presentable] for the icon.
     */
    val icon: IconPresenter = IconPresenter()
) : Presenter<IconButtonPresenter>() {
    companion object {
        @Stable
        val Default = IconButtonPresenter()
    }

    override fun safeMerge(other: IconButtonPresenter) = IconButtonPresenter(
        icon = icon.merge(other.icon)
    )

    @Composable
    override fun localized() = copy(icon = icon.localized())
}