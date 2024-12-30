package com.example.water_tracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.water_tracker.R
import com.example.water_tracker.data.model.Water

class WaterAdapter(
    private val items: MutableList<Water>,
    private val onEdit: (Water) -> Unit,
    private val onDelete: (Water) -> Unit
) : RecyclerView.Adapter<WaterAdapter.WaterViewHolder>() {

    class WaterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val waterInfo: TextView = view.findViewById(R.id.waterInfo)
        val editButton: Button = view.findViewById(R.id.editButton)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.water_item, parent, false)
        return WaterViewHolder(view)
    }

    override fun onBindViewHolder(holder: WaterViewHolder, position: Int) {
        val water = items[position]
        holder.waterInfo.text = "ID: ${water.id}, Amount: ${water.amount}ml"
        holder.editButton.setOnClickListener { onEdit(water) }
        holder.deleteButton.setOnClickListener { onDelete(water) }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newWaters: List<Water>) {
        items.clear()
        items.addAll(newWaters)
        notifyDataSetChanged()
    }

}
