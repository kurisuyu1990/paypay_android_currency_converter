package com.paypay.currencyconversion

import android.util.Log

import com.orhanobut.hawk.Hawk
import com.paypay.currencyconversion.Presenter.FlagPresenter

import java.util.HashMap

class CountryRate(var countryCode: String?) {
    var countryName: String? = null
    var rate: String? = null

    val displayCountryCode: String?
        get() = if (FlagPresenter.getFlagEmoji(countryCode).length > 0) FlagPresenter.getFlagEmoji(
            countryCode
        ) + "\n" + countryCode else countryCode

    init {
        this.countryName = (Hawk.get(
            MyApplication.CURRENCIES,
            HashMap<String, String>()
        ) as HashMap<String, String>)[countryCode]
        try {
            val currentRateToUSD = java.lang.Double.parseDouble(
                (Hawk.get(
                    MyApplication.QOUTES,
                    HashMap<String, String>()
                ) as HashMap<String, String>)["USD" + Hawk.get<Any>(
                    MyApplication.DEFAULT_COUNTRY
                )]!!
            )

            this.rate = "" + Hawk.get(
                MyApplication.SOURCE,
                1.0
            ) * java.lang.Double.parseDouble((Hawk.get<Any>(MyApplication.QOUTES) as HashMap<String, String>)["USD$countryCode"]!!) / currentRateToUSD
        } catch (e: Exception) {

        }

    }
}
