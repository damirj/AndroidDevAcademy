package hr.ferit.brunozoric.taskie.persistence

import hr.ferit.brunozoric.taskie.Taskie
import hr.ferit.brunozoric.taskie.db.NewDaoProvider
import hr.ferit.brunozoric.taskie.model.Task

class Repository: TaskRepository{

    private var db = NewDaoProvider.getInstance(Taskie.getAppContext())

    private var taskDao = db.taskDao()

    override fun addTask(task: Task) {
        taskDao.insertTask(task)
    }

    override fun getTask(id: String): Task = taskDao.getTask(id)

    override fun getAllTasks(): MutableList<Task> = taskDao.getAllTasks()


    override fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }

    override fun editTask(id: String, newTaskTitle: String, newTaskDescription: String, newTaskPriority: Int) {
        taskDao.editTask(id, newTaskTitle, newTaskDescription, newTaskPriority)
    }

    override fun getAllNotSent(): MutableList<Task> = taskDao.getAllNotSent()

    override fun updateNotSentTasks() {
        taskDao.updateNotSentTasks()
    }


}