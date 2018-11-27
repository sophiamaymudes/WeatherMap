package hu.ait.android.weathermap.data

import android.arch.persistence.room.*


@Dao
interface CityDAO {

    @Query("DELETE FROM cities")
    fun deleteAll()

    @Query("SELECT * FROM cities")
    fun findAllItems(): List<City>

    //The long is the ID
    @Insert
    fun insertCity(item: City) : Long

    @Delete
    fun deleteCity(item: City)

    @Update
    fun updateCity(item: City)
}