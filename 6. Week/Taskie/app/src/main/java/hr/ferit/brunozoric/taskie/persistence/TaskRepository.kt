package hr.ferit.brunozoric.taskie.persistence

import hr.ferit.brunozoric.taskie.model.Task

interface TaskRepository {

    fun addTask(task: Task)
    fun getAllTasks(): MutableList<Task>
    fun getTask(id: String): Task
    fun deleteTask(task: Task)
    fun editTask(id: String, newTaskTitle: String, newTaskDescription: String, newTaskPriority: Int)
    fun deleteAllTasks()
    fun getAllNotSent(): MutableList<Task>
    fun updateNotSentTasks()
}