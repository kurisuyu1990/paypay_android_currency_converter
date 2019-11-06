package com.paypay.currencyconversion.Presenter

import android.util.Log
import android.widget.LinearLayout

import androidx.recyclerview.widget.LinearLayoutManager

import com.orhanobut.hawk.Hawk
import com.paypay.currencyconversion.Activity.MainActivity
import com.paypay.currencyconversion.CustomView
import com.paypay.currencyconversion.MyAdapter
import com.paypay.currencyconversion.MyApplication
import com.paypay.currencyconversion.RatesAdapter
import java.util.*

class RatesUI(qoutes: TreeMap<String, String>, private val mainActivity: MainActivity) {
    private var qoutes = TreeMap<String, String>()

    val qoutesSource: Map<String, String>
        get() = Hawk.get<Any>(MyApplication.QOUTES) as Map<String, String>

    val currenciesSource: Map<String, String>
        get() = Hawk.get<Any>(MyApplication.CURRENCIES) as Map<String, String>

    init {
        this.qoutes = qoutes
        map()
        Log.i("rate", "rate");
    }

    fun map() {
        mainActivity.recycler_view.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        mainActivity.recycler_view.adapter = RatesAdapter(qoutes, mainActivity)
    }
}
