package com.saint.pushlib.mipush

import android.app.Application
import android.text.TextUtils
import com.saint.pushlib.BasePushInit
import com.saint.pushlib.PushConstant
import com.saint.pushlib.PushManager.init
import com.saint.pushlib.PushManager.setEnableMiPush
import com.saint.pushlib.R
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager
import com.saint.pushlib.util.PushLog.Companion.d
import com.saint.pushlib.util.PushLog.Companion.e
import com.saint.pushlib.util.PushLog.Companion.i
import com.saint.pushlib.util.PushUtil.getMetaData
import com.xiaomi.channel.commonutils.logger.LoggerInterface
import com.xiaomi.mipush.sdk.Logger
import com.xiaomi.mipush.sdk.MiPushClient

class MiPushInit(isDebug: Boolean, application: Application) : BasePushInit(isDebug, application) {
    override fun loginIn() {
        val info = ReceiverInfo()
        info.title = mContext.getString(R.string.get_token)
        info.content = MiPushClient.getRegId(mContext)
        info.pushType = PushConstant.XIAOMI
        PushReceiverManager.setToken(mContext, info)
    }

    override fun setAlias(alias: String?) {
        MiPushClient.setAlias(mContext, alias, null)
    }

    override fun loginOut() {
        MiPushClient.unsetAlias(mContext, mAlias, null)
        val aliasInfo = ReceiverInfo()
        aliasInfo.title = mContext.getString(R.string.tip_off_push_succeed)
        if (mAlias != null)
            aliasInfo.content = mAlias!!
        aliasInfo.pushType = PushConstant.XIAOMI
        PushReceiverManager.onLoginOut(mContext, aliasInfo)
    }

    /**
     * 推送初始化
     *
     * @param isDebug     设置debug模式
     * @param application --
     */
    init {
        //注册SDK
        val appId = getMetaData(application, "MIPUSH_APPID")
        val appKey = getMetaData(application, "MIPUSH_APPKEY")
        i("appId:$appId appKey:$appKey")
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(appKey)) {
            val info = ReceiverInfo()
            info.pushType = PushConstant.XIAOMI
            info.title = "小米推送"
            info.content = mContext.getString(R.string.init_failed)
            PushReceiverManager.onRegistration(application, info)
            setEnableMiPush(false)
            init(isDebug, application)
        } else {
            MiPushClient.registerPush(
                application,
                appId!!.replace(" ".toRegex(), ""),
                appKey!!.replace(" ".toRegex(), "")
            )
            if (isDebug) {
                val newLogger: LoggerInterface = object : LoggerInterface {
                    override fun setTag(tag: String) {
                        // ignore
                    }

                    override fun log(content: String, t: Throwable) {
                        e(content, t)
                    }

                    override fun log(content: String) {
                        d(content)
                    }
                }
                Logger.setLogger(application, newLogger)
            }
        }
    }
}