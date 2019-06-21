package hr.ferit.brunozoric.taskie.ui.register

import android.content.Intent
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.common.onClick
import hr.ferit.brunozoric.taskie.ui.login.LoginActivity
import hr.ferit.brunozoric.taskie.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject

class RegisterActivity : BaseActivity(), RegisterActivityContract.View {

    private val presenter by inject<RegisterActivityContract.Presenter>()

    override fun getLayoutResourceId(): Int = R.layout.activity_register

    override fun setUpUi() {
        register.onClick { signInClicked() }
        goToLogin.onClick { goToLoginClicked() }
    }

    private fun signInClicked() {
        presenter.onRegister(
            email.text.toString(),
            password.text.toString(),
            name.text.toString())
    }

    private fun goToLoginClicked() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onRegistered() {
        this.displayToast("Successfully registered!")
        val intent = Intent(this, LoginActivity::class.java)
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