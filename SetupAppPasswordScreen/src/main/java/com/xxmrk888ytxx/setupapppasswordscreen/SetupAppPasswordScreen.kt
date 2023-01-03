package com.xxmrk888ytxx.setupapppasswordscreen

import SharedInterfaces.Navigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.xxmrk888ytxx.enterpasswordscreen.EnterPasswordScreen
import remember

@Composable
fun SetupAppPasswordScreen(
    screenMode: Int,
    setupAppPasswordViewModel: SetupAppPasswordViewModel,
    navigator: Navigator
) {
    LaunchedEffect(key1 = screenMode,key2 = navigator, block = {
        setupAppPasswordViewModel.navigator = navigator
        setupAppPasswordViewModel.setupScreenMode(screenMode)
    })
    val descriptionTest = setupAppPasswordViewModel.descriptionText.remember()
    val descriptionColor = setupAppPasswordViewModel.descriptionColor.remember()
    val emptyPasswordCircleColor = setupAppPasswordViewModel.emptyPasswordCircleColor.remember()
    val inputtedPassword = setupAppPasswordViewModel.inputPassword.remember()



    EnterPasswordScreen(
        callBack = setupAppPasswordViewModel.callBack,
        descriptionText = descriptionTest.value,
        inputtedPasswordSize = inputtedPassword.value.length,
        descriptionTextColor = descriptionColor.value,
        emptyPasswordCircleColor = emptyPasswordCircleColor.value
    )

}