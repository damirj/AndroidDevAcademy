package hr.ferit.brunozoric.taskie.persistence

import hr.ferit.brunozoric.taskie.Taskie
import hr.ferit.brunozoric.taskie.db.DaoProvider
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task

class Repository: TaskRepository{

    private var db = DaoProvider.getInstance(Taskie.getAppContext())

    private var taskDao = db.taskDao()

    override fun addTask(task: Task) {
        taskDao.insertTask(task)
    }

    override fun getTask(id: Long): Task = taskDao.getTask(id)

    override fun getAllTasks(): MutableList<Task> = taskDao.getAllTasks()


    override fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }

    override fun editTask(task: Task, newTaskTitle: String, newTaskDescription: String, newTaskPriority: String) {
        taskDao.editTask(task.taskDbId!!, newTaskTitle, newTaskDescription, newTaskPriority)
    }


    /*
    private val tasks = mutableListOf<Task>()
    private var currentId = 0

    public fun save(title: String, description: String, priority: Priority): Task {
        val task = Task(currentId, title, description, priority)
        task.id = currentId
        tasks.add(task)
        currentId++
        return task
    }

    fun deleteBy(id: Int){
        tasks.removeAll { (it.id) == id }
    }

    fun count() = tasks.size

    fun get(id: Int): Task {
        return tasks.first { it.id == id }
    }

    fun getAllTasks() = tasks
    */
}