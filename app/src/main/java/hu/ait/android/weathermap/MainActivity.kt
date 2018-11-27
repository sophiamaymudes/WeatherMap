package hu.ait.android.weathermap

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import hu.ait.android.weathermap.adapter.CityAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import hu.ait.android.weathermap.data.AppDatabase
import hu.ait.android.weathermap.data.City


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, CityDialog.CityHandler {


    private lateinit var cityAdapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fabAddCity.setOnClickListener { view ->
            showAddCityDialog()
        }

        initRecyclerView()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.about -> {
                Toast.makeText(this@MainActivity, "This app was made by Sophia Maymudes", Toast.LENGTH_LONG).show()
            }
            R.id.addCity -> {
                showAddCityDialog()

            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initRecyclerView() {
        Thread {
            val cities = AppDatabase.getInstance(this@MainActivity).CityDAO().findAllItems()

            runOnUiThread {
                //inside the thread to make sure the todoAdaptor is created before the recyclerTodo touches it
                //inside UI thread because the todoAdaptor
                cityAdapter = CityAdapter(this@MainActivity, cities)
                recyclerCities.adapter = cityAdapter
                val callback = CityTouchHelperCallback(cityAdapter)
                val touchHelper = ItemTouchHelper(callback)
                touchHelper.attachToRecyclerView(recyclerCities)
            }
        }.start()
    }

    private fun showAddCityDialog() {
        CityDialog().show(supportFragmentManager, "TAG_CREATE")
    }

    override fun cityCreated(city: City) {
        Thread {
            val id = AppDatabase.getInstance(
                    this@MainActivity).CityDAO().insertCity(city)

            city.cityId = id

            runOnUiThread{
                cityAdapter.insertCity(city)

            }
        }.start()
    }

    override fun cityUpdated(item: City) {

    }

    fun showDetails(name: String){
        var intentStart = Intent()
        intentStart.setClass(this@MainActivity, CityDetails::class.java)
        intentStart.putExtra("city", name)
        startActivity(intentStart)
    }


}
