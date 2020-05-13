package com.saint.pushlib.mipush

import android.content.Context
import com.saint.pushlib.PushConstant
import com.saint.pushlib.R
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager
import com.saint.pushlib.util.PushLog.Companion.i
import com.xiaomi.mipush.sdk.*

/**
 * 1、PushMessageReceiver 是个抽象类，该类继承了 BroadcastReceiver。<br></br>
 * 2、需要将自定义的 DemoMessageReceiver 注册在 AndroidManifest.xml 文件中：
 * <pre>
 * `<receiver
 * android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"
 * android:exported="true">
 * <intent-filter>
 * <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
 * </intent-filter>
 * <intent-filter>
 * <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
 * </intent-filter>
 * <intent-filter>
 * <action android:name="com.xiaomi.mipush.ERROR" />
 * </intent-filter>
 * </receiver>
`</pre> *
 * 3、DemoMessageReceiver 的 onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。<br></br>
 * 4、DemoMessageReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发。<br></br>
 * 5、DemoMessageReceiver 的 onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br></br>
 * 6、DemoMessageReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。<br></br>
 * 7、DemoMessageReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br></br>
 * 8、以上这些方法运行在非 UI 线程中。
 */
class MiPushReceiver : PushMessageReceiver() {
    override fun onReceivePassThroughMessage(context: Context, message: MiPushMessage) {
        PushReceiverManager.onMessageReceived(context, convert2ReceiverInfo(message))
    }

    override fun onNotificationMessageClicked(context: Context, message: MiPushMessage) {
        PushReceiverManager.onNotificationOpened(context, convert2ReceiverInfo(message))
    }

    override fun onNotificationMessageArrived(context: Context, message: MiPushMessage) {
        PushReceiverManager.onNotificationReceived(context, convert2ReceiverInfo(message))
    }

    override fun onReceiveRegisterResult(context: Context, message: MiPushCommandMessage) {
        val command = message.command
        val log: String
        if (MiPushClient.COMMAND_REGISTER == command) {
            log = if (message.resultCode == ErrorCode.SUCCESS.toLong()) {
                context.getString(R.string.init_succeed)
            } else {
                context.getString(R.string.init_failed)
            }
            val info = convert2ReceiverInfo(message)
            info.title = "小米推送"
            info.content = log
            PushReceiverManager.onRegistration(context, info)
        }
    }

    override fun onCommandResult(context: Context, message: MiPushCommandMessage) {
        super.onCommandResult(context, message)
        i("XiaomiBroadcastReceiver :onCommandResult = var2=$message")
        val command = message.command
        if (MiPushClient.COMMAND_SET_ALIAS == command) {
            if (message.resultCode == ErrorCode.SUCCESS.toLong()) {
                val info = convert2ReceiverInfo(message)
                info.title = context.getString(R.string.tip_setalias)
                info.content = message.commandArguments[0]
                PushReceiverManager.onAliasSet(context, info)
            }
        }
    }

    /**
     * 将intent的数据转化为ReceiverInfo用于处理
     *
     * @param message
     * @return
     */
    private fun convert2ReceiverInfo(message: MiPushMessage): ReceiverInfo {
        val info = ReceiverInfo()
        info.content = message.content
        info.pushType = PushConstant.XIAOMI
        info.title = message.title
        if (message.content != null) {
            info.extra = message.content
        }
        return info
    }

    /**
     * 将intent的数据转化为ReceiverInfo用于处理
     *
     * @param commandMessage
     * @return
     */
    private fun convert2ReceiverInfo(commandMessage: MiPushCommandMessage): ReceiverInfo {
        val info = ReceiverInfo()
        info.content = commandMessage.command
        info.pushType = PushConstant.XIAOMI
        return info
    }
}