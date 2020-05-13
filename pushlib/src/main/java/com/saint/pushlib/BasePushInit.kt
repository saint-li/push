package com.saint.pushlib

import android.app.Application
import android.content.Context

/**
 * 初始化推送平台的基类
 */
abstract class BasePushInit(isDebug: Boolean, private var mApplication: Application) {
    @JvmField
    protected var mContext: Application = mApplication

    @JvmField
    protected var isDebug = isDebug

    @JvmField
    protected var mAlias: String? = null

    /**
     * 设置别名
     *
     * @param alias
     */
    open fun setAlias(alias: String?) {
        mAlias = alias
    }

    open fun loginOut() {}
    open fun loginIn() {}

    open fun pushStatus() {}

}