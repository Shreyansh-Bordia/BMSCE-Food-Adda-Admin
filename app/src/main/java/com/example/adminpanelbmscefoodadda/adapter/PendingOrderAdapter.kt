package com.example.adminpanelbmscefoodadda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanelbmscefoodadda.databinding.ActivityLoginBinding
import com.example.adminpanelbmscefoodadda.databinding.PendingOrderItemBinding

class PendingOrderAdapter(private val customerNames:ArrayList<String>,private val quantity:ArrayList<String>,private val foodImages:ArrayList<Int>): RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding = PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerNames.size

    inner class PendingOrderViewHolder (private val binding: PendingOrderItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                CustomerNameTextView.text = customerNames[position]
                OrderFoodQuantityTextView.text = quantity[position]
                OrderFoodItemImage.setImageResource(foodImages[position])
            }
        }

    }
}