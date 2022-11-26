package com.mhmdawad.memorizevocabulary.presentation.new_vocabulary.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.mhmdawad.memorizevocabulary.presentation.ui.theme.OffWhite
import com.mhmdawad.memorizevocabulary.presentation.ui.theme.Typography

@Composable
fun VocabularyTextField(
    labelText: String,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    textState: String,
    onStateChanged: (String) -> Unit,
    onKeyboardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = if (isSystemInDarkTheme()) Color.White else Color.DarkGray,
        backgroundColor = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.4f) else Color.DarkGray.copy(
            alpha = 0.4f),
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        OutlinedTextField(
            value = textState,
            onValueChange = { text ->
                onStateChanged(text)
            },
            label = {
                Text(text = labelText,
                    color = if (isSystemInDarkTheme()) OffWhite else Color.DarkGray)
            },
            singleLine = true,
            modifier = modifier
                .fillMaxWidth(),
            textStyle = Typography.body2,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (isSystemInDarkTheme()) Color.White else Color.DarkGray,
                unfocusedBorderColor = Color.LightGray,
                textColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,

                ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onKeyboardClick()
                },
                onNext = {
                    onKeyboardClick()
                }
        ))
    }
}