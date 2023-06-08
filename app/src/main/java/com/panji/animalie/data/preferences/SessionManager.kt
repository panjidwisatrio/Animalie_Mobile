package com.panji.animalie.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.panji.animalie.R

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"
    }

    fun saveToken(token: String) {
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun saveId(id: String) {
        editor.putString(USER_ID, id)
        editor.apply()
    }

    fun fetchId(): String? {
        return prefs.getString(USER_ID, null)
    }

    fun fetchToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}