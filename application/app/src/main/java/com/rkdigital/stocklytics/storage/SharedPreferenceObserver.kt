package com.rkdigital.stocklytics.storage
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener

class SharedPreferenceObserver private constructor(context: Context) {

    private val appPrefs: SharedPreferences =
        context.getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
    private val userPrefs: SharedPreferences =
        context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)

    private val listeners = mutableListOf<OnPreferenceChangeListener>()

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            listeners.forEach { it.onPreferenceChanged(prefs, key) }
        }

    companion object {
        private var instance: SharedPreferenceObserver? = null

        fun getInstance(context: Context): SharedPreferenceObserver {
            if (instance == null) {
                instance = SharedPreferenceObserver(context.applicationContext)
            }
            return instance!!
        }
    }

    interface OnPreferenceChangeListener {
        fun onPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?)
    }

    fun addObserver(listener: OnPreferenceChangeListener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    fun removeObserver(listener: OnPreferenceChangeListener) {
        listeners.remove(listener)
    }

    fun register() {
        appPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
        userPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    fun clear() {
        appPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        userPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        listeners.clear()
    }
}