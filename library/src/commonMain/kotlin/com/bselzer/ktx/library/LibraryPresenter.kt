package com.bselzer.ktx.library

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.style.TextOverflow
import com.bselzer.ktx.compose.ui.layout.alertdialog.AlertDialogPresenter
import com.bselzer.ktx.compose.ui.layout.badge.BadgePresenter
import com.bselzer.ktx.compose.ui.layout.badgetext.BadgeTextPresenter
import com.bselzer.ktx.compose.ui.layout.card.CardPresenter
import com.bselzer.ktx.compose.ui.layout.modifier.ModularSize.Companion.FillWidth
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class LibraryPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    val dialog: AlertDialogPresenter = AlertDialogPresenter.Default,

    /**
     * The [Presenter] for the text within the [dialog].
     */
    val dialogBody: TextPresenter = TextPresenter.Default,

    /**
     * The [Presenter] for the container holding the [title], [subtitle], and [badge].
     */
    val container: CardPresenter = CardPresenter.Default,

    /**
     * The [Presenter] for the name of the library.
     */
    val title: TextPresenter = TextPresenter.Default,

    /**
     * The [Presenter] for the name of the author and artifact version.
     */
    val subtitle: TextPresenter = TextPresenter.Default,

    /**
     * The [Presenter] for the name of a license.
     */
    val badge: BadgeTextPresenter = BadgeTextPresenter.Default
) : Presenter<LibraryPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = LibraryPresenter()
    }

    override fun safeMerge(other: LibraryPresenter) = LibraryPresenter(
        modifiers = modifiers.merge(other.modifiers),
        dialog = dialog.merge(other.dialog),
        dialogBody = dialogBody.merge(other.dialogBody),
        container = container.merge(other.container),
        title = title.merge(other.title),
        subtitle = subtitle.merge(other.subtitle),
        badge = badge.merge(other.badge)
    )

    @Composable
    override fun localized() = LibraryPresenter(
        modifiers = PresentableModifiers(size = FillWidth),
        dialogBody = TextPresenter(
            textStyle = MaterialTheme.typography.body1
        ),
        title = TextPresenter(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textStyle = MaterialTheme.typography.h6
        ),
        subtitle = TextPresenter(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textStyle = MaterialTheme.typography.subtitle1
        ),
        badge = BadgeTextPresenter(
            badge = BadgePresenter(
                backgroundColor = MaterialTheme.colors.primary
            )
        )
    ).merge(this).run {
        copy(
            dialog = dialog.localized(),
            dialogBody = dialogBody.localized(),
            container = container.localized(),
            title = title.localized(),
            subtitle = subtitle.localized(),
            badge = badge.localized()
        )
    }
}