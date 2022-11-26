package com.mhmdawad.memorizevocabulary.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mhmdawad.memorizevocabulary.domain.module.VocabularyModule
import com.mhmdawad.memorizevocabulary.presentation.memorize_screen.MemorizeScreen
import com.mhmdawad.memorizevocabulary.presentation.ui.theme.MemorizeVocabularyTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }
        setContent {
            MemorizeVocabularyTheme {
                    DestinationsNavHost(NavGraphs.root)
                }
        }
    }
}
