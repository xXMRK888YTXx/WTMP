package com.xxmrk888ytxx.observer.domain.SettingsAppManager

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CryptoManager
import com.xxmrk888ytxx.observer.DI.AppScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.text.Charsets.UTF_8

/**
 * [Ru]
 * Класс для записи/чтения настроек приложения
 * Для управеления настройками используется библеотека DataStore
 */
/**
 * [Ru]
 * Class for writing/reading application settings
 * To manage the settings, the DataStore library is used
 */
@AppScope
internal class SettingsAppManager @Inject constructor(
    private val context: Context,
    private val cryptoManager: CryptoManager
)
{
    private val Context.dataStore: DataStore<Preferences> by
        preferencesDataStore(name = "Property")

    suspend fun <TYPE> writeProperty(key:Preferences.Key<TYPE>,value:TYPE) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    fun <TYPE> getProperty(key:Preferences.Key<TYPE>,defValue:TYPE) : Flow<TYPE> {
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

    suspend fun writeProtectedProperty(key:Preferences.Key<String>,value:String) {
        val cryptoBytes = cryptoManager.encryptData(
            value.toByteArray(UTF_8)
        )

        writeProperty(key, Base64.encodeToString(cryptoBytes,0))
    }

    fun getProtectedProperty(key:Preferences.Key<String>) : Flow<String?> {
        val propertyFlow = getProperty(key,null)
        return propertyFlow.map {
            val cryptoString = it ?: return@map null
            val cryptoBytes = Base64.decode(cryptoString,0)
            return@map cryptoManager.decryptData(cryptoBytes).toString(UTF_8)
        }
    }

    fun <KEYTYPE> isPropertyExist(key:Preferences.Key<KEYTYPE>) : Flow<Boolean> {
        return context.dataStore.data.map {
            it.contains(key)
        }
    }



}