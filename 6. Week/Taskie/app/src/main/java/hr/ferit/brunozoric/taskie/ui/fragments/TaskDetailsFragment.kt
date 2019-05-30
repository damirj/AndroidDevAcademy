package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.view.View
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.*
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.networking.BackendFactory
import hr.ferit.brunozoric.taskie.persistence.Repository
import hr.ferit.brunozoric.taskie.persistence.TaskRepository
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_details.*

class TaskDetailsFragment : BaseFragment(), EditTaskFragmentDialog.TaskEditedListener {

    private val repository: TaskRepository = Repository()
    private var taskID = NO_TASK
    private val interactor = BackendFactory.getTaskieInteractor()

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_task_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                interactor.getTaskById(
                    request = id,
                    getTaskByIdReposnse = CallBackBase( {handleOkGetByIdResponse(it)}, {handleSomethingWentWrong()})
                )
            }else {
                val task = repository.getTask(id)
                displayTask(TaskConverter.convertToBackendTask(task))
            }

        } catch (e: NoSuchElementException) {
            context?.displayToast(getString(R.string.noTaskFound))
        }
    }

    private fun handleSomethingWentWrong(){
        this.activity?.displayToast("Something went wrong with fetching task!")
    }

    private fun handleOkGetByIdResponse(task: BackendTask?) {
        displayTask(task)
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
