package com.xxmrk888ytxx.supportdeveloperscreen

import MutliUse.LazySpacer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.BackGroundColor
import theme.checkedSettingsSwitch
import theme.openSansFont
import theme.primaryFontColor

@Composable
fun SupportDeveloperScreen(supportDeveloperViewModel:SupportDeveloperViewModel) {
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
            val supportPrices:List<Pair<Int,() -> Unit>> = listOf(Pair(5) {}, Pair(10) {},
                Pair(15) {})
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
                    Text(text = stringResource(R.string.Support_on) + " ${it.first}$",
                        color = primaryFontColor,
                        fontWeight = FontWeight.W700,
                        fontFamily = openSansFont,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 3.dp, bottom = 3.dp)
                    )
                }
                LazySpacer(10)
            }

        }
    }
}