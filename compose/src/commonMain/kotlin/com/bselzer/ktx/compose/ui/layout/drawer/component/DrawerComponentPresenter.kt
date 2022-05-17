package com.bselzer.ktx.compose.ui.layout.drawer.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.icon.IconPresenter
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.ModularPadding
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.row.RowPresenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class DrawerComponentPresenter(
    override val modifier: PresentableModifier = PresentableModifier,
    val container: RowPresenter = RowPresenter.Default,
    val icon: IconPresenter = IconPresenter.Default,
    val text: TextPresenter = TextPresenter.Default,
) : Presenter<DrawerComponentPresenter>(modifier) {
    companion object {
        @Stable
        val Default = DrawerComponentPresenter()
    }

    override fun safeMerge(other: DrawerComponentPresenter) = DrawerComponentPresenter(
        modifier = modifier.merge(other.modifier),
        container = container.merge(other.container),
        icon = icon.merge(other.icon),
        text = text.merge(other.text)
    )

    @Composable
    override fun localized() = DrawerComponentPresenter(
        container = RowPresenter(
            modifier = ModularPadding(start = 16.dp, end = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ),
        text = TextPresenter(
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textStyle = MaterialTheme.typography.body2
        )
    ).merge(this).run {
        copy(
            container = container.localized(),
            icon = icon.localized(),
            text = text.localized(),
        )
    }
}