package com.bselzer.ktx.compose.ui.layout.modifier

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Mergeable
import com.bselzer.ktx.function.objects.nullMerge

data class PresentableModifiers(
    val size: Size? = null
) : Modifiable, Mergeable<PresentableModifiers> {
    companion object {
        @Stable
        val Default = PresentableModifiers()
    }

    override val modifier: Modifier = Modifier.then(size)

    override fun merge(other: PresentableModifiers?) = if (other == null || other === this) this else PresentableModifiers(
        size = size.nullMerge(other.size)
    )
}