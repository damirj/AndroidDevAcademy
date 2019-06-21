package hr.ferit.brunozoric.taskie.ui.tasklist.fragment

import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.Task

interface AddTaskFragmentContract {

    interface View{

        fun onTaskCreated(task: BackendTask)

        fun onFailed()

    }


    interface Presenter{

        fun setView(view: AddTaskFragmentContract.View)

        fun onCreate(title: String, description: String, priority: Int)

        fun onCreateOffline(task: Task)
    }
}