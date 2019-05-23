package hr.ferit.brunozoric.taskie.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import hr.ferit.brunozoric.taskie.R


class ApkInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_apk_info, container, false)
    }
    
    companion object {
        fun newInstance(): Fragment{
            return ApkInfoFragment()
        }
    }

}
