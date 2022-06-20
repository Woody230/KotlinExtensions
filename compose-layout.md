# Models
[kirill-grouchnikov's Aurora](https://github.com/kirill-grouchnikov/aurora/blob/icicle/docs/component/Intro.md)

A **content model** describes the basic elements of a piece of your model realm, how the user interacts with it, and what happens when that interaction happens.

A **presentation model** describes how to "convert" (or project) that content model into a composable that can be added to the application UI hierarchy to present the data backed by that content model and react to the user interaction.

A **projection** is the act of "combining" a content model and a presentation model and creating a composable.

In a nutshell:

- **content model + presentation model &#8594; projection**
- **projection &#8594; one or more composables**

## Projection
Within this project, the content model is referred to as an `Interactor`, the presentation model as a `Presenter`, and the projection as a `Projector`.

### Composable
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

### Modifiable

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

### Merging
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

# Components

## AlertDialog
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

## SingleChoice
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

## AppBar
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

## BackgroundImage
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

## Column/Row
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

## Drawer

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

## DropdownMenu
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

## LazyColumn/LazyRow
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

## Picker
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

## Preference
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
### AlertDialogPreference
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

### PreferenceSection
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

## Scaffold
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