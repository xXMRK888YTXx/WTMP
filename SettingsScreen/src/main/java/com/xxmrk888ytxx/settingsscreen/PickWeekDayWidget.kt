package com.xxmrk888ytxx.settingsscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coredeps.models.WeekDay
import remember
import theme.Purple500
import theme.openSansFont
import theme.primaryFontColor
import toStringLiteral

@Composable
fun PickWeekDayWidget(
    pickedWeekDays:Set<WeekDay>,
    onSelectDay:(WeekDay) -> Unit = {},
    onCancelSelectDay:(WeekDay) -> Unit = {}
) {
    val weekDays = WeekDay.weekDaySet.toList().remember()
    
    LazyRow(Modifier.fillMaxWidth()) {


        items(weekDays) {
            val isChecked = pickedWeekDays.contains(it)

            val onChangeCheckedState = {
                if(!isChecked) {
                    onSelectDay(it)
                } else {
                    onCancelSelectDay(it)
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.clickable(onClick = onChangeCheckedState)
            ) {
                Text(
                    text = it.toStringLiteral(),
                    fontFamily = openSansFont,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    color = primaryFontColor
                )

                Checkbox(
                    checked = isChecked,

                    onCheckedChange = {
                        onChangeCheckedState()
                    },

                    colors = CheckboxDefaults.colors(
                        checkedColor = Purple500,
                        uncheckedColor = Purple500
                    )
                )
            }
        }
    }
}

@Composable
@Preview
fun prev() {
    val days = remember {
        mutableStateListOf<WeekDay>()
    }

    LaunchedEffect(key1 = Unit, block =  {
        WeekDay.weekDaySet.forEach {
            days.add(it)
        }
    })


    PickWeekDayWidget(
        pickedWeekDays = days.toSet(),
        onSelectDay = {
            days.add(it)
        },
        onCancelSelectDay = {
            days.remove(it)
        }
    )
}