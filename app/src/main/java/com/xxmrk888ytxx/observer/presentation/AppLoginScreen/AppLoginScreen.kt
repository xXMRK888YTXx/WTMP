package com.xxmrk888ytxx.observer.presentation.AppLoginScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleRegister
import com.xxmrk888ytxx.enterpasswordscreen.EnterPasswordScreen
import remember

@Composable
internal fun AppOpenScreen(
    appOpenViewModel: AppOpenViewModel,
    navController: NavController,
    activityLifecycleRegister: ActivityLifecycleRegister
) {
    LaunchedEffect(key1 = navController, block = {
        appOpenViewModel.navController = navController
    })

    LaunchedEffect(key1 = activityLifecycleRegister, block = {
        appOpenViewModel.registerInActivityLifeCircle(activityLifecycleRegister)
    })

    val descriptionTest = appOpenViewModel.descriptionText.remember()
    val inputPassword = appOpenViewModel.inputPassword.remember()
    val descriptionTestColor = appOpenViewModel.descriptionColor.remember()
    val emptyPasswordCircleColor = appOpenViewModel.emptyPasswordCircleColor.remember()

    EnterPasswordScreen(
        callBack = appOpenViewModel.callBack,
        descriptionText = descriptionTest.value,
        inputtedPasswordSize = inputPassword.value.length,
        descriptionTextColor = descriptionTestColor.value,
        emptyPasswordCircleColor = emptyPasswordCircleColor.value
    )
}