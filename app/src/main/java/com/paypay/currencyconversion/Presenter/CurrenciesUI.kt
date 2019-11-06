package com.paypay.currencyconversion.Presenter

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

import com.orhanobut.hawk.Hawk
import com.paypay.currencyconversion.Activity.MainActivity
import com.paypay.currencyconversion.MyApplication
import java.util.*
import kotlin.collections.HashMap

class CurrenciesUI(currencies: TreeMap<String, String>, private val mainActivity: MainActivity) {
    private var currencies = TreeMap<String, String>()

    init {
        this.currencies = currencies
        map()
    }

    fun map() {
        val countries = ArrayList<String>()
        for (i in 0 until currencies.size) {
            countries.add(currencies.values.toTypedArray()[i])
        }

        val spinnerArrayAdapter = ArrayAdapter(mainActivity, android.R.layout.simple_spinner_item, countries)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mainActivity.spinner1.adapter = spinnerArrayAdapter

        if(Hawk.get(MyApplication.DEFAULT_COUNTRY_FULL, "").length == 0)
            mainActivity.spinner1.setSelection(getIndex(mainActivity.spinner1, "United States Dollar"))
        else
            mainActivity.spinner1.setSelection(getIndex(mainActivity.spinner1, Hawk.get(MyApplication.DEFAULT_COUNTRY_FULL, "")))

        mainActivity.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val key = getKeyByValue(
                    Hawk.get<Any>(MyApplication.CURRENCIES) as Map<String, String>,
                    mainActivity.spinner1.selectedItem.toString()
                )
                val rate = (Hawk.get<Any>(MyApplication.QOUTES, HashMap<String, String>() ) as Map<String, String>)["USD" + key!!]
                val oldKey = Hawk.get<String>(MyApplication.DEFAULT_COUNTRY)
                val oldRate = (Hawk.get<Any>(MyApplication.QOUTES, HashMap<String, String>()) as Map<String, String>)["USD$oldKey"]
                Hawk.put(MyApplication.DEFAULT_COUNTRY, key)

                val currencies_full_name = (Hawk.get<Any>(MyApplication.CURRENCIES, HashMap<String, String>() ) as Map<String, String>)[Hawk.get<Any>(MyApplication.DEFAULT_COUNTRY)]
                Hawk.put(MyApplication.DEFAULT_COUNTRY_FULL, currencies_full_name)

                if(MyApplication.RELOAD == false && (rate != null || oldRate != null)) {
                    if (oldKey == "USD") {
                        mainActivity.editText.setText(
                            decimalPlacesUpToTwo(
                                java.lang.Double.parseDouble(
                                    mainActivity.editText.text.toString()
                                ) * java.lang.Double.parseDouble(rate!!)
                            )
                        )
                    } else {
                        val valueInUSD =
                            java.lang.Double.parseDouble(mainActivity.editText.text.toString()) / java.lang.Double.parseDouble(
                                oldRate!!
                            )
                        mainActivity.editText.setText(
                            decimalPlacesUpToTwo(
                                valueInUSD * java.lang.Double.parseDouble(
                                    rate!!
                                )
                            )
                        )
                    }
                }
                if(MyApplication.RELOAD)
                    MyApplication.RELOAD = false

                Log.i("key rate", "$key $rate")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        Hawk.put(MyApplication.DEFAULT_COUNTRY, "USD")
    }

    private fun getIndex(spinner: Spinner, myString: String): Int {
        var index = 0

        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i) == myString) {
                index = i
            }
        }
        return index
    }

    fun decimalPlacesUpToTwo(value: Double): String {
        return String.format("%.2f", value)
    }

    companion object {

        fun <T, E> getKeyByValue(map: Map<T, E>, value: E): T? {
            for ((key, value1) in map) {
                if (value == value1) {
                    return key
                }
            }
            return null
        }
    }
}
