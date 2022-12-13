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

/**
 * [Ru]
 * Реализация интерфейса [CryptoManager]
 * Представлят собой симитричное шифрование AES/GCM/NoPadding
 * ключ получает из хранилища ключей Android
 */
/**
 * [En]
 * Implementation of the [CryptoManager] interface
 * Represents AES/GCM/NoPadding symmetric encryption
 * key gets from android key store
 */
class CryptoManagerImpl @Inject constructor() : CryptoManager {
    private val provider = "AndroidKeyStore"
    private val keyAlias = "keyAliasStore"
    private val cipher by lazy {
        Cipher.getInstance("AES/GCM/NoPadding")
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
        val encryptedBytes = cipher.doFinal(bytes)
        val iv = cipher.iv
        return iv + encryptedBytes
    }

    override fun decryptData(encryptedData: ByteArray): ByteArray {
        val iv = encryptedData.copyOf(12)
        val encryptedBytes = encryptedData.drop(12).toByteArray()
        cipher.init(Cipher.DECRYPT_MODE, provideSecretKey(keyAlias), GCMParameterSpec(128, iv))
        return cipher.doFinal(encryptedBytes)
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