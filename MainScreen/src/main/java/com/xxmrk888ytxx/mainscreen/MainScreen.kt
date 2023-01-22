package com.xxmrk888ytxx.mainscreen

import MutliUse.*
import SharedInterfaces.Navigator
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.xxmrk888ytxx.adutils.AdMobBanner
import com.xxmrk888ytxx.adutils.models.AdMobKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback.ActivityLifecycleRegister
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
    activityLifecycleRegister: ActivityLifecycleRegister,
) {
    LaunchedEffect(key1 = activityLifecycleRegister, block = {
        mainViewModel.registerInActivityLifecycle(activityLifecycleRegister)
    })
    val appState = mainViewModel.appState.collectAsState(false)
    val eventList = mainViewModel.dayEventList.collectAsState(listOf())
    val isRemoveDialogShow = mainViewModel.isRemoveDialogShow.remember()
    val isPermissionDialogShow = mainViewModel.isShowRequestPermissionDialog.remember()
    val isNeedShowAd = mainViewModel.isShowAd.collectAsState(true)
    val isAccessibilityPermissionsDialogShow = mainViewModel.isAccessibilityPermissionsDialogShow
        .remember()
    val isAdminPermissionsDialogShow = mainViewModel.isAdminPermissionsDialogShow.remember()
    val isRequestIgnoreBatteryOptimisationDialogShow = mainViewModel
        .isRequestIgnoreBatteryOptimisationDialogShow.remember()


    Scaffold(
        Modifier
            .fillMaxSize()
            .padding(5.dp),
        backgroundColor = Color.Transparent,
        bottomBar = {
            if (isNeedShowAd.value)
                AdMobBanner(adMobKey = AdMobKey.MainScreenBanner, background = BackGroundColor)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    start = it.calculateLeftPadding(LocalLayoutDirection.current),
                    end = it.calculateRightPadding(LocalLayoutDirection.current),
                    bottom = it.calculateBottomPadding()
                )
        ) {
            item { TopBar(navigator) }

            item {
                EnableAppButton(
                    isEnable = appState.value,
                    onClick = if (!appState.value)
                        mainViewModel::showRequestIgnoreBatteryOptimisationDialog
                    else
                        mainViewModel::disableApp
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
                            is DeviceEvent.AttemptUnlockDevice -> AttemptUnlockDeviceItem(
                                event,
                                navigator,
                                onDeleteEvent
                            )
                            is DeviceEvent.AppOpen -> AppOpenItem(event, navigator, onDeleteEvent)
                            is DeviceEvent.DeviceLaunch ->
                                DeviceLaunchItem(
                                    event = event,
                                    navigator = navigator,
                                    onDeleteEvent = onDeleteEvent
                                )
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
    }

    if (isRemoveDialogShow.value.first) {
        RemoveEventDialog(onDismiss = mainViewModel::hideRemoveEventDialog,
            onRemove = {
                mainViewModel.removeEvent(isRemoveDialogShow.value.second)
                mainViewModel.hideRemoveEventDialog()
            })
    }

    if (isPermissionDialogShow.value) {
        PermissionDialog(
            mainViewModel.requestedPermission,
            mainViewModel::hideRequestPermissionDialog
        )
    }

    if(isAdminPermissionsDialogShow.value) {
        AdminPermissionWarmingDialog(mainViewModel)
    }

    if (isAccessibilityPermissionsDialogShow.value) {
        YesNoDialog(
            dialogDescription = buildString {
                append(stringResource(R.string.First_part_Accessibility_permission_description))
                append(stringResource(R.string.This_permission_will_be_used))
                append(stringResource(R.string.Getting_unlock_information))
                append(stringResource(R.string.Getting_information_about_open_applications))
                append(stringResource(R.string.This_data_is_stored_the_device))
                append(stringResource(R.string.For_the_application_to_work_you_must_agree))
            },
            onConfirm = mainViewModel::requestAccessibilityPermission,
            onCancel = { mainViewModel.isAccessibilityPermissionsDialogShow.value = false }
        )
    }

    if(isRequestIgnoreBatteryOptimisationDialogShow.value) {
        RequestIgnoreBatteryOptimisationDialog(mainViewModel)
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


@Composable
internal fun AdminPermissionWarmingDialog(mainViewModel: MainViewModel) {
    YesNoDialog(
        dialogDescription = buildString {
            append(stringResource(R.string.Warning)+"\n\n")
            append(stringResource(R.string.Admin_permission_warming))
        },
        onConfirm = mainViewModel::requestAdminPermission,
        onCancel = { mainViewModel.isAdminPermissionsDialogShow.value = false }
    )
}

@Composable
internal fun RequestIgnoreBatteryOptimisationDialog(mainViewModel: MainViewModel) {
    val notShowAgain = remember {
        mutableStateOf(false)
    }

    YesNoDialog(
        onConfirm = {
            mainViewModel.requestIgnoreBatteryOptimisationDialogHandlePressOk(notShowAgain.value)
        },
        onCancel = {
            mainViewModel.requestIgnoreBatteryOptimisationDialogHandlePressCancel(notShowAgain.value)
        },
        cancelButtonText = stringResource(R.string.Later),
    ) {
        Text(
            text = stringResource(R.string.RequestIgnoreBatteryOptimisationDialogText),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 25.dp, top = 25.dp)
                .fillMaxWidth(),
            fontSize = 17.sp,
            color = primaryFontColor,
            fontWeight = FontWeight.Bold,
            fontFamily = openSansFont
        )

        LazySpacer(15)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().clickable {
                notShowAgain.value = !notShowAgain.value
            }
        ) {
            Checkbox(checked = notShowAgain.value, onCheckedChange = {
                notShowAgain.value = it
            })

            Text(
                text = stringResource(R.string.Dont_show_again),
                fontFamily = openSansFont,
                fontWeight = FontWeight.W700,
                color = primaryFontColor,
                fontSize = 15.sp,
            )
        }
    }
}