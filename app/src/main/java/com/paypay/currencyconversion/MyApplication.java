package com.paypay.currencyconversion;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {
    public static final String DEFAULT_COUNTRY = "DEFAULT_COUNTRY";
    public static final String DEFAULT_COUNTRY_FULL = "DEFAULT_COUNTRY_FULL";

    public static final String SOURCE = "SOURCE";
    public static final String CURRENCIES = "CURRENCIES";
    public static final String CURRENCIES_TIME_STAMP = "CURRENCIES_TIME_STAMP";
    public static final String QOUTES = "QOUTES";
    public static final String QOUTES_TIME_STAMP = "QOUTES_TIME_STAMP";
    public static boolean RELOAD = false;

    private static MyApplication instance;
    private static Retrofit retrofit;
    private static APIService service = MyApplication.getRetrofit().create(APIService.class);
    private static ProgressDialog progressDialog = null;

    public static MyApplication getInstance() {
        if (instance== null) {
            synchronized(MyApplication.class) {
                if (instance == null)
                    instance = new MyApplication();
            }
        }
        // Return the instance
        return instance;
    }

    public static Retrofit getRetrofit() {
        if(getInstance().retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            getInstance().retrofit = new Retrofit.Builder()
                    .baseUrl("http://apilayer.net/")

                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return getInstance().retrofit;
    }

    public static APIService getApiService() {
        service = MyApplication.getRetrofit().create(APIService.class);
        return service;
    }

    public static void callApi(Call call, retrofit2.Callback callback) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    getProgressDialog().show();
                } catch (Exception e) {
                }
            }
        });

        call.enqueue(callback);
    }

    public void onCreate() {
        super.onCreate();
        this.instance = getInstance();
        this.retrofit = getRetrofit();
        Hawk.init(this).build();
    }

    private static Activity mCurrentActivity = null;

    public static Activity getCurrentActivity(){
        return mCurrentActivity;
    }

    public static void setCurrentActivity(Activity mCurrentActivity){
        MyApplication.mCurrentActivity = mCurrentActivity;
    }

    public static ProgressDialog getProgressDialog() {
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(getCurrentActivity());
        }
        return progressDialog;
    }

    public static void setProgressDialog(ProgressDialog progressDialog) {
        MyApplication.progressDialog = progressDialog;
    }
}
