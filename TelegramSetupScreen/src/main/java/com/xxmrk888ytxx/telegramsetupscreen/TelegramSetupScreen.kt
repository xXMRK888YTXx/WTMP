package com.xxmrk888ytxx.telegramsetupscreen

import MutliUse.CustomSnackBar
import MutliUse.GradientButton
import MutliUse.LazySpacer
import MutliUse.StyleButton
import SharedInterfaces.Navigator
import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.telegramsetupscreen.models.ScreenState
import remember
import theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
@MustBeLocalization
fun TelegramSetupScreen(telegramViewModel: TelegramViewModel, navigator: Navigator) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.Transparent,
        topBar = { TopBar(navigator) },
        snackbarHost = {
            SnackbarHost(
                hostState = telegramViewModel.snackState,
                modifier = Modifier.padding(
                    bottom = 10.dp,
                    start = 15.dp,
                    end = 15.dp
                )
            ) { snackbarData ->
                CustomSnackBar(
                    snackbarData.message,
                    textToIcon = rememberSnackbarTextToIcon(),
                    defaultIcon = {
                        Box(modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .background(errorColor)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_cancel),
                                contentDescription = "",
                                tint = snackbarColor,
                                modifier = Modifier
                                    .padding(3.dp)
                                    .size(25.dp)
                            )
                        }
                    }
                )
            }
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .height(LocalConfiguration.current.screenHeightDp.dp)
        ) {
            item {
                val screenState = telegramViewModel.screenState.remember()

                AnimatedContent(
                    targetState = screenState.value,
                    transitionSpec = {
                        slideInHorizontally(animationSpec = tween(400)) with

                        slideOutHorizontally(
                            animationSpec = tween(250),
                            targetOffsetX = { it }
                        )
                    }

                ) { screenState ->
                    Column(Modifier.fillMaxWidth()) {
                        when (screenState) {

                            is ScreenState.ChangeTelegramConfigState -> {
                                InputTelegramConfigForm(
                                    telegramViewModel,
                                )
                            }

                            is ScreenState.ConfigSavedState -> {
                                TelegramDataSaveLabel(telegramViewModel)
                            }

                            is ScreenState.LoadConfigState -> {
                                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun InputInfoTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    focusRequester: FocusRequester = FocusRequester(),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp)
            .focusRequester(focusRequester)
        ,
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
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun InputTelegramConfigForm(
    telegramViewModel: TelegramViewModel,
) {
    val userIdText = telegramViewModel.userIdText.remember()

    val botKeyText = telegramViewModel.botKeyText.remember()

    val isTelegramRequestProcessNow = telegramViewModel.isTelegramRequestProcessNow.remember()

    val botKeyFieldFocus = FocusRequester()

    val focusManager =  LocalFocusManager.current

    InputInfoTextField(
        value = userIdText.value,
        onValueChanged = {
            if(!it.isDigitsOnly()) return@InputInfoTextField
            telegramViewModel.userIdText.value = it
        },
        label = "Id пользователя",
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                botKeyFieldFocus.requestFocus()
            }
        ),
    )

    LazySpacer(height = 20)

    InputInfoTextField(
        value = botKeyText.value,
        onValueChanged = {
            telegramViewModel.botKeyText.value = it
        },
        label = "Bot-key",
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone  = {

                focusManager.clearFocus()
            }
        ),
        focusRequester = botKeyFieldFocus
    )

    LazySpacer(height = 20)

    Box(Modifier
        .fillMaxWidth()
        .padding(
            start = 25.dp,
            end = 25.dp
        )
    ) {
        AnimatedContent(
            targetState = isTelegramRequestProcessNow.value,
            transitionSpec = {
                scaleIn(animationSpec = tween(400)) with

                slideOutHorizontally(
                    animationSpec = tween(250),
                    targetOffsetX = { it }
                )
            }
        ) { isSaveConfigProcess ->
            if(!isSaveConfigProcess) {
                GradientButton(
                    backgroundGradient = enableAppButtonColor,
                    onClick = {
                        telegramViewModel.saveTelegramConfig(

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
            else {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 10.dp,
                            bottom = 10.dp
                        ),
                contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        LazySpacer(height = 15)
        StyleButton(text = "Где взять?") {}
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
@MustBeLocalization
internal fun TelegramDataSaveLabel(telegramViewModel: TelegramViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val isTelegramRequestProcessNow = telegramViewModel.isTelegramRequestProcessNow.remember()
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

        StyleButton(
            text = "Изменить данные",
            isEnable = !isTelegramRequestProcessNow.value,
            onClick = telegramViewModel::toChangeTelegramConfigState
        )

        LazySpacer(15)

        AnimatedContent(
            targetState = isTelegramRequestProcessNow.value,
            transitionSpec = {
                scaleIn(animationSpec = tween(400)) with

                slideOutHorizontally(
                    animationSpec = tween(250),
                    targetOffsetX = { it }
                )
            }
        ) { isTelegramRequestProcessNow ->
            if(isTelegramRequestProcessNow) {
                Box(
                    Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                StyleButton(
                    text = "Отправить тестовое сообщение",
                    onClick = telegramViewModel::checkTelegramConfig
                )
            }

        }
    }
}

@Composable
internal fun rememberSnackbarTextToIcon() : Map<String,@Composable () -> Unit> {
    val saveTelegramConfigMessage = stringResource(R.string.Save_telegram_config_message)
    val noConnectionMessage = stringResource(R.string.No_connection_message)
    val telegramCancelMessage = stringResource(R.string.Telegram_cancel_message)
    val unknownErrorMessage = stringResource(R.string.unknown_error_message)
    val telegramConfigValidMessage = stringResource(R.string.Telegram_config_valid_message)

    val iconFactory:@Composable (Int, Color, Dp) -> Unit = remember {
        { IdRes,circleColor,padding ->
            Box(modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(circleColor)
            ) {
                Icon(
                    painter = painterResource(IdRes),
                    contentDescription = "",
                    tint = snackbarColor,
                    modifier = Modifier
                        .padding(padding)
                        .size(25.dp)
                )
            }
        }
    }

    return remember {
        mapOf(
            saveTelegramConfigMessage to { iconFactory(R.drawable.ic_ok,okColor,0.dp) },
            noConnectionMessage to { iconFactory(R.drawable.ic_no_connection,errorColor,3.dp) },
            telegramCancelMessage to { iconFactory(R.drawable.ic_cancel, errorColor,3.dp) },
            unknownErrorMessage to { iconFactory(R.drawable.ic_cancel, errorColor,3.dp) },
            telegramConfigValidMessage to { iconFactory(R.drawable.ic_ok,okColor,0.dp) }

        )
    }
}