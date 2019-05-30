package hr.ferit.brunozoric.taskie.persistence

import android.preference.PreferenceManager
import hr.ferit.brunozoric.taskie.Taskie

object TaskPrefs: SharedPrefsAuthorization {
    const val KEY_PRIORITY = "KEY_PRIORITY"
    const val KEY_SORT = "KEY_SORT"
    const val KEY_USER_TOKEN = "user_token"

    private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(Taskie.getAppContext())

    fun store(key: String, value: String){
        sharedPrefs().edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String? = sharedPrefs().getString(key, defaultValue)

    override fun getUserToken(): String = sharedPrefs().getString(KEY_USER_TOKEN, "")

    override fun storeUserToken(token: String) = sharedPrefs().edit().putString(KEY_USER_TOKEN, token).apply()

    override fun clearUserToken() = sharedPrefs().edit().remove(KEY_USER_TOKEN).apply()

    fun provideSharedPrefs(): SharedPrefsAuthorization{
        return TaskPrefs
    }
}
