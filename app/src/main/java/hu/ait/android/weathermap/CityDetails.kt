package hu.ait.android.weathermap

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_city_details.*

class CityDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_details)

        val city = intent.getStringExtra("city")
        tvDetailsCityName.text = city

    }
}
