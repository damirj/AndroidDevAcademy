package hr.ferit.brunozoric.taskie.networking.interactors

import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.CreateTaskRequest
import hr.ferit.brunozoric.taskie.model.request.EditTaskRequest
import hr.ferit.brunozoric.taskie.model.request.UserDataRequest
import hr.ferit.brunozoric.taskie.model.response.*
import retrofit2.Callback

interface TaskieInteractor {

    fun deleteTask(request: String, deleteCallback: Callback<DeleteTaskResponse>)

    fun editTask(request: EditTaskRequest, editCallback: Callback<EditTaskResponse>)

    fun login(request: UserDataRequest, loginCallback: Callback<LoginResponse>)

    fun register(request: UserDataRequest, registerCallBack: Callback<RegisterResponse>)

    fun createTask(request: CreateTaskRequest, createTaskCallback: Callback<BackendTask>)

    fun getAllTasks(getAllTasksResponse: Callback<GetAllTasksResponse>)

    fun getTaskById(request: String, getTaskByIdReposnse: Callback<BackendTask>)
}