package com.xxmrk888ytxx.selecttrackedappscreen

import MutliUse.LazySpacer
import SharedInterfaces.Navigator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.xxmrk888ytxx.coredeps.models.AppInfo
import remember
import theme.checkedSettingsSwitch
import theme.openSansFont
import theme.primaryFontColor

@Composable
fun SelectTrackedAppScreen(
    selectTrackedAppViewModel: SelectTrackedAppViewModel,
    navigator: Navigator,
) {
    val screenState = selectTrackedAppViewModel.screenState.remember()
    val searchList = selectTrackedAppViewModel.searchLineTest.remember()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navigator)
            LazySpacer(10)
        },
        backgroundColor = Color.Transparent
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding(),
                start = it.calculateStartPadding(LocalLayoutDirection.current),
                end = it.calculateEndPadding(LocalLayoutDirection.current)
            )) {
            if (screenState.value is ScreenState.ShopAppListState) {
                item {
                    SearchLine(selectTrackedAppViewModel)
                    LazySpacer(height = 15)
                }
                items(
                    (screenState.value as ScreenState.ShopAppListState).appList
                        .filterSearch(searchList.value)
                ) { appInfo ->
                    val trackedPackageNames = selectTrackedAppViewModel
                        .trackedPackageNames.collectAsState(listOf())
                    AppItem(
                        appInfo = appInfo,
                        trackedPackageNames = trackedPackageNames.value,
                        onEnableTrack = selectTrackedAppViewModel::enableTrack,
                        onDisableTrack = selectTrackedAppViewModel::disableTrack
                    )
                }
            }
        }
        if(screenState.value is ScreenState.Loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
internal fun TopBar(navigator: Navigator) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = navigator::navigateUp) {
            Icon(
                painter = painterResource(R.drawable.ic_back_arrow),
                contentDescription = "",
                tint = primaryFontColor,
                modifier = Modifier.size(25.dp)
            )
        }

        LazySpacer(width = 15)

        Text(
            stringResource(R.string.Applications),
            fontSize = 27.sp,
            fontWeight = FontWeight.W600,
            color = Color.White,
            fontFamily = openSansFont,
        )
    }
}

@Composable
internal fun AppItem(
    appInfo: AppInfo,
    trackedPackageNames:List<String>,
    onEnableTrack:(packageName:String) -> Unit,
    onDisableTrack:(packageName:String) -> Unit,
) {
    val onChangeState:() -> Unit = {
        if(appInfo.appPackageName in trackedPackageNames) {
            onDisableTrack(appInfo.appPackageName)
        }
        else onEnableTrack(appInfo.appPackageName)
    }
    Column(Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable(onClick = onChangeState)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            AsyncImage(
                model = appInfo.appIcon ?: R.drawable.default_icon,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(55.dp)
            )

            Box(modifier = Modifier.fillMaxWidth(0.9f), contentAlignment = Alignment.Center) {
                Text(
                    text = appInfo.appName,
                    color = primaryFontColor,
                    fontWeight = FontWeight.W600,
                    fontFamily = openSansFont,
                    fontSize = 18.sp,
                    modifier = Modifier.widthIn(max = 125.dp),
                    textAlign = TextAlign.Center
                )
            }

            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Checkbox(
                    checked = appInfo.appPackageName in trackedPackageNames,
                    onCheckedChange = {
                        onChangeState()
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = checkedSettingsSwitch,
                        uncheckedColor = checkedSettingsSwitch
                    )
                )
            }

        }
        LazySpacer(5)
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(2.dp))
    }

}

@Composable
internal fun SearchLine(selectTrackedAppViewModel: SelectTrackedAppViewModel) {
    val searchListTest = selectTrackedAppViewModel.searchLineTest.remember()
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = searchListTest.value,
        onValueChange = {
            searchListTest.value = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        singleLine = true,
        label = {
            Text(
                text = stringResource(R.string.Search),
                fontFamily = openSansFont,
                fontSize = 17.sp,
                fontWeight = FontWeight.W600,

                )
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = primaryFontColor,
            placeholderColor = primaryFontColor.copy(0.6f),
            focusedBorderColor = checkedSettingsSwitch,
            unfocusedBorderColor = primaryFontColor,
            unfocusedLabelColor = primaryFontColor,
            focusedLabelColor = checkedSettingsSwitch
        ),
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.W600,
            fontFamily = openSansFont,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        )
    )
}
