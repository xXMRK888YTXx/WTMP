package com.xxmrk888ytxx.settingsscreen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import theme.cardColor
import theme.primaryFontColor
import java.time.LocalTime

@Composable
fun TimePickDialog(
    state: MaterialDialogState,
    title:String,
    minTime: LocalTime = LocalTime.MIN,
    maxTime:LocalTime = LocalTime.MAX,
    onTimePicked:(Int) -> Unit
) {
    MaterialDialog(
        dialogState = state,
        backgroundColor = cardColor,
        shape = RoundedCornerShape(10.dp),
        buttons = {
            positiveButton(stringResource(R.string.Ok))
            negativeButton(stringResource(R.string.Cancel))
        }
    ) {
        timepicker(
            title = title,
            is24HourClock = true,
            timeRange = minTime..maxTime,
            colors = TimePickerDefaults.colors(
                headerTextColor = primaryFontColor,
                inactiveTextColor = primaryFontColor
            )
        ) {
            onTimePicked(it.toSecondOfDay())
        }
    }
}