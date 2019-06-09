package osc.androiddevacademy.movieapp.presentation

import osc.androiddevacademy.movieapp.common.CallBackBase
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.model.response.MoviesResponse
import osc.androiddevacademy.movieapp.networking.interactors.MovieInteractor
import osc.androiddevacademy.movieapp.persistence.MovieRepository
import osc.androiddevacademy.movieapp.ui.movies.fragment.MoviesGridFragmentContract

class MoviesGridFragmentPresenter(private val interactor: MovieInteractor,
                                  private val database: MovieRepository): MoviesGridFragmentContract.Presenter {

    private lateinit var view: MoviesGridFragmentContract.View

    override fun setView(view: MoviesGridFragmentContract.View) {
        this.view = view
    }

    override fun getPopularMovies() {
        interactor.getPopularMovies(CallBackBase({handleHttpOkGetPopularResponse(it)}, {handleGetPopularSomethingWentWrong(it)}))
    }

    private fun handleHttpOkGetPopularResponse(response: MoviesResponse?){
        val popularMovies = response!!.movies
        view.onReceivedPopularMovies(mapAsFavorite(popularMovies))
    }

    private fun handleGetPopularSomethingWentWrong(response: String) {
        view.onFailedGetPopularMovies(response)
    }

    override fun markAsFavorite(movie: Movie) {
        if (movie.isFavorite) database.deleteFavoriteMovie(movie) else database.addFavoriteMovie(movie)
        view.onMarkedFav()
    }

    override fun getTopRatedMovies() {
        interactor.getTopMovies(CallBackBase({handleHttpOkTopMoviesResponse(it)}, {handleTopMoviesSomethingWentWrong(it)}))
    }

    private fun handleHttpOkTopMoviesResponse(response: MoviesResponse?){
        val topRatedMovies = response!!.movies
        view.onReceivedTopRatedMovies(mapAsFavorite(topRatedMovies))
    }

    private fun handleTopMoviesSomethingWentWrong(response: String){
        view.onFailedTopRatedMovies(response)
    }

    override fun getFavoriteMovies() {
        val favoriteMovies= database.getFavoriteMovies()
        favoriteMovies.map { it.isFavorite = true }
        view.onReceivedFavoriteMovies(favoriteMovies)
    }

    private fun mapAsFavorite(movies: ArrayList<Movie>): ArrayList<Movie>{
        val favoriteMovies = database.getFavoriteMovies()
        movies.map { it.isFavorite = false }

        for (movie in favoriteMovies){
            movies.find {it.title == movie.title}?.isFavorite = true
        }
        return movies
    }

}