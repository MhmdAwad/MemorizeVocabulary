package com.mhmdawad.memorizevocabulary.presentation.memorize_screen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.mhmdawad.memorizevocabulary.R
import com.mhmdawad.memorizevocabulary.presentation.memorize_screen.MemorizeScreenState
import com.mhmdawad.memorizevocabulary.presentation.memorize_screen.RandomVocabularyState
import com.mhmdawad.memorizevocabulary.presentation.ui.theme.Typography

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VocabularyData(
    memorizeState: MemorizeScreenState,
    vocabularyText: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        if (memorizeState.isNoVocabularies && !memorizeState.isLoading) {
            Text(text = stringResource(id = R.string.no_vocabularies),
                style = Typography.body1)
        }

        if (memorizeState.vocabularies.isNotEmpty())
            AnimatedContent(targetState = vocabularyText) { animatedVocabularyText ->
                Text(text = animatedVocabularyText,
                    style = Typography.body1,)
            }

        if (memorizeState.isLoading)
            CircularProgressIndicator(color = Color.White)

    }
}