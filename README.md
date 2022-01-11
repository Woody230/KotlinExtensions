![](https://img.shields.io/badge/targets-Android%2FJVM-informational)
![](https://img.shields.io/github/v/release/Woody230/KotlinExtensions)
![](https://img.shields.io/github/license/Woody230/KotlinExtensions)

# Kotlin Extensions

Kotlin Multiplatform extensions.

This repository is tailored to the needs of multiple personal projects and may not be suitable for general use as is and in the future.

## Modules

| Name | Use |
| ---- | --- |
| base64 | String/byte array base64 encoding using [kbase64](https://github.com/jershell/kbase64). |
| comparator | General object comparators. |
| compose-accompanist | Copy of [Accompanist](https://github.com/google/accompanist) modules with multiplatform capability. |
| compose-image | [Compose-jb](https://github.com/JetBrains/compose-jb) image loading. |
| compose | General [compose-jb](https://github.com/JetBrains/compose-jb) extensions. |
| coroutine | General [coroutine](https://kotlinlang.org/docs/coroutines-overview.html) extensions. |
| datetime | General [kotlinx.datetime](https://github.com/Kotlin/kotlinx-datetime) extensions. |
| function | General kotlin standard library extensions. |
| geometry | Two and three dimensional geometrical objects. |
| kodein-db | General [Kodein-DB](https://github.com/Kodein-Framework/Kodein-DB) extensions. |
| library | [Compose-jb](https://github.com/JetBrains/compose-jb) extensions for [AboutLibraries](https://github.com/mikepenz/AboutLibraries).
| livedata | **LEGACY** General [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) extensions for Android. |
| logging | Logging wrapper around [Napier](https://github.com/AAkira/Napier). |
| moshi | **LEGACY** General [Moshi](https://github.com/square/moshi) extensions. |
| preference | **LEGACY** General AndroidX [Preference](https://developer.android.com/jetpack/androidx/releases/preference/) and [DataStore](https://developer.android.com/jetpack/androidx/releases/datastore) extensions. |
| serialization | General [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) extensions. |
| settings-compose | Setting to [compose-jb](https://github.com/JetBrains/compose-jb) state converters.
| settings | Async wrapper of primitive and serializable [multiplatform-settings](https://github.com/russhwolf/multiplatform-settings) settings. |

## Gradle

```kotlin
implementation("com.bselzer.ktx:$Module:$Version")
```