package com.bselzer.ktx.intent.email

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bselzer.ktx.intent.AndroidIntentTests
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.reflect.KClass
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
internal class EmailerTests : AndroidIntentTests<SystemEmailer>() {
    override val intentClass: KClass<SystemEmailer> = SystemEmailer::class

    @Test
    fun sendFull() {
        assertTrue {
            intent.send(
                email = com.bselzer.ktx.intent.email.Email(
                    to = listOf("foo@bar.com", "fizz@buzz.com"),
                    cc = listOf("test@test.com"),
                    bcc = listOf("bar@baz.com", "test@test.com"),
                    subject = "This is a test subject.",
                    body = "This is a test body."
                )
            )
        }
    }

    @Test
    fun sendEmpty() {
        assertTrue {
            intent.send(
                email = com.bselzer.ktx.intent.email.Email(
                    to = emptyList()
                )
            )
        }
    }
}