package com.mhmdawad.memorizevocabulary.presentation.memorize_screen.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mhmdawad.memorizevocabulary.common.CardFace
import com.mhmdawad.memorizevocabulary.presentation.MemorizeViewModel
import kotlinx.coroutines.launch

@Composable
fun VocabularyCard(
    front: @Composable () -> Unit,
    back: @Composable () -> Unit,
    onCardFaceChange: () -> Unit,
    modifier: Modifier = Modifier,
    cardFace: CardFace,
) {

    FlipCard(
        cardFace = cardFace,
        onClick = { onCardFaceChange() },
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .aspectRatio(1f),
        back = {
            back()
        },
        front = {
            front()
        }
    )
}

