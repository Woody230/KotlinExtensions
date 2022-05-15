package com.bselzer.ktx.compose.ui.layout.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier

/**
 * Represents a model for creating a composable.
 */
interface Presentable<Model> : Mergeable<Model> where Model : Presentable<Model> {
    /**
     * The [Modifier]s.
     */
    val modifier: PresentableModifier

    /**
     * Creates a localized version of this presentation.
     */
    @Composable
    fun localized(): Model
}