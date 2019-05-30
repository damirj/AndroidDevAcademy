package hr.ferit.brunozoric.taskie.ui.activities

import android.content.Intent
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.CallBackBase
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.common.onClick
import hr.ferit.brunozoric.taskie.model.request.UserDataRequest
import hr.ferit.brunozoric.taskie.model.response.LoginResponse
import hr.ferit.brunozoric.taskie.networking.BackendFactory
import hr.ferit.brunozoric.taskie.persistence.TaskPrefs
import hr.ferit.brunozoric.taskie.ui.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private val interactor = BackendFactory.getTaskieInteractor()
    private val prefs = TaskPrefs.provideSharedPrefs()

    override fun getLayoutResourceId(): Int = R.layout.activity_login

    override fun setUpUi() {
        login.onClick { signInClicked() }
        goToLogin.onClick { goToRegistrationClicked() }
    }

    private fun signInClicked() {
        interactor.login(
            request = UserDataRequest(password = password.text.toString(), email = email.text.toString()),
            loginCallback = CallBackBase({handleOkResponse(it)}, {handleSomethingWentWrong()})
        )
    }

    private fun goToRegistrationClicked() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleOkResponse(loginReponse: LoginResponse?) {
        this.displayToast("Successfully logged in!")
        loginReponse?.token?.let { prefs.storeUserToken(it) }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleSomethingWentWrong() = this.displayToast("Something went wrong!")
}