package hr.ferit.brunozoric.taskie.presentation

import hr.ferit.brunozoric.taskie.common.CallBackBase
import hr.ferit.brunozoric.taskie.common.TaskConverter
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.model.request.CreateTaskRequest
import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractor
import hr.ferit.brunozoric.taskie.persistence.TaskRepository
import hr.ferit.brunozoric.taskie.ui.tasklist.fragment.AddTaskFragmentContract

class AddTaskFragmentPresenter(private val interactor: TaskieInteractor, private val repository: TaskRepository): AddTaskFragmentContract.Presenter {

    private lateinit var view: AddTaskFragmentContract.View

    override fun setView(view: AddTaskFragmentContract.View) {
        this.view = view
    }

    override fun onCreate(title: String, description: String, priority: Int) {
        interactor.createTask(
            request = CreateTaskRequest(title, description, priority),
            createTaskCallback = CallBackBase( {handleOkResponse(it)}, {handleSomethingWentWrong()} )
        )
    }

    private fun handleOkResponse(task: BackendTask?) = task?.run { view.onTaskCreated(task) }

    private fun handleSomethingWentWrong(){
        view.onFailed()
    }

    override fun onCreateOffline(task: Task) {
        task.id = task.taskDbId.toString()
        repository.addTask(task)
        view.onTaskCreated(TaskConverter.convertToBackendTask(task))
    }

}