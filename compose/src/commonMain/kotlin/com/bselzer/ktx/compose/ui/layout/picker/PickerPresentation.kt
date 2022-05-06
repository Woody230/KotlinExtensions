package com.bselzer.ktx.compose.ui.layout.picker

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.icon.IconPresentation
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class PickerPresentation(
    /**
     * The style of the text for displaying the value.
     */
    val textStyle: TextStyle = TextStyle.Default,

    /**
     * The offset of the new value within the scrolling animation.
     */
    val animationOffset: Dp = ComposeMerger.dp.default,

    /**
     * The [PresentationModel] for the up-directional icon.
     */
    val upIcon: IconPresentation = IconPresentation.Default,

    /**
     * The [PresentationModel] for the down-directional icon.
     */
    val downIcon: IconPresentation = IconPresentation.Default
) : Presenter<PickerPresentation>() {
    companion object {
        @Stable
        val Default = PickerPresentation()
    }

    @Composable
    override fun safeMerge(other: PickerPresentation) = PickerPresentation(
        textStyle = textStyle.merge(other.textStyle),
        animationOffset = ComposeMerger.dp.safeMerge(animationOffset, other.animationOffset),
        upIcon = upIcon.merge(other.upIcon),
        downIcon = downIcon.merge(other.downIcon)
    )

    @Composable
    override fun createLocalization() = PickerPresentation(
        textStyle = LocalTextStyle.current,
        animationOffset = 18.dp,
        upIcon = upIcon.localized(),
        downIcon = downIcon.localized()
    )
}