package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor

class ContentScaleMerger : ComponentMerger<ContentScale> {
    override val default: ContentScale = Default

    companion object {
        @Stable
        val Default = object : ContentScale {
            override fun computeScaleFactor(srcSize: Size, dstSize: Size): ScaleFactor = ScaleFactor.Unspecified
        }
    }
}