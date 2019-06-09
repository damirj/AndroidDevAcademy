package osc.androiddevacademy.movieapp.ui.movies.fragment

import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.model.Review

interface MovieDetailsFragmentContract {

    interface View{

        fun onGetReviews(reviews: List<Review>)

        fun onFailedGetReviews(response: String)

        fun onToggleFavorite(state: Boolean)


    }

    interface Presenter{

        fun setView(view: View)

        fun getReviews(movieId: Int)

        fun toggleFavorite(movie: Movie)

    }
}