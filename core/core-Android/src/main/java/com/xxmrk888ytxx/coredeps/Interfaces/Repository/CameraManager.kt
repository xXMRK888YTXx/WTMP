package com.xxmrk888ytxx.coredeps.Interfaces.Repository

import java.io.File

interface CameraManager {
    fun createPhoto(
        outputFile: File,
        onSuccess:() -> Unit = {},
        onErrorCreate:(e:Exception) -> Unit = {}
    )
}