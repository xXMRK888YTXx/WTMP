package com.xxmrk888ytxx.observer.domain.AppPasswordManager

import androidx.datastore.preferences.core.booleanPreferencesKey
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

    private val fingerPrintAuthorizationStateKey
        = booleanPreferencesKey("fingerPrintAuthorizationStateKey")

    override suspend fun setupAppPassword(
        password:String
    ) {
        if(isPasswordSetupFlow().first()) {
            throw IllegalStateException("Password is already Setup")
        }

        val passwordBytes = password.toByteArray()
        val passwordHash = cryptoManager.hashFromData(passwordBytes)

        settingsAppManager.writeProtectedProperty(appPasswordKey,passwordHash)
    }

    override suspend fun removePassword(currentPassword: String) {
        if(!isAppPassword(currentPassword)) throw IllegalArgumentException("Password is incorrect")

        settingsAppManager.removeProperty(appPasswordKey)
        settingsAppManager.removeProperty(fingerPrintAuthorizationStateKey)
    }

    override suspend fun updateFingerPrintAuthorizationState(state: Boolean) {
        settingsAppManager.writeProperty(fingerPrintAuthorizationStateKey,state)
    }

    override suspend fun isAppPassword(checkingPassword: String): Boolean {
        if(!isPasswordSetupFlow().first()) throw IllegalStateException("Password is not setup")

        val checkingPasswordHash = cryptoManager.hashFromData(checkingPassword.toByteArray())
        val appPasswordHash = settingsAppManager.getProtectedProperty(appPasswordKey).first()
                ?: return false

        return checkingPasswordHash == appPasswordHash
    }

    override fun isPasswordSetupFlow(): Flow<Boolean> {
        return settingsAppManager.getProtectedProperty(appPasswordKey).map {
            it != null
        }
    }

    override fun isFingerPrintAuthorizationEnabled(): Flow<Boolean> {
        return settingsAppManager.getProperty(fingerPrintAuthorizationStateKey,false)
    }
}