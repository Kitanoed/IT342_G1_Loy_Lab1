package com.example.mobile.api

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // Use 10.0.2.2 for Android emulator to reach host machine's localhost
    // Change this to your machine's IP if testing on a physical device
    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    private val cookieStore = ConcurrentHashMap<String, MutableList<Cookie>>()

    private val cookieJar = object : CookieJar {
        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            cookieStore[url.host] = cookies.toMutableList()
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            return cookieStore[url.host] ?: emptyList()
        }
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi: AuthApi = retrofit.create(AuthApi::class.java)

    fun clearCookies() {
        cookieStore.clear()
    }
}
