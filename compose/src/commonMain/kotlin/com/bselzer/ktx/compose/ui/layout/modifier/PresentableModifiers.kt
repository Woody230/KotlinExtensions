package com.bselzer.ktx.compose.ui.layout.modifier

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Mergeable
import com.bselzer.ktx.function.objects.safeMerge

data class PresentableModifiers(
    val size: Size = ModularSize.Default,
    val padding: Padding = ModularPadding.Default,
    val offset: Offset = ModularOffset.Default
) : Modifiable, Mergeable<PresentableModifiers> {
    companion object {
        @Stable
        val Default = PresentableModifiers()
    }

    override val modifier: Modifier = Modifier
        .then(size, ModularSize.Default)
        .then(padding, ModularPadding.Default)
        .then(offset, ModularOffset.Default)

    override fun merge(other: PresentableModifiers?) = if (other == null || other === this) this else PresentableModifiers(
        size = size.safeMerge(other.size, ModularSize.Default),
        padding = padding.safeMerge(other.padding, ModularPadding.Default),
        offset = offset.safeMerge(other.offset, ModularOffset.Default)
    )
}