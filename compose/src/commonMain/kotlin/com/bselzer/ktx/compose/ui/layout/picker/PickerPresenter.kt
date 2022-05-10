package com.bselzer.ktx.compose.ui.layout.picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.icon.IconPresenter
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class PickerPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     * The [Presentable] for the text.
     */
    val text: TextPresenter = TextPresenter.Default,

    /**
     * The [Presentable] for the up and down icons.
     */
    val icon: IconPresenter = IconPresenter.Default,

    /**
     * The offset of the new value within the scrolling animation.
     */
    val animationOffset: Dp = ComposeMerger.dp.default,
) : Presenter<PickerPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = PickerPresenter()
    }

    override fun safeMerge(other: PickerPresenter) = PickerPresenter(
        modifiers = modifiers.merge(other.modifiers),
        text = text.merge(other.text),
        icon = icon.merge(other.icon),
        animationOffset = ComposeMerger.dp.safeMerge(animationOffset, other.animationOffset),
    )

    @Composable
    override fun localized() = PickerPresenter(
        animationOffset = 18.dp
    ).merge(this).run {
        copy(
            text = text.localized(),
            icon = icon.localized()
        )
    }
}