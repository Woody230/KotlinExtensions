package com.bselzer.ktx.compose.ui.layout.alertdialog.singlechoice

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.radiobutton.RadioButtonPresenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class SingleChoicePresenter(
    override val modifier: PresentableModifier = PresentableModifier,
    val radioButton: RadioButtonPresenter = RadioButtonPresenter.Default,
    val text: TextPresenter = TextPresenter.Default
) : Presenter<SingleChoicePresenter>(modifier) {
    companion object {
        @Stable
        val Default = SingleChoicePresenter()
    }

    override fun safeMerge(other: SingleChoicePresenter) = SingleChoicePresenter(
        modifier = modifier.merge(other.modifier),
        radioButton = radioButton.merge(other.radioButton),
        text = text.merge(other.text)
    )

    @Composable
    override fun localized() = copy(
        radioButton = radioButton.localized(),
        text = text.localized()
    )
}