package com.bselzer.ktx.compose.ui.layout.text

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class TextInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The text to be displayed.
     */
    val text: AnnotatedString,

    /**
     * Callback that is executed when a new text layout is calculated.
     * A TextLayoutResult object that callback provides contains paragraph information, size of the text, baselines and other details.
     * The callback can be used to add additional decoration or functionality to the text. For example, to draw selection around the text.
     */
    val onTextLayout: (TextLayoutResult) -> Unit = {},

    /**
     * Callback that is executed when users click the text. This callback is called with clicked character's offset and text value.
     */
    val onClickOffset: ((Int, AnnotatedString) -> Unit)? = null,

    /**
     * A map store composables that replaces certain ranges of the text.
     * It's used to insert composables into text layout. Check InlineTextContent for more information.
     */
    val inlineContent: Map<String, InlineTextContent> = mapOf()
) : Interactor(modifier) {
    constructor(text: String) : this(text = AnnotatedString(text))
}

/**
 * @return a [TextInteractor] containing this [String]
 */
fun String.textInteractor() = TextInteractor(this)