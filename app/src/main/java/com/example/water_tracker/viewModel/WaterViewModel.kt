package com.example.water_tracker.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.water_tracker.data.dao.WaterDao
import com.example.water_tracker.data.database.WaterDatabase
import com.example.water_tracker.data.model.Water
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WaterViewModel(application: Application) : AndroidViewModel(application) {

    private val waterDao: WaterDao = WaterDatabase.getDatabase(application).waterDao()
    val waterList: LiveData<List<Water>> = waterDao.getAll()
    val TAG = "minh"

    fun addWater(amount: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val water = Water(amount = amount, date = java.util.Date())
                waterDao.insert(water)
            } catch (e: Exception) {
                Log.e(TAG, "error add: "+e )
            }
        }
    }

    fun editWater(water: Water) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedWater = water.copy(amount = water.amount + 100)
                waterDao.update(updatedWater)
            } catch (e: Exception) {
                Log.e(TAG, "error edit: "+e )

            }
        }
    }

    fun deleteWater(water: Water) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                waterDao.delete(water)
            } catch (e: Exception) {
                Log.e(TAG, "error delete: "+e )

            }
        }
    }
}
