package com.paypay.currencyconversion


import android.app.Activity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar

import com.orhanobut.hawk.Hawk
import com.paypay.currencyconversion.Activity.MainActivity
import com.paypay.currencyconversion.Presenter.CurrenciesUI
import com.paypay.currencyconversion.Presenter.RatesUI

import java.util.ArrayList
import java.util.Date

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListCallback(internal var mainActivity: MainActivity) : Callback<Entity> {

    override fun onResponse(call: Call<Entity>, response: Response<Entity>) {
        if (response.body()!!.currencies != null && response.body()!!.currencies.size > 0) {
            CurrenciesUI(response.body()!!.currencies, mainActivity)
            Hawk.put(MyApplication.CURRENCIES, response.body()!!.currencies)
            Hawk.put(MyApplication.CURRENCIES_TIME_STAMP, Date())
        }


        if (response.body()!!.quotes != null && response.body()!!.quotes.size > 0) {
            RatesUI(response.body()!!.quotes, mainActivity)
            Hawk.put(MyApplication.QOUTES, response.body()!!.quotes)
            Hawk.put(MyApplication.QOUTES_TIME_STAMP, Date())
        }


        if (response.body()!!.error != null && response.body()!!.error.size > 0) {
            mainActivity.showSnackBar(ArrayList(response.body()!!.error.values)[1])
        }

        MyApplication.getProgressDialog().cancel()
    }

    override fun onFailure(call: Call<Entity>, t: Throwable) {
        MyApplication.getProgressDialog().cancel()
    }
}
