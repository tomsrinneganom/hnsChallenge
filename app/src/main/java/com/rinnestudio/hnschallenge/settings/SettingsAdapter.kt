package com.rinnestudio.hnschallenge.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rinnestudio.hnschallenge.R

class SettingsAdapter : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

    class SettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.settingsTitle)
    }

    private val settingsList = listOf("Rename", "Edit profile photo")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.settings_item, parent, false)
        return SettingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.textView.text = settingsList[position]
    }

    override fun getItemCount() = settingsList.size
}