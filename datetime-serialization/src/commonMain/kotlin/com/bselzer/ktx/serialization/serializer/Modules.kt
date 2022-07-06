package com.bselzer.ktx.serialization.serializer

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

object Modules {
    val ALL: SerializersModule = SerializersModule {
        contextual(DurationSerializer())
    }
}