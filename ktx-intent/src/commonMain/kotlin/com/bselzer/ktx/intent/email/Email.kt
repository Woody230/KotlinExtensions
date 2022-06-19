package com.bselzer.ktx.intent.email

data class Email(
    val to: List<String>,
    val cc: List<String> = emptyList(),
    val bcc: List<String> = emptyList(),
    val subject: String? = null,
    val body: String? = null
)