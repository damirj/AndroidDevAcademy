package hr.ferit.brunozoric.taskie.ui.tasklist

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.persistence.TaskPrefs
import hr.ferit.brunozoric.taskie.ui.about.AboutActivity
import hr.ferit.brunozoric.taskie.ui.activities.SplashActivity
import hr.ferit.brunozoric.taskie.ui.base.BaseActivity
import hr.ferit.brunozoric.taskie.ui.tasklist.fragment.DeleteAlertDialog
import hr.ferit.brunozoric.taskie.ui.tasklist.fragment.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), DeleteAlertDialog.DeleteDialogListener {

    private lateinit var taskFragment: TasksFragment
    private var sortTasks = TaskPrefs.getString(TaskPrefs.KEY_SORT, TasksFragment.DEFAULT_SORT)
    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {
        taskFragment = TasksFragment.newInstance()
        showFragment(taskFragment)
        bottom_navigation.setOnNavigationItemSelectedListener { selectNav(it) }
        bottom_navigation.menu.getItem(0).isChecked = true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.sortByPriority -> {
                sortByPriority()
                true
            }
            R.id.sortByDate -> {
                sortByDate()
                true
            }
            R.id.deleteAllTasks -> {
                deleteAllTasks()
                true
            }
            R.id.logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        TaskPrefs.clearUserToken()
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun sortByDate() {
        sortTasks = TasksFragment.DEFAULT_SORT
        TaskPrefs.store(TaskPrefs.KEY_SORT, TasksFragment.DEFAULT_SORT)
        taskFragment.sortTasks()
    }

    private fun sortByPriority() {
        when (sortTasks){
            TasksFragment.DEFAULT_SORT -> {
                sortTasks = TasksFragment.DECREASE_SORT
                TaskPrefs.store(TaskPrefs.KEY_SORT, TasksFragment.DECREASE_SORT)
            }
            TasksFragment.DECREASE_SORT -> {
                sortTasks = TasksFragment.INCREASE_SORT
                TaskPrefs.store(TaskPrefs.KEY_SORT, TasksFragment.INCREASE_SORT)
            }
            TasksFragment.INCREASE_SORT -> {
                sortTasks = TasksFragment.DECREASE_SORT
                TaskPrefs.store(TaskPrefs.KEY_SORT, TasksFragment.DECREASE_SORT)
            }
        }
        taskFragment.sortTasks()
    }

    private fun deleteAllTasks() {
        val dialog = DeleteAlertDialog()
        dialog.setDeleteDialogListener(this)
        dialog.show(supportFragmentManager, dialog.tag)
    }

    override fun onYesClicked(viewHolder: RecyclerView.ViewHolder?) {
        taskFragment.deleteAllTasks()
    }

    override fun onNoClicked() {
    }

    private fun selectNav(it: MenuItem): Boolean {
        when(it.itemId){
            R.id.nav_about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return false
    }

}