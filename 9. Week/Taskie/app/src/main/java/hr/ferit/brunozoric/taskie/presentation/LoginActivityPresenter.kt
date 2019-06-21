package hr.ferit.brunozoric.taskie.presentation

import hr.ferit.brunozoric.taskie.common.CallBackBase
import hr.ferit.brunozoric.taskie.model.request.UserDataRequest
import hr.ferit.brunozoric.taskie.model.response.LoginResponse
import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractor
import hr.ferit.brunozoric.taskie.persistence.SharedPrefsAuthorization
import hr.ferit.brunozoric.taskie.ui.login.LoginActivityContract

class LoginActivityPresenter(private val interactor: TaskieInteractor, private val prefs: SharedPrefsAuthorization): LoginActivityContract.Presenter {

    private lateinit var view: LoginActivityContract.View

    override fun setView(view: LoginActivityContract.View) {
        this.view = view
    }

    override fun onLogin(password: String, email: String) {
        interactor.login(
            request = UserDataRequest(password, email),
            loginCallback = CallBackBase({handleOkResponse(it)}, {handleSomethingWentWrong()})
        )
    }

    private fun handleOkResponse(loginReponse: LoginResponse?) {
        loginReponse?.token?.let { prefs.storeUserToken(it) }
        view.onLogged()
    }

    private fun handleSomethingWentWrong(){
        view.onFailed()
    }
}