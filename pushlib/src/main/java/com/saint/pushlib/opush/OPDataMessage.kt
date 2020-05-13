package com.saint.pushlib.opush

import android.content.Context
import com.heytap.msp.push.mode.DataMessage
import com.heytap.msp.push.service.DataMessageCallbackService
import com.saint.pushlib.PushConstant
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager
import com.saint.pushlib.util.PushLog.Companion.i

/**
 * @author Saint  2020/5/12
 */
class OPDataMessage : DataMessageCallbackService() {
    override fun processMessage(context: Context, dataMessage: DataMessage) {
        super.processMessage(context, dataMessage)
        i("OPDataMessage推送消息：$dataMessage")
        val info = ReceiverInfo()
        info.pushType = PushConstant.OPPO
        info.title = dataMessage.title
        info.content = dataMessage.content
        info.extra = dataMessage.description
        PushReceiverManager.onMessageReceived(context, info)
    }
}