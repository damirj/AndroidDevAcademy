package hr.ferit.brunozoric.taskie.ui.fragments

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.*
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.response.DeleteTaskResponse
import hr.ferit.brunozoric.taskie.model.response.GetAllTasksResponse
import hr.ferit.brunozoric.taskie.networking.BackendFactory
import hr.ferit.brunozoric.taskie.persistence.Repository
import hr.ferit.brunozoric.taskie.persistence.TaskPrefs
import hr.ferit.brunozoric.taskie.persistence.TaskRepository
import hr.ferit.brunozoric.taskie.ui.activities.ContainerActivity
import hr.ferit.brunozoric.taskie.ui.adapters.TaskAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import hr.ferit.brunozoric.taskie.model.request.CreateTaskRequest


class TasksFragment : BaseFragment(), AddTaskFragmentDialog.TaskAddedListener, DeleteAlertDialog.DeleteDialogListener {

    var sortTasks = TaskPrefs.getString(TaskPrefs.KEY_SORT, DEFAULT_SORT)
    private val repository: TaskRepository = Repository()
    private val adapter by lazy { TaskAdapter {onItemSelected(it)} }
    private val swipeBackground: ColorDrawable = ColorDrawable(Color.RED)
    private lateinit var deleteIcon: Drawable
    private val interactor = BackendFactory.getTaskieInteractor()

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    private fun initUi() {
        noData.visible()
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter

        if (activity.internetAvailable()){
            updateWhileInternetGone()
            getAllTasks()
        } else {
            getAllCachedData()
        }

        taskSwipeRefresh.setOnRefreshListener { onRefresh() }

        deleteIcon = ContextCompat.getDrawable(this.context!!, R.drawable.ic_delete_sweep_black_24dp)!!
        setUpItemTouchHelper()
    }

    private fun updateWhileInternetGone() {
        val notSentTasks = repository.getAllNotSent()
        repository.updateNotSentTasks()
        for (task in notSentTasks){
            interactor.createTask(
                request = CreateTaskRequest(task.title, task.content, task.taskPriority),
                createTaskCallback = createTaskCallback())
        }
    }

    private fun getAllCachedData() {
        progress.gone()
        val tasks = repository.getAllTasks()
        val backendTasks = mutableListOf<BackendTask>()
        //val backendTasks = repository.getAllTasks().map { TaskConverter.convertToBackendTask(it) }.toMutableList()

        for (task in tasks){
            backendTasks.add(TaskConverter.convertToBackendTask(task))
        }

        checkList(backendTasks)

        adapter.setData(sortByPriority(backendTasks))
    }

    private fun onRefresh() {
        if (activity.internetAvailable()){
            updateWhileInternetGone()
            getAllTasks()
        } else getAllCachedData()

        taskSwipeRefresh.isRefreshing = false
    }

    private fun getAllTasks() {
        progress.visible()
        interactor.getAllTasks(CallBackBase({handleOkGetAllResponse(it)}, {handleSomethingWentWrong()}))
    }

    private fun handleSomethingWentWrong(){
        this.activity?.displayToast("Something went wrong with fetching tasks!")
    }

    private fun handleOkGetAllResponse(response: GetAllTasksResponse?) {
        response?.notes?.run {
            checkList(this)
            adapter.setData(sortByPriority(this))

            repository.deleteAllTasks()
            for (it in this){
                repository.addTask(TaskConverter.convertToTask(it))
            }
        }
    }

    private fun createTaskCallback(): Callback<BackendTask> = object: Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>, t: Throwable) {

        }

        override fun onResponse(call: Call<BackendTask>, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                handleSomethingWentWrong()
            }
        }
    }

    private fun deleteTask(viewHolder: RecyclerView.ViewHolder) {
        interactor.deleteTask(
            request = adapter.getItemAt(viewHolder).id,
            deleteCallback = CallBackBase({handleOkDeleteResponse(it)}, {handleSomethingWentWrong()}))
    }

    private fun handleOkDeleteResponse(response: DeleteTaskResponse?) {
        this.activity?.displayToast("Task successfully deleted!")
        updateWhileInternetGone()
        getAllTasks()
    }

    private fun initListeners() {
        addTask.setOnClickListener { addTask() }
    }

    private fun onItemSelected(task: BackendTask){
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, ContainerActivity.SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID, task.id)
        }
        startActivity(detailsIntent)
    }

    private fun checkList(tasks: MutableList<BackendTask>) {
        if (tasks.isEmpty()) {
            noData.visible()
        } else {
            noData.gone()
        }
        progress.gone()
    }

    private fun addTask() {
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onTaskAdded(task: BackendTask) {
        adapter.addData(task)
        if (activity.internetAvailable()){
            updateWhileInternetGone()
            getAllTasks()
        } else getAllCachedData()
    }

    override fun onYesClicked(viewHolder: RecyclerView.ViewHolder?) {
        deleteTask(viewHolder!!)
        progress.gone()
    }

    override fun onNoClicked() {
        updateWhileInternetGone()
        getAllTasks()
    }

    override fun onResume() {
        if (activity.internetAvailable()){
            updateWhileInternetGone()
            getAllTasks()
        } else getAllCachedData()

        super.onResume()
    }

    fun deleteAllTasks(){
        /*
        repository.deleteAllTasks()
        */
    }

     private fun sortByPriority(tasks: MutableList<BackendTask>): MutableList<BackendTask>{
        return when(sortTasks){
            INCREASE_SORT -> {
                tasks.sortBy { it.taskPriority }
                tasks
            }
            DECREASE_SORT -> {
                tasks.sortBy { it.taskPriority }
                tasks.reverse()
                tasks
            }
            else -> {
                tasks
            }
        }
    }

    fun sortTasks(){
        if (activity.internetAvailable()){
            updateWhileInternetGone()
            getAllTasks()
        } else getAllCachedData()
    }

    private fun setUpItemTouchHelper() {
        if (activity.internetAvailable()){
            val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    val dialog = DeleteAlertDialog()
                    dialog.setDeleteDialogListener(this@TasksFragment)
                    dialog.setDeleteTask(viewHolder)
                    dialog.show(childFragmentManager, dialog.tag)
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    val itemView = viewHolder.itemView

                    val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
                    swipeBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                    deleteIcon.setBounds(itemView.right - iconMargin - deleteIcon.intrinsicWidth, itemView.top + iconMargin, itemView.right - iconMargin,
                        itemView.bottom - iconMargin)
                    swipeBackground.draw(c)
                    c.save()
                    c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                    deleteIcon.draw(c)
                    c.restore()
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }

            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(tasksRecyclerView)
        }
    }

    companion object {
        fun newInstance(): TasksFragment {
            return TasksFragment()
        }

        const val DEFAULT_SORT = "DEFAULT"
        const val INCREASE_SORT = "INCREASE"
        const val DECREASE_SORT = "DECREASE"
    }
}