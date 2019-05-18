package hr.ferit.brunozoric.taskie.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.ApkInfoFragment
import hr.ferit.brunozoric.taskie.ui.fragments.AuthorInfoFragment

class AboutAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> ApkInfoFragment.newInstance()
            1 -> AuthorInfoFragment.newInstance()
            else -> ApkInfoFragment.newInstance()
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "App info"
            1 -> "Author info"
            else -> "App info"
        }
    }

}