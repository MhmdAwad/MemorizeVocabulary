package com.mhmdawad.memorizevocabulary.presentation.new_vocabulary

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.mhmdawad.memorizevocabulary.R
import com.mhmdawad.memorizevocabulary.presentation.MemorizeViewModel
import com.mhmdawad.memorizevocabulary.presentation.new_vocabulary.components.VocabularyTextField
import com.mhmdawad.memorizevocabulary.presentation.ui.theme.Typography
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun AddNewVocabulary(
    navigator: DestinationsNavigator,
    memorizeViewModel: MemorizeViewModel = hiltViewModel(),
) {
    val localFocusManager = LocalFocusManager.current
    var englishTextState by remember { mutableStateOf("") }
    var nativeTextState by remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    val addVocabularyState = memorizeViewModel.addVocabularyState

    LaunchedEffect(key1 = addVocabularyState) {
        if (addVocabularyState.errorMessage.isNotBlank()) {
            scaffoldState.snackbarHostState.showSnackbar(addVocabularyState.errorMessage)
        } else if (addVocabularyState.isInsertedSuccessfully) {
            englishTextState = ""
            nativeTextState = ""
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = DarkGray,
                onClick = {
                    memorizeViewModel.insertVocabulary(englishTextState, nativeTextState)
                }) {
                Icon(Icons.Default.Done,
                    null,
                    tint = White)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp + it.calculateBottomPadding())
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 30.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navigator.popBackStack()
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = if (isSystemInDarkTheme()) White else DarkGray
                    )
                }
                Text(
                    text = stringResource(id = R.string.add_new_vocabulary),
                    style = Typography.body2.copy(fontSize = 20.sp),
                    color = if (isSystemInDarkTheme()) White else DarkGray,)
            }
            VocabularyTextField(
                labelText = stringResource(id = R.string.add_english_vocabulary),
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                textState = englishTextState,
                onStateChanged = { text ->
                    englishTextState = text
                },
                onKeyboardClick = {
                    localFocusManager.moveFocus(FocusDirection.Down)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            VocabularyTextField(
                labelText = stringResource(id = R.string.add_your_native_vocabulary),
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                textState = nativeTextState,
                onStateChanged = { text ->
                    nativeTextState = text
                },
                onKeyboardClick = {
                    localFocusManager.clearFocus()
                },
            )
        }
        if (addVocabularyState.isLoading) {
            Dialog(
                onDismissRequest = { },
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Box(
                    contentAlignment = Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            if (isSystemInDarkTheme()) White else DarkGray,
                            shape = RoundedCornerShape(8.dp))
                ) {
                    CircularProgressIndicator(
                        color = if (isSystemInDarkTheme()) DarkGray else White,
                    )
                }
            }
        }
    }
}