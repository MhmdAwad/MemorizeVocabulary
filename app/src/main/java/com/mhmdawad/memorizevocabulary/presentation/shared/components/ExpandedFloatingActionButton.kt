package com.mhmdawad.memorizevocabulary.presentation.shared.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mhmdawad.memorizevocabulary.R
import com.mhmdawad.memorizevocabulary.presentation.MemorizeViewModel
import com.mhmdawad.memorizevocabulary.presentation.destinations.AddNewVocabularyDestination
import com.mhmdawad.memorizevocabulary.presentation.ui.theme.Typography
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


enum class FloatingActionButtonState {
    DEFAULT, EXPANDED
}

class MultiFabItem(
    val identifier: String,
    @DrawableRes val icon: Int,
    val label: String,
)

@Composable
fun ExpandedFloatingActionButton(
    navigator: DestinationsNavigator,
    memorizeViewModel: MemorizeViewModel = hiltViewModel(),
) {
    var toState by remember { mutableStateOf(FloatingActionButtonState.DEFAULT) }
    val transition = updateTransition(targetState = toState, label = "")
    val rotation by transition.animateFloat(label = "") { state ->
        if (state == FloatingActionButtonState.EXPANDED) 45f else 0f
    }
    val scale: Float by transition.animateFloat(label = "") { state ->
        if (state == FloatingActionButtonState.EXPANDED) LocalDensity.current.run { 24.dp.toPx() } else 0f
    }
    val alpha: Float by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 50)
        }, label = ""
    ) { state ->
        if (state == FloatingActionButtonState.EXPANDED) 1f else 0f
    }
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        if (scale != 0f) {
            MultipleFAB(MultiFabItem(
                "1",
                R.drawable.plus,
                "Add new vocabulary"
            ), scale, alpha) {
                navigator.navigate(AddNewVocabularyDestination())
            }
            MultipleFAB(MultiFabItem("1",
                R.drawable.sync,
                "Sync from spreadsheet"), scale, alpha) {
                toState = FloatingActionButtonState.DEFAULT
                memorizeViewModel.syncFromSpreadSheetToDb()
            }
        }
        FloatingActionButton(
            backgroundColor = DarkGray,
            onClick = {
                toState =
                    if (transition.currentState == FloatingActionButtonState.EXPANDED)
                        FloatingActionButtonState.DEFAULT
                    else
                        FloatingActionButtonState.EXPANDED

            }) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier
                    .rotate(rotation),
                tint = White
            )
        }
    }
}

@Composable
fun MultipleFAB(
    item: MultiFabItem,
    scale: Float,
    alpha: Float,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val resource = LocalContext.current.resources
    val icon = remember {
        ImageBitmap.imageResource(resource, item.icon)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .padding(bottom = 20.dp)
    ) {
        if (item.label.isNotBlank()) {
            Text(text = item.label, style = Typography.body2,
                modifier = Modifier.alpha(animateFloatAsState(targetValue = alpha).value))
            Spacer(modifier = Modifier.width(28.dp))
        }
        Canvas(modifier = Modifier
            .padding(end = 13.dp)
            .size(32.dp)
            .clickable(
                onClick = { onClick() },
                indication = rememberRipple(
                    bounded = false,
                    radius = 25.dp,
                    color = White
                ),
                interactionSource = interactionSource
            )) {
            drawCircle(
                DarkGray,
                center = Offset(center.x, center.y),
                radius = scale,
            )
            drawImage(
                icon,
                topLeft = Offset(
                    (this.center.x) - (icon.width / 2),
                    (this.center.y) - (icon.width / 2)
                ),
                alpha = alpha
            )
        }
    }
}