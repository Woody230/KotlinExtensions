package com.bselzer.ktx.compose.ui.layout.box

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.TriState
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class BoxPresentation(
    /**
     * The default alignment inside the Box.
     */
    val contentAlignment: Alignment = ComposeMerger.alignment.default,

    /**
     * Whether the incoming min constraints should be passed to content.
     */
    val propagateMinConstraints: TriState = ComposeMerger.triState.default
) : Presenter<BoxPresentation>() {
    @Composable
    override fun safeMerge(other: BoxPresentation) = BoxPresentation(
        contentAlignment = ComposeMerger.alignment.safeMerge(contentAlignment, other.contentAlignment),
        propagateMinConstraints = ComposeMerger.triState.safeMerge(propagateMinConstraints, other.propagateMinConstraints)
    )

    @Composable
    override fun createLocalization() = BoxPresentation(
        contentAlignment = Alignment.TopStart,
        propagateMinConstraints = TriState.TRUE
    )
}