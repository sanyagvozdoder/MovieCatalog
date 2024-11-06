package com.example.testxml.common.sharedprefs

import android.content.Context

fun putInSharedPrefs(context: Context, key:String,value: String){
    val pref = context.getSharedPreferences("kinohits", Context.MODE_PRIVATE).edit()
    pref.putString(key, value)
    pref.apply()
}

fun getFromSharedPrefs(context: Context):String?{
    val pref = context.getSharedPreferences("kinohits", Context.MODE_PRIVATE)
    return pref.getString("token","")
}