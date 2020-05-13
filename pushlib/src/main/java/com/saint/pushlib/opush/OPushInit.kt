package com.saint.pushlib.opush

import android.app.Application
import android.content.Context
import android.text.TextUtils
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.heytap.msp.push.HeytapPushManager
import com.heytap.msp.push.callback.ICallBackResultService
import com.saint.pushlib.BasePushInit
import com.saint.pushlib.PushConstant
import com.saint.pushlib.PushManager.init
import com.saint.pushlib.PushManager.setEnableOPPOPush
import com.saint.pushlib.R
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager
import com.saint.pushlib.util.PushLog
import com.saint.pushlib.util.PushLog.Companion.i
import com.saint.pushlib.util.PushUtil.getMetaData

class OPushInit(isDebug: Boolean, application: Application) : BasePushInit(isDebug, application) {
    override fun loginIn() {
        val info = ReceiverInfo()
        info.title = mContext.getString(R.string.get_token)
        info.content = HeytapPushManager.getRegisterID()
        info.pushType = PushConstant.XIAOMI
        PushReceiverManager.setToken(mContext, info)
    }

    /**
     * 推送初始化
     */
    init {
        val appKey = getMetaData(application, "OPPO_APPKEY")
        val appSecret = getMetaData(application, "OPPO_SECRET")
        i("appSecret:$appSecret appKey:$appKey")

        HeytapPushManager.init(application, isDebug)

        if (HeytapPushManager.isSupportPush()
            && !TextUtils.isEmpty(appKey)
            && !TextUtils.isEmpty(appSecret)
        ) {
            try {
//                HeytapPushManager.init(application, isDebug)
                //setPushCallback接口也可设置callback
                HeytapPushManager.register(application, appKey, appSecret, PushCallBack())
                HeytapPushManager.requestNotificationPermission()
            } catch (e: Exception) {
                e.printStackTrace()
                failed(isDebug, application)
            }
        } else {
            failed(isDebug, application)
        }
    }

    private fun succeed(context: Context) {
        val info = ReceiverInfo()
        info.pushType = PushConstant.OPPO
        info.title = "OPPO推送"
        info.content = mContext.getString(R.string.init_succeed)
        PushReceiverManager.onRegistration(context, info)
    }

    private fun failed(isDebug: Boolean, application: Application) {
        val info = ReceiverInfo()
        info.pushType = PushConstant.OPPO
        info.title = "OPPO推送"
        info.content = mContext.getString(R.string.init_failed)
        setEnableOPPOPush(false)
        init(isDebug, application)
    }

    override fun pushStatus() {
        super.pushStatus()
        showResult("推送", "当前：${HeytapPushManager.isSupportPush()}")
        HeytapPushManager.getPushStatus()
    }


    /************************************************************************************
     * ***************************callbacks from mcs************************************
     */
    inner class PushCallBack : ICallBackResultService {
        override fun onRegister(code: Int, s: String) {
            if (code == 0) {
                succeed(mContext)
                showResult("注册成功", "registerId:$s")
            } else {
                failed(isDebug, mContext)
                showResult("注册失败", "code=$code,msg=$s")
            }
        }

        override fun onUnRegister(code: Int) {
            if (code == 0) {
                showResult("注销成功", "code=$code")
            } else {
                showResult("注销失败", "code=$code")
            }
        }

        override fun onGetPushStatus(code: Int, status: Int) {
            if (code == 0 && status == 0) {
                showResult("Push状态正常", "code=$code,status=$status")
            } else {
                showResult("Push状态错误", "code=$code,status=$status")
            }
        }

        override fun onGetNotificationStatus(code: Int, status: Int) {
            if (code == 0 && status == 0) {
                showResult("通知状态正常", "code=$code,status=$status")
            } else {
                showResult("通知状态错误", "code=$code,status=$status")
            }
        }

        override fun onSetPushTime(code: Int, s: String) {
            showResult("SetPushTime", "code=$code,result:$s")
        }
    }

    /**
     * 此方法会将结果进行回显
     */
    private fun showResult(@Nullable tag: String, @NonNull msg: String) {
        PushLog.d("$tag:$msg")
    }
}