package com.bselzer.ktx.compose.ui.layout.drawer.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.column.ColumnPresenter
import com.bselzer.ktx.compose.ui.layout.drawer.component.DrawerComponentPresenter
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.DefaultMinHeight
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.ModularSize
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class DrawerSectionPresenter(
    override val modifier: PresentableModifier = PresentableModifier,
    val container: ColumnPresenter = ColumnPresenter.Default,
    val title: TextPresenter = TextPresenter.Default,
    val component: DrawerComponentPresenter = DrawerComponentPresenter.Default
) : Presenter<DrawerSectionPresenter>(modifier) {
    companion object {
        @Stable
        val Default = DrawerSectionPresenter()
    }

    override fun safeMerge(other: DrawerSectionPresenter) = DrawerSectionPresenter(
        modifier = modifier.merge(other.modifier),
        container = container.merge(other.container),
        title = title.merge(other.title),
        component = component.merge(other.component)
    )

    @Composable
    override fun localized() = DrawerSectionPresenter(
        container = ColumnPresenter(
            modifier = ModularSize.FillWidth then ModularSize(height = DefaultMinHeight(48.dp)),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ),
        title = TextPresenter(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textStyle = MaterialTheme.typography.body2
        )
    ).merge(this).run {
        copy(
            container = container.localized(),
            title = title.localized(),
            component = component.localized()
        )
    }
}