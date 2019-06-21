package hr.ferit.brunozoric.taskie.ui.about

import android.content.Intent
import android.view.MenuItem
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.ui.base.BaseActivity
import hr.ferit.brunozoric.taskie.ui.adapters.AboutAdapter
import hr.ferit.brunozoric.taskie.ui.tasklist.MainActivity
import kotlinx.android.synthetic.main.activitiy_about.*
import kotlinx.android.synthetic.main.activitiy_about.bottom_navigation

class AboutActivity: BaseActivity() {
    override fun getLayoutResourceId(): Int = R.layout.activitiy_about

    override fun setUpUi() {
        viewPager.adapter = AboutAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewPager)
        bottom_navigation.setOnNavigationItemSelectedListener { selectNav(it) }
        bottom_navigation.menu.getItem(1).isChecked = true
    }


    private fun selectNav(it: MenuItem): Boolean {
        when(it.itemId){
            R.id.nav_tasks -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return false
    }
}