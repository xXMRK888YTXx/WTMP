package com.xxmrk888ytxx.telegramsetupscreen

import MutliUse.GradientButton
import MutliUse.LazySpacer
import MutliUse.StyleButton
import SharedInterfaces.Navigator
import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import kotlinx.coroutines.launch
import remember
import theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
@MustBeLocalization
fun TelegramSetupScreen(telegramViewModel: TelegramViewModel, navigator: Navigator) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        backgroundColor = Color.Transparent,
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxWidth()) {
                TopBar(navigator)
            }
        }

        LazySpacer(height = 20)

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                val isDataSave = telegramViewModel.isDataSave.remember()

                AnimatedContent(
                    targetState = isDataSave.value,
                    transitionSpec = {
                        slideInHorizontally(animationSpec = tween(400)) with
                                slideOutHorizontally(
                                    animationSpec = tween(250),
                                    targetOffsetX = { it }
                                )
                    }

                ) { state ->
                    Column(Modifier.fillMaxWidth()) {
                        if (!state) {
                            InputTelegramConfigForm(telegramViewModel,scaffoldState.snackbarHostState)
                        } else {
                            TelegramDataSaveLabel(telegramViewModel)
                        }
                    }
                }
            }
        }

    }
}

@Composable
internal fun InputInfoTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    label: String,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        singleLine = true,
        label = {
            Text(
                text = label,
                fontFamily = openSansFont,
                fontSize = 17.sp,
                fontWeight = FontWeight.W600,

                )
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = primaryFontColor,
            placeholderColor = primaryFontColor.copy(0.6f),
            focusedBorderColor = checkedSettingsSwitch,
            unfocusedBorderColor = primaryFontColor,
            unfocusedLabelColor = primaryFontColor,
            focusedLabelColor = checkedSettingsSwitch
        ),
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.W600,
            fontFamily = openSansFont,
        )
    )
}

@Composable
@MustBeLocalization
internal fun TopBar(navigator: Navigator) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = navigator::navigateUp) {
            Icon(
                painter = painterResource(R.drawable.ic_back_arrow),
                contentDescription = "",
                tint = primaryFontColor,
                modifier = Modifier.size(25.dp)
            )
        }

        LazySpacer(width = 15)

        Text(
            "Настройки Telegram",
            fontSize = 27.sp,
            fontWeight = FontWeight.W600,
            color = Color.White,
            fontFamily = openSansFont,
        )
    }
}

@Composable
internal fun InputTelegramConfigForm(
    telegramViewModel: TelegramViewModel,
    snackbarHostState: SnackbarHostState
) {
    val text = remember {
        mutableStateOf("")
    }
    InputInfoTextField(
        value = text.value,
        onValueChanged = {
            text.value = it
        },
        label = "Id пользователя",
    )

    LazySpacer(height = 20)

    InputInfoTextField(
        value = text.value,
        onValueChanged = {
            text.value = it
        },
        label = "Bot-key",
    )

    LazySpacer(height = 20)

    Box(Modifier
        .fillMaxWidth()
        .padding(
            start = 25.dp,
            end = 25.dp
        )
    ) {
        GradientButton(
            backgroundGradient = enableAppButtonColor,
            onClick = {
                telegramViewModel.saveTelegramConfig(
                     onSucceededShackBar = { scope ->
                         scope.launch {
                             snackbarHostState.showSnackbar("Данные Telegram сохранены.")
                         }
                     },

                    onErrorSnackBar = { scope ->
                        scope.launch {
                            snackbarHostState.showSnackbar("При проверке данных произошла ошибка")
                        }
                    }
                )
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 10.dp,
                    bottom = 10.dp
                )
        ) {
            Text(
                text = "Сохранить",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                fontFamily = openSansFont,
                color = enableAppButtonFontColor
            )
        }
    }
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        StyleButton(text = "Где взять?") {}
    }
}

@Composable
@MustBeLocalization
internal fun TelegramDataSaveLabel(telegramViewModel: TelegramViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_ok),
                contentDescription = "",
                tint = primaryFontColor,
                modifier = Modifier.size(25.dp)
            )

            LazySpacer(width = 15)

            Text(
                text = "Данные Telegram сохранены",
                fontFamily = openSansFont,
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                color = primaryFontColor
            )
        }

        LazySpacer(height = 15)

        StyleButton(text = "Изменить данные") {
            telegramViewModel.isDataSave.value = false
        }

        LazySpacer(15)

        StyleButton(text = "Отправить тестовое сообщение") {

        }
    }
}