/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: Helper class for accessing and modifying SharedPreferences data.
 */

package com.asuni.zenithra.util.helper

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferenceHelper
@Inject
constructor(
    private val sharedPref: SharedPreferences,
) {

    private val editor = sharedPref.edit()

    fun getBoolean(name: String, defValue: Boolean): Boolean {
        return sharedPref.getBoolean(name, defValue)
    }

    fun getString(name: String, defValue: String): String? {
        return sharedPref.getString(name, defValue)
    }

    fun getInteger(name: String, defValue: Int): Int {
        return sharedPref.getInt(name, defValue)
    }

    fun setBoolean(name: String, value: Boolean) {
        editor.putBoolean(name, value).apply()
    }

    fun setString(name: String, value: String) {
        editor.putString(name, value).apply()
    }

    fun setInteger(name: String, value: Int) {
        editor.putInt(name, value).apply()
    }

    companion object {
        const val IS_LOGED_IN = "IS_LOGED_IN"
    }
}
