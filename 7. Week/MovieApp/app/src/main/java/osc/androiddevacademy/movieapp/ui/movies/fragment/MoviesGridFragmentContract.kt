package osc.androiddevacademy.movieapp.ui.movies.fragment

import osc.androiddevacademy.movieapp.model.Movie

interface MoviesGridFragmentContract {

    interface View{

        fun onReceivedPopularMovies(movies: ArrayList<Movie>)

        fun onFailedGetPopularMovies(response: String)

        fun onMarkedFav()

        fun onReceivedTopRatedMovies(movies: ArrayList<Movie>)

        fun onFailedTopRatedMovies(response: String)

        fun onReceivedFavoriteMovies(movies: List<Movie>)

    }

    interface Presenter{

        fun setView(view: View)

        fun getPopularMovies()

        fun getTopRatedMovies()

        fun getFavoriteMovies()

        fun markAsFavorite(movie: Movie)

    }

}