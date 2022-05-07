package com.bselzer.ktx.compose.ui.layout.textbutton

import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.ui.layout.button.ButtonPresentation
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresentation

data class TextButtonPresentation(
    val button: ButtonPresentation = ButtonPresentation.Default,
    val text: TextPresentation = TextPresentation.Default
) : Presenter<TextButtonPresentation>() {
    override fun safeMerge(other: TextButtonPresentation) = TextButtonPresentation(
        button = button.merge(other.button),
        text = text.merge(other.text)
    )

    @Composable
    override fun localized() = copy(
        button = button.localized(),
        text = text.localized()
    )
}