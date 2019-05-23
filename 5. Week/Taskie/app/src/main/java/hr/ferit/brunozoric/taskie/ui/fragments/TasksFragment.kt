package hr.ferit.brunozoric.taskie.ui.fragments

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.EXTRA_SCREEN_TYPE
import hr.ferit.brunozoric.taskie.common.EXTRA_TASK_ID
import hr.ferit.brunozoric.taskie.common.gone
import hr.ferit.brunozoric.taskie.common.visible
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.persistence.Repository
import hr.ferit.brunozoric.taskie.persistence.TaskPrefs
import hr.ferit.brunozoric.taskie.persistence.TaskRepository
import hr.ferit.brunozoric.taskie.ui.activities.ContainerActivity
import hr.ferit.brunozoric.taskie.ui.adapters.TaskAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : BaseFragment(), AddTaskFragmentDialog.TaskAddedListener, DeleteAlertDialog.DeleteDialogListener {

    var sortTasks = TaskPrefs.getString(TaskPrefs.KEY_SORT, DEFAULT_SORT)
    private val repository: TaskRepository = Repository()
    private val adapter by lazy { TaskAdapter {onItemSelected(it)} }
    private val swipeBackground: ColorDrawable = ColorDrawable(Color.RED)
    private lateinit var deleteIcon: Drawable

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
        refreshTasks()
    }

    private fun initUi() {
        progress.visible()
        noData.visible()
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter
        deleteIcon = ContextCompat.getDrawable(this.context!!, R.drawable.ic_delete_sweep_black_24dp)!!

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

    private fun deleteTask(viewHolder: RecyclerView.ViewHolder) {
        repository.deleteTask(adapter.removeTask(viewHolder))
        refreshTasks()
    }

    private fun initListeners() {
        addTask.setOnClickListener { addTask() }
    }

    private fun onItemSelected(task: Task){
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, ContainerActivity.SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID, task.taskDbId!!.toInt())
        }
        startActivity(detailsIntent)
    }

    private fun refreshTasks() {
        progress.gone()
        var data = repository.getAllTasks()
        if (data.isNotEmpty()) {
            noData.gone()
        } else {
            noData.visible()
        }

        data = sortByPriority()

        adapter.setData(data)
    }

    private fun addTask() {
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onTaskAdded(task: Task) {
        refreshTasks()
    }

    override fun onYesClicked(viewHolder: RecyclerView.ViewHolder?) {
        deleteTask(viewHolder!!)
    }

    override fun onNoClicked() {
        refreshTasks()
    }

    override fun onResume() {
        refreshTasks()
        super.onResume()
    }

    fun deleteAllTasks(){
        repository.deleteAllTasks()
        refreshTasks()
    }

     private fun sortByPriority(): MutableList<Task>{
        var data = repository.getAllTasks()
        data.sortBy { when(it.priority){
            "LOW" -> 0
            "MEDIUM" -> 1
            "HIGH" -> 2
            else -> 0
        } }

        return when(sortTasks){
            INCREASE_SORT -> {
                data
            }
            DECREASE_SORT -> {
                data.reverse()
                data
            }
            else -> {
                repository.getAllTasks()
            }
        }
    }

    fun sortTasks(){
        refreshTasks()
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