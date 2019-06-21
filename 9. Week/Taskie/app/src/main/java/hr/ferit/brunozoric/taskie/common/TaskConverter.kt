package hr.ferit.brunozoric.taskie.common

import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.Task

object TaskConverter {

    fun convertToBackendTask(task: Task): BackendTask {
        return BackendTask(
            id = task.id,
            userId = task.userId,
            title = task.title,
            content = task.content,
            taskPriority = task.taskPriority,
            isFavorite = task.isFavorite,
            isCompleted = task.isCompleted
        )
    }

    fun convertToTask(backendTask: BackendTask): Task {
        return Task(
            id = backendTask.id,
            userId = backendTask.userId ?: "",
            title = backendTask.title,
            content = backendTask.content,
            taskPriority = backendTask.taskPriority,
            isFavorite = backendTask.isFavorite,
            isCompleted = backendTask.isCompleted
        )
    }
}