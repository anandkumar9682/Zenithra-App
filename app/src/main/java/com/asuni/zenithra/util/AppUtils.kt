/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: Utility functions for validating email formats and formatting timestamps to readable strings.
 */

package com.asuni.zenithra.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun validateEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    return email.trim().isNotEmpty() && email.trim().matches(emailRegex.toRegex())
}

fun validateName(name: String): Boolean = name.trim().length >= 3
fun validateMobile(mobile: String): Boolean = mobile.matches(Regex("^\\d{10}\$"))


// Function to format timestamps
fun formatTimestamp(timestamp: Long?): String {
    return if (timestamp != null) {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.getDefault())
        dateFormat.format(Date(timestamp))
    } else {
        "NA"
    }
}

fun openAppSettings(context: Context) {
    val intent = Intent()
    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS")
    val uri = Uri.fromParts("package", context.packageName, null as String?)
    intent.setData(uri)
    context.startActivity(intent)
}