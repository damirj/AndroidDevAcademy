package hr.ferit.brunozoric.taskie.networking.interactors

import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.CreateTaskRequest
import hr.ferit.brunozoric.taskie.model.request.EditTaskRequest
import hr.ferit.brunozoric.taskie.model.request.UserDataRequest
import hr.ferit.brunozoric.taskie.model.response.*
import hr.ferit.brunozoric.taskie.networking.TaskieApiService
import retrofit2.Callback

class TaskieInteractorImpl(private val apiService: TaskieApiService): TaskieInteractor {

    override fun deleteTask(request: String, deleteCallback: Callback<DeleteTaskResponse>) {
        apiService.deleteTask(request).enqueue(deleteCallback)
    }

    override fun editTask(request: EditTaskRequest, editCallback: Callback<EditTaskResponse>) {
        apiService.editTask(request).enqueue(editCallback)
    }

    override fun login(request: UserDataRequest, loginCallback: Callback<LoginResponse>) {
        apiService.login(request).enqueue(loginCallback)
    }

    override fun register(request: UserDataRequest, registerCallBack: Callback<RegisterResponse>) {
        apiService.register(request).enqueue(registerCallBack)
    }

    override fun createTask(request: CreateTaskRequest, createTaskCallback: Callback<BackendTask>) {
        apiService.createTask(request).enqueue(createTaskCallback)
    }

    override fun getAllTasks(getAllTasksResponse: Callback<GetAllTasksResponse>) {
        apiService.getAllTasks().enqueue(getAllTasksResponse)
    }

    override fun getTaskById(request: String, getTaskByIdReposnse: Callback<BackendTask>) {
        apiService.getTaskById(request).enqueue(getTaskByIdReposnse)
    }
}