package hr.ferit.brunozoric.taskie.persistence

interface SharedPrefsAuthorization {
    fun getUserToken(): String

    fun storeUserToken(token: String)

    fun clearUserToken()
}