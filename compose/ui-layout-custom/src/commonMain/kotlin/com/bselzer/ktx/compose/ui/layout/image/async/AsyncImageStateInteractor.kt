package com.bselzer.ktx.compose.ui.layout.image.async

import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.progress.indicator.ProgressIndicatorInteractor

data class AsyncImageStateInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The result of fetching the image.
     */
    val state: AsyncImageResult,

    /**
     * Text used by accessibility services to describe what this image represents.
     * This should always be provided unless this image is used for decorative purposes, and does not represent a meaningful action that a user can take.
     * This text should be localized, such as by using androidx.compose.ui.res.stringResource or similar
     */
    override val contentDescription: String?,

    /**
     * The [ImageInteractor] to display when the image is being loaded.
     */
    override val loadingImage: ImageInteractor? = null,

    /**
     * The [ProgressIndicatorInteractor] to display when the [loadingImage] is null.
     */
    override val loadingProgress: ProgressIndicatorInteractor? = null,

    /**
     * The [ImageInteractor] to display when the image is unable to be loaded.
     */
    override val failedImage: ImageInteractor? = null
): AsyncImageInteractor(modifier)