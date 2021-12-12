package com.bselzer.ktx.library

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.bselzer.ktx.compose.ui.appbar.MaterialAppBarTitle
import com.bselzer.ktx.compose.ui.dialog.ConfirmationButton
import com.bselzer.ktx.compose.ui.dialog.MaterialAlertDialog
import com.mikepenz.aboutlibraries.entity.Library

/**
 * Lays out cards for each of the [libraries].
 *
 * @param modifier the column modifier
 * @param contentPadding the column padding
 * @param titleStyle the style of the text for the name of the library, and the name of the license on the dialog
 * @param subtitleStyle the style of the text for the name of the author and artifact version of the library
 * @param dialogStyle the style of the text for the license in the dialog
 * @param buttonStyle the style of the text for the dialog buttons
 * @param buttonColors the colors of the dialog buttons
 * @param shape the dialog shape
 * @param backgroundColor the color of the dialog background
 * @param contentColor the color of the dialog content
 * @param itemBorder the border of each individual card
 * @param itemElevation the elevation of each individual card
 * @param divider the divider between each individual card
 * @param licenseBackgroundColor the color of the background behind the name of the license
 * @param licenseContentColor the color of the text for the name of the license
 * @param licenseStyle the style of the text for the name of the license
 * @param dialogShape the shape of the dialog
 * @param dialogBackgroundColor the color of the background of the dialog
 * @param dialogContentColor the color of the text for the dialog
 * @param dialogProperties the dialog properties
 * @param libraries the libraries to create cards for
 */
@Composable
fun LibraryColumn(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
    titleStyle: TextStyle = MaterialTheme.typography.h6,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    dialogStyle: TextStyle = MaterialTheme.typography.body1,
    buttonStyle: TextStyle = MaterialTheme.typography.button,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    itemBorder: BorderStroke? = null,
    itemElevation: Dp = 1.dp,
    divider: @Composable () -> Unit = { Spacer(modifier = Modifier.height(16.dp)) },
    licenseBackgroundColor: Color = MaterialTheme.colors.primary,
    licenseContentColor: Color = contentColorFor(licenseBackgroundColor),
    licenseStyle: TextStyle = MaterialTheme.typography.subtitle2,
    dialogShape: Shape = MaterialTheme.shapes.medium,
    dialogBackgroundColor: Color = MaterialTheme.colors.surface,
    dialogContentColor: Color = contentColorFor(dialogBackgroundColor),
    dialogProperties: DialogProperties = DialogProperties(),
    libraries: List<Library>
) {
    var selected by remember { mutableStateOf<Library?>(null) }
    selected?.let { value ->
        LicenseDialog(
            showDialog = { if (!it) selected = null },
            shape = dialogShape,
            backgroundColor = dialogBackgroundColor,
            contentColor = dialogContentColor,
            properties = dialogProperties,
            buttonStyle = buttonStyle,
            buttonColors = buttonColors,
            titleStyle = titleStyle,
            contentStyle = dialogStyle,
            library = value
        )
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        itemsIndexed(libraries) { index, library ->
            LicenseCard(
                shape = shape,
                backgroundColor = backgroundColor,
                contentColor = contentColor,
                border = itemBorder,
                elevation = itemElevation,
                titleStyle = titleStyle,
                subtitleStyle = subtitleStyle,
                licenseBackgroundColor = licenseBackgroundColor,
                licenseContentColor = licenseContentColor,
                licenseStyle = licenseStyle,
                onClick = { selected = library },
                library = library,
            )

            if (index != libraries.count() - 1) {
                divider()
            }
        }
    }
}

/**
 * Lays out the dialog for the first license of the selected [library].
 *
 * @param showDialog the block for setting whether the dialog should be shown
 * @param shape the dialog shape
 * @param backgroundColor the color of the dialog background
 * @param contentColor the color of the dialog content
 * @param properties the properties
 * @param titleStyle the style of the text for the name of the license
 * @param contentStyle the style of the text for the license content
 * @param buttonStyle the style of the text for the dialog buttons
 * @param buttonColors the colors of the dialog buttons
 * @param library the library to display the first license for
 */
@Composable
fun LicenseDialog(
    showDialog: (Boolean) -> Unit,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties(),
    titleStyle: TextStyle = MaterialTheme.typography.h6,
    contentStyle: TextStyle = MaterialTheme.typography.body1,
    buttonStyle: TextStyle = MaterialTheme.typography.button,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    library: Library,
) {
    val license = library.licenses.firstOrNull()
    MaterialAlertDialog(
        modifier = Modifier.fillMaxSize(),
        showDialog = showDialog,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        properties = properties,
        title = { MaterialAppBarTitle(title = license?.name ?: "No License", style = titleStyle) },
        positiveButton = {
            ConfirmationButton(colors = buttonColors, textStyle = buttonStyle) {
                showDialog(false)
            }
        }
    ) {
        val text = if (license == null) "No license found." else license.licenseContent ?: ""
        Text(modifier = Modifier.verticalScroll(rememberScrollState()), text = text, style = contentStyle)
    }
}

/**
 * Lays out a card for displaying the name, author, arifact version, and name of the licenses for the [library].
 *
 * @param shape the dialog shape
 * @param backgroundColor the color of the dialog background
 * @param contentColor the color of the dialog content
 * @param border the border
 * @param elevation the elevation
 * @param titleStyle the style of the text for the name of the library
 * @param subtitleStyle the style of the text for the name of the author and artifact version of the library
 * @param licenseBackgroundColor the color of the background behind the name of the license
 * @param licenseContentColor the color of the text for the name of the license
 * @param licenseStyle the style of the text for the name of the license
 * @param onClick the on-click handler
 * @param library the library to display information for
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LicenseCard(
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    elevation: Dp = 1.dp,
    titleStyle: TextStyle = MaterialTheme.typography.h6,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    licenseBackgroundColor: Color = MaterialTheme.colors.primary,
    licenseContentColor: Color = contentColorFor(licenseBackgroundColor),
    licenseStyle: TextStyle = MaterialTheme.typography.subtitle2,
    onClick: () -> Unit,
    library: Library
) = Card(
    modifier = Modifier.fillMaxWidth(),
    shape = shape,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    border = border,
    elevation = elevation,
    onClick = onClick,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = library.name, style = titleStyle, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = library.author, style = subtitleStyle, maxLines = 1, overflow = TextOverflow.Ellipsis)
        library.artifactVersion?.let { version ->
            Text(text = version, style = subtitleStyle, maxLines = 1, overflow = TextOverflow.Clip)
        }

        if (library.licenses.isNotEmpty()) {
            Row(modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()) {
                library.licenses.forEach { license ->
                    Badge(
                        modifier = Modifier.padding(end = 4.dp),
                        contentColor = licenseContentColor,
                        backgroundColor = licenseBackgroundColor
                    ) {
                        Text(text = license.name, style = licenseStyle)
                    }
                }
            }
        }
    }
}