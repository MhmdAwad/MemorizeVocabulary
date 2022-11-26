package com.mhmdawad.memorizevocabulary.presentation.memorize_screen.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.mhmdawad.memorizevocabulary.common.CardFace

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FlipCard(
    cardFace: CardFace,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
) {
    val rotation by animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing,
        )
    )
    Card(
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (rotation <= 90f) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ){
                    front()
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            rotationY = 180f
                        }
                ) {
                    back()
                }
            }
        }
    }
}