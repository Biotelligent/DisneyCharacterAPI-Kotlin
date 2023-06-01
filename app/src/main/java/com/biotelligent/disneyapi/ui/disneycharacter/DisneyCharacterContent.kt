package com.biotelligent.disneyapi.ui.disneycharacter

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import com.biotelligent.disneyapi.data.local.database.DisneyCharacter
import com.biotelligent.disneyapi.ui.CharacterImage
import com.biotelligent.disneyapi.ui.Dimens
import com.biotelligent.disneyapi.ui.theme.GrayBackground
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@Composable
fun DisneyCharacterContent(
    disneyCharacter: DisneyCharacter,
    imageHeight: Dp,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(GrayBackground)

    ) {
        ConstraintLayout {
            val (image, info) = createRefs()

            CharacterImageHolder(
                imageUrl = disneyCharacter.imageUrl.toUri().toString(),
                imageHeight = imageHeight,
                modifier = Modifier.constrainAs(image) { top.linkTo(parent.top) }
            )

            CharacterInformation(
                disneyCharacter = disneyCharacter,
                modifier = Modifier.constrainAs(info) {
                    top.linkTo(image.bottom)
                }
            )

        }
    }
}

@Composable
private fun CharacterImageHolder(
    imageUrl: String,
    imageHeight: Dp,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }
    Box(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .height(imageHeight)
    ) {
        CharacterImage(
            model = imageUrl,
            contentDescription = null,
            isLoading = isLoading,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
        ) {
            it.addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    isLoading = false
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    isLoading = false
                    return false
                }
            })
        }
    }
}

@Composable
private fun CharacterInformation(
    disneyCharacter: DisneyCharacter,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(color = Color.White)) {
        Text(
            text = disneyCharacter.name,
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.headlineMedium,
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = Dimens.PaddingNormal, vertical = Dimens.PaddingSmall)
        )

        if (disneyCharacter.popularity == 0) {
            headerText(title = "NO FILMS YET (POPULARITY: ${disneyCharacter.popularity})")
        } else {
            CharacterItemsInformation("FILMS", disneyCharacter.films)
            CharacterItemsInformation("SHORT FILMS", disneyCharacter.shortFilms)
            CharacterItemsInformation("PARK ATTRACTIONS", disneyCharacter.parkAttractions)
        }
    }
}

@Composable
private fun CharacterItemsInformation(
    title: String,
    items: List<String>
) {
    if (items.isNotEmpty()) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(color = Color.White)
        ) {
            headerText(title)

            items.forEach {
                Text(
                    text = it,
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.PaddingNormal,
                            vertical = Dimens.PaddingSmall
                        )
                        .fillMaxWidth()
                        .align(Alignment.Start)
                )

                Box(
                    Modifier
                        .background(color = Color.Black)
                        .height(1.dp)
                        .fillMaxWidth()
                )
            }
            Box(
                Modifier
                    .background(GrayBackground)
                    .height(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun headerText(title: String) {
    Column() {
        Text(
            text = title.uppercase(),
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(GrayBackground)
                .padding(horizontal = Dimens.PaddingNormal, vertical = Dimens.PaddingSmall)
                .fillMaxWidth()
                .align(Alignment.Start)
        )
    }
}

@Preview
@Composable
private fun DisneyCharacterContentPreview(
) {
    DisneyCharacterContent(
        jessicaRabbit,
        imageHeight = 300.dp
    )
}

// Populate a DisneyCharacter with dummy data for previewing
private val jessicaRabbit = DisneyCharacter(
    id = 6,
    name = "Jessica Rabbit",
    imageUrl = "https://static.wikia.nocookie.net/disney/images/2/2c/A.J._Arno.jpg",
    films = listOf("Lilo & Stitch: The Series", "Who Framed Roger Rabbit?"),
    shortFilms = listOf("Honey, I Shrunk the Audience!"),
    updatedAt = "2021-12-20T20:39:18.032Z",
    url = null,
    parkAttractions = listOf()
)
