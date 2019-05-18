package hr.ferit.brunozoric.taskie.ui.activities

import android.content.Intent
import android.view.MenuItem
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.ui.activities.base.BaseActivity
import hr.ferit.brunozoric.taskie.ui.fragments.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {
        showFragment(TasksFragment.newInstance())
        bottom_navigation.setOnNavigationItemSelectedListener { selectNav(it) }
        bottom_navigation.menu.getItem(0).isChecked = true
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