package hu.ait.android.weathermap.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cities")

//in Android, if you have an SQLite table, an ID column is mandatory
data class City(
        //primary key is mandatory for SQLite
        //Long? instead of Long because at some points during the creation this may be null
        @PrimaryKey(autoGenerate = true) var cityId: Long?,
        @ColumnInfo(name = "CityName") var cityName: String

): Serializable