package hr.ferit.brunozoric.taskie.ui.activities

import android.content.Intent
import android.util.Log
import android.view.MenuItem
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.EXTRA_SCREEN_TYPE
import hr.ferit.brunozoric.taskie.common.EXTRA_TASK_ID
import hr.ferit.brunozoric.taskie.ui.activities.base.BaseActivity
import hr.ferit.brunozoric.taskie.ui.fragments.TaskDetailsFragment
import kotlinx.android.synthetic.main.activitiy_about.*


class ContainerActivity: BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {
        val screenType = intent.getStringExtra(EXTRA_SCREEN_TYPE)
        val id = intent.getIntExtra(EXTRA_TASK_ID, -1)
        if (screenType.isNotEmpty()) {
            when (screenType) {
                SCREEN_TASK_DETAILS -> showFragment(TaskDetailsFragment.newInstance(id))
            }
        } else {
            finish()
        }
        bottom_navigation.setOnNavigationItemSelectedListener { selectNav(it) }
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

    companion object{
        const val SCREEN_TASK_DETAILS = "task_details"
    }
}

