package com.biotelligent.disneyapi.ui.disneycharacter

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.repeatOnLifecycle
import com.biotelligent.disneyapi.data.local.database.DisneyCharacter
import com.biotelligent.disneyapi.ui.theme.DisneyCharactersTheme
import kotlin.math.absoluteValue

const val MIN_SCALE = 0.75f
const val MIN_ALPHA = 0.5f
const val MAX_ROTATION = 45f
const val TAG = "CharacterScreen"

@Composable
fun DisneyCharacterScreen(
    modifier: Modifier = Modifier,
    viewModel: DisneyCharacterViewModel = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val items by produceState<DisneyCharacterUiState>(
        initialValue = DisneyCharacterUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }
    if (items is DisneyCharacterUiState.Success) {
        CharacterPager(
            items = (items as DisneyCharacterUiState.Success).data,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterPager(
    items: List<DisneyCharacter>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()

    HorizontalPager(pageCount = items.size, state = pagerState) { page ->
        val disneyCharacter = items[page]

        Column(modifier) {
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.White)
                    .padding(bottom = 24.dp)
                    .graphicsLayer {
                        val pageDirection = pagerState.targetPage - page

                        val position =
                            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                        val pageOffset =
                            (
                                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                                ).absoluteValue

                        val scale =
                            lerp(
                                start = MIN_SCALE,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        val rotation =
                            lerp(
                                start = 0f,
                                stop = MAX_ROTATION,
                                fraction = position
                            )
                        alpha =
                            lerp(
                                start = MIN_ALPHA,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )

                        Log.d(
                            TAG,
                            "PagerState direction: $pageDirection  page: $page position $position scale: $scale rotation: $rotation currentPage ${pagerState.currentPage} target ${pagerState.targetPage} offset: ${pagerState.currentPageOffsetFraction}"
                        )

                        rotationZ = -rotation
                        scaleX = scale
                        scaleY = scale
                    }
            ) {
                DisneyCharacterContent(disneyCharacter = disneyCharacter, imageHeight = 300.dp)
            }
        }
    }
}

// Preview
// TODO: set up AnimateFloatAsState so animation can be inspected in the preview .
// such as val rotationAngle by animateFloatAsState(0f)

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    DisneyCharactersTheme {
    }
}