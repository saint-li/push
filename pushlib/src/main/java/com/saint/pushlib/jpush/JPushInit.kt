package com.saint.pushlib.jpush

import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.saint.pushlib.BasePushInit
import com.saint.pushlib.PushConstant
import com.saint.pushlib.R
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager

class JPushInit(isDebug: Boolean, application: Application) : BasePushInit(isDebug, application) {
    override fun loginIn() {
        val info = ReceiverInfo()
        info.title = mContext.getString(R.string.get_token)
        info.content = JPushInterface.getRegistrationID(mContext)
        info.pushType = PushConstant.JPUSH
        PushReceiverManager.setToken(mContext, info)
    }

    override fun setAlias(alias: String?) {
        super.setAlias(alias)
        JPushInterface.setAlias(mContext, 0, alias)
    }

    override fun loginOut() {
        JPushInterface.deleteAlias(mContext, 0)
        val aliasInfo = ReceiverInfo()
        aliasInfo.title = mContext.getString(R.string.tip_off_push_succeed)
        if (mAlias != null) aliasInfo.content = mAlias!!
        aliasInfo.pushType = PushConstant.JPUSH
        PushReceiverManager.onLoginOut(mContext, aliasInfo)
    }

    /**
     * 推送初始化
     *
     * @param isDebug     设置debug模式
     * @param application --
     */
    init {
        JPushInterface.setDebugMode(isDebug)
        JPushInterface.init(application)
        val info = ReceiverInfo()
        info.pushType = PushConstant.JPUSH
        info.title = "极光推送"
        info.content = mContext.getString(R.string.init_succeed)
        PushReceiverManager.onRegistration(application, info)
    }
}