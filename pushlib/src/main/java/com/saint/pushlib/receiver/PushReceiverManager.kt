package com.saint.pushlib.receiver

import android.content.Context
import android.content.Intent
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.cache.PushTokenCache.putToken
import com.saint.pushlib.util.PushLog.Companion.i

/**
 * 统一处理收到的推送
 */
object PushReceiverManager {

    const val INTENT_RECEIVER_INFO = "receiverInfo"

    /***
     * 用户注册sdk之后的通知
     * 注册成功之后调用
     * @param context
     */
    fun onRegistration(context: Context, info: ReceiverInfo) {
        i("onRegistration=$info")
        info.type = PushAction.RECEIVE_INIT_RESULT
        sendPushData(context, PushAction.RECEIVE_INIT_RESULT, info)
    }

    /**
     * 获取到华为token后
     */
    fun setToken(context: Context, info: ReceiverInfo) {
        i("setToken=$info")
        putToken(context, info.content)
        info.type = PushAction.RECEIVE_TOKEN_SETED
        sendPushData(context, PushAction.RECEIVE_TOKEN_SETED, info)
    }

    /**
     * 设置了别名之后
     *
     * @param context
     * @param info
     */
    fun onAliasSet(context: Context, info: ReceiverInfo) {
        i("onAliasSet=$info")
        info.type = PushAction.RECEIVE_SET_ALIAS
        sendPushData(context, PushAction.RECEIVE_SET_ALIAS, info)
    }

    /**
     * 接收到消息推送，不会主动显示在通知栏
     *
     * @param context
     * @param info
     */
    fun onMessageReceived(context: Context, info: ReceiverInfo) {
        i("onMessageReceived=$info")
        info.type = PushAction.RECEIVE_MESSAGE
        sendPushData(context, PushAction.RECEIVE_MESSAGE, info)
    }

    /**
     * 接收到通知，会主动显示在通知栏的
     *
     * @param context
     * @param info
     */
    fun onNotificationReceived(context: Context, info: ReceiverInfo) {
        i("onNotificationReceived=$info")
        info.type = PushAction.RECEIVE_NOTIFICATION
        sendPushData(context, PushAction.RECEIVE_NOTIFICATION, info)
    }

    /**
     * 用户点击了通知
     *
     * @param context
     * @param info
     */
    fun onNotificationOpened(context: Context, info: ReceiverInfo) {
        i("onNotificationOpened=$info")
        info.type = PushAction.RECEIVE_NOTIFICATION_CLICK
        sendPushData(context, PushAction.RECEIVE_NOTIFICATION_CLICK, info)
    }

    /**
     * 登出后
     */
    fun onLoginOut(context: Context, info: ReceiverInfo) {
        i("onLoginOut=$info")
        info.type = PushAction.RECEIVE_LOGIN_OUT
        sendPushData(context, PushAction.RECEIVE_LOGIN_OUT, info)
    }


    private fun sendPushData(context: Context, action: String?, data: ReceiverInfo?) {
        val intent = Intent(action)
        intent.putExtra(INTENT_RECEIVER_INFO, data)
        //ComponentName componentName = new ComponentName(context.getPackageName(),"com.saint.pushlib.handle.PushReceiverManager");
        intent.setPackage(context.packageName)
        context.sendBroadcast(intent)
    }
}