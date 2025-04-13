/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: Utility class for logging and showing toast messages in the application.
 * Includes a `showToast` extension function for easy usage in any Context.
 */

package com.asuni.zenithra.util

import android.content.Context
import android.util.Log
import android.widget.Toast

class LogUtil {
    companion object {
        fun log(message: String) {
            Log.d("LogTest", message)
        }

        fun toast(message: String) {
            Log.d("LogTest", message) // Placeholder for toast logging
        }
    }
}

fun Context.showToast(message: String) {
    LogUtil.log(message)
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
