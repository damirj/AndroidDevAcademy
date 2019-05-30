package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.*
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.request.EditTaskRequest
import hr.ferit.brunozoric.taskie.model.response.EditTaskResponse
import hr.ferit.brunozoric.taskie.networking.BackendFactory
import kotlinx.android.synthetic.main.fragment_dialog_edit_task.*
import kotlinx.android.synthetic.main.fragment_dialog_new_task.prioritySelector
import kotlinx.android.synthetic.main.fragment_dialog_new_task.saveTaskAction

class EditTaskFragmentDialog : DialogFragment() {

    private var taskEditedListener: TaskEditedListener? = null
    private var taskID = TaskDetailsFragment.NO_TASK
    private var interactor = BackendFactory.getTaskieInteractor()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_edit_task, container)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(EXTRA_TASK_ID)?.let { taskID = it }
        initUi()
        initListeners()
    }

    private fun initListeners(){
        saveTaskAction.setOnClickListener{ saveTask() }
    }

    private fun initUi(){
        interactor.getTaskById(
            taskID,
            CallBackBase({handleOkGetbyIdResponse(it)}, {handleSomethingWentWrong()})
        )
    }

    private fun handleOkGetbyIdResponse(task: BackendTask?) {
        setUpDialogUi(task)
    }

    private fun handleSomethingWentWrong() = this.activity?.displayToast("Something went wrong!")

    private fun setUpDialogUi(task: BackendTask?) {
        context?.let {
            editTaskTitleInput.setText(task?.title)
            editTaskDescriptionInput.setText(task?.content)
            prioritySelector.adapter = ArrayAdapter<Priority>(it, android.R.layout.simple_spinner_dropdown_item, Priority.values())
            when(task?.taskPriority){
                1 -> prioritySelector.setSelection(0)
                2 -> prioritySelector.setSelection(1)
                else -> prioritySelector.setSelection(2)
            }
        }
    }

    private fun saveTask() {
        if (isInputEmpty()){
            context?.displayToast(getString(R.string.emptyFields))
            return
        }

        val title = editTaskTitleInput.text.toString()
        val description = editTaskDescriptionInput.text.toString()
        val priority = prioritySelector.selectedItem as Priority

        interactor.editTask(
            request = EditTaskRequest(taskID, title, description, priority.ordinal+1),
            editCallback = CallBackBase( {handleOkResponse(it)}, {handleSomethingWentWrong()} )
        )
    }

    private fun handleOkResponse(response: EditTaskResponse?) {
        this.activity?.displayToast("Successfully edited!")
        interactor.getTaskById(taskID, CallBackBase( {handleOkGetbyIdEditedResponse(it)}, {handleSomethingWentWrong()}))

    }

    private fun handleOkGetbyIdEditedResponse(task: BackendTask?) {
        taskEditedListener?.onTaskEdit(task!!)
        dismiss()
    }

    private fun isInputEmpty(): Boolean = TextUtils.isEmpty(editTaskTitleInput.text) || TextUtils.isEmpty(
        editTaskDescriptionInput.text
    )

    interface TaskEditedListener{
        fun onTaskEdit(task: BackendTask)
    }

    fun setTaskAddedListener(listener: TaskEditedListener){
        taskEditedListener = listener
    }

    companion object {
        fun newInstance(taskId: String): EditTaskFragmentDialog {
            val bundle = Bundle().apply { putString(EXTRA_TASK_ID, taskId) }
            return EditTaskFragmentDialog().apply { arguments = bundle }
        }
    }
}
