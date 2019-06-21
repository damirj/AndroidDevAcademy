package hr.ferit.brunozoric.taskie.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.showFragment

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutResourceId())
        setUpUi()
        overridePendingTransition(0,0)
    }

    protected fun showFragment(fragment: Fragment, container: Int = R.id.fragmentContainer){
        showFragment(container, fragment)
    }

    abstract fun getLayoutResourceId(): Int
    abstract fun setUpUi()
}