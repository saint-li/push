package com.saint.pushlib.opush

import android.app.Application
import android.content.Context
import android.text.TextUtils
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.heytap.mcssdk.PushManager
import com.heytap.mcssdk.callback.PushAdapter
import com.heytap.mcssdk.callback.PushCallback
import com.heytap.mcssdk.mode.SubscribeResult
import com.saint.pushlib.BasePushInit
import com.saint.pushlib.PushConstant
import com.saint.pushlib.PushConstant.OPPO
import com.saint.pushlib.PushControl.init
import com.saint.pushlib.PushControl.setEnableOPPOPush
import com.saint.pushlib.R
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager
import com.saint.pushlib.util.PushLog
import com.saint.pushlib.util.PushLog.Companion.i
import com.saint.pushlib.util.PushUtil.getMetaData

class OPushInit(isDebug: Boolean, application: Application) : BasePushInit(isDebug, application) {
    private var getToken = false
    override fun loginIn() {
        var registerID = PushManager.getInstance().registerID
        if (!TextUtils.isEmpty(registerID)) {
            onToken(registerID, OPPO)
        } else {
            getToken = true
            PushManager.getInstance().getRegister()
        }
    }

    /**
     * 推送初始化
     *
     * @param isDebug     设置debug模式
     * @param application --
     */
    init {
        val appSecret = getMetaData(application, "OPPO_SECRET")
        val appKey = getMetaData(application, "OPPO_APPKEY")
        i("appSecret:$appSecret appKey:$appKey")
        if (!TextUtils.isEmpty(appKey) && !TextUtils.isEmpty(appSecret)) {
            PushManager.getInstance().register(application, appKey, appSecret, OPushAdapter())
        } else {
            initFailed(
                getString(R.string.OPPO),
                PushConstant.OPPO,
                "OPPO_APPKEY=$appKey,appSecret=$appSecret"
            )
        }
    }

    inner class OPushAdapter : PushAdapter() {

        override fun onRegister(code: Int, s: String) {
            if (code == 0) {
                if (getToken) {
                    showResult("获取RegisterId成功", "registerId:$s")
                    onToken(s, OPPO)
                } else {
                    showResult("注册成功", "registerId:$s")
                    initSucceed(getString(R.string.OPPO), PushConstant.OPPO)
                }
            } else {
                showResult("注册失败", "code=$code,msg=$s")
                initFailed(getString(R.string.OPPO), PushConstant.OPPO, "code=$code,msg=$s")
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
        i("$tag:$msg")
    }

}