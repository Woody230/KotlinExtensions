package com.bselzer.ktx.compose.ui.layout.modifier

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Mergeable
import com.bselzer.ktx.function.objects.safeMerge

data class PresentableModifiers(
    val size: SizeConstraint = ModularSize.Default
) : Modifiable, Mergeable<PresentableModifiers> {
    companion object {
        @Stable
        val Default = PresentableModifiers()
    }

    override val modifier: Modifier = Modifier.run {
        if (size === ModularSize.Default) this else then(size)
    }

    override fun merge(other: PresentableModifiers?) = if (other == null || other === this) this else PresentableModifiers(
        size = size.safeMerge(other.size, ModularSize.Default)
    )
}