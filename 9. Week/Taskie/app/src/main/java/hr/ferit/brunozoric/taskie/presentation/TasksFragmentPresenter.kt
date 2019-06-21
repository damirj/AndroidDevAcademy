package hr.ferit.brunozoric.taskie.presentation

import hr.ferit.brunozoric.taskie.common.CallBackBase
import hr.ferit.brunozoric.taskie.common.TaskConverter
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.CreateTaskRequest
import hr.ferit.brunozoric.taskie.model.response.DeleteTaskResponse
import hr.ferit.brunozoric.taskie.model.response.GetAllTasksResponse
import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractor
import hr.ferit.brunozoric.taskie.persistence.TaskPrefs
import hr.ferit.brunozoric.taskie.persistence.TaskRepository
import hr.ferit.brunozoric.taskie.ui.tasklist.fragment.TasksFragment
import hr.ferit.brunozoric.taskie.ui.tasklist.fragment.TasksFragmentContract

class TasksFragmentPresenter(private val interactor: TaskieInteractor, private val repository: TaskRepository): TasksFragmentContract.Presenter {

    var sortTasks = TaskPrefs.getString(TaskPrefs.KEY_SORT, TasksFragment.DEFAULT_SORT)
    private lateinit var view: TasksFragmentContract.View

    override fun setView(view: TasksFragmentContract.View) {
        this.view = view
    }

    override fun onGetAllTasks() {
        interactor.getAllTasks(CallBackBase({handleOkGetAllResponse(it)}, {handleSomethingWentWrong()}))
    }

    private fun handleOkGetAllResponse(response: GetAllTasksResponse?) {
        response?.notes?.run {
            view.onTaskListRecieved(sortByPriority(this))
            repository.deleteAllTasks()
            for (it in this){
                repository.addTask(TaskConverter.convertToTask(it))
            }
        }
        //view.onTaskListRecieved(sortByPriority(response!!.notes))
    }

    private fun handleSomethingWentWrong() {
        view.onFailed()
    }

    override fun onDeleteTask(id: String) {
        interactor.deleteTask(
            request = id,
            deleteCallback = CallBackBase({handleOkDeleteResponse(it)}, {handleSomethingWentWrong()}))
    }

    private fun handleOkDeleteResponse(response: DeleteTaskResponse?) {
        view.onTaskDeleted()
        onUpdateWhileInternetGone()
        onGetAllTasks()
    }

    private fun sortByPriority(tasks: MutableList<BackendTask>): MutableList<BackendTask>{
        sortTasks = TaskPrefs.getString(TaskPrefs.KEY_SORT, TasksFragment.DEFAULT_SORT)
        return when(sortTasks){
            TasksFragment.INCREASE_SORT -> {
                tasks.sortBy { it.taskPriority }
                tasks
            }
            TasksFragment.DECREASE_SORT -> {
                tasks.sortBy { it.taskPriority }
                tasks.reverse()
                tasks
            }
            else -> {
                tasks
            }
        }
    }

    override fun onUpdateWhileInternetGone() {
        val notSentTasks = repository.getAllNotSent()
        repository.updateNotSentTasks()
        for (task in notSentTasks){
            interactor.createTask(
                request = CreateTaskRequest(task.title, task.content, task.taskPriority),
                createTaskCallback = CallBackBase({}, {handleSomethingWentWrong()}))
        }
    }


    override fun onGetCachedData() {
        val tasks = repository.getAllTasks()
        val backendTasks = mutableListOf<BackendTask>()

        for (task in tasks){
            backendTasks.add(TaskConverter.convertToBackendTask(task))
        }
        view.onCachedDataRecieved(sortByPriority(backendTasks))
    }


}