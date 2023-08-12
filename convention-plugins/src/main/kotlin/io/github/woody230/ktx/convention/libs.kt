package io.github.woody230.ktx.convention

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project

// TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
internal val Project.libs: LibrariesForLibs
    // TODO must use root project: extension libs does not exist https://github.com/gradle/gradle/issues/18237
    get() = rootProject.extensions.getByName("libs") as org.gradle.accessors.dm.LibrariesForLibs