package hr.ferit.brunozoric.taskie.ui.tasklist.fragment

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
import hr.ferit.brunozoric.taskie.ui.taskdetails.ContainerActivity
import hr.ferit.brunozoric.taskie.ui.adapters.TaskAdapter
import hr.ferit.brunozoric.taskie.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.android.ext.android.inject


class TasksFragment : BaseFragment(), AddTaskFragmentDialog.TaskAddedListener, DeleteAlertDialog.DeleteDialogListener,
    TasksFragmentContract.View, MyItemTouchHelper.SwipeListener {

    private val adapter by lazy { TaskAdapter {onItemSelected(it)} }

    private val presenter by inject<TasksFragmentContract.Presenter>()

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    private fun initUi() {
        presenter.setView(this)
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
        setUpItemTouchHelper()
    }

    private fun setUpItemTouchHelper() {
        val deleteIcon = ContextCompat.getDrawable(this.context!!, R.drawable.ic_delete_sweep_black_24dp)!!
        val myItemTouchHelper = MyItemTouchHelper(deleteIcon)
        myItemTouchHelper.setSwipeListener(this)
        val callback = myItemTouchHelper.setUpItemTouchHelper()
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)
    }

    private fun updateWhileInternetGone() {
        presenter.onUpdateWhileInternetGone()
    }

    private fun getAllCachedData() {
        progress.gone()
        presenter.onGetCachedData()
    }

    private fun onRefresh() {
        if (activity.internetAvailable()){
            updateWhileInternetGone()
            getAllTasks()
        } else {
            getAllCachedData()
        }

        taskSwipeRefresh.isRefreshing = false
    }

    private fun getAllTasks() {
        progress.visible()
        presenter.onGetAllTasks()
    }

    private fun deleteTask(viewHolder: RecyclerView.ViewHolder) {
        presenter.onDeleteTask(adapter.getItemAt(viewHolder).id)
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
        presenter.setView(this)
        if (activity.internetAvailable()){
            updateWhileInternetGone()
            getAllTasks()
        } else getAllCachedData()

        super.onResume()
    }

    fun deleteAllTasks(){

    }

    fun sortTasks(){
        if (activity.internetAvailable()){
            updateWhileInternetGone()
            getAllTasks()
        } else {
            getAllCachedData()
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?) {
        val dialog = DeleteAlertDialog()
        dialog.setDeleteDialogListener(this@TasksFragment)
        dialog.setDeleteTask(viewHolder!!)
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onTaskListRecieved(tasks: MutableList<BackendTask>) {
        checkList(tasks)
        adapter.setData(tasks)
    }

    override fun onFailed() {
        this.activity?.displayToast("Something went wrong!")
    }

    override fun onTaskDeleted() {
        this.activity?.displayToast("Task deleted!")
    }

    override fun onCachedDataRecieved(tasks: MutableList<BackendTask>) {
        checkList(tasks)
        adapter.setData(tasks)
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