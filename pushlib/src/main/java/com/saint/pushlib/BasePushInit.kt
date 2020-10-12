package com.saint.pushlib

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager

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

    protected fun initSucceed(title: String, pushType: Int) {
        val info = ReceiverInfo()
        info.title = title
        info.pushType = pushType
        info.content = mContext.getString(R.string.init_succeed)
        PushReceiverManager.onRegistration(mContext, info)
    }

    protected fun initFailed(title: String, pushType: Int, errorMsg: String) {
        val info = ReceiverInfo()
        info.title = title
        info.pushType = pushType
        info.content = mContext.getString(R.string.init_failed)
        info.extra = errorMsg
        PushControl.setEnablePush(pushType)
        PushControl.init(isDebug, mContext)
    }

    protected fun onToken(token: String, pushType: Int) {
        val info = ReceiverInfo()
        info.title = mContext.getString(R.string.get_token)
        info.content = token
        info.pushType = pushType
        PushReceiverManager.setToken(mContext, info)
    }

    protected fun getString(@StringRes resId: Int): String {
        return mContext.getString(resId)
    }

}