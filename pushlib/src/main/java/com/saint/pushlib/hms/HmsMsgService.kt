package com.saint.pushlib.hms

import android.text.TextUtils
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import com.saint.pushlib.PushConstant
import com.saint.pushlib.R
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager
import com.saint.pushlib.util.PushLog.Companion.i

class HmsMsgService : HmsMessageService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        i("华为pushToken: $token")
        //获取token成功，token用于标识设备的唯一性
        if (!TextUtils.isEmpty(token)) {
            val info = createReceiverInfo()
            info.title = applicationContext.getString(R.string.get_token)
            info.content = token
            info.pushType = PushConstant.HUAWEI
            PushReceiverManager.setToken(this, info)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
//        super.onMessageReceived(message);
        i("onMessageReceived is called")
        if (message.data.isNotEmpty()) {
            i("Message data payload: " + message.data)
            val info = createReceiverInfo()
            info.content = message.data
            PushReceiverManager.onMessageReceived(this, info)
        }
        // Check if this message contains a notification payload.
        if (message.notification != null) {
            i("Message Notification Body: " + message.notification.body)
        }
        // TODO: your's other processing logic
    }

    private fun createReceiverInfo(): ReceiverInfo {
        val info = ReceiverInfo()
        info.pushType = PushConstant.HUAWEI
        return info
    }
}