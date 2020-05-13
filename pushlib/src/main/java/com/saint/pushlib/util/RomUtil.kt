package com.saint.pushlib.util

import android.os.Build
import android.text.TextUtils
import com.saint.pushlib.util.PushLog.Companion.e
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object RomUtil {
    private fun getSystemProperty(propName: String): String? {
        val line: String
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop $propName")
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (ex: IOException) {
            e("Unable to read sysprop $propName", ex)
            return null
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    e("Exception while closing InputStream", e)
                }
            }
        }
        return line
    }

    /**
     * 判断是否为华为UI
     */
    fun isHuaweiRom(): Boolean {
        val manufacturer = Build.MANUFACTURER
        return !TextUtils.isEmpty(manufacturer) && manufacturer.contains("HUAWEI")
    }

    /**
     * 判断是否为小米UI
     */
    fun isMiuiRom(): Boolean {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"))
    }

    /**
     * 判断是否为OPPOUI
     */
    fun isOPPORom(): Boolean {
        return !TextUtils.isEmpty(getSystemProperty("ro.build.version.opporom"))
    }

    /**
     * 判断是否为VivoUI
     */
    fun isVivoRom(): Boolean {
        return !TextUtils.isEmpty(getSystemProperty("ro.vivo.os.version"))
    }
}