package com.saint.pushlib.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.saint.pushlib.bean.ReceiverInfo

/**
 *
 */
abstract class BasePushReceiver : BroadcastReceiver(), IPushReceiver {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        val info =
            intent.getSerializableExtra(PushReceiverManager.INTENT_RECEIVER_INFO) as ReceiverInfo
        if (PushAction.RECEIVE_TOKEN_SETED == action) {
            onTokenSet(context, info)
        } else if (PushAction.RECEIVE_INIT_RESULT == action) {
            onInitResult(context, info)
        } else if (PushAction.RECEIVE_NOTIFICATION == action) {
            onReceiveNotification(context, info)
        } else if (PushAction.RECEIVE_NOTIFICATION_CLICK == action) {
            onReceiveNotificationClick(context, info)
        } else if (PushAction.RECEIVE_MESSAGE == action) {
            onReceiveMessage(context, info)
        } else if (PushAction.RECEIVE_LOGIN_OUT == action) {
            onLoginOut(context, info)
        } else if (PushAction.RECEIVE_SET_ALIAS == action) {
            onSetAlias(context, info)
        }
    }
}