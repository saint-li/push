package com.saint.pushlib.opush

import android.content.Context
import com.heytap.mcssdk.PushService
import com.heytap.mcssdk.mode.AppMessage
import com.heytap.mcssdk.mode.CommandMessage
import com.heytap.mcssdk.mode.SptDataMessage
import com.saint.pushlib.PushConstant
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager
import com.saint.pushlib.util.PushLog.Companion.e

class OppoPushService : PushService() {
    /**
     * 普通应用消息
     *
     * @param context
     * @param appMessage
     */
    override fun processMessage(context: Context, appMessage: AppMessage) {
        e("app processMessage: handle:$appMessage")
        val info = ReceiverInfo()
        info.title = appMessage.title
        info.content = appMessage.content
        info.pushType = PushConstant.OPPO
        PushReceiverManager.onNotificationReceived(context, info)
    }

    /**
     * 透传消息处理，应用可以打开页面或者执行命令,如果应用不需要处理透传消息，则不需要重写此方法
     *
     * @param context
     * @param sptDataMessage
     */
    override fun processMessage(context: Context, sptDataMessage: SptDataMessage) {
        e("spt processMessage: handle:$sptDataMessage")
        val info = ReceiverInfo()
        info.content = sptDataMessage.content
        info.extra = sptDataMessage.description
        info.pushType = PushConstant.OPPO
        PushReceiverManager.onMessageReceived(context, info)
    }

    /**
     * 命令消息，主要是服务端对客户端调用的反馈，一般应用不需要重写此方法
     *
     * @param context
     * @param commandMessage
     */
    override fun processMessage(context: Context, commandMessage: CommandMessage) {
        super.processMessage(context, commandMessage)
        e("command processMessage: $commandMessage")
    }
}