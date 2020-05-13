package com.saint.pushlib.bean

import java.io.Serializable

/**
 * @author Saint  2020/5/7
 */
class Info : Serializable {

    var type = 0
    var content = ""
    var title = ""


    override fun toString(): String {
        return "Info(type=$type, content='$content', title='$title')"
    }

}