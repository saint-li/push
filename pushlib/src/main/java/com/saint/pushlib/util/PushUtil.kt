package com.saint.pushlib.util

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Process

object PushUtil {
    /**
     * 获取Manifest 里面的meta-data
     *
     * @param context
     * @param key
     * @return
     */
    @JvmStatic
    fun getMetaData(context: Context, key: String): String? {
        return try {
            val appInfo = context.packageManager
                .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            appInfo.metaData.getString(key)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * 包名判断是否为主进程
     *
     * @param
     * @return
     */
    fun isMainProcess(context: Context): Boolean {
        return context.applicationContext.packageName == getCurrentProcessName(context)
    }


    /**
     * 获取当前进程名
     */
    fun getCurrentProcessName(context: Context): String {
        val pid = Process.myPid()
        var processName = ""
        val manager =
            context.applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (process in manager.runningAppProcesses) {
            if (process.pid == pid) {
                processName = process.processName
            }
        }
        return processName
    }
}