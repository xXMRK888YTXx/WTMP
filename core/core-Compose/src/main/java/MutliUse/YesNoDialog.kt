package MutliUse

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.xxmrk888ytxx.core_compose.R
import theme.*

@Composable
fun YesNoDialog(
    dialogDescription:String,
    confirmButtonText:String = stringResource(R.string.Ok),
    cancelButtonText:String = stringResource(R.string.Cancel),
    onConfirm:() -> Unit,
    onCancel:() -> Unit,
) {
    Dialog(onDismissRequest = onCancel) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            backgroundColor = cardColor
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = dialogDescription,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 25.dp, top = 25.dp)
                        .fillMaxWidth(),
                    fontSize = 17.sp,
                    color = primaryFontColor,
                    fontWeight = FontWeight.Bold,
                    fontFamily = openSansFont
                )

                Row() {
                    OutlinedButton(
                        onClick = onCancel,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(start = 5.dp, end = 5.dp),
                        shape = RoundedCornerShape(80),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = settingsSeparatorLineColor,
                        )
                    ) {
                        Text(text = cancelButtonText,
                            color = primaryFontColor
                        )
                    }
                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = checkedSettingsSwitch,
                        ),
                        onClick = onConfirm,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, end = 5.dp),
                        shape = RoundedCornerShape(80),
                    ) {
                        Text(text = confirmButtonText,
                            color = primaryFontColor
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun YesNoDialog(
    confirmButtonText:String = stringResource(R.string.Ok),
    cancelButtonText:String = stringResource(R.string.Cancel),
    onConfirm:() -> Unit,
    onCancel:() -> Unit,
    dialogContent:@Composable ColumnScope.() -> Unit
) {
    Dialog(onDismissRequest = onCancel) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            backgroundColor = cardColor
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                dialogContent(this)

                Row() {
                    OutlinedButton(
                        onClick = onCancel,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(start = 5.dp, end = 5.dp),
                        shape = RoundedCornerShape(80),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = settingsSeparatorLineColor,
                        )
                    ) {
                        Text(text = cancelButtonText,
                            color = primaryFontColor
                        )
                    }
                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = checkedSettingsSwitch,
                        ),
                        onClick = onConfirm,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, end = 5.dp),
                        shape = RoundedCornerShape(80),
                    ) {
                        Text(text = confirmButtonText,
                            color = primaryFontColor
                        )
                    }
                }
            }
        }

    }
}