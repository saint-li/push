package com.saint.pushlib.receiver

interface PushAction {
    companion object {
        //通知
        const val RECEIVE_NOTIFICATION = "com.saint.pushlib.ACTION_RECEIVE_NOTIFICATION"

        //通知点击
        const val RECEIVE_NOTIFICATION_CLICK = "com.saint.pushlib.ACTION_RECEIVE_NOTIFICATION_CLICK"

        //收到消息
        const val RECEIVE_MESSAGE = "com.saint.pushlib.ACTION_RECEIVE_MESSAGE"

        //Token
        const val RECEIVE_TOKEN_SETED = "com.saint.pushlib.ACTION_RECEIVE_TOKEN_SET"

        //初始化结果
        const val RECEIVE_INIT_RESULT = "com.saint.pushlib.ACTION_RECEIVE_INIT_RESULT"

        //设置别名
        const val RECEIVE_SET_ALIAS = "com.saint.pushlib.ACTION_RECEIVE_SET_ALIAS"

        //退出登录
        const val RECEIVE_LOGIN_OUT = "com.saint.pushlib.ACTION_RECEIVE_LOGIN_OUT"
    }
}