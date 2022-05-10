package com.bselzer.ktx.compose.ui.layout.text

import androidx.compose.runtime.Composable
import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.compose.stringResource

/**
 * Creates an interactor for indicating a confirmation action.
 */
@Composable
fun confirmationTextInteractor() = TextInteractor(
    text = stringResource(Resources.strings.ok)
)

/**
 * Creates an interactor for indicating a dismissal or cancellation of an action.
 */
@Composable
fun dismissTextInteractor() = TextInteractor(
    text = stringResource(Resources.strings.cancel)
)

/**
 * Creates an interactor for indicating a reset action.
 */
@Composable
fun resetTextInteractor() = TextInteractor(
    text = stringResource(Resources.strings.reset)
)