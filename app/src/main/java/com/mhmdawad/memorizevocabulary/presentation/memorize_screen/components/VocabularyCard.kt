package com.mhmdawad.memorizevocabulary.presentation.memorize_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mhmdawad.memorizevocabulary.R
import com.mhmdawad.memorizevocabulary.presentation.MemorizeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VocabularyCard(
    modifier: Modifier = Modifier,
    memorizeViewModel: MemorizeViewModel = hiltViewModel(),
){
    val randomVocabulary = memorizeViewModel.randomVocabularyState
    val memorizeState = memorizeViewModel.memorizeState

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.DarkGray)
            .combinedClickable(
                onClick = {
                    memorizeViewModel.getRandomVocabulary()
                },
                onLongClick = {
                    memorizeViewModel.rotateVocabulary()
                },
            ),
        contentAlignment = Alignment.Center
    ) {
        if (memorizeState.isLoading)
            CircularProgressIndicator()

        if (memorizeState.isNoVocabularies)
            Text(text = stringResource(id = R.string.no_vocabularies),
                style = TextStyle(fontSize = 26.sp))

        if (memorizeState.vocabularies.isNotEmpty())
            randomVocabulary.let {
                Text(text = if (it.showEnglishVocabulary)
                    it.vocabulary.englishVocabulary
                else it.vocabulary.nativeVocabulary,
                    style = TextStyle(fontSize = 26.sp))
            }

    }
}