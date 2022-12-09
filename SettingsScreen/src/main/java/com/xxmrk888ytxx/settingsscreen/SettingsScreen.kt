package com.xxmrk888ytxx.settingsscreen

import MutliUse.LazySpacer
import SharedInterfaces.Navigator
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.settingsscreen.models.SettingsParamShape
import com.xxmrk888ytxx.settingsscreen.models.SettingsParamType
import theme.*

/**
 * [Ru]
 * Данный экран предназначен для настройки работы приложения
 * [En]
 * This screen is designed to configure the application
 */

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel,navigator: Navigator) {
    LazyColumn(Modifier.fillMaxSize()) {

        item {
            TopBar(navigator)
            LazySpacer(20)
        }

        item {
            SettingsCategory(
                "Неудачная попытка разблокировки",
                getFailedUnlockDeviceParams(settingsViewModel)
            )

            LazySpacer(height = 15)
        }

        item {

            SettingsCategory(
                "Разблокировка устройства",
                getSucceededUnlockDeviceParams(settingsViewModel)
            )

            LazySpacer(height = 15)
        }

        item {

            SettingsCategory(
                "Отслеженание приложений",
                getAppOpenObserverParams(settingsViewModel)
            )

            LazySpacer(height = 15)
        }

        item {
            SettingsCategory(
                "Настройки Telegram",
                getTelegramOptionsParams(settingsViewModel)
            )

            LazySpacer(height = 15)
        }

        item {
            SettingsCategory(
                "Об приложении",
                getAppInfoParams(settingsViewModel)
            )

            LazySpacer(height = 15)
        }

    }
}

@Composable
@MustBeLocalization
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
            "Настройки",
            fontSize = 27.sp,
            fontWeight = FontWeight.W600,
            color = Color.White,
            fontFamily = openSansFont,
        )
    }
}

@Composable
internal fun SettingsCategory(categoryName: String, settingsParams: List<SettingsParamType>) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(
            start = 10.dp,
            end = 10.dp
        )) {
        Text(
            text = categoryName,
            fontSize = 16.sp,
            color = primaryFontColor.copy(0.5f),
            fontWeight = FontWeight.W300,
            fontFamily = openSansFont
        )
        LazySpacer(height = 10)
        settingsParams.forEachIndexed() { index,param ->

            val shape = if(settingsParams.visibleParamsSize == 1) SettingsParamShape.AllShape
            else when(index) {
                0 -> SettingsParamShape.TopShape
                settingsParams.lastIndex -> SettingsParamShape.BottomShape
                else -> SettingsParamShape.None
            }

            SettingsParam(param,shape)

        }
    }
}

@SuppressLint("ResourceType")
@Composable
internal fun SettingsParam(
    params: SettingsParamType,
    shape: SettingsParamShape,
) {
    val shapeSize = 10.dp
    val cardShape = when (shape) {
        is SettingsParamShape.AllShape -> RoundedCornerShape(shapeSize)
        is SettingsParamShape.TopShape -> RoundedCornerShape(topStart = shapeSize, topEnd = shapeSize)
        is SettingsParamShape.BottomShape -> RoundedCornerShape(bottomStart = shapeSize,
            bottomEnd = shapeSize)
        is SettingsParamShape.None -> RoundedCornerShape(0.dp)
    }

    val onClick: () -> Unit = when (params) {

        is SettingsParamType.Button -> params.onClick

        is SettingsParamType.CheckBox -> {
            { params.onStateChanged(!params.isChecked) }
        }

        is SettingsParamType.Label -> { {} }
    }
    AnimatedVisibility(params.isVisible) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .heightIn(min = 70.dp)
                .clickable(
                    onClick = onClick,
                ),
            shape = cardShape,
            backgroundColor = cardColor
        ) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LazySpacer(width = 20)

                    Icon(
                        painter = painterResource(params.icon),
                        contentDescription = "",
                        tint = primaryFontColor,
                        modifier = Modifier.size(25.dp)
                    )

                    LazySpacer(width = 20)


                    Text(
                        text = params.text,
                        fontFamily = openSansFont,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        color = primaryFontColor,
                        modifier = Modifier.widthIn(
                            max = 225.dp
                        )
                    )

                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                        when (params) {

                            is SettingsParamType.CheckBox -> {
                                Switch(
                                    checked = params.isChecked,
                                    onCheckedChange = params.onStateChanged,
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = checkedSettingsSwitch,
                                        uncheckedThumbColor = uncheckedSettingsSwitch,
                                        uncheckedTrackColor = uncheckedSettingsSwitch.copy(0.5f)
                                    )
                                )
                            }

                            is SettingsParamType.Button -> {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(end = 10.dp)
                                ) {

                                    Icon(
                                        painter = painterResource(R.drawable.ic_arrow),
                                        contentDescription = "",
                                        tint = uncheckedSettingsSwitch.copy(0.9f),
                                        modifier = Modifier.size(25.dp)
                                    )

                                }
                            }

                            is SettingsParamType.Label -> {
                                Text(
                                    params.secondoryText,
                                    fontWeight = FontWeight.W500,
                                    color = primaryFontColor.copy(0.6f),
                                    fontFamily = openSansFont,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(end = 10.dp)
                                )
                            }
                        }
                    }
                }

            }

            if (shape !is SettingsParamShape.BottomShape) {
                Box(contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.fillMaxHeight()) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(settingsSeparatorLineColor)
                            .height(1.dp)
                    )
                }
            }

        }
    }
}