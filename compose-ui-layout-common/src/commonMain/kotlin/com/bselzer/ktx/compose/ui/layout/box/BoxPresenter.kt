package com.bselzer.ktx.compose.ui.layout.box

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.TriState
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class BoxPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * The default alignment inside the Box.
     */
    val contentAlignment: Alignment = ComposeMerger.alignment.default,

    /**
     * Whether the incoming min constraints should be passed to content.
     */
    val propagateMinConstraints: TriState = ComposeMerger.triState.default
) : Presenter<BoxPresenter>(modifier) {
    companion object {
        @Stable
        val Default = BoxPresenter()

        @Stable
        val Centered = BoxPresenter(contentAlignment = Alignment.Center)
    }

    override fun safeMerge(other: BoxPresenter) = BoxPresenter(
        modifier = modifier.merge(other.modifier),
        contentAlignment = ComposeMerger.alignment.safeMerge(contentAlignment, other.contentAlignment),
        propagateMinConstraints = ComposeMerger.triState.safeMerge(propagateMinConstraints, other.propagateMinConstraints)
    )

    @Composable
    override fun localized() = BoxPresenter(
        contentAlignment = Alignment.TopStart,
        propagateMinConstraints = TriState.TRUE
    ).merge(this)
}