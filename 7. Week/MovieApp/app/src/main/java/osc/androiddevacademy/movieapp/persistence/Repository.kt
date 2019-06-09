package osc.androiddevacademy.movieapp.persistence

import osc.androiddevacademy.movieapp.model.Movie

interface Repository {

    fun addFavoriteMovie(movie: Movie)
    fun deleteFavoriteMovie(movie: Movie)
    fun getFavoriteMovies(): List<Movie>

}