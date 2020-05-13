package com.saint.pushlib.bean

import com.saint.pushlib.PushConstant
import java.io.Serializable

/**
 * 接收到的推送的消息
 */
class ReceiverInfo : Serializable {
    /**
     * 推送平台
     */
    var pushType = PushConstant.JPUSH

    /**
     * 标题
     */
    var title = ""

    /**
     * 内容
     */
    var content = ""

    /**
     * 额外数据
     */
    var extra = ""

    /**
     * 额外数据
     */
    var type = ""

    override fun toString(): String {
        return "ReceiverInfo{" +
                "pushType=" + pushType +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", extra='" + extra + '\'' +
                ", type='" + type + '\'' +
                '}'
    }

}