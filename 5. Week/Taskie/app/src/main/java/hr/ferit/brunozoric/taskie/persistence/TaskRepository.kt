package hr.ferit.brunozoric.taskie.persistence

import hr.ferit.brunozoric.taskie.model.Task

interface TaskRepository {

    fun addTask(task: Task)
    fun getAllTasks(): MutableList<Task>
    fun getTask(id: Long): Task
    fun deleteTask(task: Task)
    fun editTask(task: Task, newTaskTitle: String, newTaskDescription: String, newTaskPriority: String)
    fun deleteAllTasks()
}