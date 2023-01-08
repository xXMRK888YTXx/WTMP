package com.xxmrk888ytxx.mainscreen

import MutliUse.*
import SharedInterfaces.Navigator
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleRegister
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
fun MainScreen(
    mainViewModel: MainViewModel,
    navigator: Navigator,
    activityLifecycleRegister: ActivityLifecycleRegister
) {
    LaunchedEffect(key1 = activityLifecycleRegister, block = {
        mainViewModel.registerInActivityLifecycle(activityLifecycleRegister)
    })
    val appState = mainViewModel.appState.collectAsState(false)
    val eventList = mainViewModel.dayEventList.collectAsState(listOf())
    val isRemoveDialogShow = mainViewModel.isRemoveDialogShow.remember()
    val isPermissionDialogShow = mainViewModel.isShowRequestPermissionDialog.remember()
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)) {
        item { TopBar(navigator) }

        item {
            EnableAppButton(
                isEnable = appState.value,
                onClick = if(!appState.value) mainViewModel::showRequestPermissionDialog
                else mainViewModel::disableApp
            )
        }

        item {
            Text(
                text = stringResource(R.string.Events_for_today),
                fontFamily = openSansFont,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp,
                color = primaryFontColor,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        if (eventList.value.isNotEmpty()) {
            items(eventList.value, key = { it.eventId }) { event ->
                Box(Modifier.animateItemPlacement()) {
                    val onDeleteEvent = {
                        mainViewModel.showRemoveEventDialog(event.eventId)
                    }
                    when (event) {
                        is DeviceEvent.AttemptUnlockDevice -> AttemptUnlockDeviceItem(event,
                            navigator,
                            onDeleteEvent)
                        is DeviceEvent.AppOpen -> AppOpenItem(event, navigator, onDeleteEvent)
                        is DeviceEvent.DeviceLaunch ->
                            DeviceLaunchItem(event = event, navigator = navigator, onDeleteEvent = onDeleteEvent)
                    }
                }
            }
        } else {
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
    if (isRemoveDialogShow.value.first) {
        RemoveEventDialog(onDismiss = mainViewModel::hideRemoveEventDialog,
            onRemove = {
                mainViewModel.removeEvent(isRemoveDialogShow.value.second)
                mainViewModel.hideRemoveEventDialog()
            })
    }

    if(isPermissionDialogShow.value) {
        PermissionDialog(mainViewModel.requestedPermission,mainViewModel::hideRequestPermissionDialog)
    }
}

@Composable
internal fun EventListStub() {
    Text(
        text = stringResource(R.string.No_events_today),
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
internal fun EnableAppButton(isEnable: Boolean, onClick: () -> Unit) {
    val fontColor = if (isEnable) disableAppButtonFontColor else enableAppButtonFontColor

    GradientButton(
        backgroundGradient = if (isEnable) disableAppButtonColor else enableAppButtonColor,
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
                text = if (isEnable) stringResource(R.string.Switch_off)
                else stringResource(R.string.Turn_on),
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                fontFamily = openSansFont,
                color = fontColor
            )

        }
    }
}

@Composable
internal fun TopBar(navigator: Navigator) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.Events),
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
internal fun ShowAllEventButton(navigator: Navigator) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        StyleButton(
            text = stringResource(R.string.View_all_time),
            onClick = navigator::toEventListScreen
        )
    }
}

