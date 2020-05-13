package com.saint.pushlib.receiver

import android.content.Context
import com.saint.pushlib.bean.ReceiverInfo

/**
 *
 */
interface IPushReceiver {
    /**
     * 收到通知后会调用此接口
     *
     * @param context
     * @param info
     */
    fun onReceiveNotification(context: Context, info: ReceiverInfo)

    /**
     * 点击通知后会调用此接口
     *
     * @param context
     * @param info
     */
    fun onReceiveNotificationClick(context: Context, info: ReceiverInfo)

    /**
     * 收到消息后会调用此接口
     *
     * @param context
     * @param info
     */
    fun onReceiveMessage(context: Context, info: ReceiverInfo)

    /**
     * 华为推送获取到token后会调用此接口传递过来
     *
     * @param context
     * @param info
     */
    fun onTokenSet(context: Context, info: ReceiverInfo)

    /**
     * 初始化成功后会调用此接口
     *
     * @param context
     * @param info
     */
    fun onInitResult(context: Context, info: ReceiverInfo)

    /**
     * 设置别名成功后会调用此接口
     *
     * @param context
     * @param info
     */
    fun onSetAlias(context: Context, info: ReceiverInfo)

    /**
     * 登出后会调用此接口
     *
     * @param context
     * @param info
     */
    fun onLoginOut(context: Context, info: ReceiverInfo)
}