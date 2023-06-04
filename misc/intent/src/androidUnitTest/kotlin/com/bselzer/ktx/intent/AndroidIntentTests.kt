package com.bselzer.ktx.intent

import android.content.pm.ProviderInfo
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import kotlin.reflect.KClass

@RunWith(AndroidJUnit4::class)
abstract class AndroidIntentTests<Intent> where Intent : AndroidIntent {
    protected abstract val intentClass: KClass<Intent>
    protected lateinit var intent: Intent

    @Before
    fun setup() {
        val info = ProviderInfo().apply {
            authority = "authority"
        }

        intent = Robolectric
            .buildContentProvider(intentClass.java)
            .create(info)
            .get()
    }
}