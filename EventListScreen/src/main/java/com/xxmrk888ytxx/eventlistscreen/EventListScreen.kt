package com.xxmrk888ytxx.eventlistscreen

import MutliUse.AppOpenItem
import MutliUse.AttemptUnlockDeviceItem
import MutliUse.LazySpacer
import SharedInterfaces.Navigator
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
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
import theme.openSansFont
import theme.primaryFontColor

/**
 * [Ru]
 * На данном экране можно посмотреть события устройства за всё
 * время
 */

/**
 * [En]
 * On this screen, you can view device events for all time
 */

@Composable
fun EventListScreen(eventViewModel: EventViewModel,navigator: Navigator) {
    val eventList = eventViewModel.eventList.collectAsState(mapOf())

    LazyColumn(Modifier.fillMaxSize()) {

        item {
            TopBar(navigator)
        }
        if(eventList.value.isNotEmpty()) {
            eventList.value.forEach { dayEvents ->
                item {
                    key(dayEvents.key) {
                        Text(
                            text = dayEvents.key,
                            fontFamily = openSansFont,
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Normal,
                            color = primaryFontColor.copy(0.8f),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        LazySpacer(height = 5)
                    }
                }
                items(dayEvents.value,key = {it.eventId}) { event ->
                    when(event) {
                        is DeviceEvent.AttemptUnlockDevice -> AttemptUnlockDeviceItem(event)
                        is DeviceEvent.AppOpen -> AppOpenItem(event)
                    }
                }
            }
        }
        else item { ListStub() }
    }
}

@Composable
@MustBeLocalization
internal fun ListStub() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Событий пока нет",
            color = primaryFontColor,
            fontSize = 25.sp,
            fontFamily = openSansFont,
            fontWeight = FontWeight.W800
        )
    }
}

@Composable
internal fun TopBar(navigator: Navigator) {
    Row(modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = navigator::navigateUp) {
            Icon(
                painter = painterResource(R.drawable.ic_back_arrow),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

