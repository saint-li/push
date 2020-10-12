package com.saint.pushlib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.saint.pushlib.bean.ReceiverInfo;

/**
 *
 */

public abstract class BasePushReceiver extends BroadcastReceiver implements IPushReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        ReceiverInfo info = (ReceiverInfo) intent.getSerializableExtra(PushReceiverManager.INTENT_RECEIVER_INFO);
        if (PushAction.RECEIVE_TOKEN_SETED.equals(action)) {
            onTokenSet(context, info);
        } else if (PushAction.RECEIVE_INIT_RESULT.equals(action)) {
            onInitResult(context, info);
        } else if (PushAction.RECEIVE_NOTIFICATION.equals(action)) {
            onReceiveNotification(context, info);
        } else if (PushAction.RECEIVE_NOTIFICATION_CLICK.equals(action)) {
            onReceiveNotificationClick(context, info);
        } else if (PushAction.RECEIVE_MESSAGE.equals(action)) {
            onReceiveMessage(context, info);
        } else if (PushAction.RECEIVE_LOGIN_OUT.equals(action)) {
            onLoginOut(context, info);
        } else if (PushAction.RECEIVE_SET_ALIAS.equals(action)) {
            onSetAlias(context, info);
        }
    }
}
