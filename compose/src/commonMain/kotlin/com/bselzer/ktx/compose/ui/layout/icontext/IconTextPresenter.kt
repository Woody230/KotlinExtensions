package com.bselzer.ktx.compose.ui.layout.icontext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.icon.IconPresenter
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.row.RowPresenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class IconTextPresenter(
    /**
     * The [Presentable] of the container holding the icon and text.
     */
    val container: RowPresenter = RowPresenter.Default,

    /**
     * The [Presentable] of the icon.
     */
    val icon: IconPresenter = IconPresenter.Default,

    /**
     * The [Presentable] of the text.
     */
    val text: TextPresenter = TextPresenter.Default,
) : Presenter<IconTextPresenter>() {
    companion object {
        @Stable
        val Default = IconTextPresenter()
    }

    override fun safeMerge(other: IconTextPresenter) = IconTextPresenter(
        container = container.merge(other.container),
        icon = icon.merge(other.icon),
        text = text.merge(other.text)
    )

    @Composable
    override fun localized() = copy(
        container = container.localized(),
        icon = icon.localized(),
        text = text.localized()
    )
}