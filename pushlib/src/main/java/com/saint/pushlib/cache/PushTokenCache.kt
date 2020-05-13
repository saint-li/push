package com.saint.pushlib.cache

import android.content.Context
import android.content.SharedPreferences

/**
 *
 */
object PushTokenCache {
    private const val FILE_PUSH_LIBRARY_CACHE = "push_library_cache"
    private const val KEY_TOKEN = "token"
    private const val KEY_PLATFORM = "platform"

    @JvmStatic
    fun putToken(context: Context, token: String?) {
        getSharedPreferences(context).edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_TOKEN, null)
    }

    fun delToken(context: Context) {
        getSharedPreferences(context).edit().remove(KEY_TOKEN).apply()
    }

    fun putPlatform(context: Context, platform: String?) {
        getSharedPreferences(context).edit().putString(KEY_PLATFORM, platform).apply()
    }

    fun getPlatform(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_PLATFORM, null)
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(FILE_PUSH_LIBRARY_CACHE, Context.MODE_PRIVATE)
    }

    fun delPlatform(context: Context) {
        getSharedPreferences(context).edit().remove(KEY_PLATFORM).apply()
    }
}