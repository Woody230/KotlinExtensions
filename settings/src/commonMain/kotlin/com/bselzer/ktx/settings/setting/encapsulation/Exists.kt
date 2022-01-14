package com.bselzer.ktx.settings.setting.encapsulation

interface Exists {
    suspend fun exists(): Boolean
}