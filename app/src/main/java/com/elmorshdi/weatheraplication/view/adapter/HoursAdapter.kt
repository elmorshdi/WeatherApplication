package com.elmorshdi.weatheraplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elmorshdi.weatheraplication.databinding.HourItemBinding
import com.elmorshdi.weatheraplication.domain.weather.WeatherData


class HoursAdapter : androidx.recyclerview.widget.ListAdapter<WeatherData, HoursAdapter.HourViewHolder>(
    DaysDiffCallBack()
) {

    class DaysDiffCallBack : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }
    }

    class HourViewHolder constructor(
        private val binding: HourItemBinding,
     ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hour: WeatherData) = with(itemView) {
            binding.weather = hour
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HourItemBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return HourViewHolder(
            binding
        )
    }


    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val weatherData = getItem(position)
        holder.bind(weatherData)
    }





}
