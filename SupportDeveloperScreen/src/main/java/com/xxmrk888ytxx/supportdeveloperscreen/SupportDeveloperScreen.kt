package com.xxmrk888ytxx.supportdeveloperscreen

import MutliUse.LazySpacer
import MutliUse.StyleButton
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback.ActivityLifecycleRegister
import theme.BackGroundColor
import theme.checkedSettingsSwitch
import theme.openSansFont
import theme.primaryFontColor

@Composable
fun SupportDeveloperScreen(
    supportDeveloperViewModel: SupportDeveloperViewModel,
    activityLifecycleRegister: ActivityLifecycleRegister,
) {
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

            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StyleButton(text = stringResource(R.string.Restore_purchases)) {
                    supportDeveloperViewModel.restorePurchase()
                }
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
                        text = stringResource(R.string.Thanks_for_support),
                        fontWeight = FontWeight.W500,
                        fontSize = 15.sp,
                        fontFamily = openSansFont,
                        color = primaryFontColor
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun prev() {

}