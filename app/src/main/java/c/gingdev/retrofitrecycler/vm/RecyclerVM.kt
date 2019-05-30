package c.gingdev.retrofitrecycler.vm

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import c.gingdev.retrofitmvvmrecycler.data.BusData
import c.gingdev.retrofitmvvmrecycler.data.Data
import c.gingdev.retrofitmvvmrecycler.retrofit.retrofits
import c.gingdev.retrofitrecycler.recycler.itemAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.TimeUnit

class RecyclerVM: ViewModel() {
    private fun Any?.notNull(f: ()-> Unit) {
        if (this != null) f()
    }
    private fun Any?.isNull(f: ()-> Unit) {
        if (this == null) f()
    }

    private val BusObservable =
        Observable.interval(0, 10, TimeUnit.SECONDS)
            .flatMap {
                return@flatMap Observable.create<BusData> { subscriber ->
                    retrofits.getInstance()
                        .getRetrofitService()
                        .getDatas()
                        .enqueue(object : retrofit2.Callback<BusData> {
                            override fun onFailure(call: Call<BusData>, t: Throwable) {
                                subscriber.onError(t)
                            }

                            override fun onResponse(call: Call<BusData>, response: Response<BusData>) {
                                response.body().notNull {
                                    Log.i("data", "${response.body()!!.data.time1}")
                                    subscriber.onNext(response.body()!!)
                                }
                            }
                        })
                }.subscribeOn(Schedulers.io())
            }

//    Data
    val items = ArrayList<MutableLiveData<BusData>>()
    private var adapter: itemAdapter? = null

    @SuppressLint("CheckResult")
    fun AddItem() {
        items.add(MutableLiveData())
        val num = items.size - 1
        items[num].postValue(BusData(Data("0","0","0","0")))

        adapter.notNull { adapter!!.notifyDataSetChanged() }

        BusObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            items[num].value = it
        }, {
            it.printStackTrace()
        })
    }

    fun setRecyclerAdapter(adapter: itemAdapter) {
        this.adapter = adapter
    }

}