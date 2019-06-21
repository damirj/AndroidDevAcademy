package hr.ferit.brunozoric.taskie.ui.taskdetails.fragment

import hr.ferit.brunozoric.taskie.model.BackendTask

interface EditTaskFragmentDialogContract {

    interface View{

        fun onTaskRecieved(task: BackendTask)

        fun onFailed()

        fun onTaskEdited()

        fun onFinished(task: BackendTask)

    }

    interface  Presenter{

        fun setView(view: EditTaskFragmentDialogContract.View)

        fun onGetTaskById(id: String, finished: Boolean)

        fun onEditTask(id: String, title: String, description: String, priority: Int)
    }
}