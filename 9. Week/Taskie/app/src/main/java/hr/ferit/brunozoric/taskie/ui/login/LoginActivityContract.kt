package hr.ferit.brunozoric.taskie.ui.login

interface LoginActivityContract {

    interface View{

        fun onLogged()

        fun onFailed()
    }

    interface Presenter{

        fun setView(view: LoginActivityContract.View)

        fun onLogin(password: String, email: String)
    }
}