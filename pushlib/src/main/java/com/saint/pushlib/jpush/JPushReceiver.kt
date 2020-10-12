package com.saint.pushlib.jpush

import android.content.Context
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver
import com.saint.pushlib.PushConstant
import com.saint.pushlib.PushControl.app
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager
import com.saint.pushlib.util.PushLog.Companion.i

class JPushReceiver : JPushMessageReceiver() {
    override fun onTagOperatorResult(context: Context, jPushMessage: JPushMessage) {
        super.onTagOperatorResult(context, jPushMessage)
    }

    override fun onCheckTagOperatorResult(context: Context, jPushMessage: JPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage)
    }

    override fun onAliasOperatorResult(context: Context, jPushMessage: JPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage)
    }

    override fun onMobileNumberOperatorResult(context: Context, jPushMessage: JPushMessage) {
        super.onMobileNumberOperatorResult(context, jPushMessage)
    }

    override fun onNotifyMessageArrived(context: Context, message: NotificationMessage) {
        super.onNotifyMessageArrived(context, message)
        i("极光通知Arrived：$message")
        PushReceiverManager.onMessageReceived(context, convertToReceiverInfo(message))
    }

    override fun onNotifyMessageOpened(context: Context, message: NotificationMessage) {
        super.onNotifyMessageOpened(context, message)
        i("极光通知Opened：$message")
        PushReceiverManager.onNotificationOpened(context, convertToReceiverInfo(message))
    }

    override fun onRegister(context: Context, token: String) {
        super.onRegister(context, token)
        i("极光ID注册： $token")
        //获取token成功，token用于标识设备的唯一性
        val info = ReceiverInfo()
        info.content = token
        info.pushType = PushConstant.JPUSH
        PushReceiverManager.setToken(context, info)
    }

    override fun onConnected(context: Context, b: Boolean) {
        super.onConnected(context, b)
        i("极光链接是否成功：$b")
    }

    override fun onMessage(context: Context, customMessage: CustomMessage) {
        super.onMessage(context, customMessage)
        i("OnCstMsg: $customMessage")
    }

    /**
     * 将NotificationMessage的数据转化为ReceiverInfo用于处理
     *
     * @param notificationMessage
     * @return
     */
    private fun convertToReceiverInfo(notificationMessage: NotificationMessage): ReceiverInfo {
        val info = ReceiverInfo()
        info.title = notificationMessage.notificationTitle
        info.content = notificationMessage.notificationContent
        info.extra = notificationMessage.notificationExtras
        info.pushType = PushConstant.JPUSH
        return info
    }
}