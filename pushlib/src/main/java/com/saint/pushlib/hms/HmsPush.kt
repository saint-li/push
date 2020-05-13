package com.saint.pushlib.hms

import android.app.Application
import android.content.Context
import android.text.TextUtils
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.push.HmsMessaging
import com.saint.pushlib.BasePushInit
import com.saint.pushlib.PushConstant
import com.saint.pushlib.PushManager.init
import com.saint.pushlib.PushManager.setEnableHWPush
import com.saint.pushlib.R
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager
import com.saint.pushlib.util.PushLog.Companion.e
import com.saint.pushlib.util.PushLog.Companion.i

class HmsPush(isDebug: Boolean, application: Application) : BasePushInit(isDebug, application) {
    private val appId = AGConnectServicesConfig.fromContext(application).getString("client/app_id")
    private val instanceId = HmsInstanceId.getInstance(application)

    /**
     * 推送初始化
     *
     * @param isDebug     设置debug模式
     * @param application --
     */
    init {
        i("appId:$appId")
        if (TextUtils.isEmpty(appId)) {
            val info = ReceiverInfo()
            info.pushType = PushConstant.HUAWEI
            info.title = "华为推送"
            info.content = mContext.getString(R.string.init_failed)
            PushReceiverManager.onRegistration(application, info)
            setEnableHWPush(false)
            init(isDebug, application)
        } else {
            val info = ReceiverInfo()
            info.pushType = PushConstant.HUAWEI
            info.title = "华为推送"
            info.content = mContext.getString(R.string.init_succeed)
            PushReceiverManager.onRegistration(application, info)
        }
    }

    override fun loginIn() {
        getToken()
    }

    override fun loginOut() {
        object : Thread() {
            override fun run() {
                try {
                    HmsInstanceId.getInstance(mContext).deleteToken(appId, "HCM")
                } catch (e: Exception) {
                    e("deleteToken failed.", e)
                }
            }
        }.start()
    }

    /**
     * get token
     */
    private fun getToken() {
        object : Thread() {
            override fun run() {
                try {
                    // read from agconnect-services.json
//                    String appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id");
                    i("华为ID：$appId")
                    val pushtoken = instanceId.getToken(appId, "HCM")
                    if (!TextUtils.isEmpty(pushtoken)) {
                        i("get token:$pushtoken")
                        val info = ReceiverInfo()
                        info.pushType = PushConstant.HUAWEI
                        info.title = mContext.getString(R.string.get_token)
                        info.content = pushtoken
                        info.pushType = PushConstant.HUAWEI
                        PushReceiverManager.setToken(mContext, info)
                    }
                } catch (e: Exception) {
                    e("getToken failed, $e")
                }
            }
        }.start()
    }


    fun turnOnPush(context: Context?) {
        HmsMessaging.getInstance(context).turnOnPush()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    i("turnOnPush Complete")
                } else {
                    i("turnOnPush failed: ret=" + task.exception.message)
                }
            }
    }

    fun turnOffPush(context: Context?) {
        HmsMessaging.getInstance(context).turnOffPush()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    i("turnOffPush Complete")
                } else {
                    i("turnOffPush failed: ret=" + task.exception.message)
                }
            }
    }

}