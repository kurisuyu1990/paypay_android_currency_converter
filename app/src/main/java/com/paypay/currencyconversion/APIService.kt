package com.paypay.currencyconversion

import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    //0c03eb61c66b2dadb15be8a48b26c7ac
    //4199f207dea3fa28a2707f0e720c6364
    @get:GET("api/list?access_key=0c03eb61c66b2dadb15be8a48b26c7ac&format=1")
    val response: Call<Entity>

    @get:GET("api/live?access_key=0c03eb61c66b2dadb15be8a48b26c7ac&format=1")
    val quotes: Call<Entity>
}
