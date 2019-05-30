package hr.ferit.brunozoric.taskie.model.request

data class CreateTaskRequest(val title: String, val content: String, val taskPriority: Int)