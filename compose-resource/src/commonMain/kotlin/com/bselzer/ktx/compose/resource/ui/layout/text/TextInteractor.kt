package com.bselzer.ktx.compose.resource.ui.layout.text

import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.resource.strings.localized
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor
import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.desc.desc

/**
 * Creates an interactor for indicating a confirmation action.
 */
@Composable
fun confirmationTextInteractor() = TextInteractor(
    text = Resources.strings.ok.desc().localized()
)

/**
 * Creates an interactor for indicating a dismissal or cancellation of an action.
 */
@Composable
fun dismissTextInteractor() = TextInteractor(
    text = Resources.strings.cancel.desc().localized()
)

/**
 * Creates an interactor for indicating a reset action.
 */
@Composable
fun resetTextInteractor() = TextInteractor(
    text = Resources.strings.reset.desc().localized()
)