package com.bselzer.ktx.compose.ui.layout.spacer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class SpacerPresentation(
    /**
     * The spacing in the [SpacerDirection].
     */
    val size: Dp = ComposeMerger.dp.default,
) : Presenter<SpacerPresentation>() {
    companion object {
        @Stable
        val Default = SpacerPresentation()
    }

    override fun safeMerge(other: SpacerPresentation) = SpacerPresentation(
        size = ComposeMerger.dp.safeMerge(size, other.size)
    )

    @Composable
    override fun localized() = SpacerPresentation(
        size = 0.dp
    ).merge(this)
}