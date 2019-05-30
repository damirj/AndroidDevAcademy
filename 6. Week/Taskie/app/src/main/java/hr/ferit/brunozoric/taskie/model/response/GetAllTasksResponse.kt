package hr.ferit.brunozoric.taskie.model.response

import hr.ferit.brunozoric.taskie.model.BackendTask

data class GetAllTasksResponse(val notes: MutableList<BackendTask> = mutableListOf())