package com.xxmrk888ytxx.enterpasswordscreen

import MutliUse.LazySpacer
import android.annotation.SuppressLint
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W800
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.enterpasswordscreen.models.GridButtonType
import theme.BackGroundColor
import theme.okColor
import theme.openSansFont
import theme.primaryFontColor

@Composable
fun EnterPasswordScreen(
    callBack: EnterPasswordScreenCallBack,
    descriptionText:String,
    inputtedPasswordSize:Int,
    descriptionTextColor: Color = primaryFontColor,
    emptyPasswordCircleColor:Color = primaryFontColor,
    inputtedPasswordCircleColor:Color = okColor
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGroundColor),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PasswordViewer(
            descriptionText,
            descriptionTextColor,
            emptyPasswordCircleColor,
            inputtedPasswordCircleColor,
            callBack.passwordSize,
            inputtedPasswordSize
        )

        PasswordGrid(getGridButtonList(callBack.enableFingerPrintAuthorization,callBack),callBack)
    }
}



@Composable
internal fun PasswordViewer(
    descriptionText: String,
    descriptionTextColor: Color,
    emptyPasswordCircleColor:Color,
    inputtedPasswordCircleColor:Color,
    passwordSize:Int,
    inputtedPasswordSize:Int
) {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = descriptionText,
            fontFamily = openSansFont,
            fontSize = 25.sp,
            fontWeight = W800,
            color = descriptionTextColor
        )

        LazySpacer(20)

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(passwordSize) { passwordSize ->
                Canvas(modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .size(20.dp)) {
                    drawCircle(
                        if(passwordSize >= inputtedPasswordSize) emptyPasswordCircleColor
                            else inputtedPasswordCircleColor
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("ResourceType")
@Composable
internal fun PasswordGrid(
    gridButtonList:List<GridButtonType>,
    callBack: EnterPasswordScreenCallBack
) {
    val gridSize = if(LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) 50.dp
        else 100.dp
    LazyVerticalGrid(
        GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth(),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(gridButtonList) { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(gridSize)
                    .combinedClickable(
                        enabled = item !is GridButtonType.Stub,
                        onClick = {
                            if (item is GridButtonType.NumberButton) {
                                callBack.onInputNumber(item.number)
                            }
                            if (item is GridButtonType.ActionButton) {
                                item.onClick()
                            }
                        },
                        onLongClick = (item as? GridButtonType.ActionButton)?.onLongPress
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(Modifier) {
                    when(item) {
                        is GridButtonType.ActionButton -> {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = "",
                                tint = primaryFontColor,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                        is GridButtonType.NumberButton -> {
                            validateNumberForButtonNumber(item.number)

                            Text(
                                text = item.number.toString(),
                                fontWeight = W800,
                                fontFamily = openSansFont,
                                fontSize = 25.sp,
                                color = primaryFontColor
                            )
                        }

                        is GridButtonType.Stub -> {}
                    }
                }
            }
        }
    }
}



internal fun validateNumberForButtonNumber(number:Int) {
    if(number !in 0..9)
        throw IllegalArgumentException("NumberButton accept number in 0..9")
}

@SuppressLint("ResourceType")
internal fun getGridButtonList(isFingerPrintAvailable:Boolean,callBack: EnterPasswordScreenCallBack) : List<GridButtonType> {
    val fingerPrintButton = if(isFingerPrintAvailable)
        GridButtonType.ActionButton(
            R.drawable.ic_fingerprint,
            onClick = callBack::onSendFingerPrintRequest
        )
        else GridButtonType.Stub

    return listOf(
        GridButtonType.NumberButton(1),
        GridButtonType.NumberButton(2),
        GridButtonType.NumberButton(3),
        GridButtonType.NumberButton(4),
        GridButtonType.NumberButton(5),
        GridButtonType.NumberButton(6),
        GridButtonType.NumberButton(7),
        GridButtonType.NumberButton(8),
        GridButtonType.NumberButton(9),
        fingerPrintButton,
        GridButtonType.NumberButton(0),
        GridButtonType.ActionButton(
            R.drawable.ic_backspace,
            onLongPress = callBack::onClearAll,
            onClick = callBack::onClearNumber
        )
    )
}


//@Composable
//@Preview
//fun test() {
//    val descriptionText = remember {
//        mutableStateOf("Введите пароль")
//    }
//    val context = LocalContext.current
//    val password = remember {
//        mutableStateOf("")
//    }
//    val color = remember {
//        mutableStateOf(primaryFontColor)
//    }
//    val callback = object : EnterPasswordScreenCallBack {
//        override fun onInputNumber(number: Int) {
//            color.value = primaryFontColor
//            descriptionText.value = "Введите пароль"
//            password.value += number.toString()
//
//            if(password.value.length == passwordSize) {
//                if(password.value == "1111") {
//                    Toast.makeText(context,"Пароль верный",Toast.LENGTH_SHORT).show()
//                    descriptionText.value = "Пароль верный"
//                    color.value = Color.Green
//                }
//                else {
//                    Toast.makeText(context,"Пароль неверный",Toast.LENGTH_SHORT).show()
//                    descriptionText.value = "Пароль неверный"
//                    color.value = Color.Red
//                }
//
//                onClearAll()
//            }
//        }
//
//        override fun onClearNumber() {
//            password.value = password.value.dropLast(1)
//        }
//
//        override fun onClearAll() {
//            password.value = ""
//        }
//
//        override fun onSendFingerPrintRequest() {
//
//        }
//
//        override val passwordSize: Int
//            get() = 4
//
//        override val inputtedPasswordSize: Int = password.value.length
//
//    }
//
//    EnterPasswordScreen(callback,descriptionText.value,color.value,color.value)
//}