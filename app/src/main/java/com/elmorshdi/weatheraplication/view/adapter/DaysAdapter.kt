

package com.elmorshdi.weatheraplication.view.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elmorshdi.weatheraplication.R
import com.elmorshdi.weatheraplication.databinding.DayItemBinding
import com.elmorshdi.weatheraplication.domain.weather.WeatherByDay


class DaysAdapter(
    private val interaction: Interaction? = null,
    private val context:Context
) : androidx.recyclerview.widget.ListAdapter<WeatherByDay, DaysAdapter.DaysViewHolder>(DaysDiffCallBack()) {
    var selectedPosition = -1

    class DaysDiffCallBack : DiffUtil.ItemCallback<WeatherByDay>() {
        override fun areItemsTheSame(oldItem: WeatherByDay, newItem: WeatherByDay): Boolean {
            return oldItem.data == newItem.data
        }

        override fun areContentsTheSame(oldItem: WeatherByDay, newItem: WeatherByDay): Boolean {
            return oldItem == newItem
        }
    }

    class DaysViewHolder constructor(
        private val binding: DayItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: WeatherByDay) = with(itemView) {
            binding.tvDate.text= day.data
            //Notify the listener on item click
            itemView.setOnClickListener {
                interaction?.onItemSelected(day)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DayItemBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return DaysViewHolder(
            binding, interaction
        )
    }


    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        if (selectedPosition == position) {
            holder.itemView.background.setTint(ContextCompat.getColor(context,R.color.blue ))
            holder.itemView.findViewById<TextView>(R.id.tvDate).setTextColor(Color.parseColor("#ffffff"))

        }
        else {
            holder.itemView.findViewById<TextView>(R.id.tvDate).setTextColor(Color.parseColor("#6e6e69"))

            holder.itemView.background.setTint(ContextCompat.getColor(context,R.color.white ))

        }

        holder.itemView.setOnClickListener {

                selectedPosition = position
                notifyItemChanged(position)

        }
        val weatherByDay = getItem(position)
        holder.bind(weatherByDay)
    }


    interface Interaction {
        fun onItemSelected(weatherByDay: WeatherByDay)

    }


}
