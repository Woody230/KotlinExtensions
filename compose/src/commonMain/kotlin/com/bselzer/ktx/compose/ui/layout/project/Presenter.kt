package com.bselzer.ktx.compose.ui.layout.project

import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier

@Suppress("UNCHECKED_CAST")
abstract class Presenter<Model>(
    override val modifier: PresentableModifier
) : Presentable<Model> where Model : Presenter<Model> {
    override fun merge(other: Model?): Model = if (other == null || other === this) this as Model else safeMerge(other)
    protected abstract fun safeMerge(other: Model): Model

    @Composable
    override fun localized(): Model = this as Model
}