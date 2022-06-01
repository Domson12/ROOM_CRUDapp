package eu.tuto.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import eu.tuto.roomdemo.databinding.ActivityMainBinding
import eu.tuto.roomdemo.db.Subscriber
import eu.tuto.roomdemo.db.SubscriberDatabase
import eu.tuto.roomdemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
       initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.subscriberRecyclerview.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
    }
    private fun displaySubscribersList() {
        subscriberViewModel.getSaveSubscribers().observe(this) {
            Log.i("MyTag", it.toString())
            binding.subscriberRecyclerview.adapter = MyRecyclerViewAdapter(it) { selectedItem: Subscriber ->
                listItemClicked(
                    selectedItem
                )
            }
        }
    }
    private fun listItemClicked(subscriber: Subscriber){
        Toast.makeText(this, "selected name is ${subscriber.name}", Toast.LENGTH_SHORT).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}