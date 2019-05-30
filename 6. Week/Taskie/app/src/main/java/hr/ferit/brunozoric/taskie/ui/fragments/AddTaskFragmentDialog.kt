package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.text.TextUtils.isEmpty
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
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.model.request.CreateTaskRequest
import hr.ferit.brunozoric.taskie.networking.BackendFactory
import hr.ferit.brunozoric.taskie.persistence.Repository
import hr.ferit.brunozoric.taskie.persistence.TaskPrefs
import hr.ferit.brunozoric.taskie.persistence.TaskRepository
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*

class AddTaskFragmentDialog: DialogFragment() {

    private var currentPriority = TaskPrefs.getString(TaskPrefs.KEY_PRIORITY, "LOW")
    private var taskAddedListener: TaskAddedListener? = null
    private val interactor = BackendFactory.getTaskieInteractor()
    private val repository: TaskRepository = Repository()

    interface TaskAddedListener{
        fun onTaskAdded(task: BackendTask)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    fun setTaskAddedListener(listener: TaskAddedListener){
        taskAddedListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_new_task, container)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.internetAvailable()

        initUi()
        initListeners()
    }

    private fun initUi(){
        context?.let {
            prioritySelector.adapter = ArrayAdapter<Priority>(it, android.R.layout.simple_spinner_dropdown_item, Priority.values())
            when(currentPriority){
                LOW_PRIORITY -> prioritySelector.setSelection(0)
                MEDIUM_PRIORITY -> prioritySelector.setSelection(1)
                HIGH_PRIORITY -> prioritySelector.setSelection(2)
                else -> prioritySelector.setSelection(0)
            }
        }
    }

    private fun initListeners(){
        saveTaskAction.setOnClickListener{ saveTask() }
    }

    private fun saveTask() {
        if (isInputEmpty()){
            context?.displayToast(getString(R.string.emptyFields))
            return
        }

        val title = newTaskTitleInput.text.toString()
        val description = newTaskDescriptionInput.text.toString()
        val priority = prioritySelector.selectedItem as Priority

        if (activity.internetAvailable()){
            interactor.createTask(
                request = CreateTaskRequest(title, description, priority.ordinal+1),
                createTaskCallback = CallBackBase( {handleOkResponse(it)}, {handleSomethingWentWrong()} )
            )
        }else{
           val task = Task(
               id = "tempId",
               title = title,
               content = description,
               taskPriority = priority.ordinal+1,
               isSent = false
           )
            task.id = task.taskDbId.toString()
            repository.addTask(task)
            onTaskiesReceived(TaskConverter.convertToBackendTask(task))
        }
    }

    private fun handleSomethingWentWrong(){
        this.activity?.displayToast("Something went wrong with creating new task!")
    }

    private fun handleOkResponse(task: BackendTask?) = task?.run { onTaskiesReceived(this) }

    private fun onTaskiesReceived(task: BackendTask) {
        TaskPrefs.store(TaskPrefs.KEY_PRIORITY, task.taskPriority.toString())
        clearUi()
        taskAddedListener?.onTaskAdded(task)
        dismiss()
    }

    private fun clearUi() {
        newTaskTitleInput.text.clear()
        newTaskDescriptionInput.text.clear()
    }

    private fun isInputEmpty(): Boolean = isEmpty(newTaskTitleInput.text) || isEmpty(newTaskDescriptionInput.text)

    companion object{
        fun newInstance(): AddTaskFragmentDialog {
            return AddTaskFragmentDialog()
        }

    }
}