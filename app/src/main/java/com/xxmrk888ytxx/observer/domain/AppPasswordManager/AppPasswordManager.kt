package com.xxmrk888ytxx.observer.domain.AppPasswordManager

import androidx.datastore.preferences.core.stringPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CryptoManager
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AppPasswordManager @Inject constructor(
    private val settingsAppManager: SettingsAppManager,
    private val cryptoManager: CryptoManager
) : AppPasswordChanger,AppPasswordProvider {

    private val appPasswordKey = stringPreferencesKey("AppPasswordKey")

    override suspend fun setupAppPassword(
        password:String
    ) {
        if(isPasswordSetup().first()) {
            throw IllegalStateException("Password is already Setup")
        }

        val passwordBytes = password.toByteArray()
        val passwordHash = cryptoManager.hashFromData(passwordBytes)

        settingsAppManager.writeProtectedProperty(appPasswordKey,passwordHash)
    }

    override suspend fun isAppPassword(checkingPassword: String): Boolean {
        if(!isPasswordSetup().first()) throw IllegalStateException("Password is not setup")

        val checkingPasswordHash = cryptoManager.hashFromData(checkingPassword.toByteArray())
        val appPasswordHash = settingsAppManager.getProtectedProperty(appPasswordKey).first()
                ?: return false

        return checkingPasswordHash == appPasswordHash
    }

    override fun isPasswordSetup(): Flow<Boolean> {
        return settingsAppManager.getProtectedProperty(appPasswordKey).map {
            it != null
        }
    }

    private suspend fun isValidOldPassword(oldPassword: String?) : Boolean {
        if(!isPasswordSetup().first()) return true
        val checkingPassword = oldPassword ?: return false
        return isAppPassword(checkingPassword)
    }


}