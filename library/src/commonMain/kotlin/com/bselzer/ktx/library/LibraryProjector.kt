package com.bselzer.ktx.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.alertdialog.AlertDialogProjector
import com.bselzer.ktx.compose.ui.layout.alertdialog.confirmationAlertDialogInteractor
import com.bselzer.ktx.compose.ui.layout.badgetext.BadgeTextInteractor
import com.bselzer.ktx.compose.ui.layout.badgetext.BadgeTextProjector
import com.bselzer.ktx.compose.ui.layout.card.CardInteractor
import com.bselzer.ktx.compose.ui.layout.card.CardProjector
import com.bselzer.ktx.compose.ui.layout.divider.DividerInteractor
import com.bselzer.ktx.compose.ui.layout.divider.DividerPresenter
import com.bselzer.ktx.compose.ui.layout.lazycolumn.LazyColumnInteractor
import com.bselzer.ktx.compose.ui.layout.lazycolumn.LazyColumnPresenter
import com.bselzer.ktx.compose.ui.layout.lazycolumn.LazyColumnProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor
import com.bselzer.ktx.compose.ui.layout.text.TextProjector
import com.bselzer.ktx.resource.Resources
import com.mikepenz.aboutlibraries.entity.Library
import dev.icerock.moko.resources.compose.stringResource

class LibraryProjector(
    override val interactor: LibraryInteractor,
    override val presenter: LibraryPresenter = LibraryPresenter.Default
) : Projector<LibraryInteractor, LibraryPresenter>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier ->
        var selected by remember { mutableStateOf<Library?>(null) }
        Dialog(selected) { selected = it }

        LazyColumnProjector(
            interactor = LazyColumnInteractor(
                divider = DividerInteractor.Default,
                items = interactor.libraries
            ),
            presenter = LazyColumnPresenter(
                divider = DividerPresenter(color = Color.Transparent, thickness = 16.dp)
            )
        ).project(
            modifier = combinedModifier
        ) { index, library ->
            Card(library) { selected = it }
        }
    }

    @Composable
    private fun LibraryPresenter.Dialog(selected: Library?, setSelected: (Library?) -> Unit) = selected?.let {
        AlertDialogProjector(
            interactor = confirmationAlertDialogInteractor { showDialog ->
                if (!showDialog) {
                    setSelected(null)
                }
            }
        ).project {
            val license = selected.licenses.firstOrNull()
            val text = if (license == null) stringResource(Resources.strings.no_license_found) else license.licenseContent ?: ""
            TextProjector(
                interactor = TextInteractor(text = text),
                presenter = dialogBody
            ).project(
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        }
    }

    @Composable
    private fun LibraryPresenter.Card(library: Library, setSelected: (Library) -> Unit) = CardProjector(
        interactor = CardInteractor(
            onClick = { setSelected(library) },
        ),
        presenter = container
    ).project {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextProjector(
                interactor = TextInteractor(text = library.name),
                presenter = title
            ).project()

            if (library.author.isNotBlank()) {
                TextProjector(
                    interactor = TextInteractor(text = library.author),
                    presenter = subtitle
                ).project()
            }

            if (!library.artifactVersion.isNullOrBlank()) {
                TextProjector(
                    interactor = TextInteractor(text = library.artifactId),
                    presenter = subtitle
                ).project()
            }

            if (library.licenses.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                ) {
                    library.licenses.forEach { license ->
                        BadgeTextProjector(
                            interactor = BadgeTextInteractor(
                                text = TextInteractor(text = license.name)
                            ),
                            presenter = badge
                        ).project(
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }
            }
        }
    }
}