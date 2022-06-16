![](https://img.shields.io/badge/targets-Android%2FJVM-informational)
![](https://img.shields.io/github/v/release/Woody230/KotlinExtensions)
![](https://img.shields.io/github/license/Woody230/KotlinExtensions)

# Kotlin Extensions

Kotlin Multiplatform extensions.

This repository is tailored to the needs of multiple personal projects and may not be suitable for general use as is and in the future.

## Modules

| Name | Supported | Use |
| ---- | ---- | ---- | 
| base64 | ✅ | String/byte array base64 encoding using [kbase64](https://github.com/jershell/kbase64). |
| comparator | ✅ |General object comparators. |
| compose | ✅ | General [compose-jb](https://github.com/JetBrains/compose-jb) extensions. |
| compose-accompanist | ⊝ | Copy of [Accompanist](https://github.com/google/accompanist) modules (v0.23.1 and v0.2.1 [Snapper](https://github.com/chrisbanes/snapper)) with multiplatform capability.  |
| compose-constraint-layout | ⊝ | Copy of [ConstraintLayout](https://github.com/androidx/constraintlayout) (v2.1.3 core and v1.0.0 compose) with multiplatform capability. |
| compose-image | ✅ |[Compose-jb](https://github.com/JetBrains/compose-jb) image loading. |
| compose-resource | ✅ |Wrappers for strings and images for the compose module using the resource module. | 
| coroutine | ✅ | General [coroutine](https://kotlinlang.org/docs/coroutines-overview.html) extensions. |
| datetime | ✅ | General [kotlinx.datetime](https://github.com/Kotlin/kotlinx-datetime) extensions. |
| function | ✅ | General kotlin standard library extensions. |
| geometry | ✅ | Two and three dimensional geometrical objects. |
| intent | ✅ | Based on Android [intents](https://developer.android.com/guide/components/intents-filters). Currently supports browser opening and mailto. |
| intl | ✅ | Internationalization through locale support. | 
| kodein-db | ✅ | General [Kodein-DB](https://github.com/Kodein-Framework/Kodein-DB) extensions. |
| ktor-client | ✅ | General client side [Ktor](https://ktor.io/docs/getting-started-ktor-client.html) extensions. |
| library | ✅ | [Compose-jb](https://github.com/JetBrains/compose-jb) extensions for [AboutLibraries](https://github.com/mikepenz/AboutLibraries).
| livedata | ❌ | General [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) extensions for Android. |
| logging | ✅ | Logging wrapper around [Napier](https://github.com/AAkira/Napier). |
| molecule | ⊝ | Copy of [Molecule](https://github.com/cashapp/molecule) v0.2.0 with multiplatform capability. |
| moshi | ❌ | General [Moshi](https://github.com/square/moshi) extensions. |
| preference | ❌ | General AndroidX [Preference](https://developer.android.com/jetpack/androidx/releases/preference/) and [DataStore](https://developer.android.com/jetpack/androidx/releases/datastore) extensions. |
| resource | ✅ | General [moko-resources](https://github.com/icerockdev/moko-resources) extensions. |
| serialization | ✅ | General [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) extensions. |
| serialization-compose | ✅ | [Kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) extensions for compose related classes. |
| settings | ✅ | Async wrapper of primitive and serializable [multiplatform-settings](https://github.com/russhwolf/multiplatform-settings) settings. |
| settings-compose | ✅ | Setting to [compose-jb](https://github.com/JetBrains/compose-jb) state converters. |
| value | ✅ | [Value class](https://kotlinlang.org/docs/inline-classes.html) extensions. |

## Gradle

```kotlin
implementation("com.bselzer.ktx:$Module:$Version")
```