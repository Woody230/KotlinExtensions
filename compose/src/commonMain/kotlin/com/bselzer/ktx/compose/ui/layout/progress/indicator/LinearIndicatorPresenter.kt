package com.bselzer.ktx.compose.ui.layout.progress.indicator

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class LinearIndicatorPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * The color of the progress indicator.
     */
    val color: Color = ComposeMerger.color.default,

    /**
     * The color of the background behind the indicator, visible when the progress has not reached that area of the overall indicator yet.
     */
    val backgroundColor: Color = ComposeMerger.color.default,
) : Presenter<LinearIndicatorPresenter>(modifier), ProgressIndicatorPresenter<LinearIndicatorPresenter> {
    companion object {
        @Stable
        val Default = LinearIndicatorPresenter()
    }

    override fun safeMerge(other: LinearIndicatorPresenter) = LinearIndicatorPresenter(
        modifier = modifier.merge(other.modifier),
        color = ComposeMerger.color.safeMerge(color, other.color),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor)
    )

    @Composable
    override fun localized() = LinearIndicatorPresenter(
        color = MaterialTheme.colors.primary
    ).merge(this).run {
        if (ComposeMerger.color.isDefault(backgroundColor)) {
            copy(color = color.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity))
        } else {
            this
        }
    }
}