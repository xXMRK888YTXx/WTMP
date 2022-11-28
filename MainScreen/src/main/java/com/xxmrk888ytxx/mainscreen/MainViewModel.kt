package com.xxmrk888ytxx.mainscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(

) : ViewModel() {

    val isEnable = mutableStateOf(true)

}