package com.bselzer.ktx.compose.resource.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import dev.icerock.moko.resources.ImageResource

@Composable
fun ImageResource.vector(): ImageVector = ImageVector.vectorResource(id = drawableResId)