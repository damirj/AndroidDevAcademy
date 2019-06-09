package osc.androiddevacademy.movieapp.ui.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import osc.androiddevacademy.movieapp.R
import osc.androiddevacademy.movieapp.common.showFragment
import osc.androiddevacademy.movieapp.ui.movies.fragment.MoviesGridFragment

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        initMoviesGridFragment()
    }

    private fun initMoviesGridFragment(){

        showFragment(R.id.mainFragmentHolder,
            MoviesGridFragment()
        )
    }


}
