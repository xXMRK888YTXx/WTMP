package com.xxmrk888ytxx.observer.domain.SettingsAppManager

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig.TelegramConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig.TelegramConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CryptoManager
import com.xxmrk888ytxx.coredeps.fromJson
import com.xxmrk888ytxx.coredeps.models.FailedUnlockTrackedConfig
import com.xxmrk888ytxx.coredeps.models.TelegramConfig
import com.xxmrk888ytxx.coredeps.toJson
import com.xxmrk888ytxx.observer.DI.AppScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.text.Charsets.UTF_8

@AppScope
class SettingsAppManager @Inject constructor(
    private val context: Context,
    private val cryptoManager: CryptoManager
) : TelegramConfigChanger,
    TelegramConfigProvider,
    FailedUnlockTrackedConfigChanger,
    FailedUnlockTrackedConfigProvider
{

    private object ProtectedPropertyKeys {
        object TelegramConfigKeys {
            val telegramConfigKey = stringPreferencesKey("telegramConfigKey")
        }
    }

    private object FailedUnlockTrackedConfigKeys {
        val isTrackedKey = booleanPreferencesKey("FailedUnlockTrackedConfigKeys/isTrackedKey")
        val makePhotoKey = booleanPreferencesKey("FailedUnlockTrackedConfigKeys/mackPhotoKey")
        val notifyInTelegram = booleanPreferencesKey("FailedUnlockTrackedConfigKeys/notifyInTelegramKey")
        val joinPhotoToTelegramNotify = booleanPreferencesKey("FailedUnlockTrackedConfigKeys/joinPhotoToTelegramNotify")
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
            key = ProtectedPropertyKeys.TelegramConfigKeys.telegramConfigKey,
            value = toJson(telegramConfig)
        )
    }

    override val telegramConfig: Flow<TelegramConfig?>
        get() {
            val jsonString = getProtectedProperty(ProtectedPropertyKeys.TelegramConfigKeys.telegramConfigKey)

            return jsonString.map { fromJson(jsonString.first(), TelegramConfig::class.java) }

        }

    //FailedUnlockTrackedConfigChanger
    override suspend fun updateIsTracked(state: Boolean) {
        writeProperty(
            FailedUnlockTrackedConfigKeys.isTrackedKey,
            state
        )
    }

    override suspend fun updateMakePhoto(state: Boolean) {
        writeProperty(
            FailedUnlockTrackedConfigKeys.makePhotoKey,
            state
        )
    }

    override suspend fun updateNotifyInTelegram(state: Boolean) {
        writeProperty(
            FailedUnlockTrackedConfigKeys.notifyInTelegram,
            state
        )
    }

    override suspend fun updateJoinPhotoToTelegramNotify(state: Boolean) {
        writeProperty(
            FailedUnlockTrackedConfigKeys.joinPhotoToTelegramNotify,
            state
        )
    }

    //FailedUnlockTrackedConfigProvider
    override val config: Flow<FailedUnlockTrackedConfig>
        get() {
            val isTrackedFlow = getProperty(FailedUnlockTrackedConfigKeys.isTrackedKey,false)
            val makePhotoFlow = getProperty(FailedUnlockTrackedConfigKeys.makePhotoKey,false)
            val notifyInTelegramFlow = getProperty(FailedUnlockTrackedConfigKeys.notifyInTelegram,false)
            val joinPhotoToTelegramNotifyFlow = getProperty(
                FailedUnlockTrackedConfigKeys.joinPhotoToTelegramNotify,
                false
            )

            return combine(isTrackedFlow,makePhotoFlow,
                notifyInTelegramFlow,joinPhotoToTelegramNotifyFlow
            ) {
                    isTracked:Boolean ,makePhoto:Boolean,
                    notifyInTelegram:Boolean,joinPhotoToTelegramNotify:Boolean ->
               FailedUnlockTrackedConfig(
                   isTracked, makePhoto, notifyInTelegram, joinPhotoToTelegramNotify
               )
            }
        }
}