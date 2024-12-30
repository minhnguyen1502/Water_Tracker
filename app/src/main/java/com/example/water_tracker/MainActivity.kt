package com.example.water_tracker

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.water_tracker.adapter.WaterAdapter
import com.example.water_tracker.data.dao.WaterDao
import com.example.water_tracker.data.database.WaterDatabase
import com.example.water_tracker.data.model.Water
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var waterDao: WaterDao
    private lateinit var adapter: WaterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        waterDao = WaterDatabase.getDatabase(this).waterDao()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = WaterAdapter(mutableListOf(), this::editWater, this::deleteWater)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            addWater(500.0)
        }

        loadWaterData()
    }

    private fun addWater(amount: Double) {
        lifecycleScope.launch {
            val water = Water(amount = amount, date = Date())
            waterDao.insert(water)
            loadWaterData()
        }
    }

    private fun editWater(water: Water) {
        lifecycleScope.launch {
            val updatedWater = water.copy(amount = water.amount + 100)
            waterDao.update(updatedWater)
            loadWaterData()
        }
    }

    private fun deleteWater(water: Water) {
        lifecycleScope.launch {
            waterDao.delete(water)
            loadWaterData()
        }
    }

    private fun loadWaterData() {
        waterDao.getAll().observe(this, { waterList ->
            adapter.updateData(waterList)
        })
    }
}
