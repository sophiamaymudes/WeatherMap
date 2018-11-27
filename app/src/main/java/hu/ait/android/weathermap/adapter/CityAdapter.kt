package hu.ait.android.weathermap.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.ait.android.weathermap.CityDetails
import hu.ait.android.weathermap.ItemTouchHelperAdapter
import hu.ait.android.weathermap.MainActivity
import hu.ait.android.weathermap.R
import hu.ait.android.weathermap.data.AppDatabase
import hu.ait.android.weathermap.data.City
import kotlinx.android.synthetic.main.city_row.view.*
import java.util.*

class CityAdapter: RecyclerView.Adapter<CityAdapter.ViewHolder>, ItemTouchHelperAdapter {


    var cityList = mutableListOf<City>()
    val context : Context

    constructor(context: Context, cities: List<City>) : super() {
        this.context = context
        //addAll will copy over all items from the list
        this.cityList.addAll(cities)
    }

    constructor(context: Context) : super() {
        this.context = context

    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(
                R.layout.city_row, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cityList[position]
        holder.tvName.text = city.cityName


        holder.btnDelete.setOnClickListener{
            deleteCity(holder.adapterPosition)
        }
        holder.itemView.setOnClickListener(){
            MainActivity().showDetails(holder.tvName.text.toString())
        }

    }

    private fun deleteCity(adapterPosition: Int) {

        Thread{
            AppDatabase.getInstance(context).CityDAO().deleteCity(
                    cityList[adapterPosition]
            )
            cityList.removeAt(adapterPosition)

            (context as MainActivity).runOnUiThread{
                notifyItemRemoved(adapterPosition)
            }
            //START THE THING!
        }.start()

    }
    


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvName = itemView.tvCityName
        val btnDelete = itemView.btnDelete



    }

    override fun onDismissed(position: Int) {
        deleteCity(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(cityList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    //addstodo to the adaptor list
    fun insertCity(city: City){
        cityList.add(0, city)
        //inefficient, redraws whole list
        //notifyDataSetChanged()
        notifyItemInserted(0)
    }


    public fun updateCity(city: City, idx: Int) {
        cityList[idx] = city
        notifyItemChanged(idx)
    }


}