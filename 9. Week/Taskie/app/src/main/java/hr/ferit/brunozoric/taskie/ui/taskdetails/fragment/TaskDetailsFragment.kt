package hr.ferit.brunozoric.taskie.ui.taskdetails.fragment

import android.os.Bundle
import android.view.View
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.*
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_details.*
import org.koin.android.ext.android.inject

class TaskDetailsFragment : BaseFragment(), EditTaskFragmentDialog.TaskEditedListener, TaskDetailsFragmentContract.View {

    private var taskID = NO_TASK

    private val presenter by inject<TaskDetailsFragmentContract.Presenter>()

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_task_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
        arguments?.getString(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
        initListeners()
    }

    private fun initListeners() {
        editTask.setOnClickListener { editTask() }
    }

    private fun editTask(){
        if (activity.internetAvailable()){
            val dialog = EditTaskFragmentDialog.newInstance(taskID)
            dialog.setTaskAddedListener(this)
            dialog.show(childFragmentManager, dialog.tag)
        }else this.activity?.displayToast("You have no Internet connection!")
    }

    override fun onTaskEdit(task: BackendTask) {
        displayTask(task)
    }

    private fun tryDisplayTask(id: String) {
        try {
            if (activity.internetAvailable()) {
                presenter.onGetTaskById(id)
            }else {
                presenter.onGetTaskByIdOffline(id)
            }

        } catch (e: NoSuchElementException) {
            context?.displayToast(getString(R.string.noTaskFound))
        }
    }

    override fun onTaskRecieved(task: BackendTask) {
        displayTask(task)
    }

    override fun onFailed() {
        this.activity?.displayToast("Something went wrong with fetching task!")
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    private fun displayTask(task: BackendTask?) {
        detailsTaskTitle.text = task?.title
        detailsTaskDescription.text = task?.content
        val color = when(task?.taskPriority){
            3 -> Priority.HIGH.getColor()
            2 -> Priority.MEDIUM.getColor()
            else -> Priority.LOW.getColor()
        }
        detailsPriorityView.setBackgroundResource(color)
    }

    companion object {
        const val NO_TASK = ""

        fun newInstance(taskId: String): TaskDetailsFragment {
            val bundle = Bundle().apply { putString(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}
