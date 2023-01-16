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
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

@SuppressLint("ResourceType")
@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel, navigator: Navigator) {
    LazyColumn(Modifier.fillMaxSize()) {

        item {
            TopBar(navigator)
            LazySpacer(20)
        }

        item {
            SettingsCategory(
                categoryName = stringResource(R.string.Paid_content),
                settingsParams = listOf(
                    SettingsParamType.Button(
                        text = stringResource(R.string.Support_author),
                        icon = R.drawable.baseline_attach_money_24,
                        onClick = navigator::toSupportDeveloperScreen
                    )
                ))
            LazySpacer(15)
        }

        item {
            SettingsCategory(
                stringResource(R.string.Unsuccessful_unlock_attempt),
                getFailedUnlockDeviceParams(settingsViewModel)
            )

            LazySpacer(height = 15)
        }

        item {

            SettingsCategory(
                stringResource(R.string.Device_unlock),
                getSucceededUnlockDeviceParams(settingsViewModel)
            )

            LazySpacer(height = 15)
        }

        item {

            SettingsCategory(
                stringResource(R.string.Application_tracking),
                getAppOpenObserverParams(settingsViewModel, navigator)
            )

            LazySpacer(height = 15)
        }

        item {
            SettingsCategory(
                categoryName = stringResource(R.string.Device_launch_tracking),
                settingsParams = getBootDeviceParams(settingsViewModel))

            LazySpacer(height = 15)
        }

        item {
            SettingsCategory(
                stringResource(R.string.Security),
                getSecureParams(settingsViewModel, navigator)
            )

            LazySpacer(15)
        }

        item {
            SettingsCategory(
                stringResource(R.string.Telegram_settings),
                getTelegramOptionsParams(navigator)
            )

            LazySpacer(height = 15)
        }

        item {
            SettingsCategory(
                stringResource(R.string.Other),
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

        Text(
            stringResource(R.string.Settings),
            fontSize = 27.sp,
            fontWeight = FontWeight.W600,
            color = Color.White,
            fontFamily = openSansFont,
        )
    }
}

/**
 * [Ru]
 * Данная функция создаёт макет с категорией настроек
 * @param categoryName - Подпись с названием категории
 * @param settingsParams - Список параметров с настройками для данной категории,
 * список настроек можно получить в файле [SettingsParams.kt]
 */
/**
 * [Ru]
 * This function creates a layout with a category of settings
 * @param categoryName - Label with category name
 * @param settingsParams - List of parameters with settings for this category,
 * the list of settings can be obtained in the file [SettingsParams.kt]
 */
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
        settingsParams.forEachIndexed() { index, param ->

            val shape = if (settingsParams.visibleParamsSize == 1) SettingsParamShape.AllShape
            else when (index) {
                0 -> SettingsParamShape.TopShape
                settingsParams.visibleParamsLastIndex -> SettingsParamShape.BottomShape
                else -> SettingsParamShape.None
            }

            SettingsParam(param, shape)

        }
    }
}

/**
 * [Ru]
 * Данная функция создаёт элемент с настройками.
 * @param params - определяёт тип элемента и информацию элемента
 * @param shape - скругления элемента, зависит от положения в списке настроек
 */
/**
 * [En]
 * This function creates an element with settings.
 * @param params - specifies the element type and element information
 * @param shape - element roundness, depends on the position in the settings list
 */
@SuppressLint("ResourceType")
@Composable
internal fun SettingsParam(
    params: SettingsParamType,
    shape: SettingsParamShape,
) {
    val shapeSize = 10.dp
    val cardShape = when (shape) {
        is SettingsParamShape.AllShape -> RoundedCornerShape(shapeSize)
        is SettingsParamShape.TopShape -> RoundedCornerShape(topStart = shapeSize,
            topEnd = shapeSize)
        is SettingsParamShape.BottomShape -> RoundedCornerShape(bottomStart = shapeSize,
            bottomEnd = shapeSize)
        is SettingsParamShape.None -> RoundedCornerShape(0.dp)
    }

    val paramsAlpha = if (params.isEnable) 1f else 0.5f

    val onClick: () -> Unit = when (params) {

        is SettingsParamType.Button -> params.onClick

        is SettingsParamType.Switch -> {
            { params.onStateChanged(!params.isSwitched) }
        }

        is SettingsParamType.Label -> {
            {}
        }

        is SettingsParamType.DropDown -> params.onShowDropDown
    }
    AnimatedVisibility(params.isVisible) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .heightIn(min = 70.dp)
                .clickable(
                    enabled = params.isEnable,
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
                        tint = primaryFontColor.copy(paramsAlpha),
                        modifier = Modifier.size(25.dp)
                    )

                    LazySpacer(width = 20)


                    Text(
                        text = params.text,
                        fontFamily = openSansFont,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        color = primaryFontColor.copy(paramsAlpha),
                        modifier = Modifier.widthIn(
                            max = 200.dp
                        )
                    )

                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                        when (params) {

                            is SettingsParamType.Switch -> {
                                Switch(
                                    checked = params.isSwitched,
                                    onCheckedChange = params.onStateChanged,
                                    enabled = params.isEnable,
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = checkedSettingsSwitch,
                                        uncheckedThumbColor = uncheckedSettingsSwitch,
                                        uncheckedTrackColor = uncheckedSettingsSwitch.copy(0.5f),
                                        disabledCheckedThumbColor = uncheckedSettingsSwitch.copy(
                                            paramsAlpha)
                                    ),
                                    modifier = Modifier.padding(end = 10.dp)
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
                                        tint = uncheckedSettingsSwitch.copy(if (params.isEnable) 0.9f
                                        else paramsAlpha),
                                        modifier = Modifier.size(25.dp)
                                    )

                                }
                            }

                            is SettingsParamType.Label -> {
                                Text(
                                    params.secondoryText,
                                    fontWeight = FontWeight.W500,
                                    color = primaryFontColor.copy(if (params.isEnable) 0.6f
                                    else paramsAlpha - 0.2f),
                                    fontFamily = openSansFont,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(end = 10.dp)
                                )
                            }

                            is SettingsParamType.DropDown -> {
                                val annotatedLabelString = buildAnnotatedString {
                                    append(params.showSelectedDropDownParam)
                                    appendInlineContent("drop_down_triangle")
                                }
                                val inlineContentMap = mapOf(
                                    "drop_down_triangle" to InlineTextContent(
                                          Placeholder(20.sp,
                                            20.sp,
                                            PlaceholderVerticalAlign.TextCenter)
                                    ) {
                                        Icon(painter = painterResource(R.drawable.ic_drop_down_triangle),
                                            contentDescription = "",
                                            tint = secondoryFontColor,
                                        )
                                    }
                                )
                                DropdownMenu(
                                    expanded = params.isDropDownVisible,
                                    onDismissRequest = params.onHideDropDown,
                                    modifier = Modifier
                                        .heightIn(max = 200.dp)
                                        .background(dropDownColor)
                                ) {
                                    params.dropDownItems.forEach { item ->
                                        DropdownMenuItem(onClick = item.onClick) {
                                            Text(
                                                text = item.text,
                                                fontFamily = openSansFont,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.W600,
                                                color = primaryFontColor
                                            )
                                        }

                                    }
                                }

                                Text(text = annotatedLabelString,
                                    inlineContent = inlineContentMap,
                                    fontWeight = FontWeight.W600,
                                    fontSize = 18.sp,
                                    fontFamily = openSansFont,
                                    color = secondoryFontColor,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            params.onShowDropDown()
                                        }
                                        .padding(end = 10.dp)
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