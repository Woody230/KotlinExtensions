package com.bselzer.ktx.intent.browser

import org.junit.Test
import kotlin.test.assertTrue

class BrowserTests {
    @Test
    fun open() {
        assertTrue { Browser.open("https://google.com") }
    }
}