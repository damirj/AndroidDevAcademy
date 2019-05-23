package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.EXTRA_TASK_ID
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.persistence.Repository
import hr.ferit.brunozoric.taskie.persistence.TaskRepository
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_details.*
import kotlinx.android.synthetic.main.fragment_tasks.*

class TaskDetailsFragment : BaseFragment(), EditTaskFragmentDialog.TaskEditedListener {

    private val repository: TaskRepository = Repository()
    private var taskID = NO_TASK

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_task_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
        initListeners()
    }

    private fun initListeners() {
        editTask.setOnClickListener { editTask() }
    }

    private fun editTask(){
        val dialog = EditTaskFragmentDialog.newInstance(taskID)
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onTaskEdit(task: Task) {
        displayTask(task)
    }






    private fun tryDisplayTask(id: Int) {
        try {
            val task = repository.getTask(id.toLong())
            displayTask(task)
        } catch (e: NoSuchElementException) {
            context?.displayToast(getString(R.string.noTaskFound))
        }
    }

    private fun displayTask(task: Task) {
        detailsTaskTitle.text = task.title
        detailsTaskDescription.text = task.description
        var color = when(task.priority){
            Priority.HIGH.name -> Priority.HIGH.getColor()
            Priority.MEDIUM.name -> Priority.MEDIUM.getColor()
            Priority.LOW.name -> Priority.LOW.getColor()
            else -> Priority.LOW.getColor()
        }
        detailsPriorityView.setBackgroundResource(color)
    }

    companion object {
        const val NO_TASK = -1

        fun newInstance(taskId: Int): TaskDetailsFragment {
            val bundle = Bundle().apply { putInt(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}
