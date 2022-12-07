package com.xxmrk888ytxx.settingsscreen

import MutliUse.LazySpacer
import SharedInterfaces.Navigator
import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import remember
import theme.cardColor
import theme.openSansFont
import theme.primaryFontColor

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
            SettingsCategory("Ввод в устройство", getParamSettingsList(settingsViewModel))
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
        LazySpacer(height = 5)
        settingsParams.forEachIndexed() { index,param ->
            val shape = when(index) {
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
    params:SettingsParamType,
    shape:SettingsParamShape
) {
    val cardShape = when(shape) {
        is SettingsParamShape.TopShape -> RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
        is SettingsParamShape.BottomShape -> RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
        is SettingsParamShape.None -> RoundedCornerShape(0.dp)
    }
    Card(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .heightIn(min = 60.dp),
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
                )
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                    when(params) {

                        is SettingsParamType.CheckBox -> {
                            Switch(checked = params.isChecked, onCheckedChange = params.onStateChanged)
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
                                    tint = primaryFontColor,
                                    modifier = Modifier.size(25.dp)
                                )
                            }
                        }
                    }
                }
            }

        }
        if(shape !is SettingsParamShape.BottomShape) {
            Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxHeight()) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(primaryFontColor.copy(0.6f))
                        .height(1.dp)
                )
            }
        }

    }
}

@SuppressLint("ResourceType")
@Composable
internal fun getParamSettingsList(settingsViewModel: SettingsViewModel) : List<SettingsParamType> {
    val testParam = settingsViewModel.testParam.remember()
    return listOf(
        SettingsParamType.Button(
            "Test 1",R.drawable.ic_back_arrow
        ) {},
        SettingsParamType.CheckBox(
          "Test 1",R.drawable.ic_back_arrow,
          true
        ) {},
        SettingsParamType.Button(
            "Test 2",R.drawable.ic_back_arrow
        ) {},
        SettingsParamType.CheckBox(
            "Test 2",R.drawable.ic_back_arrow,
            testParam.value
        ) {
            settingsViewModel.testParam.value = it
        },
        SettingsParamType.Button(
            "Test 3",R.drawable.ic_back_arrow
        ) {},
        SettingsParamType.CheckBox(
            "Test 3",R.drawable.ic_back_arrow,
            false
        ) {},
    )
}