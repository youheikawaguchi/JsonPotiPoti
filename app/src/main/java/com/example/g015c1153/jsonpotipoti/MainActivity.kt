package com.example.g015c1153.jsonpotipoti

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    //private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://www.google.co.jp/"
        val handler = Handler()
        val request = Request.Builder()
            .addHeader("Content-Type", "text/plain; charset=utf-8")
            .url(url)
            .build()


        JsonButton.setOnClickListener {
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    val responseText: String? = response.body()?.string()
                    handler.post {
                        JsonText.text = responseText
                    }
                }
            })
        }
    }
}
