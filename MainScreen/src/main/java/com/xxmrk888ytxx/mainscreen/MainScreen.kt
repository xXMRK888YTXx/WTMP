package com.xxmrk888ytxx.mainscreen

import MutliUse.GradientButton
import MutliUse.LazySpacer
import SharedInterfaces.Navigator
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import remember
import theme.*

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
fun MainScreen(mainViewModel: MainViewModel,navigator: Navigator) {
    val isEnable = mainViewModel.isEnable.remember()
    LazyColumn(modifier = Modifier.padding(5.dp)) {
        item { TopBar() }
        item {
            EnableAppButton(isEnable = isEnable.value, onClick = {
                mainViewModel.isEnable.value = !mainViewModel.isEnable.value
            })
        }
    }
}

@Composable
@MustBeLocalization
internal fun EnableAppButton(isEnable:Boolean,onClick:() -> Unit) {
    val fontColor = if(isEnable) disableAppButtonFontColor else enableAppButtonFontColor

    GradientButton(
        backgroundGradient = if(isEnable) disableAppButtonColor else enableAppButtonColor,
        shape = RoundedCornerShape(20.dp),
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_power_off),
                contentDescription = "",
                tint = fontColor,
                modifier = Modifier.size(25.dp)
            )

            LazySpacer(width = 10)

            Text(
                text = if(isEnable) "Выключить" else "Включить",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                fontFamily = openSansFont,
                color = fontColor
            )

        }
    }
}

@Composable
@MustBeLocalization
internal fun TopBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Отчёты",
            fontFamily = openSansFont,
            fontWeight = FontWeight.W600,
            fontSize = 35.sp,
            color = primaryFontColor
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(R.drawable.ic_settings),
                    contentDescription = "",
                    tint = primaryFontColor,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}



