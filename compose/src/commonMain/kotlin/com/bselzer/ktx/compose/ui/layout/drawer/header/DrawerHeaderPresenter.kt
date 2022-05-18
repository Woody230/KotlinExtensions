package com.bselzer.ktx.compose.ui.layout.drawer.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.column.ColumnPresenter
import com.bselzer.ktx.compose.ui.layout.description.DescriptionPresenter
import com.bselzer.ktx.compose.ui.layout.image.ImagePresenter
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.ModularPadding
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.ModularSize
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class DrawerHeaderPresenter(
    override val modifier: PresentableModifier = PresentableModifier,
    val container: ColumnPresenter = ColumnPresenter.Default,
    val image: ImagePresenter = ImagePresenter.Default,
    val description: DescriptionPresenter = DescriptionPresenter.Default,
) : Presenter<DrawerHeaderPresenter>(modifier) {
    companion object {
        @Stable
        val Default = DrawerHeaderPresenter()
    }

    override fun safeMerge(other: DrawerHeaderPresenter) = DrawerHeaderPresenter(
        modifier = modifier.merge(other.modifier),
        container = container.merge(other.container),
        image = image.merge(other.image),
        description = description.merge(other.description)
    )

    @Composable
    override fun localized() = DrawerHeaderPresenter(
        container = ColumnPresenter(
            modifier = ModularSize.FillWidth then ModularPadding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
    ).merge(this).copy(
        container = container.localized(),
        image = image.localized(),
        description = description.localized(),
    )
}