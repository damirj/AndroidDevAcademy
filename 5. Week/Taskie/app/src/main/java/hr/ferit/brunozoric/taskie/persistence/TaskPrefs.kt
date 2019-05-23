package hr.ferit.brunozoric.taskie.persistence

import android.preference.PreferenceManager
import hr.ferit.brunozoric.taskie.Taskie

object TaskPrefs {

    const val KEY_PRIORITY = "KEY_PRIORITY"
    const val KEY_SORT = "KEY_SORT"

    private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(Taskie.getAppContext())

    fun store(key: String, value: String){
        sharedPrefs().edit().putString(key,value).apply()
    }

    fun getString(key: String, defaultValue: String): String? {
        return sharedPrefs().getString(key, defaultValue)
    }
}