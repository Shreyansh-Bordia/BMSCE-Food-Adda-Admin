package com.example.adminpanelbmscefoodadda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpanelbmscefoodadda.adapter.PendingOrderAdapter
import com.example.adminpanelbmscefoodadda.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPendingOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val orderCustomerName = listOf("Shreyansh Bordia","Shravani N","Shreya V")
        val orderQuantity = listOf("2","3","1")
        val orderFoodImage = listOf(R.drawable.masaladosa,R.drawable.setdosa,R.drawable.idli)
        val adapter = PendingOrderAdapter(ArrayList(orderCustomerName),ArrayList(orderQuantity),ArrayList(orderFoodImage))
        binding.PendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.PendingOrderRecyclerView.adapter = adapter
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}