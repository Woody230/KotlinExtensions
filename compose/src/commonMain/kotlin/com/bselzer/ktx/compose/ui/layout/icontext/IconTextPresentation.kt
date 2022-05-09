package com.bselzer.ktx.compose.ui.layout.icontext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.icon.IconPresentation
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.row.RowPresentation
import com.bselzer.ktx.compose.ui.layout.text.TextPresentation

data class IconTextPresentation(
    /**
     * The [PresentationModel] of the container holding the icon and text.
     */
    val container: RowPresentation = RowPresentation.Default,

    /**
     * The [PresentationModel] of the icon.
     */
    val icon: IconPresentation = IconPresentation.Default,

    /**
     * The [PresentationModel] of the text.
     */
    val text: TextPresentation = TextPresentation.Default,
) : Presenter<IconTextPresentation>() {
    companion object {
        @Stable
        val Default = IconTextPresentation()
    }

    override fun safeMerge(other: IconTextPresentation) = IconTextPresentation(
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