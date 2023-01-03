package com.xxmrk888ytxx.enterpasswordscreen

interface EnterPasswordScreenCallBack {
    fun onInputNumber(number:Int)

    fun onClearNumber()

    fun onClearAll()

    fun onSendFingerPrintRequest()

    val passwordSize: Int

    val enableFingerPrintAuthorization : Boolean
}