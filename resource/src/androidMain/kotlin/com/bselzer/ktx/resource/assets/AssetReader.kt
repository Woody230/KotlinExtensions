package com.bselzer.ktx.resource.assets

import com.bselzer.ktx.intent.AndroidIntent
import dev.icerock.moko.resources.AssetResource

internal actual class SystemAssetReader actual constructor() : AssetReader, AndroidIntent() {
    override fun AssetResource.text(): String = readText(requireApplicationContext())
}