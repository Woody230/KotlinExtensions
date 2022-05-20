package com.bselzer.ktx.intent.email

import kotlin.test.Test
import kotlin.test.assertTrue

class EmailerTests {
    @Test
    fun sendFull() {
        assertTrue {
            Emailer.send(
                email = Email(
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
            Emailer.send(
                email = Email(
                    to = emptyList()
                )
            )
        }
    }
}