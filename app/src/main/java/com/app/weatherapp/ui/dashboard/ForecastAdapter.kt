package com.app.weatherapp.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.databinding.RowForecastBinding
import com.app.weatherapp.models.showForecastModel.ForecastModel

class ForecastAdapter(
    val context: Context,
    private var itemList: ArrayList<ForecastModel>,
    private val callback: OnItemSelected
) : RecyclerView.Adapter<ForecastAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(context, itemList[position], position, callback)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MainViewHolder(val binding: RowForecastBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            context: Context,
            data: ForecastModel,
            position: Int,
            callback: OnItemSelected
        ) {
            //---set weekday text
            binding.weekDayTv.text = data.weekDay
            //--- set weekday temp
            binding.dayTempTv.text = data.avgTemp

        }

        companion object {
            fun from(parent: ViewGroup): MainViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowForecastBinding.inflate(layoutInflater, parent, false)
                return MainViewHolder(binding)
            }
        }
    }

    interface OnItemSelected {
        fun onSelect(position: Int, data: ForecastModel)
    }
}