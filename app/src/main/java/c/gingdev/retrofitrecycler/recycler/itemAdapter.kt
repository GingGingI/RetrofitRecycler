package c.gingdev.retrofitrecycler.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import c.gingdev.retrofitmvvmrecycler.data.BusData
import c.gingdev.retrofitrecycler.R
import c.gingdev.retrofitrecycler.vm.RecyclerVM
import kotlinx.android.synthetic.main.item_layout.view.*

class itemAdapter(
    val vm: RecyclerVM,
    val lifecycleOwner: LifecycleOwner):
    RecyclerView.Adapter<itemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder
        = itemViewHolder(parent, lifecycleOwner)

    override fun getItemCount(): Int
        = vm.items.size

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        holder.bindTo(vm.items[position])
    }
}

class itemViewHolder(parent: ViewGroup, val lifecycleOwner: LifecycleOwner): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)) {

    private val time1 = itemView.time1
    private val station1 = itemView.station1
    private val time2 = itemView.time2
    private val station2 = itemView.station2

    fun bindTo(data: MutableLiveData<BusData>) {
        data.observe(lifecycleOwner, Observer {
            Log.i("data", it.data.time1)
            it.data.let { data ->
                time1.text = data.time1
                station1.text = data.station1
                time2.text = data.time2
                station2.text = data.station2
            }
        })
    }
}