package com.paypay.currencyconversion

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView

import com.orhanobut.hawk.Hawk
import com.paypay.currencyconversion.Activity.MainActivity

class CustomView : LinearLayout {
    lateinit var country_name: TextView
    lateinit var country_name_full: TextView
        internal set
    lateinit var rate: TextView
        internal set
    lateinit var code: String

    constructor(context: Context) : super(context) {
        LayoutInflater.from(context).inflate(R.layout.custom_view, this, true)
        init(this)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_view, this, true)
        init(this)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    fun init(root: View) {
        country_name = root.findViewById(R.id.country_name)
        country_name_full = root.findViewById(R.id.country_name_full)
        rate = root.findViewById(R.id.rate)
    }

    fun setCountryRate(countryRate: CountryRate, mainActivity: MainActivity) {
        country_name.text = countryRate.displayCountryCode
        country_name_full.text = countryRate.countryName
        rate.text = countryRate.rate
        this.setOnClickListener {
             mainActivity.spinner1.setSelection(
                getIndex(
                    mainActivity.spinner1,
                    countryRate.countryName
                )
            )
        }
    }

    private fun getIndex(spinner: Spinner, myString: String?): Int {
        var index = 0

        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i) == myString) {
                index = i
            }
        }
        return index
    }
}
