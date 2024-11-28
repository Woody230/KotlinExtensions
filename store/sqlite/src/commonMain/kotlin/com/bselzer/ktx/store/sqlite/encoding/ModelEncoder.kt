package com.bselzer.ktx.store.sqlite.encoding

interface ModelEncoder<Model> {
    fun decode(value: String): Model
    fun encode(model: Model): String
}