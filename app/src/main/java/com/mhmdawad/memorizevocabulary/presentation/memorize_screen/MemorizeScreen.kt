package com.mhmdawad.memorizevocabulary.presentation.memorize_screen

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mhmdawad.memorizevocabulary.R
import com.mhmdawad.memorizevocabulary.common.CardFace
import com.mhmdawad.memorizevocabulary.presentation.MemorizeViewModel
import com.mhmdawad.memorizevocabulary.presentation.destinations.AddNewVocabularyDestination
import com.mhmdawad.memorizevocabulary.presentation.memorize_screen.components.VocabularyCard
import com.mhmdawad.memorizevocabulary.presentation.memorize_screen.components.VocabularyData
import com.mhmdawad.memorizevocabulary.presentation.ui.theme.Typography
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@Destination(start = true)
@Composable
fun MemorizeScreen(
    navigator: DestinationsNavigator,
    memorizeViewModel: MemorizeViewModel = hiltViewModel(),
) {
    val randomVocabulary = memorizeViewModel.randomVocabularyState
    val memorizeState = memorizeViewModel.memorizeState
    var cardFace by remember { mutableStateOf(CardFace.Front) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = DarkGray,
                onClick = {
                    navigator.navigate(AddNewVocabularyDestination())
                }) {
                Icon(Icons.Filled.Add,
                    null,
                    tint = White)
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = it.calculateBottomPadding())
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            val x = dragAmount.x
                            if (x != 0f) {
                                if (cardFace == CardFace.Front.next) {
                                    cardFace = CardFace.Back.next
                                }
                                memorizeViewModel.getRandomVocabulary(delayTime = 200L)
                            }
                        }
                    },
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = Typography.body1,
                    color = if (isSystemInDarkTheme()) White else DarkGray,
                )
                Box(modifier = Modifier
                    .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    VocabularyCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp),
                        cardFace = cardFace,
                        onCardFaceChange = { cardFace = cardFace.next },
                        front = {
                            VocabularyData(
                                memorizeState = memorizeState,
                                vocabularyText = randomVocabulary.vocabulary.englishVocabulary
                            )
                        },
                        back = {
                            VocabularyData(
                                memorizeState = memorizeState,
                                vocabularyText = randomVocabulary.vocabulary.nativeVocabulary
                            )
                        },
                    )
                }
            }
        })
}

