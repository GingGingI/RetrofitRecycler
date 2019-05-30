package c.gingdev.retrofitmvvmrecycler.retrofit

import c.gingdev.retrofitmvvmrecycler.data.BusData
import retrofit2.Call
import retrofit2.http.GET

class retrofits {
    private val retrofit = retrofitInjection
        .provideRetrofit("http://2557defb.ngrok.io")

    fun getRetrofitService() : retrofitService
            = retrofit.create(retrofitService::class.java)

    companion object {
        private var retrofitInstance: retrofits? = null

        fun getInstance(): retrofits
                = retrofitInstance ?: synchronized(this) {
            retrofitInstance ?: buildRetrofitInstance().also { retrofitInstance = it }
        }

        private fun buildRetrofitInstance(): retrofits
                = retrofits()
    }

}

interface retrofitService {
    @GET("/bus")
    fun getDatas(): Call<BusData>
}
