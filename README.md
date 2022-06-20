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
implementation("io.github.woody230.ktx:$Module:$Version")
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

New to Compose?
* [Guidelines](https://github.com/androidx/androidx/blob/androidx-main/compose/docs/compose-api-guidelines.md)
* [Jetbrains Compose Getting Started](https://github.com/JetBrains/compose-jb/tree/master/tutorials/Getting_Started)
* [Jetpack Compose Tutorial](https://developer.android.com/jetpack/compose/tutorial)

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

See the [compose-layout](compose-layout.md) file for documentation on the custom components provided.

### Alerts
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

```kotlin
enum class LegendName {
    @SerialName("Legend1")
    DRAGON,
}

with(JsonContext) {
    val legend: LegendName = "Legend1".enumValue() // LegendName.DRAGON
    val nullableLegend: LegendName? = "Legend2".enumValueOrNull() // Null
}
```

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
