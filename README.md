![](https://img.shields.io/badge/targets-Android%2FJVM-informational)
![](https://img.shields.io/github/v/release/Woody230/KotlinExtensions)
[![](https://img.shields.io/maven-central/v/io.github.woody230.ktx/compose)](https://search.maven.org/search?q=io.github.woody230.ktx)
![](https://img.shields.io/github/license/Woody230/KotlinExtensions)

# Kotlin Extensions

Kotlin Multiplatform extensions and implementations for the Kotlin standard library and third-party libraries.

# Gradle

Published to [Maven Central](https://search.maven.org/search?q=io.github.woody230.ktx).

```kotlin
repositories {
    mavenCentral()
}
```

```kotlin
implementation("io.github.woody230.ktx:$Module:4.0.1")
```

# Modules
Note that some modules will pull other modules in as api dependencies or include multiple similarly used dependencies that are often used together but do not necessarily need to be together in a module. The use cases of these modules may be split into multiple modules at some point.

## base64
Base64 encoding and decoding between strings and byte arrays using v0.0.1 of [kbase64](https://github.com/jershell/kbase64).

```kotlin
val bytes = "AdsnAAA=".decodeBase64ToByteArray()
val string = bytes.encodeBase64ToString()
```

## comparator
* Nullable string comparator. 
* User friendly string and enum comparators.

## compose
Extensions, wrappers, and composable implementations for [Jetbrains Compose](https://github.com/JetBrains/compose-jb).

### Hex
Converting hexadecimal colors in ARGB or RGB string format (with optional `#` prefix) to `androidx.compose.ui.graphics.Color`.

```kotlin
val color = Hex("3dab5a").color()
val hex = color.hex()
```

### Locale
Converter for the `com.bselzer.ktx.intl.Locale` to `androidx.compose.ui.text.intl.Locale`.

`LocalLocale` composition local that can be provided by the following in order to recompose on `Localizer.locale` changes:
```kotlin
@Composable
fun Content() {
    ProvideLocale {
        val locale = LocalLocale.current
        // ...
    }
}
```

### Layout
Wrappers and custom composable components using the content/presentation model and projection concept as described by [kirill-grouchnikov's Aurora project](https://github.com/kirill-grouchnikov/aurora/blob/icicle/docs/component/Intro.md).

New to Compose?
* [Guidelines](https://github.com/androidx/androidx/blob/androidx-main/compose/docs/compose-api-guidelines.md)
* [Jetbrains Compose Getting Started](https://github.com/JetBrains/compose-jb/tree/master/tutorials/Getting_Started)
* [Jetpack Compose Tutorial](https://developer.android.com/jetpack/compose/tutorial)

#### Models
A **content model** describes the basic elements of a piece of your model realm, how the user interacts with it, and what happens when that interaction happens.

A **presentation model** describes how to "convert" (or project) that content model into a composable that can be added to the application UI hierarchy to present the data backed by that content model and react to the user interaction.

A **projection** is the act of "combining" a content model and a presentation model and creating a composable.

In a nutshell:

- **content model + presentation model &#8594; projection**
- **projection &#8594; one or more composables**

In a nutshell:

content model + presentation model → projection
projection → one or more composables

#### Projection
Within this project, the content model is referred to as an `Interactor`, the presentation model as a `Presenter`, and the projection as a `Projector`.

##### Composable
In order to lay out the composable, create the `Projector` from the `Interactor` and `Presenter` and call the `Projection` method with an optional modifier.
```kotlin
@Composable
fun Image() = ImageProjector(
    interactor = ImageInteractor(
        painter = Icons.Filled.Delete.painter(),
        contentDescription = "Delete the file."
    ),
    presenter = ImagePresenter(colorFilter = ColorFilter.tint(Color.Blue))
).Projection(
    modifier = Modifier.clickable {
        // Delete...
    }
)
```

##### Modifiable

When constructing your `Interactor`s and `Presenter`s, you may use a subset of the modifiers that are normally present. 
These are used to modify the child composables that make up the parent composable being used.

`InteractableModifier`s
| Name | Description |
| --- | --- |
| Clickable | Equivalent to the combinedClickable modifier for onClick, onDoubleClick, and onLongClick. |
| HorizontalScroll | Equivalent to the horizontalScroll modifier. |
| VerticalScroll | Equivalent to the verticalScroll modifier. |

`PresentableModifier`s
| Name | Description |
| --- | --- |
| PreferredHeight | Equivalent to the heightIn modifier. |
| RequiredHeight | Equivalent to the requiredHeightIn modifier. |
| FilledHeight | Equivalent to the fillMaxHeight modifier. |
| WrappedHeight | Equivalent to the wrapContentHeight modifier. |
| DefaultMinHeight | Equivalent to the defaultMinSize modifier with only the height specified. |
| ModularOffset | Equivalent to the absoluteOffset or offset modifiers depending on if an OffsetType.ABSOLUTE or OffsetType.RELATIVE is used respectively. |
| ModularPadding | Equivalent to the absolutePadding or padding modifiers depending on if a PaddingType.ABSOLUTE or PaddingType.RELATIVE is used respectively. |
| ModularSize | A wrapper for providing a height and/or width `PresentableModifier`. |
| MatchParent | Equivalent to the matchParentSize modifier within a BoxScope. |
| PreferredWidth | Equivalent to the widthIn modifier. |
| RequiredWidth | Equivalent to the requiredWidthIn modifier. |
| FilledWidth | Equivalent to the fillMaxWidth modifier. |
| WrappedWidth | Equivalent to the wrapContentWidth modifier. |
| DefaultMinWidth | Equivalent to the defaultMinSize modifier with only the width specified. |

```kotlin
DrawerComponentInteractor(
    // icon interactor
    // text interactor
    modifier = Clickable {
        // On click
    }
)

ImagePresenter(modifier = ModularSize.FillSize)
```

##### Merging
`Presenter`s and `Modifiable`s are merged so that the wrapped components have their defaults set and so that custom components can provide their own defaults.
As the caller of a `Projection`, the values you provide to a `Presenter` will take precedence.

As an example, the `DescriptionPresenter` provides a specific `TextPresenter` for the title and subtitle of the description.

```kotlin
data class DescriptionPresenter(
    override val modifier: PresentableModifier = PresentableModifier,
    val container: ColumnPresenter = ColumnPresenter.Default,
    val title: TextPresenter = TextPresenter.Default,
    val subtitle: TextPresenter = TextPresenter.Default
) : Presenter<DescriptionPresenter>(modifier) {
    override fun safeMerge(other: DescriptionPresenter) = DescriptionPresenter(
        modifier = modifier.merge(other.modifier),
        container = container.merge(other.container),
        title = title.merge(other.title),
        subtitle = subtitle.merge(other.subtitle)
    )

    @Composable
    override fun localized() = DescriptionPresenter(
        title = descriptionTitlePresenter(),
        subtitle = descriptionSubtitlePresenter()
    ).merge(this)
}

@Composable
fun descriptionTitlePresenter() = TextPresenter(
    fontWeight = FontWeight.Bold,
    overflow = TextOverflow.Visible,
    textStyle = MaterialTheme.typography.subtitle1
)

@Composable
fun descriptionSubtitlePresenter() = TextPresenter(
    overflow = TextOverflow.Ellipsis,
    textStyle = MaterialTheme.typography.subtitle2
)
```

#### AlertDialog
`androidx.compose.material.AlertDialog`

Note that currently there is no common AlertDialog in compose. 
* [No Access to DropDownMenu or AlertDialog in common source set #762](https://github.com/JetBrains/compose-jb/issues/762).
* [compose-mpp](https://github.com/atsushieno/compose-mpp) for more information.

```kotlin
@Composable
fun Dialog(shouldEnablePositive: Boolean) = AlertDialogProjector(
    interactor = AlertDialogInteractor.Builder {
        // On close dialog that is performed when any of the other actions close the dialog
    }.build {
        // If using the compose-resource module, you can use uniText(), biText(), or triText() to add a confirmation (positive), cancel (negative), and reset (neutral) button.

        // In this example we will add all three buttons.
        // Note that adding the text means the actions will be available but they do not necessarily need to be provided.
        triText()

        title = "Hello World"

        // The builder needs to know whether the dialog should remain open or closed after performing the associated action.
        onPositive = {
            // On confirmation
            val shouldClose = true
            DialogState(shouldClose) // or DialogState.OPENED or DialogState.CLOSED if there is no choice
        }
        
        // Specify enabling of the buttons if desired.
        positiveEnabled = shouldEnablePositive
        
        // You can use the close methods as shorthand when there is no choice.
        closeOnNeutral {
            // On reset
        }

        // By default actions will close the dialog and so a cancel action is likely not necessary as you will likely want that logic in the on close dialog action.
        closeOnNegative {
            // On cancel
        }
        
        closeOnDimissRequest {
            // On dismissal by clicking outside the dialog or pressing a back button
        }

    }
).Projection(
    // Two types of dialogs exist currently: a standard AlertDialog provided by Jetbrains Compose and a mimicked AlertDialog using ConstraintLayout
    // The constrained version is more appealing for single choice selections since the the title will be flung along with the items.
    // It is best to use the standard version when able to. The constrained version may be removed in the future. 
    constrained = true
) {
    // ...
}
```

#### SingleChoice
Display a list of items. This is normally intended to be projected within a dialog's content.

```kotlin
@Composable
fun SingleChoice() {
    val numbers = listOf(1, 2, 3)
    SingleChoiceProjector(
        interactor = SingleChoiceInteractor(
            selected = 2,
            values = numbers,
            getLabel = { number -> number.toString() },
            onSelection = { world -> selection.setSelected(world) }
        )
    ).Projection()
}
```

#### AppBar 
Top app bars and bottom app bars are both supported. 

By default, an `ActionPolicy` is applied to restrict the number of actions displayed on the app bar when a dropdown icon is provided.
The remaining actions are placed into the dropdown.

```kotlin
// See compose-resource for the various icons used.
@Composable
fun TopAppBar() = TopAppBarProjector(
    title = "My Title".textInteractor(),
    dropdown = dropdownIconInteractor(),
    navigation = IconButtonInteractor(
        icon = drawerNavigationIconInteractor()
    ) {
        // Open the drawer
    },
    actions = listOf(
        IconButtonInteractor(
            icon = deleteIconInteractor(),
            onClick = {
                // Delete
            }
        )
    ),
).Projection()
```

#### BackgroundImage
Content is placed inside a box and the image will by default match the size in order to act as a background behind the content.

Note that this is one of the few custom components that is NOT a projection.
```kotlin
@Composable
fun Image(painter: Painter) = BackgroundImage(
    painter = painter  
) {
    // ...
}
```

#### Column/Row
Enhancements around the standard column and row composables have been included with the `Projector`:
* Divider between items if the DividerInteractor is provided. 
* The divider can also be prepended or appended.
* spacedColumnProjector() to fill space with a transparent divider

```kotlin
@Composable
fun Spaced() = spacedColumnProjector(
    thickness = 10.dp,
    presenter = ColumnPresenter(
        prepend = TriState.TRUE,
        append = TriState.TRUE,
        horizontalAlignment = Alignment.CenterHorizontally
    )
).Projection(
    modifier = Modifier.fillMaxWidth(),
    content = buildList {
        add {
            // First item
        }
        add {
            // Second item
        }
    }.toTypedArray()
)
```

#### Drawer

```kotlin
@Composable
fun Drawer() = ModalDrawerProjector(
    interactor = ModalDrawerInteractor(
        container = ColumnInteractor.Divided,
        sections = listOf(deleteSection())
    )
).Projection {
    // Non-drawer content
}

@Composable
fun deleteSection() = DrawerSectionInteractor(
    title = "Files".textInteractor(),
    components = listOf(deleteComponent())
)

@Composable
fun deleteComponent() = DrawerComponentInteractor(
    icon = deleteIconInteractor(),
    text = "Recycle Bin",
    modifier = Clickable {
        // Close the drawer
    }
)

```

#### DropdownMenu
`androidx.compose.material.DropdownMenu`

Note that currently there is no common DropdownMenu in compose.
* [No Access to DropDownMenu or AlertDialog in common source set #762](https://github.com/JetBrains/compose-jb/issues/762).
* [compose-mpp](https://github.com/atsushieno/compose-mpp) for more information.

```kotlin
@Composable
fun Dropdown(
    actions: Collection<IconButtonInteractor>, 
    isExpanded: Boolean, 
    setExpanded: (Boolean) -> Unit
) = DropdownMenuProjector(
    interactor = DropdownMenuInteractor(
        icons = actions,
        expanded = isExpanded,
        onDismissRequest = setExpanded(false)
    )
).Projection()
```

#### LazyColumn/LazyRow
Enhancements around the standard lazy column and row composables have been included with the `Projector`:
* Divider between items if the DividerInteractor is provided. 
* The divider can also be prepended or appended.

```kotlin
@Composable
fun <T> Divided(items: List<T>) = LazyColumnProjector(
    interactor = LazyColumnInteractor(
        divider = DividerInteractor.Default,
        items = items
    ),
    presenter = LazyColumnPresenter(
        divider = DividerPresenter(color = Color.Transparent, thickness = 16.dp)
    )
).Projection { index, item ->
    // Lay out content for the given item
}
```

#### Picker
There are two types of pickers:
* IntegerPickerInteractor for numerics.
* ValuePickerInteractor for any other type of object.

```kotlin

 import kotlin.time.DurationUnit@Composable
fun Numeric(selection: Int, setSelection: (Int) -> Unit) = PickerProjector(
    interactor = IntegerPickerInteractor(
        selected = selection,
        range = 1..10,
        onSelectionChanged = setSelection,

        // See compose-resource for these interactors.
        upIcon = upIconInteractor(),
        downIcon = downIconInteractor(),
    ),
).Projection()

@Composable
fun Value(selection: DurationUnit, setSelection: (DurationUnit) -> Unit) = PickerProjector(
    interactor = ValuePickerInteractor(
        selected = selection,
        values = listOf(DurationUnit.DAYS, DurationUnit.MINUTES),
        getLabel = { unit -> unit.toString() },
        onSelectionChanged = setSelection,

        // See compose-resource for these interactors.
        upIcon = upIconInteractor(),
        downIcon = downIconInteractor(),
    )
).Projection()
```

#### Preference
Preferences are broken down into two types: those that use a dialog and those that do not. 

All preferences use the base `PreferenceProjector`. A painter, title, and subtitle must be provided:
* The title should represent what the preference is for.
* The subtitle should represent the selected value of the preference.
  * If the preference awaits a user's choice, then the text "Not set" should be used. 
    * In compose-resource, you can use `KtxResources.strings.not_set`.

```kotlin
fun interactor(painter: Painter) = PreferenceInteractor(
    painter = painter,
    title = "Theme".textInteractor(),
    subtitle = "Dark".textInteractor()
)
```

The `CheckboxPreferenceProjector`, `SwitchPreferenceProjector`, and `TextPreferenceProjector` do not use a dialog.
```kotlin
@Composable
fun Switch(painter: Painter) = SwitchPreferenceProjector(
    interactor = SwitchPreferenceInteractor(
        preference = PreferenceInteractor(
            painter = painter,
            title = "Theme".textInteractor(),
            subtitle = "Dark".textInteractor()
        ),
        switch = SwitchInteractor(
            checked = themeLogic.checked,
            onCheckedChange = themeLogic.onCheckedChange
        )
    )
).Projection()

@Composable
fun Text(painter: Painter) = TextPreferenceProjector(
    interactor = TextPreferenceInteractor(
        painter = painter,
        title = "Country".textInteractor(),
        subtitle = "United States of America".textInteractor(),
    )
).Projection(modifier = Modifier.clickable {
    // Enable something to change the preference such as opening a dialog for selection.
})
```
##### AlertDialogPreference
`AlertDIalogPreferenceProjector` is the base class for a preference that uses a dialog. 
The `DurationPreferenceProjector` and `TextFieldPreferenceProjector` use a dialog.

Use the rememberDialogState() method to maintain the open or closed state of the dialog and pass it to the AlertDialogInteractor.  
Use the openOnClick() method to create a `Modifier` for opening the dialog when the user clicks the preference.

```kotlin
@Composable
fun Duration(painter: Painter) {
    val state = rememberDialogState()
    var amount by remember { mutableStateOf(5) }
    var unit by remember { mutableStateOf(DurationUnit.Days) }
    var duration by remember { mutableStateOf(5.days) }
    DurationPreferenceProjector(
        interactor = DurationPreferenceInteractor(
            amount = amount,
            unit = unit,
            amountRange = 0..10,
            onValueChange = { value -> amount = value },
            units = listOf(DurationUnit.DAYS, DurationUnit.MINUTES),
            unitLabel = { unit -> unit.toString() },
          
            // See compose-resource for these interactors.
            upIcon = upIconInteractor(),
            downIcon = downIconInteractor(),
          
            preference = AlertDialogPreferenceInteractor(
                preference = PreferenceInteractor(
                    painter = painter,
                    title = "Refresh Interval".textInteractor(),
                    subtitle = amount.toDuration(unit).toString().textInteractor()
                ),
                dialog = AlertDialogInteractor.Builder(state) {
                    // Reset to initial
                    amount = 5
                    unit = DurationUnit.Days
                }.biText().build {
                    title = "How often to refresh?"
                    closeOnPositive { 
                        // Save preference
                        duration = amount.toDuration(unit)
                    }
                }
            )
        ),
    ).Projection(modifier = state.openOnClick())
}

@Composable
fun TextField(painter: Painter) {
    val state = rememberDialogState()
    var code by remember { mutableStateOf(1) }
    var editText by remember { mutableStateOf("") }
    TextFieldPreferenceProjector(
        interactor = TextFieldPreferenceInteractor(
            preference = AlertDialogPreferenceInteractor(
                preference = PreferenceInteractor(
                    painter = painter,
                    title = "Country".textInteractor(),
                    subtitle = code.toString().textInteractor()
                ),
                  dialog = AlertDialogInteractor.Builder(state) {
                        logic.clearInput()
                  }.biText().build {
                      title = "What is your country?"
            
                      closeOnPositive {
                          // Save preference
                          code = editText.toInt()
                      }
                  }
                ),
              inputDescription = "Country Code".textInteractor(),
              input = TextFieldInteractor(
                value = editText,
                onValueChange = { value -> editText = value }
              )
          )
    ).Projection(modifier = state.openOnClick())
}
```

##### PreferenceSection
The preferenceColumnProjector() and spacedPreferenceColumnProjector() can be used to provide typical padding used for displaying preferences.

To create a header associated with a group of preferences you can use the `PreferenceSectionProjector`.

```kotlin
@Composable
fun Section(painter: Painter) = PreferenceSectionProjector(
    interactor = PreferenceSectionInteractor(
        title = "Preferences".textInteractor(),
        painter = painter,
    ),
).Projection {
    spacedPreferenceColumnProjector().Projection(
        content = buildList {
            add {
                // First preference
            }
            add {
                // Second preference
            }
        }.toTypedArray()
    )
}
```

#### Scaffold
Scaffolds are used when you need to manage a drawer, app bars, a snackbar, and/or floating action buttons together.

The drawer and snackbar host state can be remembered using the scaffoldInteractor() method.
The `LocalSnackbarHostState` can be used to access the snackbar host state within the content of the scaffold.

```kotlin
@Composable
fun Scaffold(drawer: ModalDrawerInteractor, snackbarHost: SnackbarHostInteractor) = scaffoldInteractor(
    drawer = drawer,
    snackbarHost = snackbarHost,
) { interactor ->
    ScaffoldProjector(
        interactor = interactor.copy(
            topBar = TopAppBarInteractor(
              title = "My App".textInteractor(),
            )
        ),
    ).Projection(modifier = Modifier.fillMaxSize()) {
        val snackbarHost = LocalSnackbarHostState.current
        snackbarHost.showSnackbar(message = "Hello World")
    }
}
```

#### Alerts
The ShowAlert() composable method is used to send notifications. 
* On Android, a Toast is created. Note that the title is ignored.
* On Desktop, a notification is sent to the tray.

```kotlin
@Composable
fun SendMessage() = ShowAlert(title = "Important!", "An error occurred.", type = AlertType.ERROR)
```

## compose-accompanist
A copy of [Accompanist](https://github.com/google/accompanist) modules (v0.23.1 and v0.2.1 [Snapper](https://github.com/chrisbanes/snapper)) with multiplatform capability.
Currently this only includes the pager and pager indicators.

## compose-constraint-layout
A copy of [ConstraintLayout](https://github.com/androidx/constraintlayout) (v2.1.3 core and v1.0.0 compose) with multiplatform capability.

## compose-image
* Conversion of a `ByteArray` to an `androidx.compose.ui.graphics.ImageBitmap`.
* Image fetching using [Ktor](https://ktor.io/docs/welcome.html).
* Caching extensions for [Kodein-DB](https://github.com/Kodein-Framework/Kodein-DB).
* [AsyncImageProjector](#projection) composable.

```kotlin
val client = ImageClient()

@Composable
fun Image(url: String, contentDescription: String, useProgressIndicator: Boolean) = AsyncImageProjector(
    interactor = AsyncImageInteractor(
      url = url,
      getImage = { url -> client.getImage(url) },
      contentDescription = contentDescription,
      loadingProgress = if (useProgressIndicator) ProgressIndicatorInteractor.Default else null
    ),
).Projection(
    modifier = Modifier.size(100.dp)
)
```

## compose-resource
Wrappers for strings and images for the [compose](#compose) module using the [resource](#resource) module.

Conversion of image moko-resources:
```kotlin
val painter = MyResources.images.my_resource.painter()
val localized = MyResources.strings.my_resource.localized()
```

Standardized AlertDialog buttons:
```kotlin
val withConfirmation = AlertDialogInteractor.Builder().uniText().build()
val withConfirmationCancel = AlertDialogInteractor.Builder().biText().build()
val withConfirmationCancelReset = AlertDialogInteractor.Builder().triText().build()
```

[IconInteractor](#projection) wrappers for common use cases such as:
```kotlin
val delete = deleteIconInteractor()
val language = languageIconInteractor()
val settings = settingsIconInteractor()
```

Conversion of text moko-resources:
```kotlin
val text = MyResources.strings.my_resource.textInteractor()
```

## coroutine
[coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

Managing locks by a key:
```kotlin
val lock = LockByKey<Int>()
lock.withLock(1) {
    // Perform
}
```

## datetime
[kotlinx.datetime](https://github.com/Kotlin/kotlinx-datetime)
* Formatting by pattern or style.
* Duration serializer using [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization).

```kotlin
val patternFormatter = PatternDateTimeFormatter("EEEE")
val timeFormatter = FormatStyleDateTimeFormatter(dateStyle = null, timeStyle = FormatStyle.SHORT)
```

## function
General Kotlin standard library extensions.

```kotlin
enum class Position {
    FIRST, SECOND
}

val userFriendly: String = Position.FIRST.userFriendly()
val first: Position = "FIRST".enumValue<Position>()

val items: Array<Position> = buildArray {
    add(Position.FIRST)
    add(Position.SECOND)
}
```

## geometry
Two and three dimensional geometrical objects.

Two dimensional objects include a digon, quadrilateral, 2D point, 2D size.
Three dimensional objects include a 3D point.

## intent
Based on Android [intents](https://developer.android.com/guide/components/intents-filters). Currently supports browser opening and mailto.

```kotlin
Browser.open("https://google.com")

Emailer.send(
  email = Email(
    to = listOf("foo@bar.com", "fizz@buzz.com"),
    cc = listOf("test@test.com"),
    bcc = listOf("bar@baz.com", "test@test.com"),
    subject = "This is a test subject.",
    body = "This is a test body."
  )
)
```

## intl
Internationalization through locale support.

`com.bselzer.ktx.intl.Locale` is added as a non-composable way to handle locale changes. However, it can be converted to `androidx.compose.ui.text.intl.Locale`. See the [compose](#locale) section.


`DefaultLocale` provides the system's default locale. However, there is no ability to be notified of changes to this locale. Instead, a `Localizer` should be used to maintain an instance of a locale and to be able to be notified of changes by adding a listener which can then be used to update the `DefaultLocale` if needed.

## kodein-db
[Kodein-DB](https://github.com/Kodein-Framework/Kodein-DB) caching framework extensions.

* `IdentifiableMetadataExtractor` for `Identifiable` objects from the [value](#value) module to use the identifier's value as the metadata.
* `IdentifierValueConverter` for using an `Identifier.value` as a Value if it is for an Int, Long, or String.
* `DBTransaction` for managing reading and writing a batch to a `DB` instance
    * clear() extension method for deleting all models of a given type
    * findByReferenceId() and findByIds() extension methods for finding all `Identifiable` models based on given ids
    * getById() extension method for finding an `Identifiable` model with a given id or requesting it if it does not exist
    * putMissingById() extension method for finding all `Identifiable` models based on given ids and then requesting those missing to be put into the `DB`

## ktor-client
Client side [Ktor](https://ktor.io/docs/getting-started-ktor-client.html) extensions.

* `KtorConnectivityManager` for determining if an active connection is able to be established.

## library
[Compose-jb](https://github.com/JetBrains/compose-jb) extensions for [AboutLibraries](https://github.com/mikepenz/AboutLibraries)

```kotlin
@Composable
fun Libraries(libraries: List<Library>) = LibraryProjector(
    interactor = LibraryInteractor(
        libraries = libraries
    )
).Projection()
```

Using the `AssetReader` from the [resource](#resource) module, you can use the libraries output from `AboutLibraries` as a moko-resource asset. 

First, you will need to add the following to gradle in order to copy it as a resource. 
```kotlin
val aboutLibrariesResource = task("aboutLibrariesResource") {
    dependsOn("exportLibraryDefinitions")

    copy {
        from("$buildDir\\generated\\aboutLibraries") {
            include("aboutlibraries.json")
        }
        into("$projectDir\\src\\commonMain\\resources\\MR\\assets")
    }
}

tasks.whenTaskAdded {
    if (name == "generateMRcommonMain") {
        dependsOn(aboutLibrariesResource)
    }
}
```

Now you can use the `AssetReader` to read the resource and create the libraries from it.
```kotlin
val libraries = with(AssetReader) {
    val content = MyResources.assets.aboutlibraries.readText()
    Libs.Builder().withJson(content).build().libraries
}
```

## logging
Logging wrapper around [Napier](https://github.com/AAkira/Napier).

## molecule
A copy of [Molecule](https://github.com/cashapp/molecule) v0.2.0 with multiplatform capability.

## preference
AndroidX [Preference](https://developer.android.com/jetpack/androidx/releases/preference/) and [DataStore](https://developer.android.com/jetpack/androidx/releases/datastore) extensions. This is currently not being maintained because it is Android specific.

## resource
[moko-resources](https://github.com/icerockdev/moko-resources) extensions.

* AssetReader to read the text content associated with a moko-resource asset.
* Common strings for the German, English, French, and Spanish languages including:
    * DurationUnit enums
    * [Locale](#intl) for German, English, French, and Spanish language locales only.
    * Boolean to stringResource converter for enabled/disabled.
* Common images taken from [material icons](https://fonts.google.com/icons?selected=Material+Icons) that are not bundled directly with compose.
    * Currently this project is not using the extended material icons set from `org.jetbrains.compose.material:material-icons-extended-desktop`.

## serialization
[kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) extensions.

* `JsonContext` for enum conversion of strings, collections of strings, or maps with string keys. This will convert based on the `@SerialName` associated with the enum, which is different from the [function](#function) conversion that would compare the name of the enum instead.
* `LoggingUnknownChildHandler` for XML deserialization that will log the failed deserialization of a child not defined in your object instead of throwing an exception

## serialization-compose
[kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) extensions for compose related classes.

* `ColorSerializer` for storing the [Hex](#hex) associated with a `androidx.compose.ui.graphics.Color`

## settings
Async wrapper of primitive and serializable [multiplatform-settings](https://github.com/russhwolf/multiplatform-settings) settings.

* Primitive wrappers include Boolean, Double, Float, Int, Long, and String alongside their `Identifier` counterparts from the [value](#value) module.
* Serializable wrappers include a generic `SerializableSetting` by providing the serializer associated with the object, and for the Duration class.

```kotlin
fun lastRefresh(settings: SuspendSettings): Setting<Instant> {
    return SerializableSetting(
        settings = settings,
        key = "LastRefresh",
        defaultValue = Instant.DISTANT_FUTURE, 
        serializer = serializer()
    )
}
```

These wrappers must provide an instance of `SuspendSettings`, a key, and a default value to use when the preference is not set. When calling get() or observe(), this default value will be used as the default instead of null.

| Method | Purpose |
| --- | --- |
| get | Gets the value of the preference or the default value if it does not exist. |
| getOrNull | Gets the value of the preference or null if it does not exist. |
| set | Sets the value of preference and notifies observers about the change. |
| remove | Removes the value of the preference and notifies observers about the change. |
| exists | Used to determine if the preference has been initialized. |
| initialize | Only sets a value if the preference has not been set. |
| observe | Creates a callback flow that listens to preference changes. Uses the default value if the preference does not exist. |
| observeOrNull | Creates a callback flow that listens to preference changes. Uses null if the preference does not exist. |

## settings-compose
[Setting](#settings) to [compose-jb](https://github.com/JetBrains/compose-jb) state converters.

```kotlin
@Composable
fun Observe(setting: Setting<Instant>, nullableSetting: Setting<Instant?>) {
    val defaulted: MutableState<Instant> = setting.safeState()
    val nullable: MutableState<Instant?> = setting.nullState()

    // Keep the ability to be nullable, but the value will be defaulted and not null.
    val defaultedNullable: MutableState<Instant?> = nullableSetting.defaultState()
}
```

`ColorSetting` for `androidx.compose.ui.graphics.Color`.
```kotlin
fun color(settings: SuspendSettings): Setting<Color> = ColorSetting(
    settings = settings,
    key = "Color",
    defaultValue = Color.Blue
)
```

## value
[Value classes](https://kotlinlang.org/docs/inline-classes.html) for an `Identifier` wrapping a Byte, Boolean, Double, Float, Int, Long, Short, or String. The `Identifiable` interface can be used to specify an `Identifier` id.

```kotlin
@Serializable
@JvmInline
value class LuckId(override val value: String = "") : StringIdentifier {
    override fun toString(): String = value
}

@Serializable
data class AccountLuck(
    @SerialName("id")
    override val id: LuckId = LuckId(),

    @SerialName("value")
    val value: Int = 0
) : Identifiable<LuckId, String>
```
