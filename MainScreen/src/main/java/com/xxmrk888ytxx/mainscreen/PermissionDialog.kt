package com.xxmrk888ytxx.mainscreen

import MutliUse.GradientButton
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.xxmrk888ytxx.mainscreen.models.RequestedPermission
import theme.*

@Composable
internal fun PermissionDialog(requestedPermission: List<RequestedPermission>,onDismissDialog:() -> Unit) {

    Dialog(onDismissRequest = onDismissDialog) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            backgroundColor = cardColor
        ) {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                items(requestedPermission) {
                    val permissionState = it.permissionState.collectAsState(false)
                    val fontColor = if (permissionState.value) disableAppButtonFontColor
                    else enableAppButtonFontColor
                    Column(Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {
                        Text(
                            text = it.description,
                            fontFamily = openSansFont,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600,
                            color = primaryFontColor,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        GradientButton(
                            backgroundGradient = if (permissionState.value) disableAppButtonColor
                            else enableAppButtonColor,
                            shape = RoundedCornerShape(15.dp),
                            onClick = it.onRequest,
                            enabled = !permissionState.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp).border(width = 1.dp,
                                    brush =  if (permissionState.value) disableAppButtonColor
                                    else enableAppButtonColor, RoundedCornerShape(15.dp))
                        ) {

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = if (permissionState.value) stringResource(R.string.Granted)
                                    else stringResource(R.string.Grant),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W600,
                                    fontFamily = openSansFont,
                                    color = fontColor
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}