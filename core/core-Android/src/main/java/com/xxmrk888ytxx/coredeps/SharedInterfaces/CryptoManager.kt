package com.xxmrk888ytxx.coredeps.SharedInterfaces

interface CryptoManager {
    fun encryptData(bytes: ByteArray): ByteArray

    fun decryptData(encryptedData: ByteArray): ByteArray
}