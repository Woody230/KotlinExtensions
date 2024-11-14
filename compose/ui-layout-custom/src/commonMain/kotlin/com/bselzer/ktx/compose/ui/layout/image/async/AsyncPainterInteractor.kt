package com.bselzer.ktx.compose.ui.layout.image.async

import androidx.compose.ui.graphics.painter.Painter
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.progress.indicator.ProgressIndicatorInteractor
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class AsyncPainterInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The url to retrieve the image from.
     */
    val url: String,

    /**
     * Text used by accessibility services to describe what this image represents.
     * This should always be provided unless this image is used for decorative purposes, and does not represent a meaningful action that a user can take.
     * This text should be localized, such as by using androidx.compose.ui.res.stringResource or similar
     */
    override val contentDescription: String?,

    /**
     * The delegate for retrieving the image from the [url].
     */
    val getImage: suspend (String) -> Painter?,

    /**
     * The [ImageInteractor] to display when the [url] is being loaded.
     */
    override val loadingImage: ImageInteractor? = null,

    /**
     * The [ProgressIndicatorInteractor] to display when the [loadingImage] is null.
     */
    override val loadingProgress: ProgressIndicatorInteractor? = null,

    /**
     * The [ImageInteractor] to display when the [url] is unable to be loaded.
     */
    override val failedImage: ImageInteractor? = null
) : AsyncImageInteractor(modifier)