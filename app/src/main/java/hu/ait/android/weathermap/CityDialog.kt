package hu.ait.android.weathermap

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import java.lang.RuntimeException
import java.util.*
import hu.ait.android.weathermap.data.City
import kotlinx.android.synthetic.main.dialog_city.*
import kotlinx.android.synthetic.main.dialog_city.view.*


class CityDialog : DialogFragment(){

    interface CityHandler {
        fun cityCreated(city: City)
        fun cityUpdated(item: City)
    }

    private lateinit var cityHandler: CityHandler


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is CityHandler) {
            cityHandler = context
        } else {
            throw RuntimeException("The activity does not implement the CityHandler Interface")
        }
    }

    private lateinit var  etCityName: EditText



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("New City")

        val rootView = requireActivity().layoutInflater.inflate(R.layout.dialog_city, null)

        etCityName = rootView.etCityName



        builder.setView(rootView)

        val arguments = this.arguments
        /*if (arguments != null && arguments.containsKey(MainActivity.KEY_ITEM_TO_EDIT)) {
            val currentItem = arguments.getSerializable(
                    MainActivity.KEY_ITEM_TO_EDIT
            ) as City
            etCityName.setText(currentItem.cityName)






            builder.setTitle("Edit item")
        } else {
            etItemDate.visibility = View.GONE
        }*/

        builder.setPositiveButton("OK"){
            dialog, which ->  //empty
        }

        return builder.create()

    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etCityName.text.isNotEmpty()) {
                val arguments = this.arguments
                    handleItemCreate()
                dialog.dismiss()
            } else {
                etCityName.error = "This field can not be empty"
            }
        }
    }

    private fun handleItemCreate() {
        cityHandler.cityCreated(
                City(
                        null,
                        etCityName.text.toString()

                )
        )
    }






}