package osc.androiddevacademy.movieapp.networking.interactors

import osc.androiddevacademy.movieapp.model.*
import osc.androiddevacademy.movieapp.model.response.*
import retrofit2.Callback

interface MovieInteractor {

    fun getPopularMovies(popularMoviesCallback: Callback<MoviesResponse>)

    fun getTopMovies(topMoviesCallback: Callback<MoviesResponse>)

    fun getMovie(movieId: Int, movieCallback: Callback<Movie>)

    fun getReviewsForMovie(movieId: Int, callback: Callback<ReviewsResponse>)

}