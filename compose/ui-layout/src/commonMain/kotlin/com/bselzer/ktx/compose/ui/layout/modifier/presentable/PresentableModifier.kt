package com.bselzer.ktx.compose.ui.layout.modifier.presentable

import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.merge.safeMerge
import com.bselzer.ktx.compose.ui.layout.modifier.Modifiable

interface PresentableModifier : Modifiable {
    infix fun then(other: PresentableModifier): PresentableModifier =
        if (other === PresentableModifier) this else CombinedPresentableModifier(this, other)

    infix fun merge(other: PresentableModifier) = safeMerge(other, PresentableModifier)

    companion object : PresentableModifier {
        override val modifier: Modifier = Modifier
    }
}