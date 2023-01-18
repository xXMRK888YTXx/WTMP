package com.xxmrk888ytxx.supportdeveloperscreen

import MutliUse.LazySpacer
import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.xxmrk888ytxx.adutils.AdStateManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleRegister
import com.xxmrk888ytxx.coredeps.SharedInterfaces.BillingManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import remember
import theme.*

@Composable
fun SupportDeveloperScreen(
    supportDeveloperViewModel: SupportDeveloperViewModel,
    activityLifecycleRegister: ActivityLifecycleRegister,
) {
    val isShowCongratulationsDialog = supportDeveloperViewModel.isShowCongratulationsDialog
        .remember()

    val isNeedShowAd = supportDeveloperViewModel.isNeedShowAd.collectAsState(true)

    LaunchedEffect(key1 = activityLifecycleRegister, block = {
        supportDeveloperViewModel.registerActivityCallBack(activityLifecycleRegister)
    })
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        backgroundColor = BackGroundColor
    ) { scaffoldPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    top = scaffoldPadding.calculateTopPadding(),
                    end = scaffoldPadding.calculateEndPadding(LocalLayoutDirection.current),
                    start = scaffoldPadding.calculateStartPadding(LocalLayoutDirection.current),
                    bottom = scaffoldPadding.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState()),
        ) {
            val supportPrices: List<Pair<Int, () -> Unit>> = listOf(
                Pair(5, supportDeveloperViewModel::buyDeveloperSupportOn5Dollars),
                Pair(10, supportDeveloperViewModel::buyDeveloperSupportOn10Dollars),
                Pair(15, supportDeveloperViewModel::buyDeveloperSupportOn15Dollars)
            )
            Text(
                text = stringResource(R.string.Top_label),
                fontFamily = openSansFont,
                fontSize = 30.sp,
                color = primaryFontColor,
                fontWeight = FontWeight.W900,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, bottom = 15.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(R.string.description),
                fontWeight = FontWeight.W600,
                fontFamily = openSansFont,
                fontSize = 18.sp,
                color = primaryFontColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            LazySpacer(15)

            Text(
                text = stringResource(R.string.Any_purchase_permanently_turns_off_ads),
                fontWeight = FontWeight.W600,
                fontFamily = openSansFont,
                fontSize = 18.sp,
                color = primaryFontColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic
            )



            supportPrices.forEach {
                LazySpacer(10)
                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = checkedSettingsSwitch,
                    ),
                    onClick = it.second,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(80),
                ) {
                    Text(
                        text = stringResource(R.string.Support_on) + " ${it.first}$",
                        color = primaryFontColor,
                        fontWeight = FontWeight.W700,
                        fontFamily = openSansFont,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 3.dp, bottom = 3.dp)
                    )
                }
                LazySpacer(10)
            }

            LazySpacer(15)

            if(!isNeedShowAd.value) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        painter = painterResource(R.drawable.ic_star),
                        contentDescription = "",
                        tint = checkedSettingsSwitch,
                        modifier = Modifier.size(20.dp)
                    )

                    LazySpacer(width = 10)

                    Text(
                        text = "Спасибо за поддержку. Реклама откючена",
                        fontWeight = FontWeight.W500,
                        fontSize = 15.sp,
                        fontFamily = openSansFont,
                        color = primaryFontColor
                    )
                }
            }
        }
    }

    if(isShowCongratulationsDialog.value) {
        CongratulationsDialog(supportDeveloperViewModel)
    }
}

@Composable
fun CongratulationsDialog(supportDeveloperViewModel: SupportDeveloperViewModel) {
    val onDismiss = remember {
        { supportDeveloperViewModel.isShowCongratulationsDialog.value = false }
    }
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            backgroundColor = cardColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = "",
                    tint = checkedSettingsSwitch,
                    modifier = Modifier.size(80.dp)
                )

                LazySpacer(15)

                Text(
                    stringResource(R.string.Congratulations_text),
                    fontFamily = openSansFont,
                    color = primaryFontColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W800,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                LazySpacer(15)

                Text(
                    stringResource(R.string.Ads_will_be_disabled),
                    fontFamily = openSansFont,
                    color = primaryFontColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W800,
                    fontStyle = FontStyle.Italic
                )

                LazySpacer(15)

                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = checkedSettingsSwitch,
                    ),
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(80),
                ) {
                    Text(
                        text = stringResource(R.string.Close),
                        color = primaryFontColor,
                        fontWeight = FontWeight.W700,
                        fontFamily = openSansFont,
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun prev() {
    val b = object : BillingManager {
        override fun connectToGooglePlay() {

        }

        override fun buyDeveloperSupportOn5Dollars(activity: Activity) {

        }

        override fun buyDeveloperSupportOn10Dollars(activity: Activity) {

        }

        override fun buyDeveloperSupportOn15Dollars(activity: Activity) {

        }

    }
    val a = object : AdStateManager {
        override suspend fun changeAdState(state: Boolean) {

        }

        override val isNeedShowAd: Flow<Boolean>
            get() = flowOf(true)

    }
    val viewmodel = SupportDeveloperViewModel(b,a)
    val r = object : ActivityLifecycleRegister {
        override fun registerCallback(activityLifecycleCallback: ActivityLifecycleCallback) {

        }

        override fun unregisterCallback(activityLifecycleCallback: ActivityLifecycleCallback) {

        }

    }
    SupportDeveloperScreen(supportDeveloperViewModel = viewmodel, activityLifecycleRegister = r)
}