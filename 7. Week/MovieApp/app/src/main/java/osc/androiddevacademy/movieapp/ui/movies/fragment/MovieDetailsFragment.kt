package osc.androiddevacademy.movieapp.ui.movies.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragemnt_details.*
import kotlinx.android.synthetic.main.fragemnt_details.movieFavorite
import kotlinx.android.synthetic.main.fragemnt_details.movieImage
import osc.androiddevacademy.movieapp.R
import osc.androiddevacademy.movieapp.common.displayToast
import osc.androiddevacademy.movieapp.common.loadImage
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.model.Review
import osc.androiddevacademy.movieapp.networking.BackendFactory
import osc.androiddevacademy.movieapp.persistence.MovieRepository
import osc.androiddevacademy.movieapp.presentation.MovieDetailsFragmentPresenter
import osc.androiddevacademy.movieapp.ui.adapter.ReviewAdapter

class MovieDetailsFragment : Fragment(), MovieDetailsFragmentContract.View {

    private lateinit var movie: Movie
    private val reviewsAdapter by lazy { ReviewAdapter() }
    private val presenter by lazy { MovieDetailsFragmentPresenter(BackendFactory.getMovieInteractor(), MovieRepository()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragemnt_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie = arguments?.getParcelable(MOVIE_EXTRA) as Movie
        initUi()
        getReviews(movie.id)
        initListeners()
    }

    private fun initUi(){
        movieImage.loadImage(movie.poster)
        movieTitle.text = movie.title
        movieOverview.text = movie.overview
        movieReleaseDate.text = movie.releaseDate
        movieVoteAverage.text = movie.averageVote.toString()
        movieFavorite.setImageResource(if (movie.isFavorite) R.drawable.ic_favorite_full else R.drawable.ic_favorite_empty)

        movieReviewList.apply {
            adapter = reviewsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initListeners(){
        movieFavorite.setOnClickListener { onFavoriteClicked() }
    }

    private fun onFavoriteClicked(){
        presenter.toggleFavorite(movie)
    }

    override fun onToggleFavorite(state: Boolean) {
        movieFavorite.setImageResource(if (state) R.drawable.ic_favorite_full else R.drawable.ic_favorite_empty)
        movie.isFavorite = state
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    private fun getReviews(movieId: Int){
        presenter.getReviews(movieId)
    }

    override fun onGetReviews(reviews: List<Review>) {
        reviewsAdapter.setReviewList(reviews)
    }

    override fun onFailedGetReviews(response: String) {
        activity?.displayToast(response)
    }

    companion object {
        private const val MOVIE_EXTRA = "movie_extra"

        fun getInstance(movie: Movie): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(MOVIE_EXTRA, movie)
            fragment.arguments = bundle
            return fragment
        }
    }
}