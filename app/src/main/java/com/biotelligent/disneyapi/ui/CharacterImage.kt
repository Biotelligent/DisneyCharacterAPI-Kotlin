package com.biotelligent.disneyapi.ui

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.biotelligent.disneyapi.R
import com.biotelligent.disneyapi.ui.theme.GrayBackground
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.RequestBuilderTransform

/**
 * Wrapper around a [GlideImage] so that composable previews work.
 * This can be removed once https://github.com/bumptech/glide/issues/4977 is fixed.
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterImage(
    model: Any?,
    contentDescription: String?,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.FillWidth,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    requestBuilderTransform: RequestBuilderTransform<Drawable> = { it }
) {
    if (isLoading || LocalInspectionMode.current) {
        Image(
            painter = painterResource(id = R.drawable.image_placeholder),
            contentDescription = contentDescription,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale
        )
        return
    }
    GlideImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        requestBuilderTransform = requestBuilderTransform
    )
}

@Preview
@Composable
fun ImagePlaceholderPreview() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(GrayBackground)
    ) {
        CharacterImage(
            model = null,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
    }
}