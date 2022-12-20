package com.xxmrk888ytxx.mainscreen

import MutliUse.*
import SharedInterfaces.Navigator
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
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
@OptIn(ExperimentalFoundationApi::class)
@Composable
@MustBeLocalization
fun MainScreen(mainViewModel: MainViewModel,navigator: Navigator) {
    val isEnable = mainViewModel.isEnable.remember()
    val eventList = mainViewModel.dayEventList.collectAsState(listOf())
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)) {
        item { TopBar(navigator) }

        item {
            EnableAppButton(isEnable = isEnable.value, onClick = {
                mainViewModel.isEnable.value = !mainViewModel.isEnable.value
            })
        }

//        item {
//            LazySpacer(5)
//            StatsCard()
//            LazySpacer(5)
//        }

        item {
            Text(
                text = "События за сегодня",
                fontFamily = openSansFont,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp,
                color = primaryFontColor,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        if(eventList.value.isNotEmpty()) {
            items(eventList.value, key = { it.eventId }) { event ->
                Box(Modifier.animateItemPlacement()) {
                    when(event) {
                        is DeviceEvent.AttemptUnlockDevice -> AttemptUnlockDeviceItem(event)
                        is DeviceEvent.AppOpen -> AppOpenItem(event)
                    }
                }
            }
        }
        else {
            item {
                LazySpacer(5)
                EventListStub()
                LazySpacer(5)
            }
        }

        item {
            LazySpacer(10)
            ShowAllEventButton(navigator)
            LazySpacer(10)
        }

    }
}

@Composable
@MustBeLocalization
internal fun EventListStub() {
    Text(
        text = "За сегодня событий нет",
        fontFamily = openSansFont,
        fontWeight = FontWeight.W900,
        fontSize = 28.sp,
        color = primaryFontColor,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontStyle = FontStyle.Italic
    )
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
internal fun TopBar(navigator: Navigator) {
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
            IconButton(onClick = navigator::toSettingsScreen) {
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

@Composable
@MustBeLocalization
internal fun ShowAllEventButton(navigator: Navigator) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        StyleButton(
            text = "Посмотреть за всё время",
            onClick = navigator::toEventListScreen
        )
    }
}

@Composable
@MustBeLocalization
internal fun StatsCard() {
    Card(
        backgroundColor = cardColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end = 15.dp,
            )
            .heightIn(min = 100.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(Modifier
            .fillMaxSize()
            .padding(10.dp)
        ) {
            Text(
                text = "Статистика",
                fontFamily = openSansFont,
                maxLines = 1,
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                color = primaryFontColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            LazySpacer(5)
            StatsEvent(Color.Green, statsName = "Разблокировок устройства", statsCount = 100)
            LazySpacer(5)
            StatsEvent(Color.Red, statsName = "Ошибок ввода пароля", statsCount = 100)
            LazySpacer(5)
            StatsEvent(Color.White, statsName = "Разблокировок устройства", statsCount = 1000)
            LazySpacer(5)
            StatsEvent(Color.Cyan, statsName = "Открыто приложений", statsCount = 0)
        }
    }
}

@Composable
internal fun StatsEvent(colorCircle:Color,statsName:String,statsCount:Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier, onDraw = {
            drawCircle(colorCircle,10f)
        })
        LazySpacer(width = 10)
        Text(
            text = "$statsName: $statsCount",
            fontFamily = openSansFont,
            maxLines = 1,
            fontSize = 15.sp,
            fontWeight = FontWeight.W300,
            color = primaryFontColor.copy(0.9f),
        )
    }
}

//@Composable
//@MustBeLocalization
//internal fun DeviceEventList(eventList:List<DeviceEvent>) {
//    LazyColumn() {
//        item {
//            Text(
//                text = "События за сегодня",
//                fontFamily = openSansFont,
//                fontWeight = FontWeight.W600,
//                fontSize = 20.sp,
//                color = primaryFontColor,
//                modifier = Modifier.padding(start = 10.dp)
//            )
//        }
//        items(eventList) { event ->
//            when(event) {
//                is DeviceEvent.AttemptUnlockDevice -> AttemptUnlockDeviceItem(event)
//                is DeviceEvent.AppOpen -> AppOpenItem(event)
//            }
//        }
//    }
//}


