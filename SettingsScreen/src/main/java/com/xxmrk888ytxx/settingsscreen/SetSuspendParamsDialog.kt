package com.xxmrk888ytxx.settingsscreen

import MutliUse.LazySpacer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.font.FontWeight.Companion.W900
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.xxmrk888ytxx.coredeps.models.TimeSpan
import com.xxmrk888ytxx.coredeps.models.WeekDay
import theme.*
import java.time.LocalTime
import java.util.Calendar

@Composable
fun SetSuspendParamsDialog(
    onCancel: () -> Unit,
    pickedWeekDays: Set<WeekDay>,
    onPickWeekDay: (WeekDay) -> Unit,
    onCancelPickDay: (WeekDay) -> Unit,
    currentSelectedTimeSpan: TimeSpan,
    updateStartTimeSpan: (Int) -> Unit,
    updateEndTimeSpan: (Int) -> Unit,
    resetPickedTime:() -> Unit,
    saveChanges:() -> Unit
) {
    val startTimePickDialogState = rememberMaterialDialogState()
    val endTimePickDialogState = rememberMaterialDialogState()

    Dialog(onDismissRequest = onCancel) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = cardColor,
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

                Text(
                    text = stringResource(R.string.Choose_days_of_work),
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 7.dp),
                    color = primaryFontColor,
                    fontFamily = openSansFont,
                    fontSize = 18.sp,
                    fontWeight = W700,
                    textAlign = TextAlign.Center
                )

                PickWeekDayWidget(
                    pickedWeekDays = pickedWeekDays,
                    onSelectDay = onPickWeekDay,
                    onCancelSelectDay = onCancelPickDay
                )

                LazySpacer(10)

                Column(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = buildString {
                            append(stringResource(R.string.Working_hours))
                            append("- ")
                            if (currentSelectedTimeSpan == TimeSpan.NO_SETUP)
                                append(stringResource(R.string.No_limits))
                            else {
                                val startTime =
                                    if (currentSelectedTimeSpan.start == -1L) LocalTime.NOON
                                    else LocalTime.ofSecondOfDay(currentSelectedTimeSpan.start)

                                append(if (startTime == LocalTime.NOON) "?"
                                    else "${startTime.hourToString()}:${startTime.minuteToString()}")

                                append(" - ")

                                val endTime =
                                    if (currentSelectedTimeSpan.end == -1L) LocalTime.NOON
                                    else LocalTime.ofSecondOfDay(currentSelectedTimeSpan.end)

                                append(if (endTime == LocalTime.NOON) "?"
                                else "${endTime.hourToString()}:${endTime.minuteToString()}")
                            }
                        },
                        color = primaryFontColor,
                        fontSize = 16.sp,
                        fontWeight = W900,
                        fontFamily = openSansFont
                    )

                    LazySpacer(10)

                    Row(Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = settingsSeparatorLineColor,
                                disabledBackgroundColor = settingsSeparatorLineColor.copy(0.5f)
                            ),
                            onClick = startTimePickDialogState::show,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, end = 5.dp)
                                .weight(1f),
                            shape = RoundedCornerShape(80)
                        ) {
                            Text(
                                text = stringResource(R.string.Start),
                                color = primaryFontColor
                            )
                        }

                        OutlinedButton(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = settingsSeparatorLineColor,
                                disabledBackgroundColor = settingsSeparatorLineColor.copy(0.5f)
                            ),
                            onClick = endTimePickDialogState::show,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, end = 5.dp)
                                .weight(1f),
                            shape = RoundedCornerShape(80)
                        ) {
                            Text(
                                text = stringResource(R.string.End),
                                color = primaryFontColor
                            )
                        }
                    }

                    AnimatedVisibility(visible = currentSelectedTimeSpan != TimeSpan.NO_SETUP) {
                        OutlinedButton(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = settingsSeparatorLineColor,
                                disabledBackgroundColor = settingsSeparatorLineColor.copy(0.5f)
                            ),
                            onClick = resetPickedTime,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, end = 5.dp),
                            shape = RoundedCornerShape(80)
                        ) {
                            Text(
                                text = stringResource(R.string.Reset),
                                color = primaryFontColor
                            )
                        }
                    }

                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = checkedSettingsSwitch,
                            disabledBackgroundColor = checkedSettingsSwitch.copy(0.5f)
                        ),
                        onClick = saveChanges,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, end = 5.dp),
                        shape = RoundedCornerShape(80)
                    ) {
                        Text(
                            text = stringResource(R.string.Save),
                            color = primaryFontColor
                        )
                    }
                }


            }
        }
    }

    TimePickDialog(
        state = startTimePickDialogState,
        title = stringResource(R.string.Choose_start_time),
        onTimePicked = updateStartTimeSpan,
        maxTime = if (currentSelectedTimeSpan.end == -1L) LocalTime.MAX
        else LocalTime.ofSecondOfDay(currentSelectedTimeSpan.end - 60)
    )

    TimePickDialog(
        state = endTimePickDialogState,
        title = stringResource(R.string.Choose_end_time),
        onTimePicked = updateEndTimeSpan,
        minTime = if (currentSelectedTimeSpan.start == -1L) LocalTime.MIN
        else LocalTime.ofSecondOfDay(currentSelectedTimeSpan.start + 60)
    )
}