package com.xxmrk888ytxx.selecttrackedappscreen

import MutliUse.LazySpacer
import MutliUse.StyleButton
import MutliUse.YesNoDialog
import SharedInterfaces.Navigator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.xxmrk888ytxx.selecttrackedappscreen.model.DialogState
import com.xxmrk888ytxx.selecttrackedappscreen.model.ScreenState
import theme.checkedSettingsSwitch
import theme.openSansFont
import theme.primaryFontColor
import theme.progressIndicatorColor
import theme.settingsSeparatorLineColor

@Composable
fun SelectTrackedAppScreen(
    selectTrackedAppViewModel: SelectTrackedAppViewModel,
    navigator: Navigator,
) {
    val screenState by selectTrackedAppViewModel.screenState.collectAsState()
    val dialogState by selectTrackedAppViewModel.dialogState.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navigator)
            LazySpacer(10)
        },
        backgroundColor = Color.Transparent
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    start = it.calculateStartPadding(LocalLayoutDirection.current),
                    end = it.calculateEndPadding(LocalLayoutDirection.current)
                )
        ) {
            if (screenState is ScreenState.ShopAppListState) {
                item {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        StyleButton(
                            text = stringResource(R.string.add_by_package_name),
                            onClick = { selectTrackedAppViewModel.showAddPackageDialog() },
                        )
                    }
                }
                item {
                    val searchLineText =
                        (screenState as ScreenState.ShopAppListState).searchLineText
                    SearchLine(searchLineText, selectTrackedAppViewModel::onSearchLineTextChanged)
                    LazySpacer(height = 15)
                }
                items(
                    (screenState as ScreenState.ShopAppListState).appList
                ) { appInfo ->
                    val trackedPackageNames = selectTrackedAppViewModel
                        .trackedPackageNames.collectAsState(setOf())
                    AppItem(
                        appInfo = appInfo,
                        trackedPackageNames = trackedPackageNames.value,
                        onEnableTrack = selectTrackedAppViewModel::enableTrack,
                        onDisableTrack = selectTrackedAppViewModel::disableTrack
                    )
                }
            }
        }
        if (screenState is ScreenState.Loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = progressIndicatorColor)
            }
        }

        when (dialogState) {
            is DialogState.AddPackageDialog -> AddPackageDialog(
                text = (dialogState as DialogState.AddPackageDialog).packageName,
                onDismiss = selectTrackedAppViewModel::hideAddPackageDialog,
                onValueChange = selectTrackedAppViewModel::onAddPackageNameDialogTextChanged,
                onConfirm = selectTrackedAppViewModel::addNewPackage
            )

            DialogState.None -> {}
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
internal fun LazyItemScope.AppItem(
    appInfo: AppInfo,
    trackedPackageNames: Set<String>,
    onEnableTrack: (packageName: String) -> Unit,
    onDisableTrack: (packageName: String) -> Unit,
) {
    val onChangeState: () -> Unit = {
        if (appInfo.appPackageName in trackedPackageNames) {
            onDisableTrack(appInfo.appPackageName)
        } else onEnableTrack(appInfo.appPackageName)
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable(onClick = onChangeState)
            .animateItem()
    ) {
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
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(2.dp)
        )
    }

}

@Composable
internal fun SearchLine(searchLineText: String, onSearchLineTextChanged: (String) -> Unit) {
    StyleOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        searchLineText,
        stringResource(R.string.Search),
        onSearchLineTextChanged
    )
}

@Composable
internal fun StyleOutlinedTextField(
    modifier: Modifier,
    text: String,
    label: String,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier,
        singleLine = true,
        label = {
            Text(
                text = label,
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

@Composable
internal fun AddPackageDialog(
    text: String,
    onDismiss: () -> Unit,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit
) {
    YesNoDialog(
        confirmButtonText = stringResource(R.string.add),
        onConfirm = onConfirm,
        onCancel = onDismiss
    ) {
        StyleOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp),
            text,
            stringResource(R.string.package_name),
            onValueChange
        )

        StyleButton(
            text = stringResource(R.string.where_can_i_get_it),
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            backgroundColor = settingsSeparatorLineColor
        ) { }
    }
}
