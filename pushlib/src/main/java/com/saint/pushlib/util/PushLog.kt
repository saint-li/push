package com.saint.pushlib.util

import android.util.Log

class PushLog{
    companion object{
        @JvmField
        var debug = false
        const val TAG = "PUSH_LOG"

        @JvmStatic
        fun i(log: String) {
            if (debug) {
                Log.i(TAG, log)
            }
        }

        @JvmStatic
        fun d(log: String) {
            if (debug) {
                Log.d(TAG, log)
            }
        }

        @JvmStatic
        fun e(log: String) {
            if (debug) {
                Log.e(TAG, log)
            }
        }

        @JvmStatic
        fun e(log: String, throwable: Throwable?) {
            if (debug) {
                Log.e(TAG, log, throwable)
            }
        }
    }
}