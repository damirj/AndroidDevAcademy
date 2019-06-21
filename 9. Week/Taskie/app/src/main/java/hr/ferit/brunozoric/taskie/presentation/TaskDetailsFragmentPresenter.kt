package hr.ferit.brunozoric.taskie.presentation

import hr.ferit.brunozoric.taskie.common.CallBackBase
import hr.ferit.brunozoric.taskie.common.TaskConverter
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractor
import hr.ferit.brunozoric.taskie.persistence.TaskRepository
import hr.ferit.brunozoric.taskie.ui.taskdetails.fragment.TaskDetailsFragmentContract

class TaskDetailsFragmentPresenter(private val interactor: TaskieInteractor, private val repository: TaskRepository): TaskDetailsFragmentContract.Presenter {

    private lateinit var view: TaskDetailsFragmentContract.View

    override fun setView(view: TaskDetailsFragmentContract.View) {
        this.view = view
    }

    override fun onGetTaskById(id: String) {
        interactor.getTaskById(
            request = id,
            getTaskByIdReposnse = CallBackBase({handleOkGetByIdResponse(it)}, {handleSomethingWentWrong()})
        )
    }

    private fun handleOkGetByIdResponse(task: BackendTask?) {
        view.onTaskRecieved(task!!)
    }

    private fun handleSomethingWentWrong(){
        view.onFailed()
    }

    override fun onGetTaskByIdOffline(id: String) {
        val task = repository.getTask(id)
        view.onTaskRecieved(TaskConverter.convertToBackendTask(task))
    }


}