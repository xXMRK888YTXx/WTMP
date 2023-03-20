package com.xxmrk888ytxx.settingsscreen

import MutliUse.LazySpacer
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
import androidx.compose.ui.text.font.FontWeight.Companion.W900
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.xxmrk888ytxx.coredeps.models.WeekDay
import theme.*

@Composable
fun SetSuspendParamsDialog(
    onCancel: () -> Unit,
    pickedWeekDays: Set<WeekDay>,
    onPickWeekDay: (WeekDay) -> Unit,
    onCancelPickDay: (WeekDay) -> Unit
) {
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
                PickWeekDayWidget(
                    pickedWeekDays = pickedWeekDays,
                    onSelectDay = onPickWeekDay,
                    onCancelSelectDay = onCancelPickDay
                )

                LazySpacer(10)

                Column(
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = buildString {
                            append(stringResource(R.string.Working_hours))
                            append(":")
                            append(stringResource(R.string.No_limits))
                        },
                        color = primaryFontColor,
                        fontSize = 16.sp,
                        fontWeight = W900,
                        fontFamily = openSansFont
                    )

                    LazySpacer(10)

                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = settingsSeparatorLineColor,
                            disabledBackgroundColor = settingsSeparatorLineColor.copy(0.5f)
                        ),
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, end = 5.dp),
                        shape = RoundedCornerShape(80)
                    ) {
                        Text(text = stringResource(R.string.Set_limit),
                            color = primaryFontColor
                        )
                    }

                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = checkedSettingsSwitch,
                            disabledBackgroundColor = checkedSettingsSwitch.copy(0.5f)
                        ),
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, end = 5.dp),
                        shape = RoundedCornerShape(80)
                    ) {
                        Text(text = stringResource(R.string.Save),
                            color = primaryFontColor
                        )
                    }
                }


            }
        }
    }
}