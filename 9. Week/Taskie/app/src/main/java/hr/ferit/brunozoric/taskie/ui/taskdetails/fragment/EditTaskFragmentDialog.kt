package hr.ferit.brunozoric.taskie.ui.taskdetails.fragment

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
import kotlinx.android.synthetic.main.fragment_dialog_edit_task.*
import kotlinx.android.synthetic.main.fragment_dialog_new_task.prioritySelector
import kotlinx.android.synthetic.main.fragment_dialog_new_task.saveTaskAction
import org.koin.android.ext.android.inject

class EditTaskFragmentDialog : DialogFragment(), EditTaskFragmentDialogContract.View {

    private var taskEditedListener: TaskEditedListener? = null
    private var taskID = TaskDetailsFragment.NO_TASK

    private val presenter by inject<EditTaskFragmentDialogContract.Presenter>()

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
        presenter.onGetTaskById(taskID, false)
    }


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

        presenter.onEditTask(taskID, title, description, priority.ordinal+1)
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

    override fun onTaskRecieved(task: BackendTask) {
        setUpDialogUi(task)
    }

    override fun onFailed() {
        this.activity?.displayToast("Something went wrong!")
    }

    override fun onTaskEdited() {
        presenter.onGetTaskById(taskID, true)
    }

    override fun onFinished(task: BackendTask) {
        taskEditedListener?.onTaskEdit(task!!)
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    companion object {
        fun newInstance(taskId: String): EditTaskFragmentDialog {
            val bundle = Bundle().apply { putString(EXTRA_TASK_ID, taskId) }
            return EditTaskFragmentDialog()
                .apply { arguments = bundle }
        }
    }
}
