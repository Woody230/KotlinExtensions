package com.bselzer.ktx.compose.ui.layout.progress.indicator

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class CircularIndicatorPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * The color of the progress indicator.
     */
    val color: Color = ComposeMerger.color.default,

    /**
     * The stroke width for the progress indicator.
     */
    val strokeWidth: Dp = ComposeMerger.dp.default
) : Presenter<CircularIndicatorPresenter>(modifier), ProgressIndicatorPresenter<CircularIndicatorPresenter> {
    companion object {
        @Stable
        val Default = CircularIndicatorPresenter()
    }

    override fun safeMerge(other: CircularIndicatorPresenter) = CircularIndicatorPresenter(
        modifier = modifier.merge(other.modifier),
        color = ComposeMerger.color.safeMerge(color, other.color),
        strokeWidth = ComposeMerger.dp.safeMerge(strokeWidth, other.strokeWidth)
    )

    @Composable
    override fun localized() = CircularIndicatorPresenter(
        color = MaterialTheme.colors.primary,
        strokeWidth = ProgressIndicatorDefaults.StrokeWidth
    ).merge(this)
}