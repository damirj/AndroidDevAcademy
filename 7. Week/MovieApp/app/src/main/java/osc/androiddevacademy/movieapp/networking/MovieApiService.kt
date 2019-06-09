package osc.androiddevacademy.movieapp.networking

import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.model.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {

    @GET("movie/popular")
    fun getPopularMovies(): Call<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopMovies(): Call<MoviesResponse>

    @GET("movie/{id}")
    fun getMovie(@Path(value = "id") movieId: Int): Call<Movie>

    @GET("movie/{id}/reviews")
    fun getReviews(@Path(value = "id") movieId: Int): Call<ReviewsResponse>
}