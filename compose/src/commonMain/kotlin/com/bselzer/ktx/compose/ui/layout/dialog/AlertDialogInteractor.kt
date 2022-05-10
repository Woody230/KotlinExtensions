package com.bselzer.ktx.compose.ui.layout.dialog

import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor
import com.bselzer.ktx.compose.ui.layout.textbutton.TextButtonInteractor

data class AlertDialogInteractor(
    /**
     * The [Interactor] for the negative action button for dismissal.
     */
    val negativeButton: TextButtonInteractor? = null,

    /**
     * The [Interactor] for the neutral action button for an alternative action.
     */
    val neutralButton: TextButtonInteractor? = null,

    /**
     * The [Interactor] for the positive action button for confirmation.
     */
    val positiveButton: TextButtonInteractor? = null,

    /**
     * The [Interactor] for the title.
     */
    val title: TextInteractor? = null,

    /**
     * The block for when the user tries to dismiss the dialog by clicking outside or pressing the back button
     */
    val onDismissRequest: () -> Unit,
) : Interactor()