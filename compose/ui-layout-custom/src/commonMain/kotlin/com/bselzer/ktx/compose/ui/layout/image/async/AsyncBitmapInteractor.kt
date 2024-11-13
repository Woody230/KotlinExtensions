package com.bselzer.ktx.compose.ui.layout.image.async

import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.IntOffset
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.progress.indicator.ProgressIndicatorInteractor

data class AsyncBitmapInteractor(
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
     * Optional offset relative to image used to draw a subsection of the ImageBitmap. By default this uses the origin of image
     */
    val srcOffset: IntOffset = IntOffset.Zero,

    /**
     * Sampling algorithm applied to the image when it is scaled and drawn into the destination.
     * The default is FilterQuality.Low which scales using a bilinear sampling algorithm
     */
    val filterQuality: FilterQuality = FilterQuality.Low,

    /**
     * The delegate for retrieving the image from the [url].
     */
    val getImage: suspend (String) -> ByteArray?,

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
): AsyncImageInteractor(modifier)