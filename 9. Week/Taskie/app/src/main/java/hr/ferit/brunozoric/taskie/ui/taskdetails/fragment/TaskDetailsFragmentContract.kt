package hr.ferit.brunozoric.taskie.ui.taskdetails.fragment

import hr.ferit.brunozoric.taskie.model.BackendTask

interface TaskDetailsFragmentContract {

    interface View{

        fun onTaskRecieved(task: BackendTask)

        fun onFailed()

    }

    interface Presenter{

        fun setView(view: TaskDetailsFragmentContract.View)

        fun onGetTaskById(id: String)

        fun onGetTaskByIdOffline(id: String)
    }
}