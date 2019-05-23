package hr.ferit.brunozoric.taskie.ui.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment

import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.*
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.persistence.Repository
import hr.ferit.brunozoric.taskie.persistence.TaskRepository
import kotlinx.android.synthetic.main.fragment_dialog_edit_task.*
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*
import kotlinx.android.synthetic.main.fragment_dialog_new_task.prioritySelector
import kotlinx.android.synthetic.main.fragment_dialog_new_task.saveTaskAction


class EditTaskFragmentDialog : DialogFragment() {

    private var taskEditedListener: EditTaskFragmentDialog.TaskEditedListener? = null
    private val repository: TaskRepository = Repository()
    private var taskID = TaskDetailsFragment.NO_TASK


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
        arguments?.getInt(EXTRA_TASK_ID)?.let { taskID = it }
        initUi()
        initListeners()
    }

    private fun initListeners(){
        saveTaskAction.setOnClickListener{ saveTask() }
    }



    private fun initUi(){
        var task = repository.getTask(taskID.toLong())
        context?.let {
            editTaskTitleInput.setText(task.title)
            editTaskDescriptionInput.setText(task.description)
            prioritySelector.adapter = ArrayAdapter<Priority>(it, android.R.layout.simple_spinner_dropdown_item, Priority.values())
            when(task.priority){
                LOW_PRIORITY -> prioritySelector.setSelection(0)
                MEDIUM_PRIORITY -> prioritySelector.setSelection(1)
                HIGH_PRIORITY -> prioritySelector.setSelection(2)
                else -> prioritySelector.setSelection(0)
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
        repository.editTask(repository.getTask(taskID.toLong()), title, description, priority.name)

        taskEditedListener?.onTaskEdit(repository.getTask(taskID.toLong()))
        dismiss()
    }

    private fun isInputEmpty(): Boolean = TextUtils.isEmpty(editTaskTitleInput.text) || TextUtils.isEmpty(
        editTaskDescriptionInput.text
    )


    interface TaskEditedListener{
        fun onTaskEdit(task: Task)
    }

    fun setTaskAddedListener(listener: EditTaskFragmentDialog.TaskEditedListener){
        taskEditedListener = listener
    }

    companion object {
        fun newInstance(taskId: Int): EditTaskFragmentDialog {
            val bundle = Bundle().apply { putInt(EXTRA_TASK_ID, taskId) }
            return EditTaskFragmentDialog().apply { arguments = bundle }
        }

    }
}
