package hr.ferit.brunozoric.taskie.common

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallBackBase<T>(val handleOkResponse: (T?) -> Unit, val handleSomethingWentWrong: () -> Unit): Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            when (response.code()) {
                RESPONSE_OK -> handleOkResponse(response.body())
                else -> handleSomethingWentWrong()
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {

    }
}
