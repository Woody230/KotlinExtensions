package com.bselzer.ktx.compose.ui.layout.dialog

import com.bselzer.ktx.compose.ui.layout.project.LogicModel
import com.bselzer.ktx.compose.ui.layout.text.TextLogic
import com.bselzer.ktx.compose.ui.layout.textbutton.TextButtonLogic

data class AlertDialogLogic(
    /**
     * The [LogicModel] for the negative action button for dismissal.
     */
    val negativeButton: TextButtonLogic? = null,

    /**
     * The [LogicModel] for the neutral action button for an alternative action.
     */
    val neutralButton: TextButtonLogic? = null,

    /**
     * The [LogicModel] for the positive action button for confirmation.
     */
    val positiveButton: TextButtonLogic? = null,

    /**
     * The [LogicModel] for the title.
     */
    val title: TextLogic? = null,

    /**
     * The block for when the user tries to dismiss the dialog by clicking outside or pressing the back button
     */
    val onDismissRequest: () -> Unit,
) : LogicModel