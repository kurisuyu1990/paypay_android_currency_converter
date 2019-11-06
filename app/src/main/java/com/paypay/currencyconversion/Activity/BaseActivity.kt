package com.paypay.currencyconversion.Activity

import android.app.Activity
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.paypay.currencyconversion.MyApplication

open class BaseActivity : AppCompatActivity() {
    protected var mMyApp: MyApplication? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMyApp = this.applicationContext as MyApplication
    }

    override fun onResume() {
        super.onResume()
        MyApplication.setCurrentActivity(this)
    }

    override fun onPause() {
        clearReferences()
        super.onPause()
    }

    override fun onDestroy() {
        clearReferences()
        super.onDestroy()
    }

    private fun clearReferences() {
        val currActivity = MyApplication.getCurrentActivity()
        if (this == currActivity)
            MyApplication.setCurrentActivity(null)
    }

}
