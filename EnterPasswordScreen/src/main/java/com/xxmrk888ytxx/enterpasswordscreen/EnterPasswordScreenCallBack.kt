package com.xxmrk888ytxx.enterpasswordscreen

interface EnterPasswordScreenCallBack {
    fun onInputNumber(number:Int)

    fun onClearNumber()

    fun onClearAll()

    val passwordSize: Int

    val inputtedPasswordSize:Int
}