package com.bselzer.ktx.intent

import java.awt.Desktop

internal abstract class JvmIntent {
    protected val desktop: Desktop?
        get() = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
}