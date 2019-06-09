package osc.androiddevacademy.movieapp.persistence

import android.preference.PreferenceManager
import osc.androiddevacademy.movieapp.App

object SharedPrefs {

    const val KEY_MOVIE_SORT = "MOVIE_SORT"

    private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(App.getAppContext())

    fun store(key: String, value: String){
        sharedPrefs().edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String? = sharedPrefs().getString(key, defaultValue)
}