package hr.ferit.brunozoric.taskie.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hr.ferit.brunozoric.taskie.ui.about.fragment.ApkInfoFragment

class AboutAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> ApkInfoFragment.newInstance()
            else -> ApkInfoFragment.newInstance()
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> APP_INFO
            else -> AUTHOR_INFO
        }
    }

    companion object{
        const val APP_INFO = "App info"
        const val AUTHOR_INFO = "Author info"
    }

}