package hr.ferit.brunozoric.taskie.ui.activities

import android.content.Intent
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.CallBackBase
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.common.onClick
import hr.ferit.brunozoric.taskie.model.request.UserDataRequest
import hr.ferit.brunozoric.taskie.model.response.RegisterResponse
import hr.ferit.brunozoric.taskie.networking.BackendFactory
import hr.ferit.brunozoric.taskie.ui.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    private val interactor = BackendFactory.getTaskieInteractor()

    override fun getLayoutResourceId(): Int = R.layout.activity_register

    override fun setUpUi() {
        register.onClick { signInClicked() }
        goToLogin.onClick { goToLoginClicked() }
    }

    private fun signInClicked() {
        interactor.register(
            UserDataRequest(
                email = email.text.toString(),
                password = password.text.toString(),
                name = name.text.toString()
            ), CallBackBase({handleOkResponse(it)}, {handleSomethingWentWrong()})
        )
    }

    private fun goToLoginClicked() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleOkResponse(response: RegisterResponse?) {
        this.displayToast("Successfully registered")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleSomethingWentWrong() = this.displayToast("Something went wrong!")
}