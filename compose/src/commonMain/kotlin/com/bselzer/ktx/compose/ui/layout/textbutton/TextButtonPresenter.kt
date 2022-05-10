package com.bselzer.ktx.compose.ui.layout.textbutton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.button.ButtonPresenter
import com.bselzer.ktx.compose.ui.layout.button.ButtonType
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class TextButtonPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,
    val button: ButtonPresenter = ButtonPresenter.Default,
    val text: TextPresenter = TextPresenter.Default
) : Presenter<TextButtonPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = TextButtonPresenter()
    }

    override fun safeMerge(other: TextButtonPresenter) = TextButtonPresenter(
        modifiers = modifiers.merge(other.modifiers),
        button = button.merge(other.button),
        text = text.merge(other.text)
    )

    @Composable
    override fun localized() = copy(
        button = button.localized(type = ButtonType.TEXT),
        text = text.localized()
    )
}