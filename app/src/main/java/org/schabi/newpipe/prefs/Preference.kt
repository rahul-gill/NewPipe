package org.schabi.newpipe.prefs

import android.content.Context
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

interface Preference<T> {
    fun setValue(value: T)
    val key: String
    val defaultValue: T
    val observableValue: Flow<T>
    val value: T
}

/**
 * Handle default values carefully, just like in enumPreference
 */
fun <T, BackingT> customPreference(
    backingPref: Preference<BackingT>,
    defaultValue: T,
    serialize: (T) -> BackingT,
    deserialize: (BackingT) -> T
) = object : Preference<T> {

    override val key = backingPref.key
    override val defaultValue = defaultValue
    override val observableValue: Flow<T>
        get() = backingPref.observableValue.map(deserialize)
    override val value: T
        get() = deserialize(backingPref.value)

    override fun setValue(value: T) {
        backingPref.setValue(serialize(value))
    }
}

inline fun <reified T : Enum<T>> enumPreference(
    context: Context,
    key: String,
    defaultValue: T
) = customPreference(
    backingPref = IntPreference(context, key, Int.MAX_VALUE),
    defaultValue,
    serialize = { it.ordinal },
    deserialize = {
        if (it == Int.MAX_VALUE) {
            defaultValue
        } else {
            enumValues<T>().getOrNull(it) ?: defaultValue
        }
    }
)

class IntPreference(
    context: Context,
    override val key: String,
    override val defaultValue: Int
) : Preference<Int> {
    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setValue(value: Int) {
        pref.edit {
            putInt(key, value)
        }
    }

    override val value: Int
        get() = pref.getInt(key, defaultValue)

    override val observableValue: Flow<Int>
        get() = callbackFlow {
            val callback = OnSharedPreferenceChangeListener { _, changedKey ->
                if (changedKey == key || changedKey == null) {
                    trySend(pref.getInt(key, defaultValue))
                }
            }
            pref.registerOnSharedPreferenceChangeListener(callback)
            invokeOnClose {
                pref.unregisterOnSharedPreferenceChangeListener(callback)
            }
        }
}

class BooleanPreference(
    context: Context,
    override val key: String,
    override val defaultValue: Boolean
) : Preference<Boolean> {
    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setValue(value: Boolean) {
        pref.edit {
            putBoolean(key, value)
        }
    }

    override val value: Boolean
        get() = pref.getBoolean(key, defaultValue)

    override val observableValue: Flow<Boolean>
        get() = callbackFlow {
            val callback = OnSharedPreferenceChangeListener { _, changedKey ->
                if (changedKey == key || changedKey == null) {
                    trySend(pref.getBoolean(key, defaultValue))
                }
            }
            pref.registerOnSharedPreferenceChangeListener(callback)
            invokeOnClose {
                pref.unregisterOnSharedPreferenceChangeListener(callback)
            }
        }
}

class LongPreference(
    context: Context,
    override val key: String,
    override val defaultValue: Long
) : Preference<Long> {
    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setValue(value: Long) {
        pref.edit {
            putLong(key, value)
        }
    }

    override val value: Long
        get() = pref.getLong(key, defaultValue)

    override val observableValue: Flow<Long>
        get() = callbackFlow {
            val callback = OnSharedPreferenceChangeListener { _, changedKey ->
                if (changedKey == key || changedKey == null) {
                    trySend(pref.getLong(key, defaultValue))
                }
            }
            pref.registerOnSharedPreferenceChangeListener(callback)
            invokeOnClose {
                pref.unregisterOnSharedPreferenceChangeListener(callback)
            }
        }
}

class StringPreference(
    context: Context,
    override val key: String,
    override val defaultValue: String
) : Preference<String> {

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setValue(value: String) {
        pref.edit {
            putString(key, value)
        }
    }

    override val value: String
        get() = pref.getString(key, defaultValue) ?: defaultValue

    override val observableValue: Flow<String>
        get() = callbackFlow {
            val callback = OnSharedPreferenceChangeListener { _, changedKey ->
                if (changedKey == key || changedKey == null) {
                    trySend(pref.getString(key, defaultValue) ?: defaultValue)
                }
            }
            pref.registerOnSharedPreferenceChangeListener(callback)
            invokeOnClose {
                pref.unregisterOnSharedPreferenceChangeListener(callback)
            }
        }
}
