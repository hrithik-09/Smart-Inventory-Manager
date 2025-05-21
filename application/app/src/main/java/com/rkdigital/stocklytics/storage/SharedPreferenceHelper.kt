package com.rkdigital.stocklytics.storage

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper private constructor(context: Context){

    private val appPrefs: SharedPreferences =
        context.getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE)
    private val userPrefs: SharedPreferences =
        context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE)
    companion object {
        private var instance: SharedPreferenceHelper? = null
        private const val APP_PREFS_NAME = "appPrefs"
        private const val USER_PREFS_NAME = "userPrefs"

        fun getInstance(context: Context): SharedPreferenceHelper {
            if (instance == null) {
                instance = SharedPreferenceHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    fun getAppPrefs(): SharedPreferences = appPrefs
    fun getUserPrefs(): SharedPreferences = userPrefs

    fun getBoolean(prefs: SharedPreferences, key: String, defaultValue: Boolean): Boolean =
        prefs.getBoolean(key, defaultValue)

    fun getString(prefs: SharedPreferences, key: String, defaultValue: String?): String? =
        prefs.getString(key, defaultValue)

    fun getInt(prefs: SharedPreferences, key: String, defaultValue: Int): Int =
        prefs.getInt(key, defaultValue)

    fun putString(prefs: SharedPreferences, key: String, value: String?) {
        prefs.edit().putString(key, value).apply()
    }

    fun putBoolean(prefs: SharedPreferences, key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun putInt(prefs: SharedPreferences, key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun clearKey(prefs: SharedPreferences, key: String) {
        prefs.edit().remove(key).apply()
    }

    fun clearAll(prefs: SharedPreferences) {
        prefs.edit().clear().apply()
    }
}