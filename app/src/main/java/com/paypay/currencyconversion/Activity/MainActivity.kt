package com.paypay.currencyconversion.Activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner

import androidx.recyclerview.widget.RecyclerView

import com.orhanobut.hawk.Hawk
import com.paypay.currencyconversion.*
import com.paypay.currencyconversion.Presenter.CurrenciesUI
import java.util.*
import kotlin.collections.HashMap
import android.os.Build
import android.os.Handler
import android.view.View
import android.widget.ScrollView
import com.paypay.currencyconversion.Presenter.RatesUI
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar


class MainActivity : BaseActivity() {
    lateinit var root: LinearLayout
        internal set
    lateinit var spinner1: Spinner
        internal set
    lateinit var editText: EditText
        internal set
    lateinit var recycler_view: RecyclerView
        internal set
    lateinit var scroll_view: ScrollView
        internal set

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onCreate(savedBundle: Bundle?) {
        super.onCreate(savedBundle)
        setContentView(R.layout.activity_main)

        root = findViewById(R.id.root)
        spinner1 = findViewById(R.id.spinner1)
        editText = findViewById(R.id.et_name)
        recycler_view = findViewById(R.id.recycler_view)
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                hideKeyboard(recyclerView)
            }
        })


        editText.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(10, 2))

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                try {
                    Hawk.put(MyApplication.SOURCE, java.lang.Double.parseDouble(s.toString()))
                } catch (e: Exception) {
                }

                try {
                    recycler_view.adapter!!.notifyDataSetChanged()
                } catch (e: Exception) {
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    override fun onResume() {
        super.onResume()

        if (Hawk.get(MyApplication.CURRENCIES,  HashMap<String, String>()).size == 0) {
            MyApplication.callApi(MyApplication.getApiService().response, ListCallback(this))
            MyApplication.callApi(MyApplication.getApiService().quotes, ListCallback(this))
        } else {
            if (Date().getTime() - Hawk.get(MyApplication.CURRENCIES_TIME_STAMP, Date()).getTime() >= 20 * 60 * 1000) {
                MyApplication.callApi(MyApplication.getApiService().response, ListCallback(this))
                MyApplication.callApi(MyApplication.getApiService().quotes, ListCallback(this))
            } else {
                MyApplication.RELOAD = true;

                MyApplication.getProgressDialog().show()

                
                var ref = Hawk.get(MyApplication.CURRENCIES,  HashMap<String, String>())
                val treeMap = TreeMap<String, String>()

                for ((k, v) in ref) 
                    treeMap.put(k, v);
                    
                Handler().postDelayed({
                    CurrenciesUI(treeMap, this)
                }, 1000)

                
                
                var ref2 = Hawk.get(MyApplication.QOUTES,  HashMap<String, String>())
                val treeMap2 = TreeMap<String, String>()

                for ((k, v) in ref2) 
                    treeMap2.put(k, v);
                

                Handler().postDelayed({
                    RatesUI(treeMap2, this)
                    MyApplication.getProgressDialog().cancel()
                }, 2000)
            }
        }
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

    fun showSnackBar(message: String) {
        Handler().postDelayed({
            this.hideKeyboard(recycler_view)
        }, 100)
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
    }
}
