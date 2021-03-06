package com.bselzer.ktx.compose.ui.layout

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
import com.bselzer.ktx.aboutlibraries.author
import com.bselzer.ktx.compose.ui.layout.alertdialog.AlertDialogInteractor
import com.bselzer.ktx.compose.ui.layout.alertdialog.AlertDialogProjector
import com.bselzer.ktx.compose.ui.layout.alertdialog.uniText
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
import com.bselzer.ktx.compose.ui.layout.text.textInteractor
import com.bselzer.ktx.resource.KtxResources
import com.bselzer.ktx.resource.strings.localized
import com.mikepenz.aboutlibraries.entity.Library
import dev.icerock.moko.resources.desc.desc

class LibraryProjector(
    interactor: LibraryInteractor,
    presenter: LibraryPresenter = LibraryPresenter.Default
) : Projector<LibraryInteractor, LibraryPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        var selected by remember { mutableStateOf<Library?>(null) }
        presenter.Dialog(selected) { selected = it }

        LazyColumnProjector(
            interactor = LazyColumnInteractor(
                divider = DividerInteractor.Default,
                items = interactor.libraries
            ),
            presenter = LazyColumnPresenter(
                divider = DividerPresenter(color = Color.Transparent, thickness = 16.dp)
            )
        ).Projection(
            modifier = combinedModifier
        ) { index, library ->
            presenter.Card(library) { selected = it }
        }
    }

    @Composable
    private fun LibraryPresenter.Dialog(selected: Library?, setSelected: (Library?) -> Unit) = selected?.let {
        val license = selected.licenses.firstOrNull()
        AlertDialogProjector(
            interactor = AlertDialogInteractor.Builder { setSelected(null) }.uniText().build { title = license?.name ?: "" }
        ).Projection(
            // TODO vertical scrolling not working properly without constrained version of dialog
            constrained = true
        ) {
            val text = if (license == null) KtxResources.strings.no_license_found.desc() else (license.licenseContent ?: "").desc()
            TextProjector(
                interactor = text.localized().textInteractor(),
                presenter = dialogBody
            ).Projection(
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
    ).Projection {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextProjector(
                interactor = TextInteractor(text = library.name),
                presenter = title
            ).Projection()

            if (library.author.isNotBlank()) {
                TextProjector(
                    interactor = TextInteractor(text = library.author),
                    presenter = subtitle
                ).Projection()
            }

            val version = library.artifactVersion
            if (!version.isNullOrBlank()) {
                TextProjector(
                    interactor = TextInteractor(text = version),
                    presenter = subtitle
                ).Projection()
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
                        ).Projection(
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }
            }
        }
    }
}