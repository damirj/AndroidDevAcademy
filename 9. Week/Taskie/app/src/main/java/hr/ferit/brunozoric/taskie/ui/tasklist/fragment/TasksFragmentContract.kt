package hr.ferit.brunozoric.taskie.ui.tasklist.fragment

import hr.ferit.brunozoric.taskie.model.BackendTask

interface TasksFragmentContract {

    interface View{

        fun onTaskListRecieved(tasks: MutableList<BackendTask>)

        fun onTaskDeleted()

        fun onFailed()

        fun onCachedDataRecieved(tasks: MutableList<BackendTask>)
    }

    interface Presenter{

        fun setView(view: TasksFragmentContract.View)

        fun onGetAllTasks()

        fun onDeleteTask(id: String)

        fun onUpdateWhileInternetGone()

        fun onGetCachedData()

    }

}