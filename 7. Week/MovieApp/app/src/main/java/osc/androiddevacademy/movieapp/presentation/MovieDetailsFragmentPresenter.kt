package osc.androiddevacademy.movieapp.presentation

import osc.androiddevacademy.movieapp.common.CallBackBase
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.model.response.ReviewsResponse
import osc.androiddevacademy.movieapp.networking.interactors.MovieInteractor
import osc.androiddevacademy.movieapp.persistence.MovieRepository
import osc.androiddevacademy.movieapp.ui.movies.fragment.MovieDetailsFragmentContract

class MovieDetailsFragmentPresenter(private val interactor: MovieInteractor, private val database: MovieRepository): MovieDetailsFragmentContract.Presenter {

    private lateinit var view: MovieDetailsFragmentContract.View

    override fun setView(view: MovieDetailsFragmentContract.View) {
        this.view = view
    }

    override fun getReviews(movieId: Int) {
        interactor.getReviewsForMovie(
            movieId = movieId,
            callback = CallBackBase({handleHttpOkResponse(it)}, {handleSomethingWentWrong(it)})
        )
    }

    private fun handleSomethingWentWrong(response: String) {
        view.onFailedGetReviews(response)
    }

    private fun handleHttpOkResponse(response: ReviewsResponse?){
        view.onGetReviews(response!!.reviews)
    }

    override fun toggleFavorite(movie: Movie) {
        if (movie.isFavorite) {
            database.deleteFavoriteMovie(movie)
        }else {
            database.addFavoriteMovie(movie)
        }
        view.onToggleFavorite(!movie.isFavorite)
    }
}