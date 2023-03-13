package com.xxmrk888ytxx.observer.presentation

import MutliUse.LazySpacer
import MutliUse.YesNoDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coredeps.Const.PRIVACY_POLICY_URL
import com.xxmrk888ytxx.coredeps.Const.TERMS_URL
import com.xxmrk888ytxx.coredeps.sendOpenWebSiteIntent
import com.xxmrk888ytxx.observer.R
import theme.Purple500
import theme.primaryFontColor

@Composable
internal fun AgreementDialog(
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
) {
    val context = LocalContext.current

    val privacyPolicyAgreeState = remember {
        mutableStateOf(false)
    }

    val termsAgreeState = remember {
        mutableStateOf(false)
    }


    YesNoDialog(
        onCancel = onCancel,
        onConfirm = onConfirm,
        leaveFromDialogIfClickOutSide = false,
        enableConfirmButton = privacyPolicyAgreeState.value && termsAgreeState.value
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = termsAgreeState.value,
                onCheckedChange = {
                    termsAgreeState.value = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Purple500,
                    uncheckedColor = Purple500
                )
            )

            LazySpacer(width = 10)

            Column() {
                Text(
                    text = stringResource(R.string.I_accept),
                    modifier = Modifier,
                    fontSize = 17.sp,
                    color = primaryFontColor,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.Terms_of_Use),
                    modifier = Modifier.clickable {
                        context.sendOpenWebSiteIntent(TERMS_URL)
                    },
                    fontSize = 17.sp,
                    color = Purple500,
                    fontWeight = FontWeight.Bold
                )
            }

            LazySpacer(5)

        }

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = privacyPolicyAgreeState.value,
                onCheckedChange = {
                    privacyPolicyAgreeState.value = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Purple500,
                    uncheckedColor = Purple500
                )
            )

            LazySpacer(width = 10)

            Column() {
                Text(
                    text = stringResource(R.string.I_accept),
                    modifier = Modifier,
                    fontSize = 17.sp,
                    color = primaryFontColor,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.Privacy_policy),
                    modifier = Modifier.clickable {
                        context.sendOpenWebSiteIntent(PRIVACY_POLICY_URL)
                    },
                    fontSize = 17.sp,
                    color = Purple500,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}