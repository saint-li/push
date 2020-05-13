package com.saint.pushlib

import android.app.Application
import android.content.Context
import com.heytap.msp.push.HeytapPushManager
import com.saint.pushlib.PushConstant.HUAWEI
import com.saint.pushlib.PushConstant.JPUSH
import com.saint.pushlib.PushConstant.OPPO
import com.saint.pushlib.PushConstant.XIAOMI
import com.saint.pushlib.hms.HmsPush
import com.saint.pushlib.jpush.JPushInit
import com.saint.pushlib.mipush.MiPushInit
import com.saint.pushlib.opush.OPushInit
import com.saint.pushlib.util.PushLog
import com.saint.pushlib.util.PushUtil.isMainProcess
import com.saint.pushlib.util.RomUtil

object PushManager {
    /**
     * 当前的推送平台，默认为极光 JPUSH
     */
    private var mPushTarget: BasePushInit? = null
    private var enableHWPush = true
    private var enableMiPush = true
    private var enableOPPOPush = true

    var app: Application? = null

    fun init(isDebug: Boolean, app: Application) {
        if (!isMainProcess(app)) return
        this.app = app
        //日志打印
        PushLog.debug = isDebug
        /**
         * 根据设备厂商和使用者设置来选择推送平台,小米的使用小米推送，华为使用华为推送...其他的使用极光推送
         */
        val type = getPhoneType(app)
        mPushTarget = when (type) {
            XIAOMI -> {
                MiPushInit(isDebug, app)
            }
            HUAWEI -> {
                HmsPush(isDebug, app)
            }
            OPPO -> {
                OPushInit(isDebug, app)
            }
            else -> {
                JPushInit(isDebug, app)
            }
        }
    }

    fun init(
        isDebug: Boolean, app: Application,
        enableHW: Boolean, enableMi: Boolean, enableOPPO: Boolean
    ) {
        if (!isMainProcess(app)) return
        this.enableHWPush = enableHW
        this.enableMiPush = enableMi
        this.enableOPPOPush = enableOPPO
        init(isDebug, app)
    }

    private fun getPhoneType(context: Context): Int {
        context.packageManager
        val phoneType: Int = if (RomUtil.isHuaweiRom() && enableHWPush) {
            HUAWEI
        } else if (RomUtil.isMiuiRom() && enableMiPush) {
            XIAOMI
        } else if (RomUtil.isOPPORom()
//            && HeytapPushManager.isSupportPush()//新版检测只有初始化之后才能调用
            && enableOPPOPush
        ) {
            OPPO
        } else {
            JPUSH
        }

//         else if (RomUtil.isVivoRom() && PushClient.getInstance(context).isSupport() && enableVivoPush) {
//            phoneType = VIVO;
//        }
//
        PushLog.d("当前手机类型： $phoneType")
        return phoneType
    }

    /**
     * 登录，华为登录需要在activity中
     *  延迟执行，因小米出现初始化后就获取registerid出现为空的情况
     * @param activity
     */
    fun loginIn() {
        Thread(Runnable {
            Thread.sleep(500)
            mPushTarget!!.loginIn()
        }).start()
    }

    /**
     * 登出，登出后，设置alias为空，或者传递token给服务器为空
     */
    fun loginOut() {
        mPushTarget!!.loginOut()
    }

    fun pushStatus() {
        mPushTarget!!.pushStatus()
    }

    fun setEnableHWPush(enableHWPush: Boolean) {
        this.enableHWPush = enableHWPush
    }

    fun setEnableMiPush(enableMiPush: Boolean) {
        this.enableMiPush = enableMiPush
    }

    fun setEnableOPPOPush(enableOPPOPush: Boolean) {
        this.enableOPPOPush = enableOPPOPush
    }

    fun setAlias(alias: String?) {
        mPushTarget!!.setAlias(alias)
    }
}