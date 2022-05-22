package com.bselzer.ktx.resource.assets

import dev.icerock.moko.resources.AssetResource

internal actual class SystemAssetReader : AssetReader {
    override fun AssetResource.text(): String = readText()
}