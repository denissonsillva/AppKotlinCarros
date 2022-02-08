package br.com.livroandroid.carros.utils

import android.content.SharedPreferences
import br.com.livroandroid.carros.CarrosApplication
object Prefs {
    val PREF_ID = "carros"
    private fun prefs(): SharedPreferences {
        val context = CarrosApplication.getInstance().applicationContext
        return context.getSharedPreferences(PREF_ID, 0)
    }
    fun setInt(flag: String, valor: Int) = prefs().edit().putInt(flag, valor).apply()
    fun getInt(flag: String) = prefs().getInt(flag, 0)
    fun setString(flag: String, valor: String) = prefs().edit().putString(flag, valor).apply()
    fun getString(flag: String) = prefs().getString(flag, "")
    var tabIdx: Int
        get() = getInt("tabIdx")
        set(value) = setInt("tabIdx",value)
}

/*
Versão normal (tipo java)

 object Prefs {
    val PREF_ID = "carros"
    private fun prefs(): SharedPreferences {
        val context = CarrosApplication.getInstance().applicationContext
        return context.getSharedPreferences(PREF_ID, 0)
    }
    fun setInt(flag: String, valor: Int) {
        val pref = prefs()
        val editor = pref.edit()
        editor.putInt(flag, valor)
        editor.apply()
    }

    fun getInt(flag: String): Int {
        val pref = prefs()
        val i = pref.getInt(flag, 0)
        return i
    }


    fun setString(flag: String, valor: String) {
        val pref = prefs()
        val editor = pref.edit()
        editor.putString(flag, valor)
        editor.apply()
    }

    fun getString(flag: String): String? {
        val pref = prefs()
        val s = pref.getString(flag, "")
        return s
    }
}*/