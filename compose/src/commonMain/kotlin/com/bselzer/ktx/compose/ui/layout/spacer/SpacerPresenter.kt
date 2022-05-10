package com.bselzer.ktx.compose.ui.layout.spacer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class SpacerPresenter(
    /**
     * The spacing in the [SpacerDirection].
     */
    val size: Dp = ComposeMerger.dp.default,
) : Presenter<SpacerPresenter>() {
    companion object {
        @Stable
        val Default = SpacerPresenter()
    }

    override fun safeMerge(other: SpacerPresenter) = SpacerPresenter(
        size = ComposeMerger.dp.safeMerge(size, other.size)
    )

    @Composable
    override fun localized() = SpacerPresenter(
        size = 0.dp
    ).merge(this)
}