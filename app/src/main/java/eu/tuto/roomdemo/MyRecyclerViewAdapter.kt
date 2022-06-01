package eu.tuto.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import eu.tuto.roomdemo.databinding.ListItemBinding
import eu.tuto.roomdemo.db.Subscriber

class MyRecyclerViewAdapter(

    private val clickListener: (Subscriber) -> Unit
) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val subscribers = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribers[position], clickListener)
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

    fun setList(subscriber: List<Subscriber>) {
        subscribers.clear()
        subscribers.addAll(subscriber)
    }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email
        binding.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}