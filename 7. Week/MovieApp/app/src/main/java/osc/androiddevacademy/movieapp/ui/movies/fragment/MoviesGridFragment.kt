package osc.androiddevacademy.movieapp.ui.movies.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragemnt_movie_grid.*
import osc.androiddevacademy.movieapp.R
import osc.androiddevacademy.movieapp.common.displayToast
import osc.androiddevacademy.movieapp.common.showFragment
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.networking.BackendFactory
import osc.androiddevacademy.movieapp.persistence.MovieRepository
import osc.androiddevacademy.movieapp.persistence.SharedPrefs
import osc.androiddevacademy.movieapp.presentation.MoviesGridFragmentPresenter
import osc.androiddevacademy.movieapp.ui.adapter.MoviesGridAdapter
import osc.androiddevacademy.movieapp.ui.fragment.MoviesPagerFragment

class MoviesGridFragment : Fragment(), MoviesGridFragmentContract.View {

    private val SPAN_COUNT = 2

    private val presenter by lazy {
        MoviesGridFragmentPresenter(BackendFactory.getMovieInteractor(), MovieRepository())
    }

    private val gridAdapter by lazy {
        MoviesGridAdapter({ onMovieClicked(it) }, { onFavoriteClicked(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragemnt_movie_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesGrid.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
        movieSwipeRefresh.setOnRefreshListener { onRefresh() }
    }

    private fun requestMovies() {
        when(SharedPrefs.getString(SharedPrefs.KEY_MOVIE_SORT, POPULAR)){
            POPULAR -> requestPopularMovies()
            TOP_RATED -> requestTopRatedMovies()
            else -> requestFavoriteMovies()
        }
    }

    private fun onRefresh() {
        requestMovies()
        movieSwipeRefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
        requestMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.top_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.showTopRatedMovies -> requestTopRatedMovies()
            R.id.showPopularMovies -> requestPopularMovies()
            R.id.showFavoriteMovies-> requestFavoriteMovies()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun requestPopularMovies() {
        presenter.getPopularMovies()
    }

    override fun onReceivedPopularMovies(movies: ArrayList<Movie>) {
        gridAdapter.setMovies(movies)
        SharedPrefs.store(SharedPrefs.KEY_MOVIE_SORT, POPULAR)
    }

    override fun onFailedGetPopularMovies(response: String) {
        activity?.displayToast(response)
    }

    private fun onMovieClicked(movie: Movie) {
        activity?.showFragment(R.id.mainFragmentHolder, MoviesPagerFragment.getInstance(gridAdapter.getMovies(), movie), true)
    }

    private fun onFavoriteClicked(movie: Movie) {
        presenter.markAsFavorite(movie)
    }

    override fun onMarkedFav() {
        requestMovies()
    }

    private fun requestTopRatedMovies(){
        presenter.getTopRatedMovies()
    }

    override fun onReceivedTopRatedMovies(movies: ArrayList<Movie>) {
        gridAdapter.setMovies(movies)
        SharedPrefs.store(SharedPrefs.KEY_MOVIE_SORT, TOP_RATED)
    }

    override fun onFailedTopRatedMovies(response: String) {
        activity?.displayToast(response)
    }

    private fun requestFavoriteMovies(){
        presenter.getFavoriteMovies()
    }

    override fun onReceivedFavoriteMovies(movies: List<Movie>) {
        gridAdapter.setMovies(movies)
        SharedPrefs.store(SharedPrefs.KEY_MOVIE_SORT, FAVORITE)
    }

    companion object{
        const val POPULAR = "POPULAR"
        const val TOP_RATED = "TOP RATED"
        const val FAVORITE = "FAVORITE"
    }
}