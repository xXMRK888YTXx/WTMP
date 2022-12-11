package com.xxmrk888ytxx.cryptomanager

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.*
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CryptoManager
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

class CryptoManagerImpl @Inject constructor() : CryptoManager {
    private val provider = "AndroidKeyStore"
    private val keyAlias = "keyAliasStore"
    private val cipher by lazy {
        Cipher.getInstance("AES/GCM/NoPadding")
    }
    private val charset by lazy {
        charset("UTF-8")

    }
    private val keyStore by lazy {
        KeyStore.getInstance(provider).apply {
            load(null)
        }
    }
    private val keyGenerator by lazy {
        KeyGenerator.getInstance(KEY_ALGORITHM_AES)
    }

    override fun encryptData(bytes: ByteArray): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, provideSecretKey(keyAlias))
        return cipher.doFinal(bytes)
    }

    override fun decryptData(encryptedData: ByteArray): ByteArray {
        cipher.init(Cipher.DECRYPT_MODE, provideSecretKey(keyAlias), GCMParameterSpec(128, cipher.iv))
        return cipher.doFinal(encryptedData)
    }

    private fun provideSecretKey(keyAlias: String) : SecretKey {
        return (keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry)?.secretKey
            ?: keyGenerator.apply {
                init(
                    KeyGenParameterSpec
                        .Builder(keyAlias, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                        .setBlockModes(BLOCK_MODE_GCM)
                        .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
                        .build()
                )
            }.generateKey()
    }
}