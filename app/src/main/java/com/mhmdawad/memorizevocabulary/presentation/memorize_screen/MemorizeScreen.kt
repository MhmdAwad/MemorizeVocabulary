package com.mhmdawad.memorizevocabulary.presentation.memorize_screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhmdawad.memorizevocabulary.R
import com.mhmdawad.memorizevocabulary.presentation.destinations.AddNewVocabularyDestination
import com.mhmdawad.memorizevocabulary.presentation.memorize_screen.components.VocabularyCard
import com.mhmdawad.memorizevocabulary.presentation.ui.theme.Typography
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination(start = true)
@Composable
fun MemorizeScreen(
    navigator: DestinationsNavigator,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Color.DarkGray,
                onClick = {
                navigator.navigate(AddNewVocabularyDestination())
            }) {
                Icon(Icons.Filled.Add,
                    null,
                    tint = Color.White)
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = it.calculateBottomPadding()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = Typography.body1,
                    color = if (isSystemInDarkTheme()) Color.White else Color.DarkGray,
                )
                Box(modifier = Modifier
                    .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    VocabularyCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                    )
                }
            }
        })
}