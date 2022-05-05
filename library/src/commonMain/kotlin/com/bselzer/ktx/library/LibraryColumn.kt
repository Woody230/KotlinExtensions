package com.bselzer.ktx.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.appbar.MaterialAppBarTitle
import com.bselzer.ktx.compose.ui.layout.dialog.AlertDialogStyle
import com.bselzer.ktx.compose.ui.layout.dialog.ConfirmationButton
import com.bselzer.ktx.compose.ui.layout.dialog.LocalAlertDialogStyle
import com.bselzer.ktx.compose.ui.layout.dialog.MaterialAlertDialog
import com.bselzer.ktx.compose.ui.style.*
import com.bselzer.ktx.resource.Resources
import com.mikepenz.aboutlibraries.entity.Library
import dev.icerock.moko.resources.compose.stringResource

/**
 * Lays out cards for each of the [libraries].
 *
 * @param style how to display the column
 * @param divider the divider between each individual card
 * @param libraries the libraries to create cards for
 */
@Composable
fun LibraryColumn(
    style: LazyColumnStyle = LocalLazyColumnStyle.current,
    divider: @Composable LazyListScope.(Int, Library) -> Unit = { _, _ -> Spacer(modifier = Modifier.height(16.dp)) },
    libraries: List<Library>
) {
    var selected by remember { mutableStateOf<Library?>(null) }
    selected?.let { value ->
        LicenseDialog(
            showDialog = { if (!it) selected = null },
            library = value
        )
    }

    LazyColumn(style = style) {
        itemsIndexed(libraries) { index, library ->
            LicenseCard(
                onClick = { selected = library },
                library = library,
            )

            if (index != libraries.count() - 1) {
                this@LazyColumn.divider(index, library)
            }
        }
    }
}

/**
 * Lays out the dialog for the first license of the selected [library].
 *
 * @param showDialog the block for setting whether the dialog should be shown
 * @param dialogStyle the style of the dialog for the license content
 * @param titleStyle the style of the text for the name of the license
 * @param contentStyle the style of the text for the license content
 * @param buttonStyle the style of the dialog buttons
 * @param buttonTextStyle the style of the text on the dialog buttons
 * @param library the library to display the first license for
 */
@Composable
fun LicenseDialog(
    showDialog: (Boolean) -> Unit,
    dialogStyle: AlertDialogStyle = LocalAlertDialogStyle.current,
    titleStyle: WordStyle = LocalWordStyle.current,
    contentStyle: WordStyle = LocalWordStyle.current,
    buttonStyle: ButtonStyle = LocalButtonStyle.current,
    buttonTextStyle: WordStyle = LocalWordStyle.current,
    library: Library,
) {
    val license = library.licenses.firstOrNull()
    MaterialAlertDialog(
        style = dialogStyle prioritize Modifier.fillMaxSize(),
        onDismissRequest = { showDialog(false) },
        title = { MaterialAppBarTitle(title = license?.name ?: stringResource(Resources.strings.no_license), style = titleStyle) },
        positiveButton = {
            ConfirmationButton(style = buttonStyle, textStyle = buttonTextStyle) {
                showDialog(false)
            }
        }
    ) {
        val text = if (license == null) stringResource(Resources.strings.no_license_found) else license.licenseContent ?: ""
        Text(
            text = text,
            style = WordStyle(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                textStyle = MaterialTheme.typography.body1
            ).with(contentStyle)
        )
    }
}

/**
 * Lays out a card for displaying the name, author, artifact version, and name of the licenses for the [library].
 *
 * @param cardStyle the style of the card
 * @param titleStyle the style of the text for the name of the library
 * @param subtitleStyle the style of the text for the name of the author and artifact version of the library
 * @param badgeStyle the style of the badge displaying the license name
 * @param badgeTextStyle the style of the text on the badge displaying the license name
 * @param onClick the on-click handler
 * @param library the library to display information for
 */
@Composable
fun LicenseCard(
    cardStyle: ClickableCardStyle = LocalClickableCardStyle.current,
    titleStyle: WordStyle = LocalWordStyle.current,
    subtitleStyle: WordStyle = LocalWordStyle.current,
    badgeStyle: BadgeStyle = LocalBadgeStyle.current,
    badgeTextStyle: WordStyle = LocalWordStyle.current,
    onClick: () -> Unit,
    library: Library
) = Card(
    style = ClickableCardStyle(modifier = Modifier.fillMaxWidth()).with(cardStyle),
    onClick = onClick,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = library.name,
            style = WordStyle(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textStyle = MaterialTheme.typography.h6
            ).with(titleStyle)
        )

        if (library.author.isNotBlank()) {
            Text(text = library.author,
                style = WordStyle(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textStyle = MaterialTheme.typography.subtitle1,
                ).with(subtitleStyle)
            )
        }

        library.artifactVersion?.let { version ->
            Text(
                text = version,
                style = WordStyle(
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    textStyle = MaterialTheme.typography.subtitle1,
                )
            )
        }

        if (library.licenses.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                library.licenses.forEach { license ->
                    Badge(
                        style = BadgeStyle(
                            modifier = Modifier.padding(end = 4.dp),
                            backgroundColor = MaterialTheme.colors.primary
                        ).with(badgeStyle),
                        text = license.name,
                        textStyle = WordStyle(textStyle = MaterialTheme.typography.subtitle2).with(badgeTextStyle)
                    )
                }
            }
        }
    }
}