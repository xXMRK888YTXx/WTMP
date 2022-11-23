package com.xxmrk888ytxx.coredeps.Interfaces.Repository

import java.io.File

/**
 * [Ru]
 * Данный интерфейс предназначен для создания фотографий с камеры устройства,
 * предварительно необходимо запросить разрешение (android.permission.CAMERA)
 *
 * [En]
 * This interface is designed to take photos from the device's camera,
 * you must first request permission (android.permission.CAMERA)
 */
interface CameraManager {
    fun createPhoto(
        outputFile: File,
        onSuccess:() -> Unit = {},
        onErrorCreate:(e:Exception) -> Unit = {}
    )
}