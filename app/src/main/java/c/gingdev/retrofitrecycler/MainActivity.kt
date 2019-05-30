package c.gingdev.retrofitrecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import c.gingdev.retrofitrecycler.recycler.itemAdapter
import c.gingdev.retrofitrecycler.vm.RecyclerVM
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val recyclerVM by lazy {
        ViewModelProviders.of(this@MainActivity).get(RecyclerVM::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.adapter = itemAdapter(
            vm = recyclerVM,
            lifecycleOwner = this@MainActivity)

        recyclerVM.AddItem()
        recyclerVM.AddItem()
        recyclerVM.AddItem()
    }
}
