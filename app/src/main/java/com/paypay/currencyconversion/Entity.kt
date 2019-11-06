package com.paypay.currencyconversion

import java.util.*

class Entity {
    var isSuccess: Boolean = false
    lateinit var terms: String
    lateinit var privacy: String
    internal var currencies = TreeMap<String, String>()
    lateinit var source: String
    internal var quotes = TreeMap<String, String>()
    internal var error = TreeMap<String, String>()

    fun getCurrencies(): TreeMap<String, String> {
        return currencies
    }

    fun setCurrencies(currencies: TreeMap<String, String>) {
        this.currencies = currencies
    }

    fun getQuotes(): TreeMap<String, String> {
        return quotes
    }

    fun setQuotes(quotes: TreeMap<String, String>) {
        this.quotes = quotes
    }

    fun getError(): TreeMap<String, String> {
        return error
    }

    fun setError(error: TreeMap<String, String>) {
        this.error = error
    }
}
