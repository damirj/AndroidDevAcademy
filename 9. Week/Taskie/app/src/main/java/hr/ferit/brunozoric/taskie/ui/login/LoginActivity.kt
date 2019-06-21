package hr.ferit.brunozoric.taskie.ui.login

import android.content.Intent
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.common.onClick
import hr.ferit.brunozoric.taskie.ui.tasklist.MainActivity
import hr.ferit.brunozoric.taskie.ui.base.BaseActivity
import hr.ferit.brunozoric.taskie.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity(), LoginActivityContract.View {

    private val presenter by inject<LoginActivityContract.Presenter>()

    override fun getLayoutResourceId(): Int = R.layout.activity_login

    override fun setUpUi() {
        login.onClick { signInClicked() }
        goToLogin.onClick { goToRegistrationClicked() }
    }

    private fun signInClicked() {
        presenter.onLogin(password.text.toString(), email.text.toString())
    }

    private fun goToRegistrationClicked() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLogged() {
        this.displayToast("Successfully logged in!")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFailed() {
        this.displayToast("Something went wrong!")
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }
}