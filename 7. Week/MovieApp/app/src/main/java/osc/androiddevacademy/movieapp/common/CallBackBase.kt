package osc.androiddevacademy.movieapp.common

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallBackBase<T>(val handleOkResponse: (T?) -> Unit, val handleSomethingWentWrong: (String) -> Unit): Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        when (response.code()) {
            in 200..300 -> handleOkResponse(response.body())
            in 400..500 -> handleSomethingWentWrong("Something went wrong")
            else -> handleSomethingWentWrong("There was problem with server")
        }

    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        handleSomethingWentWrong("No internet connection")
    }

}