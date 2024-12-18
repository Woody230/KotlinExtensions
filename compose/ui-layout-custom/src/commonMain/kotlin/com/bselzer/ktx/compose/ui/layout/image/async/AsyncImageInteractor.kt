package com.bselzer.ktx.compose.ui.layout.image.async

import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.progress.indicator.ProgressIndicatorInteractor
import com.bselzer.ktx.compose.ui.layout.project.Interactor

abstract class AsyncImageInteractor(
    override val modifier: InteractableModifier = InteractableModifier,
) : Interactor(modifier) {
    /**
     * Text used by accessibility services to describe what this image represents.
     * This should always be provided unless this image is used for decorative purposes, and does not represent a meaningful action that a user can take.
     * This text should be localized, such as by using androidx.compose.ui.res.stringResource or similar
     */
    abstract val contentDescription: String?

    /**
     * The [ImageInteractor] to display when the image is being loaded.
     */
    abstract val loadingImage: ImageInteractor?

    /**
     * The [ProgressIndicatorInteractor] to display when the [loadingImage] is null.
     */
    abstract val loadingProgress: ProgressIndicatorInteractor?

    /**
     * The [ImageInteractor] to display when the image is unable to be loaded.
     */
    abstract val failedImage: ImageInteractor?
}