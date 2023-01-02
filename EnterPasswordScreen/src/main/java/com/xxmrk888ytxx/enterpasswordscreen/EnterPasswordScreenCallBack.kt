package com.xxmrk888ytxx.enterpasswordscreen

interface EnterPasswordScreenCallBack {
    fun onInputNumber(number:Int)

    fun onClearNumber()

    fun onClearAll()

    fun onPasswordInput()

    val passwordSize: Int

    val inputtedPasswordSize:Int
}