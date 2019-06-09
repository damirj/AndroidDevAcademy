package osc.androiddevacademy.movieapp.persistence

import osc.androiddevacademy.movieapp.App
import osc.androiddevacademy.movieapp.database.MoviesDatabase
import osc.androiddevacademy.movieapp.model.Movie

class MovieRepository: Repository {

    private var db = MoviesDatabase.getInstance(App.getAppContext())

    private var movieDao = db.moviesDao()

    override fun addFavoriteMovie(movie: Movie) {
        movieDao.addFavoriteMovie(movie)
    }

    override fun deleteFavoriteMovie(movie: Movie) {
        movieDao.deleteFavoriteMovie(movie)
    }

    override fun getFavoriteMovies(): List<Movie> = movieDao.getFavoriteMovies()

}