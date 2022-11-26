package com.mhmdawad.memorizevocabulary.presentation.memorize_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mhmdawad.memorizevocabulary.R
import com.mhmdawad.memorizevocabulary.common.CardFace
import com.mhmdawad.memorizevocabulary.presentation.MemorizeViewModel
import com.mhmdawad.memorizevocabulary.presentation.ui.theme.Typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VocabularyCard(
    modifier: Modifier = Modifier,
    memorizeViewModel: MemorizeViewModel = hiltViewModel(),
) {
    val randomVocabulary = memorizeViewModel.randomVocabularyState
    val memorizeState = memorizeViewModel.memorizeState
    var cardFace by remember { mutableStateOf(CardFace.Front) }
    val coroutineScope = rememberCoroutineScope()

    FlipCard(
        cardFace = cardFace,
        onClick = { cardFace = cardFace.next },
        onLongClick = {
            coroutineScope.launch {
                cardFace = CardFace.Back.next
                delay(200L)
                memorizeViewModel.getRandomVocabulary()
            }
        },
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .aspectRatio(1f),
        back = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                if (memorizeState.isLoading)
                    CircularProgressIndicator()

                if (memorizeState.isNoVocabularies)
                    Text(text = stringResource(id = R.string.no_vocabularies),
                        style = Typography.body1)

                if (memorizeState.vocabularies.isNotEmpty())
                    Text(text = randomVocabulary.vocabulary.nativeVocabulary,
                        style = Typography.body1)

            }
        },
        front = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                if (memorizeState.isLoading)
                    CircularProgressIndicator()

                if (memorizeState.isNoVocabularies)
                    Text(text = stringResource(id = R.string.no_vocabularies),
                        style = Typography.body1)

                if (memorizeState.vocabularies.isNotEmpty())
                    Text(text = randomVocabulary.vocabulary.englishVocabulary,
                        style = Typography.body1)

            }
        }
    )
}