package com.xxmrk888ytxx.enterpasswordscreen

import MutliUse.LazySpacer
import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W800
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.enterpasswordscreen.models.GridButtonType
import theme.BackGroundColor
import theme.openSansFont
import theme.primaryFontColor

@Composable
fun EnterPasswordScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGroundColor),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PasswordViewer()

        //LazySpacer(20)

        PasswordGrid(getGridButtonList(true))
    }
}



@Composable
internal fun PasswordViewer() {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Введите пароль",
            fontFamily = openSansFont,
            fontSize = 25.sp,
            fontWeight = W800,
            color = primaryFontColor
        )

        LazySpacer(20)

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(4) {
                Canvas(modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .size(20.dp)) {
                    drawCircle(primaryFontColor)
                }
            }
        }
    }
}

@SuppressLint("ResourceType")
@Composable
internal fun PasswordGrid(
    gridButtonList:List<GridButtonType>
) {
    val height = (LocalConfiguration.current.screenHeightDp / gridButtonList.size + 50)
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
                    .fillMaxWidth().height(100.dp)
                    .clickable(enabled = item !is GridButtonType.Stub) {
                        if(item is GridButtonType.NumberButton) {

                        }
                        if(item is GridButtonType.ActionButton) {
                            item.onClick()
                        }
                    },
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
internal fun getGridButtonList(isFingerPrintAvailable:Boolean) : List<GridButtonType> {
    val fingerPrintButton = if(isFingerPrintAvailable)
        GridButtonType.ActionButton(R.drawable.ic_fingerprint) {}
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
        GridButtonType.ActionButton(R.drawable.ic_backspace) {}
    )
}


@Composable
@Preview
fun test() {
        EnterPasswordScreen()
}