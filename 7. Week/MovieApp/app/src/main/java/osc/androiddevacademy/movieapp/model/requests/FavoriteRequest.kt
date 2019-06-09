package osc.androiddevacademy.movieapp.model.requests

import com.google.gson.annotations.SerializedName

data class FavoriteRequest(
    @SerializedName("media_type") val mediaType: String = "movie",
    @SerializedName("media_id") val movieId: Int,
    @SerializedName("favorite") val favorite: Boolean
)