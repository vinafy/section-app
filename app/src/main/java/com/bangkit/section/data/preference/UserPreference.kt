package com.bangkit.section.data.preference

import android.content.Context
import android.content.SharedPreferences

object UserPreference {
    private const val PREFERENCE_NAME = "onSignIn"

    fun initPref(context: Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    private fun editorPreference(context: Context, name: String): SharedPreferences.Editor {
        val sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        return sharedPref.edit()
    }

    private fun getPreferenceName(): String {
        return PREFERENCE_NAME
    }

    fun saveApiKey(key: String,avatarUrl: String,name: String,email:String,id: Int ,context: Context) {
        val editor = editorPreference(context, getPreferenceName())
        editor.putString("apiKey", key)
        editor.putString("avatarURL", avatarUrl)
        editor.putString("name", name)
        editor.putString("email",email)
        editor.putInt("id",id)
        editor.apply()
    }

    fun logOut(context: Context) {
        val editor = editorPreference(context, getPreferenceName())
        editor.remove("apiKey")
        editor.remove("avatarURL")
        editor.remove("status")
        editor.remove("name")
        editor.remove("email")
        editor.apply()
    }
}