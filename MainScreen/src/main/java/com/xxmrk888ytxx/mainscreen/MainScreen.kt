package com.xxmrk888ytxx.mainscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import remember

/**
 * [Ru]
 * Данный экран является стартовым экраном приложения
 * В нем можно включить/отключить работу приложения
 * а так же увидеть список событий за сегодня, и при
 * необходимости открыть полный список.
 * [En]
 * This screen is the start screen of the application.
 * In it you can enable / disable the application
 * as well as see a list of events for today, and when
 * need to open the full list.
 */
@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val buttonState = mainViewModel.isEnable.remember()
    Column(Modifier.fillMaxWidth()) {
        Column(Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StateButton(enabled = buttonState.value) {
                mainViewModel.isEnable.value = !mainViewModel.isEnable.value
            }
        }
    }
}

@Composable
internal fun EventList() {}

@Composable
internal fun StateButton(enabled:Boolean,onClick:() -> Unit) {
    OutlinedButton(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if(enabled) enabledButtonColor else Color.Red.copy(0.9f)
        ),
        shape = CircleShape,
        modifier = Modifier.size(65.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(R.drawable.ic_power_off), 
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(35.dp)
            )   
        }
    }
} 


