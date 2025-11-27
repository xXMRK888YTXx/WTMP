package com.xxmrk888ytxx.eventlistscreen

import MutliUse.AppOpenItem
import MutliUse.AttemptUnlockDeviceItem
import MutliUse.DeviceLaunchItem
import MutliUse.LazySpacer
import MutliUse.RemoveEventDialog
import SharedInterfaces.Navigator
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import remember
import theme.openSansFont
import theme.primaryFontColor
import theme.progressIndicatorColor

/**
 * [Ru]
 * На данном экране можно посмотреть события устройства за всё
 * время
 */

/**
 * [En]
 * On this screen, you can view device events for all time
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventListScreen(eventViewModel: EventViewModel,navigator: Navigator) {
    val screenState = eventViewModel.screenState.collectAsState()
    val eventList = eventViewModel.sortedByDateEventList.collectAsState(mapOf())
    val isRemoveDialogShow = eventViewModel.isRemoveDialogShow.remember()

    Scaffold(
        Modifier.fillMaxSize(),
        backgroundColor = Color.Transparent,
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    start = it.calculateLeftPadding(LocalLayoutDirection.current),
                    end = it.calculateRightPadding(LocalLayoutDirection.current)
                )
        ) {

            item {
                TopBar(navigator)
                LazySpacer(10)
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItem(),
                                textAlign = TextAlign.Center
                            )
                            LazySpacer(height = 5)
                        }
                    }
                    items(dayEvents.value,key = { it.eventId }) { event ->
                        Box(Modifier.animateItem()) {
                            val onDeleteEvent = {
                                eventViewModel.showRemoveEventDialog(event.eventId)
                            }
                            when(event) {
                                is DeviceEvent.AttemptUnlockDevice -> AttemptUnlockDeviceItem(event,navigator,onDeleteEvent)
                                is DeviceEvent.AppOpen -> AppOpenItem(event,navigator,onDeleteEvent)
                                is DeviceEvent.DeviceLaunch -> DeviceLaunchItem(event,navigator,onDeleteEvent)
                            }
                        }
                    }
                }
            }
            else item { ListStub() }

            if(!screenState.value.isAllPageLoaded) {
                item {

                    eventViewModel.requestNewPageData()

                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = progressIndicatorColor
                        )
                    }
                }
            }


        }

    }
    if(isRemoveDialogShow.value.first) {
        RemoveEventDialog(onDismiss = eventViewModel::hideRemoveEventDialog) {
            eventViewModel.removeEvent(isRemoveDialogShow.value.second)
            eventViewModel.hideRemoveEventDialog()
        }
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
            text = stringResource(R.string.No_events_yet),
            color = primaryFontColor,
            fontSize = 25.sp,
            fontFamily = openSansFont,
            fontWeight = FontWeight.W800
        )
    }
}

@Composable
internal fun TopBar(navigator: Navigator) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        IconButton(onClick = navigator::navigateUp) {
            Icon(painter = painterResource(R.drawable.ic_back_arrow),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(25.dp)
            )
        }

        LazySpacer(width = 15)

        Text (
            stringResource(R.string.All_events),
            fontSize = 27.sp,
            fontWeight = FontWeight.W600,
            color = Color.White,
            fontFamily = openSansFont,
        )
    }
}

