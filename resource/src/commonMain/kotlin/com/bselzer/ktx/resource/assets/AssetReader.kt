package com.bselzer.ktx.resource.assets

import dev.icerock.moko.resources.AssetResource

interface AssetReader {
    /**
     * Reads the [AssetResource] as text.
     */
    fun AssetResource.readText(): String

    companion object : AssetReader by SystemAssetReader()
}

internal expect class SystemAssetReader() : AssetReader