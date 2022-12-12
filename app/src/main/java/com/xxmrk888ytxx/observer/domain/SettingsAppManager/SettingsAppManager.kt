package com.xxmrk888ytxx.observer.domain.SettingsAppManager

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CryptoManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TelegramConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TelegramConfigProvider
import com.xxmrk888ytxx.coredeps.fromJson
import com.xxmrk888ytxx.coredeps.models.TelegramConfig
import com.xxmrk888ytxx.coredeps.toJson
import com.xxmrk888ytxx.observer.DI.AppScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.text.Charsets.UTF_8

@AppScope
class SettingsAppManager @Inject constructor(
    private val context: Context,
    private val cryptoManager: CryptoManager
) : TelegramConfigChanger,TelegramConfigProvider {

    private object ProtectedPropertyKeys {
        val telegramConfigKey = stringPreferencesKey("telegramConfigKey")
    }

    private val Context.dataStore: DataStore<Preferences> by
        preferencesDataStore(name = "Property")

    private suspend fun <TYPE> writeProperty(key:Preferences.Key<TYPE>,value:TYPE) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    private fun <TYPE> getProperty(key:Preferences.Key<TYPE>,defValue:TYPE) : Flow<TYPE> {
        return context.dataStore.data.map {
            it[key] ?: defValue
        }
    }

    @JvmName("getProperty1")
    private fun <TYPE> getProperty(key:Preferences.Key<TYPE>, defValue:TYPE?) : Flow<TYPE?> {
        return context.dataStore.data.map {
            it[key] ?: defValue
        }
    }

    private suspend fun writeProtectedProperty(key:Preferences.Key<String>,value:String) {
        val cryptoBytes = cryptoManager.encryptData(
            value.toByteArray(UTF_8)
        )

        writeProperty(key, Base64.encodeToString(cryptoBytes,0))
    }

    private fun getProtectedProperty(key:Preferences.Key<String>) : Flow<String?> {
        val propertyFlow = getProperty(key,null)
        return propertyFlow.map {
            val cryptoString = it ?: return@map null
            val cryptoBytes = Base64.decode(cryptoString,0)
            return@map cryptoManager.decryptData(cryptoBytes).toString(UTF_8)
        }
    }

    private fun <KEYTYPE> isPropertyExist(key:Preferences.Key<KEYTYPE>) : Flow<Boolean> {
        return context.dataStore.data.map {
            it.contains(key)
        }
    }

    override suspend fun updateTelegramConfig(telegramConfig: TelegramConfig) {
        writeProtectedProperty(
            key = ProtectedPropertyKeys.telegramConfigKey,
            value = toJson(telegramConfig)
        )
    }

    override fun getTelegramConfig() : Flow<TelegramConfig?> {
        val jsonString = getProtectedProperty(ProtectedPropertyKeys.telegramConfigKey)

        return jsonString.map { fromJson(jsonString.first(),TelegramConfig::class.java) }

    }
}