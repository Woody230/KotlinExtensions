package com.bselzer.ktx.intent.browser

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bselzer.ktx.intent.AndroidIntentTests
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.reflect.KClass
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
internal class BrowserTests : AndroidIntentTests<SystemBrowser>() {
    override val intentClass: KClass<SystemBrowser> = SystemBrowser::class

    @Test
    fun open() {
        assertTrue { intent.open("https://google.com") }
    }
}