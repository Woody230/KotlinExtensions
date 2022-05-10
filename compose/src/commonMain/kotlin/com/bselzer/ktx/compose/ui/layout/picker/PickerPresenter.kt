package com.bselzer.ktx.compose.ui.layout.picker

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.icon.IconPresenter
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class PickerPresenter(
    /**
     * The style of the text for displaying the value.
     */
    val textStyle: TextStyle = TextStyle.Default,

    /**
     * The offset of the new value within the scrolling animation.
     */
    val animationOffset: Dp = ComposeMerger.dp.default,

    /**
     * The [Presentable] for the up-directional icon.
     */
    val upIcon: IconPresenter = IconPresenter.Default,

    /**
     * The [Presentable] for the down-directional icon.
     */
    val downIcon: IconPresenter = IconPresenter.Default
) : Presenter<PickerPresenter>() {
    companion object {
        @Stable
        val Default = PickerPresenter()
    }

    override fun safeMerge(other: PickerPresenter) = PickerPresenter(
        textStyle = textStyle.merge(other.textStyle),
        animationOffset = ComposeMerger.dp.safeMerge(animationOffset, other.animationOffset),
        upIcon = upIcon.merge(other.upIcon),
        downIcon = downIcon.merge(other.downIcon)
    )

    @Composable
    override fun localized() = PickerPresenter(
        textStyle = LocalTextStyle.current,
        animationOffset = 18.dp
    ).merge(this).run {
        copy(upIcon = upIcon.localized(), downIcon = downIcon.localized())
    }
}