package com.xxmrk888ytxx.observer.domain.AppPasswordManager

import androidx.datastore.preferences.core.stringPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CryptoManager
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class AppPasswordManager @Inject constructor(
    private val settingsAppManager: SettingsAppManager,
    private val cryptoManager: CryptoManager
) : AppPasswordChanger,AppPasswordProvider {

    private val appPasswordKey = stringPreferencesKey("AppPasswordKey")

    override suspend fun setupAppPassword(
        oldPassword: String?,
        newPassword:String
    ) {
        if(!isValidOldPassword(oldPassword)) {
            throw IllegalArgumentException("oldPassword is null or incorrect")
        }

        val passwordBytes = newPassword.toByteArray()
        val passwordHash = cryptoManager.hashFromData(passwordBytes)

        settingsAppManager.writeProtectedProperty(appPasswordKey,passwordHash)
    }

    override suspend fun isAppPassword(checkingPassword: String): Boolean {
        if(!isPasswordSetup()) throw IllegalStateException("Password is not setup")

        val checkingPasswordHash = cryptoManager.hashFromData(checkingPassword.toByteArray())
        val appPasswordHash = settingsAppManager.getProtectedProperty(appPasswordKey).first()
                ?: return false

        return checkingPasswordHash == appPasswordHash
    }

    override suspend fun isPasswordSetup(): Boolean {
        return settingsAppManager.getProtectedProperty(appPasswordKey).first() != null
    }

    private suspend fun isValidOldPassword(oldPassword: String?) : Boolean {
        if(!isPasswordSetup()) return true
        val checkingPassword = oldPassword ?: return false
        return isAppPassword(checkingPassword)
    }


}