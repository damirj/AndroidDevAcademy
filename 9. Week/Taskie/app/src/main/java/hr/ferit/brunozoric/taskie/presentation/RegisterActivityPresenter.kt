package hr.ferit.brunozoric.taskie.presentation

import hr.ferit.brunozoric.taskie.common.CallBackBase
import hr.ferit.brunozoric.taskie.model.request.UserDataRequest
import hr.ferit.brunozoric.taskie.model.response.RegisterResponse
import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractor
import hr.ferit.brunozoric.taskie.ui.register.RegisterActivityContract

class RegisterActivityPresenter(private val interactor: TaskieInteractor): RegisterActivityContract.Presenter {

    private lateinit var view: RegisterActivityContract.View

    override fun setView(view: RegisterActivityContract.View) {
        this.view = view
    }

    override fun onRegister(email: String, password: String, name: String) {
        interactor.register(
            UserDataRequest(email, password, name), CallBackBase({handleOkResponse(it)}, {handleSomethingWentWrong()})
        )
    }

    private fun handleOkResponse(response: RegisterResponse?) {
        view.onRegistered()
    }

    private fun handleSomethingWentWrong(){
        view.onFailed()
    }

}