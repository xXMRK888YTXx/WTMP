package MutliUse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun RemoveEventDialog(onDismiss:() -> Unit,onRemove:() -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            backgroundColor = cardColor
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.Delete_event),
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
                        onClick = {
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(start = 5.dp, end = 5.dp),
                        shape = RoundedCornerShape(80),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = settingsSeparatorLineColor,
                        )
                    ) {
                        Text(text = stringResource(R.string.Cancel),
                            color = primaryFontColor
                        )
                    }
                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = checkedSettingsSwitch,
                        ),
                        onClick = {
                            onRemove()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, end = 5.dp),
                        shape = RoundedCornerShape(80),
                    ) {
                        Text(text = stringResource(R.string.Ok),
                            color = primaryFontColor
                        )
                    }
                }
            }
        }
    }
}
