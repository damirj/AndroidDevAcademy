package hr.ferit.brunozoric.taskie.presentation

import hr.ferit.brunozoric.taskie.common.CallBackBase
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.EditTaskRequest
import hr.ferit.brunozoric.taskie.model.response.EditTaskResponse
import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractor
import hr.ferit.brunozoric.taskie.ui.taskdetails.fragment.EditTaskFragmentDialogContract

class EditTaskFragmentDialogPresenter(private val interactor: TaskieInteractor): EditTaskFragmentDialogContract.Presenter {

    private lateinit var view: EditTaskFragmentDialogContract.View

    override fun setView(view: EditTaskFragmentDialogContract.View) {
        this.view = view
    }

    override fun onGetTaskById(id: String, finished: Boolean) {
        if (finished){
            interactor.getTaskById(
                request = id,
                getTaskByIdReposnse = CallBackBase({handleOkFinished(it)}, {handleSomethingWentWrong()})
            )
        }else{
            interactor.getTaskById(
                request = id,
                getTaskByIdReposnse = CallBackBase({handleOkGetByIdResponse(it)}, {handleSomethingWentWrong()})
            )
        }
    }

    private fun handleOkFinished(task: BackendTask?) {
        view.onFinished(task!!)
    }

    private fun handleOkGetByIdResponse(task: BackendTask?) {
        view.onTaskRecieved(task!!)
    }

    private fun handleSomethingWentWrong(){
        view.onFailed()
    }

    override fun onEditTask(id: String, title: String, description: String, priority: Int) {
        interactor.editTask(
            request = EditTaskRequest(id, title, description, priority),
            editCallback = CallBackBase( {handleOkEdit(it)}, {handleSomethingWentWrong()} )
        )
    }

    private fun handleOkEdit(response: EditTaskResponse?) {
        view.onTaskEdited()
    }


}