package com.bselzer.ktx.store.sqlite.async

interface ModelEncoder<Model> {
    fun decode(value: String): Model
    fun encode(model: Model): String
}