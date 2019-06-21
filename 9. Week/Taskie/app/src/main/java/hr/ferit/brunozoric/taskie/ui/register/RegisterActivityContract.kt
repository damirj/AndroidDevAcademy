package hr.ferit.brunozoric.taskie.ui.register

interface RegisterActivityContract {

    interface View{

        fun onRegistered()

        fun onFailed()

    }

    interface Presenter{

        fun setView(view: RegisterActivityContract.View)

        fun onRegister(email: String, password: String, name: String)

    }
}