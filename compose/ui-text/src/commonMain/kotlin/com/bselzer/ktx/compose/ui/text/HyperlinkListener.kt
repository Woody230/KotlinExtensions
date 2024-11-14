package com.bselzer.ktx.compose.ui.text

fun interface HyperlinkListener {
    /**
     * Triggered when a user clicks on the hyperlink.
     */
    fun onClick(hyperlink: String)
}