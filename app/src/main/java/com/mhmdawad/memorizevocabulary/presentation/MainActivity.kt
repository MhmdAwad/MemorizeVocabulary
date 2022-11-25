package com.mhmdawad.memorizevocabulary.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mhmdawad.memorizevocabulary.domain.module.VocabularyModule
import com.mhmdawad.memorizevocabulary.presentation.memorize_screen.MemorizeScreen
import com.mhmdawad.memorizevocabulary.presentation.ui.theme.MemorizeVocabularyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemorizeVocabularyTheme {
                    MemorizeScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                    )
                }
        }
    }
}
