package com.example.water_tracker

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.water_tracker.adapter.WaterAdapter
import com.example.water_tracker.data.model.Water
import com.example.water_tracker.viewModel.WaterViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: WaterAdapter
    private val waterViewModel: WaterViewModel by viewModels()
    private lateinit var totalWaterTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         totalWaterTextView = findViewById(R.id.totalWaterTextView)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = WaterAdapter(mutableListOf(), this::editWater, this::deleteWater)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            waterViewModel.addWater(500.0)
        }

        waterViewModel.waterList.observe(this, Observer { waterList ->
            adapter.updateData(waterList)
            val totalWater = waterList.sumOf { it.amount }
            totalWaterTextView.text = "Total Water: $totalWater ml"
        })

    }

    private fun editWater(water: Water) {
        waterViewModel.editWater(water)
    }

    private fun deleteWater(water: Water) {
        waterViewModel.deleteWater(water)
    }
}
