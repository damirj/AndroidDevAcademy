package hr.ferit.brunozoric.taskie.networking

import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.CreateTaskRequest
import hr.ferit.brunozoric.taskie.model.request.EditTaskRequest
import hr.ferit.brunozoric.taskie.model.request.UserDataRequest
import hr.ferit.brunozoric.taskie.model.response.*
import retrofit2.Call
import retrofit2.http.*

interface TaskieApiService {

    @POST("/api/note/delete")
    fun deleteTask(@Query("id") noteId: String): Call<DeleteTaskResponse>

    @POST("/api/note/edit")
    fun editTask(@Body editTask: EditTaskRequest): Call<EditTaskResponse>

    @POST("/api/login")
    fun login(@Body userData: UserDataRequest): Call<LoginResponse>

    @POST("/api/register")
    fun register(@Body userData: UserDataRequest): Call<RegisterResponse>

    @POST("/api/note")
    fun createTask(@Body taskData: CreateTaskRequest): Call<BackendTask>

    @GET("/api/note/")
    fun getAllTasks(): Call<GetAllTasksResponse>

    @GET("/api/note/{id}")
    fun getTaskById(@Path("id") id: String): Call<BackendTask>
}